package records.classification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.svm.svm_predict;
import lib.svm.svm_scale;
import libsvm.svm;
import libsvm.svm_model;
import net.sf.json.JSONArray;

import com.wolf_datamining.autoextracting.roadrunner.application.AlignEngine;
import com.wolf_datamining.autoextracting.roadrunner.application.CleanHtml;
import com.wolf_datamining.autoextracting.roadrunner.application.ExtractFromXhtml;
import com.wolf_datamining.autoextracting.roadrunner.application.ExtractJsonToHtml;
import com.wolf_datamining.autoextracting.roadrunner.application.HeadTable;
import com.wolf_datamining.autoextracting.roadrunner.application.HtmlObject;
import com.wolf_datamining.autoextracting.roadrunner.application.LayerString;
import com.wolf_datamining.autoextracting.roadrunner.application.Object_Json_File;
import com.wolf_datamining.autoextracting.roadrunner.application.WriteRecordsToxls;
import com.wolf_datamining.autoextracting.roadrunner.preprocess.Html2XhtmlByNeko;

public class Label_predictData extends Label_data{
	public List<Label_info> label_list;
	public List<List<HeadTable>> labelRecords_lists;
	public svm_model model;
	public svm_scale svmScale;
	public String labeljson;
	public void init(String rootPath){
		labelRecords_lists = new ArrayList<List<HeadTable>>();
		this.rootPath = rootPath;
	}
	public void load_svn_Module(String svm_moduleFile, List<Label_info> label_list){
		File svmModuleFile = new File(svm_moduleFile);
		if(!svmModuleFile.exists()){
			System.out.println("SVM模型文件不存在："+svm_moduleFile);
		}
		if(label_list.size() <=1){
			System.out.println("分类标签个数太少：："+label_list.size());
		}
		
		this.label_list = label_list;
		try {
			model = svm.svm_load_model(svm_moduleFile);
			if (model == null)
			{
				System.err.print("can't open model file "+svm_moduleFile+"\n");
				System.exit(1);
			}
		} catch (IOException e) {
			System.err.print("can't open model file "+svm_moduleFile+"\n");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadsvm_scale(String scale_paramatesr){
		svmScale = new svm_scale();
		try {
			svmScale.setScaleParametes(scale_paramatesr);
		} catch (IOException e) {
			System.err.println("无法加载缩放参数文件："+scale_paramatesr);
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
			return;
		}
		
	}

	//对其数据记录
	public void alignRecordFile(){
		this.loadRecordAlignFile();		
	}
	public List<Double> classficationAttribute(){	
		
		List<Double> accuracy = new ArrayList<Double>();
		for(int i =0; i < this.label_list.size(); i++){
			accuracy.add(0.0);
		}
		//完全正确率
		accuracy.add(0.0);
		
		for(int i = 0; i < this.recordList.size(); i++){
			System.out.print("|");
			List<HeadTable> headTable_list = this.recordList.get(i);		 
			for(int j = 1; j < headTable_list.size(); j++){
				//每一列进行分类
				String future_column = getfeature(j, headTable_list.size(),headTable_list.get(j));
				String scaleFeature = getScaleFeature(future_column);
//				System.out.println(scaleFeature);
				double[] predict_args = svm_predict.predictSingle(this.model, scaleFeature);
				double label = predict_args[0];
				double label_pro = predict_args[1];
				String label_name = getLabelTrueName(new Double(label).intValue());
				
//				headTable_list.get(j).headString = label_name + ":" + label_pro;
//				writeRecords.get(j).headString =  label_name + ":" + label_pro;
				//
				headTable_list.get(j).lableName = label_name;
				headTable_list.get(j).possibility = label_pro;
			}
//			 writeLabelRecordsToxls(headTable_list , i);//
			 
			 List<HeadTable> orignalRecords = this.recordList.get(i);
			 List<HeadTable> labelRecords = outputLabelRecords(headTable_list);
			 judgeAccuracy(orignalRecords,labelRecords, accuracy);
			 
			 
//			 System.out.println("===Write algin record file===");
			 writeLabelRecordsToxls2(labelRecords , i);//可以先选择不写出
//			 System.out.println("---Write Done---");
			 
			 labelRecords_lists.add(labelRecords);	
		}
		System.out.println();
		//写出json文件
		setLabelRecordsjson();
		
		for(int i = 0; i < accuracy.size(); i++){
			double rightNum = accuracy.get(i)/this.recordList.size();
			accuracy.set(i, rightNum);
		}
		return accuracy;
	}
	public void setLabelRecordsjson(){
		List<Label_records> label_records_list = new ArrayList<Label_records>();
		for(int i = 0; i < this.labelRecords_lists.size(); i++){
			 List<HeadTable> labelRecords = this.labelRecords_lists.get(i);
			 Label_records records = new Label_records();
			 records.url = getHtmlUrl(this.page＿Url.get(i));
			 int recordsNum = getRecordsNum(labelRecords);	
			 for(int j = 1; j <= recordsNum; j++){
				 Map<String,String> record_attribute =new HashMap<String,String>();
				 for(int k = 0; k < labelRecords.size(); k++){
					 record_attribute.put(labelRecords.get(k).headString, labelRecords.get(k).getListString(j));
				 }
				 records.records.add(record_attribute);
			 }		
			 label_records_list.add(records);
		}
		JSONArray jsonArray = JSONArray.fromObject(label_records_list);
		this.labeljson = jsonArray.toString();
	}
	public String getLabelRecordsjson(){
		return this.labeljson;
	}
	public String getScaleFeature(String feature){
		String scale_string = "";
		try {
			scale_string =  this.svmScale.run_scale(feature);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("缩放特征错误" +feature);
			e.printStackTrace();
		}
		return scale_string;
	}
	private void judgeAccuracy(List<HeadTable> orignalRecords,List<HeadTable> labelRecords, List<Double> accuracy){
		int realSize = 0;
		int rigthSize = 0;
		for(int i =0; i< this.label_list.size(); i++){
			if(this.label_list.get(i).orignal_label.isEmpty()){
				continue;
			}
			for(int k = 0; k < labelRecords.size(); k++){	
				List<String> currentListString = this.getListString(labelRecords.get(k).listString);
				List<String> compareListString = getListStringByHead(orignalRecords, this.label_list.get(i));
				if(this.label_list.get(i).orignal_label.equals(labelRecords.get(k).lableName) || currentListString.equals(compareListString)){
					double num = accuracy.get(i) + 1.0;
					accuracy.set(i, num);
					rigthSize++;
				}
			}
			realSize++;
		}
		if(realSize == rigthSize){
			double num = accuracy.get(accuracy.size() - 1) + 1.0;
			accuracy.set(accuracy.size() - 1, num);
		}
	}
	private List<String> getListStringByHead(List<HeadTable> orignalRecords,Label_info label_info){
		List<Integer> num_list = label_info.getColumn();
		int index = 0;
		for(int i = 0; i < orignalRecords.size(); i++){
			if(orignalRecords.get(i).headString.equals(label_info.orignal_label)){
				index += 1;
				if(num_list.contains(index)){
					return this.getListString(orignalRecords.get(i).listString);
				}
			}
			
		}
		return null;
	}
	//可以重构为写为json文件
	public void writeLabelRecordsToxls(List<HeadTable> the_Record, int url_index){
		if(!new File(rootPath +"labelRecords/" ).exists()){
			new File(rootPath +"labelRecords/" ).mkdir();
		}
		WriteRecordsToxls witerRecords = new WriteRecordsToxls();
		witerRecords.writeRecordsToxls(the_Record, rootPath +"labelRecords/" + page＿Url.get(url_index)+ "_labelRecords_pre.xls");
		System.out.println(page＿Url.get(url_index) + " Done!");
	}
	public void writeLabelRecordsToxls2(List<HeadTable> the_Record, int url_index){
		if(!new File(rootPath +"labelRecords/" ).exists()){
			new File(rootPath +"labelRecords/" ).mkdir();
		}
		WriteRecordsToxls witerRecords = new WriteRecordsToxls();
		witerRecords.writeRecordsToxls(the_Record, rootPath +"labelRecords/" + page＿Url.get(url_index)+ "_labelRecords_solved.xls");
	}
	public void writeLabelRecordsTojson(String path, String json){
		OutputStream outstream = null;
		try {
			outstream = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		OutputStreamWriter writer = null;
		try {
			writer = new OutputStreamWriter(outstream,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private int getRecordsNum(List<HeadTable> labelRecords){
		int maxNum = 0;
		for(int i = 0; i < labelRecords.size(); i++){
			for(int j = 0; j < labelRecords.get(i).listString.size(); j++){
				if(labelRecords.get(i).listString.get(j).layerNumber > maxNum){
					maxNum = labelRecords.get(i).listString.get(j).layerNumber;
				}
			}
		}
		return maxNum;
	}
	
	public String getHtmlUrl(String html){
		for(int i = 0; i < this.pathPathUrl.size(); i++){
			String wholeFileName = this.rootPath + "html/" +html;
			PathUrl pageurl = this.pathPathUrl.get(i);	
			if(pageurl.htmlPath.equals(wholeFileName)){
				return pageurl.url;
			}
		}
		return "";
	}
	public List<HeadTable> outputLabelRecords(List<HeadTable> headTable_list){
		
		List<HeadTable> labelRecords = new ArrayList<HeadTable>();
		for(int i =0; i < this.label_list.size(); i++){
			int index = -1;
			double max_prob = -1;
			for(int j = 1; j < headTable_list.size();j++){
				if(headTable_list.get(j).lableName.equals(this.label_list.get(i).Label_name)){
					if(headTable_list.get(j).possibility > max_prob){
						max_prob = headTable_list.get(j).possibility;
						index = j;
					}
				}
				
			}
			if(index != -1){
				String oldHead = headTable_list.get(index).headString;
				headTable_list.get(index).headString = this.label_list.get(i).Label_name;
				headTable_list.get(index).lableName = oldHead;
				labelRecords.add(headTable_list.get(index));
			}
		}
		return labelRecords;
	}
	public String getLabelTrueName(int label_id){
		for(int i = 0; i < this.label_list.size(); i++){
			if(this.label_list.get(i).label_id == label_id){
				return this.label_list.get(i).Label_name;
			}
		}
		return "#";
	}
	public String getfeature(int deepth, int width, HeadTable headTable){
		List<String> the_data_string = getListString(headTable.listString);
		int pre_label = this.label_list.get(0).label_id;
		String feature = getOutputFeature(pre_label, the_data_string,deepth, width); 
		return feature;
	}
	//使用wrapper抓取网页数据记录
	public void loadResourcesFile(String WrapperFile){
		System.out.println("----------Load Wrapper---------");
		ExtractFromXhtml extracter = new ExtractFromXhtml();
		String extractXml = this.rootPath +"xhtml/";
		String resourcesOutput = this.rootPath + "output/";
		if(!new File(extractXml).exists()){
			System.out.println("网页xml文件不存在："+extractXml);
		}
		if(!new File(resourcesOutput).exists()){
			new File(resourcesOutput).mkdir();
		}
		extracter.extractAll(WrapperFile,extractXml, resourcesOutput);
	}
	public List<HtmlObject> loadHtmlJson(String HtmlJsonFilePath){
		File htmlJson = new File(HtmlJsonFilePath);
		if(!htmlJson.exists()){
			System.out.println("网页json文件不存在："+HtmlJsonFilePath);
			return null;
		}
		ExtractJsonToHtml extractHtml = new ExtractJsonToHtml(HtmlJsonFilePath);//解析json文件为html	
		return extractHtml.convertToHtml();
	}
	public void convertToXml(String extractHtmlPath,String xhtmlPath){
		System.out.println("----------convert Html to xhtml----------");
		Html2XhtmlByNeko converter = new Html2XhtmlByNeko(); 
		converter.setOutputEncoding("utf-8"); 
		converter.setInputEncoding("utf-8"); // 如果HTML没有声明编码，这个一定要设置，否则不需要设置
		File xhtmlFile = new File(xhtmlPath);
		if(!xhtmlFile.exists()){
			xhtmlFile.mkdir();
		}
		converter.convertAll(extractHtmlPath, xhtmlPath);
		System.out.println("----------convert end----------");
	}
	//预处理工作
	public void preWork(String HtmlJsonFileName){
		List<HtmlObject> htmlist = loadHtmlJson(HtmlJsonFileName);	
		System.out.println("======cleaning html=====");
		String extractHtmlPath = this.rootPath + "html/";	
		CleanHtml clean = new CleanHtml(htmlist);
		clean.doClean();
		clean.writecleanedHtml(extractHtmlPath);
		System.out.println("-------clean end-------");
		this.loadHtmlPathFile();
		String xhtmlPath = this.rootPath +"xhtml/";
		convertToXml(extractHtmlPath,xhtmlPath);//转化为xhtml格式
	}
	//rootpath是根目录文件，htmlJsonFileName是文件完整路径
	public void run(String rootPath, String HtmlJsonFileName,String svm_moduleFile, String svm_scaleParamaters,String WrapperFile, List<Label_info> label_list){
		//加载要输入的htmljson文件
		this.init(rootPath);
		loadsvm_scale(svm_scaleParamaters);	
		preWork(HtmlJsonFileName);
		loadResourcesFile(WrapperFile);
		alignRecordFile();
		load_svn_Module(svm_moduleFile, label_list);
		System.out.println("======classify=====");
		List<Double> accuracy  = classficationAttribute();
		System.out.println("----classify end---");
		System.out.println("=====================Accuracy=====================");
		for(int i = 0 ; i < label_list.size(); i++){
			if(!label_list.get(i).orignal_label.isEmpty()){
				System.out.printf("%-15s%-15s%-20s\n",label_list.get(i).Label_name, "accuracy:",accuracy.get(i));
			}	
		}
		System.out.printf("%-15s%-15s%-20s\n","Whole","accuracy:",accuracy.get(accuracy.size() - 1));
	}
}
