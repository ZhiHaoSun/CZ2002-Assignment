package com.moblima.project.view;

import java.util.Scanner;

import com.moblima.project.controller.CinemaManager;
import com.moblima.project.controller.MovieManager;
import com.moblima.project.controller.ReviewManager;
import com.moblima.project.controller.ShowTimeManager;
import com.moblima.project.controller.StaffManager;
import com.moblima.project.controller.TicketManager;

public class MovieMenu extends BaseMenu{
	
	public MovieMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager, ReviewManager mReviewManager,
			ShowTimeManager mShowTimeManager, TicketManager mTicketManager, StaffManager mStaffManager) {
		super(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
	}

	@Override
	public void displayMenu() {
		
	}

}
