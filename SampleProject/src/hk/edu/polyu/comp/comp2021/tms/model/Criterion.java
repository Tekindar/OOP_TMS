package hk.edu.polyu.comp.comp2021.tms.model;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;

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
                case "prerequisites" -> {
                    prerequisites = new LinkedHashSet<>();
                    if (Objects.equals(value, ","))
                        prerequisites.add("");
                    else
                        prerequisites.addAll(Arrays.asList(value.split(",")));
                }
                case "subtasks" -> {
                    subtasks = new LinkedHashSet<>();
                    if (Objects.equals(value, ","))
                        subtasks.add("");
                    else
                        subtasks.addAll(Arrays.asList(value.split(",")));
                }
            }
        }
        String name;
        String description;
        LinkedHashSet<String> subtasks;
        double duration;
        LinkedHashSet<String> prerequisites;

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
