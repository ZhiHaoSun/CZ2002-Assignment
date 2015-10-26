package com.moblima.project.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.moblima.project.model.Movie;
import com.moblima.project.model.Staff;

public class StaffManager {
	private static final String file = "data/staff.json";
	private ArrayList<Staff> mStaffs;
	
	private Staff curStaff = null;
	
	public ArrayList<Staff> getmStaffs() {
		return mStaffs;
	}

	public void setmStaffs(ArrayList<Staff> mStaffs) {
		this.mStaffs = mStaffs;
	}

	public Staff getCurStaff() {
		return curStaff;
	}

	public void setCurStaff(Staff curStaff) {
		this.curStaff = curStaff;
	}

	public StaffManager() throws IOException, JSONException, ParseException {
		load();
	}

	public void load() throws IOException, JSONException, ParseException {
		String data = new String(Files.readAllBytes(Paths.get(file)));
		
		JSONArray array = new JSONArray(data);
		mStaffs = new ArrayList<>();
		
		Staff staff;
		
		for(int i=0;i<array.length();i++){
			staff = new Staff(array.getJSONObject(i));
			mStaffs.add(staff);
		}
	}
	
	public boolean match(String user, String pass){
		for(Staff s : this.mStaffs){
			if(s.getUsername().equals(user) && s.getPassword().equals(pass)){
				return true;
			}
				
		}
		return false;
	}
	
	public boolean match(Staff staff){
		for(Staff s : this.mStaffs){
			if(s.getUsername().equals(staff.getUsername()) && s.getPassword().equals(staff.getPassword())){
				this.curStaff = staff;
				return true;
			}
				
		}
		return false;
	}
}