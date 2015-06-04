package com.example.phaseii;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.example.phaseii.Manager.GetTime;
import com.example.phaseii.Manager.PatientManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
/**
 * This Android activity supports activity_urgency_listview and 
 * activity_urgency_itemview, which shows the user the urgencylist in 
 * both list and several tiems 
 * 
 * @author Tony, Andrew, Joseph, Kim, Dante
 *
 */
public class UrgencyListActivity extends Activity {
	
	private PatientManager manager;
	private Transit transit;
	private ArrayList<HashMap<String, Object>> listItem;
	private SimpleAdapter listItemAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_urgency_listview);
		
		ListView list = (ListView) findViewById(R.id.urgencyListView);
		Intent intent = getIntent();
		manager = (PatientManager) intent.getSerializableExtra("manager");
		
		listItem = new ArrayList<HashMap<String, Object>>(); 
		Map<String,ArrayList<String>> urgencymap = manager.getUrgencyList();
		ArrayList<String> urgencylist = urgencymap.get("urgent");
		urgencylist.addAll(urgencymap.get("less_urgent"));
		urgencylist.addAll(urgencymap.get("non_urgent"));
		urgencylist.addAll(urgencymap.get("nonnon_urgent"));
		
		Iterator<String> iter = urgencylist.iterator();
		while (iter.hasNext()){
			String[] item = iter.next().split("-");
			String name = item[1];
			String hcnumber = item[0];
			String urgencyScore = item[2];
			String text = hcnumber+"  "+urgencyScore;
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemTitle",name);
            map.put("ItemText", "HC Number: "+text);
            listItem.add(map); 
		}
				
		listItemAdapter = new SimpleAdapter(this,listItem,
				R.layout.activity_urgency_itemview,            
	            new String[] {"ItemTitle", "ItemText"},    
	            new int[] {R.id.ItemTitle,R.id.ItemText}
				
	        );
		list.setAdapter(listItemAdapter); 
		
        list.setOnItemClickListener(new OnItemClickListener() {  
  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                    long arg3) {  
                setTitle("Hold Down to display More");
            }  
        });  
            
        list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {  
              
            @Override  
            public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
                MenuInflater inflater=getMenuInflater();
                inflater.inflate(R.menu.urgencymenu, menu);
            }
            
            
        });
	} 
	
	 @Override  
	    public boolean onContextItemSelected(MenuItem item) {  
		 AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
		 int position = menuInfo.position;
		 HashMap<String, Object> each_item = listItem.get(position);
		 String hcnumber_raw = (String)each_item.get("ItemText");
		 String hcnumber = hcnumber_raw.split(" ")[2];
		 transit = (Transit)getApplicationContext();
		 transit.setHcnumber(hcnumber);
		
		 switch (item.getItemId()) {
		 	case R.id.urgency_add_vitalsign:{
				Intent intent = new Intent(this,AddVitalsignActivity.class);
				intent.putExtra("manager", manager);
				startActivity(intent);
				break;
		 	}
		 	case R.id.urgency_checkin:{ 
		 		GetTime time = new GetTime();
				String seeDoctor = time.getArrivalTime();
				manager.addCheckinTime(hcnumber, seeDoctor,"physicians");
				new AlertDialog.Builder(this)
				.setTitle("Recorded!")
				.setMessage(seeDoctor)
				.setPositiveButton("Got it",null)
				.show();
				break;
		 	}
		 	case R.id.urgency_record:{
		 		Intent intent = new Intent(this,ViewRecordActivity.class);
				intent.putExtra("manager", manager);
				startActivity(intent);
		 		break;
		 	}
		 	case R.id.backbutton:{
		 		Intent intent = new Intent(this,LoginNurseActivity.class);
				startActivity(intent);
		 		break;
		 	}
		 }
		 return true;
	    } 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
