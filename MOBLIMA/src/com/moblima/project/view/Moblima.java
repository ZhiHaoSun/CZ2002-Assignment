package com.moblima.project.view;

import java.util.Scanner;

public class Moblima {

	public static void main(String[] args) {		
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\\n");
		
		MainMenu main = new MainMenu(sc);
		main.displayMenu();
	}
}
