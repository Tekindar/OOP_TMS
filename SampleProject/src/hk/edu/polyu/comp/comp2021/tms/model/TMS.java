package hk.edu.polyu.comp.comp2021.tms.model;
import hk.edu.polyu.comp.comp2021.tms.view.GUIViewer;

import java.util.LinkedList;

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
        if(PrimitiveTask.CPTValidation(keywords, isGUI)){
            PrimitiveTask task = new PrimitiveTask(keywords);
            tasks.add(task);
            TaskNumber++;
            GUIViewer.Log("Primitive Task Created", isGUI);
        }

    }
    public void CreateCompositeTask(String[] keywords, boolean isGUI){
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

}
