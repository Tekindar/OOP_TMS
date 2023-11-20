package hk.edu.polyu.comp.comp2021.tms.model;

import hk.edu.polyu.comp.comp2021.tms.controller.Create;
import hk.edu.polyu.comp.comp2021.tms.controller.Search;

import java.util.LinkedList;
import java.util.Arrays;

public class CriteriaStorage implements Create, Search {

    private static final LinkedList<Criterion> criteria = new LinkedList <Criterion> ();

    // map from name to stored criterion object
    public static Criterion searchName (String name) {
        for (Criterion criterion : criteria) {
            if (criterion.name.equals(name)) {
                return criterion;
            }
        }
        return null;
    }
    public static boolean isExisting (String name) {
        for (Criterion criterion : criteria) {
            if (criterion.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
    public static void printBasicCriteria (Criterion criterion) {

        System.out.print(criterion.getName() + " " + criterion.getOp() + " ");
        switch (criterion.getName()) {
            case "name" -> System.out.print(criterion.getValue().name);
            case "description" -> System.out.print(criterion.getValue().description);
            case "duration" -> System.out.print(criterion.getValue().duration);
            case "prerequisites" -> {
                if (criterion.getValue().prerequisites == null)
                    System.out.print(",");
                else
                    System.out.print(Arrays.toString(criterion.getValue().prerequisites));
            }
            case "subtasks" -> {
                if (criterion.getValue().subtasks == null)
                    System.out.print(",");
                else
                    System.out.print(Arrays.toString(criterion.getValue().subtasks));
            }
        }
    }
    public static void printBinaryCriteria (Criterion criterion) {
        if (criterion instanceof BasicCriterion) {
            printBasicCriteria(criterion);
            return;
        }
        printBinaryCriteria(((BinaryCriterion)criterion).getDualCriteria()[0]);
        System.out.print(" " + ((BinaryCriterion)criterion).getLogicOp() + " ");
        printBinaryCriteria(((BinaryCriterion)criterion).getDualCriteria()[1]);
    }

    public static void printAllCriteria (){
        for (Criterion c : criteria) {
            printBinaryCriteria(c);
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


        String oldName = command[1];
        String negName = command[0];
        Criterion oldC = searchName(oldName);
        if (oldC instanceof BasicCriterion) {
            BasicCriterion newC =
                    new BasicCriterion(negName, ((BasicCriterion)oldC).getValue(),  ((BasicCriterion)oldC).negatedOp());
            CriteriaStorage.criteria.add(newC);
        }
        else  {
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
    public String[] search(String criterion) {


        return null;
    }

}
