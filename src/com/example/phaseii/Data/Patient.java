package com.example.phaseii.Data;

import java.io.Serializable;

import com.example.phaseii.Manager.GetTime;



/**
 * This class represents a patient in a hospital.
 * @author Tony, Andrew, Joseph, Kim, Dante
 *
 */
public class Patient extends GetTime implements Serializable{

	/***/
	private static final long serialVersionUID = 5957184941451012908L;
	
	private String lastname;
	private String firstname;
	private String hcnumber;
	private String dob;

	public Patient(){
		
	}
	
	public Patient(String[] name,String dob,String hcnumber) {
		super();
		this.lastname = name[0];
		this.firstname = name[1];
		this.dob = dob;
		this.hcnumber = hcnumber;		
	}
	
	/**
	 * Get a patient's last name.
	 * @return lastname
	 */
	public String getLastname() {
		return lastname;
	}
	
	/**
	 * Set a patient's last name.
	 * @param lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	/**
	 * Get a patient's first name.
	 * @return firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * Set a patient's first name.
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	/**
	 * Get a patient's ID, which is also his
	 * health card number.
	 * @return hcnumber
	 */
	public String getID() {
		return hcnumber;
	}
	
	/**
	 * Set a patient's ID, which is also his
	 * health card number.
	 * @param hcnumber
	 */
	public void setID(String hcnumber) {
		this.hcnumber = hcnumber;
	}
	
	/**
	 * Get a patient's date of birth.
	 * @return date of birth
	 */
	public String getDob() {
		return dob;
	}
	
	/**
	 * Set a patient's date of birth.
	 * @param dob
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}	
	
	/**
	 * Calculate a patient's age using the GetTime class.
	 * @return the age of a patient.
	 */
	public int getAge(){
		int yearNow = getYear();
        int monthNow = getMonth();
        int dayOfMonthNow = getDay();
		String[] a_dob = dob.split("/");
		int year = Integer.parseInt(a_dob[0]);
		int month = Integer.parseInt(a_dob[1]);
		int day = Integer.parseInt(a_dob[0]);
		
		int age = yearNow - year;
		 if (monthNow <= month) {
	            if (monthNow == month) {
	                //monthNow==monthBirth
	                if (dayOfMonthNow < day) {
	                    age--;
	                }
	                else {
	                //monthNow>monthBirth
	                age--;
	                }
	            }
		 }
		return age;
	}
	
	public String toString(){
		return lastname+" "+firstname+","+dob+","+hcnumber+","+getArrivalTime();
	}
}
