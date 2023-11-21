package hk.edu.polyu.comp.comp2021.tms.model;

import java.util.LinkedList;

/**
 * A primitive task is inherited from the abstract class Task
 * @author FU Tao
 */

public class PrimitiveTask extends Task{

    LinkedList<Task> prerequisite;
    LinkedList<Task> IndirectPrerequisite;

    /**
     * Constructer of a primitive Task,
     * initialize an instance.
     *
     * @param keywords the input details of a primitive task
     */
    PrimitiveTask(String[] keywords){
        name = keywords[1];
        description = keywords[2];
        duration = Double.parseDouble(keywords[3]);
        prerequisite = new LinkedList<>();
        IndirectPrerequisite = new LinkedList<>();
        setSub(false);
        completion=0;
        initializeTask();
    }

    /**
     * method to calculate the complete time as well as prerequisites of a primitive task,
     * this will be executed after user inputs is stored in the object.
     */
    void initializeTask(){
        for(Task t:prerequisite)calculatePrerequisite(t);
        for(Task t:prerequisite) this.completion = Math.max(this.completion, t.completion);
        this.completion+=this.duration;
        System.out.println(completion);
    }

    /**
     * recursive method runs from the direct prerequisites to
     * find the indirect ones of this task.
     *
     * @param t the task that this recursive method focusing on
     */
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

    /**
     *this method test if the input of the details of a task is valid.
     *
     * @param keywords the input details of a primitive task
     * @return the validity of the user inputs
     */
    public static boolean CPTValidation(String[] keywords){
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
