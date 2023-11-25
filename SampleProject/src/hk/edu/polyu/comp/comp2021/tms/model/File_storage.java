package hk.edu.polyu.comp.comp2021.tms.model;

import java.io.*;
import java.util.*;

/**
 * This class is used for storing data into a file. In the class, a list is used for storing hashmaps
 * and hashmaps are used for storing data. According to the information of tasks and criteria, its command
 * of creation can be derived. Then we store these commmands into hashmaps. The reason why store the state
 * of the system in this way is decreasing the data size that need to be transfered. What's more, it can be
 * used to realize the function of undo efficiently. Since storing the state will only use a hashmap, the search
 * is very fast and data size is also decreased.
 */
public class File_storage {
    //The list will store all the hashmaps and be initialized as the command store call in each time
    private static List<Map<String, String>> list;

    // The field exist is used for checking wheather the "Store" command is used already.
    private static boolean exist = false;

    /**
     * This method read all information about tasks and convert them into the creation command
     * then store the command part as key and store information as value
     */
    private static void storeTasks() {
        for (Task t : TMS.getAllTasks()) {
            Map<String, String> map = new HashMap<>();
            if (t instanceof CompositeTask) {
                String temp = "";
                for (Task a : ((CompositeTask) t).getDirectSubtask()) {
                    temp = temp + "," + a.getName();
                }
                map.put("CreateCompositeTask", t.getName() + " " + t.getDescription() + " " + temp.substring(1));
                list.add(map);
            } else {
                String temp = "";
                if (((PrimitiveTask) t).getDirectPrerequisite().isEmpty()) {
                    map.put("CreatePrimitiveTask", t.getName() + " " + t.getDescription() + " " + t.getDuration() + " ,");
                    list.add(map);
                } else {
                    for (Task b : ((PrimitiveTask) t).getDirectPrerequisite()) {
                        temp = temp + "," + b.getName();
                    }
                    map.put("CreatePrimitiveTask", t.getName() + " " + t.getDescription() + " " + t.getDuration() + " "
                            + temp.substring(1));
                    list.add(map);
                }
            }
        }
    }

    /**
     * Very similar to above method while in the form of Criteria
     */

    private static void storeCriteria() {
        for (Criterion t : CriteriaStorage.getCriteria()) {
            if (t instanceof IsPrimitiveCriterion) {
                continue;
            }
            Map<String, String> map = new HashMap<>();
            if (t instanceof BasicCriterion) {
                map.put("DefineBasicCriterion", t.getName() + " " + t.getProperty_name()
                        + " " + t.getOp() + " " + t.getValue().getResult());
                list.add(map);
            } else {
                Criterion[] m = ((BinaryCriterion) t).getDualCriteria();
                map.put("DefineBinaryCriterion", t.getName() + " " + m[0].getName() + " "
                        + ((BinaryCriterion) t).getLogicOp() + " " + m[1].getName());
                list.add(map);
            }
        }
    }

    private static boolean deleteFile(File f) {
        System.out.print("Enter yes for overwriting the current file: ");
        Scanner s = new Scanner(System.in);
        String t = s.nextLine();
        if (t.equals("yes")) {
            f.delete();
            return true;
        }
        return false;
    }

    /**
     * @param c is the user input
     * @throws IOException due to the output file stream
     * This method call all above methods to store the data in the form of create command
     * It will read the maps in the list and use output stream writer write commands into file
     */
    public static void store(String[] c) throws IOException {
        list = new ArrayList<>();
        storeTasks();
        storeCriteria();
        //
        try {
            String p = c[1];
            File file = new File(p);
            FileOutputStream fos = null;
            if (file.exists() && !exist) {
                System.out.println("An existing file is detected at the given path");
                if (!deleteFile(file)) {
                    System.out.println("Please enter a valid path");
                    return;
                }
            }
            if (!file.exists()) {
                exist = true;
                if (file.createNewFile()) System.out.println("File has been created");
                else System.out.println("File creation falied");
            } else {
                System.out.println("File has been updated");
            }
            fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            for (Map<String, String> map : list) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String str = entry.getKey() + " " + entry.getValue();
                    osw.write(str);
                    osw.write("\r\n");
                }
            }
            osw.close();
            System.out.println("Data has been stored");
            System.out.println("-----------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}