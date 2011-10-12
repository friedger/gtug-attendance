package org.brussels.gtug.attendance.service;

import org.brussels.gtug.attendance.Constants;
import org.brussels.gtug.attendance.util.Http;

import android.app.IntentService;
import android.content.Intent;

public class AttendanceService extends IntentService {

	public AttendanceService(String name) {
		super("AttendanceService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		
		if (intent.getAction().equals(Constants.ACTION_MEETINGS)) {
			
			
			
			
		}

	}

}
