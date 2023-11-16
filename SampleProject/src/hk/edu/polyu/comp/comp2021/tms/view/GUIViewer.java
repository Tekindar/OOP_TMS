package hk.edu.polyu.comp.comp2021.tms.view;
import hk.edu.polyu.comp.comp2021.tms.model.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIViewer {
    JFrame f,CSTFrame,SL_PR;
    TMS tms;
    Font UniFont(int size){
        return new Font("LEMON", Font.BOLD, size);
    }
    Font UniFont(){
        return new Font("LEMON", Font.BOLD, 30);
    }

    void mainFrame(){
        // Components
        f = new JFrame();
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
        CSTFrame = new JFrame();
        JLabel CSTTitle = new JLabel("CREATE SIMPLE TASK");
        JLabel CSTnameLabel = new JLabel("Task Name");
        JLabel CSTdescriptLabel = new JLabel("Task Description");
        JLabel CSTdurationLabel = new JLabel("Task Duration");
        JTextField CSTName = new JTextField();
        JTextField CSTDescript = new JTextField();
        JTextField CSTDuration = new JTextField();
        JButton SL_PR = new JButton("Select Prerequisite");
        JButton CSTCreate = new JButton();

        // Properties
        CSTFrame.setLayout(null);
        CSTFrame.setSize(600,400);
        CSTTitle.setSize(500,50);
        CSTTitle.setFont(UniFont(40));
        CSTTitle.setLocation(60, 30);

        CSTName.setFont(UniFont(20));
        CSTName.setLocation(60,140);
        CSTName.setSize(240,30);
        CSTnameLabel.setSize(120,30);
        CSTnameLabel.setLocation(60,110);
        CSTnameLabel.setFont(UniFont(10));

        CSTDescript.setFont(UniFont(20));
        CSTDescript.setLocation(60,220);
        CSTDescript.setSize(240,30);
        CSTdescriptLabel.setSize(120,30);
        CSTdescriptLabel.setLocation(60,190);
        CSTdescriptLabel.setFont(UniFont(10));

        CSTDuration.setFont(UniFont(20));
        CSTDuration.setLocation(60,300);
        CSTDuration.setSize(240,30);
        CSTdurationLabel.setSize(120,30);
        CSTdurationLabel.setLocation(60,270);
        CSTdurationLabel.setFont(UniFont(10));

        SL_PR.setSize(190,60);
        SL_PR.setLocation(350, 110);
        SL_PR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectPrerequisite(tms);
            }
        });

        // Layout
        CSTFrame.add(CSTTitle);
        CSTFrame.add(CSTName);
        CSTFrame.add(CSTnameLabel);
        CSTFrame.add(CSTDescript);
        CSTFrame.add(CSTdescriptLabel);
        CSTFrame.add(CSTDuration);
        CSTFrame.add(CSTdurationLabel);
        CSTFrame.add(SL_PR);

    }

    void SelectPrerequisite(TMS tms){
        String[] taskNames = tms.getTaskNames().clone();
        // Components
        SL_PR = new JFrame();
        JPanel panel = new JPanel();
        JScrollPane scrlPane = new JScrollPane(panel);

        // Properties
        SL_PR.setSize(200, 300);
        panel.setSize(200,taskNames.length*50);
        JCheckBox[] boxes = new JCheckBox[taskNames.length];
        for(int i=0;i<taskNames.length;i++){
            boxes[i] = new JCheckBox(taskNames[i]);
        }
        for(JCheckBox b:boxes){
            panel.add(b);
        }

        // Layout
        SL_PR.add(scrlPane);
        SL_PR.setVisible(true);
    }

    void CreateCompositeTask(){

    }

    void DeleteTask(){

    }

    public void display(boolean visibility){
        this.f.setVisible(visibility);
    }
    public GUIViewer(TMS tms){
        this.tms = tms;
        this.mainFrame();
    }
}
