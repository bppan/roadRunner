package records.classification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wolf_datamining.autoextracting.roadrunner.application.AlignEngine;
import com.wolf_datamining.autoextracting.roadrunner.application.HeadTable;
import com.wolf_datamining.autoextracting.roadrunner.application.LayerString;
import com.wolf_datamining.autoextracting.roadrunner.application.Object_Json_File;
import com.wolf_datamining.autoextracting.roadrunner.application.WriteRecordsToxls;

public class Label_trainData extends Label_data{
	public Label_trainData(){
		this.recordList = new ArrayList<List<HeadTable>>();
		this.page＿Url = new ArrayList<String>();
		this.pathPathUrl = new ArrayList<PathUrl>();
	}
	public void setRootPath(String rootPath, String resultFile_name){
		this.rootPath = rootPath;
		//checkFile
		File rootFile = new File(rootPath);
		if(!rootFile.exists()){
			System.err.println("待提取的文件根路径不存在！");
			return;
		}
		this.resultFile_name = resultFile_name;
	}
	public void setRootPath(String rootPath){
		setRootPath(rootPath, "");
	}
	//根据寄送标注文件标注，暂未开发完毕
	public void run_auto(String path,String jsonFile_name){
		setRootPath(path, jsonFile_name);
		loadResourceFile();
		JSONArray jsonArray = JSONArray.fromObject(human_extract_json);
		for(int i = 0; i < jsonArray.size(); i++){
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			//获取URL
			String Url =  jsonObject.getString("Url");			
			String htmlpath = getTheHtmlRecord(Url);//获取转换路径
			if(htmlpath.isEmpty()){
				System.err.println("获取文件路径错误，---");
				return;
			}
			int record_index = getIndexOfRecordList(htmlpath);//获取recordlist的所在位置
			if(record_index < 0){
				System.err.println("获取数据记录索引错误，---");
				return;
			}
			List<HeadTable> theRecordList = recordList.get(record_index);//获取当前的record;		
			String documents = jsonObject.getString("Documents");
			JSONArray recordArray = JSONArray.fromObject(documents);	
			for(int j = 0; j < recordArray.size(); j++){
				JSONObject jsonObj = recordArray.getJSONObject(j);
				for (Iterator iter = jsonObj.keys(); iter.hasNext();) {
					String key = (String)iter.next();
					String value = jsonObj.getString(key);
					String time = getTime(value);
					if(!time.isEmpty()){
						value = time;
					}
					if(!getRegisterTime(value).isEmpty()){
						value = getRegisterTime(value);
						   System.err.println(value + "--");
					}	
					changeModle(theRecordList, key,value);				
				}
			}
			//写出
			if(!new File(rootPath +"align/").exists()){
				new File(rootPath +"align/").mkdir();
			}			
			WriteRecordsToxls witerRecords = new WriteRecordsToxls();
			witerRecords.writeRecordsToxls(theRecordList, rootPath +"align/" +page＿Url.get(record_index)+ "_label.xls");
			System.out.println(page＿Url.get(record_index) + " Done!");
			
			break;//只测试一个文件后退出，并未开发完毕
			//产生特征文件
		}
	}
	//手工标注
	public void run_humanLable(String path, List<Label_info> label_list){
		setRootPath(path);
		loadRecordAlignFile();
		loadHtmlPathFile();
		System.out.println(label_list.size() + " Done!------------------------------------");
		
		if(!new File(rootPath +"align2/").exists()){
			new File(rootPath +"align2/").mkdir();
		}
		if(!new File(rootPath +"trainfile/").exists()){
			new File(rootPath +"trainfile/").mkdir();
		}
		BufferedWriter pw = null;
		try {
			 pw = new BufferedWriter(new FileWriter(rootPath +"trainfile/train_data"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
		}
		
		for(int i = 0; i < this.recordList.size(); i++){
			List<HeadTable> the_Record = this.recordList.get(i);		
			for(int j = 0; j < label_list.size(); j++){
				Label_info temp_label = label_list.get(j);
				addLabel(the_Record, temp_label);
			}
			WriteRecordsToxls witerRecords = new WriteRecordsToxls();
			witerRecords.writeRecordsToxls(the_Record, rootPath +"align2/" +page＿Url.get(i)+ "_label.xls");
			System.out.println(page＿Url.get(i) + " Done!");
			//输出训练数据特征
//			outputFeature(the_Record, label_list,pw);
			//添加一类 others类
//			outputFeature2(the_Record, label_list,pw);
			//添加一类 others类 样本均衡 ，产生特征文件
			outputFeature3(the_Record, label_list,pw);
		}
		try {
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("can't close file " + rootPath +"trainfile/train_data");
			e.printStackTrace();
		}
	}
	//产生特征文件
	public void outputFeature(List<HeadTable> the_Record, List<Label_info> label_list, BufferedWriter pw ){
		
		for(int i = 0; i < label_list.size(); i++){
			List<String> the_data = new ArrayList();
			String label = label_list.get(i).Label_name;
			int deepth = -1;
			for(int j = 0; j < the_Record.size(); j++){
				String headString = the_Record.get(j).headString;	
				if(headString.equals(label)){					
					the_data = getListString(the_Record.get(j).listString);
					int label_id = label_list.get(i).getLabel_id();
					deepth = j;
					outputFeatureToFile(label_id,the_data, deepth, the_Record.size(),pw);
				}
			}

		}
	}
	//产生特征文件
	public void outputFeature2(List<HeadTable> the_Record, List<Label_info> label_list, BufferedWriter pw ){		
		for(int i = 0; i < the_Record.size(); i++){
			List<String> the_data = new ArrayList();
			String headString = the_Record.get(i).headString;
			the_data = getListString(the_Record.get(i).listString);
			boolean flag = false;
			int deepth = i;
			int min_id = label_list.get(0).label_id;
			for(int j = 0; j < label_list.size(); j++){
				String label = label_list.get(j).Label_name;
				if(headString.equals(label) && flag == false){				
					int label_id = label_list.get(j).getLabel_id();
					outputFeatureToFile(label_id,the_data, deepth, the_Record.size(),pw);
					flag = true;
				}
				if(label_list.get(j).label_id < min_id){
					min_id = label_list.get(j).label_id;
				}
			}
			if(!flag){
				outputFeatureToFile((min_id - 1),the_data, deepth,the_Record.size(), pw);
			}
		}
	}
	//产生特征文件
	public void outputFeature3(List<HeadTable> the_Record, List<Label_info> label_list, BufferedWriter pw ){
		Set<Integer> indexSet = new HashSet<Integer>();
		for(int i = 0; i < label_list.size(); i++){
			List<String> the_data = new ArrayList<String>();	
			String label = label_list.get(i).Label_name;
			int deepth = -1;
			for(int j = 0; j < the_Record.size(); j++){
				String headString = the_Record.get(j).headString;	
				if(headString.equals(label)){		
					//产生Others类
					List<String> other_data = getOtherListString(j, the_Record, label_list, indexSet);
					the_data = getListString(the_Record.get(j).listString);
					int label_id = label_list.get(i).getLabel_id();
					deepth = j;
					outputFeatureToFile(label_id,the_data, deepth, the_Record.size(), pw);
					if(other_data != null){
						System.out.println("Index:" + (int)indexSet.toArray()[indexSet.toArray().length - 1] + "Size" + indexSet.size());
						System.out.println("Othe:" + other_data.toString());
						outputFeatureToFile(-1,other_data,(int)indexSet.toArray()[indexSet.toArray().length - 1],the_Record.size(),pw);
					}
					
				}
			}
		}
	}
	public List<String> getOtherListString(int start,List<HeadTable> the_Record,List<Label_info> label_list, Set<Integer> indexSet){
		List<String> others_string = new ArrayList<String>();
		int index = findLeft(start, the_Record, label_list, indexSet);
		if(index != -1){
			others_string = getListString(the_Record.get(index).listString);
			return others_string;
		}
		index = findRight(start, the_Record, label_list, indexSet);
		if(index != -1){
			others_string = getListString(the_Record.get(index).listString);
			return others_string;
		}
		return null;
	}
	private int findLeft(int start, List<HeadTable> the_Record, List<Label_info> label_list,  Set<Integer> indexSet){
		int length = the_Record.get(start).listString.size();
		List<String> tempList = getListString(the_Record.get(start).listString);
		int leftbegin = start - 1;
		for(; leftbegin >= 0; leftbegin--){
			int currentLength = the_Record.get(leftbegin).listString.size();
			String head = the_Record.get(leftbegin).headString;
			List<String> curentListString = getListString(the_Record.get(leftbegin).listString);
			if(currentLength >= length && !isLabel(label_list,head)){
				boolean isExite = indexSet.add(leftbegin);
				if(!curentListString.equals(tempList) && isExite){
					
					return leftbegin;
				}
				
			}
		}
		return -1;
	}
	private boolean isLabel( List<Label_info> label_list, String head){
		for(int i = 0; i < label_list.size(); i++){
			if(head.equals(label_list.get(i).Label_name)){
				return true;
			}
		}
		return false;
	}
	private int findRight(int start, List<HeadTable> the_Record, List<Label_info> label_list, Set<Integer> indexSet){
		int length = the_Record.get(start).listString.size();
		List<String> tempList = getListString(the_Record.get(start).listString);
		int rightbegin = start + 1;
		for(; rightbegin < the_Record.size(); rightbegin++){
			int currentLength = the_Record.get(rightbegin).listString.size();
			String head = the_Record.get(rightbegin).headString;
			List<String> curentListString = getListString(the_Record.get(rightbegin).listString);
			if(currentLength >= length && !isLabel(label_list,head)){
				boolean isExite = indexSet.add(rightbegin);
				if(!curentListString.equals(tempList) && isExite){
					return rightbegin;
				}
				
			}
		}
		return -1;
	}
	public void outputFeatureToFile(int label, List<String> the_data,int deepth, int width, BufferedWriter pw){
		String feature_string = getOutputFeature(label, the_data,deepth, width);
		try {
			pw.write(feature_string + "\n");		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("写训练数据出错！");
			e.printStackTrace();
			return;
		}       

	}

	public void addLabel(List<HeadTable> the_Record,Label_info temp_label){
		List<Integer> num_list = temp_label.getColumn();
		String orignalHead = temp_label.getOrignal_label();
		String label = temp_label.getLabel_name();
		int index = 0;
		for(int i = 0; i < the_Record.size(); i++){
			if(the_Record.get(i).headString.equals(orignalHead)){
				index += 1;
				if(num_list.contains(index)){
					the_Record.get(i).headString = label;
				}
			}
			
		}
	}
	public void loadResourceFile(){
		loadResultFile();
		loadRecordAlignFile();
		loadHtmlPathFile();	
	}
	public String getTime(String value){
		int index = -1;
		if(value.length() == 0){
			return "";
		}
		if(value.charAt(value.length() - 1) == 'Z'){
			for(int i = value.length() - 2; i >= 0; i--){
				if(value.charAt(i) == '.'){
					index = i;
				}
			}
		}else{
			return "";
		}
		if(index < 0){
			return "";
		}
		value = value.substring(0, index);
		String ts = value;  
		ts = ts.replace("Z", " UTC");  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z"); 
		Date dt = null;
		try{
			dt = sdf.parse(ts); 
		}
		catch(ParseException e){
			return "";
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(dt.getTime());
		SimpleDateFormat newdata = new SimpleDateFormat("yyyy MM dd HH mm ss"); 
		String realTime = newdata.format(cal.getTime());
		return realTime;
	}
	public String getRegisterTime(String value){
		String time = value;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd H:mm:ss");
		   Date date = null;
		   try {
		    date = format.parse(time);
		   } catch (ParseException e) {
			   return "";
		   }
		   SimpleDateFormat format2 = new SimpleDateFormat("yyyy MM dd");  
		   String reg = format2.format(date.getTime());
		   System.err.println(reg);
		   return reg;
	}
	public void changeModle(List<HeadTable> theRecordList, String label, String value){
		//打标签
		for(int i = 0; i < theRecordList.size(); i++){		
			if(theValueisInHeadTable(theRecordList.get(i), value)){
				System.out.println(i + " " +theRecordList.get(i).headString + " Change!");
				theRecordList.get(i).headString = label;
			}
		}
		
	}
	//比较过程
	public boolean theValueisInHeadTable(HeadTable headTable, String value){
		if(headTable.isIntheHeadTable(value)){
			return true;
		}
		return false;
	}
	public String getTheHtmlRecord(String url){
		for(int i = 0; i < pathPathUrl.size();i++){
			PathUrl transvertUrl = pathPathUrl.get(i);
			if(transvertUrl.url.equals(url)){
				return transvertUrl.htmlPath;
			}
		}
		return "";
	}
	public int getIndexOfRecordList(String html_path){
		for(int i = 0; i < page＿Url.size(); i++){
			File theFIle = new File(html_path);
			if(theFIle.getName().equals(page＿Url.get(i))){
				return i;
			}
		}
		return -1;
	}
	
	
	
}
