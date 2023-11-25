package hk.edu.polyu.comp.comp2021.tms.view;

import hk.edu.polyu.comp.comp2021.tms.model.CriteriaStorage;
import hk.edu.polyu.comp.comp2021.tms.model.Criterion;

/**
 * Print all the defined criteria, including name, property_name, value, logic operator/operator, IsPrimitive
 */
public class Print_Criteria  {

    /**
     * Use dynamic type of Criterion c to call the polymorphic getCriteria() implementations
     */
    public static void printAllCriteria (){
        for (Criterion c : CriteriaStorage.getCriteria()) {
            c.printCriteria();
            System.out.println();
        }
    }
}