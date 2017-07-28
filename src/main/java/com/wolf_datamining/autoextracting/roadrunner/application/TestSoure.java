package com.wolf_datamining.autoextracting.roadrunner.application;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

public class TestSoure {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File inputfile = new File("C:/Users/Administrator/Desktop/ExtractRecordData/5/output/" +  "res_0.html-to-.xml");
		Document doc = null;
		try {
			doc = Jsoup.parse(inputfile,"utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < doc.childNodes().size(); i++){
			deepFirst(doc.childNode(i));
		}
	}
	public static void deepFirst(Node root){
		if(root.nodeName().toLowerCase().equals("#text")){
//			System.out.println(root.nodeName());
			String text = root.toString().replaceAll("/n", "").replaceAll("&nbsp;", "").trim();	
			if(!text.equals("")){
				if(text.equals("奥迪Q5论坛")){
					System.out.println(getParentsTagString(root));
				}
				
			}
		}
		else{
			//System.out.println(root.nodeName());
			if(root.nodeName().equals("i")){
				System.out.println(root.childNodes().size());
			}
//			System.out.println(root.nodeName());
			for(int i = 0; i < root.childNodes().size();i++){
				
				deepFirst(root.childNode(i));
			}
		}

	}
	public static String getParentsTagString(Node node){
		String parents_tag_string = "";
		Node parentNode = node.parent();
		while(parentNode != null){
			String parentPath = parentNode.nodeName();
			List<Attribute> atrs = parentNode.attributes().asList();
			for(int i = 0; i < atrs.size();i++){
				if(atrs.get(i).getKey().equals("class")){
					parentPath += atrs.get(i).getKey() + atrs.get(i).getValue();
				}else{
					parentPath += atrs.get(i).getKey();
				}
				
			}
			parents_tag_string += parentPath + "||";
			parentNode = parentNode.parent();
		}	
		return parents_tag_string;
	}
}
