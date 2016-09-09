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
    JRadioButtonMenuItem photoView, gridView, splitView;
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

        JPanel jpnlMain = new JPanel(new BorderLayout());
        JPanel jpnlContent = new JPanel();
        jpnlContent.setBackground(Color.WHITE);
        jpnlMain.add(jpnlContent, BorderLayout.CENTER);

        menuBar = new JMenuBar();
    //menu items
        fileMenu = new JMenu("File");
        viewMenu = new JMenu("View");

        importItem = new JMenuItem("Import");
        importItem.addActionListener(this);

        deleteItem = new JMenuItem("Delete");

        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this);

        fileMenu.add(importItem);
        fileMenu.add(deleteItem);
        fileMenu.add(exitItem);

        photoView = new JRadioButtonMenuItem("Photo View");
        gridView = new JRadioButtonMenuItem("Grid View");
        splitView = new JRadioButtonMenuItem("Split View");


        viewMenu.add(photoView);
        viewMenu.add(gridView);
        viewMenu.add(splitView);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
    //sub items


        statusLabel = new JLabel("Status:");

        this.setJMenuBar(menuBar);
        jpnlMain.add(statusLabel, BorderLayout.SOUTH);

        JPanel jpnlWest = new JPanel();
        BoxLayout westBox = new BoxLayout(jpnlWest, BoxLayout.Y_AXIS);
        jpnlWest.setLayout(westBox);

        vacationToggle = new JToggleButton("Vacation");
        familyToggle = new JToggleButton("Family");
        schoolToggle = new JToggleButton("School");
        workToggle = new JToggleButton("Work");


        vacationToggle.setMinimumSize(new Dimension(50, 25));
        vacationToggle.setPreferredSize(new Dimension(100, 25));
        vacationToggle.setMaximumSize(new Dimension(Short.MAX_VALUE,
                Short.MAX_VALUE));

        familyToggle.setMinimumSize(new Dimension(50, 25));
        familyToggle.setPreferredSize(new Dimension(100, 25));
        familyToggle.setMaximumSize(new Dimension(Short.MAX_VALUE,
                Short.MAX_VALUE));

        schoolToggle.setMinimumSize(new Dimension(50, 25));
        schoolToggle.setPreferredSize(new Dimension(100, 25));
        schoolToggle.setMaximumSize(new Dimension(Short.MAX_VALUE,
                Short.MAX_VALUE));

        workToggle.setMinimumSize(new Dimension(50, 25));
        workToggle.setPreferredSize(new Dimension(100, 25));
        workToggle.setMaximumSize(new Dimension(Short.MAX_VALUE,
                Short.MAX_VALUE));



        jpnlWest.add(vacationToggle);
        jpnlWest.add(familyToggle);
        jpnlWest.add(schoolToggle);
        jpnlWest.add(workToggle);
        jpnlMain.add(jpnlWest, BorderLayout.WEST);


        this.add(jpnlMain);
        

    }

    private void initUI() {

        setTitle("Simple example");
        setSize(300, 200);
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
