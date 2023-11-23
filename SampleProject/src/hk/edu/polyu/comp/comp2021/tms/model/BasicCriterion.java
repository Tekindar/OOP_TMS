package hk.edu.polyu.comp.comp2021.tms.model;



import java.util.LinkedList;
import java.util.Objects;

public class BasicCriterion extends Criterion  {

    /*
    @p
     */
    BasicCriterion (String name, String property_name, String op, String value){
        this.setName(name);
        this.property_name = property_name;
        this.op=op;
        this.setProperty(new Property(property_name, value));
    }
    BasicCriterion (String name, String property_name, String op, Property property){
        this.setName(name);
        this.property_name = property_name;
        this.op=op;
        this.setProperty(property);
    }


    public String negatedOp () {
         switch (op) {
            case (">") : return "<";
            case ("<") : return ">";
             case (">=") : return "<=";
             case ("<=") : return ">=";
             case ("==") : return "!=";
             case ("!=") : return "==";
             case ("contains") : return "!contains";
        }
         return null;
    }


    // FOR TESTING ONLY
    public static void main (String []args) {
        BasicCriterion c0 = new BasicCriterion("a", "name", "=", "abc");
        System.out.println(c0.getProperty().getName());


    }

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

    public static boolean isLegalPropertyName (String property_name){
        // validating property_name
        return (Objects.equals(property_name, "name") || Objects.equals(property_name, "description") ||
                Objects.equals(property_name, "duration") || Objects.equals(property_name, "prerequisites") || Objects.equals(property_name, "subtasks"));
    }

    public static boolean isLegalOpANDValue (String property_name, String op, String value) {
        if ( !isLegalPropertyName(property_name) )
            return false;

        // validating op and value (associated with the property_name)

        if (property_name.equals("name") || property_name.equals("description")) {

            if (value.charAt(0) != '\"' || value.charAt(value.length()-1) != '\"')
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
                    temp = t.name.contains(this.getValue().getName());
                    if (Objects.equals(this.op, "contains") )
                        return temp;
                    else
                        return !temp;

                }
                case ("description") : {
                    temp = t.description.contains(this.getValue().getDescription());
                    if (Objects.equals(this.op, "contains") )
                        return temp;
                    else
                        return !temp;

                }
                case ("duration") : {
                    switch (this.op) {
                        case (">") : {
                            return t.duration > this.getValue().getDuration();
                        }
                        case ("<") : {
                            return t.duration < this.getValue().getDuration();
                        }
                        case (">=") : {
                            return t.duration >= this.getValue().getDuration();
                        }
                        case ("<=") : {
                            return t.duration <= this.getValue().getDuration();
                        }
                        case ("==") : {
                            return t.duration == this.getValue().getDuration();
                        }
                        case ("!=") : {
                            return t.duration != this.getValue().getDuration();
                        }
                    }
                }
                case ("prerequisites") : {
                    // Only PrimitiveTask has prerequisites
                    if (t instanceof CompositeTask) return false;
                    LinkedList<Task> cList = new LinkedList<Task>();
                    LinkedList <Task> tList = ((PrimitiveTask) t).prerequisite;
                    tList.addAll(((PrimitiveTask) t).IndirectPrerequisite);

                    // For this special case, we define "when both criterion and task have empty prerequisites list"
                    // contains is true for all tasks (because empty set is the subset of any set)
                    // !contains (negation) is false for all tasks
                    if(this.getValue().getPrerequisites().isEmpty() && tList.isEmpty()) return Objects.equals(this.op, "contains");

                    for (String pre : this.getValue().getPrerequisites()) {
                        if (!TMS.taskExist(pre)) return false;
                        cList.add(TMS.getTask(pre));
                    }

                    temp = tList.containsAll(cList);

                    if (Objects.equals(this.op, "contains") )
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
                    temp = ((CompositeTask) t).subtask.containsAll(cList);
                    if (Objects.equals(this.op, "contains") )
                        return temp;
                    else
                        return !temp;
                }


        }
        return false;
    }

}
