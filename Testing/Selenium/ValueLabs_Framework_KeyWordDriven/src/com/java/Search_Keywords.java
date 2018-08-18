package com.java;

import java.util.*;
import java.text.*;

import com.java.Objects.ResultDetails;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class Search_Keywords extends TestType {
	public static int j;
	public WebDriver webdriver = null;
	ResultDetails resultDetails = new ResultDetails();
	
	public Search_Keywords(WebDriver webdriver) {
		this.webdriver = webdriver;
	}

	public enum EnterDataFileds {
		RAC, ACM, MCC, FTS, RFS, MDS, MFS, RDS, FSO, LOA, CTS, SAC, MST, WST, HTL, SBS, VTL, SRV
	}; // EDT is Text Editor
	
	// Keywords
	public enum ActionTypes {
		SEARCH
	};
	
	public ResultDetails performAction(String fieldText, String value,
			String actionType, String fieldName) {
		try {
			ActionTypes actTypes = ActionTypes
					.valueOf(actionType.toUpperCase());
			switch (actTypes) {
			case SEARCH:
				resultDetails = search(webdriver, fieldText, value, fieldName);
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
	String title="";
	public ResultDetails articleclick(WebDriver webdriver)
			throws InterruptedException {
		try {
			List<WebElement> li = (List<WebElement>) webdriver.findElements(By
					.xpath("//div[@class='searchresults']/ul/li"));
			if (li.size() == 10) {
				for (int i = 0; i <= 3; i++) {
					int a = getRandom(li.size());
					String ltitle9 = webdriver
							.findElement(
									By.xpath("//div[@class='searchresults']/ul/li["
											+ a + "]/a")).getText().toString();
					webdriver.findElement(
							By.xpath("//div[@class='searchresults']/ul/li[" + a
									+ "]/a")).click();
					Thread.sleep(10000);
					try {
						if (webdriver.findElement(
								By.xpath("//div[@id='invitestopright']/a"))
								.isDisplayed()) {
							webdriver.findElement(
									By.xpath("//div[@id='invitestopright']/a"))
									.click();
						}
					} catch (Exception e) {
					}
					title=ltitle9;
					resultDetails=titleverify(webdriver, title);
					if(!resultDetails.getFlag()){
						return resultDetails;
					}
								}
			} else {
				for (int i = 0; i <= 1; i++) {
					int a = getRandom(li.size());
					String ltitle8 = webdriver.findElement(
							By.xpath("//div[@class='searchresults']/ul/li[" + a
									+ "]/a")).getText();
					webdriver.findElement(
							By.xpath("//div[@class='searchresults']/ul/li[" + a
									+ "]/a")).click();
					Thread.sleep(10000);
					try {
						if (webdriver.findElement(
								By.xpath("//div[@id='invitestopright']/a"))
								.isDisplayed()) {
							webdriver.findElement(
									By.xpath("//div[@id='invitestopright']/a"))
									.click();
						}
					} catch (Exception e) {
					}
					title=ltitle8;
					resultDetails=titleverify(webdriver, title);
					if(!resultDetails.getFlag()){
						return resultDetails;
					}
					
				}

			}

			return resultDetails;

		} catch (Exception e) {
			resultDetails.setFlag(false);
			System.out.println(e.getMessage());
			resultDetails.setErrorMessage("unable to click the article");
			return resultDetails;

		}
	}

	  public ResultDetails titleverify(WebDriver webdriver,String title) throws InterruptedException{
	    	try{
	    	 if(webdriver.getPageSource().contains(title)==true)
	     	{
	     		webdriver.navigate().back();
	     		Thread.sleep(10000);
	     		resultDetails.setFlag(true);
	     	}
	     	else{
	        		if(webdriver.getPageSource().contains("Pre-Activity Questionnaire")){
	        			webdriver.navigate().back();
	        			Thread.sleep(5000);
	        			resultDetails.setFlag(true);
	        		}else{
	        			if(title.contains(webdriver.getTitle())){
	        				webdriver.navigate().back();
	        				Thread.sleep(5000);
	        				resultDetails.setFlag(true);
	        			}else{
	        				 if(title.startsWith(webdriver.getTitle().substring(0, webdriver.getTitle().indexOf(" ")))) {
	        					webdriver.navigate().back();
	        					Thread.sleep(10000);
	        					resultDetails.setFlag(true);
	        					}else{
	        						if(webdriver.getTitle().toLowerCase().contains("oncology")){
	        							webdriver.navigate().back();
	                					Thread.sleep(10000);
	                					resultDetails.setFlag(true);
	        						}else{
	        							if(webdriver.findElement(By.xpath("//*[@id='searchdbtext']")).getText().toString().contains("News")){
	        								if(webdriver.findElement(By.xpath("//div[@id='articleHead']/h1")).getText().toString().toLowerCase().contains(title.toLowerCase())){
	        									webdriver.navigate().back();
	                        					Thread.sleep(10000);
	                        					resultDetails.setFlag(true);
	        								}else{
	        									System.out.println("title not verified for "+title);
	                 	     				resultDetails.setErrorMessage("Title not verified for "+title);
	                 	     				resultDetails.setFlag(false);
	                 	     				if(webdriver.getTitle().toLowerCase().contains("page not found")){
	                 	     					resultDetails.setErrorMessage("page not found for "+title);	
	                 	     				}
	                 	     				return resultDetails;
	        								}
	        							}else{
	        								if(webdriver.findElement(By.xpath("//*[@id='searchdbtext']")).getText().toString().contains("Reference")){
	        									if(webdriver.findElement(By.xpath("//div[@id='maincolboxdrugdbheader']/h1")).getText().toString().toLowerCase().contains(title.toLowerCase())){
	            									webdriver.navigate().back();
	                            					Thread.sleep(10000);
	                            					resultDetails.setFlag(true);
	        										
	        									}else{
	        										System.out.println("title not verified for "+title);
	                     	     				resultDetails.setErrorMessage("Title not verified for "+title);
	                     	     				resultDetails.setFlag(false);
	                     	     				if(webdriver.getTitle().toLowerCase().contains("page not found")){
	                     	     					resultDetails.setErrorMessage("page not found for "+title);	
	                     	     				}
	                     	     				return resultDetails;
	        									}
	        									
	        								}else{
	        									if(webdriver.findElement(By.xpath("//*[@id='searchdbtext']")).getText().toString().contains("Education")){
	        										if(webdriver.findElement(By.xpath("//div[@id='title_left']/h1")).getText().toString().toLowerCase().contains(title.toLowerCase())){
	                									webdriver.navigate().back();
	                                					Thread.sleep(10000);
	                                					resultDetails.setFlag(true);
	            										
	            									}else{
	            										System.out.println("title not verified for "+title);
	                         	     				resultDetails.setErrorMessage("Title not verified for "+title);
	                         	     				resultDetails.setFlag(false);
	                         	     				if(webdriver.getTitle().toLowerCase().contains("page not found")){
	                         	     					resultDetails.setErrorMessage("page not found for "+title);	
	                         	     				}
	                         	     				return resultDetails;
	            									}	
	        									}else{
	        										if(webdriver.findElement(By.xpath("//div[@id='titleblock']/h1")).getText().toString().contains("MEDLINE")){
	            										if(webdriver.findElement(By.xpath("//div[@id='articlecontent']/h2")).getText().toString().toLowerCase().contains(title.toLowerCase())){
	                    									webdriver.navigate().back();
	                                    					Thread.sleep(10000);
	                                    					resultDetails.setFlag(true);
	                										
	                									}else{
	                										System.out.println("title not verified for "+title);
	                             	     				resultDetails.setErrorMessage("Title not verified for "+title);
	                             	     				resultDetails.setFlag(false);
	                             	     				if(webdriver.getTitle().toLowerCase().contains("page not found")){
	                             	     					resultDetails.setErrorMessage("page not found for "+title);	
	                             	     				}
	                             	     				return resultDetails;
	                									}	
	        									}
	        								}
	        							}
	        						}
	        					}
	        			}
	        		}
				}
	    	}
	    	}catch(Exception e){
	    		System.out.println("title not verified for "+title);
  				resultDetails.setErrorMessage("Title not verified for "+title);
  				resultDetails.setFlag(false);
  				if(webdriver.getTitle().toLowerCase().contains("page not found")){
  					resultDetails.setErrorMessage("page not found for "+title);	
  				}
  				return resultDetails;
	    	}
			return resultDetails;
	    	
	    }
	
	public ResultDetails randomarticleclick(WebDriver webdriver)
			throws InterruptedException {

		if (webdriver.getTitle().contains("MEDLINE")) {
			Thread.sleep(2000);
			resultDetails = medlinearticleclick(webdriver);
			if (!resultDetails.getFlag()) {
				return resultDetails;
			}
		} else {
			Thread.sleep(2000);
			resultDetails = articleclick(webdriver);
			if (!resultDetails.getFlag()) {
				return resultDetails;
			}
		}

		List<WebElement> l = (List<WebElement>) webdriver.findElements(By
				.xpath("//*[@id='btmpagination']/a"));
		System.out.println(l.size());
		if (l.size() > 1) {
			for (int i = 1; i <= 1; i++) {
				Thread.sleep(2000);
				// List<WebElement>
				// l=(List<WebElement>)driver.findElements(By.xpath("//*[@id='btmpagination']"));
				webdriver
						.findElement(
								By.xpath("//div[@class='searchresults']/table/tbody/tr/td[2]/a["
										+ getRandom(l.size()) + "]")).click();
				Thread.sleep(5000);
				if (webdriver.getTitle().contains("MEDLINE")) {
					Thread.sleep(2000);
					resultDetails = medlinearticleclick(webdriver);
					if (!resultDetails.getFlag()) {
						return resultDetails;
					}
				} else {
					Thread.sleep(2000);
					resultDetails = articleclick(webdriver);
					if (!resultDetails.getFlag()) {
						return resultDetails;
					}
				}
				try {
					if (webdriver.findElement(By.partialLinkText("next "))
							.isDisplayed()) {
						try {
							webdriver.findElement(By.partialLinkText("next "))
									.click();
							resultDetails.setFlag(true);
						} catch (Exception e) {
							System.out.println("next button is not clicked");
							resultDetails.setFlag(false);
							return resultDetails;
						}
						Thread.sleep(5000);
						if (webdriver.getTitle().contains("MEDLINE")) {
							Thread.sleep(2000);
							resultDetails = medlinearticleclick(webdriver);
							if (!resultDetails.getFlag()) {
								return resultDetails;
							}
						} else {
							Thread.sleep(2000);
							resultDetails = articleclick(webdriver);
							if (!resultDetails.getFlag()) {
								return resultDetails;
							}
						}
						try {
							if (webdriver
									.findElement(By.partialLinkText(" previous"))
									.isDisplayed()) {
								webdriver
										.findElement(By.partialLinkText(" previous"))
										.click();
								resultDetails.setFlag(true);
								Thread.sleep(2000);
							}
						} catch (Exception e) {
							resultDetails.setFlag(false);
							resultDetails
									.setErrorMessage("previous button is not displayed or clicked");
							System.out
									.println("previous button is not displayed or clicked");
							return resultDetails;
						}
					}
				} catch (Exception e) {
					if (webdriver.findElement(By.partialLinkText(" previous"))
							.isDisplayed()) {
						webdriver.findElement(By.partialLinkText(" previous"))
								.click();
						Thread.sleep(2000);
					}
				}
			}
		}
		return resultDetails;

	}

	public ResultDetails medlinearticleclick(WebDriver webdriver)
			throws InterruptedException {
		try {
			List<WebElement> li = (List<WebElement>) webdriver.findElements(By
					.xpath("//div[@id='medlineresults']//a"));
			if (li.size() == 10) {
				for (int i = 0; i <= 3; i++) {
					int a = getRandom(li.size());
					int j = (a - 1);
					int k = 2 + (3 * j);
					String ltitle7 = webdriver.findElement(
							By.xpath("//div[@id='medlineresults']/div[" + k
									+ "]/div[2]/a")).getText();
					webdriver.findElement(
							By.xpath("//div[@id='medlineresults']/div[" + k
									+ "]/div[2]/a")).click();
					Thread.sleep(10000);
					try {
						if (webdriver.findElement(
								By.xpath("//div[@id='invitestopright']/a"))
								.isDisplayed()) {
							webdriver.findElement(
									By.xpath("//div[@id='invitestopright']/a"))
									.click();
						}
					} catch (Exception e) {
					}
					if (webdriver.getPageSource().contains(ltitle7) == true) {
						webdriver.navigate().back();
						Thread.sleep(10000);
						resultDetails.setFlag(true);
					} else {
						System.out.println("title not verified for " + ltitle7);
						resultDetails.setErrorMessage("Title not verified for "
								+ ltitle7);
						resultDetails.setFlag(false);
						if (webdriver.getTitle().toLowerCase()
								.contains("page not found")) {
							resultDetails.setErrorMessage("page not found for "
									+ ltitle7);
						}else{
		 					try{
		 	 					if(webdriver.findElement(By.xpath("//div[@id='articlecontent']/h2")).getText().equalsIgnoreCase("")){
		 	 					resultDetails.setErrorMessage("Article content is blank for "+ltitle7);
		 	 					System.out.println("Article content is blank for "+ltitle7);
		 	 					}
		 	 					}catch(Exception e){}}
						return resultDetails;
					}
				}

			} else {
				for (int i = 0; i <= 1; i++) {
					int a = getRandom(li.size());
					int j = (a - 1);
					int k = 2 + (3 * j);
					String ltitle6 = webdriver.findElement(
							By.xpath(" //div[@id='medlineresults']/div[" + k
									+ "]/div[2]/a")).getText();
					webdriver.findElement(
							By.xpath(" //div[@id='medlineresults']/div[" + k
									+ "]/div[2]/a")).click();
					Thread.sleep(10000);
					try {
						if (webdriver.findElement(
								By.xpath("//div[@id='invitestopright']/a"))
								.isDisplayed()) {
							webdriver.findElement(
									By.xpath("//div[@id='invitestopright']/a"))
									.click();
						}
					} catch (Exception e) {
					}
					if (webdriver.getPageSource().contains(ltitle6) == true) {
						webdriver.navigate().back();
						Thread.sleep(10000);
						resultDetails.setFlag(true);
					} else {
						System.out.println("title not verified for " + ltitle6);
						resultDetails.setErrorMessage("Title not verified for "
								+ ltitle6);
						resultDetails.setFlag(false);
						if (webdriver.getTitle().toLowerCase()
								.contains("page not found")) {
							resultDetails.setErrorMessage("page not found for "
									+ ltitle6);
						}else{
		 					try{
		 	 					if(webdriver.findElement(By.xpath("//div[@id='articlecontent']/h2")).getText().equalsIgnoreCase("")){
		 	 					resultDetails.setErrorMessage("Article content is blank for "+ltitle6);
		 	 					System.out.println("Article content is blank for "+ltitle6);
		 	 					}
		 	 					}catch(Exception e){}}
						return resultDetails;
					}
				}
			}
			return resultDetails;

		} catch (Exception e) {
			resultDetails.setFlag(false);
			System.out.println(e.getMessage());
			return resultDetails;

		}

	}

	public ResultDetails tagnamehorizontally(WebDriver webdriver) {
		try {
			List<WebElement> lr2 = (List<WebElement>) webdriver.findElements(By
					.xpath("//div[@class='searchresults']/ul/li"));
			for (int b = 1; b <= lr2.size(); b++) {
				List<WebElement> lr1 = (List<WebElement>) webdriver
						.findElements(By
								.xpath("//div[@class='searchresults']/ul/li["
										+ b + "]/div/a"));
				if (lr1.size() > 0) {
					int j = getRandom(lr1.size());
					String ss = webdriver
							.findElement(
									By.xpath("//div[@class='searchresults']/ul/li["
											+ b + "]/div/a[" + j + "]"))
							.getText().toString();
					webdriver
							.findElement(
									By.xpath("//div[@class='searchresults']/ul/li["
											+ b + "]/div/a[" + j + "]"))
							.click();
					Thread.sleep(10000);
					try {
						if (webdriver.findElement(
								By.xpath("//div[@id='invitestopright']/a"))
								.isDisplayed()) {
							webdriver.findElement(
									By.xpath("//div[@id='invitestopright']/a"))
									.click();
						}
					} catch (Exception e) {
					}
					Thread.sleep(10000);
					String st = webdriver
							.findElement(
									By.xpath("//div[@id='topic_menu']/ul/li[@class='current_topic']"))
							.getAttribute("id");
					if (webdriver
							.findElement(By.xpath("//li[@id='" + st + "']/a"))
							.getText().toString().contains(ss)) {
						resultDetails.setFlag(true);
						System.out.println("Msg found");
						return resultDetails;
					} else {
						resultDetails.setFlag(false);
						System.out.println("Msg not found");
						resultDetails.setErrorMessage("MSG not found");
						return resultDetails;
					}
				}
			}
			resultDetails.setFlag(true);
			System.out.println("horizontal tags are not present in this page");
		} catch (Exception e) {
			resultDetails.setFlag(false);
			System.out.println(e.getMessage());
			resultDetails.setErrorMessage(e.getMessage());
			return resultDetails;
		}

		return resultDetails;

	}

	public ResultDetails tagname(WebDriver webdriver) {
		try {
			List<WebElement> lr2 = (List<WebElement>) webdriver.findElements(By
					.xpath("//div[@class='searchresults']/ul/li"));
			for (int b = 1; b <= lr2.size(); b++) {
				List<WebElement> lr1 = (List<WebElement>) webdriver
						.findElements(By
								.xpath("//div[@class='searchresults']/ul/li["
										+ b + "]/ul/li"));
				if (lr1.size() > 0) {
					if (lr1.size() <= 5) {
						int j = getRandom(lr1.size());
						String ss = webdriver
								.findElement(
										By.xpath("//div[@class='searchresults']/ul/li["
												+ b + "]/ul/li[" + j + "]/a"))
								.getText().toString();
						webdriver
								.findElement(
										By.xpath("//div[@class='searchresults']/ul/li["
												+ b + "]/ul/li[" + j + "]/a"))
								.click();
						Thread.sleep(10000);
						try {
							if (webdriver.findElement(
									By.xpath("//div[@id='invitestopright']/a"))
									.isDisplayed()) {
								webdriver
										.findElement(
												By.xpath("//div[@id='invitestopright']/a"))
										.click();
							}
						} catch (Exception e) {
						}
						Thread.sleep(10000);
						String st = webdriver
								.findElement(
										By.xpath("//div[@class='sectionmenu']/ul/li[@class='current_section']"))
								.getAttribute("id");
						st = st.replace("li", "content");
						if (webdriver
								.findElement(
										By.xpath("//div[@id='" + st + "']/h2"))
								.getText().toString().contains(ss)) {
							resultDetails.setFlag(true);
							System.out.println("Msg found");
							return resultDetails;
						} else {
							resultDetails.setFlag(false);
							System.out.println("Msg not found");
							resultDetails.setErrorMessage("MSG not found");
							return resultDetails;
						}
					} else {
						resultDetails.setFlag(false);
						System.out.println("jump links morethan 5 are present");
						resultDetails
								.setErrorMessage("jump links morethan 5 are present");
						return resultDetails;
					}
				}
			}
			resultDetails.setFlag(true);
			System.out.println("jump links are not present in this page");
		} catch (Exception e) {
			resultDetails.setFlag(false);
			System.out.println(e.getMessage());
			resultDetails.setErrorMessage(e.getMessage());
			return resultDetails;
		}

		return resultDetails;

	}

	public ResultDetails medlinecheckbox(WebDriver webdriver)
			throws InterruptedException {
		try {
			List<WebElement> l = (List<WebElement>) webdriver.findElements(By
					.xpath("//div[@id='medlineresults']//a"));
			String[] str1 = new String[l.size()];
			if (l.size() > 1) {
				for (int lm = 0; lm <= 1; lm++) {
					int i = getRandom(l.size());
					int j = i - 1;
					int k = 2 + (3 * j);
					try {
						while (webdriver.findElement(
								By.xpath("//div[@id='medlineresults']/div[" + k
										+ "]/div/input")).isSelected()) {
							i = getRandom(l.size());
							j = i - 1;
							k = 2 + (3 * j);
						}
						str1[lm] = webdriver
								.findElement(
										By.xpath("//div[@id='medlineresults']/div["
												+ k + "]/div[2]/a")).getText()
								.toString();
						webdriver.findElement(
								By.xpath("//div[@id='medlineresults']/div[" + k
										+ "]/div/input")).click();
						Thread.sleep(3000);
					} catch (Exception e) {
						System.out
								.println("checkbox not click for " + str1[lm]);
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage("checkbox not click for "
								+ str1[lm]);
						return resultDetails;
					}
				}

				Thread.sleep(3000);
				webdriver.findElement(By.linkText("View Selected Abstracts"))
						.click();
				Thread.sleep(5000);
				try {
					if (webdriver.findElement(
							By.xpath("//div[@id='invitestopright']/a"))
							.isDisplayed()) {
						webdriver.findElement(
								By.xpath("//div[@id='invitestopright']/a"))
								.click();
					}
				} catch (Exception e) {
				}
				for (int lm2 = 0; lm2 <= 1; lm2++) {
					String s1 = webdriver.getPageSource();
					if (s1.contains(str1[lm2])) {
						resultDetails.setFlag(true);
					} else {
						System.out.println("title of " + str1[lm2]
								+ " not present");
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage("title of " + str1[lm2]
								+ " not present");
						if(lm2==0){
						if(webdriver.findElement(By.xpath("//div[@id='contentbody']/div[4]/h2")).equals("")){
							resultDetails.setErrorMessage("title of " + str1[lm2]
									+ " shows a blank page");
						}}else{
							if(webdriver.findElement(By.xpath("//div[@id='contentbody']/div[6]/h2")).equals("")){
								resultDetails.setErrorMessage("title of " + str1[lm2]
										+ " shows a blank page");
							}
						}
						return resultDetails;
					}
					Thread.sleep(3000);
				}
			} else {
				String ch = webdriver
						.findElement(
								By.xpath("//div[@id='medlineresults']/div[2]/div[2]/a"))
						.getText().toString();
				webdriver
						.findElement(
								By.xpath("//div[@id='medlineresults']/div[2]/div/input"))
						.click();
				Thread.sleep(3000);
				webdriver.findElement(By.linkText("View Selected Abstracts"))
						.click();
				Thread.sleep(3000);
				if (webdriver.getPageSource().contains(ch)) {
					resultDetails.setFlag(true);
					webdriver.navigate().back();
				} else {
					System.out.println("title of " + ch + " not present");
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("title of " + ch
							+ " not present");
					return resultDetails;
				}

			}
			return resultDetails;
		} catch (Exception e) {
			resultDetails.setFlag(false);
			System.out.println(e.getMessage());
			return resultDetails;

		}
	}

	public ResultDetails randomtagname(WebDriver webdriver)
			throws InterruptedException {

		Thread.sleep(2000);
		resultDetails = tagname(webdriver);
		if (!resultDetails.getFlag()) {
			return resultDetails;
		}
		Thread.sleep(3000);
		webdriver.navigate().back();
		Thread.sleep(5000);
		List<WebElement> l = (List<WebElement>) webdriver.findElements(By
				.xpath("//*[@id='btmpagination']/a"));
		System.out.println(l.size());
		if (l.size() > 1) {
			for (int i = 1; i <= 1; i++) {
				Thread.sleep(2000);
				// List<WebElement>
				// l=(List<WebElement>)driver.findElements(By.xpath("//*[@id='btmpagination']"));
				webdriver
						.findElement(
								By.xpath("//div[@class='searchresults']/table/tbody/tr/td[2]/a["
										+ getRandom(l.size()) + "]")).click();
				Thread.sleep(5000);
				resultDetails = tagname(webdriver);
				if (!resultDetails.getFlag()) {
					return resultDetails;
				}
				webdriver.navigate().back();
				Thread.sleep(5000);
				try {
					if (webdriver.findElement(By.partialLinkText("next "))
							.isDisplayed()) {
						try {
							webdriver.findElement(By.partialLinkText("next "))
									.click();
							resultDetails.setFlag(true);
						} catch (Exception e) {
							System.out.println("next button is not clicked");
							resultDetails.setFlag(false);
							return resultDetails;
						}
						Thread.sleep(5000);
						resultDetails = tagname(webdriver);
						if (!resultDetails.getFlag()) {
							return resultDetails;
						}
						webdriver.navigate().back();
						Thread.sleep(5000);

						try {
							if (webdriver
									.findElement(By.partialLinkText(" previous"))
									.isDisplayed()) {
								webdriver
										.findElement(By.partialLinkText(" previous"))
										.click();
								resultDetails.setFlag(true);
								Thread.sleep(2000);
							}
						} catch (Exception e) {
							resultDetails.setFlag(false);
							// resultDetails.setErrorMessage("previous button is not displayed or clicked");

							System.out
									.println("previous button is not displayed or clicked");
							return resultDetails;
						}
					}
				} catch (Exception e) {
					if (webdriver.findElement(By.partialLinkText(" previous"))
							.isDisplayed()) {
						webdriver.findElement(By.partialLinkText(" previous"))
								.click();
						Thread.sleep(2000);
					}
				}
			}
		}
		return resultDetails;

	}

	public ResultDetails randomtagnamehorizontally(WebDriver webdriver)
			throws InterruptedException {

		Thread.sleep(2000);
		resultDetails = tagnamehorizontally(webdriver);
		if (!resultDetails.getFlag()) {
			return resultDetails;
		}
		Thread.sleep(3000);
		webdriver.navigate().back();
		Thread.sleep(5000);
		List<WebElement> l = (List<WebElement>) webdriver.findElements(By
				.xpath("//*[@id='btmpagination']/a"));
		System.out.println(l.size());
		if (l.size() > 1) {
			for (int i = 1; i <= 1; i++) {
				Thread.sleep(2000);
				// List<WebElement>
				// l=(List<WebElement>)driver.findElements(By.xpath("//*[@id='btmpagination']"));
				webdriver
						.findElement(
								By.xpath("//div[@class='searchresults']/table/tbody/tr/td[2]/a["
										+ getRandom(l.size()) + "]")).click();
				Thread.sleep(5000);
				resultDetails = tagnamehorizontally(webdriver);
				if (!resultDetails.getFlag()) {
					return resultDetails;
				}
				webdriver.navigate().back();
				Thread.sleep(5000);
				try {
					if (webdriver.findElement(By.partialLinkText("next "))
							.isDisplayed()) {
						try {
							webdriver.findElement(By.partialLinkText("next "))
									.click();
							resultDetails.setFlag(true);
						} catch (Exception e) {
							System.out.println("next button is not clicked");
							resultDetails.setFlag(false);
							return resultDetails;
						}
						Thread.sleep(5000);
						resultDetails = tagnamehorizontally(webdriver);
						if (!resultDetails.getFlag()) {
							return resultDetails;
						}
						webdriver.navigate().back();
						Thread.sleep(5000);

						try {
							if (webdriver
									.findElement(By.partialLinkText(" previous"))
									.isDisplayed()) {
								webdriver
										.findElement(By.partialLinkText(" previous"))
										.click();
								resultDetails.setFlag(true);
								Thread.sleep(2000);
							}
						} catch (Exception e) {
							resultDetails.setFlag(false);
							// resultDetails.setErrorMessage("previous button is not displayed or clicked");

							System.out
									.println("previous button is not displayed or clicked");
							return resultDetails;
						}
					}
				} catch (Exception e) {

					if (webdriver.findElement(By.partialLinkText(" previous"))
							.isDisplayed()) {
						webdriver.findElement(By.partialLinkText(" previous"))
								.click();
						Thread.sleep(2000);
					}
				}
			}
		}
		return resultDetails;

	}

	public ResultDetails randommedlinecheckbox(WebDriver webdriver)
			throws InterruptedException {

		Thread.sleep(2000);
		resultDetails = medlinecheckbox(webdriver);
		if (!resultDetails.getFlag()) {
			return resultDetails;
		}
		Thread.sleep(3000);
		webdriver.navigate().back();
		Thread.sleep(5000);
		List<WebElement> l = (List<WebElement>) webdriver.findElements(By
				.xpath("//*[@id='btmpagination']/a"));
		System.out.println(l.size());
		if (l.size() > 1) {
			for (int i = 1; i <= 1; i++) {
				Thread.sleep(2000);
				// List<WebElement>
				// l=(List<WebElement>)driver.findElements(By.xpath("//*[@id='btmpagination']"));
				webdriver
						.findElement(
								By.xpath("//div[@class='searchresults']/table/tbody/tr/td[2]/a["
										+ getRandom(l.size()) + "]")).click();
				Thread.sleep(5000);
				resultDetails = medlinecheckbox(webdriver);
				if (!resultDetails.getFlag()) {
					return resultDetails;
				}
				webdriver.navigate().back();
				Thread.sleep(5000);
				try {
					if (webdriver.findElement(By.partialLinkText("next "))
							.isDisplayed()) {
						try {
							webdriver.findElement(By.partialLinkText("next "))
									.click();
							resultDetails.setFlag(true);
						} catch (Exception e) {
							System.out.println("next button is not clicked");
							resultDetails.setFlag(false);
							resultDetails
									.setErrorMessage("next button is not clicked");
							return resultDetails;
						}
						Thread.sleep(5000);
						resultDetails = medlinecheckbox(webdriver);
						if (!resultDetails.getFlag()) {
							return resultDetails;
						}
						webdriver.navigate().back();
						Thread.sleep(5000);

						try {
							if (webdriver
									.findElement(By.partialLinkText(" previous"))
									.isDisplayed()) {
								webdriver
										.findElement(By.partialLinkText(" previous"))
										.click();
								resultDetails.setFlag(true);
								Thread.sleep(2000);
							}
						} catch (Exception e) {
							resultDetails.setFlag(false);
							resultDetails
									.setErrorMessage("previous button is not displayed or clicked");

							System.out
									.println("previous button is not displayed or clicked");
							return resultDetails;
						}
					}
				} catch (Exception e) {
					if (webdriver.findElement(By.partialLinkText(" previous"))
							.isDisplayed()) {
						webdriver.findElement(By.partialLinkText(" previous"))
								.click();
						Thread.sleep(2000);
					}
				}
			}
		}
		return resultDetails;

	}

	public ResultDetails filtertypesearch(WebDriver webdriver,
			String field) throws InterruptedException {
		Thread.sleep(5000);
		List<WebElement> l = (List<WebElement>) webdriver.findElements(By
				.xpath(field));
		int i = getRandom(l.size() - 1);
		String str1 = webdriver.findElement(By.xpath(field + "[" + i + "]/a"))
				.getText();
		webdriver.findElement(By.xpath(field + "[" + i + "]/a")).click();
		Thread.sleep(2000);
		String str3 = webdriver.findElement(
				By.xpath("//div[@class='row']/h2/span")).getText();
		if (str1.contains(str3) == true) {
			List<WebElement> l1 = (List<WebElement>) webdriver.findElements(By
					.xpath("//div[@class='searchresults']/ul/li"));
			for (i = 1; i <= l1.size(); i++) {
				String str2 = webdriver.findElement(
						By.xpath("//div[@class='searchresults']/ul/li[" + i
								+ "]/div/span")).getText();
				str2 = str2.replace(", ", "");
				if (str1.contains(str2) == true) {
					System.out.println("filter type search is verified for "
							+ str1);
					resultDetails.setFlag(true);
				} else {
					resultDetails.setFlag(false);
					System.out
							.println("filter type search is not verified for "
									+ str1);
					resultDetails
							.setErrorMessage("filter type search is not verified for "
									+ str1);
					return resultDetails;
				}
			}
			List<WebElement> l2 = (List<WebElement>) webdriver.findElements(By
					.xpath("//*[@id='btmpagination']/a"));
			if (l2.size() > 1) {
				for (int k = 1; k <= 1; k++) {
					Thread.sleep(2000);
					// List<WebElement>
					// l=(List<WebElement>)driver.findElements(By.xpath("//*[@id='btmpagination']"));
					webdriver
							.findElement(
									By.xpath("//div[@class='searchresults']/table/tbody/tr/td[2]/a["
											+ getRandom(l2.size()) + "]"))
							.click();
					Thread.sleep(2000);
					List<WebElement> l5 = (List<WebElement>) webdriver
							.findElements(By
									.xpath("//div[@class='searchresults']/ul/li"));
					for (i = 1; i <= l5.size(); i++) {
						String str2 = webdriver.findElement(
								By.xpath("//div[@class='searchresults']/ul/li["
										+ i + "]/div/span")).getText();
						str2 = str2.replace(", ", "");
						if (str1.contains(str2) == true) {
							System.out
									.println("filter type search is verified for "
											+ str1);
							resultDetails.setFlag(true);
						} else {
							resultDetails.setFlag(false);
							System.out
									.println("filter type search is not verified for "
											+ str1);
							resultDetails
									.setErrorMessage("filter type search is not verified for "
											+ str1);
							return resultDetails;
						}
					}
					try {
						if (webdriver.findElement(By.partialLinkText("next "))
								.isDisplayed()) {
							try {
								webdriver.findElement(By.partialLinkText("next "))
										.click();
								resultDetails.setFlag(true);
							} catch (Exception e) {
								System.out
										.println("next button is not clicked");
								resultDetails
										.setErrorMessage("next button is not clicked");
								resultDetails.setFlag(false);
								return resultDetails;
							}
							Thread.sleep(2000);
							List<WebElement> l3 = (List<WebElement>) webdriver
									.findElements(By
											.xpath("//div[@class='searchresults']/ul/li"));
							for (i = 1; i <= l3.size(); i++) {
								String str2 = webdriver
										.findElement(
												By.xpath("//div[@class='searchresults']/ul/li["
														+ i + "]/div/span"))
										.getText();
								str2 = str2.replace(", ", "");

								if (str1.contains(str2) == true) {
									System.out
											.println("filter type search is verified for "
													+ str1);
									resultDetails.setFlag(true);
								} else {
									resultDetails.setFlag(false);
									System.out
											.println("filter type search is verified for "
													+ str1);
									resultDetails
											.setErrorMessage("filter type search is not verified for "
													+ str1);
									return resultDetails;
								}
							}
							try {
								if (webdriver.findElement(
										By.partialLinkText(" previous"))
										.isDisplayed()) {
									webdriver.findElement(
											By.partialLinkText(" previous")).click();
									resultDetails.setFlag(true);
									Thread.sleep(2000);
									return resultDetails;

								}
							} catch (Exception e) {
								resultDetails.setFlag(false);
								resultDetails
										.setErrorMessage("previous button is not clicked or displayed"
												+ str1);
								return resultDetails;
							}

						}
					} catch (Exception e) {

						if (webdriver.findElement(By.partialLinkText(" previous"))
								.isDisplayed()) {
							webdriver.findElement(By.partialLinkText(" previous"))
									.click();
							resultDetails.setFlag(true);
							Thread.sleep(2000);
							return resultDetails;
						}

					}
				}

			} else {
				Thread.sleep(2000);
				List<WebElement> l4 = (List<WebElement>) webdriver
						.findElements(By
								.xpath("//div[@class='searchresults']/ul/li"));
				for (i = 1; i <= l4.size(); i++) {
					String str2 = webdriver.findElement(
							By.xpath("//div[@class='searchresults']/ul/li[" + i
									+ "]/div/span")).getText();
					str2 = str2.replace(", ", "");

					if (str1.contains(str2) == true) {
						System.out
								.println("filter type search is not verified for "
										+ str1);
						resultDetails.setFlag(true);
					} else {
						resultDetails.setFlag(false);
						System.out
								.println("filter type search is not verified for "
										+ str1);
						resultDetails
								.setErrorMessage("filter type search is not verified for "
										+ str1);
						return resultDetails;
					}
				}
			}
		}

		else {
			System.out.println("links are missing for " + str1);
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("links are missing for " + str1);
			return resultDetails;
		}
		return resultDetails;

	}

	public ResultDetails referencefiltertypesearch(WebDriver webdriver,
			String str3) {

		List<WebElement> l1 = (List<WebElement>) webdriver.findElements(By
				.xpath("//div[@class='searchresults']/ul/li"));

		for (j = 1; j <= l1.size(); j++) {

			String str2 = webdriver.findElement(
					By.xpath("//div[@class='searchresults']/ul/li[" + j
							+ "]/div/span")).getText();
			str2 = str2.replace(", ", "");

			if (str2.contains(str3) == true) {
				resultDetails.setFlag(true);
			} else {
				resultDetails.setFlag(false);
				System.out
						.println("filter type search is verified for " + str2);
				resultDetails
						.setErrorMessage("filter type search is not verified for "
								+ str2);
				return resultDetails;
			}
		}
		return resultDetails;

	}

	public ResultDetails reffiltertypesearch(WebDriver webdriver,
			String str3) throws InterruptedException {
		resultDetails = referencefiltertypesearch(webdriver, str3);
		if (!resultDetails.getFlag()) {
			return resultDetails;
		}
		List<WebElement> l2 = (List<WebElement>) webdriver.findElements(By
				.xpath("//*[@id='btmpagination']/a"));
		if (l2.size() > 1) {
			for (int k = 1; k <= 1; k++) {
				Thread.sleep(2000);
				// List<WebElement>
				// l=(List<WebElement>)driver.findElements(By.xpath("//*[@id='btmpagination']"));
				webdriver
						.findElement(
								By.xpath("//div[@class='searchresults']/table/tbody/tr/td[2]/a["
										+ getRandom(l2.size()) + "]")).click();
				Thread.sleep(2000);
				resultDetails = referencefiltertypesearch(webdriver, str3);
				if (!resultDetails.getFlag()) {
					return resultDetails;
				}
				try {
					if (webdriver.findElement(By.partialLinkText("next "))
							.isDisplayed()) {
						try {
							webdriver.findElement(By.partialLinkText("next "))
									.click();
							resultDetails.setFlag(true);
						} catch (Exception e) {
							System.out.println("next button is not clicked");
							resultDetails
									.setErrorMessage("next button is not clicked");
							resultDetails.setFlag(false);
							return resultDetails;
						}
						Thread.sleep(2000);
						resultDetails = referencefiltertypesearch(
								webdriver, str3);
						if (!resultDetails.getFlag()) {
							return resultDetails;
						}
						try {
							if (webdriver
									.findElement(By.partialLinkText(" previous"))
									.isDisplayed()) {
								webdriver
										.findElement(By.partialLinkText(" previous"))
										.click();
								resultDetails.setFlag(true);
								Thread.sleep(2000);
								return resultDetails;
							}
						} catch (Exception e) {
							resultDetails.setFlag(false);
							resultDetails
									.setErrorMessage("previous button is not clicked or displayed");
							return resultDetails;
						}

					}
				} catch (Exception e) {

					if (webdriver.findElement(By.partialLinkText(" previous"))
							.isDisplayed()) {
						webdriver.findElement(By.partialLinkText(" previous"))
								.click();
						resultDetails.setFlag(true);
						Thread.sleep(2000);
						return resultDetails;
					}

				}
			}
		} else {
			Thread.sleep(2000);
			resultDetails = referencefiltertypesearch(webdriver, str3);
			if (!resultDetails.getFlag()) {
				return resultDetails;
			}
		}

		return resultDetails;
	}

	public ResultDetails totalarticle(WebDriver webdriver, String str2) {
		String str3 = webdriver.findElement(
				By.xpath("//div[@class='row']/h2/span")).getText();
		System.out.println(str2);

		if (str2.contains(str3)) {
			resultDetails.setFlag(true);
		} else {
			System.out.println("total no of articles not present for " + str2);
			resultDetails
					.setErrorMessage("total no of articles not present for "
							+ str2);
			resultDetails.setFlag(false);
			return resultDetails;
		}
		return resultDetails;

	}

	public ResultDetails randomdate(WebDriver webdriver, int j)
			throws ParseException {
		List<WebElement> l = (List<WebElement>) webdriver.findElements(By
				.xpath("//div[@class='searchresults']/ul/li"));
		for (int i = 1; i <= l.size(); i++) {
			List<WebElement> l1 = (List<WebElement>) webdriver
					.findElements(By.xpath("//*[@id='medscaperesults']/li[" + i
							+ "]/div/span"));
			String str1 = webdriver.findElement(
					By.xpath("//*[@id='medscaperesults']/li[" + i
							+ "]/div/span[" + l1.size() + "]")).getText();
			DateFormat formatter1 = new SimpleDateFormat("MMM yyyy");
			Date d1 = (Date) formatter1.parse(str1);
			System.out.println("Article date: " + d1);
			Date date1 = new Date();
			int b = date1.getDate();
			GregorianCalendar gc1 = new GregorianCalendar();
			gc1.setTime(date1);
			gc1.add(GregorianCalendar.MONTH, -j);
			Date dateBefore = gc1.getTime();
			gc1.add(GregorianCalendar.DATE, -b);
			Date dateBefore1 = gc1.getTime();
			System.out.println("system date: " + date1);
			System.out.println("required date: " + dateBefore1);

			if ((d1.compareTo(dateBefore1) >= 0) && (d1.compareTo(date1) <= 0)) {
				System.out.println("date is present between given days");
				resultDetails.setFlag(true);
			} else {
				System.out.println("date is not present between given days");
				resultDetails.setFlag(false);
				resultDetails
						.setErrorMessage("date is not present between given days");
				return resultDetails;

			}
		}
		return resultDetails;
	}

	public ResultDetails medlinerandomdate(WebDriver webdriver, int j)
			throws ParseException, InterruptedException {
		try {
			Thread.sleep(5000);
			List<WebElement> ml1 = (List<WebElement>) webdriver.findElements(By
					.xpath("//div[@id='medlineresults']//a"));
			for (int i = 1; i <= ml1.size(); i++) {
				int j1 = i - 1;
				int k1 = 2 + (3 * j1);
				// List<WebElement>
				// l1=(List<WebElement>)webdriver.findElements(By.xpath("//*[@id='medscaperesults']/li["+i+"]/div/span"));
				String str1 = webdriver.findElement(
						By.xpath("//div[@id='medlineresults']/div[" + k1
								+ "]/div[2]/div")).getText();
				String[] str2 = str1.split(" ");
				int s = str2.length;
				String str3 = str2[s - 2].concat(" " + str2[s - 1]);
				DateFormat formatter1 = new SimpleDateFormat("MMM yyyy");
				Date d1 = (Date) formatter1.parse(str3);
				System.out.println("Article date: " + d1);
				Date date1 = new Date();
				int b = date1.getDate();
				GregorianCalendar gc1 = new GregorianCalendar();
				gc1.setTime(date1);
				gc1.add(GregorianCalendar.MONTH, -j);
				Date dateBefore = gc1.getTime();
				gc1.add(GregorianCalendar.DATE, -b);
				Date dateBefore1 = gc1.getTime();
				System.out.println("system date: " + date1);
				System.out.println("required date: " + dateBefore1);

				if ((d1.compareTo(dateBefore1) >= 0)
						&& (d1.compareTo(date1) <= 0)) {
					System.out
							.println("date is present between given days for "
									+ str1);
					resultDetails.setFlag(true);
				} else {

					System.out
							.println("date is not present between given days");
					resultDetails.setFlag(false);
					resultDetails
							.setErrorMessage("date is not present between given days");
					return resultDetails;

				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			resultDetails.setFlag(false);
			resultDetails
					.setErrorMessage("Article date not found in this page");
		}
		return resultDetails;
	}

	public ResultDetails totalfilterlinks(WebDriver webdriver,
			String field) throws InterruptedException {
		List<WebElement> l1 = (List<WebElement>) webdriver.findElements(By
				.xpath(field));
		int i = getRandom(l1.size());
		String str = webdriver.findElement(By.xpath(field + "[" + i + "]/a"))
				.getText();
		System.out.println(str);
		webdriver.findElement(By.xpath(field + "[" + i + "]/a")).click();
		Thread.sleep(2000);
		String str1 = webdriver.findElement(
				By.xpath("//div[@class='row']/h2/span")).getText();
		System.out.println(str1);
		if (str.contains(str1)) {
			System.out
					.println("Total required type articles present correctly for "
							+ str);
			resultDetails.setFlag(true);
		} else {
			System.out
					.println("Total required type articles not present correctly for "
							+ str);
			resultDetails.setFlag(false);
			resultDetails
					.setErrorMessage("Total required type articles not present correctly for "
							+ str);
			return resultDetails;
		}
		return resultDetails;
	}

	public ResultDetails publishdate(WebDriver webdriver)
			throws ParseException {

		List<WebElement> l = (List<WebElement>) webdriver.findElements(By
				.xpath("//div[@class='searchresults']/ul/li"));
		if (l.size() > 1) {
			for (int i = 1; i <= l.size() - 1; i++) {
				List<WebElement> l1 = (List<WebElement>) webdriver
						.findElements(By.xpath("//*[@id='medscaperesults']/li["
								+ i + "]/div/span"));
				String str1 = webdriver.findElement(
						By.xpath("//*[@id='medscaperesults']/li[" + i
								+ "]/div/span[" + l1.size() + "]")).getText();
				DateFormat formatter1 = new SimpleDateFormat("MMM yyyy");
				Date d1 = (Date) formatter1.parse(str1);
				System.out.println(d1);
				List<WebElement> l2 = (List<WebElement>) webdriver
						.findElements(By.xpath("//*[@id='medscaperesults']/li["
								+ (i + 1) + "]/div/span"));
				String str2 = webdriver.findElement(
						By.xpath("//*[@id='medscaperesults']/li[" + (i + 1)
								+ "]/div/span[" + l2.size() + "]")).getText();
				DateFormat formatter2 = new SimpleDateFormat("MMM yyyy");
				Date d2 = (Date) formatter2.parse(str2);
				System.out.println(d2);
				Date date1 = new Date();
				if ((d1.compareTo(date1) <= 0) && (d2.compareTo(date1) <= 0)) {
					if (d1.compareTo(d2) >= 0) {
						resultDetails.setFlag(true);
						System.out.println("date is verified");
					} else {
						System.out.println("date is not verified");
						resultDetails.setFlag(false);
						resultDetails
								.setErrorMessage("date is not verified in publish date");
						return resultDetails;
					}
				} else {
					System.out
							.println("date is greaterthan current system date");
					resultDetails.setFlag(false);
					resultDetails
							.setErrorMessage("date is greaterthan current system date in publish date");
					return resultDetails;
				}
			}
		} else {
			resultDetails.setFlag(true);
		}
		return resultDetails;

	}

	public ResultDetails medlinepublishdate(WebDriver webdriver)
			throws ParseException {

		List<WebElement> l = (List<WebElement>) webdriver.findElements(By
				.xpath("//div[@id='medlineresults']//a"));
		if (l.size() > 1) {
			for (int i = 1; i <= l.size() - 1; i++) {
				int j = i - 1;
				int k = 2 + (3 * j);
				String str1 = webdriver.findElement(
						By.xpath("//div[@id='medlineresults']/div[" + k
								+ "]/div[2]/div")).getText();
				String[] str2 = str1.split(" ");
				int s = str2.length;
				String str3 = str2[s - 2].concat(" " + str2[s - 1]);
				DateFormat formatter1 = new SimpleDateFormat("MMM yyyy");
				Date d1 = (Date) formatter1.parse(str3);
				System.out.println(d1);
				// List<WebElement>
				// l2=(List<WebElement>)webdriver.findElements(By.xpath("//*[@id='medscaperesults']/li["+(i+1)+"]/div/span"));
				String str4 = webdriver.findElement(
						By.xpath("//div[@id='medlineresults']/div[" + (k + 3)
								+ "]/div[2]/div")).getText();
				String[] str5 = str4.split(" ");
				int ss = str5.length;
				String str6 = str5[ss - 2].concat(" " + str5[ss - 1]);
				DateFormat formatter2 = new SimpleDateFormat("MMM yyyy");
				Date d2 = (Date) formatter2.parse(str6);
				System.out.println(d2);
				Date date1 = new Date();
				if ((d1.compareTo(date1) <= 0) && (d2.compareTo(date1) <= 0)) {
					if ((d1.compareTo(d2) >= 0)) {
						System.out.println("date is verified");
						resultDetails.setFlag(true);
					} else {
						System.out.println("date is not verified");
						resultDetails.setFlag(false);
						resultDetails
								.setErrorMessage("date is not verified in publish date");
						return resultDetails;
					}
				} else {
					System.out
							.println("date is greaterthan current system date");
					resultDetails.setFlag(false);
					resultDetails
							.setErrorMessage("date is greaterthan current system date in publish date");
					return resultDetails;

				}
			}
		} else {
			resultDetails.setFlag(true);
		}
		return resultDetails;
	}

	public ResultDetails randomdatemethod(WebDriver webdriver, int j)
			throws ParseException, InterruptedException {
		Thread.sleep(3000);
		if (webdriver.getTitle().contains("MEDLINE")) {
			Thread.sleep(4000);
			resultDetails = medlinerandomdate(webdriver, j);
			if (!resultDetails.getFlag()) {
				return resultDetails;
			}
		} else {
			Thread.sleep(3000);
			resultDetails = randomdate(webdriver, j);
			if (!resultDetails.getFlag()) {
				return resultDetails;
			}

		}
		List<WebElement> lp = (List<WebElement>) webdriver.findElements(By
				.xpath("//*[@id='btmpagination']/a"));
		if (lp.size() > 1) {
			for (int p = 1; p <= 1; p++) {
				Thread.sleep(2000);
				// List<WebElement>
				// l=(List<WebElement>)webdriver.findElements(By.xpath("//*[@id='btmpagination']"));
				webdriver
						.findElement(
								By.xpath("//div[@class='searchresults']/table/tbody/tr/td[2]/a["
										+ getRandom(lp.size()) + "]")).click();
				if (webdriver.getTitle().contains("MEDLINE")) {
					Thread.sleep(4000);
					resultDetails = medlinerandomdate(webdriver, j);
					if (!resultDetails.getFlag()) {
						return resultDetails;
					}
				} else {
					Thread.sleep(2000);
					resultDetails = randomdate(webdriver, j);
					if (!resultDetails.getFlag()) {
						return resultDetails;
					}
				}
				try {
					if (webdriver.findElement(By.partialLinkText("next "))
							.isDisplayed()) {
						try {
							webdriver.findElement(By.partialLinkText("next "))
									.click();
							resultDetails.setFlag(true);
						} catch (Exception e) {
							System.out.println("next button is not clicked");
							resultDetails
									.setErrorMessage("next button is not clicked");
							resultDetails.setFlag(false);
							return resultDetails;
						}
						if (webdriver.getTitle().contains("MEDLINE")) {
							Thread.sleep(4000);
							resultDetails = medlinerandomdate(webdriver,
									j);
							if (!resultDetails.getFlag()) {
								return resultDetails;
							}
						} else {
							Thread.sleep(2000);
							resultDetails = randomdate(webdriver, j);
							if (!resultDetails.getFlag()) {
								return resultDetails;
							}
						}
						try {
							if (webdriver
									.findElement(By.partialLinkText(" previous"))
									.isDisplayed()) {
								webdriver
										.findElement(By.partialLinkText(" previous"))
										.click();
								resultDetails.setFlag(true);
								Thread.sleep(2000);
							}
						} catch (Exception e) {
							System.out
									.println("previous button is not displayed or clicked");
							resultDetails.setFlag(false);
							resultDetails
									.setErrorMessage("previous button is not displayed or clicked");
							return resultDetails;
						}
					}
				} catch (Exception e) {

					if (webdriver.findElement(By.partialLinkText(" previous"))
							.isDisplayed()) {
						webdriver.findElement(By.partialLinkText(" previous"))
								.click();
						resultDetails.setFlag(true);
						Thread.sleep(2000);
					}
				}
			}
		} else {
			if (webdriver.getTitle().contains("MEDLINE")) {
				Thread.sleep(4000);
				resultDetails = medlinerandomdate(webdriver, j);
				if (!resultDetails.getFlag()) {
					return resultDetails;
				}
			} else {
				Thread.sleep(2000);
				resultDetails = randomdate(webdriver, j);
				if (!resultDetails.getFlag()) {
					return resultDetails;
				}
			}
		}

		return resultDetails;

	}

	public ResultDetails sortbysearch(WebDriver webdriver)
			throws InterruptedException, ParseException {
		try {
			webdriver.findElement(By.linkText("Relevance")).click();
			webdriver.findElement(By.linkText("Publish Date")).click();
			Thread.sleep(2000);
			if (webdriver.getTitle().contains("MEDLINE")) {
				resultDetails = medlinepublishdate(webdriver);
				if (!resultDetails.getFlag()) {
					return resultDetails;

				}
			} else {
				resultDetails = publishdate(webdriver);
				if (!resultDetails.getFlag()) {
					return resultDetails;
				}
			}
			List<WebElement> l1 = (List<WebElement>) webdriver.findElements(By
					.xpath("//*[@id='btmpagination']/a"));
			if (l1.size() > 1) {
				for (int i1 = 1; i1 <= 1; i1++) {
					Thread.sleep(2000);
					// List<WebElement>
					// l=(List<WebElement>)webdriver.findElements(By.xpath("//*[@id='btmpagination']"));
					webdriver
							.findElement(
									By.xpath("//div[@class='searchresults']/table/tbody/tr/td[2]/a["
											+ getRandom(l1.size()) + "]"))
							.click();
					Thread.sleep(2000);
					if (webdriver.getTitle().contains("MEDLINE")) {
						resultDetails = medlinepublishdate(webdriver);
						if (!resultDetails.getFlag()) {
							return resultDetails;
						}
					} else {
						resultDetails = publishdate(webdriver);
						if (!resultDetails.getFlag()) {
							return resultDetails;
						}
					}
					try {
						if (webdriver.findElement(By.partialLinkText("next "))
								.isDisplayed()) {
							try {
								webdriver.findElement(By.partialLinkText("next "))
										.click();
								resultDetails.setFlag(true);
							} catch (Exception e) {
								System.out
										.println("next button is not clicked");
								resultDetails
										.setErrorMessage("next button is not clicked");
								resultDetails.setFlag(false);
								return resultDetails;
							}
							Thread.sleep(2000);
							if (webdriver.getTitle().contains("MEDLINE")) {
								resultDetails = medlinepublishdate(webdriver);
								if (!resultDetails.getFlag()) {
									return resultDetails;
								}
							} else {
								resultDetails = publishdate(webdriver);
								if (!resultDetails.getFlag()) {
									return resultDetails;
								}
							}
							try {
								if (webdriver.findElement(
										By.partialLinkText(" previous"))
										.isDisplayed()) {
									webdriver.findElement(
											By.partialLinkText(" previous")).click();
									Thread.sleep(2000);
									resultDetails.setFlag(true);
								}
							} catch (Exception e) {
								System.out
										.println("previous button is not displayed or clicked");
								resultDetails
										.setErrorMessage("previous button is not displayed or clicked");
								resultDetails.setFlag(false);
								return resultDetails;
							}
						}
					} catch (Exception e) {

						if (webdriver.findElement(By.partialLinkText(" previous"))
								.isDisplayed()) {
							webdriver.findElement(By.partialLinkText(" previous"))
									.click();
							Thread.sleep(2000);
							resultDetails.setFlag(true);
						}
					}
				}
			} else {
				Thread.sleep(2000);
				if (webdriver.getTitle().contains("MEDLINE")) {
					resultDetails = medlinepublishdate(webdriver);
					if (!resultDetails.getFlag()) {
						return resultDetails;
					}
				} else {
					resultDetails = publishdate(webdriver);
					if (!resultDetails.getFlag()) {
						return resultDetails;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resultDetails;

	}

	public ResultDetails olderthan5years(WebDriver webdriver)
			throws InterruptedException, ParseException {
		if (webdriver.getTitle().contains("MEDLINE")) {
			Thread.sleep(5000);
			resultDetails = medlineolderthan5yearsmethod(webdriver);
			if (!resultDetails.getFlag()) {
				return resultDetails;
			}
		} else {
			Thread.sleep(2000);
			resultDetails = olderthan5yearsmethod(webdriver);
			if (!resultDetails.getFlag()) {
				return resultDetails;
			}
		}

		List<WebElement> lp = (List<WebElement>) webdriver.findElements(By
				.xpath("//*[@id='btmpagination']/a"));
		if (lp.size() > 1) {
			for (int p = 1; p <= 1; p++) {
				Thread.sleep(2000);
				// List<WebElement>
				// l=(List<WebElement>)webdriver.findElements(By.xpath("//*[@id='btmpagination']"));
				webdriver
						.findElement(
								By.xpath("//div[@class='searchresults']/table/tbody/tr/td[2]/a["
										+ getRandom(lp.size()) + "]")).click();
				if (webdriver.getTitle().contains("MEDLINE")) {
					Thread.sleep(5000);
					resultDetails = medlineolderthan5yearsmethod(webdriver);
					if (!resultDetails.getFlag()) {
						return resultDetails;
					}
				} else {
					Thread.sleep(5000);
					resultDetails = olderthan5yearsmethod(webdriver);
					if (!resultDetails.getFlag()) {
						return resultDetails;
					}
				}
				try {
					if (webdriver.findElement(By.partialLinkText("next "))
							.isDisplayed()) {
						try {
							webdriver.findElement(By.partialLinkText("next "))
									.click();
							resultDetails.setFlag(true);
						} catch (Exception e) {
							System.out.println("next button is not clicked");
							resultDetails
									.setErrorMessage("next button is not clicked");
							resultDetails.setFlag(false);
							return resultDetails;
						}
						if (webdriver.getTitle().contains("MEDLINE")) {
							Thread.sleep(5000);
							resultDetails = medlineolderthan5yearsmethod(webdriver);
							if (!resultDetails.getFlag()) {
								return resultDetails;
							}

						} else {
							Thread.sleep(5000);
							resultDetails = olderthan5yearsmethod(webdriver);
							if (!resultDetails.getFlag()) {
								return resultDetails;
							}
						}
						try {
							if (webdriver
									.findElement(By.partialLinkText(" previous"))
									.isDisplayed()) {
								webdriver
										.findElement(By.partialLinkText(" previous"))
										.click();
								resultDetails.setFlag(true);
								Thread.sleep(2000);
							}
						} catch (Exception e) {
							System.out
									.println("previous button is not displayed or clicked");
							resultDetails
									.setErrorMessage("previous button is not displayed or clicked");
							resultDetails.setFlag(false);
							return resultDetails;
						}
					}
				} catch (Exception e) {

					if (webdriver.findElement(By.partialLinkText(" previous"))
							.isDisplayed()) {
						webdriver.findElement(By.partialLinkText(" previous"))
								.click();
						resultDetails.setFlag(true);
						Thread.sleep(2000);
					}
				}
			}
		}
		return resultDetails;

	}

	public ResultDetails olderthan5yearsmethod(WebDriver webdriver)
			throws ParseException, InterruptedException {
		Thread.sleep(3000);
		List<WebElement> lo = (List<WebElement>) webdriver.findElements(By
				.xpath("//div[@class='searchresults']/ul/li"));
		for (int o = 1; o <= lo.size(); o++) {
			List<WebElement> l1 = (List<WebElement>) webdriver
					.findElements(By.xpath("//*[@id='medscaperesults']/li[" + o
							+ "]/div/span"));
			String str11 = webdriver.findElement(
					By.xpath("//*[@id='medscaperesults']/li[" + o
							+ "]/div/span[" + l1.size() + "]")).getText();
			DateFormat formatter1 = new SimpleDateFormat("MMM yyyy");
			Date d1 = (Date) formatter1.parse(str11);
			System.out.println("Article date: " + d1);
			Date date1 = new Date();
			int j = 60;
			int b = date1.getDate();
			GregorianCalendar gc1 = new GregorianCalendar();
			gc1.setTime(date1);
			gc1.add(GregorianCalendar.MONTH, -j);
			Date dateBefore = gc1.getTime();
			gc1.add(GregorianCalendar.DATE, -(b - 1));
			Date dateBefore1 = gc1.getTime();
			System.out.println("system date: " + date1);
			System.out.println("Required date: " + dateBefore1);
			if (d1.compareTo(dateBefore1) <= 0) {
				System.out.println("date is present between given days");
				resultDetails.setFlag(true);

			} else {
				System.out.println("date is not present between these days for"
						+ str11);
				resultDetails
						.setErrorMessage("date is not present between these days for"
								+ str11);
				resultDetails.setFlag(false);
				return resultDetails;

			}
		}
		return resultDetails;
	}

	public ResultDetails medlineolderthan5yearsmethod(WebDriver webdriver)
			throws ParseException, InterruptedException {
		Thread.sleep(5000);
		List<WebElement> lo = (List<WebElement>) webdriver.findElements(By
				.xpath("//div[@id='medlineresults']//a"));
		System.out.println(lo.size());
		for (int o = 1; o <= lo.size(); o++) {
			int a = o - 1;
			int k = 2 + (3 * a);
			// List<WebElement>
			// l1=(List<WebElement>)webdriver.findElements(By.xpath("//*[@id='medscaperesults']/li["+i+"]/div/span"));
			String str1 = webdriver.findElement(
					By.xpath("//div[@id='medlineresults']/div[" + k
							+ "]/div[2]/div")).getText();
			String[] str2 = str1.split(" ");
			int s = str2.length;
			String str3 = str2[s - 2].concat(" " + str2[s - 1]);
			DateFormat formatter1 = new SimpleDateFormat("MMM yyyy");
			Date d1 = (Date) formatter1.parse(str3);
			System.out.println("Article date: " + d1);
			Date date1 = new Date();
			int j = 60;
			int b = date1.getDate();
			b = b - 1;
			GregorianCalendar gc1 = new GregorianCalendar();
			gc1.setTime(date1);
			gc1.add(GregorianCalendar.MONTH, -j);
			Date dateBefore = gc1.getTime();
			gc1.add(GregorianCalendar.DATE, -b);
			Date dateBefore1 = gc1.getTime();
			System.out.println("system date: " + date1);
			System.out.println("Required date: " + dateBefore1);

			if (d1.compareTo(dateBefore1) <= 0) {
				System.out.println("date is present between given days");
				resultDetails.setFlag(true);

			} else {
				System.out.println("date is not present between these days for"
						+ str3);
				resultDetails
						.setErrorMessage("date is not present between these days for"
								+ str3);
				resultDetails.setFlag(false);
				return resultDetails;

			}
		}
		return resultDetails;
	}

	public ResultDetails customrangedatemethod(WebDriver webdriver,
			Date dt1, Date dt2) throws ParseException, InterruptedException {
		if (webdriver.getPageSource().toLowerCase()
				.contains("did not match any documents")) {
			resultDetails.setFlag(true);
			System.out
					.println("No articles are present in this custom date range");
			webdriver.navigate().back();
			Thread.sleep(10000);
			resultDetails
					.setErrorMessage("No articles are present in this custom date range");
			return resultDetails;
		}
		List<WebElement> l2 = (List<WebElement>) webdriver.findElements(By
				.xpath("//div[@class='searchresults']/ul/li"));
		System.out.println(l2.size());
		for (int k = 1; k <= l2.size(); k++) {
			List<WebElement> l1 = (List<WebElement>) webdriver.findElements(By
					.xpath("//div[@class='searchresults']/ul/li[" + k
							+ "]/div/span"));
			String str2 = webdriver.findElement(
					By.xpath("//div[@class='searchresults']/ul/li[" + k
							+ "]/div/span[" + l1.size() + "]")).getText();
			DateFormat formatter4 = new SimpleDateFormat("MMM yyyy");
			Date dt3 = (Date) formatter4.parse(str2);
			System.out.println("Article date: " + dt3);
			if (dt1.compareTo(dt2) > 0) {

				if ((dt3.compareTo(dt1) <= 0) && (dt3.compareTo(dt2) >= 0)) {
					System.out.println("date is present between these days");
					resultDetails.setFlag(true);
				} else {
					System.out
							.println("date is not present between these days for"
									+ str2);
					resultDetails
							.setErrorMessage("date is not present between these days for"
									+ str2);
					resultDetails.setFlag(false);
					return resultDetails;

				}
			} else {

				if (dt3.compareTo(dt1) >= 0 && dt3.compareTo(dt2) <= 0) {
					System.out.println("date is present between these days");
					resultDetails.setFlag(true);
				} else {
					System.out
							.println("date is not present betwwn given days for "
									+ str2);
					resultDetails
							.setErrorMessage("date is not present between these days for"
									+ str2);
					resultDetails.setFlag(false);
					return resultDetails;
				}
			}
		}
		return resultDetails;
	}

	public ResultDetails medlinecustomrangedatemethod(
			WebDriver webdriver, Date dt1, Date dt2) throws ParseException,
			InterruptedException {
try{
		if (webdriver.getPageSource().toLowerCase()
				.contains("did not match any documents")) {
			resultDetails.setFlag(true);
			webdriver.navigate().back();
			Thread.sleep(10000);
			System.out
					.println("No articles are present in this custom date range");
			resultDetails
					.setErrorMessage("No articles are present in this custom date range");
			return resultDetails;
		}

		List<WebElement> l = (List<WebElement>) webdriver.findElements(By
				.xpath("//div[@id='medlineresults']//a"));
		System.out.println(l.size());
		for (int i = 1; i <= l.size(); i++) {
			int j = i - 1;
			int k = 2 + (3 * j);
			String str1 = webdriver.findElement(
					By.xpath("//div[@id='medlineresults']/div[" + k
							+ "]/div[2]/div")).getText();
			String[] str2 = str1.split(" ");
			int s = str2.length;
			String str3 = str2[s - 2].concat(" " + str2[s - 1]);
			DateFormat formatter4 = new SimpleDateFormat("MMM yyyy");
			Date dt3 = (Date) formatter4.parse(str3);
			System.out.println("Article date: " + dt3);
			if (dt1.compareTo(dt2) > 0) {

				if ((dt3.compareTo(dt1) <= 0) && (dt3.compareTo(dt2) >= 0)) {
					System.out.println("date is present between these days");
					resultDetails.setFlag(true);
				} else {
					System.out
							.println("date is not present between these days for"
									+ str2);
					resultDetails
							.setErrorMessage("date is not present between these days for"
									+ str2);
					resultDetails.setFlag(false);
					return resultDetails;

				}
			} else {

				if (dt3.compareTo(dt1) >= 0 && dt3.compareTo(dt2) <= 0) {
					System.out.println("date is present between these days");
					resultDetails.setFlag(true);
				} else {
					System.out
							.println("date is not present betwwn given days for "
									+ str2);
					resultDetails.setFlag(false);
					return resultDetails;
				}
			}
		}}catch(Exception e){
			System.out.println(e.getMessage());
			resultDetails.setFlag(false);
			return resultDetails;
		}
		return resultDetails;
	}

	public ResultDetails customrangedate(WebDriver webdriver)
			throws InterruptedException, ParseException {

		Thread.sleep(3000);
		String str1 = webdriver.findElement(By.partialLinkText("Custom Date Range "))
				.getText();
		webdriver.findElement(By.partialLinkText("Custom Date Range ")).click();
		System.out.println(str1);
		Thread.sleep(5000);
		Select selectBox = new Select(webdriver.findElement(By
				.xpath("//div[@id='divCustomDateRangedefault']/div/select[1]")));
		List<WebElement> l3 = (List<WebElement>) webdriver.findElements(By
				.xpath("//select[@name='fromMonth']/option"));
		int a = getRandom(l3.size() - 1);
		selectBox.selectByIndex(a);
		Thread.sleep(2000);

		Select selectBox1 = new Select(webdriver.findElement(By
				.xpath("//div[@id='divCustomDateRangedefault']/div/select[2]")));
		List<WebElement> l4 = (List<WebElement>) webdriver.findElements(By
				.xpath("//select[@name='fromYear']/option"));
		int b = getRandom(l4.size() - 1);
		selectBox1.selectByIndex(b);
		Thread.sleep(2000);

		String str3 = selectBox.getFirstSelectedOption().getText();
		String str4 = selectBox1.getFirstSelectedOption().getText();
		String str5 = str3.concat(" " + str4);
		System.out.println(str5);
		DateFormat formatter2 = new SimpleDateFormat("MM yyyy");
		Date dt1 = (Date) formatter2.parse(str5);
		System.out.println("From date: " + dt1);

		Select selectBox2 = new Select(webdriver.findElement(By
				.xpath("//div[@id='divCustomDateRangedefault']/div/select[3]")));
		List<WebElement> l5 = (List<WebElement>) webdriver.findElements(By
				.xpath("//select[@name='toMonth']/option"));
		int c = getRandom(l5.size() - 1);
		selectBox2.selectByIndex(c);
		Thread.sleep(2000);

		Select selectBox3 = new Select(webdriver.findElement(By
				.xpath("//div[@id='divCustomDateRangedefault']/div/select[4]")));
		List<WebElement> l6 = (List<WebElement>) webdriver.findElements(By
				.xpath("//select[@name='toYear']/option"));
		int d = getRandom(l6.size() - 1);
		selectBox3.selectByIndex(d);
		Thread.sleep(2000);

		String str6 = selectBox2.getFirstSelectedOption().getText();
		String str7 = selectBox3.getFirstSelectedOption().getText();
		String str8 = str6.concat(" " + str7);
		System.out.println(str8);
		DateFormat formatter3 = new SimpleDateFormat("MM yyyy");
		Date dt2 = (Date) formatter3.parse(str8);
		System.out.println("ToDate: " + dt2);

		webdriver.findElement(By.name("submitdate")).click();
		Thread.sleep(3000);
		resultDetails = customrangedatemethod(webdriver, dt1, dt2);
		try{
		if (resultDetails.getErrorMessage().equalsIgnoreCase(
				"No articles are present in this custom date range")) {
			return resultDetails;
		}}catch(Exception e){}
		if (!resultDetails.getFlag()) {
			return resultDetails;
		}
		List<WebElement> lp = (List<WebElement>) webdriver.findElements(By
				.xpath("//*[@id='btmpagination']/a"));
		if (lp.size() > 1) {
			for (int p = 1; p <= 1; p++) {
				Thread.sleep(2000);
				// List<WebElement>
				// l=(List<WebElement>)webdriver.findElements(By.xpath("//*[@id='btmpagination']"));
				webdriver
						.findElement(
								By.xpath("//div[@class='searchresults']/table/tbody/tr/td[2]/a["
										+ getRandom(lp.size()) + "]")).click();
				Thread.sleep(2000);
				resultDetails = customrangedatemethod(webdriver, dt1,
						dt2);

				if (!resultDetails.getFlag()) {
					return resultDetails;
				}

				try {
					if (webdriver.findElement(By.partialLinkText("next "))
							.isDisplayed()) {
						try {
							webdriver.findElement(By.partialLinkText("next "))
									.click();
							resultDetails.setFlag(true);
						} catch (Exception e) {
							System.out.println("next button is not clicked");
							resultDetails
									.setErrorMessage("next button is not clicked");
							resultDetails.setFlag(false);
							return resultDetails;
						}
						Thread.sleep(3000);
						resultDetails = customrangedatemethod(webdriver,
								dt1, dt2);
						if (!resultDetails.getFlag()) {
							return resultDetails;
						}

						try {
							if (webdriver
									.findElement(By.partialLinkText(" previous"))
									.isDisplayed()) {
								webdriver
										.findElement(By.partialLinkText(" previous"))
										.click();
								resultDetails.setFlag(true);
								Thread.sleep(2000);
							}
						} catch (Exception e) {
							System.out
									.println("previous button is not displayed or clicked");
							resultDetails.setFlag(false);
							return resultDetails;
						}
					}
				} catch (Exception e) {

					if (webdriver.findElement(By.partialLinkText(" previous"))
							.isDisplayed()) {
						webdriver.findElement(By.partialLinkText(" previous"))
								.click();
						resultDetails.setFlag(true);
						Thread.sleep(2000);
					}
				}
			}
		}

		return resultDetails;

	}
	 public ResultDetails resultsverify(WebDriver webdriver ,String field,String value){
		   List<WebElement> li1=(List<WebElement>)webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver, field));
			  System.out.println(li1.size());
			  String[] str2=new String[li1.size()];
			  for(int i=0;i<li1.size();i++){
				  str2[i]=webdriver.findElement(By.xpath(field+"["+(i+1)+"]/a")).getText().toString();
			  }
			  webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, value)).click();
			  for(int i=0;i<li1.size();i++){
				 if(webdriver.findElement(By.xpath("//ul[@id='medscaperesults']/li["+(i+1)+"]/a")).getText().toString().contains(str2[i])){
					resultDetails.setFlag(true);
				 }else{
					 resultDetails.setFlag(false);
		 			resultDetails.setErrorMessage(str2[i]+" Result is not dispayed at starting ");
		 			System.out.println(str2[i]+" Result is not dispayed at starting ");
		 			return resultDetails;
				 }
			  }
	      
		return resultDetails;
	 }
	public ResultDetails medlinecustomrangedate(WebDriver webdriver)
			throws InterruptedException, ParseException {
try{
		Thread.sleep(3000);
		String str1 = webdriver.findElement(By.partialLinkText("Custom Date Range "))
				.getText();
		webdriver.findElement(By.partialLinkText("Custom Date Range ")).click();
		System.out.println(str1);
		Thread.sleep(3000);
		Select selectBox = new Select(webdriver.findElement(By
				.xpath("//div[@id='divCustomDateRangemedline']/div/select")));
		List<WebElement> l3 = (List<WebElement>) webdriver.findElements(By
				.xpath("//select[@name='fromMonth']/option"));
		int a = getRandom(l3.size() - 1);
		selectBox.selectByIndex(a);
		Thread.sleep(2000);

		Select selectBox1 = new Select(webdriver.findElement(By
				.xpath("//div[@id='divCustomDateRangemedline']/div/select[2]")));
		List<WebElement> l4 = (List<WebElement>) webdriver.findElements(By
				.xpath("//select[@name='fromYear']/option"));
		int b = getRandom(l4.size() - 1);
		selectBox1.selectByIndex(b);
		Thread.sleep(2000);

		String str3 =selectBox.getFirstSelectedOption().getText();
		String str4 = selectBox1.getFirstSelectedOption().getText();
		String str5 = str3.concat(" " + str4);
		System.out.println(str5);
		DateFormat formatter2 = new SimpleDateFormat("MM yyyy");
		Date dt1 = (Date) formatter2.parse(str5);
		System.out.println("From date: " + dt1);

		Select selectBox2 = new Select(webdriver.findElement(By
				.xpath("//div[@id='divCustomDateRangemedline']/div/select[3]")));
		List<WebElement> l5 = (List<WebElement>) webdriver.findElements(By
				.xpath("//select[@name='toMonth']/option"));
		int c = getRandom(l5.size() - 1);
		selectBox2.selectByIndex(c);
		Thread.sleep(2000);

		Select selectBox3 = new Select(webdriver.findElement(By
				.xpath("//div[@id='divCustomDateRangemedline']/div/select[4]")));
		List<WebElement> l6 = (List<WebElement>) webdriver.findElements(By
				.xpath("//select[@name='toYear']/option"));
		int d = getRandom(l6.size() - 1);
		selectBox3.selectByIndex(d);
		Thread.sleep(2000);

		String str6 = selectBox2.getFirstSelectedOption().getText();
		String str7 = selectBox3.getFirstSelectedOption().getText();
		String str8 = str6.concat(" " + str7);
		System.out.println(str8);
		DateFormat formatter3 = new SimpleDateFormat("MM yyyy");
		Date dt2 = (Date) formatter3.parse(str8);
		System.out.println("ToDate: " + dt2);

		webdriver.findElement(By.name("submitdate")).click();
		Thread.sleep(5000);
		resultDetails = medlinecustomrangedatemethod(webdriver, dt1, dt2);
		try{
		if (resultDetails.getErrorMessage().equalsIgnoreCase("No articles are present in this custom date range")) {
			return resultDetails;
		}}catch(Exception e){}
		if (!resultDetails.getFlag()) {
			return resultDetails;
		}
		List<WebElement> lp = (List<WebElement>) webdriver.findElements(By
				.xpath("//*[@id='btmpagination']/a"));
		if (lp.size() > 1) {
			for (int p = 1; p <= 1; p++) {
				Thread.sleep(2000);
				// List<WebElement>
				// l=(List<WebElement>)webdriver.findElements(By.xpath("//*[@id='btmpagination']"));
				webdriver
						.findElement(
								By.xpath("//div[@class='searchresults']/table/tbody/tr/td[2]/a["
										+ getRandom(lp.size()) + "]")).click();
				resultDetails = medlinecustomrangedatemethod(webdriver,
						dt1, dt2);
				if (!resultDetails.getFlag()) {
					return resultDetails;
				}
				try {
					if (webdriver.findElement(By.partialLinkText("next "))
							.isDisplayed()) {
						try {
							webdriver.findElement(By.partialLinkText("next "))
									.click();
							resultDetails.setFlag(true);
						} catch (Exception e) {
							System.out.println("next button is not clicked");
							resultDetails
									.setErrorMessage("next button is not clicked");
							resultDetails.setFlag(false);
							return resultDetails;
						}
						resultDetails = medlinecustomrangedatemethod(
								webdriver, dt1, dt2);
						if (!resultDetails.getFlag()) {
							return resultDetails;
						}
						try {
							if (webdriver
									.findElement(By.partialLinkText(" previous"))
									.isDisplayed()) {
								webdriver
										.findElement(By.partialLinkText(" previous"))
										.click();
								resultDetails.setFlag(true);
								Thread.sleep(2000);
							}
						} catch (Exception e) {
							System.out
									.println("previous button is not displayed or clicked");
							resultDetails.setFlag(false);
							return resultDetails;
						}
					}
				} catch (Exception e) {

					if (webdriver.findElement(By.partialLinkText(" previous"))
							.isDisplayed()) {
						webdriver.findElement(By.partialLinkText(" previous"))
								.click();
						resultDetails.setFlag(true);
						Thread.sleep(2000);
					}
				}
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

	/*
	 * For Search Functionality
	 */
	public ResultDetails search(WebDriver webdriver, String fieldText, String value,String fieldName) {		
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		System.out.println("field = "+field);
		value = GETVALUE(value);
		resultDetails.setFlag(true);
		
		try {
			EnterDataFileds edf = EnterDataFileds.valueOf(fieldType
					.toUpperCase());			
			switch (edf) {
		case RAC:
			try{
				Thread.sleep(5000);
              String num=webdriver.findElement(By.xpath("//div[@class='row']/h2/span")).getText();              num=num.substring(1,num.length()-1);
              num=num.replace(",", "");
             int n = Integer.parseInt(num);
                 if( n > 10)
            {
                	 resultDetails=randomarticleclick(webdriver);
                           } 
                 else{
          	  Thread.sleep(2000);
          	resultDetails=articleclick(webdriver);
          	            }
      			} catch(Exception e){
                	 System.out.println(e);
                	 resultDetails.setFlag(false);
                	 resultDetails.setErrorMessage(e.getMessage());
                     return resultDetails;
                 }
			
                 break;
		case MCC: 
			try{
				Thread.sleep(5000);
			 String num2=webdriver.findElement(By.xpath("//div[@class='row']/h2/span")).getText();
             num2=num2.substring(1,num2.length()-1);
             num2=num2.replace(",", "");
             int n2 = Integer.parseInt(num2);
                if( n2 > 10)
           {
                	resultDetails=randommedlinecheckbox(webdriver);
           }else{
         	
         	  Thread.sleep(2000);
         	 resultDetails=randommedlinecheckbox(webdriver);
           }
			}catch(Exception e){
				System.out.println("");
				resultDetails.setFlag(false);
				return resultDetails;
				
			}
			
			break;
		case ACM:
			try{
				Thread.sleep(5000);
              String num=webdriver.findElement(By.xpath("//div[@class='row']/h2/span")).getText();             
              num=num.substring(1,num.length()-1);
              num=num.replace(",", "");
               int n = Integer.parseInt(num);
                 if(n>10)
            {
                	 resultDetails=randomarticleclick(webdriver);
                	// return resultDetails;
                	
            } 
                 else{
          	  Thread.sleep(2000);
          	resultDetails=medlinearticleclick(webdriver);
          	            }
      			} catch(Exception e){
                	 System.out.println(e);
                	 resultDetails.setFlag(false);
                     return resultDetails;
                 }
			
                 break;
		case FTS:
			try
			{
				Thread.sleep(5000);
			if(webdriver.findElement(By.xpath(field+"/a")).isDisplayed()){
			 String num1=webdriver.findElement(By.xpath("//div[@class='row']/h2/span")).getText();             num1=num1.substring(1,num1.length()-1);
             num1=num1.replace(",", "");
             int n1 = Integer.parseInt(num1);
             	   if(n1>10){
              	   Thread.sleep(2000);
             	  resultDetails=filtertypesearch(webdriver,field);
              	   }else{
        		   Thread.sleep(2000);
        		   resultDetails=filtertypesearch(webdriver,field);
        	   }
						}
			}catch(Exception e){
				resultDetails.setFlag(true);
				System.out.println("filter news results are not present in this page");
				return resultDetails;
			}
           break;
		
		case RFS:
			 List<WebElement> lr=(List<WebElement>)webdriver.findElements(By.xpath("//div[@class='refinetype']/ul/li"));
             System.out.println(lr.size());
             int r=getRandom(lr.size());
            String str2=webdriver.findElement(By.xpath("//div[@class='refinetype']/ul/li["+r+"]/a")).getText();
            System.out.println(str2);
            webdriver.findElement(By.xpath("//div[@class='refinetype']/ul/li["+r+"]/a")).click();
            if(str2.contains("Slideshow")){
            	String str3="Slideshow";
         	    resultDetails=reffiltertypesearch(webdriver,str3);
            }
            else
         	   if(str2.contains("Clinical Case")){
         		  String str3="Clinical Case";
         		   resultDetails=reffiltertypesearch(webdriver,str3);
         	   }
         	   else
         		  
         		   if(str2.contains("Conditions")){
         			   try{
           			  resultDetails=totalarticle(webdriver, str2);
           			  List<WebElement> ll=(List<WebElement>)webdriver.findElements(By.xpath("//div[@class='refinetype']/ul[1]/li"));
           			int r1=getRandom(ll.size())-1;
                    String str4=webdriver.findElement(By.xpath("//div[@class='refinetype']/ul[1]/li["+r1+"]/a")).getText();
         			webdriver.findElement(By.xpath("//div[@class='refinetype']/ul[1]/li["+r1+"]/a")).click();
         			   Thread.sleep(5000);
                        if(str4.contains("Adult")){
                        	Thread.sleep(5000);
                        	str2=str4;
                         	resultDetails=totalarticle(webdriver, str2);
                        	return resultDetails;
                        }else
                        	str2=str4;
                        resultDetails=totalarticle(webdriver, str2);
                        return resultDetails;
         			   }catch(Exception e){
         				   resultDetails.setFlag(false);
         				   System.out.println(e.getMessage());
         				   resultDetails.setErrorMessage(e.getMessage());
         				  return resultDetails;
         			   }
         			   }
         		  
         		   else{
         			   Thread.sleep(2000);
         			   resultDetails=totalarticle(webdriver, str2);
         			 
         		   }
         		
            
            break;
            
		case SRV:
			try{
			resultDetails=resultsverify(webdriver, field, value);
			}catch(Exception e){
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage(e.getMessage());
				return resultDetails;
			}
			
			break;
		
		case MFS:
			try{
			 List<WebElement> lr1=(List<WebElement>)webdriver.findElements(By.xpath(field));
             int r1=getRandom(lr1.size());
            String str21=webdriver.findElement(By.xpath(field+"["+r1+"]/a")).getText();
            webdriver.findElement(By.xpath(field+"["+r1+"]/a")).click();
            Thread.sleep(2000);
            str2=str21;
			resultDetails=totalarticle(webdriver, str2);
			}catch(Exception e){
				System.out.println(e.getMessage());
				resultDetails.setFlag(false);
				return resultDetails;
			}
            break;
		
		case MDS:
			
			try{
				if(webdriver.findElement(By.xpath("//div[@id='divDateRefinementmedline']/span/table/tbody/tr/td/div/a")).isDisplayed()){
			webdriver.findElement(By.xpath("//div[@id='divDateRefinementmedline']/span/table/tbody/tr/td/div/a")).click();
	        List<WebElement> l=(List<WebElement>)webdriver.findElements(By.xpath("//div[@id='default_Date']/table/tbody/tr/td/ul/li"));
	        System.out.println(l.size());
	        int i=getRandom(l.size());
	    
	                if(i==6){
	                	resultDetails=medlinecustomrangedate(webdriver);
	                }
	                else
	        {
	         String str1=webdriver.findElement(By.xpath("//div[@id='default_Date']/table/tbody/tr/td/ul/li["+(i+1)+"]/a")).getText();
	        webdriver.findElement(By.xpath("//div[@id='default_Date']/table/tbody/tr/td/ul/li["+(i+1)+"]/a")).click();
	        System.out.println(str1);
	        if(str1.contains("Last 90 days")){
	        	int j=3;
	        	resultDetails=randomdatemethod(webdriver,j);
	      	        }
	        if(str1.contains("Last year")){
	            	 int j=12;
	            	 resultDetails=randomdatemethod(webdriver,j); 
	     	         }
	            if(str1.contains("Last 6 months")){
	            	int j=6;
	            	resultDetails=randomdatemethod(webdriver,j);
	    	            }
	           if(str1.contains("Last 5 years")){
	        	   int j=60;
	        	   resultDetails=randomdatemethod(webdriver,j);
	    	           }
	            if(str1.contains("Older than 5 years")){
	            	resultDetails=olderthan5years(webdriver);
	       	            }
	        }
			}
			}catch(Exception e){
				System.out.println(e.getMessage());
				resultDetails.setFlag(true);
				System.out.println("search by date field is not present in this page ");
				return resultDetails;
			}
				
	       break;
		
		case RDS: 
		try{
			if(webdriver.findElement(By.xpath("//div[@id='divDateRefinementdefault']/span/table/tbody/tr/td/div/a")).isDisplayed()){
		webdriver.findElement(By.xpath("//div[@id='divDateRefinementdefault']/span/table/tbody/tr/td/div/a")).click();
        List<WebElement> l=(List<WebElement>)webdriver.findElements(By.xpath("//div[@id='default_Date']/table/tbody/tr/td/ul/li"));
        System.out.println(l.size());
      int i=getRandom(l.size());
        
      if(i==6){
                	resultDetails=customrangedate(webdriver);
                      }
                else
        {
         String str1=webdriver.findElement(By.xpath("//div[@id='default_Date']/table/tbody/tr/td/ul/li["+(i+1)+"]/a")).getText();
        webdriver.findElement(By.xpath("//div[@id='default_Date']/table/tbody/tr/td/ul/li["+(i+1)+"]/a")).click();
        System.out.println(str1);
        if(str1.contains("Last 90 days")){
        	int j=3;
        	resultDetails=randomdatemethod(webdriver,j);
        	        }
        if(str1.contains("Last year")){
            	 int j=12;
            	 resultDetails=randomdatemethod(webdriver,j); 
            	         }
            if(str1.contains("Last 6 months")){
            	int j=6;
            	resultDetails=randomdatemethod(webdriver,j);
            	            }
           if(str1.contains("Last 5 years")){
        	   int j=60;
        	   resultDetails=randomdatemethod(webdriver,j);
        	             }
            if(str1.contains("Older than 5 years")){
            	resultDetails=olderthan5years(webdriver);
            	            }
        }
		}
		}catch(Exception e){
			resultDetails.setFlag(true);
			System.out.println("search by date field is not present in this page ");
			return resultDetails;
		}
			
       break;
		
		case LOA:
			 List<WebElement> l3=(List<WebElement>)webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver, field));
				
                String x=value;
                int nn = Integer.parseInt(x);
                System.out.println("x is" +x);
                System.out.println(nn);
                 if(l3.size()<=nn)
               {
			resultDetails.setFlag(true);
		               }
               else{
                  	   resultDetails.setFlag(false);
            	   System.out.println("no of links are not equal to required value");
            	   resultDetails.setErrorMessage("no of links are not equal to required value");
               }
		
			break;
		
		case CTS:
			try{
				if(webdriver.findElement(By.xpath("//table[@id='creditinputs']/tbody/tr/td[2]/table/tbody/tr/td/div/a")).isDisplayed()){
			webdriver.findElement(By.xpath("//table[@id='creditinputs']/tbody/tr/td[2]/table/tbody/tr/td/div/a")).click();
			Thread.sleep(2000);
			resultDetails=totalfilterlinks(webdriver, field);
				}
			}catch(Exception e){
				resultDetails.setFlag(true);
				System.out.println("Credit type search is not present in this page");
			}
			break;
		
		case SAC:
			
			List<WebElement> li=(List<WebElement>)webdriver.findElements(By.xpath(field));
			try{
			if(webdriver.findElement(By.xpath("//div[@id='more_int_results']/a")).getText().contains("More Featured")){
			webdriver.findElement(By.xpath("//div[@id='more_int_results']/a")).click();
			Thread.sleep(20000);
			List<WebElement> lii=(List<WebElement>)webdriver.findElements(By.xpath(field));
			if(lii.size()>3){
			resultDetails.setFlag(true);
			}else{
			resultDetails.setFlag(false);
			System.out.println("more featured links are not present");
			resultDetails.setErrorMessage("more featured links are not present");
			return resultDetails;
			}
			}}catch(Exception e){}
			System.out.println("Value ::"+value);
			int n=Integer.parseInt(value);
			List<WebElement> li2=(List<WebElement>)webdriver.findElements(By.xpath(field));
			for(int i=0;i<n;i++)
			{
			int a=getRandom(li2.size());
			String ltitle5=webdriver.findElement(By.xpath(field+"["+a+"]/a")).getText();
			while(ltitle5.equalsIgnoreCase("")){
			a=getRandom(li.size());
			ltitle5=webdriver.findElement(By.xpath(field+"["+a+"]/a")).getText();
			}
			webdriver.findElement(By.xpath(field+"["+a+"]/a")).click();
			Thread.sleep(20000);
			try{
			if(webdriver.findElement(By.xpath("//div[@id='invitestopright']/a")).isDisplayed()){
			webdriver.findElement(By.xpath("//div[@id='invitestopright']/a")).click();
			Thread.sleep(20000);
			}}catch(Exception e){}
			title=ltitle5;
			resultDetails=titleverify(webdriver, title);
			if(!resultDetails.getFlag()){
			return resultDetails;
			}}
break;
		
		case FSO:
			List<WebElement> l=(List<WebElement>)webdriver.findElements(By.xpath("//div[@class='refinetype']/ul/li"));
			  String[] result1 = new String[l.size()];
			  int []result;
			  result=new int[l.size()];
              for (int i = 0; i < l.size(); i++)  {
              result1[i] = webdriver.findElement(By.xpath("//div[@class='refinetype']/ul/li["+(i+1)+"]/a/span")).getText().toString();
              result1[i]=result1[i].substring(1,result1[i].length()-1);
              result1[i]=result1[i].replace(",","");
               result[i]=Integer.parseInt(result1[i]);
              }
              for(int i=0; i<l.size()-1;i++){
            	  
            	  if(result[i]>=result[i+1]){
            		  resultDetails.setFlag(true);
            	  }
            	  else{
            		  System.out.println("filters are not in sorted order for "+result[i]);
            	  resultDetails.setFlag(false);
            	  resultDetails.setErrorMessage("filters are not in sorted order for "+result[i]);
            	  return resultDetails;
            	  }
              }
              
              break;
		case WST:
			System.out.println(value);
			String[] sp=value.split("/");
			 String s1=webdriver.findElement(By.xpath("//div[@id='leftcol']/div[2]/span")).getText().toString();
             if(s1.equalsIgnoreCase("We're sorry, your search for "+sp[0]+" did not match any documents in "+sp[1]+".")){
             	System.out.println("MSG found");
             	resultDetails.setFlag(true);
             }else{
            	 System.out.println("MSG not found");
            	 resultDetails.setFlag(false);
            	 resultDetails.setErrorMessage("MSG not found");
             }
             break;
             
		case MST:
			String[] sp1=value.split("/");
			 String s11=webdriver.findElement(By.xpath("//div[@id='leftcol']/div[2]/span")).getText().toString();
             if(s11.equalsIgnoreCase("We're sorry, your search for "+sp1[0]+" did not match any documents in "+sp1[1]+".")){
             	System.out.println("MSG found");
             	resultDetails.setFlag(true);
             }else{
            	 System.out.println("MSG not found");
            	 resultDetails.setFlag(false);
            	 resultDetails.setErrorMessage("MSG not found");
                }
            try{
                   if(webdriver.findElement(By.xpath("//div[@id='leftcol']/div[2]/table/tbody/tr/td[2]/ul/li/a")).isDisplayed()){
            	 String s9=webdriver.findElement(By.xpath("//div[@id='leftcol']/div[2]/table/tbody/tr/td[2]/ul/li/a")).getText().toString();
            	 webdriver.findElement(By.xpath("//div[@id='leftcol']/div[2]/table/tbody/tr/td[2]/ul/li/a")).click();
            	 assertEquals(webdriver.findElement(By.id("searchtextinput")).getAttribute("value"),s9);
            	 resultDetails.setFlag(true);
             }else{
                   	 System.out.println("correct search terms are not displayed");
                   	 resultDetails.setFlag(true);
                   	resultDetails.setErrorMessage("MSG not found");
             }}catch(Exception e){
            	 resultDetails.setFlag(true);
            	 System.out.println("reference search terms are not present");
            	 resultDetails.setErrorMessage("reference search terms are not present");
             }
             break;
		case HTL:
			try{
			resultDetails=randomtagnamehorizontally(webdriver);
			}catch(Exception e){
				resultDetails.setFlag(false);
				System.out.println("MSG not found");
				resultDetails.setErrorMessage("MSG not found");
			}
			break;
		case VTL:
			try{
				resultDetails=randomtagname(webdriver);
			}catch(Exception e){
				resultDetails.setFlag(false);
				System.out.println("MSG not found");
				resultDetails.setErrorMessage("MSG not found");
			}
			break;
		case SBS:
			try{
				Thread.sleep(5000);
				if(webdriver.findElement(By.xpath("//table[@id='sortinputs']/tbody/tr/td[2]/div/a/span")).isDisplayed()){
			 String num2=webdriver.findElement(By.xpath("//div[@class='row']/h2/span")).getText();
              num2=num2.substring(1,num2.length()-1);
             num2=num2.replace(",", "");
              int n2 = Integer.parseInt(num2);
                if( n2 > 10)
           {
                	resultDetails=sortbysearch(webdriver);
           }
                else{
         	webdriver.findElement(By.linkText("Relevance")).click();
            	webdriver.findElement(By.linkText("" +
            			"Publish Date")).click();
            	if(webdriver.getTitle().contains("MEDLINE")){
            		Thread.sleep(3000);
            		resultDetails=medlinepublishdate(webdriver);
            	}else{
         	  Thread.sleep(2000);
         	 resultDetails=publishdate(webdriver);
            	}
           }
			}
			}catch(Exception e){
				resultDetails.setFlag(true);
				System.out.println("sort by search field is not present in this page ");
				return resultDetails;
			}
			
				
			break;
				}
				return resultDetails;			
			} 
			catch (Exception e) {
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("unable to search the field  ::"+e.getMessage());
				return resultDetails;
			}
		
		}
	
	public static int getRandom(int i) {
		int r = 0;
		while (r == 0) {
			r = (int) ((Math.random()) * i) + 1;
		}
		return r;
	}
}