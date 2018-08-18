package com.java; 											

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import junit.framework.TestCase;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.util.EntityUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.java.ImportnExport.ExportTestResultsExcel;
import com.java.ImportnExport.ImportConfigDetailsExcel;
import com.java.ImportnExport.ImportTestDataDetailsExcel;
import com.java.Objects.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.text.*;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class SeleniumDriverTest extends TestCase { 
	
	private static Logger log = Logger.getLogger(SeleniumDriverTest.class.getName());
	
	private CustomRemoteWebDriver  webdriver;
	public static HashMap<String,String> HM = new HashMap<String,String>();
	//This Hash map object is used to store the UI values , it is used in Store Value method in TestType class
	public static  HashMap<String , String> hMap; //= new HashMap<String , String>();
	public static  HashMap<String , String> parameterDetails; // HashMap to store Parameter Test Data
	public static  LinkedHashMap<Integer , String> TestCaseDetails; // HashMap to store list of test case numbers and titles
	public static  HashMap<Integer , String> TestCaseExecutionDetails; // HashMap to store list of test cases and their test result
	public static  HashMap<Integer , String> TestCaseComments;
	//Creates an excel sheet for ADTag
		Calendar cal = Calendar.getInstance();
		public static final String dateTime = "MMddyy_HHmmss";
		public static final String dateTime1 = "EEE MMM dd hh:mm:ss z yyyy";
		public static String strEmailDateFormat;
		public static long lngTimeDuration;
		public static String AdtestResultPath;
		public static String tc_name_curr = "";	
		public DesiredCapabilities dc;
	//HashMaps to read the Configuration details and Test Data details from Excel Sheet
	HashMap<Integer,ConfigDetails> ConfigDtls = new HashMap<Integer,ConfigDetails>();
	HashMap<Integer,TestDataDetails> TestData = new HashMap<Integer,TestDataDetails>();	
	public static Map map;
	public static  HSSFWorkbook workbook = new HSSFWorkbook();
	public static  HSSFSheet sheet =  workbook.createSheet("AdTag");
	public static  HSSFRow row ;
	public static String Environ="";
	//An ArrayList to store the test cases to be executed
	ArrayList<Integer> TestCases = new ArrayList<Integer>();
	String browser=null;
	static String appUrl = null;
	static String browserType = null;
	static String HubHost;
	static String HubPort;
	public static String NodeIp;
	String CloseAllBrowsers = null;
	String strError = "";
	String strScreenshotName = "";	
	String NodeName;
	int[] ReportCounters = new int[3];
	
	public static ArrayList<Integer> testCaseID = new ArrayList<Integer>();
	//Object To Read Test Data Details Excel Sheet. 
	ImportTestDataDetailsExcel impxl = new ImportTestDataDetailsExcel ();
	/**
	 * This is to read the configuration data from the excel sheets and initialize
	 * the selenium object
	 */
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	
	public void setUp() throws Exception {
		try{
		ReportCounters[0] = 0; 	ReportCounters[1] = 0;	ReportCounters[2] = 0;
		SimpleDateFormat emailDateFormat = new SimpleDateFormat("dd/MMM/yyyy hh:mm a");
		strEmailDateFormat = emailDateFormat.format(cal.getTime());
		Date date = new Date();
		lngTimeDuration = date.getTime();
		if(System.getenv("env")==null)
		{
			System.out.println(System.getenv("env")+" env value is LIVE");
			SeleniumDriverTest.Environ="";
		}
		else{
			SeleniumDriverTest.Environ=System.getenv("env");
			System.out.println("The ENV value in setup method is "+System.getenv("env"));
		}
		
		if(System.getenv("NodeName")==null)
		{
			NodeName="";
			System.out.println("Executing in Grid");
		}
		else{
			NodeName=System.getenv("NodeName");
			System.out.println("Executing in NodeName "+NodeName);
		}
		
		System.out.println("The value of env is "+SeleniumDriverTest.Environ);
		System.out.println(SeleniumDriverTest.Environ);
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateTime);
		String fileName = "ADTag_values" + dateFormat.format(cal.getTime());
		AdtestResultPath = "./TestReports/"+fileName+".xls";
		//Creating Objects for the HashMaps 
		hMap = new HashMap<String , String>();
		parameterDetails = new HashMap<String , String>();
		
		TestCaseDetails = new LinkedHashMap<Integer , String>();
		TestCaseExecutionDetails = new HashMap<Integer , String>();
		//System.setProperty("TestData","TestData");
		//Get the data source values like TestData.xls, Confif.xls etc.
		getDataSource();
		
		//Object To Read Configuration Excel Sheet
    	ImportConfigDetailsExcel impCnfxl=new ImportConfigDetailsExcel();
    	    	    	
    	//Bean to store objects which we get from HashMaps
		ConfigDetails confDtls = new ConfigDetails();
		
		//Reading Data from Config and Test Data details Excel Sheet.
		ConfigDtls=impCnfxl.displayFromExcel();		
		
		//Retrieving first row from Configuration Details Excel Sheet
    	confDtls=(ConfigDetails)ConfigDtls.get(1);
    	
    	//Reading Data from Config and Test Data details Excel Sheet.
    	//TestData = impxl.displayFromExcel();   	
    	
    	//An ArrayList to store the test cases to be executed
    	TestCases = confDtls.getTestCasesToBeExecuted();
    	
    	//browser="*custom C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    	//Browser Type retrieved from the config details sheet
		browserType = confDtls.getBrowser().toUpperCase();		
		//Close all the existing browsers
		try {
			if (SeleniumDriverTest.hMap.get("CloseAllBrowsers").equalsIgnoreCase("true"))
				CloseBrowser(browserType);
		}catch(NullPointerException e) {
			System.out.println("Please provide \"CloseAllBrowsers\" value in DataProperties file");
		}
		
		
		//Retrieving URL from Config Excel Sheet
		
    	appUrl = confDtls.getScriptPath();
    	if(appUrl.contains(SeleniumDriverTest.Environ))
			System.out.println("Environment "+SeleniumDriverTest.Environ+ "  is not dropped");
    	else
    	{
    	String url[]=appUrl.split("\\.",2);
    	System.out.println(url[0]);
    	System.out.println(url[1]);
    	System.out.println(SeleniumDriverTest.Environ);
		appUrl=url[0]+"."+SeleniumDriverTest.Environ+"."+url[1];
    	}
    	System.out.println("url == "+appUrl);
    	
    	log.debug("url : "+appUrl);
    	SeleniumDriverTest.hMap.put("TimeStamp", strEmailDateFormat);
    	SeleniumDriverTest.hMap.put("Browser",confDtls.getBrowser());
    	System.out.println("Browser ===== "+confDtls.getBrowser());
    	SeleniumDriverTest.hMap.put("URL", appUrl);
    	HubHost=java.net.InetAddress.getLocalHost().getCanonicalHostName().toString().trim();
    	SeleniumDriverTest.HubHost=java.net.InetAddress.getLocalHost().getCanonicalHostName().toString().trim();
//    	SeleniumDriverTest.HubHost=SeleniumDriverTest.hMap.get("HubHost").toString().trim();
//    	SeleniumDriverTest.HubPort=SeleniumDriverTest.hMap.get("HubPort").toString().trim();
    	File file = new File("./r");
    	String path = file.getCanonicalPath();
    	if (HubHost.contains("qanyl01q-shr-09.webmdhealth.net")) {
    		if (path.contains("\\projects\\consumer\\"))
    			HubPort = "7777";
    		else if (path.contains("\\projects\\professional\\"))
    			HubPort = "8888";
    		else
    			HubPort = "9999";
    	} else 
    		HubPort = "4444";
		System.out.println("Selenium Hub is running on :: "+HubHost);
    	if(confDtls.getBrowser().equalsIgnoreCase("IE8") || confDtls.getBrowser().equalsIgnoreCase("IE")){
    		 dc=new DesiredCapabilities();
    		 dc = DesiredCapabilities.internetExplorer();
     		dc.setBrowserName("internet explorer");
    		if(NodeName.isEmpty())
    		{}
    		else
    		{
    		dc.setVersion(NodeName);
    		}	
    		URL grid_url=new URL("http://"+HubHost+":"+HubPort+"/wd/hub");
    		webdriver = new CustomRemoteWebDriver(grid_url,dc);
  
    	}
    	else if (confDtls.getBrowser().equalsIgnoreCase("FF")){   
    		DesiredCapabilities dc=new DesiredCapabilities();
    		dc = DesiredCapabilities.firefox();
    		dc.setBrowserName("firefox");
    		if(NodeName.isEmpty())
    		{}
    		else
    		{
    			dc.setVersion(NodeName);
    		}
    		URL grid_url=new URL("http://"+HubHost+":"+HubPort+"/wd/hub");
    		webdriver = new CustomRemoteWebDriver(grid_url,dc);
    	}    		
    	else if (confDtls.getBrowser().equalsIgnoreCase("GCHROME")){
    		DesiredCapabilities dc=new DesiredCapabilities();
    		dc = DesiredCapabilities.chrome();
    		dc.setBrowserName("chrome");
    		if(NodeName.isEmpty())
    		{}
    		else
    		{
    			dc.setVersion(NodeName);
    		}
    		URL grid_url=new URL("http://"+HubHost+":"+HubPort+"/wd/hub");
    		webdriver = new CustomRemoteWebDriver(grid_url,dc);
    	}
    	else if (confDtls.getBrowser().equalsIgnoreCase("SAFARI")){    		
    		//########################################## PENDING
    		DesiredCapabilities dc=new DesiredCapabilities();
    		dc = DesiredCapabilities.safari();
    		dc.setBrowserName("safari");
    		if(NodeName.isEmpty())
    		{}
    		else
    		{
    			dc.setVersion(NodeName);
    		}
    		URL grid_url=new URL("http://"+HubHost+":"+HubPort+"/wd/hub");
    		webdriver = new CustomRemoteWebDriver(grid_url,dc);
    	}    
		int Port=Integer.parseInt(HubPort);
		HttpHost host = new HttpHost(HubHost,Port); 
		DefaultHttpClient client = new DefaultHttpClient(); 
		URL testSessionApi = new URL("http://" + HubHost + ":" + HubPort +"/grid/api/testsession?session=" +  ((CustomRemoteWebDriver) webdriver).getSessionId()); 
		BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST", testSessionApi.toExternalForm()); 
		org.apache.http.HttpResponse response  = client.execute(host,r);
		JSONObject object = new JSONObject(EntityUtils.toString(response.getEntity()));   
		String proxyID = (String) object.get("proxyId");
		SeleniumDriverTest.NodeIp=proxyID.split("//")[1].split(":")[0];
		System.out.println("Testcases are being executed in "+proxyID.split("//")[1].split(":")[0]+" Node");
    } catch(Exception e) {
    	e.printStackTrace();
      }
	}
	
	/**
	 * This method is to stop the selenium object
	 */
    protected void tearDown() throws Exception {
    	if(webdriver!=null)
    		webdriver.close();
    }
    
    
	public String getNodeIp() {
		try {
			int Port = Integer.parseInt("4444");
			HttpHost host = new HttpHost("qatll11q-shr-08.portal.webmd.com",
					Port);
			DefaultHttpClient client = new DefaultHttpClient();
			URL testSessionApi = new URL("http://"
					+ "qatll11q-shr-08.portal.webmd.com" + ":" + "4444"
					+ "/grid/api/testsession?session="
					+ ((CustomRemoteWebDriver) webdriver).getSessionId());
			BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest(
					"POST", testSessionApi.toExternalForm());
			org.apache.http.HttpResponse response = client.execute(host, r);
			JSONObject object = new JSONObject(EntityUtils.toString(response
					.getEntity()));
			String proxyID = (String) object.get("proxyId");
			String NodeIp = proxyID.split("//")[1].split(":")[0];
			System.out.println("Testcases are being executed in "
					+ proxyID.split("//")[1].split(":")[0] + " Node");
			return NodeIp;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
   
    /**
     * This method is to read the testdata from excel sheet, submit them to the 
     * browser and writing the results to an excel sheet.
     */
    public void testInitiation() throws Exception {
    	
		int casefound = 0;
		// variables used to track the failed cases
		boolean sequenceNumber = false;
		int previousFailedTCID = 0;		
		String failedCases = "";
		String strErrorMsg = "";
		String arrCon[] = null;
		int ifPos = 1;
		
    	//Object To Write Test Results into an Excel Sheet
    	ExportTestResultsExcel expxl = new ExportTestResultsExcel ();
    	TemplateGenerator report = new TemplateGenerator ();
	
        //This is to store the success & failure of a test case
		ResultDetails resultDetails=new ResultDetails();		
		
		//This is to store test data of a test case
		TestDataDetails tdd=new TestDataDetails();
		
		System.out.println("The number of cases to be executed..."+TestCases.size());
		try {
			webdriver.get(appUrl);
			webdriver.manage().window().maximize();
		}
		catch(Throwable e){
			System.out.println("exception occured : " + e.getMessage());
		}
		expxl.exportExcelHeader();
		//This is to repeat the test for the number of testcases that are in the Test Data
		for(int i=0;i<TestCases.size();i++){
			ExportTestResultsExcel.Comments="";
			//Reading Data from Test Data details Excel Sheet for specific Test Case Id.
        	TestData = impxl.displayFromExcel(TestCases.get(i));
        	//Array list to store the result of the test case
        	ArrayList<String> result = new ArrayList<String>();
    	    //This is to get the Test Data details i.e. a row in Test Data Details, & submit to the selenium
			casefound = 0;
			String act="";
    		for(int k=1;k<=TestData.size();k++){
    			if(act.equals("ignore")){
    				continue;
    			}
    			resultDetails.setFlag(false);//This is to store the success & failure of a test case    			
    			//Retrieving & storing the testdata in a bean
    			tdd=(TestDataDetails)TestData.get(k);
        		if(TestCases.get(i).equals(tdd.getTestCaseID())&& tdd.getBrowserType().indexOf(browserType) != -1 ||tdd.getBrowserType().indexOf("common") != -1){	
        		if (casefound == 0) {
    					casefound = 1;
    					//Store testcase details in HashMap
    		    		TestCaseDetails.put(tdd.getTestCaseID(), tdd.getTestCaseTitle());
    				}
        			System.out.println("****************************************************");
					System.out.println("Test case being Executed: "+tdd.getTestCaseID()+" :: Step NO: "+tdd.getTestDataID()+" Out of "+TestData.size());
    				int tcID =tdd.getTestCaseID();
    				if (!(testCaseID.contains(tcID))) { testCaseID.add(tcID);}
//	    			Thread.sleep(3000);
	        		//These String variables are to store the data Fields & corresponding data Values of the test case
	        		String dataFields=tdd.getDataFields();
	    			String dataValues=tdd.getDataValues();
					String fieldName=tdd.getFieldName();
	    			if(dataValues==null)
	    				dataValues = "";
	    			
		    		System.out.println("dataFields : "+dataFields);
		    		System.out.println("dataValues : "+dataValues);
	        		
		    		//This is object of the class in which different functions exists with different functionalities
		    		TestType test=new TestType();
		    				    		
		    		//From Here, Based on the type of test mentioned in the test data, corresponding functionalities are implemented
		    		
		    			//System.out.println("<In Set values of the page>");
		    			//Internally Based on the data provided in the test Data again the functionalities differ
		    			if(tdd.getDataFields()!=null){//&& tdd.getExpectedPage()!=null{
			    			//Submitting the fields and values to Selenium
			    			System.out.println("-----------setting values------------");
				    		//String fieldType = dataFields.substring(0, 3);
				    		String actionType = tdd.getActionType();
				    		System.out.println("Action Type : "+ actionType);
				    		if(actionType == null){
				    			resultDetails.setErrorMessage("Action Field is Empty");
				    			resultDetails.setFlag(false);
				    		}else if(actionType.equalsIgnoreCase("ignore")){
				    			resultDetails.setWarningMessage("This is Manual Test Case.Plz execute by Manually");
				    			resultDetails.setFlag(true);
				    			 act="ignore";
					    		}
				    		else {
				    			//ExportTestResultsExcel.Comments="";
				    			tc_name_curr = tdd.getTestCaseTitle();
				    			test.webdriver = webdriver;
				    			resultDetails = test.performAction(dataFields,dataValues, actionType, fieldName);
				    			webdriver = (CustomRemoteWebDriver) test.webdriver;
				    		}						    		
			    		}
	    			//check whether there is IF condition associated with the test step
		    		if (tdd.getActionType().equalsIgnoreCase("IF")) {
		    			if (!resultDetails.getFlag())
		    				ifPos = 2;
		    			else
		    				ifPos = 1;
		    			arrCon = tdd.getDataValues().split("::");		
		    			System.out.println("Size : "+arrCon.length+" "+arrCon[0]+"::"+arrCon[1]+"::"+arrCon[2]);
		    		}
		    			
		    		//Adding the test case id, data id and the result to the ArrayList		    		
		    		result.add(Integer.toString(tdd.getTestCaseID()));
		    		result.add(Integer.toString(tdd.getTestDataID()));
		    		result.add((String)tdd.getTestCaseTitle());	   		

		    		if(!resultDetails.getFlag()){
		    			System.out.println("?   F A I L   ?");
		    			if (tdd.getActionType().equalsIgnoreCase("IF"))
		    				result.add("Pass");
		    			else
		    				result.add("Fail");
		    			
							SimpleDateFormat ScreenShot = new SimpleDateFormat("MMddyy_HHmmss");
							Calendar cal = Calendar.getInstance();
							SimpleDateFormat strDirFormat = new SimpleDateFormat("MMddyy");
							String strDirectoryName = strDirFormat.format(new Date());
							File f = new File(".//TestOutputs//ScreenShots//"+strDirectoryName);
							try{
								if (!f.exists()) {
									f.mkdir();
//									System.out.println("Directory Created");
								}
							} catch(Throwable e){
								System.out.println("Unable to create directory");
							}
							strScreenshotName = ".\\TestOutputs\\ScreenShots\\"+strDirectoryName+"\\"+tdd.getTestCaseID()+"_"+ScreenShot.format(cal.getTime())+".png";
							
							boolean isScreenShot = false;
							try {
								f = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);
								isScreenShot = true;
							}catch (Exception e) {
								if (e.getMessage().contains("Error communicating with the remote browser. It may have died.")) {
									resultDetails.setErrorMessage("Browser Crashed...");
								}
							}
							
							try {
								  FileUtils.copyFile(f, new File(strScreenshotName));
								} catch (IOException ioe) {
										
									System.out.println(ioe.getMessage());
								}
							if (isScreenShot)
								strScreenshotName = ". Screen Shot : "+strScreenshotName;
							else
								strScreenshotName = "";
						
						/**
						 *  !!Block Start!! To track the failed testcases list in a string variable
						 */						
						if (tdd.getActionType().equalsIgnoreCase("IF")) {
							if (failedCases == "") {								
								failedCases = tdd.getTestCaseID()+"";
								previousFailedTCID = tdd.getTestCaseID();
							} else {
								if (tdd.getTestCaseID() - previousFailedTCID == 1) {
									if (sequenceNumber)
										failedCases = failedCases.replace(failedCases.substring(failedCases.lastIndexOf("-")+1),tdd.getTestCaseID()+"");
									else
										failedCases =  failedCases+"-"+tdd.getTestCaseID();
									sequenceNumber = true;
								} else {
									failedCases = failedCases+","+tdd.getTestCaseID();
									sequenceNumber = false;								
								}
								previousFailedTCID = tdd.getTestCaseID();
							}
						}
						System.out.println("failedCases = "+failedCases);
						if(resultDetails.getErrorMessage()!= null) {
		    				strErrorMsg = "Test case failed at Step No. :: "+ tdd.getTestDataID() +"   Error Message ::  "+ resultDetails.getErrorMessage();
		    				result.add(strErrorMsg+strScreenshotName);
		    			} else {
		    				strErrorMsg = "Test case failed at Step No. :: "+ tdd.getTestDataID();
		    				result.add(strErrorMsg+strScreenshotName);
		    			}
						TestCaseExecutionDetails.put(tdd.getTestCaseID(), "FAIL"+strErrorMsg);
		    		}
		    		else{
		    			System.out.println("?   P A S S   ?");
		    			if(act.equalsIgnoreCase("ignore")){
		    				result.add("Warning");
		    			}else{
		    			result.add("Pass");
		    			}
		    			String strMsg = "";	    			
//		    			System.out.println("Html added"+ExportTestResultsExcel.Comments);
		    			TestCaseExecutionDetails.put(tdd.getTestCaseID(), "PASS"+ExportTestResultsExcel.Comments);
		    			//TestCaseExecutionDetails.put(tdd.getTestCaseID(), "PASS"+System.getProperty("comment"));
		    			if ((resultDetails.getWarningMessage()!= null)) {
		    				if (SeleniumDriverTest.hMap.get("strWarningMessage")!= null) {
		    					strMsg = SeleniumDriverTest.hMap.get("strWarningMessage") +"\n"+ "!! Warning !! Step No. :: "+ tdd.getTestDataID() + "   Message ::  " + resultDetails.getWarningMessage();
		    				} else {
		    					strMsg = "!! Warning !! Step No. :: "+ tdd.getTestDataID() + " Message ::  " + resultDetails.getWarningMessage();
		    				}
		    				System.out.println("strMsg = "+strMsg);
		    				SeleniumDriverTest.hMap.put("strWarningMessage", strMsg);
		    				result.add(strMsg);		    				
		    			} else if (SeleniumDriverTest.hMap.get("strWarningMessage")!= null) {
		    				result.add(SeleniumDriverTest.hMap.get("strWarningMessage"));
		    			} else {
		    				SeleniumDriverTest.hMap.put("strWarningMessage", "");
		    				result.add("");
		    			}
		    		}
		    		
		    		result.add((new java.util.Date()).toString());
		    		
		    		try {
		    			if (tdd.getActionType().equalsIgnoreCase("IF")) {
			    			if (arrCon[ifPos].toUpperCase().equalsIgnoreCase("NEXT")) {
			    				System.out.println("Goto Next Step");
			    			} else {
			    				k = Integer.parseInt(arrCon[ifPos])-1;
			    				System.out.println( "step change to "+ k);
			    			}
			    			resultDetails.setFlag(true);
			    		}
		    		} catch (NumberFormatException ex) {
		    			System.out.println("invalid step number."+arrCon[ifPos]);
		    		}
					
		    		//To Stop executing the current test case and to proceed with the next test case if any of the test fails.
		    		if(!resultDetails.getFlag()) {
		    			try {
		    				String strTitle = webdriver.getTitle();
		    			//To check if the failure is caused due to 404 error then to load the logout url.
		    			if(strTitle.equalsIgnoreCase("404 Not Found")) {
		    				try {
		    					webdriver.get(appUrl);
		    				}
		    				catch(Throwable e){
		    					System.out.println("Exception : "+e.getMessage());
		    				}
		    			}
		    			}catch(Exception e) {}
		    			break; 
		    		}		    		
	    		}
	    		
	    		//Loop the steps
	    		try {
					if (tdd.getCondition() != null && tdd.getCondition().toUpperCase().indexOf("LOOP") != -1) {
						int from = k+1;
						String condition[] = tdd.getCondition().split(":");
						if (condition[1].split("-")[0].toUpperCase().equalsIgnoreCase("NEXT")) {
		    				System.out.println("Goto Next Step");
		    				from = k; 
		    			} else {
		    				from = Integer.parseInt(condition[1].split("-")[0]); //Loop:3-5:4 - from is 3
		    			}
						int to = Integer.parseInt(condition[1].split("-")[1]); //Loop:3-5:4 - to is 5
						int lcnt = Integer.parseInt(condition[2]); //Loop:3-5:4 - loop for 4 times
						executeSteps(webdriver, from, to , lcnt, i, TestData, resultDetails, result);
					}
	    		} catch (NumberFormatException ex) {
	    			System.out.println("invalid step number."+arrCon[1].split(":")[1]);
	    		}
    		}
	    		
    		System.out.println("****************************************************");
			if (casefound == 1) {	    		
				List<String> exportResult = new ArrayList<String>();
				exportResult = result;
				ReportCounters[0] = ReportCounters[0] + 1;
				
				for(int ii=0;ii<result.size(); ii=ii+6){
					if(result.get(ii+3).equals("Fail")) {
						exportResult = result.subList(ii, ii+6);
						ReportCounters[2] = ReportCounters[2] + 1;
						break;
					}
				}
				expxl.exportExcelRows(exportResult);    		
				File file = new File(".//temp.txt");
				file.delete();
				try {
					Set<String> windowHandles = webdriver.getWindowHandles();
					if (windowHandles.size()>1)
						webdriver.switchTo().window(windowHandles.toArray()[0].toString());
					webdriver.manage().deleteAllCookies();
					Thread.sleep(1500);
					webdriver.manage().deleteAllCookies();
					webdriver.get(appUrl);
				}
				catch(Throwable e){
					if (e.getMessage().contains("Error communicating with the remote browser. It may have died."))
						System.out.println("Browser Crashed...");
					if(browserType.equalsIgnoreCase("IE8") || browserType.equalsIgnoreCase("IE")){
			    		 dc=new DesiredCapabilities();
			    		 dc = DesiredCapabilities.internetExplorer();
			     		dc.setBrowserName("internet explorer");
			    		if(NodeName.isEmpty())
			    		{}
			    		else
			    		{
			    		dc.setVersion(NodeName);
			    		}	
			    		URL grid_url=new URL("http://"+HubHost+":"+HubPort+"/wd/hub");
			    		webdriver = new CustomRemoteWebDriver(grid_url,dc);
			  
			    	}
			    	else if (browserType.equalsIgnoreCase("FF")){   
			    		DesiredCapabilities dc=new DesiredCapabilities();
			    		dc = DesiredCapabilities.firefox();
			    		dc.setBrowserName("firefox");
			    		if(NodeName.isEmpty())
			    		{}
			    		else
			    		{
			    			dc.setVersion(NodeName);
			    		}
			    		URL grid_url=new URL("http://"+HubHost+":"+HubPort+"/wd/hub");
			    		webdriver = new CustomRemoteWebDriver(grid_url,dc);
			    	}    		
			    	else if (browserType.equalsIgnoreCase("GCHROME")){
			    		DesiredCapabilities dc=new DesiredCapabilities();
			    		dc = DesiredCapabilities.chrome();
			    		dc.setBrowserName("chrome");
			    		if(NodeName.isEmpty())
			    		{}
			    		else
			    		{
			    			dc.setVersion(NodeName);
			    		}
			    		URL grid_url=new URL("http://"+HubHost+":"+HubPort+"/wd/hub");
			    		webdriver = new CustomRemoteWebDriver(grid_url,dc);
			    	}
			    	else if (browserType.equalsIgnoreCase("SAFARI")){    		
			    		//########################################## PENDING
			    		DesiredCapabilities dc=new DesiredCapabilities();
			    		dc = DesiredCapabilities.safari();
			    		dc.setBrowserName("safari");
			    		if(NodeName.isEmpty())
			    		{}
			    		else
			    		{
			    			dc.setVersion(NodeName);
			    		}
			    		URL grid_url=new URL("http://"+HubHost+":"+HubPort+"/wd/hub");
			    		webdriver = new CustomRemoteWebDriver(grid_url,dc);
			    	}    

		    		webdriver.get(appUrl);
		    		webdriver.manage().window().maximize();
		    		System.out.println("*** New Browser Instance created...");
				}
			}
			
    	}
    	
    	//Store the failed cases in Hash Map
    	hMap.put("failedCases", failedCases);
    	
    	//Export the Test summary report and Build HTML report
		ReportCounters[1] = ReportCounters[0] - ReportCounters[2];
		report.buildTemplate(ReportCounters[0],ReportCounters[1],ReportCounters[2]);
		
		//Send Email report
		try{
    	EmailTestReport etp = new EmailTestReport();    		
		etp.postMail(ReportCounters,browserType,appUrl); 
		List<String> resultSummary = new ArrayList<String>();
		resultSummary.add(browserType);
		resultSummary.add(Integer.toString(ReportCounters[0]));
		resultSummary.add(Integer.toString(ReportCounters[1]));
		resultSummary.add(Integer.toString(ReportCounters[2]));
		expxl.exportTestSummary(resultSummary);
		}catch(Exception e)
		{
			System.out.println("Unable to send a mail due to unreachable host ");
		}	         
    }
    
    public Map getMap()
    {
    	return this.map;
    }
    
    public void setMap(Map map)
    {
    	this.map=map;
    }
    public String getGuid()
    {
    	String source=webdriver.getPageSource();
    	String guid="";
		if (source.contains("guid=")) 
    			 guid=source.split("guid=",2)[1];
			if (guid.contains("&"))
			{
				guid=guid.split("&")[0];
			}
    	else
    		System.out.println("doesn't contain guid in the source code");
			return guid;
    }
	
    /*'#########################################################################################################
	'Function name		:	CloseBrowser
	'Description		:	Close all the existing open browsers
	'Parameters			:	imageName type of browser - IE8 / IE6 / FF / GCHROME
	'#########################################################################################################*/
	public void CloseBrowser(String imageName) {
		if(imageName.equalsIgnoreCase("IE8") || imageName.equalsIgnoreCase("IE"))
    		imageName="iexplore.exe";
    	else if (imageName.equalsIgnoreCase("FF"))
    		imageName="firefox.exe";
    	else if (imageName.equalsIgnoreCase("GCHROME"))
    		imageName="chrome.exe";
    	else if (imageName.equalsIgnoreCase("SAFARI"))
    		imageName = "Safari.exe";

		Runtime r = Runtime.getRuntime(); 
		// check to see if the process is running 
		Process p = null; 
		String listCommand = "tasklist /FI \"IMAGENAME eq " + imageName + "\" /NH /FO CSV"; 
		String killCommand = "taskkill /f /im " + imageName + " /t"; 
		try { 
				p = r.exec(listCommand); 
		} catch (IOException e) { 
				//fail("An IOException occurred while trying to exec '" + listCommand + "'"); 
		} 
		try { 
				// if running, error stream will be empty, i.e. null 
				BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream())); 
				if(err.readLine() == null) { 
						// if it is running, kill it 
						r.exec(killCommand); 
						Thread.sleep(5000);     // give it 5 seconds to die 
				} 
		} catch (IOException e) { 
				//fail("An IOException occurred while trying to exec '" + killCommand + "'"); 
		} catch (InterruptedException e) { 
				// Do nothing of the sleep gets interrupted. 
		} catch (Exception e) { 
				//fail("Some unknown error occurred in misc.killProcess(" + imageName + ")"); 
		} 
    }
	
	/*'#########################################################################################################
	'Function name		:	getDataSource
	'Description		:	Method to get all the Data source details.
	'Parameters			:	
	'#########################################################################################################*/
	public void getDataSource(){
	
		Properties dbProps=new Properties();
		
		try{
			FileInputStream in = new FileInputStream("./TestInputs/properties/dataSource.properties");
			dbProps.load(in);
			hMap.put("configFile", dbProps.getProperty("configFile"));
			hMap.put("testDataFile", dbProps.getProperty("testDataFile"));
			hMap.put("HubHost",dbProps.getProperty("HubHost"));
			hMap.put("HubPort",dbProps.getProperty("HubPort"));
			hMap.put("CloseAllBrowsers", dbProps.getProperty("CloseAllBrowsers"));
			dbProps.getProperty("ExecuteFailedCases");
			in.close();
		}
		catch(IOException io){
			io.printStackTrace();
			System.out.println("Unable to read Datasource Properties File.");
		}		
	}
	
	public void executeSteps(WebDriver webdriver, int from, int to, int lcnt, int i, HashMap<Integer,TestDataDetails> TestData, ResultDetails resultDetails, ArrayList<String> result) {
		
		String arrCon[] = null;
		String strErrorMsg = "";
		String strMsg = "";
		
		for (int l=1; l<=lcnt; l++) {
			System.out.println("Looping" + l + "TCID = "+i);
			for(int k=from;k<=to;k++){
	    		
				//Retrieving & storing the testdata in a bean
				TestDataDetails tdd1=(TestDataDetails)TestData.get(k);
				
				System.out.println("TestData size ::: " +TestData.size() + " tdd.getTestCaseID() : "+tdd1.getTestCaseID());
				//Finding the test case to be executed in the test data details
				//if(TestCases.get(i).equals(tdd1.getTestCaseID()) && (tdd1.getBrowserType().equalsIgnoreCase("COMMON") || tdd1.getBrowserType().indexOf(browserType) != -1)){
				if((tdd1.getBrowserType().equalsIgnoreCase("COMMON") || tdd1.getBrowserType().indexOf(browserType) != -1)){
					System.out.println("****************************************************");
					System.out.println("Test case being Executed: "+tdd1.getTestCaseID()+" :: Step NO: "+tdd1.getTestDataID()+" Out of "+TestData.size());
					int tcID =tdd1.getTestCaseID();
					if (!(testCaseID.contains(tcID))) { testCaseID.add(tcID);}        		      		
	        		
	        		//These String variables are to store the data Fields & corresponding data Values of the test case
	        		String dataFields=tdd1.getDataFields();
	    			String dataValues=tdd1.getDataValues();
					String fieldName=tdd1.getFieldName();
	    			if(dataValues==null)
	    				dataValues = "";	    			
	    			
		    		System.out.println("dataFields : "+dataFields);
		    		System.out.println("dataValues : "+dataValues);
		    		//System.out.println("Test Type : "+tdd.getTestType());
	        		
		    		//This is object of the class in which different functions exists with different functionalities
		    		TestType test=new TestType();					
		    				    		
		    		//From Here, Based on the type of test mentioned in the test data, corresponding functionalities are implemented
		    		
		    			System.out.println("<In Set values of the page>");
		    			//Internally Based on the data provided in the test Data again the functionalities differ
		    			if(tdd1.getDataFields()!=null){//&& tdd.getExpectedPage()!=null{
		    				System.out.println("{Data Fields & Data Values Exist}");		    				
		    				//String the fields & values by tokensing the dataFields,dataValues into an ArrayList		    				
			    			//Submitting the fields and values to Selenium
			    			System.out.println("--------------------------setting values-----------------------------");
				    		String actionType = tdd1.getActionType();
				    		System.out.println("Action Type : "+ actionType);				    		
				    		if(actionType == null){
				    			resultDetails.setErrorMessage("Action Field is Empty");
				    			resultDetails.setFlag(false);				    		
				    		} else {
				    			test.webdriver = webdriver;
				    			resultDetails = test.performAction(dataFields,dataValues, actionType, fieldName);
				    			webdriver = (CustomRemoteWebDriver) test.webdriver;
//				    			System.out.println(" RESULT "+resultDetails.getFlag());
				    		}						    		
			    		}
		    		//check whether there is IF condition associated with the test step
		    		if (tdd1.getCondition() != null && tdd1.getCondition().indexOf("IF") != -1) { //  && tdd.getActionType() == "VERIFY") {
		    			System.out.println(" tdd.getCondition() "+tdd1.getCondition());
		    			arrCon = tdd1.getCondition().split("-");		    			
		    		}		    		
	
		    		//Adding the test case id, data id and the result to the ArrayList		    		
		    		result.add(Integer.toString(tdd1.getTestCaseID()));
		    		result.add(Integer.toString(tdd1.getTestDataID()));
		    		result.add((String)tdd1.getTestCaseTitle());	   		
	
		    		//Test case Pass / Fail 
		    		if(!resultDetails.getFlag()){
		    			System.out.println("?   F A I L   ?");
		    			result.add("Fail");
//						System.out.println("Browser Type : "+browserType);
						if (browserType.equals("FF")) {
							SimpleDateFormat ScreenShot = new SimpleDateFormat("MMddyy_HHmmss");
							Calendar cal = Calendar.getInstance();
							SimpleDateFormat strDirFormat = new SimpleDateFormat("MMddyy");
							String strDirectoryName = strDirFormat.format(new Date());
							File f = new File(".//TestOutputs//ScreenShots//"+strDirectoryName);
							try{
								if (!f.exists()) {
									f.mkdir();
									System.out.println("Directory Created");
								}
							} catch(Throwable e){
								System.out.println("Unable to create directory");
							}
							strScreenshotName = ".//TestOutputs//ScreenShots//"+strDirectoryName+"\\"+tdd1.getTestCaseID()+"_"+ScreenShot.format(cal.getTime())+".png";
							
							f = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);							
							try {
								  FileUtils.copyFile(f, new File(strScreenshotName));
								} catch (IOException ioe) {
									System.out.println(ioe.getMessage());
								}							
								strScreenshotName = ". Screen Shot : "+strScreenshotName;
						}
						/**
						 *  !!Block Start!! To track the failed testcases list in a string variable
						 */
	
		    			/*try {
				    		if (tdd.getCondition() != null && tdd.getCondition().indexOf("IF") != -1) {			    			
				    			if (arrCon[1].split(":")[0].toUpperCase().equalsIgnoreCase("NEXT")) {
				    				System.out.println("Continue next step...");
				    			} else {
				    				new java.math.BigInteger(arrCon[1].split(":")[0]);
				    				k = Integer.parseInt(arrCon[1].split(":")[0])-1;
				    				System.out.println("step change to "+ k);
				    			}
				    		}
			    		} catch (NumberFormatException ex) {
			    			System.out.println("invalid step number."+arrCon[1].split(":")[0]);
			    		}*/
		    			//TestCaseExecutionDetails.put(tdd.getTestCaseID(), "FAIL"+strErrorMsg);
		    		}
		    		else{
		    			System.out.println("?   P A S S   ?");
		    			result.add("Pass");		    			
		    			//TestCaseDetails.put(tdd.getTestCaseID(), tdd.getTestCaseTitle());		    			
		    			//TestCaseExecutionDetails.put(tdd.getTestCaseID(), "PASS");
		    			if ((resultDetails.getWarningMessage()!= null)) {
		    				if (strMsg != null) {
		    					strMsg =  strMsg +"\n"+ "!! Warning !! Step No. :: "+ tdd1.getTestDataID() + "   Message ::  " + resultDetails.getWarningMessage();
		    				} else {
		    					strMsg = "!! Warning !! Step No. :: "+ tdd1.getTestDataID() + "   Message ::  " + resultDetails.getWarningMessage();
		    				}
		    			} 
		    			result.add(strMsg);		    			
		    			//System.out.println("failedCases = "+failedCases);
		    		}
		    		result.add((new java.util.Date()).toString());
		    		/*try {
			    		if (tdd.getCondition() != null && tdd.getCondition().indexOf("IF") != -1) {			    			
			    			if (arrCon[0].split(":")[1].toUpperCase().equalsIgnoreCase("NEXT")) {
			    				System.out.println("Goto Next Step");
			    			} else {
			    				new java.math.BigInteger(arrCon[0].split(":")[1]);
			    				k = Integer.parseInt(arrCon[0].split(":")[1])-1;
			    				System.out.println( "step change to "+ k);
			    			}
			    		}
		    		} catch (NumberFormatException ex) {
		    			System.out.println("invalid step number."+arrCon[0].split(":")[1]);
		    		}*/
	    		}
			}
		}
		System.out.println("Looping End......");
	}
	
}