package com.wolf_datamining.autoextracting.roadrunner.application;

public class JaroWinklerDistance {

	public String str1;
	public String str2;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s1 = "attributelabelandhookandplusandinstancesourcenamebodyhtml#document";
		String s2 = "spanclassjubao float_Rclassonclickpclasslianxiclassarticlesubtreeandinstancesourcenamebodyhtml#document";
		System.out.println(new JaroWinklerDistance(s1,s2).jaro_winkler_distance());
		System.out.println(new JaroWinklerDistance(s1,s2).jaro_distance());

	}
	/**
	 * @param s1
	 * @param s2
	 * @return
	 */
	public JaroWinklerDistance(String str1, String str2){
		this.str1 = str1;
		this.str2 = str2;
	}
	public double jaro_winkler_distance()  
	{  		
		
		double p = 0.1;  
		int len = 0;  
		for(int i = 0; i < 4; ++i)  
		{  
			if(this.str1.charAt(i) != this.str2.charAt(i))  
				break;  
			++len;  
		}  
		double dj = jaro_distance();  
		return dj + p*len*(1-dj);  
	}
	public double jaro_distance() 
	{   
		if(this.str1.isEmpty() ||this.str2.isEmpty())   
		{   
			if(this.str1.isEmpty()&&this.str2.isEmpty())   
				return 1.0;   
			return 0.0;  
		}   
		
		int allowrange= Math.max(this.str1.length(),this.str2.length())/2-1;   
		boolean is_match = false;  
		int []matches= new int[this.str2.length()];
		for(int i=0;i<this.str2.length();++i)  
			matches[i] = -1;  
		int m = 0;//ƥ�����Ŀ  
		for(int i = 0; i < this.str1.length(); ++i)  
		{  
			is_match = false; 
			for(int j = i;is_match==false && j >=0 && j >= i - allowrange;--j) 
			{ 
				if(j >= this.str2.length()){
					continue;
				}
				if(this.str2.charAt(j) == this.str1.charAt(i))  
				{  
					matches[j]= m++;  
					is_match=true;  
				}  
			}  
			for(int j = i; is_match == false && j < this.str2.length() && j <= i + allowrange; ++j)  
			{  
				if(this.str2.charAt(j) == this.str1.charAt(i))  
				{  
					matches[j]=m++;  
					is_match=true;  
				}  
			}  
		}  
		double t = 0;  
		int i = 0; 
		for(int j = 0;j < this.str2.length(); ++j)  
		{  
			if(matches[j] != -1)  
			{  
				if(matches[j] != i++)  
					++t;  
			}  
		}  
		double fm = m;
		return ((fm/this.str1.length())+fm/this.str2.length()+(fm-t/2)/m)/3;  
	}  
}
