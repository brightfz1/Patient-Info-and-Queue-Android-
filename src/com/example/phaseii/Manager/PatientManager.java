package com.example.phaseii.Manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.example.phaseii.Data.Patient;
import com.example.phaseii.Data.Prescription;
import com.example.phaseii.Data.VitalSign;


/**
 * A class that manages records. RecordManager knows how to read a record V
 * objects from a file and how to write its collection of Vs to a file.
 */
public class PatientManager implements Serializable {
    
    /** This RecordManager's unique ID for serialization. */
    private static final long serialVersionUID = -8343341355344834001L;
    
    /** The Patient being managed by this PatientManager. */
    
    /** An instance of the class being managed by this PatientManager. */

	private JSONObject records;

	private File file;

	private String filepath;

    /**
     * Constructs a new PatientManager that manages a collection of records
     * stored in directory dir in file named fileName.
     * @param dir the directory in which the data file is stored
     * @param fileName the data file containing Patient information
     * @throws IOException
     */
    public PatientManager(File dir, String fileName) throws IOException {
        
    	this.filepath = dir.toString() +"/"+ fileName;        
        file = new File(filepath);     
        this.records = new JSONObject();
        if ((!file.exists()) || (file.length() == 0)) {
        	saveToFile();
        }
        else{
        	readFromFile();
        }
        	
        
    }
    
    /*
     * Write JSONObject to file.
     */
    public void saveToFile(){
    	try {
			FileWriter new_file = new FileWriter(filepath);
			new_file.write(records.toJSONString());
			new_file.flush();
			new_file.close();
		} catch (IOException e) {
			e.printStackTrace(); 
		}
    }
    
    /*
     * Read JSONObject from file.
     */
    public void readFromFile() {
    	try {
    		JSONParser parser = new JSONParser();
			Object obj;
			try {
				obj = parser.parse(new FileReader(filepath));
				records = (JSONObject) obj;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}		
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Adds Patient to this recordManager.
     * @param Patient a patient to be added.
     */
	@SuppressWarnings("unchecked")
	public void addPatient(Patient patient) {
		if ((file.exists()) || (file.length() != 0)) {
			readFromFile();
        }
		JSONObject each_patient = new JSONObject();	
		
		JSONObject patientData_value = new JSONObject();
		patientData_value.put("last_name",patient.getLastname());
		patientData_value.put("first_name",patient.getFirstname());
		patientData_value.put("dob",patient.getDob());
		patientData_value.put("hcnumber",patient.getID());
		
		GetTime gettime = new GetTime();
		String time = (String) gettime.getArrivalTime();
		patientData_value.put("arrival_time",time);
		
		Integer urgencyScrore=Integer.valueOf(0);
		if (patient.getAge()<2){
			urgencyScrore +=1;
		}
		
		JSONObject vitalsign_value = new JSONObject();	
		JSONObject symptom_value = new JSONObject();
		JSONObject prescription = new JSONObject();
		JSONObject seeDoctorTime = new JSONObject();	
		
		each_patient.put("patientdata", patientData_value);
		each_patient.put("vitalsign", vitalsign_value);
		each_patient.put("symptom", symptom_value);
		each_patient.put("prescription", prescription);
		each_patient.put("urgencyScrore", urgencyScrore);
		each_patient.put("seeDoctorTime", seeDoctorTime);
		
		records.put(patient.getID(), each_patient);
		saveToFile();
    }
	
	/**
     * Adds Vitalsign to this recordManager.
     * @param Vitalsign vital signs of a patient to be added.
     */
	@SuppressWarnings({ "unchecked" })
	public void addVitalsign(String hcnumber,VitalSign vitalsign){
		if ((file.exists()) || (file.length() != 0)) {
			readFromFile();
        }
		JSONObject each_patient = (JSONObject) records.get(hcnumber);
		JSONObject each_record = (JSONObject) each_patient.get("vitalsign");
		
		long urgencyScrore = (Long)each_patient.get("urgencyScrore");		
		double temperature = vitalsign.getTemperature();
		String bloodpre = vitalsign.getBloodPressure();
		String[] bloodpressure = vitalsign.getBloodPressure().split("-");
		double systolic = Double.parseDouble(bloodpressure[0]);
		double diastolic = Double.parseDouble(bloodpressure[1]);
		int heartrate = vitalsign.getHeartRate();
		int tmp_urgencyScrore = 0;
		if (temperature >= 39.0){
			tmp_urgencyScrore += 1;
		}
		if (systolic >= 140 || diastolic <= 50){
			tmp_urgencyScrore += 1;
		}
		if (heartrate >= 100 || heartrate <= 50){
			tmp_urgencyScrore += 1;
		}
		urgencyScrore = urgencyScrore + tmp_urgencyScrore;
		
		JSONObject vitalsign_value = new JSONObject();
		vitalsign_value.put("temperature",temperature);
		vitalsign_value.put("blood",bloodpre);
		vitalsign_value.put("heart",heartrate);

		GetTime gettime = new GetTime();
		String time = (String) gettime.getArrivalTime();
		each_record.put(time, vitalsign_value);
		each_patient.put("urgencyScrore", urgencyScrore);
		
		saveToFile();
	}
	
	/**
	 * Adds prescription to the patient of the healthcard number.
	 * @param hcnumber; A patient's healthcard number.
	 * @param prescription; a patient's to be added prescription.
	 */
	@SuppressWarnings("unchecked")
	public void addPrescription(String hcnumber,Prescription prescription){
		if ((file.exists()) || (file.length() != 0)) {
			readFromFile();
        }
		JSONObject each_patient = (JSONObject) records.get(hcnumber);
		JSONObject each_record = (JSONObject) each_patient.get("prescription");
		each_record.put("medication",prescription.getMedication());
		each_record.put("instruction",prescription.getInstructions());
		saveToFile();
	}
	
	/**
	 * Adds check-in time to a patient's record.
	 * @param hcnumber; a patient's healthcard number.
	 * @param time; the checkin time of a patient.
	 * @param doctor; the name of the doctor.
	 */
	@SuppressWarnings("unchecked")
	public void addCheckinTime(String hcnumber,String time,String doctor){
		if ((file.exists()) || (file.length() != 0)) {
			readFromFile();
        }
		JSONObject each_patient = (JSONObject) records.get(hcnumber);
		JSONObject each_record = (JSONObject) each_patient.get("seeDoctorTime");
		each_record.put(doctor, time);
		saveToFile();
	}
	
	/**
	 * Groups all patients based on their urgency points.
	 * @return map; a map of categorized patients.
	 */
	@SuppressWarnings("unchecked")
	public Map<String,ArrayList<String>> getUrgencyList(){
		if ((file.exists()) || (file.length() != 0)) {
			readFromFile();
        }
		Map<String,ArrayList<String>>map = new HashMap<String,ArrayList<String>>();
		ArrayList<String> urgent = new ArrayList<String>();
		ArrayList<String> less_urgent = new ArrayList<String>();
		ArrayList<String> non_urgent = new ArrayList<String>();
		ArrayList<String> nonnon_urgent = new ArrayList<String>();
		
		Set<String>hcnumber_set = records.keySet();
		Iterator<String> iter = hcnumber_set.iterator();
		while (iter.hasNext()){
			 String hcnumber = (String)iter.next();
			 JSONObject each_patient = (JSONObject) records.get(hcnumber);
			 JSONObject see_doctorTime = (JSONObject) each_patient.get("seeDoctorTime");
			 if (see_doctorTime.toJSONString().equals("{}")){
			 	long urgencyScore = (Long) each_patient.get("urgencyScrore");
				String name = "";
				JSONObject patientdata = (JSONObject) each_patient.get("patientdata");
				String last_name = (String)patientdata.get("last_name");
				String first_name = (String)patientdata.get("first_name");
				String birthday = (String)patientdata.get("dob");
				name = last_name+" "+first_name+"   "+birthday;
				if (urgencyScore == 0){
					nonnon_urgent.add(hcnumber+"-"+name+"-Urgency Score:"+urgencyScore);
				}
				if (urgencyScore == 1){
					non_urgent.add(hcnumber+"-"+name+"-Urgency Score:"+urgencyScore);
				}
				if (urgencyScore == 2){
					less_urgent.add(hcnumber+"-"+name+"-Urgency Score:"+urgencyScore);
				}
				if (urgencyScore == 3 || urgencyScore == 4){
					urgent.add(hcnumber+"-"+name+"-Urgency Score:"+urgencyScore);
				}
			 }
			 map.put("urgent", urgent);
			 map.put("less_urgent", less_urgent);
			 map.put("non_urgent", non_urgent);
			 map.put("nonnon_urgent", nonnon_urgent);
		}
		return map;
	}
	
	/**
	 * Uses a patient's healthcard number to get his record.
	 * @param hcnumber; healthcard number of a patient
	 * @return record; record of the corresponding patient.
	 */
	public Map<String,String> getRecord(String hcnumber){
		if ((file.exists()) || (file.length() != 0)) {
			readFromFile();
        }
		Map<String,String>record = new HashMap<String,String>();
		
		JSONObject each_patient = (JSONObject) records.get(hcnumber);
		JSONObject patientdata = (JSONObject) each_patient.get("patientdata");
		String lastname = (String)patientdata.get("last_name");
		String firstname = (String)patientdata.get("first_name");
		String arrivaltime = (String)patientdata.get("arrival_time");
		String birthday = (String)patientdata.get("dob");
		long urgencyScore = (Long) each_patient.get("urgencyScrore");
		JSONObject see_doctorTime = (JSONObject) each_patient.get("seeDoctorTime");
		
		String name = lastname+" "+firstname;
		record.put("name", name);
		record.put("arrivaltime", arrivaltime);
		record.put("birthday",birthday);
		if (urgencyScore == 0){
			record.put("urgency", "N/A");
		 }
		if (urgencyScore == 1){
			record.put("urgency", "Non urgent");
		 }
		 if (urgencyScore == 2){
			 record.put("urgency", "Less urgent");
		 }
		 if (urgencyScore == 3 || urgencyScore == 4){
			 record.put("urgency", "Urgent");
		 }
		 if (see_doctorTime.toJSONString().equals("{}")){
			 record.put("seeDoctor", "Not yet");
		 }else{
			 record.put("seeDoctor", "Yes");
		 }
		 
		return record;
	}
	
	/**
	 * Uses a patient's healthcard number to find his vital signs.
	 * @param hcnumber; healthcard number of a patient.
	 * @return vitalsign; vital sign of the corresponding patient.
	 */
	public Map<String,String> getVitasign (String hcnumber){
		if ((file.exists()) || (file.length() != 0)) {
			readFromFile();
        }
		Map<String,String>vitalsign = new HashMap<String,String>();
		JSONObject each_patient = (JSONObject) records.get(hcnumber);
		JSONObject each_vitalsign = (JSONObject) each_patient.get("vitalsign");
		@SuppressWarnings("unchecked")
		Set<String>vitalsign_set = each_vitalsign.keySet();
		Iterator<String> iter = vitalsign_set.iterator();
		while (iter.hasNext()){
			String recordtime = (String)iter.next();
			JSONObject each_sign = (JSONObject)each_vitalsign.get(recordtime);
			String blood = (String)each_sign.get("blood");
			long heart = (Long)each_sign.get("heart");
			
			String heart_str = String.valueOf(heart);
			Double temperature = (Double)each_sign.get("temperature");
			String temperature_str = String.valueOf(temperature);
			String total_sign = "Heart Rate:"+heart_str+" Blood Pressure:"+blood;
			String key = recordtime+"  Temperature:"+temperature_str;
			vitalsign.put(key, total_sign);
		}
		return vitalsign;
	}
	
	/** 
	 * Uses a patient's healthcard number to get his prescriptions.
	 * @param hcnumber; healthcard number of a patient.
	 * @return prescription; prescriptions of the corresponding patient.
	 */
	public ArrayList<String> getPrescription(String hcnumber){
		if ((file.exists()) || (file.length() != 0)) {
			readFromFile();
        }
		ArrayList<String> prescription = new ArrayList<String>();
		JSONObject each_patient = (JSONObject) records.get(hcnumber);
		JSONObject each_prescription = (JSONObject) each_patient.get("prescription");
		String medication = (String)each_prescription.get("medication");
		String instruction = (String)each_prescription.get("instruction");
		prescription.add(medication);
		prescription.add(instruction);
		
		return prescription;
	}
	
    /**
     * Gets the records managed by this recordManager.
     * @return a map of record ID to record object
     */
    public JSONObject getRecords() {
    	return records;
    }
    
    @Override
    public String toString() {
        return records.toString();
    }
    
    /*
     * Return whether the hcnumber is the key of an non-empty
     * key/value pair
     * @Return boolean value
     */
    public boolean isExistPitient(String hcnumber){
    	return records.containsKey(hcnumber);
   
    }
}

