package hk.edu.polyu.comp.comp2021.tms.controller;

/**
 * The functional interface Search will be implemented in CriteriaStorage Class so that
 * the search function could be realized with given criteria and all existing tasks
 */
public interface Search {
    /**
     * @param command is command of user input except of keyword ("Search")
     */
    void search (String []command);
}
