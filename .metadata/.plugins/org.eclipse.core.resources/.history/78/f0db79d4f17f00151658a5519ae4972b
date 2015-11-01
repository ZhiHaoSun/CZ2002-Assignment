package com.moblima.project.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constant {
	
	public static SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("MM dd yyyy");
//	public static SimpleDateFormat dateFormat = new SimpleDateFormat("MM dd yyyy");
	public static SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd MMM yyyy, hh:mma");

	//Date Time format : DD/MM/YY hh:mm PM
	public static DateFormat dateTimeFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
	
	public static enum Cineplex {
		THE_CATHAY_CINEPLEX("The Cathay Cineplex"), 
		CATHAY_CINEPLEX_CINELEISURE_ORCHARD("Cathay Cineplex Cineleisure Orchard"), 
		CATHAY_CINEPLEX_DOWNTOWN_EAST("Cathay Cineplex Downtown East");
		
		private String value;
		Cineplex(String value) { this.value = value; }
		public String value() { return value; }
	}
	
	public static enum Status {
		COMING_SOON("Coming Soon"), 
		PREVIEW("Preview"), 
		NOW_SHOWING("Now Showing");
		
		private String value;
		Status(String value) { this.value = value; }
		public String value() { return value; }
	}
	
	public static enum Language {
		ENGLISH("English"), 
		ENGLISH_WITH_CHINESE_SUBTITLES("English with Chinese subtitles"), 
		ENGLISH_WITH_NO_SUBTITLES("English with no subtitles"), 
		MANDARIN("Mandarin"), 
		MALAY("Malay"), 
		TAMIL("Tamil"), 
		HINDI("Hindi"), 
		JAPANESE("Japanese"), 
		THAI("Thai");
		
		private String value;
		Language(String value) { this.value = value; }
		public String value() { return value; }
	}
	
	public static enum Rating {
		G, PG, PG13, NC16, M18, R21,NA
	}
	
	public static enum ClassType{
		VIP,FIRST,SECONDARY,NORMAL
	}
}
