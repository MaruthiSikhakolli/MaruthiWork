package cucumber;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/cucumber/CucumberFeatureFileConcepts.feature", //Path of feature file
				 glue="src/cucumber/StepDefinition.java", //Path of step definition file
				 dryRun = false, //If it is set as true, it means that Cucumber will only checks that every Step mentioned in the Feature File have 
				 //corresponding code written in Step Definition file or not. So in case any of the function is missed in the Step Definition for any Step in Feature File, 
				 //it will give us the message.
				 
				 tags= {"@SmokeTest"}, //It defines which scenarios to run. we can use multiple tags here similar to AND or OR logics
				 //tags = {"@SmokeTest,@RegressionTest"} //This is logical OR. OR means scenarios which are tagged either as @SmokeTest OR @RegressionTest.
				 //tags = {"@SmokeTest","@RegressionTest"} //This is logical AND. AND means scenarios which are tagged with both @SmokeTest AND @RegressionTest.
				 //tags = {"@SmokeTest","~@RegressionTest"} //This will ignore running RegressionTest scenarios
				
				 plugin="pretty", //Used to generate different reports
				 //plugin = {"html:src/cucumber/Cucumber_HTML_Reports"} //This will generate HTML reports in the mentioned folder
				 //plugin = {"json:Folder_Name/cucumber.json"} //This will generate JSON reports in the mentioned folder
				 //plugin = {"junit:Folder_Name/cucumber.xml"} //This will generate JUNIT reports in the mentioned folder
				 
				 monochrome=true, //Output will be displayed in a better readable manner
				 strict=true //Will fail execution if there are any undefined or pending steps
				 )
public class TestRunner {
	
}
