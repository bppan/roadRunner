package lib.svm;

import java.io.IOException;

public class Svm_main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//Test for svm_train and svm_predict
		//svm_train: 
		//	  param: String[], parse result of command line parameter of svm-train
		//    return: String, the directory of modelFile
		//svm_predect:
		//	  param: String[], parse result of command line parameter of svm-predict, including the modelfile
		//    return: Double, the accuracy of SVM classification
		
		
		
		
		String rootPath = "C:/Users/Administrator/Desktop/ExtractRecordData/0/";
		
		
		String[] testArgs = {"-l","-1", "-u","1",			
				"-o",rootPath + "train_scale_data",//缩放后保存的文件名称
				"-s",rootPath + "train_scale_parameters",//缩放后保存参数文件名称
				rootPath + "train_data"};  //缩要缩放的文件
        svm_scale.main(testArgs);  
        
        
        String[] argvScaleTest ={"-o",rootPath+ "train_scale_test_data",
        		"-r",rootPath + "train_scale_parameters",
        		rootPath + "train9_data"};  
        svm_scale.main(argvScaleTest);  
        
		//6\7\8\->9 "-c","32768.0","-g","0.03125"	99.2156862745098% 
    	//6\7\9\->8 "-c","2048.0","-g","0.5"	41.568627450980394%
    	//6\8\9\->7 "-c","128.0","-g","8.0"	55.80357142857143%
        //7\8\9\->6 "-c","2048.0","-g","0.03125"	63.97306397306397%   65.31986531986533% 
        
        //1\2\3\6\7\8->9	"-c","8192.0","-g","0.125"	99.2156862745098%
         
        //1\2\3\6\7\9->8	"-c","128.0","-g","2.0"	58.03921568627452%        
        //1\2\3\6\8\9->7	"-c","32.0","-g","8.0"	39.732142857142854%       
        //1\2\3\7\8\9->6	"-c","32768.0","-g","0.03125"	97.3063973063973% 
        //1\2\6\7\8\9->3	"-c","8192.0","-g","0.125"	81.40495867768594% 
        //1\3\6\7\8\9->2	"-c","32768.0","-g","0.0078125"	92.63157894736842%       
        //2\3\6\7\8\9->1	"-c","32768.0","-g","0.125"	55.21235521235521% 
        //增加一类后，"-c","32768.0","-g","2.0",	
        //未增加一类      "-c","32768.0","-g","8.0", 83.33333333333334% (425/510)  
        // Accuracy = 82.54901960784314% (421/510) (classification)
        //Accuracy = 83.52941176470588% (426/510)
        //Accuracy = 83.92156862745098% (428/510)
        //Accuracy = 95.49019607843138% (487/510) 
        //Accuracy = 98.0392156862745% (500/510) (classification)
        //Accuracy = 92.15686274509804% (470/510) (classification)
        //Accuracy = 97.25490196078431% (496/510)
        //Accuracy = 95.68627450980392% (488/510)
        //Accuracy = 95.29411764705881% (486/510) 
        //Accuracy = 99.01960784313727% (505/510)
        //SVM Classification is done! The accuracy is 0.992156862
		String[] trainArgs ={"-c","32768.0","-g","0.125",	"-b","1",//可选参数
						rootPath + "train_scale_data", //存放SVM训练模型用的数据的路径
						rootPath + "model_r.txt"};  //存放SVM通过训练数据训练出来的模型的路径
//
		String[] parg={"-b","1",
				rootPath+ "train_scale_test_data",   //这个是存放测试数据
						rootPath + "model_r.txt",  //调用的是训练以后的模型
						rootPath + "out_r.txt"};  //生成的结果的文件的路径
//////////
	    System.out.println("........SVM运行开始.........."); 		
	    svm_train.main(trainArgs);
////		String modelFile = svm_train.main(trainArgs);
		Double accuracy = svm_predict.main(parg);
		System.out.println("SVM Classification is done! The accuracy is " + accuracy);
//		
//		//Test for cross validation
//		//String[] crossValidationTrainArgs = {"-v", "10", "UCI-breast-cancer-tra"};// 10 fold cross validation
//		//modelFile = svm_train.main(crossValidationTrainArgs);
//		//System.out.print("Cross validation is done! The modelFile is " + modelFile);
//	    System.out.println("..........End.........."); 
	}

}
