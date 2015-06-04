package com.example.phaseii;

import java.util.ArrayList;

import com.example.phaseii.Manager.PatientManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
/**
 * This Android activity supports activity_view_prescription,
 * which allows the user view the patient's prescription.
 * 
 * @author Tony, Andrew, Joseph, Kim, Dante
 *
 */
public class ViewPrescriptionActivity extends Activity {

	private PatientManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_prescription);
		Intent intent = getIntent();
		Transit transit = (Transit)getApplicationContext();
		String hcnumber = transit.getHcnumber();
		manager = (PatientManager) intent.getSerializableExtra("manager");
		ArrayList<String>prescription = manager.getPrescription(hcnumber);
		if (!prescription.isEmpty()){
			String medication = prescription.get(0);
			String instruction = prescription.get(1);
			
			TextView textView = (TextView) this.findViewById(R.id.medication);
			textView.setText(medication);
			TextView textView_instruction = (TextView) this.findViewById(R.id.instruction);
			textView_instruction.setText(instruction);
		}	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_prescription, menu);
		return true;
	}
	/**
	 * Returns to the previous page.
	 * @param view
	 */
	public void back(View view){
		Intent intent = new Intent(this,ViewRecordActivity.class);
		intent.putExtra("manager", manager);
		startActivity(intent);
	}
}
