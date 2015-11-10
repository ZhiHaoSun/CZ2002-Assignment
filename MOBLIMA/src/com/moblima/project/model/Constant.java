package com.moblima.project.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**The class to hold all the constant values and enum classes, Date formats
 * @author sunzhihao
 *
 */
public class Constant {
	
	public static final String FORMAT_TIME_CLOCK = "HHmm";
	public static final String FORMAT_TIME_HOUR  = "hh:mma";
	public static final String FORMAT_DATE_SHORT = "dd/MM/yyyy";
	public static final String FORMAT_DATE_LONG  = "dd MMM yyyy";
	public static final String FORMAT_BOOKING_ID = "yyyyMMddHHmm";
	public static final String FORMAT_HOLIDAY	 = "dd MMM";
	
	public static SimpleDateFormat dateFormatShort = new SimpleDateFormat(FORMAT_DATE_SHORT);
	public static SimpleDateFormat dateFormatLong  = new SimpleDateFormat(FORMAT_DATE_LONG);
	public static SimpleDateFormat clockFormat = new SimpleDateFormat(FORMAT_TIME_CLOCK);
	public static SimpleDateFormat timeFormat  = new SimpleDateFormat(FORMAT_TIME_HOUR);
	
	public static SimpleDateFormat bookingFormat = new SimpleDateFormat(FORMAT_BOOKING_ID);
	public static SimpleDateFormat holidayFormat = new SimpleDateFormat(FORMAT_HOLIDAY);
	public static SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd MMM yyyy, hh:mma");

	//Date Time format : DD/MM/YY hh:mm PM
	public static DateFormat dateTimeFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
	
	public static final int colNumber = 9;
	public static final int rowNumber = 5;
	
	/**Name of Cineplex
	 * @author sunzhihao
	 *
	 */
	public static enum Cineplex {
		THE_CATHAY_CINEPLEX("The Cathay Cineplex"), 
		CATHAY_CINEPLEX_CINELEISURE_ORCHARD("Cathay Cineplex Cineleisure Orchard"), 
		CATHAY_CINEPLEX_DOWNTOWN_EAST("Cathay Cineplex Downtown East");
		
		private String value;
		Cineplex(String value) { this.value = value; }
		public String value() { return value; }
	}
	
	/**Status of a movie.
	 * @author sunzhihao
	 *
	 */
	public static enum Status {
		COMING_SOON("Coming Soon"), 
		PREVIEW("Preview"), 
		NOW_SHOWING("Now Showing");
		
		// END_OF_SHOWING("End of Showing")
	
		private String value;
		Status(String value) { this.value = value; }
		public String value() { return value; }
	}
	
	/**Language of a movie
	 * @author sunzhihao
	 *
	 */
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
	
	/**Rating of a movie, like 18-ban
	 * @author sunzhihao
	 *
	 */
	public static enum Rating {
		G, PG, PG13, NC16, M18, R21
	}
	
	/**The type of movie, like Digital and 3D
	 * @author sunzhihao
	 *
	 */
	public static enum MovieType {
		DIGITAL ("Digital"),
		THREED ("3D");
		
		private String value;
		MovieType(String value) { this.value = value; }
		public String value() { return value; }
	}
	
	/**The type of a ticket, like holiday and buyer age
	 * @author sunzhihao
	 *
	 */
	public static enum TicketType {
		SENIOR_CITIZENS("Senior Citizens"), 
		STUDENTS("Students"), 
		MON_WED("Mon - Wed"), 
		THU("Thu"), 
		FRI_BEFORE_SIX_PM("Fri before 6pm"), 
		FRI_AFTER_SIX_PM("Fri after 6pm"), 
		SAT_AND_SUN("Sat & Sun"),
		MON_TO_THU("Mon - Thu"), 
		FRI_TO_SUN("Fri - Sun");
		
		private String value;
		TicketType(String value) { this.value = value; }
		public String value() { return value; }
	}
	
	
}
