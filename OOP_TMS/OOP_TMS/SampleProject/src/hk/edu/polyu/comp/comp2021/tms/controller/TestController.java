package hk.edu.polyu.comp.comp2021.tms.controller;
import hk.edu.polyu.comp.comp2021.tms.model.CriteriaStorage;
import hk.edu.polyu.comp.comp2021.tms.model.File_storage;
import hk.edu.polyu.comp.comp2021.tms.model.Print_Task;
import hk.edu.polyu.comp.comp2021.tms.model.TMS;
import hk.edu.polyu.comp.comp2021.tms.view.GUIViewer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/*
这个java文件就是用来测试的最后再搬回TMSController
 */
public class TestController {

    static boolean isGUI = false; // whether user using gui
    public static void main (String[] args) throws IOException {
        TMS tms = new TMS();
        CriteriaStorage cs = new CriteriaStorage();
        GUIViewer GUI = new GUIViewer(tms);
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
                    tms.CreatePrimitiveTask(keywords,false);
                    break;
                case "CreateCompositeTask":
                    tms.CreateCompositeTask(keywords,false);
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
                case "GUI":
                    if(keywords.length>1)break;
                    isGUI = true;
                    GUI.display(true);
                    System.out.println("Please Click Window Manually If Not Popped Up");
                    break;
                case "command":
                    isGUI = false;
                    GUI.display(false);
                    break;
                case "Print":
                    tms.printTask(keywords,true);
                    break;
                case "PrintAllTasks":
                    tms.printTask(keywords,false);
                    break;
                case "Store":
                    tms.storeTask(keywords);
                    break;
                case "Load":
                    tms.FileLoad(keywords);
                    break;
                case "DeleteTask":
                    tms.DeleteTask(keywords);
                    break;
                default:
                    System.out.println("Invalid Operation Name");
                    break;
            }
        }
    }
    public static boolean getIsGUI(){
        return isGUI;
    }
}
