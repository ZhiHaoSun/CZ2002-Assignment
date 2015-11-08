package com.moblima.project.view.staff;

import java.util.ArrayList;

import com.moblima.project.controller.CineplexManager;
import com.moblima.project.model.Constant.TicketType;
import com.moblima.project.model.Holiday;
import com.moblima.project.model.Movie;
import com.moblima.project.model.Ticket;
import com.moblima.project.view.BaseMenu;
import com.moblima.project.view.TopRankingMenu;

public class ManageSystemMenu extends BaseMenu {

	private TopRankingMenu mTopRankingMenu;
	public ManageSystemMenu(CineplexManager mCineplexManager) {
		super(mCineplexManager);
		
		mTopRankingMenu = new TopRankingMenu(mCineplexManager);
	}
	
	@Override
	public void displayMenu() {
		choice = 0; // each menu manage their own choice integer
		
		do {
			printHeader("System Setting");
			println(" 1. Top 5 Movies");
			println(" 2. View Ticket Price");
			println(" 3. Change Ticket Price");
			println(" 4. Manage Public Holiday");
			println(" 5. Back");
			println("");
			
			try {
				choice = readChoice(1, 5);
				
				switch (choice) {
					case 1:
						mTopRankingMenu.displayMenu();
						break;
					case 2:
						optionViewTicketPrice();
						break;
					case 3:
						optionChangeTicketPrice();
						break;
					case 4:
						optionManagePublicHoliday();
						break;
				}			
			} catch (ExitException e) {
				break;
			} 
		} while (choice != 3);
	}
	
	public void optionViewTicketPrice() {
		String table;
		
		table = mCineplexManager.getTicketManager().generateTicketPriceTable(true);
		println(table);
		
		table = mCineplexManager.getTicketManager().generateTicketPriceTable(false);
		println(table);
	}
	
	public void optionChangeTicketPrice() throws ExitException {
		printHeader("Change Ticket Price");
		
		Ticket ticket = new Ticket();
		ticket.setPlatinum(confirm("Change Platinum Ticket Price?"));
		
		// Plot the Ticket Price Table
		// So the Staff is able to see the current price before changing
		String table = mCineplexManager.getTicketManager().generateTicketPriceTable(ticket.isPlatinum());
		println(table);
		
		ticket.setMovieType(chooseMovieType("What Type of Ticket Price:"));
		ticket.setTicketType(chooseTicketType(ticket.isPlatinum()));
		ticket.setPrice(readDouble("New Ticket Price($): "));
		
		if (mCineplexManager.update(ticket)) {
			println("Successful Change the Ticket Price");
		} else {
			println("Unsuccessful Change the Ticket Price");
		}
	}

	protected TicketType chooseTicketType(boolean platinum) throws ExitException {
		int length = TicketType.values().length;
		int j, i   = platinum? length-2:0;
		
		if (!platinum) length = length - 2;
		
		println("Select Ticket Type:");						
		
		for (j=1; i+j<=length; j++)
			println(" "+j+". "+TicketType.values()[i+j-1].value());
		
		println(" "+(j)+". Back");
		println("");
		int index = readChoice(1, j) -1;
		
		return TicketType.values()[index+i];
	}
	
	// Manage Holiday
	public void optionManagePublicHoliday() {
		Holiday holiday;
		choice = 0; // each menu manage their own choice integer
		
		do {
			printHeader("Manage Public Holiday");
			println(" 1. Add Public Holiday");
			println(" 2. Update Public Holiday");
			println(" 3. Remove Public Holiday");
			
			println(" 4. Back");
			println("");
			
			try {
				choice = readChoice(1, 4);
				
				switch (choice) {
					case 1: printHeader("Add Public Holiday");
						
						holiday = new Holiday();
						holiday.setName(read("Name of Public Holiday: "));
						holiday.setDate(readDate("Date of the Pubilc Holiday: ", "dd/MM"));
						
						if (mCineplexManager.create(holiday)) {
							println("Public Holiday has added into the system.");
						} else {
							println("Public Holiday has failed to add into the system.");
						}
						break;
					case 2: printHeader("Update Public Holiday");

						holiday = choosePublicHoliday();
						
						if (holiday != null) {
							if (confirm("Change Name of PH?"))
								holiday.setName(read("Name"));
							
							if (confirm("Change Date of PH?"))
								holiday.setDate(readDate("New Date", "dd/MM"));
							
							if (mCineplexManager.update(holiday)) {
								println("Successful updated the selected holiday.");
							} else {
								println("Unsuccessful update the selected holiday.");
							}
						}

						break;
					case 3: printHeader("Remove Public Holiday");
						
						holiday = choosePublicHoliday();
						
						if (holiday != null) {
							// if you dont want to remove PH then go back to the menu
							if (!confirm("Are you sure you want to remove?")) continue;
							
							if (mCineplexManager.delete(holiday)) {
								println("Successful removed the selected holiday.");
							} else {
								println("Unsuccessful remove the selected holiday.");
							}
						}
						
						break;
				}			
			} catch (ExitException e) {
				break;
			} 
		} while (choice != 3);
	}
	
	protected Holiday choosePublicHoliday() {		
		try {
			ArrayList<Holiday> holidays = mCineplexManager.getTicketManager().getHolidays();
			
			if (!holidays.isEmpty()) {
				println("Select a Public Holiday:");						
				
				for (int i=0, j=1; i<holidays.size(); i++,j++)
					println(" "+j+". "+holidays.get(i).getDate()+", "+holidays.get(i).getName());
				
				println(" "+(holidays.size()+1)+". Back");
				println("");
				
				int index = readChoice(1, holidays.size()+1) -1;
				
				return holidays.get(index).clone();
			} else {
				println("No Public Holiday found");
			}
		} catch (ExitException e) {}
		return null;
		
	}
}
