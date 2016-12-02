import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by ralfpopescu on 11/27/16.
 */
public class Magnet extends JLabel implements MouseListener, MouseMotionListener {

    String type;
    int xloc;
    int yloc;

    public Magnet (String t){
        type = t;
        xloc = 0;
        yloc = 0;
        this.setText(t);
        this.setOpaque(true);
        this.setBackground(Color.cyan);

        this.setPreferredSize(new Dimension(50, 50));
        this.setSize(new Dimension(50,50));
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

    public void moveMagnet(int delta_x, int delta_y){

    }

    public int getXLoc(){
        return xloc;
    }

    public int getYLoc(){
        return yloc;
    }
}
