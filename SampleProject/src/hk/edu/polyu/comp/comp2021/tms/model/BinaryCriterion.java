package hk.edu.polyu.comp.comp2021.tms.model;

import java.util.Objects;

public class BinaryCriterion extends Criterion{
    private final String logicOp;
    private final Criterion [] dualCriteria;

    public String getLogicOp () {
        return this.logicOp;
    }

    public Criterion[] getDualCriteria () {
        return this.dualCriteria;
    }
    BinaryCriterion (String name, Criterion [] dualCriteria, String logicOp){
        this.setName(name);
        this.dualCriteria = dualCriteria;
        this.logicOp=logicOp;
    }

    public String negatedLogicOp () {
       if (logicOp.equals("&&") )
           return "!&&";
       else if (logicOp.equals("||"))
           return "!||";
       return null;
    }

    public static boolean isLegalName (String command) {
        return BasicCriterion.isLegalName(command);
    }
    public static boolean isLegalLogicOp (String command) {
        if (! ( Objects.equals(command, "&&") || Objects.equals(command, "||") ) )
        {
            System.out.println ("Illegal logic operators, please please align with the rule and try again.");
            return false;
        }
        return true;
    }



    public static boolean isLegalOperands (String []command) {
        if ( CriteriaStorage.isExisting(command[0]) && CriteriaStorage.isExisting(command[1]))
            return true;
        System.out.println ("Illegal Criterion Operands to define a BinaryCriterion, please please align with the rule and try again.");
        return false;
    }

    public static boolean isLegal (String []command) {
        return (command.length==4 && isLegalName(command[0]) && isLegalLogicOp(command[2])
                && isLegalOperands( new String[] {command[1], command[3]} ) );

    }

    public void printBinaryCriteria (Criterion c) {

        if (c instanceof BasicCriterion) {

            ((BasicCriterion) c).printCriteria();
            return;
        }
        System.out.print("(");
        printBinaryCriteria(((BinaryCriterion)c).getDualCriteria()[0]);
        System.out.print(" " + ((BinaryCriterion)c).getLogicOp() + " ");
        printBinaryCriteria(((BinaryCriterion)c).getDualCriteria()[1]);
        System.out.print(")");
    }

    @Override
    public void printCriteria () {
        printBinaryCriteria (this);
    }


    public boolean isMatching(Task t, Criterion c) {
        if (c instanceof BasicCriterion) return c.isMatching(t);

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
    public boolean isMatching(Task t) {
        return this.isMatching(t, this);
    }

}
