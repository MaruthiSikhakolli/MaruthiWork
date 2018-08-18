package com.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.java.Objects.ResultDetails;
import com.java.TestType.DataFileds;

public class Reference_Keywords extends TestType {

	public WebDriver webdriver = null;
	public Actions builder = null;
	public static HashMap<String, String> hMap1 = new HashMap<String, String>();
	public static HashMap<Integer, String> hMap = new HashMap<Integer, String>();
	 static String title="";
	 static String selectstate="";

	static ResultDetails resultDetails = new ResultDetails();
	
	public Reference_Keywords(WebDriver webdriver) {
		this.webdriver = webdriver;
	}
	public enum DataFileds {
		ADD,RMV,RNM,SRS,FLV,RRC,RLC,RCC,PIB,PIF,PIU,drglimit,adddrug,SSV, SAV, dropdown
	}; // EDT is Text Editor

	// Keywords
	public enum ActionTypes
	{
		IMAGEHOVER,REFERENCE,MDIC,READDATA,VERIFYEXCELDATA,WRITEDATA,CREATEEXCEL,COMPARECELLTOCELLDATA
	};
	
	public ResultDetails performAction(String fieldText, String value,
			String actionType, String fieldName) {
		try {
			ActionTypes actTypes = ActionTypes
					.valueOf(actionType.toUpperCase());
			switch (actTypes) {
				case IMAGEHOVER:
				resultDetails=imageHover(webdriver, fieldText, value,
						fieldName);
				break;
				case REFERENCE:
					resultDetails=reference(webdriver, fieldText, value,
							fieldName);
					break;
				case VERIFYEXCELDATA:
					resultDetails=verifyexceldata(webdriver, fieldText, value,
							fieldName);
					break;
				case READDATA:
					resultDetails=readdata(webdriver, fieldText, value,
							fieldName);
					break;
				case CREATEEXCEL:
					resultDetails=createexcel(webdriver, fieldText, value,
							fieldName);
					break;
				case WRITEDATA:
					resultDetails=writedata(webdriver, fieldText, value,
							fieldName);
					break;
				case COMPARECELLTOCELLDATA:
					resultDetails=comparecelltocelldata(webdriver, fieldText, value,
							fieldName);
					break;
				
				case MDIC:
					resultDetails=mdic(webdriver, fieldText, value
							);
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
	
	 public ResultDetails createexcel(WebDriver webdriver, String fieldText,
			String value, String fieldName) {
		 ResultDetails resultDetails = new ResultDetails();
			String fieldType = fieldText.substring(0, 3);
			String field = fieldText.substring(3, fieldText.length());
			System.out.println("field:"+field);
			resultDetails.setFlag(false);
		//creating a workbook;
			HSSFWorkbook wb = new HSSFWorkbook();
			
			String fileName = null;
			FileOutputStream fileOut = null;
			InputStream inputStream = null;
			
			Calendar cal = Calendar.getInstance();
			final String dateTime = "MMddyy_HHmmss";
			final String dateTime1 = "EEE MMM dd hh:mm:ss z yyyy";
			SimpleDateFormat dateFormat = new SimpleDateFormat(dateTime);	
			String testResultPath = null ;
			
			int headings = 0;
			try
			{	
			String[] field1=field.split("::");
				
				    	if (headings == 0)
					{			
				    	fileName = "Results_" + dateFormat.format(cal.getTime())+ ".xls";
				    	testResultPath = ".//TestOutputs//TestReports//" + fileName;
				    	hMap1.put("TestResultPath", testResultPath);
				    	System.out.println("OUT FILE : "+testResultPath);					
				    	wb = new HSSFWorkbook();
					    wb.createSheet("Results");
					    fileOut = new FileOutputStream(testResultPath);
						System.out.println("Test Result file is created");
						HSSFSheet sheet = wb.getSheetAt(0);
						sheet.setAutobreaks(false);
					
						HSSFRow row = sheet.createRow((short)0);            
			            HSSFFont font = wb.createFont();			
			            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);		
						HSSFCellStyle cellStyle = wb.createCellStyle();
						cellStyle.setFont(font);
						int x=Integer.parseInt(value);
			           // Setting Headings in Test Results file
					for(int c=0;c<x;c++){
						row.createCell((short)c).setCellStyle(cellStyle);
					}
					for(int l=0;l<x;l++){
			            row.createCell((short)l).setCellValue(field1[l]);
			          
						}

					headings = 1;
					}
				wb.write(fileOut);
				fileOut.close();
				System.out.println("Test Result file is created with columns");
				resultDetails.setFlag(true);
			}
			catch (Exception e)
			{
				resultDetails.setFlag(false);
			System.out.println("Error while creating Test Report Excel file..");
				e.printStackTrace ();
			} 
		
		return resultDetails;
	}

	
	public ResultDetails writedata(WebDriver webdriver, String fieldText,
			String value, String fieldName) throws IOException {
		
			 	ResultDetails resultDetails = new ResultDetails();
				String fieldType = fieldText.substring(0, 3);
				String field = fieldText.substring(3, fieldText.length());
				System.out.println("field:"+field);
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("");
				try
				{   
					System.out.println("field "+field);
					String newdata="";
                         if(value.contains("HMVINT")){
                                          String[] hmv=value.split("::");
                                          System.out.println("no of hashmap values : "+hmv[1]);
                                          int x=Integer.parseInt(hmv[1]);
                                          System.out.println("no of hashmap values in integer : "+x);
                                         String[] hash=new String[100];
                                          for(int h=0;h<x;h++){
                                         hash[h]=hMap.get(h);
                                        newdata=newdata + hash[h] +"\n ";
                                          }
                                  }
                         else if(value.startsWith("//")){
                             try{    
                        	 String[] val=value.split("::");                                	  
                                  List<WebElement>
                                  list=(List<WebElement>)webdriver.findElements(By.xpath(val[0]));
                                    System.out.println("size: "+list.size());
                                  for(int i=0;i<list.size();i++)
                                  {   System.out.println("on split:"+val[0]);
                                          System.out.println("Condition["+i+"] : "+webdriver.findElement(By.xpath(val[0]+"["+(i+1)+"]"+val[1])).getText());
                                          newdata=newdata + webdriver.findElement(By.xpath(val[0]+"["+(i+1)+"]"+val[1])).getText()+ "\n ";
                                      }}
                                  catch(Exception e){
                                	  newdata="element not found for : "+value;
                                	  resultDetails.setFlag(false);
                                	  resultDetails.setErrorMessage("element not found ");
                                  }
                                  }else{
                                	  newdata=value;
                                  }
                          // Read from original Excel file.
                         System.out.println("results path : "+hMap1.get("TestResultPath"));
                          HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(hMap1.get("TestResultPath")));
                     
                          // Get the first sheet.
                          HSSFSheet sheet = workbook.getSheetAt(0);
                          String pos=field;
                          int cellpos=0;
                         
                          System.out.println("Name  of the column:"+field);
                                 
                          // Set value of the first cell.
                          HSSFRow row = sheet.getRow(0);
                          HSSFCell cell = row.getCell((short)0);
                         
                          for(int i=0;i<row.getLastCellNum();i++)
                          {   System.out.println("pos:"+pos+" exelcel:"+row.getCell((short)i).getStringCellValue());
                                  if(pos.contentEquals(row.getCell((short)i).getStringCellValue()))
                                  {
                                          cellpos=i;
                                          break;
                                  }
                                  if(i==(row.getLastCellNum()-1))
                                                  {
                                                System.out.println(pos+" column not found at the first row ");
                                                resultDetails.setFlag(false);
                                                return resultDetails;
                                                  }
                          }
                          System.out.println("cell pos matched at:"+cellpos);
                     int maxrow = sheet.getLastRowNum();//for finding the maximum cell number
                          System.out.println("maxrow  :  "+maxrow+"sheet.getLastRowNum():"+sheet.getLastRowNum());
                          int i; 
                          if(maxrow==0)
                          {
                              row=sheet.createRow(maxrow+1);
                        	  row = sheet.getRow(maxrow+1);
                          
                          }
                          else
                          row = sheet.getRow(maxrow);
                          
                          System.out.println("row:"+row.getRowNum());
                          for(i=maxrow;row.getCell((short)cellpos)== null&&i>0;i--){
                              row = sheet.getRow(i-1); 
                        	  System.out.println("In loop row no:"+i);
                          } 
                          if(i==maxrow)
                          {
                        	  row=sheet.createRow(maxrow+1);
                        	  row = sheet.getRow(maxrow+1);
                          }
                          System.out.println("------------------");
                          row = sheet.getRow(i+1); 
                                cell=row.getCell((short)cellpos);
                                cell=row.createCell(cellpos);
                          cell.setCellValue(newdata);
                                                
                          // Write newly modified workbook to a file.
                          FileOutputStream fileOut = new FileOutputStream(hMap1.get("TestResultPath"));
                          workbook.write(fileOut);
                          System.out.println("write");
                          fileOut.close();
                          resultDetails.setFlag(true);
                          try{
                       if(resultDetails.getErrorMessage().equals("element not found ")){
                    	   resultDetails.setFlag(false);
                       }
                          }catch(Exception e){}
                        }
                        catch(FileNotFoundException e)
                        {
                                System.out.println("exception value : " + e.getMessage());
                                    resultDetails.setFlag(false);
                                    resultDetails.setErrorMessage(e.getLocalizedMessage());
                        }
                       
		return resultDetails;
	}

	public ResultDetails verifyexceldata(WebDriver webdriver,
			String fieldText, String value, String fieldName) throws ClassNotFoundException, SQLException {
		 	ResultDetails resultDetails = new ResultDetails();
			String fieldType = fieldText.substring(0, 3);
			String field = fieldText.substring(3, fieldText.length());
			resultDetails.setFlag(false);
			ResultSet rs;
			String field1[]=field.split("::");
			 List<WebElement> ls=(List<WebElement>)webdriver.findElements(By.xpath(field));
			  System.out.println(ls.size());
			  String[] href=new String[ls.size()];
			  String[] href1=new String[ls.size()];
			  for(int m=1;m<=ls.size();m++){
				  href[m-1]=webdriver.findElement(By.xpath(field)).getAttribute("href");
				  href1[m-1]=webdriver.findElement(By.xpath(field)).getText();
			  }
			  	Class.forName("org.relique.jdbc.csv.CsvDriver");
				String url = "jdbc:relique:csv:"+field1[0];
				System.out.println(url);
				Connection conn = DriverManager.getConnection(url,"","");
				Statement st = conn.createStatement();
			 for(int a=1;a<ls.size();a++){
			 String sql="Select "+field1[2]+" from  "+field1[1]+" where "+field1[3]+"='"+href[a-1].toLowerCase()+"'";
			  rs = st.executeQuery(sql);
			  while(rs.next())
			  {
			     System.out.println(rs.getString(field1[2]));
			  }
			  webdriver.get("href[a-1");
			  String title=webdriver.findElement(By.xpath(value)).getText();
			  if(title.equals(rs.getString(field1[2]))){
			resultDetails.setFlag(true);
			  }else{
				  resultDetails.setFlag(false);
				  System.out.println("Title is not verified with excel data for : "+title);
			  }
			 }
	  			
		return resultDetails;
	}
	public ResultDetails comparecelltocelldata(WebDriver webdriver,
            String fieldText, String value, String fieldName) {
            ResultDetails resultDetails = new ResultDetails();
            String fieldType = fieldText.substring(0, 3);
            String field = fieldText.substring(3, fieldText.length());
            System.out.println("field:"+field);
            resultDetails.setFlag(false);
            try {
                  String pos=field;//String pos=field;
                int cellpos=0;
                  FileInputStream fileInputStream = new FileInputStream(hMap1.get("TestResultPath"));
                  HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
                  HSSFSheet worksheet = workbook.getSheetAt(0);
      
                   HSSFRow row = worksheet.getRow(0);
                  HSSFCell cell = row.getCell((short)0);
                  System.out.println("column: "+row.getLastCellNum());
                  for(int i=0;i<row.getLastCellNum();i++)
                  {   System.out.println("pos:"+pos+" exel cel:"+row.getCell((short)i).getStringCellValue());
                    if(pos.contentEquals(row.getCell((short)i).getStringCellValue()))
                    {
                          cellpos=i;
                          break;
                    }
                    if(i==(row.getLastCellNum()-1))
                                {
                                System.out.println(pos+" column not found at the first row ");
 		resultDetails.setFlag(false);
                                }
                  }
                     
                  System.out.println("cell pos matched at:"+cellpos);
                  int x=Integer.parseInt(value);
                        for(int i=1;i<=x;i++){
                  HSSFRow row1 = worksheet.getRow(i);
                  HSSFCell cellA1 = row1.getCell((short) cellpos);
                  String a1Val = cellA1.getStringCellValue();
                  HSSFCell cellB1 = row1.getCell((short) cellpos+1);
                  String b1Val = cellB1.getStringCellValue();
                  System.out.println("A1: " + a1Val);
                  System.out.println("B1: " + b1Val);
                  row1 = worksheet.getRow(i);
                  if(a1Val.equals(b1Val)){
                        cellA1 = row1.createCell((short) cellpos+2);
                         cellA1.setCellValue("Pass");
                         resultDetails.setFlag(true);

                  }else{
                       String[] a1val=a1Val.split("\n");
	String[] b1val=b1Val.split("\n");
	String fail="";
	for(int j=0;j<a1val.length;j++){
	if(a1val[j].equals(b1val[j])){
	//System.out.println("true");
	}else{
	fail=fail+a1val[j]+",";
	}
}
System.out.println("Fail At : "+fail);
cellA1 = row1.createCell((short) cellpos+2);
cellA1.setCellValue("Fail At : "+fail);
                  }
                  FileOutputStream fileOut = new FileOutputStream(hMap1.get("TestResultPath"));
                  workbook.write(fileOut);
                  System.out.println("write");
                  fileOut.close();
                        }
            //    System.out.println("C1: " + c1Val);
            //    System.out.println("D1: " + d1Val);
            } catch (FileNotFoundException e) {
                  e.printStackTrace();
            } catch (IOException e) {
                  e.printStackTrace();
            }
      return resultDetails;
  }



	public ResultDetails readdata(WebDriver webdriver, String fieldText,
			String value, String fieldName) throws SQLException {
		 	
		 	ResultDetails resultDetails = new ResultDetails();
			String fieldType = fieldText.substring(0, 3);
			String field = fieldText.substring(3, fieldText.length());
			resultDetails.setFlag(false);
			ResultSet rs;
			String[] arr=new String[100];
			int o=0;
			String field1[]=field.split("::");
				int i=0;
			try {
				Class.forName("org.relique.jdbc.csv.CsvDriver");
				String url = "jdbc:relique:csv:"+field1[0];
				System.out.println(url);
				Connection conn = DriverManager.getConnection(url,"","");
				Statement st = conn.createStatement();
				
				if(value.contains("range")){
					String[] val1=value.split("::");
				String[] val=val1[1].split("-");
				int x=Integer.parseInt(val[0]);			
				int y=Integer.parseInt(val[1]);
				
				for(int k=x;k<y;k++){
				String sql="Select "+field1[3]+" from  "+field1[1]+" where "+field1[2]+"='"+k+"'";
					 rs = st.executeQuery(sql);
					 while(rs.next()){
					 System.out.println(rs.getString(field1[3]));
					 arr[i]=rs.getString(field1[3]);
					 }
					 hMap.put(i,arr[i]); 
					System.out.println("hMap1.get["+i+"] : "+hMap.get(i));
					i++;
					 rs.close();
				}
			}else if(value.contains("random")){
				String[] val=value.split("::");
				String sql="Select "+field1[3]+" from  "+field1[1]+" ";
				rs = st.executeQuery(sql);
								while(rs.next()){
										o++;
					 				 }
					int x=Integer.parseInt(val[1]);		
								int y=o/x;
								System.out.println(y);
					for(int j=0;j<y;j++){
					Random randomNumbers = new Random();  
					int start = x*j;     // beginning of range  
					int end = x*(j+1);      // end of range  
					int p = start + randomNumbers.nextInt( end - start + 1 ); 
					System.out.println(p);
				sql="Select "+field1[3]+" from  "+field1[1]+" where "+field1[2]+"='"+p+"'";
				rs = st.executeQuery(sql);
					while(rs.next()){
						arr[i]=rs.getString(field1[3]);
						 }
					 System.out.println("arr["+i+"] : "+arr[i]);
					 hMap.put(i,arr[i]); 
					 System.out.println("hMap1.get["+i+"] : "+hMap.get(i));
					 i++;
					 rs.close();
				}
			
					
			}else if(value.contains("specific")){
				String sql="Select "+field1[3]+" from  "+field1[1]+" ";
				rs = st.executeQuery(sql);
				 o=0;
					while(rs.next()){
						o++;
					 				 }
				String[] val=value.split("::");
				if(val[1].startsWith("[")){
					int q=0;
					int start=0;
					int end=0;
					val[1]=val[1].substring(1, val[1].length()-1);
					String[] val1=val[1].split("##");
					if(val1[0].contains("-")){
						String[] val2=val1[0].split("-");
						start =Integer.parseInt(val2[0]);     // beginning of range  
						end = Integer.parseInt(val2[1]);      // end of range  
											}
					int z=Integer.parseInt(val1[1]);
					for(int k=0;k<z;k++){
						Random randomNumbers = new Random();
						q = start + getRandom( end - start + 1 );
						 sql="Select "+field1[3]+" from  "+field1[1]+" where "+field1[2]+"='"+q+"'";
						 rs = st.executeQuery(sql);
						 while(rs.next()){
						 arr[i]=rs.getString(field1[3]);
						 }
						 hMap.put(i,arr[i]);
						 System.out.println("hMap1.get["+i+"] : "+hMap.get(i));
						 i++;
						 rs.close();
					}	
				}else{
				int y=Integer.parseInt(val[1]);
				for(int k=0;k<y;k++){
					int p=getRandom(o);
					 sql="Select "+field1[3]+" from  "+field1[1]+" where "+field1[2]+"='"+p+"'";
					 rs = st.executeQuery(sql);
					 while(rs.next()){
					 arr[i]=rs.getString(field1[3]);
					 }
					 hMap.put(i,arr[i]);
					 System.out.println("hMap1.get["+i+"] : "+hMap.get(i));
					 i++;
					 rs.close();
				}	
			}
			}
			resultDetails.setFlag(true);
			} catch (ClassNotFoundException e) {
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage(e.getMessage());
				e.printStackTrace();
			}	
		return resultDetails;
	}

	public static ResultDetails reference(WebDriver webdriver, String fieldText, String value ,String fieldName) throws InterruptedException, SQLException {
		DataFileds dfs = DataFileds.valueOf(fieldText.substring(0, 3));
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		resultDetails.setFlag(false);
		String[] stt=new String[10];
		
		int l = 0;
       int r=0;
       String st1 ="";
       String st2 ="";
       String st ="";
       
  		switch(dfs){
		 
		case ADD:
			try{
           	 if(webdriver.findElement(By.linkText("Create Your List of Plans")).isDisplayed()){
           		 webdriver.findElement(By.linkText("Create Your List of Plans")).click();
           	 }
           	 }catch(Exception e){
            webdriver.findElement(By.linkText("Add or Remove Plans")).click();
           	 }
            Thread.sleep(5000);
            Select selectBox = new Select(webdriver.findElement(By.id("plan_state")));
             selectBox.getFirstSelectedOption();
           st1=selectBox.getFirstSelectedOption().getAttribute("value");
           st=st1;
           Thread.sleep(2000);
           resultDetails=randomcheck(webdriver, stt, st,r, l);
           if(!resultDetails.getFlag()){
           	return resultDetails;
           }
           l=2;
            webdriver.findElement(By.linkText("Add or Remove Plans")).click();
           Select selectBox4 = new Select(webdriver.findElement(By.id("plan_state")));
           List<WebElement> la1=(List<WebElement>)webdriver.findElements(By.xpath("//select[@id='plan_state']/option"));
           r=getRandom(la1.size());
           selectBox4.selectByIndex(r);
           selectBox.getFirstSelectedOption();
           st2=selectBox.getFirstSelectedOption().getAttribute("value");
           hMap1.put("selectstate", selectBox.getFirstSelectedOption().getAttribute("value"));
           st=st2;
            Thread.sleep(2000);
          resultDetails=randomcheck(webdriver, stt, st,r, l); 
          if(!resultDetails.getFlag()){
          	return resultDetails;
          }
          break;
	
		case RMV:
			 System.out.println(st2);
			WebElement myElement5 = webdriver.findElement(By.linkText("Add or Remove Plans"));
				Actions builder5 = new Actions(webdriver);
				builder5.moveToElement(myElement5).build().perform();
				Thread.sleep(5000);
          webdriver.findElement(By.linkText("Add or Remove Plans")).click();
         Select selectBox1 = new Select(webdriver.findElement(By.id("plan_state")));
        // selectBox1.selectByValue(st1);
         resultDetails=removecheck(webdriver);
         if(!resultDetails.getFlag()){
            	return resultDetails;
            }
         Thread.sleep(5000);
         WebElement myElement6 = webdriver.findElement(By.linkText("Add or Remove Plans"));
			Actions builder6 = new Actions(webdriver);
			builder6.moveToElement(myElement6).build().perform();
         webdriver.findElement(By.linkText("Add or Remove Plans")).click();
         Select selectBox5 = new Select(webdriver.findElement(By.id("plan_state")));
         st2=hMap1.get("selectstate");
          System.out.println(st2);
         selectBox5.selectByValue(st2);
         resultDetails=removecheck(webdriver);
         if(!resultDetails.getFlag()){
            	return resultDetails;
            }
         if(webdriver.findElement(By.xpath("//div[@id='createList']/div/a")).getText().equalsIgnoreCase("Create Your List of Plans")){
        	resultDetails.setFlag(true);
         }else{
        	resultDetails.setFlag(false);
         resultDetails.setErrorMessage("planned states are not removed ");
         return resultDetails;
         }
			break;
		
		case SRS:
			resultDetails=selectstate(webdriver,field);
			break;
		
		case RNM:
						resultDetails=randomclick(webdriver, field, value, fieldName, title);
			break;
		
		case RRC:
			for(int k=2;k<=3;k++){
  			  List<WebElement> li2=(List<WebElement>)webdriver.findElements(By.xpath("//div[@id='fs_planList']/div["+k+"]/ul/li"));
        			  for(int i1=1;i1<=li2.size();i1++){
        		try{		 
  				if(webdriver.findElement(By.xpath("//div[@id='fs_planList']/div["+k+"]/ul/li["+i1+"]/input")).isSelected()){
  					webdriver.findElement(By.xpath("//div[@id='fs_planList']/div["+k+"]/ul/li["+i1+"]/input")).click(); 
  					resultDetails.setFlag(true);
  				 }
        		}catch(Exception e){} 
  			  }
              }
			break;
		
		case FLV:
			try{
			if(webdriver.findElement(By.id("moredrugbrand")).isDisplayed()){
			webdriver.findElement(By.id("moredrugbrand")).click();
			}}catch(Exception e){}
			Thread.sleep(3000);
			String ss1=webdriver.findElement(By.xpath("//div[@id='maincolboxdrugdbheader']/h1")).getText();
     	  System.out.println(webdriver.findElement(By.xpath("//div[@id='maincolboxdrugdbheader']/h1")).getText());
     	  ss1=ss1.replace(") -", ",");
     	  String[] links=ss1.split(",");
     	  int n=links.length;
           	  for(int i1=0;i1<n;i1++){
     		  if(i1==0){
     			  String[] link1=links[i1].split("-");
     			  links[i1]=link1[i1].substring(0, links[i1].indexOf(" "));
     			  links[i1]=" ".concat(links[i1]);
          		  }
     	  System.out.println(links[i1]);
     	  }
     	  webdriver.findElement(By.xpath("//div[@id='fs_table']/table/tbody/tr[2]/td/div/a")).click();
     	List<WebElement> li1=(List<WebElement>)webdriver.findElements(By.xpath("//div[@id='compareList']/table/tbody/tr"));
		  String[] str1 = new String[li1.size()];
		  int k=0;
       for (int i1 = 0; i1 < li1.size()-1; i1++) {
     	  try{
     		  if(webdriver.findElement(By.xpath("//div[@id='compareList']/table/tbody/tr["+(i1+2)+"]/td/a")).isDisplayed()){
                		  }
     	  }catch(Exception e){
     		  str1[k] = webdriver.findElement(By.xpath("//div[@id='compareList']/table/tbody/tr["+(i1+2)+"]/td[1]")).getText();
     		  str1[k] =" ".concat(str1[k]);
               System.out.println(str1[k]);
               k++;
     	  }}
     	 for(int i1=0;i1<2;i1++){
     		  int count=0;
     	  for(int j=0;j<2;j++){
     		  System.out.println(links[i1]);
     	 if(links[i1].toLowerCase().equals(str1[j].toLowerCase())){
     		resultDetails.setFlag(true);
     		count++;
     	 }else
     		resultDetails.setFlag(false);
       }
     	  if(count==1){
     		resultDetails.setFlag(true);
     	  }else{
     		resultDetails.setFlag(false);
     		System.out.println("link is highlighted for "+links[i1]);
     		resultDetails.setErrorMessage("link is highlighted for "+links[i1]);
     		return resultDetails;
     	  }
       }
			break;
		
		case RLC:
				String s2="";
				try{
			if(webdriver.findElement(By.xpath("//div[@class='farleftcolbox']/ul/li/a")).isDisplayed()){
			WebElement myElement2 = webdriver.findElement(By.xpath("//div[@class='farleftcolbox']/ul/li/a"));
			Actions builder2 = new Actions(webdriver);
			builder2.moveToElement(myElement2).build().perform();
			List<WebElement> li3=(List<WebElement>)webdriver.findElements(By.xpath("//div[@class='farleftcolbox']/ul/li"));
		System.out.println(li3.size());
		int h=li3.size();
 		 List<WebElement> li5=(List<WebElement>)webdriver.findElements(By.xpath("//h4"));
     	   if(h!=li5.size()){
     		   h=li5.size();
         	   }
     	   int a=getRandom(h);
		String s1=webdriver.findElement(By.xpath("//div[@class='farleftcolbox']/ul/li["+a+"]/a")).getText();
		webdriver.findElement(By.xpath("//div[@class='farleftcolbox']/ul/li["+a+"]/a")).click();
		for(int j1=1;j1<=2;j1++){
				List<WebElement> li2=(List<WebElement>)webdriver.findElements(By.xpath("//div[@id='leftcol']/div/div[2]/div[2]/div/div["+((2*a)+1)+"]/ul/li"));
				int b=getRandom(li2.size());
				s2=webdriver.findElement(By.xpath("//div[@id='leftcol']/div/div[2]/div[2]/div/div["+((2*a)+1)+"]/ul/li["+b+"]/a")).getText();
			WebElement myElement1 = webdriver.findElement(By.xpath("//div[@id='leftcol']/div/div[2]/div[2]/div/div["+((2*a)+1)+"]/ul/li["+b+"]/a"));
				Actions builder1 = new Actions(webdriver);
				builder1.moveToElement(myElement1).build().perform();
				webdriver.findElement(By.linkText(s2)).click();
				Thread.sleep(5000);
				title=s2;
		resultDetails=showall(webdriver, field, fieldName, title);
		if(!resultDetails.getFlag()){
			return resultDetails;
		}
	
		}

			}}catch(Exception e){
			resultDetails=randomclick(webdriver, field, value, fieldName, s2);
			if(!resultDetails.getFlag()){
				return resultDetails;
			}
			
			
		}
	
			break;
		case RCC:
			try{
		for(int k1=2;k1<=3;k1++){
				  List<WebElement> li14=(List<WebElement>)webdriver.findElements(By.xpath("//div[@id='fs_planList']/div["+k1+"]/ul/li"));
	      			  for(int i1=1;i1<=11;i1++){
	      				  webdriver.findElement(By.xpath("//div[@id='fs_planList']/div["+k1+"]/ul/li["+i1+"]/input")).click();
							resultDetails.setFlag(true);		  
	      			  }
	            }
			}catch(Exception e){
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage(e.getMessage());
				return resultDetails;
				}
			break;
		case PIB:
				Thread.sleep(5000);
				List<WebElement> li12=(List<WebElement>)webdriver.findElements(By.xpath(field));
				String[] str12 = new String[li12.size()];
         		for (int i1 = 0; i1 < li12.size(); i1++) {
          str12[i1] =webdriver.findElement(By.xpath(field+"["+(i1+1)+"]/td[1]")).getText().toString();
          str12[i1]=str12[i1].substring(0, str12[i1].indexOf("-"));
         		}
         		for(int j=0;j<li12.size()-1;j++){
         			if(str12[j].contains("(")){
         	 String str2=str12[j].substring(str12[j].indexOf("("), str12[j].indexOf(")")); 
       	 str12[j].replace(str2, "");      
     	 }
           if(str12[j+1].contains("(")){
         	 String str2=str12[j+1].substring(str12[j+1].indexOf("("), (str12[j+1].indexOf(")")+1)); 
           	str12[j+1]= str12[j+1].replace(str2,"");
           }
           	 if(str12[j].toLowerCase().compareTo(str12[j+1].toLowerCase())<=0){
           	      resultDetails.setFlag(true);
           	      }else{
           	    	  resultDetails.setFlag(false);
           	  	 System.out.println("Not in sorted order for "+str12[j]);
           	  	 resultDetails.setErrorMessage("Not in sorted order for "+str12[j]);
           	  	 return resultDetails;
           	 }
           }
      	break;
	case PIF:
				int n1=0;
				int n2=0;
				List<WebElement> li11=(List<WebElement>)webdriver.findElements(By.xpath(field));
				String[] str11 = new String[li11.size()];
				for (int i1 = 0; i1 < li11.size(); i1++) {
					str11[i1] =webdriver.findElement(By.xpath(field+"["+(i1+1)+"]/td[2]")).getText();
										}
				for (int i1 = 0; i1 < li11.size()-1; i1++) {
					if(str11[i1].contains(value) && str11[i1+1].contains(value)){
						str11[i1]=str11[i1].substring(0, str11[i1].indexOf(value));
						if(str11[i1].contains("-")){
							str11[i1]=str11[i1].substring(0, str11[i1].indexOf("-")); 
        	             }
						n1=Integer.parseInt(str11[i1]);
						str11[i1+1]=str11[i1+1].substring(0, str11[i1+1].indexOf(value));
							if(str11[i1+1].contains("-")){
								str11[i1+1]=str11[i1+1].substring(0, str11[i1+1].indexOf("-")); 
								System.out.println(str11[i1+1]);
         }
         n2=Integer.parseInt(str11[i1+1]);
              		  if(n1>=n2){
              	 resultDetails.setFlag(true);
                         }else{
        	 resultDetails.setFlag(false);
        	 System.out.println("Not in sorted order for "+str11[i1]);
        	 resultDetails.setErrorMessage("Not in sorted order for "+str11[i1]);
        	 return resultDetails;
        	           }
    	  }
    	 }
        
	break;
case PIU:
		float n11;
		float n12;
		try{
			List<WebElement> li3=(List<WebElement>)webdriver.findElements(By.xpath(field));
			String[] str3 = new String[li3.size()];
			for (int i1 = 0; i1 < li3.size(); i1++) {
				str3[i1] =webdriver.findElement(By.xpath(field+"["+(i1+1)+"]/td[3]")).getText();
							}
			for (int i1 = 0; i1 < li3.size()-1; i1++) {
				if(str3[i1].contains(value)&& str3[i1+1].contains(value)){
      		 str3[i1]=str3[i1].substring(1);
   		 if(str3[i1].contains("-")){
       	 str3[i1]=str3[i1].substring(0, str3[i1].indexOf("-")); 
   		 }
        n11=Float.parseFloat(str3[i1]);
           str3[i1+1]=str3[i1+1].substring(1);
        if(str3[i1+1].contains("-")){
       	 str3[i1+1]=str3[i1+1].substring(0, str3[i1+1].indexOf("-")); 
        }
        n12=Float.parseFloat(str3[i1+1]);
            	  if(n11<=n12){
        	 resultDetails.setFlag(true);
          }else{
        	 resultDetails.setFlag(false);
        	 System.out.println("Not in sorted order for "+str3[i1]);
        	 resultDetails.setErrorMessage("Not in sorted order for "+str3[i1]);
        	 return resultDetails;
            }
       }
   	 
       }}catch(Exception e){
       	System.out.println(e.getMessage());
       }

	break;
	
case SSV:
	try{
		  Select selectBox12 = new Select(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)));
	  String s1=selectBox12.getFirstSelectedOption().getText();
	  System.out.println(s1);
	  String smap=hMap1.get("currentstate");
	  System.out.println(smap);
	  if(smap.equalsIgnoreCase(s1))
	  {
		  System.out.println("pass");
		  resultDetails.setFlag(true);
		  System.out.println("flag : "+resultDetails.getFlag());
				  }
	  else{
		  resultDetails.setFlag(false);
    	 System.out.println("Default state is not present correctly");
    	 resultDetails.setErrorMessage("Default state is not present correctly");
    	 return resultDetails;
	  		}
	} catch(Exception e){
		  resultDetails.setFlag(false);
		  System.out.println(e.getMessage());
		  resultDetails.setErrorMessage(e.getMessage());
	    	 return resultDetails;
	  }
	
	  		break;
	
case SAV:
	resultDetails=showall(webdriver, field, fieldName,title);
			break;
		}
  	  System.out.println("flag2 : "+resultDetails.getFlag());
		return resultDetails;
		
	}
	 public static ResultDetails showall(WebDriver webdriver, String field, String fieldName,String title) throws InterruptedException{
			Thread.sleep(5000);
			String sub=webdriver.findElement(By.xpath("//div[@id='maincolboxdrugdbheader']/ul/li")).getText();
			String[] subtitle=new String[10];
			subtitle=sub.split(":");
				if(subtitle[0].equalsIgnoreCase("Author")){

					List<WebElement> li=(List<WebElement>)webdriver.findElements(By.xpath("//div[@class='sectionmenu']/ul/li"));
					System.out.println(li.size());
					String[] str1 = new String[li.size()-1];
					for (int i = 0; i < li.size()-1; i++) {
						str1[i] = li.get(i).getText().toString();
					}
					WebElement myElement = webdriver.findElement(By.linkText("Show All"));
					Actions builder = new Actions(webdriver);
					builder.moveToElement(myElement).build().perform();
					webdriver.findElement(By.linkText("Show All")).click();
					for(int i =0; i < li.size()-1; i++){
						if(webdriver.findElement(By.xpath("//div[@class='drugdbsectioncontent']/div["+(i+1)+"]/h2")).getText().toLowerCase().contains(str1[i].toLowerCase())){
							resultDetails.setFlag(true);
						}else{
							resultDetails.setFlag(false);
							System.out.println( str1[i]+ " Link is not present in show all");
							resultDetails.setErrorMessage(str1[i]+ "Link is not present in show all");
							return resultDetails;
						}
		}
				}else{
					if(webdriver.getPageSource().contains(title)){
						resultDetails.setFlag(true);
					}else{
						if(webdriver.findElement(By.xpath("//div[@id='maincolboxdrugdbheader']/h1")).getText().contains(title)){
							resultDetails.setFlag(true);
						}else{
				 if(title.contains(webdriver.getTitle())){
				 resultDetails.setFlag(true);
			 }else{
				 if(title.startsWith(webdriver.getTitle().substring(0, webdriver.getTitle().indexOf(" ")))){
					 
				 }else{
					 if(webdriver.findElement(By.xpath("")).getText().contains(title)){
					 resultDetails.setFlag(true); 
				 }else
				 {
					 resultDetails.setFlag(false);
					 System.out.println("Link is not present "+title+" in show all");
					 resultDetails.setErrorMessage("Link is not present "+title+" in show all");
					 return resultDetails;
				 }
				 }
				 }
			 }
					}
				}
			
				if(subtitle[0].equalsIgnoreCase("Author")){
					Thread.sleep(5000);
					webdriver.navigate().back();
					Thread.sleep(5000);
					webdriver.navigate().back();
					Thread.sleep(5000);
				}else{
					Thread.sleep(5000);
					webdriver.navigate().back();
					Thread.sleep(5000);
				}
			
			return resultDetails;
			
		}
	 
		public ResultDetails mdic(WebDriver webdriver,String fieldText,String value) throws InterruptedException {
			System.out.println("fieldText= " + fieldText + " value= " + value);
			DataFileds dfs = DataFileds.valueOf(fieldText.substring(0, fieldText.indexOf("/")));
			ResultDetails resultDetails = new ResultDetails();
			resultDetails.setFlag(false);
						
			switch(dfs)
			{
			case drglimit:
						String field=fieldText.substring(8, fieldText.length());
						resultDetails=adddrugs(webdriver,field,25);
						String attr=webdriver.findElement(By.xpath("//div[@id='loading_img']/img")).getAttribute("alt");
						if(attr.equalsIgnoreCase("activity indicator"))
						{
							System.out.println("loading-image found");
						}
						else
						{
							System.out.println("loading-image not found");
							resultDetails.setFlag(false);
							return resultDetails;
						}
						System.out.println("after removing one drug");
						webdriver.findElement(By.xpath("//ul[@id='pdlist']/li/span")).click();
						Thread.sleep(7000);
						try{
							if(webdriver.findElement(By.xpath("//div[@id='loading_img']/img")).isDisplayed())
								
							{
								System.out.println("still loading even after 25th drug is removed");
								resultDetails.setFlag(false);
								return resultDetails;
							}
							else
							{
								System.out.println("interactions found after deleting 25th drug");
								resultDetails.setFlag(true);
							}
						}
						catch(Exception e)
						{
							resultDetails.setFlag(true);
						}
			break;
			case dropdown:
				field=fieldText.substring(7, fieldText.length());
				try{
					int f=0; 
					int p=Integer.parseInt(value);
					resultDetails=adddrugs(webdriver,field,p);
				}catch(Exception e){
					System.out.println(e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
					return resultDetails;
				}
				break;
			case adddrug: 
					field=fieldText.substring(7, fieldText.length());
				try{
						int f=0; 
						int p=Integer.parseInt(value);
						resultDetails=adddrugs(webdriver,field,p);
						String no_of_interactions=webdriver.findElement(By.xpath("/html/body/div[2]/div[3]/div[2]/div/div[2]/div/div[5]/div[3]/span")).getText();
						int t=Integer.parseInt(no_of_interactions);
						System.out.println("interactions found==="+no_of_interactions);
						int l=webdriver.findElements(By.xpath("//div[@id='intcheck_intfound']/div")).size();
						for(int s=1;s<=l;s++)
						{
							f=f+webdriver.findElements(By.xpath("//div[@id='intcheck_intfound']/div["+s+"]/ul/li")).size();
						}
						Thread.sleep(5000);
						System.out.println("\n no of drug combinations in interactions==="+f);
						if(f==t)
							resultDetails.setFlag(true);
						
						else
						resultDetails.setFlag(false);	
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage(e.getMessage());
				return resultDetails;
			}
						break;
			}
			
			return resultDetails;
		}
		public ResultDetails adddrugs(WebDriver webdriver,String field,int n) throws InterruptedException
		{
			String[] field1=new String[2];
			if(field.contains("::")){
				field1=field.split("::");
			}
			webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field1[0])).sendKeys(getRandomChar());
			Thread.sleep(5000);
			webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field1[1]+"/a")).click();
			//System.out.println(" No of drugs present currently==="+webdriver.findElements(By.xpath("//ul[@id='pdlist']/li")).size());
			//System.out.println();
			Thread.sleep(2000);
			
			if(n>1){
			while(webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver, field1[2])).size()<n)
			{
				
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field1[0])).sendKeys(getRandomChar());
				Thread.sleep(5000);
				int p=webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver, field1[1])).size();
				System.out.println("no of drugs shown in the drugs list==="+p+"\n");
				if(p>12){
					p=8;
				}
				int c=getRandom(p);
				System.out.println("clicking drug no==="+c+"\n");
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field1[1]+"["+c+"]/a")).click();
				Thread.sleep(2000);
				System.out.println(" No of drugs present currently==="+webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver, field1[2])).size());
				System.out.println();
			}
			}
			return resultDetails;
		}
		
					public static String getRandomChar() {
							final String alphabet = "abcdefghijklmnopqrstuvwxyz";
							final int n = alphabet.length();
							char ch;
		    Random r = new Random();
		    ch=alphabet.charAt(r.nextInt(n));
		    String temp = Character.toString(ch);
			return temp;
			}
		
		public static ResultDetails randomcheck(WebDriver webdriver,String[] stt,String st, int r,int l){
			try{ 
			for(int k=2;k<=3;k++){
				  List<WebElement> li1=(List<WebElement>)webdriver.findElements(By.xpath("//div[@id='fs_planList']/div["+k+"]/ul/li"));
	      			  for(int i=1;i<=1;i++){
					  r=getRandom(li1.size());
					  stt[l]= webdriver.findElement(By.xpath("//div[@id='fs_planList']/div["+k+"]/ul/li["+r+"]")).getText();
					  webdriver.findElement(By.xpath("//div[@id='fs_planList']/div["+k+"]/ul/li["+r+"]/input")).click();

					  System.out.println(stt[l]);
					  l++;
				  }
	            }
			webdriver.findElement(By.xpath("//div[@class='closewindow']/a/span")).click();
			  List<WebElement> li2=(List<WebElement>)webdriver.findElements(By.xpath("//div[@id='fs_table']/table/tbody/tr"));
			 String s = null;
			  for(int f=0;f<=li2.size()-2;f++){
				  int count=0;
	              for(int i=0;i<=li2.size()-2;i++){
	            	//  System.out.println(webdriver.findElement(By.xpath("//div[@id='fs_table']/table/tbody/tr["+(f+2)+"]/td/span")).getText());
	             	//System.out.println(stt[i]);
	            if(webdriver.findElement(By.xpath("//div[@id='fs_table']/table/tbody/tr["+(f+2)+"]/td/span")).getText().contains(stt[i])){ 
	         	   resultDetails.setFlag(true);
	         	   System.out.println("true");
	         	   count++;
	            }else{
	         	   resultDetails.setFlag(false);
	            }
	               }
	              if(count==0){
	           	   resultDetails.setFlag(false);
	           	   System.out.println("plan list is not matched for "+s);
	           	   resultDetails.setErrorMessage("plan list is not matched for "+s);
	                  return resultDetails;
	              }else{
	           	   resultDetails.setFlag(true);
	              }
	 	  } 
			  }catch(Exception e){
			  resultDetails.setFlag(false);
			  System.out.println(e.getMessage());
			  resultDetails.setErrorMessage(e.getMessage());
			  return resultDetails;
		  }
			return resultDetails;
		  }
		 
		public static ResultDetails selectstate(WebDriver webdriver,String field){
			try{
				Select selectBox2 = new Select(webdriver.findElement(By.xpath("//select[@name='state']")));
		    	   List<WebElement> l4=(List<WebElement>)webdriver.findElements(By.xpath("//select[@name='state']/option"));
	           System.out.println(l4.size());
	           int r=getRandom(l4.size());
	           if(r>51){
	           r=58;
	           }
	           selectBox2.selectByIndex(r-1);
	           String s1=selectBox2.getFirstSelectedOption().getAttribute("value");
	           String s2=selectBox2.getFirstSelectedOption().getText();
	           System.out.println(s2);
	           String[] states=new String[l4.size()];
	           String[] zipcode=new String[]{"35801","99524","85055","72217","94209","80239","06112","19905","20020","32509","30381","96830","83254","60641","46209","50323","67221","41702","70119","04034","21237","02137","49036","55808","39535","63141","59044","68902","89513","3217","07039","87506","10048","27565","58282","44179","74110","97225","15244","02841","29020","57402","37222","78705","84323","05751","24517","98009","25813","53228","82941"};
	           for(int i=0;i<=50;i++){
	          	 states[i]=l4.get(i).getAttribute("value");
	          	            }
	           for(int i=0;i<=50;i++){
	          hMap1.put(states[i],zipcode[i]);
	                   }
	         if(r==58){
	        	 webdriver.findElement(By.xpath("//input[@id='regzippractice']")).clear();
	      	   webdriver.findElement(By.xpath("//input[@id='regzippractice']")).sendKeys("00603"); 
	      	  resultDetails.setFlag(true);
	         }else{
	        	 webdriver.findElement(By.xpath("//input[@id='regzippractice']")).clear();
	          webdriver.findElement(By.xpath("//input[@id='regzippractice']")).sendKeys(hMap1.get(s1));
	         resultDetails.setFlag(true);
	         }
	         String currentstate = null;
	          hMap1.put("currentstate",s2);
	         System.out.println("current state : "+ hMap1.get("currentstate"));
	      		}
			catch(Exception e){
				 resultDetails.setFlag(false);
				  System.out.println(e.getMessage());
				  resultDetails.setErrorMessage(e.getMessage());
				  return resultDetails;
			}
			return resultDetails;
	                       
		}

		  
			public static ResultDetails removecheck(WebDriver webdriver) throws InterruptedException{
	      		try{
				for(int k=2;k<=3;k++){
	     			  List<WebElement> li2=(List<WebElement>)webdriver.findElements(By.xpath("//div[@id='fs_planList']/div["+k+"]/ul/li"));
	           			  for(int i=1;i<=li2.size();i++){
	           		try{		 
	     				if(webdriver.findElement(By.xpath("//div[@id='fs_planList']/div["+k+"]/ul/li["+i+"]/input")).isSelected()){
	     					webdriver.findElement(By.xpath("//div[@id='fs_planList']/div["+k+"]/ul/li["+i+"]/input")).click();
	     					resultDetails.setFlag(true);
	     				 }
	           		}catch(Exception e){} 
	     			  }
	                 }
	       		webdriver.findElement(By.xpath("//div[@class='closewindow']/a/span")).click();
	       		Thread.sleep(5000);
	      		}
	      		catch(Exception e){
	      			 resultDetails.setFlag(false);
	      			  System.out.println(e.getMessage());
	      			  resultDetails.setErrorMessage(e.getMessage());
	      			  return resultDetails;
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
		 	public static ResultDetails randomclick(WebDriver webdriver, String field,String value, String fieldName,String title) throws InterruptedException{
				try{
					System.out.println(value);
					String s="/a";
					int a=0;
					Thread.sleep(5000);
					if(field.endsWith("/tr")){
						 s=s.replace("/a", "/td/a");
					}
					if(webdriver.findElement(By.xpath(field+s)).isDisplayed()){
					 List<WebElement> li2=(List<WebElement>)webdriver.findElements(By.xpath(field));
					 System.out.println(li2.size());
					 a=getRandom(li2.size());
					 List<WebElement> li3=(List<WebElement>)webdriver.findElements(By.xpath(field+"["+a+"]"+s));
					 int h=li3.size();
					 System.out.println(field+"["+a+"]"+s);
					 System.out.println(li3.size());
					while(h==0){
						a=getRandom(li2.size()); 
						 List<WebElement> li4=(List<WebElement>)webdriver.findElements(By.xpath(field+"["+a+"]"+s));
						 	h=li4.size();
					 }
				 WebElement myElement = webdriver.findElement(By.xpath(field+"["+a+"]"+s+"["+h+"]"));
					Actions builder = new Actions(webdriver);
					builder.moveToElement(myElement).build().perform();
				
						System.out.println(field+"["+a+"]"+s+"["+h+"]");
						Thread.sleep(5000);
						String str=	webdriver.findElement(By.xpath(field+"["+a+"]"+s+"["+h+"]")).getText();
						System.out.println(str);
						webdriver.findElement(By.linkText(str)).click();
						if(value.equalsIgnoreCase("")){
						title=str;
						resultDetails=showall(webdriver, field, fieldName, title);
						if(!resultDetails.getFlag()){
						return resultDetails;
						}
					}
						resultDetails.setFlag(true);
					}
					}catch(Exception e){
						resultDetails.setFlag(true);
						System.out.println("Links are not present in this navigation");
					}
				
				return resultDetails;
				
			}
		
	/*
	 * This is a function to verify the Image Hover is enable or not
	 */
	public ResultDetails imageHover(WebDriver webdriver, String field,
			String value, String fieldName) {
		try {
			WebElement element = webdriver.findElement(By.xpath(value));
			String str1, str2;
			str1 = element.getAttribute("title");
			System.out.println(str1);

			Actions builder = new Actions(webdriver);
			builder.clickAndHold(element).perform();

			str2 = element.getAttribute("title");
			if (str1.equalsIgnoreCase(str2)) {
				assertFalse(true);
			}
			ResultDetails resultDetails = new ResultDetails();
			resultDetails.setFlag(true);
		} catch (Throwable e) {
			System.out.println("Image Hover is disabled");
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(field + " Image Hover is disabled");
		}
		return resultDetails;
	}
}