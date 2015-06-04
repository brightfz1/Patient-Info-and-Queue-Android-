package com.example.phaseii;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.example.phaseii.Manager.PatientManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
/**
 * This supports the activity_vitalsign_listview and activity_vitalsign_itemview 
 * interface, which allows nurse to view the vital sign page.
 * @author Tony, Andrew, Joseph, Kim, Dante
 *
 */
public class ViewVitalSignActivity extends Activity {

	private PatientManager manager;
	private ArrayList<HashMap<String, Object>> listItem;
	private SimpleAdapter listItemAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vitalsign_listview);
		
		Intent intent = getIntent();
		Transit transit = (Transit)getApplicationContext();
		String hcnumber = transit.getHcnumber();
		manager = (PatientManager) intent.getSerializableExtra("manager");
		Map<String,String> vitalsign = manager.getVitasign(hcnumber);
		
		
		ListView list = (ListView) findViewById(R.id.vitalsignListView);
		listItem = new ArrayList<HashMap<String, Object>>(); 
		
		Set<String>vitalsign_set = vitalsign.keySet();
		Iterator<String> iter = vitalsign_set.iterator();
		while (iter.hasNext()){
			String title = iter.next();
			String text = vitalsign.get(title);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemTitle",title);
			map.put("ItemText",text);
			listItem.add(map);
		}
		
		listItemAdapter = new SimpleAdapter(this,listItem,
				R.layout.activity_urgency_itemview,            
	            new String[] {"ItemTitle", "ItemText"},    
	            new int[] {R.id.ItemTitle,R.id.ItemText}
				
	        );
		list.setAdapter(listItemAdapter); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_vital_sign, menu);
		return true;
	}
}
