package com.example.phaseii;

import android.app.Application;

public class Transit extends Application {
	private String hcnumber;
	private String backpage;

	public String getBackpage() {
		return backpage;
	}

	public void setBackpage(String backpage) {
		this.backpage = backpage;
	}

	public String getHcnumber() {
		return hcnumber;
	}

	public void setHcnumber(String hcnumber) {
		this.hcnumber = hcnumber;
	}
}
