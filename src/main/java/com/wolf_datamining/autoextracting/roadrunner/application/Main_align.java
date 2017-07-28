package com.wolf_datamining.autoextracting.roadrunner.application;

import java.io.File;

public class Main_align {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  AlignRecords align_Records = new AlignRecords();
		  File record_outputFile = new File("C:/Users/Administrator/Desktop/ExtractRecordData/10/Records/");
			if(!record_outputFile.exists()){
				record_outputFile.mkdir();
			}
		// 对齐操作
		// arg1: 输入数据记录定位文件路径，到文件名
		// arg2: 输入使用包装器抽取的数据记录文件，到文件夹
		// arg3: 输出对齐后的数据记录，到文件夹
//		  align_Records.alignAllRecords("C:/Users/Administrator/Desktop/ExtractRecordData/11/output/Records00.json", 
//					"C:/Users/Administrator/Desktop/ExtractRecordData/11/output/",
//					"C:/Users/Administrator/Desktop/ExtractRecordData/11/Records/");
		// 对齐操作
		// arg1: 输入使用包装器抽取的数据记录文件，到文件夹
		// arg2: 输出对齐后的数据记录，到文件夹
		  align_Records.alignAllRecords(
					"C:/Users/Administrator/Desktop/ExtractRecordData/10/output/",
					"C:/Users/Administrator/Desktop/ExtractRecordData/10/Records/");
	}

}
