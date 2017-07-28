package records.classification;

import java.io.File;
import java.io.IOException;

import lib.svm.svm_scale;

public class Data_scale {
	public static void main(String[] args) throws IOException {
		String rootPath = "C:/Users/Administrator/Desktop/ExtractRecordData/0/";
		String need_scale_data_file = rootPath + "train_data";
		
		File need_scale = new File(need_scale_data_file);
		if(!need_scale.exists()){
			System.err.println("需要缩放的文件不存在"+ rootPath +"train_data");
			System.exit(1);
		}
		String scaled_data = need_scale.getParent() + "/train_scaled_data";
		String scale_parameters = need_scale.getParent() + "/train_scale_parameters";
		String[] testArgs = {"-l","-1", "-u","1",			
				"-o",scaled_data,//缩放后保存的文件名称
				"-s",scale_parameters,//缩放后保存参数文件名称
				need_scale_data_file};  //缩要缩放的文件
        try {
			svm_scale.main(testArgs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
