/**
 * Created by ralfpopescu on 8/30/16.
 */

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.*;

public class MainFrame extends JFrame implements MenuListener, ActionListener, KeyListener {

    JMenuBar menuBar;
    JMenu fileMenu, viewMenu;
    JMenuItem importItem, deleteItem, exitItem;
    JRadioButtonMenuItem photoView, gridView, splitView;


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            MainFrame ex = new MainFrame();
            ex.setVisible(true);
        });
    }

    public MainFrame() {

        initUI();

        menuBar = new JMenuBar();
    //menu items
        fileMenu = new JMenu("File");
        viewMenu = new JMenu("View");

        importItem = new JMenuItem("Import");
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

        this.setJMenuBar(menuBar);

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
    }
}
