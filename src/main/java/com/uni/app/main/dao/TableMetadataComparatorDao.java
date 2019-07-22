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
import com.uni.app.main.response.modal.OutputResponse;
import com.uni.app.main.util.ComparatorConstants;
import com.uni.app.main.util.ComparatorUtil;

@Component
@Scope("prototype")
public class TableMetadataComparatorDao {
	private static final Logger logger = LoggerFactory.getLogger(TableMetadataComparatorDao.class);
	@Autowired
	private ConnectionRepository connectionRepository;
	OutputResponse response = new OutputResponse();
	
	public String compareTableColumns(List<String> tableNames, String priEnv, String secEnv) {
	    try {
            for (String schTableName : tableNames) {
            	Map<String, String> schemaTableMap = ComparatorUtil.getSchemaTableMap(schTableName); 
                Map<String, ColumnInfo> primaryColMap = ComparatorUtil.getColumnMetadataMap(connectionRepository.getConnection(priEnv), schemaTableMap);
                Map<String, ColumnInfo> secondaryColMap = ComparatorUtil.getColumnMetadataMap(connectionRepository.getConnection(secEnv), schemaTableMap);
                response.setOutput("Comparing table : "+ schemaTableMap.get(ComparatorConstants.TABLE_NAME));
                compareColumns(primaryColMap, secondaryColMap);
            }
        } catch (Exception e) {
        	response.setError("Exception while Comparing Tables "+tableNames.toString()+"--- ERROR :- "+e.getMessage());
        	logger.error("Exception while Comparing Tables {} -- ERROR -- {}",tableNames.toString(),e);
        }
        return response.toString();
	}
	
	public void compareColumns(Map<String, ColumnInfo> primaryColMap, Map<String, ColumnInfo> secondaryColMap) {
	    try {
	        boolean isEqual = true;
	        for(Map.Entry<String, ColumnInfo> entry : primaryColMap.entrySet()) {
	            String columnName = entry.getKey();
	            ColumnInfo primaryColInfo = entry.getValue();
	            ColumnInfo secondaryColInfo = secondaryColMap.remove(columnName);
	            if(secondaryColInfo == null) {
	            	response.setOutput(ComparatorUtil.createDDLOutput("ALTER", primaryColInfo));
	                isEqual = false;
	                continue;
	            }
	            if(!primaryColInfo.equals(secondaryColInfo)) {
	                isEqual = false;
	                response.setOutput(ComparatorUtil.createDDLOutput("MODIFY", primaryColInfo));
	            }
	        }
	        if(!secondaryColMap.isEmpty()) {
	            isEqual = false;
	            for(Map.Entry<String, ColumnInfo> entry : secondaryColMap.entrySet()) {
	            	response.setOutput(ComparatorUtil.createDDLOutput("DROP", entry.getValue()));
	            }
	        }

	        if(isEqual) {
	        	response.setOutput("--Exact Match");
	        }
	    } catch (Exception e) {
	    	logger.error("Error Comparing Columns {}", e);
	    	response.setOutput("ERROR"+e.getMessage());
	    }
	}
}
