package records.classification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wolf_datamining.autoextracting.roadrunner.application.AlignEngine;
import com.wolf_datamining.autoextracting.roadrunner.application.HeadTable;
import com.wolf_datamining.autoextracting.roadrunner.application.LayerString;
import com.wolf_datamining.autoextracting.roadrunner.application.Object_Json_File;

public class Label_data {
	public  String rootPath;
	public  List<String> page＿Url;
	public  List<List<HeadTable>> recordList;
	public String human_extract_json;
	public String resultFile_name;
	public  List<PathUrl> pathPathUrl;
	
	public Label_data(){
		this.page＿Url = new ArrayList();
		this.recordList = new ArrayList<List<HeadTable>>();
		this.pathPathUrl = new ArrayList<PathUrl>();
	}
	//j
	public void loadResultFile(){
		try{
			Object_Json_File human_extract_result = new Object_Json_File(rootPath + this.resultFile_name);
			human_extract_json = human_extract_result.getJsonString();
		}catch(Exception e){
			System.err.println("标注数据不存在，应该在根目录文件夹下");
			return;
		}

	}
//	对齐数据记录文件;
	public void loadRecordAlignFile(){
		System.out.println("正在对其数据记录文件...");
		File resFile = new File(rootPath + "output/");
		String outputAlignRecordsPath = rootPath + "Records/";
		if(!resFile.exists()){
			System.err.println("输出对齐的数据记录文件夹不存在，应该在根目录的Records文件夹");
			return;
		}
		File [] resFiles = resFile.listFiles();
		if(resFiles.length == 0){
			System.err.println("资源文件夹不存在，应该根目录的 output文件夹");
			return;
		}
		File recordFile = new File(outputAlignRecordsPath);
		if(!recordFile.exists()){
			recordFile.mkdir();
		}
		
		for(int i = 0; i < resFiles.length; i++){
			if(!resFiles[i].getName().endsWith(".xml")){
				continue;
			}
			try{
				AlignEngine  engine = new AlignEngine();				
				engine.outputAlignedRecordsPath = outputAlignRecordsPath;
				engine.alignCore(resFiles[i], true, "");
				List<HeadTable> the_Record = engine.headTable;
				String the_pageUrl = resFiles[i].getName().replaceAll("res_", "").replaceAll("\\.html-to-\\.xml", "\\.html");
				//数据记录文件加入
				recordList.add(the_Record);
				page＿Url.add(the_pageUrl);
				
			}catch(Exception e){
				System.err.println("A Resouces file is Wrong: " + resFiles[i].getAbsolutePath());
				e.printStackTrace();
			}
		}	
		System.out.println("对齐数据记完成！");
	}
	public void loadHtmlPathFile(){
		System.out.println("正在加载html路径文件...");
		try{
			Object_Json_File htmlConvertJson = new Object_Json_File( rootPath + "html/htmlPathJson.json");
			String convert = htmlConvertJson.getJsonString();
			JSONArray jsonArray = JSONArray.fromObject(convert);
			for(int i = 0; i < jsonArray.size(); i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String htmlPath =  jsonObject.getString("htmlpath");
				String url = jsonObject.getString("url");
				PathUrl pageurl = new PathUrl();
				pageurl.htmlPath = htmlPath;
				pageurl.url = url;
				pathPathUrl.add(pageurl);
			}
			System.out.println("加载html路径文件完成！");
		}catch(Exception e){
			System.err.println("请检查路径文件，应该为根路径的 html/htmlPathJson.json！");
			e.printStackTrace();
			return;
		}
	}
	public List<String> getListString(List<LayerString> listString){
		List<String> resultString = new ArrayList();
		for(int i = 0; i < listString.size(); i++){
			LayerString theLayer = listString.get(i);
			resultString.add(theLayer.layerString);
		}
		return resultString;
	}
	public String getOutputFeature(int label, List<String> the_data,int deepth, int width){
		List<Double> featureData = new ArrayList();
		Data_feature feature = new Data_feature();
		feature.stringList = the_data;
		
		//feature1-数字个数		
		double average_count = feature.getString_average_num_count();
		featureData.add(average_count);		
		//feature2-数字个数	
		double variance_count = feature.getString_variance_num_count();
		featureData.add(variance_count);
		
		//feature3-中文个数
		double average_Chinese = feature.getSting_average_chines_word_count();
		featureData.add(average_Chinese);	
		//feature4-中文个数
		double variance_Chinese = feature.getString_variance_chines_word_count();
		featureData.add(variance_Chinese);
		
		//feature5-非中文英文个数
		double average_NoWord = feature.getString_average_none_word_count();
		featureData.add(average_NoWord);
		//feature6-非中文英文个数
		double variance_NoWord = feature.getString_variance_none_word_count();
		featureData.add(variance_NoWord);
		
		//feature7-字符串长度
		double average_Length = feature.getString_average_length();
		featureData.add(average_Length);
		//feature8-字符串长度
		double varance_Length = feature.getString_variance_length();
		featureData.add(varance_Length);
		
		//feature9-英文个数
		double average_English = feature.getString_average_english_word_count();
		featureData.add(average_English);
		//feature10-英文个数
		double varance_English = feature.getString_variance_english_word_count();
		featureData.add(varance_English);
		
		//feature11-是否含有发表时间固定格式字符串
		double average_PublishTimefixString = feature.getString_average_publictimeFixString();
		featureData.add(average_PublishTimefixString);
		//feature12含有发表时间格式
		double average_publisTime = feature.getStirng_average_publisTime();
		featureData.add(average_publisTime);
		
		//feature13字符串长度大于10所占的比例
		double len_lessThanTen = feature.getString_stringLenghtLessThanTenRat();
		featureData.add(len_lessThanTen);
		//feature14字符串长度大于10所占的比例
		double len_lessThanSix = feature.getString_stringLenghtLessThanSixRat();
		featureData.add(len_lessThanSix);
		//feature14字符串长度大于10所占的比例
		double len_lessThanSixteen = feature.getString_stringLenghtLessThanSixteenRat();
		featureData.add(len_lessThanSixteen);
		
		//feature14-是否含有注册时间固定格式字符串
		double average_RegistimefixString = feature.getString_average_RegisterTimeString();
		featureData.add(average_RegistimefixString);
		//feature15 含有注册时间格式
		double average_registime = feature.getStirng_average_registTime();
		featureData.add(average_registime);

		
		//feature16-是否含有楼层固定格式字符串
		double average_FloorFixString = feature.getString_average_FloorFixString();
		featureData.add(average_FloorFixString);
		//feature17-是否含有连续数字
		double average_NumseaquenceString = feature.getString_FloorNumFeatureString();
		featureData.add(average_NumseaquenceString);
		//feature18-楼层开始数
		double floor_startNum = feature.getString_FloorNumStart();
		featureData.add(floor_startNum);
		//feature19-楼层结束数
		double floor_endNum = feature.getString_FloorNumEnd();
		featureData.add(floor_endNum);
		//feature20-最大楼层和最小楼层的差
		double floor_gap=  feature.getString_FloorGap();
		featureData.add(floor_gap);

		
		
		//feature21-字符串相似比
		double string_simility = feature.getString_sameSimility();
		featureData.add(string_simility);
		//feature22-行数
		double string_size = feature.getString_size();
		featureData.add(string_size);
		//feature23-深度
		if(deepth != -1){
			featureData.add(deepth*1.0/width);
		}	
		String feature_string = label + " ";
		for(int i = 0; i < featureData.size(); i++){
			if((featureData.get(i) + "").equals("0.0"))continue;
			feature_string += (i + 1) + ":" + featureData.get(i) + " ";
		}
		return feature_string;
	}
}
