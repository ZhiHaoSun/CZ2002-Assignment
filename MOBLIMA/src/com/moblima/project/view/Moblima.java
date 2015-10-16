package com.moblima.project.view;

import java.util.Scanner;

import com.moblima.project.controller.AccountManager;
import com.moblima.project.controller.MovieManager;
import com.moblima.project.model.Movie;
import com.moblima.project.model.Movie.Language;
import com.moblima.project.model.Movie.Rating;
import com.moblima.project.model.Movie.Status;
import com.moblima.project.model.User;

public class Moblima {
	private static Scanner sc;
	
	// controller
	private static AccountManager mAcctManager;
	
	public static void main(String[] args) {		
		sc = new Scanner(System.in);
		sc.useDelimiter("\\n");
		
		displayMainMenu();
	}
	
	public static void displayMainMenu() {
		int choice; // each menu manage their own choice integer

		do {
			System.out.println();
			System.out.println("/** MOBLIMA Application ************************/");
			System.out.println("1. Staff Login");
			System.out.println("2. Customer");
			System.out.println("3. Quit");
			
			System.out.print("Please select your entry: ");
			choice = sc.nextInt();
			
			try {
				switch (choice) {
					case 1:
						login();
						break;
					case 2:
						break;
					case 3:
						System.out.println("Programme is shutting down.");
						return;
					default:
						System.out.println("Invalid choice! Please select again!!!");
						break;
				}			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (choice != 3);
	}
	
	// Main Menu: Choice #1
	private static void login() {
		System.out.println();
		System.out.println("/** Staff Login ************************/");
		
		// initialize AccountManager <-- controller
		mAcctManager = new AccountManager();
		
		User user = new User();
		
		// prompt user for username
		System.out.print("username: ");
		user.setUsername(sc.next());

		// prompt user for password
		System.out.print("password: ");
		user.setPassword(sc.next());

		// authenticate user
		if(mAcctManager.authenticate(user)) {
			displayStaffMenu();
		} else System.out.println("Invalid Username/Password");
	}
	
	public static void displayStaffMenu() {
		int choice; // each menu manage their own choice integer

		do {
			System.out.println();
			System.out.println("/** Howdy, "+ mAcctManager.getLoginUser().getUsername() +" ************************/");
			System.out.println("1. Create/Update/Remove movie listing");
			System.out.println("2. Create/Update/Remove cinema showtimes and the movies to be shown");
			System.out.println("3. Configure system settings");
			System.out.println("4. Logout");
			
			System.out.print("Please select your entry: ");
			choice = sc.nextInt();
			
			try {
				switch (choice) {
					case 1:
						displayManageMovie();
						break;
					case 2:
						break;
					case 3:
						System.out.println("Programme is shutting down.");
						return;
					case 4: 
						return;
					default:
						System.out.println("Invalid choice! Please select again!!!");
						break;
				}			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (choice != 4);
	}
	private static MovieManager mMovieManager;
	public static void displayManageMovie() {
		int choice, length; // each menu manage their own choice integer
		String input;
		String[] sArr;
		Movie movie;

		// initialize movie manager
		mMovieManager = new MovieManager();
		
		do {
			System.out.println();
			System.out.println("/** Manage Movie Listing ************************/");
			System.out.println("1. Create Movie");
			System.out.println("2. Update Movie");
			System.out.println("3. Remove Movie");
			System.out.println("4. Back");
			
			System.out.print("Please select your entry: ");
			choice = sc.nextInt();
			
			try {
				switch (choice) {
					case 1:
						movie = new Movie();
						
						System.out.print("/** Create Movie ************************/");
						System.out.println();						
						
						System.out.print("Title: ");
						movie.setTitle(sc.next());
						
						System.out.print("SYNOPSIS: ");
						movie.setSynopsis(sc.next());
						
						System.out.print("Director: ");
						movie.setDirector(sc.next());
						
						System.out.print("Casts (separate by COMMA): ");
						input = sc.next();
						
						sArr  = input.split(",");
						for (int i=0; i<sArr.length; i++)
							movie.addCast(sArr[i].trim());
						
						System.out.println("** Language **");						
						length = Language.values().length;
						for (int i=0, j=1; i<length; i++,j++)
							System.out.println(" "+j+". "+Language.values()[i].value());
						System.out.print("Choose Language (1~"+length+"): ");
						movie.setLanguage(Language.values()[sc.nextInt()-1]);
						
						System.out.println("** Movie Rating **");
						length = Rating.values().length;
						for (int i=0, j=1; i<length; i++,j++)
							System.out.println(" "+j+". "+Rating.values()[i]);
						System.out.print("Choose Rating (1~"+length+"): ");
						movie.setRating(Rating.values()[sc.nextInt()-1]);
						
						System.out.println("** Movie Status **");
						length = Status.values().length;
						for (int i=0, j=1; i<length; i++,j++)
							System.out.println(" "+j+". "+Status.values()[i].value());
						System.out.print("Choose Status (1~"+length+"): ");
						movie.setStatus(Status.values()[sc.nextInt()-1]);
						
						System.out.print("RunTime (minutes): ");
						movie.setRunTime(sc.next());
						
						System.out.print("Opening (TBA or dd/MM/yyyy): ");
						movie.setOpening(sc.next());
						
						if (mMovieManager.create(movie))
							System.out.println("Create Successful");
						else
							System.out.println("Create Unsuccessful");
						break;
					case 2:
						break;
					case 3:
						System.out.print("/** Create Movie ************************/");
						System.out.println();			
						
						System.out.print("Enter Movie ID:");
						if (mMovieManager.remove(sc.next()))
							System.out.println("Remove Successful");
						else
							System.out.println("Remove Unsuccessful");

						break;
					case 4: 
						return; // end this method and go back to previous menu
					default:
						System.out.println("Invalid choice! Please select again!!!");
						break;
				}			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (choice != 4);
	}
}
