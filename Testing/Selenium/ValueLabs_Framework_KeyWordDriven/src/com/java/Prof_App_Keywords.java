package com.java;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.java.Objects.ResultDetails;

public class Prof_App_Keywords extends TestType {
	
	private WebDriver webdriver = null;
	private Actions builder = null;
	
	ResultDetails resultDetails = new ResultDetails();
	
	public Prof_App_Keywords(WebDriver webdriver) {
		this.webdriver = webdriver;
	}

	// Keywords
	public enum ActionTypes {
		LOGOUTIF, FINDANDCLICK, BLANKPAGEFORSPECIALCHARACTER, VERIFYFEATUREBAR
	};
	
	public ResultDetails performAction(String fieldText, String value,
			String actionType, String fieldName) {
		try {
			ActionTypes actTypes = ActionTypes
					.valueOf(actionType.toUpperCase());
			switch (actTypes) {
				case LOGOUTIF: logoutIf(webdriver);
				break;
				
				case FINDANDCLICK:findandclick(webdriver, fieldText, value,fieldName);
				break;
				
				case BLANKPAGEFORSPECIALCHARACTER:
					blankpageforspecialcharacter(webdriver,fieldText, value);
				break;
				
				case VERIFYFEATUREBAR:
					assertTrue(webdriver.getCurrentUrl().contains(value));
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
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
	
	
	public void logoutIf(WebDriver webdriver) {
		WebDriverUtils.waitForPageToLoad(webdriver, "50000");
		try {
			if (WebDriverUtils.isElementPresent(webdriver,
					"xpath=//div[@id='rightlinksetting']/a")) {
				Actions builder = new Actions(webdriver);
				builder.moveToElement(webdriver.findElement(By.xpath("//div[@id='rightlinksetting']/a"))).build().perform();
				webdriver.findElement(
						By.xpath("//a[contains(text(),'Log Out')]")).click();
				WebDriverUtils.waitForPageToLoad(webdriver, "50000");
				webdriver.findElement(By.id("logoutbtn")).click();
				WebDriverUtils.waitForPageToLoad(webdriver, "50000");
			}
			resultDetails.setFlag(true);
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.getMessage());
		}
	}
	
	/*
	 * It Searches for given link in first 10 links and clicks if it finds the
	 * required link
	 */
	public void findandclick(WebDriver webdriver, String fieldText,
			String value, String fieldName) {
		String strFieldText[] = fieldText.split("li");
		value = GETVALUE(value);
		System.out.println("Value :: " + value);
		int i = 1;
		try {
			Thread.sleep(3000);
			while (i <= 10) {
				System.out.println(webdriver.findElement(
						By.xpath(strFieldText[0] + "li[" + i + "]")).getText());
				
				System.out.println("*************************************");
				if (webdriver.getTitle().equalsIgnoreCase("News Search")) {
					if (webdriver
							.findElement(
									By.xpath(strFieldText[0] + "li[" + i + "]")).getText()
							.contains(value)) {
						webdriver.findElement(
								By.xpath(strFieldText[0] + "li[" + i + "]//a")).click();
						System.out.println(webdriver.getTitle());
						break;
					}
				}
				else {
					if (webdriver
							.findElement(
									By.xpath(strFieldText[0] + "li[" + i + "]")).getText()
							.contains(value)
							&& webdriver
									.findElement(
											By.xpath(strFieldText[0] + "li[" + i
													+ "]"))
									.getText().contains("www.medscape")) {
						webdriver.findElement(
								By.xpath(strFieldText[0] + "li[" + i + "]"
										+ strFieldText[1] + "//a")).click();
						System.out.println(webdriver.getTitle());
						break;
					}
				}
				i++;
			}
			if (i>10)
				assertFalse(true);
			
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.getMessage());
		}
	}
	
	/*
	 * It clicks on the Articles which are having below specified special characters in it.
	 * And verifies it is able to show the data or not
	 */
	public void blankpageforspecialcharacter(WebDriver webdriver,
			String fieldText, String value) {
		ResultDetails resultDetails = new ResultDetails();
		String strLink = "";
		try {
			List<WebElement> elem = webdriver.findElements(By
					.xpath("//div[@id='news']//a"));
			System.out.println(elem.size());
			for (WebElement ele : elem) {
				strLink = ele.getText();
				System.out.println(strLink);
				if (ele.getText().contains("!") || ele.getText().contains("@")
						|| ele.getText().contains("#")
						|| ele.getText().contains("$")
						|| ele.getText().contains("%")
						|| ele.getText().contains("^")
						|| ele.getText().contains("?")
						|| ele.getText().contains("*")
						|| ele.getText().contains("'")
						|| ele.getText().contains("\"")
						|| ele.getText().contains("_")
						|| ele.getText().contains("(")) {
					ele.click();
					Thread.sleep(3000);
					assertTrue(webdriver.getPageSource().toString()
							.toLowerCase().contains(strLink.toLowerCase()));
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				}
			}

			resultDetails.setFlag(true);
			resultDetails
					.setWarningMessage("Did not find the link with special characters");
		} catch (Throwable e) {
			System.out.println(e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Link :: NOT FOUND");
		}
	}
}
