package org.brussels.gtug.attendance.adapter;

import java.util.List;

import org.brussels.gtug.attendance.model.Event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MeetingAdapter extends ArrayAdapter<Event>{

	private int viewResourceId;
	
	public MeetingAdapter(Context context, int textViewResourceId,
			List<Event> items) {
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
		Event m = getItem(position);
		if (m != null) {
			TextView t1 = (TextView) v.findViewById(android.R.id.text1);
			if (t1 != null) {
				t1.setText(m.getName());
			}
			TextView t2 = (TextView) v.findViewById(android.R.id.text2);
			if (t2 != null) {
				t2.setText(m.getName());
			}
		}
		return v;
	}

	
}
