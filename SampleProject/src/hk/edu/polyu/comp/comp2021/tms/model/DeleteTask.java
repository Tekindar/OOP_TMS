package hk.edu.polyu.comp.comp2021.tms.model;

import java.util.LinkedList;

/**
 * This class is used for deleting task
 */
public class DeleteTask {
    /**
     *
     * @param p The primitive task in the Composite task which are deleted
     * @param c The composite task needed to be deleted
     * @return Whether the primitive is allowed to be deleted, true is not allowed.
     * This method check the primitive task in a composite task whether it can be deleted
     * 1. if the primitive task is a prerequisite task of other task which is not in the composite task
     * 2. if the composite task has only two subtasks and the primitive task is one of them
     * In these two cases, the deletion is not allowed.
     */
    private static boolean isPre(PrimitiveTask p,CompositeTask c){
        for(Task t: TMS.getAllTasks()){
            if(t instanceof PrimitiveTask){
                if(((PrimitiveTask) t).getDirectPrerequisite().contains(p)){
                    System.out.println("Deletion is not allowed: The composite task is a prerequisite task");
                    return true;
                }
            }
            else {
                LinkedList<Task> compositeDirectSub = ((CompositeTask) t).getDirectSubtask();
                if(compositeDirectSub.contains(c) && compositeDirectSub.size()<=2){
                    System.out.println("Deletion is not allowed: The composite task is " +
                            "involved in a two subtasks composite task.");
                    return true;

                }
            }
        }
        return false;
    }

    /**
     *
     * @param c current composite task in each recursion
     * @param parent the task needed to be deleted
     * @return if it is legal to be deleted
     * In these two cases, it is not allowed to delete a composite task
     * 1. The composite task is in another composite task which only contain 2 subtasks
     * 2. The composite task has a subtask which is the prequisite task of other subtask(s) which are
     * in this composite task.
     * Line 98 to 100 will decompose the composite task into primitive task.
     * Line94 to 96 will check whether each primitive task are allowed to be deleted.*
     */
    private static boolean detectTask(CompositeTask c,CompositeTask parent){
        //check whether it is allowed to delete, false is allowed
        boolean cpre = false;
        for(Task t:c.getAllSubtask()){
            if (t instanceof PrimitiveTask){
                if(isPre((PrimitiveTask) t,parent)){
                    return true;
                }
            }
            else{
                cpre = detectTask((CompositeTask) t,parent);
                if(cpre) return true;
            }
        }
        return false;
    }

    /**
     * @param p is current composite task in each recursion
     * @param c is the task needed to be deleted
     * The method will delete the primitive task in a composite task if it is allowed
     */
    private static void deletePrimitiveSubtask(PrimitiveTask p,CompositeTask c){
        if(isPre(p,c)) {System.out.println("Deletion is not allowed: The primitive task is a prerequisite task");}
        else{
            TMS.getAllTasks().remove(p);
        }
    }

    /**
     * @param parent record the composite task needed to be deleted.
     * @param c is the current composite task in each iteration.
     * This method delete the composite task, it calls detectTask() to check whether the task
     * can be deleted. If legal, it will delete all its subtasks according to their type.
     * If the subtask is composite task, it will do a recursion until all tasks are primitive
     * tasks. Then call deletePrimitiveTask to delete the primitive tasks and finally remove the composite task itself.
     * After that, it does an iteration to find whether it is a subtask. If it is, it will reduce the size
     * of its parent task.
     */
    public static void deleteCompositeTask(CompositeTask c, CompositeTask parent){
        if(detectTask(c,parent))return;
        for(Task t:c.getDirectSubtask()){
            if (t instanceof PrimitiveTask){
                deletePrimitiveSubtask((PrimitiveTask) t,parent);
            }
            else{
                deleteCompositeTask((CompositeTask) t,parent);
                TMS.getAllTasks().remove(t);
            }
        }
        for(Task t:TMS.getAllTasks()){
            if (t instanceof CompositeTask){
                if(((CompositeTask) t).getDirectSubtask().contains(parent)){
                    ((CompositeTask) t).getDirectSubtask().remove(parent);
                }
            }
        }
        TMS.getAllTasks().remove(c);
    }
    /**
     * @param p the primitive task needed to be deleted
     * @return Whether the primitive is allowed to be deleted, true is not allowed.
     * This method check the primitive task whether it can be deleted
     * 1. if the primitive task is a prerequisite task of other task
     * 2. if the composite task has only two subtasks and the primitive task is one of them
     * In these two cases, the deletion is not allowed.
     */
    private static boolean isPre(PrimitiveTask p){
        for(Task t: TMS.getAllTasks()){
            if(t instanceof PrimitiveTask){
                if(((PrimitiveTask) t).getDirectPrerequisite().contains(p)){
                    System.out.println("Deletion is not allowed: The primitive task is a prerequisite task");
                    return true;
                }
            } else{
                if(((CompositeTask) t).getDirectSubtask().contains(p)){
                    if(((CompositeTask) t).getDirectSubtask().size()<=2){
                        System.out.println("Deletion is not allowed: The primitive task is involved " +
                                "in a two subtasks composite task.");
                        return true;
                    }
                }
            }
        }

        return false;
    }
    /**
     * @param p the primitive task needed to be deleted
     * This method delete the primitive task
     * if it is allowed, the method will check whether it is a subtask,
     * then delete the size of subtask of the parent composite task
     */
    public static void deletePrimitiveTask(PrimitiveTask p){
        if(!isPre(p)) {
            if(p.getSub()) {
                for (Task t : TMS.getAllTasks()) {
                    if (t instanceof CompositeTask) {
                        if (((CompositeTask) t).getDirectSubtask().contains(p)) {
                            ((CompositeTask) t).getDirectSubtask().remove(p);
                        }
                    }
                }
            }
            TMS.getAllTasks().remove(p);
        }
    }
}
