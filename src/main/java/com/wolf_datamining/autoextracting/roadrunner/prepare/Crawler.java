package com.wolf_datamining.autoextracting.roadrunner.prepare;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class Crawler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		try{
//			Document doc = Jsoup.connect("http://info.ruc.edu.cn/academic_professor.php?teacher_id=0").get();
//			System.out.println(doc.html());
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
		
		
		try {
//			File inputfile = new File("C:/Users/Administrator/Desktop/ExtractRecordData/第二批/xhtml/" +  "0.html-to-.xhtml");
		
//			Document doc = Jsoup.parse(inputfile,"utf-8");
//			List<Node> list = new ArrayList<Node>();
//			for(int i = 0; i < doc.childNodes().size();i++){
//				list.add(doc.childNodes().get(i));
//			}
//    		while(list.size() != 0){
//				Node temp = list.get(0);
//				
//				System.out.println(temp.nodeName());
////				if(temp.nodeName().toLowerCase().equals("#text")){
////					if(!temp.toString().equals(" ")|| temp.toString().isEmpty()){
////						System.out.println(temp.nodeName() + " : " + temp.toString());
////					}
////			
////				}
//				for(int i = 0; i < temp.childNodes().size();i++){
//					list.add(temp.childNodes().get(i));
//				}
//				list.remove(0);	    			
//			}
			
//			for(int j = 0; j< 50; j++){
//				File inputfile = new File("C:/Users/Administrator/Desktop/ExtractRecordData/第一批/html/" +j+ ".html");
//				if(!inputfile.exists()){
//					continue;
//				}
//				Document doc = Jsoup.parse(inputfile, "gb2312");
//				List<Node> list = new ArrayList<Node>();
//				for(int i = 0; i < doc.childNodes().size();i++){
//					list.add(doc.childNodes().get(i));
//				}
//	    		System.out.println("-------");
//	    		while(list.size() != 0){
//	    			Node temp = list.get(0);
//	    			List<Attribute> attrlist = temp.attributes().asList();		
//	    			if(temp.nodeName().toLowerCase().equals("script") || temp.nodeName().toLowerCase().equals("style") || temp.nodeName().toLowerCase().equals("#comment")){
//	    				System.out.println("Remove Node: " + temp.nodeName());
//	    				temp.remove();
//	    				list.remove(0);
//	    				continue;
//	    			}
//	    			for(int i = 0; i <attrlist.size();i++){
//	    				if(attrlist.get(i).getValue().equals("")){
//	    					System.out.println("remove Key:" + attrlist.get(i).getKey() + " value: " + attrlist.get(i).getValue());
//	    					temp.removeAttr(attrlist.get(i).getKey());
//	    					
//	    				}
//	    			}	    			
//	    			for(int i = 0; i < temp.childNodes().size();i++){
//	    				list.add(temp.childNodes().get(i));
//	    			}
//	    			list.remove(0);	    			
//	    		}
//	    		OutputStream outstream = new FileOutputStream("C:/Users/Administrator/Desktop/ExtractRecordData/第一批/html/" + j+".html");
//	    		OutputStreamWriter writer = new OutputStreamWriter(outstream,"gb2312");
//	    		writer.write(doc.html());
//	    		writer.close();
//	    		System.out.println(j + ".html Done");
//			}
//			
//			Document doc = Jsoup.connect("http://www.19lou.com/forum-269-thread-10411477922139846-1-1.html").header("User-Agent","Mozilla/5.0(WindowsNT6.1;WOW64;rv:33.0)Gecko/20100101Firefox/33.0").timeout(5000).get();
//			
//		    System.out.println(docte.childNode(2).childNode(1).childNode(0).childNode(0).nodeName());
//			Elements pt = doc.getElementsByClass("item-a");
////			
////			System.out.println(pt.size());
			OutputStreamWriter writer = null;
			int count = 0;
			for(int i = 0; i < 100;i++){	
				String  url = "http://bbs.tianya.cn/post-cars-1000"+i + "-1.shtml";
				try{		
				Document doc = Jsoup.connect(url).header("User-Agent","Mozilla/5.0(WindowsNT6.1;WOW64;rv:33.0)Gecko/20100101Firefox/33.0").timeout(5000).get();
				if(doc.title().equals("天涯论坛_天涯社区"))continue;
//				Elements pt = doc.getElementsByClass("item-a");
//				String path = pt.get(i).attr("data-url");
//				System.out.println(path);
//				Document doc2 = Jsoup.connect(path).header("User-Agent","Mozilla/5.0(WindowsNT6.1;WOW64;rv:33.0)Gecko/20100101Firefox/32.0").get();
//				System.out.println(doc2);
				String fileName = "C:/Users/Administrator/Desktop/crawWeb/1/html/" + i + ".html";
				String html = doc.html();
				File file = new File(fileName);
				OutputStream outstream = new FileOutputStream(fileName);
				String encoding = getHtmlEncoding(html);
				writer = new OutputStreamWriter(outstream,encoding);
				writer.write(html);		
				System.out.println(count + " craw: "+ url);
				count++;
				}catch(Exception e){
					System.err.println("skip: "+ url);
					continue;
				}
				
			}
			writer.close();
//			//System.out.println(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			String URLPath = "http://info.ruc.edu.cn/academic_professor.php?teacher_id=";
//			int value = 0;
//			int Number = 0;
//			int nodFind = 0;
//			while(true){
//				String url = URLPath + value;
//				try{
//					Document doc = Jsoup.connect(url).get();
//					if(doc.title().isEmpty()){
//						value++;
//						continue;
//					}
//					String fileName = "C:/Users/Administrator/Desktop/RoadRunner/Sample/Html/" + doc.title().replace("/", "-").replace("\\", "-").replace(":", "-").replace(" ", "")
//							.replace("\n", "-")+".html";
//					File file = new File(fileName);
//					if(file.exists()){
//						value++;
//						continue;
//					}
//					OutputStream outstream = new FileOutputStream(fileName);
//				
//					OutputStreamWriter writer = new OutputStreamWriter(outstream,"utf8");
//					
//					writer.write(doc.html());
//					writer.close();
//					value++;
//					Number++;
//					if(Number >= 100){
//						break;
//					}
//					System.out.println("Done HtmlID " + Number);
//				}
//				catch(Exception e){
//					e.printStackTrace();
//					nodFind++;
//					if(nodFind >= 100){
//						break;
//					}
//				}
//				
//			}
//			} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
			
			//Document doc = Jsoup.connect("http://www.douban.com/event/19500282/").get();
			
//			System.out.println(doc.html());
//			System.out.println(doc.title());
////			System.out.println(new URL("http://wwww.baidu.com/1.html").getFile());
//
//			OutputStream outstream = new FileOutputStream("C:/Users/Administrator/Desktop/douban/"
//			+doc.title().replace("/", "-").replace("\\", "-").replace(":", "-").replace(" ", "")
//			.replace("\n", "-")+".html");
//			OutputStreamWriter writer = new OutputStreamWriter(outstream,"utf8");
//			writer.write(doc.html());
//			writer.close();
//			
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		

	}
	
	public static String getHtmlEncoding(String html){
	    Document doc = Jsoup.parse(html);  
	    Elements elements = doc.select("meta");  
	    String charset = "utf-8";
	    for(Element metaElement : elements){    
	        if(metaElement!=null && StringUtils.isNotBlank(metaElement.attr("http-equiv")) 
	        		&& metaElement.attr("http-equiv").toLowerCase().equals("content-type")){    
	            String content = metaElement.attr("content");    
	            charset = getCharSet(content);    
	            break;    
	        }    
	    }  
	    return charset;
	}
	public static String getCharSet(String content){    
	    String regex = ".*charset=([^;]*).*";    
	    Pattern pattern = Pattern.compile(regex);    
	    Matcher matcher = pattern.matcher(content);    
	    if(matcher.find())    
	        return matcher.group(1);    
	    else    
	        return null;    
	} 

}
