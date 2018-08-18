package com.java.Objects;

import java.util.ArrayList;

/**
 * This is to store each row details from Configuration Details Excel Sheet 
 */
public class ConfigDetails {
	private ArrayList<Integer> testCasesToBeExecuted;
	private String scriptPath;
	private String testDataDetailsFilePath;
	private String testResultsFolderPath;
	private String browser;
	private String databaseDetails;
		
	/**
	 * @return Returns the browser.
	 */
	public String getBrowser() {
		return browser;
	}
	/**
	 * @param browser The browser to set.
	 */
	public void setBrowser(String browser) {
		if(System.getenv("Browser")==null)
		{
			this.browser=browser;
		}
		else
		{
			this.browser = System.getenv("Browser");
		}
	}
	
	/**
	 * @return Returns the testCasesToBeExecuted.
	 */
	public ArrayList<Integer> getTestCasesToBeExecuted() {
		return testCasesToBeExecuted;
	}
	/**
	 * @param testCasesToBeExecuted The testCasesToBeExecuted to set.
	 */
	public void setTestCasesToBeExecuted(ArrayList<Integer> testCasesToBeExecuted) {
		this.testCasesToBeExecuted = testCasesToBeExecuted;
	}
	/**
	 * @return Returns the testDataDetailsFilePath.
	 */
	public String getTestDataDetailsFilePath() {
		return testDataDetailsFilePath;
	}
	/**
	 * @param testDataDetailsFilePath The testDataDetailsFilePath to set.
	 */
	public void setTestDataDetailsFilePath(String testDataDetailsFilePath) {
		this.testDataDetailsFilePath = testDataDetailsFilePath;
	}
	
	/**
	 * @return Returns the scriptPath.
	 */
	public String getScriptPath() {
		return scriptPath;
	}
	
	
	/**
	 * @param scriptPath The scriptPath to set.
	 */
	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}
	
	
	public String getTestResultsFolderPath() {
		return testResultsFolderPath;
	}
	public void setTestResultsFolderPath(String testResultsFolderPath) {
		this.testResultsFolderPath = testResultsFolderPath;
	 	
	}
	public String getDatabaseDetails() {
		return databaseDetails;
	}
	public void setDatabaseDetails(String databaseDetails) {
		this.databaseDetails = databaseDetails;	 	
	}
	
}
