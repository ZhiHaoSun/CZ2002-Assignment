package com.moblima.project.view.staff;

import java.util.Scanner;

import com.moblima.project.controller.AccountManager;
import com.moblima.project.model.User;
import com.moblima.project.view.BaseMenu;

public class LoginMenu extends BaseMenu {

	private StaffMenu mStaffMenu;
	
	private AccountManager mAcctManager;

	public LoginMenu(Scanner sc) {
		super(sc);
		
		mStaffMenu = new StaffMenu(sc);

		mAcctManager = new AccountManager();
	}

	// Main Menu: Choice #1
	public boolean login() {
		printHeader("Staff Login");
		
		// initialize AccountManager <-- controller
		
		User user = new User();
		
		// prompt user for username
		user.setUsername(read("username: "));

		// prompt user for password
		user.setPassword(read("password: "));

		// authenticate user
		return mAcctManager.authenticate(user);
	}
	
	public User getLoginUser() {
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

