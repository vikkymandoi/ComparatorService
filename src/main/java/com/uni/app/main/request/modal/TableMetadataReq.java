package com.uni.app.main.request.modal;

import java.util.List;

public class TableMetadataReq {
	private List<String> tableNames;
	private String primaryEnv;
	private String secondaryEnv;
	public List<String> getTableNames() {
		return tableNames;
	}
	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
	}
	public String getPrimaryEnv() {
		return primaryEnv;
	}
	public void setPrimaryEnv(String primaryEnv) {
		this.primaryEnv = primaryEnv;
	}
	public String getSecondaryEnv() {
		return secondaryEnv;
	}
	public void setSecondaryEnv(String secondaryEnv) {
		this.secondaryEnv = secondaryEnv;
	}
	@Override
	public String toString() {
		return "TableMetaDataModal [tableNames=" + tableNames + ", primaryEnv=" + primaryEnv + ", secondaryEnv="
				+ secondaryEnv + "]";
	}
}
