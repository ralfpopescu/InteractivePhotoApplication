import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

/**
 * Created by ralfpopescu on 10/11/16.
 */
public class ThumbnailComponent extends JComponent {

    PhotoComponent photoComponent;
    BufferedImage photo;
    BufferedImage selectedPhoto;
    int photoHeight;
    int photoWidth;
    boolean selected;
    private final int desiredHeight = 100;
    private float scaleFactor;

    public ThumbnailComponent(PhotoComponent pC){
        photoComponent = pC;
        photo = pC.getPhoto();
        selected = false;


        scaleFactor = (float)desiredHeight / (float)photoComponent.getPhotoHeight();

        System.out.println(scaleFactor);

        photoWidth = (int)(photo.getWidth() * scaleFactor);
        photoHeight = (int)(photo.getHeight() * scaleFactor);


        RescaleOp op = new RescaleOp(scaleFactor, 100, null);
        selectedPhoto = op.filter(photo, null);


        this.setPreferredSize(new Dimension(photoWidth, photoHeight));
        this.setSize(new Dimension(photoWidth, photoHeight));
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

}
