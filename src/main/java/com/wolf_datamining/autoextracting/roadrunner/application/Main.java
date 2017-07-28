package com.wolf_datamining.autoextracting.roadrunner.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wolf_datamining.autoextracting.roadrunner.preprocess.Html2XhtmlByNeko;

/**
 * 
 * 从一堆html文件到最终的生成包装器并抽取，可以：
 * 
 * 1. 手动执行转换，即先将HTML转换成xhtml，再进行抽取相关操作
 * 
 * 2. 自动执行转换，即输入HTML文件，在抽取操作内部完成转换，不需关心转换问题
 * 
 *
 *
 */

public class Main {
	public static void main(String [] args)
	{
////	path参数为带抽取的数据记录	
		String rootpath = "C:/Users/Administrator/Desktop/ExtractRecordData/9/";
		String path = rootpath +"凤凰汽车_HtmlContent.json";	
		System.out.println("======extract html======");
		ExtractJsonToHtml extractHtml = new ExtractJsonToHtml(path);//解析json文件为html	
		List<HtmlObject> htmlList = extractHtml.convertToHtml();
		System.out.println("-----extract html end----");
////	清楚网页冗余数据
		System.out.println("======cleaning html=====");
		String extractHtmlPath = rootpath + "html/";	
		CleanHtml clean = new CleanHtml(htmlList);
		clean.doClean();
		clean.writecleanedHtml(extractHtmlPath);
		System.out.println("-------clean end-------");
////	将html转化为xhtml格式
		System.out.println("=====convert html to xhtml====");
		Html2XhtmlByNeko converter = new Html2XhtmlByNeko(); 
		converter.setOutputEncoding("utf-8"); 
		converter.setInputEncoding("utf-8"); // 如果HTML没有声明编码，这个一定要设置，否则不需要设置
		String xhtmlPath = rootpath +"xhtml\\";//转化的xtml文件存放路径
		File xhtmlFile = new File(xhtmlPath);
		if(!xhtmlFile.exists()){
			xhtmlFile.mkdir();
		}
		// 批量转换
		// arg1: 输入文件路径，到文件夹
		// arg2: 输出文件路径，到文件夹
		converter.convertAll(extractHtmlPath, xhtmlPath);
		// 单文件转换
		// arg1: 输入文件路径，包含文件名
		// arg2: 输出文件路径，包含文件名
//		converter.convert("C:/Users/Admin/Desktop/test3.html", "C:/Users/Admin/Desktop/test33.xhtml");
		System.out.println("----------convert end----------");
	
		//如果没有包装器，产生包装器Wrapper，输入是一组网页，一组网页为测试时为50个网页左右
		System.out.println("----------Generate Wrapper---------");
		ExtractFromXhtml extracter = new ExtractFromXhtml();
// 		设置输入文件的编码格式
		extracter.setInputFileEncoding("utf-8");
//		//设置配置文件路径  默认配置即可
		//extracter.setConfigPath("path");	
////	生成包装器	
		// arg1: 转换的xhtml文件路径，到文件夹
		// arg2: 包装器文件夹名称，生成的文件夹，查看output文件夹
		extracter.generateWrapper("C:/Users/Administrator/Desktop/ExtractRecordData/9/xhtml/","Wrapper9");
//		新建输出文件夹
		File outputFile = new File(rootpath + "output/");
		if(!outputFile.exists()){
			outputFile.mkdir();
		}
		// 批量抽取
		// arg1: 包装器路径
		// arg2: 输入文件路径，到文件所在的文件夹
		// arg3: 输出资源文件路径，到输出文件所在的文件夹
		extracter.extractAll("D:/test2/roadrunner/output/Wrapper9/Wrapper900.xml",
				"C:/Users/Administrator/Desktop/ExtractRecordData/9/xhtml/", "C:/Users/Administrator/Desktop/ExtractRecordData/9/output/");
		// 从一张页面抽取
		// 参数1：包装器路径
		// 参数2：输入文件路径，包含文件名
		// 参数3：输出资源文件路径，不包含文件名
//		extracter.extract("G:/datamining/Projects/roadrunnertest/output/newtest/newtest00.xml",
//				"C:/Users/Admin/Desktop/jd-标注更小/1.html",
//				"G:/datamining/Projects/roadrunnertest/output/newtest/");
		System.out.println("============= END ===============");	
	}

}
