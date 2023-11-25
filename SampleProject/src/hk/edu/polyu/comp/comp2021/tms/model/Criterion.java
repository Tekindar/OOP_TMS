package hk.edu.polyu.comp.comp2021.tms.model;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * Criterion is the superclass of both BasicCriterion and BinaryCriterion to achieve polymorphism
 * Since Criterion doesn't need to be instantiated, we design it as an abstract class including abstract methods
 * For example, abstract methods printCriteria and isMatching are overridden in BasicCriterion and BinaryCriterion to facilitate the different operations
 */
abstract public class Criterion {
    private String name;
    private String property_name;
    private Property property;
    private String op;


    /**
     * @param property the Property of an existing criterion.
     * This method is used to define NegatedCriterion of an existing BasicCriterion (same value). This is actually the deep copy of property field.
     */
    public void setProperty(Property property) {
        this.property = new Property(getProperty_name(), property.getResult());
    }

    /**
     * @param name the name of this Criterion
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param property_name the property_name of this Criterion, either name, description, duration, prerequisites, or subtasks
     */
    public void setProperty_name(String property_name) {
        this.property_name = property_name;
    }
    /**
     * @param op the operator of this (Basic)Criterion
     */
    public void setOp(String op) {
        this.op = op;
    }


    /**
     *  This nested static class Property is used to define the criterion value associated with the property name
     *  For example, DefineBasicCriterion c1 name contains "A", here the property_name "name" determined the value type ("A")
     *  Since that the value could be different types determined by different property_name, this class only explicitly initialized the
     *  field with defined property_name.
     *  Also, the result will record the passed value to facilitate store/load/undo/redo operations
     */
    public static class Property {

        /**
         * @param property_name The name of property, either name, description, duration, prerequisites, or subtasks
         * @param value The value of corresponding property_name
         */
        Property (String property_name, String value){
            this.result = value;
            switch (property_name) {
                case "name" : {
                    this.setName(value);
                    break;}
                case "description" : {
                    this.setDescription(value);
                    break;}
                case "duration" :{
                    this.setDuration(Double.parseDouble(value));
                    break;}
                case "prerequisites" : {
                    setPrerequisites(new LinkedHashSet<>());
                    if (!Objects.equals(value, ","))
                        getPrerequisites().addAll(Arrays.asList(value.split(",")));
                    break;
                }
                case "subtasks" : {
                    setSubtasks(new LinkedHashSet<>());
                    if (!Objects.equals(value, ","))
                        getSubtasks().addAll(Arrays.asList(value.split(",")));
                    break;
                }
            }
        }
        private String name;
        private String description;
        private LinkedHashSet<String> subtasks;
        private double duration;
        private LinkedHashSet<String> prerequisites;

        private final String result;

        /**
         * @return the value of the property according to its specification
         */
        public String getResult() {return this.result;}

        /**
         * @return The name of this Criterion
         */
        public String getName() {
            return name;
        }

        /**
         * @param taskName The task name value of a BasicCriterion
         *                  Here the taskName must contain characters within "" (i.e., "" will not be passed in this method).
         */
        public void setName(String taskName) {
            this.name = taskName.substring(1, taskName.length()-1);
        }


        /**
         * @return The description value to be searched
         */
        public String getDescription() {
            return description;
        }


        /**
         * @param taskDescription The task description value of a BasicCriterion
         *                        Here the taskDescription must contain characters within "" (i.e., "" will not be passed in this method).
         */
        public void setDescription(String taskDescription) {
            this.description = taskDescription.substring(1, taskDescription.length()-1);
        }

        /**
         * @return The duration value to be searched
         */
        public double getDuration() {
            return duration;
        }

        /**
         * @param duration  The (double) duration value of a BasicCriterion
         */

        public void setDuration(double duration) {
            this.duration = duration;
        }


        /**
         * @return The prerequisites LinkedHashSet to be searched
         */
        public LinkedHashSet<String> getPrerequisites() {
            return prerequisites;
        }

        /**
         * @param prerequisites  The prerequisites LinkedHashSet (to eliminate the duplicates) value of a BasicCriterion
         */
        public void setPrerequisites(LinkedHashSet<String> prerequisites) {
            this.prerequisites = prerequisites;
        }

        /**
         * @return The subtasks LinkedHashSet to be searched
         */
        public LinkedHashSet<String> getSubtasks() {
            return subtasks;
        }

        /**
         * @param subtasks  The subtasks LinkedHashSet (to eliminate the duplicates) value of a BasicCriterion
         */
        public void setSubtasks(LinkedHashSet<String> subtasks) {
            this.subtasks = subtasks;
        }
    }


    /**
     * @return the name of this BasicCriterion
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the property_name of this BasicCriterion
     */
    public String getProperty_name() {
        return this.property_name;
    }

    /**
     * @return the property (value) of this BasicCriterion
     */

    public Property getProperty() {
        return property;
    }

    /**
     * @return the operator of this BasicCriterion
     */
    public String getOp() {
        return this.op;
    }

    /**
     * @return the value of input value String
     */

    public Property getValue() {
        return getProperty();
    }

    /**
     *  Print the criterion of either BasicCriterion, BinaryCriterion, or IsPrimitive (Singleton)
     *  Implemented respectively
     */
    abstract public void printCriteria ();

    /**
     * @param t the task object to be checked
     * @return the boolean value of matching-check result
     * check whether the task t is matched with this criterion
     * Implemented in BasicCriterion, BinaryCriterion, and IsPrimitive (Singleton)
     */
    abstract public boolean isMatching (Task t);

}
