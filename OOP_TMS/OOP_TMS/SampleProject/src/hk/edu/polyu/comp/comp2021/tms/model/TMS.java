package hk.edu.polyu.comp.comp2021.tms.model;
import hk.edu.polyu.comp.comp2021.tms.view.GUIViewer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class TMS {

    public static LinkedList<Task> tasks;
    private int TaskNumber;
    public TMS(){
        tasks = new LinkedList<>();
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
                DeleteTask.deletectask((CompositeTask) tem, (CompositeTask) tem);
            } else{
                DeleteTask.deleteptask((PrimitiveTask) tem);
            }
        }
    }

    /**
     * @param o is used for distingushi the command between printone and printall
     *          true represent one and false represent all.
     */
    public void printTask(String[] keywords, boolean o){
        if(o){
            Print_Task.printone(keywords);
        }
    }
    public void storeTask(String[] keywords) throws IOException{
        if (keywords.length != 2) {
            System.out.println("Wrong input length.");
            return;
        }
        File_storage.store(keywords);
    }

    public static boolean taskExist(String s){
        if(tasks==null)return false;
        for(Task t: tasks) if(s.equals(t.name))return true;
        return false;
    }
    public void CreatePrimitiveTask(String[] keywords, boolean isGUI){
        String[] prs = keywords[4].split(",");
        Set<String> hash_set = new HashSet<>(Arrays.asList(prs));
        keywords[4] = createTaskString(hash_set);
        System.out.println(keywords[4]);
        if(PrimitiveTask.CPTValidation(keywords, isGUI)){
            PrimitiveTask task = new PrimitiveTask(keywords);
            tasks.add(task);
            TaskNumber++;
            GUIViewer.Log("Primitive Task Created", isGUI);
        }

    }
    public void CreateCompositeTask(String[] keywords, boolean isGUI){
        String[] prs = keywords[3].split(",");
        Set<String> hash_set = new HashSet<>(Arrays.asList(prs));
        keywords[3] = createTaskString(hash_set);
        if(CompositeTask.CCTValidation(keywords, isGUI)){
            CompositeTask task = new CompositeTask(keywords);
            tasks.add(task);
            TaskNumber++;
            GUIViewer.Log("Composite Task Created", isGUI);
        }
    }

    public static Task getTask(String tName){
        for(Task t:tasks){
            if(t.name.equals(tName)){
                return t;
            }
        }
        return null;
    }
    public String[] getTaskNames(){
        String[] temp = new String[TaskNumber];
        int index = 0;
        for(Task t:tasks){
            temp[index++] = t.name;
        }
        return temp;
    }

    public static String createTaskString(String[] s){
        boolean flag = true;
        StringBuilder Temporary= new StringBuilder();
        for (String string : s) {
            if (flag) flag = false;
            else Temporary.append(",");
            Temporary.append(string);
        }
        return Temporary.toString();
    }
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

}
