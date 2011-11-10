package org.brussels.gtug.attendance.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.brussels.gtug.attendance.Constants;
import org.brussels.gtug.attendance.R;
import org.brussels.gtug.attendance.Util;
import org.brussels.gtug.attendance.model.Event;
import org.brussels.gtug.attendance.util.Http;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetailActivity extends Activity implements OnClickListener {	
	
	public static final String EXTRA_EVENT = "extraEvent";
	
	private Event event;
	
	private TextView textName;
	private Button checkin;
	
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
		checkin = (Button) findViewById(R.id.checkin);
		checkin.setOnClickListener(this);
	}
	
	protected void showEvent() {
		textName.setText(event.getName());
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.checkin:
				checkin();
				break;
		}
		
	}
	
	protected void checkin() {
		
		SharedPreferences settings = Util.getSharedPreferences(this);
		final String accountName = settings.getString(Util.ACCOUNT_NAME, "Unknown");
		final int eventId = event.getId(); 
		
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				
				List<NameValuePair> postParams = new ArrayList<NameValuePair>();
				postParams.add(new BasicNameValuePair("eventId", ""+eventId));
				postParams.add(new BasicNameValuePair("accountName", ""+accountName));
				return Http.post(Constants.APP_SERVER_URL + Constants.ROUTE_CHECKIN, postParams);
			}
			
			protected void onPostExecute(String result) {
				if (result != null && result.toLowerCase().equals("ok")) {
					Toast.makeText(EventDetailActivity.this, "You're checked in for this event!", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(EventDetailActivity.this, "Sorry, something went wrong during checkin!", Toast.LENGTH_LONG).show();
				}
			}
			
		}.execute();
	}
		
	
}
