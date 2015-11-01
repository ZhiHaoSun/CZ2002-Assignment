package com.moblima.project.view.moviegoer;

import java.util.Date;
import java.util.Scanner;

import org.json.JSONException;

import com.moblima.project.controller.CinemaManager;
import com.moblima.project.controller.MovieManager;
import com.moblima.project.controller.ReviewManager;
import com.moblima.project.controller.ShowTimeManager;
import com.moblima.project.controller.StaffManager;
import com.moblima.project.controller.TicketManager;
import com.moblima.project.model.Cinema;
import com.moblima.project.model.Customer;
import com.moblima.project.model.Movie;
import com.moblima.project.model.ShowTime;
import com.moblima.project.model.Ticket;
import com.moblima.project.view.BaseMenu;
import com.moblima.project.view.BaseMenu.ExitException;

public class MovieGoerTicketMenu extends BaseMenu {

	public MovieGoerTicketMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager,
			ReviewManager mReviewManager, ShowTimeManager mShowTimeManager, TicketManager mTicketManager, StaffManager mStaffManager) {
		super(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
	}
	
	private Ticket ticket;
	private ShowTime time;
	private Cinema cinema;
	private Movie movie;
	private Customer customer;

	@Override
	public void displayMenu() {
		int choice = 0; // each menu manage their own choice integer

		do {
			printHeader("Options for tickets");
			println(" 1. Book Ticket");
			println(" 2. View Tickets");
			println(" 3. View Seats");
			println(" 4. Quit");
			println("");
			
			try {
				choice = readChoice(1, 4);
				
				switch (choice) {
					case 1:
						createTicket();
						break;
					case 2:
						String email = this.read("Please enter your email address");
						this.displayTicketsByEmail(email);
						break;
					case 3:
						time = chooseShowTime();
						this.displaySeats(mShowTimeManager.getSeats(time, mTicketManager));
						break;
				}			
			} catch (ExitException e) {
				println("Go back.");
				break;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} while (choice != 4);
	}
	
	public void createTicket() throws ExitException, JSONException{
		ticket = new Ticket();
		customer = new Customer();
		ShowTime time;
		
		printHeader("Book new Ticket");
		
		time = this.chooseShowTime();
		ticket.setShowTime(time);
		customer.setName(read("Your name: "));
		customer.setEmail(read("Your email address: "));
		customer.setPhone(read("Your phone number: "));
		
		ticket.setCustomer(customer);
		ticket.setDate(new Date());
		
		ticket.setSeat(chooseSeat(time));
		ticket.setPrice(mShowTimeManager.getMovie(time, mMovieManager).getPrice());
		
		if(mTicketManager.create(ticket))
			System.out.println("Booking Successful");
		else
			System.out.println("Booking Unsuccessful");
	}
	
	public void displayTicketsByEmail(String email){
		printHeader("Display Your Tickts");
		
		for(Ticket t: mTicketManager.getTicketsByEmail(email)){

			time = t.getShowTime();
			cinema = (Cinema) this.mCinemaManager.getInstanceById(time.getCinemaId());
			movie = (Movie)this.mMovieManager.getInstanceById(time.getMovieId());
			println(cinema.getName() + "  " + movie.getTitle() + "  " + time.getDateTimeStr() + "  " + ticket.getDateStr());
		}
	}
}
