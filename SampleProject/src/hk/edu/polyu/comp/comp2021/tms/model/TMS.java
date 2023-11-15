package hk.edu.polyu.comp.comp2021.tms.model;

public class TMS {

    String tasks = " ";
    public TMS(){
    }

    public boolean taskExist(String s){
        boolean flag = false;
        if(tasks==null)return false;
        for(String t: tasks.split(" ")) {
            if(s.equals(t)) {
                flag = true; break;
            }
        }
        return flag;
    }
    public void CreateSimpleTask(String[] keywords){
        SimpleTask task = new SimpleTask(keywords);
        tasks += task.name + " ";
        System.out.println(1);
    }

}
