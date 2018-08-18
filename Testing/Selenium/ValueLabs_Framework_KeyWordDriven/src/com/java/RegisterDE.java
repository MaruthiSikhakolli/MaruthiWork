package com.java;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.lang.RandomStringUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import com.java.Objects.ResultDetails;

public class RegisterDE {

	public static String randNumGen(int i) {
		return RandomStringUtils.randomNumeric(i);
	}

	public static String randNameGen(int i) {
		return RandomStringUtils.randomAlphabetic(i).toLowerCase();
	}
	public static String randUserGen() {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmm");
		String strUser = ft.format(dNow);
		return strUser;
	}
	public static void waitUntil(WebDriver webdriver,String id)
	{
		WebDriverWait wait = new WebDriverWait(webdriver, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
	}
	
	public static void selectWebListRandom(WebDriver webdriver,String id)
	{
		Select select=new Select(webdriver.findElement(By.id(id)));
		Random rd=new Random();
		int index=rd.nextInt(select.getOptions().size());
		if(index==0)
			index=index+1;
		select.selectByIndex(index);
		
		if(select.getFirstSelectedOption().getText().contains("Sonstiges Fachgebiet")|| select.getFirstSelectedOption().getText().contains("Nicht-klinisch"))
			selectWebListRandom(webdriver,"subSpecialty");
	}
	
	public static void selectSpeciality(WebDriver webdriver, String value, String locator) {
		if(!value.equalsIgnoreCase("")){
			Select select=new Select(webdriver.findElement(By.id(locator)));
			select.selectByVisibleText(value);	
			if(select.getFirstSelectedOption().getText().contains("Sonstiges Fachgebiet")|| select.getFirstSelectedOption().getText().contains("Nicht-klinisch"))
				selectWebListRandom(webdriver,"subSpecialty");
		}else{
			selectWebListRandom(webdriver,"specialty");
		}
	}
	
	public static void selectWebListByIndex(WebDriver webdriver,String id,int index)
	{

		Select select=new Select(webdriver.findElement(By.id(id)));
		select.selectByIndex(index);
	}	
	
	public enum Profession {
		apotheker, arzt, krankenschwesterkrankenpfleger, medizinischerfachangestellter, medizinjournalistin, mitarbeiterinmedizinischerinstitutionoderpharmaindustrie
		, optiker, psychologe, sonstigegesundheitsdienstleister, student, zahnarztzahnmedizinischerfachangestellter
		, sonstige
	};
	
	public ResultDetails Register(WebDriver webdriver,String strProfession, String value){
		String strField="";
		ResultDetails resultDetails = new ResultDetails();

//		webdriver.get("http://www.medscapemedizin.de");
		webdriver.findElement(By.linkText("Registrieren")).click();	
	
		String strCoun[]=new String[5];

		strCoun=value.split("::");

		//Removing spaces and special symbols from fieldText
		strField = strProfession.replace(" ", "");
		strField = strField.replace("/", "");
		strField = strField.toLowerCase();
		System.out.println(strField);
		try{Thread.sleep(3000);}catch(Exception e){}
	selectWebListRandom(webdriver,"anrede");
	webdriver.findElement(By.id("regfnamefield")).sendKeys(randNameGen(5));
	webdriver.findElement(By.id("reglnamefield")).sendKeys(randNameGen(5));
	selectWebListRandom(webdriver,"titel");
	String strRandUser=randUserGen()+randNameGen(3);
	String strEmail=strRandUser+"@"+strRandUser+".com";
	webdriver.findElement(By.id("regemailaddress")).sendKeys(strEmail);
	webdriver.findElement(By.id("regemailaddressconfirm")).sendKeys(strEmail);
	String username = strRandUser;
	webdriver.findElement(By.id("regusername")).sendKeys(username);
	webdriver.findElement(By.id("regpassword")).sendKeys("123456");
	webdriver.findElement(By.id("regpasswordconfirm")).sendKeys("123456");
	//System.out.println(strCoun[3]);
	if(!strCoun[3].equalsIgnoreCase(""))
	{ Select select = new Select(webdriver.findElement(By.id("hintQuestion")));
	 //System.out.println(strCoun[3]);
	 select.selectByVisibleText(strCoun[3]);
	 //System.out.println(strCoun[3]);
		
	}
	else 
	{
		selectWebListRandom(webdriver, "hintQuestion");
	}
	webdriver.findElement(By.id("reghintanswer")).sendKeys(randNameGen(6));

	if (strCoun[0].equalsIgnoreCase("Deutschland")) {

		webdriver.findElement(By.id("chooseCountrya")).click();
		selectWebListRandom(webdriver, "germanyState");
		Profession prf = Profession.valueOf(strField);
		Select select = new Select(webdriver.findElement(By
				.id("profession")));
		switch (prf) {
		case apotheker:
			// selectWebListByIndex(webdriver,"profession",1);
			select = new Select(webdriver.findElement(By.id("profession")));
			select.selectByVisibleText(strProfession);
			waitUntil(webdriver, "practiceSetting");
			selectSpeciality(webdriver, strCoun[1], "specialty");
			select = new Select(webdriver.findElement(By.id("specialty")));
			System.out.println(select.getFirstSelectedOption().getText());
			if(!strCoun[4].equalsIgnoreCase(""))
			{
				Select select1 = new Select(webdriver.findElement(By.id("practiceSetting")));
			    select1.selectByVisibleText(strCoun[4]);}
			else
			{
				selectWebListRandom(webdriver, "practiceSetting");				 
				
			}
			break;
		case arzt:
			select = new Select(webdriver.findElement(By.id("profession")));
			select.selectByVisibleText(strProfession);
			waitUntil(webdriver, "practiceSetting");
			if (strCoun[2].equalsIgnoreCase("111111111111119"))
			{
				if (webdriver.findElement(By.id("efn")).isDisplayed()) 
				{
					webdriver.findElement(By.id("efn")).sendKeys(strCoun[2]);
				}
			}
			else if (strCoun[2].equalsIgnoreCase("123456612")) 
			{
				webdriver.findElement(By.id("noEfn")).click();	
				if (webdriver.findElement(By.id("lanr")).isDisplayed()) 
				{
					webdriver.findElement(By.id("lanr")).sendKeys(strCoun[2]);
				}
			} 
			else 
			{
				webdriver.findElement(By.id("noEfn")).click();
				webdriver.findElement(By.id("noEfn")).click();						
				if (webdriver.findElement(By.id("lanr")).isDisplayed())
				{
					webdriver.findElement(By.id("lanr")).sendKeys(strCoun[2]);
				}
			}
			selectSpeciality(webdriver, strCoun[1], "specialty");

			// if(webdriver.findElement(By.id("specialty")).getText().contains("Sonstiges Fachgebiet")||
			// webdriver.findElement(By.id("specialty")).getText().contains("Nicht-klinisch"))
			// selectWebListRandom(webdriver,"subSpecialty");
			if(!strCoun[4].equalsIgnoreCase(""))
			{
				Select select1 = new Select(webdriver.findElement(By.id("practiceSetting")));
			    select1.selectByVisibleText(strCoun[4]);}
			else
			{
				selectWebListRandom(webdriver, "practiceSetting");				 
				
			}
			
			break;
	case krankenschwesterkrankenpfleger:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"occupation");
		selectWebListRandom(webdriver,"occupation");
		selectSpeciality(webdriver, strCoun[1], "specialty");
//		if(webdriver.findElement(By.id("specialty")).getText().contains("Sonstiges Fachgebiet")|| webdriver.findElement(By.id("specialty")).getText().contains("Nicht-klinisch"))
//			selectWebListRandom(webdriver,"subSpecialty");
		break;	
	case medizinischerfachangestellter:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"specialty");
		selectSpeciality(webdriver, strCoun[1], "specialty");
//		if(webdriver.findElement(By.id("specialty")).getText().contains("Sonstiges Fachgebiet")|| webdriver.findElement(By.id("specialty")).getText().contains("Nicht-klinisch"))
//			selectWebListRandom(webdriver,"subSpecialty");
		break;	
	case medizinjournalistin:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		break;
	case mitarbeiterinmedizinischerinstitutionoderpharmaindustrie:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"occupation");
		selectWebListRandom(webdriver,"occupation");
		break;
	case optiker:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"occupation");
		selectWebListRandom(webdriver,"occupation");
		if(!strCoun[4].equalsIgnoreCase(""))
		{
			Select select1 = new Select(webdriver.findElement(By.id("practiceSetting")));
		    select1.selectByVisibleText(strCoun[4]);}
		else
		{
			selectWebListRandom(webdriver, "practiceSetting");				 
			
		}
		break;
	case psychologe:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"occupation");
		selectWebListRandom(webdriver,"occupation");
		if(!strCoun[4].equalsIgnoreCase(""))
		{
			Select select1 = new Select(webdriver.findElement(By.id("practiceSetting")));
		    select1.selectByVisibleText(strCoun[4]);}
		else
		{
			selectWebListRandom(webdriver, "practiceSetting");				 
			
		}
		break;
	case sonstigegesundheitsdienstleister:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"occupation");
		selectWebListRandom(webdriver,"occupation");
		break;
	case student:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"specialty");
		selectSpeciality(webdriver, strCoun[1], "specialty");
//		if(webdriver.findElement(By.id("specialty")).getText().contains("Sonstiges Fachgebiet")|| webdriver.findElement(By.id("specialty")).getText().contains("Nicht-klinisch"))
//			selectWebListRandom(webdriver,"subSpecialty");
		break;
	case zahnarztzahnmedizinischerfachangestellter:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"occupation");
		selectWebListRandom(webdriver,"occupation");
		Select select2 =new Select(webdriver.findElement(By.id("occupation")));
		if (select2.getFirstSelectedOption().getText().equalsIgnoreCase("Zahnarzt")) {
			selectSpeciality(webdriver, strCoun[1], "specialty");
		}
		//selectWebListRandom(webdriver,"specialty");
//		if(webdriver.findElement(By.id("specialty")).getText().contains("Sonstiges Fachgebiet")|| webdriver.findElement(By.id("specialty")).getText().contains("Nicht-klinisch"))
//			selectWebListRandom(webdriver,"subSpecialty");
		break;
	case sonstige:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		break;		
	}
}
 else 
 {
	 webdriver.findElement(By.id("chooseCountryb")).click();
	 waitUntil(webdriver,"country");
	 if(strCoun[0].equalsIgnoreCase("Sonstiges")||strCoun[0].equalsIgnoreCase(""))
		 selectWebListRandom(webdriver,"country");
	 else 
	 {
		 Select select = new Select(webdriver.findElement(By.id("country")));
			select.selectByVisibleText(strCoun[0]);
 	//selectSpeciality(webdriver, strCoun[1], "country");
 	
	
	 }
	Profession prf = Profession.valueOf(strField);
	Select select=new Select(webdriver.findElement(By.id("profession")));
	
	switch(prf)
	{
	case apotheker:
		//selectWebListByIndex(webdriver,"profession",1);
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"practiceSetting");
		selectSpeciality(webdriver, strCoun[1], "specialty");
		select  = new Select(webdriver.findElement(By.id("specialty")));
		System.out.println(select.getFirstSelectedOption().getText());
		
		if(!strCoun[4].equalsIgnoreCase(""))
		{
			Select select1 = new Select(webdriver.findElement(By.id("practiceSetting")));
		    select1.selectByVisibleText(strCoun[4]);}
		else
		{
			selectWebListRandom(webdriver, "practiceSetting");				 
			
		}
		break;
	case arzt:
		//selectWebListByIndex(webdriver,"profession",1);
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"practiceSetting");
		selectSpeciality(webdriver, strCoun[1], "specialty");
		select  = new Select(webdriver.findElement(By.id("specialty")));
		System.out.println(select.getFirstSelectedOption().getText());
		if(strCoun[4].equalsIgnoreCase(""))
		{
			selectWebListRandom(webdriver, "practiceSetting");				 
		}
		else
		{
			Select select1 = new Select(webdriver.findElement(By.id("practiceSetting")));
		    select1.selectByVisibleText(strCoun[4]);
		}
		break;
	case krankenschwesterkrankenpfleger:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"occupation");
		selectWebListRandom(webdriver,"occupation");
		selectSpeciality(webdriver, strCoun[1], "specialty");
//		if(webdriver.findElement(By.id("specialty")).getText().contains("Sonstiges Fachgebiet")|| webdriver.findElement(By.id("specialty")).getText().contains("Nicht-klinisch"))
//			selectWebListRandom(webdriver,"subSpecialty");
		break;	
	case medizinischerfachangestellter:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"specialty");
		selectSpeciality(webdriver, strCoun[1], "specialty");
//		if(webdriver.findElement(By.id("specialty")).getText().contains("Sonstiges Fachgebiet")|| webdriver.findElement(By.id("specialty")).getText().contains("Nicht-klinisch"))
//			selectWebListRandom(webdriver,"subSpecialty");
		break;	
	case medizinjournalistin:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		break;
	case mitarbeiterinmedizinischerinstitutionoderpharmaindustrie:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"occupation");
		selectWebListRandom(webdriver,"occupation");
		break;
	case optiker:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"occupation");
		selectWebListRandom(webdriver,"occupation");
		if(!strCoun[4].equalsIgnoreCase(""))
		{
			Select select1 = new Select(webdriver.findElement(By.id("practiceSetting")));
		    select1.selectByVisibleText(strCoun[4]);}
		else
		{
			selectWebListRandom(webdriver, "practiceSetting");				 
			
		}
		break;
	case psychologe:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"occupation");
		selectWebListRandom(webdriver,"occupation");
		if(!strCoun[4].equalsIgnoreCase(""))
		{
			Select select1 = new Select(webdriver.findElement(By.id("practiceSetting")));
		    select1.selectByVisibleText(strCoun[4]);}
		else
		{
			selectWebListRandom(webdriver, "practiceSetting");				 
			
		}
		break;
	case sonstigegesundheitsdienstleister:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"occupation");
		selectWebListRandom(webdriver,"occupation");
		break;
	case student:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"specialty");
		selectSpeciality(webdriver, strCoun[1], "specialty");
//		if(webdriver.findElement(By.id("specialty")).getText().contains("Sonstiges Fachgebiet")|| webdriver.findElement(By.id("specialty")).getText().contains("Nicht-klinisch"))
//			selectWebListRandom(webdriver,"subSpecialty");
		break;
	case zahnarztzahnmedizinischerfachangestellter:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		waitUntil(webdriver,"occupation");
		selectWebListRandom(webdriver,"occupation");
		Select select2 =new Select(webdriver.findElement(By.id("occupation")));
		if (select2.getFirstSelectedOption().getText().equalsIgnoreCase("Zahnarzt")) {
			selectSpeciality(webdriver, strCoun[1], "specialty");
		}
		//selectWebListRandom(webdriver,"specialty");
//		if(webdriver.findElement(By.id("specialty")).getText().contains("Sonstiges Fachgebiet")|| webdriver.findElement(By.id("specialty")).getText().contains("Nicht-klinisch"))
//			selectWebListRandom(webdriver,"subSpecialty");
		break;
	case sonstige:
		select=new Select(webdriver.findElement(By.id("profession")));
		select.selectByVisibleText(strProfession);
		break;	}
	}
 
	try {Thread.sleep(3000);}catch(Exception e){}
	webdriver.findElement(By.id("termsofuses")).click();
	webdriver.findElement(By.id("submitreg")).click();
	if(webdriver.findElement(By.id("regpost_continuebtn")).isDisplayed())
	{
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");
	}
	resultDetails.setWarningMessage("Username :: "+username+" Password :: 123456");
	WebDriverUtils.waitForPageToLoad(webdriver, "50000");
	return resultDetails;
	}
	}

