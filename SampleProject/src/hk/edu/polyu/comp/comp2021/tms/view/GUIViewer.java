package hk.edu.polyu.comp.comp2021.tms.view;
import hk.edu.polyu.comp.comp2021.tms.controller.TestController;
import hk.edu.polyu.comp.comp2021.tms.model.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIViewer {
    JFrame f,CPTFrame,CCTFrame,SelectFrame;
    TMS tms;
    String TemporarySelection = ",";
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
        JButton CPT = new JButton("CreatePrimitiveTask");
        JButton CCT = new JButton("CreateCompositeTask");

        //properties
        quit.setFont(UniFont());
        quit.setSize(100,100);
        quit.setLocation(350,350);
        quit.addActionListener(e -> {
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            System.exit(0);
        });
        
        CPT.setFont(UniFont(20));
        CPT.setSize(600,100);
        CPT.setLocation(100,50);
        CPT.addActionListener(e -> {
            display(false);
            resetCCT();
            if(CPTFrame!=null) CPTFrame.setVisible(true);
            else CreatePrimitiveTask();
        });

        CCT.setFont(UniFont(20));
        CCT.setSize(600,100);
        CCT.setLocation(100,200);
        CCT.addActionListener(e -> {
            display(false);
            resetCPT();
            if(CCTFrame!=null) CCTFrame.setVisible(true);
            else CreateCompositeTask();
        });

        //layout
        f.setSize(800,800);
        f.setLayout(null);
        f.add(quit);
        f.add(CPT);
        f.add(CCT);
    }

    void CreatePrimitiveTask(){
        // Components
        CPTFrame = new JFrame();
        SelectTasks(tms,'p');
        JLabel CPTTitle = new JLabel("CREATE PRIMITIVE TASK");
        JLabel CPT_nameLabel = new JLabel("Task Name");
        JLabel CPT_descriptionLabel = new JLabel("Task Description");
        JLabel CPT_durationLabel = new JLabel("Task Duration");
        JTextField CPT_Name = new JTextField();
        JTextField CPT_Description = new JTextField();
        JTextField CPT_Duration = new JTextField();
        JButton Select = new JButton("Select Prerequisite");
        JButton CPTCreate = new JButton("Create");
        JButton BackMain = new JButton("Return Main");

        // Properties
        CPTFrame.setLayout(null);
        CPTFrame.setSize(600,400);
        CPTTitle.setSize(500,50);
        CPTTitle.setFont(UniFont(40));
        CPTTitle.setLocation(60, 30);

        CPT_Name.setFont(UniFont(20));
        CPT_Name.setLocation(60,140);
        CPT_Name.setSize(240,30);
        CPT_nameLabel.setSize(120,30);
        CPT_nameLabel.setLocation(60,110);
        CPT_nameLabel.setFont(UniFont(10));

        CPT_Description.setFont(UniFont(20));
        CPT_Description.setLocation(60,220);
        CPT_Description.setSize(240,30);
        CPT_descriptionLabel.setSize(120,30);
        CPT_descriptionLabel.setLocation(60,190);
        CPT_descriptionLabel.setFont(UniFont(10));

        CPT_Duration.setFont(UniFont(20));
        CPT_Duration.setLocation(60,300);
        CPT_Duration.setSize(240,30);
        CPT_durationLabel.setSize(120,30);
        CPT_durationLabel.setLocation(60,270);
        CPT_durationLabel.setFont(UniFont(10));

        Select.setSize(190,60);
        Select.setLocation(350, 110);
        Select.addActionListener(e -> SelectFrame.setVisible(true));

        BackMain.setSize(190,60);
        BackMain.setLocation(350, 270);
        BackMain.addActionListener(e -> {
            CPTFrame.setVisible(false);
            resetSelection('p');
            display(true);
        });

        CPTCreate.setSize(190,60);
        CPTCreate.setLocation(350, 190);
        CPTCreate.addActionListener(e -> {
            String a = "CreatePrimitiveTask "+CPT_Name.getText()+" "+CPT_Description.getText()+" "+
                    CPT_Duration.getText()+" "+TemporarySelection;
            if(a.split(" ").length!=5){
                Log("Input Error", true);
            }else{
                CPT(a.split(" "));
            }
            CPT_Name.setText("");
            CPT_Description.setText("");
            CPT_Duration.setText("");
            resetSelection('p');
        });


        // Layout
        CPTFrame.add(CPTTitle);
        CPTFrame.add(CPT_Name);
        CPTFrame.add(CPT_nameLabel);
        CPTFrame.add(CPT_Description);
        CPTFrame.add(CPT_descriptionLabel);
        CPTFrame.add(CPT_Duration);
        CPTFrame.add(CPT_durationLabel);
        CPTFrame.add(Select);
        CPTFrame.add(BackMain);
        CPTFrame.add(CPTCreate);
        CPTFrame.setVisible(true);
    }

    void CreateCompositeTask(){
        // Components
        CCTFrame = new JFrame();
        SelectTasks(tms,'c');
        JLabel CCTTitle = new JLabel("CREATE COMPOSITE TASK");
        JLabel CCT_nameLabel = new JLabel("Task Name");
        JLabel CCT_descriptionLabel = new JLabel("Task Description");
        JTextField CCT_Name = new JTextField();
        JTextField CCT_Description = new JTextField();
        JButton Select = new JButton("Select Subtask");
        JButton CCTCreate = new JButton("Create");
        JButton BackMain = new JButton("Return Main");

        // Properties
        CCTFrame.setLayout(null);
        CCTFrame.setSize(600,400);
        CCTTitle.setSize(500,50);
        CCTTitle.setFont(UniFont(40));
        CCTTitle.setLocation(60, 30);

        CCT_Name.setFont(UniFont(20));
        CCT_Name.setLocation(60,140);
        CCT_Name.setSize(240,30);
        CCT_nameLabel.setSize(120,30);
        CCT_nameLabel.setLocation(60,110);
        CCT_nameLabel.setFont(UniFont(10));

        CCT_Description.setFont(UniFont(20));
        CCT_Description.setLocation(60,220);
        CCT_Description.setSize(240,30);
        CCT_descriptionLabel.setSize(120,30);
        CCT_descriptionLabel.setLocation(60,190);
        CCT_descriptionLabel.setFont(UniFont(10));

        Select.setSize(190,60);
        Select.setLocation(350, 110);
        Select.addActionListener(e -> SelectFrame.setVisible(true));

        BackMain.setSize(190,60);
        BackMain.setLocation(350, 270);
        BackMain.addActionListener(e -> {
            CCTFrame.setVisible(false);
            resetSelection('c');
            display(true);
        });

        CCTCreate.setSize(190,60);
        CCTCreate.setLocation(350, 190);
        CCTCreate.addActionListener(e -> {
            String a = "CreateCompositeTask "+CCT_Name.getText()+" "+CCT_Description.getText()+" "+TemporarySelection;
            if(a.split(" ").length!=4){
                Log("Input Error", true);
            }else{
                CCT(a.split(" "));
            }
            CCT_Name.setText("");
            CCT_Description.setText("");
            resetSelection('c');
        });


        // Layout
        CCTFrame.add(CCTTitle);
        CCTFrame.add(CCT_Name);
        CCTFrame.add(CCT_nameLabel);
        CCTFrame.add(CCT_Description);
        CCTFrame.add(CCT_descriptionLabel);
        CCTFrame.add(Select);
        CCTFrame.add(BackMain);
        CCTFrame.add(CCTCreate);
        CCTFrame.setVisible(true);
    }

    void SelectTasks(TMS tms, char operation){
        String[] taskNames;
        if(operation=='c') taskNames = getValidSubtask(tms.getTaskNames());
        else taskNames = tms.getTaskNames();
        // Components
        SelectFrame = new JFrame();
        JPanel panel = new JPanel();
        JScrollPane SRLPane = new JScrollPane(panel);
        JButton ConfirmSelection = new JButton("Confirm");

        // Properties
        SelectFrame.setSize(200, 300);
        panel.setLayout(null);
        panel.setSize(200,taskNames.length*50+60);
        ConfirmSelection.setSize(200,60);
        ConfirmSelection.setFont(UniFont(20));
        ConfirmSelection.setLocation(0,taskNames.length*50);
        // checkboxes for all existed tasks
        JCheckBox[] boxes = new JCheckBox[taskNames.length];
        for(int i=0;i<taskNames.length;i++){
            boxes[i] = new JCheckBox(taskNames[i]);
            boxes[i].setFont(UniFont(20));
            boxes[i].setSize(200,50);
            boxes[i].setLocation(0,i*50);
        }
        ConfirmSelection.addActionListener(e -> {
            getCheckboxes(boxes, taskNames);
            SelectFrame.setVisible(false);
        });

        // Layout

        for(JCheckBox b:boxes){
            panel.add(b);
        }
        panel.add(ConfirmSelection);
        SelectFrame.add(SRLPane);
    }
    
    void resetSelection(char operation){
        TemporarySelection=",";
        SelectFrame.removeAll();
        SelectTasks(tms,operation);
        SelectFrame.setVisible(false);
    }
    
    void resetCPT(){
        if(CPTFrame!=null)CPTFrame.removeAll();
        CPTFrame=null;
    }

    void resetCCT(){
        if(CCTFrame!=null)CCTFrame.removeAll();
        CCTFrame=null;
    }
    
    void CPT(String[] keywords){
        tms.CreatePrimitiveTask(keywords,true);
    }
    void CCT(String[] keywords){
        tms.CreateCompositeTask(keywords,true);
    }
    String[] getValidSubtask(String[] allTask){
        StringBuilder validTask = new StringBuilder(" ");
        int item=0;
        for(String s:allTask){
            Task t = TMS.getTask(s);
            if(t!=null){
                if(!t.getSub()) {
                    if(item==0) validTask = new StringBuilder(s);
                    else {
                        validTask.append(" ").append(s);
                    }
                    item++;
                }
            }
        }
        return validTask.toString().split(" ");
    }
    void getCheckboxes(JCheckBox[] boxes, String[] taskNames){
        if(taskNames.length==0)TemporarySelection=",";
        else{
            boolean flag = true;
            StringBuilder Temporary= new StringBuilder();
            for(int i=0;i<taskNames.length;i++) if(boxes[i].isSelected()){
                if(flag)flag = false;
                else Temporary.append(",");
                Temporary.append(taskNames[i]);
            }
            TemporarySelection = Temporary.toString();
        }
    }

    

    //void DeleteTask(){}

    public void display(boolean visibility){
        f.setVisible(visibility);
    }
    public GUIViewer(TMS tms){
        this.tms = tms;
        this.mainFrame();
        display(false);
    }

    public static void Log(String message, boolean GUI){
        if(GUI) JOptionPane.showMessageDialog(null,message,"Task Management System", JOptionPane.INFORMATION_MESSAGE);
        else System.out.println(message);
    }
}
