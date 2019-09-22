package com.uni.app.main.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.uni.app.main.modal.ColumnInfo;
import com.uni.app.main.repository.ConnectionRepository;
import com.uni.app.main.request.modal.TableMetadataReq;
import com.uni.app.main.response.modal.OutputResponse;
import com.uni.app.main.util.ComparatorConstants;
import com.uni.app.main.util.ComparatorUtil;

@Component
@Scope("prototype")
public class TableMetadataComparatorDao {
	private static final Logger logger = LoggerFactory.getLogger(TableMetadataComparatorDao.class);
	@Autowired
	private ConnectionRepository connectionRepository;
	@Autowired
	private ComparatorUtil comparatorUtil;
	private OutputResponse response = new OutputResponse();

	public OutputResponse compareTableMeta(TableMetadataReq tableMetadata, String primaryEnv, String secondaryEnv) {
		List<String> tablenames = tableMetadata.getTableNames();
		if (tablenames != null && !tablenames.isEmpty()) {
			return compareTables(tableMetadata.getTableNames(), tableMetadata.getPrimaryEnv(),
					tableMetadata.getSecondaryEnv());
		} else {
			return compareTableColumns(tableMetadata, tableMetadata.getPrimaryEnv(), tableMetadata.getSecondaryEnv());
		}
	}

	public OutputResponse compareTables(List<String> tableNames, String priEnv, String secEnv) {
		try {
			for (String schTableName : tableNames) {
				Map<String, String> schemaTableMap = comparatorUtil.getSchemaTableMap(schTableName);
				Map<String, ColumnInfo> primaryColMap = comparatorUtil.getColumnMetadataMap(connectionRepository.getConnection(priEnv), schemaTableMap, null);
				Map<String, ColumnInfo> secondaryColMap = comparatorUtil.getColumnMetadataMap(connectionRepository.getConnection(secEnv), schemaTableMap, null);
				
				response.setOutput("Comparision Result for Table : " + schemaTableMap.get(ComparatorConstants.TABLE_NAME));
				compareColumns(primaryColMap, secondaryColMap);
			}
		} catch (Exception e) {
			response.setError("Exception while Comparing Tables " + tableNames.toString() + "--- ERROR :- " + e.getMessage());
			logger.error("Exception while Comparing Tables {} -- ERROR -- {}", tableNames.toString(), e);
		}
		return response;
	}

	public OutputResponse compareTableColumns(TableMetadataReq tableMetadata, String priEnv, String secEnv) {
		try {
			Map<String, String> primaryTableSchemaMap = comparatorUtil.getSchemaTableMap(tableMetadata.getPrimaryTableName());
			Map<String, String> secondaryTableSchemaMap = comparatorUtil.getSchemaTableMap(tableMetadata.getSecondaryTableName());
			
			List<String> tableColumnsNames = tableMetadata.getColumnNames();
			Map<String, ColumnInfo> primaryColMap = comparatorUtil.getColumnMetadataMap(connectionRepository.getConnection(priEnv), primaryTableSchemaMap, tableColumnsNames);
			Map<String, ColumnInfo> secondaryColMap = comparatorUtil.getColumnMetadataMap(connectionRepository.getConnection(secEnv), secondaryTableSchemaMap, tableColumnsNames);

			if (tableColumnsNames != null && !"*".equalsIgnoreCase(tableColumnsNames.get(0))) {
				for (String columnName : tableColumnsNames) {
					if (!primaryColMap.containsKey(columnName)) {
						response.setError("INVALID COLUMN: " + columnName + " Column Name not available in Primary Db");
					}
					if (!secondaryColMap.containsKey(columnName)) {
						response.setError("INVALID COLUMN: " + columnName + " Column Name not available in Secondary Db");
					}
				}
			}
			compareColumns(primaryColMap, secondaryColMap);
		} catch (Exception e) {
			response.setError("EXCEPTION while Comparing Primary Secondary Tables  ERROR :- " + e.getMessage());
			logger.error("Exception while Comparing Primary Secondary Tables -- ERROR -- {}", e);
		}
		return response;
	}

	public void compareColumns(Map<String, ColumnInfo> primaryColMap, Map<String, ColumnInfo> secondaryColMap) {
		try {
			boolean isEqual = true;
			for (Map.Entry<String, ColumnInfo> entry : primaryColMap.entrySet()) {
				String columnName = entry.getKey();
				ColumnInfo primaryColInfo = entry.getValue();
				ColumnInfo secondaryColInfo = secondaryColMap.remove(columnName);
				if (secondaryColInfo == null) {
					response.setOutput(comparatorUtil.createDDLOutput("ADD", primaryColInfo));
					isEqual = false;
					continue;
				}
				if (!primaryColInfo.equals(secondaryColInfo)) {
					isEqual = false;
					response.setOutput(comparatorUtil.createDDLOutput("MODIFY", primaryColInfo));
				}
			}
			if (!secondaryColMap.isEmpty()) {
				isEqual = false;
				for (Map.Entry<String, ColumnInfo> entry : secondaryColMap.entrySet()) {
					response.setOutput(comparatorUtil.createDDLOutput("DROP", entry.getValue()));
				}
			}

			if (isEqual) {
				response.setOutput("--Exact Match");
			}
		} catch (Exception e) {
			logger.error("Error Comparing Columns {}", e);
			response.setOutput("ERROR" + e.getMessage());
		}
	}
}
