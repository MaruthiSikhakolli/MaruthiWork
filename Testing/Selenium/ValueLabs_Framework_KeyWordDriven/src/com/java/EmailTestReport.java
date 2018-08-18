package com.java;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.InputVerifier;
import com.java.ImportnExport.ExportTestResultsExcel;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.util.zip.*;

public class EmailTestReport
{
	//Set the host smtp address
	Properties props = new Properties();
	private String smtpHostName = null;
	private String recipient = null;
	private	String reportType = null;
	private String subject = null;
	private String from = null;
	private String message = null;
	private String port = null;
	private String smtp = null;
	private String countersText = null;
	private long lngTimeDuration;
	
	public EmailTestReport() {
		try {
			InputStream is = new FileInputStream(".//TestInputs//properties//Email.properties");
			System.out.println(is);
			props.load(is);
			is.close();
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}	
	}	
	
	public void postMail(int[] counters, String BrowserType, String URL) throws MessagingException
	{
		Date date = new Date();
		SimpleDateFormat emailDateFormat = new SimpleDateFormat("dd/MMM/yyyy hh:mm a");
		lngTimeDuration = date.getTime() - SeleniumDriverTest.lngTimeDuration;
		
		boolean debug = false;
//		String testData = ".//TestInputs//DataSheets//"+SeleniumDriver.hMap.get("testDataAttachment");
		String testHTMLReport = ExportTestResultsExcel.testHTMLResultPath;
		String line;		
		
		Message msg;
		System.out.println(testHTMLReport);
		smtpHostName = props.getProperty("SMTP_HOST_NAME");
		recipient = props.getProperty("recipients");
		reportType = props.getProperty("reportType");
		from = props.getProperty("from");
		subject = props.getProperty("subject");
		message = props.getProperty("message");
		port = props.getProperty("SMTP_PORT");		
		countersText = "Browser Executed: " + BrowserType + "<br><br>" +"Execution Start Date & Time: "+SeleniumDriverTest.strEmailDateFormat+ "<br>" +"Execution End Date & Time: "+emailDateFormat.format(date)+"<br>" +"Total Execution Time Duration: "+timeDuration(lngTimeDuration)+ "<br><br>" + "URL: " + URL + "<br>" + "Total Cases Executed: " + counters[0] + "<br>" + "Total Cases Passed: " + counters[1] + "<br>" + "Total Cases Failed: " + counters[2];
		//message = message.replace("&&Counters&&",countersText);
		Properties newprops = new Properties();
		newprops.put("mail.smtp.host", smtpHostName);
		newprops.setProperty("mail.port", port);		
		Session session = Session.getDefaultInstance(newprops, null);
		
		if (reportType.equalsIgnoreCase("Basic")) {
			message = message.replace("&&Counters&&",countersText);
		} else if (reportType.equalsIgnoreCase("HTML")) {
			message = "";
			try {
				BufferedReader input = new BufferedReader(new FileReader(testHTMLReport));			
				while ((line = input.readLine()) != null){
					//System.out.println(line);
					message = message+line;
				}		
			} catch (IOException ioe) {
				//System.err.println();
				ioe.printStackTrace();
			}
		}
		
		// create a message
		msg = new MimeMessage(session);
		
		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);
		
		String[] recipients = recipient.split(";");
		InternetAddress[] addressTo =new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++)
		{
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		
		// Setting the Subject and Content Type
		//msg.setSubject(subject +" - "+ (new java.util.Date()).toString());
		msg.setSubject(subject +" - "+ SeleniumDriverTest.hMap.get("TimeStamp"));
		
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		String testResultPath = ExportTestResultsExcel.testResultPath;
		messageBodyPart = new MimeBodyPart();
		DataSource tResult = new FileDataSource(testResultPath);
		messageBodyPart.setDataHandler(new DataHandler(tResult));
		messageBodyPart.setFileName(testResultPath.substring(testResultPath.lastIndexOf('/')+ 1, testResultPath.length()));
		multipart.addBodyPart(messageBodyPart);
		messageBodyPart = new MimeBodyPart();		
		DataSource tReport = new FileDataSource(testHTMLReport);
		messageBodyPart.setDataHandler(new DataHandler(tReport));
		messageBodyPart.setFileName(tReport.getName());
		multipart.addBodyPart(messageBodyPart);
		msg.setContent(multipart);
		System.out.println("Sending Email...");
		Transport.send(msg);
		System.out.println("Report E-mail Sent.");
	}
	
	public String timeDuration(long timeDuration) {
		int hour,days;
		String strDuration;
		timeDuration = (timeDuration/1000);
		strDuration = timeDuration+" sec";
		if (timeDuration>=60) {
			int sec = (int) timeDuration%60;
			int min = (int) timeDuration/60;
			strDuration = min+" min "+sec+" sec";
			if (min>=60) {
				hour = min/60;
				min = min%60;
				strDuration = hour+" hour "+min+" min "+sec+" sec";
				if (hour>=23) {
					days = hour/24;
					hour = hour%24;
					strDuration = days+" day "+hour+" hour "+min+" min "+sec+" sec";
				}
			}
		}
		return strDuration;
	}
}