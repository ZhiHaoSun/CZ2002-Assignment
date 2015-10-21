package com.moblima.project.view;

import java.io.IOException;
import java.util.Scanner;

import org.json.JSONException;

import com.moblima.project.model.Movie;

public class Moblima {

	public static void main(String[] args) throws IOException, JSONException {	
		
		Movie.load();
		
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\\n");
                
		MainMenu main = new MainMenu(sc);
		
//		JSONObject object = new JSONObject();
//		object.put("id", 2);
//		object.put("title", "Avengers");
//		object.put("synopsis", "abcde");
//		object.put("director","Sean");
//		object.put("status","COMING_SOON");
//		object.put("language","THAI");
//		object.put("rating","NO");
//		object.put("casts",new JSONArray());
//		
//		Movie movie = new Movie(object);
//		movie.save();
//		main.displayMenu();
		main.displayMovies();
	}
}
