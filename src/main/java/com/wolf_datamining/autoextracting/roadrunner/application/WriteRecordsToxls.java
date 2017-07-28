package com.wolf_datamining.autoextracting.roadrunner.application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public  class WriteRecordsToxls {
	public 	OutputStream outstream;
	public WriteRecordsToxls(){
		outstream = null;
	}
	public void writeRecordsToxls(List<HeadTable> headTable,String path){
		int layer = getHeadTableLayer(headTable);
		try {
			outstream = new FileOutputStream(path);
//			System.out.println("begin======" +headTable.size());
			try {
				WritableWorkbook  workbook = Workbook.createWorkbook(outstream);
				WritableSheet sheet = workbook.createSheet("First Sheet Records",0);
				for(int i = 0; i < headTable.size(); i++){
					Label head = new Label(i,0,headTable.get(i).headString);
			        try {
						sheet.addCell(head);
					} catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		        
				}
				for(int i = 0; i < layer; i++){
					for(int j = 0; j < headTable.size();j++){
						if(headTable.get(j).getListString(i + 1)  != null){				
							Label head = new Label(j,i + 1,headTable.get(j).getListString(i + 1));
					        try {
								sheet.addCell(head);
							} catch (RowsExceededException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (WriteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
						}
					}
				}
			     workbook.write();
			     workbook.close();
			     outstream.close();
			} catch (IOException | WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getHeadTableLayer(List<HeadTable> headTable){
		int max = 1;
		for(int i = 0; i < headTable.size();i++){
			for(int j= 0; j < headTable.get(i).listString.size(); j++){
				if(headTable.get(i).listString.get(j).layerNumber >= max){
					max = headTable.get(i).listString.get(j).layerNumber;
				}
			}
		}
		return max;
	}
}
