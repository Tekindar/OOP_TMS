package hk.edu.polyu.comp.comp2021.tms.model;

import org.junit.Test;

public class TMSTest {

<<<<<<< Updated upstream
=======
    private TMS tms;
    private CriteriaStorage cs;
    private LinkedList<String> wrongTaskInputs = new LinkedList<>();
    private LinkedList<String> correctTaskInputs = new LinkedList<>();
    private LinkedList<String> wrongCriteriaInputs = new LinkedList<>();
    private LinkedList<String> correctCriteriaInputs = new LinkedList<>();

    private final double[] smalls = {0.1, 0.2, 0.3, 0.5, 1.5, 5, 10};


    /**
     * a copy of a simple translator from controller
     *
     * @param input the user input
     */
    public void run(String input){
        String[] keywords = input.split(" ");
        String[] content = Arrays.copyOfRange(keywords, 1, keywords.length);
        if(keywords.length==0){
            System.out.println ("Invalid Operation Name");
            return;
        }
        else if ((keywords[0].equals("quit")||keywords[0].equals("exit"))&&keywords.length==1) return;

        switch(keywords[0]){
            case "CreatePrimitiveTask":
                tms.CreatePrimitiveTask(keywords);
                break;
            case "CreateCompositeTask":
                tms.CreateCompositeTask(keywords);
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
            default:
                System.out.println("Invalid Operation Name");
                break;
        }
    }

    /**
     * instantiate the two models
     */
    @Before
    public void setUp(){
        tms = new TMS();

        {
            cs = new CriteriaStorage();
            CriteriaStorage.clearCriteria();
            CriteriaStorage.getCriteria().add(IsPrimitiveCriterion.getSingleton());
        }
    }


    /**
     * create string array that stores inputs
     */
    @Before
    public void createTasks(){
        // Illegal Input
        String testName1 = "CreatePrimitiveTask 1p test 1 ,";
        String testName2 = "CreatePrimitiveTask mytestname test 1 ,";
        String testDescription = "CreatePrimitiveTask test1 test_descript 1 ,";
        String testDuration1 = "CreatePrimitiveTask test2 test -1 ,";
        String testDuration2 = "CreatePrimitiveTask test3 test 0 ,";
        String testPrerequisite1 = "CreatePrimitiveTask test4 test 1 ";
        String testPrerequisite2 = "CreatePrimitiveTask test5 test 1 p1";
        String testSubtask1 = "CreateCompositeTask test6 test p1,p2";
        String other1 = "CreatePrimitiveTask test7 test 0.3";
        String other2 = "CreateCompositeTask test8  ,";
        String other3 = "CreateCompositeTask 1name test p1,p2";
        String other4 = "CreatePrimitiveTask p- this-is-p1 0.5 ,";
        String other5 = "CreateCompositeTask c- this-is-p1 ,";
        String other6 = "CreateCompositeTask a";
        String other7 = "CreateCompositeTask a .p ,";
        String other8 = "CreateCompositeTask a b ,";
        String other9 = "CreatePrimitiveTask b  1 ,";
        String other10 = "CreatePrimitiveTask c d e ,";


        // legal input
        String p1 = "CreatePrimitiveTask p1 this-is-p1 0.5 ,";
        String p2 = "CreatePrimitiveTask p2 this-is-p2 0.3 p1";
        String p3 = "CreatePrimitiveTask p3 this-is-p3 1.3 p2";
        String p4 = "CreatePrimitiveTask p4 this-is-p4 2 ,";
        String p5 = "CreatePrimitiveTask p5 this-is-p5 1 ,";
        String c1 = "CreateCompositeTask c1 this-is-c1 p1,p4";
        String c2 = "CreateCompositeTask c2 this-is-c2 p3,p5";
        String c3 = "CreateCompositeTask c3 this-is-c3 c1,c2";
        String p6 = "CreatePrimitiveTask p6 this-is-p6 1 c3";
        String fail1 = "CreateCompositeTask c3 this-is-c3 c1,c2";
        String fail2 = "CreatePrimitiveTask p1 this-is-p1 0.5 ,";
        String fail3 = "CreateCompositeTask c4 this-is-c4 c1,c2;";





        String[] temp = {testName1,testName2,testDescription,testDuration1,testDuration2,
                testPrerequisite1,testPrerequisite2,testSubtask1,other1,other2,other3,
        other4,other5,other6,other7,other8,other9,other10};
        wrongTaskInputs.addAll(Arrays.asList(temp));
        String[] temp2 = {p1,p2,p3,p4,p5,c1,c2,c3,fail1,fail2,fail3,p6};
        correctTaskInputs.addAll(Arrays.asList(temp2));
    }

    @Before
    public void createCriteria(){
        // Illegal Input
        String testBasicCriterion = "DefineBasicCriterion bc1 duration contains 0.5";
        String testNegatedCriterion = "DefineNegatedCriterion bc2 bc0 ";
        String testBinaryCriterion = "DefineBinaryCriterion bc3 bc1 + bc2 ";
        String testSearch = "Search bc0";

        // legal input
        String cri1 = "DefineBasicCriterion cri1 duration > 0.5";
        String cri2 = "DefineNegatedCriterion cri2 cri1";
        String cri3 = "DefineBinaryCriterion cri3 cri1 || cri2";
        //String search = "Search cri3"; // should display all
        String[] wrong = {testBasicCriterion, testNegatedCriterion, testBinaryCriterion
                , testSearch};
        wrongCriteriaInputs.addAll(Arrays.asList(wrong));

        String[] correct = {cri1, cri2, cri3};
        correctCriteriaInputs.addAll(Arrays.asList(correct));
    }

    /**
     *
     */
>>>>>>> Stashed changes
    @Test
    public void testTMSConstructor(){
        TMS tms = new TMS();
    }

<<<<<<< Updated upstream
=======
    /**
     * test for primitive and composite tasks name,
     * description, duration and prerequisite validation, then
     * test if task created and added successfully
     */
    @Test
    public void testTaskCreation(){
        for(String s:wrongTaskInputs){
            run(s);
        }
        Assert.assertEquals(0,tms.getSize());
        for(String s:correctTaskInputs){
            run(s);
        }
        Assert.assertEquals(9,tms.getSize());
        Assert.assertEquals(2, ((CompositeTask)TMS.getTask("c1")).getAllSubtask().size());
        Assert.assertEquals(0, ((CompositeTask)TMS.getTask("c1")).getAllCompositeSubtask().size());
        Assert.assertEquals(1,((PrimitiveTask)TMS.getTask("p3")).getIndirectPrerequisite().size());
        Assert.assertEquals("this-is-p1",TMS.getTask("p1").getDescription());
    }


    /**
     * test algorithm for duration and completion
     */
    @Test
    public void timeCalculation(){
        Task p1 = TMS.getTask("p1");
        Task p3 = TMS.getTask("p3");
        Task c2 = TMS.getTask("c2");
        Task c3 = TMS.getTask("c3");

        if(p1!= null)
            Assert.assertEquals(smalls[3],p1.getDuration());
        if(p3!=null)
            Assert.assertEquals(2+smalls[0],p3.getCompletion());
        if(c2!=null)
            Assert.assertEquals(2+smalls[0],c2.getCompletion());
        if(c3!=null)
            Assert.assertEquals(2+smalls[0],c3.getDuration());
    }

    @Test
    public void criteriaDefinition(){
        for(String s: wrongCriteriaInputs){
            run(s);
        }
        Assert.assertEquals(1,CriteriaStorage.getCriteria().size());
        for(String s:correctCriteriaInputs){
            run(s);
        }
        Assert.assertEquals(4,CriteriaStorage.getCriteria().size());


    }
>>>>>>> Stashed changes


}