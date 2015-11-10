package com.moblima.project.view.staff;

import com.moblima.project.controller.CineplexManager;
import com.moblima.project.model.Staff;
import com.moblima.project.view.BaseMenu;

public class StaffMenu extends BaseMenu {
	
	private Staff mCurrentLoginStaff;
	
	private ManageMovieMenu mManageMovieMenu;

	private ManageSystemMenu mManageSystemMenu;
	private ManageShowTimeMenu mManageShowTimeMenu;

	public StaffMenu(CineplexManager mCineplexManager) {
		super(mCineplexManager);
		
		mManageMovieMenu    = new ManageMovieMenu(mCineplexManager);
		mManageSystemMenu   = new ManageSystemMenu(mCineplexManager);
		mManageShowTimeMenu = new ManageShowTimeMenu(mCineplexManager);
	}
	
	// Main Menu: Choice #1
	/**Read in staff info and authenticate the staff
	 * @return boolean
	 */
	public boolean login() {
		printHeader("Staff Login");
				
		mCurrentLoginStaff = new Staff();
		
		// prompt user for username
		mCurrentLoginStaff.setUsername(read("username: "));

		// prompt user for password
		mCurrentLoginStaff.setPassword(read("password: "));

		// authenticate user
		return mCineplexManager.authenticate(mCurrentLoginStaff);
	}
	
	@Override
	public void displayMenu() {
		if (login()) {
			do {
				printHeader("Howdy, "+ mCurrentLoginStaff.getUsername());
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
							mManageShowTimeMenu.displayMenu();
							break;
						case 3:
							mManageSystemMenu.displayMenu();
							break;
					}			
				} catch (ExitException e) {
					break;
				}
			} while (choice != 4);				
		} else println("Invalid username/password");	

	}
	
	
}
