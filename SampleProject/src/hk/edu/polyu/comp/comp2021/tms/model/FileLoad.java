package hk.edu.polyu.comp.comp2021.tms.model;

import java.io.*;
import java.util.Arrays;

/**
 * This class is responsible for the loading of file
 */
public class FileLoad {
    /**
     * This method will clear all the tasks and criterions stored now.
     */
    public static void clear() {
        CriteriaStorage.clearCriteria();
        TMS.getAllTasks().clear();
        CriteriaStorage.getCriteria().add(IsPrimitiveCriterion.getSingleton());
    }

    /**
     * @param k is the user input.
     * @throws IOException because reading file may falied
     * This method will load the data in file. After doing a simple check to the command,
     * the variables are initialized. Then, file.exist() will check whether the path is existing.
     * If there is a file, it will load all contents in the file. Since the data are at most four
     * commands, it is similiar to the code in controller. The code just read command one by one
     * and create task or criterion accordingly.
     */
    public static void load(String[] k) throws IOException {
        if (k.length > 2) {
            System.out.println("Wrong input length");
            return;
        }
        String p = k[1];
        String content = "";
        File file = new File(p);
        if (!file.exists()) {
            System.out.println("File does not exist");
        } else {
            InputStreamReader isd = new InputStreamReader(new FileInputStream(file));
            BufferedReader bd = new BufferedReader(isd);
            TMS tm = new TMS();
            CriteriaStorage c = new CriteriaStorage();
            String[] keywords,contents;
            while ((content = bd.readLine()) != null) {
                keywords = content.split(" ");
                contents = Arrays.copyOfRange(keywords, 1, keywords.length);
                switch (keywords[0]) {
                    case "CreatePrimitiveTask":
                        tm.CreatePrimitiveTask(keywords);
                        break;
                    case "CreateCompositeTask":
                        tm.CreateCompositeTask(keywords);
                        break;
                    case "DefineBasicCriterion":
                        c.createBasicCriterion(contents);
                        break;
                    case "DefineBinaryCriterion":
                        c.createBinaryCriterion(contents);
                        break;
                    default:
                        System.out.println("The contents in the data file cannot be loaded");
                        break;
                }

            }
            System.out.println("Loading completed");
        }

    }
}
