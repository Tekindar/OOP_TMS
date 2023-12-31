package hk.edu.polyu.comp.comp2021.tms.model;

import java.util.*;

/**
 * ChangeTask is a class for operating properties of existed task
 * @author NING Weichen
 */
public class ChangeTask {
    /**
     * Related task list, used to store those task which changes in the current task may affect their properties
     */
    private final LinkedList<Task> RelatedTask;
    /**
     * For Recording the Primitive and Composite Separate Point
     */
    private final int CutPoint;

    /**
     * Constructor of Change Task to initialize related task list and the cut point between composite tasks and primitive tasks
     *
     * @param keywords the details of changing tasks
     */
    ChangeTask(String[] keywords){
        RelatedTask = new LinkedList<>();
        CutPoint = FindRelatedTask(TMS.getTask(keywords[1]), TMS.getAllTasks());
    }

    /**
     * method providing related tasks as a list
     *
     * @param task the task to be changed
     * @param l the list of all tasks
     * @return return the cut point between composite tasks and primitive tasks
     */
    int FindRelatedTask(Task task, LinkedList<Task> l){
        int i = 0;
        for(Task t: l){
            if(t == task) continue;
            if(t.getClass().equals(CompositeTask.class)) {
                if (((CompositeTask) t).getAllCompositeSubtask().contains(task)) RelatedTask.add(t);
                if (((CompositeTask) t).getAllSubtask().contains(task)) RelatedTask.add(t);
            }
            if(t.getClass().equals(PrimitiveTask.class)) {
                if (((PrimitiveTask) t).getDirectPrerequisite().contains(task)) {
                    RelatedTask.addFirst(t);
                    i++;
                }
                if (((PrimitiveTask) t).getIndirectPrerequisite().contains(task)) {
                    RelatedTask.addFirst(t);
                    i++;
                }
            }
        }
        return i;
    }

    /**
     * The method is to calculate task duration and completion
     *
     * @param task the task needed to be recalculated
     */
    void TimeCalculation(Task task){
        task.completion = 0;
        if(task.getClass().equals(PrimitiveTask.class)) {
            for (Task t : ((PrimitiveTask)task).getDirectPrerequisite()) task.completion = Math.max(task.completion, t.completion);
            task.completion += task.duration;
        }
        if(task.getClass().equals(CompositeTask.class)){
            for(Task t: ((CompositeTask)task).getDirectSubtask()){
                task.completion = Math.max(task.completion, t.completion);
                t.setSub(true);
            }
            for(Task t: ((CompositeTask)task).getDirectSubtask()){
                ((CompositeTask)task).DurationCalculation(t, 0, 0);
            }
        }
    }


    /**
     * the method is to justify whether a loop exists
     *
     * @param task the task in progress
     * @param t1 the primitive task for comparing whether loop exists
     * @param b boolean field to record loop
     * @return return true for loop existence and false for none
     */
    boolean LoopJustify(Task task, Task t1, boolean b){
        if(task == t1) {
            b = true;
            return b;
        }
        if(task.getClass().equals(CompositeTask.class)){
            for(Task sub: ((CompositeTask) task).getDirectSubtask()) {
                b = LoopJustify(sub, t1, b);
                if(b == true) return b;
            }
        }
        if(task.getClass().equals(PrimitiveTask.class)){
            if(((PrimitiveTask) task).getDirectPrerequisite() == null) return b;
            for(Task t: ((PrimitiveTask) task).getDirectPrerequisite()) {
                b = LoopJustify(t, t1, b);
                if(b == true) return b;
            }
        }
        return b;
    }

    /**
     * The method for prerequisite calculation
     *
     * @param task the task to be calculated
     */
    void PrerequisiteCalculation(Task task){
        for(Task t : ((PrimitiveTask)task).getDirectPrerequisite()) {
            ((PrimitiveTask) task).calculatePrerequisite(t);
        }
    }


    /**
     * the main method containing all functions of adjusting whether user operation can be approved and manage change operation
     *
     * @param keywords the input information of the task to be changed
     * @return returning a boolean value to justify whether change operation is allowed
     */
    public boolean Change(String[] keywords){
        if(keywords.length != 4){
            System.out.println("Invalid Input Length");
            return false;
        }

        if(keywords[1].isEmpty()||keywords[2].isEmpty()||keywords[3].isEmpty()){
            System.out.println("Missing Input");
            return false;
        }

        if(!TMS.taskExist(keywords[1])){
            System.out.println("Task Not Found");
            return false;
        }

        Task task = TMS.getTask(keywords[1]);
        String key = keywords[3];
        switch (keywords[2]) {
            default:
                System.out.println("No Such Task Information");
                return false;
            case "name" :
            {//Condition Check
                if(TMS.taskExist(key)){
                    System.out.println("Name Existed");
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
                //Operation
                TMS.getTask(keywords[1]).name = key;
                break;}

            case "description":
            {//Condition Check
                for(int i=0;i<key.length();i++){
                    if((key.charAt(i)<'a'||key.charAt(i)>'z')&&
                            (key.charAt(i)<'A'||key.charAt(i)>'Z')&&
                            (key.charAt(i)<'0'||key.charAt(i)>'9')&&
                            key.charAt(i)!='-') {
                        System.out.println("Illegal Description");
                        return false;
                    }
                }
                //Operation
                TMS.getTask(keywords[1]).description = key;
                break;}

            case "duration":
            {//Condition Check
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
                //Operation
                if(TMS.getTask(keywords[1]).getClass().equals(PrimitiveTask.class)){
                    TMS.getTask(keywords[1]).duration = Double.parseDouble(key);
                    TimeCalculation(TMS.getTask(keywords[1]));
                    if(RelatedTask != null) for(Task t: RelatedTask) TimeCalculation(t);
                }
                else {
                    System.out.println("Invalid Change for Task " + keywords[1]);
                    return false;
                }
                break;}

            case "prerequisites":
            {
                if(task.getClass().equals(PrimitiveTask.class)){
                    //Record and Empty the Prerequisite to be Changed
                    LinkedList<Task> tmp = ((PrimitiveTask)TMS.getTask(keywords[1])).getDirectPrerequisite();
                    LinkedList<Task> tmip = ((PrimitiveTask)TMS.getTask(keywords[1])).getIndirectPrerequisite();
                    ((PrimitiveTask)TMS.getTask(keywords[1])).getDirectPrerequisite().clear();
                    ((PrimitiveTask)TMS.getTask(keywords[1])).getIndirectPrerequisite().clear();

                    //Condition Check
                    for(String s:key.split(",")) {
                        boolean repeated = true;
                        for(Task t: ((PrimitiveTask)TMS.getTask(keywords[1])).getDirectPrerequisite()){
                            if(t.name.equals(s)){
                                repeated=false;
                                break;
                            }
                        }
                        if(repeated)((PrimitiveTask)TMS.getTask(keywords[1])).getDirectPrerequisite().add(TMS.getTask(s));
                        else System.out.println("Repeated Prerequisite Detected, Automatically Removed Duplication");
                    }
                    boolean loop = false;
                    for(Task t:((PrimitiveTask)TMS.getTask(keywords[1])).getDirectPrerequisite()){
                        loop = LoopJustify(t, TMS.getTask(keywords[1]), loop);
                        if(loop){
                            System.out.println("Loop Denied");
                            ((PrimitiveTask)TMS.getTask(keywords[1])).getDirectPrerequisite().addAll(tmp);
                            ((PrimitiveTask)TMS.getTask(keywords[1])).getIndirectPrerequisite().addAll(tmip);
                            return false;
                        }
                    }

                    //Operation
                    PrerequisiteCalculation((PrimitiveTask)TMS.getTask(keywords[1]));
                    TimeCalculation((PrimitiveTask)TMS.getTask(keywords[1]));
                    if(RelatedTask != null) for(Task t: RelatedTask){
                        if(t.getClass().equals(PrimitiveTask.class)) {
                            for(Task t1: tmp){
                                if(((PrimitiveTask) t).getDirectPrerequisite().contains(t1)) ((PrimitiveTask) t).getDirectPrerequisite().remove(t1);
                                if(((PrimitiveTask) t).getIndirectPrerequisite().contains(t1)) ((PrimitiveTask) t).getIndirectPrerequisite().remove(t1);
                            }
                            for(Task t1: tmip){
                                if(((PrimitiveTask) t).getDirectPrerequisite().contains(t1)) ((PrimitiveTask) t).getDirectPrerequisite().remove(t1);
                                if(((PrimitiveTask) t).getIndirectPrerequisite().contains(t1)) ((PrimitiveTask) t).getIndirectPrerequisite().remove(t1);
                            }
                            PrerequisiteCalculation(t);
                            TimeCalculation(t);
                        }
                        else TimeCalculation(t);
                    }
                }
                else{
                    System.out.println("Illegal Prerequisite Change");
                    return false;
                }
                break;}

            case "subtasks":
            {
                if(task.getClass().equals(CompositeTask.class)){
                    //Empty and record the Subtasks to be Changed
                    LinkedList<Task> tmacs = ((CompositeTask) task).getAllCompositeSubtask();
                    LinkedList<Task> tmas = ((CompositeTask) task).getAllSubtask();
                    LinkedList<Task> tms = ((CompositeTask) task).getDirectSubtask();
                    for(Task t: ((CompositeTask) TMS.getTask(keywords[1])).getDirectSubtask()){
                        t.setSub(false);
                    }
                    ((CompositeTask) TMS.getTask(keywords[1])).getAllCompositeSubtask().clear();
                    ((CompositeTask) TMS.getTask(keywords[1])).getAllSubtask().clear();
                    ((CompositeTask) TMS.getTask(keywords[1])).getDirectSubtask().clear();

                    //Condition Check
                    String[] prs = key.split(",");
                    if(prs.length < 2 || prs[0].equals(",") || prs[1].equals(",")){
                        System.out.println("Subtasks Number Wrong");
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
                        boolean repeated = true;
                        for(Task t1:((CompositeTask) task).getDirectSubtask()){
                            if(t1.name.equals(s)){
                                repeated=false;
                                break;
                            }
                        }
                        if(repeated)((CompositeTask) TMS.getTask(keywords[1])).getDirectSubtask().add(TMS.getTask(s));
                    }
                    boolean loop = false;
                    for(Task t: ((CompositeTask) TMS.getTask(keywords[1])).getDirectSubtask()){
                        if(t.getClass().equals(CompositeTask.class)){
                            for(Task t1: ((CompositeTask) t).getAllSubtask()) {
                                for(Task t2: ((PrimitiveTask)t1).getDirectPrerequisite()) {
                                    loop = LoopJustify(t2, t1, loop);
                                    if(loop == true) break;
                                }
                                if(loop == true) break;
                            }
                        }
                        else{
                            for(Task t1: ((PrimitiveTask)t).getDirectPrerequisite()) {
                                loop = LoopJustify(t1, t, loop);
                                if(loop == true) break;
                            }
                        }
                        if(loop){
                            System.out.println("Loop Denied");
                            ((CompositeTask) TMS.getTask(keywords[1])).getAllCompositeSubtask().addAll(tmacs);
                            ((CompositeTask) TMS.getTask(keywords[1])).getAllSubtask().addAll(tmas);
                            ((CompositeTask) TMS.getTask(keywords[1])).getDirectSubtask().addAll(tms);
                            return false;
                        }
                    }

                    //Operation
                    ((CompositeTask) TMS.getTask(keywords[1])).subtaskCalculate(((CompositeTask) TMS.getTask(keywords[1])).getDirectSubtask());
                    TimeCalculation((CompositeTask) TMS.getTask(keywords[1]));
                    if(RelatedTask != null) for(int i = CutPoint; i < RelatedTask.size(); i++){
                        ((CompositeTask)RelatedTask.get(i)).subtaskCalculate(((CompositeTask) RelatedTask.get(i)).getDirectSubtask());
                        TimeCalculation(RelatedTask.get(i));
                    }
                    if(RelatedTask != null) for(int i = 0; i < CutPoint; i++){
                        PrerequisiteCalculation(RelatedTask.get(i));
                        TimeCalculation(RelatedTask.get(i));
                    }
                }
                else{
                    System.out.println("Illegal Subtasks Change");
                    return false;
                }
                break;}
        }
        return true;
    }

    //Test only
    public static void main(String[] args){
        String a = "CreatePrimitiveTask task1 boil-water 0.3 ,";
        String b = "CreatePrimitiveTask task2 make-coffee 0.3 task1";
        String c = "CreateCompositeTask comp1 make-coffee task1,task2";
        String d = "CreatePrimitiveTask task3 boil-watering 0.3 comp1";
    }
}
