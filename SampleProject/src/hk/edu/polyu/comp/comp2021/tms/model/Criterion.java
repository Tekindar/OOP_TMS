package hk.edu.polyu.comp.comp2021.tms.model;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;

abstract public class Criterion {
    private String name;
    protected String property_name;
    private Property property;
    protected String op;

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Property {
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getDuration() {
            return duration;
        }

        public void setDuration(double duration) {
            this.duration = duration;
        }

        public LinkedHashSet<String> getPrerequisites() {
            return prerequisites;
        }

        public void setPrerequisites(LinkedHashSet<String> prerequisites) {
            this.prerequisites = prerequisites;
        }

        public LinkedHashSet<String> getSubtasks() {
            return subtasks;
        }

        public void setSubtasks(LinkedHashSet<String> subtasks) {
            this.subtasks = subtasks;
        }
    }

    public String getName() {
        return this.name;
    }


    public String getProperty_name() {
        return this.property_name;
    }


    public String getOp() {
        return this.op;
    }


    public Property getValue() {
        return getProperty();
    }

    abstract public void printCriteria ();

    /**
     * @param t the task object to be checked
     * @return the boolean value of matching-check result
     */
    abstract public boolean isMatching (Task t);

}
