package passingDriverAsParamInConstructor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverClass{
	public static void main(String args[]) {
		System.setProperty("webdriver.chrome.driver", "E:\\Softwares\\Selenium\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		DrivenClass drivenClass = new DrivenClass(driver);
		drivenClass.driverParameterPassing();
		driver.manage().window().maximize();
		drivenClass.openAmazon();
		driver.quit();
	}
}
