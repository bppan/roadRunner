package records.classification;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.wolf_datamining.autoextracting.roadrunner.application.AlignEngine;
import com.wolf_datamining.autoextracting.roadrunner.application.HeadTable;
import com.wolf_datamining.autoextracting.roadrunner.application.Object_Json_File;
import com.wolf_datamining.autoextracting.roadrunner.application.WriteRecordsToxls;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Main_json {
	public static List<List<HeadTable>> recordList;
	public static List<String> page＿Url;
	public static List<PathUrl> pathPathUrl;
	public static String rootPath = "C:/Users/Administrator/Desktop/ExtractRecordData/9/";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Label_predictData predicRecord = new Label_predictData();
		List<Label_info> label_list = new ArrayList();
		Label_info  label_info1 = new Label_info();
		label_info1.orignal_label = "spanidh1classtdclasstrtbodytablecellpaddingcellspacingdivclassidsubtreeandinstancesourcenamebodyhtml#document";
		label_info1.Label_name= "Title";
		label_info1.column.add(1);	
		label_info1.setLabel_id(0);
		
		Label_info  label_info2 = new Label_info();
		
		label_info2.orignal_label = "aclasshrefreltargetdivclassdivclassdivclassidtdclassrowspantrtbodytablecellpaddingcellspacingclassidsummarydividdivclassidsubtreeandinstancesourcenamebodyhtml#document";
		label_info2.Label_name= "User";
		label_info2.column.add(1);
		label_info2.setLabel_id(1);
		
		Label_info  label_info3 = new Label_info();
		label_info3.orignal_label = "";
		label_info3.Label_name= "ReservedField4";
		label_info3.column.add(0);
		label_info3.setLabel_id(2);
		
		Label_info  label_info4 = new Label_info();
		label_info4.orignal_label = "emiddivclassdivclassdivclasstdclasstrtbodytablecellpaddingcellspacingclassidsummarydividdivclassidsubtreeandinstancesourcenamebodyhtml#document";
		label_info4.Label_name= "PublishTime";
		label_info4.column.add(1);
		label_info4.setLabel_id(3);
		
		Label_info  label_info5 = new Label_info();
		label_info5.orignal_label = "tdclassidtrtbodytablecellpaddingcellspacingdivclassdivclassdivclasstdclasstrtbodytablecellpaddingcellspacingclassidsummarydividdivclassidsubtreeandinstancesourcenamebodyhtml#document";
		label_info5.Label_name= "MainContent";
		label_info5.column.add(1);
		label_info5.setLabel_id(4);
		
		Label_info  label_info6 = new Label_info();
		label_info6.orignal_label = "emahrefidonclickrelstrongdivclasstdclasstrtbodytablecellpaddingcellspacingclassidsummarydividdivclassidsubtreeandinstancesourcenamebodyhtml#document";
		label_info6.Label_name= "Floor";
		label_info6.column.add(1);
		label_info6.setLabel_id(5);
		
		
		//网站8标注数据
//		Label_info  label_info1 = new Label_info();
//		label_info1.orignal_label = "attributelabelandinstancesourcenamebodyhtml#document";
//		label_info1.Label_name= "Title";
//		label_info1.column.add(2);	
//		label_info1.setLabel_id(0);
//	
//		Label_info  label_info2 = new Label_info();
//		label_info2.orignal_label = "ahreftargeth3divclassdivclassiddivclasssubtreeandinstancesourcenamebodyhtml#document";
//		label_info2.Label_name= "User";
//		label_info2.column.add(1);
//		label_info2.setLabel_id(1);			
//		
//		Label_info  label_info3 = new Label_info();
//		label_info3.orignal_label = "";
//		label_info3.Label_name= "ReservedField4";
//		label_info3.column.add(0);
//		label_info3.setLabel_id(2);
//
//		Label_info  label_info4 = new Label_info();
//		label_info4.orignal_label = "divclassdivclassiddivclasssubtreeandinstancesourcenamebodyhtml#document";
//		label_info4.Label_name= "PublishTime";
//		label_info4.column.add(1);
//		label_info4.setLabel_id(3);
//		
//		Label_info  label_info5 = new Label_info();
//		label_info5.orignal_label = "pdivclassdivclassdivclassiddivclasssubtreeandinstancesourcenamebodyhtml#document";
//		label_info5.Label_name= "MainContent";
//		label_info5.column.add(1);
//		label_info5.setLabel_id(4);
//		
//		Label_info  label_info6 = new Label_info();
//		label_info6.orignal_label = "h3divclassdivclassiddivclasssubtreeandinstancesourcenamebodyhtml#document";
//		label_info6.Label_name= "Floor";
//		label_info6.column.add(1);
//		label_info6.setLabel_id(5);
//		

		
		
//		网站7标注数据
//		Label_info  label_info1 = new Label_info();
//		label_info1.orignal_label = "h3divclassdivclasssubtreeandinstancesourcenamebodyhtml#document";
//		label_info1.Label_name= "Title";
//		label_info1.column.add(3);	
//		label_info1.setLabel_id(0);
//		
//		Label_info  label_info2 = new Label_info();
//		label_info2.orignal_label = "ahreftargetdivclassiddivclasssubtreeandplusandinstancesourcenamebodyhtml#document";
//		label_info2.Label_name= "User";
//		label_info2.column.add(1);
//		label_info2.setLabel_id(1);
//			
//		Label_info  label_info3 = new Label_info();
//		label_info3.orignal_label = "";
//		label_info3.Label_name= "ReservedField4";
//		label_info3.column.add(0);
//		label_info3.setLabel_id(2);
//		
//		Label_info  label_info4 = new Label_info();
//		label_info4.orignal_label = "attributelabelandplusandinstancesourcenamebodyhtml#document";
//		label_info4.Label_name= "PublishTime";
//		label_info4.column.add(1);
//		label_info4.setLabel_id(3);
//		
//		Label_info  label_info5 = new Label_info();
//		label_info5.orignal_label = "divclasssubtreeandhookandplusandinstancesourcenamebodyhtml#document";
//		label_info5.Label_name= "MainContent";
//		label_info5.column.add(1);
//		label_info5.setLabel_id(4);
//		
//		Label_info  label_info6 = new Label_info();
//		label_info6.orignal_label = "attributelabelandplusandinstancesourcenamebodyhtml#document";
//		label_info6.Label_name= "Floor";
//		label_info6.column.add(1);
//		label_info6.setLabel_id(5);
		
		//网站6标注数据
//		Label_info  label_info1 = new Label_info();
//		label_info1.orignal_label = "ahrefidh1classtdclasstrtbodytablecellpaddingcellspacingdivclassidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info1.Label_name= "Title";
//		label_info1.column.add(1);	
//		label_info1.setLabel_id(0);
//		
//		Label_info  label_info2 = new Label_info();
//		label_info2.orignal_label = "aclasshreftargetdivclassdivclasstdclassrowspantrtbodytablecellpaddingcellspacingidsummarydividdivclassidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info2.Label_name= "User";
//		label_info2.column.add(1);
//		label_info2.setLabel_id(1);
//			
//		Label_info  label_info3 = new Label_info();
//		label_info3.orignal_label = "dddlclassdivclassdivclassidstyletdclassrowspantrtbodytablecellpaddingcellspacingidsummarydividdivclassidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info3.Label_name= "ReservedField4";
//		label_info3.column.add(2);
//		label_info3.setLabel_id(2);
//		
//		Label_info  label_info4 = new Label_info();
//		label_info4.orignal_label = "emiddivclassdivclassdivclasstdclasstrtbodytablecellpaddingcellspacingidsummarydividdivclassidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info4.Label_name= "PublishTime";
//		label_info4.column.add(1);
//		label_info4.setLabel_id(3);
//		
//		Label_info  label_info5 = new Label_info();
//		label_info5.orignal_label = "tdclassidtrtbodytablecellpaddingcellspacingdivclassdivclassdivclasstdclasstrtbodytablecellpaddingcellspacingidsummarydividdivclassidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info5.Label_name= "MainContent";
//		label_info5.column.add(1);
//		label_info5.setLabel_id(4);
//		
//		Label_info  label_info6 = new Label_info();
//		label_info6.orignal_label = "emahrefidonclicktitlestrongdivclasstdclasstrtbodytablecellpaddingcellspacingidsummarydividdivclassidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info6.Label_name= "Floor";
//		label_info6.column.add(1);
//		label_info6.setLabel_id(5);
		
		//网站3标注数据 
//		Label_info  label_info1 = new Label_info();
//		label_info1.orignal_label = "spanidh1classtdclasstrtbodytablecellpaddingcellspacingdivclassiddivclassiddivclassidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info1.Label_name= "Title";
//		label_info1.column.add(1);	
//		label_info1.setLabel_id(0);
//		
//		Label_info  label_info2 = new Label_info();
//		label_info2.orignal_label = "aclasshreftargetdivclassdivclassdivclassidtdclassrowspantrtbodytablecellpaddingcellspacingclassidsummarydividdivclassiddivclassiddivclassidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info2.Label_name= "User";
//		label_info2.column.add(1);
//		label_info2.setLabel_id(1);
//			
//		Label_info  label_info3 = new Label_info();
//		label_info3.orignal_label = "dddlclassdivclassidtdclassrowspantrtbodytablecellpaddingcellspacingclassidsummarydividdivclassiddivclassiddivclassidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info3.Label_name= "ReservedField4";
//		label_info3.column.add(2);
//		label_info3.setLabel_id(2);
//		
//		Label_info  label_info4 = new Label_info();
//		label_info4.orignal_label = "emiddivclassdivclassdivclasstdclasstrtbodytablecellpaddingcellspacingclassidsummarydividdivclassiddivclassiddivclassidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info4.Label_name= "PublishTime";
//		label_info4.column.add(1);
//		label_info4.setLabel_id(3);
//		
//		Label_info  label_info5 = new Label_info();
//		label_info5.orignal_label = "tdclassidtrtbodytablecellpaddingcellspacingdivclassdivclassdivclasstdclasstrtbodytablecellpaddingcellspacingclassidsummarydividdivclassiddivclassiddivclassidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info5.Label_name= "MainContent";
//		label_info5.column.add(1);
//		label_info5.setLabel_id(4);
//		
//		Label_info  label_info6 = new Label_info();
//		label_info6.orignal_label = "emahrefidonclickstrongdivclasstdclasstrtbodytablecellpaddingcellspacingclassidsummarydividdivclassiddivclassiddivclassidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info6.Label_name= "Floor";
//		label_info6.column.add(1);
//		label_info6.setLabel_id(5);
		
		//网站2标注数据 
//		Label_info  label_info1 = new Label_info();
//		label_info1.orignal_label = "attributelabelandinstancesourcenamebodyhtml#document";
//		label_info1.Label_name= "Title";
//		label_info1.column.add(4);	
//		label_info1.setLabel_id(0);
//		
//		Label_info  label_info2 = new Label_info();
//		label_info2.orignal_label = "aclasshrefonclickreltargettdclassrowspanstylevalignwidthtrtbodytablealigncellpaddingcellspacingclassidwidthdivclassstylesubtreeandplusandinstancesourcenamebodyhtml#document";
//		label_info2.Label_name= "User";
//		label_info2.column.add(1);
//		label_info2.setLabel_id(1);
//			
//		Label_info  label_info3 = new Label_info();
//		label_info3.orignal_label = "pstyledivclasstdclassrowspanstylevalignwidthtrtbodytablealigncellpaddingcellspacingclassidwidthdivclassstylesubtreeandplusandinstancesourcenamebodyhtml#document";
//		label_info3.Label_name= "ReservedField4";
//		label_info3.column.add(3);
//		label_info3.setLabel_id(2);
//		
//		Label_info  label_info4 = new Label_info();
//		label_info4.orignal_label = "divstyledivstyletdtrtbodytablebordercellpaddingcellspacingclassstyletdstylevalignwidthtrtbodytablealigncellpaddingcellspacingclassidwidthdivclassstylesubtreeandplusandinstancesourcenamebodyhtml#document";
//		label_info4.Label_name= "PublishTime";
//		label_info4.column.add(1);
//		label_info4.setLabel_id(3);
//		
//		Label_info  label_info5 = new Label_info();
//		label_info5.orignal_label = "divclassidtdclassheightstylevaligntrtbodytablebordercellpaddingcellspacingclassstyletdstylevalignwidthtrtbodytablealigncellpaddingcellspacingclassidwidthdivclassstylesubtreeandplusandinstancesourcenamebodyhtml#document";
//		label_info5.Label_name= "MainContent";
//		label_info5.column.add(1);
//		label_info5.setLabel_id(4);
//		
//		Label_info  label_info6 = new Label_info();
//		label_info6.orignal_label = "aclasshrefonclicktitledivclassdivstyletdtrtbodytablebordercellpaddingcellspacingclassstyletdstylevalignwidthtrtbodytablealigncellpaddingcellspacingclassidwidthdivclassstylesubtreeandplusandinstancesourcenamebodyhtml#document";
//		label_info6.Label_name= "Floor";
//		label_info6.column.add(1);
//		label_info6.setLabel_id(5);
		
		label_list.add(label_info1);
		label_list.add(label_info2);
		label_list.add(label_info3);
		label_list.add(label_info4);
		label_list.add(label_info5);
		label_list.add(label_info6);
		
		//8  新车评_HtmlContent.json"
		//9  凤凰汽车_HtmlContent.json
		//7 网易汽车_HtmlContent.json
		predicRecord.run(rootPath, rootPath + "凤凰汽车_HtmlContent.json", 
				"C:/Users/Administrator/Desktop/ExtractRecordData/0/" + "model_r.txt", 
				"C:/Users/Administrator/Desktop/ExtractRecordData/0/" + "train_scale_parameters",
				"D:/workspace_roadrunner/roadrunner/output/Wrapper9/Wrapper900.xml", 
				label_list);
		String json  = predicRecord.getLabelRecordsjson();
		predicRecord.writeLabelRecordsTojson(rootPath + "labelRecords.json", json);
//数据格式转换
//		用户名  （user）
//		帖子内容 （mainContent）
//		发表时间（精确到分钟）（publishTime）
//		发帖地理位置 （reservedField5）
//		注册时间（精确到天） （reservedField4）
//		楼层数 （floor）（标注抽取的结果，楼层已经失去特征全部转化为数字）
//		主贴标题 （title）
//特征如下：
//		1、所在列数（DOM书深度）--不可用（训练数据中没有该特征值）
//		2、字符串长度- 平均长度
//		3、含有的数字个数-平均个数
//		4、含有的英文个数--平均
//		5、含有中文个数。--平均
//		6、含有非数字、英文、中文个数。--平均
//		7、分词后，词项个数（考虑发帖内容为句子形式）暂不考虑--平均
		
		//JSONObject遍历json对象 
//		Object_Json_File human_extract_result = new Object_Json_File( rootPath +"腾讯汽车_WebPageResults.json");
//		String human_extract_json = human_extract_result.getJsonString();
			
//		String json2 = "{name:'Wallace',age:15}";
//		
////		JSONObject jsonObj = JSONObject.fromObject(json2);
////		for (Iterator iter = jsonObj.keys(); iter.hasNext();) {
////			String key = (String)iter.next();
////			System.out.println(jsonObj .getString(key));
////		}
//		String json2 = "{name:'Wallace',age:15}";
////		
//		JSONArray jsonArray = JSONArray.fromObject(json2);
//		for(int i = 0; i < jsonArray.size(); i++){
//			JSONObject jsonObj = jsonArray.getJSONObject(i);
//			for (Iterator iter = jsonObj.keys(); iter.hasNext();) {
//				String key = (String)iter.next();
//				System.out.println(jsonObj .getString(key));
//			}
//		}
//读入人工抽取数据
//		Label_trainData trainData = new Label_trainData();
//		trainData.setRootPath("C:/Users/Administrator/Desktop/ExtractRecordData/6/");
//		trainData.run();
		
//		recordList = new ArrayList<List<HeadTable>>();
//		page＿Url = new ArrayList<String>();
//		pathPathUrl = new ArrayList<PathUrl>();
//		
//		getRecordsList("");
//		getHtmlPathJson();
//		
//		JSONArray jsonArray = JSONArray.fromObject(human_extract_json);
//		for(int i = 0; i < jsonArray.size(); i++){
//			JSONObject jsonObject = jsonArray.getJSONObject(i);
//			//获取URL
//			String Url =  jsonObject.getString("Url");
//			
//			String htmlpath = getTheHtmlRecord(Url);//获取转换路径
//			if(htmlpath.isEmpty()){
//				System.err.println("获取文件路径错误，---");
//				return;
//			}
//			int record_index = getIndexOfRecordList(htmlpath);//获取recordlist的所在位置
//			if(record_index < 0){
//				System.err.println("获取数据记录索引错误，---");
//				return;
//			}
//			List<HeadTable> theRecordList = recordList.get(record_index);//获取当前的record;
//			
//			String documents = jsonObject.getString("Documents");
//			JSONArray recordArray = JSONArray.fromObject(documents);	
//			for(int j = 0; j < recordArray.size(); j++){
//				JSONObject jsonObj = recordArray.getJSONObject(j);
//				for (Iterator iter = jsonObj.keys(); iter.hasNext();) {
//					String key = (String)iter.next();
//					String value = jsonObj.getString(key);
//					
//					if(value.equals("轮毂也好大")){
//						System.err.println("---" +  value);
//					}
//					
//					String time = getTime(value);
//					if(!time.isEmpty()){
//						value = time;
//					}
//					if(!getRegisterTime(value).isEmpty()){
//						value = getRegisterTime(value);
//						   System.err.println(value + "--");
//					}
//			
//					
//					changeModle(theRecordList, key,value);				
//					System.out.println(jsonObj.getString(key));
//				}
//			}
//			
//			//写出
//			WriteRecordsToxls witerRecords = new WriteRecordsToxls();
//			witerRecords.writeRecordsToxls(theRecordList, rootPath +"align/" +page＿Url.get(record_index)+ "_label.xls");
//			System.out.println(page＿Url.get(record_index) + " Done!");
//			break;
//			
//		}
		
//加载数据记录文件
		//训练svm
		//预测
		
	}



	
	

}
