package CME;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class StringLength {
public static void main(String args[]) throws InterruptedException
{
 WebDriver webdriver=new ChromeDriver();
 webdriver.get("http://pagebuilder.dev02.webmd.com/");
 		//					0														1
 String s1="//table[@id='Wizard']/tbody/tr[2]/td/table/tbody/tr/td/table/tbody$$/tr[9]/td" +
 		"$$Omniture Asset Name:$$/tr[7]/td$$*Friendly Name:$$/tr[11]/td$$*Tier:";
 	//		2						3			4				5			6
 String f="[$$]+";
 String temp[]=s1.split(f);
 for(int i=0;i<temp.length;i++){
	// System.out.println("temp["+i+"]="+temp[i]);
 }
 
 	webdriver.manage().window().maximize();
	webdriver.findElement(By.id("userNameTextBox")).sendKeys("selenium");
	webdriver.findElement(By.id("passwordTextBox")).sendKeys("selenium");
	webdriver.findElement(By.id("signInBtn")).click();
	
	Select select = new Select(webdriver.findElement(By.id("mdgsiteSelection_ddSiteManageSiteIds")));
	select.selectByVisibleText("WebMD");
	webdriver.findElement(By.xpath("//div[@id='mdgsiteSelection_mdgSiteSelection']/table/tbody/tr/td/input")).click();

	webdriver.findElement(By.xpath("//table[@id='ctl04_5']/tbody/tr/td/nobr")).click();
	Thread.sleep(2000);
	
	webdriver.findElement(By.xpath("//table[@id='navMapTree_item_0']/tbody/tr/td[2]/img")).click();
	Thread.sleep(2000);
	webdriver.findElement(By.xpath("//table[@id='navMapTree_item_1']/tbody/tr/td[3]/img")).click();
	Thread.sleep(2000);
	webdriver.findElement(By.xpath("//table[@id='navMapTree_item_4565']/tbody/tr/td[4]/img")).click();
	Thread.sleep(2000);
	webdriver.findElement(By.xpath("//div[@id='navMapTree_item_4598_cell']/nobr")).click();
	Thread.sleep(2000);
	
	webdriver.switchTo().frame("PageWorkCenterData");
	webdriver.findElement(By.xpath("//input[@id='ButtonBar2_createNewPageBtn']")).click();
	Thread.sleep(2000);
	System.out.println("create new page");
	webdriver.findElement(By.xpath("//input[@id='Wizard_layoutOptions_1']")).click();
	webdriver.findElement(By.id("Wizard_StartNavigationTemplateContainerID_StartNextButton")).click();
	
	Select select1 = new Select(webdriver.findElement(By.xpath("//select[@id='Wizard_TemplateLayoutSelector_DDLLayoutOptions']")));
	select1.selectByIndex(2);
	
	webdriver.findElement(By.xpath("//input[@id='Wizard_StepNavigationTemplateContainerID_StepNextButton']")).click();
	
	String s5=webdriver.findElement(By.xpath("//table[@id='Wizard']/tbody/tr[2]/td/table/tbody/tr/td/table")).getText();
	s5=s5.replaceAll("\n", "").replaceAll(" ", "").replaceAll("\\*","").replaceAll("\\?","").replaceAll(":",";");
	System.out.println("s5="+s5);
	
	 CharSequence y1="FriendlyName;OmnitureAssetName;Tier";
	 if(s5.contains(y1)==true)
	 {
		 System.out.println("true");
	 }
	 
	/*String s2=webdriver.findElement(By.xpath(temp[0]+temp[1])).getText();
	System.out.println("s2="+s2);
	
	
	String s3=webdriver.findElement(By.xpath(temp[0]+temp[3])).getText();
	System.out.println("s3="+s3);
	
	String s4=webdriver.findElement(By.xpath(temp[0]+temp[5])).getText();
	System.out.println("s4="+s4);
			if(s2.equalsIgnoreCase(temp[2]))
			{
				if(s3.equalsIgnoreCase(temp[4]))
				{
					if(s4.equalsIgnoreCase(temp[6]))
					{
						System.out.println("true");
					}
				}
			}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
}
