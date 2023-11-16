package hk.edu.polyu.comp.comp2021.tms.view;
import hk.edu.polyu.comp.comp2021.tms.model.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIViewer {
    JFrame f,CSTFrame,SL_PR;
    TMS tms;
    String TemporaryPrerequisite = ",";
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
                display(false);
                if(CSTFrame!=null) CSTFrame.setVisible(true);
                else CreateSimpleTask();
            }
        });

        //layout
        f.setSize(800,800);
        f.setLayout(null);
        f.add(quit);
        f.add(CST);
    }

    void CreateSimpleTask(){
        // Components
        CSTFrame = new JFrame();
        SelectPrerequisite(tms);
        JLabel CSTTitle = new JLabel("CREATE SIMPLE TASK");
        JLabel CSTnameLabel = new JLabel("Task Name");
        JLabel CSTdescriptLabel = new JLabel("Task Description");
        JLabel CSTdurationLabel = new JLabel("Task Duration");
        JTextField CSTName = new JTextField();
        JTextField CSTDescript = new JTextField();
        JTextField CSTDuration = new JTextField();
        JButton SLPR = new JButton("Select Prerequisite");
        JButton CSTCreate = new JButton("Create");
        JButton BackMain = new JButton("Return Main");

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

        SLPR.setSize(190,60);
        SLPR.setLocation(350, 110);
        SLPR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SL_PR.setVisible(true);
            }
        });

        BackMain.setSize(190,60);
        BackMain.setLocation(350, 270);
        BackMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CSTFrame.setVisible(false);
                resetPrerequisites();
                display(true);
            }
        });

        CSTCreate.setSize(190,60);
        CSTCreate.setLocation(350, 190);
        CSTCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String a = "CreateSimpleTask "+CSTName.getText()+" "+CSTDescript.getText()+" "+
                        CSTDuration.getText()+" "+TemporaryPrerequisite;
                if(a.split(" ").length!=5){
                    System.out.println("Input Error");
                }else{
                    CST(a.split(" "));
                    CSTName.setText("");
                    CSTDescript.setText("");
                    CSTDuration.setText("");
                }
                resetPrerequisites();
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
        CSTFrame.add(SLPR);
        CSTFrame.add(BackMain);
        CSTFrame.add(CSTCreate);
        CSTFrame.setVisible(true);
    }

    void SelectPrerequisite(TMS tms){
        String[] taskNames = tms.getTaskNames().clone();
        // Components
        SL_PR = new JFrame();
        JPanel panel = new JPanel();
        JScrollPane scrlPane = new JScrollPane(panel);
        JButton ConfirmPrerequisites = new JButton("Confirm");

        // Properties
        SL_PR.setSize(200, 300);
        panel.setSize(200,taskNames.length*50+60);
        ConfirmPrerequisites.setSize(200,60);
        ConfirmPrerequisites.setFont(UniFont(20));
        ConfirmPrerequisites.setLocation(0,taskNames.length*50);
        // checkboxes for all existed tasks
        JCheckBox[] boxes = new JCheckBox[taskNames.length];
        for(int i=0;i<taskNames.length;i++){
            boxes[i] = new JCheckBox(taskNames[i]);
        }
        ConfirmPrerequisites.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(taskNames.length==0)TemporaryPrerequisite=",";
                else{
                    boolean flag = true;
                    String temporaryPrerequisite="";
                    for(int i=0;i<taskNames.length;i++) if(boxes[i].isSelected()){
                        if(flag)flag = false;
                        else temporaryPrerequisite+=",";
                        temporaryPrerequisite+=taskNames[i];
                    }
                    TemporaryPrerequisite = temporaryPrerequisite;
                }

                SL_PR.setVisible(false);
            }
        });

        // Layout
        for(JCheckBox b:boxes){
            panel.add(b);
        }
        scrlPane.add(ConfirmPrerequisites);
        SL_PR.add(scrlPane);
    }

    void resetPrerequisites(){
        SL_PR.removeAll();
        SelectPrerequisite(tms);
        SL_PR.setVisible(false);
        TemporaryPrerequisite=",";
    }

    void CST(String[] keywords){
        tms.CreateSimpleTask(keywords);
    }

    String getCheckboxes(){
        return null;
    }

    void CreateCompositeTask(){

    }

    void DeleteTask(){

    }

    public void display(boolean visibility){
        f.setVisible(visibility);
    }
    public GUIViewer(TMS tms){
        this.tms = tms;
        this.mainFrame();
        display(false);
    }
}
