package hk.edu.polyu.comp.comp2021.tms.model;

import java.util.LinkedList;

public class SimpleTask extends Task{

    SimpleTask(){}
    SimpleTask(String[] keywords){
        this.name = keywords[1];
        this.description = keywords[2];
        this.duration = Double.parseDouble(keywords[3]);
        this.prerequisite = new LinkedList<>();
    }

}
