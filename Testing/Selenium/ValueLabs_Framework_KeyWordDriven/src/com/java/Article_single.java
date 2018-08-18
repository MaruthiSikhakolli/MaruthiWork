package com.java;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.openqa.selenium.WebDriver;
import com.java.Objects.ResultDetails;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Article_single{
	
	ResultDetails resultDetails = new ResultDetails();
	OracleDB ODB=new OracleDB();
	ResultSet rs=null;Statement st=ODB.stmt;
	String s1="",s2="",s3="";
	public enum Article_SinglePage{
		BTM,CME, NUR, PRM, LOC,OHC, NPC, PAC, BNT, PRQ, PNR, BTS, BTR, BTZ, EVL, EVR, ART, ARE, NTC
	}

public ResultDetails Article(WebDriver webdriver, String field,String value) throws ClassNotFoundException, SQLException, XPathExpressionException, IOException, ParserConfigurationException, InterruptedException, SAXException
{
	try {
		webdriver.get("http://www."+System.getenv("env")+".medscape.org/");
		webdriver.manage().window().maximize();
	
		resultDetails=FetchArticle(webdriver,field,value);
		return resultDetails;
	}
	catch(Throwable e) {
		System.out.println("e456");
	//	System.out.println(e.getMessage());
		resultDetails=FetchArticle(webdriver,field,value);
		return resultDetails;
	}
}//Article
public ResultDetails FetchArticle(WebDriver webdriver, String field,String value) throws SQLException, ClassNotFoundException, XPathExpressionException, IOException, ParserConfigurationException, InterruptedException, SAXException 
{
	System.out.println("field= " + field + " value= " + value);
	Article_SinglePage art = Article_SinglePage.valueOf(field);
	//field = field.substring(3, field.length());
	try{
Integer article_id1 = null;

switch(art)
{
case BTM://Botom not required
	ConnectionDB();
rs=st.executeQuery("select distinct qd.questionnaire_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=0 and  qd.form_id=qf.form_id and qd.questionnaire_id=qf.questionnaire_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<500 minus select distinct qd.questionnaire_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=1 and  qd.form_id=qf.form_id and qd.questionnaire_id=qf.questionnaire_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<2000");
		break;
case CME://Fetch the article which have CME eligibility for physician
	ConnectionDB();
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.CREDIT_TYPE_ID=1 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");			
break;
case NUR://Get the Nurse eligibility CME Article
	ConnectionDB();
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.CREDIT_TYPE_ID=2 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
break;
case PRM://Get the Pharmacit Eligibility CME Article 
	ConnectionDB();
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.CREDIT_TYPE_ID=3 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
break;

case OHC://Get the CMLE
	ConnectionDB();
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.CREDIT_TYPE_ID=5 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
	break;
case LOC://Get the LOC
	ConnectionDB();
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.CREDIT_TYPE_ID=6 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");	
	break;
case NPC://Get the NP_ce
	ConnectionDB();
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.CREDIT_TYPE_ID=7 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
	break;
case PAC://GET THE pa_ce
	ConnectionDB();
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.CREDIT_TYPE_ID=8 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
	break;
case PRQ:ConnectionDB();
//PreActivity Required Questionnaire(We have only 3 records for this type of requirement)
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=2 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
break;
case PNR:ConnectionDB();
	//PreActivity Not Required Questionnaire
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=0 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=2 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
break;
case BNT:ConnectionDB();//Botttom Not Required
	System.out.println("Botttom Not Required");
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=0 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");

	break;
case BTR:ConnectionDB();//Bottom Required Question form
	System.out.println("Bottom Required Question form");
rs=st.executeQuery("select distinct qd.questionnaire_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=1 and  qd.form_id=qf.form_id and qd.questionnaire_id=qf.questionnaire_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<500 minus select distinct qd.questionnaire_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd where qd.is_required=0 and  qd.form_id=qf.form_id and qd.questionnaire_id=qf.questionnaire_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<2000");
	break;
case BTS:
	ConnectionDB();//Bottom Required Question form with 70% score
	System.out.println("Bottom Required Question form with 70% score");
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.passing_score=70 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
break;
case BTZ:
	ConnectionDB();//Bottom Required Question form with 0% score(16records in 1000 records)
	System.out.println("Bottom Required Question form with 0% score");
rs=st.executeQuery("select distinct qd.questionnaire_id,qd.form_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,QUESTION_DISPLAY qd,ACTIVITY_ELIGIBILITY ac where qd.is_required=1 and qf.questionnaire_id=qd.questionnaire_id and qd.form_id=qf.form_id and qf.form_type_id=3 and a.questionnaire_id=qf.questionnaire_id and ac.passing_score=0 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
	break;
case EVR://Evaluation Required from
	ConnectionDB();
rs=st.executeQuery("select distinct qf.questionnaire_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.questionnaire_id=qf.questionnaire_id and ac.EVAL_REQUIRED=1 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
	break;
case EVL:// Not required Evaluation Question form
	ConnectionDB();
	System.out.println("Not Required Evaluation Question form");
rs=st.executeQuery("select distinct qf.questionnaire_id from QUESTIONNAIRE_FORM qf,article a,activity_info i,ACTIVITY_ELIGIBILITY ac where a.questionnaire_id=qf.questionnaire_id and ac.EVAL_REQUIRED=0 and a.activity_id=ac.activity_id and a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1000");
	break;
case ART://Fetch the article which is not expired
	ConnectionDB();
rs=st.executeQuery("select a.questionnaire_id,a.article_id,a.activity_id,i.expiration_date from article a,activity_info i where a.activity_id=i.activity_id and i.expiration_date>(sysdate+1) and rownum<1200");
	break;
case ARE:
	//To get Expired article
	ConnectionDB();
	rs=st.executeQuery("select a.questionnaire_id,a.article_id from article a,activity_info i where a.activity_id=i.activity_id and a.questionnaire_id!=0 and i.expiration_date<(sysdate+1) and rownum<1000");
	break;
case NTC:
	ConnectionDB();
	//No test cme
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
//questionnaire_id=26895;
//questionnaire_id=24861;
System.out.println("questionnaire_id="+questionnaire_id);

rs=st.executeQuery("select article_id from article where questionnaire_id="+questionnaire_id);
rs.next();
//article_id1=763117;
//article_id1=764634;
article_id1=rs.getInt(1);

resultDetails=XMLQuery(webdriver,article_id1,field,value);
return resultDetails;

}catch(Throwable e){
	e.printStackTrace();
	System.out.println(e.getMessage());
	
	resultDetails.setFlag(false);
	System.out.println(e.getMessage());
	return resultDetails;
}
}//Fetch Article
public ResultDetails XMLQuery(WebDriver webdriver,Integer article_id1, String field,String value) throws XPathExpressionException, IOException, ParserConfigurationException, InterruptedException, SAXException, ClassNotFoundException, SQLException 
{
	ResultDetails resultDetails = new ResultDetails();
try{
	//String s1="",s2="",s3="";
	s2=null;s1=null;s3=null;
webdriver.get("http://www."+System.getenv("env")+".medscape.org/viewarticle/"+article_id1);
System.out.println("article_id1="+article_id1);
s1=article_id1.toString();
s2=s1.substring(0, 3);
s3=s1.substring(3, 6);

System.out.println("s2="+s2);
System.out.println("s3="+s3);
Runtime.getRuntime().exec("D:/sowjanya/pscp -scp -pw 1@Welcome WBMD+sveeravalli@ats03q-prf-08.portal.webmd.com:/webmd/content/qa/article/"+s2+"/"+s3+"/"+s2+s3+".xml D:/sowjanya");

DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
domFactory.setNamespaceAware(true);
DocumentBuilder builder = domFactory.newDocumentBuilder();

String strXML = "D:/sowjanya/"+s2+s3+".xml";
File file = new File("temp");
Thread.sleep(5000);
/*while(true){
	
		file=new File(strXML);
		Thread.sleep(5000);
		try{
		if(file.exists())
		{
			System.out.println("Article created in XML");
			break;
		}else{
			System.out.println("XML file not exists so fetching for another article");
			resultDetails=FetchArticle(webdriver,field,value);

		}
		
	}catch(Exception e){
		System.out.println("e456585");
		//resultDetails=FetchArticle(webdriver,field,value);

	}
	}*/try{
		file=new File(strXML);
		Thread.sleep(5000);

		if(!(file.exists())){
			System.out.println("XML File is not copied");
			s2=null;s1=null;s3=null;
			System.out.println("s2="+s2);
			System.out.println("s3="+s3);
			resultDetails=FetchArticle(webdriver,field,value);
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

XPathFactory factory = XPathFactory.newInstance();
XPath xpath = factory.newXPath();

//It will gives number of questions in that article 
XPathExpression expr = xpath.compile("//body//sec[@sec-type='page']//supplementary-material//object-id");
//It will give the number of apgees in this article
//XPathExpression expr = xpath.compile("//body//sec[@sec-type='page']");
Object result = expr.evaluate(doc, XPathConstants.NODESET);
NodeList nodes1 = (NodeList) result;

System.out.println("no of pages in the article="+nodes1.getLength());
try{
	if(webdriver.getTitle().equalsIgnoreCase("Page Not Found"))
	{
		System.out.println("This is Page Not Found Article,so Fetching for another article");
		resultDetails=FetchArticle(webdriver,field,value);
	}
else if(nodes1.getLength()==1||nodes1.getLength()==0)
{
	System.out.println("This is a SINGLE PAGE............");
	
	resultDetails.setFlag(true);
	resultDetails.setErrorMessage("");
}

else{
	System.out.println("This is a MULTI PAGE Searching for SINGLE PAGE............");
	resultDetails=FetchArticle(webdriver,field,value);
}
}
catch(Exception e){		
	System.out.println("Catch Block in Aricle _single");
	//resultDetails=FetchArticle(webdriver,field,value);
}
file.delete();
return resultDetails;
}catch(Throwable e){
	resultDetails.setFlag(false);
	System.out.println(e.getMessage());
	return resultDetails;
}
}//XML_QUERY ARTILCE
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
		{	
			System.out.println("IN connection_Catch");
			System.out.println(e.getLocalizedMessage());
			ODB=new OracleDB(); 
			st=ODB.stmt;	
			rs=null;
		}
}

}
