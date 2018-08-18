package com.java.ImportnExport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import com.java.SeleniumDriverTest;
import com.java.Objects.ConfigDetails;

/**
 * This is used to write the data into an Excel sheet 
 */
public class ExportTestResultsExcel{
	private static Logger log = Logger.getLogger(ExportTestResultsExcel.class.getName());
	public static String Comments;
	public ExportTestResultsExcel (){}
	
	//creating a workbook;
	HSSFWorkbook wb = new HSSFWorkbook();
	
	String fileName = null;
	String htmlFileName = null;
	FileOutputStream fileOut = null;
	InputStream inputStream = null;
	
	Calendar cal = Calendar.getInstance();
	public static final String dateTime = "MMddyy_HHmmss";
	public static final String dateTime1 = "EEE MMM dd hh:mm:ss z yyyy";
	SimpleDateFormat dateFormat = new SimpleDateFormat(dateTime);	
	Properties props = new Properties();
	public static String testResultPath ;
	public static String testHTMLResultPath ;
	int headings = 0;
	
	/**
	 * This method is to write the data into Excel Sheet(Test Results Header)
	 * @param result
	 */
	@SuppressWarnings("deprecation")
		public void exportExcelHeader() throws IOException 
		{
			try
			{		
				
				HashMap<Integer,ConfigDetails> ConfigDtls = new HashMap<Integer,ConfigDetails>();
			    //Beans to store objects which we get from HashMaps
				ConfigDetails confDtls=new ConfigDetails();
				//Object To Read Configuration Excel Sheet
				ImportConfigDetailsExcel impCnfxl=new ImportConfigDetailsExcel();
				//Reading Data from Config and Test Data details Excel Sheet.
				//ConfigDtls=impCnfxl.displayFromExcel();
				//Reads path from properties file and opens the file from that location
				//Retrieving first row from Configuration Details Excel Sheet
		    	//confDtls=(ConfigDetails)ConfigDtls.get(1);
				
		    	if (headings == 0)
					{			
				    	fileName = "Test Results_" + dateFormat.format(cal.getTime())+ ".xls";
				    	htmlFileName = "Test Results_" + dateFormat.format(cal.getTime())+ ".html";
				    	SeleniumDriverTest.hMap.put("htmlFile", htmlFileName);
//						SimpleDateFormat dateFormat1 = new SimpleDateFormat(dateTime1);
//						String strTimeStamp = dateFormat1.format(cal.getTime());
//						SeleniumDriver.hMap.put("TimeStamp", strTimeStamp);
//						System.out.println("Time Stamp "+strTimeStamp);			
				    	testResultPath = "./TestOutputs/TestReports/" + fileName;
				    	testHTMLResultPath = "./TestOutputs/TestReports/" + htmlFileName;
				    	props.put("TestResultsPath", testResultPath);
				    	props.put("testHTMLResultPath", testHTMLResultPath);
				    	
				    	System.out.println("OUT FILE : "+testResultPath);
				    	
						wb = new HSSFWorkbook();
					    wb.createSheet("Test Result");
					    fileOut = new FileOutputStream(testResultPath);
						System.out.println("Test Result file is created");
						HSSFSheet sheet = wb.getSheetAt(0);
						sheet.setAutobreaks(false);
					
						HSSFRow row = sheet.createRow((short)0);            
			            HSSFFont font = wb.createFont();			
			            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);		
						HSSFCellStyle cellStyle = wb.createCellStyle();
						cellStyle.setFont(font);
						
			           // Setting Headings in Test Results file
						row.createCell((short)0).setCellStyle(cellStyle);
						row.createCell((short)1).setCellStyle(cellStyle);
						row.createCell((short)2).setCellStyle(cellStyle);
						row.createCell((short)3).setCellStyle(cellStyle);
						row.createCell((short)4).setCellStyle(cellStyle);
						row.createCell((short)5).setCellStyle(cellStyle);
					
			            row.createCell((short)0).setCellValue("Test Case ID");
			            row.createCell((short)1).setCellValue("Test Case Title");
			            row.createCell((short)2).setCellValue("Result(P/F)"); 
			            row.createCell((short)3).setCellValue("Error Message"); 
			            row.createCell((short)4).setCellValue("Time Stamp");
			            row.createCell((short)5).setCellValue("Output Value");
						headings = 1;
					}
				wb.write(fileOut);
				fileOut.close();		
			}
			catch (Exception e)
			{
				log.error("Error while creating Test Report Excel file..");
				e.printStackTrace ();
			}
	}
	
	@SuppressWarnings("deprecation")
	/**
 * This is used to write the test case results into an Excel sheet after completing the each test case execution
 */
	public void exportExcelRows(List<String> result) throws IOException 
	{		
		try{
			System.out.println("testResultPath = " +testResultPath);
//			System.out.println("inputStream" + inputStream);
			inputStream = new FileInputStream (testResultPath);
			POIFSFileSystem fs = new POIFSFileSystem(inputStream);
			fileOut = new FileOutputStream(testResultPath);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
//			System.out.println(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			
			sheet.setAutobreaks(false);
//			System.out.println(sheet);
			System.out.println("result.size() = "+result.size());
			int rows; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();
		    System.out.println("No of rows in sheet="+rows);
		    for(int ii=0;ii<result.size(); ii++)
    		System.out.println("Result = "+result.get(ii));
		    
		   
				HSSFRow row=sheet.createRow(rows);
				//Adding the cell values in each row from bean
				result.remove(1); // to skip test data value in the report sheet
				for(int i=0; i<5; i++){ 
					sheet.setColumnWidth((short)2,  (short)(256*13));
					sheet.setColumnWidth((short)1,  (short)(256*25));
					sheet.setColumnWidth((short)2,  (short)(256*14));
					sheet.setColumnWidth((short)3,  (short)(256*30));
					sheet.setColumnWidth((short)4,  (short)(256*28));
					HSSFCell cell=row.createCell((short)i);
					//set cell style
	        		//cs.setFont(f);
	        		System.out.println("..in export.."+result.get(i).toString());
	        		HSSFRichTextString str=new HSSFRichTextString(result.get(i).toString());
				 
	        		if(i == 3){
					 if(result.get(2).equalsIgnoreCase("Pass"))
						 str=new HSSFRichTextString(SeleniumDriverTest.hMap.get("strWarningMessage"));
	        		}
	       		log.debug("str---"+str);
				cell.setCellValue(str);
				}
				SeleniumDriverTest.hMap.put("strWarningMessage","");
				sheet.setColumnWidth((short)5,  (short)(256*28));
				HSSFCell cell=row.createCell((short)5);
				HSSFRichTextString str=new HSSFRichTextString(this.Comments);
				log.debug("str---"+str);
				cell.setCellValue(str);
			wb.write(fileOut);
			fileOut.close();			
		}
		catch(Exception e){
			log.error("Error writing the Excel file");
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			fileOut.close();
		}
	}
	/**
	 * This is used to write the Test Summary into an Test Results Excel Sheet after completion of total test cases execution.
	 */
	public void exportTestSummary(List<String> result) throws IOException 
	{		
		try{
			inputStream = new FileInputStream (testResultPath);
			POIFSFileSystem fs = new POIFSFileSystem(inputStream);
			fileOut = new FileOutputStream(testResultPath);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			sheet.setAutobreaks(false);
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
		    for(int ii=0;ii<result.size(); ii++)
			{

				HSSFRow row=sheet.createRow(rows+ii+1);
				HSSFCell cell;
				HSSFRichTextString str;
				//Adding the cell values in each row from bean
				switch (ii) {
					case 0:
						cell=row.createCell((short)1);
						cell.setCellValue("Browser Tested");
						cell=row.createCell((short)2);						
						str=new HSSFRichTextString(result.get(ii).toString());
						cell.setCellValue(str);
						break;
					case 1:
						cell=row.createCell((short)1);
						cell.setCellValue("Total Cases Executed");
						cell=row.createCell((short)2);						
						str=new HSSFRichTextString(result.get(ii).toString());
						cell.setCellValue(str);
						break;					
					case 2:
						cell=row.createCell((short)1);
						cell.setCellValue("Total Cases Passed");
						cell=row.createCell((short)2);						
						str=new HSSFRichTextString(result.get(ii).toString());
						cell.setCellValue(str);
						break;		
					case 3:
						cell=row.createCell((short)1);
						cell.setCellValue("Total Cases Failed");
						cell=row.createCell((short)2);						
						str=new HSSFRichTextString(result.get(ii).toString());
						cell.setCellValue(str);
						break;							
				}
			}
		
			wb.write(fileOut);
			fileOut.close();			
		}
		catch(Exception e){
			log.error("Error writing the Excel file");
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			fileOut.close();
		}
	}	
}


