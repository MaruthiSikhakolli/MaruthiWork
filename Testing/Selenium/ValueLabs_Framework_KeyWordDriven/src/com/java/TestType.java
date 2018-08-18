package com.java;

import java.awt.Color;
import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import com.java.ImportnExport.ExportTestResultsExcel;
import com.java.Objects.ResultDetails;
import com.thoughtworks.selenium.Selenium;

public class TestType extends TestCase {
	public static String TempValue;
	private static Logger log = Logger.getLogger(TestType.class.getName());
	public WebDriver webdriver = null;
	public Selenium selenium = null;
	public boolean isKeywordFound;
	public boolean isKeywordInTestType = true;
	public Actions actions = null;
	public static String json = "";
	public static String capturedValues = "";
	public static String defaultTimeout = "120000";
	ADTag AD=new ADTag();
	public TestType() {

		// super();
	}

	ResultDetails resultDetails = new ResultDetails();

	// enum to store type of fields in the form
	public enum DataFileds {
		TXT, RDB, COB, CHK, SLB, BTN, LNK, CNF, XPH, CBS, DBV, TTL, ALT, MSG, WND, IMG, GET, TBL, EDT, STR, POP, REM, KYV, NAV, VAL, BTW, URL,TBLTXT, DTE, DTV, CKI, DDL
	, VED
	};


	public enum SelectDataFileds {
		RDB, COB, SLB, WND
	};

	public enum ClickDataFileds {
		BTN, LNK, CNF, XPH, IMG, FUL,RND
	};

	public enum CheckDataFileds {
		CHK
	};

	public enum EnterDataFileds {
		TXT, BTN, EDT, TXD, RLC
	}; // EDT is Text Editor

	public enum WaitForFields {
		IMG, TTL, BTN, LNK, COB, MSG, TXT, XPH
	};

	public enum SelectWindow {
		TTL
	};

	public enum CloseWindow {
		TTL
	};

	public enum goBack {
		TTL
	};

	public enum DBDataFileds {
		DBD, DBU, DBL, DBI
	};

	public enum Country {
		UK, DE
	};

	public enum Tables {
		TBL
	};
	
	public enum Order {
		INC, DEC
	};

	public enum ActionTypes {
		VERIFY, VERIFYNOTPRESENT, SELECT, SELECTANDWAIT, SELECTFRAME, ENTER, CLEARANDENTER, 
		CLICK, CLICKANDWAIT, CHECK, UNCHECK, WAITFORELEMENT, SELECTWINDOW, DB, CLOSEWINDOW, 
		GOBACK, ISDISABLED, ISENABLED, STOREVALUE, KEYPRESS, OPENURL, STOREATTRIBUTE, 
		VERIFYATTRIBUTE, WAITTIME, VERIFYCONTINUE, MOUSEOVER, CLICKTABLECPP, OITARGET, 
		OIATTRIB, OUCPDATE, MONGOSAVEUSER, MONGOINSERTSAVED, REFRESH, JSFILEUPLOAD, ALPHAORDER, 
		DELETECOOKIES, OVERIFY, BROWSERCLOSEANDOPEN, CLICKHIDDEN,ADTAGNEW,VERIFYADTAG,ADTAGUPDATE,
OGIFTMGR, ADDTITLE, APPENDURL,AUTOIT,DBEXECUTE,DBVERIFY, VERIFYSUGGESTIONLIST, STRINGMANIPULATE, 
UPDATEVALUE ,RESPONSETIME,CLICKTABLEOBJECT, VERIFYORDER, DRAGANDDROP, CLEAR, IF,VERIFYCOLOR,
VERIFYCSS, GETDBDATA,VERIFYBETWEENDATES,FREPLACECHRCHR,FCOPYDIR,FREPLACEALL, ISSELECTED, OCLICK, 
OENTER, OMOUSEOVER, OCLICKANDWAIT, OOPENURL, VERIFYOMNITURE, COMPAREURL, GETOMNITURE, COMPAREOMNITURE, ODELETECOOKIES, ADDHOSTENTRY, REMOVEHOSTENTRY, 
OSELECT
	};

	public ResultDetails performAction(String fieldText, String value,
			String actionType, String fieldName) {
		try {
			ActionTypes actTypes = ActionTypes
					.valueOf(actionType.toUpperCase());
			isKeywordFound = true;
			switch (actTypes) {
			
			/*###################################################################################
			 * Omniture Keywords
			 *#################################################################################*/
			
			case OSELECT:
				try {
					value = GETVALUE(value);
					selenium.select(fieldText, value);
					Thread.sleep(3000);
					capturedValues = "";
					capturedValues = selenium.captureNetworkTraffic("json");
					resultDetails.setFlag(true);
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
				}
				break;
			
			case ODELETECOOKIES:
				try {
					selenium.deleteAllVisibleCookies();
					Thread.sleep(2000);
					resultDetails.setFlag(true);
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
				}
				break;
			
			case GETOMNITURE:
				try {
				//	SeleniumDriverTest.hMap.put(value, Omniture.getInstance().decodeOmnitureValues(capturedValues, selenium));
					resultDetails.setFlag(true);
				} catch(Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
				
		/*	case COMPAREOMNITURE:
				try {
					Omniture.getInstance().setOutputUrl(fieldText, GETVALUE(value.split("::")[0]), GETVALUE(value.split("::")[1]), fieldName);
					resultDetails.setFlag(true);
				} catch(Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
			
			case COMPAREURL:
				try {
					String json1="",json2="";
					selenium.open(value.split("::")[0]);
					selenium.waitForPageToLoad(defaultTimeout);
					Thread.sleep(3000);
					String j1 = selenium.captureNetworkTraffic("json");
					json1 = "";
					//json1 = Omniture.getInstance().decodeOmnitureValues(j1, selenium);
					Runtime.getRuntime().exec("cmd /k echo "+j1+">d:/json1.txt");
					selenium.open(value.split("::")[1]);
					selenium.waitForPageToLoad(defaultTimeout);
					Thread.sleep(3000);
					String j2 = selenium.captureNetworkTraffic("json");
					json2 = "";
					json2 = Omniture.getInstance().decodeOmnitureValues(j2, selenium);
					Runtime.getRuntime().exec("cmd /k echo "+j2+">d:/json2.txt");
					Omniture.getInstance().setOutputUrl(value, json1, json2, fieldName);
					resultDetails.setFlag(true);
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
				}
				break;
			
			case VERIFYOMNITURE:
				try {
					json = Omniture.getInstance().decodeOmnitureValues(capturedValues, selenium);
					Omniture.getInstance().setOutputUrl(value,json,fieldName);
					resultDetails.setFlag(true);
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
				}
				break;
			
			case OOPENURL:
				try {
					selenium.open(fieldText);
					if (!value.equalsIgnoreCase(""))
						selenium.waitForPageToLoad(value);
					else
						selenium.waitForPageToLoad(defaultTimeout);
					Thread.sleep(3000);
					Omniture.getInstance().strURL = fieldText;
					capturedValues = "";
					capturedValues = selenium.captureNetworkTraffic("json");
					resultDetails.setFlag(true);
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
				}
				break;*/
				
			case OCLICK:
				try {
					selenium.click(fieldText);
					Thread.sleep(3000);
					capturedValues = "";
					capturedValues = selenium.captureNetworkTraffic("json");
					resultDetails.setFlag(true);
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
				}
				break;
				
			case OCLICKANDWAIT:
				try {
					selenium.click(fieldText);
					if (!value.equalsIgnoreCase(""))
						selenium.waitForPageToLoad(value);
					else
						selenium.waitForPageToLoad(defaultTimeout);
					Thread.sleep(000);
					capturedValues = "";
					capturedValues = selenium.captureNetworkTraffic("json");
					resultDetails.setFlag(true);
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
					System.out.println(e.getMessage());
				}
				break;
				
			case OENTER:
				try {
					selenium.type(fieldText, value);
					Thread.sleep(3000);
					capturedValues = "";
					capturedValues = selenium.captureNetworkTraffic("json");
					resultDetails.setFlag(true);
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
				}
				break;
				
			case OMOUSEOVER:
				try {
					selenium.mouseOver(fieldText);
					Thread.sleep(3000);
					capturedValues = "";
					capturedValues = selenium.captureNetworkTraffic("json");
					resultDetails.setFlag(true);
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
				}
				break;
				
			case ADDHOSTENTRY:
				try {
					File file = new File("./r");
		        	String path = file.getCanonicalPath();
		        	path = path.substring(0,path.length()-1)+"TestInputs/";
					Runtime.getRuntime().exec("cmd /k echo "+value+">"+path+"hostEntry");
					Runtime.getRuntime().exec("cmd /k start "+path+"hostentry.bat");
					resultDetails.setFlag(true);
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
				}
				break;
				
			case REMOVEHOSTENTRY:
				try {
					File file = new File("./r");
		        	String path = file.getCanonicalPath();
		        	path = path.substring(0,path.length()-1)+"TestInputs/";
		        	Runtime.getRuntime().exec("cmd /k start "+path+"hostexit.bat");
					resultDetails.setFlag(true);
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
				}
				break;
			
			/*###################################################################################
			 * Omniture Keywords
			 *#################################################################################*/
			
			case IF:
				ifKeyword(webdriver, fieldText, value,fieldName);
				break;
				
			case ISSELECTED:
				isSelected(webdriver, fieldText, value, fieldName);
				break;
				
			case VERIFYCOLOR:
				try {
					VerifyColor(webdriver, fieldText,value);
					
				}  catch(Exception e) {
					resultDetails.setFlag(false);
					e.printStackTrace();
					resultDetails.setErrorMessage("Unable to verify colors");
					System.out.println(e.getMessage());
				}
				break;
			case VERIFYCSS:
				try {
					VerifyCss(webdriver, fieldText,value);
					
				}  catch(Exception e) {
					resultDetails.setFlag(false);
					e.printStackTrace();
					resultDetails.setErrorMessage("Unable to verify css property");
					System.out.println(e.getMessage());
				}
				break;
			
			case FREPLACEALL:
				try {
					String value1=GETVALUE(value.trim().split(";;")[1]);
				//	FileUtilties fu=new FileUtilties();
					//resultDetails=fu.srchreplaceAll(fieldText,value.trim().split(";;")[0],value1);
					//resultDetails=fu.CopyDir(fieldText, value);
					//resultDetails=fu.replacechrtochr(fieldText, value.trim().split(";;")[0],value.trim().split(";;")[1], value1);
				}  catch(Exception e) {
					resultDetails.setFlag(false);
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
				break;
			case FCOPYDIR:
				try {
				//	FileUtilties fu=new FileUtilties();
				
					//resultDetails=fu.CopyDir(fieldText, value);
					//resultDetails=fu.replacechrtochr(fieldText, value.trim().split(";;")[0],value.trim().split(";;")[1], value1);
				}  catch(Exception e) {
					resultDetails.setFlag(false);
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
				break;
				
			case FREPLACECHRCHR:
				try {
					//FileUtilties fu=new FileUtilties();
				//	System.out.println("Values got from replace are");
					//System.out.println(value.trim().split(";;")[2]);
					//System.out.println(value.trim().split(";;")[1]);
					//System.out.println(value.trim().split(";;")[0]);
					String value1=GETVALUE(value.trim().split(";;")[2]);
					System.out.println(fieldText+"is the path of file");
					System.out.println("Value to replace"+value1);
				//	resultDetails=fu.replacechrtochr(fieldText, value.trim().split(";;")[0],value.trim().split(";;")[1], value1);
				}  catch(Exception e) {
					resultDetails.setFlag(false);
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
				break;	
			/*
			 * It clears the text in given text field Location
			 * Note - Given Element should be Editable
			 */
			case CLEAR:
				fieldText = fieldText.substring(3);
				try {
					webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, fieldText)).clear();
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} catch(NoSuchElementException e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(fieldName+" Not Found");
					System.out.println(fieldName+" :: Not Found");
				} catch(Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Invalid Test Inputs");
					System.out.println(e.getMessage());
				}
				break;
			
			case DRAGANDDROP:
				dragAndDrop(webdriver, fieldText, value, fieldName);
				break;			
			case VERIFYORDER:
				verifyOrder(webdriver, fieldText, value, fieldName);
				break;
			case VERIFYSUGGESTIONLIST:
				resultDetails = verifyAjaxSuggestionlist(webdriver, fieldText, value, fieldName);  
				break;
			case STRINGMANIPULATE:
				resultDetails = stringManipulate(webdriver, fieldText, value,
						fieldName);
				break;
			case UPDATEVALUE:
				resultDetails = updateValue(webdriver, fieldText, value,
						fieldName);
				break;
			case VERIFYBETWEENDATES:
				verifybetweendates(webdriver, fieldText, value, fieldName);
				break;
			case APPENDURL:
				fieldText = GETVALUE(fieldText);
				fieldText = fieldText + GETVALUE(value);
				System.out.println("Append URL : " + fieldText);
				try {
					webdriver.get(fieldText); // url
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} catch (Exception e) {
					System.out.println("exception value : " + e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Window with title   ::+"
							+ value + "::not Found");
					WebDriverUtils.waitForPageToLoad(webdriver, "50000");
				}
				break;
			case DBEXECUTE:
				try {
					System.out.println("Operation on Oracle database");
					OracleDB oDB=new OracleDB(fieldText);
					resultDetails = oDB.oExecute(value);
				} catch (Exception e) {
					e.printStackTrace();
					resultDetails.setErrorMessage(e.getLocalizedMessage());
					resultDetails.setFlag(false);
				}
				break;
			case GETDBDATA:
				try {
					System.out.println("Operation on Oracle database");
					OracleDB oDB=new OracleDB(fieldText);
				//	resultDetails = oDB.getdbdata(value);
				} catch (Exception e) {
					e.printStackTrace();
					resultDetails.setErrorMessage(e.getLocalizedMessage());
					resultDetails.setFlag(false);
				}
				break; 
			case DBVERIFY:
				try {
					if(value.split("::")[2].toUpperCase().contains("SQLSERVER"))
					{
						OracleDB oDB=new OracleDB(fieldText,"SQL");
						resultDetails = oDB.oracleVerify(value);
					}
					else
					{
						OracleDB oDB=new OracleDB(fieldText);
						resultDetails = oDB.oracleVerify(value);
					}
				} catch (Exception e) {
					e.printStackTrace();
					resultDetails.setErrorMessage(e.getLocalizedMessage());
					resultDetails.setFlag(false);
					
				}
				break;
			case CLICKTABLEOBJECT:
				try {
					resultDetails=ClickTableObject(fieldText,value);
					
						//System.out.println(e.getText());
					}
					//resultDetails = oDB.oExecute(value);
			 catch (Exception e) {
					e.printStackTrace();
					resultDetails.setErrorMessage(e.getLocalizedMessage());
					resultDetails.setFlag(false);
				}
				break;	
		case RESPONSETIME:
			try{
				Set<Cookie> cc = null;
				Iterator<Cookie> it = null;
				cc = webdriver.manage().getCookies();
				it = cc.iterator();
				Cookie ck;
				System.out.println("Before adding cookie");
				while (it.hasNext()) {
				ck = it.next();
				System.out.println(ck.toString());
				}
				String name = (String) ((JavascriptExecutor) webdriver).executeScript(value);
				Cookie c = new Cookie("responsetime", name);

				webdriver.manage().addCookie(c);
				System.out.println("After adding cookie");
				cc = webdriver.manage().getCookies();
				it = cc.iterator();
				Cookie ck1;
				while (it.hasNext()) {
				ck1 = it.next();
				System.out.println(ck1.toString());
				}
				resultDetails.setFlag(true);
				} catch (Exception e) {
					e.printStackTrace();
					resultDetails.setErrorMessage(e.getLocalizedMessage());
					resultDetails.setFlag(false);
					
				}
				break;	
			case AUTOIT:
				//SeleniumDriverTest test=new SeleniumDriverTest();
				String IP=SeleniumDriverTest.NodeIp;
				fieldText =GETVALUE(value);
				System.out.println("Mission to execute "+value+" AutoIt script in machine "+IP);
				try {
					SocketClient client=new SocketClient(IP,fieldText);
					resultDetails.setFlag(true);
					System.out.println("Mission completed successfully");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("exception value : " + e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getLocalizedMessage());
					}
				break;					
			case ADDTITLE:
				try {
					System.out.println("Addtitle is called");

					resultDetails = AddTitle(webdriver);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;			
			case CLICKHIDDEN:
				resultDetails = clickHidden(webdriver, fieldText);
				break;
			case ADTAGNEW:
				System.out.println("Adding new values for ADTag from profile Update page");
				resultDetails = ADTAGNEW(webdriver,fieldText);	
			if(webdriver.getCurrentUrl().contains(SeleniumDriverTest.Environ))
					System.out.println("Environment "+SeleniumDriverTest.Environ+ "  is not dropped");
			else
			{
				String cururl=webdriver.getCurrentUrl();
				//String navurl="http://www."+SeleniumDriver.Environ+"."+cururl.substring(cururl.indexOf("www.")+4);
				String url[]=cururl.split("\\.",2);
				String navurl=url[0]+"."+SeleniumDriverTest.Environ+"."+url[1];
				webdriver.navigate().to(navurl);
			}
				break;	
			case ADTAGUPDATE:
				System.out.println("Updating values for ADTag");
				resultDetails = ADTAGUPDATE(webdriver,fieldText);
				if(webdriver.getCurrentUrl().contains(SeleniumDriverTest.Environ))
					System.out.println("Environment "+SeleniumDriverTest.Environ+ "  is not dropped");
			else
			{
				String cururl=webdriver.getCurrentUrl();
				//String navurl="http://www."+SeleniumDriver.Environ+"."+cururl.substring(cururl.indexOf("www.")+4);
				String url[]=cururl.split("\\.",2);
				String navurl=url[0]+"."+SeleniumDriverTest.Environ+"."+url[1];
				webdriver.navigate().to(navurl);
			}
				break;		
			case VERIFYADTAG:
				System.out.println("Entered ADTag case");
				resultDetails = VerifyADTag(webdriver, fieldText);
				if(webdriver.getCurrentUrl().contains(SeleniumDriverTest.Environ))
					System.out.println("Environment "+SeleniumDriverTest.Environ+ "  is not dropped");
			else
			{
				String cururl=webdriver.getCurrentUrl();
				//String navurl="http://www."+SeleniumDriver.Environ+"."+cururl.substring(cururl.indexOf("www.")+4);
				String url[]=cururl.split("\\.",2);
				String navurl=url[0]+"."+SeleniumDriverTest.Environ+"."+url[1];
				webdriver.navigate().to(navurl);
			}
				break;		
			case OVERIFY:
				try {
					value = GETVALUE(value);
					fieldText = GETVALUE(fieldText);
					OracleDB oDB = new OracleDB();
					System.out.println(value);
					boolean flagdetails = oDB.oracleVerifyValue(fieldText,
							value);
					if (flagdetails) {
						resultDetails.setFlag(true);
					}
				} catch (Exception e) {
					resultDetails.setErrorMessage(e.getMessage());
					resultDetails.setFlag(false);
					e.printStackTrace();
				}
				break;
			case BROWSERCLOSEANDOPEN:
				resultDetails = browsercloseandopen(fieldText, value, fieldName);
				break;
			case JSFILEUPLOAD:
				resultDetails = JSFILEUPLOAD(webdriver, fieldText, value);
				break;
			case ALPHAORDER:
				resultDetails = alphaOrder(webdriver, fieldText);
				break;
			case VERIFY:
				resultDetails = verify(webdriver, fieldText, value, fieldName);
				break;
			case VERIFYNOTPRESENT:
				resultDetails = verifynotpresent(webdriver, fieldText, value);
				break;
			case VERIFYCONTINUE:
				resultDetails = VERIFYCONTINUE(webdriver, fieldText, value,
						fieldName);
				break;
			case SELECT:
				resultDetails = select(webdriver, fieldText, value);
				break;
			case SELECTFRAME:
				resultDetails = SELECTFRAME(webdriver, fieldText, value);
				break;
			case SELECTANDWAIT:
				resultDetails = select(webdriver, fieldText, value);
				WebDriverUtils.waitForPageToLoad(webdriver, "40000");
				break;
			case CLICK:
				resultDetails = click(webdriver, fieldText, value, fieldName);
				break;
			case CLICKANDWAIT:
				resultDetails = click(webdriver, fieldText, value, fieldName);
				WebDriverUtils.waitForPageToLoad(webdriver, "40000");

				break;
			case CHECK:
				String fieldType1 = fieldText.substring(0, 3);
				fieldType1 = fieldType1 + "C";
				fieldText = fieldType1
						+ fieldText.substring(3, fieldText.length());
				resultDetails = checkOrUncheck(webdriver, fieldText, value,
						fieldName);
				break;
			case UNCHECK:
				String fieldType2 = fieldText.substring(0, 3);
				fieldType2 = fieldType2 + "U";
				fieldText = fieldType2
						+ fieldText.substring(3, fieldText.length());
				resultDetails = checkOrUncheck(webdriver, fieldText, value,
						fieldName);
				break;

			case DELETECOOKIES:
				System.out.println(fieldText+" : "+value);
				value = GETVALUE(value);
				fieldText = GETVALUE(fieldText);
				System.out.println(fieldText+":"+value);
				if (fieldText.equalsIgnoreCase(""))
					fieldText = webdriver.getCurrentUrl();
				if (!value.equalsIgnoreCase("")) {
					webdriver.manage().deleteCookieNamed(value);
					webdriver.get(fieldText);
					if(webdriver.getCurrentUrl().contains(SeleniumDriverTest.Environ))
						System.out.println("Environment "+SeleniumDriverTest.Environ+ "  is not dropped");
					else if (webdriver.getCurrentUrl().contains("medscape")||webdriver.getCurrentUrl().contains("webmd")
							||webdriver.getCurrentUrl().contains("wbmd")||webdriver.getCurrentUrl().contains("medicine")
							||webdriver.getCurrentUrl().contains("rxlist")||webdriver.getCurrentUrl().contains("preview"))
					{
						if (!(webdriver.getCurrentUrl().contains(".qa00.")||
								webdriver.getCurrentUrl().contains(".qa00.")||
								webdriver.getCurrentUrl().contains(".qa01.")||
								webdriver.getCurrentUrl().contains(".qa02.")||
								webdriver.getCurrentUrl().contains(".perf.")||
								webdriver.getCurrentUrl().contains(".preview."))) {
							String cururl=webdriver.getCurrentUrl();
							String url2[]=cururl.split("\\.",2);
							String navurl=url2[0]+"."+SeleniumDriverTest.Environ+"."+url2[1];
							webdriver.navigate().to(navurl);
						}
						
					}
				} else {
					webdriver.manage().deleteAllCookies();
					webdriver.get(fieldText);
					if(webdriver.getCurrentUrl().contains(SeleniumDriverTest.Environ))
						System.out.println("Environment "+SeleniumDriverTest.Environ+ "  is not dropped");
					else if (webdriver.getCurrentUrl().contains("medscape")||webdriver.getCurrentUrl().contains("webmd")
							||webdriver.getCurrentUrl().contains("wbmd")||webdriver.getCurrentUrl().contains("medicine")
							||webdriver.getCurrentUrl().contains("rxlist")||webdriver.getCurrentUrl().contains("preview"))
					{
						if (!(webdriver.getCurrentUrl().contains(".qa00.")||
								webdriver.getCurrentUrl().contains(".qa00.")||
								webdriver.getCurrentUrl().contains(".qa01.")||
								webdriver.getCurrentUrl().contains(".qa02.")||
								webdriver.getCurrentUrl().contains(".perf.")||
								webdriver.getCurrentUrl().contains(".preview."))) {
							String cururl=webdriver.getCurrentUrl();
							String url2[]=cururl.split("\\.",2);
							String navurl=url2[0]+"."+SeleniumDriverTest.Environ+"."+url2[1];
							webdriver.navigate().to(navurl);
						}
					}
				}
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;

			case ENTER:
				resultDetails = enter(webdriver, fieldText, value);
				break;
			case CLEARANDENTER:
				resultDetails = CLEARANDENTER(webdriver, fieldText, value);
				break;
			case WAITFORELEMENT:
				resultDetails = waitForElement(webdriver, fieldText, value);
				break;
			case SELECTWINDOW:
				resultDetails = selectWindow(webdriver, fieldText, value);
				break;
			case CLOSEWINDOW:
				resultDetails = closeWindow(webdriver, fieldText, value);
				break;
			case GOBACK:
				try {
					webdriver.navigate().back();
					WebDriverUtils.waitForPageToLoad(webdriver, "30000");
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Window with title :: "
							+ value + " ::not Found");
					return resultDetails;
				}
				break;
			case MONGOSAVEUSER:
				resultDetails = MongoSaveUser(value);
				break;
			case MONGOINSERTSAVED:
				resultDetails = MongoInsertSaveUser();
				break;
			case DB:
				resultDetails = updateDBValue(fieldText, value);
				break;
			case ISDISABLED:
				resultDetails = isDisabled(webdriver, fieldText);
				break;
			case ISENABLED:
				resultDetails = isEnabled(webdriver, fieldText);
				break;
			case STOREVALUE:
				resultDetails = storeValue(webdriver, fieldText, value,
						fieldName);
				break;
			case KEYPRESS:
				resultDetails = keypress(webdriver, fieldText, value);
				WebDriverUtils.waitForPageToLoad(webdriver, value.toString());
				break;
			case STOREATTRIBUTE:
				resultDetails = STOREATTRIBUTE(webdriver, fieldText, value);
				break;
			case VERIFYATTRIBUTE:
				resultDetails = VERIFYATTRIBUTE(webdriver, fieldText, value);
				break;
			case REFRESH:
				try {
					webdriver.navigate().refresh();
					resultDetails.setFlag(true);
				} catch (Exception e) {
					System.out.println("exception value : " + e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Refresh Error");
				}
				break;
			case WAITTIME:
				try {
					if (!value.equals("")) {
						if(Integer.parseInt(value)<20000)
							Thread.sleep(Integer.parseInt(value));
						else
							Thread.sleep(20000);
					}
					else
						Thread.sleep(20000);
					resultDetails.setFlag(true);
				} catch (Exception e) {
					System.out.println("exception value : " + e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Wait Error");
				}
				break;
			case OPENURL:
				fieldText = GETVALUE(fieldText);
				System.out.println("Open URL : " + fieldText);
				try {
					webdriver.get(fieldText); // url
					if(webdriver.getCurrentUrl().contains(SeleniumDriverTest.Environ))
						System.out.println("Environment "+SeleniumDriverTest.Environ+ "  is not dropped");
					else if (webdriver.getCurrentUrl().contains("medscape")||webdriver.getCurrentUrl().contains("webmd")
							||webdriver.getCurrentUrl().contains("wbmd")||webdriver.getCurrentUrl().contains("medicine")
							||webdriver.getCurrentUrl().contains("rxlist")||webdriver.getCurrentUrl().contains("preview"))
					{
						if (!(webdriver.getCurrentUrl().contains(".qa00.")||
								webdriver.getCurrentUrl().contains(".qa00.")||
								webdriver.getCurrentUrl().contains(".qa01.")||
								webdriver.getCurrentUrl().contains(".qa02.")||
								webdriver.getCurrentUrl().contains(".perf.")||
								webdriver.getCurrentUrl().contains(".preview."))) {
							String cururl=webdriver.getCurrentUrl();
							String url2[]=cururl.split("\\.",2);
							String navurl=url2[0]+"."+SeleniumDriverTest.Environ+"."+url2[1];
							webdriver.navigate().to(navurl);
						}
					}
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} catch (Exception e) {
					System.out.println("exception value : " + e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Window with title   ::+"
							+ value + "::not Found");
					WebDriverUtils.waitForPageToLoad(webdriver, "50000");
				}
				break;

			case CLICKTABLECPP:
				// fieldText = GETVALUE(fieldText);
				System.out.println("Clicking the row which contains : "
						+ fieldText);
				String strExpected = GETVALUE(value);
				try {
					resultDetails = CLICKTABLECPP(webdriver, fieldText,
							strExpected, fieldName);
				} catch (Exception e) {
					System.out.println("exception value : " + e.getMessage());
					resultDetails.setFlag(false);
					resultDetails
							.setErrorMessage("unable to click the required   ::+"
									+ value + "::not Found");
					WebDriverUtils.waitForPageToLoad(webdriver, "50000");
				}
				break;
			case OITARGET:
				try {
					OracleDB oDB = new OracleDB();
					oDB.oracleInsertTargetList();

				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
			case OIATTRIB:
				try {
					OracleDB oDB = new OracleDB();
					oDB.oracleInsertAttribute(fieldText, value);
				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
			case OUCPDATE:
				try {
					OracleDB oDB = new OracleDB();
					oDB.oracleupdateCPUpdatedDate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
				
			case OGIFTMGR:
				try {
				OracleDB oDB = new OracleDB();
				oDB.oracleGiftManager(fieldText);
				resultDetails.setFlag(true);
				} catch (Exception e) {
				resultDetails.setFlag(false);
				e.printStackTrace();
				}
				break;

			case MOUSEOVER:
				System.out.println("Field ::" + fieldText);
				value = GETVALUE(value);
				String strField = fieldText.substring(0, 3);
				// String field = fieldText.substring(3, fieldText.length());
				WebDriverBackedSelenium sel = new WebDriverBackedSelenium(
						webdriver, webdriver.getCurrentUrl());

				try {
					if (strField.equalsIgnoreCase("XPH")) { 
						WebElement myElement = webdriver
								.findElement(WebDriverUtils.locatorToByObj(
										webdriver, value));
						actions = new Actions(webdriver);
						actions.clickAndHold(myElement).build().perform();
					} else {
						if (value.contains("xpath"))
							value = value.replace("xpath=", "");
						if (fieldText.equalsIgnoreCase("LNK"))
							value = "link=" + value;
						System.out.println(value);
						sel.mouseOver(value);
					}
					Thread.sleep(3000);
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} catch (Exception e) {
					System.out.println("exception value : " + e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Element: " + value
							+ " is not found");
				}
			}
			return resultDetails;
		} 
		
		/**
		 * Here It catches the exception and Searches for the keyword in below mentioned classes,
		 * If the required keyword is not present then it stops execution
		 */
		
		catch (Throwable e) {
			// Keywords Verification in CMS Class
			try {
					CMS_Keywords.ActionTypes.valueOf(actionType.toUpperCase());
					CMS_Keywords cms = new CMS_Keywords(webdriver);
					resultDetails = cms.performAction(fieldText,value, actionType, fieldName);
					return resultDetails;
			}catch(Exception e1){}
			
			// Keywords Verification in Search_Keywords Class
			try {
					Search_Keywords.ActionTypes.valueOf(actionType.toUpperCase());
					Search_Keywords search = new Search_Keywords(webdriver);
					resultDetails = search.performAction(fieldText,value, actionType, fieldName);
					return resultDetails;
			}catch(Exception e1) {}
			//Keywords Verification in Reg_Feature_Keywords Class
			try {
					Reg_Feature_Keywords.ActionTypes.valueOf(actionType.toUpperCase());
					Reg_Feature_Keywords reg_feature = new Reg_Feature_Keywords(webdriver);
					resultDetails = reg_feature.performAction(fieldText, value, actionType, fieldName);
					webdriver = reg_feature.webdriver;
					return resultDetails;
			}catch(Exception e1) {}
			
			try {
				PICME_Keywords.ActionTypes.valueOf(actionType.toUpperCase());
				PICME_Keywords picmeApp = new PICME_Keywords(webdriver);
				resultDetails = picmeApp.performAction(fieldText,value, actionType, fieldName);
				return resultDetails;
		}catch(Exception e1){}
			
			try {
				Prof_App_Keywords.ActionTypes.valueOf(actionType.toUpperCase());
				Prof_App_Keywords profApp = new Prof_App_Keywords(webdriver);
				resultDetails = profApp.performAction(fieldText,value, actionType, fieldName);
				return resultDetails;
		}catch(Exception e1){}
			
			// Keywords Verification in Pro_App_Keywords Class
			try {
					Reference_Keywords.ActionTypes.valueOf(actionType.toUpperCase());
					Reference_Keywords profApp = new Reference_Keywords(webdriver);
					resultDetails = profApp.performAction(fieldText,value, actionType, fieldName);
					return resultDetails;
			}catch(Exception e1){}
			
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

	/**
	 * It verifies given check box is selected or not
	 * and compares with given value (TRUE/FALSE) 
	 * 
	 * @param webdriver
	 * @param fieldText
	 * @param value
	 * @param fieldName
	 */
	
	public void isSelected(WebDriver webdriver, String fieldText,
			String value, String fieldName) {
		String field = fieldText.substring(0, 3);
		fieldText = fieldText.substring(3);
		CheckDataFileds cdf = CheckDataFileds.valueOf(field);
		try {
			switch (cdf) {
			case CHK:
				if (value.equalsIgnoreCase("true"))
					assertEquals(true, webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, fieldText)).isSelected());
				else if (value.equalsIgnoreCase("false"))
					assertEquals(false, webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, fieldText)).isSelected());
				break;
			}
			resultDetails.setFlag(true);
		} catch (NoSuchElementException e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(fieldText+" :: is Not Found");
			System.out.println(fieldText+" :: is Not Found");
		} catch (AssertionFailedError e) {
			resultDetails.setFlag(false);
			if (value.equalsIgnoreCase("true")) {
				resultDetails.setErrorMessage("Given Object is not Selected but you have provided true");
				System.out.println("Given Object is not Selected but you have provided true");
			}
			else if (value.equalsIgnoreCase("false")) {
				resultDetails.setErrorMessage("Given Object is Selected but you have provided false");
				System.out.println("Given Object is Selected but you have provided false");
			}
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.getMessage());
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * It verifies all the suggestions are start with given key value or not
	 * 
	 * @param webdriver
	 * @param fieldText
	 * @param value
	 * @param fieldName
	 * @return
	 */
	public ResultDetails verifyAjaxSuggestionlist(WebDriver webdriver,
			String fieldText, String value, String fieldName) {
		ResultDetails resultDetails = new ResultDetails();
		String strField = fieldText.substring(0, 3);
		fieldText = fieldText.substring(3, fieldText.length());
		value = GETVALUE(value);
		try {
			Thread.sleep(3000);
			DataFileds dfs = DataFileds.valueOf(strField);
			switch (dfs) {
			case TXT:
				int count = webdriver.findElements(
						By.xpath(fieldText.split("::")[0])).size();
				for (int i = 0; i < count; i++) {
					assertTrue(webdriver
							.findElement(
									By.xpath(fieldText.split("::")[0] + "["
											+ (i + 1) + "]"
											+ fieldText.split("::")[1]))
							.getText().toLowerCase()
							.startsWith(value.toLowerCase()));
				}
				break;
			}
			resultDetails.setFlag(true);
		} catch (NoSuchElementException e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(fieldName + "Not Found");
			System.out.println(e.getMessage());
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(value + "Not present in the Suggestions");
			System.out.println(e.getMessage());
		}
		return resultDetails;
	}
	public ResultDetails verifybetweendates(WebDriver webdriver, String fieldText,
			String value, String fieldName) {
		ResultDetails resultDetails = new ResultDetails();
		String strField = fieldText.substring(0, 3);
		fieldText = fieldText.substring(3, fieldText.length());
		value = GETVALUE(value);
		try {
			Thread.sleep(3000);
			DataFileds dfs = DataFileds.valueOf(strField);
			switch (dfs) {
			case DTE:
				try{
				Select select = new Select(webdriver.findElement(By.xpath(fieldText.split("::")[0])));
				String frommonth=select.getFirstSelectedOption().getText();
				Select select1 = new Select(webdriver.findElement(By.xpath(fieldText.split("::")[1])));
				String fromyear=select1.getFirstSelectedOption().getText();
				Select select2 = new Select(webdriver.findElement(By.xpath(fieldText.split("::")[2])));
				String tomonth=select2.getFirstSelectedOption().getText();
				Select select3 = new Select(webdriver.findElement(By.xpath(fieldText.split("::")[3])));
				String toyear=select3.getFirstSelectedOption().getText();
				String fromdate = frommonth.concat(" " + fromyear);
				System.out.println(fromdate);
				DateFormat formatter1 = new SimpleDateFormat("MM yyyy");
				Date Fromdate = (Date) formatter1.parse(fromdate);
				System.out.println("From date: " + Fromdate);
				String todate = tomonth.concat(" " + toyear);
				System.out.println(todate);
				DateFormat formatter2 = new SimpleDateFormat("MM yyyy");
				Date Todate = (Date) formatter2.parse(todate);
				int n=new Date(Integer.parseInt(toyear), Integer.parseInt(tomonth), 0).getDate();
				System.out.println(n);
				Calendar cal  = Calendar.getInstance();
				cal.setTime(Todate);
				cal.add(Calendar.DATE, n-1);
				System.out.println(cal.getTime());
				Todate=cal.getTime();
				System.out.println("To date: " + Todate);
				int rowCount = webdriver.findElements(By.xpath(value.split("::")[0])).size();
				String[] arr=new String[rowCount];
				for (int i=0; i<rowCount; i++) {
					arr[i] = webdriver.findElement(By.xpath(value.split("::")[0]+"["+(i+1)+"]"+value.split("::")[1])).getText();
					
					DateFormat formatter3 = new SimpleDateFormat(value.split("::")[2]);
					Date d1 = (Date) formatter1.parse(arr[i]);
					System.out.println(d1);
					if(d1.compareTo(Fromdate)>=0 && d1.compareTo(Todate)<=0){
						resultDetails.setFlag(true);
					}else{
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage(d1+" Date is not present between given custom date");
						return resultDetails;
					}
				}}catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getLocalizedMessage());
					return resultDetails;
				}
				
		
				break;
			}
		
		} catch (NoSuchElementException e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(fieldName + "Not Found");
			System.out.println(e.getMessage());
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(value + "Not present in the Suggestions");
			System.out.println(e.getMessage());
		}
		return resultDetails;
		
	}

	public ResultDetails VerifyColor(WebDriver webdriver, String fieldText,String value) {
		try {
			System.out.println(fieldText);
			System.out.println("Going to find locator");
			String Colo=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getCssValue("color").toString();
			System.out.println("found locator");
			System.out.println(Colo);
			System.out.println(value);
			//SeleniumDriver.hMap.p
			String RGB=Colo.substring(Colo.indexOf("(")+1,Colo.length()-1);
			Color c =new Color(Integer.parseInt(RGB.split(",")[0].trim()),Integer.parseInt(RGB.split(",")[1].trim()),Integer.parseInt(RGB.split(",")[2].trim()),Integer.parseInt(RGB.split(",")[3].trim()));
			System.out.println("#"+Integer.toHexString(c.getRGB() &0x00ffffff));
			String co="#"+Integer.toHexString(c.getRGB() &0x00ffffff);
			
			if(value.equalsIgnoreCase(co.trim()))
			{
				resultDetails.setFlag(true);
				
			}
			else
			{
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Colors doesn't match");
			}
			//SeleniumDriver.hMap.put(value, Colo);
			//System.out.println(SeleniumDriver.hMap.get(value).toString());
			//resultDetails.setFlag(true);
		} catch (Exception e) {
			resultDetails.setFlag(false);
			e.printStackTrace();
			resultDetails.setErrorMessage(e.getMessage());
		}
		return resultDetails;
	}
	
	
	public ResultDetails VerifyCss(WebDriver webdriver, String fieldText,String value) {
		try {
			System.out.println(fieldText);
			System.out.println("Going to find locator");
			String Colo=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getCssValue(value.split("::")[0].trim()).toString();
			System.out.println("found locator");
			System.out.println(Colo);
			System.out.println(value);
			//SeleniumDriver.hMap.p
			
			if(Colo.equalsIgnoreCase(value.split("::")[1].trim()))
			{
				resultDetails.setFlag(true);
				
			}
			else
			{
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Css properties doesn't match");
			}
			//SeleniumDriver.hMap.put(value, Colo);
			//System.out.println(SeleniumDriver.hMap.get(value).toString());
			//resultDetails.setFlag(true);
		} catch (Exception e) {
			resultDetails.setFlag(false);
			e.printStackTrace();
			resultDetails.setErrorMessage(e.getMessage());
		}
		return resultDetails;
	}
	
	public ResultDetails VerifyRgbtoHex(String fieldText,String value) {
		try {
			String VALUE1 = GETVALUE(fieldText);
			String RGB=VALUE1.substring(VALUE1.indexOf("(")+1,VALUE1.length()-1);
			Color c =new Color(Integer.parseInt(RGB.split(",")[0]),Integer.parseInt(RGB.split(",")[1]),Integer.parseInt(RGB.split(",")[2]),Integer.parseInt(RGB.split(",")[3]));
			System.out.println("#"+Integer.toHexString(c.getRGB() &0x00ffffff));
			
			if(VALUE1.equalsIgnoreCase(value.trim()))
			{
				resultDetails.setFlag(true);
				
			}
			else
			{
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Colors doesn't match");
			}
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.getMessage());
		}
		return resultDetails;
	}
	
	public void ifKeyword(WebDriver webdriver, String fieldText,
			String value, String fieldName) {
		try {
			 String VALUE = GETVALUE(value);
			 String[] val=VALUE.split("::");
			 value=val[0];
			 String field=GETVALUE(fieldText);
			 resultDetails=verify(webdriver, field, value, fieldName);
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.getMessage());
		}
	}
	
	/*
	 * It Drags the element from the source path to destination path and drops there
	 */
	public void dragAndDrop(WebDriver webdriver, String fieldText, String value, String fieldName) {
		String x,y,x1,y1;
		try {
		Dimension dimension = new Dimension(webdriver.manage().window().getSize().getWidth()-5, webdriver.manage().window().getSize().getHeight()-5);
		webdriver.manage().window().setSize(dimension);
		WebElement webEleSource = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, fieldText.split("::")[0]));
		WebElement webEleDestination = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, fieldText.split("::")[1]));
		x = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, fieldText.split("::")[0])).getText();
		y = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, fieldText.split("::")[1])).getText();
		System.out.println("Source before draganddrop:"+x);
		System.out.println("Destination before draganddrop:"+y);
		Actions builder = new Actions(webdriver);
		Action dragAndDrop = builder.clickAndHold(webEleSource)
		.moveToElement(webEleDestination)
		.release(webEleDestination)
		.build();
		dragAndDrop.perform();
		Thread.sleep(5000);
		x1 = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, fieldText.split("::")[0])).getText();
		y1 = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, fieldText.split("::")[1])).getText();
		System.out.println("Source AFTER draganddrop:"+x1);
		System.out.println("Destination AFTER draganddrop:"+y1);
		assertTrue(x!=x1||y!=y1);
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");
		} catch (NoSuchElementException e) {
		resultDetails.setFlag(false);
		resultDetails.setErrorMessage(fieldName+" :: Not Found");
		System.out.println(fieldName+" :: Not Found");
		} catch (Exception e) {
		resultDetails.setFlag(false);
		resultDetails.setErrorMessage("DragAndDrop was not performed as Expected");
		System.out.println("DragAndDrop was not performed as Expected");
		} finally {
		webdriver.manage().window().maximize();
		}
		}
	
	/**
	 * It verifies the Chronological order of Date, String and Numeric value
	 * 
	 * @param webdriver
	 * @param fieldText
	 * @param value
	 * @param fieldName
	 */
	public void verifyOrder(WebDriver webdriver, String fieldText, String value, String fieldName) {
		fieldText = GETVALUE(fieldText);
		String field = fieldText.substring(0, 3);
		fieldText = fieldText.substring(3);
		int rowCount = webdriver.findElements(By.xpath(fieldText.split("::")[0])).size();
		String strArrayActual[] = new String[rowCount];
		String strArrayExpected[] = new String[rowCount];
		for (int i=0; i<rowCount; i++) {
			strArrayActual[i] = webdriver.findElement(By.xpath(fieldText.split("::")[0]+"["+(i+1)+"]"+fieldText.split("::")[1])).getText();
			strArrayExpected[i] = webdriver.findElement(By.xpath(fieldText.split("::")[0]+"["+(i+1)+"]"+fieldText.split("::")[1])).getText();
		}
		try {
			Order order = Order.valueOf(field);
			switch(order) {
			case DEC:
				//Increasing Order
				if (value.equalsIgnoreCase("DATE")) {
					for (int i=0; i<strArrayExpected.length; i++) {
						int sel = i;
						String curr = "";
						for (int j=i+1; j<strArrayExpected.length; j++) {
							if (Integer.parseInt(strArrayExpected[sel].split("/")[2]) >= Integer.parseInt(strArrayExpected[j].split("/")[2])) {
								if (Integer.parseInt(strArrayExpected[sel].split("/")[2]) > Integer.parseInt(strArrayExpected[j].split("/")[2]))
									sel = j;
								else if (Integer.parseInt(strArrayExpected[sel].split("/")[0]) >= Integer.parseInt(strArrayExpected[j].split("/")[0])) {
									if (Integer.parseInt(strArrayExpected[sel].split("/")[0]) > Integer.parseInt(strArrayExpected[j].split("/")[0]))
										sel = j;
									else if (Integer.parseInt(strArrayExpected[sel].split("/")[1]) > Integer.parseInt(strArrayExpected[j].split("/")[1]))
										sel = j;
								}
							}
							
						}
						curr = strArrayExpected[i];
						strArrayExpected[i] = strArrayExpected[sel];
						strArrayExpected[sel] = curr;
					}
					for (int i=0; i<strArrayActual.length; i++) {
						System.out.println(strArrayActual[i]+"  "+strArrayExpected[i]);
						assertEquals(strArrayActual[i], strArrayExpected[i]);
					}
				} else if (value.equalsIgnoreCase("STRING")) {
					Arrays.sort(strArrayExpected);
					int j=0;
					for (int i=strArrayActual.length-1; i>=0; i--) {
						System.out.println(strArrayActual[j]+"  "+strArrayExpected[i]);
						assertEquals(strArrayActual[j], strArrayExpected[i]);
						j++;
					}
				} else if (value.equalsIgnoreCase("NUMBER")) {
					try {
						int numArrayExpected[] = new int[rowCount];
						for (int i=0; i<strArrayActual.length; i++)
							numArrayExpected[i] = Integer.parseInt(strArrayExpected[i]);
						Arrays.sort(numArrayExpected);
						int j=0;
						for(int i=strArrayActual.length; i>=0; i--) {
							assertEquals(numArrayExpected[i]+"", strArrayActual[j]);
							j++;
						}
					} catch(NumberFormatException e) {
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage(e.getMessage());
						System.out.println("You have given Invalid Number");
					}	
				}
				break;
			case INC:
				//Decreasing Order
				if (value.equalsIgnoreCase("DATE")) {
					for (int i=0; i<strArrayExpected.length; i++) {
						int sel = i;
						String curr = "";
						for (int j=i+1; j<strArrayExpected.length; j++) {
							if (Integer.parseInt(strArrayExpected[sel].split("/")[2]) >= Integer.parseInt(strArrayExpected[j].split("/")[2])) {
								if (Integer.parseInt(strArrayExpected[sel].split("/")[2]) > Integer.parseInt(strArrayExpected[j].split("/")[2]))
									sel = j;
								else if (Integer.parseInt(strArrayExpected[sel].split("/")[0]) >= Integer.parseInt(strArrayExpected[j].split("/")[0])) {
									if (Integer.parseInt(strArrayExpected[sel].split("/")[0]) > Integer.parseInt(strArrayExpected[j].split("/")[0]))
										sel = j;
									else if (Integer.parseInt(strArrayExpected[sel].split("/")[1]) > Integer.parseInt(strArrayExpected[j].split("/")[1]))
										sel = j;
								}
							}
							
						}
						curr = strArrayExpected[i];
						strArrayExpected[i] = strArrayExpected[sel];
						strArrayExpected[sel] = curr;
					}
					for (int i=0; i<strArrayActual.length; i++) {
						System.out.println(strArrayActual[i]+"  "+strArrayExpected[i]);
						assertEquals(strArrayActual[i], strArrayExpected[i]);
					}
				} else if (value.equalsIgnoreCase("STRING")) {
					Arrays.sort(strArrayExpected);
					for (int i=0; i<strArrayActual.length; i++) {
						System.out.println(strArrayActual[i]+"  "+strArrayExpected[i]);
						assertEquals(strArrayActual[i], strArrayExpected[i]);
					}
				} else if (value.equalsIgnoreCase("NUMBER")) {
					try {
						int numArrayExpected[] = new int[rowCount];
						for (int i=0; i<strArrayActual.length; i++)
							numArrayExpected[i] = Integer.parseInt(strArrayExpected[i]);
						Arrays.sort(numArrayExpected);
						for(int i=0; i<strArrayActual.length; i++) {
							assertEquals(numArrayExpected[i]+"", strArrayActual[i]);
						}
					} catch(NumberFormatException e) {
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage(e.getMessage());
						System.out.println("You have given Invalid Number");
					}	
				}
				break;
			}
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
		} catch(NoSuchElementException e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Unable to find :: "+fieldText);
			System.out.println("Unable to find :: "+fieldText);
		} catch(Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Not in chronological order as expected");
		}
	}
	
	/*
	 * It manipulates the different string functions
	 */
	public ResultDetails stringManipulate(WebDriver webdriver,
			String fieldText, String value, String fieldName) {
		ResultDetails resultDetails = new ResultDetails();
		String strValue = value.split("::")[0];
		if (strValue.startsWith("HMV"))
			value = value.substring(3, value.length());
		strValue = GETVALUE(strValue);
		try {
			System.out.println(strValue);
			if (fieldText.equalsIgnoreCase("SUBSTRING"))
				SeleniumDriverTest.hMap.put(
						value.split("::")[0],
						strValue.substring(
								Integer.parseInt(value.split("::")[1]),
								Integer.parseInt(value.split("::")[2])));
			if (fieldText.equalsIgnoreCase("CONCAT"))
				SeleniumDriverTest.hMap.put(
						value.split("::")[0],value.split("::")[0]+value.split("::")[1]);
			System.out.println("Manipulated Value : "+GETVALUE(value.split("::")[0]));
			resultDetails.setFlag(true);
			return resultDetails;
		} catch (Exception e) {
			System.out.println("Invalid Format");
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Invalid Fromat");
			return resultDetails;
		}
	}
	
	/**
	 * It Increases or decreases the given value
	 * 
	 * @param webdriver
	 * @param fieldText
	 * @param value - 
	 * @param fieldName
	 * @return
	 */
	public ResultDetails updateValue(WebDriver webdriver, String fieldText,
			String value, String fieldName) {
		ResultDetails resultDetails = new ResultDetails();
		String strLabel = fieldText.substring(0, 3);
		String strVal1 = "";
		String strVal2 = "";
		if (value.contains("::")) {
			strVal1 = value.split("::")[0];
			strVal2 = value.split("::")[1];
			if (strVal1.startsWith("HMV"))
				value = strVal1.substring(3, strVal1.length());
			else
				value = strVal2.substring(3,strVal2.length());
			strVal1 = GETVALUE(strVal1);
			strVal2 = GETVALUE(strVal2);
		} else {
			strVal1 = GETVALUE(value);
			value = value.substring(3, value.length());
		}

		try {
			System.out.println("First Value : "+strVal1);
			System.out.println("Second Value : "+strVal2);
			if (strVal2.equalsIgnoreCase(""))
				strVal2 = "1";
			// Inserting updated value into hMap with the same key value
			if (strLabel.equalsIgnoreCase("INC"))
				SeleniumDriverTest.hMap.put(value, Integer.parseInt(strVal1)
						+ Integer.parseInt(strVal2) + "");
			else if (strLabel.equalsIgnoreCase("DEC"))
				SeleniumDriverTest.hMap.put(value, Integer.parseInt(strVal1)
						- Integer.parseInt(strVal2) + "");
			System.out.println("Updated Value : "+GETVALUE("HMV"+value));
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
			return resultDetails;
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(fieldName + " is not a valid Value");
			return resultDetails;
		}
	}

	public ResultDetails ClickTableObject(String fieldText, String value)
	{
		boolean flag=false;
		ResultDetails resultdetails=new ResultDetails();
		try{
		fieldText =GETVALUE(fieldText);
		value =GETVALUE(value);
		
		int i=0;
		WebElement table=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText));
		List <WebElement> rows= table.findElements(By.xpath("//tr"));
		//List <WebElement> list= driver.findElements(By.xpath("//table[@id='Giftlist']/tbody/tr"));
		for(WebElement e:rows)
		{
			 i=i+1;
			if(e.getText().trim().contains(value))
			{
				System.out.println("text found at"+i);
				List <WebElement> list1=e.findElements(By.xpath(fieldText+"//tr["+i+"]//input"));
				System.out.println(list1.size());
			
				for(WebElement we:list1)
				{
					if(!we.getText().contains("hidden"))
					{
						System.out.println("CLicked");
						we.click();
						flag=true;
						break;
						
					}
				}
			}
	}
	}catch(Exception e)
	{
		e.printStackTrace();
		resultdetails.setFlag(false);
		resultdetails.setErrorMessage(e.getLocalizedMessage());
		return resultdetails;
	}
		System.out.println(flag);
		if(flag)
			resultdetails.setFlag(true);
		else
			resultdetails.setFlag(false);
			return resultdetails;
	}
	
	public ResultDetails VerifyTableText(WebDriver webdriver, String tableLocator,String rowText, String colText,String colnum) {
		String Actual="";
		WebElement table = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,
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
						if (tdElement.getText().trim().contains(rowText))
						{
							int rownum=row_num;
							break;
						}
						col_num++;
					}
				row_num++;
			}
		 Actual=table.findElement(By.xpath("//tr[" + row_num + "]/td["+Integer.parseInt(colnum)+"]")).getText();
		if(Actual.trim().contains(colText))
		{
			System.out.println("Text found");
			resultDetails.setFlag(true);	
		}
		else
		{
			System.out.println("Actual text is"+Actual+"  mismatch with "+colText);
			resultDetails.setErrorMessage("Actual text is"+Actual+"  mismatch with "+colText);
			resultDetails.setFlag(false);
		}
		return resultDetails;
	}
	
	public ResultDetails clickHidden(WebDriver webdriver, String fieldText) {
		ResultDetails resultDetails = new ResultDetails();
		try {
			fieldText = GETVALUE(fieldText);
			WebElement ele = webdriver.findElement(WebDriverUtils
					.locatorToByObj(webdriver, fieldText));
			JavascriptExecutor js = (JavascriptExecutor) webdriver;
			js.executeScript("arguments[0].click();", ele);
			WebDriverUtils.waitForPageToLoad(webdriver, "50000");
			resultDetails.setFlag(true);
		} catch (Exception e) {
			e.printStackTrace();
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.getMessage());
		}
		return resultDetails;
	}
	
	public ResultDetails VerifyADTag(WebDriver webdriver, String field)
	{
		System.out.println("Created the required object");
		ResultDetails resultDetails = new ResultDetails();
	try{	
		System.out.println("Verifying the ADTag values");
		//System.out.println(field);
		String Match=AD.VerifyADTagValues(webdriver,field);
		if (Match.isEmpty())
		{
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
		}
		else
		{
			resultDetails.setFlag(false);
			System.out.println("MisMatches are : "+Match);
			resultDetails.setErrorMessage(Match);
			
		}
		}catch (Throwable e) {
			resultDetails.setFlag(false);
			System.out.println("error message : " + e.getMessage());
			resultDetails.setErrorMessage(e.getMessage());
			return resultDetails;
		}
	return resultDetails;
	}

	/*
	 * It saves the cookies (If requires) and close the browser and open new
	 * instance and add the saved cookies here
	 */
	public ResultDetails browsercloseandopen(String field, String value,
			String fieldName) throws Exception {
		ResultDetails resultDetails = new ResultDetails();
		URL grid_url = new URL("http://" + SeleniumDriverTest.HubHost + ":"
				+ SeleniumDriverTest.HubPort + "/wd/hub");
		String strValue[] = value.split("::");

		Set<Cookie> c = null;
		Iterator<Cookie> it = null;
		try {
			c = webdriver.manage().getCookies();
			it = c.iterator();

			webdriver.quit();

			if (SeleniumDriverTest.browserType.equalsIgnoreCase("GCHROME")) {
				DesiredCapabilities dc = new DesiredCapabilities().chrome();
				dc.setBrowserName("chrome");
				webdriver = new CustomRemoteWebDriver(grid_url, dc);
			} else if (SeleniumDriverTest.browserType.equalsIgnoreCase("FF")) {
				DesiredCapabilities dc = new DesiredCapabilities().firefox();
				dc.setBrowserName("firefox");
				webdriver = new CustomRemoteWebDriver(grid_url, dc);
			} else if (SeleniumDriverTest.browserType.equalsIgnoreCase("IE8")
					|| SeleniumDriverTest.browserType.equalsIgnoreCase("IE6")) {
				DesiredCapabilities dc = new DesiredCapabilities()
						.internetExplorer();
				dc.setBrowserName("internet explorer");
				webdriver = new CustomRemoteWebDriver(grid_url, dc);
			}
			webdriver.get(strValue[1]);
			if(webdriver.getCurrentUrl().contains(SeleniumDriverTest.Environ))
				System.out.println("Environment "+SeleniumDriverTest.Environ+ "  is not dropped");
			else if (webdriver.getCurrentUrl().contains("medscape")||webdriver.getCurrentUrl().contains("webmd")
					||webdriver.getCurrentUrl().contains("wbmd")||webdriver.getCurrentUrl().contains("medicine")
					||webdriver.getCurrentUrl().contains("rxlist")||webdriver.getCurrentUrl().contains("preview"))
			{
				if (!(webdriver.getCurrentUrl().contains(".qa00.")||
						webdriver.getCurrentUrl().contains(".qa00.")||
						webdriver.getCurrentUrl().contains(".qa01.")||
						webdriver.getCurrentUrl().contains(".qa02.")||
						webdriver.getCurrentUrl().contains(".perf.")||
						webdriver.getCurrentUrl().contains(".preview."))) {
					String cururl=webdriver.getCurrentUrl();
					String url2[]=cururl.split("\\.",2);
					String navurl=url2[0]+"."+SeleniumDriverTest.Environ+"."+url2[1];
					webdriver.navigate().to(navurl);
				}
			}
			webdriver.manage().window().maximize();
			webdriver.manage().deleteAllCookies();
			Cookie ck;

			if (strValue[0].equalsIgnoreCase("ON")) {
				while (it.hasNext()) {
					ck = it.next();
					if (ck.toString().contains("expires="))
						webdriver.manage().addCookie(ck);
				}
			}
			webdriver.get(webdriver.getCurrentUrl());
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
			return resultDetails;
		} catch (Throwable e) {
			System.out.println(e.getStackTrace());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.getMessage());
			return resultDetails;
		}
	}
	
	public ResultDetails ADTAGUPDATE(WebDriver webdriver,String fieldText) {
		ResultDetails resultDetails = new ResultDetails();
		try {
			String Message=AD.putDynamic(webdriver, fieldText);
			if (!Message.contains("0"))
			{
				resultDetails.setErrorMessage("");
				resultDetails.setFlag(true);
			}
			else{
				resultDetails.setErrorMessage(Message);
				resultDetails.setFlag(false);
				}
			return resultDetails;
		} catch (Throwable e) {
			resultDetails.setFlag(false);
			resultDetails
					.setErrorMessage("Unable to add values to hashmap from profile page");
			return resultDetails;
		}
	}
	
	public ResultDetails ADTAGNEW(WebDriver webdriver,String fieldText) {
		ResultDetails resultDetails = new ResultDetails();
		// String fieldType = fieldText.substring(0, 3);
		try {
			webdriver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
			AD.moveToProfPage(webdriver);
			String Message=AD.putNewProf(webdriver,fieldText);
			if (Message.contains("")) {
				resultDetails.setErrorMessage("");
				resultDetails.setFlag(true);
			}else{
				System.out.println(Message);
				resultDetails.setErrorMessage(Message);
				resultDetails.setFlag(false);
			}
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.getMessage());
		}
		return resultDetails;
	}
	
	public static int getRandom(int i) {
		int r = 0;
		while (r == 0) {
			r = (int) ((Math.random()) * i) + 1;
		}
		return r;
	}
	/*
	 * Function to get the actual value for the Data Values column
	 */
	public String GETVALUE(String value) {
		if (value.length() > 3) {
			if (value.substring(0, 3).equals("RND")) {
				Random randomGenerator = new Random();
				long intRandom = randomGenerator.nextInt(99000) + 10000;
				value = value.substring(3, value.length()) + intRandom;
			}else if(value.startsWith("HMVINT")){
				value=Reference_Keywords.hMap.get(Integer.parseInt(value.substring(6)));				
			}else if(value.substring(0, 3).equals("RNE")) {
				//Random randomGenerator = new Random();
				String Rndnum=value.substring(3, value.length())+RandomStringUtils.randomNumeric(5);
				String email=Rndnum+"@"+value.substring(3, value.length())+".com";
				value = email;
				resultDetails.setWarningMessage("Randomly created Mail ID :: "+value);
			}
			else if (value.substring(0, 3).equals("HMV")) {
				value = SeleniumDriverTest.hMap.get(value.substring(3));
			} 
			
			else if (value.indexOf("d:") != -1) {
				value = GETDATE(value);
			} else if (value.startsWith("dt:")) {

				// System.out.println("value.substring(3).replace(#, ).toLowerCase()"+value.substring(3).replace("#","").toLowerCase());
				if (value.substring(3).indexOf("#") == -1) {
					value = value + "1";
					System.out.println("value::::"+value);
				}
				if (SeleniumDriverTest.parameterDetails.containsKey(value
						.substring(3).replace("#", "").toLowerCase())) {
					value = SeleniumDriverTest.parameterDetails.get(value
							.substring(3).replace("#", "").toLowerCase());
				} else {
					System.out
							.println("ERROR : Unable to find the value for the Parameter '"
									+ value + "' in the Hasp Map.");
				}
			}
		}
		System.out.println("value = " + value);
		return value;
	}

	public ResultDetails JSFILEUPLOAD(WebDriver webdriver, String fieldText,
			String value) {
		value = GETVALUE(value);
		ResultDetails resultdetails = new ResultDetails();
		System.out.println("This is in JSUpload");
		try {
			
			System.out.println("Element path is "+fieldText);
			WebElement wb = webdriver.findElement(By.name(fieldText));
			System.out.println(wb.getAttribute("type")+"This is the object");
			System.out.println("Path to type is"+value);
			Thread.sleep(4000);
			webdriver.findElement(By.name(fieldText)).sendKeys(value);
			//wb.sendKeys(value);
			webdriver.findElement(By.xpath("//input[@type='submit']")).click();
			resultdetails.setFlag(true);
			resultdetails.setErrorMessage("");
		} catch (Exception e) {
			e.printStackTrace();
			resultdetails.setErrorMessage(e.getMessage());
			resultdetails.setFlag(false);
			return resultdetails;
		}
		return resultdetails;
	}
	
	public ResultDetails AddTitle(WebDriver webdriver) {
		ResultDetails resultdetails = new ResultDetails();
		// System.out.println("Addtitle is called");
		String tactics = "";
		String out = webdriver.findElement(By.xpath("//body")).getText();
		String arr[] = out.split("\"adActive");

		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i].contains("\"active\":true")) {
				tactics = tactics
						+ "/"
						+ arr[i].substring(arr[i].lastIndexOf("active") - 5,
								arr[i].lastIndexOf("active") - 2);

			}
		}
		// TestDataDetails testdetails=new TestDataDetails();
		// testdetails.setTestCaseTitle(Sample);
		ExportTestResultsExcel.Comments = "  with tactics:::" + tactics;
		// System.setProperty("Comment", tactics);
		resultdetails.setFlag(true);
		return resultdetails;
	}
	
	/*
	 * Function to perform the select operations on combobox /list box / radio
	 * button / window with specific title
	 */
	public ResultDetails select(WebDriver webdriver, String fieldText,
			String value) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		String selectBy = "";

		if (value.indexOf(":") != -1) {
			selectBy = value.split(":")[0];
			value = value.split(":")[1];
		}
		value = GETVALUE(value);
		try {
			SelectDataFileds sdf = SelectDataFileds.valueOf(fieldType
					.toUpperCase());
			Select select = new Select(webdriver.findElement(WebDriverUtils
					.locatorToByObj(webdriver, field)));
			switch (sdf) {
			case COB:
				System.out.println(" In COB " + field + " : " + value);
				if (selectBy.equalsIgnoreCase("text"))
					select.selectByVisibleText(value);
				else if (selectBy.equalsIgnoreCase("value"))
					select.selectByValue(value);
				else if (selectBy.equalsIgnoreCase("index"))
					select.selectByIndex(Integer.parseInt(value));
				else if (selectBy.equalsIgnoreCase("")) {
					try {
						field = webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field))
								.getAttribute("id");
						String Script = "";
						Script += "javascript:var s = document.getElementById('"
								+ field + "');";
						Script += "for (i = 0; i< s.options.length; i++){";
						Script += "   if (s.options[i].text.trim().toUpperCase() == '"
								+ value.toUpperCase() + "'){";
						Script += "      s.options[i].selected = true;";
						Script += "      s.click();";
						Script += "      if (s.onchange) {";
						Script += "         s.onchange();";
						Script += "      }";
						Script += "      break;";
						Script += "   }";
						Script += "}";
						System.out.println("Java Script : " + Script);
						((JavascriptExecutor) webdriver).executeScript(Script);
						// System.out.println("selected");
						Thread.sleep(2000);
					} catch (Exception e) {
						System.out.println("Exception occured in select : "
								+ e.getMessage());
					}
				}
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;
			case RDB:
				webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver, field))
						.click();
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;

			case SLB:
				WebDriverUtils.select(webdriver, field, value);
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;

			case WND:
				webdriver.switchTo().window(value);
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;
			}
			return resultDetails;
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Option ::" + value
					+ ":: not found in Combo box :: " + field);
			return resultDetails;
		}
	}

	/*
	 * Function to perform the select operations on combobox /list box / radio
	 * button / window with specific title
	 */
	public ResultDetails SELECTFRAME(WebDriver webdriver, String fieldText,
			String value) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());

		try {
			ClickDataFileds cdf = ClickDataFileds.valueOf(fieldType
					.toUpperCase());
			switch (cdf) {
			case BTN:
				// webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,
				// field)).click();
				if (field.equalsIgnoreCase("parent")) {
					webdriver.switchTo().defaultContent();

					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} 
				 else if(field.equalsIgnoreCase("Null"))

                 {
                         webdriver.switchTo().frame("");
                         resultDetails.setFlag(true);
     					resultDetails.setErrorMessage("");

                 }

				
				else {
								webdriver.switchTo().frame(

							webdriver.findElement(WebDriverUtils.locatorToByObj(
									webdriver, field)));
				}
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;
				
			}
			return resultDetails;
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Option ::" + value
					+ ":: not found in Combo box :: " + field);
			return resultDetails;
		}
	}

	/*
	 * Function to perform click operations on objects like Link / Button etc.
	 */
	public ResultDetails click(WebDriver webdriver, String fieldText,
			String value, String fieldName) {
		ResultDetails resultDetails = new ResultDetails();
		actions = new Actions(webdriver);
		System.out.println("Field ::" + fieldText);
		System.out.println("Value :: "+ value);
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		if (fieldName.equalsIgnoreCase("NONE"))
			fieldName = field;

		if ((field.length() > 3) && (field.substring(0, 3).equals("HMV"))) {
			field = SeleniumDriverTest.hMap.get(field.substring(3));
		}
		try {
			ClickDataFileds cdf = ClickDataFileds.valueOf(fieldType
					.toUpperCase());
			switch (cdf) {
			case FUL:
				webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver, field))
						.sendKeys(value);
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;

			case LNK:
				actions.click(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field))).perform();
//				Selenium selenium = new WebDriverBackedSelenium(webdriver, webdriver.getCurrentUrl());
//				selenium.click("link="+field);
//				selenium.waitForPageToLoad(value);
				// webdriver.findElement(By.xpath("//a[contains(text(),'"+field+"')]")).click();
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;
				

				case RND:
							try{
								String[] rnd=field.split("::");
								int n=rnd.length;
								System.out.println(n);
								field="";
								for(int i=0;i<n-1;i++){
									List<WebElement> li2=(List<WebElement>)webdriver.findElements(By.xpath(field+rnd[i]));
									 int c=getRandom(li2.size());
									 field=field+rnd[i]+"["+c+"]";
														}
								field=field+rnd[n-1];
								webdriver.findElement(By.xpath(field)).click();
								resultDetails.setFlag(true);
									}catch(Exception e){
									resultDetails.setFlag(false);
									System.out.println("Unable to click the object " + fieldName);
									resultDetails.setErrorMessage(e.getMessage());
									return resultDetails;	
									}
							break;
	

			case BTN:
				if (fieldName.indexOf("Logout") != -1) {
					if (WebDriverUtils.isElementPresent(webdriver,
							"a[id*='account_t']")) {
						webdriver.findElement(
								By.cssSelector("a[id*='account_t']")).click();
						WebDriverUtils.waitForPageToLoad(webdriver, "10000");
					}
				}
				actions.click(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field))).perform();
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;

			case CNF:
				Alert alert = webdriver.switchTo().alert();
				if (value.equalsIgnoreCase("CANCEL"))
					alert.dismiss();
				else
					alert.accept();
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("Unable to click the object "
						+ fieldName);
				break;

			case XPH:
//				actions.click(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field))).perform();
//				System.out.println("XPH field  " + field);
//				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).click();
				actions.click(webdriver.findElement(By.xpath(field))).perform();
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;
			case IMG:
				webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver, field))
						.click();
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;
			}
			return resultDetails;
		} catch (IllegalArgumentException e) {
			resultDetails.setFlag(false);
			resultDetails
					.setErrorMessage("For click action type the data field should be BTN, CNF, LNK, IMG or XPH");
			return resultDetails;
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails
					.setErrorMessage("Element ::" + field + ":: not found");
			return resultDetails;
		}

	}

	/*
	 * Function to perform Check or uncheck operation on the Check box control
	 */
	public ResultDetails checkOrUncheck(WebDriver webdriver, String fieldText,
			String value, String fieldName) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		try {
			CheckDataFileds cdf = CheckDataFileds.valueOf(fieldType
					.toUpperCase());
			switch (cdf) {
			case CHK:
				String chkFlag = field.substring(0, 1);
				field = field.substring(1, field.length());
				if (WebDriverUtils.isElementPresent(webdriver, field)
						&& chkFlag.equalsIgnoreCase("C")
						&& !webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field)).isSelected()) {
					webdriver.findElement(
							WebDriverUtils.locatorToByObj(webdriver, field))
							.click();
				}
				if (WebDriverUtils.isElementPresent(webdriver, field)
						&& chkFlag.equalsIgnoreCase("U")
						&& webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field)).isSelected()) {
					webdriver.findElement(
							WebDriverUtils.locatorToByObj(webdriver, field))
							.click();
				}
				Thread.sleep(3000);
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;
			}

			return resultDetails;
		} catch (IllegalArgumentException e) {
			resultDetails.setFlag(false);
			resultDetails
					.setErrorMessage("For check/uncheck action type the data field should be CHK");
			return resultDetails;
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Element ::" + fieldName
					+ ":: not found");
			return resultDetails;
		}
	}

	/*
	 * Function to perform enter operations on different Text box controls.
	 */
	public ResultDetails enter(WebDriver webdriver, String fieldText,
			String value) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		System.out.println("field = " + field);
		System.out.println("Value is" +value);
		if (value.startsWith("RNE")) {
			value = GETVALUE(value);
			resultDetails.setWarningMessage("Randomly added Email ID : "+value);
		}else {
			value = GETVALUE(value);
		}
		try {
			EnterDataFileds edf = EnterDataFileds.valueOf(fieldType
					.toUpperCase());
			switch (edf) {
			case TXD:
				try {

					String Drug[] = value.split("-");
					webdriver.findElement(
							WebDriverUtils.locatorToByObj(webdriver, field))
							.sendKeys(Drug[0]);
					Thread.sleep(2000);
					webdriver.findElement(
							WebDriverUtils.locatorToByObj(webdriver, field))
							.sendKeys(Drug[1]);
					Thread.sleep(2000);
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} catch (Exception e) {
					System.out.println("Unable to type into drug field");
					resultDetails.setFlag(false);
					resultDetails
							.setErrorMessage("Unable to type into drug field");
				}
				break;

			case TXT:
//				webdriver.findElement(
//						WebDriverUtils.locatorToByObj(webdriver, field))
//						.clear();
				value = GETVALUE(value);
				webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver, field))
						.sendKeys(value);
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;
			case BTN:
				webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver, field))
						.sendKeys(value);
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;
			case EDT:
				webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver, field))
						.sendKeys(value);
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;
			}
			return resultDetails;
		} catch (IllegalArgumentException e) {
			resultDetails.setFlag(false);
			resultDetails
					.setErrorMessage("For ENTER action type the data field should be TXT");
			return resultDetails;
		}
	}

	/*
	 * Function to perform enter operations on different Text box controls.
	 */
	public ResultDetails CLEARANDENTER(WebDriver webdriver, String fieldText,
			String value) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		value = GETVALUE(value);

		try {
			EnterDataFileds edf = EnterDataFileds.valueOf(fieldType
					.toUpperCase());
			webdriver.findElement(
					WebDriverUtils.locatorToByObj(webdriver, field)).clear();
			switch (edf) {
			case TXT:
				webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver, field))
						.sendKeys(value);

				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;
			case BTN:
				webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver, field))
						.sendKeys(value);
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;
			case EDT:
				webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver, field))
						.sendKeys(value);
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
				break;
			}
			return resultDetails;
		} catch (IllegalArgumentException e) {
			resultDetails.setFlag(false);
			resultDetails
					.setErrorMessage("For ENTER action type the data field should be TXT");
			return resultDetails;
		}
	}

	/*
	 * Function to ensure specific element if present on the page.
	 */
	public ResultDetails waitForElement(WebDriver webdriver, String fieldText,
			String value) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		// String field = fieldText.substring(3, fieldText.length());
		try {
			WaitForFields wff = WaitForFields.valueOf(fieldType.toUpperCase());
			switch (wff) {

			case BTN:
			case IMG:
			case COB:
			case TXT:
			case XPH:
				try {
					for (int second = 0;; second++) {
						if (second >= 180)
							fail("timeout");
						try {
							if (WebDriverUtils.isElementPresent(webdriver,
									value))
								break;
						} catch (Exception e) {
						}
						Thread.sleep(1000);
					}
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} catch (Throwable e) {
					System.out.println(value + "   :: Element not Found");
					resultDetails.setFlag(false);
					// resultDetails.setErrorMessage(e.getMessage());
					resultDetails.setErrorMessage(value
							+ "   :: Element not Found");
					return resultDetails;
				}
				break;
			case LNK:
				try {
					for (int second = 0;; second++) {
						if (second >= 180)
							fail("timeout");
						try {
							if (WebDriverUtils.isElementPresent(webdriver,
									value))
								break;
						} catch (Exception e) {
						}
						Thread.sleep(1000);
					}
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} catch (Throwable e) {
					System.out
							.println("link=" + value + "   :: Link not Found");
					resultDetails.setFlag(false);
					// resultDetails.setErrorMessage(e.getMessage());
					resultDetails.setErrorMessage("link=" + value
							+ "   :: Link not Found");
					return resultDetails;
				}
				break;
			case TTL:
				try {
					for (int second = 0;; second++) {
						if (second >= 180)
							fail("timeout");
						try {
							if (value.equals(webdriver.getTitle()))
								break;
						} catch (Exception e) {
						}
						Thread.sleep(1000);
					}
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} catch (Throwable e) {
					System.out.println(value + "   :: Title not Found");
					resultDetails.setFlag(false);
					// resultDetails.setErrorMessage(e.getMessage());
					resultDetails.setErrorMessage(value
							+ "   :: Title not Found");
					return resultDetails;
				}
				break;

			case MSG:
				try {
					for (int second = 0;; second++) {
						if (second >= 180)
							fail("timeout");
						try {
							if (webdriver.getPageSource().toLowerCase().trim()
									.contains(value.toLowerCase().trim()))
								break;
						} catch (Exception e) {
						}
						Thread.sleep(1000);
					}
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} catch (Throwable e) {
					System.out.println("GOOD>>>");
					resultDetails.setFlag(false);
					// resultDetails.setErrorMessage(e.getMessage());
					resultDetails.setErrorMessage(value
							+ "   :: Text not Found");
					return resultDetails;
				}
				break;

			}
			return resultDetails;
		} catch (IllegalArgumentException e) {
			resultDetails.setFlag(false);
			resultDetails
					.setErrorMessage("For SELECT action type the data field should be TXT");
			return resultDetails;
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(value + "   :: Element not Found");
			return resultDetails;
		}
	}

	/*
	 * Function to Select a window.
	 */
	public ResultDetails selectWindow(WebDriver webdriver, String fieldText,
			String value) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		System.out.println(value + " :: Value");
		try {
			SelectWindow wff = SelectWindow.valueOf(fieldType.toUpperCase());
			switch (wff) {
			case TTL:
			default:
				try {
					Thread.sleep(3000);
					try {
						Set<String> windowHandles = webdriver.getWindowHandles();
						int intWinNum = Integer.parseInt(value) - 1;
						
						if (field.equalsIgnoreCase("close"))
							webdriver.close();
						Thread.sleep(2000);
						webdriver.switchTo().window(windowHandles.toArray()[intWinNum].toString());	
					}
					catch(Throwable e) {
						if (field.equalsIgnoreCase("close"))
							webdriver.close();
						
						if (field.equalsIgnoreCase("NULL")) {
							webdriver.switchTo()
									.window(webdriver.getWindowHandle());
							System.out.println("OOOOOOOOOOOOOOOOOOO : "
									+ webdriver.getTitle());
						} else
							WebDriverUtils.selectWindow(webdriver, value);
					}
					webdriver.manage().window().maximize();
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} catch (Throwable e) {
					System.out.println(value + "   ::Title not found");
					resultDetails.setFlag(false);
					// resultDetails.setErrorMessage(e.getMessage());
					resultDetails.setErrorMessage("Window with title   ::+"
							+ value + "::not Found");
					return resultDetails;
				}
				break;
			}
			return resultDetails;

		} catch (Throwable e) {
			System.out.println(value + "   ::Title not found");
			resultDetails.setFlag(false);
			// resultDetails.setErrorMessage(e.getMessage());
			resultDetails.setErrorMessage("Window with title   ::+" + value
					+ "::not Found");
			return resultDetails;
		}
	}

	/*
	 * Function to Close a browser window
	 */
	public ResultDetails closeWindow(WebDriver webdriver, String fieldText,
			String value) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		try {
			SelectWindow wff = SelectWindow.valueOf(fieldType.toUpperCase());
			switch (wff) {
			case TTL:

				try {
//					for (int second = 0;; second++) {
//						if (second >= 60)
//							break;
//						else
//							Thread.sleep(200);
//					}
					Thread.sleep(30000);
					if (field.equalsIgnoreCase("NULL")) {
						webdriver.switchTo()
								.window(webdriver.getWindowHandle());
						System.out.println("OOOOOOOOOOOOOOO : "
								+ webdriver.getTitle());
					} else {
						String parentWindow = webdriver.getTitle();
						WebDriverUtils.selectWindow(webdriver, value);
						webdriver.close();
						WebDriverUtils.selectWindow(webdriver, parentWindow); // switching
																				// back
																				// to
																				// parent
																				// window
					}
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Window with title   ::+"
							+ value + "::not Found");
					return resultDetails;
				}
				break;
			}
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Window with title   ::+" + value
					+ "::not Found");
			return resultDetails;
		}
		return resultDetails;
	}

	/*
	 * Function to Update the Database values
	 */
	public ResultDetails updateDBValue(String fieldText, String value) {
		System.out.println("This is updatefield");
		System.out.println("This is field text" + fieldText);
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		System.out.println("This is fieldtype" + fieldType);
		value = GETVALUE(value);
		String field = fieldText.substring(3, fieldText.length());
		try {
			DBDataFileds dbf = DBDataFileds.valueOf(fieldType.toUpperCase());
			System.out.println("This is datafileds" + dbf);
			switch (dbf) {
			case DBD:
				try {
					MyConnection mc = new MyConnection();
					mc.delete(value, field);
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
					return resultDetails;
				}
				break;
			case DBU:
				if (field == null) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("field Value is NULL");
					return resultDetails;
				}
				try {
					MyConnection mc = new MyConnection();
					if (field.startsWith("Day")) {
						mc.updateDate(value, field.substring(3));
					} else if (field.startsWith("Act")) {
						mc.updateAct(value, field.substring(3));
					} else if (field.startsWith("Seg")) {
						mc.updateSegment(value, field.substring(3));
					} else if (field.startsWith("Tac")) {
						mc.updateTac(value, field.substring(3));
					} else {
						resultDetails.setFlag(false);
						resultDetails
								.setErrorMessage("Field is not as expected");
						return resultDetails;
					}
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
					return resultDetails;
				}
				break;
			case DBL:
				try {
					// System.out.println("Entered DBL");
					MyConnection mc = new MyConnection();
					System.out.println();
					fieldType = fieldText.subSequence(3, 4).toString();
					String fieldvalue = fieldText.substring(4,
							fieldText.length());
					String guid = fieldText
							.substring(4, fieldText.indexOf('-'));
					String attr = fieldText.substring(
							fieldText.indexOf('-') + 1, fieldText.length());
					System.out.println("guid is" + guid);
					System.out.println("attr is" + attr);
					System.out.println("Value to verify is" + value);
					// System.out.println(fieldType+"is the field type");
					if (fieldType.equalsIgnoreCase("I"))
						mc.VerifyTable(guid, attr, Integer.parseInt(value));
					else
						mc.VerifyTable(guid, attr, value);
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
					return resultDetails;
				}
				break;
			case DBI:
				try {
					MyConnection mc = new MyConnection();
					mc.insert(value, field);
					resultDetails.setFlag(mc.flag);
					resultDetails.setErrorMessage(mc.msg);
				} catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
					return resultDetails;
				}
				break;
			}
			return resultDetails;
		} catch (IllegalArgumentException e) {
			resultDetails.setFlag(false);
			resultDetails
					.setErrorMessage("For DB action type the data field should be DBU");
			return resultDetails;
		}

	}

	public ResultDetails alphaOrder(WebDriver webdriver, String fieldtext) {
		ResultDetails resultdetails = new ResultDetails();
		String Sample = "";
		String Chk = "";
		Boolean flag = true;
		Select select = new Select(webdriver.findElement(WebDriverUtils
				.locatorToByObj(webdriver, fieldtext)));
		List<WebElement> selectitems = select.getOptions();
		ArrayList<String> Selectitems = new ArrayList<String>();
		for (WebElement element : selectitems) {
			Selectitems.add(element.getText());
		}
		for (String item : Selectitems) {
			if (item.compareTo(Sample) < 0) {
				Chk = item;
				flag = false;
				break;
			}
			Sample = item;

		}
		if (flag) {
			resultdetails.setFlag(true);
			resultdetails.setErrorMessage("The list is in alphabetical order");
		} else {
			resultdetails.setFlag(false);
			resultdetails
					.setErrorMessage("The list is not in alphabetical order"
							+ Chk);
		}

		return resultdetails;
	}

	/**
	 * This function is to verify whether the form values and the expected
	 * values are equal or not and continue the test with a warning message even
	 * though verification failed.
	 * 
	 * @param selenium
	 *            ,field,value
	 * @return boolean
	 */
	public ResultDetails VERIFYCONTINUE(WebDriver webdriver, String field,
			String value, String fieldName) {
		// String result = null;
		// System.out.println("field= " + field + " value= " + value);
		DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
		field = field.substring(3, field.length());
		ResultDetails resultDetails = new ResultDetails();
		resultDetails.setFlag(false);

		if (fieldName.equalsIgnoreCase("NONE"))
			fieldName = value;

		/*
		 * if ((value.length() > 3) && (value.substring(0, 3).equals("HMV"))) {
		 * value = SeleniumDriver.hMap.get(value.substring(3)); }
		 */
		value = GETVALUE(value);
		// System.out.println("field= " + field + " value= " + value);
		switch (dfs) {

		case TTL:
			try {
				assertEquals(value, webdriver.getTitle());
				resultDetails.setFlag(true);
				resultDetails.setWarningMessage(null);
			} catch (Throwable e) {
				System.out.println("Window with title   ::+" + value
						+ "::not Found");
				resultDetails.setFlag(true);
				resultDetails.setWarningMessage("Window with title   ::+"
						+ value + "::not Found. \n");
				return resultDetails;
			}
			break;
		case TXT:
			try {

				if (field.startsWith("/")) {
					System.out.println("Value = " + value);
					System.out
							.println("webdriver.findElement(field).getText() = "
									+ webdriver.findElement(
											WebDriverUtils.locatorToByObj(
													webdriver, field))
											.getText());
					if (value.equalsIgnoreCase(webdriver.findElement(
							WebDriverUtils.locatorToByObj(webdriver, field))
							.getText())) {
						resultDetails.setFlag(true);
						resultDetails.setWarningMessage(null);
					} else {
						resultDetails.setFlag(true);
						resultDetails
								.setWarningMessage("Both are NOT Equal.  Expected : "
										+ value
										+ ", Actual : "
										+ webdriver.findElement(
												WebDriverUtils.locatorToByObj(
														webdriver, field))
												.getText());
					}

				} else {
					assertEquals(
							value,
							webdriver.findElement(
									WebDriverUtils.locatorToByObj(webdriver,
											field)).getAttribute("value"));
					resultDetails.setFlag(true);
					resultDetails.setWarningMessage(null);
				}
			} catch (Throwable e) {
				System.out.println(value + "    ::Text not found");
				System.out.println("Actual Value : "
						+ webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field))
								.getAttribute("value"));
				resultDetails.setFlag(true);
				resultDetails.setWarningMessage("Text not found ::  "
						+ fieldName);
				return resultDetails;
			}
			break;

		case BTN:
			try {
				if (value.substring(0, 3).equals("HMV")) {
					value = SeleniumDriverTest.hMap.get(value.substring(3));
				}
				assertTrue(WebDriverUtils.isElementPresent(webdriver, value));
				resultDetails.setFlag(true);
				resultDetails.setWarningMessage(null);

			} catch (Throwable e) {
				System.out.println(" Button with id '" + value
						+ "' doesn't exist");
				resultDetails.setFlag(true);
				resultDetails.setWarningMessage("Button '" + fieldName
						+ "' doesn't exist");
				return resultDetails;
			}
			break;

		case LNK:
			try {
				assertTrue(WebDriverUtils.isElementPresent(webdriver, value));
				resultDetails.setFlag(true);
				resultDetails.setWarningMessage(null);
			} catch (Throwable e) {
				resultDetails.setFlag(true);
				resultDetails.setWarningMessage("Link with id '" + value
						+ "' doesn't exist");
				System.out.println("Link '" + fieldName + "' doesn't exist");
				return resultDetails;
			}
			break;
		case MSG:
			try {
				assertTrue(webdriver.getPageSource().toLowerCase().trim()
						.contains(value.toLowerCase().trim()));
				resultDetails.setFlag(true);
				resultDetails.setWarningMessage(null);
			} catch (Throwable e) {
				System.out.println("Text :: +" + value + "   :: not found");
				resultDetails.setFlag(true);
				resultDetails.setWarningMessage("Text :: +" + value
						+ "   :: not found");
				return resultDetails;
			}
			break;
		}
		return resultDetails;
	}

	/**
	 * This function is to verify whether the form values and the expected
	 * values are equal or not
	 * 
	 * @param selenium
	 *            ,field,value
	 * @return boolean
	 */
	public ResultDetails verify(WebDriver webdriver, String field,
			String value, String fieldName) {
		String result = null;
		System.out.println("field= " + field + " value= " + value);
		DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
		field = field.substring(3, field.length());
		ResultDetails resultDetails = new ResultDetails();
		resultDetails.setFlag(false);
		if (fieldName.equalsIgnoreCase("NONE"))
			fieldName = value;
		System.out.println(value);
		value = GETVALUE(value);
		/*
		 * if ((value.length() > 3) && (value.substring(0, 3).equals("HMV"))) {
		 * value = SeleniumDriver.hMap.get(value.substring(3)); }
		 */
		// System.out.println("field= " + field + " value= " + value);
		switch (dfs) {
		
		case CKI:
			try {
				Set<Cookie> c = webdriver.manage().getCookies();
				Iterator<Cookie> it = c.iterator();
				Cookie ck;
				while (it.hasNext()) {
					ck = it.next();
					if (value.trim().equalsIgnoreCase(ck.toString().trim())) {
						System.out.println("Expected Cookie :: "+ck.toString().split("=")[0]);
						break;
					}
				}
				resultDetails.setFlag(true);
			} catch (Throwable e) {
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Invalid Cookie");
				System.out.println("Invalid Cookie");
			}
			break;
		
		case URL:
			try {
				assertEquals(value, webdriver.getCurrentUrl());
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch(Throwable e) {
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Given URL is not matched with the page URL");
				System.out.println("Given URL is not matched with the page URL");
			}
			break;
		
		case REM:
			try {
				List<WebElement> l = (List<WebElement>) webdriver
						.findElements(By.xpath(value));
				String[] result1 = new String[l.size()];
				for (int i = 0; i < l.size(); i++) {
					result1[i] = l.get(i).getText().toString();
				}
				int j = 1;
				boolean flag = true;
				while (j < l.size()) {
					boolean b = true;
					int count = 0;
					int n = result1[j].indexOf(" ");
					System.out.println(n);
					String temp1 = result1[j].substring(0, n);
					String[] str = new String[l.size()];
					int k = 0;
					for (int i = 0; i < l.size(); i++) {
						b = result1[i].contains(temp1);
						if (b) {
							str[k] = result1[i];
							str[k] = str[k].split(" ", 5)[4];
							count++;
							k++;
						}
					}
					if (count > 1) {
						for (int i = 0; i < count - 1; i++) {
							int Result = str[i].compareTo(str[i + 1]);
							if (Result > 0) {
								flag = false;
								break;
							}
						}
					}
					j = j + count;
				}
				assertEquals(flag, true);
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			}

			catch (Throwable e) {
				System.out.println(e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Vaccine names were not sorted");
			}
			break;

		case NAV:
			try {
				String s1 = SeleniumDriverTest.hMap.get("store");
				System.out.println(field);
				System.out.println("value :: " + value);

				String s3 = webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver, field))
						.getText();
				boolean flag = true;
				flag = s3.toLowerCase().contains(s1.toLowerCase());
				assertEquals(flag, true);
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			}

			catch (Throwable e) {
				System.out.println(e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage(value + " :: Not Found");
			}
			break;

		case KYV:
			try {

				List<WebElement> l = (List<WebElement>) webdriver
						.findElements(By.xpath(field));
				int e = l.size();
				String[] str = new String[l.size()];
				for (int i = 0; i < l.size(); i++) {
					str[i] = l.get(i).getText().toString();
				}
				System.out.println("value :: " + value);
				boolean b = true;
				int count = 0;
				for (int i = 0; i < str.length; i++) {
					b = str[i].toLowerCase().contains(value.toLowerCase());
					if (b) {
						System.out.println(str[i]);
						count++;
					}
				}
				assertEquals(e, count);
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				System.out.println("Vaccines not Found");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Vaccines not Found");
				return resultDetails;
			}
			break;

		case DBV:
			try {
				MyConnection mc = new MyConnection();
				// result = mc.getValue(field);
				if (value.startsWith("$")) {
					result = "$" + result.substring(0, (result.length() - 2));
					value = value.substring(1);
					System.out.println("DATABAESE VALUE " + result);
					System.out.println("Actual  value "
							+ webdriver.findElement(
									WebDriverUtils.locatorToByObj(webdriver,
											value)).getText());
				} else {
					System.out.println("DATABAESE VALUE " + result);
					System.out.println("Actual  value "
							+ webdriver.findElement(
									WebDriverUtils.locatorToByObj(webdriver,
											value)).getText());

				}
				assertEquals(
						result,
						webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, value)).getText());
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				System.out.println("Value doesn't match data base");
				resultDetails.setFlag(false);
				resultDetails
						.setErrorMessage("Data base value does not match Expected:"
								+ webdriver.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, value))
										.getAttribute("value")
								+ "Actual :"
								+ result);
				return resultDetails;
			}
			break;
		case VED:
			resultDetails.setFlag(true);
			String failedcases="";
			int fail=0;
			for(int i=0;i<Integer.parseInt(value.split("::")[0]);i++){
				String values="";
				values=Reference_Keywords.hMap.get(i);
				webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver, field.split("::")[0]))
						.clear();
			webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver, field.split("::")[0]))
						.sendKeys(values);
						try{
					webdriver.findElement(
							WebDriverUtils.locatorToByObj(webdriver, field.split("::")[3])).click();
				}catch(Exception e){}
				
					try{
						if(!value.split("::")[1].startsWith("HMV")){
							values=value.split("::")[1];
						}
						Thread.sleep(3000);
						if(value.split("::")[2].equalsIgnoreCase("verify")){
					if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,
							field.split("::")[1])).getText().contains(values)){
					}else{
						if(!webdriver.findElement(
								WebDriverUtils.locatorToByObj(webdriver, field.split("::")[2])).getText().equalsIgnoreCase(Reference_Keywords.hMap.get(i))){
							
						}else
						resultDetails.setFlag(false);
					}}else if(value.split("::")[2].equalsIgnoreCase("verifynotpresent")){
						if(field.split("::")[1].contains("##")){
							List <WebElement> links=webdriver.findElements(By.xpath(field.split("::")[1].split("##")[0]));
							for(int j=1;j<=links.size();j++){
								fail=0;
								if(webdriver.findElement(By.xpath(field.split("::")[1].split("##")[0]+"["+j+"]"+field.split("::")[1].split("##")[1])).getText().contains(values)){
								}else{
									resultDetails.setFlag(false);
							}
							}
							if(fail!=0){
								resultDetails.setFlag(false);	
							}
						}else if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,
								field.split("::")[1])).getText().contains(values)){
						}else{
							resultDetails.setFlag(false);
					}
					}}catch(Exception e){
							resultDetails.setFlag(false);
							System.out.println(e.getLocalizedMessage());
							resultDetails.setErrorMessage(e.getLocalizedMessage());
							return resultDetails;
					}
										
				if(!resultDetails.getFlag()){
					failedcases=failedcases+Reference_Keywords.hMap.get(i)+" , ";
					fail++;
				}
			}
			if(fail!=0){
				resultDetails.setFlag(false);
				System.out.println("failed at given values are :"+failedcases);
				resultDetails.setErrorMessage("failed at given values are :"+failedcases);
			}
			break;
		case TTL:
			try {
				assertEquals(value, webdriver.getTitle());
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				System.out.println("Window with title   ::+" + value
						+ "::not Found");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Window with title   ::+" + value
						+ "::not Found");
				return resultDetails;
			}
			break;
		case TXT:
			try {
				System.out.println("Value = "+webdriver.findElement(
									WebDriverUtils.locatorToByObj(webdriver,
											field)).getText());
					assertEquals(
							value.trim().replaceAll(" ", "").toLowerCase(),
							webdriver.findElement(
									WebDriverUtils.locatorToByObj(webdriver,
											field)).getText().trim().replaceAll(" ", "").toLowerCase());
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				System.out.println(value + "    ::Text not found");
				resultDetails.setFlag(false);
				resultDetails
						.setErrorMessage("Text not found ::  " + fieldName);
				return resultDetails;
			}
			break;

		case VAL:
			try {
				if (field.startsWith("/"))
					assertEquals(
							value,
							webdriver.findElement(
									WebDriverUtils.locatorToByObj(webdriver,
											field)).getAttribute("value"));
				else
					assertEquals(
							value,
							webdriver.findElement(
									WebDriverUtils.locatorToByObj(webdriver,
											field)).getAttribute("value"));
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				System.out.println(value + "    ::Value not found");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Value not found ::  "
						+ fieldName);
				return resultDetails;
			}
			break;

		case STR:
			try {
				if (field.startsWith("/")) {
					System.out.println(webdriver.findElement(
							WebDriverUtils.locatorToByObj(webdriver, field))
							.getText().toLowerCase());
					if(webdriver
							.findElement(
									WebDriverUtils.locatorToByObj(webdriver,
											field)).getText().toLowerCase().contains(value.toLowerCase())){
				resultDetails.setFlag(true);
					}else if(value.toLowerCase().contains(webdriver
							.findElement(
									WebDriverUtils.locatorToByObj(webdriver,
											field)).getText().toLowerCase())){
						resultDetails.setFlag(true);
					}else{
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage("Text not found ::  " + value);
					}
						
						
					}

				else {
					System.out.println(webdriver.findElement(
							WebDriverUtils.locatorToByObj(webdriver, field))
							.getText().toLowerCase());
					if(webdriver
							.findElement(
									WebDriverUtils.locatorToByObj(webdriver,
											field)).getText().toLowerCase().contains(value.toLowerCase())){
				resultDetails.setFlag(true);
					}else if(value.toLowerCase().contains(webdriver
							.findElement(
									WebDriverUtils.locatorToByObj(webdriver,
											field)).getText().toLowerCase())){
						resultDetails.setFlag(true);
					}else{
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage("Text not found ::  " + value);
					}
				}
				
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				System.out.println(value + "    ::Text not found");
				resultDetails.setFlag(false);
				resultDetails
						.setErrorMessage("Text not found ::  " + fieldName);
				return resultDetails;
			}
			break;


		case POP:
			try {
				try {
					Thread.sleep(3000);
					if (value.startsWith("xpath")||value.startsWith("//")) {
						if (webdriver.findElement(By.xpath(value)).isDisplayed()) {
							webdriver.findElement(By.xpath(value)).click();
							System.out.println("Identified");
						}
					}
					else {
						if (webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, value)).isDisplayed()) {
							webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, value)).click();
							System.out.println("Identified");
						}
					}
					Thread.sleep(2000);
				} catch (Throwable e) {
					System.out.println("Exception thrown");
				}
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				System.out.println("PopUp   ::+" + value + "::not Found");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("PopUp   ::+" + value
						+ "::not Found");
				return resultDetails;
			}
			break;
		case DTV:
			try{
				int rowCount = webdriver.findElements(By.xpath(field.split("::")[0])).size();
				String[] arr=new String[rowCount];
				int temp=0;
				for (int i=0; i<rowCount; i++) {
					arr[i] = webdriver.findElement(By.xpath(field.split("::")[0]+"["+(i+1)+"]"+field.split("::")[1])).getText();
				}
				for (int i=0; i<rowCount; i++) {
					for(int j=0;j<rowCount;j++){
						if(i==j){
							continue;
						}
						if(!arr[i].equals(arr[j])){
							resultDetails.setFlag(true);
				}else{
					temp++;
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Duplicate values are present in the given label");
					return resultDetails;
				}
						}}
				if(temp!=0){
				resultDetails.setFlag(false);
				}
				}catch(Exception e){
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage(e.getLocalizedMessage());
				return resultDetails;
			}
			break;
		case XPH:
			try {
				// assertEquals(selenium.getText(field),value);
				System.out.println("text=="
						+ webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field)).getText()
						+ " value=" + value);
				System.out.println("res=="
						+ webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field)).getText()
								.contains(value));
				if (webdriver
						.findElement(
								WebDriverUtils.locatorToByObj(webdriver, field))
						.getText().contains(value)) {
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} else if (webdriver
						.findElement(
								WebDriverUtils.locatorToByObj(webdriver, field))
						.getAttribute("value").contains(value)) {
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} else {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Value :: '"
							+ value
							+ " Not present in the string : "
							+ webdriver.findElement(
									WebDriverUtils.locatorToByObj(webdriver,
											field)).getText());
				}
			} catch (Throwable e) {
				System.out.println("Element  not found :" + value);
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Element not found :" + value);
				return resultDetails;
			}
			break;
		case RDB:
			try {
				assertEquals(
						value,
						webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field))
								.getAttribute("value"));
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				System.out.println("GOOD>>>");
				resultDetails.setFlag(false);
				resultDetails
						.setErrorMessage("RadioButton not found :" + value);
				return resultDetails;
			}
			break;

		case COB:
			try {
				if (value.indexOf(":") != -1)
					assertEquals(value,
							WebDriverUtils.getSelectedLabel(webdriver, field));
				else {
					Selenium sel = new WebDriverBackedSelenium(webdriver,
							webdriver.getCurrentUrl());
					assertEquals(value, sel.getSelectedLabel(field));
				}
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("ComboBox not found :" + value);
				return resultDetails;
			}
			break;
		case CHK:
			try {
				assertEquals(
						value,
						webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field))
								.getAttribute("value"));
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("CheckBox not found :" + value);
				return resultDetails;

			}
			break;

		case SLB:
			GetTokens tokens = new GetTokens();
			ArrayList<String> arr = new ArrayList<String>();
			arr = tokens.dataValuesTokens(value, "|");
			tokens = new GetTokens();
			String[] options = WebDriverUtils.getSelectedOptions(webdriver,
					field);
			int size = options.length;
			System.out.println("A Size: " + arr.size() + " size: " + size);
			if (arr.size() == size) {
				for (int i = 0; i < size; i++) {
					System.out.println("..options= " + options[i]);
					try {
						assertEquals(options[i], arr.get(i));
						resultDetails.setFlag(true);
						resultDetails.setErrorMessage("");
					} catch (Throwable e) {
						System.out
								.println("Options mismatch with expected result  ::"
										+ field);
						resultDetails.setFlag(false);
						resultDetails
								.setErrorMessage("Options mismatch with expected result  ::"
										+ field);
						return resultDetails;
					}
				}
			}
			break;
		case CBS:
			GetTokens tokens1 = new GetTokens(); // CBS stands for Combo box
													// Values
			ArrayList<String> arr1 = new ArrayList<String>();
			arr1 = tokens1.dataValuesTokens(value, "|");
			tokens1 = new GetTokens();
			String[] options1 = WebDriverUtils.getSelectedOptions(webdriver,
					field);
			int optionsSize = options1.length;
			System.out.println("Test Data Size: " + arr1.size()
					+ " OptionsSize: " + optionsSize);
			if (arr1.size() == optionsSize) {
				for (int i = 0; i < optionsSize; i++) {
					System.out.println("Option = " + options1[i]);
					System.out.println("Test Data = " + arr1.get(i));
					try {
						assertEquals(options1[i], arr1.get(i));
						resultDetails.setFlag(true);
						resultDetails.setErrorMessage("");
					} catch (Throwable e) {
						resultDetails.setFlag(false);
						resultDetails
								.setErrorMessage("Options mismatch with expected result in drop down");
						return resultDetails;
					}
				}
			} else {
				int count = 0;
				for (int j = 0; j < arr1.size(); j++) {

					for (int i = 0; i < optionsSize; i++) {
						System.out.println("Option = " + options1[i]);
						System.out.println("Test Data = " + arr1.get(j));
						if (arr1.get(j).equalsIgnoreCase(options1[i])) {
							count++;
							System.out.println("Test data found in options:: "
									+ count);
							break;
						}
					}
				}

				if (count > 0 && count == arr1.size()) {
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} else {
					resultDetails.setFlag(false);
					resultDetails
							.setErrorMessage("ComboBox values does not Match with expected result . ");
					// return resultDetails;
				}
			}

			System.out.println(" in case flag= " + resultDetails.getFlag());

			break;

		case IMG:
		case BTN:
			try {
				if (value.substring(0, 3).equals("HMV")) {
					value = SeleniumDriverTest.hMap.get(value.substring(3));
				}
				assertTrue(WebDriverUtils.isElementPresent(webdriver, value));
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");

			} catch (Throwable e) {
				System.out.println(" Button with id '" + value
						+ "' doesn't exist");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Button '" + fieldName
						+ "' doesn't exist");
				return resultDetails;
			}
			break;
		case LNK:
			if ((field.length() > 6)
					&& (field.substring(field.length() - 5, field.length())
							.equalsIgnoreCase("@href"))) {
				try {
					System.out.println(" Attribute value : "
							+ webdriver.findElement(
									WebDriverUtils.locatorToByObj(webdriver,
											field.substring(0,
													field.length() - 5)))
									.getAttribute("href"));
					System.out.println(" Parameter value : "
							+ String.valueOf(value));
					if (webdriver
							.findElement(
									WebDriverUtils.locatorToByObj(webdriver,
											field.substring(0,
													field.length() - 5)))
							.getAttribute("href")
							.equalsIgnoreCase(String.valueOf(value))) {
						resultDetails.setFlag(true);
						resultDetails.setErrorMessage("");
					} else {
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage(fieldName
								+ " attribute value NOT matched. Expected : "
								+ String.valueOf(value)
								+ " Actual: "
								+ webdriver.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver,
												field.substring(0,
														field.length() - 5)))
										.getAttribute("href"));
						return resultDetails;
					}
				} catch (Throwable e) {
					resultDetails.setFlag(false);
					resultDetails
							.setErrorMessage("Error occured while getting the attribute value of :: "
									+ fieldName);
					return resultDetails;
				}
				break;
			} else {
				try {
					assertTrue(WebDriverUtils
							.isElementPresent(webdriver, value));
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				} catch (Throwable e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Link with id '" + value
							+ "' doesn't exist");
					System.out
							.println("Link '" + fieldName + "' doesn't exist");
					return resultDetails;
				}
			}
			break;

		case ALT:
			try {
				assertEquals(value, WebDriverUtils.getAlert(webdriver));
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				System.out.println("Alert box not found");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Alert box not found");
				return resultDetails;
			}
			break;

		case CNF:
			try {
				assertEquals(value, WebDriverUtils.getAlert(webdriver));
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				System.out.println("Confirmation box not found");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Confirmation box not found");
				return resultDetails;
			}
			break;

		case MSG:
			try {
				System.out.println("value :: " + value);
				// if(WebDriverUtils.isTextPresent(webdriver, value))
				// assertTrue(WebDriverUtils.isTextPresent(webdriver, value));
				// else
				assertTrue(webdriver.getPageSource().toLowerCase().trim()
						.contains(value.toLowerCase().trim()));
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				System.out.println("Text :: +" + value + "   :: not found");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Text :: +" + value
						+ "   :: not found");
				return resultDetails;
			}
			break;

		case GET:
			try {
				if (value.substring(0, 3).equals("TXT")) {
					value = webdriver.findElement(
							WebDriverUtils.locatorToByObj(webdriver,
									value.substring(3))).getAttribute("value");
				} else if (value.substring(0, 3).equals("LNK")) {
					value = webdriver.findElement(
							WebDriverUtils.locatorToByObj(webdriver,
									value.substring(3))).getText();
				} else {
					value = webdriver.findElement(
							WebDriverUtils.locatorToByObj(webdriver,
									value.substring(3))).getText();
				}
				assertEquals(SeleniumDriverTest.hMap.get(field), value);
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				resultDetails.setFlag(false);
				System.out.println("Actual Value :: " + value
						+ "   Expected Value ::"
						+ SeleniumDriverTest.hMap.get(field));
				resultDetails.setErrorMessage("Actual Value :: " + value
						+ "   Expected Value ::"
						+ SeleniumDriverTest.hMap.get(field));
				return resultDetails;
			}
			break;
		case DDL:
			try{
			String RunTimeDDLValues = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getText();
			//System.out.println("RunTimeDDLValues"+RunTimeDDLValues);
			if (RunTimeDDLValues.contains("\n"))
			{
			RunTimeDDLValues=RunTimeDDLValues.replace("\n", "::").trim();
			}
			System.out.println("RunTimeDDLValues"+RunTimeDDLValues);
			System.out.println("Values"+value);
			if (RunTimeDDLValues.equals(value))
			{
			System.out.println("Values are Equal.");
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
			}}catch(Exception e){
			e.printStackTrace();
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Option ::" + value+ ":: not found in DropDown List :: " + field);
			}
			break;	
		case TBL:
			String[] tempValues = value.split(":");
			if ((tempValues[2].length() > 3)
					&& (tempValues[2].substring(0, 3).equals("HMV"))) {
				tempValues[2] = SeleniumDriverTest.hMap.get(tempValues[2]
						.substring(3));
			}
			try {
				System.out.println(field + "." + tempValues[0] + "."
						+ tempValues[1]);
				if (WebDriverUtils.getTable(webdriver, field, tempValues[0],
						tempValues[1]).equalsIgnoreCase(tempValues[2])) {
					System.out.println("Values are Equal.");
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				}
			} catch (Throwable e) {
				System.out.println("Text :: " + tempValues[2]
						+ "   :: not found");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Text :: +" + tempValues[2]
						+ "   :: not found");
				return resultDetails;
			}
			break;

		case TBLTXT:
			
			try {
				field=field.substring(6);
				String rowtext=value.split("::")[0];
				String coltext=value.split("::")[1];
				String colnum=value.split("::")[2];
				//resultDetails=verifyTableText(webdriver,field,rowtext,coltext,colnum);
			} catch (Throwable e) {
				e.printStackTrace();
				return resultDetails;
			}
			break;	
			
		}
		return resultDetails;
	}

	/*
	 * This method is to verify whether the specified element(field) is disabled
	 * or not
	 */

	public ResultDetails MongoSaveUser(String value) {
		value = GETVALUE(value);
		ResultDetails resultDetails = new ResultDetails();
		MyConnection mc = new MyConnection();
		resultDetails.setFlag(false);
		try {
			mc.saveUser(value.trim());
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
		} catch (Throwable e) {
			resultDetails.setFlag(false);
			resultDetails
					.setErrorMessage("Unable to save the user from mongo::");
			return resultDetails;
		}
		return resultDetails;
	}

	public ResultDetails MongoInsertSaveUser() {
		ResultDetails resultDetails = new ResultDetails();
		MyConnection mc = new MyConnection();
		resultDetails.setFlag(false);
		try {
			mc.insertSavedMap();
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
		} catch (Throwable e) {
			resultDetails.setFlag(false);
			resultDetails
					.setErrorMessage("Unable to insert saved the user from mongo::");
			return resultDetails;
		}
		return resultDetails;
	}

	public ResultDetails isDisabled(WebDriver webdriver, String field) {
		System.out.println("field= " + field);
		DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
		field = field.substring(3, field.length());
		ResultDetails resultDetails = new ResultDetails();
		resultDetails.setFlag(false);
		switch (dfs) {
		case IMG:
		case BTN:
		case XPH:
			try {
				assertFalse(webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver, field))
						.isEnabled());
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Element is Enabled::" + field);
				return resultDetails;
			}
			break;
		}
		return resultDetails;
	}

	/*
	 * This is method is to verify whether the specified element(field) is
	 * Enabled or not
	 */
	public ResultDetails isEnabled(WebDriver webdriver, String field) {
		System.out.println("field= " + field);
		DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
		field = field.substring(3, field.length());
		ResultDetails resultDetails = new ResultDetails();
		resultDetails.setFlag(false);
		switch (dfs) {
		case IMG:
		case BTN:
		case XPH:
			try {
				assertTrue(webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver, field))
						.isEnabled());
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Element is Disabled:: " + field);
				return resultDetails;
			}
			break;
		}
		return resultDetails;
	}

	/**
	 * This method is used to store the value from the UI and can refer in
	 * further test steps
	 */
	public ResultDetails storeValue(WebDriver webdriver, String field,
			String key, String fieldName) {

		System.out.println("field= " + field);
		DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
		field = field.substring(3, field.length());
		ResultDetails resultDetails = new ResultDetails();
		resultDetails.setFlag(true);
		if (fieldName.equalsIgnoreCase("NONE"))
			fieldName = field;
		switch (dfs) {
		
		case URL:
			try {
				System.out.println("Key:: "
						+ key
						+ "  Value:: "
						+ webdriver
								.getCurrentUrl());
				SeleniumDriverTest.hMap.put(
						key,
						webdriver.getCurrentUrl());
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				resultDetails.setFlag(false);
				resultDetails
						.setErrorMessage("Error occured while storing the value form:: "
								+ field);
				return resultDetails;
			}
			break;
		case BTW:
			try {
				String st=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getText();
				System.out.println(st+"Stored Value");
				String s2=key;
				String[] s1=s2.split("::");
				st=st.substring(st.indexOf(s1[0])+1, st.indexOf(s1[1]));
				System.out.println(st);
				System.out.println("Key:: "+ s1[2]+ "  Value:: "+ st);
				SeleniumDriverTest.hMap.put(s1[2],st);
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			}catch (Throwable e) {
				resultDetails.setFlag(false);
				resultDetails
						.setErrorMessage("Error occured while storing the value form:: "
								+ field);
				return resultDetails;
			}
			break;
		case TXT:
//			System.out.println("Series text is"+webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getAttribute("value"));
			//webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).
			try {
				System.out.println("Key:: "
						+ key
						+ "  Value:: "
						+ webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field))
								.getText());
				SeleniumDriverTest.hMap.put(
						key,
						webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field))
								.getText());
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				resultDetails.setFlag(false);
				resultDetails
						.setErrorMessage("Error occured while storing the value form:: "
								+ field);
				return resultDetails;
			}
			break;
			
		case VAL:
			try {
				System.out.println("Key:: "
						+ key
						+ "  Value:: "
						+ webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field))
								.getAttribute("value"));
				SeleniumDriverTest.hMap.put(
						key,
						webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field))
								.getAttribute("value"));
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				resultDetails.setFlag(false);
				resultDetails
						.setErrorMessage("Error occured while storing the value form:: "
								+ field);
				return resultDetails;
			}
			break;
		case COB:
			try {
				System.out.println("Key:: "
						+ key
						+ "  Value:: "
						+ webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field))
								.getAttribute("value"));
				SeleniumDriverTest.hMap.put(
						key,
						webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field))
								.getAttribute("value"));
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				resultDetails.setFlag(false);
				resultDetails
						.setErrorMessage("Error occured while storing the value form:: "
								+ field);
				return resultDetails;
			}
			break;
		case XPH:
			try {
				System.out.println("Key:: "
						+ key
						+ "   Value:: "
						+ webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field)).getText());
				SeleniumDriverTest.hMap.put(
						key,
						webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field)).getText());
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				resultDetails.setFlag(false);
				resultDetails
						.setErrorMessage("Error occured while storing the value for:: "
								+ fieldName);
				return resultDetails;
			}
			break;
		case LNK:
			try {
				System.out.println("Key:: "
						+ key
						+ "  Value:: "
						+ webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field)).getText());
				SeleniumDriverTest.hMap.put(
						key,
						webdriver
								.findElement(
										WebDriverUtils.locatorToByObj(
												webdriver, field)).getText());
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				resultDetails.setFlag(false);
				resultDetails
						.setErrorMessage("Error occured while storing the value for "
								+ fieldName);
				return resultDetails;
			}
			break;
		case TBL:
			String[] tempValues = key.split(":");
			String rowNum = tempValues[0];
			String colNum = tempValues[1];
			key = tempValues[2];
			try {
				// String strHtmlID = field+"."+intRow+"."+intCol;
				System.out.println("Key:: "
						+ key
						+ "  Value:: "
						+ WebDriverUtils.getTable(webdriver, field, rowNum,
								colNum));
				// SeleniumDriver.hMap.put(key, selenium.getTable(strHtmlID));
				SeleniumDriverTest.hMap.put(key, WebDriverUtils.getTable(webdriver,
						field, rowNum, colNum));
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				resultDetails.setFlag(false);
				resultDetails
						.setErrorMessage("Error occured while storing the value for "
								+ fieldName);
				return resultDetails;
			}
			break;
		}
		return resultDetails;
	}

	

	public ResultDetails keypress(WebDriver webdriver, String fieldText,
			String value) {
		ResultDetails resultDetails = new ResultDetails();
		// String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		try {
			webdriver.findElement(
					WebDriverUtils.locatorToByObj(webdriver, field)).sendKeys(
					value);
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
			return resultDetails;
		} catch (Throwable e) {
			resultDetails.setFlag(false);
			resultDetails
					.setErrorMessage("For ENTER action type the data field should be TXT");
			return resultDetails;
		}
	}

	public ResultDetails verifynotpresent(WebDriver webdriver, String field,
			String value) {
		// String result =null;
		System.out.println("field= " + field + " value= " + value);
		DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
		field = field.substring(3, field.length());
		ResultDetails resultDetails = new ResultDetails();
		resultDetails.setFlag(false);

		if (value.substring(0, 3).equals("HMV")) {
			value = SeleniumDriverTest.hMap.get(value.substring(3));
		} else {
			value = GETVALUE(value);
		}

		switch (dfs) {

		case MSG:
			try {
				assertFalse(webdriver.getPageSource().toLowerCase().trim()
						.contains(value.toLowerCase().trim()));
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				System.out.println("Text :: +" + value
						+ "   :: found which is NOT expected.");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Text :: +" + value
						+ "   :: found which is NOT expected.");
				return resultDetails;
			}
			break;

		case BTN:
			try {
				assertFalse(WebDriverUtils.isElementPresent(webdriver, value));
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				System.out.println("Object :: +" + value
						+ "   :: found which is NOT expected.");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Object :: +" + value
						+ "   :: found which is NOT expected.");
				return resultDetails;
			}
			break;
		case LNK:
			try {
				assertFalse(WebDriverUtils.isElementPresent(webdriver, value));
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				System.out.println("Link object :: +" + value
						+ "   :: found which is NOT expected.");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Link object :: +" + value
						+ "   :: found which is NOT expected.");
				return resultDetails;
			}
			break;
		}

		return resultDetails;
	}

	/*
	 * '#########################################################################################################
	 * 'Function name : STOREATTRIBUTE 'Description : This function is used to
	 * store the object's attribute value in environment variable. ' 'Parameters
	 * : field parameter should be given as BTN followed by object id
	 * 
	 * @<attribute name> ' Eg - BTNcss=table[id*='libvwreditor']@id 'Assumptions
	 * '#########################################################################################################
	 */
	public ResultDetails STOREATTRIBUTE(WebDriver webdriver, String field,
			String key) {

		System.out.println("field= " + field);
		DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
		field = field.substring(3, field.length());
		ResultDetails resultDetails = new ResultDetails();
		resultDetails.setFlag(true);
		switch (dfs) {
		case BTN:
			try {
				System.out.println(" field ---- : " + field);
				System.out.println("Key:: "
						+ key
						+ "  Value:: "
						+ webdriver.findElement(
								WebDriverUtils.locatorToByObj(webdriver,
										field.split("@")[0])).getAttribute(
								field.split("@")[1]));
				SeleniumDriverTest.hMap.put(
						key,
						webdriver.findElement(
								WebDriverUtils.locatorToByObj(webdriver,
										field.split("@")[0])).getAttribute(
								field.split("@")[1]));
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			} catch (Throwable e) {
				resultDetails.setFlag(false);
				System.out.println(" Msg : " + e.getMessage());
				resultDetails
						.setErrorMessage("Error occured while storing the attribute value of object:: "
								+ field);
				return resultDetails;
			}
			break;
		}
		return resultDetails;
	}

	/*
	 * '#########################################################################################################
	 * 'Function name : VERIFYATTRIBUTE 'Description : This function is used to
	 * verify the object's attribute value. ' 'Parameters : field parameter
	 * should be given as BTN followed by object id @<attribute name> ' Eg -
	 * BTNcss=table[id*='libvwreditor']@id 
	 * '#########################################################################################################
	 */
	public ResultDetails VERIFYATTRIBUTE(WebDriver webdriver, String field,
			String value) {

		System.out.println("field= " + field);
		// DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
		field = field.substring(3, field.length());
		ResultDetails resultDetails = new ResultDetails();
		resultDetails.setFlag(true);
		value = GETVALUE(value);
		/*
		 * switch (dfs) { case CHK:
		 */
		System.out.println(field.split("::")[0]+" --@-- "+field.split("::")[1]);
		try {
			System.out.println(" field ---- : " + field);
			System.out.println("Attribute Value:: "
					+ webdriver.findElement(
							WebDriverUtils.locatorToByObj(webdriver,
									field.split("::")[0])).getAttribute(
							field.split("::")[1]));
			if(field.contains("substring")){
				if(webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver,
								field.split("::")[0])).getAttribute(
						field.split("::")[1]).replaceAll(" ", "").replaceAll("\n", "").contains(value.split("::")[2].replaceAll("\n", ""))){
					resultDetails.setFlag(true);
				}else
					resultDetails.setFlag(false);
				resultDetails.setErrorMessage(value+" not match with attribute value");
				return resultDetails;
			}
			assertEquals(
					value.replaceAll(" ", "").replaceAll("\n", ""),
					webdriver.findElement(
							WebDriverUtils.locatorToByObj(webdriver,
									field.split("::")[0])).getAttribute(
							field.split("::")[1]).replaceAll(" ", "").replaceAll("\n", ""));
			resultDetails.setFlag(true);
//			System.out.println("Attribute '"
//					+ value
//					+ "' Value is as expected :: "
//					+ webdriver.findElement(
//							WebDriverUtils.locatorToByObj(webdriver,
//									field.split("::")[0])).getAttribute(
//							field.split("::")[1]));
			resultDetails.setErrorMessage("");
		} catch (Throwable e) {
			resultDetails.setFlag(false);
			System.out.println(" Msg : " + e.getMessage());
			resultDetails
					.setErrorMessage("Error occured while retriveing the attribute value of object:: "
							+ field);
			return resultDetails;
		}
		return resultDetails;
	}

	public ResultDetails CLICKTABLECPP(WebDriver webdriver, String field,
			String value, String fieldName) {
		ResultDetails resultDetails = new ResultDetails();
		int col;
		try {
			System.out.println("Clicking the row which contains : "
					+ value.trim());
			int RowCount = webdriver.findElements(
					By.xpath("//table[@class='mainbox']/tbody/tr")).size();
			System.out.println(RowCount);
			if (webdriver.getPageSource().contains(
					"<td><h3>All Campaigns</h3></td>"))
				col = 2;
			else
				col = 1;
			for (int i = 1; i < RowCount - 1; i++) {
				WebElement wb = webdriver.findElement(By
						.xpath("//table[@class='mainbox']/tbody/tr[" + i
								+ "]/td[" + col + "]"));
				if (wb.getText().trim().equalsIgnoreCase(value)) {
					webdriver.findElement(
							By.xpath("//table[@class='mainbox']/tbody/tr[" + i
									+ "]/td[" + col + "]/a")).click();
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
					break;
				}
			}
		} catch (Throwable e) {
			resultDetails.setFlag(false);
			System.out.println("error message : " + e.getMessage());
			resultDetails.setErrorMessage(e.getMessage());
			return resultDetails;
		}
		return resultDetails;
	}

	/*
	 * '#########################################################################################################
	 * 'Function name : GETDATE 'Description : This function will return the
	 * date in string format based on the given parameters ' 'Parameters : field
	 * parameter should be given as TXT followed by text box name ' value
	 * parameter should be given in the below format seperated by colon(:) ' Eg
	 * - d:currentdate - will return the current date in MM/DD/YYYY format ' Eg
	 * - d:currentdate:M:1 - will return the date by adding 1 month to the
	 * current date in MM/DD/YYYY format ' Eg - d:currentdate:M:-1 - will return
	 * the date by subtracting 1 month to the current date in MM/DD/YY ' Eg -
	 * d:effectivedate - will return the effective date used to enroll a
	 * subscriber in aetna application ' Eg - d:effectivedate:M:-1 - will return
	 * the effective date by subtracting 1 month that is used to enroll a
	 * subscriber in aetna application ' Eg - d:HMVstrDate:M:1 - will return the
	 * date by adding 1 month to the Hash Map date variable strDate.
	 * '#########################################################################################################
	 */
	public String GETDATE(String value) {
		String[] tempValues = value.split(":");
		String strReqDate = "";
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date today = new Date();

		if (tempValues[0].equalsIgnoreCase("d")) {
			if ((tempValues[1].length() > 3)
					&& (tempValues[1].substring(0, 3).equals("HMV"))) {
				tempValues[1] = SeleniumDriverTest.hMap.get(tempValues[1]
						.substring(3));
			}
			if (tempValues.length == 2) {
				Calendar calendar = Calendar.getInstance();
				if (tempValues[1].equalsIgnoreCase("currentdate")) {
					strReqDate = sdf.format(today);
					System.out.println("Current Date  = " + strReqDate);
					return strReqDate;
				} else if (tempValues[1].equalsIgnoreCase("effectivedate")) {
					calendar.setTime(today);
					calendar.set(Calendar.DAY_OF_MONTH, 1);
					Date reqDate = calendar.getTime();
					strReqDate = sdf.format(reqDate);
					System.out.println("Effective Date = " + strReqDate);
					return strReqDate;
				}
			} else if (tempValues.length == 4) {
				Calendar cal = Calendar.getInstance();
				if (tempValues[1].equalsIgnoreCase("currentdate"))
					cal.setTime(new Date());
				else if (tempValues[1].equalsIgnoreCase("effectivedate")) {
					cal.setTime(today);
					cal.set(Calendar.DAY_OF_MONTH, 1);
				} else {
					try {
						today = (Date) sdf.parse(tempValues[1]);
						cal.setTime(today);
					} catch (ParseException e) {
						System.out.println("Exception :" + e);
					}
				}
				if (tempValues[2].equals("M")) {
					cal.add(Calendar.MONTH, Integer.parseInt(tempValues[3]));
				} else if (tempValues[2].equals("d"))
					cal.add(Calendar.DATE, Integer.parseInt(tempValues[3]));
				else if (tempValues[2].equals("y"))
					cal.add(Calendar.YEAR, Integer.parseInt(tempValues[3]));
				else
					cal.add(Calendar.MONTH, 0);
				strReqDate = sdf.format(cal.getTime());
				System.out.println("Required date : " + strReqDate);
			}
		}
		SeleniumDriverTest.hMap.put("strDate", strReqDate);
		return strReqDate;
	}
}