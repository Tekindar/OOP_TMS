package hk.edu.polyu.comp.comp2021.tms.model;


import java.util.LinkedList;

public class CompositeTask extends Task{

    LinkedList<Task> subtask;
    CompositeTask(String[] keywords){
        name = keywords[1];
        description = keywords[2];
        subtask = new LinkedList<>();
        duration = 0;
        completion = 0;
        setSub(false);
        for(String s:keywords[3].split(",")){
            boolean repeated = true;
            for(Task t:subtask){
                if(t.name.equals(s)){
                    repeated=false;
                    break;
                }
            }
            if(repeated)subtask.add(TMS.getTask(s));
            else System.out.println("Repeated Subtask Detected, Automatically Removed Duplication");
        }
        initializeTask();

    }

    void initializeTask(){
        for(Task t: subtask) this.completion = Math.max(this.completion, t.completion);
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
            if(subtask.contains(t))tempHigh = parentTime;
            for(Task pr:t.prerequisite) DurationCalculation(pr, parentTime, tempHigh);
            if(t.prerequisite.isEmpty()) this.duration = Math.min(this.duration,tempHigh);
        }
    }



    public static boolean CCTValidation(String[] keywords){
        if(keywords.length != 4){
            System.out.print("Invalid Inputs");
        }
        if(keywords[1].isEmpty()||keywords[2].isEmpty()||keywords[3].isEmpty()){
            System.out.println("Missing Input");
            return false;
        }
        // validating name
        String key = keywords[1];
        if(TMS.taskExist(key)){
            System.out.println("Task Existed");
            return false;
        }

        if((key.charAt(0)>='0'&&key.charAt(0)<='9'||key.length()>8)){
            System.out.println("Illegal Task Name");
            return false;
        }

        for(int i=0;i<key.length();i++){
            if((key.charAt(i)<'a'||key.charAt(i)>'z')&&
                    (key.charAt(i)<'A'||key.charAt(i)>'Z')&&
                    (key.charAt(i)<'0'||key.charAt(i)>'9')) {
                System.out.println("Illegal Task Name");
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
                System.out.println("Illegal Description");
                return false;
            }
        }

        String[] prs = keywords[3].split(",");
        if(prs.length < 2) return false;
        for(String s:prs) {
            Task t = TMS.getTask(s);
            if(t!=null){
                if(t.getSub()){
                    System.out.println("An Appointed Task Has Been Used As Subtask Already");
                    return false;
                }
            }
            else{
                System.out.println("Illegal Subtasks");
                return false;
            }
        }
        return true;
    }

}
