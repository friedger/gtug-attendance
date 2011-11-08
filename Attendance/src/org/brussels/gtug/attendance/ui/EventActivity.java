package org.brussels.gtug.attendance.ui;

import java.util.ArrayList;
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

	private List<Event> events;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getListView().setOnItemClickListener(this);
		
		new AsyncTask<Void, Void, List<Event>>() {

			@Override
			protected List<Event> doInBackground(Void... params) {
				
				List<Event> meetings = new ArrayList<Event>();
				String response = Http.get(Constants.APP_SERVER_URL + Constants.ROUTE_EVENTS);
				try {
					JSONArray data = new JSONArray(response);
					Event meeting = null;
					for (int i=0; i<data.length(); i++) {
						JSONObject object = data.getJSONObject(i);
						meeting = new Event();
						meeting.setId(object.getInt("id"));
						meeting.setName(object.getString("name"));
						//meeting.setDescription(object.getString("description"));
						meeting.setUrl(object.getString("url"));
						meetings.add(meeting);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return meetings;
			}
			
			
			@Override
			protected void onPostExecute(List<Event> result) {
				events = result;
				getListView().setAdapter(
						new MeetingAdapter(
								EventActivity.this, 
								android.R.layout.simple_list_item_1,
								events
								));
				
			}
		}.execute();
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
