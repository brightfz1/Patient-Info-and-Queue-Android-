package com.example.phaseii.Data;

import java.io.Serializable;

import com.example.phaseii.Manager.GetTime;

/**
 * The VitalSign class includes all patient's vital signs.
 * @author Tony, Andrew, Joseph, Kim, Dante
 *
 */
public class VitalSign extends GetTime implements Serializable{

	/***/
	private static final long serialVersionUID = 7340156898885153559L;
	private String bloodPressure;
	private double temperature;
	private int heartRate;
	
	/**
	 * Sets a patient's vital sign using his temperature, blood pressure
	 * and heart rate.
	 * @param temperature; a patient's temperature.
	 * @param bloodPressure; a patient's blood pressure.
	 * @param heartRate; a patient's heart rate.
	 */
	public VitalSign(double temperature,String bloodPressure,int heartRate){
		super();
		this.temperature = temperature;
		this.bloodPressure = bloodPressure;
		this.heartRate = heartRate;
	}

	/**
	 * Get the patient's blood pressure.
	 * @return bloodPressure; a patient's blood pressure.
	 */
	public String getBloodPressure() {
		return bloodPressure;
	}
	
	/**
	 * Set a patient's blood pressure.
	 * @param bloodPressure; a patient's blood pressure.
	 */
	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	/**
	 * Get a patient's temperature.
	 * @return temperature; a patient's temperature.
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * Set a patient's temperature.
	 * @param temperature
	 */
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	/**
	 * Get a patient's heart rate.
	 * @return heartRate
	 */
	public int getHeartRate() {
		return heartRate;
	}

	/**
	 * Set a patient's heart rate.
	 * @param heartRate
	 */
	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}
	
	/**
	 * Scan an array of strings, and create a new VitalSign
	 * based on the scanned values.
	 * @param strings
	 * @return a patient's vital sign.
	 */
	public VitalSign scan (String[] strings){
		double tmp_temperate = Double.valueOf(strings[0]);
		String tmp_blood = strings[1];
		int tmp_heartRate = Integer.valueOf(strings[2]).intValue();
		VitalSign vitalSign = new VitalSign(tmp_temperate,tmp_blood,tmp_heartRate);
		return vitalSign;
	}
	
	public String toString(){
		return getArrivalTime()+","+temperature+","+bloodPressure+","+heartRate;
	}
}
