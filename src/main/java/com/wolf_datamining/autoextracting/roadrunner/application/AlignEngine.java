package com.wolf_datamining.autoextracting.roadrunner.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

public class AlignEngine {

	public static List<HeadTable> headTable;
	public double fixThreshold;
	public int min_fieldOfRecordsNum;
	public int recordsColumn;
	public int orignRecordsColumn;
	public Document doc;
	public String outputAlignedRecordsPath;
	
	public AlignEngine(){
		doc = null;
		recordsColumn = 0;
		fixThreshold = 1;
		min_fieldOfRecordsNum = 5;
	}
	// 对齐数据记录
	// arg1: 输入使用包装器抽取的资源文件路径，到文件名
	// arg2: 是否定位数据区域
	// arg3: 如果arg2==false,必须加载定位文件
	public void alignCore(File resourecesFile){
		this.alignCore(resourecesFile, true,"");
	}
	public void alignCore(File resourecesFile, boolean isIdentifyRecordsColumn, String loadHeadTalbe){
		doc = null;
		try {
			doc = Jsoup.parse(resourecesFile,"utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("文件不存在!");
			e.printStackTrace();
		}
		headTable = new ArrayList<HeadTable>();
		//深度遍历DOM树
		for(int i = 0; i < doc.childNodes().size(); i++){
			deepFirst(doc.childNode(i));
		}	
		//将数据记录中间结果以xls的格式写到外部文件中
		String path = outputAlignedRecordsPath + resourecesFile.getName() + "_records.xls";	
		
		WriteRecordsToxls witerRecords = new WriteRecordsToxls();
		witerRecords.writeRecordsToxls(headTable, path);

		//定位数据区域
		if(isIdentifyRecordsColumn){
//			this.recordsColumn = findRecordsFields();
			this.recordsColumn = find_Records_Fileds();
			orignRecordsColumn = this.recordsColumn;
		}else{
			this.recordsColumn = find_records_loadFile(loadHeadTalbe);
			System.out.println(recordsColumn);
		}
		//test output
		if(this.recordsColumn  != -1)
		{
			System.out.println(resourecesFile.getName() + " :: " + recordsColumn);
		}else{
			System.err.println(resourecesFile.getName() + " :: " + recordsColumn);
		}
//		System.out.println(headTable.get(0).listString.get(0).layerNumber);
		
		
		boolean NoSecondRecords = false;
		if(this.recordsColumn  != -1){
			//调整HeadTable，是数据记录序列化
			serialized_data_records(this.recordsColumn);		
		}else{
			NoSecondRecords = true;
			this.recordsColumn = headTable.size();
		}
		//调整头部数据记录区域
		serialized_first_data_records(0);
//		调整数据记录的层级数
		if(!NoSecondRecords){
			adjustRecordsLayer();	
		}
		System.err.println("size: " + headTable.size());
		String path2 = outputAlignedRecordsPath + resourecesFile.getName() +  "_records00.xls";
		witerRecords.writeRecordsToxls(headTable, path2);
		
	}
//	public void alignCore(File resourecesFile, String loadHeadTalbe){
//		this.recordsColumn = find_records_loadFile(loadHeadTalbe);
//		System.out.println(loadHeadTalbe);
//		alignCore(resourecesFile, false);
//	}
	
	public  int findRecordsFields(){
		//此处原先为-1，本次修改为0
		int tempRecordsColumn = -1;
		int maxColumnNum = getLayer()/2;
		for(int i = 0; i < headTable.size(); i++){
			if(i == 0){
				continue;
			}
			HeadTable tableColumn = headTable.get(i);
			int rowSize = tableColumn.listString.size();
			int startRow = tableColumn.listString.get(0).layerNumber;
			int endRow = tableColumn.listString.get(rowSize - 1).layerNumber;
			if((endRow - startRow) >= maxColumnNum){
				if(rowSize >= min_fieldOfRecordsNum){
					tempRecordsColumn = i;
					break;
				}
			}
		}
		return tempRecordsColumn;
	}
	////////////////////////////
	public int find_Records_Fileds(){	
		int tempRecordsColumn = -1;
		int startLayer = 1;
		int endLayer = getLayer();	
		for(int i = 0; i < headTable.size(); i++){
			if(isLayerContainText(i, 1) != -1){
				//缩小范围,开始层级为1，所以startLay就是1
				//向下缩小
				int downLayer = 1;
				while(isLayerContainText(i, downLayer) != -1){
					downLayer++;
				}
				startLayer = downLayer - 1;
				//向上缩小
				int upLayer = endLayer;
				if(startLayer != endLayer){
					while(isLayerContainText(i, upLayer) != -1){
						upLayer--;
					}
					endLayer = upLayer + 1;
				}else{
					return -1;
				}
				continue;
			}
			int theColumnMinLayer = getMinLaye_Column(i);
			int theColumnMaxLayer =  getMaxLaye_Column(i);
			int area = theColumnMaxLayer - theColumnMinLayer;
			int total_area = endLayer - startLayer;
			if(area >= total_area * 0.49){
				//判断个数
				System.out.println(total_area/2  + " : " + area);
				if(isProperDensity(i)){
					tempRecordsColumn = i;
					break;
				}
			}			
		}
		return tempRecordsColumn;
	}
	public int find_records_loadFile(String sumHeadString){		
		for(int i = 0; i < headTable.size();i++){
			if(headTable.get(i).headString.equals(sumHeadString)){
				return i;
			}
		}
		return -1;
	}
	public boolean isProperDensity(int column){
		List<Integer> theGape = new ArrayList<Integer>();	
		int fisrtLayer= getMinLaye_Column(column);
		int RowSize = headTable.get(column).listString.size();
		
		int firstSum = 0;
		for(int i = 1; i <RowSize; i++){
			int theNextLayer = headTable.get(column).listString.get(i).layerNumber;
			theGape.add(theNextLayer - fisrtLayer);
			firstSum += theNextLayer - fisrtLayer;
			fisrtLayer = headTable.get(column).listString.get(i).layerNumber;
		}
		if(theGape.size() <= 1){
			return false;
		}
		int theStart = getMinLaye_Column(column);
		int theEnd = getMaxLaye_Column(column);
		int totleGape = theEnd - theStart;
		
		double theAverage = (double)firstSum/theGape.size();
		int sum = 0;
		for(int i = 0; i < theGape.size();i++){
			sum += Math.pow(theAverage -theGape.get(i) , 2);
			
		}
		double variance = Math.pow(sum/theGape.size(), 0.5);
		System.out.println(column + " : average : " + theAverage);
		System.out.println(column + " : variance : " + variance);
		if(0 <= variance && variance < 13){
			return true;
		}
		return false;
	}
	public void serialized_first_data_records(int startColumn){
		int endColumn = this.recordsColumn;
		int currentLayer = headTable.get(startColumn).listString.get(0).layerNumber;
//		int firstLayer =  getTheFieldsLayer(startColumn, endColumn);
//		System.out.println("~~~~~~~~~~~~ " + getTheFieldsLayer(startColumn, endColumn));
		for(int rowk = 2; rowk < getTheFieldsLayer(startColumn, endColumn) + 1; rowk++){
			for(int j = startColumn; j < endColumn; j++){
				int theInsertNumber = isLayerContainText(j, rowk);	
				if(theInsertNumber != -1){
//					System.out.println("~~~~~~~~~~~~~~~~~~~~~    " + headTable.get(startColumn).listString.get(theInsertNumber).layerString);
					String theElementHead = headTable.get(j).headString;
					boolean flag = false;
					for(int k = startColumn; k < endColumn; k++){
						if(isTheRightPosition(k, rowk - 1)){
							if(headTable.get(k).headString.equals(theElementHead)){
								//添加
								String text = headTable.get(j).listString.get(theInsertNumber).layerString;
								headTable.get(k).addListString(currentLayer, text);
								headTable.get(j).listString.remove(theInsertNumber);						
							}else{
								//插入
//								System.out.println("theInsertNumber:    "+ theInsertNumber);
//								System.out.println("theInsertNumber:    "+ headTable.get(j).listString.size());							
								String text = headTable.get(j).listString.get(theInsertNumber).layerString;
								HeadTable table = new HeadTable(theElementHead);
								table.addListString(currentLayer, text);
								headTable.get(j).listString.remove(theInsertNumber);
								headTable.add(k,table);
								this.recordsColumn++;
								endColumn = this.recordsColumn;
							}
							flag = true;
							break;
							//插入该节点；
						}
					}
					if(!flag){
//						System.out.println("theInsertNumber:    "+ theInsertNumber);
//						System.out.println("theInsertNumber:    "+ headTable.get(j).listString.size());						
						String text = headTable.get(j).listString.get(theInsertNumber).layerString;
						HeadTable table = new HeadTable(theElementHead);
						table.addListString(currentLayer, text);
						headTable.get(j).listString.remove(theInsertNumber);
						headTable.add(endColumn,table);
						this.recordsColumn++;
						endColumn = this.recordsColumn;
					}
				}
			}
		}
	}
	public boolean isTheRightPosition(int column, int rowk){
		if(isLayerContainText(column, rowk) != -1){
			return false;
		}
		for(int i = 0; i < headTable.get(column).listString.size();i++){
			if(headTable.get(column).listString.get(i).layerNumber <= rowk){
					return false;
			}
		}
		return  true;
	}
	public int getTheFieldsLayer(int startColumn, int endColumn){
		int theMaxFieldsLayer = 0;
		for(int i = startColumn; i < endColumn; i++){
			for(int j = 0; j < headTable.get(i).listString.size();j++){
				if(headTable.get(i).listString.get(j).layerNumber > theMaxFieldsLayer){
					theMaxFieldsLayer = headTable.get(i).listString.get(j).layerNumber;
				}
			}
		}
		return theMaxFieldsLayer;
	}
	//定位数据区域
	public void serializedOneRecord(int startColumn, int currentLayer, int marginLayer){
		for(int rowk = currentLayer + 1; rowk < marginLayer; rowk++){
			for(int j = startColumn + 1; j < headTable.size();j++){	
				//获取当前的列
				if( headTable.get(j).listString.size() != 0){						
					int theInsertNumber = isLayerContainText(j, rowk);									
					if( theInsertNumber != -1){							
						int insertColumnPositon = getInsertPosition(startColumn, currentLayer);
						int properlocation = -1;
						boolean flag = false;
						for(int pi = insertColumnPositon; pi < headTable.size(); pi++){
							String headTableHead = headTable.get(pi).headString;
							String textHead = headTable.get(j).headString;
							if(headTableHead.equals(textHead)){
								if(headTable.get(pi).getListString(currentLayer) == null){
									properlocation = pi;
									flag = true;
									break;											
								}
								else if(pi> properlocation){
									properlocation = pi;
								}
							}			
						}												
						if(properlocation == -1){
							//插入
							String head = headTable.get(j).headString;
							String text = headTable.get(j).listString.get(theInsertNumber).layerString;
							HeadTable table = new HeadTable(head);
							table.addListString(currentLayer, text);
							headTable.get(j).listString.remove(theInsertNumber);
							headTable.add(insertColumnPositon,table);
						}else if(flag){
							//添加
							String text = headTable.get(j).listString.get(theInsertNumber).layerString;
//							System.out.println( text);		
							if(!headTable.get(properlocation).addListString(currentLayer, text)){
								String head = headTable.get(j).headString;
								HeadTable table = new HeadTable(head);
								table.addListString(currentLayer, text);
								headTable.add(properlocation,table);
							}
							headTable.get(j).listString.remove(theInsertNumber);
						
						}else if(!flag){
							//插入
							String head = headTable.get(j).headString;
							String text = headTable.get(j).listString.get(theInsertNumber).layerString;
							HeadTable table = new HeadTable(head);
							table.addListString(currentLayer, text);
							headTable.get(j).listString.remove(theInsertNumber);
							headTable.add(properlocation + 1,table);
						}				
					}
				}
			}
//			System.out.println("end");	
		}
	}
	//序列化数据记录
	public void serialized_data_records(int startColumn){
		//获取当前这列的数据记录
		HeadTable tableColumn = headTable.get(startColumn);
		//以此处理每一个数据记录
		for(int i = 0; i < tableColumn.listString.size();i++){
			//获取当前的数据记录层数	
			int currentLayer = tableColumn.listString.get(i).layerNumber;
			int marginLayer = 0;//当前处理的数据记录边界
			if(i == tableColumn.listString.size() - 1){
				marginLayer = getLastRecordsLayer(startColumn);
			}else{
				marginLayer = tableColumn.listString.get(i + 1).layerNumber;
			}
			//对于每一个数据记录，以此处理
			serializedOneRecord(startColumn, currentLayer, marginLayer);
		}
	}
	//调整层级数
	public void adjustRecordsLayer(){
		int firstRegionStartColumn = 0;
		int SecondRegionStartColumn = recordsColumn;
		int startLayer = headTable.get(firstRegionStartColumn).listString.get(0).layerNumber;
		int rowSize = headTable.get(SecondRegionStartColumn).listString.size() + headTable.get(0).listString.size();
		for(int j = 0; j < rowSize; j++){	
			int tempLayer = 1;
			if(j >= headTable.get(0).listString.size()){
				tempLayer = headTable.get(SecondRegionStartColumn).listString.get(j - headTable.get(firstRegionStartColumn).listString.size()).layerNumber;
			}
			else{
				tempLayer = headTable.get(firstRegionStartColumn).listString.get(j).layerNumber;
			}
			for(int i = 0; i < headTable.size();i++){	
				int tempCount = isLayerContainText(i, tempLayer);
				if(tempCount !=  -1){
					headTable.get(i).listString.get(tempCount).layerNumber = startLayer;
				}
			}
			startLayer++;
		}
	}
	
	public static void main(String[] args) {
		
		
		// TODO Auto-generated method stub
//		File inputfile = new File("C:/Users/Administrator/Desktop/ExtractRecordData/8/output/" +  "res_0.html-to-.xml");
//		Document doc = null;
//		try {
//			doc = Jsoup.parse(inputfile,"utf-8");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		headTable = new ArrayList<HeadTable>();
//		for(int i = 0; i < doc.childNodes().size(); i++){
//			deepFirst(doc.childNode(i));
//		}
//		System.out.println("Layer: " + getLayer());
//		//将数据记录中间结果以xls的格式写到外部文件中
//		String path = "C:/Users/Administrator/Desktop/records8.xls";
//		WriteRecordsToxls witerRecords = new WriteRecordsToxls();
//		witerRecords.writeRecordsToxls(headTable, getLayer(), path);
//		//以下部分为对齐操作
//		int maxColumnNum = getLayer()/2;
//		int recordsColumn = -1;
//		for(int i = 0; i < headTable.size(); i++){
//			if(i == 0){
//				continue;
//			}
//			HeadTable tableColumn = headTable.get(i);
//			int rowSize = tableColumn.listString.size();
//			int startRow = tableColumn.listString.get(0).layerNumber;
//			int endRow = tableColumn.listString.get(rowSize - 1).layerNumber;
//			if((endRow - startRow) > maxColumnNum){
//				if(rowSize >= 5){
//					recordsColumn = i;
//					break;
//				}
//			}
//		}
//		
//		System.out.println("Start column: " + recordsColumn);
//		//调整headTabl进行序列化操作		
//		HeadTable tableColumn = headTable.get(recordsColumn);
//		for(int i = 0; i < tableColumn.listString.size();i++){
//				
//			int currentLayer = tableColumn.listString.get(i).layerNumber;
//			int marginLayer = 0;
//			
//			if(i == tableColumn.listString.size() - 1){
//				marginLayer = getLastRecordsLayer(recordsColumn);
//			}else{
//				marginLayer = tableColumn.listString.get(i + 1).layerNumber;
//			}
//
//			//
//			for(int rowk = currentLayer + 1; rowk < marginLayer; rowk++){
//				for(int j = recordsColumn + 1; j < headTable.size();j++){	
//					
//					if( headTable.get(j).listString.size() != 0){						
//						int theInsertNumber = isLayerContainText(j, rowk);									
//						if( theInsertNumber != -1){							
//							int insertColumnPositon = getInsertPosition(recordsColumn, currentLayer);
//							int properlocation = -1;
//							boolean flag = false;
//							for(int pi = insertColumnPositon; pi < headTable.size(); pi++){
//								String headTableHead = headTable.get(pi).headString;
//								String textHead = headTable.get(j).headString;
//								if(headTableHead.equals(textHead)){
//									if(headTable.get(pi).getListString(currentLayer) == null){
//										properlocation = pi;
//										flag = true;
//										break;
//												
//									}
//									else if(pi> properlocation){
//										properlocation = pi;
//									}
//								}			
//							}
//													
//							System.out.println("insert Row" + rowk);
//							System.out.println( j + "insert " + insertColumnPositon);
//							System.out.println( "proper " + properlocation);
//							if(properlocation == -1){
//								//插入
//								String head = headTable.get(j).headString;
//								String text = headTable.get(j).listString.get(theInsertNumber).layerString;
//								HeadTable table = new HeadTable(head);
//								table.addListString(currentLayer, text);
//								headTable.get(j).listString.remove(theInsertNumber);
//								headTable.add(insertColumnPositon,table);
//							}else if(flag){
//								//添加
//								String text = headTable.get(j).listString.get(theInsertNumber).layerString;
//								System.out.println( text);		
//								if(!headTable.get(properlocation).addListString(currentLayer, text)){
//									String head = headTable.get(j).headString;
//									HeadTable table = new HeadTable(head);
//									table.addListString(currentLayer, text);
//									headTable.add(properlocation,table);
//								}
//								headTable.get(j).listString.remove(theInsertNumber);
//							
//							}else if(!flag){
//								//插入
//								String head = headTable.get(j).headString;
//								String text = headTable.get(j).listString.get(theInsertNumber).layerString;
//								HeadTable table = new HeadTable(head);
//								table.addListString(currentLayer, text);
//								headTable.get(j).listString.remove(theInsertNumber);
//								headTable.add(properlocation + 1,table);
//							}				
//						}
//					}
//				}
//				System.out.println("end");	
//			}
//		}
//		//调整层级数
//	
//		System.out.println("Layer: " + getLayer());
//		System.out.println("----------: " + headTable.size());
//		System.out.println("----------: " + recordsColumn);
//		int startLayer = headTable.get(recordsColumn).listString.get(0).layerNumber;
//		int rowSize = headTable.get(recordsColumn).listString.size();
//		for(int j = 0; j < rowSize; j++){	
//			int tempLayer = headTable.get(recordsColumn).listString.get(j).layerNumber;
//			for(int i = recordsColumn; i < headTable.size();i++){	
//			
//				System.out.println("-----tempLayer: "  + tempLayer);
//				int tempCount = isLayerContainText(i, tempLayer);
//				if(tempCount !=  -1){
//					System.out.println("+++: " + headTable.get(i).listString.get(tempCount).layerString);
//					System.out.println("+++: " + headTable.get(i).listString.get(tempCount).layerNumber);
//					headTable.get(i).listString.get(tempCount).layerNumber = startLayer;
//					System.out.println("+++: " + headTable.get(i).listString.get(tempCount).layerNumber);
//				}
//				System.out.println("--i :: "  + i);
//			}
//			System.out.println("-------------------------------------------" );
//			startLayer++;
//		}
//		//此部分便于调试方便
//		String path2 = "C:/Users/Administrator/Desktop/records80.xls";
//		witerRecords.writeRecordsToxls(headTable, getLayer(), path2);
	}
	//深度遍历ＤＯＭ树
	public void deepFirst(Node root){
		if(root.nodeName().toLowerCase().equals("#text")){
			String text = root.toString().replaceAll("/n", "").
					replaceAll("&nbsp;", "").replaceAll("&gt;", "").
					replaceAll("&raquo;", "").replaceAll("&lt;", "").
					trim();	
			if(!text.equals("")){	
				String head = getParentsTagString(root);		
				int index = addHeadtable(head);	
				if(index != -1){
					if(getMaxLayerColumnNum() > index){
						headTable.get(index).addListString(getLayer() + 1, text);
					}
					else if(!headTable.get(index).addListString(getLayer(), text)){
						headTable.get(index).addListString(getLayer() + 1, text);
					}					
				}else{
					HeadTable table = new HeadTable(head);
					table.addListString(getLayer(), text);
					headTable.add(table);
				}				
			}
		}
		else{
			for(int i = 0; i < root.childNodes().size();i++){
				deepFirst(root.childNode(i));
			}
		}
	}
	//返回福父路径
	public String getParentsTagString(Node node){
		String parents_tag_string = "";
		Node parentNode = node.parent();
		while(parentNode != null){
//			if(parentNode.nodeName().toLowerCase().equals("font")){
//				parentNode = parentNode.parent();
//				continue;
//			}
			String parentPath = parentNode.nodeName();
			List<Attribute> atrs = parentNode.attributes().asList();
			for(int i = 0; i < atrs.size();i++){
				if(atrs.get(i).getKey().equals("class")){
//					parentPath += atrs.get(i).getKey() + atrs.get(i).getValue();
					parentPath += atrs.get(i).getKey();
				}else{
					parentPath += atrs.get(i).getKey();
				}				
			}
			parents_tag_string += parentPath;
			parentNode = parentNode.parent();
		}	
		return parents_tag_string;
	}
	//两个字符串是否相似
	public boolean isSimilarity(String str1, String str2){	
		if(str1.trim().equals(str2.trim())){
			return true;
		}
//		if(new JaroWinklerDistance(str1, str2).jaro_distance() == fixThreshold){
//			return true;
//		}
		return false;
	}
	//向Headtable中添加text节点
	public int addHeadtable(String head){
		for(int i = 0; i < headTable.size(); i++){
			if(isSimilarity(headTable.get(i).headString, head)){	
				return i;
			}
		}
		return -1;
	}
	//获取层数
	public int getLayer(){
		int max = 1;
		for(int i = 0; i < headTable.size();i++){
			for(int j= 0; j < headTable.get(i).listString.size(); j++){
				if(headTable.get(i).listString.get(j).layerNumber >= max){
					max = headTable.get(i).listString.get(j).layerNumber;
				}
			}
		}
		return max;
	}
	//获取最后一个数据记录的层数
	public int getLastRecordsLayer(int column){
		int size = headTable.get(column).listString.size();
		int maxLayer = headTable.get(column).listString.get(size - 1).layerNumber;
		for(int i = column; i < headTable.size();i++){
			for(int j= 0; j < headTable.get(i).listString.size(); j++){
				if(headTable.get(i).listString.get(j).layerNumber >= maxLayer){
					maxLayer = headTable.get(i).listString.get(j).layerNumber;
				}
			}
		}
		return maxLayer + 1;
	}
	//是否该列包含该层级的数据记录
	public int isLayerContainText(int column, int layer){
		for(int i = 0; i <headTable.get(column).listString.size();i++){
			if(headTable.get(column).listString.get(i).layerNumber == layer){
				return i;
			}
		}
		return -1;
	}
	//获取插入的位置
	public int getInsertPosition(int column, int rowK){
		for(int j = column + 1; j < headTable.size();j++){
			if(isLayerContainText(j, rowK) == -1){
				return j;
			}
		}
		return headTable.size();	
	}
	//获取最大层级的列数
	public int getMaxLayerColumnNum(){
		int max = 1;
		int column = 0;
		for(int i = 0; i < headTable.size();i++){
			for(int j= 0; j < headTable.get(i).listString.size(); j++){
				if(headTable.get(i).listString.get(j).layerNumber >= max){
					max = headTable.get(i).listString.get(j).layerNumber;
					column = i;
				}
			}
		}
		return column;
	}
	//获取当前列数的最小层级数
	public int getMinLaye_Column(int column){
		int minlayer = getLayer();
		for(int i = 0; i < headTable.get(column).listString.size();i++){
			if(headTable.get(column).listString.get(i).layerNumber <= minlayer){
				minlayer = headTable.get(column).listString.get(i).layerNumber;
			}
		}
		return minlayer;
	}
	//获取当前列数的最大层级数
	public int getMaxLaye_Column(int column){
		int maxlayer = -1;
		for(int i = 0; i < headTable.get(column).listString.size();i++){
			if(headTable.get(column).listString.get(i).layerNumber >= maxlayer){
				maxlayer = headTable.get(column).listString.get(i).layerNumber;
			}
		}
		return maxlayer;
	}



}
