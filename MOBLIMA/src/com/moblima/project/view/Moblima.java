package com.moblima.project.view;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;

public class Moblima {
	
	public static void main(String[] args) throws IOException, JSONException, ParseException {			
		MainMenu main = new MainMenu();
		main.displayMenu();
	}
}
