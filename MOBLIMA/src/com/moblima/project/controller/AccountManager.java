package com.moblima.project.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.moblima.project.model.User;

public class AccountManager extends Manager {
	// 
	private static final String JSON_FILE_PATH = "data/user.json";
	
	private JSONObject jdata;
	private JSONArray jusers;
	
	private User mCurrentUser; 			// current login user
	private ArrayList<User> mUsers;		//
	
	public AccountManager() {
		retrieveAllUsers();
	}
	
	public boolean isLogin() {
		return (mCurrentUser != null);
	}
	
	public User getLoginUser() {
		return mCurrentUser;
	}
	
	public void create(User user) {
		try {
			if (!isUserExist(user.getUsername())) {
				// add user into json array
				jusers.put(user.toJSONObject());
				
				// 
				mUsers.add(user);

				FileWriter fw = new FileWriter(JSON_FILE_PATH);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(jdata.toString());
				bw.close();

				System.out.println("Successful Created");

			} else System.out.println("user existed.");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private ArrayList<User> retrieveAllUsers() {
		JSONObject user;
		mUsers = new ArrayList<>();

		try {
			String data = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
			jdata  = new JSONObject(data);
			jusers = jdata.getJSONArray("users");

			for (int i=0; i<jusers.length(); i++) {
				user = jusers.getJSONObject(i);
				mUsers.add(new User(user.getString("usrname"), user.getString("passwd")));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mUsers;
	}
	
	public boolean authenticate(User u) {
		return authenticate(u.getUsername(), u.getPassword());
	}
	
	public boolean authenticate(String uname, String passwd) {
		for (User u: mUsers) {
			if (uname.equals(u.getUsername()) &&
					passwd.equals(u.getPassword())) {
				mCurrentUser = u;
				return true;
			}
		}
		
		return false;		
	}
	
	private boolean isUserExist(String username) {
		for (User user: mUsers)
			if (user.getUsername().equals(username))
				return true;
		
		return false;
	}
}
