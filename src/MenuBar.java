/**
 * Created by ralfpopescu on 9/5/16.
 */

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.io.*;

public class MenuBar implements ActionListener{
    JMenuBar menuBar;
    JMenu fileMenu, viewMenu;
    JMenuItem importItem, deleteItem, exitItem;
    JRadioButtonMenuItem photoView, gridView, splitView;

    public MenuBar() {

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


        ButtonGroup viewButtonGroup = new ButtonGroup();

        viewButtonGroup.add(photoView);
        viewButtonGroup.add(gridView);
        viewButtonGroup.add(splitView);

        viewMenu.add(photoView);
        viewMenu.add(gridView);
        viewMenu.add(splitView);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(exitItem)) {
            System.exit(0);
        }
        if (e.getSource().equals(importItem)) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println(selectedFile.getName());
            }
        }
    }

    public JMenuBar getMenuBar(){
        return menuBar;
    }

}
