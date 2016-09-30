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

/**
 * Created by ralfpopescu on 9/20/16.
 */
public class PhotoComponent extends JComponent implements MouseListener  {
    private BufferedImage photo;
    private BufferedImage paperTexture;
    private int photoWidth;
    private int photoHeight;
    private boolean flipped = false;
    private ArrayList<Graphics2D> strokeDisplayList = new ArrayList<>();
    private ArrayList<TextRegion> textRegionList = new ArrayList<>();


    public PhotoComponent(BufferedImage image) {
        photo = image;
        photoWidth = photo.getWidth();
        photoHeight = photo.getHeight();

        try {
            paperTexture = ImageIO.read(new File("src/papertexture.jpg"));

        } catch (IOException error) {
            System.out.println("omg");

        }

        this.addMouseListener(this);

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (flipped){
            BufferedImage subPaper = paperTexture.getSubimage(0, 0, photoWidth, photoHeight);
            g.drawImage(subPaper, 0, 0, this);
            System.out.println("woop");
        } else {
            g.drawImage(photo, 0, 0, this);
            System.out.println("woop");
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


    }


}

