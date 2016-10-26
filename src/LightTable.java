import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;


/**
 * Created by ralfpopescu on 10/11/16.
 */
public class LightTable extends JComponent {

    ArrayList<PhotoComponent> photoComps = new ArrayList<>();
    ArrayList<ThumbnailComponent> thumbnails = new ArrayList<>();
    ArrayList<Graphics2D> importedPhotos = new ArrayList<>();
    Graphics2D currentPhoto;
    PhotoComponent currentPhotoComp;
    String mode;
    ModeController mc;
    JPanel main;
    JPanel splitThumbnails;
    int index;

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

    public JPanel updateComponent(){

        //main.add(photoComps.get(index));
//        JPanel test = new JPanel();
//
//        PhotoComponent pc = new PhotoComponent(photoComps.get(index).getPhoto(),
//                photoComps.get(index).getModeController());
//        test.add(pc);
//        test.setPreferredSize(new Dimension(200, 200));
//        test.setSize(new Dimension(200, 200));
//        main.add(test, BorderLayout.CENTER);
//        revalidate();
        //repaint();

        if(mc.getViewMode().equals("PHOTOVIEW")){
            main.removeAll();
            for(int i = 0; i <photoComps.size(); i++){
                PhotoComponent j = photoComps.get(i);
                main.add(j);
            }
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
            //BoxLayout boxLayout = new BoxLayout();
            main = new JPanel(new BorderLayout());
            main.setPreferredSize(new Dimension(500,500));
            if(photoComps.size() > 0) {
                currentPhotoComp = photoComps.get(0);
//                currentPhotoComp.setPreferredSize(new Dimension(
//                        currentPhotoComp.getPhotoWidth(), currentPhotoComp.getPhotoWidth()));


                JPanel photo = new JPanel();
                photo.add(currentPhotoComp, BorderLayout.CENTER);

                ThumbnailComponent tn = new ThumbnailComponent(currentPhotoComp);
                tn.setPreferredSize(new Dimension(
                        (int) tn.getPhotoWidth(), (int) tn.getPhotoWidth()));
                JPanel tns = new JPanel();
                tns.add(tn);

                main.add(photo, BorderLayout.CENTER);
                main.add(tns, BorderLayout.SOUTH);
                revalidate();
            }


        }

        return main;

    }

    public void addPhoto(PhotoComponent photoToAdd){
        System.out.println(photoToAdd);
        photoComps.add(photoToAdd);
        thumbnails.add(new ThumbnailComponent(photoToAdd));
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

    }

    public void prev(){
        if(index > 0){
            index--;
        } else {
            index = photoComps.size();
        }
    }

    public ArrayList<ThumbnailComponent> getThumbnails(){
        return thumbnails;
    }
}
