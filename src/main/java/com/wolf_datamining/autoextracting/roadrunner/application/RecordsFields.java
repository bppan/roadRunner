package com.wolf_datamining.autoextracting.roadrunner.application;

public class RecordsFields {

	public int recordsColumn;
	public String fileName;
	public String headTableName;

	public String getHeadTableName() {
		return headTableName;
	}

	public void setHeadTableName(String headTableName) {
		this.headTableName = headTableName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getRecordsColumn() {
		return recordsColumn;
	}

	public void setRecordsColumn(int recordsColumn) {
		this.recordsColumn = recordsColumn;
	}	
}
