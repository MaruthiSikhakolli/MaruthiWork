package com.java;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class GetTokens {
	
	private static Logger log = Logger.getLogger(GetTokens.class.getName());
	
	
	/**
	 * This function is used to divide the string into tokens and add to an ArrayList
	 * @param data,delimiter
	 * @return ArrayList
	 */
	public ArrayList<String> dataValuesTokens(String data,String delimiter){
		ArrayList<String> DataValuesTokens = new ArrayList<String>();
		log.debug("String Tokenizer...."+data+"....");
		StringTokenizer st = new StringTokenizer (data);
		while(st.hasMoreElements()){
			DataValuesTokens.add(st.nextToken(delimiter));
		}
		return DataValuesTokens;
	}
}
