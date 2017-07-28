package com.wolf_datamining.autoextracting.roadrunner.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

public class AlignRecords {

	// 对齐数据记录
	// arg1: 输入使用包装器抽取的资源文件路径，
	public void generateRecordsFileds(String filepath){
		File resFile = new File(filepath);
		File templateFile;
		if(!resFile.exists()){
			System.err.println(filepath);
			System.err.println("文件夹不存在");
			return;
		}else{
			File [] resFiles = resFile.listFiles();
			if(resFiles.length == 0){
				System.err.println("文件夹为空");
				return;
			}else{
				long  maxsize = 0;
				int index = -1;
				for(int i = 0; i < resFiles.length; i++){
					if(resFiles[i].getName().endsWith(".xml")){
						if(resFiles[i].length() > maxsize){
							maxsize = resFiles[i].length();
							index = i;
						}
					}
				}
				templateFile=  resFiles[index];
			}
		}
		if(templateFile != null){
			try{
				AlignEngine  engine = new AlignEngine();
				engine.outputAlignedRecordsPath = filepath;
				engine.alignCore(templateFile, true,"");
				RecordsFields records_fields = new RecordsFields();
				records_fields.setFileName(templateFile.getAbsolutePath());
				records_fields.setRecordsColumn(engine.orignRecordsColumn);
				records_fields.setHeadTableName(engine.headTable.get(engine.recordsColumn).headString);				
				//test outoput
				System.out.println(records_fields.getHeadTableName());
				write_jsonRecordsFields(records_fields, filepath);
			}catch(Exception e){
				System.err.println("产生对齐数据记录模板失败！");
				e.printStackTrace();
			}
			
		}
	}
	public boolean write_jsonRecordsFields(RecordsFields records_fields, String filePath){	
	    JSONObject jsonObject = JSONObject.fromObject(records_fields);  
		OutputStream outstream = null;
		try {
			outstream = new FileOutputStream(filePath + "\\Records00.json");
			OutputStreamWriter writer = new OutputStreamWriter(outstream,"utf-8");
			writer.write(jsonObject.toString());
			writer.close();
		} catch (FileNotFoundException e) {		
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("Write Records Fileds Success!  " + filePath);
		return true;
	}
	
	public boolean alignAllRecords(String recordsFieldsFilePath, String inputResoucesPath, String outputAlignRecordsPath){
		JSONObject loadRecords = loadRecordsFields(recordsFieldsFilePath);
		if(loadRecords != null){
			File resFile = new File(inputResoucesPath);
			if(!resFile.exists()){
				System.err.println(inputResoucesPath);
				System.err.println("资源文件夹不存在");
				return false;
			}
			File outputresFile = new File(outputAlignRecordsPath);
			if(!outputresFile.exists()){
				outputresFile.mkdir();
			}
			File [] resFiles = resFile.listFiles();
			if(resFiles.length == 0){
				System.err.println("文件夹为空");
				return false;
			}
			int failuecount = 0;
			String recordsHeadTableString = loadRecords.getString("headTableName");
	
			for(int i = 0; i < resFiles.length; i++){
				if(!resFiles[i].getName().endsWith(".xml")){
					continue;
				}
				try{
					AlignEngine  engine = new AlignEngine();					
					engine.outputAlignedRecordsPath = outputAlignRecordsPath;
//					loadRecords.ge
					engine.alignCore(resFiles[i], true, recordsHeadTableString);				
//					engine.alignCore(resFiles[i], true);					
				}catch(Exception e){
					System.err.println("A Resouces file is Wrong: " + resFiles[i].getAbsolutePath());
					e.printStackTrace();
					failuecount++;
				}
			}
			System.out.println("------------------------------");
			System.out.println("Align Records Files Number: " +  resFiles.length);
			System.out.println("Success Files Number: " +  (resFiles.length - failuecount));
			System.out.println("Failue Files Number: " +  (failuecount));
			System.out.println("Success Ratio: " +  ((resFiles.length - failuecount)*1.0/ resFiles.length)*100.0 +"%");
			return true;
		}		
		return false;
	}
	
	public boolean alignAllRecords(String inputResoucesPath, String outputAlignRecordsPath){
			File resFile = new File(inputResoucesPath);
			if(!resFile.exists()){
				System.err.println(inputResoucesPath);
				System.err.println("资源文件夹不存在");
				return false;
			}
			File outputresFile = new File(outputAlignRecordsPath);
			if(!outputresFile.exists()){
				outputresFile.mkdir();
			}
			File [] resFiles = resFile.listFiles();
			if(resFiles.length == 0){
				System.err.println("文件夹为空");
				return false;
			}
			int failuecount = 0;	
			for(int i = 0; i < resFiles.length; i++){
				if(!resFiles[i].getName().endsWith(".xml")){
					continue;
				}
				try{
					AlignEngine  engine = new AlignEngine();					
					engine.outputAlignedRecordsPath = outputAlignRecordsPath;
//					loadRecords.ge
					engine.alignCore(resFiles[i], true, "");				
//					engine.alignCore(resFiles[i], true);					
				}catch(Exception e){
					System.err.println("A Resouces file is Wrong: " + resFiles[i].getAbsolutePath());
					e.printStackTrace();
					failuecount++;
				}
			}
			System.out.println("------------------------------");
			System.out.println("Align Records Files Number: " +  resFiles.length);
			System.out.println("Success Files Number: " +  (resFiles.length - failuecount));
			System.out.println("Failue Files Number: " +  (failuecount));
			System.out.println("Success Ratio: " +  ((resFiles.length - failuecount)*1.0/ resFiles.length)*100.0 +"%");
			return true;	
	}
	public JSONObject loadRecordsFields(String recordsFieldsFilePath){
		File file = new File(recordsFieldsFilePath);
		if(!file.exists()){
			System.out.println("数据记录文件不存在！");
			return null;
		}
		Object_Json_File jsonfile = new Object_Json_File(recordsFieldsFilePath);
		String jsonRecords = jsonfile.getJsonString();  
		JSONObject jsonObject = null;
	    try{
	    	jsonObject = JSONObject.fromObject(jsonRecords);  
	    }catch(Exception e){
	    	e.printStackTrace();
	    	return null;
	    }	
		return jsonObject;	
	}
}
