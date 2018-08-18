package cucumber;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinition {

	WebDriver driver = new FirefoxDriver();

	@Given("^User is on Home Page$")
	public void user_is_on_Home_Page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^User Navigate to LogIn Page$")
	public void user_Navigate_to_LogIn_Page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@And("^User enters UserName and Password$")
	public void user_enters_UserName_and_Password() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@And("^User enters \"(.*)\" and \"(.*)\"$")
	public void user_enters_UserName_and_Password(String username, String password) throws Throwable {
		driver.findElement(By.id("log")).sendKeys(username);
		driver.findElement(By.id("pwd")).sendKeys(password);
		driver.findElement(By.id("login")).click();
	}

	@And("^User enters Credentials to LogIn$")
	public void user_enters_testuser__and_Test(DataTable usercredentials) throws Throwable {
		List<List<String>> data = usercredentials.raw();
		// This is to get the first data of the set (First Row + First Column)
		driver.findElement(By.id("log")).sendKeys(data.get(0).get(0));
		// This is to get the first data of the set (First Row + Second Column)
		driver.findElement(By.id("pwd")).sendKeys(data.get(0).get(1));
		driver.findElement(By.id("login")).click();
	}
	
	//Code to handle maps data
	@When("^User enters Credentials to LogIn using Maps$")
	public void user_enters_testuser_and_Test_using_Maps(DataTable usercredentials) throws Throwable {

		//Write the code to handle Data Table
		List<Map<String,String>> data = usercredentials.asMaps(String.class,String.class);
		driver.findElement(By.id("log")).sendKeys(data.get(0).get("Username")); 
	    driver.findElement(By.id("pwd")).sendKeys(data.get(0).get("Password"));
	    driver.findElement(By.id("login")).click();
    }
	
	@When("^User enters Credentials to LogIn using Maps with multiple data$")
	public void user_enters_testuser_and_Test_using_Maps_with_multiple_data(DataTable usercredentials) throws Throwable {
		for (Map<String, String> data : usercredentials.asMaps(String.class, String.class)) {
			driver.findElement(By.id("log")).sendKeys(data.get("Username")); 
		    driver.findElement(By.id("pwd")).sendKeys(data.get("Password"));
		    driver.findElement(By.id("login")).click();
			}
	}
	
	@When("^User enters Credentials to LogIn using class objects$")
	public void user_enters_testuser_and_Test(List<Credentials>  usercredentials) throws Throwable {
		for (Credentials credentials : usercredentials) {			
			driver.findElement(By.id("log")).sendKeys(credentials.getUsername()); 
		    driver.findElement(By.id("pwd")).sendKeys(credentials.getPassword());
		    driver.findElement(By.id("login")).click();
			}		
	}

	@Then("^Message displayed LogIn Successfully$")
	public void message_displayed_LogIn_Successfully() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@But("^The user name displayed is wrong$")
	public void the_user_name_displayed_is_wrong() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

}
