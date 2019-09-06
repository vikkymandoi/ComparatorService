package com.uni.app.main.request.modal;

import java.util.List;

public class TableMetadataReq {
	private List<String> tableNames;
	private String primaryEnv;
	private String secondaryEnv;
	private String primaryTableName;
	private String secondaryTableName;
	private String primaryColName;
	private String secondaryColName;
	
	public String getPrimaryTableName() {
		return primaryTableName;
	}
	public void setPrimaryTableName(String primaryTableName) {
		this.primaryTableName = primaryTableName;
	}
	public String getSecondaryTableName() {
		return secondaryTableName;
	}
	public void setSecondaryTableName(String secondaryTableName) {
		this.secondaryTableName = secondaryTableName;
	}
	public String getPrimaryColName() {
		return primaryColName;
	}
	public void setPrimaryColName(String primaryColName) {
		this.primaryColName = primaryColName;
	}
	public String getSecondaryColName() {
		return secondaryColName;
	}
	public void setSecondaryColName(String secondaryColName) {
		this.secondaryColName = secondaryColName;
	}
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
		return "TableMetadataReq [tableNames=" + tableNames + ", primaryEnv=" + primaryEnv + ", secondaryEnv="
				+ secondaryEnv + ", primaryTableName=" + primaryTableName + ", secondaryTableName=" + secondaryTableName
				+ ", primaryColName=" + primaryColName + ", secondaryColName=" + secondaryColName + "]";
	}
}
