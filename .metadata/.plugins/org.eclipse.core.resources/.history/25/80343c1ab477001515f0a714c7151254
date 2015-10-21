package com.moblima.project.view;

import java.util.Scanner;

import com.moblima.project.view.staff.LoginMenu;
import com.moblima.project.view.staff.StaffMenu;

public class MainMenu extends BaseMenu {
	private LoginMenu mLoginMenu;
	
	public MainMenu(Scanner sc) {
		super(sc);
		
		mLoginMenu = new LoginMenu(sc);
	}
	
	public void displayMenu() {
		int choice = 0; // each menu manage their own choice integer

		do {
			printHeader("MOvie Booking and LIsting Management Application (MOBLIMA)");
			println(" 1. Staff Login");
			println(" 2. Customer");
			println(" 3. Quit");
			println("");
			
			try {
				choice = readChoice(1, 3);
				
				switch (choice) {
					case 1:
						mLoginMenu.displayMenu();
						break;
					case 2:
						break;
				}			
			} catch (ExitException e) {
				println("Program is exiting.");
				break;
			}
		} while (choice != 3);
	}
	
}
