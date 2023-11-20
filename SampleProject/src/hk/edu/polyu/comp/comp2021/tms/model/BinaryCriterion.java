package hk.edu.polyu.comp.comp2021.tms.model;

import java.util.Objects;

public class BinaryCriterion extends Criterion{
    private String logicOp;
    private final Criterion [] dualCriteria;

    public String getLogicOp () {
        return this.logicOp;
    }

    public Criterion[] getDualCriteria () {
        return this.dualCriteria;
    }
    BinaryCriterion (String name, Criterion [] dualCriteria, String logicOp){
        this.name = name;
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

}
