package org.brussels.gtug.attendance.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.brussels.gtug.attendance.Constants;
import org.brussels.gtug.attendance.Util;
import org.brussels.gtug.attendance.adapter.MeetingAdapter;
import org.brussels.gtug.attendance.model.Meeting;
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

public class MeetingsActivity extends ListActivity implements OnItemClickListener {

	private List<Meeting> meetings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getListView().setOnItemClickListener(this);
		
		new AsyncTask<Void, Void, List<Meeting>>() {

			@Override
			protected List<Meeting> doInBackground(Void... params) {
				
				List<Meeting> meetings = new ArrayList<Meeting>();
				String response = Http.get(Constants.APP_SERVER_URL + "?format=json");
				try {
					JSONArray data = new JSONArray(response);
					Meeting meeting = null;
					for (int i=0; i<data.length(); i++) {
						JSONObject object = data.getJSONObject(i);
						meeting = new Meeting();
						meeting.setId(object.getInt("id"));
						meeting.setName(object.getString("name"));
						meeting.setDescription(object.getString("description"));
						meeting.setUrl(object.getString("url"));
						meetings.add(meeting);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return meetings;
			}
			
			
			@Override
			protected void onPostExecute(List<Meeting> result) {
				meetings = result;
				getListView().setAdapter(
						new MeetingAdapter(
								MeetingsActivity.this, 
								android.R.layout.simple_list_item_1,
								meetings
								));
				
			}
		}.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		Meeting meeting = (Meeting) arg0.getItemAtPosition(arg2);

		final SharedPreferences settings = Util.getSharedPreferences(this);
		final String accountName = settings.getString(Util.ACCOUNT_NAME, "Unknown");
		 
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("meeting_id", ""+meeting.getId()));
		params.add(new BasicNameValuePair("accountName", accountName));
		 
		String response = Http.post(Constants.APP_SERVER_URL + Constants.ROUTE_SIGNUP, params);
		 
		Toast.makeText(this, "signup response: " + response, Toast.LENGTH_LONG).show();
	}
	
}
