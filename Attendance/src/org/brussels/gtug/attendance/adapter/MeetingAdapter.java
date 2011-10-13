package org.brussels.gtug.attendance.adapter;

import java.util.List;

import org.brussels.gtug.attendance.model.Meeting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MeetingAdapter extends ArrayAdapter<Meeting>{

	private int viewResourceId;
	
	public MeetingAdapter(Context context, int textViewResourceId,
			List<Meeting> items) {
		super(context, textViewResourceId, items);
		viewResourceId = textViewResourceId;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(viewResourceId, null);
		}
		Meeting m = getItem(position);
		if (m != null) {
			TextView tt = (TextView) v.findViewById(android.R.id.text1);
			if (tt != null) {
				tt.setText(m.getName());
			}			
		}
		return v;
	}

	
}
