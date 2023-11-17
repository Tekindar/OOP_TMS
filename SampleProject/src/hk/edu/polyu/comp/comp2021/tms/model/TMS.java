package hk.edu.polyu.comp.comp2021.tms.model;
import java.util.LinkedList;

public class TMS {

    public static LinkedList<Task> tasks;
    private int TaskNumber;
    public TMS(){
        tasks = new LinkedList<>();
    }

    boolean CSTValidation(String[] keywords) {
        return SimpleTask.CSTValidation(keywords);
    }
    public static boolean taskExist(String s){
        if(tasks==null)return false;
        for(Task t: tasks) if(s.equals(t.name))return true;
        return false;
    }
    public void CreateSimpleTask(String[] keywords){
        if(CSTValidation(keywords)){
            SimpleTask task = new SimpleTask(keywords);
            tasks.add(task);
            TaskNumber++;
            System.out.println("Primitive Task Created");
        }

    }
    public void CreateCompositeTask(String[] keywords){
        if(CompositeTask.CCTValidation(keywords)){
            CompositeTask task = new CompositeTask(keywords);
            tasks.add(task);
            TaskNumber++;
            System.out.print("Composite Task Created");
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
