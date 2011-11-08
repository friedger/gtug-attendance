package org.brussels.gtug.attendance.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.brussels.gtug.attendance.Constants;
import org.brussels.gtug.attendance.Util;
import org.brussels.gtug.attendance.adapter.MeetingAdapter;
import org.brussels.gtug.attendance.model.Event;
import org.brussels.gtug.attendance.util.Http;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class EventActivity extends ListActivity implements OnItemClickListener {

	private static final String KEY_EVENTS = "keyEvents";
	
	private List<Event> events;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getListView().setOnItemClickListener(this);
		
		if (savedInstanceState != null && savedInstanceState.containsKey(KEY_EVENTS)) {
			events = (List<Event>) savedInstanceState.getSerializable(KEY_EVENTS);
			showEvents();
		} else {
			getEvents();
		}
	}
		
	protected void getEvents() {
		new AsyncTask<Void, Void, List<Event>>() {

			@Override
			protected List<Event> doInBackground(Void... params) {
				
				List<Event> meetings = new ArrayList<Event>();
				String response = Http.get(Constants.APP_SERVER_URL + Constants.ROUTE_EVENTS);
				try {
					JSONArray data = new JSONArray(response);
					Event event = null;
					for (int i=0; i<data.length(); i++) {
						JSONObject object = data.getJSONObject(i);
						event = new Event();
						event.setId(object.getInt("id"));
						event.setName(object.getString("name"));
						//meeting.setDescription(object.getString("description"));
						event.setUrl(object.getString("url"));
						String timeZone = object.getString("timeZone");
						event.setStartDate(Util.parseJsonDate(object.getString("startDate"), timeZone));
						event.setEndDate(Util.parseJsonDate(object.getString("endDate"), timeZone));
						meetings.add(event);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return meetings;
			}
			
			
			@Override
			protected void onPostExecute(List<Event> result) {
				
				Collections.sort(result);
				Collections.reverse(result);
				
				events =  result;
				
				showEvents();
				
			}
		}.execute();
	}
	
	protected void showEvents() {
		
		getListView().setAdapter(
				new MeetingAdapter(
						EventActivity.this, 
						android.R.layout.simple_list_item_2,
						events
						));
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(KEY_EVENTS, (Serializable) events);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		Event event = (Event) arg0.getItemAtPosition(arg2);

		final SharedPreferences settings = Util.getSharedPreferences(this);
		final String accountName = settings.getString(Util.ACCOUNT_NAME, "Unknown");
		 
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("eventId", ""+event.getId()));
		params.add(new BasicNameValuePair("accountName", accountName));
		 
		String response = Http.post(Constants.APP_SERVER_URL + Constants.ROUTE_CHECKIN, params);
		 
		Toast.makeText(this, "signup response: " + response, Toast.LENGTH_LONG).show();
	}
	
}
