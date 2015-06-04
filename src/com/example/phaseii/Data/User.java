package com.example.phaseii.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tony, Andrew, Joseph, Kim, Dante
 * To record all existing nurse account.
 */
public class User {

	private Map<String, String[]> account;
	
	/**
	 * Constructs a Nurse with all null fields.
	 */
	public User(){
		account = new HashMap<String,String[]>();
		
	}
	
	/**
	 * Put the user's account into an account.
	 * @param username; username entered by the user.
	 * @param password; password entered by the user.
	 * @param act_whoLogin; user's identity.
	 */
	public void enter(String username, String password,String act_whoLogin){
		String [] whologin = {password,act_whoLogin};
		account.put(username, whologin);
	}
	
	/**
	 * Verifies whether of not the user can access the program.
	 * @param enter_name; username entered by the user.
	 * @param enter_pass; password entered by the user.
	 * @param whoLogin; user's identity.
	 * @return true if the parameters are valid, else return false.
	 */
	public boolean verify(String enter_name,String enter_pass,String whoLogin){
		String[] a = account.get(enter_name);
		String password = a[0];
		String act_whologin = a[1];
		if (act_whologin.equals(whoLogin) && password.equals(enter_pass)){
			return true;
		}else
			return false;

	}
	
}
