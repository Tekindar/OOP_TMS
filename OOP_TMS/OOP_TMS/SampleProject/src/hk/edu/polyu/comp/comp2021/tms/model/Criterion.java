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
                case "name" : {
                    this.name = value;
                    this.result = value;
                    break;}
                case "description" : {
                    this.description = value;
                    this.result = value;
                    break;}
                case "duration" :{
                    this.duration = Double.parseDouble(value);
                    this.result = value;
                    break;}
                case "prerequisites" : {
                    prerequisites = new LinkedHashSet<>();
                    if (Objects.equals(value, ","))
                        prerequisites.add("");
                    else{
                        prerequisites.addAll(Arrays.asList(value.split(",")));}
                    this.result = value;
                    break;
                }
                case "subtasks" : {
                    subtasks = new LinkedHashSet<>();
                    if (Objects.equals(value, ","))
                        subtasks.add("");
                    else{
                        subtasks.addAll(Arrays.asList(value.split(",")));}
                    this.result = value;
                    break;
                }
            }
        }
        private String result;
        String name;
        String description;
        LinkedHashSet<String> subtasks;
        double duration;
        LinkedHashSet<String> prerequisites;

        public String getResult() {
            return result;
        }
        public String getResult(String pname) {
            switch (pname) {
                case "name" : {
                    return name;
                }
                case "description" : {
                    return description;
                }
                case "duration" :{
                    return String.valueOf(duration);
                }
                case "prerequisites" : {
                    prerequisites = new LinkedHashSet<>();
                    if (Objects.equals(value, ","))
                        prerequisites.add("");
                    else{
                        prerequisites.addAll(Arrays.asList(value.split(",")));}
                    this.result = value;
                    break;
                }
                case "subtasks" : {
                    subtasks = new LinkedHashSet<>();
                    if (Objects.equals(value, ","))
                        subtasks.add("");
                    else{
                        subtasks.addAll(Arrays.asList(value.split(",")));}
                    this.result = value;
                    break;
                }
            }
            return result;
        }
        /**
         * @return the value of the property accoring to its specification
         */

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
