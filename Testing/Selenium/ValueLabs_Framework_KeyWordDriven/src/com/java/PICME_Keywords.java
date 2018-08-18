package com.java;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import com.java.Objects.ResultDetails;
import com.java.Objects.TestDataDetails;

public class PICME_Keywords extends TestType {

	public WebDriver webdriver = null;
	
	public Actions builder = null;
	public static String urll=SeleniumDriverTest.appUrl;
	public static String query="";
	public static int profession_id;
	public static 	String env=SeleniumDriverTest.Environ;
	 
	
	
	public static 	String picme_id;
	ResultDetails resultDetails = new ResultDetails();
	
	public PICME_Keywords(WebDriver webdriver) {
		this.webdriver = webdriver;
	}

	public enum DataFileds {
		stga,LPV,stgb,stgc,SUV,SAV,CRT,CRTR,EDV
	}
	// Keywords
	public enum ActionTypes {
		 PICME,
	};
	
	public ResultDetails performAction(String fieldText, String value,
			String actionType, String fieldName) {
		try {
			ActionTypes actTypes = ActionTypes
					.valueOf(actionType.toUpperCase());
			switch (actTypes) {
				
				case PICME:
					
					resultDetails = picme(webdriver, fieldText, value);
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
	

	public  ResultDetails picme(WebDriver webdriver, String field, String value) throws InterruptedException, ClassNotFoundException, SQLException, IOException{
		
		System.out.println("field= " + field + " value= " + value);
		DataFileds dfs = DataFileds.valueOf(field);
		field = field.trim();
		field = field.substring(3, field.length());
		ResultDetails resultDetails = new ResultDetails();
		resultDetails.setFlag(false);
		
		String f="[\\$$]+";
		String temp[]=value.split(f);
		for(int i =0; i < temp.length ; i++)
		{//System.out.println("temp["+i+"]=" +temp[i]);
		}
		
		 picme_id=temp[0];value=temp[1];
		//System.out.println("Data value in picme:"+p[0]+"value_p[1]:"+p[1]);
		if (picme_id.startsWith("dt:")){
			if (SeleniumDriverTest.parameterDetails.containsKey(picme_id
					.substring(3).replace("#", "").toLowerCase())) {
				picme_id = SeleniumDriverTest.parameterDetails.get(picme_id
						.substring(3).replace("#", "").toLowerCase());
			}
		}
		System.out.println("picme_id= " + picme_id+"value="+value);
		//Properties dbProps=new Properties();
		//FileInputStream in = new FileInputStream("./TestInputs/properties/picme.properties");
		//dbProps.load(in);
		//System.out.println("pid="+dbProps.getProperty("pid"));
		//picme_id= dbProps.getProperty("pid");
		
		Thread.sleep(3000);
		//in.close();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection	con=DriverManager.getConnection("jdbc:oracle:thin:@racvp01q-prf-08.portal.webmd.com:1521/SVCMED_"+env,"survey","survey");
		Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

		switch(dfs){
		
		case stga:
			field=field.trim();
			String t4[]=value.split(",");
			System.out.println(t4[0]+"  "+t4[1]);
			t4[0]=t4[0].trim();
			t4[1]=t4[1].trim();
			System.out.println("country="+t4[0]+" speciality="+t4[1]);
			ResultSet rss; 
			if(t4[0].contains("Physician")){
				profession_id=10;
				System.out.println("retrieving Physician credentials");
			}
			else if(t4[0].contains("Nurse/Advanced Practice Nurse")){
				profession_id=12;
				System.out.println("retrieving Nurse/Advanced Practice Nurse credentials");
			}
			else if(t4[0].contains("Pharmacist")){
				profession_id=8;
				System.out.println("retrieving Pharmacist credentials");
			}
			query="select  ru.GLOBAL_USER_ID, ru.LOGIN_NAME,ru.PASSWD from REG_USER  ru,REG_PROFESSIONAL rp  where  ru.GLOBAL_USER_ID  not in (select GUID from PICME_USER_SESSION where STAGE_A_START is not null )   and rp.GLOBAL_USER_ID=ru.GLOBAL_USER_ID and rp.PROFESSION_ID="+profession_id+"  and not  REGEXP_LIKE(ru.LOGIN_NAME, '[0-9][0-9][0-9][0-9[A-Z][A-Z]$') and rownum<250";
			rss=st.executeQuery(query);
			userB(webdriver,rss,t4,field);
			if(webdriver.findElement(By.xpath("//div[@id='continuepicmebtn']//a")).isEnabled()){
				resultDetails.setFlag(true);
			}
			break;
		case LPV: try{
			int s=webdriver.findElements(By.xpath("//div[@id='resultsContainer']")).size();
			System.out.println("no of performance measures="+s);
			for(int k=0;k<s;k++)
			{
				Actions hover = new Actions(webdriver);
				hover.moveToElement(webdriver.findElement(By.xpath("//input[@id='plan["+k+"].improvementCriteria[0].selectedBridge1']"))).build().perform();
				Thread.sleep(200);
				String string=webdriver.findElement(By.xpath("//div[@id='resultsContainer']["+(k+1)+"]/div")).getText();
				System.out.println(string+" displayed");
				System.out.println("");
				System.out.println("clicking the checkbox in "+string);
				System.out.println("");
				webdriver.findElement(By.xpath("//input[@id='plan["+k+"].improvementCriteria[0].selectedBridge1']")).click();
			}
			System.out.println("");
			resultDetails.setFlag(true);
		}
		catch(Exception e4){
			System.out.println(e4.getMessage());
			resultDetails.setFlag(false);
		}
		
			break;
		
		case stgb:
			try{
				String t[]=value.split(",");
				System.out.println(t[0]+"  "+t[1]);
				t[0]=t[0].trim();
				t[1]=t[1].trim();
				System.out.println("env="+env);
				con=DriverManager.getConnection("jdbc:oracle:thin:@racvp01q-prf-08.portal.webmd.com:1521/SVCMED_"+env,"survey","survey");
				st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs;
				if(t[0].contains("Physician")){
					profession_id=10;
					System.out.println("retrieving Physician credentials");
				}
				else if(t[0].contains("Nurse/Advanced Practice Nurse")){
					profession_id=12;
					System.out.println("retrieving Nurse/Advanced Practice Nurse credentials");
				}
				else{
					profession_id=8;
					System.out.println("retrieving Pharmacist credentials");
				}
				query="select R.GLOBAL_USER_ID,R.LOGIN_NAME,R.PASSWD ,G.GUID,G.PICME_ID from REG_PROFESSIONAL RP,REG_USER R ,PICME_USER_SESSION G where G.STAGE_A_FINISH is not null and G.STAGE_B_START is null and G.PICME_ID="+picme_id+" and G.GUID=R.GLOBAL_USER_ID and RP.GLOBAL_USER_ID=R.GLOBAL_USER_ID and RP.PROFESSION_ID="+profession_id;
				rs=st.executeQuery(query);
				userB(webdriver,rs,t,field);
				if(webdriver.findElement(By.xpath("//div[@id='continuepicmebtn']//a")).getText().contains("Continue to Learning Plan")){
					resultDetails.setFlag(true);
				}
			}
			catch(Exception e){
				System.out.println(e);
				resultDetails.setFlag(false);
			}
			break;
		case stgc:
			con=DriverManager.getConnection("jdbc:oracle:thin:@racvp01q-prf-08.portal.webmd.com:1521/SVCMED_"+env,"survey","survey");
			st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs;
			if(value.contains("Physician")){
				profession_id=10;
				System.out.println("retrieving Physician credentials");
			}
			else if(value.contains("Nurse/Advanced Practice Nurse")){
				profession_id=12;
				System.out.println("retrieving Nurse/Advanced Practice Nurse credentials");
			}
			else
			{
				profession_id=8;
				System.out.println("retrieving Pharmacist credentials");
			}
			query="select R.GLOBAL_USER_ID,R.LOGIN_NAME,R.PASSWD ,G.GUID,G.PICME_ID from REG_PROFESSIONAL RP,REG_USER R ,PICME_USER_SESSION G where G.STAGE_B_FINISH is not null and G.STAGE_C_START is null and G.PICME_ID="+picme_id+" and G.GUID=R.GLOBAL_USER_ID and RP.GLOBAL_USER_ID=R.GLOBAL_USER_ID and RP.PROFESSION_ID="+profession_id+" and G.STAGE_B_FINISH<sysdate-90";
			rs=st.executeQuery(query);
			user(webdriver,rs);
	
			if(webdriver.findElement(By.xpath("//div[@id='continuepicmebtn']//a")).isEnabled()){
				resultDetails.setFlag(true);
				return resultDetails;
			}
			else{
				System.out.println("in else");
				user(webdriver,rs);
			}
			break;
		case SUV:
			String d2=webdriver.findElement(By.xpath("//div[@id='cmeform']//p")).getText();
			d2=d2.trim();
			int a=d2.indexOf("answered");
			String num=d2.substring(a+9, a+12);
			num=num.trim();
			int z=webdriver.findElements(By.xpath("//div[@class='qaresponse']/table")).size();
			int count=0;
			for(int i=1;i<=z;i++){
				String d1=webdriver.findElement(By.xpath("//div[@class='qaresponse']/table["+i+"]/tbody/tr[2]/td[3]")).getText();
				d1=d1.trim();
				if(d1.equalsIgnoreCase("Best Answer"))
					count++;
			}
				resultDetails.setFlag(true);
			if(count==Integer.parseInt(num)){
				System.out.println("no of answers answered correctly="+count);
			}
		break;
		case SAV:
			try{
				int n=0;
				if(value.equalsIgnoreCase("all")){
					n=webdriver.findElements(By.xpath("//div[@class='questiontext12']//b")).size();
				}
				else{
					n=Integer.parseInt(value);
				}
				System.out.println("no of questions==="+n);
				for(int m=0;m<n;m++){
					Actions hover = new Actions(webdriver);
					hover.moveToElement(webdriver.findElement(By.xpath("//input[@id='forms[0].questions["+m+"].selectedChoice1']"))).build().perform();
					Thread.sleep(100);
					webdriver.findElement(By.xpath("//input[@id='forms[0].questions["+m+"].selectedChoice1']")).click();
				}
				webdriver.findElement(By.xpath("//div[@id='continuepicmebtn']/input")).submit();
				System.out.println("Save & Submit Self Assessment clicked");
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				resultDetails.setFlag(false);
				return resultDetails;
			}
			break;
		
		
			
		case CRT:
			try{
				int form_id;
				String t[]=value.split(",");
				System.out.println(t[0]+"  "+t[1]);
				
				ResultSet rs1,rs3;
				try{
					int k=0;
					if(t[1].equalsIgnoreCase("all")){
						rs1=st.executeQuery("select STAGE_"+t[0].toUpperCase()+"_PATIENTS_COUNT from picme_info where STAGE_A_EXPIRATION>sysdate and PICME_ID="+picme_id);
						rs1.next();
						k=rs1.getInt(1);
					}
					else{
					k=Integer.parseInt(t[1]);
					}
					rs3=st.executeQuery("select questionnaire_id from picme_info where picme_id="+picme_id);
					rs3.next();
					int questionnaire_id=rs3.getInt(1);
					System.out.println("questionnaire_id="+questionnaire_id);
					if(t[0].toUpperCase().equalsIgnoreCase("A")){
						form_id=5;
						System.out.println("retrieving satge A chart review choice ids");
					}
					else{
						form_id=7;
						System.out.println("retrieving satge C chart review choice ids");
					}
					query="select qc.choice_id  from question_choice qc,question_display qd  where qd.questionnaire_Id="+questionnaire_id+" and qd.form_id="+form_id+" and qc.display_order=1 and qc.question_id=qd.question_id";
					rs3=st.executeQuery(query);
					
					int i=0;
					int[] choice_id=new int[20];
					int j=0;
					while(rs3.next()){
						choice_id[i]=rs3.getInt(1);
						System.out.println("choice_id["+i+"]="+choice_id[i]);
					i++;j++;
					}
					//System.out.println("No Of Patients="+k);
					for(int i3=0;i3<k;i3++){
						
						for(int i2=0;i2<j;i2++){
							Actions hover = new Actions(webdriver);
							hover.moveToElement(webdriver.findElement(By.xpath("//input[@value="+choice_id[i2]+"]"))).build().perform();
							Thread.sleep(200);
							webdriver.findElement(By.xpath("//input[@value="+choice_id[i2]+"]")).click();
						}
						System.out.println("Patient "+(i3+1)+" of "+k+" completed");
						webdriver.findElement(By.xpath("//div[@id='continuepicmebtn']/input")).submit();
						WebDriverUtils.waitForPageToLoad(webdriver, "30000");
					}
					System.out.println("no of charts completed:"+k);
					resultDetails.setFlag(true);
					
				}
				catch(Exception e){
				System.out.println(e.getMessage());
				resultDetails.setFlag(false);
				
				return resultDetails;
				}
			}
			catch(Exception e1){
				System.out.println(e1.getMessage());
				resultDetails.setFlag(false);
			
				return resultDetails;
			}
			
			break;
		
		case CRTR:
			try{
				
				String url=webdriver.getCurrentUrl();
				int form_id;
				String t[]=value.split(",");
				ResultSet rs3;
				rs3=st.executeQuery("select STAGE_"+t[0].toUpperCase()+"_PATIENTS_COUNT from picme_info where STAGE_A_EXPIRATION>sysdate and PICME_ID="+picme_id);
				rs3.next();
				int k1=rs3.getInt(1);
				int f1=getRandom(k1-1);
				System.out.println("Answering "+f1+" no of charts");
			
				rs3=st.executeQuery("select questionnaire_id from picme_info where picme_id="+picme_id);
				rs3.next();
				if(t[1].equalsIgnoreCase("back")){
					int questionnaire_id=rs3.getInt(1);
					System.out.println("questionnaire_id="+questionnaire_id);
					if(t[0].toUpperCase().equalsIgnoreCase("A")){
						System.out.println("retrieving satge A chart review choice ids");
						form_id=5;
					}
				else{
					System.out.println("retrieving satge C chart review choice ids");
					form_id=7;
				}
				query="select qc.choice_id  from question_choice qc,question_display qd  where qd.questionnaire_Id="+questionnaire_id+" and qd.form_id="+form_id+" and qc.display_order=1 and qc.question_id=qd.question_id";

				rs3=st.executeQuery(query);
				int i=0;
				int[] choice_id=new int[20];
				int j=0;
				while(rs3.next()){
					choice_id[i]=rs3.getInt(1);
					System.out.println("choice_id["+i+"]="+choice_id[i]);
				i++;j++;
				}
				for(int i4=0;i4<f1;i4++){
					for(int i2=0;i2<j;i2++){
						Actions hover = new Actions(webdriver);
						hover.moveToElement(webdriver.findElement(By.xpath("//input[@value="+choice_id[i2]+"]"))).build().perform();
						Thread.sleep(500);
						webdriver.findElement(By.xpath("//input[@value="+choice_id[i2]+"]")).click();
					}
					System.out.println("Patient "+(i4+1)+" of "+k1+" completed");
					webdriver.findElement(By.xpath("//div[@id='continuepicmebtn']/input")).submit();//submit chart review button
					WebDriverUtils.waitForPageToLoad(webdriver, "30000");
				}
			
					System.out.println("Navigating Back");
					webdriver.navigate().back();
					String browser=SeleniumDriverTest.hMap.get("Browser");
					if(browser.equalsIgnoreCase("FF")){
						webdriver.findElement(By.xpath("//*[@id='errorTryAgain']")).click();
						webdriver.switchTo().alert().accept();
						webdriver.findElement(By.xpath("//div[@id='continuepicmebtn']/a")).click();//return to picme link
			
					}
					if(!browser.equalsIgnoreCase("FF")){
						//System.out.println("Navigating Back");
						//webdriver.navigate().back();
						webdriver.findElement(By.xpath("//div[@id='continuepicmebtn']/input")).submit();//submit chart review button
						webdriver.findElement(By.xpath("//div[@id='continuepicmebtn']/a")).click();//return to picme link
					}
			
				}
			
			String com=webdriver.findElement(By.xpath("//div[@class='complete']")).getText();
			if(com.equalsIgnoreCase("Self Assessment: Complete")){
				System.out.println("Self Assessment: Complete is displayed");
			}
			else{
				System.out.println("Self Assessment: Complete is not displayed");
			}
			String progress=webdriver.findElement(By.xpath("//div[@class='inProgress']")).getText();
			if(progress.equalsIgnoreCase("Chart Review in progress: "+f1+"/ "+k1+" completed")){
				System.out.println("Chart Review in progress: "+f1+"/ "+k1+" completed  is displayed");
			}
			else{
				System.out.println("Chart Review in progress: "+f1+"/ "+k1+" completed  is  not displayed");
			}
			webdriver.findElement(By.xpath("//div[@id='continuepicmebtn']//a")).click();
			WebDriverUtils.waitForPageToLoad(webdriver, "7000");
			String s3=webdriver.findElement(By.xpath("//div[@id='cmeform']//h3")).getText();
			System.out.println(s3);
			if(s3.equalsIgnoreCase("Patient "+(f1+1)+" of "+k1+"")){
				System.out.println("Patient "+(f1+1)+" of "+k1+"  is displayed");
				resultDetails.setFlag(true);
			}
			else{
				System.out.println("Patient "+(f1+1)+" of "+k1+"  is not displayed");
				resultDetails.setFlag(false);
			}
			
				System.out.println("opening Url in middle of course::"+url);
				webdriver.get(url);
			
			String com2=webdriver.findElement(By.xpath("//div[@class='complete']")).getText();
			if(com2.equalsIgnoreCase("Self Assessment: Complete")){
				System.out.println("Self Assessment: Complete is displayed");
			}
			else{
				System.out.println("Self Assessment: Complete is not displayed");
			}
			String progress2=webdriver.findElement(By.xpath("//div[@class='inProgress']")).getText();
			if(progress2.equalsIgnoreCase("Chart Review in progress: "+f1+"/ "+k1+" completed")){
				System.out.println("Chart Review in progress: "+f1+"/ "+k1+" completed  is displayed");
			}
			else{
				System.out.println("Chart Review in progress: "+f1+"/ "+k1+" completed  is  not displayed");
			}
			webdriver.findElement(By.xpath("//div[@id='continuepicmebtn']//a")).click();
			WebDriverUtils.waitForPageToLoad(webdriver, "7000");
			String s32=webdriver.findElement(By.xpath("//div[@id='cmeform']//h3")).getText();
			//System.out.println(s32);
			if(s32.equalsIgnoreCase("Patient "+(f1+1)+" of "+k1+"")){
				System.out.println("Patient "+(f1+1)+" of "+k1+"  is displayed");
				resultDetails.setFlag(true);
			}
			else{
				System.out.println("Patient "+(f1+1)+" of "+k1+"  is not displayed");
				resultDetails.setFlag(false);
			}
		}
		catch(Exception e4){
			System.out.println(e4.getMessage());
			resultDetails.setFlag(false);
		}
		break;
		
		
		case EDV:
			try{
				Thread.sleep(5000);
				String s=webdriver.findElement(By.xpath("//p[@id='releasedate']")).getText();
				//System.out.println(s);
				String s1[];
				s1=s.split(";");
				//System.out.println("s1="+s1[1]+" s2="+s1[2]);
				int i=s1[1].indexOf("/");
				//System.out.println(i);
				String A_date=s1[1].substring(i-2, s1[1].length());
				//System.out.println("date1="+A_date);
				int j=s1[2].indexOf("/");
				String BC_date=s1[2].substring(j-2, s1[2].length());
				System.out.println("Expiration of Stage A============="+A_date);
				SimpleDateFormat sdf = new SimpleDateFormat( "mm/dd/yyyy" );  
				java.util.Date d1 = sdf.parse( A_date );  
				java.util.Date d21 = sdf.parse( BC_date );  
				System.out.println( "Stage A Expiration Date: " + sdf.format( d1 ).toUpperCase() );  
				System.out.println( "Stage BC Expiration Date: " + sdf.format( d21 ).toUpperCase() );  
			   if ( compareTo( d1, d21 ) < 0 ){  
				   System.out.println( "Stage B&C Expiration date is greater than that of Stage A" );  
				resultDetails.setFlag(true);
			   }  
				else if ( compareTo( d1, d21 ) > 0 ){  
					System.out.println( "Stage B&C Expiration date is less than that of Stage A" );  
					resultDetails.setFlag(false);
				}  
				else  
				{  
					System.out.println( "Stage B&C Expiration date is equal to that of Stage A" );  
					resultDetails.setFlag(true);
				}  
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				resultDetails.setFlag(false);
				return resultDetails;
			}
		break;
			
		}//switch close
		return resultDetails;
	}//picme close
	public  void userB(WebDriver driver,ResultSet rs,String t[],String stage) throws SQLException, InterruptedException
	{
		ResultDetails resultDetails = new ResultDetails();
		try{
			
			
			if(rs.next()){
				
				rs.beforeFirst();
				int k=0;
				while(rs.next()){
					k++;
				}
				System.out.println("no of users="+k);
				rs.beforeFirst();
				int p= getRandom(k-1);
				
				rs.absolute(p);
				String username=rs.getString("LOGIN_NAME");
				String pwd=rs.getString("PASSWD");
				
				driver.get("http://tools.bi.medscape.com/php/decrypt.php");
				driver.findElement(By.xpath("//input[@id='str2decrypt']")).sendKeys(pwd);
				driver.findElement(By.xpath("//input[@type='SUBMIT']")).submit();
				String pass=driver.findElement(By.xpath("//form")).getText();
				
				String s[]=pass.split(":");
				System.out.println("username=="+username+" \npassword=="+s[3].trim());
				String password=s[3].trim();
				System.out.println(picme_id+"is retreived");
				driver.get("http://www."+env+".medscape.org/picme/start/"+picme_id);
				
				Thread.sleep(1000);
				driver.findElement(By.id("userId")).sendKeys(username);
				driver.findElement(By.id("password")).sendKeys(password);
				driver.findElement(By.id("loginbtn")).click();
				
			}
			else
			{
				System.out.println("registering user as there are no sufficient user from database ");
				driver.findElement(By.linkText("Register")).click();
				System.out.println("register link clicked");
				RegisterUS Regus =new RegisterUS();
				resultDetails = Regus.register(driver, t[0] , t[1]);
				System.out.println("registered "+t[0]+" with "+t[1]+" speciality completed");
				driver.get("http://www."+env+".medscape.org/picme/start/"+picme_id);
				if(stage.equalsIgnoreCase("stgb"))
				{
				//continue to stage A
				driver.findElement(By.xpath("//div[@id='continuepicmebtn']//a")).click();
				//continue to stage self assesment
				driver.findElement(By.xpath("//div[@id='continuepicmebtn']//a")).click();
				String field ="SAV";
				String value = "all";
				
				//PICME_Keywords p1= new PICME_Keywords();
				resultDetails = picme(driver, field,value);
				//save and submit
				driver.findElement(By.xpath("//div[@id='continuepicmebtn']/input")).click();
				//continue to chart review
				driver.findElement(By.xpath("//div[@id='continuepicmebtn']//a")).click();
				field="CRT";
				value="A,all";
				//continue to stage B
				driver.findElement(By.xpath("//div[@id='continuepicmebtn']//a")).click();
				}
			}
		}
		catch(Exception e9){
			System.out.println(e9.getMessage());
			resultDetails.setFlag(false);
		}
	}
	public  void user(WebDriver driver,ResultSet rs) throws SQLException, InterruptedException
	{
		try{
			//System.out.println("hai");
			//int p= getRandom(999);
			int k=0;
			//System.out.println(p);
			//rs.absolute(p);
			while(rs.next()){
				k++;
			}
			System.out.println("no of users="+k);
			rs.beforeFirst();
			int p= getRandom(k-1);
		
			rs.absolute(p);
			String username=rs.getString("LOGIN_NAME");
			String pwd=rs.getString("PASSWD");
			//System.out.println("encrypted form of password:"+pwd);
			driver.get("http://tools.bi.medscape.com/php/decrypt.php");
			driver.findElement(By.xpath("//input[@id='str2decrypt']")).sendKeys(pwd);
			driver.findElement(By.xpath("//input[@type='SUBMIT']")).submit();
			String pass=driver.findElement(By.xpath("//form")).getText();
			//System.out.println(pass);
			String s[]=pass.split(":");
			System.out.println("username=="+username+" \npassword=="+s[3].trim());
			String password=s[3].trim();
			driver.get("http://www."+env+".medscape.org/picme/start/"+picme_id);
			//driver.findElement(By.linkText("Log In")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("userId")).sendKeys(username);
			driver.findElement(By.id("password")).sendKeys(password);
			driver.findElement(By.id("loginbtn")).click();
			
		}
		catch(Exception e9){
			System.out.println(e9.getMessage());
		}
		
	}
	public  long compareTo( java.util.Date date1, java.util.Date date2 )  
	{  
	//returns negative value if date1 is before date2  
	//returns 0 if dates are even  
	//returns positive value if date1 is after date2  
	  return date1.getTime() - date2.getTime();  
	}
	public static int getRandom(int i){
		int r = 0;
		while (r == 0) {
			r = (int) ((Math.random()) * i) + 1;
		}
		return r;
	}
}