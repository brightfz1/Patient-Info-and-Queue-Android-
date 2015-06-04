package com.example.phaseii;

import com.example.phaseii.Data.Prescription;
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
 * This supports the activity_add_prescription interface.
 * Allows the add prescription page to compile and function.
 * @author Tony, Andrew, Joseph, Kim, Dante
 *
 */
public class AddPrescription extends Activity {
	
	//private Patient patient;
    private PatientManager manager;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_prescription);
		Intent intent = getIntent();
		manager = (PatientManager) intent.getSerializableExtra("manager");
		Transit transit = (Transit)getApplicationContext();
		if (!transit.getHcnumber().equals(null)){
			TextView textView = (TextView) this.findViewById(R.id.hcnumbertext);
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
	 * add_prescription the android interface AddPrescription.
	 * It add prescription information by showing user the 
	 * prescription.
	 * @param view;
	 */
	public void add_prescription(View view){
		EditText input_medicationText = (EditText) findViewById(R.id.input_medication);
		EditText input_instructionsText = (EditText) findViewById(R.id.input_instructions);
		TextView hcnumberText = (TextView) findViewById(R.id.hcnumbertext);
		
		String medication = input_medicationText.getText().toString();
		String instruction = input_instructionsText.getText().toString();
		String hcnumber = hcnumberText.getText().toString();
		
		if (manager.isExistPitient(hcnumber)){	
			if (("".equals(medication)) || ("".equals(instruction)) || ("".equals(hcnumber))){
				new AlertDialog.Builder(this)
				.setTitle("Warning!")
				.setMessage("Invalid Empty")
				.setPositiveButton("Got it",null)
				.show();
			}else{
				Prescription prescription = new Prescription(medication,instruction);
				manager.addPrescription(hcnumber, prescription);
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				GetTime gettime = new GetTime();
				String time = (String) gettime.getArrivalTime();
				builder.setMessage("Record Time:"+time);
				builder.setTitle("Recorded!");
				builder.setPositiveButton("Got it",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								Intent i = new Intent(AddPrescription.this,LoginPhysiciansActivity.class);
								startActivity(i);	
							}
						});
				builder.create().show();
			}
		}else{
			new AlertDialog.Builder(this)
			.setTitle("Warning!")
			.setMessage("Cannot find this patient!")
			.setPositiveButton("Got it",null)
			.show();
		}
	}
	
	public void back(View view){
		Intent intent = new Intent(this,LoginPhysiciansActivity.class);
		startActivity(intent);
	}
}
