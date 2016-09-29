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


    public PhotoComponent(BufferedImage image) {
        photo = image;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(photo, 0, 0, null);
        System.out.println("woop");

    }



}

