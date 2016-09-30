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
    private boolean mode = true;
    private ModeController modeController;

    private ArrayList<Point> strokeDisplayList = new ArrayList<>();
    private ArrayList<TextRegion> textRegionList = new ArrayList<>();


    public PhotoComponent(BufferedImage image, ModeController modeController2) {
        photo = image;
        photoWidth = photo.getWidth();
        photoHeight = photo.getHeight();
        modeController = modeController2;

        try {
            paperTexture = ImageIO.read(new File("src/papertexture.jpg"));

        } catch (IOException error) {
            System.out.println("omg");

        }

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = g2.getRenderingHints ();
        rh.put (RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints (rh);

        g.setColor(Color.black);

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
        } else {
            g.drawImage(photo, 0, 0, this);
            System.out.println("woop");
        }



    }

    public void mousePressed(MouseEvent e) {
        if(flipped && modeController.getMode()) {
            strokeDisplayList.add(new Point(e.getX(), e.getY()));
            repaint();
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(flipped && modeController.getMode()) {
            strokeDisplayList.add(new Point(-1,0));
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
    }

    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent ke){}

    @Override
    public void keyPressed(KeyEvent ke){}

    @Override
    public void keyReleased(KeyEvent ke){}


}

