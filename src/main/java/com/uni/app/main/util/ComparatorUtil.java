package com.uni.app.main.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.uni.app.main.dao.TableMetadataComparatorDao;
import com.uni.app.main.modal.ColumnInfo;

@Component
@Scope("prototype")
public class ComparatorUtil {
	private static final Logger logger = LoggerFactory.getLogger(TableMetadataComparatorDao.class);
	
	public Map<String, String> getSchemaTableMap(String tableNameWithSchema) {
		if (isNullorEmpty(tableNameWithSchema)) {
			return null;
		}
		Map<String, String> result = new LinkedHashMap<>();
		int index = tableNameWithSchema.indexOf(".");
		String schemaName = tableNameWithSchema.substring(0, index);
		String tableName = tableNameWithSchema.substring(index + 1);
		result.put(ComparatorConstants.SCHEMA_NAME, schemaName);
		result.put(ComparatorConstants.TABLE_NAME, tableName);
		return result;
	}
	
	public String getStringfromList(List<String> list) {
		if(list == null || list.isEmpty())
			return null;
		return list.toString().replaceAll(",", "\n").replaceAll("\\[|\\]| ", "");
	}
	
	public Map<String, ColumnInfo> getColumnMetadataMap(Connection conn, Map<String, String> schemaTableMap, List<String> tableColumnsNames) {
		try {
			String schemaName = schemaTableMap.get(ComparatorConstants.SCHEMA_NAME);
			String tableName = schemaTableMap.get(ComparatorConstants.TABLE_NAME);
			ResultSet columnsMetaData = conn.getMetaData().getColumns(null, schemaName, tableName, null);
			Map<String, ColumnInfo> resultSetTwoColInfo = getColumnInfo(schemaName, tableName, columnsMetaData, tableColumnsNames);
			return resultSetTwoColInfo;
		} catch (SQLException e) {
			logger.error("Error While getting column metadata map {}", e);
		}
		return null;
	}

	/*
	 * Number Type mapping 12-----VARCHAR 3-----DECIMAL 93-----TIMESTAMP
	 * 1111-----OTHER
	 */
	public Map<String, ColumnInfo> getColumnInfo(String schemaName, String tableName, ResultSet columns, List<String> tableColumnsNames) {
		try {
			Map<String, ColumnInfo> tableColumnInfo = new LinkedHashMap<String, ColumnInfo>();
			while (columns.next()) {
				ColumnInfo columnInfo = new ColumnInfo();
				columnInfo.setSchemaName(schemaName);
				columnInfo.setTableName(tableName);
				String columnName = columns.getString("COLUMN_NAME");
				if(tableColumnsNames != null && !tableColumnsNames.contains(columnName)) {
					continue;
				}
				columnInfo.setColumnName(columnName);
				columnInfo.setDatatype(columns.getString("TYPE_NAME"));
				columnInfo.setColumnsize(columns.getString("COLUMN_SIZE"));
				columnInfo.setDecimaldigits(columns.getString("DECIMAL_DIGITS"));
				columnInfo.setIsNullable(columns.getString("IS_NULLABLE"));
				tableColumnInfo.put(columnInfo.getColumnName(), columnInfo);
			}
			return tableColumnInfo;
		} catch (Exception e) {
			logger.error("Error While Mapping column Info Data {}", e);
		}
		return null;
	}

	public boolean isNullOrEmpty(Object obj) {
		if (obj == null)
			return true;
		if (String.valueOf(obj).equalsIgnoreCase("NULL"))
			return true;
		if (obj.toString().trim().length() == 0)
			return true;
		return false;
	}

	public boolean isNullorEmpty(String str) {
		if (str == null)
			return true;
		if (str.trim().length() == 0)
			return true;
		return false;
	}

	public String createDDLOutput(String type, ColumnInfo columnInfo) {
		String str = "ALTER TABLE " + columnInfo.getSchemaName() + "." + columnInfo.getTableName();
		switch (type.toUpperCase()) {
			case "ADD":
				str += " ADD (" + columnInfo.getColumnName() + " " + columnInfo.getDatatype() + " DATATYPE_SIZE NULLABLE_TYPE);";
				break;
			case "DROP":
				str += " DROP (" + columnInfo.getColumnName() + ");";
				break;
			case "MODIFY":
				str += " MODIFY (" + columnInfo.getColumnName() + " " + columnInfo.getDatatype() + " :DATATYPE_SIZE NULLABLE_TYPE);";
				break;
		}
		System.out.println(columnInfo.toString());
		
		// Adding DataType Size
		if(columnInfo.getColumnsize() != null && columnInfo.getColumnsize().trim().length() != 0 ) {	
			str = str.replaceAll("DATATYPE_SIZE", "(" +columnInfo.getColumnsize() +")") ; 
		} else { 
			str = str.replaceAll("DATATYPE_SIZE", ""); 
		}
		
		// Adding DataType Size
		if(columnInfo.getIsNullable() != null && "YES".equalsIgnoreCase(columnInfo.getIsNullable()) ) {	
			str = str.replaceAll("NULLABLE_TYPE", "") ; 
		} else { 
			str = str.replaceAll("NULLABLE_TYPE", "NOT NULL"); 
		}
		return str;
	}
	
	public String getStringPlaces(String[] attribs) {
		String params = "";
		for (int i = 0; i < attribs.length; i++) {
			params += "?,";
		}
		return params.substring(0, params.length() - 1);
	}
}
