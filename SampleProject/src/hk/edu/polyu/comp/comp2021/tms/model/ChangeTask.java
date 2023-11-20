package hk.edu.polyu.comp.comp2021.tms.model;

import hk.edu.polyu.comp.comp2021.tms.view.GUIViewer;

import java.util.*;
public class ChangeTask {
    LinkedList<Task> RelatedTask;
    //For Recording the Primitive and Composite Separate Point
    int CutPoint;

    ChangeTask(String[] keywords){
        CutPoint = FindRelatedTask(TMS.getTask(keywords[1]), TMS.tasks);
    }

    int FindRelatedTask(Task task, LinkedList<Task> l){
        int i = 0;
        for(Task t: l){
            if(t == task) continue;
            if(t.getClass().equals(CompositeTask.class)) {
                if (((CompositeTask) t).AllComSubtask.contains(task)) RelatedTask.add(t);
                if (((CompositeTask) t).AllSubtask.contains(task)) RelatedTask.add(t);
            }
            if(t.getClass().equals(PrimitiveTask.class)) {
                if (((PrimitiveTask) t).prerequisite.contains(task)) {
                    RelatedTask.addFirst(t);
                    i++;
                }
                if (((PrimitiveTask) t).IndirectPrerequisite.contains(task)) {
                    RelatedTask.addFirst(t);
                    i++;
                }
            }
        }
        return i;
    }

    void TimeCalculation(Task task){
        task.completion = 0;
        if(task.getClass().equals(PrimitiveTask.class)) {
            for (Task t : ((PrimitiveTask)task).prerequisite) task.completion = Math.max(task.completion, t.completion);
            task.completion += task.duration;
        }
        if(task.getClass().equals(CompositeTask.class)){
            for(Task t: ((CompositeTask)task).subtask){
                task.completion = Math.max(task.completion, t.completion);
                t.setSub(true);
            }
            for(Task t: ((CompositeTask)task).subtask){
                ((CompositeTask)task).DurationCalculation(t, 0, 0);
            }
        }
    }


    void LoopJustify(Task task, Task t1, boolean b){
        if(task == t1) {
            b = true;
            return;
        }
        if(task.getClass().equals(CompositeTask.class)){
            for(Task sub: ((CompositeTask) task).subtask) LoopJustify(sub, t1, b);
        }
        if(task.getClass().equals(PrimitiveTask.class)){
            if(((PrimitiveTask) task).prerequisite == null) return;
            for(Task t: ((PrimitiveTask) task).prerequisite) LoopJustify(t, t1, b);
        }
    }

    void PrerequisiteCalculation(Task task){
        for(Task t : ((PrimitiveTask)task).prerequisite) {
            ((PrimitiveTask) task).calculatePrerequisite(t);
        }
    }


    public boolean Change(String[] keywords, boolean isGUI){
        if(keywords.length != 4){
            GUIViewer.Log("Invalid Input Length", isGUI);
            return false;
        }

        if(keywords[1].isEmpty()||keywords[2].isEmpty()||keywords[3].isEmpty()){
            GUIViewer.Log("Missing Input", isGUI);
            return false;
        }

        if(!TMS.taskExist(keywords[1])){
            GUIViewer.Log("Task Not Found", isGUI);
            return false;
        }

        Task task = TMS.getTask(keywords[1]);
        String key = keywords[3];
        switch (keywords[2]) {
            default:
                GUIViewer.Log("No Such Task Information", isGUI);
                return false;
            case "name" :
                {//Condition Check
                if(TMS.taskExist(key)){
                    GUIViewer.Log("Name Existed", isGUI);
                    return false;
                }

                if(key.charAt(0)>='0'&&key.charAt(0)<='9'||key.length()>8){
                    GUIViewer.Log("Illegal Task Name", isGUI);
                    return false;
                }

                for(int i=0;i<key.length();i++){
                    if((key.charAt(i)<'a'||key.charAt(i)>'z')&&
                            (key.charAt(i)<'A'||key.charAt(i)>'Z')&&
                            (key.charAt(i)<'0'||key.charAt(i)>'9')) {
                        GUIViewer.Log("Illegal Task Name", isGUI);
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
                        GUIViewer.Log("Illegal Description", isGUI);
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
                        GUIViewer.Log("Illegal Range of Duration", isGUI);
                        return false;
                    }
                }catch(Exception e){
                    GUIViewer.Log("Illegal Duration Input", isGUI);
                    return false;
                }
                //Operation
                if(TMS.getTask(keywords[1]).getClass().equals(PrimitiveTask.class)){
                    TMS.getTask(keywords[1]).duration = Double.parseDouble(key);
                    TimeCalculation(TMS.getTask(keywords[1]));
                    if(RelatedTask != null) for(Task t: RelatedTask) TimeCalculation(t);
                }
                else {
                    GUIViewer.Log("Invalid Change for Task " + keywords[1], isGUI);
                    return false;
                }
                break;}

            case "prerequisites":
                {
                if(task.getClass().equals(PrimitiveTask.class)){
                    //Record and Empty the Prerequisite to be Changed
                    LinkedList<Task> tmp = ((PrimitiveTask)TMS.getTask(keywords[1])).prerequisite;
                    LinkedList<Task> tmip = ((PrimitiveTask)TMS.getTask(keywords[1])).IndirectPrerequisite;
                    ((PrimitiveTask)TMS.getTask(keywords[1])).prerequisite.clear();
                    ((PrimitiveTask)TMS.getTask(keywords[1])).IndirectPrerequisite.clear();

                    //Condition Check
                    for(String s:key.split(",")) {
                        boolean repeated = true;
                        for(Task t: ((PrimitiveTask)TMS.getTask(keywords[1])).prerequisite){
                            if(t.name.equals(s)){
                                repeated=false;
                                break;
                            }
                        }
                        if(repeated)((PrimitiveTask)TMS.getTask(keywords[1])).prerequisite.add(TMS.getTask(s));
                        else GUIViewer.Log("Repeated Prerequisite Detected, Automatically Removed Duplication", isGUI);
                    }
                    boolean loop = false;
                    for(Task t:((PrimitiveTask)TMS.getTask(keywords[1])).prerequisite){
                        LoopJustify(t, TMS.getTask(keywords[1]), loop);
                        if(loop){
                            GUIViewer.Log("Loop Denied", isGUI);
                            ((PrimitiveTask)TMS.getTask(keywords[1])).prerequisite = tmp;
                            ((PrimitiveTask)TMS.getTask(keywords[1])).IndirectPrerequisite = tmip;
                            return false;
                        }
                    }

                    //Operation
                    PrerequisiteCalculation((PrimitiveTask)TMS.getTask(keywords[1]));
                    TimeCalculation((PrimitiveTask)TMS.getTask(keywords[1]));
                    if(RelatedTask != null) for(Task t: RelatedTask){
                        if(t.getClass().equals(PrimitiveTask.class)) {
                            for(Task t1: tmp){
                                if(((PrimitiveTask) t).prerequisite.contains(t1)) ((PrimitiveTask) t).prerequisite.remove(t1);
                                if(((PrimitiveTask) t).IndirectPrerequisite.contains(t1)) ((PrimitiveTask) t).IndirectPrerequisite.remove(t1);
                            }
                            for(Task t1: tmip){
                                if(((PrimitiveTask) t).prerequisite.contains(t1)) ((PrimitiveTask) t).prerequisite.remove(t1);
                                if(((PrimitiveTask) t).IndirectPrerequisite.contains(t1)) ((PrimitiveTask) t).IndirectPrerequisite.remove(t1);
                            }
                            PrerequisiteCalculation(t);
                            TimeCalculation(t);
                        }
                        else TimeCalculation(t);
                    }
                }
                else{
                    GUIViewer.Log("Illegal Prerequisite Change", isGUI);
                    return false;
                }
                break;}

            case "subtasks":
                {
                if(task.getClass().equals(CompositeTask.class)){
                    //Empty and record the Subtasks to be Changed
                    LinkedList<Task> tmacs = ((CompositeTask) task).AllComSubtask;
                    LinkedList<Task> tmas = ((CompositeTask) task).AllSubtask;
                    LinkedList<Task> tms = ((CompositeTask) task).subtask;
                    for(Task t: ((CompositeTask) TMS.getTask(keywords[1])).subtask){
                        t.setSub(false);
                    }
                    ((CompositeTask) TMS.getTask(keywords[1])).AllComSubtask.clear();
                    ((CompositeTask) TMS.getTask(keywords[1])).AllSubtask.clear();
                    ((CompositeTask) TMS.getTask(keywords[1])).subtask.clear();

                    //Condition Check
                    String[] prs = key.split(",");
                    if(prs.length < 3 || prs[0].equals(",") || prs[1].equals(",")){
                        GUIViewer.Log("Subtasks Number Wrong", isGUI);
                        return false;
                    }
                    for(String s:prs) {
                        Task t = TMS.getTask(s);
                        if(t!=null){
                            if(t.getSub()){
                                GUIViewer.Log("An Appointed Task Has Been Used As Subtask Already", isGUI);
                                return false;
                            }
                        }
                        else{
                            GUIViewer.Log("Illegal Subtasks", isGUI);
                            return false;
                        }
                        boolean repeated = true;
                        for(Task t1:((CompositeTask) task).subtask){
                            if(t1.name.equals(s)){
                                repeated=false;
                                break;
                            }
                        }
                        if(repeated)((CompositeTask) TMS.getTask(keywords[1])).subtask.add(TMS.getTask(s));
                    }
                    boolean loop = false;
                    for(Task t: ((CompositeTask) TMS.getTask(keywords[1])).subtask){
                        if(t.getClass().equals(CompositeTask.class)){
                            for(Task t1: ((CompositeTask) t).AllSubtask) {
                                for(Task t2: ((PrimitiveTask)t1).prerequisite)LoopJustify(t2, t1, loop);
                            }
                        }
                        else{
                            for(Task t1: ((PrimitiveTask)t).prerequisite)LoopJustify(t1, t, loop);
                        }
                        if(loop){
                            GUIViewer.Log("Loop Denied", isGUI);
                            ((CompositeTask) TMS.getTask(keywords[1])).AllComSubtask = tmacs;
                            ((CompositeTask) TMS.getTask(keywords[1])).AllSubtask = tmas;
                            ((CompositeTask) TMS.getTask(keywords[1])).subtask =tms;
                            return false;
                        }
                    }

                    //Operation
                    ((CompositeTask) TMS.getTask(keywords[1])).subtaskCalculate(((CompositeTask) TMS.getTask(keywords[1])).subtask);
                    TimeCalculation((CompositeTask) TMS.getTask(keywords[1]));
                    if(RelatedTask != null) for(int i = CutPoint; i < RelatedTask.size(); i++){
                        ((CompositeTask)RelatedTask.get(i)).subtaskCalculate(((CompositeTask) RelatedTask.get(i)).subtask);
                        TimeCalculation(RelatedTask.get(i));
                    }
                    if(RelatedTask != null) for(int i = 0; i < CutPoint; i++){
                        PrerequisiteCalculation(RelatedTask.get(i));
                        TimeCalculation(RelatedTask.get(i));
                    }
                }
                else{
                    GUIViewer.Log("Illegal Subtasks Change", isGUI);
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
    }
}
