package hk.edu.polyu.comp.comp2021.tms.model;

/**
 * abstract class task containing basic information of a task
 * including name, dexription, duration, and its completion time.
 *
 * @author NING Weichen, FU Tao
 */
public abstract class Task {
    private String name;
    private String description;
    private double duration;
    /**
     *  the earliest completion time of the task
     */
    protected double completion;
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

    /**
     *  the name of the task
     */ /**
     * return the name of the task
     *
     * @return name
     */
    public String getName(){
        return name;
    }
    /**
     *  the description of the task
     */ /**
     * return the description of the task
     *
     * @return description
     */
    public String getDescription(){
        return description;
    }
    /**
     * return the completion of the task
     *
     * @return completion
     */
    public double getCompletion(){
        return completion;
    }
    /**
     *  the duration time of the task
     */ /**
     * return the duration of the task
     *
     * @return duration
     */
    public double getDuration(){
        return duration;
    }


    /**
     * set the name of the task
     *
     * @param name new name
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * set the description of the task
     *
     * @param description new description
     */
    public void setDescription(String description){
        this.description = description;
    }
    /**
     * set the completion of the task
     *
     * @param completion new completion
     */
    public void setCompletion(double completion){
        this.completion = completion;
    }
    /**
     * set the duration of the task
     *
     * @param duration new duration
     */
    public void setDuration(double duration){
        this.duration = duration;
    }
}