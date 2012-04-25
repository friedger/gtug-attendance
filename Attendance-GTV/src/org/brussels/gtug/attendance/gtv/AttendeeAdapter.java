/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brussels.gtug.attendance.gtv;

import org.brussels.gtug.attendance.gtv.R;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter used to bind data for the GridView in ImageGrid Activity. This
 * Adapter is updated by the ImageManager.
 */
public class AttendeeAdapter extends BaseAdapter {

	/**
	 * Maintains the state of our data
	 */
	private final AttendeeManager mAttendeeManager;

	private final Context mContext;

	private final MyDataSetObserver mObserver;

	/**
	 * Used by the {@link AttendeeManager} to report changes in the list back to
	 * this adapter.
	 */
	private class MyDataSetObserver extends DataSetObserver {
		@Override
		public void onChanged() {
			notifyDataSetChanged();
		}

		@Override
		public void onInvalidated() {
			notifyDataSetInvalidated();
		}
	}

	public AttendeeAdapter(Context c) {
		mAttendeeManager = AttendeeManager.getInstance(c);
		mContext = c;
		mObserver = new MyDataSetObserver();
		mAttendeeManager.addObserver(mObserver);
	}

	/**
	 * Returns the number of images to display
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount() {
		return mAttendeeManager.size();
	}

	/**
	 * Returns the image at a specified position
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	public Object getItem(int position) {
		return mAttendeeManager.get(position);
	}

	/**
	 * Returns the id of an image at a specified position
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	public long getItemId(int position) {
		final Attendee a = mAttendeeManager.get(position);
		return a.getId();
	}

	/**
	 * Returns a view to display the image at a specified position
	 * 
	 * @param position
	 *            The position to display
	 * @param convertView
	 *            An existing view that we can reuse. May be null.
	 * @param parent
	 *            The parent view that will eventually hold the view we return.
	 * @return A view to display the image at a specified position
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			// Make up a new view
			final LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.channel_app_item, null);
		} else {
			// Use convertView if it is available
			view = convertView;
		}
		final Attendee channelItem = mAttendeeManager.get(position);

		if (channelItem != null) {
			final ImageView imageView = (ImageView) view
					.findViewById(R.id.logo);
			imageView.setImageBitmap(channelItem.getIcon());

			final TextView textView = (TextView) view
					.findViewById(R.id.channel);
			Log.v("TAG", channelItem.getName());
			textView.setText(channelItem.getName());
		}

		return view;
	}
}
