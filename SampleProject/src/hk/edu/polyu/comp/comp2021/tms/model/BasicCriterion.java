package hk.edu.polyu.comp.comp2021.tms.model;



import java.util.LinkedList;
import java.util.Objects;


/**
 * BasicCriterion is consisting of name, property, operator and value
 * BasicCriterion is the subclass of Criterion, override the printCriteria and isMatching methods
 * to implement print/checking legal creation features
 */
public class BasicCriterion extends Criterion  {

    /** @param name name of BasicCriterion, follow the rule:
     *               1) may contain only English letters and digits,
     *               2) cannot start with digits, and
     *               3) may contain at most eight characters
     *  @param property_name name of property of BasicCriterion, either name, description, duration, prerequisites, or subtasks
     *  @param op operator of BasicCriterion
     *  @param value value of BasicCriterion
     *               If property_name is name or description, op must be "contains" and value must be a string in double quotes;
     *               If property_name is duration, op can be >, <, >=, <=, ==, or !=, and value must be a real value.
     *               If property_name is prerequisites or subtasks, op must be "contains", and value must be a list of comma-separated task names.
     */
    BasicCriterion (String name, String property_name, String op, String value){
        this.setName(name);
        this.setProperty_name(property_name);
        this.setOp(op);
        this.setProperty(new Property(property_name, value));
    }
    /** Overload the constructor to facilitate the creation of negated BasicCriterion,
     * @param name name of BasicCriterion, follow the rule:
     *               1) may contain only English letters and digits,
     *               2) cannot start with digits, and
     *               3) may contain at most eight characters
     *  @param property_name name of property of BasicCriterion, either name, description, duration, prerequisites, or subtasks
     *  @param op (negated) operator of BasicCriterion
     *  @param property This property is used to create negatedCriterion based on BasicCriterion (Here it setProperty is the deep copy of property)
     */
    BasicCriterion (String name, String property_name, String op, Property property){
        this.setName(name);
        this.setProperty_name(property_name);
        this.setOp(op);
        this.setProperty(property);
    }


    /**
     * @return the negated operator of original BasicCriterion
     */
    public String negatedOp () {
         switch (getOp()) {
            case (">") : return "<=";
            case ("<") : return ">=";
             case (">=") : return "<";
             case ("<=") : return ">";
             case ("==") : return "!=";
             case ("!=") : return "==";
             case ("contains") : return "!contains";
        }
         return null;
    }


    /**
     * @param name is the input name of this BasicCriterion
     * @return the boolean value to check whether this is a legal name
     */
    public static boolean isLegalName (String name) {
        // validating criterion name

        if(CriteriaStorage.isExisting(name)){
            System.out.println("Criterion Existed");
            return false;
        }

        if((name.charAt(0)>='0' && name.charAt(0)<='9') || name.length()>8){
            System.out.println("Illegal Criterion Name");
            return false;
        }

        for(int i=0; i<name.length(); i++){
            if((name.charAt(i)<'a'|| name.charAt(i)>'z')&&
                    (name.charAt(i)<'A'|| name.charAt(i)>'Z')&&
                    (name.charAt(i)<'0'|| name.charAt(i)>'9')) {
                System.out.println("Illegal Criterion Name");
                return false;
            }
        }
        return true;
    }

    /**
     * @param property_name is name of property of BasicCriterion, either name, description, duration, prerequisites, or subtasks
     * @return the boolean value to check whether this is a legal property_name
     */
    public static boolean isLegalPropertyName (String property_name){
        // validating property_name
        return (Objects.equals(property_name, "name") || Objects.equals(property_name, "description") ||
                Objects.equals(property_name, "duration") || Objects.equals(property_name, "prerequisites") || Objects.equals(property_name, "subtasks"));
    }


    /**
     * @param property_name the input property_name, since the checking of op are value related to property_name
     * @param op the operator
     * @param value the value given property_name
     * @return the boolean value to check whether this is a legal (operator, value) pair
     */
    public static boolean isLegalOpANDValue (String property_name, String op, String value) {
        if ( !isLegalPropertyName(property_name) )
            return false;

        // validating op and value (associated with the property_name)

        if (property_name.equals("name") || property_name.equals("description")) {

            if (value.length()<=2 || value.charAt(0) != '\"' || value.charAt(value.length()-1) != '\"')
                return false;
            else return Objects.equals(op, "contains");
        }

        else if (property_name.equals("duration")) {
            if (!(Objects.equals(op, ">") || Objects.equals(op, "<") ||
                    Objects.equals(op, ">=") || Objects.equals(op, "<=") ||
                    Objects.equals(op, "==") || Objects.equals(op, "!=")))
                return false;
            else
                try {
                    double temp = Double.parseDouble(value);
                    if (temp <= 0) {
                        System.out.println("Illegal Range of Duration");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Illegal Duration Input");
                    return false;
                }
        }
        else {
            if (!Objects.equals(op, "contains"))
                return false;
            else if (value.isEmpty()) {
                System.out.println("Empty prerequisites/subtasks list must be denoted as a single comma");
                return false;
            }
        }
        return true;
    }

    /**
     * @param command a String array of the input command (content after the keyword DefineBasicCriterion)
     * @return the boolean value to check whether this is a legal input to define a new BasicCriterion
     */
    public static boolean isLegal(String[] command) {
        if (command.length!=4) return false;
        for (String i : command ) {
            if (i==null)
                return false;
        }

        String name = command[0];
        String property_name = command[1];
        String op = command[2];
        String value = command[3];

        return isLegalName(name) && isLegalPropertyName(property_name) && isLegalOpANDValue(property_name, op, value);
    }

    @Override
    public void printCriteria () {

        System.out.print(this.getName() + " " + this.getProperty_name() + " " + this.getOp() + " ");
        switch (this.getProperty_name()) {
            case "name" : {
                System.out.print(this.getValue().getName());
                break;}
            case "description" : {
                System.out.print(this.getValue().getDescription());
                break;}
            case "duration" : {
                System.out.print(this.getValue().getDuration());
                break;}
            case "prerequisites" : {
                if (this.getValue().getPrerequisites() == null)
                    System.out.print(",");
                else
                    System.out.print(this.getValue().getPrerequisites());
                break;
            }
            case "subtasks" : {
                if (this.getValue().getSubtasks() == null)
                    System.out.print(",");
                else
                    System.out.print(this.getValue().getSubtasks());
                break;
            }

        }

    }

    @Override
    public boolean isMatching(Task t) {

            boolean temp = false;
            switch (this.getProperty_name()) {
                case ("name") : {
                    temp = t.getName().contains(this.getValue().getName());
                    if (Objects.equals(this.getOp(), "contains") )
                        return temp;
                    else
                        return !temp;

                }
                case ("description") : {
                    temp = t.getDescription().contains(this.getValue().getDescription());
                    if (Objects.equals(this.getOp(), "contains") )
                        return temp;
                    else
                        return !temp;

                }
                case ("duration") : {
                    switch (this.getOp()) {
                        case (">") : {
                            return t.getDuration() > this.getValue().getDuration();
                        }
                        case ("<") : {
                            return t.getDuration() < this.getValue().getDuration();
                        }
                        case (">=") : {
                            return t.getDuration() >= this.getValue().getDuration();
                        }
                        case ("<=") : {
                            return t.getDuration() <= this.getValue().getDuration();
                        }
                        case ("==") : {
                            return t.getDuration() == this.getValue().getDuration();
                        }
                        case ("!=") : {
                            return t.getDuration() != this.getValue().getDuration();
                        }
                    }
                }
                case ("prerequisites") : {
                    // Only PrimitiveTask has prerequisites
                    if (t instanceof CompositeTask) return false;
                    LinkedList<Task> cList = new LinkedList<Task>();
                    LinkedList <Task> tList = ((PrimitiveTask) t).getDirectPrerequisite();
                    tList.addAll(((PrimitiveTask) t).getIndirectPrerequisite());

                    // For this special case, we define "when both criterion and task have empty prerequisites list"
                    // contains is true for all tasks (because empty set is the subset of any set)
                    // !contains (negation) is false for all tasks
                    if(this.getValue().getPrerequisites().isEmpty() && tList.isEmpty()) return Objects.equals(this.getOp(), "contains");

                    for (String pre : this.getValue().getPrerequisites()) {
                        if (!TMS.taskExist(pre)) return false;
                        cList.add(TMS.getTask(pre));
                    }

                    temp = tList.containsAll(cList);

                    if (Objects.equals(this.getOp(), "contains") )
                        return temp;
                    else
                        return !temp;
                }
                case ("subtasks") : {
                    // Only CompositeTask has subtasks
                    if (t instanceof PrimitiveTask) return false;
                    LinkedList <Task> cList = new LinkedList<Task>();
                    for (String sub : this.getValue().getSubtasks()) {
                        if (!TMS.taskExist(sub)) return false;
                        cList.add(TMS.getTask(sub));
                    }
                    temp = ((CompositeTask) t).getDirectSubtask().containsAll(cList);
                    if (Objects.equals(this.getOp(), "contains") )
                        return temp;
                    else
                        return !temp;
                }


        }
        return false;
    }

}
