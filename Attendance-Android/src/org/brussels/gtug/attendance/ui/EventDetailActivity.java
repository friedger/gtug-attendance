package org.brussels.gtug.attendance.ui;

import org.brussels.gtug.attendance.R;
import org.brussels.gtug.attendance.model.Event;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EventDetailActivity extends Activity {	
	
	public static final String EXTRA_EVENT = "extraEvent";
	
	private Event event;
	
	private TextView textName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail);
		findAndSetupViews();
		
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey(EXTRA_EVENT)) {
			event = (Event) extras.getSerializable(EXTRA_EVENT);
			showEvent();
		} else {
			finish();
		}
	}
	
	protected void findAndSetupViews() {
		textName = (TextView) findViewById(R.id.name);
	}
	
	protected void showEvent() {
		textName.setText(event.getName());
	}
		
	
}
