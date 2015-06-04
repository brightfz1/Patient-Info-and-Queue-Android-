package com.example.phaseii.Manager;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.example.phaseii.Data.User;


/**
 * This class is used to verify the username
 * and password to which the user inputed
 * is the same with the stored information.
 * @author Tony, Andrew, Joseph, Kim, Dante
 *
 */
public class NurseManager{

	private String password;
	private String username;
	private FileInputStream filestream;
	private User user;
	private String whoLogin;

	/**
	 * Constructs a nurse manager using username, password,
	 * and the user's identity. Also initializes a new user.
	 * @param file; the file of storage.
	 * @param username; the username required to log in.
	 * @param password; the password required to log in.
	 * @param whoLogin; the identity of the person logging in.
	 */
	public NurseManager(File filepath, String username, String password, String whoLogin) {
		this.username = username;
		this.password = password;
		this.whoLogin = whoLogin;
		this.user = new User();
		try {
			this.filestream = new FileInputStream(filepath);		
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			readFromfile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Get the username and password from the file.
	 * If the file is not found, a FileNotFoundException is
	 * raised.
	 * @throws FileNotFoundException
	 */
	public void readFromfile() throws FileNotFoundException{
		BufferedReader context = new BufferedReader(new InputStreamReader(filestream));
		try {
			String record;
			while((record = context.readLine()) != null)  {
				String[] account = record.split(",");
				String act_username = account[0];
				String act_password = account[1];
				String act_whoLogin = account[2];
				user.enter(act_username, act_password,act_whoLogin);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Verify the inputed username and password.
	 * @return true if username and password matches
	 * the actual login information.
	 */
	public boolean verify(){
		return user.verify(username, password,whoLogin);
	}
}
