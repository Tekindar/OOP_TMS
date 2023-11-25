package hk.edu.polyu.comp.comp2021.tms.model;

import hk.edu.polyu.comp.comp2021.tms.controller.Create;
import hk.edu.polyu.comp.comp2021.tms.controller.Search;

import java.util.*;

/**
 * CriteriaStorage is used to store and manage the created criteria.
 * It also implements the Create and Search interfaces
 * so that it could achieve the definition of criteria and search functionality.
 * The search method are based on the polymorphism of isMatching method, which is implemented in
 * two types of Criteria (BasicCriterion and BinaryCriterion) respectively.
 */
public class CriteriaStorage implements Create, Search {


    private static final LinkedList<Criterion> criteria = new LinkedList <Criterion> ();
    static {
        criteria.add(IsPrimitiveCriterion.getSingleton());
    }

    /**
     * @return a LinkedList of all defined criteria
     */
    public static LinkedList<Criterion> getCriteria() {
        return criteria;
    }


    /**
     * @param name the name of criterion
     * @return if this name is defined, return the reference of this criterion object
     * otherwise return null
     */
    public static Criterion searchName (String name) {
        for (Criterion criterion : criteria) {
            if (criterion.getName().equals(name)) {
                return criterion;
            }
        }
        return null;
    }


    /**
     * @param name the name of criterion
     * @return if this name is defined, return true
     * otherwise return false
     */
    public static boolean isExisting (String name) {
        for (Criterion criterion : criteria) {
            if (criterion.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * clear the criteria LinkedList (used for store/load/undo/redo operations)
     */
    public static void clearCriteria() {
        criteria.clear();
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





    @Override
    public void search(String []command) {
        if (command.length>1) {
            System.out.println("Fail to conduct a search, please align with the rule and try again.");
            return;
        }
        if (!isExisting(command[0]))
        {System.out.println("Criterion name does not exist, please try again.");
            return;
        }

        Criterion criterion = CriteriaStorage.searchName(command[0]);
        LinkedList<String> matchedList = new LinkedList<String>();

        for (Task t : TMS.getAllTasks()) {
            assert criterion != null;
            if (criterion.isMatching(t))
                matchedList.add(t.getName());
        }
        searchResult(matchedList);
    }

    /**
     * @param res the LinkedList of search results (matched task names)
     */
    public void searchResult (LinkedList<String> res) {
        for (String s : res) {
            System.out.println (s);
        }
    }

}
