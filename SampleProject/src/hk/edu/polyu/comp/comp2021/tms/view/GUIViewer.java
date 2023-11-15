package hk.edu.polyu.comp.comp2021.tms.view;
import hk.edu.polyu.comp.comp2021.tms.controller.TMSController;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIViewer {
    JFrame f;
    Font UniFont(int size){
        return new Font("LEMON", Font.BOLD, size);
    }
    Font UniFont(){
        return new Font("LEMON", Font.BOLD, 30);
    }

    void mainFrame(){
        // Components
        JButton quit = new JButton("Quit");
        JButton CST = new JButton("CreateSimpleTask");

        //properties
        quit.setFont(UniFont());
        quit.setSize(100,100);
        quit.setLocation(350,350);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                System.exit(0);
            }
        });
        CST.setFont(UniFont(20));
        CST.setSize(600,100);
        CST.setLocation(100,50);
        CST.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.setVisible(false);
                CreateSimpleTask();
            }
        });

        //layout
        f.setSize(800,800);
        f.setLayout(null);
        f.add(quit);
        f.add(CST);

        f.setVisible(true);
    }

    void CreateSimpleTask(){
        // Components
        JFrame CSTFrame = new JFrame();
        JLabel CSTTitle = new JLabel("CREATE SIMPLE TASK");
        JTextField CSTName = new JTextField();
        JTextField CSTDescript = new JTextField();
        JTextField CSTDuration = new JTextField();
        JButton CSTCreate = new JButton();

        // Properties
        CSTFrame.setSize(600,400);
        CSTTitle.setSize(500,50);
        CSTTitle.setFont(UniFont());
        CSTTitle.setLocation(50, 50);

        // Layout
        CSTFrame.setVisible(true);
    }

    void CreateCompositeTask(){

    }

    void DeleteTask(){

    }

    public GUIViewer(){
        f = new JFrame();
        this.mainFrame();
    }
}
