package com.wolf_datamining.autoextracting.roadrunner.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExtractJsonToHtml {
	public final String ENCODING = "utf-8";
	private JSONArray jsonArray;
	public List<HtmlObject> htmlList;
	
	public ExtractJsonToHtml(){	
		this.htmlList = new ArrayList<HtmlObject>();
	}
	public void setHtmlJson(String htmlJson){
		this.jsonArray = JSONArray.fromObject(htmlJson);//初始化json对象
	}
	public ExtractJsonToHtml(String filePath){
		Object_Json_File htmlfile = new Object_Json_File(filePath);//读取json文件，转化为字符串
		if(htmlfile.getFile() == null){
			System.err.println("输入文件路径不存在："+ filePath);
			return;
		}
		String htmlJson = htmlfile.getJsonString();	//获取读入的json字符串
		setHtmlJson(htmlJson);
		this.htmlList = new ArrayList<HtmlObject>();
	}
	public List<HtmlObject> convertToHtml(){		
		for(int i = 0; i <jsonArray.size();i++){
			JSONObject jsonObject = jsonArray.getJSONObject(i);		
			String htmlurl = jsonObject.getString("Url");
			if(jsonObject.getString("HtmlContent").equals("null")){
				System.err.println(jsonObject.getString("Url"));	
				continue;
			}				
			String html = jsonObject.getString("HtmlContent");
			Document doc= setCharSet(html);//从字符串中加载			
			HtmlObject htmlstring = new HtmlObject();
			htmlstring.setUrl(htmlurl);
			htmlstring.setHtmlString(doc.html());
			htmlList.add(htmlstring);				
			System.out.println(jsonObject.getString("Url"));				
		}
		System.out.println("-------------------------------------");
		System.out.println("Html Count: " + jsonArray.size());
		return this.htmlList;
	}
	private Document setCharSet(String html){
		Document doc= Jsoup.parse(html);//从字符串中加载
		List<Node> list = new ArrayList<Node>();
		for(int j = 0; j < doc.childNodes().size();j++){
			list.add(doc.childNodes().get(j));
		}
		boolean flag = false;
  		while(list.size() != 0){
			Node temp = list.get(0);
			List<Attribute> attrlist = temp.attributes().asList();		
			for(int k = 0; k <attrlist.size();k++){
				//改变网页编码方式 改为utf-8
				if(attrlist.get(k).getValue().contains("charset")){			    					
					attrlist.get(k).setValue("text/html; charset=" + this.ENCODING);
					flag = true;
				}
			}	
			if(flag)break;
			for(int p = 0; p < temp.childNodes().size();p++){	    				
				list.add(temp.childNodes().get(p));
			}
			list.remove(0);	    			
		}	
		return doc;
	}
	
}
