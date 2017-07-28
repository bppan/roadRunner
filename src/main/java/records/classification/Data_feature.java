package records.classification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//特征如下：
//1、所在列数（DOM书深度）--可用（已经在上层调用函数中添加）
//2、字符串长度- 平均长度
//3、含有的数字个数-平均个数
//4、含有的英文个数--平均
//5、含有中文个数。--平均
//6、含有非数字、英文、中文个数。--平均
//7、分词后，词项个数（考虑发帖内容为句子形式）暂不考虑--平均

public class Data_feature {
	public List<String> stringList;
	public Data_feature(){
		stringList = new ArrayList();
	}

//	1、所在列数（DOM书深度）--不可用（训练数据中没有该特征值）
	public double getString_average_column(){

		return 0;
	}
	
//	2、字符串长度- 平均长度
	public double getString_average_length(){
		if(stringList.size() == 0){
			return 0;
		}
		int sum = 0;
		for(int i = 0; i < stringList.size(); i++){
			sum += stringList.get(i).trim().length();
		}
		if(stringList.size() == 0){
			return 0;
		}
		return sum*1.0/stringList.size();
	}
// 2、字符串长度--方差
	public double getString_variance_length(){
		if(stringList.size() == 0){
			return 0;
		}
		double average = this.getString_average_length();
		double sum_average = 0;
		for(int i = 0; i < stringList.size(); i++){
			sum_average += Math.pow(average - stringList.get(i).trim().length(), 2);
		}	
		return sum_average/stringList.size();
	}
//	3、含有的数字个数-平均个数
	public double getString_average_num_count(){
		List<Integer> int_list =  getList_number();
		return getAverage(int_list);	
	}
//	3、含有的数字个数-方差个数
	public double getString_variance_num_count(){	
		double average = this.getString_average_num_count();	
		List<Integer> int_list = getList_number();
		return this.getVariance(average, int_list);
	}
//	4、含有的英文个数--平均
	public double getString_average_english_word_count(){
		List<Integer> int_list = getList_english();
		return getAverage(int_list);
	}
//	4、含有的英文个数--方差
	public double getString_variance_english_word_count(){	
		double average = this.getString_average_english_word_count();		
		List<Integer> int_list =  getList_english();
		return this.getVariance(average, int_list);
	}
//	5、含有中文个数。--平均
	public double getSting_average_chines_word_count(){
		List<Integer> int_list = getList_chinese();
		return getAverage(int_list);
	}
//	5、含有中文个数。--方差
	public double getString_variance_chines_word_count(){
		double average = this.getSting_average_chines_word_count();				
		List<Integer> int_list = getList_chinese();	
		return this.getVariance(average, int_list);
	}
//	6、含有非数字、英文、中文个数。--平均
	public double getString_average_none_word_count(){
		List<Integer> int_list =getList_Noword();
		return getAverage(int_list);
	}
// 6、含有非数字、英文、中文个数。--方差
	public double getString_variance_none_word_count(){
		double average = this.getString_average_none_word_count();				
		List<Integer> int_list = getList_Noword();
		return getVariance(average, int_list);
	}
//7、含有的字符串个数
	public double getString_size(){
		return this.stringList.size();
	}

//	7、分词后，词项个数（考虑发帖内容为句子形式）暂不考虑--平均
	public String getLable(){
		
		return null;
	}
//	8、发表时间格式特征
	public double getString_average_publictimeFixString(){
		List<Integer> int_list = new ArrayList();	
		int num_count = 0;
		for(int i = 0; i< stringList.size();i++){	
			if(stringList.get(i).contains("发表于")||stringList.get(i).contains("发表时间")||stringList.get(i).contains("发帖时间")){
				if(stringList.get(i).equals("发表于")||stringList.get(i).equals("发表时间")||stringList.get(i).equals("发帖时间")){
					continue;
				}else{
					String first3 = stringList.get(i).trim().substring(0, 3);
					String first4 = stringList.get(i).trim().substring(0, 4);
					if(first3.equals("发表于") || first4.equals("发表时间") || first4.equals("发帖时间")){
						num_count++;
						int_list.add(num_count);
					}
				}
			}	
		}
		if(int_list.size() == 0){
			return 0;
		}
		return num_count*1.0/int_list.size();	
	}
//9、注册时间格式特征
	public double getString_average_RegisterTimeString(){
		List<Integer> int_list = new ArrayList();	
		int num_count = 0;
		for(int i = 0; i< stringList.size();i++){	
			if(stringList.get(i).contains("注册于")||stringList.get(i).contains("注册时间")||stringList.get(i).contains("注册")){
				if(stringList.get(i).trim().equals("注册于")||stringList.get(i).trim().equals("注册时间")||stringList.get(i).contains("注册")){
					continue;
				}else{
					String first2 = stringList.get(i).trim().substring(0, 2);
					String first3 = stringList.get(i).trim().substring(0, 3);
					String first4 = stringList.get(i).trim().substring(0, 4);
					boolean hasNum = false;
					for(int k = 0; k <  stringList.get(i).length(); i++){
						if(this.isNumber(stringList.get(i).charAt(k))){
							hasNum = true;
						}
					}
					if(!hasNum){
						continue;
					}
					if(first3.equals("注册于") || first4.equals("注册时间") || first2.equals("注册") ){
						num_count++;
						int_list.add(num_count);
					}
				}

			}	
		}
		if(int_list.size() == 0){
			return 0;
		}
		return num_count*1.0/int_list.size();	
	}
	public double getString_sameSimility(){
		int the_same_num = stringList.size() - 1;
		int real_same_num = 0;
		for(int i = 0; i < stringList.size();i++){
			real_same_num += the_same_num;
			the_same_num--;
		}
		if(real_same_num == 0){
			return 0.0;
		}
		int simility_num = 0;
		for(int i = 0; i < stringList.size();i++){
			int the_simility = 0;
			for(int j = i + 1; j < stringList.size(); j++){
				if(stringList.get(i).trim().equals(stringList.get(j))){
					the_simility++;
				}
			}
			simility_num += the_simility;
		}
		return simility_num*1.0/real_same_num;		
	}
//10 楼层特定格式特征
	public double getString_average_FloorFixString(){
		List<Integer> int_list = new ArrayList();	
		int num_count = 0;
		for(int i = 0; i< stringList.size();i++){	
			if(stringList.get(i).contains("楼") || stringList.get(i).contains("#") || stringList.get(i).contains("层") ){
				if(stringList.get(i).trim().equals("楼")|| stringList.get(i).trim().equals("#") || stringList.get(i).trim().equals("层")){
					continue;
				}else{
					num_count++;
					int_list.add(num_count);
				}
			}	
		}
		if(int_list.size() == 0){
			return 0;
		}
		return num_count*1.0/int_list.size();	
	}
	//获得开始Floor数
	public double getString_FloorNumStart(){
		
		if(stringList.size() == 0 && stringList.size() > 4){
			return 0;
		}
		for(int i = 0; i < stringList.size(); i++){
			if(stringList.get(i).contains("楼") || stringList.get(i).contains("层") ||stringList.get(i).contains("#")){
				if(stringList.get(i).equals("楼") || stringList.get(i).contains("层") ||stringList.get(i).contains("#")){
					return 0.0;
				}
			}else{
				for(int j = 0; j < stringList.get(i).length(); j++){
					if(!this.isNumber(stringList.get(i).charAt(j))){
						return 0.0;
					}
				}
			}
		}
		int start = -1;
		int min_start = 10000;
		for(int i = 0; i< stringList.size();i++){
			start = getFloorNum(stringList.get(i));
			if(start == -1){
				continue;
			}else{
				if(min_start > start){
					min_start  = start;
				}
			}
		}
		if(min_start == -1){
			return 0;
		}
		return min_start;
	}
	//获得最大一个Floor数
	public double getString_FloorNumEnd(){
		
		if(stringList.size() == 0  && stringList.size() > 4){
			return 0;
		}
		for(int i = 0; i < stringList.size(); i++){
			if(stringList.get(i).contains("楼") || stringList.get(i).contains("层") ||stringList.get(i).contains("#")){
				if(stringList.get(i).equals("楼") || stringList.get(i).contains("层") ||stringList.get(i).contains("#")){
					return 0.0;
				}
			}else{
				for(int j = 0; j < stringList.get(i).length(); j++){
					if(!this.isNumber(stringList.get(i).charAt(j))){
						return 0.0;
					}
				}
			}
		}
		int end = -1;
		int max_end = -1;
		for(int i = stringList.size() - 1; i>=0;i--){
			end = getFloorNum(stringList.get(i));
			if(end == -1){
				continue;
			}else{
				if(max_end < end){
					max_end = end;
				}
			}
		}
		if(max_end == -1){
			return 0;
		}
		return max_end;
	}
	//获得floor的差距
	public double getString_FloorGap(){
		double start = getString_FloorNumStart();
		double end = getString_FloorNumEnd();
		if(start == 0 || end == 0){
			return 0;
		}
		double gap = end - start;
		return gap;
	}
//11是否是连续数字
	public double getString_FloorNumFeatureString(){
		if(stringList.size() == 0 && stringList.size() > 4){
			return 0;
		}
		for(int i = 0; i < stringList.size(); i++){
			if(stringList.get(i).contains("楼") || stringList.get(i).contains("层") ||stringList.get(i).contains("#")){
				if(stringList.get(i).equals("楼") || stringList.get(i).contains("层") ||stringList.get(i).contains("#")){
					return 0.0;
				}
			}else{
				for(int j = 0; j < stringList.get(i).length(); j++){
					if(!this.isNumber(stringList.get(i).charAt(j))){
						return 0.0;
					}
				}
			}
		}
		int start = -1;
		int startIndex = -1;
		for(int i = 0; i< stringList.size();i++){
			start = getFloorNum(stringList.get(i));
			startIndex = i;
			if(start == -1){
				continue;
			}else{
				break;
			}
		}
		if(start == -1 || start == 0){
			return 0;
		}
		for(int i = startIndex + 1; i< stringList.size();i++){
			if(getFloorNum(stringList.get(i)) > start){
				start = getFloorNum(stringList.get(i));
			}else{
				return 0;
			}
		}
		return 1.0;
	}
	private int getFloorNum(String floor){
		String num = "";
		int intNUm = -1;
		for(int i = 0; i < floor.length(); i++){
			if(i == 0 && !isNumber(floor.charAt(0))){
				return -1;
			}
			if(isNumber(floor.charAt(i))){
				num += floor.charAt(i);
			}
		}
		try{
			if(num.isEmpty()){
				return -1;
			}
			intNUm =Integer.parseInt(num);		
		}catch(NumberFormatException e){
			return -1;
		}
		return intNUm;
	}
	//获取发表时间特定格式
	public double getStirng_average_publisTime(){
		int num_count = 0;
		for(int i = 0; i< stringList.size();i++){
			if(this.isPublisTimeFormate(stringList.get(i))){
				num_count++;
			}
		}
		return num_count*1.0/stringList.size();
	}
	//获取注册时间特定格式
	public double getStirng_average_registTime(){
		int num_count = 0;
		for(int i = 0; i< stringList.size();i++){
			if(this.isRegistTimeFormate(stringList.get(i))){
				num_count++;
			}
		}
		return num_count*1.0/stringList.size();
	}

	//获取字符串长小于于5所占的比例
	public double getString_stringLenghtLessThanSixRat(){
		if(stringList.size() == 0){
			return 0;
		}
		int num = 0;
		for(int i = 0; i<stringList.size(); i++){		
			if(stringList.get(i).trim().length() <= 6){
				num++;
			}
		}
		return num*1.0/stringList.size();
	}

	//获取字符串长度大于10所占的比例
	public double getString_stringLenghtLessThanTenRat(){
		if(stringList.size() == 0){
			return 0;
		}
		int num = 0;
		for(int i = 0; i<stringList.size(); i++){		
			if(stringList.get(i).trim().length() <= 10){
				num++;
			}
		}
		return num*1.0/stringList.size();
	}
	//获取字符串长度 小于15所占的比例
	public double getString_stringLenghtLessThanSixteenRat(){
		if(stringList.size() == 0){
			return 0;
		}
		int num = 0;
		for(int i = 0; i<stringList.size(); i++){		
			if(stringList.get(i).trim().length() <= 16){
				num++;
			}
		}
		return num*1.0/stringList.size();
	}
	//判断是不是数字字符
	private boolean isNumber(char char_at){
		if('0'<= char_at&&char_at <= '9'){
			return true;
		}
		return false;
	}
	//判断是不是英文字符
	private boolean isEnglish(char char_at){
		if('a'<= char_at&&char_at<= 'z' || 'A'<= char_at&&char_at<= 'Z'){
			return true;
		}
		return false;
	}
	//判断是不是中文字符
	private boolean isChinese(char c){
	    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
	    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
	    		|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
	    		|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
	    		|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
	    	return true;
	    }
	    return false;
	}
	private boolean isNotWord(char char_at){
		if(!isNumber(char_at) && !isEnglish(char_at) && !isChinese(char_at)){
			return true;
		}
		return false;
	}
	private double getVariance(double average, List<Integer> num_list){
		if(num_list.size() == 0){
			return 0;
		}
		double sum_average = 0;
		for(int i = 0; i < num_list.size(); i++){
			sum_average += Math.pow(average - num_list.get(i), 2);
		}	
		return sum_average/num_list.size();
	}
	private double getAverage(List<Integer> num_list){
		if(num_list.size() == 0){
			return 0;
		}
		int sum = 0;
		for(int i = 0; i < num_list.size(); i++){
			sum += num_list.get(i);
		}
		return sum*1.0/num_list.size();
	}
	private List<Integer> getList_number(){
		List<Integer> int_list = new ArrayList();	
		for(int i = 0; i< stringList.size();i++){
			int num_count = 0;
			for(int j =0; j < stringList.get(i).length();j++){
				if(isNumber(stringList.get(i).charAt(j))){
					num_count++;
				}
			}
			int_list.add(num_count);
		}
		return int_list;
	}
	private List<Integer> getList_english(){
		List<Integer> int_list = new ArrayList();	
		for(int i = 0; i< stringList.size();i++){
			int num_count = 0;
			for(int j =0; j < stringList.get(i).length();j++){
				if(isEnglish(stringList.get(i).charAt(j))){
					num_count++;
				}
			}
			int_list.add(num_count);
		}
		return int_list;
	}
	private boolean isPublisTimeFormate(String timeString){
		String time = "";
		for(int i = 0; i < timeString.length();i++){
			if(!isNumber(timeString.charAt(i))){
				time += " ";
				continue;
			}
			time += timeString.charAt(i);
		}
		time = time.trim();
		String[] timesplit = time.split(" ");
		if(timesplit.length <= 4){
			return false;
		}
		for(int i = 0; i < timesplit.length; i++){
			if(i == 0){
				if(timesplit[0].length() != 4)return false;
				
			}else{
				if(timesplit[i].length()>2)return false;
			}
			
		}
		List<SimpleDateFormat> formateList = new ArrayList();
		//发表时间
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy MM dd HH mm ss");
		formateList.add(format1);
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy MM dd HH mm");
		formateList.add(format2);
		SimpleDateFormat format3 = new SimpleDateFormat("yyyy M d HH mm ss");
		formateList.add(format3);	
		for(int i = 0; i < formateList.size();i++){
			try {
				formateList.get(i).parse(time);
				return true;
			} catch (ParseException e) {}
		}
		return false;
		
	}
	private boolean isRegistTimeFormate(String timeString){
		String time = "";
		for(int i = 0; i < timeString.length();i++){
			if(!isNumber(timeString.charAt(i))){
				time += " ";
				continue;
			}
			time += timeString.charAt(i);
		}
		time = time.trim();
		String[] timesplit = time.split(" ");
		if(timesplit.length != 3){
			return false;
		}
		for(int i = 0; i < timesplit.length; i++){
			if(i == 0){
				if(timesplit[0].length() != 4)return false;
			}else{
				if(timesplit[i].length()>2)return false;
			}
			
		}
		System.err.println(time);
		List<SimpleDateFormat> formateList = new ArrayList();
		//注册时间
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy MM dd");
		formateList.add(format1);
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy M d");
		formateList.add(format2);
		for(int i = 0; i < formateList.size();i++){
			try {
				formateList.get(i).parse(time);
				System.err.println("----OKay");
				return true;
			} catch (ParseException e) {}
		}
		return false;
		
	}

	
	
	private List<Integer> getList_chinese(){
		List<Integer> int_list = new ArrayList();	
		for(int i = 0; i< stringList.size();i++){
			int num_count = 0;
			for(int j =0; j < stringList.get(i).length();j++){
				if(isChinese(stringList.get(i).charAt(j))){
					num_count++;
				}
			}
			int_list.add(num_count);
		}
		return int_list;
	}
	private List<Integer> getList_Noword(){
		List<Integer> int_list = new ArrayList();	
		for(int i = 0; i< stringList.size();i++){
			int num_count = 0;
			for(int j =0; j < stringList.get(i).length();j++){
				if(isNotWord(stringList.get(i).charAt(j))){
					num_count++;
				}
			}
			int_list.add(num_count);
		}
		return int_list;
	}
	//包含时间格式
 
	 
}
