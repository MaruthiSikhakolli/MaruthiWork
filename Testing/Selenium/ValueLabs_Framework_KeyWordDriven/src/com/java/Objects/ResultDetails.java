package com.java.Objects;

public class ResultDetails {
	private boolean flag;
	private String errorMessage;
	private String warningMessage;
	/**
	 * @return Returns the flag.
	 */
	public boolean getFlag() {
		return flag;
	}
	/**
	 * @param flag The flag to set.
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	/**
	 * @return Returns the errorMessage.
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @return Returns the warningMessage.
	 */
	public String getWarningMessage() {
		return warningMessage;
	}
	/**
	 * @param errorMessage The errorMessage to set.
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	/**
	 * @param warningMessage The warningMessage to set.
	 */
	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}
}
