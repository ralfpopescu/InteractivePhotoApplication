import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import java.util.Random;


/**
 * Created by ralfpopescu on 10/11/16.
 */
public class LightTable extends JComponent implements MouseListener{

    ArrayList<PhotoComponent> photoComps = new ArrayList<>();
    ArrayList<ThumbnailComponent> thumbnails = new ArrayList<>();
    ArrayList<Magnet> magnets = new ArrayList<>();
    PhotoComponent currentPhotoComp;
    String mode;
    ModeController mc;
    JPanel main;
    int index;
    TagController tagController;

    public LightTable(ModeController modeController, TagController tc){
        mc = modeController; //setup
        mode = "PHOTOVIEW";

        main = new JPanel(new FlowLayout());
        main.setPreferredSize(new Dimension(600, 600));
        main.setSize(new Dimension(600, 600));
        this.add(main);
        index = 0; //keeps track of currentPhotoComp index

        tagController = tc;
    }

    public void updateComponent(){ //"repainting"


        if(mc.getViewMode().equals("PHOTOVIEW")){


                main.removeAll();
                JScrollPane photoScroll = new JScrollPane(currentPhotoComp,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, //scrollpane to hold photo
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                photoScroll.setMaximumSize(new Dimension(400, 400));
                photoScroll.setPreferredSize(new Dimension(400, 400));
                photoScroll.setSize(new Dimension(400, 400));

                main.add(photoScroll); //basic photo component

                if (photoComps.size() == 0) {
                    main.removeAll();
                }

                revalidate();
                repaint();



        }

        if(mc.getViewMode().equals("GRIDVIEW")){

            if(mc.getMagnetMode()){
                main.removeAll();
                JPanel panel = new JPanel();
                panel.setLayout(null);
                Random rng = new Random();
                panel.setPreferredSize(new Dimension(500, 500));


                for (int i = 0; i < thumbnails.size(); i++) { //adds all thumbnails
                    ThumbnailComponent j = thumbnails.get(i);
                    Dimension size = j.getPreferredSize();

                    j.setSize(size);
                    j.deselect();
                    panel.add(j);

                    j.setSize(size);

                    double rx = rng.nextDouble();
                    double ry = rng.nextDouble();

                    int randomx = (int)(rx * 400); //randomly positions thumbnails on magnet mode
                    int randomy = (int)(ry * 400);

                    j.setBounds(25, 25, 25, 25);
                    j.setLocation(randomx, randomy);



                }

                for (Magnet mag: magnets){
                    double rx = rng.nextDouble();
                    double ry = rng.nextDouble();

                    int randomx = (int)(rx * 400); //randomly positions thumbnails on magnet mode
                    int randomy = (int)(ry * 400);

                    mag.setBounds(40, 20, 40, 20);
                    mag.setLocation(randomx, randomy);
                    mag.setSize(50,50);

                    panel.add(mag);

                    System.out.println("added");


                }

                main.add(panel);
                revalidate();
                repaint();

            } else {

                //add all thumbnails
                main.removeAll();
                JPanel flow = new JPanel(new GridLayout(3, 3, 10, 10));
                flow.addMouseListener(this);
                flow.setPreferredSize(new Dimension(500, 500));
                for (int i = 0; i < thumbnails.size(); i++) { //adds all thumbnails
                    ThumbnailComponent j = thumbnails.get(i);
                    j.deselect();
                    flow.add(j);
                }
                int indexOf = photoComps.indexOf(currentPhotoComp);
                if (indexOf >= 0) {
                    thumbnails.get(indexOf).select(); //select highlights photo
                }
                main.add(flow);
                revalidate();
                repaint();

            }

        }

        if(mc.getViewMode().equals("SPLITVIEW")){
            main.removeAll();
            JPanel border = new JPanel(new BorderLayout());
            JPanel flow = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));



            flow.addMouseListener(this);

            JScrollPane photoScroll = new JScrollPane(currentPhotoComp,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, //scrollpane to hold photo
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            photoScroll.setMaximumSize(new Dimension(300, 300));
            photoScroll.setPreferredSize(new Dimension(300, 300));
            photoScroll.setSize(new Dimension(300, 300));

            border.add(photoScroll, BorderLayout.CENTER); //big photo

            for(int i = 0; i <thumbnails.size(); i++){ //adds all thumbnails
                ThumbnailComponent j = thumbnails.get(i);
                JPanel thumbpanel = new JPanel();

                thumbpanel.setPreferredSize(j.getPreferredSize());
                thumbpanel.setSize(j.getSize());

                thumbpanel.add(j);

                flow.add(j);
            }
            JScrollPane tnScroll = new JScrollPane(flow, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            tnScroll.setPreferredSize(new Dimension(600, 110));
            tnScroll.setSize(new Dimension(600, 110));

            for(ThumbnailComponent tn: thumbnails){ //deselects all thumbnails so we can select just one
                tn.deselect();
            }
            int indexOf = photoComps.indexOf(currentPhotoComp);
            if(indexOf >= 0) {
                thumbnails.get(indexOf).select();
            }

            border.add(tnScroll, BorderLayout.SOUTH); //adds to bottom of screen
            main.add(border);

            if(photoComps.size() == 0){
                main.removeAll();
            }

            revalidate();
            repaint();


        }



    }

    public void addPhoto(PhotoComponent photoToAdd){
        photoComps.add(photoToAdd);

        ThumbnailComponent thumbnail = new ThumbnailComponent(photoToAdd); //makes thumbnail

        thumbnails.add(thumbnail);

        if(currentPhotoComp == null){ //edge case if no photo already
            currentPhotoComp = photoToAdd;
            currentPhotoComp.setMaximumSize(new Dimension(400, 400));
        }

        updateComponent();
        revalidate();


    }


    public void next(){
        if(index < photoComps.size() - 1){
            index++;
        } else {
            index = 0;
        }
        currentPhotoComp = photoComps.get(index);
        updateComponent();

    }

    public void prev(){
        if(index > 0){
            index--;
        } else {
            index = photoComps.size() - 1;
        }
        currentPhotoComp = photoComps.get(index);
        updateComponent();
    }

    public void delete(){
        if(photoComps.size() > 0){ //makes sure there is photo first
            System.out.println(photoComps.size());
            if(photoComps.size() == 1){ //edge case
                main.removeAll();
                photoComps.clear();
                thumbnails.clear();
                index = 0;
                updateComponent();
            } else { //removes components and sets current photo to the next index
                photoComps.remove(index);
                thumbnails.remove(index);
                if(index < photoComps.size() - 1) {
                    //index++;
                } else {
                    index = 0;
                }
                currentPhotoComp = photoComps.get(index);
            }

        }
        updateComponent();

    }

    public ArrayList<ThumbnailComponent> getThumbnails(){
        return thumbnails;
    }

    public void refresh(){
        tagController.updateTags(getTags());
        super.revalidate();
        super.repaint();
    }

    public boolean[] getTags(){
        if(currentPhotoComp != null) {
            return currentPhotoComp.getTags();
        } else {
            return null;
        }
    }

    public void setGestureStatus(String stat){
        tagController.setGestureStatus(stat);
    }

    public void addMagnet(String type){
        magnets.add(new Magnet(type));
        updateComponent();
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
        if (e.getClickCount() == 2){
            mc.setViewMode("PHOTOVIEW");
        }
        for(int j = 0; j<thumbnails.size(); j++){ //for every thumbnail we see if clicked, then select
            ThumbnailComponent child = thumbnails.get(j);
            if(child.getBounds().contains(e.getPoint())){
                for(int k = 0; k<thumbnails.size(); k++) {
                    thumbnails.get(k).deselect();
                }
                child.select();
                System.out.println(child.getBounds());
                currentPhotoComp = photoComps.get(j);
                index = j;
                currentPhotoComp.setMaximumSize(new Dimension(400,400));
                updateComponent();
                break;
            }

        }

    }
}
