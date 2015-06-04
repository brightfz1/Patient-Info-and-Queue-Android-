package com.example.phaseii;

import java.io.File;

import com.example.phaseii.Manager.NurseManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * This android activity uses activity_main interface
 * to create a main menu of the android program.
 * @author Tony, Andrew, Joseph, Kim, Dante
 *
 */
public class MainActivity extends Activity {
	
	/**
	 * password.txt is used to store the username and password.
	 */
	public static final String FILENAME = "passwords.txt";
	private File filepath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * LoginAdoctor supports the android interface activity_main. It takes in
	 * the user provided information, and compares them to the username and
	 * password. If they match, they are logged in. Else, an error will be
	 * raised.
	 * @param view
	 */
	public void LoginAdoctor(View view){
		
		filepath = new File(this.getApplicationContext().getFilesDir(),FILENAME);
		String finalpath = filepath.toString();
		File finalfile = new File(finalpath);
		
		EditText usernameText = (EditText) findViewById(R.id.textusername_field);
		EditText passwordText = (EditText) findViewById(R.id.textpassword_field);
		String username = usernameText.getText().toString();
		String password = passwordText.getText().toString();
		RadioButton clickNurse = (RadioButton)findViewById(R.id.Nurse);
		
		String whoLogin;
		
		if(clickNurse.isChecked()){
			whoLogin = "nurse";
		}
		else{
			whoLogin = "physicians";
		}
		
		if(!"".equals(username) && !"".equals(password)){
			NurseManager verifydoctor = new NurseManager(finalfile,username,password,whoLogin);
			if (verifydoctor.verify()){
				if (whoLogin.equals("nurse")){
					Intent intent = new Intent(this,LoginNurseActivity.class);
					startActivity(intent);
				}
				else{
					Intent intent = new Intent(this,LoginPhysiciansActivity.class);
					startActivity(intent);
				}
			}
			else{
				new AlertDialog.Builder(this)
				.setTitle("Warning")
				.setMessage("Incorrect Username or Password")
				.setPositiveButton("Got it",null)
				.show();
			}
		}
		else{
			new AlertDialog.Builder(this)
			.setTitle("Warning")
			.setMessage("Empty Username or Password")
			.setPositiveButton("Got it",null)
			.show();
		}
	}
}
