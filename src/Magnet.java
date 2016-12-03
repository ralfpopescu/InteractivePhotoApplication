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
    Point drag;
    LightTable lightTable;

    public Magnet (String t, LightTable lt){
        type = t;
        xloc = 0;
        yloc = 0;
        this.setText(t);
        this.setOpaque(true);
        this.setBackground(Color.cyan);

        this.setPreferredSize(new Dimension(50, 50));
        this.setSize(new Dimension(50, 50));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        lightTable = lt;
    }

    public String getType(){
        return type;
    }

    public void mousePressed(MouseEvent e) {
        drag = new Point(e.getX(),e.getY());
        System.out.println("m");
    }

    public void mouseReleased(MouseEvent e) {
        lightTable.attraction();

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

    public void moveMagnet(int delta_x, int delta_y){
        this.setLocation(this.getX() + delta_x, this.getY() + delta_y);
    }

    public int getXLoc(){
        return xloc;
    }

    public int getYLoc(){
        return yloc;
    }
}
