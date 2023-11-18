package hk.edu.polyu.comp.comp2021.tms.controller;
import hk.edu.polyu.comp.comp2021.tms.model.TMS;
import hk.edu.polyu.comp.comp2021.tms.view.GUIViewer;

import java.util.Scanner;

/*
这个java文件就是用来测试的最后再搬回TMSController
 */
public class TestController {

    public static void main(String[] args) {
        TMS tms = new TMS();
        GUIViewer GUI = new GUIViewer(tms);
        Scanner scanner = new Scanner(System.in);
        String[] keywords;
        while(true) {
            keywords = scanner.nextLine().split(" ");
            if(keywords.length==0){
                System.out.println("Invalid Operation Name");
                continue;
            }
            if (keywords[0].equals("exit")&&keywords.length==1) break;
            switch(keywords[0]){
                case "CreatePrimitiveTask":
                    tms.CreatePrimitiveTask(keywords);
                    break;
                case "CreateCompositeTask":
                    tms.CreateCompositeTask(keywords);
                    break;
                case "GUI":
                    if(keywords.length>1)break;
                    GUI.display(true);
                    System.out.println("Please Click Window Manually If Not Popped Up");
                    break;
                case "command":
                    GUI.display(false);
                    break;
                default:
                    System.out.println("Invalid Operation Name");
                    break;
            }
        }
    }
}
