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

public class LoginMenu extends BaseMenu {
	
	private StaffMenu mStaffMenu;

	public LoginMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager, ReviewManager mReviewManager,
			ShowTimeManager mShowTimeManager, TicketManager mTicketManager, StaffManager mStaffManager) {
		super(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
	}

	// Main Menu: Choice #1
	public boolean login() {
		printHeader("Staff Login");
		
		// initialize AccountManager <-- controller
		
		Staff user = new Staff();
		
		// prompt user for username
		user.setUsername(read("username: "));

		// prompt user for password
		user.setPassword(read("password: "));

		// authenticate user
		return this.mStaffManager.match(user);
	}
	
	public Staff getLoginUser() {
		return mAcctManager.getLoginUser();
	}

	@Override
	public void displayMenu() {
		if (login()) {
			mStaffMenu.setLoginUser(getLoginUser());
			mStaffMenu.displayMenu();
		} else println("Invalid username/password");	
	}
}

