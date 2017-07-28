package com.wolf_datamining.autoextracting.roadrunner.application;

import java.util.List;

public class test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String rootpath = "C:/Users/Administrator/Desktop/ExtractRecordData/9/";
		String path = rootpath +"凤凰汽车_HtmlContent.json";	
		ExtractJsonToHtml extractHtml = new ExtractJsonToHtml(path);//解析json文件为html	
		List<HtmlObject> htmlList = extractHtml.convertToHtml();
////	清楚网页冗余数据
		
		String extractHtmlPath = rootpath + "html/";	
		CleanHtml clean = new CleanHtml(htmlList);
		clean.doClean();
		clean.writecleanedHtml(extractHtmlPath);
	}

}
