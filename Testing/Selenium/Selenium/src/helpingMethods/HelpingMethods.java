package helpingMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import au.com.bytecode.opencsv.CSVReader;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class HelpingMethods {
	public void readExcelDataUsingJxl() {
		File src = new File("./data/testdata.xls");
		try {
			Workbook wb = Workbook.getWorkbook(src);
			Sheet sh1 = wb.getSheet(0);
			Cell c1 = sh1.getCell(0, 0);
			String data1 = c1.getContents();
			System.out.println(data1);
			System.out.println("Sheet data is " + data1);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readExcelDataUsingApachePOI() {
		try {
			File src = new File("filepath/excelsheetname.xlsx");
			FileInputStream fis = new FileInputStream(src);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sh1 = wb.getSheetAt(0);
			int totalRows = sh1.getLastRowNum();
			System.out.println(sh1.getRow(0).getCell(0).getStringCellValue());
			System.out.println(sh1.getRow(0).getCell(1).getNumericCellValue());
			System.out.println(sh1.getRow(1).getCell(0).getStringCellValue());
			System.out.println(sh1.getRow(1).getCell(1).getNumericCellValue());
			System.out.println(sh1.getRow(2).getCell(0).getStringCellValue());
			System.out.println(sh1.getRow(2).getCell(1).getStringCellValue());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void readCSVData() {
		try {
			CSVReader reader = new CSVReader(new FileReader("C:\\Users\\mukesh_otwani\\Desktop\\demo.csv"));
			List<String[]> li = reader.readAll();
			System.out.println("Total rows which we have is " + li.size());
			Iterator<String[]> i1 = li.iterator();
			while (i1.hasNext()) {
				String[] str = i1.next();
				System.out.print(" Values are ");
				for (int i = 0; i < str.length; i++) {
					System.out.print(" " + str[i]);
				}
				System.out.println("   ");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void sendEmailWithAttachment() {
		// Create object of Property file
		Properties props = new Properties();
		// this will set host of server- you can change based on your requirement
		props.put("mail.smtp.host", "smtp.gmail.com");
		// set the port of socket factory
		props.put("mail.smtp.socketFactory.port", "465");
		// set socket factory
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		// set the authentication to true
		props.put("mail.smtp.auth", "true");
		// set the port of SMTP server
		props.put("mail.smtp.port", "465");

		// This will handle the complete authentication
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("add your email", "add your password");
			}
		});

		try {
			// Create object of MimeMessage class
			Message message = new MimeMessage(session);
			// Set the from address
			message.setFrom(new InternetAddress("mukeshotwani.50@gmail.com"));
			// Set the recipient address
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("mukesh.otwani50@gmail.com"));
			// Add the subject link
			message.setSubject("Testing Subject");
			// Create object to add multimedia type content
			BodyPart messageBodyPart1 = new MimeBodyPart();
			// Set the body of email
			messageBodyPart1.setText("This is message body");
			// Create another object to add another content
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			// Mention the file which you want to send
			String filename = "G:\\a.xlsx";
			// Create data source and pass the filename
			DataSource source = new FileDataSource(filename);
			// set the handler
			messageBodyPart2.setDataHandler(new DataHandler(source));
			// set the file
			messageBodyPart2.setFileName(filename);
			// Create object of MimeMultipart class
			Multipart multipart = new MimeMultipart();
			// add body part 1
			multipart.addBodyPart(messageBodyPart2);
			// add body part 2
			multipart.addBodyPart(messageBodyPart1);
			// set the content
			message.setContent(multipart);
			// finally send the email
			Transport.send(message);
			System.out.println("=====Email Sent=====");

		} catch (MessagingException e) {
			throw new RuntimeException(e);

		}
	}

	// Method to connect to DB and fetch the data
	public void SQLConnector() throws ClassNotFoundException, SQLException {
		String dbUrl = "jdbc:mysql://localhost:3036/emp";
		String username = "root";
		String password = "guru99";

		// Query to Execute
		String query = "select *  from employee;";

		// Load mysql jdbc driver
		Class.forName("com.mysql.jdbc.Driver");

		// Create Connection to DB
		Connection con = DriverManager.getConnection(dbUrl, username, password);

		// Create Statement Object
		Statement stmt = con.createStatement();

		// Execute the SQL Query. Store results in ResultSet
		ResultSet rs = stmt.executeQuery(query); // executeQuery is used to execute select queries only. To execute
													// update/delete queries
		// we need to use updateQuery as shown below
		String updateQuery = "update employee set salary = 1000000 where emp_id = 100";
		String deleteQuery = "Delete from employee where emp_id = 100";
		int updateResult = stmt.executeUpdate(updateQuery);
		int deleteResult = stmt.executeUpdate(deleteQuery);

		// While Loop to iterate through all data and print results
		while (rs.next()) {
			String myName = rs.getString(1);
			String myAge = rs.getString(2);
			System.out.println(myName + "  " + myAge);
		}

		// Prepared statement example
		String sql = "DELETE FROM warehouses WHERE id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		// set the corresponding param
		pstmt.setInt(1, 100);
		// execute the delete statement
		pstmt.executeUpdate();

		// closing DB Connection
		con.close();
	}
}
