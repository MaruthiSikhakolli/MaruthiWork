package com.java;

import java.net.URL;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

import CME.THO.verifysession;

import com.java.Objects.ResultDetails;

public class Reg_Feature_Keywords extends TestType {

	public WebDriver webdriver = null;
	private Actions builder = null;
	private DesiredCapabilities dc = new DesiredCapabilities();

	ResultDetails resultDetails = new ResultDetails();
	
	public Reg_Feature_Keywords(WebDriver webdriver) {
		this.webdriver = webdriver;
	}
	public enum verifysession{
		THO,REG
	}
	// Keywords
	public enum ActionTypes {
		REGISTERUS, REGISTERDE, VERIFYBIAUTHCOOKIE, VERIFYSESSION
	};
	
	public ResultDetails performAction(String fieldText, String value,
			String actionType, String fieldName) {
		try {
			ActionTypes actTypes = ActionTypes
					.valueOf(actionType.toUpperCase());
			switch (actTypes) {
			case REGISTERUS:
				try {
					System.out
							.println("Registering a new user in US with professioon index: "
									+ fieldText);
					RegisterUS regUs = new RegisterUS();
					resultDetails = regUs.register(webdriver, fieldText, value);
				} catch (Exception e) {
					System.out.println("exception value : " + e.getMessage());
					resultDetails.setFlag(false);
					resultDetails
							.setErrorMessage("unable to click the required   ::+"
									+ value + "::not Found");
					WebDriverUtils.waitForPageToLoad(webdriver, "50000");
				}
				break;
			case REGISTERDE:
				
				System.out
						.println("Registering a new user in DE with professioon index: "
								+ fieldText);
				RegisterDE regDe = new RegisterDE();
				try {
					resultDetails = regDe.Register(webdriver, fieldText, value);
				} catch (Exception e) {
					System.out.println("exception value : " + e.getMessage());
					resultDetails.setFlag(false);
					resultDetails
							.setErrorMessage("unable to click the required   ::+"
									+ value + "::not Found");
					WebDriverUtils.waitForPageToLoad(webdriver, "50000");
				}
				break;
				
			case VERIFYBIAUTHCOOKIE:
				verifybiauthcookie(webdriver, fieldText, value,
						fieldName);
				break;
				
			case VERIFYSESSION:
				verifysession(fieldText, value, fieldName);
				break;
			}
			return resultDetails;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("BEFORE CLICK" + fieldText);
			String field;
			if (fieldName.equalsIgnoreCase("NONE"))
				field = fieldText.substring(3, fieldText.length());
			else
				field = fieldName;
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Element with Id : " + field
					+ " is not found");
			return resultDetails;
		}
	}
	
	/*
	 * This keyword verifies the biauthcookie for the different scenarios
	 */
	public void verifybiauthcookie(WebDriver webdriver,
			String fieldText, String value, String fieldName) {
		try {
			Cookie k = webdriver.manage().getCookieNamed("biauth");
			String str = k.toString();
			String str1[] = str.split("=", 2);
			String strA[] = str1[1].split(";", 2);
			 if (strA[0].startsWith("\"")) {
			 strA[0] = strA[0].substring(1,strA[0].length()-1);
			 }
			webdriver.get("http://tools.bi.medscape.com/php/decrypt.php");

			webdriver.findElement(By.id("str2decrypt")).sendKeys(strA[0]);
			webdriver.findElement(By.xpath("//html/body/form/input")).click();
			System.out.println(value);
			String strValue[] = value.split("::");
			String strD = "\"guid\":\"" + strValue[0] + "\",\"authchannel\":"
					+ strValue[1] + ",\"rememberme\":" + strValue[2]
					+ ",\"logout\":" + strValue[3] + "";
			assertTrue(webdriver.getPageSource().toString().contains(strD));
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
		} catch (Throwable e) {
			System.out.println(e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.getMessage());
		}
	}
	
	/*
	 * Access the open or restricted page URL with UAC or without UAC
	 * Specified for Professional(Registration)
	 */
	public void verifysession(String field, String value,
			String fieldName) throws Exception {
		URL grid_url = new URL("http://" + SeleniumDriverTest.HubHost + ":"
				+ SeleniumDriverTest.HubPort + "/wd/hub");
		String f="[::\\$$]+";
		String strField[] = field.split(f);
		//String temp[]=s.split(f);
		for(int i =0; i < strField.length ; i++)
		{
			//System.out.println("strField["+i+"]="+strField[i]);
		}
		if(strField[0].startsWith("@@")==true)
		{
			String t[]=strField[0].split("@@");
			for(int i =0; i < t.length ; i++)
			{
				//System.out.println("t["+i+"]="+t[i]);
			}
			field=t[1];
		}
		else{
			field="REG";
		}
		if (strField.length < 2) {
			value = "blank::" + value;
		}
		String strValue[] = value.split("::");
		verifysession  v=verifysession.valueOf(field);
		
		Set<Cookie> c = null;
		Iterator<Cookie> it = null;
		try {
			if (!strField[0].equalsIgnoreCase("")) {
				webdriver.manage().deleteAllCookies();
				System.out.println("appurl:"+SeleniumDriverTest.appUrl);
				webdriver.get(SeleniumDriverTest.appUrl);
				
	switch(v)
	{
	case THO://System.out.println("This is THO");
			webdriver.findElement(By.className("login_but")).click();
			webdriver.findElement(By.xpath(strField[1])).sendKeys(strField[2]);
			webdriver.findElement(By.xpath(strField[3]))
							.sendKeys(strField[4]);
	if (strValue[0].equalsIgnoreCase("ON"))
	{
		if((webdriver.findElement(By.xpath(strField[5])).isSelected())==true)
		{
		//webdriver.findElement(By.xpath("(//input[@name='rememberMe'])[2]")).click();
		}
		else
		{
			webdriver.findElement(By.xpath(strField[5])).click();
		}
	}
	else if(strValue[0].equalsIgnoreCase("OFF"))
	{
		if((webdriver.findElement(By.xpath(strField[5])).isSelected())==true)
		{
			webdriver.findElement(By.xpath(strField[5])).click();
		}
		else
		{
			//webdriver.findElement(By.xpath("(//input[@name='rememberMe'])[2]")).click();
		}
	}
			
//			if (strValue[0].equalsIgnoreCase("ON"))
			//webdriver.findElement(By.xpath(strField[5])).click();
				webdriver.findElement(By.xpath(strField[6])).submit();
				break;				
						
	case REG://System.out.println("This default");
			webdriver.findElement(By.name("userId")).sendKeys(strField[0]);
			webdriver.findElement(By.name("password"))
				.sendKeys(strField[1]);
			if (strValue[0].equalsIgnoreCase("ON"))
			webdriver.findElement(By.name("remember")).click();
			webdriver.findElement(By.name("password")).submit();
			break;
				}
			}
			c = webdriver.manage().getCookies();
			it = c.iterator();

			webdriver.quit();

			if (SeleniumDriverTest.browserType.equalsIgnoreCase("GCHROME"))
			{
				dc = DesiredCapabilities.chrome();
				dc.setBrowserName("chrome");
				webdriver = new CustomRemoteWebDriver(grid_url, dc);
				System.out.println("grid_url:"+grid_url);
			} else if (SeleniumDriverTest.browserType.equalsIgnoreCase("FF")) {
				dc = DesiredCapabilities.firefox();
				dc.setBrowserName("firefox");
				webdriver = new CustomRemoteWebDriver(grid_url, dc);
			} else if (SeleniumDriverTest.browserType.equalsIgnoreCase("IE8")
					|| SeleniumDriverTest.browserType.equalsIgnoreCase("IE")) {
				dc = DesiredCapabilities
						.internetExplorer();
				dc.setBrowserName("internet explorer");
				webdriver = new CustomRemoteWebDriver(grid_url, dc);
			}
			if(strValue[1].contains(SeleniumDriverTest.Environ)) {
				System.out.println("Environment "+SeleniumDriverTest.Environ+ "  is not dropped");
				webdriver.get(strValue[1]);
			}
			else if (webdriver.getCurrentUrl().contains("medscape")||webdriver.getCurrentUrl().contains("webmd")
					||webdriver.getCurrentUrl().contains("wbmd")||webdriver.getCurrentUrl().contains("medicine")
					||webdriver.getCurrentUrl().contains("rxlist"))
			{
				String cururl=strValue[1];
				String url2[]=cururl.split("\\.",2);
				String navurl=url2[0]+"."+SeleniumDriverTest.Environ+"."+url2[1];
				webdriver.navigate().to(navurl);
			}
			webdriver.manage().window().maximize();
			webdriver.manage().deleteAllCookies();
			Cookie ck;

			if (!strField[0].equalsIgnoreCase("")) {
				while (it.hasNext()) {
					ck = it.next();
				System.out.println("ck:"+ck);
					if (ck.toString().contains("expires="))
						webdriver.manage().addCookie(ck);
				}
				
			}
			webdriver.navigate().refresh();
			//WebDriverUtils.waitForPageToLoad(webdriver, "10000");
			assertTrue(webdriver.getPageSource().toLowerCase().trim()
						.contains(strValue[2].toLowerCase().trim()));

			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(strValue[2] + " Not Found");
		}
	}
}
