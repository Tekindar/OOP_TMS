package hk.edu.polyu.comp.comp2021.tms.model;

public class SimpleTask extends Task{

    SimpleTask(){}
    SimpleTask(String[] keywords){
        this.name = keywords[1];
        this.description = keywords[2];
        this.duration = Double.parseDouble(keywords[3]);
        this.completion = 0;

        String[] tempPR = keywords[4].split(",");
        if(tempPR.length!=0) this.prerequisite = tempPR.clone();

    }

}
