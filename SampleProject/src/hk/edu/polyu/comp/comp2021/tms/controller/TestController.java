package hk.edu.polyu.comp.comp2021.tms.controller;
import hk.edu.polyu.comp.comp2021.tms.model.TMS;
import java.util.Scanner;

/*
这个java文件就是用来测试的最后再搬回TMSController
 */
public class TestController {

    static String[] blankKeys = {"","","","",""};


    public static void main(String[] args) {
        TMS tms = new TMS();
        Scanner scanner = new Scanner(System.in);
        String[] keywords;
        while(true) {
            keywords = blankKeys.clone();
            String input = scanner.nextLine();
            if (input.equals("exit")) break;
            int i=0;
            for (String key : input.split(" ")) keywords[i++]=key;
            switch(keywords[0]){
                case "CreateSimpleTask":
                    tms.CreateSimpleTask(keywords);
                    break;
                case "CreateCompositeTask":

                    break;
                default:
                    System.out.println("Invalid Operation Name");
                    break;
            }
        }
    }
}
