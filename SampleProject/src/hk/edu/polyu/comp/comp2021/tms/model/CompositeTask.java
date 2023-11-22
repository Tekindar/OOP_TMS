package hk.edu.polyu.comp.comp2021.tms.model;

import java.math.BigDecimal;
import java.util.LinkedList;

/**
 * A primitive task is inherited from the abstract class Task
 * @Author FU Tao, NING Weichen
 */
public class CompositeTask extends Task{

    LinkedList<Task> subtask;
    LinkedList<Task> AllSubtask;

    /**
     * Constructer of a composite Task,
     * initialize an instance.
     *
     * @param keywords the input details of a composite task
     */
    CompositeTask(String[] keywords){
        name = keywords[1];
        description = keywords[2];
        subtask = new LinkedList<>();
        AllSubtask = new LinkedList<>();
        duration = 0;
        completion = 0;
        setSub(false);
        for(String s:keywords[3].split(","))subtask.add(TMS.getTask(s));
        subtaskCalculate(subtask); // Initiate all direct and indirect subtasks for duration calculation
        initializeTask();
        System.out.println(duration);
    }

    /**
     * method to calculate the duration, complete time as well as indirect subtasks of a composite task,
     * this will be executed after user inputs is stored in the object.
     */
    void initializeTask(){
        for(Task t: subtask){
            this.completion = Math.max(this.completion, t.completion);
            t.setSub(true);
        }
        for(Task t:subtask) DurationCalculation(t,0,0);
    }

    /**
     * recursive method runs from the direct subtask,
     * increase the duration for each primitive task and then
     * go to its prerequisite task.
     * If the target task is compositive, then go to each of its subtasks,
     * If the method runs to a primitive task that is within the composite,
     * we update duration with temporary maximum.
     *
     * @param t the task that this recursive method focusing on
     * @param parentTime accumulated duration from parent
     * @param tempHigh temporary maximum duration
     */
    void DurationCalculation(Task t, double parentTime, double tempHigh){
        if(t.getClass().equals(CompositeTask.class)){
            for(Task sub: ((CompositeTask) t).subtask) {
                DurationCalculation(sub, parentTime, tempHigh);// goto it's sub
            }
        }
        else {
            BigDecimal a = new BigDecimal(parentTime);
            BigDecimal b = new BigDecimal(t.duration);
            parentTime=a.add(b).doubleValue();
            if(AllSubtask.contains(t))tempHigh = parentTime;
            for(Task pr:((PrimitiveTask)t).prerequisite) DurationCalculation(pr, parentTime, tempHigh);
            if(((PrimitiveTask)t).prerequisite.isEmpty()) this.duration = Math.max(this.duration,tempHigh);
        }
    }

    /**
     * reach and add every subtask to the allTask linked-list
     *
     * @param sub the subtask of a composite task
     */
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

    /**
     *this method test if the input of the details of a task is valid.
     *
     * @param keywords the input details of a composite task
     * @return the validity of the user inputs
     */
    public static boolean CCTValidation(String[] keywords){
        if(keywords.length != 4){
            System.out.println("Invalid Inputs");
            return false;
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
        if(prs.length<2){
            System.out.println("A Composite Task Needs To Have At Least Two Subtask");
            return false;
        }
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
