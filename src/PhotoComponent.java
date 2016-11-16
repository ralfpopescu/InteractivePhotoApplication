

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.xml.soap.Text;
import java.io.*;
import java.awt.*;


/**
 * Created by ralfpopescu on 9/20/16.
 */
public class PhotoComponent extends JComponent implements MouseListener, MouseMotionListener,  KeyListener  {
    private BufferedImage photo;
    private BufferedImage paperTexture;
    private int photoWidth;
    private int photoHeight;
    private boolean flipped = false;
    private ModeController modeController;
    private Point textRegionStartingPoint;
    private Point textRegionEndPoint;
    private boolean makingTextRegion;
    private boolean typing;
    private Font font;
    private FontMetrics metrics;
    public LightTable lightTable;

    boolean vacationTag;
    boolean schoolTag;
    boolean workTag;
    boolean familyTag;

    private ArrayList<Point> strokeDisplayList = new ArrayList<>();
    private ArrayList<TextRegion> textRegionList = new ArrayList<>();

    private ArrayList<Point> gestureList = new ArrayList<>();
    private ArrayList<Point> boundingBox = new ArrayList<>();
    private Point drag;
    double[] extremePoints;

    private Siger siger;


    public PhotoComponent(BufferedImage image, ModeController modeController2, LightTable lt) {
        photo = image;
        photoWidth = photo.getWidth();
        photoHeight = photo.getHeight();
        modeController = modeController2; //modecontroller handles mode changes
        typing = false;
        font  = new Font("SansSerif", Font.BOLD, 14);
        this.setFocusable(true);
        this.requestFocus();


        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);

        this.setPreferredSize(new Dimension(photoWidth, photoHeight));
        this.setSize(new Dimension(photoWidth, photoHeight));

        siger = new Siger();
        lightTable = lt;

        vacationTag = false;
        schoolTag = false;
        workTag = false;
        familyTag = false;

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setFont(font);
        metrics = g.getFontMetrics(font);

        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = g2.getRenderingHints ();
        rh.put(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        g.setColor(Color.black);

        if (flipped){
            g.setColor(Color.lightGray);
            g.fillRect(0, 0, photoWidth, photoHeight);
            g.setColor(Color.black);
            ArrayList<Point> selectedStrokes = selectStrokesInBox();
            ArrayList<TextRegion> selectedTextRegions = selectTextRegionsInBox();

            for(int i=1; i<strokeDisplayList.size(); i++){ //draws strokes
                Point currentPoint = strokeDisplayList.get(i);
                Point previousPoint = strokeDisplayList.get(i-1);


                if(previousPoint.getX() > photoWidth || currentPoint.getY() > photoHeight){ //makes sure we draw inside bounds
                    continue;
                } else {

                    if (previousPoint.getX() != -1 && currentPoint.getX() != -1) { //draws lines, -1 signifies mouse releases

                        if(modeController.dragging() && selectedStrokes.contains(currentPoint)) { //in dragging mode
                            g2.setColor(Color.yellow);
                        } else {
                            g2.setColor(Color.black);
                        }

                        if(modeController.getSelectMode() && !modeController.dragging()){
                            for(Point p: boundingBox){
                                g2.drawLine((int)p.getX(),(int)p.getY(),(int)p.getX(),(int)p.getY());
                            }
                        }


                        g2.drawLine((int) currentPoint.getX(), (int) currentPoint.getY(),
                                (int) previousPoint.getX(), (int) previousPoint.getY());
                    }
                }
            }

            for(int i = 0; i<textRegionList.size(); i++){
                TextRegion textRegion = textRegionList.get(i);
                String textString = textRegion.getText();

                Point start = textRegion.getStartingPoint();


                if(textRegion.getEndPoint().getX() > photoWidth){ //handles if you drag mouse out of bounds for region
                    textRegion.setEndPoint(photoWidth, (int)textRegion.getEndPoint().getY());
                }

                if(textRegion.getEndPoint().getY() > photoHeight){
                    textRegion.setEndPoint((int)textRegion.getEndPoint().getX(), photoHeight);
                }

                textRegion.updateHeightWidth();

                int regionWidth = textRegion.getWidth();
                int regionHeight = textRegion.getHeight();



                ArrayList<String> lines = new ArrayList<>(); //stores blocks of lines to draw
                int blockStart = 0;



                    int counter = 0;
                if(metrics.stringWidth(textString) < regionWidth){ //tackles case of one line
                    lines = new ArrayList<>();
                    lines.add(textString);
                } else {
                    lines = new ArrayList<>();
                    while (counter < textString.length()) { //goes through all text to create blocks
                        String sub = textString.substring(blockStart, counter); //check to see if sub fits text region
                        while (metrics.stringWidth(sub) < regionWidth - metrics.stringWidth(" -")) { //leaves space for hyphen
                            sub = textString.substring(blockStart, counter);
                            counter++;
                            if(counter > textString.length()){
                                break;
                            }
                        }
                        char c = sub.charAt(sub.length()-1);
                        if(c != ' ' && metrics.stringWidth(sub) > regionWidth - metrics.stringWidth(" -")){ //adding a hyphen
                            sub = sub.concat("-");
                        }
                        lines.add(sub);
                        blockStart = counter - 1;
                    }
                }

                //lines = splitBySpaces(textString, regionWidth, metrics);

                if(textString.length() > 0) {

                    String lastLine = lines.get(lines.size() - 1); //makes sure there isn't a running hyphen
                    if (lastLine.charAt(lastLine.length() - 1) == '-') {
                        lastLine = lastLine.substring(0,lastLine.length()-2);
                        lines.remove(lines.size()-1);
                        lines.add(lastLine);
                    }
                }

                int lineStart = (int)start.getY() + 20; //a little offset to make it look cleaner
                int fontHeight = metrics.getHeight();
                int linesDrawn = 0;

                int textToBottom = photoHeight - (int)textRegion.getStartingPoint().getY();

                //int maxLines = regionHeight/fontHeight; //lines the current region can support
                int maxLines = textToBottom/fontHeight;

                g.setColor(Color.yellow);
                g.fillRect((int)start.getX(),(int)start.getY(), regionWidth, regionHeight);
                int numOfLines = lines.size();
                if(numOfLines*fontHeight > regionHeight ){ //handles rectangle bounds
                    int ysize = fontHeight*numOfLines;
                    if(ysize > fontHeight*maxLines){
                        ysize = fontHeight*maxLines;
                    }
                    g.fillRect((int) start.getX(), (int) start.getY(), regionWidth, ysize + (fontHeight / 2));

                }

                g.setColor(Color.black);

                    int resizeAmount = 1;
                    for(String line: lines) { //draw lines
                        g.drawString(line, (int)start.getX(), lineStart);
                        lineStart = lineStart + fontHeight;
                        if(linesDrawn >= maxLines - 1){ //was just break
//
                            break;

                        }
                        linesDrawn++;
                    }


            }


        } else {
            g.drawImage(photo, 0, 0, this);
            if(modeController.getSelectMode()){
                for(Point p: gestureList){
                    g2.drawLine((int)p.getX(),(int)p.getY(),(int)p.getX(),(int)p.getY());
                }
            }
        }

        this.setFocusable(true);
        this.requestFocus();

    }

    public BufferedImage getPhoto(){
        return photo;
    }


    public int getPhotoWidth(){
        return photoWidth;
    }

    public int getPhotoHeight(){
        return photoHeight;
    }

    public ModeController getModeController(){
        return modeController;
    }

    public ArrayList<Point> selectStrokesInBox(){
        ArrayList<Point> selectedStrokes = new ArrayList<>();

        if(extremePoints != null) {
            for (Point p : strokeDisplayList) {
                double x = p.getX();
                double y = p.getY();

                if (x < extremePoints[1] && x > extremePoints[3]
                        && y < extremePoints[2] && y > extremePoints[0]) {
                    selectedStrokes.add(p);
                }

            }
        }
        return selectedStrokes;
    }

    public ArrayList<TextRegion> selectTextRegionsInBox(){
        ArrayList<TextRegion> selectedTextRegions = new ArrayList<>();
        if(extremePoints != null) {
            for (TextRegion t : textRegionList) {
                double x_s = t.getStartingPoint().getX();
                double y_s = t.getStartingPoint().getY();
                double x_e = t.getEndPoint().getX();
                double y_e = t.getEndPoint().getY();

                if (x_e < extremePoints[1] && x_s > extremePoints[3] &&
                        y_e < extremePoints[2] && y_s > extremePoints[0]) {
                    selectedTextRegions.add(t);
                }

            }
        }
        return selectedTextRegions;
    }

    public boolean[] getTags(){
        boolean[] array = new boolean[4];
        array[0] = workTag;
        array[1] = vacationTag;
        array[2] = schoolTag;
        array[3] = familyTag;
        return array;
    }


    public void mousePressed(MouseEvent e) {
        if(flipped && modeController.getMode() && !modeController.getSelectMode()) {
            strokeDisplayList.add(new Point(e.getX(), e.getY()));
            repaint();
        }
        if(!modeController.getMode() && flipped && !modeController.getSelectMode()){
            textRegionStartingPoint = e.getPoint();
            typing = false;
        }

        if(!flipped){
            gestureList.add(new Point(e.getX(), e.getY()));
        }
        if(flipped && modeController.getSelectMode()){
            if(modeController.dragging()){
                drag = new Point(e.getX(), e.getY());
            } else {
                if(boundingBox.size()>0){
                    boundingBox.clear();
                }
                boundingBox.add(new Point(e.getX(), e.getY()));
            }

        }
    }

    public void mouseReleased(MouseEvent e) {
        if(flipped && modeController.getMode() && !modeController.getSelectMode()) {
            strokeDisplayList.add(new Point(-1, 0)); //adds negative point to "stop" strokes
        }
        if(flipped && !modeController.getMode() && !modeController.getSelectMode()){
            if(makingTextRegion){
                textRegionEndPoint = e.getPoint();
                textRegionList.add(new TextRegion(textRegionStartingPoint, textRegionEndPoint, null));
                makingTextRegion = false;
                typing = true;
                repaint();
            }
        }

        if(!flipped){
            ArrayList<Integer> points = siger.createGestureString(gestureList);
            points = siger.stripSmalls(points);
            points = siger.stripDuplicates(points);
            String template = siger.matchTemplate(points);
            System.out.println(template);
            gestureList.clear();

            if(template.equals("UNREC")){
                System.out.println("unrecognized gesture");
            }

            if(template.equals("RIGHTANGLE")){
                lightTable.next();
            }
            if(template.equals("LEFTANGLE")){
                lightTable.prev();
            }
            if(template.equals("PIGTAIL")){
                lightTable.delete();
            }

            if(template.equals("FAMILY")){
                familyTag = !familyTag;
            }
            if(template.equals("WORK")){
                workTag = !workTag;
            }
            if(template.equals("VACATION")){
                vacationTag = !vacationTag;
            }
            if(template.equals("SCHOOL")){
                schoolTag = !schoolTag;
            }
            lightTable.refresh();
        }
        if(modeController.getSelectMode() && flipped){

            extremePoints = siger.getExtremesNESW(boundingBox);
            int[] boundingRect = siger.makeBoundingBox(extremePoints);

            modeController.setDragging(!modeController.dragging());

            System.out.println("right click");

            repaint();

            boundingBox.clear();
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            System.out.println("double clicked");

        int x = e.getX();
        int y = e.getY();

            if(flipped) {
                flipped = false;
            } else {
                flipped = true;

            }
            revalidate();
            repaint();
        }

    }

    public void mouseDragged(MouseEvent e) {
        if(flipped && modeController.getMode() && !modeController.getSelectMode()) {
            strokeDisplayList.add(new Point(e.getX(), e.getY()));
            repaint();
        }
        if(flipped && !modeController.getMode() && !modeController.getSelectMode()){
            makingTextRegion = true;
            textRegionEndPoint = e.getPoint();
        }
        if(!flipped){
            gestureList.add(new Point(e.getX(), e.getY()));
            repaint();
        }
        if(modeController.getSelectMode() && flipped){
            if(modeController.dragging()) {

                double xDistanceFromPrev = e.getX() - drag.getX();
                double yDistanceFromPrev = e.getY() - drag.getY();

                drag = new Point(e.getX(), e.getY());

                movePoints(xDistanceFromPrev, yDistanceFromPrev);
                moveTextRegions(xDistanceFromPrev, yDistanceFromPrev);

                repaint();

            } else { //otherwise we are creating the selection bounding box
                boundingBox.add(new Point(e.getX(), e.getY()));
                repaint();
            }
        }
    }

    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent ke){

        if(typing){
            TextRegion currentTextRegion = textRegionList.get(textRegionList.size() - 1);
            char character = ke.getKeyChar();
            currentTextRegion.addCharacter(character);
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent ke){

    }

    @Override
    public void keyReleased(KeyEvent ke){}

    public void movePoints(double delta_x, double delta_y){
        ArrayList<Point> selectedStrokes = selectStrokesInBox();


        for(Point p: strokeDisplayList){
            if(selectedStrokes.contains(p)){
                p.setLocation(p.getX()+delta_x, p.getY() + delta_y);
            }
        }
    }

    public void moveTextRegions(double delta_x, double delta_y){
        ArrayList<TextRegion> selectedTRs = selectTextRegionsInBox();


        for(TextRegion t: textRegionList){
            if(selectedTRs.contains(t)){
                Point end = t.getEndPoint();
                Point start = t.getStartingPoint();

                double endx = end.getX();
                double endy = end.getY();
                double startx = start.getX();
                double starty = start.getY();

                t.setEndPoint(endx + delta_x, endy + delta_y);
                t.setStartingPoint(startx + delta_x, starty + delta_y);
            }
        }
    }


}

