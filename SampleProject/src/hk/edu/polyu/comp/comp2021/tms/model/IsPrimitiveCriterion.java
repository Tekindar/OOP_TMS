package hk.edu.polyu.comp.comp2021.tms.model;

public class IsPrimitiveCriterion extends Criterion{
    private static IsPrimitiveCriterion singleton;

    private IsPrimitiveCriterion (){
         name = "isPrimitive";
    }

    public static IsPrimitiveCriterion getSingleton(){
        if (singleton == null) {
            singleton = new IsPrimitiveCriterion();
        }
        return singleton;
    }

}
