package hk.edu.polyu.comp.comp2021.tms.view;
import hk.edu.polyu.comp.comp2021.tms.controller.TMSController;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIViewer {
    JFrame f;
    Font UniFont;

    void mainFrame(){
        JButton quit = new JButton("Quit");
        quit.setFont(UniFont);

        //properties
        quit.setSize(100,100);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                System.exit(0);
            }
        });

        //layout
        f.add(quit);
    }

    void CreateSimpleTask(){
        JFrame CSTFrame = new JFrame();
        JTextField CSTName = new JTextField();
        JTextField CSTDescript = new JTextField();
        JTextField CSTDuration = new JTextField();
        JButton CSTCreate = new JButton();
    }

    void CreateCompositeTask(){

    }

    void DeleteTask(){

    }

    public GUIViewer(){
        UniFont = new Font("Lemon", Font.BOLD, 60);
        f = new JFrame();
        f.setSize(800,800);
        this.mainFrame();
        f.setVisible(true);
    }
}
