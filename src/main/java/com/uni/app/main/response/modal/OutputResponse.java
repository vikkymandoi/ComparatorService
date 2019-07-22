package com.uni.app.main.response.modal;

import java.util.ArrayList;
import java.util.List;

import com.uni.app.main.util.ComparatorUtil;

public class OutputResponse {
	private List<String> outputList = new ArrayList<String>();
	private List<String> errorList = new ArrayList<String>();
	public void setOutput(String msg) {
		outputList.add(msg);
	}
	public void setError(String error) {
		errorList.add(error);
	}
	@Override
	public String toString() {
		return "OUTPUT:\n" 
				+ ComparatorUtil.getStringfromList(outputList)
				+ "\n\n" 
				+ "ERRORS:\n" 
				+ ComparatorUtil.getStringfromList(errorList);
	}
}
