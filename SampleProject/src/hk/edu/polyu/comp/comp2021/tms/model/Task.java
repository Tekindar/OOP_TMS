package hk.edu.polyu.comp.comp2021.tms.model;

abstract class Task {
    String name;
    String description;
    double duration;
    double completion;
    String[] prerequisite;

    Task(){}

}