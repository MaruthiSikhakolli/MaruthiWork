package com.java.ImportnExport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import com.java.Objects.TestDataDetails;
import com.java.SeleniumDriverTest;

/**
* This java program is used to read the data from a Excel sheet using SQL query
*/
public class ImportTestDataDetailsExcel{
	String TestDataFile=null;
	private static Logger log = Logger.getLogger(ImportTestDataDetailsExcel.class.getName());
	public ImportTestDataDetailsExcel (){}	
	
	/**
	 * This is to read the details from Excel sheet which is 
	 * in the path given as argument 
	 * @param String
	 * @return HashMap
	 */	
	@SuppressWarnings("deprecation")	
		
	public HashMap<Integer,TestDataDetails> displayFromExcel (int TCID)
	{
			//HashMap to store each row as bean object along with the row number
			TreeMap<Integer,TestDataDetails> intermediateRows =  new TreeMap<Integer,TestDataDetails>();
			HashMap<Integer,TestDataDetails> Rows =  new HashMap<Integer,TestDataDetails>();
		
			String xlsPath ="";
			ResultSet rs, rs1;
			try
			{
				//System.out.println("TCID"+TCID);
				xlsPath = SeleniumDriverTest.hMap.get("testDataFile");
				System.out.println("test data details path=====** "+xlsPath);
				Class.forName("org.relique.jdbc.csv.CsvDriver");
	            String url = "jdbc:relique:csv:.//TestInputs//DataSheets";
	            //String sql="select * from Sample";
	            Connection conn = DriverManager.getConnection(url, "", "");
				//Connection to excel sheet as database
				//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				//Connection conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ="+xlsPath+";DriverID=22;READONLY=false","","");
				//System.out.println("xlsPath"+xlsPath);
	            String sql="Select * from  "+xlsPath+"  Where TestCaseID='"+TCID+"' Order by TestPriority";
		       // System.out.println(sql);
	            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		        rs = st.executeQuery(sql);
		        //System.out.println(rs.getString(1));
		        //System.out.println(rs.);
				ResultSetMetaData rsmd = rs.getMetaData();
		        int numberOfColumns = rsmd.getColumnCount(); //number of columns in a row
		       // System.out.println(numberOfColumns);
		        //Loop through the test steps resultset.
		        while (rs.next()) {
		        	TestDataDetails tdd=new TestDataDetails();
		        	int priority = 0;
		        	int caseId = 0;
					int dataId = 0;
					tdd.setTestCaseID(Integer.parseInt(rs.getString(1)));
					priority=Integer.parseInt(rs.getString(2));
					tdd.setTestDataID(Integer.parseInt(rs.getString(2)));
					//System.out.println(rs.getString(3)+"rs.getString(3)");
					tdd.setTestCaseTitle(rs.getString(3));
					tdd.setWorkingPage(rs.getString(4));
					tdd.setDataFields(rs.getString(5));	
					tdd.setDataValues(rs.getString(6));
					tdd.setActionType(rs.getString(7));
					tdd.setCondition(rs.getString(8));
					tdd.setBrowserType(rs.getString(9));
					tdd.setFieldName(rs.getString(10));
					
			       
		            if(priority != 0)	
		            	intermediateRows.put(priority,tdd);
		        }
		        
		        Set<Integer> prKeys = new TreeSet<Integer>();
				prKeys = intermediateRows.keySet();
				//System.out.println(" prKeys size =" +prKeys.size());
				Iterator<Integer> iterKeys = prKeys.iterator();		
				int counter = 0;
				
				while(iterKeys.hasNext()) {
					counter++;
					int nextKey = iterKeys.next();					
					Rows.put(new Integer(counter), intermediateRows.get(nextKey));
					//System.out.println("Iterator size"+Rows.size());
				}
				
				//Push the Parameters in to hash map
				if (SeleniumDriverTest.parameterDetails.isEmpty()) {
					
					try{
					 TestDataFile=System.getenv("TestData");
					 //System.out.println("TestDataFile"+TestDataFile);
					}catch(Exception e)
					{
						e.printStackTrace();
						System.out.println("Test Data property is not set so taking a default value TestData");
						TestDataFile="TestData";
					}
					if(TestDataFile==null)
						TestDataFile="TestData";
					sql="Select * from  "+TestDataFile;
					System.out.println("TestDataFile::"+TestDataFile);
			        Statement st1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			        rs1 = st1.executeQuery(sql);
			        
			        String pName = "";		        
			        String pValue = "";
			        int pIndex = 0;
			        String pKey = "";			        
			        
			        ResultSetMetaData rsmd1 = rs1.getMetaData();
			        int numberOfColumns1 = rsmd1.getColumnCount(); //number of columns
			        while (rs1.next()) {
			        	//for (int c = 1; c <= numberOfColumns1; c++) {
			        		pName = rs1.getString("ParameterName");	
			        		//System.out.println(pName);
							pValue = rs1.getString("ParameterValue");
							//System.out.println(pValue);
							pIndex = Integer.parseInt(rs1.getString("Index"));
			        		
			        	pKey = pName.toLowerCase()+pIndex+"";
			            //System.out.println("pKey = "+pKey);
			            //System.out.println("pValue = "+pValue);
			            SeleniumDriverTest.parameterDetails.put(pKey, pValue);
			        }
				}
		        rs.close();		        
		        st.close();
		        conn.close();
	        
			} catch (Exception e){
				e.printStackTrace ();
			}
			//System.out.println(Rows.size());
			return Rows;
			
		}
}
