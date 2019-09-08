package com.uni.app.main.response.modal;

import java.util.ArrayList;
import java.util.List;

public class OutputResponse {
	private List<String> outputList = new ArrayList<String>();
	private List<String> invalidList = new ArrayList<String>();
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
	public void setInvalidList(String invalidItem) {
		invalidList.add(invalidItem);
	}
	public List<String> getInvalidList() {
		return invalidList;
	}
	@Override
	public String toString() {
		return "OutputResponse [outputList=" + outputList + ", invalidList=" + invalidList + ", errorList=" + errorList
				+ "]";
	}
}
