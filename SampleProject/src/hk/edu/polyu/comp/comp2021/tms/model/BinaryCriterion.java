package hk.edu.polyu.comp.comp2021.tms.model;

import java.util.Objects;
/**
 * BinaryCriterion is consisting of name of BinaryCriterion, two existing criteria names, and a logic operator
 * BasicCriterion is the subclass of Criterion, override the printCriteria and isMatching methods
 * to implement print/checking legal creation features
 * (Since the recursion needs the different parameter list, use an overloaded version to achieve override)
 */
public class BinaryCriterion extends Criterion{
    private final String logicOp;
    private final Criterion [] dualCriteria;

    /**
     * @return logic operator of this BinaryCriterion
     */
    public String getLogicOp () {
        return this.logicOp;
    }

    /**
     * @return an array of two Criteria (two existing criteria that formed this BinaryCriterion)
     */
    public Criterion[] getDualCriteria () {
        return this.dualCriteria;
    }
    /**
     * @param name the name of BinaryCriterion
     * @param dualCriteria an array of two Criteria (two existing criteria that formed this BinaryCriterion)
     * @param logicOp the logic operator of BinaryCriterion
     */
    BinaryCriterion (String name, Criterion [] dualCriteria, String logicOp){
        this.setName(name);
        this.dualCriteria = dualCriteria;
        this.logicOp=logicOp;
    }


    /**
     * @return a string of negated logic operator
     */
    public String negatedLogicOp () {
       if (logicOp.equals("&&") )
           return "!&&";
       else if (logicOp.equals("||"))
           return "!||";
       return null;
    }


    /**
     * @param name the name of BinaryCriterion
     * @return the boolean value to check whether this is a legal BinaryCriterion name
     */
    public static boolean isLegalName (String name) {
        return BasicCriterion.isLegalName(name);
    }

    /**
     * @param command the logic operator of BinaryCriterion, either "&&" or "||"
     * @return the boolean value to check whether this is a legal BinaryCriterion logic operator
     */
    public static boolean isLegalLogicOp (String command) {
        if (! ( Objects.equals(command, "&&") || Objects.equals(command, "||") ) )
        {
            System.out.println ("Illegal logic operators, please please align with the rule and try again.");
            return false;
        }
        return true;
    }

    /**
     * @param command a Criterion array of two operands of BinaryCriterion (two existing criteria that formed this BinaryCriterion)
     * @return the boolean value to check whether this is a legal BinaryCriterion operands array
     */

    public static boolean isLegalOperands (String []command) {
        if ( CriteriaStorage.isExisting(command[0]) && CriteriaStorage.isExisting(command[1]))
            return true;
        System.out.println ("Illegal Criterion Operands to define a BinaryCriterion, please please align with the rule and try again.");
        return false;
    }

    /**
     * @param command a String array of the input command (content after the keyword DefineBasicCriterion)
     * @return the boolean value to check whether this is a legal input to define a new BinaryCriterion
     */

    public static boolean isLegal (String []command) {
        return (command.length==4 && isLegalName(command[0]) && isLegalLogicOp(command[2])
                && isLegalOperands( new String[] {command[1], command[3]} ) );

    }

    /**
     * @param c The criterion to be printed in recursion
     * Basic case: c is a BasicCriterion
     */
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


    /**
     * @param t The retrieved task
     * @param c The Criterion to be evaluated
     * @return THe boolean value to measure whether this task fulfills the criterion c
     */
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
