package com.java;
import java.io.File;
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
public class CME_Keywords extends TestType {

	public WebDriver webdriver = null;
	public Actions builder = null;
	public static String path="";

	ResultDetails resultDetails = new ResultDetails();
	
	OracleDB ODB=new OracleDB();
	Statement st=ODB.stmt;	ResultSet rs=null;
	
	public CME_Keywords(WebDriver webdriver) {
		this.webdriver = webdriver;
	}
	public enum Program_Collections{
		PNR,PRG,COL,CLT
	}
	public enum MULITPAGE{
		 GRT, NXT, ERN,BNT, BTR,BNM,BTM
			}
	String s1="",s2="",s3="";
	public enum Article_SinglePage{
		BTM,CME, NUR, PRM, LOC,OHC, NPC, PAC, BNT, PRQ, PNR, BTS, BTR, BTZ, EVL, EVR, ART, ARE, NTC
	}
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
	// Keywords
	public enum ActionTypes {
		PROGRAM,ARTICLE,MULTIPAGE,SINGLEPAGE,COLLECTION
	}
	
	public ResultDetails performAction(String fieldText, String value,
			String actionType, String fieldName) {
		try {
			ActionTypes actTypes = ActionTypes
					.valueOf(actionType.toUpperCase());
			File f=new File("./FrameWork/lib");
			path=f.getCanonicalPath();
			switch (actTypes) {
		case ARTICLE:	resultDetails =Article(webdriver, fieldText, value);
						break;
		case SINGLEPAGE:resultDetails = SINGLEPAGE(webdriver, fieldText, value);
						break;
		case MULTIPAGE:resultDetails=MULTIPAGE(webdriver,fieldText,value);
						break;
		case PROGRAM:resultDetails=program(webdriver,fieldText,value);
						break;
		case COLLECTION:resultDetails=Program_Collection(webdriver,fieldText,value);
						break;
			}
		}catch(Throwable e){
			System.out.println("Catch Block in CME_KeyWords");
			resultDetails.setFlag(false);
			System.out.println(e.getMessage());
			return resultDetails;
		}
		return resultDetails;
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
		switch(ps)
		{
		case COL:ConnectionDB();
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
		case CLT:ConnectionDB();
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
				}catch(Exception e1){}

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
	switch(ps)
	{
	case PNR:ConnectionDB();//Preact not required program
			  rs=st.executeQuery("select distinct qd.questionnaire_id,p.program_id from QUESTIONNAIRE_FORM qf,program p,activity_info i,QUESTION_DISPLAY qd where qf.questionnaire_id=qd.questionnaire_id and qd.IS_REQUIRED=0 and p.questionnaire_id=qf.questionnaire_id and qf.form_id=qd.form_id and qf.form_type_id=2 and p.activity_id=i.activity_id  and i.expiration_date>(sysdate+1) and rownum<1000");	
			  break;
	case PRG:ConnectionDB();
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
		//questionnaire_id=25081;
		
		questionnaire_id=rs.getInt(1);
		program_id=rs.getInt(2);
		//program_id=32504;
		System.out.println("Program_id="+program_id);
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
		//article_id=765002;
		
		webdriver.get("http://www."+System.getenv("env")+".medscape.org/viewarticle/"+article_id);
		System.out.println("article_id1="+article_id);
		
try {
	if(webdriver.getTitle().equalsIgnoreCase("Page Not Found"))
	{
		System.out.println("This is Page Not Found Program,so Fetching for another Program");
		resultDetails=program(webdriver,field,value);
	}
	else if(webdriver.getTitle().equalsIgnoreCase("Pre-Activity Questionnaire"))
		{
			webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();
			String error="All questions must be answered in order to proceed.";
			
				if (webdriver.getPageSource().toLowerCase().trim()
						.contains(error.toLowerCase().trim()))
				{
					System.out.println("Answering the preActivity questions");
					resultDetails=Pre_Answered(webdriver,rs,st);
				}
			}
		}catch(Exception e){}
	try{
		if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
		{
			System.out.println("No pre activity form");
			webdriver.findElement(By.linkText("Continue to Activity")).click();
	
			resultDetails=internal(article_id,value,questionnaire_id,webdriver,no_of_completed_forms,article_id);
		}	
	}catch(Exception e){
	System.out.println("353");
	resultDetails=program(webdriver, field, value);
	e.printStackTrace();
	System.out.println(e.getMessage());
	}
	   }catch(Exception e3){
			System.out.println("353");
		   System.out.println(e3.getMessage());
			resultDetails.setFlag(false);
			return resultDetails;
	   }
	 return resultDetails;
}//program
public ResultDetails internal(int article_id,String value, int questionnaire_id,WebDriver webdriver, int no_of_completed_forms,int article_id2) throws SQLException
{
int choice_id[]=new int[45];
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
		
	
	try{
		String msg="All questions must be answered in order to proceed.";
	if((webdriver.getPageSource().toLowerCase().trim()
			.contains(msg.toLowerCase().trim())))
	{
		System.out.println("All questions must be answered in order to proceed.");
		//SINGLEPAGE2 s =new SINGLEPAGE2();
		resultDetails=EvalReq_SINGLEPAGE(questionnaire_id,webdriver,form_id);
	}
	}catch(Exception e){}	
		}}catch(Exception e){}
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
public ResultDetails MULTIPAGE(WebDriver webdriver, String field, String value) throws ClassNotFoundException, SQLException, ParserConfigurationException, IOException, XPathExpressionException, InterruptedException, SAXException
{
try {
	webdriver.get("http://www."+System.getenv("env")+".medscape.org/");
	webdriver.manage().window().maximize();
		
	resultDetails=FetchArticle_MULTIPAGE(webdriver,field,value);
	return resultDetails;		
	}
	catch(Throwable e) {
		System.out.println("e456");
		System.out.println(e.getMessage());
		resultDetails=FetchArticle_MULTIPAGE(webdriver,field,value);
		return resultDetails;
		}		
	}//MULTIPAGE

public ResultDetails  FetchArticle_MULTIPAGE(WebDriver webdriver,String field,String value) throws SQLException, ClassNotFoundException, XPathExpressionException, IOException, ParserConfigurationException, InterruptedException, SAXException 
	{
		System.out.println("field= " + field + " value= " + value);
		MULITPAGE art = MULITPAGE.valueOf(field);
		//field = field.substring(3, field.length());
		try{
	Integer article_id1 = null;

	switch(art)
	{
	case GRT:ConnectionDB();
			rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.passing_score=70 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");		
			break;
	case BTR:try{
				System.out.println("IN connection");
				if(ODB.connection.isClosed())
				{
					System.out.println("IN connection_IF");

					ODB=new OracleDB(); 
					st=ODB.stmt;	
					rs=null;
					try{
						int questionnaire_id=26593;
						rs=st.executeQuery("select article_id from article where questionnaire_id="+questionnaire_id);
						int article1=rs.getInt(1);
						}catch(Exception e1){
							System.out.println("Connection_catch");
							ODB=new OracleDB(); 
							st=ODB.stmt;	
							rs=null;
						}
				}}catch(Exception e)
				{		
					System.out.println("IN connection_Catch");
					System.out.println(e.getLocalizedMessage());
					ODB=new OracleDB(); 
					st=ODB.stmt;	
					rs=null;
				}
		
			rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qd.form_id=3 and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
			break;
	case ERN:System.out.println("Earn credits");
			ConnectionDB();
			rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
			break;
	case BTM:ConnectionDB();
			rs=st.executeQuery("select distinct qd.questionnaire_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=0 and  qd.form_id=qf.form_id and qd.questionnaire_id=qf.questionnaire_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<500 minus select distinct qd.questionnaire_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=1 and  qd.form_id=qf.form_id and qd.questionnaire_id=qf.questionnaire_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<2000");
			break;
	}
	int k;
	rs.last();
	k= rs.getRow();
	System.out.println("Number of Rows return by the Query="+k);
	int p= getRandom(k);
	System.out.println("The Querstionanire_id contians in "+p+"th row");
	rs.beforeFirst();
	rs.absolute(p);

	int questionnaire_id;
	//questionnaire_id=rs.getInt(1);
	questionnaire_id=25677;
	System.out.println("questionnaire_id="+questionnaire_id);
	ConnectionDB();
	rs=st.executeQuery("select article_id from article where questionnaire_id="+questionnaire_id);
	rs.next();
	//article_id1=770645;
	article_id1=765369;
	//article_id1=rs.getInt(1);
	
	resultDetails=ArticleType_MULTIPAGE(webdriver,article_id1,questionnaire_id,art,field,value);
	}catch(Throwable e){
			e.printStackTrace();
			resultDetails.setFlag(false);
			return resultDetails;
			}
		return resultDetails;
	}//FetchArticle_multipage
public ResultDetails ArticleType_MULTIPAGE(WebDriver webdriver,int article_id1,
		int questionnaire_id, MULITPAGE art, String field, String value) 
{
try//Total class
{
	webdriver.get("http://www."+System.getenv("env")+".medscape.org/viewarticle/"+article_id1);
	System.out.println("article_id::"+article_id1);
	try{
		if(webdriver.getTitle().equalsIgnoreCase("Pre-Activity Questionnaire"))
		{
			webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();
			String error="All questions must be answered in order to proceed.";
			
				if (webdriver.getPageSource().toLowerCase().trim()
						.contains(error.toLowerCase().trim()))
				{
					System.out.println("Answering the preActivity questions");
					resultDetails=Pre_Answered(webdriver,rs,st);
				}
			}
	}catch(Exception e){}
	try{
		if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
		{
			 webdriver.findElement(By.linkText("Continue to Activity")).click();
			 WebDriverUtils.waitForPageToLoad(webdriver, "5000");
			 System.out.println("Continue to Activity clicked");
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
	int n=0;
	n=webdriver.getPageSource().indexOf("current_page_nav");
	//System.out.println("n="+n);
	if(n>0)
	{
		System.out.println("**********THIS IS A MULTI-PAGE**********");
		resultDetails=MULTIPAGE_Answering(webdriver,article_id1, questionnaire_id,art,field,value);
	}
	else if(webdriver.getTitle().equalsIgnoreCase("Page Not Found"))
	{
		System.out.println("This is page not found artilce");
		WebDriverUtils.waitForPageToLoad(webdriver, "1000");	
		resultDetails=FetchArticle_MULTIPAGE(webdriver,field,value);
	}
	else{
		System.out.println("THIS IS A SINGLE-PAGE SEARCHING FOR MULTI-PAGE");
		resultDetails=FetchArticle_MULTIPAGE(webdriver,field,value);
	}
	}catch(Exception e)
	{
		resultDetails=FetchArticle_MULTIPAGE(webdriver,field,value);
	}
	
}catch(Throwable e){
	System.out.println("Catch Block in June_CME_KeyWords_Multipage");
	resultDetails.setFlag(false);
	System.out.println(e.getMessage());
	return resultDetails;
}
return resultDetails;
}
public ResultDetails MULTIPAGE_Answering(WebDriver webdriver,int article_id1,int questionnaire_id,MULITPAGE art,String field,String value)
{
	try{
		if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
		{
			System.out.println("internal form");
			webdriver.findElement(By.linkText("Continue to Activity")).click();
			Thread.sleep(5000);
		}}catch(Exception e){}
		try{
		if(webdriver.findElement(By.linkText("Transcript")).isDisplayed())
		{
			System.out.println("Transcript displayed");
			webdriver.findElement(By.linkText("Transcript")).click();
			Thread.sleep(2000);
		}}catch(Exception e){}
		
		String g=webdriver.findElement(By.id("current_page_nav")).getText();
		System.out.println("g="+g);
		String temp1[]=g.split(" ");
		for(int i3 =0; i3 < temp1.length ; i3++)
		{
			System.out.println(temp1[i3]);
		    //System.out.println("temp1["+i3+"](Number of pages)="+temp1[i3]);
		}
		int NOP=Integer.parseInt(temp1[3]);
	
try{
	switch(art)
	{
	case BTM:resultDetails=BottomNotRequired_MULTIPAGE(NOP,webdriver,questionnaire_id,field,value);
			break;
	case BTR:resultDetails=BottomRequired_MULTIPAGE(webdriver,NOP,questionnaire_id);
			break;
	case ERN:resultDetails=EARNCME_CREDITS_MULTIPAGE(NOP,webdriver,questionnaire_id);
			break;
	case GRT:resultDetails=GrantAttribution_MULTIPAGE(NOP,webdriver,questionnaire_id);
			break;
	}
 }catch(Throwable e){
		e.printStackTrace();
		resultDetails.setFlag(false);
		System.out.println(e.getMessage());
		return resultDetails;
		}
		return resultDetails;
}//XMLQUERY ANSWERING _MULITPAGE

public ResultDetails BottomNotRequired_MULTIPAGE(int NOP,WebDriver webdriver,int questionnaire_id,String field,String value) throws ClassNotFoundException, XPathExpressionException, SQLException, IOException, ParserConfigurationException, InterruptedException, SAXException
{
try{//class try
	for (int i=0; i<NOP; i++)
	{
	System.out.println("In Page(BTM):"+(i+1));
	try{			
	if(webdriver.findElement(By.linkText("Earn CME/CE Credit �")).isDisplayed())
	{						
		System.out.println("Earn CME/CE(Rigth Rail) Credit is displayed @page="+(i+1));
	}
	}catch(Exception e){
		try{
			if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
			{	
			System.out.println("Earn CME(Rigth Rail) Credit is displayed @page="+(i+1));
			}
			}catch(Exception  e2)
			{
				System.out.println("In This article does not have Earn CMe credits.....");
				resultDetails=FetchArticle_MULTIPAGE(webdriver, field, value);
			}
		}
	try{//Earn CME bottom link
		if(webdriver.findElement(By.xpath("//div[@id='next_page_nav']/span/a")).isDisplayed())
		{
			System.out.println("Earn CME(Bottom) Credit is displayed @page="+i);
			webdriver.findElement(By.xpath("//div[@id='next_page_nav']/span/a")).click();
		}				
	}catch(Exception e1)
	{
		try{//save and proceed
		if(webdriver.findElement(By.xpath("//input[@name='submitbutton']")).isDisplayed())
		{
			webdriver.findElement(By.xpath("//input[@name='submitbutton']")).click();
			System.out.println("save and proceed clikced");
		}
		}
		catch(Exception e2){
			try{
			if(webdriver.findElement(By.linkText("Next Page �")).isDisplayed())
			{
				webdriver.findElement(By.linkText("Next Page �")).click();
				System.out.println("next page clicked");
				WebDriverUtils.waitForPageToLoad(webdriver, "20000");			
			}
			}
	catch(Exception e)
	{	
	try{			
	if(webdriver.findElement(By.linkText("Earn CME/CE Credit �")).isDisplayed())
	{						
	System.out.println("Earn CME/CE(Rigth Rail) Credit is displayed @page="+(i+1));
	}
	}catch(Exception e3)
	{
	if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
	{	
		System.out.println("Earn CME(Rigth Rail) Credit is displayed @page="+(i+1));
	}					
	}//catch e3
	}//catch e
	}//catch e2
	}//catch e1
	}//for
	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");
	}catch(Throwable e){
		resultDetails.setFlag(false);System.out.println("e1");
		return resultDetails;
		}
	return resultDetails;
}//BottomNotRequiredMultipage
public ResultDetails EARNCME_CREDITS_MULTIPAGE(int NOP,WebDriver webdriver,int questionnaire_id) throws SQLException, InterruptedException, XPathExpressionException
{
try{
	for (int i=0; i<NOP; i++)
	{
	System.out.println("In Page(BTM):"+(i+1));
	try{
		if(webdriver.findElement(By.linkText("Earn CME/CE Credit �")).isDisplayed())
		{					
			System.out.println("Earn CME/CE(Rigth Rail_BTM) Credit is displayed @page="+(i+1));
		}
	}catch(Exception e){}
	try{
		if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
		{
			System.out.println("Earn CME(Rigth Rail_BTM) Credit is displayed @page="+(i+1));
		}
		WebDriverUtils.waitForPageToLoad(webdriver, "20000");
	}catch(Exception e){}
	try{//Earn CME bottom link
		if(webdriver.findElement(By.xpath("//div[@id='next_page_nav']/span/a")).isDisplayed())
		{
			System.out.println("Earn CME(Bottom) Credit is displayed @page="+i);
			webdriver.findElement(By.xpath("//div[@id='next_page_nav']/span/a")).click();
		}	
	}catch(Exception e1)
	{	
		try
		{
		if(webdriver.findElement(By.linkText("Next Page �")).isDisplayed())
		{
			webdriver.findElement(By.linkText("Next Page �")).click();
		 	System.out.println("next page clicked");
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");			
		}
		}catch(Exception e2)
		{
		if(webdriver.findElement(By.xpath("//input[@name='submitbutton']")).isDisplayed())
		{
			System.out.println("Save & Proceed @"+(i+1));
			String p1 = webdriver.getPageSource();
			String word = "form_id";
			int q=0;int k[]=new int[10], form_id1[]=new int[40];
			for (int i4 = p1.length(); (i4= p1.lastIndexOf(word, i4- 1)) != -1; )
			{
				while(true){
			    q++;
				break;
				}
			} 
			System.out.println("Number of Form_id's in the Page["+i+"] are="+q);
			int a=0,j=0;String temp[]=null;
			for (int i2=-1;(i2=p1.indexOf(word,i2+1))!=-1;)
			{	
				if(a<q)
				{
					k[a]=i;
					//System.out.println("k["+a+"]="+i);
					String Page = webdriver.getPageSource().substring(i2-10,i2-7);
					//System.out.println("form_id="+Page);
					String f="";
					temp=Page.trim().split(f);
					for(int i1 =0; i1 < temp.length ; i1++)
					{
						//System.out.println("temp["+i1+"]="+temp[i1]);
					}	
					a++;
				}
				form_id1[j]=Integer.parseInt(temp[2]);
				System.out.println("In Page["+(i+1)+"] with Form_Number["+(j+1)+"] have Form_id="+form_id1[j]);
				j++;		
			}
		
	if(form_id1.length>=1)//Number of forms in page morethan 1
	{
		System.out.println("In Page(Form_id):"+(i+1));
		//System.out.println("form_id1.length:"+j);
		for(int c=0;c<j;c++)
		{
			System.out.println("Answering form no:"+(c+1));
			int form_id=form_id1[c];
			System.out.println("Current form_id="+form_id);
				
			resultDetails=InternalAnswering_MULTIPAGE2(form_id,webdriver,rs,st,questionnaire_id,c);
		}	
	}
		}
	}//ctach e2
	}//Catch
	}
	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");
}catch(Throwable e){
	//e.printStackTrace();
	resultDetails.setFlag(false);
	System.out.println(e.getMessage());
	return resultDetails;
	}
	return resultDetails;
}
public ResultDetails InternalAnswering_MULTIPAGE2(int form_id,WebDriver webdriver,ResultSet rs,Statement st,int questionnaire_id,int c) throws SQLException, InterruptedException
	{

		int choice_id[]=new int[45];
	try{
		rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.is_correct=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
		if(rs.next())
		{
			int	k=0,j=0;
			rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.is_correct=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
			while(rs.next())
			{
				choice_id[k]=rs.getInt(1);
				
				//System.out.println("choice_id["+k+"]=" +""+choice_id[k]);
				k++;j++;
			}
			try { Thread.sleep(1000); }catch (Exception e1) {}
			for(k=0;k<j;k++)
			{				
				webdriver.findElement(By.xpath("//input[@value="+choice_id[k]+"]")).click();
			}
		}
		else
		{
			//System.out.println("hello");
			rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
			int	k=0,j=0;
			if(!rs.next())
			{
								
				//question have no choice id
				int question_id[]=new int[45],p=0,l=0;
				rs=st.executeQuery("select question_id from question_display where questionnaire_id="+questionnaire_id+" and form_id="+form_id);
				while(rs.next()){			
					question_id[p]=rs.getInt(1);
					//System.out.println("question["+i+"]="+question_id[i]);
					p++;l++;
				}
				for(p=0;p<l;p++)
				{
			webdriver.findElement(By.name("option-"+question_id[p]+"")).click();
				l++;
				}
			}
			else{
			
			rs.beforeFirst();
			while(rs.next())
			{
				choice_id[k]=rs.getInt(1);
				//question_id[k]=rs.getInt(2);
				//System.out.println("choice_id["+k+"]="+choice_id[k]);
				//System.out.println("question_id["+k+"]="+question_id[k]);
				k++;j++;
			}
			try { Thread.sleep(1000); }catch (Exception e1) {}
			for(k=0;k<j;k++)
			{
				
				webdriver.findElement(By.xpath("//input[@value="+choice_id[k]+"]")).click();
			}
			}
		}
		webdriver.findElement(By.xpath("//input[@name='submitbutton']")).click();
		System.out.println("Save and Proceeed clicked");
		
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");

		return resultDetails;
	}catch(Throwable e){
		resultDetails.setFlag(false);
		System.out.println("sas4325");
		//System.out.println(e.getMessage());
		return resultDetails;
		}
	}
public ResultDetails GrantAttribution_MULTIPAGE(int NOP,WebDriver webdriver,int questionnaire_id)
{	
	
	try{
	for (int i = 0; i <NOP; i++)
	//for(int p=1;p<=t;p++)
	{
	try{
		if(webdriver.findElement(By.id("supporter_logo")).isDisplayed())
		{
			System.out.println("GRANT ATTRIBUTION @page no:"+(i+1));
			try
			{
			if(webdriver.findElement(By.xpath("//input[@name='submitbutton']")).isDisplayed())//for clicking Save and Proceed button if exists in the current page
			{	
				System.out.println("Save & Proceed() @"+(i+1));
				String p1 = webdriver.getPageSource();
				String word = "form_id";
				int q=0;int k[]=new int[10], form_id1[]=new int[40];
				for (int i4 = p1.length(); (i4= p1.lastIndexOf(word, i4- 1)) != -1; )
				{
					while(true){
				    q++;
					break;
					}
				} 
				System.out.println("Number of Form_id's in the Page["+(i+1)+"] are="+q);
				int a=0,j=0;String temp[]=null;
				for (int i2=-1;(i2=p1.indexOf(word,i2+1))!=-1;)
				{	
					if(a<q)
					{
						k[a]=i;
						//System.out.println("k["+a+"]="+i);
						String Page = webdriver.getPageSource().substring(i2-10,i2-7);
						//System.out.println("form_id="+Page);
						String f="";
						temp=Page.trim().split(f);
						for(int i1 =0; i1 < temp.length ; i1++)
						{
							//System.out.println("temp["+i1+"]="+temp[i1]);
						}	
						a++;
					}
					form_id1[j]=Integer.parseInt(temp[2]);
					System.out.println("In Page["+(i+1)+"] with Form_Number["+(j+1)+"] have Form_id="+form_id1[j]);
					j++;		
				}
			
		if(form_id1.length>=1)//Number of forms in page morethan 1
		{
			System.out.println("In Page(Form_id):"+(i+1));
			//System.out.println("form_id1.length:"+j);
			for(int c=0;c<j;c++)
			{
				System.out.println("Answering form no:"+(c+1));
				int form_id=form_id1[c];
				System.out.println("Current form_id="+form_id);
					
				resultDetails=InternalAnswering_MULTIPAGE2(form_id,webdriver,rs,st,questionnaire_id,c);
			}	
				}
				}
			}															
			catch(Exception e1)
			{
			try
			{
				if(webdriver.findElement(By.linkText("Next Page �")).isDisplayed())//for clicking next page button if exists in the current page
				{			
					System.out.println("logo @(NEXT_PAGE)page no:"+i); 
					webdriver.findElement(By.linkText("Next Page �")).click();
	    		 	System.out.println("next page clicked");
	    		 	WebDriverUtils.waitForPageToLoad(webdriver, "20000");	
				}
			}
			catch(Exception e2)
			{
			try
			{
			if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
			{	System.out.println("logo @(EARN_CE)page no:"+i);
				webdriver.findElement(By.linkText("Earn CME Credit")).click();
				WebDriverUtils.waitForPageToLoad(webdriver, "20000");					
			}}
			catch(Exception e3)
			{
				if(webdriver.findElement(By.linkText("Earn CME/CE Credit �")).isDisplayed())
				{
					System.out.println("logo @(EARN_CME)page no:"+i); 				
					webdriver.findElement(By.linkText("Earn CME/CE Credit �")).click();
					System.out.println("Earn CME/CE Credit  clicked...Moving to post-form");
					WebDriverUtils.waitForPageToLoad(webdriver, "20000");
				}
			}
			}
			}
		}}catch(Exception e){}
		}
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");
		}
	catch(Throwable e){
			System.out.println("37267");
			resultDetails.setFlag(false);
			//System.out.println(e.getMessage());
			return resultDetails;
			}
	
	return resultDetails;
	}
public ResultDetails BottomRequired_MULTIPAGE(WebDriver webdriver,int NOP,int questionnaire_id)
{
	try{
		for (int i=0; i<NOP; i++)
		{
			System.out.println("In Page:"+(i+1));
			String p1 = webdriver.getPageSource();
			String word = "form_id";
			int q=0;int k[]=new int[10], form_id1[]=new int[40];
			for (int i4 = p1.length(); (i4= p1.lastIndexOf(word, i4- 1)) != -1; )
			{
				while(true){
			    //System.out.println("From last:"+i);
			    q++;
				break;
				}
			} 
			System.out.println("Number of Form_id's in the Page["+(i+1)+"] are="+q);
			int a=0,j=0;String temp[]=null;
			for (int i2=-1;(i2=p1.indexOf(word,i2+1))!=-1;)
			{	
				if(a<q)
				{
					k[a]=i;
					//System.out.println("k["+a+"]="+i);
					String Page = webdriver.getPageSource().substring(i2-10,i2-7);
					//System.out.println("form_id="+Page);
					String f="";
					temp=Page.trim().split(f);
					for(int i1 =0; i1 < temp.length ; i1++)
					{
						//System.out.println("temp["+i1+"]="+temp[i1]);
					}	
					a++;
				}
				form_id1[j]=Integer.parseInt(temp[2]);
				System.out.println("In Page["+(i+1)+"] with Form_Number["+(j+1)+"] have Form_id="+form_id1[j]);
				j++;		
			}
		
	if(form_id1.length>=1)//Number of forms in page morethan 1
	{
		System.out.println("In Page(Form_id):"+(i+1));
		//System.out.println("form_id1.length:"+j);
		for(int c=0;c<j;c++)
		{
			System.out.println("Answering form no:"+(c+1));
			int form_id=form_id1[c];
			System.out.println("Current form_id="+form_id);
				
			resultDetails=BottomInternalAnswering_MULTIPAGE(form_id,webdriver,rs,st,questionnaire_id,c);
		}	
	}else if(form_id1.length==0)
	{
	try
	{
	System.out.println("In Page:"+(i+1));
	if(webdriver.findElement(By.xpath("//div[@id='next_page_nav']/a")).isDisplayed())//for clicking next page button if exists in the current page
	{	
		System.out.println("next page clicked");
		webdriver.findElement(By.xpath("//div[@id='next_page_nav']/a")).click();
		WebDriverUtils.waitForPageToLoad(webdriver, "20000");		
	}
	}catch(Exception e){	
		try{//Earn CME bottom link
			if(webdriver.findElement(By.xpath("//div[@id='next_page_nav']/span/a")).isDisplayed())
			{
				System.out.println("Earn CME(Bottom) Credit is displayed @page="+i);
				webdriver.findElement(By.xpath("//div[@id='next_page_nav']/span/a")).click();
			}	
		}catch(Exception e1){
		try{
		if(webdriver.findElement(By.xpath("//input[@name='submitbutton']")).isDisplayed())
		{
		webdriver.findElement(By.xpath("//input[@name='submitbutton']")).click();		
		}
		}catch(Exception e2){}
		}
	}
	}
	try
	{	
		if(webdriver.findElement(By.xpath("//div[@id='next_page_nav']/a")).isDisplayed())//for clicking next page button if exists in the current page
		{	
			webdriver.findElement(By.xpath("//div[@id='next_page_nav']/a")).click();
			System.out.println("next page clicked");
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");		
		}	
		}catch(Exception e){	
			try{//Earn CME bottom link
				if(webdriver.findElement(By.xpath("//div[@id='next_page_nav']/span/a")).isDisplayed())
				{
					System.out.println("Earn CME(Bottom) Credit is displayed @page="+i);
					webdriver.findElement(By.xpath("//div[@id='next_page_nav']/span/a")).click();
				}	
		}catch(Exception e1){
		try{
		if(webdriver.findElement(By.xpath("//input[@name='submitbutton']")).isDisplayed())
		{
		webdriver.findElement(By.xpath("//input[@name='submitbutton']")).click();		
		}
		}catch(Exception e2){}
		}
		}
	}	
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");
		}catch(Throwable e){
			e.printStackTrace();
			resultDetails.setFlag(false);
			System.out.println(e.getMessage());
			return resultDetails;
			}
			return resultDetails;
}

public ResultDetails BottomInternalAnswering_MULTIPAGE(int form_id,WebDriver webdriver,ResultSet rs,Statement st,int questionnaire_id,int c) throws SQLException, InterruptedException
{
	int choice_id[]=new int[45],question_id[]=new int[45];
	try{
		System.out.println("***Answering the questions***");
		System.out.println("form_id(Ansewring):"+form_id);
		ConnectionDB();
		rs=st.executeQuery("select qd.question_id from question_display qd where qd.IS_REQUIRED=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
		if(!rs.next())
		{
		System.out.println("Bottom not required::");
		}
		else{
			int i=0,j=0,k=0,p=0;
			rs.beforeFirst();
			while(rs.next()){
				question_id[i]=rs.getInt(1);
				System.out.println("question_id["+i+"]="+question_id[i]);
			i++;j++;
			}
			for(i=0;i<j;i++)
			{	
			ConnectionDB();
			rs=st.executeQuery("select CHOICE_ID from question_choice  where  IS_CORRECT=1 and question_id="+question_id[i]);

			if(!rs.next())
			{	
				ConnectionDB();
				rs=st.executeQuery("select CHOICE_ID from question_choice  where DISPLAY_ORDER=1 and question_id="+question_id[i]);
				while(rs.next()){
					choice_id[k]=rs.getInt(1);
					//System.out.println("p="+p);
					System.out.println("choice_id["+k+"]="+choice_id[k]);
					k++;p++;
				}	
			}
			else
			{
				rs.beforeFirst();
				while(rs.next()){
				choice_id[k]=rs.getInt(1);
				//System.out.println("k90="+k+"p980="+p);
				//System.out.println("choice_id["+k+"]="+choice_id[k]);
				k++;p++;
				}
			}
		}
			for(k=0;k<p;k++)
			{		
				webdriver.findElement(By.xpath("//input[@value="+choice_id[k]+"]")).click();
				System.out.println("choice clicked");
			}
			
	/*	try
		{	
			if(webdriver.findElement(By.xpath("//div[@id='next_page_nav']/a")).isDisplayed())//for clicking next page button if exists in the current page
			{	
				webdriver.findElement(By.xpath("//div[@id='next_page_nav']/a")).click();
				System.out.println("next page clicked");
				WebDriverUtils.waitForPageToLoad(webdriver, "20000");		
			}	
			}catch(Exception e){	
				try{//Earn CME bottom link
					if(webdriver.findElement(By.xpath("//div[@id='next_page_nav']/span/a")).isDisplayed())
					{
						System.out.println("Earn CME(Bottom) Credit is displayed @page="+i);
						webdriver.findElement(By.xpath("//div[@id='next_page_nav']/span/a")).click();
					}	
			}catch(Exception e1){
			try{
			if(webdriver.findElement(By.xpath("//input[@name='submitbutton']")).isDisplayed())
			{
			webdriver.findElement(By.xpath("//input[@name='submitbutton']")).click();		
			}
			}catch(Exception e2){}
			}
		}*/
		}
		/*try{
			//Proceed to evaluation
			if(webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).isDisplayed())
			{
			webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).click();
	    	}
		}catch(Exception e){}*/
		
		resultDetails.setFlag(true);
		resultDetails.setErrorMessage("");

		}catch(Throwable e){
			resultDetails.setFlag(false);
			return resultDetails;
			}
		return resultDetails;
}

	public ResultDetails Article(WebDriver webdriver, String field,String value) throws ClassNotFoundException, SQLException, XPathExpressionException, IOException, ParserConfigurationException, InterruptedException, SAXException
	{
		try {
			webdriver.get("http://www."+System.getenv("env")+".medscape.org/");
			webdriver.manage().window().maximize();
		
			resultDetails=Article_FetchArticle(webdriver,field,value);
			return resultDetails;
		}
		catch(Throwable e) {
			//System.out.println("e456");
		//	System.out.println(e.getMessage());
			resultDetails=Article_FetchArticle(webdriver,field,value);
			return resultDetails;
		}
	}//Article
	public ResultDetails Article_FetchArticle(WebDriver webdriver, String field,String value) throws SQLException, ClassNotFoundException, XPathExpressionException, IOException, ParserConfigurationException, InterruptedException, SAXException 
	{
		System.out.println("field= " + field + " value= " + value);
		Article_SinglePage art = Article_SinglePage.valueOf(field);
		//field = field.substring(3, field.length());
		try{
	Integer article_id1 = null;
	switch(art)
	{
	case ART:ConnectionDB();//Fetch the article which is not expired
			rs=st.executeQuery("select a.questionnaire_id,a.article_id,a.activity_id,i.expiration_date from article a,activity_info i where a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1200");
			break;
	case PRQ:ConnectionDB();//PreActivity Required Questionnaire(We have only 3 records for this type of requirement)
			rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=2 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
			break;
	case ARE:ConnectionDB();//To get Expired article
			rs=st.executeQuery("select a.questionnaire_id,a.article_id from article a,activity_info i where a.activity_id=i.activity_id and a.questionnaire_id!=0 and i.expiration_date<(sysdate+1) and rownum<1000");
			break;
	case BTS:ConnectionDB();//Bottom Required Question form with 70% score
			System.out.println("Bottom Required Question form with 70% score");
			rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.passing_score=70 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
			break;
	case PNR:ConnectionDB();//PreActivity Not Required Questionnaire
			rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=0 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=2 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
			break;
	case EVR:ConnectionDB();//Evaluation Required from	
			rs=st.executeQuery("select distinct qf.questionnaire_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.questionnaire_id=qf.questionnaire_id and ac.EVAL_REQUIRED=1 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
			break;
	case CME:ConnectionDB();//Fetch the article which have CME eligibility for physician	
			rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.CREDIT_TYPE_ID=1 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");			
			break;
	case NUR://Get the Nurse eligibility CME Article
		try{
			System.out.println("IN connection");
			if(ODB.connection.isClosed())
			{
				System.out.println("IN connection_IF");

				ODB=new OracleDB(); 
				st=ODB.stmt;	
				rs=null;
				try{
					int questionnaire_id=26593;
					rs=st.executeQuery("select article_id from article where questionnaire_id="+questionnaire_id);
					int article1=rs.getInt(1);
					}catch(Exception e1){
						System.out.println("Connection_catch");
						ODB=new OracleDB(); 
						st=ODB.stmt;	
						rs=null;
					}
			}}catch(Exception e)
			{		System.out.println("IN connection_Catch");

				System.out.println(e.getLocalizedMessage());
				ODB=new OracleDB(); 
				st=ODB.stmt;	
				rs=null;
			}
			rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.CREDIT_TYPE_ID=2 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
			break;
	case PRM:ConnectionDB();//Get the Pharmacit Eligibility CME Article 	
			rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.CREDIT_TYPE_ID=3 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
			break;
	case OHC:ConnectionDB();//Get the CMLE	
			rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.CREDIT_TYPE_ID=5 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
			break;
	case LOC:ConnectionDB();//Get the LOC	
			rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.CREDIT_TYPE_ID=6 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");	
			break;	
	case BTR:ConnectionDB();//Bottom Required Question form
			System.out.println("Bottom Required Question form");
			rs=st.executeQuery("select distinct qd.questionnaire_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=1 and  qd.form_id=qf.form_id and qd.questionnaire_id=qf.questionnaire_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<500 minus select distinct qd.questionnaire_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=0 and  qd.form_id=qf.form_id and qd.questionnaire_id=qf.questionnaire_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<2000");
			break;
	case NTC:ConnectionDB();//No test cme
			//rs=st.executeQuery("select distinct a.questionnaire_id,a.article_id from article a,activity_info i,questionnaire_form q where a.questionnaire_id=q.questionnaire_id and  q.form_type_id!=3 and q.form_type_id!=4 and q.form_type_id!=2 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000 ");
			rs=st.executeQuery("(select distinct a.questionnaire_id from article a,activity_info i,questionnaire_form q where a.questionnaire_id=q.questionnaire_id and  q.form_type_id!=2 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000 minus select distinct a.questionnaire_id from article a,activity_info i,questionnaire_form q  where a.questionnaire_id=q.questionnaire_id and  q.form_type_id=2 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<2000) intersect (select distinct a.questionnaire_id from article a,activity_info i,questionnaire_form q where a.questionnaire_id=q.questionnaire_id and  q.form_type_id!=3 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000 minus select distinct a.questionnaire_id from article a,activity_info i,questionnaire_form q where a.questionnaire_id=q.questionnaire_id and  q.form_type_id=3 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<5000) intersect (select distinct a.questionnaire_id from article a,activity_info i,questionnaire_form q where a.questionnaire_id=q.questionnaire_id and  q.form_type_id!=4 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000 minus select distinct a.questionnaire_id from article a,activity_info i,questionnaire_form q where a.questionnaire_id=q.questionnaire_id and  q.form_type_id=4 and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<5000)");	
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
	int questionnaire_id;
	questionnaire_id=rs.getInt(1);
	//questionnaire_id=26169;
	//questionnaire_id=25207;
	System.out.println("questionnaire_id="+questionnaire_id);
	ConnectionDB();
	rs=st.executeQuery("select article_id from article where questionnaire_id="+questionnaire_id);
	rs.next();
	//article_id1=763117;
	//article_id1=769108;
	article_id1=rs.getInt(1);
	
	resultDetails=ArticleType_SinglePage(webdriver,article_id1,field,value);
	return resultDetails;
	
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			
			resultDetails.setFlag(false);
			System.out.println(e.getMessage());
			return resultDetails;
		}
}
	public ResultDetails ArticleType_SinglePage(WebDriver webdriver,int article_id1,
			String field, String value) 
	{
		try{
		webdriver.get("http://www."+System.getenv("env")+".medscape.org/viewarticle/"+article_id1);
		System.out.println("article_id1="+article_id1);
		try{
			if(webdriver.getTitle().equalsIgnoreCase("Pre-Activity Questionnaire"))
			{
				webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();
				String error="All questions must be answered in order to proceed.";
				
 				if (webdriver.getPageSource().toLowerCase().trim()
 						.contains(error.toLowerCase().trim()))
 				{
 					System.out.println("Answering the preActivity questions");
 					resultDetails=Pre_Answered(webdriver,rs,st);
 				}
 			}
		}catch(Exception e){}
		try{
		int n=0;
		n=webdriver.getPageSource().indexOf("current_page_nav");
		System.out.println("n="+n);
		if(n>0)
		{
			System.out.println("THIS IS A MULTI-PAGE SEARCHING FOR SINGLE PAGE ARTICLE");
			resultDetails=Article_FetchArticle(webdriver,field,value);
		}
		else if(webdriver.getTitle().equalsIgnoreCase("Page Not Found"))
		{
			System.out.println("This is page not found artilce");
			WebDriverUtils.waitForPageToLoad(webdriver, "1000");	
		resultDetails=Article_FetchArticle(webdriver,field,value);
		}
		else{
			System.out.println("**********THIS IS A SINGLE-PAGE**********");
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
		}
		}catch(Exception e){
			resultDetails=Article_FetchArticle(webdriver,field,value);
		}
		try{
			if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
			{
				 webdriver.findElement(By.linkText("Continue to Activity")).click();
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
		/*try{
			if(webdriver.findElement(By.xpath("//div[@id='page_nav']")).isDisplayed())
			{
			String s=webdriver.findElement(By.xpath("//div[@id='page_nav']/div[2]")).getText();
			String s2="Page";
			//System.out.println("s="+s);
			if(s.contains(s2))
			{
				System.out.println("**********THIS IS A MULTI-PAGE**********");
				resultDetails=Article_FetchArticle(webdriver,field,value);
				
			}else{
				System.out.println("**********THIS IS A SINGLE-PAGE**********");
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			}
			}
		}catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("This is a Single page in catch block");
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
		}*/
		
		}catch(Throwable e){
			System.out.println("Catch Block in June_CME_KeyWords");
			resultDetails.setFlag(false);
			System.out.println(e.getMessage());
			return resultDetails;
		}
		return resultDetails;
	}
public ResultDetails Pre_Answered(WebDriver webdriver,ResultSet rs,Statement st) throws SQLException
{
 String s=webdriver.getCurrentUrl();
 System.out.println(s); 		  
 String f="[_\\/\\#]+";String[] temp = s.split(f);
 for(int i =0; i < temp.length ; i++)
System.out.println("temp["+i+"]="+temp[i]);	
int questionnaire_id=Integer.parseInt(temp[4]);
System.out.println(questionnaire_id);
		

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
	if(!rs.next())
	{
		System.out.println("These questions are not required questions in PreAcitivity form");
		webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();		
	}
	else
	{
		int choice_id[]=new int[45];
		int i=0,j=0;
		System.out.println("This Pre Activity with Required Questions ");
		ConnectionDB();
		rs=st.executeQuery("select qc.choice_id,qc.question_id from question_choice qc,question_display qd where qc.question_id=qd.question_id and qd.IS_REQUIRED=1 and qc.display_order=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
									
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
		System.out.println("pre -submit");						
		}		
 resultDetails.setFlag(true);
 resultDetails.setErrorMessage("");
	   
	}catch(Exception e)
	{ 
		System.out.println("Pre required catch block");		
		resultDetails.setFlag(false);		
		return resultDetails;
  }
	return resultDetails;
}//Pre_answered class

		
	
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
	case PGE:resultDetails=ProgramEarnCME_SINGLEPAGE(webdriver,rs,st,value);//open the program click Earn Cme then again open program
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
	 case CRD://Credits earned message
			 resultDetails=Credits_SINGLEPAGE(webdriver,rs,st);
			 break;
	 case PRF:// To complete CME WF  for pre req
		 	 resultDetails=PRF_SINGLEPAGE(webdriver,rs,st,field,value);
			 break;
	 case EVL://Evaluation with required
			 resultDetails=Evaluation_SINGLEPAGE(webdriver,value,rs,st);
			 break;
	 case ALL:System.out.println("Internal Bottom required form");
			 resultDetails=AllInternal_SINGLEPAGE(webdriver,rs,st);
			 break;
	 case ELG:resultDetails=Eligibility_credits_SINGLEPAGE(webdriver,value,rs,st);
	 		  break;//Eligibility
	 case FMW:System.out.println("For score with wrong answers");
			resultDetails=ForScoreWrong_SINGLEPAGE(webdriver,rs,st);
			break;
	 case FME://ANSWER THE PREACT IF THERE CLICK "SAVE AND PROCEED" WITHOUT ANSWERING IN POST System.out.println("For score and save proceed");
			   resultDetails=ForScoreSave_SINGLEPAGE(webdriver,rs,st);
			   break;
	 case PRG:resultDetails=Program_SINGLEPAGE(webdriver,rs,st,value);
			  break;
	 case EPG:resultDetails=ExpiredProgram_SINGLEPAGE(webdriver,rs,st,value);
			  break;
	 case PRP://Pre-Activity link is not displayed for other articles in the program once answered
				resultDetails=ProgramPreAct_SINGLEPAGE(webdriver,rs,st,value);
				break;
		}
	}catch(Exception e1){
		
		resultDetails.setFlag(false);
		return resultDetails;}

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
		
			resultDetails=internal(article_id,value,questionnaire_id,webdriver,no_of_completed_forms,article_id);
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
			System.out.println("Catch Block in ERP");
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
	}//ArticleEarned_SINGLEPAGE
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
}//ProgramPreAct_SINGLEPAGE

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
	if(l==0){
		resultDetails=Program_SINGLEPAGE(webdriver, rs, st, value);
	}
	
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
	public  ResultDetails ForScoreSave_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
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
public  ResultDetails ForScoreWrong_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
{
try{
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
	try{
	if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
	{
		System.out.println("internal form");
		webdriver.findElement(By.linkText("Continue to Activity")).click();
		Thread.sleep(5000);
	}
	}catch(Exception e){}
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

			try{
				System.out.println("IN connection");
				if(ODB.connection.isClosed())
				{
					System.out.println("IN connection_IF");

					ODB=new OracleDB(); 
					st=ODB.stmt;	
					rs=null;
					try{
						int questionnaire_id=26593;
						rs=st.executeQuery("select article_id from article where questionnaire_id="+questionnaire_id);
						int article1=rs.getInt(1);
						}catch(Exception e1){
							System.out.println("Connection_catch");
							ODB=new OracleDB(); 
							st=ODB.stmt;	
							rs=null;
						}
				}}catch(Exception e)
				{		System.out.println("IN connection_Catch");

					System.out.println(e.getLocalizedMessage());
					ODB=new OracleDB(); 
					st=ODB.stmt;	
					rs=null;
				}
			rs=st.executeQuery("select QUESTIONNAIRE_ID from ARTICLE where ARTICLE_ID="+article_id1);
			rs.next();
			int questionnaire_id=rs.getInt(1);			
					
			int form_type_id=2;
			System.out.println("In Pre-Questionnaire Form.....");
			System.out.println("form_type_id(Pre-Activity)="+form_type_id);
			try{
				System.out.println("IN connection");
				if(ODB.connection.isClosed())
				{
					System.out.println("IN connection_IF");

					ODB=new OracleDB(); 
					st=ODB.stmt;	
					rs=null;
					try{
						int questionnaire_id1=26593;
						rs=st.executeQuery("select article_id from article where questionnaire_id="+questionnaire_id1);
						int article1=rs.getInt(1);
						}catch(Exception e1){
							System.out.println("Connection_catch");
							ODB=new OracleDB(); 
							st=ODB.stmt;	
							rs=null;
						}
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
			System.out.println("form_id(Pre-Activity)="+form_id);
			try{
				System.out.println("IN connection");
				if(ODB.connection.isClosed())
				{
					System.out.println("IN connection_IF");

					ODB=new OracleDB(); 
					st=ODB.stmt;	
					rs=null;
					try{
						int questionnaire_id1=26593;
						rs=st.executeQuery("select article_id from article where questionnaire_id="+questionnaire_id1);
						int article1=rs.getInt(1);
						}catch(Exception e1){
							System.out.println("Connection_catch");
							ODB=new OracleDB(); 
							st=ODB.stmt;	
							rs=null;
						}
				}}catch(Exception e)
				{		System.out.println("IN connection_Catch");

					System.out.println(e.getLocalizedMessage());
					ODB=new OracleDB(); 
					st=ODB.stmt;	
					rs=null;
				}
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
				//System.out.println("choice_id["+i+"]="+choice_id[i]);
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
			//System.out.println("choice_id["+i+"]="+choice_id[i]);
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
	}//Eligibility_credits_SINGLEPAGE	
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
	}//Credits_SINGLEPAGE

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
try{
if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
{
	System.out.println("internal form");
	webdriver.findElement(By.linkText("Continue to Activity")).click();
	Thread.sleep(5000);
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

public  ResultDetails Evaluation_SINGLEPAGE(WebDriver webdriver,String value,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
{
	System.out.println("evaluation");
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
			try{
				int questionnaire_id1=26593;
				rs=st.executeQuery("select article_id from article where questionnaire_id="+questionnaire_id1);
				int article1=rs.getInt(1);
				}catch(Exception e1){
					System.out.println("Connection_catch");
					ODB=new OracleDB(); 
					st=ODB.stmt;	
					rs=null;
				}
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
}//Evaluation_SINGLEPAGE

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
	//System.out.println("question["+i+"](Select)="+question_id[i]);	
	select(webdriver,"2");
	//webdriver.findElement(By.xpath("//select[@name='pCredits']")).click();	
	}
	else
	{ 
		ConnectionDB();
//	System.out.println("question["+i+"](else)="+question_id[i]);
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
//		System.out.println("i(for)="+i);
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
		}//EvalReq_SINGLEPAGE

	public ResultDetails PRF_SINGLEPAGE(WebDriver webdriver2, ResultSet rs2,
			Statement st2, String field, String value) {
		try{
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
					resultDetails= Article_FetchArticle(webdriver,field,value);
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
			try{
				int questionnaire_id1=26593;
				rs=st.executeQuery("select article_id from article where questionnaire_id="+questionnaire_id1);
				int article1=rs.getInt(1);
				}catch(Exception e1){
					System.out.println("Connection_catch");
					ODB=new OracleDB(); 
					st=ODB.stmt;	
					rs=null;
				}
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
else{//Internal page contains no questions at this time this will work
	form_type_id=4;
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
			try{
				int questionnaire_id1=26593;
				rs=st.executeQuery("select article_id from article where questionnaire_id="+questionnaire_id1);
				int article1=rs.getInt(1);
				}catch(Exception e1){
					System.out.println("Connection_catch");
					ODB=new OracleDB(); 
					st=ODB.stmt;	
					rs=null;
				}
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
		//System.out.println("choice_id["+i+"]="+choice_id[i]);
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
			try{
				int questionnaire_id1=26593;
				rs=st.executeQuery("select article_id from article where questionnaire_id="+questionnaire_id1);
				int article1=rs.getInt(1);
				}catch(Exception e1){
					System.out.println("Connection_catch");
					ODB=new OracleDB(); 
					st=ODB.stmt;	
					rs=null;
				}
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
		//System.out.println("choice_id["+i+"]="+choice_id[i]);
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
	public  ResultDetails Pre_SINGLEPAGE(WebDriver webdriver,ResultSet rs,Statement st) throws SQLException, InterruptedException, ClassNotFoundException
	 {
		return resultDetails;
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
				try{
				int questionnaire_id=26593;
				rs=st.executeQuery("select article_id from article where questionnaire_id="+questionnaire_id);
				int article1=rs.getInt(1);
				}catch(Exception e1){
					System.out.println("Connection_catch");
					ODB=new OracleDB(); 
					st=ODB.stmt;	
					rs=null;
				}
			}}catch(Exception e)
			{		System.out.println("IN connection_Catch");

				System.out.println(e.getLocalizedMessage());
				ODB=new OracleDB(); 
				st=ODB.stmt;	
				rs=null;
			}
	}
	}






