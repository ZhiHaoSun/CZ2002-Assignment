package com.moblima.project.view.moviegoer;

import com.moblima.project.controller.CineplexManager;
import com.moblima.project.model.Booking;
import com.moblima.project.model.Customer;
import com.moblima.project.model.Seat;
import com.moblima.project.view.BaseMenu;

public class MovieGoerMenu extends BaseMenu {
	
	private MoviesMenu mMovieMenu;
	
	public MovieGoerMenu(CineplexManager mCineplexManager) {
		super(mCineplexManager);
		mMovieMenu  = new MoviesMenu(mCineplexManager);
	}

	public void displayMenu() {
		choice = 0; // each menu manage their own choice integer
		
		do {
			printHeader("Welcome to MOBLIMA");
			println(" 1. Movie Listing");
			println(" 2. Display Booking History");
			println(" 3. Back");
			println("");
			
			try {
				choice = readChoice(1, 3);
				
				switch (choice) {
					case 1:
						printHeader("Movies");
						mMovieMenu.displayMenu(chooseMovie(""));
						
						break;
					case 2:
						//** View Book History 
						displayBookingHistory();
						break;
				}			
			} catch (ExitException e) {
				break;
			}
		} while (choice != 3);
	}
	
	public void displayBookingHistory() {
		int choice = 0; // each menu manage their own choice integer

		do {
			printHeader("Display Booking History using:");
			println(" 1. Booking ID");
			println(" 2. User Information");
			println(" 3. Quit");
			println("");
			
			try {
				choice = readChoice(1, 3);
				
				switch (choice) {
					case 1:
						displayBookingRecord();
						break;
					case 2:
						displayBookingRecords();
						break;
					case 3:
						//Display the seat arrangement of the show time.
//						time = chooseShowTime();
						//this.displaySeats(mShowTimeManager.getSeats(time, mTicketManager));
						break;
				}			
			} catch (ExitException e) {
				println("Go back.");
				break;
			} catch(Exception e){
				println(e.getMessage());
				break;
			}
		} while (choice != 4);
	}
	
	public void displayBookingRecords() {
		// prompt for customer information
		println("Please input the following details to view your booking");
		Customer customer = new Customer();
		customer.setName(read("Your name: "));
		customer.setEmail(read("Your email address: "));
		customer.setPhone(read("Your phone number: "));
		
		if (mCineplexManager.getCustomers().contains(customer)) {
			customer = (Customer) mCineplexManager.getInstance(customer);
			
			printHeader("Your Booking History");
			for (Booking record:customer.getBookingRecords()) {
				printBookingRecord(record);
			}
			
			return;
		}
	
		println("Please inuput the correct customer information");
	}
	
	public void displayBookingRecord() {
		Booking record = new Booking(read("Please input the Booking ID: "));
		
		record = (Booking) mCineplexManager.getInstance(record);
		if (record != null) {
			printHeader("Booking Details");
			printBookingRecord(record);
		} else println("No Booking Record Found.");
	}
	
	public void printBookingRecord(Booking record) {
		println("Booking ID  : "+ record.getTID() +"\n");
		
		mMovieMenu.printShowTimeInfo(record.getShowtime());
		print("\nSeats Booked: ");
		for (Seat s: record.getSeats()) {
			print(s.getSeat() +" ");
		}
		
		println("\nTotal Price : "+ record.getTotalPrice());
		println("");
	}
	
	public void displayMovieListing() throws ExitException {
		printHeader("Movies");
	}
}


