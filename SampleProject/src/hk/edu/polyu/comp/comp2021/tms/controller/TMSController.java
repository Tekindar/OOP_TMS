package hk.edu.polyu.comp.comp2021.tms.controller;

import hk.edu.polyu.comp.comp2021.tms.model.CriteriaStorage;
import hk.edu.polyu.comp.comp2021.tms.model.TMS;

import java.util.Arrays;
import java.util.Scanner;


public class TMSController {

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
                    break;
                case "ChangeTask" :
                    break;
                case "PrintTask" :
                    break;
                case "PrintAllTasks" :
                    break;
                case "ReportDuration" :
                    break;
                case "ReportEarliestFinishTime":
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
                    cs.printAllCriteria();
                    break;
                case "Search":
                    cs.search(content);
                    break;
                case "Store":
                    if(keywords.length!=2||!keywords[1].equals("Path"))
                        System.out.println("Invalid Operation Name");
                    else
                        break;
                case "Load" :
                    if(keywords.length!=2||!keywords[1].equals("Path"))
                        System.out.println("Invalid Operation Name");
                    else
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
