package hk.edu.polyu.comp.comp2021.tms.controller;
import hk.edu.polyu.comp.comp2021.tms.model.TMS;

import java.util.Scanner;

public class TestController {

    private static boolean CSTValidation(String[] keywords){
        // validating name
        String key = keywords[1];
        if(key.isEmpty()||key.charAt(0)>='0'&&key.charAt(0)<='9'||key.length()>8)
            return false;
        for(int i=0;i<key.length();i++){
            if((key.charAt(i)<'a'||key.charAt(i)>'z')&&
               (key.charAt(i)<'A'||key.charAt(i)>'Z')&&
               (key.charAt(i)<'0'||key.charAt(i)>'9')) return false;
        }

        // validating description
        key = keywords[2];
        if(key.isEmpty()) return false;
        for(int i=0;i<key.length();i++){
            if((key.charAt(i)<'a'||key.charAt(i)>'z')&&
               (key.charAt(i)<'A'||key.charAt(i)>'Z')&&
               (key.charAt(i)<'0'||key.charAt(i)>'9')&&
                key.charAt(i)!='-') return false;
        }

        // validating duration
        key = keywords[3]; // reject negative number, reject non-number
        try{
            double temp = Double.parseDouble(key);
            if(temp<=0) return false;
        }catch(Exception e){
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        TMS tms = new TMS();
        Scanner scanner = new Scanner(System.in);
        String[] keywords = {"","","",""};
        while(true) {
            String input = scanner.nextLine();
            if (input.equals("exit")) break;
            int i=0;
            for (String key : input.split(" ")) keywords[i++]=key;
            switch(keywords[0]){
                case "CreateSimpleTask":
                    if(CSTValidation(keywords)){
                        tms.CreateSimpleTask(keywords);
                    }else System.out.println("Input Error");
                    break;
                default:
                    break;
            }
        }
    }
}
