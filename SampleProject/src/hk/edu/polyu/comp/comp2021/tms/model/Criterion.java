package hk.edu.polyu.comp.comp2021.tms.model;

public class Criterion {
    String name;
    String property_name;
    Property property;
    String op;
    public static class Property {
        Property (String property_name, String value){
            switch (property_name) {
                case "name" -> this.name = value;
                case "description" -> this.description = value;
                case "duration" -> this.duration = Double.parseDouble(value);
                case "prerequisites" -> this.prerequisites = value.split(",");
                case "subtasks" -> this.subtasks = value.split(",");
            }
        }
        String name;
        String description;
        String[] subtasks;
        double duration;
        String[] prerequisites;

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
        return property;
    }



}
