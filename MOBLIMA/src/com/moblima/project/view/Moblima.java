package com.moblima.project.view;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import com.moblima.project.controller.CinemaManager;
import com.moblima.project.controller.MovieManager;
import com.moblima.project.controller.ReviewManager;
import com.moblima.project.controller.ShowTimeManager;
import com.moblima.project.controller.StaffManager;
import com.moblima.project.controller.TicketManager;
import com.moblima.project.model.Ticket;
import com.moblima.project.model.Cinema;
import com.moblima.project.model.Constant;
import com.moblima.project.model.Constant.Rating;
import com.moblima.project.model.Movie;
import com.moblima.project.model.Review;
import com.moblima.project.model.ShowTime;

public class Moblima {
	
	public static MovieManager movieManager;
	public static CinemaManager cinemaManager;
	public static ShowTimeManager showTimeManager;
	public static ReviewManager reviewManager;
	public static TicketManager ticketManager;
	public static StaffManager staffManager;

	public static void main(String[] args) throws IOException, JSONException, ParseException {	
		
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\\n");
                
		movieManager = new MovieManager();
		cinemaManager = new CinemaManager();
		showTimeManager = new ShowTimeManager();
		reviewManager = new ReviewManager();
		staffManager = new StaffManager();
		
		MainMenu main = new MainMenu(sc, movieManager, cinemaManager, reviewManager, showTimeManager, ticketManager, staffManager);
		
		main.displayMenu();
	}
}
