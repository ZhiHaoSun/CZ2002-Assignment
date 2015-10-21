package com.moblima.project.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constant {
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	public static DateFormat dateTimeFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
	
	public static enum Cineplex {
		THE_CATHAY_CINEPLEX, CATHAY_CINEPLEX_CINELEISURE_ORCHARD, CATHAY_CINEPLEX_DOWNTOWN_EAST,NONE
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
		G, PG, PG13, NC16, M18, R21,NO
	}
	
	public static enum ClassType{
		VIP,FIRST,SECONDARY,NORMAL
	}
}
