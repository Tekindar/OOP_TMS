package hk.edu.polyu.comp.comp2021.tms.model;

import java.math.BigDecimal;
import java.util.LinkedList;

/**
 * A primitive task is inherited from the abstract class Task
 * @author FU Tao
 */

public class PrimitiveTask extends Task{

    private final LinkedList<Task> prerequisite;
    private final LinkedList<Task> IndirectPrerequisite;

    /**
     * Constructor of a primitive Task,
     * initialize an instance.
     *
     * @param keywords the input details of a primitive task
     */
    PrimitiveTask(String[] keywords){
        name = keywords[1];
        description = keywords[2];
        duration = new BigDecimal(keywords[3]).doubleValue();
        prerequisite = new LinkedList<>();
        IndirectPrerequisite = new LinkedList<>();
        setSub(false);
        completion=0;
        initializeTask(keywords[4]);
    }

    /**
     * method to calculate the complete time as well as prerequisites of a primitive task,
     * this will be executed after user inputs is stored in the object.\
     *
     * @param tasks input string containing task names
     */
    void initializeTask(String tasks){
        for(String s:tasks.split(","))prerequisite.add(TMS.getTask(s));
        for(Task t:prerequisite)calculatePrerequisite(t);
        for(Task t:prerequisite) this.completion = Math.max(this.completion, t.completion);
        BigDecimal a = new BigDecimal(Double.toString(this.completion));
        BigDecimal b = new BigDecimal(Double.toString(this.duration));
        this.completion=a.add(b).doubleValue();
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
            for(Task sub: ((CompositeTask) t).getDirectSubtask()) {
                // delete if task appears in domain of subtasks
                this.prerequisite.removeIf(pr -> pr.equals(sub));
                if(!this.IndirectPrerequisite.contains(sub)) this.IndirectPrerequisite.add(sub);
                // recursively check subtasks
                calculatePrerequisite(sub);
            }
        }
        else{
            if(((PrimitiveTask)t).getDirectPrerequisite().isEmpty())return;
            for(Task task:((PrimitiveTask)t).getDirectPrerequisite()){
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

    /**
     * the method will return direct prerequisites.
     *
     * @return all direct prerequisites of the task
     */
    public LinkedList<Task> getDirectPrerequisite(){
        return prerequisite;
    }

    /**
     * the method will return indirect prerequisites.
     *
     * @return all indirect prerequisites of the task
     */
    public LinkedList<Task> getIndirectPrerequisite(){
        return IndirectPrerequisite;
    }

}
