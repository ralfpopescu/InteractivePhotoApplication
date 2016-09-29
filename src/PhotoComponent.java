import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by ralfpopescu on 9/20/16.
 */
public class PhotoComponent extends JComponent {
    private BufferedImage photo;
    private boolean flipped = false;
    private ArrayList<Graphics2D> strokeDisplayList = new ArrayList<>();
    private ArrayList<TextRegion> textRegionList = new ArrayList<>();


    public PhotoComponent(BufferedImage g) {
        photo = g;
    }

    public void paintComponent(Graphics g) {
        if (flipped) {
            //white back
            //draw strokeDisplayList
            //draw TextRegions
        }
        else {
            if(photo != null) {
                g.drawImage(photo, 0, 0, null);
            }
        }

    }



}

