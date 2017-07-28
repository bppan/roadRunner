package roadrunner.Main;

import java.io.File;

public class DoAll {

	public DoAll(){
		
	}
	public void readHtml(String path){
		File htmlFile = new File(path);
		if(!htmlFile.exists()){
			System.out.println("文件不存在："+ path);
			return;
		}
		
	}
	public void loadWebModule(String modulePath){
		
	}
}
