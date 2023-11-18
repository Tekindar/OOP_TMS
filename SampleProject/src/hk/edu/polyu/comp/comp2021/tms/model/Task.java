package hk.edu.polyu.comp.comp2021.tms.model;

import hk.edu.polyu.comp.comp2021.tms.view.GUIViewer;

public abstract class Task {
    String name;
    String description;
    double duration;
    double completion;
    private boolean isSub;

    Task(){
    }
    public void setSub(boolean s){
        isSub = s;
    }
    public boolean getSub(){
        return Boolean.TRUE.equals(isSub);
    }


}