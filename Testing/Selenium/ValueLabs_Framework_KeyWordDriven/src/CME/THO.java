package CME;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class THO {
	public enum verifysession{
		THO,REG
	}
public static void main(String args[])
{
	WebDriver webdriver =new ChromeDriver();
	webdriver.get("http://www.qa00.theheart.org/");
	webdriver.manage().window().maximize();
	
	String s="@@THO:://input[@id='usernamePage']$$AndersonPamela$$" +
		"//input[@id='passwordPage']$$Anderson123$$(//input[@name='rememberMe'])[2]" +
		"$$//div[@class='button loginbut']/a";
		
String f="[::\\$$]+",field = null;
String temp[]=s.split(f);
for(int i =0; i < temp.length ; i++)
{
	//System.out.println("temp["+i+"]="+temp[i]);
}
if(temp[0].startsWith("@@")==true)
{
	String t[]=temp[0].split("@@");
	for(int i =0; i < t.length ; i++)
	{
		//System.out.println("t["+i+"]="+t[i]);
	}
	field=t[1];
}
else{
	field="REG";
}
verifysession  v=verifysession.valueOf(field);
switch(v)
{
	case THO:System.out.println("This is THO");
	webdriver.findElement(By.className("login_but")).click();
	webdriver.findElement(By.xpath("//input[@id='usernamePage']")).sendKeys("AndersonPamela");
	webdriver.findElement(By.xpath("//input[@id='passwordPage']"))
	.sendKeys("Anderson123");
	if ("ON".equalsIgnoreCase("ON"))
	{
		if((webdriver.findElement(By.xpath("(//input[@name='rememberMe'])[2]")).isSelected())==true)
		{
			System.out.println("this is tho90990");
			//webdriver.findElement(By.xpath("(//input[@name='rememberMe'])[2]")).click();
		}
		else
		{
			System.out.println("esgjkjs");
			webdriver.findElement(By.xpath("(//input[@name='rememberMe'])[2]")).click();
		}
	}
	else if("OFF".equalsIgnoreCase("OFF"))
	{
		if((webdriver.findElement(By.xpath("(//input[@name='rememberMe'])[2]")).isSelected())==true)
		{
			System.out.println("this is tho90990");
			webdriver.findElement(By.xpath("(//input[@name='rememberMe'])[2]")).click();
		}
		else
		{
			System.out.println("esgjkjs");
		//	webdriver.findElement(By.xpath("(//input[@name='rememberMe'])[2]")).click();
		}
	}
	webdriver.findElement(By.xpath("//div[@class='button loginbut']/a")).submit();
			break;
	case REG:System.out.println("This default");
			break;
}




}
}