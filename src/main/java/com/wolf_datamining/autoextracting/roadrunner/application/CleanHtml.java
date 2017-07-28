package com.wolf_datamining.autoextracting.roadrunner.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

public class CleanHtml {

	public final String ENCODING = "utf-8";
	public List<HtmlObject> htmlList;
	
	public List<HtmlObject> getHtmlList() {
		return htmlList;
	}
	public CleanHtml(List<HtmlObject> htmlList){
		this.htmlList = htmlList;
	}
	public static boolean isdeleteElement(Node node){
		String elementName = node.nodeName().toLowerCase();
		if(elementName.equals("script")){
			return true;
		}
		if(elementName.equals("style")){
			return true;
		}
		if(elementName.equals("#comment")){
			return true;
		}
		if(elementName.equals("img")){
			return true;
		}
		if(!elementName.equals("#text") && !elementName.equals("meta") &&  !elementName.equals("#doctype")){
			//删除仅仅含有一个text节点，并且text节点为空		
			int count = 0;			
			for(int i = 0; i < node.childNodes().size(); i++){
				if(node.childNodes().get(i).nodeName().toLowerCase().equals("#text")){
					if(node.childNodes().get(i).toString().replaceAll("/n", "").replaceAll("&nbsp;", "").trim().equals("")){
						count++;
					}
				}
			}
			if(count ==  node.childNodes().size()){	
				if(elementName.equals("br")){	
					return false;
				}
				return true;
			}			
		}
		return false;
	}
	public boolean doClean(){		
		int cleanElementNum =this.cleanHtmlElement(); //含有的待清理数
		if(cleanElementNum == -1){
			return false;
		}
		while(cleanElementNum != 0){
			System.out.print("|");
			cleanElementNum =this.cleanHtmlElement(); //清楚html，并且将html格式转化为utf-8
		}
		return true;
	}
	public int cleanHtmlElement(){	
		if(this.htmlList == null){
			System.err.println("请设置htmlList!");
			return -1;
		}		
		if(this.htmlList.size()== 0){
			System.err.println("htmlList为空!" );
			return -1;
		}else{
			return cleanHtmlFile();
		}
	}
	private int cleanHtmlFile(){
		int deleteElementCount = 0;
		List<HtmlObject> cleanedtHtmlList = new ArrayList<HtmlObject>();
		for(int i = 0;  i < htmlList.size();i++){
			HtmlObject cleantarget = htmlList.get(i);
			Document doc = null;
			doc = Jsoup.parse(cleantarget.getHtmlString(),ENCODING);			
			List<Node> list = new ArrayList<Node>();
			for(int j = 0; j < doc.childNodes().size();j++){
				list.add(doc.childNodes().get(j));
			}	    	
	    	while(list.size() != 0){
	    		Node temp = list.get(0);
	    		List<Attribute> attrlist = temp.attributes().asList();		
	    		if(isdeleteElement(temp)){
	    			temp.remove();
	    			list.remove(0);
	    			deleteElementCount++;
	    			continue;
	    		}
	    		for(int k = 0; k <attrlist.size();k++){
	    		if(attrlist.get(k).getValue().equals("")){
	    				attrlist.get(k).setValue("null");
	    			}
	    		}	
	    		for(int p = 0; p < temp.childNodes().size();p++){	    				
	    			list.add(temp.childNodes().get(p));
	    		}
	    		list.remove(0);	    			
	    	}
	    	cleantarget.setHtmlString(doc.html());
	    	cleanedtHtmlList.add(cleantarget);	    		
		}
		this.htmlList = cleanedtHtmlList;
		return deleteElementCount;
	}
	public void writecleanedHtml(String path){
		
		List<HtmlToJson> htmljson = new ArrayList<HtmlToJson>();
		OutputStream outstream = null;
		for(int i = 0; i < this.htmlList.size();i++){
			try {
				outstream = new FileOutputStream(path + i +".html");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		try {
    			OutputStreamWriter writer = new OutputStreamWriter(outstream,ENCODING);
				writer.write(htmlList.get(i).htmlString);
				htmljson.add(new HtmlToJson( this.htmlList.get(i).getUrl(), path + i +".html"));
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		JSONArray jsonArray2 = JSONArray.fromObject(htmljson);
		writeCleanedHtmlJson(path, jsonArray2);
	}
	private void writeCleanedHtmlJson(String filePath,JSONArray jsonArray2){
		OutputStream outstream = null;
		try {
			outstream = new FileOutputStream(filePath + "htmlPathJson.json");
			OutputStreamWriter writer = new OutputStreamWriter(outstream,this.ENCODING);
			writer.write(jsonArray2.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Html Url Json: " + filePath + "htmlPathJson.json");
	}
}
