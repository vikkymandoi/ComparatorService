package com.uni.app.main.request.modal;

import java.util.List;

public class TableMetadataReq {
	private List<String> tableNames;
	private String primaryEnv;
	private String secondaryEnv;
	private String primaryTableName;
	private String secondaryTableName;
	private List<String> columnNames;
	
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
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}
	public List<String> getColumnNames() {
		return columnNames;
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
				+ ", columnNames=" + columnNames + "]";
	}
}
