package passingDriverAsParamInConstructor;

import org.openqa.selenium.WebDriver;

public class DrivenClass{

	WebDriver driver;
	DrivenClass(WebDriver driver){
		this.driver = driver;
	}
	
	public void driverParameterPassing() {
		System.out.println("Passed driver parameter through constructor");
	}
	
	public void openAmazon() {
		System.out.println("Opening Amazon");
		driver.get("http://www.amazon.in");
	}
}
