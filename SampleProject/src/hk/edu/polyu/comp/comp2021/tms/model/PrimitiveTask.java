package hk.edu.polyu.comp.comp2021.tms.model;

import java.util.LinkedList;

public class PrimitiveTask extends Task{

    LinkedList<Task> prerequisite;
    LinkedList<Task> IndirectPrerequisite;

    PrimitiveTask(String[] keywords){
        name = keywords[1];
        description = keywords[2];
        duration = Double.parseDouble(keywords[3]);
        prerequisite = new LinkedList<>();
        IndirectPrerequisite = new LinkedList<>();
        setSub(false);

        for(String s:keywords[4].split(",")) {
            boolean repeated = true;
            for(Task t:prerequisite){
                if(t.name.equals(s)){
                    repeated=false;
                    break;
                }
            }
            if(repeated)prerequisite.add(TMS.getTask(s));
            else System.out.println("Repeated Prerequisite Detected, Automatically Removed Duplication");
        }
        completion=0;
        initializeTask();
    }

    void initializeTask(){
        for(Task t:prerequisite)calculatePrerequisite(t);
        for(Task t:prerequisite) this.completion = Math.max(this.completion, t.completion);
        this.completion+=this.duration;
        System.out.println(completion);
    }

    void calculatePrerequisite(Task t){
        if(t.getClass().equals(CompositeTask.class)){
            for(Task sub: ((CompositeTask) t).subtask) {
                // delete if task appears in domain of subtasks
                this.prerequisite.removeIf(pr -> pr.equals(sub));
                if(!this.IndirectPrerequisite.contains(sub)) this.IndirectPrerequisite.add(sub);
                // recursively check subtasks
                calculatePrerequisite(sub);
            }
        }
        else{
            if(((PrimitiveTask)t).prerequisite.isEmpty())return;
            for(Task task:((PrimitiveTask)t).prerequisite){
                // delete if task appears in prerequisite of subtasks
                this.prerequisite.removeIf(pr -> pr.equals(task));
                if(!this.IndirectPrerequisite.contains(task)) this.IndirectPrerequisite.add(task);
                //recursively check direct prerequisites
                calculatePrerequisite(task);
            }
        }

    }

    public static boolean CSTValidation(String[] keywords){
        if(keywords.length!=5){
            System.out.println("Invalid Input Length");
            return false;
        }

        if(keywords[1].isEmpty()||keywords[2].isEmpty()||keywords[3].isEmpty()||keywords[4].isEmpty()){
            System.out.println("Missing Input");
            return false;
        }
        // validating name
        String key = keywords[1];
        if(TMS.taskExist(key)){
            System.out.println("Task Existed");
            return false;
        }

        if(key.charAt(0)>='0'&&key.charAt(0)<='9'||key.length()>8){
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

        // validating duration
        key = keywords[3]; // reject negative number, reject non-number
        try{
            double temp = Double.parseDouble(key);
            if(temp<=0) {
                System.out.println("Illegal Range of Duration");
                return false;
            }
        }catch(Exception e){
            System.out.println("Illegal Duration Input");
            return false;
        }

        String[] prs = keywords[4].split(",");
        for(String s:prs) if(!TMS.taskExist(s)) {
            System.out.println("Illegal Subtasks");
            return false;
        }
        return true;
    }


}
