/**
 * Created by ralfpopescu on 8/30/16.
 */

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.*;
import java.io.*;

public class MainFrame extends JFrame implements MenuListener, ActionListener, KeyListener {

    JMenuBar menuBar;
    JMenu fileMenu, viewMenu;
    JMenuItem importItem, deleteItem, exitItem;
    JRadioButton drawingMode, textMode;
    JToggleButton vacationToggle, familyToggle, schoolToggle, workToggle;
    JButton forwardButton, backwardButton;
    JLabel statusLabel;


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            MainFrame ex = new MainFrame();
            ex.setVisible(true);
        });
    }

    public MainFrame() {

        initUI();
        //basic setup

        //Main Borderlayout organizes all other elements
        JPanel jpnlMain = new JPanel(new BorderLayout());

        //White JPanel to represent future content
        JPanel jpnlContent = new JPanel();
        jpnlContent.setBackground(Color.WHITE);
        jpnlMain.add(jpnlContent, BorderLayout.CENTER);

        //prevents window from being minimized to distortion
        this.setMinimumSize(new Dimension(200,250));

        //creates menu bar, abstracted away for functionality
        MenuBar topMenuBar = new MenuBar();
        menuBar = topMenuBar.getMenuBar();
        this.setJMenuBar(menuBar);

        //Status bar that changes on "Draw Mode" and "Text Mode" controls
        statusLabel = new JLabel("Status:");

        //Toolbox JPanel on the left of the window, basic setup
        JPanel jpnlWest = new JPanel();
        BoxLayout westBox = new BoxLayout(jpnlWest, BoxLayout.Y_AXIS); //box layout for top-down structure
        jpnlWest.setLayout(westBox);

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



        jpnlWest.add(vacationToggle);
        jpnlWest.add(familyToggle);
        jpnlWest.add(schoolToggle);
        jpnlWest.add(workToggle);

        //Drawing/Text mode buttons
        JRadioButton drawingMode = new JRadioButton("Draw");
        JRadioButton textMode = new JRadioButton("Text");

        //Action Listeners for changing status label
        drawingMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Drawing Mode");

            }
        });

        textMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Text Mode");

            }
        });
        //adds radio buttons to button group for mutual exclusivity
        ButtonGroup drawTextGroup = new ButtonGroup();
        drawTextGroup.add(drawingMode);
        drawTextGroup.add(textMode);

        //organizes draw and text mode buttons side by side
        JPanel drawTextPanel = new JPanel();
        drawTextPanel.add(drawingMode);
        drawTextPanel.add(textMode);
        drawTextPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        //forward and backward buttons
        JButton backward = new JButton("<-");
        JButton forward = new JButton("->");

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

        //add main panel to the frame
        this.add(jpnlMain);


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

    @Override
    public void menuDeselected(MenuEvent me){}

    @Override
    public void menuCanceled(MenuEvent me){}

    @Override
    public void keyTyped(KeyEvent ke){}

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
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION)
            {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println(selectedFile.getName());
            }
        }

        }
    }

