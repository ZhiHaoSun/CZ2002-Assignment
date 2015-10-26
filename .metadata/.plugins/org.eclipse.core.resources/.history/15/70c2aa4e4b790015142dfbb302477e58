package com.moblima.project.view.staff;

import java.util.Scanner;

import com.moblima.project.model.User;
import com.moblima.project.view.BaseMenu;

public class StaffMenu extends BaseMenu {

	private User mLoginUser;
	private ManageMovieMenu mManageMovieMenu;
	
	public StaffMenu(Scanner sc) {
		super(sc);
		
		mManageMovieMenu = new ManageMovieMenu(sc);
	}
	
	public void setLoginUser(User user) {
		mLoginUser = user;
	}

	@Override
	public void displayMenu() {
		do {
			printHeader("Howdy, "+ mLoginUser.getUsername());
			println(" 1. Create/Update/Remove movie listing");
			println(" 2. Create/Update/Remove cinema showtimes and the movies to be shown");
			println(" 3. Configure system settings");
			println(" 4. Logout");
			println("");
			
			try {
				choice = readChoice(1, 4);
				switch (choice) {
					case 1:
						mManageMovieMenu.displayMenu();
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
			} catch (ExitException e) {
				break;
			}
		} while (choice != 4);		
	}
	
	
}
