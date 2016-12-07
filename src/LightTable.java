import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import java.util.Random;
import javax.swing.Timer;


/**
 * Created by ralfpopescu on 10/11/16.
 */
public class LightTable extends JComponent implements MouseListener, ActionListener{

    ArrayList<PhotoComponent> photoComps = new ArrayList<>();
    ArrayList<ThumbnailComponent> thumbnails = new ArrayList<>();
    ArrayList<Magnet> magnets = new ArrayList<>();
    PhotoComponent currentPhotoComp;
    String mode;
    ModeController mc;
    JPanel main;
    int index;
    TagController tagController;
    Timer timer;
    int speed;

    public LightTable(ModeController modeController, TagController tc){
        mc = modeController; //setup
        mode = "PHOTOVIEW";

        main = new JPanel(new FlowLayout());
        main.setPreferredSize(new Dimension(600, 600));
        main.setSize(new Dimension(600, 600));
        this.add(main);
        speed = 30;
        index = 0; //keeps track of currentPhotoComp index
        timer = new Timer(speed, this);
        //timer.setInitialDelay(pause);


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

                int layer = 0; //determines layer of item
                for (Magnet mag: magnets){
                    mag.setSize(50,25);

                    if(mc.getInitialMagModeSwitch()) {
                        double rx = rng.nextDouble();
                        double ry = rng.nextDouble();

                        int randomx = (int) (rx * 400); //randomly positions thumbnails on magnet mode
                        int randomy = (int) (ry * 400);

                        mag.setLocation(randomx, randomy);

                    }
                    panel.add(mag);
                    layer++;
                }

                for (int i = 0; i < thumbnails.size(); i++) { //adds all thumbnails
                    ThumbnailComponent j = thumbnails.get(i);
                    Dimension size = j.getPreferredSize();

                    j.setSize(size);
                    j.deselect();
                    panel.add(j);
                    layer++;

                    j.setSize(size);
                    if(mc.getInitialMagModeSwitch()) { //makes sure we only randomize on initial mode switch
                        double rx = rng.nextDouble();
                        double ry = rng.nextDouble();

                        int randomx = (int) (rx * 400); //randomly positions thumbnails on magnet mode
                        int randomy = (int) (ry * 400);

                        //j.setBounds(25, 25, 25, 25);
                        j.setLocation(randomx, randomy);

                    }
                }



                main.add(panel);
                mc.setInitialMagModeSwitch(false); //turn off initial switch, only turns on again if you switch magnet mode or add magnet
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
        tagController.updateTags(getTags(), this);
        transferTagsToThumbnails();
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

        boolean contains = false;
        int index = 0;
        for(int i = 0; i<magnets.size(); i++){
            Magnet mag = magnets.get(i);
            if(mag.type.equals(type)){
                contains = true;
                index = i;
            }
        }
        if(!contains) {
            magnets.add(new Magnet(type, this));
            mc.setInitialMagModeSwitch(true);
            updateComponent();
        } else {
            magnets.remove(index);
            updateComponent();
        }
        timer.start();
    }

    public void transferTagsToThumbnails(){
        for(int i =0; i < photoComps.size(); i++){
            ThumbnailComponent tn = thumbnails.get(i);
            PhotoComponent pc = photoComps.get(i);

            boolean[] tags = pc.getTags();

            tn.workTag = tags[0];
            tn.vacationTag = tags[1];
            tn.schoolTag = tags[2];
            tn.familyTag = tags[3];
        }
    }

    public void attraction(){
        /*for(int i = 0; i<thumbnails.size(); i++){
            ThumbnailComponent tn = thumbnails.get(i);
            Point newPos = tn.calculateUltimatePosition(magnets);
            tn.setLocation((int)newPos.getX(),(int)newPos.getY());
        }*/
        timer.start();
    }

    public void moveTowardsEndPoint(){
        for(int i = 0; i<thumbnails.size(); i++){
            ThumbnailComponent tn = thumbnails.get(i);
            Point newPos = tn.calculateUltimatePosition(magnets);

            double delta_x = (newPos.getX() - tn.getX());
            double delta_y = (newPos.getY() - tn.getY());

            System.out.println(newPos.getX() + " " + newPos.getY());

            if(newPos.equals(tn.getLocation())){
                return;
            }
            if(Math.abs(delta_x) < 5 && Math.abs(delta_y) < 5){
                return;
            }

            if(delta_x > 4){
                int speed = 4;
                if(delta_x > 70){
                    speed = 20;
                }
                if(delta_x > 40 && delta_y < 70){
                    speed = 10;
                }
                if(Math.abs(delta_x) > Math.abs(delta_y)) {
                    tn.setLocation(tn.getX() + speed, tn.getY());
                } else {
                    tn.setLocation(tn.getX() + speed/2, tn.getY());
                }
            }
            if(delta_x < 4){
                int speed = 4;
                if(delta_x < 70){
                    speed = 20;
                }
                if(delta_x < 40 && delta_x > 10){
                    speed = 10;
                }
                if(Math.abs(delta_x) > Math.abs(delta_y)) {
                    tn.setLocation(tn.getX() - speed, tn.getY());
                } else {
                    tn.setLocation(tn.getX() - speed/2, tn.getY());
                }
            }
            if(delta_y > 4){
                int speed = 4;
                if(delta_y > 70){
                    speed = 20;
                }
                if(delta_y > 40 && delta_y < 70){
                    speed = 10;
                }

                if(Math.abs(delta_x) < Math.abs(delta_y)) {
                    tn.setLocation(tn.getX(), tn.getY() + speed);
                } else {
                    tn.setLocation(tn.getX(), tn.getY() + speed/2);
                }

            }
            if(delta_y < 4){
                int speed = 4;
                if(delta_y < 70){
                    speed = 20;
                }
                if(delta_y < 40 && delta_y > 10){
                    speed = 10;
                }

                if(Math.abs(delta_x) < Math.abs(delta_y)) {
                    tn.setLocation(tn.getX(), tn.getY() - speed);
                } else {
                    tn.setLocation(tn.getX(), tn.getY() - speed/2);
                }
            }

        }

    }

    public void actionPerformed(ActionEvent e){
        if(mc.getMagnetMode()) {
            moveTowardsEndPoint();
        } else {
            timer.stop();
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
    public void setVacationTag(boolean x){
        if(currentPhotoComp != null) {
            currentPhotoComp.setVacationTag(x);
        }
    }
    public boolean getVacationTag(){
        if(currentPhotoComp != null) {
            return currentPhotoComp.getVacationTag();
        }
        return false;
    }
    public void setSchoolTag(boolean x){
        if(currentPhotoComp != null) {
            currentPhotoComp.setSchoolTag(x);
        }
    }
    public boolean getSchoolTag(){
        if(currentPhotoComp != null) {
            return currentPhotoComp.getSchoolTag();
        }
        return false;
    }
    public void setWorkTag(boolean x){
        if(currentPhotoComp != null) {
            currentPhotoComp.setWorkTag(x);
        }
    }
    public boolean getWorkTag(){
        if(currentPhotoComp != null) {
            return currentPhotoComp.getworkTag();
        }
        return false;
    }
    public void setFamilyTag(boolean x){
        if(currentPhotoComp != null) {
            currentPhotoComp.setFamilyTag(x);
        }
    }
    public boolean getFamilyTag(){
        if(currentPhotoComp != null) {
            return currentPhotoComp.getFamilyTag();
        }
        return false;
    }
}
