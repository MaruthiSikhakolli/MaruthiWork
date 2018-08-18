package com.java.Objects;

/**
 * This is to store each row details form Test Data Details Excel Sheet
 */
public class TestDataDetails {
	private int testCaseID;
	private int testDataID;
	private String workingPage;
	private String dataFields;
	private String dataValues;
	private String actionType;
	private String expectedResult;
	private String expectedDataFields;
	private String expectedDataValues;
	private String testType;
	private String testCaseTitle;
	private String browserType;
	private String fieldName;
	
	private String parameterName;
	private String parameterValue;
	private int parameterIndex;	
	
	private String conditionType;
	/**
	 * @return Returns the dataFields.
	 */
	public String getDataFields() {
		return dataFields;
	}
	/**
	 * @param dataFields The dataFields to set.
	 */
	public void setDataFields(String dataFields) {
		//System.out.println(dataFields);
		this.dataFields = dataFields;
	}
	/**
	 * @return Returns the dataValues.
	 */
	public String getDataValues() {
		return dataValues;
	}
	/**
	 * @param dataValues The dataValues to set.
	 */
	public void setDataValues(String dataValues) {
		//System.out.println(dataValues);
		this.dataValues = dataValues;
	}
	/**
	 * @return Returns the testCaseID.
	 */
	public int getTestCaseID() {
		return testCaseID;
	}
	/**
	 * @param testCaseID The testCaseID to set.
	 */
	public void setTestCaseID(int testCaseID) {
		//System.out.println(testCaseID);
		this.testCaseID = testCaseID;
	}
	/**
	 * @return Returns the testDataID.
	 */
	public int getTestDataID() {
		return testDataID;
	}
	/**
	 * @param testDataID The testDataID to set.
	 */
	public void setTestDataID(int testDataID) {
		//System.out.println(testDataID);
		this.testDataID = testDataID;
	}
	/**
	 * @return Returns the workingPage.
	 */
	public String getWorkingPage() {
		return workingPage;
	}
	/**
	 * @param workingPage The workingPage to set.
	 */
	public void setWorkingPage(String workingPage) {
		this.workingPage = workingPage;
	}
	
	/**
	 * @param conditionType the conditionType to set
	 */
	public void setCondition(String conditionType) {
		this.conditionType = conditionType;
	}
	/**
	 * @param conditionType the conditionType to get
	 */
	public String getCondition() {
		return conditionType;
	}
	/**
	 * @return Returns the expectedDataFields.
	 */
	public String getExpectedDataFields() {
		return expectedDataFields;
	}
	/**
	 * @param expectedDataFields The expectedDataFields to set.
	 */
	public void setExpectedDataFields(String expectedDataFields) {
		this.expectedDataFields = expectedDataFields;
	}
	/**
	 * @return Returns the expectedDataValues.
	 */
	public String getExpectedDataValues() {
		return expectedDataValues;
	}
	/**
	 * @return the expectedResult
	 */
	public String getActionType() {
		return actionType;
	}
	/**
	 * @param expectedResult the expectedResult to set
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	/**
	 * @param expectedDataValues The expectedDataValues to set.
	 */
	public void setExpectedDataValues(String expectedDataValues) {
		this.expectedDataValues = expectedDataValues;
	}
	/**
	 * @return the expectedResult
	 */
	public String getExpectedResult() {
		return expectedResult;
	}
	/**
	 * @param expectedResult the expectedResult to set
	 */
	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}
	/**
	 * @return Returns the testType.
	 */
	public String getTestType() {
		return testType;
	}
	/**
	 * @param testType The testType to set.
	 */
	public void setTestType(String testType) {
		this.testType = testType;
	}
	/**
	 * @param testCaseTitle The testCaseTitle to set.
	 */
	public String getTestCaseTitle() {
		return testCaseTitle;
	}
	public void setTestCaseTitle(String testCaseTitle) {
		this.testCaseTitle = testCaseTitle;
	}
	/**
	 * @return Returns the browserType.
	 */
	public String getBrowserType() {
		return browserType;
	}
	/**
	 * @param browserType The browserType to set.
	 */
	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}
	/**
	 * @return Returns the fieldName.
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * @param browserType The fieldName to set.
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * @return Returns the parameterName.
	 */
	public String getParameterName() {
		return parameterName;
	}
	/**
	 * @param paramterName The parameterName to set.
	 */
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	/**
	 * @return Returns the parameterValue.
	 */
	public String getParameterValue() {
		return parameterValue;
	}
	/**
	 * @param paramtervalue The paramterValue to set.
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	/**
	 * @return Returns the index.
	 */
	public int getParameterIndex() {
		return parameterIndex;
	}
	/**
	 * @param index The paramterIndex to set.
	 */
	public void setParameterIndex(int parameterIndex) {
		this.parameterIndex = parameterIndex;
	}	
}
