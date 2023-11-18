package hk.edu.polyu.comp.comp2021.tms.model;

import java.util.LinkedList;

public abstract class Task {
    String name;
    String description;
    double duration;
    double completion;
    private static boolean isSub;
    LinkedList<Task> prerequisite;
    LinkedList<Task> IndirectPrerequisite;

    Task(){
        isSub = false;
    }
    public void setSub(boolean s){
        isSub = s;
    }
    public boolean getSub(){
        return Boolean.TRUE.equals(isSub);
    }

}