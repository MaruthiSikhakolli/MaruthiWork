package com.java;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.xml.sax.SAXException;
import com.java.Objects.ResultDetails;
//import com.opera.core.systems.scope.protos.SelftestProtos.SelftestResult.Result;

public class SINGLEPAGE2 {
	 ResultDetails resultDetails = new ResultDetails();
	 OracleDB ODB=new OracleDB();
	 
		Statement st=ODB.stmt;	ResultSet rs=null;
		
	public enum SinglePage_Questions{
		ELG,ERP,PRP,PGE, PGL,PRG,EPG,PRQ, PRF, PNR, ALL, FEW, FME, BRE, BRW, FMW, FMG, BNT,
		 EVL, CRD, LEN	
	}
	public enum CmeEligibility {
		cme,nurse,pharma,psych,OHC,loc,np_ce,pa_ce,cmle
		}
	public static void select(WebDriver webdriver,String index){
		Select sel=new Select(webdriver.findElement(By.xpath("//select[@name='pCredits']")));
		sel.selectByIndex(Integer.parseInt(index));
		}
		
public ResultDetails SINGLEPAGE(WebDriver webdriver, String field, String value) throws SQLException, ClassNotFoundException
		{
	System.out.println("field= " + field + " value= " + value);
	SinglePage_Questions spq = SinglePage_Questions.valueOf(field.substring(0, 3));
	field = field.substring(3, field.length());
	try{
		System.out.println("THIS IS A SINGLEPAGE");	   	
		switch(spq){
		case LEN:
			//webdriver.getCurrentUrl();
			int li=webdriver.findElements(By.xpath("//div[@id='edu_right_col']/ul/li")).size();
			
			//int li=webdriver.findElements(By.xpath(""+field+"")).size();
			//System.out.println("li="+li);
			try{
			try{
				for(int i=1;i<=li;i++){
					String s=webdriver.findElement(By.xpath("//div[@id='edu_right_col']/ul/li["+i+"]/a")).getText();
					WebDriverUtils.waitForPageToLoad(webdriver, "10000");
					System.out.println(s+" Link is present at right rail links");
					}
				}catch(Exception e){
					//System.out.println(e.getMessage());
					}
int l=webdriver.findElements(By.xpath("//div[@id='article_tools']/ul/li")).size();
		//int l=webdriver.findElements(By.xpath(""+value+"")).size();
System.out.println("l="+l);
			try{
				for(int i=1;i<=l;i++){
					String s=webdriver.findElement(By.xpath("//div[@id='edu_right_col']/ul/li["+i+"]/a")).getText();
					WebDriverUtils.waitForPageToLoad(webdriver, "10000");
					System.out.println(s+" Link is present at Bottom");
					}
				}catch(Exception e){
					//System.out.println(e.getMessage());
					}
		
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
		}catch(Exception e){
			   System.out.println("LEN catch block");
				resultDetails.setFlag(false);
				return resultDetails;
			}
				
			break;
		case ERP:// You have already earned credit for this activity.
			resultDetails=ArticleEarned_SINGLEPAGE(webdriver,rs,st,value);
			break;
		case PRP://Pre-Activity link is not displayed for other articles in the program once answered
			resultDetails=ProgramPreAct_SINGLEPAGE(webdriver,rs,st,value);
			break;
		case PGE://open the program click Earn Cme then again open program
			resultDetails=ProgramEarnCME_SINGLEPAGE(webdriver,rs,st,value);
			break;
		case EPG:
			resultDetails=ExpiredProgram_SINGLEPAGE(webdriver,rs,st,value);
			break;
		case PRG:			
			resultDetails=Program_SINGLEPAGE(webdriver,rs,st,value);
			break;
		case CRD:
			//Credits earned message
			resultDetails=Credits_SINGLEPAGE(webdriver,rs,st);
			break;
		case ELG:	//Eligibility
			resultDetails=Eligibility_credits_SINGLEPAGE(webdriver,value,rs,st);
			break;
			
	    case PRQ:
	    	if(webdriver.getTitle().equalsIgnoreCase("Pre-Activity Questionnaire"))
			{
			System.out.println("This page is PreActivity with Required questionaaire.");
			resultDetails=Pre_SINGLEPAGE(webdriver,rs,st);
			}
			else{
				System.out.println("Its not a prerequired form.");
			}
			break;			
	    case PRF:// To complete CME WF  for pre req
			resultDetails=PRF_SINGLEPAGE(webdriver,rs,st,field,value);
			break;		
	    case PNR:
			System.out.println("This page is PreActivity with Not Required questionaaire.");
			resultDetails=PreNotRequiredAll_SINGLEPAGE(webdriver,rs,st);
			break;		
	    case ALL:
			System.out.println("Internal Bottom required form");
			resultDetails=AllInternal_SINGLEPAGE(webdriver,rs,st);
			break;
	    case FEW:
			System.out.println("Internal Bottom required form(few)");
			resultDetails=fewanswers_SINGLEPAGE(webdriver,rs,st);
			break;
		case FME:
			//ANSWER THE PREACT IF THERE
			//CLICK "SAVE AND PROCEED" WITHOUT ANSWERING IN POST
			//System.out.println("For score and save proceed");
			resultDetails=ForScoreSave_SINGLEPAGE(webdriver,rs,st);
			break;
		case BRE:
			//IN BOTTOM REQUIRED CLICK THE "SAVE AND PROCEED" WITHOUT ANSWERING 
			System.out.println("Btm req without answer ");
			resultDetails=BTM_NO_ANS_SINGLEPAGE(webdriver);
			break;
		case BRW:
			//IN BOTTOM REQUIRED CLICK THE "SAVE AND PROCEED" WITH WRONG  ANSWERING 
			System.out.println("Btm req with WRONG answer");
			resultDetails=BTM_REQ_WRNG_ANS_SINGLEPAGE(webdriver,rs,st);
			break;
		case FMW:
			System.out.println("For score with wrong answers");
			resultDetails=ForScoreWrong_SINGLEPAGE(webdriver,rs,st);
			break;
		case FMG:
			//NOT NEEDED
			// 70% error message 
			//resultDetails=Message(webdriver);
			System.out.println("For Score messages");
			resultDetails=Score_SINGLEPAGE(webdriver,rs,st);
			break;
		case BNT:
			//Internal bottom not required
			//No question answered directly click Save And Proceed
			resultDetails=IntNotRequired_SINGLEPAGE(webdriver,rs,st);
			break;
		
		case EVL:
			//Evaluation with required
			resultDetails=Evaluation_SINGLEPAGE(webdriver,value,rs,st);
			break;	
		
	}//SWITCH CASE			    				        	
			}catch(Exception e1){
			
					resultDetails.setFlag(false);
					return resultDetails;}
			
				return resultDetails;

			}	//SINGLEPAGE
public  ResultDetails Pre_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
	 {

	   String s=webdriver.getCurrentUrl();
	   System.out.println(s); 		  
	   String f="[_\\/\\#]+";;String[] temp = s.split(f);
	   for(int i =0; i < temp.length ; i++)
		System.out.println("temp["+i+"]="+temp[i]);	
		int article_id1=Integer.parseInt(temp[8]);
		System.out.println(article_id1);
		
		ConnectionDB();
		rs=st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
		rs.next();
		int questionnaire_id=rs.getInt(1);	
		 try{
 			int form_type_id=2;
 			System.out.println("In Pre-Questionnaire Form.....");
 			System.out.println("form_type_id(Pre-Activity)="+form_type_id);
 			
 			ConnectionDB();
 			rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
 			rs.next();
 			int form_id=rs.getInt(1);
 			System.out.println("form_id(Pre-Activity)="+form_id);
 			
 			ConnectionDB();
 			rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qd.IS_REQUIRED=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
 			if(!rs.next()){
 				System.out.println("These questions are not required questions in PreAcitivity form");
 				webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();		
 			}
 			else
 			{
 				try{
 	 				webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();		
 	 				String error="All questions must be answered in order to proceed.";
 	 				if (webdriver.getPageSource().toLowerCase().trim()
 	 						.contains(error.toLowerCase().trim()))
 	 				{
 	 					System.out.println("Message="+error);
 	 					Thread.sleep(5000);
 	 				}	
 				
 	int choice_id[]=new int[45];
 	int i=0,j=0;
 System.out.println("This Pre Activity with Required Questions ");
 ConnectionDB();
 rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qd.IS_REQUIRED=1 and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
 						
 	while(rs.next()){
 	choice_id[i]=rs.getInt(1);
 	//System.out.println("choice_id["+i+"]="+choice_id[i]);
 i++;j++;
 }			
 				
for(i=0;i<j;i++){
	webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
	}
 				
webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();		
	System.out.println("pre -submit");
						
 				}catch(Exception e3){}
 				resultDetails.setFlag(true);
 	 			resultDetails.setErrorMessage("");
 	 		   }
		 }catch(Exception e){ System.out.println("Pre required catch block");
				resultDetails.setFlag(false);
				return resultDetails;
		   }
			return resultDetails;

		}//pre
public  ResultDetails PRF_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st,String field,String value) throws SQLException, InterruptedException, ClassNotFoundException
{
 try{	 
try{
if(webdriver.getTitle().equalsIgnoreCase("Pre-Activity Questionnaire"))
{
 String s=webdriver.getCurrentUrl();
   System.out.println(s);
	
	String f="[_\\/\\#\\?]+";String[] temp = s.split(f);
	for(int i =0; i < temp.length ; i++)
	System.out.println("temp["+i+"]="+temp[i]);	
	int article_id1=Integer.parseInt(temp[9]);
	
	//int article_id1=Integer.parseInt(temp[8]);
	System.out.println(article_id1);
	ConnectionDB();
	rs=st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
	rs.next();
	int questionnaire_id=rs.getInt(1);

int form_type_id=2;
System.out.println("********************In Pre-Questionnaire Form****************");
System.out.println("form_type_id(Pre-Activity)="+form_type_id);
ConnectionDB();
rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
rs.next();
int form_id=rs.getInt(1);
System.out.println("form_id(Pre-Activity)="+form_id);
ConnectionDB();
rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and  qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
if(!rs.next()){
System.out.println("These questions are not required questions in PreAcitivity form");		
}
else
{
int choice_id[]=new int[45];int i=0,j=0;														
rs.beforeFirst();				
while(rs.next()){
choice_id[i]=rs.getInt(1);						
i++;j++;
}			
for(i=0;i<j;i++)
{				
webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
}
webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();		
System.out.println("pre -submit");											 
}				 
	 }
}catch(Exception e){
	System.out.println("e4567");
		 System.out.println(e.getMessage());
	 }
	 
try{
	System.out.println("****************INTERNAL PAGE******************");
	if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
	{
		webdriver.findElement(By.linkText("Continue to Activity")).click();
		System.out.println("Continue to Activity clicked");
		WebDriverUtils.waitForPageToLoad(webdriver, "5000");
	}
	}catch(Exception e){}
try{
	if(webdriver.findElement(By.linkText("Transcript")).isDisplayed())
	{
		System.out.println("Transcript displayed");
		webdriver.findElement(By.linkText("Transcript")).click();
		Thread.sleep(2000);
	}}catch(Exception e){}
try{
	try{
		if(webdriver.findElement(By.linkText("Earn CME/CE Credit �")).isDisplayed()){					
		webdriver.findElement(By.linkText("Earn CME/CE Credit �")).click();
		System.out.println("Earn CME clicked...........Moving to post-form");
		WebDriverUtils.waitForPageToLoad(webdriver, "20000");
		}
	}catch(Exception e){}
	try{
		if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
		{
		webdriver.findElement(By.partialLinkText("Earn CME Credit")).click();
		System.out.println("Earn CME clicked...Moving to post-form");
		}
		WebDriverUtils.waitForPageToLoad(webdriver, "20000");
	}catch(Exception e){}
		try{//Earn Credits>>
			if(webdriver.findElement(By.xpath("//span[@id='cme_btns']/a[2]")).isDisplayed())
			{
			webdriver.findElement(By.xpath("//span[@id='cme_btns']/a[2]")).click();
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");	
			}
		}catch(Exception e){}
}catch(Exception e){
	System.out.println("catch earn");
	Article_single a=new Article_single();
	resultDetails=a.FetchArticle(webdriver,field,value);
	}
try{
	String msg="You must achieve a minimum score of 70% to earn credit for this activity.";
	if((webdriver.getPageSource().toLowerCase().trim()
			.contains(msg.toLowerCase().trim())))
	{
		System.out.println("You must achieve a minimum score of 70% to earn credit for this activity.");
		WebDriverUtils.waitForPageToLoad(webdriver, "20000");
	}
}catch(Exception e){}

		String s=webdriver.getCurrentUrl();
		System.out.println("URL::"+s);
			
		String f="[_\\/\\#\\?]+";String[] temp = s.split(f);
		for(int k =0; k < temp.length ; k++)
		System.out.println("temp["+k+"]="+temp[k]);	
		int questionnaire_id=Integer.parseInt(temp[4]);
		
		System.out.println(questionnaire_id);	
	try{
		int form_type_id=3;	
		System.out.println("form_type_id(Interna@post)="+form_type_id);
		
		try{
			System.out.println("In Connection");
			if(ODB.connection.isClosed())
			{
				System.out.println("IN connection_IF");

				ODB=new OracleDB(); 
				st=ODB.stmt;	
				rs=null;
			}}catch(Exception e)
			{		System.out.println("IN connection_Catch");

				System.out.println(e.getLocalizedMessage());
				ODB=new OracleDB(); 
				st=ODB.stmt;	
				rs=null;
			}
	rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
	
	if(rs.next())//Internel questions ansewered in post form
	{
		int choice_id[]=new int[45];
		int i=0,j=0;
		int form_id=rs.getInt(1);
		System.out.println("form_id(Interna@post)="+form_id);
		
		ConnectionDB();//is_correct=1
		rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.is_correct=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
		
		if(rs.next())
		{	
			rs.beforeFirst();
			while(rs.next())
			{
				choice_id[i]=rs.getInt(1);
				//System.out.println("choice_id["+i+"]="+choice_id[i]);
				i++;j++;
			}	
			
			for(i=0;i<j;i++)
			{											
			webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();						
			}		
			webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();		
		}//if
		else//display order =1
		{
			ConnectionDB();
			rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
			if(rs.next())
			{
				rs.beforeFirst();	
				while(rs.next())
				{
					choice_id[i]=rs.getInt(1);
					//System.out.println("choice_id["+i+"]="+choice_id[i]);
					i++;j++;
				}						
				for(i=0;i<j;i++)
				{									
					webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();			
				}				
				webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();						
			}			
		}				
					
	}
	}
	catch(Exception e){
		//Post questions answered @ post form
		int form_type_id=4;
		int choice_id[]=new int[45];
		int i=0,j=0;
		
		try{
			System.out.println("In Connection");
			if(ODB.connection.isClosed())
			{
				System.out.println("IN connection_IF");

				ODB=new OracleDB(); 
				st=ODB.stmt;	
				rs=null;
			}}catch(Exception e1)
			{		System.out.println("IN connection_Catch");

				System.out.println(e1.getLocalizedMessage());
				ODB=new OracleDB(); 
				st=ODB.stmt;	
				rs=null;
			}
		rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);	
		rs.next();
		
		int form_id=rs.getInt(1);
		System.out.println("form_id(@Post)="+form_id);
		
		ConnectionDB();//is_correct=1
		rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.is_correct=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
		
		if(rs.next())
		{	
			rs.beforeFirst();
			while(rs.next())
			{
				choice_id[i]=rs.getInt(1);
				//System.out.println("choice_id["+i+"]="+choice_id[i]);
				i++;j++;
			}	
			
			for(i=0;i<j;i++)
			{											
			webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();						
			}		
			webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();		
		}//if iscoorct=1
		else//display order =1
		{
			ConnectionDB();
			rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
			if(rs.next())
			{
				rs.beforeFirst();	
				while(rs.next())
				{
					choice_id[i]=rs.getInt(1);
					//System.out.println("choice_id["+i+"]="+choice_id[i]);
					i++;j++;
				}						
				for(i=0;i<j;i++)
				{									
					webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();			
				}				
				webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();						
			}			
		}				
	}//catch @post form
	

	try{
		//proceed to evaluation
		if(webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).isDisplayed())
		{	
			webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).click();
			System.out.println("Proceed to Evaluation is clicked @prf");
			WebDriverUtils.waitForPageToLoad(webdriver, "30000");
		}
	}catch(Exception e){
		System.out.println("e2");
		e.printStackTrace();
	}
resultDetails.setFlag(true);
resultDetails.setErrorMessage("");
   }catch(Exception e){
	   e.printStackTrace();
	   System.out.println(e.getMessage());
	   System.out.println("Pre required CME WF catch block");
		resultDetails.setFlag(false);
		return resultDetails;
   }
 return resultDetails;
}//PRF
public  ResultDetails PreNotRequiredAll_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st) throws SQLException, ClassNotFoundException 
{   
try{
	 if(webdriver.getTitle().equalsIgnoreCase("Pre-Activity Questionnaire"))
		 {
		 String s=webdriver.getCurrentUrl();
		 System.out.println(s);
		   
		String f="[_\\/\\#]+";;String[] temp = s.split(f);
		for(int i =0; i < temp.length ; i++)
		System.out.println("temp["+i+"]="+temp[i]);	
		int article_id1=Integer.parseInt(temp[8]);
		System.out.println(article_id1);
		ConnectionDB();
		rs=st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
		rs.next();
		int questionnaire_id=rs.getInt(1);
		
		//System.out.println(questionnaire_id);
	  
	    int form_type_id=2;
		System.out.println("In Pre-Questionnaire Form.....");
		System.out.println("form_type_id(Pre-Activity)="+form_type_id);
		ConnectionDB();
		rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
		rs.next();
		int form_id=rs.getInt(1);
		System.out.println("form_id(Pre-Activity)="+form_id);
		ConnectionDB();
		rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qd.IS_REQUIRED=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
		if(!rs.next()){
		System.out.println("This Pre Not_Required form ");
		//rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.is_correct=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
		webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();		
		System.out.println("In PreActivity Form save and proceed clicked");
		}
		else{
			System.out.println("This PreActivity form with Required questionnaire.");
		}
		
	   }resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");
	   }
	   catch(Exception e){
		   System.out.println("Pre not required catch block");
			resultDetails.setFlag(false);
			return resultDetails;
	   }
	return resultDetails;
}//PreNotRequiredAll
public  ResultDetails AllInternal_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
{
	String s=webdriver.getCurrentUrl();
	System.out.println(s);
	
	String f="[_\\/\\#]+";;String[] temp = s.split(f);
	for(int i =0; i < temp.length ; i++)
	    System.out.println("temp["+i+"]="+temp[i]);	
	int article_id1=Integer.parseInt(temp[3]);
	System.out.println("Article="+article_id1);
	ConnectionDB();	
	rs=st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
	rs.next();
	int questionnaire_id=rs.getInt(1);
	
	System.out.println("questionnaire_id="+questionnaire_id);
	try{
	int choice_id[]=new int[45], i=0,j=0;
	int form_type_id=3;	
	WebDriverUtils.waitForPageToLoad(webdriver, "30000");
	System.out.println("In Internal Form.....");
	System.out.println("form_type_id(internal)="+form_type_id);
	if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
	{
		System.out.println("internal form");
		webdriver.findElement(By.linkText("Continue to Activity")).click();
		Thread.sleep(5000);
	}
	try{
	if(webdriver.findElement(By.linkText("Transcript")).isDisplayed())
	{
		System.out.println("Transcript displayed");
		webdriver.findElement(By.linkText("Transcript")).click();
		Thread.sleep(2000);
	}}catch(Exception e){}
	try{
		if(webdriver.findElement(By.xpath("//input[@name='submitbutton']")).isDisplayed())
		{ConnectionDB();
		rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
		rs.next();
		int form_id=rs.getInt(1);
		System.out.println("form_id(inter_btr)="+form_id);
		
		ConnectionDB();
		rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qd.IS_REQUIRED=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
		if(!rs.next()){
			System.out.println("bottom not required");
		}
		else{//is_required=1 and is_correct=1
			ConnectionDB();
			rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qd.IS_REQUIRED=1 and qc.is_correct=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
				if(!rs.next())
				{
					//System.out.println("123");
					ConnectionDB();
					rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qd.IS_REQUIRED=1 and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
					while(rs.next()){
						choice_id[i]=rs.getInt(1);
						System.out.println("choice_id["+i+"]="+choice_id[i]);
					i++;j++;
					}
				}
				else
				{
					rs.beforeFirst();
					while(rs.next()){
						choice_id[i]=rs.getInt(1);
						System.out.println("choice_id["+i+"]="+choice_id[i]);
					i++;j++;
					}
				}
				for(i=0;i<j;i++)
				{	
					webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
				
				}
		}
		webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();		
		WebDriverUtils.waitForPageToLoad(webdriver, "30000");
		System.out.println("Submit is clicked @intrnl");
		try{
			//proceed to evaluation
		if(webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).isDisplayed())
		{	
		webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).click();
		WebDriverUtils.waitForPageToLoad(webdriver, "30000");
		}
		}catch(Exception e){
			/*System.out.println("e2");
			System.out.println(e.getMessage());*/
		}
	}
	}catch(Exception e){
		/*System.out.println("e1");
		System.out.println(e.getMessage());*/
	}
	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");
}catch(Exception e){
	System.out.println("This is not Internal Bottom required form");
	//System.out.println(e.getMessage());
	resultDetails.setFlag(false);
	return resultDetails;
		}
	return resultDetails;

}//ALL
public  ResultDetails fewanswers_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
{
	String s=webdriver.getCurrentUrl();
	System.out.println(s);
	
	String f="[_\\/\\#]+";;String[] temp = s.split(f);
	for(int i =0; i < temp.length ; i++)
	    System.out.println("temp["+i+"]="+temp[i]);	
	int article_id1=Integer.parseInt(temp[3]);
	System.out.println(article_id1);
	ConnectionDB();
	rs=st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
	rs.next();
	int questionnaire_id=rs.getInt(1);
	
	System.out.println(questionnaire_id);
try{
	int choice_id[]=new int[45], i=0,j=0;
	int form_type_id=3;	
	WebDriverUtils.waitForPageToLoad(webdriver, "30000");
	System.out.println("In Internal Form.....");
	System.out.println("form_type_id(internal)="+form_type_id);
	if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
	{
		System.out.println("internal form");
		webdriver.findElement(By.linkText("Continue to Activity")).click();
	}try{		
	if(webdriver.findElement(By.linkText("Transcript")).isDisplayed())
	{
		System.out.println("Transcript displayed");
		webdriver.findElement(By.linkText("Transcript")).click();
	}}
	catch(Exception e){}
	if(webdriver.findElement(By.name("submitbutton")).isDisplayed())
	{ 
		ConnectionDB();
		rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
		rs.next();
		int form_id=rs.getInt(1);
		System.out.println("form_id(inter_btr)="+form_id);
		ConnectionDB();	
rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
while(rs.next()){
	choice_id[i]=rs.getInt(1);
						
	i++;j++;
}
}

for(i=0;i<j;i++)
{				
webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();					
}

webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();	
Thread.sleep(10000);

try{
//proceed to evaluation
if(webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).isDisplayed())
{	
System.out.println("Proceed to Evaluation");
webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).click();
System.out.println("Proceed to Evaluation is clicked @prf");
WebDriverUtils.waitForPageToLoad(webdriver, "30000");
}
}catch(Exception e){
System.out.println("e2");
//System.out.println(e.getMessage());
}



resultDetails.setFlag(true);
resultDetails.setErrorMessage("");
}catch(Exception e){
	System.out.println("This is not Internal Bottom required form");
	resultDetails.setFlag(false);
	return resultDetails;
}
return resultDetails;

}//few
public  ResultDetails ForScoreSave_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
{
try{
	try{
	if(webdriver.getTitle().equalsIgnoreCase("Pre-Activity Questionnaire"))
 {
	String s=webdriver.getCurrentUrl();
  System.out.println(s);
					
String f="[_\\/\\#]+";;String[] temp = s.split(f);
for(int i =0; i < temp.length ; i++)
	System.out.println("temp["+i+"]="+temp[i]);	
int article_id1=Integer.parseInt(temp[8]);
System.out.println(article_id1);					
ConnectionDB();		
rs=st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
rs.next();
int questionnaire_id=rs.getInt(1);					
int form_type_id=2;
System.out.println("In Pre-Questionnaire Form.....");
System.out.println("form_type_id(Pre-Activity)="+form_type_id);
ConnectionDB();
rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
rs.next();
int form_id=rs.getInt(1);
System.out.println("form_id(Pre-Activity)="+form_id);
ConnectionDB();
rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and  qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
if(!rs.next()){
System.out.println("These questions are not required questions in PreAcitivity form");		
}					
					
else
{
	int choice_id[]=new int[45];int i=0,j=0;														
	rs.beforeFirst();				
		while(rs.next()){
			choice_id[i]=rs.getInt(1);						
			i++;j++;
		}			
for(i=0;i<j;i++)				
{				
	webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
}				
			
webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();		
System.out.println("pre -submit");											 
}							 
 }
	}catch(Exception e)
	{
		System.out.println("e4567");
		System.out.println(e.getMessage());
	}
		 					
		String s=webdriver.getCurrentUrl();
		System.out.println(s);
		String f="[_\\/\\#]+";;String[] temp = s.split(f);
		for(int i =0; i < temp.length ; i++)
		    System.out.println("temp["+i+"]="+temp[i]);	
		int article_id1=Integer.parseInt(temp[3]);
		System.out.println(article_id1);
		
		if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
		{
			System.out.println("internal form");
			webdriver.findElement(By.linkText("Continue to Activity")).click();
			Thread.sleep(5000);
		}
		
		try{
			if(webdriver.findElement(By.linkText("Earn CME/CE Credit �")).isDisplayed()){					
			webdriver.findElement(By.linkText("Earn CME/CE Credit �")).click();
			System.out.println("Earn CME clicked...........Moving to post-form");
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");
			}
		}catch(Exception e){
			//System.out.println(e.getMessage());
			//System.out.println("e3");
			}
		try{
			if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
			{
			webdriver.findElement(By.partialLinkText("Earn CME Credit")).click();
			System.out.println("Earn CME clicked...Moving to post-form");
			}
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");
		}catch(Exception e){
			System.out.println("Earn cme  clicked");
			System.out.println(e.getMessage());
			}
		
		try{
			if(webdriver.findElement(By.xpath("//input[@name='submitbutton']")).isDisplayed())
			{
				webdriver.findElement(By.xpath("//input[@name='submitbutton']")).click();	
			}
	}catch(Exception e){}
		
		try{
			String msg="All questions must be answered in order to proceed.";
		if((webdriver.getPageSource().toLowerCase().trim()
				.contains(msg.toLowerCase().trim())))
		{
			System.out.println("All questions must be answered in order to proceed.");
			
		}
		}catch(Exception e){}	
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");
	
	}catch(Exception e)
	{
		resultDetails.setFlag(false);
		return resultDetails;
	}
return resultDetails;
}//ForScoreSave
public  ResultDetails BTM_NO_ANS_SINGLEPAGE(WebDriver webdriver) throws SQLException, InterruptedException, ClassNotFoundException
{
try{
		String s=webdriver.getCurrentUrl();
		System.out.println(s);
		
		String f="[_\\/\\#]+";;String[] temp = s.split(f);
		for(int i =0; i < temp.length ; i++)
		    System.out.println("temp["+i+"]="+temp[i]);	
		int article_id1=Integer.parseInt(temp[3]);
		System.out.println(article_id1);
		
		if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
		{
			System.out.println("internal form");
			webdriver.findElement(By.linkText("Continue to Activity")).click();
			Thread.sleep(5000);
		}
		
		try{
			if(webdriver.findElement(By.xpath("//input[@name='submitbutton']")).isDisplayed())
			{
				webdriver.findElement(By.xpath("//input[@name='submitbutton']")).click();
				System.out.println("submit button clicked.....");
			}
	}catch(Exception e){}
		
		try{
			//verify the message "All questions must be answered in order to proceed."	
			String msg="All questions must be answered in order to proceed.";
		if((webdriver.getPageSource().toLowerCase().trim()
				.contains(msg.toLowerCase().trim())))
		{
			System.out.println("All questions must be answered in order to proceed.");
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");
		}
		}catch(Exception e){}
		
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");
	}catch(Exception e)
	{
		resultDetails.setFlag(false);
		return resultDetails;
	}
	return resultDetails;
}//BTM_NO_ANS
public  ResultDetails BTM_REQ_WRNG_ANS_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
{
try{
		String s=webdriver.getCurrentUrl();
		System.out.println(s);
		
		String f="[_\\/\\#]+";;String[] temp = s.split(f);
		for(int i =0; i < temp.length ; i++)
		    System.out.println("temp["+i+"]="+temp[i]);	
		int article_id1=Integer.parseInt(temp[3]);
		System.out.println(article_id1);
		try{
		if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
		{
			System.out.println("internal form");
			webdriver.findElement(By.linkText("Continue to Activity")).click();
			Thread.sleep(5000);
		}
		}catch(Exception e){}
		
		rs= st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
		rs.next();
		int questionnaire_id=rs.getInt(1);
		System.out.println("questionnaire_id="+questionnaire_id);
		int choice_id[]=new int[45], i=0,j=0;
		int form_type_id;
		int form_id=0;
	
	form_type_id=3;
	ConnectionDB();
	rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
	if(rs.next())
	{
		form_id=rs.getInt(1);
		System.out.println("form_id(inter_btm_questions)="+form_id);
		ConnectionDB();	
		rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.display_order=2 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
		while(rs.next())
		{
		choice_id[i]=rs.getInt(1);
		//System.out.println("choice_id["+i+"]="+choice_id[i]);
		i++;j++;
		}
		for(i=0;i<j;i++)
		{	
			webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
			}
	}
		
	try{
		if(webdriver.findElement(By.xpath("//input[@name='submitbutton']")).isDisplayed())
		{
			webdriver.findElement(By.xpath("//input[@name='submitbutton']")).click();
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");
			System.out.println("submit button clicked.....");
		}
}catch(Exception e){}
	try{
		
		String msg="You must achieve a minimum score of 70% to earn credit for this activity.";
	if((webdriver.getPageSource().toLowerCase().trim()
			.contains(msg.toLowerCase().trim())))
	{
		System.out.println("You must achieve a minimum score of 70% to earn credit for this activity.");
		WebDriverUtils.waitForPageToLoad(webdriver, "20000");
	}
	}catch(Exception e){}

	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");
}catch(Exception e)
{
	resultDetails.setFlag(false);
	return resultDetails;
}
return resultDetails;		
}//BTM_REQ_WRNG_ANS
public  ResultDetails ForScoreWrong_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
{
	try{
		try{
			if(webdriver.getTitle().equalsIgnoreCase("Pre-Activity Questionnaire"))
		 {
			String s=webdriver.getCurrentUrl();
		  System.out.println(s);
							
		String f="[_\\/\\#]+";;String[] temp = s.split(f);
		for(int i =0; i < temp.length ; i++)
			System.out.println("temp["+i+"]="+temp[i]);	
		int article_id1=Integer.parseInt(temp[8]);
		System.out.println(article_id1);					
		ConnectionDB();			
		rs=st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
		rs.next();
		int questionnaire_id=rs.getInt(1);					
		int form_type_id=2;
		System.out.println("In Pre-Questionnaire Form.....");
		System.out.println("form_type_id(Pre-Activity)="+form_type_id);
		ConnectionDB();
		rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
		rs.next();
		int form_id=rs.getInt(1);
		System.out.println("form_id(Pre-Activity)="+form_id);
		ConnectionDB();
		rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and  qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
		if(!rs.next()){
		System.out.println("These questions are not required questions in PreAcitivity form");		
		}					
							
		else
		{
			int choice_id[]=new int[45];int i=0,j=0;														
			rs.beforeFirst();				
				while(rs.next()){
					choice_id[i]=rs.getInt(1);						
					i++;j++;
				}			
		for(i=0;i<j;i++)				
		{				
			webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
		}				
					
		webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();		
		System.out.println("pre -submit");											 
		}							 
		 }
			}catch(Exception e)
			{
				System.out.println("e4567");
				System.out.println(e.getMessage());
			}
		
		String s=webdriver.getCurrentUrl();
		System.out.println(s);
		String f="[_\\/\\#]+";;String[] temp = s.split(f);
		for(int i =0; i < temp.length ; i++)
		    System.out.println("temp["+i+"]="+temp[i]);	
		int article_id1=Integer.parseInt(temp[3]);
		System.out.println(article_id1);
		ConnectionDB();
		rs=st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
		rs.next();
		int questionnaire_id=rs.getInt(1);
		
		if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
		{
			System.out.println("internal form");
			webdriver.findElement(By.linkText("Continue to Activity")).click();
			Thread.sleep(5000);
		}
		
		try{
			if(webdriver.findElement(By.linkText("Earn CME/CE Credit �")).isDisplayed()){					
			webdriver.findElement(By.linkText("Earn CME/CE Credit �")).click();
			System.out.println("Earn CME clicked...........Moving to post-form");
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");
			}
		}catch(Exception e){
			//System.out.println(e.getMessage());
			//System.out.println("e3");
			}
		try{
			if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
			{
			webdriver.findElement(By.partialLinkText("Earn CME Credit")).click();
			System.out.println("Earn CME clicked...Moving to post-form");
			}
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");
		}catch(Exception e){
			System.out.println("Earn cme  clicked");
			//System.out.println(e.getMessage());
			}
		try{
		
			String msg="You must achieve a minimum score of 70% to earn credit for this activity.";
		if((webdriver.getPageSource().toLowerCase().trim()
				.contains(msg.toLowerCase().trim())))
		{
			System.out.println("You must achieve a minimum score of 70% to earn credit for this activity.");
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");
		}
		}catch(Exception e){}
		
		try{
			int choice_id[]=new int[45];
			int i=0,j=0;
			int form_type_id=3;
			System.out.println("form_type_id(Interna@post)="+form_type_id);
			ConnectionDB();
			rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
			rs.next();
			int form_id=rs.getInt(1);
			System.out.println("form_id(Interna@post)="+form_id);
			ConnectionDB();
			rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
			if(rs.next())
			{	rs.beforeFirst();
				while(rs.next()){
					choice_id[i]=rs.getInt(1);
					System.out.println("choice_id["+i+"]="+choice_id[i]);
				i++;j++;
				}			
				
		for(i=0;i<j;i++)
		{											
		webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();						
		}				
	webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();		
			
	}
		}
	catch(Exception e)
	{
	int choice_id[]=new int[45];
	int i=0,j=0;
	int form_type_id=4;
	System.out.println("form_type_id(post)="+form_type_id);
	ConnectionDB();
	rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
	rs.next();
	int form_id=rs.getInt(1);
	System.out.println("form_id(post)="+form_id);
	ConnectionDB();		
	rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
	if(rs.next())
	{
		rs.beforeFirst();
		while(rs.next()){
			choice_id[i]=rs.getInt(1);
			//System.out.println("choice_id["+i+"]="+choice_id[i]);
			i++;j++;			
			}	
	}
	for(i=0;i<j;i++)
	{														
	webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
	
	Thread.sleep(5000);
	}				
	}
	try{
		String msg="You haven't achieved the passing score. The questions you answered incorrectly are highlighted below.";
	if((webdriver.getPageSource().toLowerCase().trim()
			.contains(msg.toLowerCase().trim())))
	{
	System.out.println("You haven't achieved the passing score. The questions you answered incorrectly are highlighted below.");	
	}
		}catch(Exception e1){}		
	
	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");
}
	catch(Exception e){
	resultDetails.setFlag(false);
	return resultDetails;
}
return resultDetails;	
}
public  ResultDetails Score_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
{
	try{
		try{
			if(webdriver.getTitle().equalsIgnoreCase("Pre-Activity Questionnaire"))
			{
			 String s=webdriver.getCurrentUrl();
			 System.out.println(s);
			 String f="[_\\/\\#]+";String[] temp = s.split(f);
				for(int i =0; i < temp.length ; i++)
				System.out.println("temp["+i+"]="+temp[i]);	
				int article_id1=Integer.parseInt(temp[8]);
				System.out.println(article_id1);		
				ConnectionDB();
				rs=st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
				rs.next();
				int questionnaire_id=rs.getInt(1);	
				int form_type_id=2;
				System.out.println("In Pre-Questionnaire Form.....");
				System.out.println("form_type_id(Pre-Activity)="+form_type_id);
				ConnectionDB();
				rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
				rs.next();
				int form_id=rs.getInt(1);
				System.out.println("form_id(Pre-Activity)="+form_id);
				ConnectionDB();
				rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and  qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
				if(!rs.next()){
				System.out.println("These questions are not required questions in PreAcitivity form");		
				}	
				else
				{
			int choice_id[]=new int[45];int i=0,j=0;														
				rs.beforeFirst();				
			while(rs.next()){
			choice_id[i]=rs.getInt(1);						
				i++;j++;
				}			
			for(i=0;i<j;i++)
			{						
			webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
			}
			webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();		
			System.out.println("pre -submit");											 
			}				 
		 }
	}catch(Exception e){System.out.println("e4567");
			 System.out.println(e.getMessage());
		 }		
			
		String s=webdriver.getCurrentUrl();
		System.out.println(s);
		String f="[_\\/\\#]+";;String[] temp = s.split(f);
		for(int i =0; i < temp.length ; i++)
		    System.out.println("temp["+i+"]="+temp[i]);	
		int article_id1=Integer.parseInt(temp[3]);
		System.out.println(article_id1);
		
		if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
		{
			System.out.println("internal form");
			webdriver.findElement(By.linkText("Continue to Activity")).click();
			Thread.sleep(5000);
		}
		
		try{
			if(webdriver.findElement(By.linkText("Earn CME/CE Credit �")).isDisplayed()){					
			webdriver.findElement(By.linkText("Earn CME/CE Credit �")).click();
			System.out.println("Earn CME clicked...........Moving to post-form");
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");
			}
		}catch(Exception e){}
			
		try{
			if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
			{
			webdriver.findElement(By.partialLinkText("Earn CME Credit")).click();
			System.out.println("Earn CME clicked...Moving to post-form");
			}
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");
		}catch(Exception e){
			System.out.println("Earn cme  clicked");
			System.out.println(e.getMessage());
			}
		resultDetails=scoreAnswering_SINGLEPAGE(webdriver,article_id1,st,rs);
		
	}catch(Exception e)
	{
		resultDetails.setFlag(false);
		return resultDetails;
	}
	return resultDetails;
}
public  ResultDetails scoreAnswering_SINGLEPAGE(WebDriver webdriver,int article_id1,Statement st,ResultSet rs) throws ClassNotFoundException, SQLException 
{
	ResultDetails resultDetails = new ResultDetails();
	try{
		System.out.println("sgdfhgh");
		ConnectionDB();
		rs=st.executeQuery("select ac.passing_score from ACTIVITY_ELIGIBILITY ac,article a where a.activity_id=ac.activity_id and article_id="+article_id1);
		rs.next();
		int p=rs.getInt(1);
		
		if(p==0)
		{
			resultDetails=Answering_SINGLEPAGE(article_id1,webdriver,rs,st);
			}
			
}catch(Exception e)
{
	resultDetails.setFlag(false);
	return resultDetails;
}
return resultDetails;
}
private  ResultDetails Answering_SINGLEPAGE(int article_id1, WebDriver webdriver,ResultSet rs,Statement st) 
{
	ResultDetails resultDetails = new ResultDetails();
	try{
		System.out.println(" 0% required");
		
		int form_type_id=0,form_id=0;
		//GET THE QUESTIONNAIRE_ID 		
	
				rs= st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
				rs.next();
				int questionnaire_id=rs.getInt(1);
				System.out.println("questionnaire_id="+questionnaire_id);
				int choice_id[]=new int[45], i=0,j=0;
				try{
				form_type_id=3;	
				
				//GET THE FORM_ID
				ConnectionDB();
				rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
				rs.next();
				 form_id=rs.getInt(1);
				System.out.println("form_id(inter_btm_questions@post)="+form_id);
				
		webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();
				}
				catch(Exception e){
					form_type_id=4;	
					
					//GET THE FORM_ID
					ConnectionDB();
					rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
					rs.next();
					 form_id=rs.getInt(1);
					System.out.println("form_id(inter_btm_questions@post)="+form_id);
					
			webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();
				}
		try{
		webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).click();
		}catch(Exception e){}
		try{
			
			String msg="All questions must be answered in order to proceed.";
		if((webdriver.getPageSource().toLowerCase().trim()
				.contains(msg.toLowerCase().trim())))
		{
			//System.out.println("ruhye");
			ConnectionDB();
		rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
		while(rs.next()){
			choice_id[i]=rs.getInt(1);
			//System.out.println("choice_id["+i+"]="+choice_id[i]);
		i++;j++;
		}
		for(i=0;i<j;i++)
		{				
			webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
		}
		
		webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();
		//System.out.println("subit clicked(32)");
		}
		
		}catch(Exception e){
			//System.out.println("dffj");
			}
		
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");
	}catch(Exception e)
	{
		resultDetails.setFlag(false);
		return resultDetails;
	}
	return resultDetails;	
	}
public ResultDetails IntNotRequired_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
{
	   String s=webdriver.getCurrentUrl();
		System.out.println(s);
		
		String f="[_\\/\\#]+";;String[] temp = s.split(f);
		for(int i =0; i < temp.length ; i++)
		    System.out.println("temp["+i+"]="+temp[i]);	
		int article_id1=Integer.parseInt(temp[3]);
		System.out.println(article_id1);
		ConnectionDB();
		rs=st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
		rs.next();
		int questionnaire_id=rs.getInt(1);
		
		System.out.println(questionnaire_id);
	   try{
		   //int choice_id[]=new int[45], i=0,j=0;
			int form_type_id=3;	
			WebDriverUtils.waitForPageToLoad(webdriver, "30000");
			System.out.println("In Internal Form.....");
			System.out.println("form_type_id(internal)="+form_type_id);
			if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
			{
				System.out.println("internal form");
				webdriver.findElement(By.linkText("Continue to Activity")).click();
				Thread.sleep(5000);
			}
			try{
			if(webdriver.findElement(By.linkText("Transcript")).isDisplayed())
			{
				System.out.println("Transcript displayed");
				webdriver.findElement(By.linkText("Transcript")).click();
				Thread.sleep(2000);
			}}
			catch(Exception e){}
			try{
				if(webdriver.findElement(By.xpath("//input[@name='submitbutton']")).isDisplayed())
				{
					ConnectionDB();
				rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
				rs.next();
				int form_id=rs.getInt(1);
				System.out.println("form_id(inter_btr)="+form_id);
				ConnectionDB();
				rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qd.IS_REQUIRED=0 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
				if(!rs.next()){
					System.out.println("bottom required");
				}
				else{
					webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();		
				}
				}
				
	   }catch(Exception e){}
				
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
	   }catch(Exception e){
			System.out.println("This is Internal Bottom required form");
			//System.out.println(e.getMessage());
			resultDetails.setFlag(false);
			return resultDetails;
	   }
	   return resultDetails;
	   }
public  ResultDetails IntNotRequiredOn_SINGLEPAGEe(WebDriver webdriver,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
{ 
	String s=webdriver.getCurrentUrl();
	System.out.println(s);
	
	String f="[_\\/\\#]+";;String[] temp = s.split(f);
	for(int i =0; i < temp.length ; i++)
	    System.out.println("temp["+i+"]="+temp[i]);	
	int article_id1=Integer.parseInt(temp[3]);
	System.out.println(article_id1);
	ConnectionDB();
	rs=st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
	rs.next();
	int questionnaire_id=rs.getInt(1);
	
	System.out.println(questionnaire_id);
   try{
	   int choice_id[]=new int[45], i=0;
		int form_type_id=3;	
		WebDriverUtils.waitForPageToLoad(webdriver, "30000");
		System.out.println("In Internal Form.....");
		System.out.println("form_type_id(internal)="+form_type_id);
		if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
		{
			System.out.println("internal form");
			webdriver.findElement(By.linkText("Continue to Activity")).click();
			Thread.sleep(5000);
		}
		try{
		if(webdriver.findElement(By.linkText("Transcript")).isDisplayed())
		{
			System.out.println("Transcript displayed");
			webdriver.findElement(By.linkText("Transcript")).click();
			Thread.sleep(2000);
		}}
		catch(Exception e){}
		try{
			int j=1;
			if(webdriver.findElement(By.xpath("//input[@name='submitbutton']")).isDisplayed())
			{
				ConnectionDB();
			rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
			rs.next();
			int form_id=rs.getInt(1);
			System.out.println("form_id(inter_btr)="+form_id);
			ConnectionDB();
			rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qd.IS_REQUIRED=0 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
			if(!rs.next()){
				System.out.println("bottom required");
			}
			else{
				ConnectionDB();
				rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qd.IS_REQUIRED=0 and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
				while(rs.next())
				{
					choice_id[i]=rs.getInt(1);
					//System.out.println("choice_id["+i+"]="+choice_id[i]);
				i++;
				}			
				for(i=0;i<j;i++)
					{				
						webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
					}
			
			}
			}
			webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();			
   }catch(Exception e){}
			
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");
   }catch(Exception e){
		System.out.println("This is Internal Bottom required form");
		//System.out.println(e.getMessage());
		resultDetails.setFlag(false);
		return resultDetails;
   }
   return resultDetails;
	
}

public  ResultDetails Evaluation_SINGLEPAGE(WebDriver webdriver,String value,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
{
	System.out.println("evaluation");
	ResultDetails resultDetails = new ResultDetails();
	String s=webdriver.getCurrentUrl();
	System.out.println(s);
	//value=GETVALUE(value);
	System.out.println(value);
	String f="[_\\/\\#\\?]+";
	String[] temp = s.split(f);
	
	for(int i =0; i < temp.length ; i++)
	    System.out.println("temp["+i+"]="+temp[i]);	
	//int article_id1;
	//article_id1=Integer.parseInt(temp[3]);
	int questionnaire_id;
	questionnaire_id=Integer.parseInt(temp[4]);
	//article_id1=Integer.parseInt(value);
	//System.out.println(article_id1);
	System.out.println(questionnaire_id);
				
	try{
		try{
			webdriver.findElement(By.linkText("Earn CME Credit")).click();
			System.out.println("Earn CME clicked...Moving to post-form");
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");
		}catch(Exception e){}
		try{
			//Proceed to evaluation
			if(webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).isDisplayed())
			{
				webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).click();
	    	}
		}catch(Exception e){}
		int form_type_id=5;
		//int question_id[]=new int[45];int display_type[]=new int[45];
		System.out.println("In Evaluation Form.....");
		System.out.println("form_type_id(evaluation form)="+form_type_id);
		 
		try{
			System.out.println("In Connection");
			if(ODB.connection.isClosed())
			{
				System.out.println("IN connection_IF");

				ODB=new OracleDB(); 
				st=ODB.stmt;	
				rs=null;
			}}catch(Exception e)
			{		System.out.println("IN connection_Catch");

				System.out.println(e.getLocalizedMessage());
				ODB=new OracleDB(); 
				st=ODB.stmt;	
				rs=null;
			}
		rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
		rs.next();
		int form_id=rs.getInt(1);
		System.out.println("form_id(evaluation form)="+form_id);
		CmeEligibility prf = CmeEligibility.valueOf(value);
		switch(prf)
{
case cme:
	ConnectionDB();
rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=1 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
break;
case nurse:ConnectionDB();
rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=2 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");	
break;
case pharma:ConnectionDB();
rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=3 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");	
break;
case psych:ConnectionDB();
rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=4 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");		
break;
case cmle:ConnectionDB();
rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=5 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");	
break;
case loc:ConnectionDB();
rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=6 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");	
break;
case np_ce:ConnectionDB();
rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=7 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");	
break;
case pa_ce:ConnectionDB();
rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=8 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");	
break;
}
		if(rs.next()){
		int p=rs.getInt(1);
		System.out.println("p="+p);
		try{
		if(p==0){
			System.out.println("This is Eval Not Required Form");
			resultDetails=EvalNotReq_SINGLEPAGE(questionnaire_id, rs, st, webdriver,form_id);
		}
		else if(p==1)
		{
			System.out.println("This is Eval Required Form");
			resultDetails=EvalReq_SINGLEPAGE(questionnaire_id,webdriver,form_id);
		}	
		}	catch(Exception e){	
		//else
		//{
			ConnectionDB();
			rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=6 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");	
			System.out.println("There is  Evaluation form for LOC");
			if(rs.next()){
				int m=rs.getInt(1);
				System.out.println("m="+m);
				if(m==0){
					System.out.println("This is Eval Not Required Form");
					resultDetails=EvalNotReq_SINGLEPAGE(questionnaire_id, rs, st, webdriver,form_id);
				}
				else if(m==1)
				{
					System.out.println("This is Eval Required Form");
					resultDetails=EvalReq_SINGLEPAGE(questionnaire_id,webdriver,form_id);
				}		
			}
		}
	 }
		else{
			resultDetails=EvalNotReq_SINGLEPAGE(questionnaire_id, rs, st, webdriver,form_id);
		}
	}catch(Exception e){
		System.out.println("This is Evaluation Required Catch block");
		e.printStackTrace();
		System.out.println(e.getMessage());
		resultDetails.setFlag(false);
		return resultDetails;
   }
	return resultDetails;
}	
public  ResultDetails EvalReq_SINGLEPAGE(int questionnaire_id,WebDriver webdriver,int form_id) throws SQLException, InterruptedException, Exception
{	
	System.out.println("This is Eval req form");
	webdriver.get("http://www."+System.getenv("env")+".medscape.org/qna/form-evaluation/"+questionnaire_id);
	
	int i=0,j=0,q=0,n=0,k=0,p=0;
	int choice_id[]=new int[45];
	int question_id[]=new int[45];int display_type[]=new int[45];	
	ConnectionDB();
	rs=st.executeQuery("select question_id from question_display where questionnaire_id="+questionnaire_id+" and form_id="+form_id);
	while(rs.next()){			
		question_id[i]=rs.getInt(1);
		//System.out.println("question["+i+"]="+question_id[i]);
		i++;j++;
	}
	try{
	for(i=0;i<j;i++){	
		ConnectionDB();
rs=st.executeQuery("select DISPLAY_TYPE_ID from question_display where question_id="+question_id[i]+" and form_id="+form_id+" and questionnaire_id="+questionnaire_id);	
		rs.next();
		display_type[i]=rs.getInt(1);
		//System.out.println("display["+i+"]="+display_type[i]);
	}
	for(i=0;i<j;i++){
	
	if(display_type[i]==5)
	{
		q=i;
		//System.out.println("q=="+q);
	//System.out.println("question["+i+"](TextArea)="+question_id[i]);
	webdriver.findElement(By.name("option-"+question_id[i]+"")).sendKeys("Sample");
	n++;
	//Here n is number of text area questions
	}
	else if(display_type[i]==3)
	{
	System.out.println("question["+i+"](Select)="+question_id[i]);	
	select(webdriver,"2");
	//webdriver.findElement(By.xpath("//select[@name='pCredits']")).click();	
	}
	else
	{ 
		ConnectionDB();
System.out.println("question["+i+"](else)="+question_id[i]);
rs=st.executeQuery("select choice_id from question_choice where question_id="+question_id[i]+" and display_order=1");
	if(!rs.next()){
		//System.out.println("question["+i+"](else_if)="+question_id[i]);
		webdriver.findElement(By.name("option-"+question_id[i]+"")).click();
	}
	else
	{
		rs.beforeFirst();
		while(rs.next())	
		{
			choice_id[i]=rs.getInt(1);
			//System.out.println("choice_id["+i+"]="+choice_id[i]);
		k++;	
		//System.out.println("k(total)="+k);
		}
	}
	}
	}//System.out.println("TextAreas@@="+n);
	
	java.util.List<WebElement> l=webdriver.findElements(By.xpath("//input[@value=1100]"));
	for(i=0;i<j;i++){
	//	System.out.println("i(for)="+i);
		//System.out.println("j(for)="+j);
		if(choice_id[i]==1100)
		{
			//System.out.println("i(if)="+i);
		WebElement t=l.get(p);
		t.click();
		p++;
		}
		else if(choice_id[q-n+1]!=choice_id[i]){
			//System.out.println("q="+q);			System.out.println("i(else)="+i);
			//System.out.println("n="+n);

			//System.out.println("choice_id["+i+"](for)="+choice_id[i]);
			webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();	
		}
	}
	webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();
	System.out.println("submit@eval clicked");
	WebDriverUtils.waitForPageToLoad(webdriver, "20000");
	
	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");
	
	}catch(Exception e){
			resultDetails.setFlag(false);
			return resultDetails;
		}
	return resultDetails;

		}
public  ResultDetails EvalNotReq_SINGLEPAGE(int questionnaire_id,ResultSet rs,Statement st,WebDriver webdriver,int form_id) throws SQLException, InterruptedException, Exception
{
	int i=0,j=2;
	int choice_id[]=new int[45];
	System.out.println("This is eval  not req");
	webdriver.get("http://www."+System.getenv("env")+".medscape.org/qna/form-evaluation/"+questionnaire_id);
	try{ 
		ConnectionDB();
		rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qd.IS_REQUIRED=0 and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
		if(!rs.next())
		{
			ConnectionDB();
			rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qd.IS_REQUIRED=0 and qc.is_correct=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
			while(rs.next()){
				choice_id[i]=rs.getInt(1);
				//System.out.println("choice_id["+i+"]="+choice_id[i]);
			i++;
			}
		}
		else
		{
			rs.beforeFirst();
			while(rs.next()){
				choice_id[i]=rs.getInt(1);
				//System.out.println("choice_id["+i+"]="+choice_id[i]);
			i++;
			}
		}
		for(i=0;i<j;i++)
		{	
			//System.out.println("232412");
			webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
		}
		webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();
		System.out.println("submit@eval clicked");
		WebDriverUtils.waitForPageToLoad(webdriver, "20000");
		
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");
		
		}catch(Exception e){
				resultDetails.setFlag(false);
				return resultDetails;
			}
	return resultDetails;
		}


public  ResultDetails InternalTranscript(int questionnaire_id,ResultSet rs,Statement st,WebDriver webdriver,int article_id) throws SQLException, InterruptedException, ClassNotFoundException, IOException, ParserConfigurationException, SAXException, XPathExpressionException
{
	try{
		int f=webdriver.findElements(By.id("midcontentqs")).size();
		System.out.println("No of submit buttons in this page are(f):"+f);

		for(int c=0;c<1;c++)
		{
			System.out.println("Answering form no:"+(c+1));
			int form_type_id=3;ConnectionDB();
			rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
			rs.next();
			int form_id=rs.getInt(1);
			System.out.println("form_id="+form_id);
			int p=form_id;
			for(int k=form_id;k<f+p;k++)
			{
			form_id=k;
			//System.out.println("form_id(k)="+form_id);
			InternalAnswering(form_id,webdriver,rs,st,questionnaire_id,c);
		
			}
	}
		
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");
		
	}catch(Exception e3){
	System.out.println("e3");
	resultDetails.setFlag(false);
	return resultDetails;
	}
	return resultDetails;
}
public  ResultDetails InternalAnswering(int form_id,WebDriver webdriver,ResultSet rs,Statement st,int questionnaire_id,int c) throws SQLException, InterruptedException
{
	try{
	int choice_id[]=new int[45];
	ConnectionDB();
	rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.is_correct=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
	if(rs.next())
	{
		int	k=0,j=0;ConnectionDB();
		rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.is_correct=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
		while(rs.next())
		{
			choice_id[k]=rs.getInt(1);
			
			System.out.println("choice_ _id["+k+"]=" +
					""+choice_id[k]);
			Thread.sleep(2000);
			k++;j++;
		}
		try { Thread.sleep(1000); }catch (Exception e1) {}
		for(k=0;k<j;k++)
		{
			Actions hover = new Actions(webdriver);
			hover.moveToElement(webdriver.findElement(By.xpath("//input[@value="+choice_id[k]+"]"))).build().perform();
			Thread.sleep(1000);
			webdriver.findElement(By.xpath("//input[@value="+choice_id[k]+"]")).click();
			
			//System.out.println("choice with id="+choice_id[k]+" clicked");
			try{
				if(webdriver.findElement(By.xpath("//input[@value='Submit']")).isDisplayed())
				{
				webdriver.findElement(By.xpath("//input[@value='Submit']")).click();
				
				System.out.println("submit clicked");
				}
				}catch(Exception e){}
			Thread.sleep(5000);
		}
	}
	else
	{
		ConnectionDB();
		rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
		int	k=0,j=0;
		while(rs.next())
		{
			choice_id[k]=rs.getInt(1);
			System.out.println("choice_id["+k+"]="+choice_id[k]);
			k++;j++;
		}
		try { Thread.sleep(1000); }catch (Exception e1) {}
		for(k=0;k<j;k++)
		{
			Actions hover = new Actions(webdriver);
			hover.moveToElement(webdriver.findElement(By.xpath("//input[@value="+choice_id[k]+"]"))).build().perform();
			Thread.sleep(1000);
			webdriver.findElement(By.xpath("//input[@value="+choice_id[k]+"]")).click();
		
			System.out.println("choice with id="+choice_id[k]+" clicked");
			try{
			if(webdriver.findElement(By.xpath("//input[@value='Submit']")).isDisplayed())
			{
			webdriver.findElement(By.xpath("//input[@value='Submit']")).click();
			
			System.out.println("submit clicked");
			}
			}catch(Exception e){}
			
		}
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");
		
	}
	}catch(Exception e4){
	//System.out.println("e4");
	resultDetails.setFlag(false);
	return resultDetails;
	}
	return resultDetails;	
}
public  ResultDetails Submit(WebDriver webdriver,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
{
	String s=webdriver.getCurrentUrl();
	System.out.println(s);
	
	String f="[_\\/\\#]+";;String[] temp = s.split(f);
	for(int i =0; i < temp.length ; i++)
	    System.out.println("temp["+i+"]="+temp[i]);	
	int article_id1=Integer.parseInt(temp[3]);
	System.out.println(article_id1);
	ConnectionDB();
	rs=st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
	rs.next();
	int questionnaire_id=rs.getInt(1);
	
	System.out.println(questionnaire_id);
	try{
		//int choice_id[]=new int[45], i=0,j=0;
		int form_type_id=3;	
		WebDriverUtils.waitForPageToLoad(webdriver, "30000");
		System.out.println("In Internal Form.....");
		System.out.println("form_type_id(internal)="+form_type_id);
		if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
		{
			System.out.println("internal form");
			webdriver.findElement(By.linkText("Continue to Activity")).click();
			Thread.sleep(5000);
		}
		try{
		if(webdriver.findElement(By.linkText("Transcript")).isDisplayed())
		{
			System.out.println("Transcript displayed");
			webdriver.findElement(By.linkText("Transcript")).click();
			Thread.sleep(2000);
		}}catch(Exception e){}
		try
		{
			int f1=webdriver.findElements(By.id("midcontentqs")).size();
    		if(f1>=1)// for clicking submits button if exists in the current page
    		{
    			System.out.println("No of submit buttons in this page are:"+f1);
    			for(int c=0;c<f1;c++)
    			{
		    	 	System.out.println("In for-loop of clicking submits button");
		          	Actions hover = new Actions(webdriver);
		   			hover.moveToElement(webdriver.findElement(By.xpath("//div[@id='midcontentqs']["+(c+1)+"]/div/div/input[@value='Submit']"))).build().perform();
		   			Thread.sleep(500);
		   			webdriver.findElement(By.xpath("//div[@id='midcontentqs']["+(c+1)+"]/div/div/input[@value='Submit']")).click();
		   			System.out.println((c+1)+"th Submit button clicked");
				}
    		}
		}catch(Exception e){
			System.out.println("submit exception");
		}
		
	}catch(Exception e){
		System.out.println("This is Submit type Internal Bottom required form");
		//System.out.println(e.getMessage());
		resultDetails.setFlag(false);
		return resultDetails;
			}
	return resultDetails;
}
public  ResultDetails Eligibility_credits_SINGLEPAGE(WebDriver webdriver,String value,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
{				
try{
String E1=null,C1=null,num=null;
try{
if(webdriver.getTitle().equalsIgnoreCase("Pre-Activity Questionnaire"))
{
String s=webdriver.getCurrentUrl();
System.out.println(s);
			
String f="[_\\/\\#]+";;String[] temp = s.split(f);
for(int i =0; i < temp.length ; i++)
System.out.println("temp["+i+"]="+temp[i]);	
int article_id1=Integer.parseInt(temp[8]);
System.out.println(article_id1);
	
ConnectionDB();
rs=st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
rs.next();
int questionnaire_id=rs.getInt(1);			
			
int form_type_id=2;
System.out.println("In Pre-Questionnaire Form.....");
System.out.println("form_type_id(Pre-Activity)="+form_type_id);
ConnectionDB();
rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
rs.next();
int form_id=rs.getInt(1);
System.out.println("form_id(Pre-Activity)="+form_id);
ConnectionDB();
rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and  qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
if(!rs.next()){
System.out.println("These questions are not required questions in PreAcitivity form");		
}		
else
{
	int choice_id[]=new int[45];int i=0,j=0;														
	rs.beforeFirst();				
	while(rs.next()){
			choice_id[i]=rs.getInt(1);						
			i++;j++;
	}			
for(i=0;i<j;i++)
{				
webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
}		
webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();		
System.out.println("pre -submit");											 
}							 
}
}catch(Exception e){
System.out.println("e4567");
System.out.println(e.getMessage());
}

String s=webdriver.getCurrentUrl();
System.out.println(s);

String f="[_\\/\\#]+";String[] temp = s.split(f);
for(int i =0; i < temp.length ; i++)
System.out.println("temp["+i+"]="+temp[i]);	
int article_id1=Integer.parseInt(temp[3]);
System.out.println(article_id1);	

ConnectionDB();
rs=st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
rs.next();
int questionnaire_id=rs.getInt(1);

try{
if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
{
 E1=webdriver.findElement(By.xpath("//div[@id='accme_content_right']/ul[1]/li")).getText();	 
 System.out.println("ElIGIBILTY************"+E1);
	String f1=null,temp1[]=null;
 CmeEligibility prf = CmeEligibility.valueOf(value);
	switch(prf)
{
case cme:
    //GET THE CREDITS	
	C1=webdriver.findElement(By.xpath("//div[4]/div[2]/div[2]/div[2]/div[3]/p[1]")).getText();
	System.out.println("CREDITS="+C1);
	f1="[-\\/\\ ]+";
	temp1=C1.split(f1);			
	for(int i =0; i < temp1.length ; i++)
	System.out.println("temp["+i+"]="+temp1[i]);				
	num=temp1[3];
break;

case nurse:
C1=webdriver.findElement(By.xpath("//div[4]/div[2]/div[2]/div[2]/div[3]/p[1]")).getText();
f1="[-\\/\\ ]+";
 temp1=C1.split(f1);			
for(int i =0; i < temp1.length ; i++)
System.out.println("temp1["+i+"]="+temp1[i]);				

if(temp1[0]=="Nurse")
{
	num=temp1[1];
}
else if(webdriver.findElement(By.xpath("//div[@id='accme_content_right']/p[2]")).isDisplayed())
{
	C1=webdriver.findElement(By.xpath("//div[@id='accme_content_right']/p[2]")).getText();
	f1="[-\\/\\ ]+";
 temp1=C1.split(f1);			
for(int i =0; i < temp1.length ; i++)
System.out.println("temp1["+i+"]="+temp1[i]);				

if(temp1[0]=="Nurse")
{
	num=temp1[1];
}
}
else
{
	C1=webdriver.findElement(By.xpath("//div[@id='accme_content_right']/p[3]")).getText();
	f1="[-\\/\\ ]+";
	temp1=C1.split(f1);			
	for(int i =0; i < temp1.length ; i++)
	System.out.println("temp["+i+"]="+temp1[i]);				
	num=temp1[1];
}
break;
case pharma:
C1=webdriver.findElement(By.xpath("//div[@id='accme_content_right']/p[2]")).getText();
f1="[-\\/\\ ]+";
 temp1=C1.split(f1);			
for(int i =0; i < temp1.length ; i++)
System.out.println("temp1["+i+"]="+temp1[i]);				

if(temp1[0]=="Pharmacists")
{
	num=temp1[1];
}
else
{
	C1=webdriver.findElement(By.xpath("//div[@id='accme_content_right']/p[3]")).getText();
	f1="[-\\/\\ ]+";
	temp1=C1.split(f1);			
	for(int i =0; i < temp1.length ; i++)
	System.out.println("temp["+i+"]="+temp1[i]);				
	num=temp1[1];
	if(temp1[0]=="Pharmacists")
	{
		num=temp1[1];
	}
	else
	{
		C1=webdriver.findElement(By.xpath("//div[@id='accme_content_right']/p[4]")).getText();
		f1="[-\\/\\ ]+";
		temp1=C1.split(f1);			
		for(int i =0; i < temp1.length ; i++)
		System.out.println("temp["+i+"]="+temp1[i]);				
		num=temp1[1];
		if(temp1[0]=="Pharmacists")
		{
			num=temp1[1];
		}
	}
	}
break;
case loc:
C1=webdriver.findElement(By.xpath("//div[4]/div[2]/div[2]/div[2]/div[3]/p[1]")).getText();
System.out.println("CREDITS="+C1);
f1="[-\\/\\ ]+";
temp1=C1.split(f1);			
for(int i =0; i < temp1.length ; i++)
System.out.println("temp["+i+"]="+temp1[i]);				
num=temp1[3];
break;
	case OHC:
		C1=webdriver.findElement(By.xpath("//div[@id='accme_content_right']/p[3]")).getText();
		f1="[-\\/\\ ]+";
		temp1=C1.split(f1);			
		for(int i =0; i < temp1.length ; i++)
		System.out.println("temp["+i+"]="+temp1[i]);
		
		num=temp1[4];
		break;
}
	System.out.println("Credits in ACCme Layer:"+num);
	webdriver.findElement(By.linkText("Continue to Activity")).click();
	WebDriverUtils.waitForPageToLoad(webdriver, "10000");
}
}catch(Exception e){}

try{
if(webdriver.findElement(By.linkText("Earn CME/CE Credit �")).isDisplayed()){					
webdriver.findElement(By.xpath("//a[contains(@href, '/qna/processor/"+questionnaire_id+"?showStandAlone=true')]")).click();
System.out.println("Earn CME clicked...........Moving to post-form");
WebDriverUtils.waitForPageToLoad(webdriver, "20000");
}
}catch(Exception e){}
try{
if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
{
webdriver.findElement(By.partialLinkText("Earn CME Credit")).click();
System.out.println("Earn CME clicked...Moving to post-form");
}
WebDriverUtils.waitForPageToLoad(webdriver, "20000");
}catch(Exception e){}
try{
int choice_id[]=new int[45];
int i=0,j=0;
int form_type_id=3;
System.out.println("form_type_id(Interna@post)="+form_type_id);
ConnectionDB();
rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
rs.next();
int form_id=rs.getInt(1);
System.out.println("form_id(Interna@post)="+form_id);
ConnectionDB();
rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.is_correct=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
if(rs.next())
{	rs.beforeFirst();
	while(rs.next()){
		choice_id[i]=rs.getInt(1);
		System.out.println("choice_id["+i+"]="+choice_id[i]);
	i++;j++;
	}			
	
for(i=0;i<j;i++)
{											
webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();						
}				
webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();		

}
else
{
	ConnectionDB();
rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
if(rs.next())
{
rs.beforeFirst();
while(rs.next()){
choice_id[i]=rs.getInt(1);
System.out.println("choice_id["+i+"]="+choice_id[i]);
i++;j++;
}						
for(i=0;i<j;i++)
{									
webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();			
}				
webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();						
}		
}				
}	
catch(Exception e)
{
int choice_id[]=new int[45];
int i=0,j=0;
int form_type_id=4;
System.out.println("form_type_id(post)="+form_type_id);

ConnectionDB();
rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
rs.next();
int form_id=rs.getInt(1);
System.out.println("form_id(post)="+form_id);

ConnectionDB();
rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.is_correct=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
if(rs.next())
{
rs.beforeFirst();
while(rs.next()){
choice_id[i]=rs.getInt(1);
//System.out.println("choice_id["+i+"]="+choice_id[i]);
i++;j++;			
}						
for(i=0;i<j;i++)
{														
webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();					
Thread.sleep(5000);
}				
try{
if(webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/input")).isDisplayed())
{
webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();									
}
}catch(Exception e1){
System.out.println("ere34we");
System.out.println(e.getMessage());
}
}
else
{
	ConnectionDB();
rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
if(rs.next())
{
rs.beforeFirst();
while(rs.next()){
choice_id[i]=rs.getInt(1);
System.out.println("choice_id["+i+"]="+choice_id[i]);
i++;j++;
}						
for(i=0;i<j;i++)
{							
webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();		
}				
try{

if(webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/input")).isDisplayed())
{
webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();	

}
}catch(Exception e1){
System.out.println("erewe");
System.out.println(e.getMessage());
}
}				
}
}		
try{
//proceed to evaluation
if(webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).isDisplayed())
{	
System.out.println("Proceed to Evaluation");
webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).click();
System.out.println("Proceed to Evaluation is clicked @prf");

resultDetails=Evaluation1(webdriver, value,E1,num,rs,st);
WebDriverUtils.waitForPageToLoad(webdriver, "30000");
}
}catch(Exception e){
System.out.println("e2");
resultDetails=Evaluation1(webdriver, value,E1,num,rs,st);
//System.out.println(e.getMessage());
}



resultDetails.setFlag(true);
resultDetails.setErrorMessage("");
}catch(Exception e){
System.out.println("Pre required CME WF catch block");
resultDetails.setFlag(false);
return resultDetails;
}
return resultDetails;
}

public  ResultDetails Evaluation1(WebDriver webdriver,String value,String E1,String num,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
{
System.out.println("evaluation");

String s=webdriver.getCurrentUrl();
System.out.println(s);

System.out.println(value);
String f="[_\\/\\#]+";
String[] temp = s.split(f);

for(int i =0; i < temp.length ; i++)
    System.out.println("temp["+i+"]="+temp[i]);	

int questionnaire_id;
questionnaire_id=Integer.parseInt(temp[4]);

System.out.println(questionnaire_id);
	
try{
	try{
		webdriver.findElement(By.linkText("Earn CME Credit")).click();
		System.out.println("Earn CME clicked...Moving to post-form");
		WebDriverUtils.waitForPageToLoad(webdriver, "20000");
	}catch(Exception e){}
	try{
		//Proceed to evaluation
		if(webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).isDisplayed())
		{
			webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).click();
    		}
	}catch(Exception e){}
	int form_type_id=5;
	//int question_id[]=new int[45];int display_type[]=new int[45];
	System.out.println("In Evaluation Form.....");
	System.out.println("form_type_id(evaluation form)="+form_type_id);
	ConnectionDB();
	rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
	rs.next();
	int form_id=rs.getInt(1);
	System.out.println("form_id(evaluation form)="+form_id);
	CmeEligibility prf = CmeEligibility.valueOf(value);
	switch(prf)
{
case cme:ConnectionDB();
rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=1 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
break;
case nurse:ConnectionDB();
rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=2 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");	
break;
case pharma:ConnectionDB();
rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=3 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");	
break;
case psych:ConnectionDB();
rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=4 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");		
break;
case OHC:ConnectionDB();
rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=5 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");	
break;
case loc:ConnectionDB();
rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=6 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");	
break;
case np_ce:ConnectionDB();
rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=7 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");	
break;
case pa_ce:ConnectionDB();
rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=8 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");	
break;
}
	if(rs.next()){
	int p=rs.getInt(1);
	System.out.println("p="+p);
	if(p==0){
		System.out.println("This is Eval Not Required Form");
		resultDetails=EvalNotReq1(questionnaire_id, rs, st, webdriver,form_id);
	}
	else if(p==1)
	{
		System.out.println("This is Eval Required Form");
		resultDetails=EvalReq1(questionnaire_id, rs, st, webdriver,form_id);
	}	}		
	else
	{
		ConnectionDB();
		rs=st.executeQuery("select ac.EVAL_REQUIRED from article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.activity_id=ac.activity_id and a.questionnaire_id="+questionnaire_id+" and ac.CREDIT_TYPE_ID=6 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");	
		System.out.println("There is  Evaluation form for LOC");
		if(rs.next()){
			int m=rs.getInt(1);
			System.out.println("m="+m);
			if(m==0){
				System.out.println("This is Eval Not Required Form");
				resultDetails=EvalNotReq1(questionnaire_id, rs, st, webdriver,form_id);
			}
			else if(m==1)
			{
				System.out.println("This is Eval Required Form");
				resultDetails=EvalReq1(questionnaire_id, rs, st, webdriver,form_id);
			}		
		}
	}	
	
	//System.out.println("12389");
	webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();
	System.out.println("submit@eval(ELG) clicked");
	WebDriverUtils.waitForPageToLoad(webdriver, "20000");
	
	
	resultDetails=TrackerEligibiltiy(webdriver,E1,num,value);

}catch(Exception e){
	System.out.println("This is Evaluation Required Catch block");
	//System.out.println(e.getMessage());
	resultDetails.setFlag(false);
	return resultDetails;
}
return resultDetails;
}	
public  ResultDetails EvalReq1(int questionnaire_id,ResultSet rs,Statement st,WebDriver webdriver,int form_id) throws SQLException, InterruptedException, Exception
{
System.out.println("This is Eval req form");
webdriver.get("http://www."+System.getenv("env")+".medscape.org/qna/form-evaluation/"+questionnaire_id);

int i=0,j=0,q=0,n=0,k=0,p=0;
int choice_id[]=new int[45];
int question_id[]=new int[45];int display_type[]=new int[45];	
ConnectionDB();
rs=st.executeQuery("select question_id from question_display where questionnaire_id="+questionnaire_id+" and form_id="+form_id);
while(rs.next()){			
	question_id[i]=rs.getInt(1);
	//System.out.println("question["+i+"]="+question_id[i]);
	i++;j++;
}
try{
for(i=0;i<j;i++){	
	ConnectionDB();
rs=st.executeQuery("select DISPLAY_TYPE_ID from question_display where question_id="+question_id[i]+" and form_id="+form_id+" and questionnaire_id="+questionnaire_id);	
	rs.next();
	display_type[i]=rs.getInt(1);
	//System.out.println("display["+i+"]="+display_type[i]);
}
for(i=0;i<j;i++){

if(display_type[i]==5)
{
	q=i;
//System.out.println("question["+i+"](TextArea)="+question_id[i]);
webdriver.findElement(By.name("option-"+question_id[i]+"")).sendKeys("Sample");
n++;
//Here n is number of text area questions
}
else if(display_type[i]==3)
{
System.out.println("question["+i+"](Select)="+question_id[i]);	
select(webdriver,"2");
//webdriver.findElement(By.xpath("//select[@name='pCredits']")).click();	
}
else
{
//System.out.println("question["+i+"](else)="+question_id[i]);
	ConnectionDB();
	rs=st.executeQuery("select choice_id from question_choice where question_id="+question_id[i]+" and display_order=1");
if(!rs.next()){
	//System.out.println("question["+i+"](else_if)="+question_id[i]);
	webdriver.findElement(By.name("option-"+question_id[i]+"")).click();
}
else
{
	rs.beforeFirst();
	while(rs.next())	
	{
		choice_id[i]=rs.getInt(1);
	k++;	
	}
}
}
}

java.util.List<WebElement> l=webdriver.findElements(By.xpath("//input[@value=1100]"));
for(i=0;i<k;i++){
	
	if(choice_id[i]==1100)
	{
	WebElement t=l.get(p);
	t.click();
	p++;

	}
	else if(choice_id[q-n+1]!=choice_id[i]){
		
		//System.out.println("choice_id["+i+"](for)="+choice_id[i]);
		webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();	
	}
}

}catch(Exception e){
	resultDetails.setFlag(false);
	return resultDetails;
}
return resultDetails;

}
public   ResultDetails EvalNotReq1(int questionnaire_id,ResultSet rs,Statement st,WebDriver webdriver,int form_id) throws SQLException, InterruptedException, Exception
{
int i=0,j=2;
int choice_id[]=new int[45];
System.out.println("This is eval  not req");
webdriver.get("http://www."+System.getenv("env")+".medscape.org/qna/form-evaluation/"+questionnaire_id);
try{
	ConnectionDB();
rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qd.IS_REQUIRED=0 and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
if(!rs.next())
{
	ConnectionDB();
	rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qd.IS_REQUIRED=0 and qc.is_correct=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
	while(rs.next()){
		choice_id[i]=rs.getInt(1);
		//System.out.println("choice_id["+i+"]="+choice_id[i]);
	i++;
	}
}
else
{
	rs.beforeFirst();
	while(rs.next()){
		choice_id[i]=rs.getInt(1);	
	i++;
	}
}
for(i=0;i<j;i++)
{	
	webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
}

}
catch(Exception e){
	resultDetails.setFlag(false);
	return resultDetails;
}
return resultDetails;
}
public  ResultDetails TrackerEligibiltiy(WebDriver webdriver,String E1,String num,String value)
{
	try{
	String t=null,E2=null,s=null;	
	webdriver.findElement(By.id("edu_view_tracker_btn")).click();
	WebDriverUtils.waitForPageToLoad(webdriver, "20000");
	
System.out.println("***********************************");
t=webdriver.findElement(By.xpath("//div[2]/div/div/h2")).getText();
System.out.println("Activity="+t);	

E2=webdriver.findElement(By.xpath("//table[@id='physicianactivity']/tbody/tr[2]/td[5]")).getText();
System.out.println("E=="+E2);
s=webdriver.findElement(By.xpath("//table[@id='physicianactivity']/tbody/tr[2]/td[6]")).getText();
System.out.println("S="+s);

try{
	if(webdriver.findElement(By.partialLinkText("View/Print Certificate")).isDisplayed())
	{
	webdriver.findElement(By.partialLinkText("View/Print Certificate")).click();
	WebDriverUtils.waitForPageToLoad(webdriver, "20000");
	}			
	
	
	try{
	String t1=webdriver.findElement(By.xpath("//div[2]/div[2]/h1/b")).getText();		
	System.out.println("t1="+t1);
	}catch(Exception e){}
	try{
		//Certificate title
	String t2=webdriver.findElement(By.xpath("//div[@id='certborder']/h2/i")).getText();
	System.out.println("t2="+t2);
}catch(Exception e){}
	
	
	String p=webdriver.findElement(By.xpath("//div[2]/div[2]/p[6]/b")).getText();
	System.out.println("p="+p);
	
}catch(Exception e){	
//System.out.println(e.getMessage());
}
		if(E2.contains(E1))
		{
		System.out.println("Eligibility equal");	
		}
		if(s.equalsIgnoreCase(num)){				
			System.out.println("Credits equal");
		}		
		webdriver.findElement(By.xpath("//table[@id='physicianactivity']/tbody/tr[2]/td/div/a[2]")).click();
		WebDriverUtils.waitForPageToLoad(webdriver, "20000");
	}catch(Exception e)
	{
		resultDetails.setFlag(false);
		return resultDetails;
	}
	return resultDetails;
}
public  ResultDetails Credits_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st)
{
	try{
		String s=webdriver.getCurrentUrl();
		System.out.println(s);
	
		//System.out.println(value);
		String f="[_\\/\\#]+";
		String[] temp = s.split(f);

		for(int i =0; i < temp.length ; i++)
		    System.out.println("temp["+i+"]="+temp[i]);	
		//int article_id1;
		//article_id1=Integer.parseInt(temp[3]);
		int questionnaire_id;
		questionnaire_id=Integer.parseInt(temp[4]);
		
		
		System.out.println(questionnaire_id);
		
		ConnectionDB();
		rs=st.executeQuery("select article_id from article where questionnaire_id="+questionnaire_id);
		rs.next();
		int article_id=rs.getInt(1);
		webdriver.get("http://www."+System.getenv("env")+".medscape.org/viewarticle/"+article_id);	
		WebDriverUtils.waitForPageToLoad(webdriver, "20000");

		try{
			if(webdriver.findElement(By.linkText("Earn CME/CE Credit �")).isDisplayed()){					
			webdriver.findElement(By.xpath("//a[contains(@href, '/qna/processor/"+questionnaire_id+"?showStandAlone=true')]")).click();
			System.out.println("Earn CME clicked...........Moving to post-form");
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");
			}
			}catch(Exception e){}
			try{
			if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
			{
			webdriver.findElement(By.partialLinkText("Earn CME Credit")).click();
			System.out.println("Earn CME clicked...Moving to post-form");
			}
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");
			}catch(Exception e){}
			
			try{
				
				String msg="You have already earned credit for this activity.";
			if((webdriver.getPageSource().toLowerCase().trim()
					.contains(msg.toLowerCase().trim())))
			{
				System.out.println("You have already earned credit for this activity.");
				WebDriverUtils.waitForPageToLoad(webdriver, "20000");
			}
			}catch(Exception e){}
			

			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
			}catch(Exception e){
			   System.out.println("Pre required CME WF catch block");
				resultDetails.setFlag(false);
				return resultDetails;
			}
			return resultDetails;
}

public  ResultDetails ExpiredProgram_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st,String value)
{	
	try{
		ConnectionDB();
		rs=st.executeQuery("select distinct p.program_id from QUESTIONNAIRE_FORM qf,program p,activity_info i where  p.questionnaire_id=qf.questionnaire_id  and qf.form_type_id!=2 and  p.activity_id=i.activity_id  and i.expiration_date<(sysdate+1) and rownum<1000");
		int k=0;
		while(rs.next())
		{
			k++;
		}
		int p= getRandom(k);
		System.out.println("p="+p);
		rs.beforeFirst();
		rs.absolute(p);	
		
		int program_id;
		program_id=rs.getInt(1);		
		System.out.println("program_id="+program_id);
		webdriver.get("http://www."+System.getenv("env")+".medscape.org/viewprogram/"+program_id);
		
		//CME Information
		try{
			if(webdriver.findElement(By.xpath("//div[@id='content_btn_row']/span/a")).isDisplayed())
			{
				webdriver.findElement(By.xpath("//div[@id='content_btn_row']/span/a")).click();
				WebDriverUtils.waitForPageToLoad(webdriver, "10000");

			}
		}catch(Exception e){}
		if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
		{
			 webdriver.findElement(By.linkText("Continue to Activity")).click();
		} 
		
		//This activity has expired.
		try{
			
			String msg="This activity has expired.";
		if((webdriver.getPageSource().toLowerCase().trim()
				.contains(msg.toLowerCase().trim())))
		{
			System.out.println("This activity has expired.");
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");
		}
		}catch(Exception e){}	
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");
		}catch(Exception e){
			//resultDetails=ExpiredProgram(webdriver, rs, st, value);
			resultDetails.setFlag(false);
			return resultDetails;
		}
		return resultDetails;
}

public  ResultDetails ArticleEarned_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st,String value)
{
	try{
		ConnectionDB();
		rs=st.executeQuery("select distinct p.program_id,p.questionnaire_id from QUESTIONNAIRE_FORM qf,program p,activity_info i where  p.questionnaire_id=qf.questionnaire_id  and qf.form_type_id!=2 and  p.activity_id=i.activity_id  and i.expiration_date>(sysdate+1) and rownum<1000");
		int k=0;
		while(rs.next())
		{
			k++;
		}

		int p= getRandom(k);
		System.out.println("p="+p);
		rs.beforeFirst();
		rs.absolute(p);		
		int program_id,questionnaire_id,article_id,no_of_completed_forms=3;
		program_id=rs.getInt(1);questionnaire_id=rs.getInt(2);
		System.out.println("program_id="+program_id);
		ConnectionDB();
		rs=st.executeQuery("select article_id from PROGRAM_ARTICLE where program_id="+program_id);
		int m=0;
		while(rs.next())
		{
			m++;
		}

		int q= getRandom(m);
		System.out.println("q="+q);
		rs.beforeFirst();
		rs.absolute(q);

		article_id=rs.getInt(1);
		//article_id=76564;
		webdriver.get("http://www."+System.getenv("env")+".medscape.org/viewarticle/"+article_id);
		System.out.println("article_id1="+article_id);

		try{
		if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
		{
			System.out.println("internal form");
			webdriver.findElement(By.linkText("Continue to Activity")).click();
			program p1=new program();
			resultDetails=p1.internal(article_id,value,questionnaire_id,webdriver,no_of_completed_forms,article_id);
		}
		}catch(Exception e){}
		
		
		webdriver.get("http://www."+System.getenv("env")+".medscape.org/viewarticle/"+article_id);
		WebDriverUtils.waitForPageToLoad(webdriver,"10000");
		
		try{
			if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
			{
				System.out.println("internal form");
				webdriver.findElement(By.linkText("Continue to Activity")).click();
			}
			}catch(Exception e){}
		
		try{
			if(webdriver.findElement(By.linkText("Earn CME/CE Credit �")).isDisplayed()){					
			webdriver.findElement(By.linkText("Earn CME/CE Credit �")).click();
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");
			}
		}catch(Exception e){}
			
		try{
			if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
			{
			webdriver.findElement(By.partialLinkText("Earn CME Credit")).click();
			}
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");
		}catch(Exception e){
		
			resultDetails=ArticleEarned_SINGLEPAGE(webdriver,rs,st,value);
			}
		
		try{
			
			String msg="You have already earned credit for this activity.";
		if((webdriver.getPageSource().toLowerCase().trim()
				.contains(msg.toLowerCase().trim())))
		{
			System.out.println("You have already earned credit for this activity.");
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");
		}
		}catch(Exception e){}	
		
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");
		}catch(Exception e){
		  // System.out.println("Pre required CME WF catch block");
			
			resultDetails.setFlag(false);
			return resultDetails;
		}
		return resultDetails;
}
public  ResultDetails ProgramPreAct_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st,String value)
{
	try{
		ConnectionDB();
		rs=st.executeQuery("select distinct qd.questionnaire_id,p.program_id from QUESTIONNAIRE_FORM qf,program p,activity_info i,QUESTION_DISPLAY qd where qf.questionnaire_id=qd.questionnaire_id and qd.IS_REQUIRED=0 and p.questionnaire_id=qf.questionnaire_id and qf.form_id=qd.form_id and qf.form_type_id=2 and p.activity_id=i.activity_id  and i.expiration_date>(sysdate+1) and rownum<1000");	
		int k=0;
		while(rs.next())
		{
			k++;
		}

		int p= getRandom(k);
		System.out.println("p="+p);
		rs.beforeFirst();
		rs.absolute(p);		
		int program_id,questionnaire_id,article_id,no_of_completed_forms=3;
		program_id=rs.getInt(2);questionnaire_id=rs.getInt(1);
		System.out.println("program_id="+program_id);
		ConnectionDB();
		rs=st.executeQuery("select article_id from PROGRAM_ARTICLE where program_id="+program_id);
		int m=0;
		while(rs.next())
		{
			m++;
		}

		int q= getRandom(m);
		System.out.println("q="+q);
		rs.beforeFirst();
		rs.absolute(q);

		article_id=rs.getInt(1);
		//article_id=76564;
		webdriver.get("http://www."+System.getenv("env")+".medscape.org/viewarticle/"+article_id);
		System.out.println("article_id1="+article_id);
		
		try {	
			if(webdriver.getTitle().equalsIgnoreCase("Pre-Activity Questionnaire"))
			{
				webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();
				WebDriverUtils.waitForPageToLoad(webdriver, "20000");
			}
		}
		catch(Exception e1){resultDetails=ProgramPreAct_SINGLEPAGE(webdriver,rs,st,value);}

		webdriver.get("http://www."+System.getenv("env")+".medscape.org/viewarticle/"+article_id);
		System.out.println("article_id1="+article_id);
		
		try{
			if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
			{
				System.out.println("internal form");
				webdriver.findElement(By.linkText("Continue to Activity")).click();
			}
			}catch(Exception e){System.out.println("PreAct not displayed");}
		
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");
		}catch(Exception e){
		  // System.out.println("Pre required CME WF catch block");
			
			resultDetails.setFlag(false);
			return resultDetails;
		}
		return resultDetails;
}
public  ResultDetails ProgramEarnCME_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st,String value)
{
try{
	ConnectionDB();
rs=st.executeQuery("select distinct p.program_id,p.questionnaire_id from QUESTIONNAIRE_FORM qf,program p,activity_info i where  p.questionnaire_id=qf.questionnaire_id  and qf.form_type_id!=2 and  p.activity_id=i.activity_id  and i.expiration_date>(sysdate+1) and rownum<1000");

int k=0;
while(rs.next())
{
	k++;
}

int p= getRandom(k);
System.out.println("p="+p);
rs.beforeFirst();
rs.absolute(p);		

int program_id,questionnaire_id,article_id,no_of_completed_forms=3;
program_id=rs.getInt(1);questionnaire_id=rs.getInt(2);
System.out.println("program_id="+program_id);
ConnectionDB();
rs=st.executeQuery("select article_id from PROGRAM_ARTICLE where program_id="+program_id);
int m=0;
while(rs.next())
{
	m++;
}

int q= getRandom(m);
System.out.println("q="+q);
rs.beforeFirst();
rs.absolute(q);

article_id=rs.getInt(1);
//article_id=76564;
webdriver.get("http://www."+System.getenv("env")+".medscape.org/viewprogram/"+program_id);
//System.out.println("article_id1="+article_id);

try{
	if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
	{
		webdriver.findElement(By.linkText("Continue to Activity")).click();
	}
	}catch(Exception e){}

try{
	if(webdriver.findElement(By.linkText("Earn CME/CE Credit �")).isDisplayed()){					
	webdriver.findElement(By.linkText("Earn CME/CE Credit �")).click();
	WebDriverUtils.waitForPageToLoad(webdriver, "20000");
	 resultDetails=internal_SINGLEPAGE(article_id,value,questionnaire_id,rs,st,webdriver,no_of_completed_forms,article_id);
	}
}catch(Exception e){}
	
try{
	if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
	{
	webdriver.findElement(By.partialLinkText("Earn CME Credit")).click();
	resultDetails=internal_SINGLEPAGE(article_id,value,questionnaire_id,rs,st,webdriver,no_of_completed_forms,article_id);
	}
}catch(Exception e){
	resultDetails=ProgramEarnCME_SINGLEPAGE(webdriver,rs,st,value);
	}

webdriver.get("http://www."+System.getenv("env")+".medscape.org/viewprogram/"+program_id);

try{
	if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
	{
	webdriver.findElement(By.linkText("Continue to Activity")).click();
	}
	}catch(Exception e){}

try{
	if(webdriver.findElement(By.linkText("Earn CME/CE Credit �")).isDisplayed()){					
	webdriver.findElement(By.linkText("Earn CME/CE Credit �")).click();
	}
}catch(Exception e){}
	
try{
	if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
	{
	webdriver.findElement(By.partialLinkText("Earn CME Credit")).click();
	}
}catch(Exception e){}

try{	
	String msg="You have already earned credit for this activity.";
if((webdriver.getPageSource().toLowerCase().trim()
		.contains(msg.toLowerCase().trim())))
{
	System.out.println("You have already earned credit for this activity.");
	WebDriverUtils.waitForPageToLoad(webdriver, "20000");
}
}catch(Exception e){}	

resultDetails.setFlag(true);
resultDetails.setErrorMessage("");
}catch(Exception e){
  // System.out.println("Pre required CME WF catch block");
	resultDetails.setFlag(false);
	return resultDetails;
}
return resultDetails;
}
public ResultDetails internal_SINGLEPAGE(int article_id,String value, int questionnaire_id, ResultSet rs,Statement st, WebDriver webdriver, int no_of_completed_forms,int article_id2) throws SQLException
{
	int choice_id[]=new int[45];
try{
	try{
		
		int form_type_id=3;
		System.out.println("form_type_id(Interna@post)="+form_type_id);
		ConnectionDB();
		rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
		rs.next();
		int form_id=rs.getInt(1);
		System.out.println("form_id(Interna@post)="+form_id);
		int i=0,j=0;
		ConnectionDB();
		rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.is_correct=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
		if(rs.next())
		{	rs.beforeFirst();
			while(rs.next()){
				choice_id[i]=rs.getInt(1);
			//	System.out.println("choice_id["+i+"]="+choice_id[i]);
			i++;j++;
			}					
	for(i=0;i<j;i++)
	{											
	webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
	//WebDriverUtils.waitForPageToLoad(webdriver, "5000");
	}				
	webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();
}
else
{
ConnectionDB();
rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
if(rs.next())
{
rs.beforeFirst();
while(rs.next()){
choice_id[i]=rs.getInt(1);
//System.out.println("choice_id["+i+"]="+choice_id[i]);
i++;j++;
}						
for(i=0;i<j;i++)
{									
webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
}				
webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();
}		
}	
	}	
catch(Exception e)
{
	int form_type_id=4,i=0,j=0;
	System.out.println("form_type_id(post)="+form_type_id);
	ConnectionDB();
	rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
	rs.next();
	int form_id=rs.getInt(1);
	System.out.println("form_id(post)="+form_id);
	ConnectionDB();		
	rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.is_correct=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
	if(rs.next())
	{
		rs.beforeFirst();
		while(rs.next()){
			choice_id[i]=rs.getInt(1);
			//System.out.println("choice_id["+i+"](asfsaf)="+choice_id[i]);
			i++;j++;			
			}						
	for(i=0;i<j;i++)
	{	
	webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
	WebDriverUtils.waitForPageToLoad(webdriver, "5000");	
	}
	webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();
	}
	else
	{
	ConnectionDB();
	rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
	if(rs.next())
	{
		rs.beforeFirst();
		while(rs.next()){
			choice_id[i]=rs.getInt(1);
			//System.out.println("choice_id["+i+"](5555)="+choice_id[i]);
		i++;j++;
		}						
	for(i=0;i<j;i++)
	{							
	webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();	
	}				
	}
	webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();
	}
}
	
	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");
	   }catch(Exception e){
			resultDetails.setFlag(false);
			return resultDetails;
	   }
	 return resultDetails;
}
public  ResultDetails Program_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st,String value)
{	
try{
	ConnectionDB();
	rs=st.executeQuery("select distinct p.program_id from QUESTIONNAIRE_FORM qf,program p,activity_info i where  p.questionnaire_id=qf.questionnaire_id  and qf.form_type_id!=2 and  p.activity_id=i.activity_id  and i.expiration_date>(sysdate+1) and rownum<1000");
	int k=0;
	while(rs.next())
	{
		k++;
	}

	int p= getRandom(k);
	System.out.println("p="+p);
	rs.beforeFirst();
	rs.absolute(p);		
	int program_id;
	program_id=rs.getInt(1);
	System.out.println("program_id="+program_id);
	webdriver.get("http://www."+System.getenv("env")+".medscape.org/viewprogram/"+program_id);
	WebDriverUtils.waitForPageToLoad(webdriver,"10000");

	try{
	if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
	{
		System.out.println("internal form");
		webdriver.findElement(By.linkText("Continue to Activity")).click();
	}
	}catch(Exception e){
		resultDetails=Program_SINGLEPAGE(webdriver, rs, st, value);
	}
	
	//int l=webdriver.findElements(By.xpath("//div[@id='program_toc']//")).size();
	int l=webdriver.findElements(By.xpath("//ol[@id='program_toc_list']/li")).size();
	System.out.println("l="+l);
	webdriver.findElement(By.xpath("//ol[@id='program_toc_list']/li/a")).click();
	WebDriverUtils.waitForPageToLoad(webdriver,"20000");
	int n=webdriver.findElements(By.xpath("//ol[@id='program_toc_list']/li")).size();
	System.out.println("n="+n);
	
	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");
	}catch(Exception e){
	  // System.out.println("Pre required CME WF catch block");
		resultDetails.setFlag(false);
		return resultDetails;
	}
	return resultDetails;
}

 
public static int getRandom(int i) {
	int r = 0;
	while (r == 0)
	{
	r = (int) ((Math.random()) * i) + 1;
	}
return r;	
}
public static void ConnectionDB(){
	OracleDB ODB=new OracleDB();
	ResultSet rs=null;Statement st=ODB.stmt;
try{
	System.out.println("In Connection");
	if(ODB.connection.isClosed())
	{
		System.out.println("IN connection_IF");

		ODB=new OracleDB(); 
		st=ODB.stmt;	
		rs=null;
	}}catch(Exception e)
	{		System.out.println("IN connection_Catch");

		System.out.println(e.getLocalizedMessage());
		ODB=new OracleDB(); 
		st=ODB.stmt;	
		rs=null;
	}
}

}
	    		  