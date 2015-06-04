package com.example.phaseii;

import com.example.phaseii.Data.VitalSign;
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
 * This supports the activity_add_vital interface.
 * Allows the add vital signs page to compile and function.
 * @author Tony, Andrew, Joseph, Kim, Dante
 *
 */
public class AddVitalsignActivity extends Activity {
	
	//private Patient patient;
    private PatientManager manager;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_vital);
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
	 * update_vital_sign supports the android interface AddVitalsignActivity.
	 * It updates the patient's vital sign information by  showing user the 
	 * newest vital sign.
	 * @param view;
	 */
	public void update_vital_sign(View view){
		EditText systolicText = (EditText) findViewById(R.id.input_blood_pressure);
		EditText diastolicText = (EditText) findViewById(R.id.diastolic);
		EditText input_temperatureText = (EditText) findViewById(R.id.input_temperature);
		EditText input_heart_rateText = (EditText) findViewById(R.id.input_heart_rate);
		TextView hcnumberText = (TextView) findViewById(R.id.hcnumbertext);
		
		String temperature = input_temperatureText.getText().toString();
		String systolic = systolicText.getText().toString();
		String diastolic = diastolicText.getText().toString();
		String heart_rate = input_heart_rateText.getText().toString();
		String hcnumber = hcnumberText.getText().toString();
		
		if (manager.isExistPitient(hcnumber)){	
			if (("".equals(temperature)) || ("".equals(systolic)) || ("".equals(diastolic)) || ("".equals(heart_rate)) || ("".equals(hcnumber))){
				new AlertDialog.Builder(this)
				.setTitle("Warning!")
				.setMessage("Invalid Empty")
				.setPositiveButton("Got it",null)
				.show();
			}else{
				double double_temperature = Double.parseDouble(temperature);
				int int_systolic = Integer.valueOf(systolic).intValue();
				int int_diastolic = Integer.valueOf(diastolic).intValue();
				int int_heart = Integer.valueOf(heart_rate).intValue();
				if(int_systolic > int_diastolic){
					String bloodpressure = systolic+"-"+diastolic;
					VitalSign vitalsign = new VitalSign(double_temperature,bloodpressure,int_heart);
					GetTime gettime = new GetTime();
					String time = (String) gettime.getArrivalTime();
					manager.addVitalsign(hcnumber, vitalsign);
					manager.saveToFile();		
					new AlertDialog.Builder(this)
					.setTitle("Recorded!")
					.setMessage("Record Time:"+time)
					.setPositiveButton("Got it",null)
					.show();
				}
				else{
					new AlertDialog.Builder(this)
					.setTitle("Warning!")
					.setMessage("Invalid Blood Pressure")
					.setPositiveButton("Got it",null)
					.show();
				}
			}
		}
		else{
			new AlertDialog.Builder(this)
			.setTitle("Warning!")
			.setMessage("Cannot find this patient!")
			.setPositiveButton("Got it",null)
			.show();
		}
	}
	/**
	 * Returns to the previous page.
	 * @param view
	 */
	public void back(View view){
		Intent intent = new Intent(this,LoginNurseActivity.class);
		startActivity(intent);
	}
}
