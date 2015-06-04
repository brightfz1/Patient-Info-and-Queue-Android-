package com.example.phaseii;

import java.util.Map;

import com.example.phaseii.Manager.PatientManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
/**
 * This Android activity supports activity_view_record,
 * which allows the nurse and physician view patient's record.
 * 
 * @author Tony, Andrew, Joseph, Kim, Dante
 *
 */
public class ViewRecordActivity extends Activity {

	private PatientManager manager;
	private Map<String, String> record;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_record);
		Intent intent = getIntent();
		manager = (PatientManager) intent.getSerializableExtra("manager");
		Transit transit = (Transit)getApplicationContext();
		String hcnumber = transit.getHcnumber();
		if (!hcnumber.equals(null)){
			TextView textView = (TextView) this.findViewById(R.id.hcnumbertext);
			textView.setText(hcnumber);
		}
		record = manager.getRecord(hcnumber);
		
		TextView name = (TextView) findViewById(R.id.nametext);
		name.setText(record.get("name"));
		TextView birthday = (TextView) findViewById(R.id.birthdaytext);
		birthday.setText(record.get("birthday"));
		TextView arrivaltime = (TextView) findViewById(R.id.arrivaltimetext);
		arrivaltime.setText(record.get("arrivaltime"));
		TextView urgency = (TextView) findViewById(R.id.urgencytext);
		urgency.setText(record.get("urgency"));
		TextView seedoctor = (TextView) findViewById(R.id.seedoctortext);
		seedoctor.setText(record.get("seeDoctor"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_record, menu);
		return true;
	}
	
	public void vitalsign(View view){
		Intent intent = new Intent(this,ViewVitalSignActivity.class);
		intent.putExtra("manager", manager);
		startActivity(intent);
	}
	
	public void prescription(View view){
		Intent intent = new Intent(this,ViewPrescriptionActivity.class);
		intent.putExtra("manager", manager);
		startActivity(intent);
	}
	
	/**
	 * Returns to the previous page.
	 * @param view
	 */
	public void back(View view){
		Transit transit = (Transit)getApplicationContext();
		String backpage = transit.getBackpage();
		if (backpage.equals("nurse")){
			Intent intent = new Intent(this,LoginNurseActivity.class);
			startActivity(intent);
		}else if (backpage.equals("physicians")){
			Intent intent = new Intent(this,LoginPhysiciansActivity.class);
			startActivity(intent);
		}	
	}
}
