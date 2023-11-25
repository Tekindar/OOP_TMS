package hk.edu.polyu.comp.comp2021.tms.model;

/**
 * IsPrimitive is a singleton design, because this special criterion is not defined by users
 * User could input command Search IsPrimitive to check the primitive tasks directly.
 */
public final class IsPrimitiveCriterion extends Criterion{
    private static IsPrimitiveCriterion singleton;

    private IsPrimitiveCriterion (){
         setName("isPrimitive");
    }

    /**
     * @return if there is no instance of IsPrimitive, create and return the reference of it
     * otherwise return the existing reference
     */
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
