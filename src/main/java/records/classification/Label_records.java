package records.classification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Label_records {
	public String url;
	public List<Map> records;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<Map> getRecords() {
		return records;
	}
	public void setRecords(List<Map> records) {
		this.records = records;
	}
	public Label_records(){
		this.url = "";
		this.records = new ArrayList<Map>();
		
	}
}
