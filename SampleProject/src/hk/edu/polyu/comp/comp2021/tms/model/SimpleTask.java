package hk.edu.polyu.comp.comp2021.tms.model;

import java.util.LinkedList;

public class SimpleTask extends Task{

    SimpleTask(String[] keywords){
        name = keywords[1];
        description = keywords[2];
        duration = Double.parseDouble(keywords[3]);
        prerequisite = new LinkedList<>();
        completion=0;
        initializeTask();
    }

    void initializeTask(){
        calculatePrerequisite(this);
        for(Task t:prerequisite) this.completion = Math.max(this.completion, t.completion);
        this.completion+=this.duration;
    }

    void calculatePrerequisite(Task t){
        if(t.prerequisite.isEmpty())return;
        for(Task task:t.prerequisite){
            this.prerequisite.removeIf(pr -> pr.equals(task));
            this.IndirectPrerequisite.add(task);
            calculatePrerequisite(task);
        }
    }

}
