package hk.edu.polyu.comp.comp2021.tms.view;

import hk.edu.polyu.comp.comp2021.tms.model.CompositeTask;
import hk.edu.polyu.comp.comp2021.tms.model.PrimitiveTask;
import hk.edu.polyu.comp.comp2021.tms.model.TMS;
import hk.edu.polyu.comp.comp2021.tms.model.Task;

/**
 * Print_Task is used for printing one or more tasks.
 */
public  class Print_Task {
    /**
     * @param taskname is the task deleted
     * In this method, the code will check wheather the task exists, then it will get the task name
     *                 After that, it will print the information of the task according to its type.
     */

    public static void printone(String taskname){
        Task t = null;
        boolean exist = false;
        for(Task a: TMS.getAllTasks()){
            if(a.getName().equals(taskname)){
                t = a;
                exist = true;
                break;
            }
        }//get the task and check wheather it exists

        if(!exist){
            System.out.println("Task not found");
            return;
        }
        System.out.println("The name of the Task: "+t.getName());
        if(t instanceof PrimitiveTask){
            System.out.println("This is a Primitive Task");
            if(((PrimitiveTask) t).getDirectPrerequisite().isEmpty()){
                System.out.println("It doesn't have any prerequisite task");
            }
            else {
                System.out.print("It has ");
                for (Task a : ((PrimitiveTask) t).getDirectPrerequisite()) {
                    System.out.print(a.getName() + " ");
                }
                System.out.println("as its prerequisite task(s)");
            }
        }
        else {
            System.out.println("This is a Composite Task");
            System.out.print("It has ");
            for (Task a : ((CompositeTask) t).getDirectSubtask()) {
                System.out.print(a.getName() + " ");
            }
            System.out.println("as its subtasks");
        }
        System.out.println("The duration of the Task: "+t.getDuration());
        System.out.println("The description of the Task: "+t.getDescription());
    }

    /**
     * Traverse the tasks and call printone()
     */
    public static void printAll(){
        for(Task t: TMS.getAllTasks()){
            printone(t.getName());
            //divide the tasks
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------------");
        }
    }

    /**
     * @param a is the user input
     * Simply check the command and call printone()
     */
    public static void printone(String[] a){
        if(a.length!=2){
            System.out.println("Wrong input length");
        }
        printone(a[1]);
    }
}

