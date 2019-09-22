package com.uni.app.main.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.uni.app.main.modal.ColumnInfo;

public class ComparatorUtil {
	public static Map<String, String> getSchemaTableMap(String tableNameWithSchema) {
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
	
	public static String getStringfromList(List<String> list) {
		if(list == null || list.isEmpty())
			return null;
		return list.toString().replaceAll(",", "\n").replaceAll("\\[|\\]| ", "");
	}
	
	public static synchronized Map<String, ColumnInfo> getColumnMetadataMap(Connection conn, Map<String, String> schemaTableMap) {
		try {
			String schemaName = schemaTableMap.get(ComparatorConstants.SCHEMA_NAME);
			String tableName = schemaTableMap.get(ComparatorConstants.TABLE_NAME);
			ResultSet resultSetConnOne = conn.getMetaData().getColumns(null, schemaName, tableName, null);
			Map<String, ColumnInfo> resultSetTwoColInfo = getColumnInfo(schemaName, tableName, resultSetConnOne);
			return resultSetTwoColInfo;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Number Type mapping 12-----VARCHAR 3-----DECIMAL 93-----TIMESTAMP
	 * 1111-----OTHER
	 */
	public static Map<String, ColumnInfo> getColumnInfo(String schemaName, String tableName, ResultSet columns) {
		try {
			Map<String, ColumnInfo> tableColumnInfo = new LinkedHashMap<String, ColumnInfo>();
			while (columns.next()) {
				ColumnInfo columnInfo = new ColumnInfo();
				columnInfo.setSchemaName(schemaName);
				columnInfo.setTableName(tableName);
				columnInfo.setColumnName(columns.getString("COLUMN_NAME"));
				columnInfo.setDatatype(columns.getString("DATA_TYPE"));
				columnInfo.setColumnsize(columns.getString("COLUMN_SIZE"));
				columnInfo.setDecimaldigits(columns.getString("DECIMAL_DIGITS"));
				columnInfo.setIsNullable(columns.getString("IS_NULLABLE"));
				tableColumnInfo.put(columnInfo.getColumnName(), columnInfo);
			}
			return tableColumnInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null)
			return true;
		if (String.valueOf(obj).equalsIgnoreCase("NULL"))
			return true;
		if (obj.toString().trim().length() == 0)
			return true;
		return false;
	}

	public static boolean isNullorEmpty(String str) {
		if (str == null)
			return true;
		if (str.trim().length() == 0)
			return true;
		return false;
	}

	public static String createDDLOutput(String type, ColumnInfo columnInfo) {
		String str = "ALTER TABLE " + columnInfo.getSchemaName() + "." + columnInfo.getTableName();
		switch (type.toUpperCase()) {
		case "ALTER":
			if ("NUMBER".equalsIgnoreCase(columnInfo.getDatatype())
					|| "DATE".equalsIgnoreCase(columnInfo.getDatatype())) {
				str += " ADD (" + columnInfo.getColumnName() + " " + columnInfo.getDatatype() + ");";
			} else {
				str += " ADD (" + columnInfo.getColumnName() + " " + columnInfo.getDatatype() + "("
						+ columnInfo.getColumnsize() + "));";
			}
			break;
		case "DROP":
			str += " DROP (" + columnInfo.getColumnName() + ");";
			break;
		case "MODIFY":
			if ("NUMBER".equalsIgnoreCase(columnInfo.getDatatype())
					|| "DATE".equalsIgnoreCase(columnInfo.getDatatype())) {
				str += " MODIFY (" + columnInfo.getColumnName() + " " + columnInfo.getDatatype() + ");";
			} else {
				str += " MODIFY (" + columnInfo.getColumnName() + " " + columnInfo.getDatatype() + "("
						+ columnInfo.getColumnsize() + "));";
			}
			break;
		}
		return str;
	}

	public static Map<Integer, String> allJdbcTypeName = null;

	public static Map<Integer, String> getAllJdbcTypeNames() {
		Map<Integer, String> result = new HashMap<Integer, String>();
		if (allJdbcTypeName != null)
			return allJdbcTypeName;
		try {
			for (Field field : java.sql.Types.class.getFields()) {
				result.put((Integer) field.get(null), field.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allJdbcTypeName = result;
	}

	public static String getStringPlaces(String[] attribs) {
		String params = "";
		for (int i = 0; i < attribs.length; i++) {
			params += "?,";
		}
		return params.substring(0, params.length() - 1);
	}
}
