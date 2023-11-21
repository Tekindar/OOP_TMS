package hk.edu.polyu.comp.comp2021.tms.model;

/**
 * abstract class task containing basic information of a task
 * including name, dexription, duration, and its completion time.
 *
 * @author NING Weichen, FU Tao
 */
public abstract class Task {
    String name;
    String description;
    double duration;
    double completion;
    private boolean isSub;


    /**
     * record whether this Task is used as a subtask.
     *
     * @param s boolean of whether it is subtask
     */
    public void setSub(boolean s){
        isSub = s;
    }

    /**
     * get the result of whether it is subtask
     *
     * @return boolean of whether it is subtask
     */
    public boolean getSub(){
        return Boolean.TRUE.equals(isSub);
    }


}