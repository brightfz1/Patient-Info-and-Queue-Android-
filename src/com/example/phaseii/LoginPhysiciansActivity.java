package com.example.phaseii;

import java.io.IOException;

import com.example.phaseii.Manager.GetTime;
import com.example.phaseii.Manager.PatientManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This Android activity supports activity_login_success
 * which allows the successful login page to compile.
 * @author Tony, Andrew, Joseph, Kim, Dante
 *
 */
public class LoginPhysiciansActivity extends Activity {

	public static final String FILENAME = "patient_records.json";
	//private Patient patient;
    private PatientManager manager;
    private Transit transit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_physicians);

		try {
			manager = new PatientManager(this.getApplicationContext().getFilesDir(), FILENAME);
		} catch (IOException e) {
          e.printStackTrace();
		}
		TextView welcome = (TextView) findViewById(R.id.welcome);
		GetTime time = new GetTime();
		welcome.setText(time.welcome());
		
		transit = (Transit)getApplicationContext();
		transit.setBackpage("physicians");
	} 	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	/**
	 * Gets the health card number inputed by the user.
	 */
	public void getHCnumber(){
		EditText hcnumberText = (EditText) findViewById(R.id.textid);
		String hcnumber = hcnumberText.getText().toString();
		transit = (Transit)getApplicationContext();
		transit.setHcnumber(hcnumber); 
	}
	/**
	 * Uses the health card number to check the validity of the patient.
	 * If the patient exists, that patient's prescriptions will be
	 * recorded. Else, errors are raised.
	 * @param view
	 */
	public void Add_Prescription(View view){
		getHCnumber();
		EditText hcnumberText = (EditText) findViewById(R.id.textid);
		String hcnumber = hcnumberText.getText().toString();
		if (manager.isExistPitient(hcnumber)){
			Intent intent = new Intent(this,AddPrescription.class);
			intent.putExtra("manager", manager);
			startActivity(intent);
		}else{
			new AlertDialog.Builder(this)
			.setTitle("Warning")
			.setMessage("You edited an non-existent patient!")
			.setPositiveButton("Got it",null)
			.show();
		}
	}
	/**
	 * view_record supports interface activity_login_physicians. It
	 * takes in the health card number of a patient, and displays the
	 * information of that patient. If the health card number is invalid,
	 * errors will be raised.
	 * @param view
	 */
	public void view_record(View view){
		getHCnumber();
		EditText hcnumberText = (EditText) findViewById(R.id.textid);
		String hcnumber = hcnumberText.getText().toString();
		if (manager.isExistPitient(hcnumber)){
			Intent intent = new Intent(this,ViewRecordActivity.class);
			intent.putExtra("manager", manager);
			startActivity(intent);
		}else{
			new AlertDialog.Builder(this)
			.setTitle("Warning")
			.setMessage("You edited an non-existent patient!")
			.setPositiveButton("Got it",null)
			.show();
		}
	}
	/**
	 * Returns to the login page.
	 * @param view
	 */
	public void back(View view){
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
	}
}
