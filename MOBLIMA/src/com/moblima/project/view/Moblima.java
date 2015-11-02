package com.moblima.project.view;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Scanner;

import org.json.JSONException;

import com.moblima.project.controller.CinemaManager;
import com.moblima.project.controller.MovieManager;
import com.moblima.project.controller.ReviewManager;
import com.moblima.project.controller.ShowTimeManager;
import com.moblima.project.controller.StaffManager;
import com.moblima.project.controller.TicketManager;
import com.moblima.project.model.Discount;

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
                
		//Initialize all the managers and load all the data
		movieManager = new MovieManager();
		cinemaManager = new CinemaManager();
		showTimeManager = new ShowTimeManager();
		reviewManager = new ReviewManager();
		staffManager = new StaffManager();
		ticketManager = new TicketManager();
		
		//To initialize the data
		//Discount.startData();
		Discount.initialize();
		
		//Pass the managers to the next menu to keep all the data
		MainMenu main = new MainMenu(sc, movieManager, cinemaManager, reviewManager, showTimeManager, ticketManager, staffManager);
		
		main.displayMenu();
		
	}
}
