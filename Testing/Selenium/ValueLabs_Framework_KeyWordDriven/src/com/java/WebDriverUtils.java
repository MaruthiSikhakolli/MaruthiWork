package com.java;

import java.util.List;
import java.util.Set;
import org.apache.commons.lang.RandomStringUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.WebDriverBackedSelenium;
import com.thoughtworks.selenium.Selenium;

public class WebDriverUtils {

	// this method makes the driver wait until the element is present
	public static WebElement getWebElement(WebDriver webdriver,
			final By byLocator, int seconds) throws Exception {
		WebElement webElement = null;
		System.out.println("Waiting for element - " + byLocator.toString()
				+ " - to present.....");
		try {
			webElement = (new WebDriverWait(webdriver, seconds))
					.until(new ExpectedCondition<WebElement>() {
						public WebElement apply(WebDriver d) {
							return d.findElement(byLocator);
						}
					});

		} catch (Exception e) {
			System.out.println("Timed-out waiting for element - "
					+ byLocator.toString() + " - to present.");
		}
		return webElement;

	}

	// to identify whether the locator is an id or xpath or link, etc.
	public static By locatorToByObj(WebDriver webdriver, String locator) {

		if (locator.startsWith("css="))
			locator = locator.substring(4, locator.length());
		if (locator.startsWith("xpath="))
			locator = locator.substring(6, locator.length());
		if (locator.startsWith("class="))
			locator = locator.substring(6, locator.length());
		try {
			webdriver.findElement(By.xpath(locator));
			System.out.println("Element by xpath");
			return By.xpath(locator);
		} catch (Throwable e) {
		}

		try {
			webdriver.findElement(By.id(locator));
			System.out.println("Element by id");
			return By.id(locator);
		} catch (Throwable e) {
		}

		try {
			webdriver.findElement(By.linkText(locator));
			System.out.println("Element by linktext");
			return By.linkText(locator);
		} catch (Throwable e) {
		}

		try {
			webdriver.findElement(By.cssSelector(locator));
			System.out.println("Element by cssSelector");
			return By.cssSelector(locator);
		} catch (Throwable e) {
		}

		try {
			webdriver.findElement(By.name(locator));
			System.out.println("Element by name");
			return By.name(locator);
		} catch (Throwable e) {
		}

		try {
			webdriver.findElement(By.className(locator));
			System.out.println("Element by className");
			return By.className(locator);
		} catch (Throwable e) {
		}

//		try {
//			webdriver.findElement(By.xpath("//a[contains(text(),'" + locator
//					+ "')]")); // this is for objects without linkText. for eg:
//								// Home button
//			return By.xpath("//a[contains(text(),'" + locator + "')]");
//		} catch (Throwable e) {
//		}

		return null;
	}
	
	
//	public static int[] ReturnRowColumn(WebDriver webdriver,String locator,String value)
	//{
	//	WebElement webele=webdriver.findElement(locatorToByObj(webdriver,locator));
		//webele.findElements("//tr");
	//}

	// waits for an element to be present and returns true if the element is
	// present, else returns false
	public static boolean isElementPresent(WebDriver webdriver,
			final By byLocator, int seconds) throws Exception {

		if ((new WebDriverWait(webdriver, seconds))
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return d.findElement(byLocator);
					}
				}) == null)
			return false;
		else
			return true;
	}

	// returns true if the element is present, else returns false
	public static boolean isElementPresent(WebDriver webdriver, String locator) {
		if (locatorToByObj(webdriver, locator) != null)
			return true;
		else
			return false;
	}

	// Wait for element to appear and click
	public static void waitAndClick(WebDriver webdriver, final By byLocator,
			int timeOut) throws Exception {
		WebElement element = getWebElement(webdriver, byLocator, timeOut);
		System.out.println("Click ->" + byLocator.toString());
		element.click();
	}

	// Returns the message of the alert/confirmation box
	// Note: Web Driver handles both alert and confirmation boxes in same way
	public static String getAlert(WebDriver webdriver) {
		String alertText;
		String currentWindowHandle = webdriver.getWindowHandle();// store
																	// current
																	// window
																	// handle
		Alert alert = webdriver.switchTo().alert();
		// Get the text from the alert/confirmation
		alertText = alert.getText();
		webdriver.switchTo().window(currentWindowHandle);// switch back to the
			System.out.println("sdf:"+alertText);												// current window
		return alertText;
	}

	// Returns the selected label of a combo box/list
	public static String getSelectedLabel(WebDriver webdriver, String locator) {
		Select select = new Select(webdriver.findElement(locatorToByObj(
				webdriver, locator)));
		System.out.println("Selected Value 1 "
				+ select.getFirstSelectedOption().getText());
		return select.getFirstSelectedOption().getText();
	}

	// Returns the selected options of a combo box/list
	public static String[] getSelectedOptions(WebDriver webdriver,
			String locator) {
		Select select = new Select(webdriver.findElement(locatorToByObj(
				webdriver, locator)));
		String[] selectOptions = new String[select.getAllSelectedOptions()
				.size()];

		for (int i = 0; i < selectOptions.length; i++)
			selectOptions[i] = select.getAllSelectedOptions().toArray()[i]
					.toString();

		return selectOptions;
	}

	// Selects the given option based on visible text/label -- also works as
	// addSelection( )
	public static void select(WebDriver webdriver, String locator, String option) {
		try {
			Select select = new Select(webdriver.findElement(locatorToByObj(
					webdriver, locator)));
			select.selectByVisibleText(option);
		} catch (Exception e) {
			Select select = new Select(webdriver.findElement(locatorToByObj(
					webdriver, locator)));
			List<WebElement> elem = select.getOptions();
			System.out.println(option + "This is sent value");
			for (WebElement item : elem) {
				System.out.println(item.getText().replaceAll("/n", "").trim()+ "this is option text");
				if (item.getText().replaceAll("/n", "").trim().replaceAll(" ", "").contains(option.replaceAll(" ", "")))
				{
					item.click();
					break;
				}
			}
		}
	}

	// Selects the given option based on its index -- also works as
	// addSelection( )
	public static void selectByIndex(WebDriver webdriver, String locator,
			int option) {
		Select select = new Select(webdriver.findElement(locatorToByObj(
				webdriver, locator)));
		select.selectByIndex(option);
	}

	// Returns the text content of a specific cell
	public static String getTable(WebDriver webdriver, String tableLocator,
			String rowNum, String colNum) {
		WebElement table = webdriver.findElement(locatorToByObj(webdriver,
				tableLocator));
		java.util.List<WebElement> tr_collection = table.findElements(By
				.xpath("//tr"));
		int row_num = 1;

		if (!tr_collection.isEmpty() && tr_collection.size() >= 1)
			for (WebElement trElement : tr_collection) {
				java.util.List<WebElement> td_collection = trElement
						.findElements(By.xpath("//tr[" + row_num + "]/td"));
				int col_num = 1;
				if (!td_collection.isEmpty() && td_collection.size() >= 1)
					for (WebElement tdElement : td_collection) {
						if ((Integer.toString(row_num).equalsIgnoreCase(rowNum))
								&& (Integer.toString(col_num)
										.equalsIgnoreCase(colNum)))
							return tdElement.getText();
						col_num++;
					}
				row_num++;
			}
		return null;
	}

	// To wait for the element to be present
	public static void waitForElementToPresent(WebDriver webdriver,
			final By byLocator, int seconds) throws Exception {

		if ((new WebDriverWait(webdriver, seconds))
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return d.findElement(byLocator);
					}
				}) == null)
			throw new Exception("Timed-out waiting for - "
					+ byLocator.toString() + " - element to present...");

	}

	// Switches the focus to the window specified
	public static void selectWindow(WebDriver webdriver, String windowTitle) {
		WebDriver popup = null;
		Set<String> windowHandles = webdriver.getWindowHandles();
		for (int i = 0; i < windowHandles.size(); i++) {
			popup = webdriver.switchTo().window(
					windowHandles.toArray()[i].toString());
			if (popup.getTitle().equals(windowTitle))
				break;
		}
	}

	// Waits for the page to load completely
	// timeOut should be in milliseconds
	public static void waitForPageToLoad(WebDriver webdriver, String timeOut) {

		Selenium sel = new WebDriverBackedSelenium(webdriver,
				webdriver.getCurrentUrl());
		sel.waitForPageToLoad(timeOut);
		if (webdriver.getCurrentUrl().contains(SeleniumDriverTest.Environ))
			System.out.println("Environment " + SeleniumDriverTest.Environ
					+ "  is not dropped");
		else if (webdriver.getCurrentUrl().contains("medscape")
				|| webdriver.getCurrentUrl().contains("webmd")
				|| webdriver.getCurrentUrl().contains("wbmd")
				|| webdriver.getCurrentUrl().contains("medicine")
				|| webdriver.getCurrentUrl().contains("rxlist")
				||webdriver.getCurrentUrl().contains("preview")) {
			String cururl = webdriver.getCurrentUrl();
			// String
			// navurl="http://www."+SeleniumDriver.Environ+"."+cururl.substring(cururl.indexOf("www.")+4);
			String url[] = cururl.split("\\.", 2);
			String navurl = url[0] + "." + SeleniumDriverTest.Environ + "."
					+ url[1];
			webdriver.navigate().to(navurl);
		}
	}

	// Returns true if the text is present else returns false
	public static boolean isTextPresent(WebDriver webdriver, String text) {
		Selenium sel = new WebDriverBackedSelenium(webdriver,
				webdriver.getCurrentUrl());
		return sel.isTextPresent(text);
	}
}
