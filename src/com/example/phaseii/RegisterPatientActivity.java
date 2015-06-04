package com.example.phaseii;


import com.example.phaseii.Data.Patient;
import com.example.phaseii.Manager.GetTime;
import com.example.phaseii.Manager.PatientManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This activity allows register_patient interface to compile.
 * This is used to register a new patient.
 * @author Tony, Andrew, Joseph, Kim, Dante
 *
 */
public class RegisterPatientActivity extends Activity {
	
	//private Patient patient;
    private PatientManager manager;
    private Transit transit;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_patient);
		Intent intent = getIntent();
		manager = (PatientManager) intent.getSerializableExtra("manager");
		transit = (Transit)getApplicationContext();
		if (!transit.getHcnumber().equals(null)){
			TextView textView = (TextView) this.findViewById(R.id.texthcnumber_field);
			textView.setText(transit.getHcnumber());
		}
		
	} 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Register a new patient, utilizing the interface register_patient. The
	 * user's input is taken and used to create a new patient. If the
	 * information is invalid, an error will be raise. Else, a new patient
	 * is created.
	 * @param view
	 */
	public void Register_A_Patient(View view){
		
		EditText lastnameText = (EditText) findViewById(R.id.textlastname_field);
		EditText firstnameText = (EditText) findViewById(R.id.textfirstname_field);
		EditText dobText = (EditText) findViewById(R.id.textdob_field);
		EditText hcnumberText = (EditText) findViewById(R.id.texthcnumber_field);
		String lastname = lastnameText.getText().toString();
		String firstname = firstnameText.getText().toString();
		String dob = dobText.getText().toString();
		String hcnumber = hcnumberText.getText().toString();
		
		if (!manager.isExistPitient(hcnumber)){		
			char a = '/';
			if (("".equals(lastname)) || ("".equals(firstname)) || ("".equals(dob)) || ("".equals(hcnumber))){
				new AlertDialog.Builder(this)
				.setTitle("Warning")
				.setMessage("Invalid Empty")
				.setPositiveButton("Got it",null)
				.show();
			}
			else{
				if ((dob.length() == 10) && (a == dob.charAt(4)) && (a == dob.charAt(7))){
					String [] name = {lastname,firstname};			
					Patient patient = new Patient(name,dob,hcnumber);
					manager.addPatient(patient);
					manager.saveToFile();
					
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					GetTime gettime = new GetTime();
					String time = (String) gettime.getArrivalTime();
					builder.setMessage("Arrival Time:"+time);
					builder.setTitle("Registered!");
					builder.setPositiveButton("Got it",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
									Intent i = new Intent(RegisterPatientActivity.this,LoginNurseActivity.class);
									startActivity(i);	
								}
							});
					builder.create().show(); 
				}
				else{
					new AlertDialog.Builder(this)
					.setTitle("Warning")
					.setMessage("Invalid Day of Birth")
					.setPositiveButton("Got it",null)
					.show();
				}
			}
		}
		else{
			new AlertDialog.Builder(this)
			.setTitle("Warning")
			.setMessage("You added an existing patient!")
			.setPositiveButton("Got it",null)
			.show();
		}
	}
	
	public void back(View view){
		Intent intent = new Intent(this,LoginNurseActivity.class);
		startActivity(intent);
	}
}
