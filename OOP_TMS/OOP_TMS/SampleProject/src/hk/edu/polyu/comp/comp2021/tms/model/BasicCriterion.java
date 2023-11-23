package hk.edu.polyu.comp.comp2021.tms.model;



import java.util.Objects;

public class BasicCriterion extends Criterion  {
    BasicCriterion (String name, String property_name, String op, String value){
        this.name = name;
        this.property_name = property_name;
        this.property = new Property(property_name, value);
        this.op=op;
    }

    BasicCriterion (String name, Property p, String op){
        this.name = name;
        this.property = p;
        this.op=op;
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
        System.out.println(c0.property.name);


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


}
