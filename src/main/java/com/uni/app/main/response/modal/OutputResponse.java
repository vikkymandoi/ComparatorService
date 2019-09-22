package com.uni.app.main.response.modal;

import java.util.ArrayList;
import java.util.List;

public class OutputResponse {
	private List<String> outputList = new ArrayList<String>();
	private List<String> errorList = new ArrayList<String>();
	public List<String> getOutputList() {
		return outputList;
	}
	public void setOutput(String msg) {
		outputList.add(msg);
	}
	public List<String> getErrorList() {
		return errorList;
	}
	public void setError(String error) {
		errorList.add(error);
	}
	
	@Override
	public String toString() {
		return "OutputResponse [outputList=" + outputList +
				", errorList=" + errorList + "]";
	}
}
