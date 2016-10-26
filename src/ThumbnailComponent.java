import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by ralfpopescu on 10/11/16.
 */
public class ThumbnailComponent extends JComponent {

    PhotoComponent photoComponent;
    BufferedImage photo;
    int photoHeight;
    int photoWidth;

    public ThumbnailComponent(PhotoComponent pC){
        photoComponent = pC;
        photo = pC.getPhoto();

        photoWidth = (int)(photo.getWidth() * .2);
        photoHeight = (int)(photo.getHeight() * .2);

        this.setPreferredSize(new Dimension(photoWidth, photoHeight));
        this.setSize(new Dimension(photoWidth, photoHeight));
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.scale(.2,.2);

        g2.drawImage(photo, 0, 0, this);

    }

    public double getPhotoWidth(){
        return photoWidth;
    }

    public double getPhotoHeight(){
        return photoHeight;
    }

}
