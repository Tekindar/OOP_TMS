package hk.edu.polyu.comp.comp2021.tms.model;

import hk.edu.polyu.comp.comp2021.tms.controller.Create;
import hk.edu.polyu.comp.comp2021.tms.controller.Search;

import java.util.*;

/**
 *
 *
 * @author BAI Haoran
 */
public class CriteriaStorage implements Create, Search {


    private static final LinkedList<Criterion> criteria = new LinkedList <Criterion> ();
    static {
        criteria.add(IsPrimitiveCriterion.getSingleton());
    }

    public static LinkedList<Criterion> getCriteria() {
        return criteria;
    }

    // map from name to stored criterion object
    public static Criterion searchName (String name) {
        for (Criterion criterion : criteria) {
            if (criterion.getName().equals(name)) {
                return criterion;
            }
        }
        return null;
    }


    public static boolean isExisting (String name) {
        for (Criterion criterion : criteria) {
            if (criterion.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static void ClearCriteria() {
        criteria.clear();
    }

    public void printAllCriteria (){
        for (Criterion c : criteria) {
            c.printCriteria();
            System.out.println();
        }
    }

    @Override
    public void createBasicCriterion(String[] command) {
        if (!BasicCriterion.isLegal(command)) {
            System.out.println("Fail to define a BasicCriterion, please align with the rule and try again.");
            return;}

        BasicCriterion newC = new BasicCriterion(command[0], command[1], command[2], command[3]);
        CriteriaStorage.criteria.add(newC);
    }

    @Override
    public void createNegatedCriterion(String[] command) {
        for (String i : command ) {
            if (i==null){
                System.out.println("Fail to define a NegatedCriterion, please align with the rule and try again.");
                return;}
        }
        if (command.length!=2 || !(BasicCriterion.isLegalName(command[0])) || !CriteriaStorage.isExisting(command[1])) {
            System.out.println("Fail to define a NegatedCriterion, please align with the rule and try again.");
            return;}

        if(Objects.equals(command[1], "IsPrimitive")) {
            System.out.println ("The negation of IsPrimitive is not allowed");
            return;
        }


        String oldName = command[1];
        String negName = command[0];
        Criterion oldC = searchName(oldName);
        if (oldC instanceof BasicCriterion) {
            BasicCriterion newC =
                    new BasicCriterion(negName, ((BasicCriterion)oldC).getProperty_name(),  ((BasicCriterion)oldC).negatedOp(), ((BasicCriterion)oldC).getValue());
            CriteriaStorage.criteria.add(newC);
        }
        else  {
            assert oldC != null;
            BinaryCriterion newC =
                    new BinaryCriterion(negName, ((BinaryCriterion) oldC).getDualCriteria(), ((BinaryCriterion) oldC).negatedLogicOp());
            CriteriaStorage.criteria.add(newC);
        }


    }

    @Override
    public void createBinaryCriterion(String[] command) {

        if (!BinaryCriterion.isLegal(command)) {
            System.out.println("Fail to define a BinaryCriterion, please align with the rule and try again.");
            return;}
        Criterion []subCriteria = new Criterion []{CriteriaStorage.searchName(command[1]), CriteriaStorage.searchName(command[3])};
        BinaryCriterion newC = new BinaryCriterion(command[0], subCriteria, command[2]);
        CriteriaStorage.criteria.add(newC);
    }




                    temp = ((PrimitiveTask) t).getDirectPrerequisite().containsAll(cList) || ((PrimitiveTask) t).getIndirectPrerequisite().containsAll(cList);
                    if (Objects.equals(c.op, "contains") )
                        return temp;
                    else
                        return !temp;
                }
                case ("subtasks") : {
                    // Only CompositeTask has subtasks
                    if (t instanceof PrimitiveTask) return false;
                    LinkedList <Task> cList = new LinkedList<Task>();
                    for (String sub : c.getValue().subtasks) {
                        if (!TMS.taskExist(sub)) return false;
                        cList.add(TMS.getTask(sub));
                    }
                    temp = ((CompositeTask) t).getDirectSubtask().containsAll(cList);
                    if (Objects.equals(c.op, "contains") )
                        return temp;
                    else
                        return !temp;
                }

            }
        }


        assert c instanceof BinaryCriterion;
        boolean left = isMatching(t, ((BinaryCriterion) c).getDualCriteria()[0]);
        boolean right = isMatching(t, ((BinaryCriterion) c).getDualCriteria()[1]);
        switch (((BinaryCriterion) c).getLogicOp()) {
            case ("&&") : {
                return left && right;
            }
            case ("||") : {
                return left || right;
            }
            case ("!&&") : {
                return !(left && right);
            }
            case ("!||") : {
                return !(left || right);
            }

        }
        return false;
    }
    @Override
    public LinkedList<String> search(String []command) {
        if (command.length>1) {
            System.out.println("Fail to conduct a search, please align with the rule and try again.");
            return null;
        }
        if (!isExisting(command[0]))
        {System.out.println("Criterion name does not exist, please try again.");
            return null;
        }

        Criterion criterion = CriteriaStorage.searchName(command[0]);
        LinkedList<String> matchedList = new LinkedList<String>();
        for (Task t : TMS.tasks) {
            assert criterion != null;
            if (criterion.isMatching(t))
        for (Task t : TMS.getAllTasks()) {
            if (isMatching(t, criterion))
                matchedList.add(t.name);
        }
        searchResult(matchedList);
        return matchedList;
    }

    public void searchResult (LinkedList<String> res) {
        for (String s : res) {
            System.out.println (s);
        }
    }

}
