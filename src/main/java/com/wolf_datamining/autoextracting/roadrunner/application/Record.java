package com.wolf_datamining.autoextracting.roadrunner.application;

import java.util.ArrayList;
import java.util.List;

public class Record {
	public List<String> attributeName;
	public List<String> attributeValue;
	public int attributeNum;
	public int deepth;
	
	public int getDeepth() {
		return deepth;
	}
	public void setDeepth(int deepth) {
		this.deepth = deepth;
	}
	public List<String> getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(List<String> attributeName) {
		this.attributeName = attributeName;
	}
	public List<String> getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(List<String> attributeValue) {
		this.attributeValue = attributeValue;
	}
	public int getAttributeNum() {
		return attributeNum;
	}
	public void setAttributeNum(int attributeNum) {
		this.attributeNum = attributeNum;
	}
	public Record(){
		this.attributeName = new ArrayList<String>();
		this.attributeValue = new ArrayList<String>();
		this.attributeNum = 0;
	}
	public boolean addAttribute(String attribureName, String attributeValue){
		try{
			this.attributeName.add(attribureName);
			this.attributeValue.add(attributeValue);
			this.attributeNum++;
			
		}catch(Exception e){
			System.err.println("数据记录属性添加失败");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
