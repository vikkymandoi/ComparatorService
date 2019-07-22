package com.uni.app.main.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uni.app.main.modal.ColumnInfo;
import com.uni.app.main.util.ComparatorConstants;
import com.uni.app.main.util.ComparatorUtil;

@RestController
@EnableAutoConfiguration
public class ComparatorController {
	@RequestMapping("/")
	public String homeRequest() {
		return "This is Comparator Controller..!";
	}
	
	public boolean isComparatorRunning  = false;
	public void compareTableColumns(List<String> tableNames) {
	    if(!isComparatorRunning) {
	        isComparatorRunning = true;
	        try {
	            for (String schTableName : tableNames) {
	                Map<String, String> schemaTableMap = ComparatorUtil.getSchemaTableMap(schTableName); 
	                //Map<String, ColumnInfo> primaryColMap = ComparatorUtil.getColumnMetadataMap(DbConnectionRepository.getConnectionOne(), schemaTableMap);
	                //Map<String, ColumnInfo> secondaryColMap = ComparatorUtil.getColumnMetadataMap(DbConnectionRepository.getConnectionTwo(), schemaTableMap);
	                //ComparatorUtil.publishColumnInfoOutput("Comparing table : "+ schemaTableMap.get(ComparatorConstants.TABLE_NAME));
	                //compareColumns(primaryColMap, secondaryColMap);
	            }
	        } catch (Exception e) {
	            //ComparatorUtil.publishColumnInfoOutput("ERROR"+e.getMessage());
	        }
	        isComparatorRunning = false;
	    }
	}
	
	public void compareColumns(Map<String, ColumnInfo> primaryColMap, Map<String, ColumnInfo> secondaryColMap) {
	    try {
	        boolean isEqual = true;
	        for(Map.Entry<String, ColumnInfo> entry : primaryColMap.entrySet()) {
	            String columnName = entry.getKey();
	            ColumnInfo primaryColInfo = entry.getValue();
	            ColumnInfo secondaryColInfo = secondaryColMap.remove(columnName);
	            if(secondaryColInfo == null) {
	                // column is not present in Secondary Environment
	                ComparatorUtil.publishColumnInfoOutput("ALTER", primaryColInfo);
	                isEqual = false;
	                continue;
	            }
	            if(!primaryColInfo.equals(secondaryColInfo)) {
	                isEqual = false;
	                // Column not equal in secondary env
	                ComparatorUtil.publishColumnInfoOutput("MODIFY", primaryColInfo);
	            }
	        }
	        if(!secondaryColMap.isEmpty()) {
	            isEqual = false;
	            for(Map.Entry<String, ColumnInfo> entry : secondaryColMap.entrySet()) {
	                // column is not present in Primary Environment
	                ComparatorUtil.publishColumnInfoOutput("DROP", entry.getValue());
	            }
	        }

	        if(isEqual) {
	            //ComparatorUtil.publishColumnInfoOutput("--Exact Match");
	        }
	    } catch (Exception e) {
	        //ComparatorUtil.publishColumnInfoOutput("ERROR"+e.getMessage());
	    }
	}
}
