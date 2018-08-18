package com.java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.java.Objects.ResultDetails;

public class CMS_Keywords extends TestType {

	public WebDriver webdriver = null;
	public Actions builder = null;

	ResultDetails resultDetails = new ResultDetails();
	
	public CMS_Keywords(WebDriver webdriver) {
		this.webdriver = webdriver;
	}

	// Keywords
	public enum ActionTypes {
		VERIFYLINKFUN, VERIFYCMSBUCKET
	};
	
	public ResultDetails performAction(String fieldText, String value,
			String actionType, String fieldName) {
		try {
			ActionTypes actTypes = ActionTypes
					.valueOf(actionType.toUpperCase());
			switch (actTypes) {
			
				case VERIFYLINKFUN:
					resultDetails = verifylinkfun(fieldText, value, fieldName);
					break;
					
				case VERIFYCMSBUCKET:
					resultDetails = verifycmsbucket(webdriver, fieldText, value, fieldName);
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
	 * Verifies if the link is exist or not, if it exist click on the link and
	 * verifies Title, Which is passed through 'value', then go back to the
	 * previous page
	 */
	public ResultDetails verifylinkfun(String fieldText, String value,
			String fieldName) {
		try {
			fieldText = fieldText.substring(3, fieldText.length());
			assertTrue(webdriver.findElement(
					WebDriverUtils.locatorToByObj(webdriver, fieldText))
					.isDisplayed());
//			webdriver.findElement(
//					WebDriverUtils.locatorToByObj(webdriver, fieldText))
//					.click();
			builder = new Actions(webdriver);
			builder.click(webdriver.findElement(
					WebDriverUtils.locatorToByObj(webdriver, fieldText))).build().perform();
			
			try {
				Thread.sleep(4000);
				if (webdriver.findElement(
						By.xpath("//div[@id='roadB_ad_close']/a"))
						.isDisplayed())
					builder.click(webdriver.findElement(
							By.xpath("//div[@id='roadB_ad_close']/a"))).build().perform();
				
			} catch (Throwable e) {
			}

			try {
				Thread.sleep(1000);
			} catch (Throwable e) {
			}
			System.out.println(webdriver.getTitle().contains(value));
			assertTrue(webdriver.getTitle().contains(value));
			webdriver.navigate().back();
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
			return resultDetails;
		} catch (Throwable e) {
			System.out.println(fieldName + " :: Not Found");
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(fieldName + " :: Not Found");
			return resultDetails;
		}
	}
	
	/*
	 * Verifies that contents in the Bucket are present or not
	 */
	public ResultDetails verifycmsbucket(WebDriver webdriver, String fieldText,
			String value, String fieldName) {
		String strLink = "";
		String field = "";
		if (fieldText.equalsIgnoreCase(null) || fieldText.equalsIgnoreCase(""))
			fieldText = "";
		else
			field = fieldText.substring(0, 3);

		try {

			int num = webdriver.findElements(By.xpath(value)).size();
			System.out.println(num + " :: No of Links");
			String strValue[] = value.split("/li/");
			int i = 1;
			if (num == 0) {
				assertFalse(true);
			}
			while (i <= num) {
				Thread.sleep(3000);
				System.out.println(webdriver
						.findElement(
								By.xpath(strValue[0] + "/li[" + i + "]/"
										+ strValue[1])).getText());
				assertTrue(webdriver
						.findElement(
								By.xpath(strValue[0] + "/li[" + i + "]/"
										+ strValue[1])).isDisplayed());
				if (field.equalsIgnoreCase("FUN")) {
					String strField[] = fieldText.substring(3,
							fieldText.length()).split("::");
					String strPath = strValue[0] + "/li[" + i + "]/"
							+ strValue[1];
					strLink = webdriver.findElement(
							By.xpath(strValue[0] + "/li[" + i + "]/"
									+ strValue[1])).getText();
					webdriver.findElement(By.xpath(strPath)).click();
					Thread.sleep(3000);
					try {
						if (webdriver.findElement(
								By.xpath("//div[@id='roadB_ad_close']/a"))
								.isDisplayed())
							webdriver.findElement(
									By.xpath("//div[@id='roadB_ad_close']/a"))
									.click();
					} catch (Throwable e) {
					}
					if (webdriver.getTitle()
							.equalsIgnoreCase("Medscape Log In")) {
						System.out.println(strField[0] + " :: " + strField[1]);
						webdriver.findElement(By.id("userId")).sendKeys(
								strField[0]);
						webdriver.findElement(By.id("password")).sendKeys(
								strField[1]);
						webdriver.findElement(By.id("loginbtn")).click();
						Thread.sleep(2000);
						assertTrue(webdriver.getTitle().contains(strLink));
						webdriver.manage().deleteAllCookies();
						webdriver.get("http://www.medscape.com");
						Thread.sleep(4000);
					} else if (!strLink.contains("More")) {
						assertTrue(webdriver.getTitle().contains(strLink));
						webdriver.navigate().back();
						Thread.sleep(2000);
					}
				}
				i++;
			}
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
			return resultDetails;
		} catch (Throwable e) {
			System.out.println(e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(fieldName + " :: NOT FOUND");
			return resultDetails;
		}
	}

}
