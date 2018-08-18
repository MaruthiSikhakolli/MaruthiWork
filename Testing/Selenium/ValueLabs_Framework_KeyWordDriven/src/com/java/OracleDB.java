package com.java;

import java.io.FileInputStream;
import com.java.Objects.ResultDetails;
//import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.Properties;

public class  OracleDB extends TestType {
	
	static Connection connection = null;
	String dbURL = "jdbc:oracle:thin:@racvp01q-prf-08.portal.webmd.com:1521/SVCMED_QA00";
	String username = "GIFT_MGR";
	String password = "GIFT_MGR";
	Statement stmt = null;
	SeleniumDriverTest sele=new SeleniumDriverTest();
	String msg = "";
	boolean flag = true;
	ResultDetails res=new ResultDetails();
	public OracleDB() {
		try {
			Properties prop=new Properties();	
				FileInputStream in = new FileInputStream("./TestInputs/properties/dataSource.properties");
				prop.load(in);
				String dbURL=prop.getProperty("oracle");
				String username=prop.getProperty("oUser");
				String password=prop.getProperty("oPass");
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			connection = DriverManager.getConnection(dbURL, username, password);
			//stmt = connection.createStatement();
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE,ResultSet.CONCUR_READ_ONLY);

			System.out.println("connected successfully");

		} catch (Exception e) {
			e.printStackTrace();
			msg = "unable to register the JDBC Driver";
			flag = false;
			return;
		}
	}
	
	public OracleDB(String conn) {
		try {
			//Properties prop=new Properties();	
				//FileInputStream in = new FileInputStream("./TestInputs/properties/dataSource.properties");
			//	prop.load(in);
			//	String dbURL=prop.getProperty("oracle");
			//	String username=prop.getProperty("oUser");
			//	String password=prop.getProperty("oPass");
			String dbURL=conn.split("::")[0].trim();
			String username=conn.split("::")[1].trim();
			String password=conn.split("::")[2].trim();
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			connection = DriverManager.getConnection(dbURL, username, password);
			stmt = connection.createStatement();
			System.out.println("connected successfully");

		} catch (Exception e) {
			e.printStackTrace();
			msg = "unable to register the JDBC Driver";
			flag = false;
			return;
		}
	}
	
	public OracleDB(String conn,String Sqlserver) {
		try {
			String dbURL=conn.split("::")[0].trim();
			String username=conn.split("::")[1].trim();
			String password=conn.split("::")[2].trim();
			String databaseName = "";
			System.out.println(dbURL);
			try {
				databaseName = conn.split("::")[3].trim();
				if (dbURL.startsWith("jdbc:sqlserver://"))
					dbURL = dbURL.split("/")[0]+";instanceName="+dbURL.split("/")[1].split(",")[0]+";instancePort="+dbURL.split("/")[1].split(",")[1]+";databaseName="+databaseName;
				else
					dbURL = "jdbc:sqlserver://" + dbURL.split("/")[0]+";instanceName="+dbURL.split("/")[1].split(",")[0]+";instancePort="+dbURL.split("/")[1].split(",")[1]+";databaseName="+databaseName;
			} catch(Exception e) {
				if (dbURL.startsWith("jdbc:sqlserver://"))
					dbURL = dbURL.split("/")[0]+";instanceName="+dbURL.split("/")[1].split(",")[0]+";instancePort="+dbURL.split("/")[1].split(",")[1]+";databaseName="+databaseName;
				else
					dbURL = "jdbc:sqlserver://" + dbURL.split("/")[0]+";instanceName="+dbURL.split("/")[1].split(",")[0]+";instancePort="+dbURL.split("/")[1].split(",")[1]+";databaseName="+databaseName;
			}
			System.out.println(dbURL);
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
			connection = DriverManager.getConnection(dbURL, username, password);
			stmt = connection.createStatement();
			System.out.println("connected successfully");
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			msg = "Please provide the connection string in specified format";
			flag = false;
			return;
		} catch (SQLException e){
			e.printStackTrace();
			msg = "Please provide the connection string in specified format";
			flag = false;
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			msg = "Please attach the respective jar file";
			flag = false;
			return;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Given value is not matched with DB value";
			flag = false;
			return;
		}
	}
	
	public ResultDetails oExecute(String query) {
		
		try {
			
			int result=stmt.executeUpdate(query);
			String Output=result+"number of records are affected";
			res.setFlag(true);
			
			return res;
		} catch (Exception e) {
			msg = "error in querying database";
			flag = false;
			res.setFlag(false);
			res.setErrorMessage(e.getMessage());
			return res;
		}
	}
	
	public void oracleInsertTargetList() {
		String guid=sele.getGuid();
		int uid=Integer.parseInt(guid);
		try {
			String query="insert into ACTIVITY_TARGET_LIST_XREF (PROMO_ACTIVITY_ID,LIST_MATCH_ID,CREATE_DATE) values(10000,(select to_char((select sysdate from dual),'yyMMddHHmmss') from dual),(select sysdate+3/1440 from dual))";
			boolean result=stmt.execute(query);
			System.out.println(result);
			String query2="INSERT INTO TARGET_LIST" +
					"(TARGET_LIST_ID,CLIENT_CUSTOMER_ID,LIST_MATCH_ID,GUID,LIST_SOURCE_ID,IS_ACTIVE,TARGET_LIST_TYPE) " +
					"VALUES((select max(TARGET_LIST_ID)+1 from target_list)," +
					"5484185544," +
					"(select LIST_MATCH_ID from ACTIVITY_TARGET_LIST_XREF where CREATE_DATE=(select max(CREATE_DATE) from ACTIVITY_TARGET_LIST_XREF)),"+uid+
					",1," +
					"1," +
					"7)";
			result=stmt.execute(query2);
		} catch (Exception e) {
			msg = "error in querying database";
			flag = false;
			return;
		}
	}
	
	public ResultDetails oracleVerify(String strQuery) {
		String Val;
		System.out.println(strQuery);
		System.out.println(strQuery.split("::")[0]);
		System.out.println(strQuery.split("::")[1]);
		//SstrQuery1=strQuery.split("::")[0];
		//String value=strQuery.split("::")[1];
		boolean flag=false;
		try {	
		ResultSet result=stmt.executeQuery(strQuery.split("::")[0].trim());
		ResultSetMetaData rsmd= result.getMetaData();
		//result.next();
		//System.out.println(result.getString(1));
		while(result.next())
		{
			System.out.println("This has entered While Loop");
			for(int i=1;i<=rsmd.getColumnCount();i++)
			{
				
				if(result.getObject(i)==null)
					 Val="";
				else
					Val=result.getObject(i).toString();
				System.out.println(Val);
				//System.out.println(result.getObject(i).toString());
				if(Val.trim().contentEquals(GETVALUE(strQuery.split("::")[1].trim())))
				{
					System.out.println("Value is found in the ResultSet");
					flag=true;
					res.setFlag(true);
					return res;
				}
			}}
		if(!flag)
		{
			res.setFlag(false);
			res.setErrorMessage("Expected value not found in the resulset");
		}
		
		return res;
		} catch (Throwable e) {
		System.out.println(e.getMessage());
		msg = "error in querying database";
		res.setFlag(false);
		res.setErrorMessage(e.getLocalizedMessage());
		return res;
		}
		
		}
	
	
	public boolean oracleVerifyValue(String strQuery,String value) {
		boolean flag=false;
		try {	
		ResultSet result=stmt.executeQuery(strQuery);

		result.next();
		System.out.println(result.getString(1));
		if(result.getString(1)==null)
		{
		System.out.println("Value is null");
		
		}
		else if(result.getString(1).contains(value.trim()))
		{
		System.out.println("Contains the expected value");
		flag=true;
		
		}
		else
		{
		System.out.println("Value is not as expected");
		}

		} catch (Throwable e) {
		System.out.println(e.getMessage());
		msg = "error in querying database";
		flag = false;
		}
		return flag;
		}

	public void oracleGiftManager(String guid) {	
		try {
			String query2="select ORDER_ID from GIFT_ORDER where CUSTOMER_GUID="+guid;
			ResultSet result=stmt.executeQuery(query2);
			int order=result.getInt(1);
			String query3="Delete from ORDER_TYPE_VALUES_LKP where ORDER_ID="+order;
			stmt.execute(query3);
			String query4="Delete from GIFT_ORDER_TRANSACTION  where ORDER_ID="+order;
			stmt.execute(query4);
			String query5="Delete from GIFT_ORDER  where ORDER_ID="+order;
			stmt.execute(query5);
		} catch (Exception e) {
			msg = "error in querying database";
			flag = false;
			return;
		}
	}
	
	public void oracleInsertAttribute(String Attrname,String Attrval) {
		String guid=sele.getGuid();
		int uid=Integer.parseInt(guid);
		int value=Integer.parseInt(Attrval);
		try {
			String query2="insert into TARGET_LIST_ATTRIBUTE " +
					"(TARGET_LIST_ATTRIB_ID,TARGET_LIST_ID,ATTRIBUTE_VALUE,ATTRIBUTE_ID)" +
					"values((select max(TARGET_LIST_ATTRIB_ID)+1 from TARGET_LIST_ATTRIBUTE)," +
					"(SELECT max(TARGET_LIST_ID) FROM TARGET_LIST where GUID="+uid+"),'"+Attrname+"',"+value+")";
			//String query="insert into ACTIVITY_TARGET_LIST_XREF (PROMO_ACTIVITY_ID,LIST_MATCH_ID,CREATE_DATE) values(10739,(select to_char((select sysdate from dual),'yyMMddHHmmss') from dual),(select sysdate+3/1440 from dual))";
			boolean result=stmt.execute(query2);
			System.out.println(result);
			//result=stmt.execute(query2);
		} catch (Exception e) {
			msg = "error in querying database";
			flag = false;
			return;
		}
	}
	
	public void oracleupdateCPUpdatedDate() {
		String guid=sele.getGuid();
		int uid=Integer.parseInt(guid);
		try {
			//String query="update Target_List set CP_LAST_UPDATED=(select max(LAST_UPDATED)+3/1440 from Target_List where guid='"+uid+"') where TARGET_LIST_ID=(select max(TARGET_LIST_ID) from TARGET_LIST)";
			String query="update Target_list  set CP_LAST_UPDATED=(select max(LAST_UPDATED)+3/1440 from Target_List) where Target_list_ID=(select max(Target_list_ID) from Target_list where guid="+guid+")";
			boolean result=stmt.execute(query);
			System.out.println(result);
			String query2="select * from Target_List where TARGET_LIST_ID=(select max(TARGET_LIST_ID) from TARGET_LIST)";
			ResultSet rset=stmt.executeQuery(query2);
			rset.next();
			System.out.println(rset.getString(32));	
			System.out.println(rset.getString(29));
		} catch (Exception e) {
			msg = "error in querying database";
			flag = false;
			return;
		}
	}
	
	public void finalize() {
		try {
			connection.close();
		} catch (SQLException e) {
			msg = "Unable to close collection";
			flag = false;
			return;
		}
	}
	
	public static void main(String args[])
	{
		try{
		
			//String dbURL = "jdbc:sqlserver://sqlvp-cq00-08.portal.webmd.com\\cq00:1433;databaseName=ConsRatings";
			//String dbURL = "jdbc:sqlserver://sqlvp-cq00-08.portal.webmd.com\\cq00:1433;databaseName=ConsRatings;integratedSecurity=true;";
		//String username = "sqldbread";
		//String password = "dbr33d!";
		//String query2="SELECT * FROM dbo.UserReviews WHERE PrimaryId = '44141'";
	//	String result=query2+"::"+"194815";
	//	String query=dbURL+"::"+username+"::"+password;
	//	OracleDB db=new OracleDB(query,"sql");
	//	db.oracleVerify(result);
		/*Statement stmt = null;
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
		connection = DriverManager.getConnection(dbURL, username, password);
		String query2="SELECT * FROM dbo.UserReviews WHERE PrimaryId = '44141'";
		stmt = connection.createStatement();
		ResultSet rset=stmt.executeQuery(query2);
		rset.next();
		System.out.println(rset.getObject(1).toString());*/
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	//	SeleniumDriverTest sele=new SeleniumDriverTest();
		String msg = "";
	}
	
}
