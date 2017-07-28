package com.wolf_datamining.autoextracting.roadrunner.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HeadTable{
	public String headString;
	public String lableName;
	public double possibility;	
	public List<LayerString> listString;
	
	public HeadTable(String headString){
		this.headString = headString;;
		this.listString = new ArrayList<LayerString>();
		lableName = "";
		possibility = 0;
	}
	public String getLableName() {
		return lableName;
	}
	public void setLableName(String lableName) {
		this.lableName = lableName;
	}
	public double getPossibility() {
		return possibility;
	}
	public void setPossibility(double possibility) {
		this.possibility = possibility;
	}
	public boolean addListString(int layer, String layerString){
		for(int i = 0; i < listString.size();i++){
			if(listString.get(i).layerNumber == layer){
				return false;
			}
		}
		listString.add(new LayerString(layer, layerString));	
		return true;
	}
	public String getListString(int Layer){
		for(int i = 0; i < this.listString.size();i++){
			if(this.listString.get(i).layerNumber == Layer){
				return this.listString.get(i).layerString;
			}
		}
		return null;
	}
	public boolean isIntheHeadTable(String value){

		for(int i = 0; i < this.listString.size(); i++){
			LayerString thelayString = this.listString.get(i);
			if(thelayString.layerString.trim().equals(value)){	
				return true;
			}else{
				
//				String time_string = thelayString.layerString.replaceAll("发帖时间", "").replaceAll("发表于", "");
				String time_string = thelayString.layerString;
				String show_time  = "";
				boolean flag = false;
				for(int j = 0; j < time_string.length(); j++){					
					if('0'<= time_string.charAt(j) && time_string.charAt(j)  <= '9'){
						show_time += time_string.charAt(j);
					}else{
						show_time += " ";
					}
				}
				show_time = show_time.trim();
				if(show_time.trim().equals(value)){
					return true;
				}
				String[] whole_time = show_time.split(" ");
				if(whole_time.length != 6){
					flag = false;
				}
				String try_time = "";
				for(int k = 0; k < whole_time.length; k++){
					if(k == 0){
						try_time += whole_time[k] + " ";
					}else{
						if(whole_time[k].length() < 2){
							try_time +=  "0" + whole_time[k] + " ";
						}else if(whole_time[k].length() == 2){
							try_time += whole_time[k] + " ";
						}else if(whole_time[k].length() > 2){
							flag = false;
						}
					}
				}
				if(try_time.trim().equals(value)){
					return true;
				}
				if(!flag){				
					String regist_string = thelayString.layerString;
					String show_regist = "";
					for(int j = 0; j < regist_string.length(); j++){					
						if('0'<= regist_string.charAt(j) && regist_string.charAt(j)  <= '9'){
							show_regist += regist_string.charAt(j);
						}else{
							show_regist += " ";
						}
					}
					if(show_regist.trim().equals(value)){
						return true;
					}
			
					
					String[] whole_regist = show_regist.split(" ");
					if(whole_regist.length != 3){
						flag = false;
					}
					String try_regist = "";
					for(int k = 0; k < whole_regist.length; k++){
						if(k == 0){
							try_regist += whole_regist[k] + " ";
						}else{
							if(whole_regist[k].length() < 2){
								try_regist +=  "0" + whole_regist[k] + " ";
							}else if(whole_regist[k].length() == 2){
								try_regist += whole_regist[k] + " ";
							}else if(whole_regist[k].length() > 2){
								flag = false;
							}
						}
					}
					if(try_regist.trim().equals(value)){
						return true;
					}			
				}
			}
		}		
		return false;
	}

}

