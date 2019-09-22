package com.uni.app.main.modal;

public class ColumnInfo {
	private String schemaName;
	private String tableName;
	private String columnName;
	private String datatype;
	private String columnsize;
	private String decimaldigits;
	private String isNullable;
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getColumnsize() {
		return columnsize;
	}
	public void setColumnsize(String columnsize) {
		this.columnsize = columnsize;
	}
	public String getDecimaldigits() {
		return decimaldigits;
	}
	public void setDecimaldigits(String decimaldigits) {
		this.decimaldigits = decimaldigits;
	}
	public String getIsNullable() {
		return isNullable;
	}
	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}
	@Override
	public String toString() {
		return "ColumnInfo [schemaName=" + schemaName + ", tableName=" + tableName + ", columnName=" + columnName
				+ ", datatype=" + datatype + ", columnsize=" + columnsize + ", decimaldigits=" + decimaldigits
				+ ", isNullable=" + isNullable + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result + ((columnsize == null) ? 0 : columnsize.hashCode());
		result = prime * result + ((datatype == null) ? 0 : datatype.hashCode());
		result = prime * result + ((decimaldigits == null) ? 0 : decimaldigits.hashCode());
		result = prime * result + ((isNullable == null) ? 0 : isNullable.hashCode());
		result = prime * result + ((schemaName == null) ? 0 : schemaName.hashCode());
		result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColumnInfo other = (ColumnInfo) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (columnsize == null) {
			if (other.columnsize != null)
				return false;
		} else if (!columnsize.equals(other.columnsize))
			return false;
		if (datatype == null) {
			if (other.datatype != null)
				return false;
		} else if (!datatype.equals(other.datatype))
			return false;
		if (decimaldigits == null) {
			if (other.decimaldigits != null)
				return false;
		} else if (!decimaldigits.equals(other.decimaldigits))
			return false;
		if (isNullable == null) {
			if (other.isNullable != null)
				return false;
		} else if (!isNullable.equals(other.isNullable))
			return false;
		return true;
	}
}
