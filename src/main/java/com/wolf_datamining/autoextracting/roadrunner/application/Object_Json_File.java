package com.wolf_datamining.autoextracting.roadrunner.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Object_Json_File {
	
	private File objectfile;
	private String path;
	private String jsonString;
	
	
	public File getHtmlfile() {
		return objectfile;
	}
	public void setObjectfile(File objectfile) {
		this.objectfile = objectfile;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getFileParentPath(){
		return objectfile.getParent();
	}
	public File getFile(){
		return this.objectfile;
	}
	public  Object_Json_File(String path){
		this.path = path;
		if(!new File(path).exists()){
			this.objectfile = null;
			this.jsonString = null;
		}else{
			this.objectfile = new File(path); 
			this.jsonString = concvertJsonString();
		}
	}
	//读取json文件转化为字符串
	private String concvertJsonString(){
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStreamReader inputStreamReader = null;
		inputStreamReader = new InputStreamReader(fileInputStream);
		BufferedReader reader = new BufferedReader(inputStreamReader);
		String tempString = null;
		String tempjsonString = "";
		try {
			while((tempString = reader.readLine()) != null){
				tempjsonString += tempString;
			}
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return tempjsonString;
	}
	
}
