package com.java;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.java.WebDriverUtils;
import com.java.Objects.ResultDetails;

public class Multipage {
	ResultDetails resultDetails = new ResultDetails();
	OracleDB ODB=new OracleDB();

	ResultSet rs=null;Statement st=ODB.stmt;
	public enum MULITPAGE{
 GRT, NXT, ERN,BNT, BTR,BNM,BTM
	}
	public ResultDetails MULTIPAGE(WebDriver webdriver, String field, String value) throws ClassNotFoundException, SQLException, ParserConfigurationException, IOException, XPathExpressionException, InterruptedException, SAXException
	{
		try {
			webdriver.get("http://www."+System.getenv("env")+".medscape.org/");
			webdriver.manage().window().maximize();
		
			resultDetails=FetchArticle_MULTIPAGE(webdriver,field,value);
			return resultDetails;
		//	xmlWriter.Close();
			
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
	case BTM: 
		ConnectionDB();
rs=st.executeQuery("select distinct qd.questionnaire_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=0 and  qd.form_id=qf.form_id and qd.questionnaire_id=qf.questionnaire_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<500 minus select distinct qd.questionnaire_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=1 and  qd.form_id=qf.form_id and qd.questionnaire_id=qf.questionnaire_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<2000");
		break;
	case BNM://bottom required mutlipage  NO Test cme
		System.out.println("Bottom NOT Required multi page");
		ConnectionDB();
rs=st.executeQuery("select distinct qd.questionnaire_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=0 and qf.form_type_id=3 and qd.questionnaire_id=qf.questionnaire_id and qd.form_id=qf.form_id and qd.questionnaire_id not in(select  distinct questionnaire_id from question_display where is_required=1) and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");

		break;
	case BTR:	
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
		
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qd.form_id=3 and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
			break;
	case BNT:
		ConnectionDB();
		System.out.println("Bottom NOT Required Question form");
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=0 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
			break;
	case GRT:	ConnectionDB();
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.passing_score=70 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");		
		break;
	case NXT: 	ConnectionDB();
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id!=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");		
		break;
	case ERN:System.out.println("Earn credits");
	ConnectionDB();
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");

       break;
	}
	int k;
	/*while(rs.next())
	{
		k++;
		
	}*/
	
	rs.last();
 k= rs.getRow();
	System.out.println("Number of Rows return by the Query="+k);
	int p= getRandom(k);
	System.out.println("The Querstionanire_id contians in "+p+"th row");
	rs.beforeFirst();
	rs.absolute(p);

	int questionnaire_id;
//	questionnaire_id=27091;
	//questionnaire_id=25237;
	questionnaire_id=rs.getInt(1);
	System.out.println("questionnaire_id="+questionnaire_id);
	 ConnectionDB();
	rs=st.executeQuery("select article_id from article where questionnaire_id="+questionnaire_id);
	rs.next();
	//article_id1=771685;
	//article_id1=764689;
	article_id1=rs.getInt(1);
	
	resultDetails=XMLQuery_MULTIPAGE(webdriver,article_id1,questionnaire_id,art,field,value);
		}
		catch(Throwable e){
			e.printStackTrace();
			resultDetails.setFlag(false);
			return resultDetails;
			}
		return resultDetails;
	}//FetchArticle_multipage

public ResultDetails XMLQuery_MULTIPAGE(WebDriver webdriver, Integer article_id1,int questionnaire_id,MULITPAGE art,String field,String value) throws ClassNotFoundException, SQLException, ParserConfigurationException, IOException, InterruptedException, XPathExpressionException, SAXException
{
	try{
	String s1="",s2="",s3="";
	webdriver.get("http://www."+System.getenv("env")+".medscape.org/viewarticle/"+article_id1);
	System.out.println("article_id1="+article_id1);
	s1=article_id1.toString();
	s2=s1.substring(0, 3);
	s3=s1.substring(3, 6);
	
	Runtime.getRuntime().exec("D:/sowjanya/pscp -scp -pw 1@Welcome WBMD+sveeravalli@ats03q-prf-08.portal.webmd.com:/webmd/content/qa/article/"+s2+"/"+s3+"/"+s2+s3+".xml D:/sowjanya");
	// it creates xml file
	DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	domFactory.setNamespaceAware(true);
	DocumentBuilder builder = domFactory.newDocumentBuilder();

	String strXML = "D:/sowjanya/"+s2+s3+".xml";
	File file = new File("temp");
	Thread.sleep(5000);
	try{
	//while(true){766305 25431
			file=new File(strXML);
			Thread.sleep(5000);

			if(!(file.exists())){
				System.out.println("............XML File is NOT Copied..........");
				resultDetails=FetchArticle_MULTIPAGE(webdriver,field,value);
			}
				//break;
			else{
				WebDriverUtils.waitForPageToLoad(webdriver, "10000");	
				//resultDetails=FetchArticle(webdriver,field,value);
			}
	//}
		}catch(Exception e){
			System.out.println("catch block");
			//System.out.println(e.getMessage());
			//resultDetails=FetchArticle(webdriver,field,value);
		}
		
		
	Document doc = builder.parse(file);
	 //Document doc = builder.parse("D:/771358.xml");
	XPathFactory factory = XPathFactory.newInstance();
	XPath xpath = factory.newXPath();
	//XPathExpression expr = xpath.compile("//body//sec[@sec-type='page']//supplementary-material//object-id");

	XPathExpression expr = xpath.compile("//body//sec[@sec-type='page']");
	Object result = expr.evaluate(doc, XPathConstants.NODESET);
	NodeList nodes1 = (NodeList) result;
	System.out.println("no of pages in the article="+nodes1.getLength());
	try{
	if(webdriver.getTitle().equalsIgnoreCase("Page Not Found"))
	{
		System.out.println("This is page not found artilce");
		WebDriverUtils.waitForPageToLoad(webdriver, "1000");	
	resultDetails=FetchArticle_MULTIPAGE(webdriver,field,value);
	}
	else if(nodes1.getLength()==1||nodes1.getLength()==0)
	{
		System.out.println("This is a SINGLE PAGE Searching for  MULTI PAGE..........");
		WebDriverUtils.waitForPageToLoad(webdriver, "1000");	
		resultDetails=FetchArticle_MULTIPAGE(webdriver,field,value);
	}
	
	else{
		System.out.println("This is a MULTI PAGE ...........");
		resultDetails=XMLQuery_Answering_MULTIPAGE(file,webdriver, nodes1, xpath, doc, article_id1, questionnaire_id,art,field,value);
	}
	}catch(Exception e){}
	return resultDetails;
	}
	catch(Throwable e){
		System.out.println("sebfh xml");
		resultDetails=FetchArticle_MULTIPAGE(webdriver,field,value);
		//e.printStackTrace();
		//System.out.println(e.getMessage());
//		resultDetails.setFlag(false);
		return resultDetails;
		}
}//Xml_query Multipage
public ResultDetails XMLQuery_Answering_MULTIPAGE(File file,WebDriver webdriver,NodeList nodes1,XPath xpath,Document doc,Integer article_id1,int questionnaire_id, MULITPAGE art,String field,String value)
{
	try {
		if(webdriver.getTitle().equalsIgnoreCase("Pre-Activity Questionnaire")){
		pre_MULTIPAGE(article_id1,questionnaire_id, rs, st, webdriver,article_id1);
	 }
	}catch(Exception e){}
	
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
		String temp[]=g.split(" ");
		for(int i =0; i < temp.length ; i++)
			System.out.println(temp[i]);
		    //System.out.println("temp["+i+"]="+temp[i]);	
		int t=Integer.parseInt(temp[3]);
		try{

	switch(art)
	{
	case BTM:
resultDetails=BottomNotRequired_MULTIPAGE(file,nodes1,xpath,doc,webdriver,questionnaire_id,field,value);
		break;
	case BNM:resultDetails=BottomNotRequired_MULTIPAGE(nodes1,xpath,doc,webdriver,questionnaire_id,field,value);
		break;
	case BTR:resultDetails=BottomRequired_MULTIPAGE(file,nodes1,xpath,doc,webdriver,questionnaire_id);
		break;
	case BNT:resultDetails=BottomNotRequired2_MULTIPAGE(file,nodes1,xpath,doc,webdriver,questionnaire_id,field,value);
		break;
	case GRT:
			resultDetails=GrantAttribution_MULTIPAGE(t,nodes1,xpath,doc,webdriver,questionnaire_id);
			break;
	case NXT:
			resultDetails=Links_MULTIPAGE(webdriver,field,value);
			break;
	case ERN:
			resultDetails=EARNCME_CREDITS_MULTIPAGE(file,t,nodes1,xpath,doc,webdriver,questionnaire_id);
			break;
			
	}
	//resultDetails.setFlag(true);
	//resultDetails.setErrorMessage("");
	}catch(Throwable e){
		e.printStackTrace();
		resultDetails.setFlag(false);
		System.out.println(e.getMessage());
		return resultDetails;
		}
		return resultDetails;
}//XMLQUERY ANSWERING _MULITPAGE
public ResultDetails  BottomRequired_MULTIPAGE(File file,NodeList nodes1,XPath xpath,Document doc,WebDriver webdriver,int questionnaire_id)
{
	try{
	for (int i = 0; i <nodes1.getLength(); i++)
	{
	XPathExpression expr2 = xpath.compile("//body/sec[@id='P"+(i+1)+"']//supplementary-material//object-id/text()");
	Object result2 = expr2.evaluate(doc, XPathConstants.NODESET);
	NodeList nodes2 = (NodeList) result2;
	System.out.println("No of forms in this page are"+nodes2.getLength());
	if(nodes2.getLength()>=1)
	{
	System.out.println("In Page:"+(i+1));
	for(int c=0;c<nodes2.getLength();c++)
	{
	System.out.println("Answering form no:"+(c+1));
	int form_id=Integer.parseInt(nodes2.item(c).getNodeValue());
	System.out.println("Current form_id="+form_id);
			
	resultDetails=BottomInternalAnswering_MULTIPAGE(form_id,webdriver,rs,st,questionnaire_id,c);
		}
	}else if(nodes2.getLength()==0){
		try
		{
			System.out.println("In Page:"+(i+1));
		if(webdriver.findElement(By.xpath("//div[@id='next_page_nav']/a")).isDisplayed())//for clicking next page button if exists in the current page
		{	System.out.println("next page clicked");
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
	}//for
	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");
	}catch(Throwable e){
		resultDetails.setFlag(false);System.out.println("e1");
		//System.out.println(e.getMessage());
		return resultDetails;
		}
	return resultDetails;	
}
public ResultDetails BottomNotRequired_MULTIPAGE(NodeList nodes1,XPath xpath,Document doc,WebDriver webdriver,int questionnaire_id,String field,String value) throws ClassNotFoundException, XPathExpressionException, SQLException, IOException, ParserConfigurationException, InterruptedException, SAXException
{
try{//class try
	try{
		if(webdriver.findElement(By.linkText("Earn CME/CE Credit �")).isDisplayed())
		{						
		System.out.println("Earn CME/CE(Rigth Rail) Credit is displayed @page=");
		}
	}catch(Exception e){
		try{
			if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
			{	
			System.out.println("Earn CME(Rigth Rail) Credit is displayed @page=");
			}
			}catch(Exception  e2)
			{
				try{//Earn Creidt
				if(webdriver.findElement(By.xpath("//span[@id='cme_btns']/a[2]")).isDisplayed())
				{
				webdriver.findElement(By.xpath("//span[@id='cme_btns']/a[2]")).click();
				WebDriverUtils.waitForPageToLoad(webdriver, "20000");	
				}
				}catch(Exception e3){
					//NO earn cme or ce links in right rail fetch for another article
				resultDetails=FetchArticle_MULTIPAGE(webdriver, field, value);
				}
			}
		}	
	try{
		if(webdriver.findElement(By.xpath("//input[@name='submitbutton']")).isDisplayed())
		{
			webdriver.findElement(By.xpath("//input[@name='submitbutton']")).click();
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");	
		}
		}catch(Exception e){
		try{
		String msg="All questions must be answered in order to proceed.";
		if((webdriver.getPageSource().toLowerCase().trim()
		.contains(msg.toLowerCase().trim())))
		{
		System.out.println("All questions must be answered in order to proceed.");
		
		rs=st.executeQuery("select form_id from questionnaire_form where form_type_id=5 and questionnaire_id="+questionnaire_id);
		rs.next();
		int form_id=rs.getInt(1);

		//SINGLEPAGE2 s =new SINGLEPAGE2();
		//resultDetails=s.EvalReq(questionnaire_id,webdriver,form_id);
		}
		}catch(Exception e1){}		
			
		}
	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");
	}catch(Throwable e){
		resultDetails.setFlag(false);System.out.println("e134345");
		return resultDetails;
		}
	return resultDetails;
}//BottomNotRequired_Multipage
public ResultDetails BottomNotRequired_MULTIPAGE(File file,NodeList nodes1,XPath xpath,Document doc,WebDriver webdriver,int questionnaire_id,String field,String value) throws ClassNotFoundException, XPathExpressionException, SQLException, IOException, ParserConfigurationException, InterruptedException, SAXException
{
try{//class try
	for (int i = 1; i <=nodes1.getLength(); i++)
	{
	System.out.println("In Page(BTM):"+i);
	try{			
	if(webdriver.findElement(By.linkText("Earn CME/CE Credit �")).isDisplayed())
	{						
		System.out.println("Earn CME/CE(Rigth Rail) Credit is displayed @page="+i);
	}
	}catch(Exception e){
		try{
			if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
			{	
			System.out.println("Earn CME(Rigth Rail) Credit is displayed @page="+i);
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
	System.out.println("Earn CME/CE(Rigth Rail) Credit is displayed @page="+i);
	}
	}catch(Exception e3)
	{
	if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
	{	
		System.out.println("Earn CME(Rigth Rail) Credit is displayed @page="+i);
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
public ResultDetails BottomNotRequired2_MULTIPAGE(File file,NodeList nodes1,XPath xpath,Document doc,WebDriver webdriver,int questionnaire_id,String field,String value) throws ClassNotFoundException, XPathExpressionException, SQLException, IOException, ParserConfigurationException, InterruptedException, SAXException
{
try{//class try
	for (int i = 0; i <nodes1.getLength(); i++)
	{
	System.out.println("In Page(BNT):"+(i+1));
	
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
				try{//Earn Creidt//
				if(webdriver.findElement(By.xpath("//span[@id='cme_btns']/a[2]")).isDisplayed())
				{
				webdriver.findElement(By.xpath("//span[@id='cme_btns']/a[2]")).click();
				WebDriverUtils.waitForPageToLoad(webdriver, "20000");	
				}
				}catch(Exception e3){
					//NO earn cme or ce links in right rail fetch for another article
				resultDetails=FetchArticle_MULTIPAGE(webdriver, field, value);
				}
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
		try{
		if(webdriver.findElement(By.xpath("//input[@name='submitbutton']")).isDisplayed())
		{
			System.out.println("Save & Proceed() @"+(i+1));
			XPathExpression expr2 = xpath.compile("//body/sec[@id='P"+(i+1)+"']//supplementary-material//object-id/text()");
			Object result2 = expr2.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes2 = (NodeList) result2;
			System.out.println("No of forms in this page are"+nodes2.getLength());
			if(nodes2.getLength()>=1)
			{
				for(int c=0;c<nodes2.getLength();c++)
				{
					System.out.println("Answering form no:"+(c+1));
					int form_id=Integer.parseInt(nodes2.item(c).getNodeValue());
					System.out.println("Current form_id="+form_id);
					resultDetails=InternalAnswering_MULTIPAGE(form_id,webdriver,rs,st,questionnaire_id,c);
				}
			}
			webdriver.findElement(By.xpath("//input[@name='submitbutton']")).click();
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");	
			System.out.println("Save & proceed clciked");
		}
		}catch(Exception e2){
		if(webdriver.findElement(By.linkText("Next Page �")).isDisplayed())
		{
			webdriver.findElement(By.linkText("Next Page �")).click();
			System.out.println("next page clicked");
			WebDriverUtils.waitForPageToLoad(webdriver, "20000");			
		}
	
		}
	}
	}//for
	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");
	}catch(Throwable e){
		resultDetails.setFlag(false);System.out.println("e1");
		return resultDetails;
		}
	return resultDetails;
}
public ResultDetails EARNCME_CREDITS_MULTIPAGE(File file,int t,NodeList nodes1,XPath xpath,Document doc,WebDriver webdriver,int questionnaire_id) throws SQLException, InterruptedException, XPathExpressionException
{

for (int i = 0; i <nodes1.getLength(); i++)
{	
try{
System.out.println("In Page:"+(i+1));
try{
	if(webdriver.findElement(By.linkText("Earn CME/CE Credit �")).isDisplayed()){					
	
		System.out.println("Earn CME/CE(Rigth Rail) Credit is displayed @page="+(i+1));
	}
}catch(Exception e){
	//System.out.println(e.getMessage());
	//System.out.println("e3");
	}
try{
	if(webdriver.findElement(By.partialLinkText("Earn CME Credit")).isDisplayed())
	{

		System.out.println("Earn CME(Rigth Rail) Credit is displayed @page="+(i+1));
	}
	WebDriverUtils.waitForPageToLoad(webdriver, "20000");
}catch(Exception e){
	//System.out.println(e.getMessage());
	//System.out.println("e4");
	}
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
}catch(Exception e2){
	
	if(webdriver.findElement(By.xpath("//input[@name='submitbutton']")).isDisplayed())
	{
		System.out.println("Save & Proceed @"+(i+1));
		XPathExpression expr2 = xpath.compile("//body/sec[@id='P"+(i+1)+"']//supplementary-material//object-id/text()");
		Object result2 = expr2.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes2 = (NodeList) result2;
		System.out.println("No of forms in this page are"+nodes2.getLength());
		if(nodes2.getLength()>=1)
		{
			for(int c=0;c<nodes2.getLength();c++)
			{
				System.out.println("Answering form no:"+(c+1));
				int form_id=Integer.parseInt(nodes2.item(c).getNodeValue());
				System.out.println("Current form_id="+form_id);
				resultDetails=InternalAnswering_MULTIPAGE(form_id,webdriver,rs,st,questionnaire_id,c);
			}
		}
		}
				}
			}
 file.delete();
resultDetails.setFlag(true);
resultDetails.setErrorMessage("");

}catch(Throwable e){
	resultDetails.setFlag(false);
	//System.out.println(e.getMessage());
	return resultDetails;
	}
}
return resultDetails;
	}
public ResultDetails BottomInternalAnswering_MULTIPAGE(int form_id,WebDriver webdriver,ResultSet rs,Statement st,int questionnaire_id,int c) throws SQLException, InterruptedException
{
	int choice_id[]=new int[45],question_id[]=new int[45];
	try{
		System.out.println("We are Answering the questions");
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
			//System.out.println("question_id["+i+"]="+question_id[i]);
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
	//System.out.println("choice_id["+k+"]="+choice_id[k]);
k++;p++;
}	
}else{
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
	try{
		//Proceed to evaluation
		if(webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).isDisplayed())
		{
		webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/a[@id='btn_con_eval']")).click();
    	}
	}catch(Exception e){}
	
	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");

	}catch(Throwable e){
		resultDetails.setFlag(false);
		return resultDetails;
		}
	return resultDetails;
}

public ResultDetails InternalAnswering_MULTIPAGE(int form_id,WebDriver webdriver,ResultSet rs,Statement st,int questionnaire_id,int c) throws SQLException, InterruptedException
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
			System.out.println("hello");
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

public ResultDetails Links_MULTIPAGE(WebDriver webdriver,String field,String value) throws ClassNotFoundException, XPathExpressionException, SQLException, IOException, ParserConfigurationException, InterruptedException, SAXException
{
	
	try{
	try{
		if(webdriver.findElement(By.xpath("//input[@name='submitbutton']")).isDisplayed())//for clicking Save and Proceed button if exists in the current page
		{
			resultDetails=FetchArticle_MULTIPAGE(webdriver,field,value);
		}
	}
	catch(Exception e){
	try
	{
	if(webdriver.findElement(By.linkText("Next Page �")).isDisplayed())//for clicking next page button if exists in the current page
		{
		webdriver.findElement(By.linkText("Next Page �")).click();
	 	 System.out.println("next page clicked");
	 	 WebDriverUtils.waitForPageToLoad(webdriver, "20000");	
	 	 webdriver.findElement(By.linkText("� Previous")).click();
	 	 System.out.println("Prevoius clicked");
	 	 WebDriverUtils.waitForPageToLoad(webdriver, "20000");
		}
	}catch(Exception e1){
		resultDetails=FetchArticle_MULTIPAGE(webdriver,field,value);}
	}
	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");
	}catch(Throwable e){
		resultDetails.setFlag(false);
		//System.out.println(e.getMessage());
		return resultDetails;
		}
	return resultDetails;
}
public ResultDetails GrantAttribution_MULTIPAGE(int t,NodeList nodes1,XPath xpath,Document doc,WebDriver webdriver,int questionnaire_id)
{	
	
	try{
	for (int i = 0; i <nodes1.getLength(); i++)
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
				XPathExpression expr2 = xpath.compile("//body/sec[@id='P"+(i+1)+"']//supplementary-material//object-id/text()");
				Object result2 = expr2.evaluate(doc, XPathConstants.NODESET);
				NodeList nodes2 = (NodeList) result2;
				System.out.println("No of forms in this page are"+nodes2.getLength());
				if(nodes2.getLength()>=1)
				{
					for(int c=0;c<nodes2.getLength();c++)
					{
						System.out.println("Answering form no:"+(c+1));
						int form_id=Integer.parseInt(nodes2.item(c).getNodeValue());
						System.out.println("Current form_id="+form_id);
						resultDetails=InternalAnswering_MULTIPAGE(form_id,webdriver,rs,st,questionnaire_id,c);
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
public ResultDetails  pre_MULTIPAGE(Integer article_id1,int questionnaire_id,ResultSet rs,Statement st,WebDriver webdriver,int article_id) throws SQLException, InterruptedException, ClassNotFoundException, IOException, ParserConfigurationException, SAXException, XPathExpressionException
{
	int form_type_id=2;
	int choice_id[]=new int[45];
	System.out.println("In Pre-Questionnaire Form.....");
	System.out.println("form_type_id(Pre-Activity)="+form_type_id);
	rs=st.executeQuery("select form_id from questionnaire_form where form_type_id="+form_type_id+" and questionnaire_id="+questionnaire_id);
	rs.next();
	int form_id=rs.getInt(1);
	System.out.println("form_id(Pre-Activity)="+form_id);
	try{
	rs=st.executeQuery("select qc.choice_id,qc.question_id,qc.is_correct from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.is_correct=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
	
	if(!rs.next()){
		
		int i=0,j=0;
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
	//webdriver.findElement(By.linkText("Save and Proceed")).click();
	webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/input")).click();
	//webdriver.findElement(By.linkText("Continue to Activity")).click();
	//internal(article_id1,questionnaire_id,rs,st,webdriver,no_of_completed_forms,article_id);
	WebDriverUtils.waitForPageToLoad(webdriver, "30000");
		//webdriver.findElement(By.xpath("//input[@name='submitbutton']")).submit();
		//webdriver.findElement(By.linkText("Continue to Activity")).click();
		//internal(article_id1,questionnaire_id,rs,st,webdriver,no_of_completed_forms, article_id);
	 
	}
	else{
		int i=0,j=0;
		rs=st.executeQuery("select qc.choice_id,qc.question_id,qc.is_correct from question_choice qc,question_display qd where qc.question_id=qd.question_id and qc.is_correct=1 and qd.questionnaire_id="+questionnaire_id+" and qd.form_id="+form_id);
		while(rs.next()){
			choice_id[i]=rs.getInt(1);
			//System.out.println("choice_id["+i+"]="+choice_id[i]);
		i++;j++;
		}

	for(i=0;i<j;i++){
		webdriver.findElement(By.xpath("//input[@value="+choice_id[i]+"]")).click();
	}
	//webdriver.findElement(By.linkText("Save and Proceed")).click();
	webdriver.findElement(By.xpath("//div[@id='edu_workflow_submit']/input")).click();
	//webdriver.findElement(By.linkText("Continue to Activity")).click();
	//internal(article_id1,questionnaire_id,rs,st,webdriver,no_of_completed_forms,article_id);
	WebDriverUtils.waitForPageToLoad(webdriver, "30000");
	}
	return resultDetails;	
}catch(Exception e){
	resultDetails.setFlag(false);
	return resultDetails;
}
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