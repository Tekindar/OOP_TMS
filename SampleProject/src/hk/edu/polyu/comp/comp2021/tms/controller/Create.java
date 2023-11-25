package hk.edu.polyu.comp.comp2021.tms.controller;

/**
 * The definition of criteria includes createBasicCriterion, createNegatedCriterion, and createBinaryCriterion
 * These three abstract methods will be implemented in CriteriaStorage Class
 */
public interface Create {
    /**
     * @param command The input command except for keyword "DefineBasicCriterion"
     */
    void createBasicCriterion (String []command);
    /**
     * @param command The input command except for keyword "DefineNegatedCriterion"
     */
    void createNegatedCriterion (String []command);
    /**
     * @param command The input command except for keyword "DefineBinaryCriterion"
     */
    void createBinaryCriterion (String []command);
}
