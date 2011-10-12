package org.brussels.gtug.attendance.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.brussels.gtug.attendance.Constants;
import org.brussels.gtug.attendance.util.Http;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MeetingsActivity extends ListActivity implements OnItemClickListener {

	private Map<Integer, String> meetings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getListView().setOnItemClickListener(this);
		
		new AsyncTask<Void, Void, Map<Integer, String>>() {

			@Override
			protected Map<Integer, String> doInBackground(Void... params) {
				
				Map<Integer, String> meetings = new HashMap<Integer, String>();
				String response = Http.get(Constants.APP_SERVER_URL + "?format=json");
				try {
					JSONArray data = new JSONArray(response);
					for (int i=0; i<data.length(); i++) {
						JSONObject object = data.getJSONObject(i);
						meetings.put(object.getInt("id"), object.getString("name"));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return meetings;
			}
			
			
			@Override
			protected void onPostExecute(Map<Integer, String> result) {
				
				meetings = result;
				
				ArrayList<String> names = new ArrayList<String>(meetings.values());
				
				getListView().setAdapter(
						new ArrayAdapter<String>(
								MeetingsActivity.this, 
								android.R.layout.simple_list_item_1, 
								names));
				
			}
		}.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		if (meetings.keySet().size() >= arg2) {
			
			Toast.makeText(this, "todo: signup for meeting", Toast.LENGTH_LONG).show();
			
			// TODO signup for meeting
			
		}
		
	}
	
}
