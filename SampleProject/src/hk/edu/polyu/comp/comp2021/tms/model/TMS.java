package hk.edu.polyu.comp.comp2021.tms.model;
import java.util.LinkedList;

public class TMS {

    LinkedList<Task> tasks;
    public TMS(){
        tasks = new LinkedList<>();
    }

    boolean CSTValidation(String[] keywords){
        // still need to check for keywords[5] to see if not properly ended


        if(keywords[1].isEmpty()||keywords[2].isEmpty()||keywords[3].isEmpty()||keywords[4].isEmpty()){
            System.out.println("Missing Input");
            return false;
        }
        // validating name
        String key = keywords[1];
        if(taskExist(key)){
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
        for(String s:prs) if(!taskExist(s)) {
            System.out.println("Illegal Subtasks");
            return false;
        }
        return true;
    }
    public boolean taskExist(String s){
        if(tasks==null)return false;
        for(Task t: tasks) if(s.equals(t.name))return true;
        return false;
    }
    public void CreateSimpleTask(String[] keywords){
        if(CSTValidation(keywords)){
            SimpleTask task = new SimpleTask(keywords);
            String[] tempPR = keywords[4].split(",");
            for(String s:tempPR){
                for(Task t:tasks){
                    if(t.name.equals(s)) {
                        if (task.prerequisite.contains(t)) {
                            System.out.println("Repeated Prerequisite, Automatically Removed Duplication");
                        }
                        else {
                            task.prerequisite.add(t);
                        }
                    }

                }
            }
            task.initializeTask();
            tasks.add(task);
            System.out.println("Simple Task Created");
        }

    }

}
