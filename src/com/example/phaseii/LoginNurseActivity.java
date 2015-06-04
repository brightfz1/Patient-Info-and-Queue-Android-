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
public class LoginNurseActivity extends Activity {

	public static final String FILENAME = "patient_records.json";
	//private Patient patient;
    private PatientManager manager;
    private Transit transit;
	private GetTime time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_success);

		try {
			manager = new PatientManager(this.getApplicationContext().getFilesDir(), FILENAME);
		} catch (IOException e) {
          e.printStackTrace();
		}
		TextView welcome = (TextView) findViewById(R.id.welcome);
		time = new GetTime();
		welcome.setText(time.welcome());
		transit = (Transit)getApplicationContext();
		transit.setBackpage("nurse");
	} 	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	/**
	 * gets back to the previous page of user interface.
	 * @param view
	 */
	public void urgencyList(View view){
		Intent intent = new Intent(this,UrgencyListActivity.class);
		intent.putExtra("manager", manager);
		startActivity(intent);
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
	 * Adds the new patient.
	 * @param view;
	 */
	public void Add_a_patient(View view){
		getHCnumber();
		Intent intent = new Intent(this,RegisterPatientActivity.class);
		intent.putExtra("manager", manager);
		startActivity(intent);
	}
	/**
	 * checks in the new patient along with current time.
	 * @param view;
	 */
	public void checkin(View view){
		EditText hcnumberText = (EditText) findViewById(R.id.textid);
		String hcnumber = hcnumberText.getText().toString();
		time = new GetTime();
		String seeDoctor = time.getArrivalTime();
		if (manager.isExistPitient(hcnumber)){
			manager.addCheckinTime(hcnumber, seeDoctor,"physicians");
			new AlertDialog.Builder(this)
								.setTitle("Recorded!")
								.setMessage(seeDoctor)
								.setPositiveButton("Got it",null)
								.show();
		}else{
			new AlertDialog.Builder(this)
			.setTitle("Warning")
			.setMessage("You check-in an non-existent patient!")
			.setPositiveButton("Got it",null)
			.show();
		}
	}
	/**
	 * Adds the vital sign inputed by the user.
	 * @param view;
	 */
	public void Add_VitalSign(View view){
		getHCnumber();
		EditText hcnumberText = (EditText) findViewById(R.id.textid);
		String hcnumber = hcnumberText.getText().toString();
		if (manager.isExistPitient(hcnumber)){
			Intent intent = new Intent(this,AddVitalsignActivity.class);
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
	 * view_record supports interface activity_login_nurse. It
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
