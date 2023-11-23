package hk.edu.polyu.comp.comp2021.tms.model;

public final class IsPrimitiveCriterion extends Criterion{
    private static IsPrimitiveCriterion singleton;

    private IsPrimitiveCriterion (){
         setName("isPrimitive");
    }

    public static IsPrimitiveCriterion getSingleton(){
        if (singleton == null) {
            singleton = new IsPrimitiveCriterion();
        }
        return singleton;
    }

    @Override
    public void printCriteria() {
        System.out.print("IsPrimitive");
    }

    @Override
    public boolean isMatching(Task t) {
        return t instanceof PrimitiveTask;
    }
}
