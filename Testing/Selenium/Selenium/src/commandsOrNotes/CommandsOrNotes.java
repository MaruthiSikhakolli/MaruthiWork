package commandsOrNotes;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class CommandsOrNotes {

	public static void main(String[] args) {
		try {
		// TODO Auto-generated method stub
			
		//Firefox Driver
		WebDriver driver = new FirefoxDriver();
		
		//Chrome Driver
		System.setProperty("webdriver.chrome.driver", "driver .exe file path");
		WebDriver chromeDriver = new ChromeDriver();
		
		//IE Driver
		System.setProperty("webdriver.ie.driver", "driver .exe file path");
		WebDriver IEDriver = new InternetExplorerDriver();
		
		//sendkeys() - Enters a value into Edit box/Text box
		driver.findElement(By.id("Email")).sendKeys("India");
		
		//Finding element by id:
		WebElement Email = driver.findElement(By.id("EmailID"));
		Email.sendKeys("email@gmail.com");
		
		//Finding element by name:
		WebElement e = driver.findElement(By.name("Email"));
		e.sendKeys("Maruthi");
		
		//Finding element by className:
		WebElement e2 = driver.findElement(By.className("textboxcolor"));
		e2.sendKeys("Hyderabad");
		
		//Finding element by tagName:
		WebElement e3 = driver.findElement(By.tagName("textboxcolor"));
		e3.sendKeys("Hyderabad");
		
		//Finding element by linkText:
		WebElement e4 = driver.findElement(By.linkText("Gmail"));
		e4.click();
		
		//Finding element by partialLinkText:
		WebElement e5 = driver.findElement(By.partialLinkText("Gma"));
		e5.click();
		
		//Finding element by CSSSelector:
		WebElement e6 = driver.findElement(By.cssSelector(".gb_m"));
		e6.click();
		
		//Finding element by XPATH:
		WebElement e7 = driver.findElement(By.xpath(".//*[@id='Email']"));
		e7.click();
		
		//To open a URL in browser:
		driver.get("https://www.google.co.in");
		
		//getTitle() - Returns Title of the Browser.
		driver.get("https://www.google.co.in");
		String Title = driver.getTitle();
		System.out.println(Title);
		
		//getPageSource() - Returns HTML page source.
		driver.get("https://www.google.co.in");
		String pageSource = driver.getPageSource();
		System.out.println(pageSource);
		
		//getCurrentUrl() - Returns Current URL of the Browser.
		driver.get("https://www.google.co.in");
		String URL = driver.getCurrentUrl();
		System.out.println(URL);
		
		//Browser Navigation Methods
		//navigate().to() - Loads a new web page in the current browser window.
		driver.navigate().to("https://login.yahoo.com/");
		URL = driver.getCurrentUrl();
		System.out.println(URL);
		
		//navigate().back() - It moves a single item back in the Browser history.
		driver.navigate().to("https://login.yahoo.com/");
		URL = driver.getCurrentUrl();
		System.out.println(URL);
		driver.navigate().back();
		URL = driver.getCurrentUrl();
		System.out.println(URL);
		
		//navigate().forward() - It moves single item forward in the Browser history.
		driver.navigate().to("https://login.yahoo.com/");
		URL = driver.getCurrentUrl();
		System.out.println(URL);

		driver.navigate().back();
		URL = driver.getCurrentUrl();
		System.out.println(URL);

		driver.navigate().forward();
		URL = driver.getCurrentUrl();
		System.out.println(URL);
		
		//navigate().refresh() - Refresh the current web page
		driver.get("https://www.google.co.in");
		URL = driver.getCurrentUrl();
		System.out.println(URL);
		driver.navigate().refresh();
		URL = driver.getCurrentUrl();
		System.out.println(URL);
		
		//Difference between navigate and get methods
		//Get : 
		//1. Waits till complete page loads. 
		//2. can not move forward & backward in the browser.
		//Navigate : 
		//1. Will not wait until the page loads, you can feel this experience only if page takes time to load, like more number of images or ajax calls etc......
		//2. can move forward & backward in the browser.
		
		//Close and Quit methods
		//close() - It closes the focused Browser.
		driver.get("https://www.google.co.in");
		driver.close();
		
		//quit() - It closes all browser that opened by WebDriver during execution.
		driver.get("file:///C:/Users/gcreddy/Desktop/HTMLExamples/LoginPage.html");
		driver.quit();
		
		//findElement() - It finds the first element within the current page using the give locator.
		driver.get("file:///C:/Users/gcreddy/Desktop/HTMLExamples/LoginPage.html");
		driver.findElement(By.tagName("input")).sendKeys("abcd");
		
		//findElements() - It finds all the element within the current page using the give locator.
		driver.get("file:///C:/Users/gcreddy/Desktop/HTMLExamples/LoginPage.html");
		List<WebElement> elements = driver.findElements(By.tagName("input"));
		System.out.println(elements.size());
		
		//clear() - It clears the value of the corresponding text box or text area
		driver.get("https://www.gmail.com");
		driver.findElement(By.id("Email")).sendKeys("India");
		driver.findElement(By.id("Email")).clear();
		
		//click() - Clicks an Element (Buttons, Links)
		driver.get("https://www.gmail.com");
		driver.findElement(By.id("next")).click();
		
		//isEnabled() - It checks weather the Element is in enabled state or not
		driver.get("https://www.gmail.com");
		boolean a = driver.findElement(By.id("next")).isEnabled();
		System.out.println(a);
		
		//isDisplayed() - Checks if the Element is displayed or not? in the current web page.
		driver.get("https://www.gmail.com");
		boolean a2 = driver.findElement(By.id("next")).isDisplayed();
		System.out.println(a2);

		//isSelected() - checks if the Element is Selected or not? in the current web page.
		driver.get("file:///C:/Users/gcreddy/Desktop/HTMLExamples/MultipleCheckbox.html");
		boolean a3 = driver.findElement(By.xpath("html/body/input[2]")).isSelected();
		System.out.println(a3);//false
		driver.findElement(By.xpath("html/body/input[2]")).click();
		a3 = driver.findElement(By.xpath("html/body/input[2]")).isSelected();
		System.out.println(a3);//true
		
		//manage().window().maximize() - To maximize the current browser window
		driver.get("file:///C:/Users/gcreddy/Desktop/HTMLExamples/MultipleCheckbox.html");
		driver.manage().window().maximize();
		
		//getText() - To get the text from textbox or area
		WebElement e8 = driver.findElement(By.xpath("html/body/input[2]"));
		String text = e8.getText();
		System.out.println(text);
		
		//getAttribute() - To get the values or other attributes of an element
		WebElement e9 = driver.findElement(By.xpath("html/body/input[2]"));
		String text2 = e9.getAttribute("value");
		System.out.println(text2);
		
		//Handling drop down box
		driver.get("http://www.gcrit.com/build3/create_account.php?osCsid=47gtsrhe41613u5r3eqhgdbas7");
		Select dropDown = new Select (driver.findElement(By.name("country")));
		dropDown.selectByIndex(6);//Select an item by index
		dropDown.selectByVisibleText("India");

		List<WebElement> e10 = dropDown.getOptions();
		int itemsCount = e10.size();
		System.out.println(itemsCount);
		
		//Handling tables
		driver.get("file:///E:/HTMLExamples/htmlTable.html");
		String s = driver.findElement(By.xpath(".//*[@id='students']/tbody/tr[2]/td[2]")).getText();
		System.out.println(s);
		WebElement htmlTable = driver.findElement(By.id("students"));

		List <WebElement> rows = htmlTable.findElements(By.tagName("tr"));
		int r = rows.size();
		System.out.println(r);

		List <WebElement> cells = htmlTable.findElements(By.tagName("td"));
		int c = cells.size();
		System.out.println(c);
		
		//Handling frames
		//Using frame index
		driver.get("http://seleniumhq.github.io/selenium/docs/api/java/index.html");
		driver.switchTo().frame(2); //Frame index starts from 0
		driver.findElement(By.xpath("html/body/div[3]/table/tbody[2]/tr[1]/td[1]/a")).click();
		
		//Using frame name
		driver.get("http://seleniumhq.github.io/selenium/docs/api/java/index.html");
		driver.switchTo().frame("classFrame");
		driver.findElement(By.xpath("html/body/div[3]/table/tbody[2]/tr[1]/td[1]/a")).click();
		
		//Switching from a frame to top window
		driver.switchTo().defaultContent();
		
		//Handle Mouse hover
		driver.get("http://www.carmax.com/");
		//create Action builder instance by passing WebDriver instance
		Actions builder = new Actions(driver);
		WebElement menuElement = driver.findElement(By.linkText("Sell Us Your Car"));
		builder.moveToElement(menuElement).build().perform();
		driver.findElement(By.linkText("FAQ")).click();
		
		//Switching between multiple browser windows
		String parent = driver.getWindowHandle(); //getWindowHandle returns current window handle (Handle is nothing but unique ID of window)
		System.out.println(parent);

		Set <String> Handles = driver.getWindowHandles(); //getWindowHandles returns all the handles of the windows
		int BrowserCount = Handles.size();
		System.out.println(BrowserCount);

		for (String s1:Handles){
		if (!s1.equals(parent)){
		driver.switchTo().window(s1);
		System.out.println(driver.getCurrentUrl());
		}}
		driver.switchTo().window(parent);
		System.out.println(driver.getCurrentUrl());
		
		//Login into any site if its showing any authentication pop-up for user name and password - Pass the username and password with url.
		//Syntax : driver.get(http://username:password@url);
		driver.get("http://creyate:jamesbond007@alpha.creyate.com");
		
		//How do you send key presses in WebDriver ?
		WebDriver driver2 = new FirefoxDriver();
		Actions act = new Actions(driver2);
		act.sendKeys(Keys.ENTER);
		act.sendKeys(Keys.RETURN);
		act.sendKeys(Keys.BACK_SPACE);
		act.sendKeys(Keys.ARROW_RIGHT);
		act.sendKeys(Keys.ESCAPE);
		act.sendKeys(Keys.F8);
		act.sendKeys(Keys.LEFT_SHIFT);
		act.sendKeys(Keys.NUMPAD2);
		act.sendKeys(Keys.TAB);
		
		//How to press Shift+Tab ?
		String press = Keys.chord(Keys.SHIFT,Keys.ENTER);
		WebElement webElement = driver.findElement(By.linkText("FAQ"));
		webElement.sendKeys(press);
		
		//How to perform double click using WebDriver ?
		Actions action = new Actions(driver);
		WebElement webElement2 = driver.findElement(By.linkText("FAQ"));
		action.doubleClick(webElement2);
		
		//How to right click using webdriver ?
		WebElement webElement3 = driver.findElement(By.id("Id"));
		Actions action2 = new Actions(driver);
		action2.moveToElement(webElement3).contextClick();
		
		//How to handle internationalization through web driver?
		FirefoxProfile profile2 = new FirefoxProfile();
		profile2.setPreference("intl.accept_languages","jp");
		FirefoxOptions options2 = new FirefoxOptions();
		options2.setProfile(profile2);
		WebDriver driver3 = new FirefoxDriver(options2); 
		driver3.get("www.google.com"); //will open google in Japanese Language
		
		//How to overcome same origin policy through web driver?
		FirefoxOptions options3 = new FirefoxOptions();
		options3.setCapability(CapabilityType.PROXY,"your desired proxy");
		WebDriver driver4 = new FirefoxDriver(options3); 
		driver4.get("http://www.google.com");
		
		//How to get the name of browser using Web Driver?
		String browserName = (String)((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
		System.out.println("Browser name : " + browserName);
		
		//How to change user agent in Firefox by selenium web driver.
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("general.useragent.override", "some UA string");
		FirefoxOptions options4 = new FirefoxOptions();
		options4.setProfile(profile);
		WebDriver driver5 = new FirefoxDriver(options2); 
		driver5.get("www.google.com");
		
		//How to Drag & Drop an Element using selenium?
		WebElement draggable = driver.findElement(By.xpath("//*[@id='draggable']"));
		WebElement droppable = driver.findElement(By.xpath("//*[@id='droppable']"));
		Actions action1 = new Actions(driver);
		action1.dragAndDrop(draggable, droppable).perform();
		
		//Sliding an Element
		WebElement slider = driver.findElement(By.xpath("//*[@id='slider']/a"));
		Actions action3 = new Actions(driver);
		action3.dragAndDropBy(slider, 90, 0).perform();
		
		//Scrolling the page
		WebDriver driver6 = new FirefoxDriver();
		JavascriptExecutor jse = (JavascriptExecutor)driver6;
		jse.executeScript("scroll(0,400)"); //Vertical Scrolling
		jse.executeScript("scroll(400,0)"); //Horizantal Scrolling
		
		//Re-sizing an Element:
		WebElement resize = driver.findElement(By.xpath("//*[@id='resizable']/div[3]"));
		Actions action4 = new Actions(driver);
		action4.dragAndDropBy(resize, 400, 200).perform();
		
		//How can we get the font size, font color, font type used for a particular text on a web page using Selenium web driver?
		driver.findElement(By.xpath("xpath of element")).getCssValue("font-size");
		driver.findElement(By.xpath("xpath of element")).getCssValue("font-colour");
		driver.findElement(By.xpath("xpath of element")).getCssValue("font-type");
		driver.findElement(By.xpath("xpath of element")).getCssValue("background-colour");
		
		//How will you start the selenium server from your Java class?
//				try {
//					seleniumServer = new SeleniumServer();
//					seleniumServer.start();
//				} catch (Exception exception2) {
//					exception2.printStackTrace();
//				}
		
		//Handling web alerts
		Alert alert = driver.switchTo().alert();
		alert.accept();
		alert.dismiss();
		alert.getText();
		
		//How to take a screenshot with Selenium WebDriver?
		//Method-1 - By casting the webDriver object
		driver.get("http://www.google.com/");
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("c:\\tmp\\screenshot.png"));
		
		//Method-2 - By using EventFiringWebDriver
		EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
		File scrFile2 = eventDriver.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile2, new File("c:\\tmp\\screenshot.png"));
		
		//How to upload a file using selenium?
		//We cannot do window actions with selenium. Hence we use third party apps like AutoIt
		driver.findElement(By.name("img")).click();    
		//To execute autoIt script .exe file which Is located at E:\\AutoIT\\ location.
		Runtime.getRuntime().exec("E:\\AutoIT\\Script To Upload File.exe"); //We need to code the steps to handle the windows in auto it script
		
		//Using PROXY (Example defined with IE browser):
		String PROXY = "localhost:8080";
		
		org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
		
		InternetExplorerOptions IEOptions = new InternetExplorerOptions();
		IEOptions.setCapability(CapabilityType.PROXY, proxy);
		WebDriver driver7 = new InternetExplorerDriver(IEOptions);
		
		//Different Xpath examples
		//using single attribute
		driver.findElement(By.xpath("//a[@id='id1']"));
		//using multiple attribute
		driver.findElement(By.xpath("//a[@id='id1'][@name='namevalue1']"));
		//using contains method
		driver.findElement(By.xpath("//input[contains(@id,'button')]"));
		//Using starts-with method
		driver.findElement(By.xpath("//input[starts-with(@id,'button')]"));
		//Using ends-with method
		driver.findElement(By.xpath("//input[ends-with(@id,'button')]"));
		//Using Following node
		driver.findElement(By.xpath("//input[@id,'buttonID1']/following::div[@id,'buttonID2']"));
		//Using preceding node
		driver.findElement(By.xpath("//input[@id,'buttonID1']/preceding::div[@id,'buttonID2']"));
		//Using Following-sibling node
		driver.findElement(By.xpath("//input[@id,'buttonID1']/following-sibling::div[@id,'buttonID2']"));
		//Using preceding-sibling node
		driver.findElement(By.xpath("//input[@id,'buttonID1']/preceding-sibling::div[@id,'buttonID2']"));
		//Using parent
		driver.findElement(By.xpath("//input[@id,'buttonID1']/parent"));
		//OR
		driver.findElement(By.xpath("//input[@id,'buttonID1']/.."));
		//Using Child
		driver.findElement(By.xpath("//input[@id,'buttonID1']/child"));
		//Ancestor 
		driver.findElement(By.xpath("//input[@id,'buttonID1']/ancestor::div[@id,'buttonID2']"));
		//Descendant 
		driver.findElement(By.xpath("//input[@id,'buttonID1']/descendant::div[@id,'buttonID2']"));
		//Absolute XPath method
		driver.findElement(By.xpath("/html/head/body/div/input"));
		//Relative XPath method
		driver.findElement(By.xpath("//a[@id='id1']"));
		//Relative and Absolute Xpath method
		driver.findElement(By.xpath("//input[@id=’section’]/div/input"));
		
		
		//How to wait for a certain time for the particular step
		try {
			driver.findElement(By.id("Id")).click();
			Thread.sleep(2000); //Pauses the execution for 2000 Milli seconds (2 Seconds) and then resumes the next step
			driver.findElement(By.id("Id2")).click();
		}catch(Exception exception) {
			System.out.println(e);
		}
		
		//How to set speed of selenium?
		//selenium.setSpeed("2000") //will wait for 2 seconds for every step execution --But long ago this method was depricated. We can use below wait class functions 
		//to set speed in selenium
		
		//Implicit wait
		//Once set, the implicit wait is set for the life of the WebDriver object instance.
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Explicit wait
		WebDriverWait wait = new WebDriverWait(driver,10);
		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id("ID of element")));
		button.click();
		
		//Fluent wait
		Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(100))
				.pollingEvery(Duration.ofMillis(600))
			    .ignoring(NoSuchElementException.class);
			
		WebElement foo = fluentWait.until(new Function<WebDriver, WebElement>() 
			{
			  public WebElement apply(WebDriver driver) {
			  return driver.findElement(By.id("foo"));
			}
			});
		}
		catch(Exception mainException) {
			System.out.println(mainException);
		}
	}
	
	//Enter data into text box without send keys
	public static void setAttribute(WebElement element, String attributeName, String value)
	    {
	        WrapsDriver wrappedElement = (WrapsDriver) element;
	        JavascriptExecutor driver = (JavascriptExecutor)wrappedElement.getWrappedDriver();
	        driver.executeScript("arguments[0].setAttribute(arguments[1],arguments[2])", element, attributeName, value);
	    }
	
	//Handle HTTPS website in Selenium by changing the setting of FirefoxProfile.
	public static void HTTPSSecuredConnection() {
		ProfilesIni profile = new ProfilesIni();
		FirefoxProfile myprofile = profile.getProfile("profileToolsQA"); //profileToolsQA is a firefox profile created
		FirefoxOptions options = new FirefoxOptions();
		options.setProfile(myprofile);
		WebDriver driver2 = new FirefoxDriver(options);
		myprofile.setAcceptUntrustedCertificates(false);
		driver2.get("https://www.google.co.in/");
		}
	
	//Open a browser in memory means whenever it will try to open a browser the browser page must not come and can perform the operation internally.
	//Ans: use HtmlUnitDriver.
	public void htmlUnitDriver(){
		HtmlUnitDriver driver = new HtmlUnitDriver(true);
		driver.setJavascriptEnabled(false);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.google.co.in/");
		System.out.println(driver.getTitle());
	}
}
