package com.moblima.project.view;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import com.moblima.project.model.Constant;
import com.moblima.project.model.Constant.Rating;
import com.moblima.project.model.Movie;
import com.moblima.project.model.Review;

public class Moblima {

	public static void main(String[] args) throws IOException, JSONException, ParseException {	
		
//		Movie.load();
		Review.load();
		
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\\n");
                
		MainMenu main = new MainMenu(sc);
		
//		JSONObject object = new JSONObject();
//		object.put("id", 1);
//		object.put("title", "Avengers");
//		object.put("synopsis", "abcde");
//		object.put("director","Sean");
//		object.put("status","COMING_SOON");
//		object.put("language","THAI");
//		object.put("rating","NO");
//		object.put("casts",new JSONArray());
//		object.put("movieId", 1);
//		object.put("date", Constant.dateFormat.format(new Date()));
//		object.put("", value)
//		
//		Movie movie = new Movie(object);
//		movie.save();
//		main.displayMenu();
//		Review review = new Review(Rating.NC16, "This is a normal movie", 1);
//		review.create();
//		main.displayMovies();
		for(Review r : Review.instances){
			System.out.println(r.toDisplay());
		}
	}
}
