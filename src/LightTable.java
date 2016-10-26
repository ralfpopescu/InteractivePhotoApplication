import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;


/**
 * Created by ralfpopescu on 10/11/16.
 */
public class LightTable extends JComponent implements MouseListener{

    ArrayList<PhotoComponent> photoComps = new ArrayList<>();
    ArrayList<ThumbnailComponent> thumbnails = new ArrayList<>();
    ArrayList<Graphics2D> importedPhotos = new ArrayList<>();
    Graphics2D currentPhoto;
    PhotoComponent currentPhotoComp;
    int currentPhotoIndex;
    String mode;
    ModeController mc;
    JPanel main;
    JPanel splitThumbnails;
    int index;
    Border border;

    public LightTable(ModeController modeController){
        mc = modeController;
        mode = "PHOTOVIEW";

        main = new JPanel(new FlowLayout());
        main.setPreferredSize(new Dimension(600, 600));
        main.setSize(new Dimension(600, 600));
        main.setBackground(Color.BLACK);
        this.add(main);
        index = 0;
    }

    public void updateComponent(){


        if(mc.getViewMode().equals("PHOTOVIEW")){
            main.removeAll();
            main.add(currentPhotoComp);
            revalidate();
            repaint();


        }

        if(mc.getViewMode().equals("GRIDVIEW")){
            //add all thumbnails
            main.removeAll();
            JPanel flow = new JPanel(new FlowLayout());
            for(int i = 0; i <thumbnails.size(); i++){
                System.out.println("getting thumbs");
                ThumbnailComponent j = thumbnails.get(i);
                flow.add(j);
            }
            main.add(flow);
            revalidate();
            repaint();

        }

        if(mc.getViewMode().equals("SPLITVIEW")){
            main.removeAll();
            JPanel border = new JPanel(new BorderLayout());
            JPanel flow = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
            flow.addMouseListener(this);

            border.add(currentPhotoComp, BorderLayout.CENTER);
            for(int i = 0; i <thumbnails.size(); i++){
                System.out.println("getting thumbs");
                ThumbnailComponent j = thumbnails.get(i);
                JPanel thumbpanel = new JPanel();

                thumbpanel.setPreferredSize(j.getPreferredSize());
                thumbpanel.setSize(j.getSize());

                //thumbpanel.addMouseListener(this);
                thumbpanel.add(j);

                flow.add(j);
            }
            border.add(flow, BorderLayout.SOUTH);
            main.add(border);
            revalidate();
            repaint();


        }



    }

    public void addPhoto(PhotoComponent photoToAdd){
        System.out.println(photoToAdd);
        photoComps.add(photoToAdd);

        ThumbnailComponent thumbnail = new ThumbnailComponent(photoToAdd);
        //thumbnail.addMouseListener(this);

        thumbnails.add(thumbnail);

        if(currentPhotoComp == null){
            currentPhotoComp = photoToAdd;
        }

        updateComponent();
        //main.add(photoToAdd);
        revalidate();


    }

    public void setCurrentPhoto(){

    }

    public void getCurrentPhoto(){

    }

    public void next(){
        if(index < photoComps.size() - 1){
            index++;
        } else {
            index = 0;
        }
        System.out.println(index);
        currentPhotoComp = photoComps.get(index);
        updateComponent();

    }

    public void prev(){
        if(index > 0){
            index--;
        } else {
            index = photoComps.size() - 1;
        }
        System.out.println(index);
        currentPhotoComp = photoComps.get(index);
        updateComponent();
    }

    public ArrayList<ThumbnailComponent> getThumbnails(){
        return thumbnails;
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
        for(int j = 0; j<thumbnails.size(); j++){
            ThumbnailComponent child = thumbnails.get(j);
            if(child.getBounds().contains(e.getPoint())){
                for(int k = 0; k<thumbnails.size(); k++) {
                    thumbnails.get(k).deselect();
                }
                child.select();
                System.out.println(child.getBounds());
                currentPhotoComp = photoComps.get(j);
                updateComponent();
                break;
            }

        }

    }
}
