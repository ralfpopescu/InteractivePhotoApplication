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

    public void updateTag(String tag, boolean x){
        if(tag.equals("VACATION")){
            vacationTag = x;
        }
        if(tag.equals("WORK")){
            workTag = x;
        }
        if(tag.equals("SCHOOL")){
            schoolTag = x;
        }
        if(tag.equals("FAMILY")){
            familyTag = x;
        }
    }




    public void mousePressed(MouseEvent e) {

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

    }

    public void mouseMoved(MouseEvent e) {

    }



}
