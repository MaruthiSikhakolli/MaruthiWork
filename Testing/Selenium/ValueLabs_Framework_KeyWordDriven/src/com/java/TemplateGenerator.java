package com.java;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import com.java.SeleniumDriverTest;
import com.java.ImportnExport.ExportTestResultsExcel;

public class TemplateGenerator
{
	private static Logger log = Logger.getLogger(TemplateGenerator.class.getName());
	public TemplateGenerator (){}
	
	public void buildTemplate(int Total, int Passed, int Failed) {
		String color = "FFFFFF";
		String testType = "Regression";
		String resultsType = "Regression Test Results";
		String strWaiting = "";
		String strNotReady = "";
		String  strChart = Passed + "," + Failed + "," + "0" + "," + "0";
		String enviro = "QA";
		String strBrowser = SeleniumDriverTest.hMap.get("Browser");
		String strURL = SeleniumDriverTest.hMap.get("URL");
		String detailFileName = "";
		String parentFolder = "./TestOutputs/TestReports/";
		String chartDimensions = "";
		String chartMaxHeight = "";
		String arrCaseID = "";
		String arrCaseTitle = "";
		String arrCaseResult = "";
		
		if (Total < 10) {
			chartDimensions = "0|5|10";
			chartMaxHeight = "10";
		} else if ((Total >= 10) && (Total < 20)) {
			chartDimensions = "0|5|10|15|20";
			chartMaxHeight = "20";
		} else if ((Total >= 20) && (Total < 50)) {
			chartDimensions = "0|10|20|30|40|50";
			chartMaxHeight = "50";
		} else if ((Total >= 50) && (Total < 100)) {
			chartDimensions = "0|20|40|60|80|100";
			chartMaxHeight = "100";
		} else if ((Total >= 100) && (Total < 200)) {
			chartDimensions = "0|40|80|120|160|200";
			chartMaxHeight = "200";
		} else if ((Total >= 200) && (Total < 300)) {
			chartDimensions = "0|50|100|150|200|250|300";
			chartMaxHeight = "300";
		} else if ((Total >= 300) && (Total < 400)) {
			chartDimensions = "0|80|160|240|320|400";
			chartMaxHeight = "400";
		} else if ((Total >= 400) && (Total < 500)) {
			chartDimensions = "0|100|200|300|400|500";
			chartMaxHeight = "500";
		} else if ((Total >= 500) && (Total < 800)) {
			chartDimensions = "0|160|320|480|640|800";
			chartMaxHeight = "800";
		} else if ((Total >= 800) && (Total < 1000)) {
			chartDimensions = "0|200|400|600|800|1000";
			chartMaxHeight = "1000";
		} else {
			System.out.println("Error: Invalid Chart Scale");
		}
	    
		/*System.out.println("chartDimensions : "+chartDimensions);
		System.out.println("chartMaxHeight :"+chartMaxHeight);*/
		
		try {
			detailFileName = parentFolder +SeleniumDriverTest.hMap.get("htmlFile");
			//System.out.println("detailFileName :"+detailFileName);
			
			writeDetailFile (detailFileName, color, testType, resultsType, enviro, Total, Passed, Failed, strBrowser, strURL, strChart, chartDimensions, chartMaxHeight, arrCaseID, arrCaseTitle, arrCaseResult);
		} catch (Exception e) {
			log.error("Error in Template Generator.");
		}
	}

	public void writeDetailFile(String name, String color, String testType, String resultsType, String enviro, int tested, int passed, int failed, String strBrowser, String strURL, String strChart, String chartDimensions, String chartMaxHeight, String arrCaseID, String arrCaseTitle, String arrCaseResult) {

		String file = name;
		String fontColor = "";
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));		    
					
			out.write("<html> \n <body leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" bgcolor='#FFFFFF'>");
			out.write("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor='#FFFFFF'> \n <tr> \n <td valign=\"top\" align=\"left\">");
			out.write("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">");
			out.write("<td align=\"left\" valign=\"middle\" style=\"background-color:#" + color + ";border-top:0px solid #333333;border-bottom:1px dashed #000000;\"><left><a href=\"\"><IMG id=editableImg1 SRC=\"http://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/usability/2012/LBi_Masthead/logo.jpg\" BORDER=\"0\" align=\"center\"></a></left></td>");
			out.write("<td align=\"left\" valign=\"middle\" style=\"background-color:#" + color + ";border-top:0px solid #333333;border-bottom:1px dashed #000000;\"><span style=\"font-size:14px;font-weight:bold;color:#000000;line-height:200%;font-family:verdana;text-decoration:none;\">" + "AUTOMATION TEST RESULTS" + "</span></td>");
			out.write("<td align=\"\" valign=\"middle\" style=\"background-color:#" + color + ";border-top:0px solid #000000;border-bottom:1px dashed #000000;\"><span style=\"font-size:15px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;text-decoration:none;\"></span></td>");
			out.write("</tr> \n </table> \n <table width=\"100%\" cellpadding=\"3\" cellspacing=\"0\"> \n <tr>");
			out.write("<td align=\"left\" valign=\"middle\" style=\"background-color:#" + color + ";border-top:5px solid #" + color + ";border-bottom:1px dashed #000000;\"><span style=\"font-size:12px;font-weight:bold;color:#000000;line-height:200%;font-family:verdana;text-decoration:none;\">" + "Date & Time : "+SeleniumDriverTest.hMap.get("TimeStamp") + "</span></td>");
			out.write("<td align=\"left\" valign=\"middle\" style=\"background-color:#" + color + ";border-top:5px solid #" + color + ";border-bottom:1px dashed #000000;\"><span style=\"font-size:12px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;text-decoration:none;\">" + "Test Type : "+ testType + "</span></td>");
			out.write("<td align=\"left\" valign=\"middle\" style=\"background-color:#" + color + ";border-top:5px solid #" + color + ";border-bottom:1px dashed #000000;\"colspan=2><span style=\"font-size:12px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;text-decoration:none;\">" + "Application : <font color=\"#000000\">Communication Platform</font>" + "</span></td></tr>");
			//out.write("<td align=\"left\" valign=\"middle\" style=\"background-color:#" + color + ";border-top:5px solid #" + color + ";border-bottom:1px dashed #000000;\"><span style=\"font-size:12px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;text-decoration:none;\">" + "Env : "+ enviro + "</span></td> \n </tr>");
			out.write("<td align=\"left\" valign=\"middle\" style=\"background-color:#" + color + ";border-top:5px solid #" + color + ";border-bottom:0px dashed #000000;\"><span style=\"font-size:12px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;text-decoration:none;\">Test Cases Executed : " + tested + "</span></td>");
			out.write("<td align=\"left\" valign=\"middle\" style=\"background-color:#" + color + ";border-top:5px solid #" + color + ";border-bottom:0px dashed #000000;\"><span style=\"font-size:12px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;text-decoration:none;\">Passed : " + passed + "</span></td>");
			out.write("<td align=\"left\" valign=\"middle\" style=\"background-color:#" + color + ";border-top:5px solid #" + color + ";border-bottom:0px dashed #000000;\"><span style=\"font-size:12px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;text-decoration:none;\">Failed : " + failed + "</span></td>");
			out.write("<td align=\"left\" valign=\"middle\" style=\"background-color:#" + color + ";border-top:5px solid #" + color + ";border-bottom:0px dashed #000000;\"><span style=\"font-size:12px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;text-decoration:none;\">Browser: " + strBrowser + "</span></td></tr>");
			//out.write("<td align=\"left\" valign=\"middle\" style=\"background-color:#" + color + ";border-top:5px solid #" + color + ";border-bottom:1px dashed #000000;\"><span style=\"font-size:12px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;text-decoration:none;\"></span></td>");
			//out.write("<td align=\"left\" valign=\"middle\" style=\"background-color:#" + color + ";border-top:5px solid #" + color + ";border-bottom:1px dashed #000000;\"colspan=4><span style=\"font-size:12px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;text-decoration:none;\">URL:" + strURL + "</span></td>");
			out.write("</tr> \n </table> \n <table width=\"100%\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
			out.write("<td bgcolor=\"#FFFFFF\" valign=\"top\" width=\"99%\"><IMG id=editableImg1 SRC=\"http://chart.apis.google.com/chart?cht=bvg&chs=350x175&chd=t:" + strChart + "&chds=0," + chartMaxHeight + "&chxt=x,y&chxs=0,000000,12|1,000000,12&chco=00FF00|FF0000|0000FF|FFFF00&chbh=50,0,20&chxl=0:|Passed|Failed|Waiting|NotReady|1:|" + chartDimensions + "&chg=25,16.667,2,5&chtt=Total+Test+Cases+=+" + tested + "&chts=000000,15\" BORDER=\"0\" align=\"left\"></td>");
			out.write("<tr> \n <td align=\"left\" valign=\"left\" style=\"font-size:12px;color:#000000;line-height:150%;font-family:trebuchet ms;\">");
			out.write("<p> \n <span style=\"font-size:20px;font-weight:bold;color:#000000;font-family:arial;line-height:110%;\">" + testType + " Details </span><br>");
			out.write("\n <span style=\"font-size:12px;font-weight:bold;color:#000000;font-family:arial;line-height:110%;\">URL : </span><a href=\"" + strURL +"\">"+strURL+"</a><br>");
			out.write("<p><span style=\"font-size: 15px;font-weight:bold;color:#000000;font-family:arial;\">Items Tested:</span><br>");
			
			Set TCset= SeleniumDriverTest.TestCaseDetails.keySet(); 
			Iterator TCiter = TCset.iterator();
						
			while(TCiter.hasNext()) {
				int Key = Integer.parseInt(TCiter.next().toString());
				String Value = SeleniumDriverTest.TestCaseDetails.get(Key);
				String OutputValue="";
				System.out.println("ID " + Key + " : " + SeleniumDriverTest.TestCaseDetails.get(Key));
				
				if (SeleniumDriverTest.TestCaseExecutionDetails.containsKey(Key)) {
					
					if (SeleniumDriverTest.TestCaseExecutionDetails.get(Key).startsWith("PASS")) {
						fontColor = "00FF00";
						System.out.println("Test Execution details "+SeleniumDriverTest.TestCaseExecutionDetails.get(Key).substring(4));
						Value = Value+" - Passed" ;
						OutputValue=SeleniumDriverTest.TestCaseExecutionDetails.get(Key).substring(4);
						System.out.println("OutputValue"+OutputValue);
						//OutputValue=ExportTestResultsExcel.Comments;
						//System.out.println("ExportTestResultsExcel.Comments"+ExportTestResultsExcel.Comments);
						//OutputValue=SeleniumDriver.TestCaseExecutionDetails.get(Key).substring(4);
					} else if (SeleniumDriverTest.TestCaseExecutionDetails.get(Key).startsWith("FAIL")) {
						fontColor = "FF0000";
						Value = Value+" - Failed : "+SeleniumDriverTest.TestCaseExecutionDetails.get(Key).substring(4);
						//OutputValue="  ";
					}
					out.write("<span style=\"font-size: 14px;font-weight:bold;color:#" + fontColor + ";font-family:arial;\">" + Key + " : </span>");
					System.out.println("Key is"+Key);
					out.write("<span style=\"font-size: 14px;font-weight:normal;color:#000000;font-family:arial;\">" + Value + "</span> <span style=\"font-size: 14px;font-weight:normal;color:#0000FF;font-family:arial;\">"+ OutputValue+ "</span><br>");
					//out.write("<span style=\"font-size: 14px;font-weight:normal;color:#0000FF;font-family:arial;\">" + OutputValue + "</span><br>");
					System.out.println("Value is "+Value);
				}
			}
			out.write("</p> \n </td>");
			out.write("</tr> \n </table> \n </td> \n </tr> \n </table> \n </body> \n </html>");
			out.close();
		} catch (IOException ioe) {
		    System.err.println(ioe);
			ioe.printStackTrace();
		}
		
	}
}

