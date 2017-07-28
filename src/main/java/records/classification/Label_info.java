package records.classification;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class Label_info {

	public String getLabel_name() {
		return Label_name;
	}
	public void setLabel_name(String label_name) {
		Label_name = label_name;
	}
	public String getOrignal_label() {
		return orignal_label;
	}
	public void setOrignal_label(String orignal_label) {
		this.orignal_label = orignal_label;
	}
	public List<Integer> getColumn() {
		return column;
	}
	public void setColumn(List<Integer> column) {
		this.column = column;
	}
	public int getLabel_id() {
		return label_id;
	}
	public void setLabel_id(int label_id) {
		this.label_id = label_id;
	}
	public String Label_name;
	public String orignal_label;
	public List<Integer> column;
	public int label_id;

	public Label_info(){
		Label_name = "";
		orignal_label = "";
		column = new ArrayList();
		label_id = -1;
	}
	
}
