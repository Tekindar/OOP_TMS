package hk.edu.polyu.comp.comp2021.tms.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * This class is a system that receives user input and
 * commit operatation based on the inputs.
 *
 * @author ..
 */
public class TMS {

    private static LinkedList<Task> tasks;
    private int TaskNumber;

    /**
     * Constructer of the Task Management System,
     * initialize an instance of TMS with a new linkedlist of tasks.
     */
    public TMS(){
        initializeTasks();
    }

    /**
     * static method to initialize the static linkedlist tasks
     */
    static void initializeTasks(){
        tasks = new LinkedList<>();
    }

    /**
     * the method returns whether a task is existing in the system
     *
     * @param s the name of the target task
     * @return the existence of the task
     */
    public static boolean taskExist(String s){
        if(tasks==null)return false;
        for(Task t: tasks) if(s.equals(t.name))return true;
        return false;
    }

    /**
     * the method removes duplications of prerequisite inputs and
     * involks validation, if input is valid, then create the task.
     *
     * @param keywords the input details of a primitive task
     */
    public void CreatePrimitiveTask(String[] keywords){
        String[] prs = keywords[4].split(",");
        Set<String> hash_set = new HashSet<>(Arrays.asList(prs));
        keywords[4] = createTaskString(hash_set);
        System.out.println(keywords[4]);
        if(PrimitiveTask.CPTValidation(keywords)){
            PrimitiveTask task = new PrimitiveTask(keywords);
            tasks.add(task);
            TaskNumber++;
            System.out.println("Primitive Task Created");
        }
    }

    /**
     * the method remove duplications of subtask inputs and
     * involk validation, if input is valid, then create the task.
     *
     * @param keywords the input details of a composite task
     */
    public void CreateCompositeTask(String[] keywords){
        String[] prs = keywords[3].split(",");
        Set<String> hash_set = new HashSet<>(Arrays.asList(prs));
        keywords[3] = createTaskString(hash_set);
        if(CompositeTask.CCTValidation(keywords)){
            CompositeTask task = new CompositeTask(keywords);
            tasks.add(task);
            TaskNumber++;
            System.out.println("Composite Task Created");
        }
    }

    public void ChangeTask(String[] keywords){
        ChangeTask task = new ChangeTask(keywords);
        if(task.Change(keywords)){
            System.out.println("Change Task Completed");
        }
    }


    /**
     * the method returns the specific task
     * by receiving a name.
     *
     * @param tName the name of target task
     * @return the task with correct name, null if not found
     */
    public static Task getTask(String tName){
        for(Task t:tasks){
            if(t.name.equals(tName)){
                return t;
            }
        }
        return null;
    }

    /**
     * the method returns an array of names of the all tasks.
     *
     * @return return the names in array of type string
     */
    public String[] getTaskNames(){
        String[] temp = new String[TaskNumber];
        int index = 0;
        for(Task t:tasks){
            temp[index++] = t.name;
        }
        return temp;
    }

    /**
     * a method generate the proper inputs of a task collection
     * easy for use.
     *
     * @param s the set of names of tasks
     * @return the final string that includes the name in proper form
     */
    public static String createTaskString(Set<String> s){
        StringBuilder Temporary = new StringBuilder();
        if(s.isEmpty())Temporary.append(",");
        else{
            boolean flag = true;
            for (String string : s) {
                if (flag) flag = false;
                else Temporary.append(",");
                Temporary.append(string);
            }
        }
        return Temporary.toString();
    }

    /**
     * return all existing tasks
     * @return linked-list of tasks existing
     */
    public static LinkedList<Task> getAllTasks(){
        return tasks;
    }
}
