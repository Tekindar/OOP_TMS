package hk.edu.polyu.comp.comp2021.tms.model;

/**
 * This class is used for deleting task
 */
public class DeleteTask {
    /**
     *
     * @param p The primitive task in the Composite task which are deleted
     * @param c The composite task needed to be deleted
     * @return Whether the primitive is allowed to be deleted, true is not allowed.
     * This method check the prmitive task in a composite task wheather can be deleted
     * 1. if the primitive task is a prerequiste task of other task which is not in the composite task
     * 2. if the composite task has only two subtasks and the primitive task is one of them
     * In these two cases, the deletion is not allowed.
     */
    private static boolean ispre(PrimitiveTask p,CompositeTask c){
        for(Task t: TMS.tasks){
            if(t instanceof PrimitiveTask){
                if(((PrimitiveTask) t).prerequisite.contains(p)){
                    if(!c.AllSubtask.contains(t)){
                        System.out.println("Deletion is not allowed: The composite task is a prerequisite task");
                        return true;
                    }
                }
            }
            else if(t.getClass().equals(CompositeTask.class)){
                if(((CompositeTask) t).subtask.contains(c)){
                    if(((CompositeTask) t).getsize()<=2){
                        System.out.println("Deletion is not allowed: The composite task is " +
                                "involved in a two subtasks composite task.");
                        return true;
                    }
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
    private static boolean detecttask(CompositeTask c,CompositeTask parent){
        //check whether it is allowed to delete, false is allowed
        boolean cpre = false;
        for(Task t:c.subtask){
            if (t instanceof PrimitiveTask){
                if(ispre((PrimitiveTask) t,parent)){
                    return true;
                }
            }
            else{
                cpre = detecttask((CompositeTask) t,parent);
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
    private static void deleteptask(PrimitiveTask p,CompositeTask c){
        if(ispre(p,c)) {System.out.println("Deletion is not allowed: The primitive task is a prerequisite task");}
        else{
            TMS.tasks.remove(p);
        }
    }

    /**
     * @param parent record the composite task needed to be deleted.
     * @param c is the current composite task in each iteration.
     * This method delete the composite task, it calls detecttask() to check whether the task
     * can be deleted. If legal, it will delete all its subtasks according to their type.
     * If the subtask is composite task, it will do a recursion until all tasks are primitive
     * tasks. Then call deleteptask to delete the primitive tasks and finally remove the composite task itself.
     * After that, it does an iteration to find wheather it is a subtask. If it is, it will reduce the size
     * of its parent task.
     */
    public static void deletectask(CompositeTask c, CompositeTask parent){
        if(detecttask(c,parent))return;
        for(Task t:c.subtask){
            if (t instanceof PrimitiveTask){
                deleteptask((PrimitiveTask) t,parent);
            }
            else{
                deletectask((CompositeTask) t,parent);
                TMS.tasks.remove(t);
            }
        }
        for(Task t:TMS.tasks){
            if (t instanceof CompositeTask){
                if(((CompositeTask) t).subtask.contains(parent)){
                    ((CompositeTask) t).subtask.remove(parent);
                }
            }
        }
        TMS.tasks.remove(c);
    }
    /**
     * @param p the primitive task needed to be deleted
     * @return Whether the primitive is allowed to be deleted, true is not allowed.
     * This method check the prmitive task wheather can be deleted
     * 1. if the primitive task is a prerequiste task of other task
     * 2. if the composite task has only two subtasks and the primitive task is one of them
     * In these two cases, the deletion is not allowed.
     */
    private static boolean ispre(PrimitiveTask p){
        for(Task t: TMS.tasks){
            if(t instanceof PrimitiveTask){
                if(((PrimitiveTask) t).prerequisite.contains(p)){
                    System.out.println("Deletion is not allowed: The primitive task is a prerequisite task");
                    return true;
                }
            } else{
                if(((CompositeTask) t).subtask.contains(p)){
                    if(((CompositeTask) t).getsize()<=2){
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
     * This method delete the prmitive task
     * if it is allowed, the method will check wheather it is a subtask,
     * then delete the size of subtask of the parent composite task
     */
    public static void deleteptask(PrimitiveTask p){
        if(!ispre(p)) {
            if(p.getSub()) {
                for (Task t : TMS.tasks) {
                    if (t instanceof CompositeTask) {
                        if (((CompositeTask) t).subtask.contains(p)) {
                            ((CompositeTask) t).subtask.remove(p);
                        }
                    }
                }
            }
            TMS.tasks.remove(p);
        }
    }
}
