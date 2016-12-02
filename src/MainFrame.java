/**
 * Created by ralfpopescu on 8/30/16.
 */

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MainFrame extends JFrame implements MenuListener, ActionListener, KeyListener, MouseListener {

    JToggleButton vacationToggle, familyToggle, schoolToggle, workToggle;
    JButton vacMag, famMag, schoolMag, workMag;
    JLabel statusLabel;
    JPanel jpnlMain;
    JMenuBar menuBar;
    JMenu fileMenu, viewMenu;
    JMenuItem importItem, deleteItem, exitItem;
    JRadioButtonMenuItem photoView, gridView, splitView;
    BufferedImage currentPhoto;
    ModeController modeController;
    Boolean hasPicture = false;
    JScrollPane scrollPane;
    LightTable lightTable;
    JPanel ltPanel;
    TagController tagController;


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            MainFrame ex = new MainFrame();
            ex.setVisible(true);
        });
    }

    public MainFrame() {

        initUI();
        //basic setup

        //toggle buttons for tagging, will be abstracted later for functionality
        vacationToggle = new JToggleButton("Vacation");
        familyToggle = new JToggleButton("Family");
        schoolToggle = new JToggleButton("School");
        workToggle = new JToggleButton("Work");

        vacationToggle.setAlignmentX(Component.LEFT_ALIGNMENT);
        familyToggle.setAlignmentX(Component.LEFT_ALIGNMENT);
        schoolToggle.setAlignmentX(Component.LEFT_ALIGNMENT);
        workToggle.setAlignmentX(Component.LEFT_ALIGNMENT);


        vacationToggle.setMinimumSize(new Dimension(50, 25));
        vacationToggle.setPreferredSize(new Dimension(100, 25));
        vacationToggle.setMaximumSize(new Dimension(300,
                300));

        familyToggle.setMinimumSize(new Dimension(50, 25));
        familyToggle.setPreferredSize(new Dimension(100, 25));
        familyToggle.setMaximumSize(new Dimension(300,
                300));

        schoolToggle.setMinimumSize(new Dimension(50, 25));
        schoolToggle.setPreferredSize(new Dimension(100, 25));
        schoolToggle.setMaximumSize(new Dimension(300,
                300));

        workToggle.setMinimumSize(new Dimension(50, 25));
        workToggle.setPreferredSize(new Dimension(100, 25));
        workToggle.setMaximumSize(new Dimension(300,
                300));

        //Status bar that changes on "Draw Mode" and "Text Mode" controls
        statusLabel = new JLabel("Status:");

        tagController = new TagController(vacationToggle, familyToggle, workToggle, schoolToggle, statusLabel);

        modeController = new ModeController();
        lightTable = new LightTable(modeController, tagController);
        lightTable.setPreferredSize(new Dimension(500, 500));
        lightTable.setSize(new Dimension(200, 200));

        //Main Borderlayout organizes all other elements
        jpnlMain = new JPanel(new BorderLayout());
        jpnlMain.add(lightTable, BorderLayout.CENTER);


        //prevents window from being minimized to distortion
        this.setMinimumSize(new Dimension(200,250));

        //creates menu bar, abstracted away for functionality


        menuBar = new JMenuBar();
        //menu items
        fileMenu = new JMenu("File");
        viewMenu = new JMenu("View");

        importItem = new JMenuItem("Import");
        importItem.addActionListener(this);

        deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(this);

        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this);

        fileMenu.add(importItem);
        fileMenu.add(deleteItem);
        fileMenu.add(exitItem);

        photoView = new JRadioButtonMenuItem("Photo View");
        gridView = new JRadioButtonMenuItem("Grid View");
        splitView = new JRadioButtonMenuItem("Split View");

        photoView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Photo View Mode");
                modeController.setViewMode("PHOTOVIEW");
                lightTable.updateComponent();
                revalidate();
            }
        });

        gridView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Grid View Mode");
                modeController.setViewMode("GRIDVIEW");
                lightTable.updateComponent();
                revalidate();
            }
        });

        splitView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Split View Mode");
                modeController.setViewMode("SPLITVIEW");
                lightTable.updateComponent();
                revalidate();
            }
        });


        ButtonGroup viewButtonGroup = new ButtonGroup();

        viewButtonGroup.add(photoView);
        viewButtonGroup.add(gridView);
        viewButtonGroup.add(splitView);

        viewMenu.add(photoView);
        viewMenu.add(gridView);
        viewMenu.add(splitView);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);

        this.setJMenuBar(menuBar);



        //Toolbox JPanel on the left of the window, basic setup
        JPanel jpnlWest = new JPanel();
        BoxLayout westBox = new BoxLayout(jpnlWest, BoxLayout.Y_AXIS); //box layout for top-down structure
        jpnlWest.setLayout(westBox);



        jpnlWest.add(vacationToggle);
        jpnlWest.add(familyToggle);
        jpnlWest.add(schoolToggle);
        jpnlWest.add(workToggle);

        //Drawing/Text mode buttons
        JRadioButton drawingMode = new JRadioButton("Draw");
        JRadioButton textMode = new JRadioButton("Text");
        JRadioButton selectMode = new JRadioButton("Select");

        JRadioButton magnetMode = new JRadioButton("Magnet");

        //Action Listeners for changing status label
        drawingMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Drawing Mode");
                modeController.setMode(true);
                modeController.setSelectMode(false);

            }
        });

        textMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Text Mode");
                modeController.setMode(false);
                modeController.setSelectMode(false);

            }
        });

        selectMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Select Mode");
                modeController.setSelectMode(true);

            }
        });

        magnetMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modeController.setMagnetMode(!modeController.getMagnetMode());
                lightTable.updateComponent();

            }
        });

        //adds radio buttons to button group for mutual exclusivity
        ButtonGroup drawTextGroup = new ButtonGroup();
        drawTextGroup.add(drawingMode);
        drawTextGroup.add(textMode);
        drawTextGroup.add(selectMode);



        //organizes draw and text mode buttons side by side
        JPanel drawTextPanel = new JPanel(new BorderLayout());
        JPanel dtPane = new JPanel();
        dtPane.add(drawingMode);
        dtPane.add(textMode, BorderLayout.NORTH);
        dtPane.add(selectMode, BorderLayout.NORTH);
        dtPane.add(magnetMode, BorderLayout.NORTH);
        dtPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        drawTextPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        drawTextPanel.add(dtPane, BorderLayout.NORTH);

        //magnet buttons
        vacMag = new JButton("Vac Mag");
        famMag = new JButton("Fam Mag");
        schoolMag = new JButton("School Mag");
        workMag = new JButton("Work Mag");

        vacMag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lightTable.addMagnet("VACATION");

            }
        });

        famMag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lightTable.addMagnet("FAMILY");

            }
        });

        workMag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lightTable.addMagnet("WORK");

            }
        });

        schoolMag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lightTable.addMagnet("SCHOOL");

            }
        });

        JPanel magPane = new JPanel();
        magPane.add(vacMag);
        magPane.add(famMag);
        magPane.add(workMag);
        magPane.add(schoolMag);

        drawTextPanel.add(magPane, BorderLayout.CENTER);


        //forward and backward buttons
        JButton backward = new JButton("<-");
        JButton forward = new JButton("->");

        backward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Back");
                lightTable.prev();
                revalidate();
            }
        });

        forward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Next");
                lightTable.next();
                revalidate();
            }
        });

        backward.setMinimumSize(new Dimension(25, 25));
        backward.setPreferredSize(new Dimension(25, 25));
        backward.setMaximumSize(new Dimension(100, 25));

        forward.setMinimumSize(new Dimension(25, 25));
        forward.setPreferredSize(new Dimension(25, 25));
        forward.setMaximumSize(new Dimension(100, 25));


        JPanel jpnlBackwardForward = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jpnlBackwardForward.add(backward);
        jpnlBackwardForward.add(forward);

        jpnlBackwardForward.setPreferredSize(new Dimension(100, 30));
        jpnlBackwardForward.setMaximumSize(new Dimension(100, 30));

        jpnlBackwardForward.setAlignmentX(Component.LEFT_ALIGNMENT);

        //add elements to left panel
        jpnlWest.add(drawTextPanel);
        jpnlWest.add(jpnlBackwardForward);

        //add left panel to main panel
        jpnlMain.add(jpnlWest, BorderLayout.WEST);
        jpnlMain.add(statusLabel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        //add main panel to the frame

        scrollPane = new JScrollPane(lightTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, //scrollpane to hold photo
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(200, 200));
        scrollPane.setBackground(Color.LIGHT_GRAY); //background to photo


        this.jpnlMain.add(scrollPane, BorderLayout.CENTER); //add component

        this.add(jpnlMain);

        /*if(modeController.getMagnetMode()){
            this.pack();
            this.setVisible(true);
        }*/


    }

    private void initUI() {

        setTitle("Ralf's Photo Application");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void menuSelected(MenuEvent me){
        if (me.getSource().equals(exitItem)) {
            System.exit(0);
        }
    }

    //public

    @Override
    public void menuDeselected(MenuEvent me){}

    @Override
    public void menuCanceled(MenuEvent me){}

    @Override
    public void keyTyped(KeyEvent ke){
    }

    @Override
    public void keyPressed(KeyEvent ke){}

    @Override
    public void keyReleased(KeyEvent ke){}

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(exitItem)){
            System.exit(0);
        }
        if(e.getSource().equals(importItem)){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG","jpg")); //chooses JPG files
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION)
            {
                File file = fileChooser.getSelectedFile();
                try {
                    BufferedImage inImg = ImageIO.read(file); //reads image
                    currentPhoto = inImg;
                    System.out.println(file);
                } catch (IOException error) {
                    System.exit(0);
            }

                PhotoComponent photoComp = new PhotoComponent(currentPhoto, modeController, lightTable);

                lightTable.addPhoto(new PhotoComponent(currentPhoto, modeController, lightTable));

                revalidate();


                hasPicture = true; //boolean helps with deletion

                this.revalidate();
                this.repaint();
            }
        }
        if(e.getSource().equals(deleteItem)){ //deletes item
            lightTable.delete();
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


    }
    public void updateTags(){
        boolean[] tags = lightTable.getTags();
        if(tags != null) {
            vacationToggle.setSelected(tags[0]);
            workToggle.setSelected(tags[1]);
            schoolToggle.setSelected(tags[2]);
            familyToggle.setSelected(tags[3]);
        }

    }
    }

