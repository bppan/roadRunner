package records.classification;

import java.util.List;
import java.util.Map;

public class Page_records {
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public List<Map> getDocuments() {
		return Documents;
	}
	public void setDocuments(List<Map> documents) {
		Documents = documents;
	}
	public String Url;
	public List<Map> Documents;
	
}
