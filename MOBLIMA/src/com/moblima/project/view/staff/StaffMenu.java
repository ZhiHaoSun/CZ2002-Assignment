package com.moblima.project.view.staff;

import java.util.Scanner;

import com.moblima.project.controller.CinemaManager;
import com.moblima.project.controller.MovieManager;
import com.moblima.project.controller.ReviewManager;
import com.moblima.project.controller.ShowTimeManager;
import com.moblima.project.controller.StaffManager;
import com.moblima.project.controller.TicketManager;
import com.moblima.project.model.Staff;
import com.moblima.project.view.BaseMenu;

public class StaffMenu extends BaseMenu {
	
	private Staff mLoginUser;
	private ManageMovieMenu mManageMovieMenu;
	private ManageCinemaMenu mManageCinemaMenu;
	private ManageSystemMenu mManageTicketMenu;

	public StaffMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager, ReviewManager mReviewManager,
			ShowTimeManager mShowTimeManager, TicketManager mTicketManager, StaffManager mStaffManager) {
		super(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
		
		mManageMovieMenu = new ManageMovieMenu(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
		mManageCinemaMenu = new ManageCinemaMenu(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
		mManageTicketMenu = new ManageSystemMenu(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
	}

	public void setLoginUser(Staff user) {
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
				choice = readChoice(1, 5);
				switch (choice) {
					case 1:
						mManageMovieMenu.displayMenu();
						break;
					case 2:
						mManageCinemaMenu.displayMenu();
						break;
					case 3:
						mManageTicketMenu.displayMenu();
						break;
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
