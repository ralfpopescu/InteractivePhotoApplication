import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;

/**
 * Created by ralfpopescu on 10/11/16.
 */
public class ThumbnailComponent extends JComponent implements MouseListener, MouseMotionListener {

    PhotoComponent photoComponent;
    BufferedImage photo;
    BufferedImage selectedPhoto;
    int photoHeight;
    int photoWidth;

    int xloc;
    int yloc;

    boolean selected;
    private final int desiredHeight = 100;
    private float scaleFactor;

    boolean vacationTag;
    boolean schoolTag;
    boolean workTag;
    boolean familyTag;

    Point drag;

    public ThumbnailComponent(PhotoComponent pC){
        photoComponent = pC;
        photo = pC.getPhoto();
        selected = false;


        scaleFactor = (float)desiredHeight / (float)photoComponent.getPhotoHeight(); //scales to desired height

        System.out.println(scaleFactor);

        photoWidth = (int)(photo.getWidth() * scaleFactor); //sets photo dimensions accordingly
        photoHeight = (int)(photo.getHeight() * scaleFactor);


        RescaleOp op = new RescaleOp(scaleFactor, 100, null); //filters photo for "selected"
        selectedPhoto = op.filter(photo, null);


        this.setPreferredSize(new Dimension(photoWidth, photoHeight));
        this.setSize(new Dimension(photoWidth, photoHeight));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        xloc = 0;
        yloc = 0;
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.scale(scaleFactor, scaleFactor);
        if (!selected) {
            g2.drawImage(photo, 0, 0, this);
        } else {
            g2.drawImage(selectedPhoto, 0, 0, this);
        }

    }

    public double getPhotoWidth(){
        return photoWidth;
    }

    public double getPhotoHeight(){
        return photoHeight;
    }

    public void select(){
        selected = true;
    }

    public void deselect(){
        selected = false;
    }


    public Point calculateUltimatePosition(ArrayList<Magnet> magnets){
        //System.out.println(schoolTag + "" + vacationTag + workTag + familyTag);
        Point ult_pos = null;
        ArrayList<Magnet> magnetsThatMatter = new ArrayList<>();
        String matters = "";
//        for (Magnet m: magnets){
//            System.out.println(m.getType());
//        }

        for(int i =0; i<magnets.size();i++){
            Magnet mag = magnets.get(i);
            if(mag.getType().equals("SCHOOL") && schoolTag){
                magnetsThatMatter.add(mag);
                matters += mag.getType();
            }
            if(mag.getType().equals("VACATION") && vacationTag){
                magnetsThatMatter.add(mag);
                matters += mag.getType();

            }
            if(mag.getType().equals("WORK") && workTag){
                magnetsThatMatter.add(mag);
                matters += mag.getType();
            }
            if(mag.getType().equals("FAMILY") && familyTag){
                magnetsThatMatter.add(mag);
                matters += mag.getType();
            }

        }
        //System.out.println(matters);
        int final_x = 0;
        int final_y = 0;

        if(magnetsThatMatter.size() > 0) { //calculate average x and y
            for (int j = 0; j < magnetsThatMatter.size(); j++) {
                Magnet mag = magnetsThatMatter.get(j);
                final_x += mag.getX();
                final_y += mag.getY();
            }
            final_x = final_x/magnetsThatMatter.size();
            final_y = final_y/magnetsThatMatter.size();

            final_x -= photoWidth/2 - 25;
            final_y -= photoHeight/2 - 12;

            return new Point(final_x,final_y);
        } else {
            return this.getLocation();
        }


    }



    public void mousePressed(MouseEvent e) {
        drag = new Point(e.getX(),e.getY());
        //System.out.println("m");
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {


    }

    public void mouseDragged(MouseEvent e) {
        int anchorX = drag.x;
        int anchorY = drag.y;

        Point parentOnScreen = getParent().getLocationOnScreen();
        Point mouseOnScreen = e.getLocationOnScreen();
        Point position = new Point(mouseOnScreen.x - parentOnScreen.x -
                anchorX, mouseOnScreen.y - parentOnScreen.y - anchorY);
        setLocation(position);

    }

    public void mouseMoved(MouseEvent e) {

    }



}
