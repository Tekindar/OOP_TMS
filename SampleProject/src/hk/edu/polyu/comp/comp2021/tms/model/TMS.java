package hk.edu.polyu.comp.comp2021.tms.model;

import hk.edu.polyu.comp.comp2021.tms.view.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * This class is a system that receives user input and
 * commit operation based on the inputs.
 *
 * @author ..
 */
public class TMS {

    private static LinkedList<Task> tasks;
    private int TaskNumber;

    /**
     * Constructor of the Task Management System,
     * initialize an instance of TMS with a new linked-list of tasks.
     */
    public TMS(){
        initializeTasks();
    }

    /**
     * static method to initialize the static linked-list tasks
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
        for(Task t: tasks) if(s.equals(t.getName()))return true;
        return false;
    }

    /**
     * the method removes duplications of prerequisite inputs and
     * invokes validation, if input is valid, then create the task.
     *
     * @param keywords the input details of a primitive task
     */
    public void CreatePrimitiveTask(String[] keywords){

        if(PrimitiveTask.CPTValidation(keywords)){
            String[] prs = keywords[4].split(",");
            Set<String> hash_set = new HashSet<>(Arrays.asList(prs));
            keywords[4] = createTaskString(hash_set);
            System.out.println(keywords[4]);
            PrimitiveTask task = new PrimitiveTask(keywords);
            tasks.add(task);
            TaskNumber++;
            System.out.println("Primitive Task Created");
        }
    }

    /**
     * the method remove duplications of subtask inputs and
     * invokes validation, if input is valid, then create the task.
     *
     * @param keywords the input details of a composite task
     */
    public void CreateCompositeTask(String[] keywords){

        if(CompositeTask.CCTValidation(keywords)){
            String[] prs = keywords[3].split(",");
            Set<String> hash_set = new HashSet<>(Arrays.asList(prs));
            keywords[3] = createTaskString(hash_set);
            CompositeTask task = new CompositeTask(keywords);
            tasks.add(task);
            TaskNumber++;
            System.out.println("Composite Task Created");
        }
    }

    /**
     * @param keywords The input command to ChangeTask property
     */
    public void ChangeTask(String[] keywords){
        ChangeTask task = new ChangeTask(keywords);
        if(task.Change(keywords)){
            System.out.println("Change Task Completed");
        }
    }
    /**
     * @param keywords The input command to report duration of a task
     */
    public void reportDuration (String[] keywords){
        if (keywords.length!=2 || getTask(keywords[1]) == null) {
            System.out.println ("Illegal command to report duration.");
        return;}

        assert getTask(keywords[1]) != null;
        System.out.println ("The duration of task " + keywords[1] + " is " +getTask(keywords[1]).getDuration());
    }
    /**
     * @param keywords The input command to report the earliest finish time of a task
     */
    public void reportEarliestFinishTime (String[] keywords){
        if (keywords.length!=2 || getTask(keywords[1]) == null) {
            System.out.println ("Illegal command to report duration.");
            return;}

        assert getTask(keywords[1]) != null;
        System.out.println ("The earliest finish time of task " + keywords[1] + " is " +getTask(keywords[1]).getCompletion());
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
            if(t.getName().equals(tName)){
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
            temp[index++] = t.getName();
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

    /**
     * @param keywords is the user input
     * @throws IOException casused by the file read streaam
     * This method call clear() to initialize the system, then load data from file.
     */
    public void FileLoad(String[] keywords) throws IOException {
        FileLoad.clear();
        FileLoad.load(keywords);
    }

    /**
     * @param keywords is the user input
     * This method will delete the task determined by user.
     * First, it will check wheather the input is valid. Then it will distinguish
     * the primitive task and the composite task and delete them according
     * to correspoding method.
     */
    public void DeleteTask(String[] keywords) {
        if (keywords.length != 2) {
            System.out.println("Invalid Input Length");
        }
        if (keywords[1].isEmpty()) {
            System.out.println("Missing Input");
        }
        // If the input is legal, get the task.
        Task tem = getTask(keywords[1]);
        if (tem == null) {
            System.out.println("The task name is not included"); //if tem = null, it means no task returned
        } else {
            if (tem.getClass().equals(CompositeTask.class)) {
                DeleteTask.deleteCompositeTask((CompositeTask) tem, (CompositeTask) tem);
            } else{
                DeleteTask.deletePrimitiveTask((PrimitiveTask) tem);
            }
            TaskNumber--;
        }

    }

    /**
     * @param keywords The input command
     * @param o is used for distinguish the command between printone and printall
     *          true represent one and false represent all.
     */
    public void printTask(String[] keywords, boolean o){
        if(o){
            Print_Task.printone(keywords);
        }
        else{
            Print_Task.printAll();
        }
    }

    /**
     * @param keywords user input
     * @throws IOException due to storing file
     */
    public void storeTask(String[] keywords) throws IOException{
        if (keywords.length != 2) {
            System.out.println("Wrong input length.");
            return;
        }
        File_storage.store(keywords);
    }
    /**
     * @return the size of existing tasks
     */
    public int getSize() {
        return TaskNumber;
    }
}
