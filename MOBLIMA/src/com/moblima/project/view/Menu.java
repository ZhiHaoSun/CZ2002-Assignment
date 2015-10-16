package com.moblima.project.view;

public class Menu {
	public enum ManageMovie {
		CREATE_MOVIE("Create Movie"),
        UPDATE_MOVIE("Update Movie"),
        REMOVE_MOVIE("Remove Movie");
		
		private final String item;
		
		ManageMovie(String item) { this.item = item; }
		
		public String getItem() { return item; }
	}
}
