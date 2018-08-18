package com.java;

import java.util.*;


import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.java.Objects.ResultDetails;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RegisterUS  {
	String Message="";
	public static String username="";
	public String randNumGen(int i) {
		return RandomStringUtils.randomNumeric(i);
	}

	public String randNameGen(int i) {
		return RandomStringUtils.randomAlphabetic(i).toLowerCase();
	}

	public String randUserGen() {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmss");
		String strUser = ft.format(dNow);
		return strUser;
	}
	
	public void select(WebDriver webdriver,String id,String index)
	{
		Select sel=new Select(webdriver.findElement(By.id(id)));
		sel.selectByIndex(Integer.parseInt(index));
	}
	
	public void selectText(WebDriver webdriver,String id,String index)
	{
		Select sel=new Select(webdriver.findElement(By.id(id)));
		sel.selectByVisibleText(index);
	}

	public void randSelectGen(WebDriver webdriver,String str) {
		Select select=new Select(webdriver.findElement(By.id(str)));
		int options=select.getOptions().size();
		System.out.println(options);
		if(options>2)
		{
			Random rnd = new Random();
			int index=rnd.nextInt(options);
			if(index==0)
				index=index+1;
			System.out.println("selected Index"+index);
			select.selectByIndex(index);
		}
		else
		{
			select.selectByIndex(1);
		}
	}

	public enum ProfessionUS {
		physician, healthbusinessadministration, mediapress, medicalstudent,
		nurseadvancedpracticenurse, otherhealthcareprovider, pharmacist, physicianassistant, consumerother, psychologist, dentistoralhealthprofessional, optometrist
	};
	
	public ResultDetails register(WebDriver webdriver,String Profession,String value)  {
			try{
				String strField="";
				
				ResultDetails resultDetails = new ResultDetails();
				webdriver.manage().timeouts().implicitlyWait(9,TimeUnit.SECONDS);
				webdriver.findElement(By.id("regfnamefield")).sendKeys(randNameGen(6));
				webdriver.findElement(By.id("reglnamefield")).sendKeys(randNameGen(6));
				Select countryselect=new Select(webdriver.findElement(By.id("country")));
				countryselect.selectByVisibleText(Profession.split("::")[1].trim());
				webdriver.findElement(By.id("regzippractice")).sendKeys("10029");
				String email="SelAuto"+randUserGen()+"@"+randNumGen(5)+".com";
				webdriver.findElement(By.id("regemailaddress")).sendKeys(email);
				webdriver.findElement(By.id("regemailaddressconfirm")).sendKeys(email);
				webdriver.findElement(By.id("regusername")).clear();
				username = "SelAuto"+randUserGen();
				webdriver.findElement(By.id("regusername")).sendKeys(username);
				webdriver.findElement(By.id("regpassword")).sendKeys("123456");
				webdriver.findElement(By.id("regpasswordconfirm")).sendKeys("123456");
				randSelectGen(webdriver,"hintQuestion");
				webdriver.findElement(By.id("reghintanswer")).sendKeys(randNameGen(6));
				//Removing spaces and special symbols from fieldText
				strField = Profession.replace(" ", "");
				strField = strField.replace("/", "");
				strField = strField.toLowerCase();
				//System.out.println(strField);
				
				selectText(webdriver,"profession",Profession.split("::")[0].trim());
				
				WebDriverWait wait=new WebDriverWait(webdriver,10);
				countryselect=new Select(webdriver.findElement(By.id("profession")));
				String strProfval=countryselect.getFirstSelectedOption().getText().trim().toLowerCase();
				strProfval = strProfval.replace(" ", "");
				strProfval = strProfval.replace("/", "");
				strProfval = strProfval.toLowerCase();
				ProfessionUS prf = ProfessionUS.valueOf(strProfval);
				//ProfessionUS prf = ProfessionUS.valueOf(countryselect.getFirstSelectedOption().getText().trim().toLowerCase().replaceAll(" ",""));
				
				switch(prf)
				{
				case physician:
					wait.until(ExpectedConditions.visibilityOf(webdriver.findElement(By.id("specialty"))));
					//wait.until(ExpectedConditions.presenceOfElementLocated(By.id("medSchool")));
					wait.withTimeout(2, TimeUnit.SECONDS);
					randSelectGen(webdriver,"degree");
					wait.withTimeout(3, TimeUnit.SECONDS);
					System.out.println("Speciality is "+value);
					//value = value.toLowerCase();
					if(!value.equalsIgnoreCase("")){
						Select select=new Select(webdriver.findElement(By.id("specialty")));
						select.selectByVisibleText(value);
					}else{
						randSelectGen(webdriver,"specialty");
					}
					if ((webdriver.findElement(By.id("specialty")).getAttribute("value"))== "339" || (webdriver.findElement(By.id("specialty")).getAttribute("value"))=="340")
					{
						wait.withTimeout(2, TimeUnit.SECONDS);
						randSelectGen(webdriver,"subSpecialty");
					}
					wait.withTimeout(2, TimeUnit.SECONDS);
					Thread.sleep(5000);
					System.out.println("Selecting medschool");
					randSelectGen(webdriver,"medSchoolState");
					if ((webdriver.findElement(By.id("medSchoolState")).getAttribute("value"))== "00")
					{
						wait.withTimeout(2, TimeUnit.SECONDS);
						wait.until(ExpectedConditions.presenceOfElementLocated(By.id("medSchoolCountry")));
						randSelectGen(webdriver,"medSchoolCountry");
					}
					//webdriver.findElement(By.id("medSchool")).click();
					wait.until(ExpectedConditions.elementToBeClickable(By.id("medSchool")));
					Thread.sleep(5000);
					select(webdriver,"medSchool","1");
					webdriver.findElement(By.id("regyeargrad")).sendKeys("2008");
					webdriver.findElement(By.id("regyear")).sendKeys("1983");
					if(webdriver.findElement(By.name("licenseNumber")).isDisplayed())
						webdriver.findElement(By.name("licenseNumber")).sendKeys("123456");
					break;
				case healthbusinessadministration:
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id("occupation")));
					randSelectGen(webdriver,"occupation");
					
					break;
				case medicalstudent:
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id("specialty")));
					if(!value.equalsIgnoreCase("")){
						Select select=new Select(webdriver.findElement(By.id("specialty")));
						select.selectByVisibleText(value);
					}else{
						randSelectGen(webdriver,"specialty");
					}
					if ((webdriver.findElement(By.id("specialty")).getAttribute("value"))== "339" || (webdriver.findElement(By.id("specialty")).getAttribute("value"))=="340")
						randSelectGen(webdriver,"subSpecialty");
					randSelectGen(webdriver,"medSchoolState");
					if ((webdriver.findElement(By.id("medSchoolState")).getAttribute("value"))== "00")
					{
						wait.until(ExpectedConditions.presenceOfElementLocated(By.id("medSchoolCountry")));
						randSelectGen(webdriver,"medSchoolCountry");
					}
					Thread.sleep(5000);
					wait.withTimeout(4, TimeUnit.SECONDS);
					randSelectGen(webdriver,"medSchool");
					webdriver.findElement(By.id("regyeargrad")).sendKeys("2015");
					webdriver.findElement(By.id("regyear")).sendKeys("1984");
					break;
				case nurseadvancedpracticenurse:
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id("specialty")));
					Select nurse = new Select(webdriver.findElement(By.id("occupation")));
					nurse.selectByVisibleText(Profession.split("::")[2].trim());
//					randSelectGen(webdriver,"occupation");
					if(!value.equalsIgnoreCase("")){
						Select select=new Select(webdriver.findElement(By.id("specialty")));
						select.selectByVisibleText(value);
					}else{
						randSelectGen(webdriver,"specialty");
					}
					if ((webdriver.findElement(By.id("specialty")).getAttribute("value"))== "339" || (webdriver.findElement(By.id("specialty")).getAttribute("value"))=="340")
						randSelectGen(webdriver,"subSpecialty");
					webdriver.findElement(By.id("regyear")).sendKeys("1984");
					break;
				case otherhealthcareprovider:
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id("occupation")));
					Select ohcp = new Select(webdriver.findElement(By.id("occupation")));
					ohcp.selectByVisibleText(Profession.split("::")[2].trim());
//					randSelectGen(webdriver,"occupation");
					break;
				case pharmacist:
					
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id("specialty")));
					randSelectGen(webdriver,"degree");
					if(!value.equalsIgnoreCase("")){
						Select select=new Select(webdriver.findElement(By.id("specialty")));
						select.selectByVisibleText(value);
					}else{
						randSelectGen(webdriver,"specialty");
					}
					if ((webdriver.findElement(By.id("specialty")).getAttribute("value"))== "339" || (webdriver.findElement(By.id("specialty")).getAttribute("value"))=="340")
						randSelectGen(webdriver,"subSpecialty");
					randSelectGen(webdriver,"practiceSetting");
					break;
				case physicianassistant:
					
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id("specialty")));
					if(!value.equalsIgnoreCase("")){
						Select select=new Select(webdriver.findElement(By.id("specialty")));
						select.selectByVisibleText(value);
					}else{
						randSelectGen(webdriver,"specialty");
					}
					if ((webdriver.findElement(By.id("specialty")).getAttribute("value"))== "339" || (webdriver.findElement(By.id("specialty")).getAttribute("value"))=="340")
						randSelectGen(webdriver,"subSpecialty");
						webdriver.findElement(By.id("regyear")).sendKeys("1984");
					break;	
					
					case dentistoralhealthprofessional:
					
					wait.until(ExpectedConditions.visibilityOf(webdriver.findElement(By.id("occupation"))));
					wait.withTimeout(2, TimeUnit.SECONDS);
					//select(webdriver,"occupation",value);//
					if(!value.equalsIgnoreCase("")){
						Select select=new Select(webdriver.findElement(By.id("occupation")));
						select.selectByVisibleText(value);
					}else{
						randSelectGen(webdriver,"occupation");
					}
					System.out.println("selected Occupation:"+(webdriver.findElement(By.id("occupation")).getAttribute("value")));
					//System.out.println("selected Occupation:"+(webdriver.findElement(By.id("occupation")).getAttribute("Name")));
					wait.withTimeout(2, TimeUnit.SECONDS);
					//if(value.contentEquals("1"))
					if((webdriver.findElement(By.id("occupation")).getAttribute("value")).matches("19"))
					{  
						System.out.println("( Occupation: Dentist");
						wait.withTimeout(2, TimeUnit.SECONDS);
						randSelectGen(webdriver,"specialty");
						webdriver.findElement(By.id("regyeargrad")).sendKeys("2008");
						webdriver.findElement(By.id("regyear")).sendKeys("1988");
					}	
						
						Thread.sleep(200);
					
					break;
					
					
				case optometrist:
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id("occupation")));
					wait.withTimeout(2, TimeUnit.SECONDS);
					if(!value.equalsIgnoreCase("")){
						Select select=new Select(webdriver.findElement(By.id("occupation")));
						select.selectByVisibleText(value);
					}else{
						randSelectGen(webdriver,"specialty");
					}
					randSelectGen(webdriver,"practiceSetting");
					webdriver.findElement(By.id("regyear")).sendKeys("1984");
					
					break;
	
				case psychologist:
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id("profession")));
					wait.withTimeout(2, TimeUnit.SECONDS);
					if(!value.equalsIgnoreCase("")){
						Select select=new Select(webdriver.findElement(By.id("occupation")));
						select.selectByVisibleText(value);
					}else{
						randSelectGen(webdriver,"specialty");
					}
					randSelectGen(webdriver,"practiceSetting");
					webdriver.findElement(By.id("regyear")).sendKeys("1984");
					break;
				}
				//wait.withTimeout(4, TimeUnit.SECONDS);
//				Thread.sleep(7000);
				if(!webdriver.findElement(By.id("remembermecheck")).isSelected())
				{//reason for including this : after entering all details in the register page "SUBMIT" button is not getting enabled 
				webdriver.findElement(By.id("remembermecheck")).click();
				}
				else
				{
					webdriver.findElement(By.id("remembermecheck")).click();
					webdriver.findElement(By.id("remembermecheck")).click();
				}
				System.out.println(webdriver.findElement(By.id("submitreg")).isEnabled());
					Actions act=new Actions(webdriver);
					webdriver.findElement(By.id("submitreg")).click();
					//act.click(webdriver.findElement(By.id("submitreg"))).perform();
//					Thread.sleep(10000);
				webdriver.findElement(By.partialLinkText("Continue")).click();	
				if(webdriver.findElement(By.id("searchbtn")).isDisplayed()) {
					resultDetails.setFlag(true);
					resultDetails.setErrorMessage("");
				}
				resultDetails.setWarningMessage("Username :: "+username+" Password :: 123456");
				System.out.println(resultDetails.getWarningMessage());
				if (webdriver.getCurrentUrl().contains(".qa00.")||webdriver.getCurrentUrl().contains(".qa01.")||webdriver.getCurrentUrl().contains(".qa02.")) {
					OracleDB oracle = new OracleDB();
					oracle.oracleVerifyValue("select LOGIN_NAME from REG_USER where LOGIN_NAME='"+username+"'", username);
				}
				return resultDetails;
			} catch(Throwable e){
				e.printStackTrace();
				ResultDetails resultDetails = new ResultDetails();
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Registration Failed: "+e.getMessage());
				return resultDetails;
			}
		}
}