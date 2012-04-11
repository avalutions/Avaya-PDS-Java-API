package com.avalutions.dialer.common;

import java.util.ArrayList;
import java.util.List;

public class Job {
	private CallType callType;
	private String name;
	private List<Screen> screens;
	private JobStatus jobStatus;
	
	public Job(String name, CallType callType, JobStatus jobStatus) {
		screens = new ArrayList<Screen>();
		
		this.name = name;
		this.callType = callType;
		this.jobStatus = jobStatus;
	}
	
	public void addScreen(Screen screen) {
		screens.add(screen);
	}

	public CallType getCallType() {
		return callType;
	}

	public String getName() {
		return name;
	}

	public List<Screen> getScreens() {
		return screens;
	}

	public JobStatus getJobStatus() {
		return jobStatus;
	}
}
