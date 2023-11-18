package hk.edu.polyu.comp.comp2021.tms.model;
import java.util.LinkedList;

public class TMS {

    public static LinkedList<Task> tasks;
    private int TaskNumber;
    public TMS(){
        tasks = new LinkedList<>();
    }

    boolean CSTValidation(String[] keywords) {
        return PrimitiveTask.CSTValidation(keywords);
    }
    boolean CCTValidation(String[] keywords) {
        return CompositeTask.CCTValidation(keywords);
    }
    public static boolean taskExist(String s){
        if(tasks==null)return false;
        for(Task t: tasks) if(s.equals(t.name))return true;
        return false;
    }
    public void CreatePrimitiveTask(String[] keywords){
        if(CSTValidation(keywords)){
            PrimitiveTask task = new PrimitiveTask(keywords);
            tasks.add(task);
            TaskNumber++;
            System.out.println("Primitive Task Created");
        }

    }
    public void CreateCompositeTask(String[] keywords){
        if(CCTValidation(keywords)){
            CompositeTask task = new CompositeTask(keywords);
            tasks.add(task);
            TaskNumber++;
            System.out.println("Composite Task Created");
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
