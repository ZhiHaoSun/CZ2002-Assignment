package com.moblima.project.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.moblima.project.model.Constant.Language;
import com.moblima.project.model.Constant.MovieType;
import com.moblima.project.model.Constant.Rating;
import com.moblima.project.model.Constant.Status;

/**The model to hold all the movie info.
 * Also holds all the reviews to the movie and the showtimes
 * @author sunzhihao
 *
 */
public class Movie extends Model {
	private String title;
	private String synopsis;
	
	private String opening;
	private String runtime;
	
	private String director;
	private ArrayList<String> casts;
	
	private MovieType type;  //The ClassType is the type of the move, like 3D, Blockbulster
	private boolean blockbuster;

	private Status   status;
	private Rating   rating;
	private Language language;
	private double   overallRating;    // Overall Reviewer's Rating

	private int ticketSales;
	
	private ArrayList<Review> reviews;
	private ArrayList<ShowTime> showtimes;
	
	public Movie() {
		ticketSales = 0;
		
		casts     = new ArrayList<>();
		reviews   = new ArrayList<>();
		showtimes = new ArrayList<>();
	}
	
	/**
	 * @param id
	 */
	public Movie(int id) {
		this();
		this.id = id;
	}
	
	/**Constructors of movie, loads all the reivews of the movie to it.
	 * @param jObj
	 * @throws JSONException
	 * @throws ParseException
	 */
	public Movie(JSONObject jObj) throws JSONException, ParseException {
		this(jObj.getInt("id"));

		title 	 = jObj.getString("title");
		synopsis = jObj.getString("synopsis");
		director = jObj.getString("director");
		
		type	 = MovieType.valueOf(jObj.getString("type"));
		status 	 = Status.valueOf(jObj.getString("status"));
		rating 	 = Rating.valueOf(jObj.getString("rating"));
		language = Language.valueOf(jObj.getString("language"));
		
		opening = jObj.getString("opening");
		runtime = jObj.getString("runtime");

		overallRating = jObj.getDouble("overall rating");
		
		if (jObj.has("blockbuster"))
			blockbuster = jObj.getBoolean("blockbuster");
		
		JSONArray jcasts = jObj.getJSONArray("casts");
		
		for (int i=0; i<jcasts.length(); i++)
			casts.add(jcasts.getString(i));
		
		if (jObj.has("reviews")) {
			JSONArray jreviews = jObj.getJSONArray("reviews");
			
			for (int i=0; i<jreviews.length(); i++)
				reviews.add(new Review(jreviews.getJSONObject(i)));
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isBlockBuster() {
		return blockbuster;
	}

	public void setBlockBuster(boolean blockbuster) {
		this.blockbuster = blockbuster;
	}

	public MovieType getMovieType() {
		return type;
	}

	public void setMovieType(MovieType movieType) {
		this.type = movieType;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public ArrayList<String> getCasts() {
		return casts;
	}
	
	/** Builds all the casts of a movie into a string.
	 * @return String
	 */
	public String getCastsStr(){
		StringBuilder builder = new StringBuilder();
		
		for(String cast: this.casts){
			builder.append(cast + " ,");
		}
		
		builder.deleteCharAt(builder.length()-1);
		
		return builder.toString();
	}
	
	/**Set the casts of a movie from a combined string.
	 * Decompose the string split by ,
	 * @param casts
	 */
	public void setCasts(String casts) {
		String[] names = casts.split(",");
		
		this.casts.clear();
		
		for (String name: names)
			addCast(name.trim());
	}
	
	public void addCast(String cast) {
		casts.add(cast);
	}
	
	public void removeCast(String cast) {
		casts.remove(cast);
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	/**Get the overall rating of the movie based on its reviews.
	 * Result is given by stars.
	 * @return String
	 */
	public String getOverallStarRating() {
		String orate = "";
		
		for (int i=0; i<(int) overallRating; i++) {
			orate += "★";
		}
		
		for (int i=(int) overallRating; i<5; i++) {
			orate += "☆";
		}
		
		return orate;
	}
	
	/**The total sale of the movie
	 * @return int
	 */
	public int getTicketSales() {
		return ticketSales;
	}

	/**All a value to the total TicketValue
	 * @param ticketSales
	 */
	public void addTicketSales(int ticketSales) {
		this.ticketSales += ticketSales;
	}
	
	public void setTicketSales(int ticketSales) {
		this.ticketSales = ticketSales;
	}

	public double getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(int overallRating) {
		this.overallRating = overallRating;
	}

	public String getOpening() {
		return opening;
	}
	
	/**Set the opening time of movie from a string.
	 * Format the time string into a date object.
	 * @param opening
	 * @throws ParseException
	 */
	public void setOpening(String opening) throws ParseException {
		if (opening.equals("TBA")) this.opening = opening;
		else {
			Date date = Constant.dateFormatShort.parse(opening);
			this.opening = Constant.dateFormatLong.format(date);
		}
	}

	public String getRunTime() {
		return runtime;
	}

	public void setRunTime(String runtime) {
		this.runtime = runtime;
	}
	
	public boolean hasReviews() {
		if (reviews.isEmpty()) 
			return false;
		else
			return true;
	}
	public ArrayList<Review> getReviews() {
		return reviews;
	}

	/**Add the review to a movie and update the new rating.
	 * @param review
	 */
	public void addReview(Review review) {
		double total;
		
		total = overallRating * reviews.size();
		total += review.getRating();
		
		reviews.add(review);
		
		overallRating =  total/reviews.size();
	}
	
	public ArrayList<ShowTime> getShowTimes() {
		return showtimes;
	}

	public void addShowTime(ShowTime showtime) {
		this.showtimes.add(showtime);
	}
	
	public void removeShowTime(ShowTime showtime) {
		this.showtimes.remove(showtime);
	}
	
	public void setShowtimes(ArrayList<ShowTime> showtimes) {
		this.showtimes = showtimes;
	}
	
	public Movie clone() {
		Movie cloned = new Movie();
		
		cloned.id 	  	= id;
		cloned.title 	= title;
		cloned.synopsis = synopsis;

		cloned.casts    = casts;
		cloned.director = director;

		cloned.type	 	= type;
		cloned.status 	= status;
		cloned.rating   = rating;
		cloned.language = language;

		cloned.opening  = opening;
		cloned.runtime  = runtime;
		cloned.reviews  = reviews;
		
		cloned.showtimes = showtimes;
		cloned.blockbuster = blockbuster;
		cloned.overallRating = overallRating;
		return cloned;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Movie) {
			Movie m = (Movie) obj;
			return m.id == id;
		} 
		return super.equals(obj);
	}
	
	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject jobj = new JSONObject();
		
		jobj.put("id", id);
		jobj.put("title", title);
		jobj.put("synopsis", synopsis);
		
		jobj.put("casts", casts);
		jobj.put("director", director);
		
		jobj.put("runtime", runtime);
		jobj.put("opening", opening);
		jobj.put("blockbuster", blockbuster);
		jobj.put("status", status.name());
		jobj.put("rating", rating.name());
		jobj.put("language", language.name());
		jobj.put("type", type.name());
		
		jobj.put("overall rating", String.format("%.1f", overallRating));
		
		JSONArray jreviews = new JSONArray();
		for (Review r: reviews)
			jreviews.put(r.toJSONObject());
		jobj.put("reviews", jreviews);
		
		return jobj;
	}

	@Override
	public String toDisplay() {
		return this.title + "  " + this.opening + "  " +this.runtime + "  " + this.director;
	}	
}
