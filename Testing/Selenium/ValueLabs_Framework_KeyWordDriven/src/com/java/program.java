package com.java;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.xml.sax.SAXException;

import com.java.Objects.ResultDetails;

public class program {
	ResultDetails resultDetails=new ResultDetails();
	OracleDB ODB=new OracleDB();
	
 Statement st=ODB.stmt;	ResultSet rs=null;
	public enum Program_Collections{
		PNR1,PRG1,COL1,CLT1
	}
	public static void select(WebDriver webdriver,String index){
		Select sel=new Select(webdriver.findElement(By.xpath("//select[@name='pCredits']")));
		sel.selectByIndex(Integer.parseInt(index));
		}
	
	public ResultDetails Program_Collection(WebDriver webdriver,String field, String value) throws ClassNotFoundException, SQLException
	{
		System.out.println("This is Collection of programs........");
		System.out.println("field= " + field + " value= " + value);
		Program_Collections ps=Program_Collections.valueOf(field.substring(0, 3));
		
		int COLLECTION_ID,k,p;
		field=field.substring(0, 3);
		try{
			
			System.out.println("This is Collection of programs........");
			switch(ps){
case COL1:
	ConnectionDB();
		rs=st.executeQuery("select distinct c.COLLECTION_ID,cp.PROGRAM_ID,p.activity_id from COLLECTION c,COLLECTION_PROGRAM cp, program p,ACTIVITY_INFO i WHERE p.activity_id=i.activity_id and p.PROGRAM_ID=cp.PROGRAM_ID and c.COLLECTION_ID=cp.COLLECTION_ID  and c.SITE_ON = 2003 and i.expiration_date>(sysdate+1) and rownum<10");
		
		rs.last();
		k =rs.getRow();
		System.out.println("We have only "+k+" Collections Which are not expired");
			
		p= getRandom(k);
		System.out.println("p="+p);
		rs.beforeFirst();
		rs.absolute(p);
		
		COLLECTION_ID=rs.getInt(1);
		System.out.println("COLLECTION_ID="+COLLECTION_ID);
		
		webdriver.get("http://www."+System.getenv("env")+".medscape.org/viewcollection/"+COLLECTION_ID);	
		WebDriverUtils.waitForPageToLoad(webdriver, "5000");
		break;
case CLT1:
	ConnectionDB();
	
rs=st.executeQuery("select distinct c.COLLECTION_ID,cp.PROGRAM_ID,p.activity_id from COLLECTION c,COLLECTION_PROGRAM cp, program p,ACTIVITY_INFO i WHERE p.activity_id=i.activity_id and p.PROGRAM_ID=cp.PROGRAM_ID and c.COLLECTION_ID=cp.COLLECTION_ID  and c.SITE_ON = 2003 and i.expiration_date>(sysdate+1) and rownum<10");
	
	rs.last();
	k =rs.getRow();
	System.out.println("We have only "+k+" Collections Which are not expired");
		
	p= getRandom(k);
	//System.out.println("p="+p);
	rs.beforeFirst();
	rs.absolute(p);
	
	COLLECTION_ID=rs.getInt(1);
	//int COLLECTION_ID=32499;
	System.out.println("COLLECTION_ID="+COLLECTION_ID);
	
	webdriver.get("http://www.qa00.medscape.org/viewcollection/"+COLLECTION_ID);	
	WebDriverUtils.waitForPageToLoad(webdriver, "5000");
	
	try {
		if(webdriver.getTitle().equalsIgnoreCase("Page Not Found"))
		{
			System.out.println("This is Page Not Found COllection,so Fetching for another COllection");
			Program_Collection(webdriver,field,value);
		}
	}
	
	catch(Exception e1){}
	
	try{
		if(webdriver.findElement(By.xpath("//div[4]/div/div[2]/div[2]/div/div/div[2]/ul/li/a")).isDisplayed())
		{
			webdriver.findElement(By.xpath("//div[4]/div/div[2]/div[2]/div/div/div[2]/ul/li/a")).click();
			WebDriverUtils.waitForPageToLoad(webdriver, "5000");
		}
	}
	catch(Exception e)
	{
		try{
		if(webdriver.findElement(By.xpath("//div[4]/div/div[2]/div[2]/div/div/div[2]/div/a/strong")).isDisplayed())
		{
		webdriver.findElement(By.xpath("//div[4]/div/div[2]/div[2]/div/div/div[2]/div/a/strong")).click();
		WebDriverUtils.waitForPageToLoad(webdriver, "5000");
		System.out.println("link catch");
		}	
		}catch(Exception e3)
		{
	//	System.out.println("catch block");
			Program_Collection(webdriver,field,value);

		}
	}
	try{
		if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
		{
			 webdriver.findElement(By.linkText("Continue to Activity")).click();
			 WebDriverUtils.waitForPageToLoad(webdriver, "5000");
		}
	}catch(Exception e)
	{}
	
	try{
		if(webdriver.findElement(By.linkText("Earn CME/CE Credit »")).isDisplayed()){					
		webdriver.findElement(By.linkText("Earn CME/CE Credit »")).click();
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
				break;
			}
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");
		   }catch(Exception e){
				resultDetails.setFlag(false);
				return resultDetails;
		   }
		 return resultDetails;
		
		
}

public ResultDetails program(WebDriver webdriver,String field, String value) throws ClassNotFoundException, SQLException
{
	int program_id,article_id,questionnaire_id,no_of_completed_forms=3;;

	System.out.println("field= " + field + " value= " + value);
	Program_Collections ps=Program_Collections.valueOf(field.substring(0, 3));
	//field = field.substring(3, field.length());
	field=field.substring(0, 3);
	try{
		System.out.println("This is a Program");
		switch(ps){
		case PNR1://Preact not required program
			ConnectionDB();
rs=st.executeQuery("select distinct qd.questionnaire_id,p.program_id from QUESTIONNAIRE_FORM qf,program p,activity_info i,QUESTION_DISPLAY qd where qf.questionnaire_id=qd.questionnaire_id and qd.IS_REQUIRED=0 and p.questionnaire_id=qf.questionnaire_id and qf.form_id=qd.form_id and qf.form_type_id=2 and p.activity_id=i.activity_id  and i.expiration_date>(sysdate+1) and rownum<1000");	
			break;
		case PRG1://
			ConnectionDB();
rs=st.executeQuery("select distinct qf.questionnaire_id,p.program_id from QUESTIONNAIRE_FORM qf,program p,activity_info i where p.questionnaire_id=qf.questionnaire_id and  p.activity_id=i.activity_id  and i.expiration_date>(sysdate+1) and rownum<1000");		
		break;
		}
		int k=0;
		while(rs.next())
		{
			k++;
		}

		int p= getRandom(k);
		System.out.println("p="+p);
		rs.beforeFirst();
		rs.absolute(p);
		//questionnaire_id=26449;
		
	questionnaire_id=rs.getInt(1);
		program_id=rs.getInt(2);
		//program_id=32600;
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
	if(webdriver.getTitle().equalsIgnoreCase("Page Not Found"))
	{
		System.out.println("This is Page Not Found Program,so Fetching for another Program");
		resultDetails=program(webdriver,field,value);
	}
	else if(webdriver.getTitle().equalsIgnoreCase("Pre-Activity Questionnaire")){
	resultDetails=pre(article_id,value,questionnaire_id,webdriver,no_of_completed_forms,article_id);
 }
 else{
	 System.out.println("No pre activity form");
	 webdriver.findElement(By.linkText("Continue to Activity")).click();
	 resultDetails=internal(article_id,value,questionnaire_id,webdriver,no_of_completed_forms,article_id);
 }	
}
catch(Exception e){
	System.out.println("353");
	resultDetails=program(webdriver, field, value);
	e.printStackTrace();
System.out.println(e.getMessage());
}
	   }catch(Exception e){
			System.out.println("353");
		   System.out.println(e.getMessage());
			resultDetails.setFlag(false);
			return resultDetails;
	   }
	 return resultDetails;
}//program

public ResultDetails internal(int article_id,String value, int questionnaire_id,WebDriver webdriver, int no_of_completed_forms,int article_id2) throws SQLException
{
	int choice_id[]=new int[45];
	try{
		if(webdriver.findElement(By.linkText("Earn CME/CE Credit »")).isDisplayed()){					
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
	WebDriverUtils.waitForPageToLoad(webdriver, "30000");
	
	webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).click();

	resultDetails=Evaluation(webdriver,questionnaire_id);
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
WebDriverUtils.waitForPageToLoad(webdriver, "30000");
webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).click();
resultDetails=Evaluation(webdriver,questionnaire_id);
}		
}	
	}	
catch(Exception e)
{
	int form_type_id=4,i=0,j=0;
	System.out.println("form_type_id(post)="+form_type_id);
	try{
		try{
			System.out.println("IN connection");
			if(ODB.connection.isClosed())
			{
				System.out.println("IN connection_IF");

				ODB=new OracleDB(); 
				st=ODB.stmt;	
				rs=null;
			}}catch(Exception e3)
			{		System.out.println("IN connection_Catch");

				System.out.println(e.getLocalizedMessage());
				ODB=new OracleDB(); 
				st=ODB.stmt;	
				rs=null;
			}
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
	WebDriverUtils.waitForPageToLoad(webdriver, "30000");
	webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).click();
	resultDetails=Evaluation(webdriver,questionnaire_id);
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
	WebDriverUtils.waitForPageToLoad(webdriver, "30000");
	webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).click();
	resultDetails=Evaluation(webdriver,questionnaire_id);
	}
	}catch(Exception e1){e1.printStackTrace();
	System.out.println(e1.getMessage());}
}
	
	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");
	   }catch(Exception e){
			resultDetails.setFlag(false);
			return resultDetails;
	   }
	 return resultDetails;
}//internal _program

public ResultDetails Evaluation(WebDriver webdriver,int questionnaire_id) throws SQLException
{
	int form_type_id=5;
	System.out.println("In Evaluation Form.....");
	System.out.println("form_type_id(evaluation form)="+form_type_id);
	System.out.println("queationnaire_id="+questionnaire_id);
	try{	

	//rs=st.executeQuery("select form_id from questionnaire_form where form_type_id=5 and questionnaire_id="+questionnaire_id);
	System.out.println("35909250");
	//rs.next();
	//int form_id=rs.getInt(1);
	int form_id=2;
	System.out.println("form_id(evaluation form)="+form_id);
	
	try{
		if(webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/input")).isDisplayed())
		{
	webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/input")).submit();
	System.out.println("submit@eval clicked");
	WebDriverUtils.waitForPageToLoad(webdriver, "20000");
		}}catch(Exception e){}
	
	try{
		String msg="All questions must be answered in order to proceed.";
	if((webdriver.getPageSource().toLowerCase().trim()
			.contains(msg.toLowerCase().trim())))
	{
		System.out.println("All questions must be answered in order to proceed.");
		//SINGLEPAGE2 s =new SINGLEPAGE2();
		//resultDetails=s.EvalReq(questionnaire_id,webdriver,form_id);
	}
	}catch(Exception e){}	
	
	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");
	   }catch(Exception e){
		   e.printStackTrace();
		   System.out.println(e.getMessage());
			resultDetails.setFlag(false);
			return resultDetails;
	   }
	 return resultDetails;
}//Evaluation_program

public ResultDetails pre(Integer article_id1,String value,int questionnaire_id,WebDriver webdriver,int no_of_completed_forms,int article_id) throws SQLException, InterruptedException, ClassNotFoundException, IOException, ParserConfigurationException, SAXException, XPathExpressionException
{
	int form_type_id=2;
	int choice_id[]=new int[45];
	System.out.println("In Pre-Questionnaire Form.....");
	System.out.println("form_type_id(Pre-Activity)="+form_type_id);
	try{
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
	int i=0,j=0;														
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
	WebDriverUtils.waitForPageToLoad(webdriver, "10000");
	System.out.println("pre -submit");	
	
	webdriver.findElement(By.linkText("Continue to Activity")).click();
	
	resultDetails=internal(article_id1,value,questionnaire_id,webdriver,no_of_completed_forms,article_id);
	WebDriverUtils.waitForPageToLoad(webdriver, "30000");
	}	
	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");
	   }catch(Exception e){
			resultDetails.setFlag(false);
			return resultDetails;
	   }
	 return resultDetails;
}//Pre_program

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
			System.out.println("IN connection");
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
