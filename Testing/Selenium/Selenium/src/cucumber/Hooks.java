package cucumber;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
	
	@Before
    public void beforeScenario(){
        System.out.println("This will run before the Scenario");
    }	
	
	@After
    public void afterScenario(){
        System.out.println("This will run after the Scenario");
    }
	
	//We learned that @Before & @After hooks runs before & after every Scenario. But @Before(“@First”) will run only before the first scenario 
	//and like wise other tagged hooks. 
	
	@Before("@SmokeTest")
    public void beforeSmoke(){
        System.out.println("This will run only before the smoke scenario");
    }	
	
	@After("@RegressionTest")
    public void afterRegression(){
        System.out.println("This will run only after the regression scenario");
    }
	
	@Before("@SmokeTest,@RegressionTest")
    public void beforeFirstAndThird(){
        System.out.println("This will run before both Smoke & Regression Scenario");
	}
	
	//Execution Order of Hooks
	//The very important thing to note here is:

	//@Before(order = int) : This runs in increment order, means value 0 would run first and 1 would be after 0.
	//@After(order = int) : This runs in decrements order, means opposite of @Before. Value 1 would run first and 0 would be after 1.

	@Before(order=1)
    public void beforeScenarioWithOrder(){
        System.out.println("This will run before the every Scenario");
    }	
	@Before(order=0)
    public void beforeScenarioStart(){
        System.out.println("-----------------Start of Scenario-----------------");
    }	
	
	
	@After(order=0)
    public void afterScenarioFinish(){
        System.out.println("-----------------End of Scenario-----------------");
    }	
	@After(order=1)
    public void afterScenarioWithOrder(){
        System.out.println("This will run after the every Scenario");
    }	
}