package records.classification;

import java.util.ArrayList;
import java.util.List;

/*
 * 0 title 类别
 * 1 User 类别
 * 2 ReservedField4 注册时间  类别
 * 3 PublishTime 发表时间
 * 4 MainContent 发表内容
 * 5 Floor 楼层类别
 * */

public class Main_label {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Label_info> label_list = new ArrayList();
		//网站1标注数据
		Label_info  label_info1 = new Label_info();
		label_info1.orignal_label = "attributelabelandinstancesourcenamebodyhtml#document";
		label_info1.Label_name= "Title";
		label_info1.column.add(6);	
		label_info1.setLabel_id(0);
		
		Label_info  label_info2 = new Label_info();
		label_info2.orignal_label = "aclassuserNickhreftargetpsubtreeandplusandinstancesourcenamebodyhtml#document";
		label_info2.Label_name= "User";
		label_info2.column.add(1);
		label_info2.setLabel_id(1);
		
		Label_info  label_info20 = new Label_info();
		label_info20.orignal_label = "aclassuserNickhrefpsubtreeandplusandinstancesourcenamebodyhtml#document";
		label_info20.Label_name= "User";
		label_info20.column.add(1);
		label_info20.setLabel_id(1);
		
		Label_info  label_info3 = new Label_info();
		label_info3.orignal_label = "";
		label_info3.Label_name= "ReservedField4";
		label_info3.column.add(0);
		label_info3.setLabel_id(2);
		
		Label_info  label_info4 = new Label_info();
		label_info4.orignal_label = "spanpsubtreeandplusandinstancesourcenamebodyhtml#document";
		label_info4.Label_name= "PublishTime";
		label_info4.column.add(1);
		label_info4.setLabel_id(3);
		
		Label_info  label_info5 = new Label_info();
		label_info5.orignal_label = "aclasstreeReplyhreftargetpsubtreeandplusandinstancesourcenamebodyhtml#document";
		label_info5.Label_name= "MainContent";
		label_info5.column.add(1);
		label_info5.setLabel_id(4);
		
		Label_info  label_info6 = new Label_info();
		label_info6.orignal_label = "";
		label_info6.Label_name= "Floor";
		label_info6.column.add(0);
		label_info6.setLabel_id(5);
	
		label_list.add(label_info1);
		label_list.add(label_info2);
		label_list.add(label_info20);
		label_list.add(label_info3);
		label_list.add(label_info4);
		label_list.add(label_info5);
		label_list.add(label_info6);
		//网站2标注数据 
//		Label_info  label_info1 = new Label_info();
//		label_info1.orignal_label = "attributelabelandinstancesourcenamebodyhtml#document";
//		label_info1.Label_name= "Title";
//		label_info1.column.add(4);	
//		label_info1.setLabel_id(0);
//		
//		Label_info  label_info2 = new Label_info();
//		label_info2.orignal_label = "aclassboldhrefonclickreltargettdclasst_userrowspanstylevalignwidthtrtbodytablealigncellpaddingcellspacingclasst_rowidwidthdivclassF_box_2stylesubtreeandplusandinstancesourcenamebodyhtml#document";
//		label_info2.Label_name= "User";
//		label_info2.column.add(1);
//		label_info2.setLabel_id(1);
//			
//		Label_info  label_info3 = new Label_info();
//		label_info3.orignal_label = "pstyledivclasssmalltxttdclasst_userrowspanstylevalignwidthtrtbodytablealigncellpaddingcellspacingclasst_rowidwidthdivclassF_box_2stylesubtreeandplusandinstancesourcenamebodyhtml#document";
//		label_info3.Label_name= "ReservedField4";
//		label_info3.column.add(3);
//		label_info3.setLabel_id(2);
//		
//		Label_info  label_info4 = new Label_info();
//		label_info4.orignal_label = "divstyledivstyletdtrtbodytablebordercellpaddingcellspacingclasst_msgstyletdstylevalignwidthtrtbodytablealigncellpaddingcellspacingclasst_rowidwidthdivclassF_box_2stylesubtreeandplusandinstancesourcenamebodyhtml#document";
//		label_info4.Label_name= "PublishTime";
//		label_info4.column.add(1);
//		label_info4.setLabel_id(3);
//		
//		Label_info  label_info5 = new Label_info();
//		label_info5.orignal_label = "divclasst_msgfont1idtdclasslineheightstylevaligntrtbodytablebordercellpaddingcellspacingclasst_msgstyletdstylevalignwidthtrtbodytablealigncellpaddingcellspacingclasst_rowidwidthdivclassF_box_2stylesubtreeandplusandinstancesourcenamebodyhtml#document";
//		label_info5.Label_name= "MainContent";
//		label_info5.column.add(1);
//		label_info5.setLabel_id(4);
//		
//		Label_info  label_info6 = new Label_info();
//		label_info6.orignal_label = "aclassboldhrefonclicktitledivclassright t_numberdivstyletdtrtbodytablebordercellpaddingcellspacingclasst_msgstyletdstylevalignwidthtrtbodytablealigncellpaddingcellspacingclasst_rowidwidthdivclassF_box_2stylesubtreeandplusandinstancesourcenamebodyhtml#document";
//		label_info6.Label_name= "Floor";
//		label_info6.column.add(1);
//		label_info6.setLabel_id(5);

		//网站3标注数据 
//		Label_info  label_info1 = new Label_info();
//		label_info1.orignal_label = "spanidh1clasststdclassplc ptm pbn vwthdtrtbodytablecellpaddingcellspacingdivclasspl bmiddivclasswp cliddivclasswpidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info1.Label_name= "Title";
//		label_info1.column.add(1);	
//		label_info1.setLabel_id(0);
//		
//		Label_info  label_info2 = new Label_info();
//		label_info2.orignal_label = "aclassxw1hreftargetdivclassauthidivclasspidivclasspls favataridtdclassplsrowspantrtbodytablecellpaddingcellspacingclassplhinidsummarydividdivclasspl bmiddivclasswp cliddivclasswpidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info2.Label_name= "User";
//		label_info2.column.add(1);
//		label_info2.setLabel_id(1);
//			
//		Label_info  label_info3 = new Label_info();
//		label_info3.orignal_label = "dddlclasspil cldivclasspls favataridtdclassplsrowspantrtbodytablecellpaddingcellspacingclassplhinidsummarydividdivclasspl bmiddivclasswp cliddivclasswpidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info3.Label_name= "ReservedField4";
//		label_info3.column.add(2);
//		label_info3.setLabel_id(2);
//		
//		Label_info  label_info4 = new Label_info();
//		label_info4.orignal_label = "emiddivclassauthidivclassptidivclasspitdclassplctrtbodytablecellpaddingcellspacingclassplhinidsummarydividdivclasspl bmiddivclasswp cliddivclasswpidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info4.Label_name= "PublishTime";
//		label_info4.column.add(1);
//		label_info4.setLabel_id(3);
//		
//		Label_info  label_info5 = new Label_info();
//		label_info5.orignal_label = "tdclasst_fidtrtbodytablecellpaddingcellspacingdivclasst_fszdivclasspcbdivclasspcttdclassplctrtbodytablecellpaddingcellspacingclassplhinidsummarydividdivclasspl bmiddivclasswp cliddivclasswpidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info5.Label_name= "MainContent";
//		label_info5.column.add(1);
//		label_info5.setLabel_id(4);
//		
//		Label_info  label_info6 = new Label_info();
//		label_info6.orignal_label = "emahrefidonclickstrongdivclasspitdclassplctrtbodytablecellpaddingcellspacingclassplhinidsummarydividdivclasspl bmiddivclasswp cliddivclasswpidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info6.Label_name= "Floor";
//		label_info6.column.add(1);
//		label_info6.setLabel_id(5);
		
		
		//网站4标注数据 
//		Label_info  label_info1 = new Label_info();
//		label_info1.orignal_label = "spanidh1clasststdclassplc ptm pbn vwthdtrtbodytablecellpaddingcellspacingdivclasspl bmiddivclasswp cliddivclasswpidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info1.Label_name= "Title";
//		label_info1.column.add(1);	
//		label_info1.setLabel_id(0);
//		
//		Label_info  label_info2 = new Label_info();
//		label_info2.orignal_label = "aclassxw1hreftargetdivclassauthidivclasspidivclasspls favataridtdclassplsrowspantrtbodytablecellpaddingcellspacingclassplhinidsummarydividdivclasspl bmiddivclasswp cliddivclasswpidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info2.Label_name= "User";
//		label_info2.column.add(1);
//		label_info2.setLabel_id(1);
//			
//		Label_info  label_info3 = new Label_info();
//		label_info3.orignal_label = "dddlclasspil cldivclasspls favataridtdclassplsrowspantrtbodytablecellpaddingcellspacingclassplhinidsummarydividdivclasspl bmiddivclasswp cliddivclasswpidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info3.Label_name= "ReservedField4";
//		label_info3.column.add(2);
//		label_info3.setLabel_id(2);
//		
//		Label_info  label_info4 = new Label_info();
//		label_info4.orignal_label = "emiddivclassauthidivclassptidivclasspitdclassplctrtbodytablecellpaddingcellspacingclassplhinidsummarydividdivclasspl bmiddivclasswp cliddivclasswpidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info4.Label_name= "PublishTime";
//		label_info4.column.add(1);
//		label_info4.setLabel_id(3);
//		
//		Label_info  label_info5 = new Label_info();
//		label_info5.orignal_label = "tdclasst_fidtrtbodytablecellpaddingcellspacingdivclasst_fszdivclasspcbdivclasspcttdclassplctrtbodytablecellpaddingcellspacingclassplhinidsummarydividdivclasspl bmiddivclasswp cliddivclasswpidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info5.Label_name= "MainContent";
//		label_info5.column.add(1);
//		label_info5.setLabel_id(4);
//		
//		Label_info  label_info6 = new Label_info();
//		label_info6.orignal_label = "emahrefidonclickstrongdivclasspitdclassplctrtbodytablecellpaddingcellspacingclassplhinidsummarydividdivclasspl bmiddivclasswp cliddivclasswpidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info6.Label_name= "Floor";
//		label_info6.column.add(1);
//		label_info6.setLabel_id(5);
		
		
		//网站6标注数据
//		Label_info  label_info1 = new Label_info();
//		label_info1.orignal_label = "ahrefidh1clasststdclassplc ptm pbn vwthdtrtbodytablecellpaddingcellspacingdivclasspl bmidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info1.Label_name= "Title";
//		label_info1.column.add(1);	
//		label_info1.setLabel_id(0);
//		
//		Label_info  label_info2 = new Label_info();
//		label_info2.orignal_label = "aclassxi2hreftargetstrongdivdivclassi ydivclassp_pop blk buiidstyletdclassplsrowspantrtbodytablecellpaddingcellspacingidsummarydividdivclasspl bmidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info2.Label_name= "User";
//		label_info2.column.add(1);
//		label_info2.setLabel_id(1);
//			
//		Label_info  label_info3 = new Label_info();
//		label_info3.orignal_label = "dddlclasscldivclassi ydivclassp_pop blk buiidstyletdclassplsrowspantrtbodytablecellpaddingcellspacingidsummarydividdivclasspl bmidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info3.Label_name= "ReservedField4";
//		label_info3.column.add(2);
//		label_info3.setLabel_id(2);
//		
//		Label_info  label_info4 = new Label_info();
//		label_info4.orignal_label = "emiddivclassauthidivclassptidivclasspitdclassplctrtbodytablecellpaddingcellspacingidsummarydividdivclasspl bmidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info4.Label_name= "PublishTime";
//		label_info4.column.add(1);
//		label_info4.setLabel_id(3);
//		
//		Label_info  label_info5 = new Label_info();
//		label_info5.orignal_label = "tdclasst_fidtrtbodytablecellpaddingcellspacingdivclasst_fszdivclasspcbdivclasspcttdclassplctrtbodytablecellpaddingcellspacingidsummarydividdivclasspl bmidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info5.Label_name= "MainContent";
//		label_info5.column.add(1);
//		label_info5.setLabel_id(4);
//		
//		Label_info  label_info6 = new Label_info();
//		label_info6.orignal_label = "emahrefidonclicktitlestrongdivclasspitdclassplctrtbodytablecellpaddingcellspacingidsummarydividdivclasspl bmidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info6.Label_name= "Floor";
//		label_info6.column.add(1);
//		label_info6.setLabel_id(5);
		
		
//		网站7标注数据
//		Label_info  label_info1 = new Label_info();
//		label_info1.orignal_label = "attributelabelandinstancesourcenamebodyhtml#document";
//		label_info1.Label_name= "Title";
//		label_info1.column.add(3);	
//		label_info1.setLabel_id(0);
//		
//		Label_info  label_info2 = new Label_info();
//		label_info2.orignal_label = "ahreftargetdivclassbbs_user_headiddivclassbbs_usersubtreeandplusandinstancesourcenamebodyhtml#document";
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
//		label_info5.orignal_label = "divclassbbs_show_contentsubtreeandhookandplusandinstancesourcenamebodyhtml#document";
//		label_info5.Label_name= "MainContent";
//		label_info5.column.add(1);
//		label_info5.setLabel_id(4);
//		
//		Label_info  label_info6 = new Label_info();
//		label_info6.orignal_label = "attributelabelandplusandinstancesourcenamebodyhtml#document";
//		label_info6.Label_name= "Floor";
//		label_info6.column.add(1);
//		label_info6.setLabel_id(5);

		//网站8标注数据
//		Label_info  label_info1 = new Label_info();
//		label_info1.orignal_label = "attributelabelandinstancesourcenamebodyhtml#document";
//		label_info1.Label_name= "Title";
//		label_info1.column.add(2);	
//		label_info1.setLabel_id(0);
//	
//		Label_info  label_info2 = new Label_info();
//		label_info2.orignal_label = "ahreftargeth3divclasscomleftdivclasscoml_boxiddivclasscomlistsubtreeandinstancesourcenamebodyhtml#document";
//		label_info2.Label_name= "User";
//		label_info2.column.add(1);
//		label_info2.setLabel_id(1);			
//		
//		Label_info  label_info3 = new Label_info();
//		label_info3.orignal_label = "#";
//		label_info3.Label_name= "ReservedField4";
//		label_info3.column.add(0);
//		label_info3.setLabel_id(2);
//
//		Label_info  label_info4 = new Label_info();
//		label_info4.orignal_label = "divclassu_dtimedivclasscoml_boxiddivclasscomlistsubtreeandinstancesourcenamebodyhtml#document";
//		label_info4.Label_name= "PublishTime";
//		label_info4.column.add(1);
//		label_info4.setLabel_id(3);
//		
//		Label_info  label_info5 = new Label_info();
//		label_info5.orignal_label = "pdivclasscorinfodivclasscorightdivclasscoml_boxiddivclasscomlistsubtreeandinstancesourcenamebodyhtml#document";
//		label_info5.Label_name= "MainContent";
//		label_info5.column.add(1);
//		label_info5.setLabel_id(4);
//		
//		Label_info  label_info6 = new Label_info();
//		label_info6.orignal_label = "h3divclasscorightdivclasscoml_boxiddivclasscomlistsubtreeandinstancesourcenamebodyhtml#document";
//		label_info6.Label_name= "Floor";
//		label_info6.column.add(1);
//		label_info6.setLabel_id(5);
		//网站9标注数据
//		Label_info  label_info1 = new Label_info();
//		label_info1.orignal_label = "spanidh1clasststdclassplc ptm pbn vwthdtrtbodytablecellpaddingcellspacingdivclasspl bmidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info1.Label_name= "Title";
//		label_info1.column.add(1);	
//		label_info1.setLabel_id(0);
//		
//		Label_info  label_info2 = new Label_info();
//		label_info2.orignal_label = "aclassxi2hrefreltargetstrongdivdivclassi ydivclassp_pop blk bui card_gender_0idstyledivclasspls favataridtdclassplsrowspantrtbodytablecellpaddingcellspacingclassplhinidsummarydividdivclasspl bmidsubtreeandinstancesourcenamebodyhtml#document";
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
//		label_info4.orignal_label = "emiddivclassauthidivclassptidivclasspitdclassplctrtbodytablecellpaddingcellspacingclassplhinidsummarydividdivclasspl bmidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info4.Label_name= "PublishTime";
//		label_info4.column.add(1);
//		label_info4.setLabel_id(3);
//		
//		Label_info  label_info5 = new Label_info();
//		label_info5.orignal_label = "tdclasst_fidtrtbodytablecellpaddingcellspacingdivclasst_fszdivclasspcbdivclasspcttdclassplctrtbodytablecellpaddingcellspacingclassplhinidsummarydividdivclasspl bmidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info5.Label_name= "MainContent";
//		label_info5.column.add(1);
//		label_info5.setLabel_id(4);
//		
//		Label_info  label_info6 = new Label_info();
//		label_info6.orignal_label = "emahrefidonclickrelstrongdivclasspitdclassplctrtbodytablecellpaddingcellspacingclassplhinidsummarydividdivclasspl bmidsubtreeandinstancesourcenamebodyhtml#document";
//		label_info6.Label_name= "Floor";
//		label_info6.column.add(1);
//		label_info6.setLabel_id(5);
////		
//		label_list.add(label_info1);
//		label_list.add(label_info2);
//		label_list.add(label_info3);
//		label_list.add(label_info4);
//		label_list.add(label_info5);
//		label_list.add(label_info6);
		
		Label_trainData trainData = new Label_trainData();
		//手工标注
		// arg1: 对齐文件的父路径
		// arg2: 标注列表
		trainData.run_humanLable("C:/Users/Administrator/Desktop/ExtractRecordData/1/",label_list);
		//根据标注json文件自动标注标注
		// arg1: 对齐文件的父路径
		// arg2: 标注寄送文件名称，默认在父路径下
//		trainData.run_auto("C:/Users/Administrator/Desktop/ExtractRecordData/1/","人民网汽车论坛_WebPageResults.json");

	}

}
