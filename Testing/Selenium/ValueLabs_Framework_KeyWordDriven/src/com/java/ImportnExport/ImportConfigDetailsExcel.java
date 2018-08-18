package com.java.ImportnExport;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;
import com.java.SeleniumDriverTest;
import com.java.Objects.ConfigDetails;
import java.sql.*;

/**
* This java program is used to read the data from a Excel sheet (TestData.xls)
*/
public class ImportConfigDetailsExcel {
	private static Logger log = Logger.getLogger(ImportConfigDetailsExcel.class.getName());
	int rscount=0;
	public ImportConfigDetailsExcel (){}	
	/**
	 * This is to read the details from Excel sheet which is 
	 * in the path given as argument
	 * @return HashMap 
	 */	
	
	public HashMap<Integer,ConfigDetails> displayFromExcel ()
	{
		//HashMap to store each row as bean object along with the row number
		HashMap<Integer,ConfigDetails> Rows =  new HashMap<Integer,ConfigDetails>();
		ResultSet rs;
		String str = null;		
		
		try
		{	
			ArrayList<Integer> TestCases = new ArrayList<Integer>();
			
			String xlsPath =SeleniumDriverTest.hMap.get("configFile");
			System.out.println("test data details path===== "+xlsPath);
			Class.forName("org.relique.jdbc.csv.CsvDriver");
            String url = "jdbc:relique:csv:.//TestInputs//DataSheets";
            //String sql="select * from Sample";
            Connection conn = DriverManager.getConnection(url, "", "");
			//Connection to excel sheet as database
			//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			//Connection conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ="+xlsPath+";DriverID=22;READONLY=false","","");
			String sql="Select TestCasestobeExecuted,ApplicationURL,BrowserType  from "+xlsPath;
	        Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        rs = st.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
	        int numberOfColumns = rsmd.getColumnCount(); //number of columns in a row
	        
	        //Loop through the test steps resultset.
	        while (rs.next()) {
	        	ConfigDetails confDtls=new ConfigDetails();
	        	TestCases = new ArrayList<Integer>();
	        	rscount=rscount+1;
		        //Loop through the columns in each row.
	        	for (int c = 1; c <= numberOfColumns; c++) {
		          str = rs.getString(c);
		          if(c==1){
					    log.debug("String to split: "+ str);
					    //Splitting the Test cases to be executed token
					    String[] tokens = str.split(",");
					    for(int i=0;i<tokens.length;i++){
					    	System.out.println("Tokens: " + tokens[i]);									    	
					    	if(!tokens[i].contains("-"))
					    		TestCases.add(Integer.parseInt(tokens[i]));
					    	else{
					    		String[] range=tokens[i].split("-");
					    		String from = range[0];
					    		String to =  range[1];
					    		int f = Integer.parseInt(from);
					    		int t = Integer.parseInt(to);
					    		TestCases.add(f);
					    		while(f!=t){	
					    			f=f+1;
					    			int s = f;									    			
					    			TestCases.add(s);
					    		}
					    	}
					    }
					   log.debug("ranges of - : " + TestCases);
					   for(int i=0;i<TestCases.size();i++){
						   System.out.println("TEST CASES ARE HERE : ----- " +TestCases.get(i));
					   }
					    confDtls.setTestCasesToBeExecuted(TestCases);
					} else if(c==2) { 
						confDtls.setScriptPath(str);
					} else if(c==3) {
						confDtls.setBrowser(str);
					}
	        	}	        	
	        	//Adding bean object i.e. a row along with number to HashMap
	        	//System.out.println("rows to put"+rs.getInt(1));
	        	//System.out.println("rows to put"+rs.getInt(2));
	        	Rows.put(rscount,confDtls);
	            //System.out.println(rs.getInt(1));
	        }
	        rs.close();
	        st.close();
	        conn.close();
		} catch (Exception e){
			e.printStackTrace ();
		}		
	return Rows;
	}
}	

