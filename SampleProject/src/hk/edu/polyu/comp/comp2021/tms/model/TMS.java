package hk.edu.polyu.comp.comp2021.tms.model;
import hk.edu.polyu.comp.comp2021.tms.view.GUIViewer;

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

    public void ChangeTask(String[] keywords, boolean isGUI){
        ChangeTask task = new ChangeTask(keywords);
        if(task.Change(keywords, isGUI)) GUIViewer.Log("Change Completed", isGUI);
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
