package com.wolf_datamining.autoextracting.roadrunner.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HtmlToJson{


	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getHtmlpath() {
		return Htmlpath;
	}

	public void setHtmlpath(String htmlpath) {
		Htmlpath = htmlpath;
	}

	String Url;
	String Htmlpath;
	
	HtmlToJson(String Url, String Htmlpath){
		this.Url = Url;
		this.Htmlpath = Htmlpath;
	}


}
