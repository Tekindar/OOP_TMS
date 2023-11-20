package hk.edu.polyu.comp.comp2021.tms.model;


import hk.edu.polyu.comp.comp2021.tms.controller.TestController;
import hk.edu.polyu.comp.comp2021.tms.view.GUIViewer;

import java.util.LinkedList;

public class CompositeTask extends Task{

    LinkedList<Task> subtask;
    LinkedList<Task> AllSubtask;
    CompositeTask(String[] keywords){
        name = keywords[1];
        description = keywords[2];
        subtask = new LinkedList<>();
        AllSubtask = new LinkedList<>();
        duration = 0;
        completion = 0;
        setSub(false);
        subtaskCalculate(subtask); // Initiate all direct and indirect subtasks for duration calculation
        initializeTask();
        System.out.println(duration);
    }

    void initializeTask(){
        for(Task t: subtask){
            this.completion = Math.max(this.completion, t.completion);
            t.setSub(true);
        }
        for(Task t:subtask) DurationCalculation(t,0,0);
    }

    void DurationCalculation(Task t, double parentTime, double tempHigh){
        if(t.getClass().equals(CompositeTask.class)){
            for(Task sub: ((CompositeTask) t).subtask) {
                DurationCalculation(sub, parentTime, tempHigh);// goto it's sub
            }
        }
        else {
            parentTime += t.duration;
            if(AllSubtask.contains(t))tempHigh = parentTime;
            for(Task pr:((PrimitiveTask)t).prerequisite) DurationCalculation(pr, parentTime, tempHigh);
            if(((PrimitiveTask)t).prerequisite.isEmpty()) this.duration = Math.max(this.duration,tempHigh);
        }
    }

    void subtaskCalculate(LinkedList<Task> sub){
        for(Task t:sub){
            if(t.getClass().equals(CompositeTask.class)){
                subtaskCalculate(((CompositeTask) t).subtask);
            }
            else{
                AllSubtask.add(t);
            }
        }
    }

    public static boolean CCTValidation(String[] keywords, boolean isGUI){
        if(keywords.length != 4){
            GUIViewer.Log("Invalid Inputs", isGUI);
            return false;
        }
        if(keywords[1].isEmpty()||keywords[2].isEmpty()||keywords[3].isEmpty()){
            GUIViewer.Log("Missing Input", isGUI);
            return false;
        }
        // validating name
        String key = keywords[1];
        if(TMS.taskExist(key)){
            GUIViewer.Log("Task Existed", isGUI);
            return false;
        }

        if((key.charAt(0)>='0'&&key.charAt(0)<='9'||key.length()>8)){
            GUIViewer.Log("Illegal Task Name", isGUI);
            return false;
        }

        for(int i=0;i<key.length();i++){
            if((key.charAt(i)<'a'||key.charAt(i)>'z')&&
                    (key.charAt(i)<'A'||key.charAt(i)>'Z')&&
                    (key.charAt(i)<'0'||key.charAt(i)>'9')) {
                GUIViewer.Log("Illegal Task Name", isGUI);
                return false;
            }
        }

        // validating description
        key = keywords[2];
        for(int i=0;i<key.length();i++){
            if((key.charAt(i)<'a'||key.charAt(i)>'z')&&
                    (key.charAt(i)<'A'||key.charAt(i)>'Z')&&
                    (key.charAt(i)<'0'||key.charAt(i)>'9')&&
                    key.charAt(i)!='-') {
                GUIViewer.Log("Illegal Description", isGUI);
                return false;
            }
        }

        String[] prs = keywords[3].split(",");
        if(prs.length<2){
            GUIViewer.Log("A Composite Task Needs To Have At Least Two Subtask", isGUI);
            return false;
        }
        for(String s:prs) {
            Task t = TMS.getTask(s);
            if(t!=null){
                if(t.getSub()){
                    GUIViewer.Log("An Appointed Task Has Been Used As Subtask Already", isGUI);
                    return false;
                }
            }
            else{
                GUIViewer.Log("Illegal Subtasks", isGUI);
                return false;
            }
        }
        return true;
    }


}
