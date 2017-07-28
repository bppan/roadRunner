package com.wolf_datamining.autoextracting.roadrunner.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.xml.sax.SAXException;

import com.wolf_datamining.autoextracting.roadrunner.ast.Expression;
import com.wolf_datamining.autoextracting.roadrunner.preprocess.Html2XhtmlByNeko;

import dataming.autoextracting.roadrunner.Wrapper;


public class test {

	public test() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String [] args)
	{
		
		
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\test.txt");
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
		System.out.println(tempjsonString);
		JSONObject js = JSONObject.fromObject(tempjsonString);
		
		String value = js.getString("value");
		
		System.out.println(value);
		
//		List<Integer> pt = new ArrayList<Integer>();
//		pt.add(0);
//		pt.add(0);
//		pt.add(0);
//		pt.add(0);
//		pt.add(4,1);
//		for(int i = 0; i < pt.size();i++)
//		{
//			System.out.println(pt.get(i));
//		}
//		try {
//			
//			Html2XhtmlByNeko converter = new Html2XhtmlByNeko(); 
////			// 设置输出文件编码
//////			converter.setOutputEncoding("utf8"); // 输出编码的设置，如果不设置将采用输入编码
////			// 设置输入文件的编码
//////			converter.setInputEncoding("utf8"); // 如果HTML没有声明编码，这个一定要设置，否则不需要设置
////			// 单文件转换
////			// arg1: 输入文件路径，包含文件名
////			// arg2: 输出文件路径，包含文件名
//			converter.convertAll("C:\\Users\\Administrator\\Desktop\\RoadRunner\\Sample\\Html\\",
//					"C:\\Users\\Administrator\\Desktop\\RoadRunner\\Sample\\XHtml\\");
			
//			ExtractFromHtml extracter = new ExtractFromHtml();
			ExtractFromXhtml extracter = new ExtractFromXhtml();
			// 设置输入文件的编码格式
//			extracter.setInputFileEncoding("gb2312");
			//设置配置文件路径
//			extracter.setConfigPath("path");
//			System.out.println("============= Start ===============");
//			// 生成包装器
//			extracter.generateWrapper("C:/Users/Administrator/Desktop/RoadRunner/Sample/XHtml/", "WrapperTest");
//			extracter.extractAll("D:\\WorkspacesForWeb\\roadrunner\\output\\WrapperTest\\WrapperTest00.xml",
//			"C:\\Users\\Administrator\\Desktop\\RoadRunner\\Sample\\XHtml\\", "C:\\Users\\Administrator\\Desktop\\RoadRunner\\Sample\\Output\\");
//			System.out.println("============= END ===============");
//			Wrapper w = Wrapper.load(new File("G:/datamining/Projects/roadrunnertest/output/test11/test1100.xml"));
//			Expression expr = w.getExpression();
//			Wrapper w2 = Wrapper.load(new File("C:/Users/Admin/Desktop/test1100.xml"));
//			Expression exp2 = w.getExpression();
//			
//			if(w.equals(w2))
//			{
//				System.out.println(true);
//			}
//			else
//			{
//				System.out.println(false);
//			}
			
			
			
			
//			
//		} catch (IOException | SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
	}
	
	

}
