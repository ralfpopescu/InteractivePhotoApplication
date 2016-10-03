import com.sun.org.apache.xpath.internal.operations.Mod;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
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

    private ArrayList<Point> strokeDisplayList = new ArrayList<>();
    private ArrayList<TextRegion> textRegionList = new ArrayList<>();


    public PhotoComponent(BufferedImage image, ModeController modeController2) {
        photo = image;
        photoWidth = photo.getWidth();
        photoHeight = photo.getHeight();
        modeController = modeController2;
        typing = false;
        font  = new Font("SansSerif", Font.BOLD, 14);
        this.setFocusable(true);
        this.requestFocus();

        try {
            paperTexture = ImageIO.read(new File("src/papertexture.jpg"));

        } catch (IOException error) {
            System.out.println("omg");

        }

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setFont(font);
        metrics = g.getFontMetrics(font);

        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = g2.getRenderingHints ();
        rh.put (RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints (rh);

        g.setColor(Color.yellow);

        if (flipped){
            BufferedImage subPaper = paperTexture.getSubimage(0, 0, photoWidth, photoHeight);
            g.drawImage(subPaper, 0, 0, this);

            for(int i=1; i<strokeDisplayList.size(); i++){
                Point currentPoint = strokeDisplayList.get(i);
                Point previousPoint = strokeDisplayList.get(i-1);
                if(previousPoint.getX() != -1 && currentPoint.getX() != -1) {
                    g.drawLine((int) currentPoint.getX(), (int) currentPoint.getY(),
                            (int) previousPoint.getX(), (int) previousPoint.getY());
                }
            }

            for(int i = 0; i<textRegionList.size(); i++){
                TextRegion textRegion = textRegionList.get(i);
                String textString = textRegion.getText();
                System.out.println(textString);
                int lineLength = 0;
                if(textString != null) {
                    lineLength = metrics.stringWidth(textString);
                }
                Point start = textRegion.getStartingPoint();
                int regionWidth = textRegion.getWidth();
                int regionHeight = textRegion.getHeight();

                int numberOfLines = (lineLength / regionWidth) + 1;

                ArrayList<String> lines = new ArrayList<>();
                int blockStart = 0;



                    int counter = 0;
                if(metrics.stringWidth(textString) < regionWidth){
                    lines = new ArrayList<>();
                    lines.add(textString);
                } else {
                    lines = new ArrayList<>();
                    while (counter < textString.length()) {
                        String sub = textString.substring(blockStart, counter);
                        while (metrics.stringWidth(sub) < regionWidth) {
                            sub = textString.substring(blockStart, counter);
                            counter++;
                            if(counter > textString.length()){
                                break;
                            }
                        }
                        lines.add(sub);
                        blockStart = counter;

                    }
                }

                if(textString.length() > 0) {
                    System.out.println(lines.get(0));
                }

                int lineStart = (int)start.getY() + 10;
                int fontHeight = metrics.getHeight();
                int linesDrawn = 0;
                int maxLines = regionHeight/fontHeight;
                System.out.println(maxLines);

                g.setColor(Color.yellow);
                g.fillRect((int)start.getX(),(int)start.getY(), regionWidth, regionHeight);

                g.setColor(Color.black);
                    System.out.println();
                    for(String line: lines) {
                        g.drawString(line, (int)start.getX(), lineStart);
                        lineStart = lineStart + fontHeight;
                        if(linesDrawn >= maxLines){
                            break;
                        }
                        linesDrawn++;
                    }


            }


        } else {
            g.drawImage(photo, 0, 0, this);
            System.out.println("woop");
        }

        this.setFocusable(true);
        this.requestFocus();

    }

    public void mousePressed(MouseEvent e) {
        if(flipped && modeController.getMode()) {
            strokeDisplayList.add(new Point(e.getX(), e.getY()));
            repaint();
        }
        if(!modeController.getMode() && flipped){
            textRegionStartingPoint = e.getPoint();
            typing = false;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(flipped && modeController.getMode()) {
            strokeDisplayList.add(new Point(-1,0));
        }
        if(flipped && !modeController.getMode()){
            if(makingTextRegion){
                textRegionEndPoint = e.getPoint();
                textRegionList.add(new TextRegion(textRegionStartingPoint, textRegionEndPoint, null));
                makingTextRegion = false;
                typing = true;
                repaint();
            }
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {

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
        System.out.println(modeController.getMode());

    }

    public void mouseDragged(MouseEvent e) {
        if(flipped && modeController.getMode()) {
            strokeDisplayList.add(new Point(e.getX(), e.getY()));
            repaint();
        }
        System.out.println("lol");
        if(flipped && !modeController.getMode()){
            makingTextRegion = true;
            textRegionEndPoint = e.getPoint();
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
            System.out.println(ke.getKeyChar());
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent ke){
        /*
        System.out.println(ke.getKeyChar());
        if(typing){
            TextRegion currentTextRegion = textRegionList.get(textRegionList.size() - 1);
            char character = ke.getKeyChar();
            currentTextRegion.addCharacter(character);
            repaint();
        }*/
    }

    @Override
    public void keyReleased(KeyEvent ke){}


}

