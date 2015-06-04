package com.example.phaseii.Data;

import java.io.Serializable;

/**
 * Prescription class records the the prescription information
 * of a patient.
 * 
 * @author Tony, Andrew, Joseph, Kim, Dante
 * 
 */
public class Prescription implements Serializable{

	
	private static final long serialVersionUID = 1017851217096486703L;
	private String medication;
	private String instructions;
	
	/**
	 * Constructs a prescription using medications and instructions.
	 * @param medication; a patient's medications.
	 * @param instructions; intructions to the medications.
	 */
	public Prescription(String medication, String instructions) {
		super();
		this.medication = medication;
		this.instructions = instructions;
	}
	
	/**
	 * Gets a patient's medications.
	 * @return medication; a patient's medication.
	 */
	public String getMedication() {
		return medication;
	}
	
	/**
	 * Sets a patient's medications.
	 * @param medication; a patient's medication.
	 */
	public void setMedication(String medication) {
		this.medication = medication;
	}
	
	/**
	 * Get the instructions for a patient.
	 * @return instructions; intructions to the medications.
	 */
	public String getInstructions() {
		return instructions;
	}

	/**
	 * Set the instructions for the patient.
	 * @param instructions; intructions to the medications
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	@Override
	public String toString() {
		return medication +","+ instructions;
	}	
}
