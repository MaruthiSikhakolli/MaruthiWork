package com.java;

import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class ADTag {

	int count = 0;
	
	public HashMap<String,String> actual = new HashMap<String,String>();
	
	public void moveToProfPage(WebDriver webdriver) {
		Actions act = new Actions(webdriver);
		webdriver.manage().window().maximize();
		act.moveToElement(webdriver.findElement(By.cssSelector("div#rightlinksetting"))).perform();
		webdriver.findElement(By.xpath("//div[@id='headerrightlinkdd']/a[2]")).click();
		webdriver.findElement(By.linkText("Professional/Contact Information")).click();
		webdriver.findElement(By.linkText("Content Preference")).click();
		SeleniumDriverTest.HM.put("ssp", webdriver.findElement(By.id("homePageId")).getAttribute("value"));
		webdriver.findElement(By.partialLinkText("Professional Information")).click();
		SeleniumDriverTest.HM.put("pf", webdriver.findElement(By.id("profession")).getAttribute("value"));
	}

	public String putNewProf(WebDriver webdriver,String fieldText) {
		String msg = "";
		SeleniumDriverTest.HM.put("pub",fieldText);
		try{
		if ((SeleniumDriverTest.HM.get("pf") == "13") || (SeleniumDriverTest.HM.get("pf") == "12")	|| (SeleniumDriverTest.HM.get("pf") == "15")) {
			SeleniumDriverTest.HM.put("occ",webdriver.findElement(By.id("occupation")).getAttribute("value"));
		} else {
			SeleniumDriverTest.HM.put("occ", "0");
		}
		try{
			SeleniumDriverTest.HM.put("usp", webdriver.findElement(By.id("specialty")).getAttribute("value"));
		}catch(Exception e){}
		
		try{
			SeleniumDriverTest.HM.put("ct", webdriver.findElement(By.id("country")).getAttribute("value").toLowerCase());
		}catch(Exception e){}
		
		
		try{
			SeleniumDriverTest.HM.put("st", webdriver.findElement(By.id("state")).getAttribute("value").toLowerCase());
		}catch(Exception e){}
		
		
		}catch(Exception e){
			msg = e.getMessage();
		}
		SeleniumDriverTest.HM.put("leaf", "0");
		SeleniumDriverTest.HM.put("bc", "__");
		SeleniumDriverTest.HM.put("auth", "1");
		SeleniumDriverTest.HM.put("scg", "0");
		SeleniumDriverTest.HM.put("inst", "0");
		return msg;
		
	}

	public String putDynamic(WebDriver webdriver, String pos) {
		String Message = "";
		try {
			pos = "pos="+pos.replace("pos", "");
			String pgSource = webdriver.getPageSource();
			String pgURL = webdriver.getCurrentUrl();

			SeleniumDriverTest.HM.put("pos",pos.replace("pos=", ""));
			if (pgSource.contains("Search Results"))
				SeleniumDriverTest.HM.put("kw",
						webdriver.findElement(By.id("searchtextinput"))
								.getAttribute("value"));
			else
				SeleniumDriverTest.HM.put("kw", "0");
			
			if (pgURL.contains("com")) {
				SeleniumDriverTest.HM.put("site", "1");
				if (pgURL.contains("boards"))
					SeleniumDriverTest.HM.put("site", "4");
			} else if (pgURL.contains("org"))
				SeleniumDriverTest.HM.put("site", "3");
			else if (pgURL.contains("medscapemedizin"))
				SeleniumDriverTest.HM.put("site", "5");
			
			if (pgURL.contains("viewarticle")) {
				SeleniumDriverTest.HM.put("artid", pgURL.split("viewarticle/")[1]);
				SeleniumDriverTest.HM.put("pclass", "content");
			} else if (pgURL.contains("viewprogram")) {
				SeleniumDriverTest.HM.put("artid", pgURL.split("viewprogram/")[1]);
				SeleniumDriverTest.HM.put("pclass", "content");
			} else if (webdriver.getCurrentUrl().contains("viewcollection")) {
				SeleniumDriverTest.HM.put("pclass", "content");
				SeleniumDriverTest.HM.put("artid",
						pgURL.split("viewcollection/")[1]);
			} else if (pgURL.contains("viewpublication"))
			{
				SeleniumDriverTest.HM.put("pclass", "pubpage");
				SeleniumDriverTest.HM.put("artid", "0");
			}
			else if (pgURL.contains("/index/list"))
			{
				SeleniumDriverTest.HM.put("pclass", "indexpage");
				SeleniumDriverTest.HM.put("artid", "0");
			}
			else if(pgURL.contains("drug")){
				String s[] = pgURL.substring(pgURL.indexOf("/drug/")).split("-");
				SeleniumDriverTest.HM.put("artid","drg"+s[s.length - 1 ].trim());
				SeleniumDriverTest.HM.put("pclass", "content");
			}else
			{
				SeleniumDriverTest.HM.put("pclass", "hp");
				SeleniumDriverTest.HM.put("artid", "0");
			}

			String strPageSource = pgSource;
			strPageSource = strPageSource.replaceAll("%3D", "");
			strPageSource = strPageSource.replaceAll("&amp", "");
			String override = strPageSource.split(pos.trim())[0];
			override = override.substring(
					override.lastIndexOf("Adspace debug"), override.length());
			override = override.split("-->")[0];
			override = override.substring(override.indexOf(";"),
					override.length());
			if (override.contains(";"))
				override = override.replaceFirst(";", "");
			String overrideval = override.split(";")[0];
			String refpath = override.split(";")[1];
			overrideval = overrideval.split("=")[1].trim();
			System.out.println(overrideval);
			refpath = refpath.split("=")[1].trim();
			if (overrideval == "0")
				SeleniumDriverTest.HM.put("spon", refpath);
			else
				SeleniumDriverTest.HM.put("spon", overrideval);
			if ((SeleniumDriverTest.HM.get("pf") == "13")
					|| (SeleniumDriverTest.HM.get("pf") == "12")
					|| (SeleniumDriverTest.HM.get("pf") == "15")) {
				SeleniumDriverTest.HM.put("occ",
						webdriver.findElement(By.id("occupation"))
								.getAttribute("value"));
			}
			String cururl = webdriver.getCurrentUrl();
			if (cururl.contains("qa00")){
				cururl = cururl.replaceAll("qa00", "");
				SeleniumDriverTest.HM.put("env", "1");
			}
			else if (cururl.contains("qa02")){
				cururl = cururl.replaceAll("qa02", "");
				SeleniumDriverTest.HM.put("env", "1");
			}
			else if (cururl.contains("qa01")){
				cururl = cururl.replaceAll("qa01", "");
				SeleniumDriverTest.HM.put("env", "1");
			}
			else if (cururl.contains("perf")){
				cururl = cururl.replaceAll("perf", "");
				SeleniumDriverTest.HM.put("env", "1");
			}else{
				SeleniumDriverTest.HM.put("env", "0");
			}

			if (cururl.contains("www.medscape.com")) {
				SeleniumDriverTest.HM.put("affiliate", "1");
			} else if (cururl.contains("boards.medscape.com")) {
				SeleniumDriverTest.HM.put("affiliate", "7");
			} else if (cururl.contains("profreg.medscape.com")) {
				SeleniumDriverTest.HM.put("affiliate", "1");
			} else if (cururl.contains("search.medscape.com")) {
				SeleniumDriverTest.HM.put("affiliate", "1");
			} else if (cururl.contains("webmail2.medscape.com")) {
				SeleniumDriverTest.HM.put("affiliate", "1");
			} else if (cururl.contains("mail.medscape.com")) {
				SeleniumDriverTest.HM.put("affiliate", "1");
			} else if (cururl.contains("www.emedicine.com")) {
				SeleniumDriverTest.HM.put("affiliate", "2");
			} else if (cururl.contains("emedicine.medscape.com")) {
				SeleniumDriverTest.HM.put("affiliate", "2");
			} else if (cururl.contains("www.theheart.org")) {
				SeleniumDriverTest.HM.put("affiliate", "4");
			} else if (cururl.contains("www.medscape.org")) {
				SeleniumDriverTest.HM.put("affiliate", "2003");
			} else if (cururl.contains("reference.medscape.com")) {
				SeleniumDriverTest.HM.put("affiliate", "2");
			} else if (cururl.contains("www.medscapemedizin.de")) {
				SeleniumDriverTest.HM.put("affiliate", "8");
			} else if (cururl.contains("praxis.medscapemedizin.de")) {
				SeleniumDriverTest.HM.put("affiliate", "9");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message = e.getMessage();
		}
		return Message;
	}

	public String VerifyADTagValues(WebDriver wd, String pos) {

		String MisMatches = "";
		try {
			
			pos = "pos="+pos.replace("pos", "");
			
			FillExcelColor FX = new FillExcelColor();

			TopBottom(wd, pos);
			
			HSSFRow row = FX.createRow();

			Object obj[] = actual.keySet().toArray();
			
			String keys[] = new String[obj.length];
			int i =0;
			for(Object o : obj){
				keys[i++] = o.toString();
			}
			
			Arrays.sort(keys);
			
			for (String Key : keys) {
				String strActual = actual.get(Key);
				String strExpected = SeleniumDriverTest.HM.get(Key);
						
				if(strExpected == null){
					strExpected = "";
				}
			if((Key.toString().trim().equalsIgnoreCase("tid")) ||(Key.toString().trim().equalsIgnoreCase("tile")) ||(Key.toString().trim().equalsIgnoreCase("cg")))	
			{
				FX.writePassCell(row, Key+"::"+strActual);
			}			
			else
				{
				if(strActual.equalsIgnoreCase(strExpected))
					FX.writePassCell(row, Key+"::"+strActual);
				else{
					FX.writeFailCell(row, Key+"::"+strActual);
					MisMatches = MisMatches + "\nExpected value is "+ Key +"::"+ strExpected + "\n" + "Actual value is " + Key +"::"+ strActual + "\n";
				}}
			}
			FX.flush();
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return MisMatches;
	}

	public void TopBottom(WebDriver wd, String pos) {
		String strPageSource = wd.getPageSource();
		strPageSource = strPageSource.replaceAll("%3D", "=");
		strPageSource = strPageSource.replaceAll("&amp", "");
		
		String src ="";
		
		try{
			src = strPageSource.split(pos)[1];
		}catch(Exception e){
			System.out.println("Null Pointer");
		}
		if (src.contains("<"))
			src = src.split("<")[0];
		if (src.contains("script"))
			src = src.split("script")[0];
		if (src.contains("--->"))
			src = src.replaceAll("--->", "");
		if (src.contains("\""))
			src = src.split("\"")[0];
		if (src.contains("-->"))
			src = src.replaceAll("-->", "");
		src =  pos+src;
		String str[] = src.trim().split(";");
			for(String s : str){
				String keyval[] = s.split("=");
				try{
					actual.put(keyval[0], keyval[1]);
				}catch(Exception e){
					
				}
			}
	}

}
