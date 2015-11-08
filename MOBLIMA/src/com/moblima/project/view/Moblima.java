package com.moblima.project.view;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import org.json.JSONException;

import com.moblima.project.controller.TicketManager;
import com.moblima.project.model.Discount;

public class Moblima {
	public static TicketManager ticketManager;
	
	public static void main(String[] args) throws IOException, JSONException, ParseException {			
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\\n");
                
		//Initialize all the managers and load all the data
		ticketManager = new TicketManager();
		
		
		//To initialize the data
		//Discount.startData();
		Discount.initialize();
		
		//Pass the managers to the next menu to keep all the data
		MainMenu main = new MainMenu();

		main.displayMenu();
		
	}
}
