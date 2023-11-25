package hk.edu.polyu.comp.comp2021.tms.controller;

import hk.edu.polyu.comp.comp2021.tms.view.*;
import hk.edu.polyu.comp.comp2021.tms.model.CriteriaStorage;
import hk.edu.polyu.comp.comp2021.tms.model.TMS;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The controller class, works as an intermedia between user and model,
 * it receives input strings and translates into command that
 * calls for operation in the model.
 */
public class TMSController {

    /**
     * The system programme begins to run here.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        TMS tms = new TMS();
        CriteriaStorage cs = new CriteriaStorage();
        Scanner scanner = new Scanner(System.in);
        String[] keywords;
        String[] content;
        while(true) {
            keywords = scanner.nextLine().split(" ");
            content = Arrays.copyOfRange(keywords, 1, keywords.length);
            if(keywords.length==0){
                System.out.println ("Invalid Operation Name");
                continue;
            }
            else if ((keywords[0].equals("quit")||keywords[0].equals("exit"))&&keywords.length==1) break;

            switch(keywords[0]){
                case "CreatePrimitiveTask":
                    tms.CreatePrimitiveTask(keywords);
                    break;
                case "CreateCompositeTask":
                    tms.CreateCompositeTask(keywords);
                    break;
                case "DeleteTask" :
                    tms.DeleteTask(keywords);
                    break;
                case "ChangeTask" :
                    tms.ChangeTask(keywords);
                    break;
                case "PrintTask" :
                    tms.printTask(keywords, true);
                    break;
                case "PrintAllTasks" :
                    tms.printTask(keywords, false);
                    break;
                case "ReportDuration" :
                    tms.reportDuration(keywords);
                    break;
                case "ReportEarliestFinishTime":
                    tms.reportEarliestFinishTime(keywords);
                    break;
                case "DefineBasicCriterion" :
                    cs.createBasicCriterion(content);
                    break;
                case "DefineNegatedCriterion" :
                    cs.createNegatedCriterion(content);
                    break;
                case "DefineBinaryCriterion" :
                    cs.createBinaryCriterion(content);
                    break;
                case "PrintAllCriteria":
                    Print_Criteria.printAllCriteria();
                    break;
                case "Search":
                    cs.search(content);
                    break;
                case "Store":
                    if(keywords.length!=2)
                        System.out.println("Invalid Operation Name");
                    else {
                        try {
                            tms.storeTask(keywords);
                        } catch (IOException ignored) {
                        }
                    }
                    break;
                case "Load" :
                    if(keywords.length!=2)
                        System.out.println("Invalid Operation Name");
                    else {
                        try {tms.FileLoad(keywords);}
                        catch (IOException ignored) {}
                    }
                    break;
                case "Quit":
                    if(keywords.length==1) System.exit(0);
                    System.out.println("Invalid Operation Name");
                    break;
                default:
                    System.out.println("Invalid Operation Name");
                    break;
            }
        }
    }
}
