package com.moblima.project.view.moviegoer;

import java.util.Scanner;

import com.moblima.project.controller.MovieManager;
import com.moblima.project.view.BaseMenu;

public class MovieGoerMenu extends BaseMenu {

	public MovieGoerMenu(Scanner sc) {
		super(sc);
		
		mMovieManager = new MovieManager();
	}
	
	public void displayMenu() {
		
	}
}
