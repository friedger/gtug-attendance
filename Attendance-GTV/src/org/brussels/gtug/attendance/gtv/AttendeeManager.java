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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.brussels.gtug.attendance.util.PlusWrap;
import org.json.JSONException;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import com.google.api.services.plus.model.Person;

/**
 * This class is responsible for analyzing installed apps. All of the work is
 * done on a separate thread, and progress is reported back through the
 * DataSetObserver set in {@link #addObserver(DataSetObserver). State is held in
 * memory by in memory maintained by a single instance of the ChannelAppManager
 * class. *
 */
public class AttendeeManager {
	private static final String TAG = "Attendance";

	/**
	 * Used to post results back to the UI thread
	 */
	private static Handler mHandler = new Handler();

	/**
	 * Holds the single instance of a ImageManager that is shared by the
	 * process.
	 */
	private static AttendeeManager sInstance;

	/**
	 * Holds the images and related data that have been downloaded
	 */
	private final ArrayList<Attendee> mAttendees = new ArrayList<Attendee>();

	private int mCurrentPosition;

	/**
	 * Observers interested in changes to the current search results
	 */
	private final ArrayList<WeakReference<DataSetObserver>> mObservers = new ArrayList<WeakReference<DataSetObserver>>();

	/**
	 * True if we are in the process of loading
	 */
	private static boolean mLoading;

	private static Context mContext;

	public static AttendeeManager getInstance(Context c) {
		if (sInstance == null) {
			sInstance = new AttendeeManager(c);
		}
		return sInstance;
	}

	private AttendeeManager(Context c) {
		mContext = c;
	}

	/**
	 * @return True if we are still loading content
	 */
	public boolean isLoading() {
		return mLoading;
	}

	/**
	 * Clear all downloaded content
	 */
	public void clear() {
		File cacheDir = mContext.getCacheDir();
		for (File file : cacheDir.listFiles()) {
			file.delete();
		}
		cacheDir.delete();
		for (Attendee item : mAttendees) {
			item.clear();
			item = null;
		}
		mAttendees.clear();
		notifyInvalidateObservers();
	}

	public Attendee getNext() {
		if (mCurrentPosition + 1 <= mAttendees.size() - 1) {
			mCurrentPosition = mCurrentPosition + 1;
			return mAttendees.get(mCurrentPosition);
		}
		return null;
	}

	public Attendee getPrevious() {
		if (mCurrentPosition - 1 >= 0) {
			mCurrentPosition = mCurrentPosition - 1;
			return mAttendees.get(mCurrentPosition);
		}
		return null;
	}

	/**
	 * Add an item to and notify observers of the change.
	 * 
	 * @param item
	 *            The item to add
	 */
	private void add(Attendee item) {
		mAttendees.add(item);
		notifyObservers();
	}

	/**
	 * @return The number of items displayed so far
	 */
	public int size() {
		return mAttendees.size();
	}

	/**
	 * Gets the item at the specified position
	 */
	public Attendee get(int position) {
		mCurrentPosition = position;
		if (mAttendees.size() > position) {
			return mAttendees.get(position);
		}
		return null;
	}

	/**
	 * Adds an observer to be notified when the set of items held by this
	 * ImageManager changes.
	 */
	public void addObserver(DataSetObserver observer) {
		final WeakReference<DataSetObserver> obs = new WeakReference<DataSetObserver>(
				observer);
		mObservers.add(obs);
	}

	Thread mPrevThread = null;

	/**
	 * Load a new set of search results for the specified area.
	 * 
	 * @throws JSONException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public void load() {

		clear();
		mLoading = true;

		mPrevThread = new AttendeesLoader();
		mPrevThread.start();

	}

	/**
	 * Called when something changes in our data set. Cleans up any weak
	 * references that are no longer valid along the way.
	 */
	private void notifyObservers() {
		final ArrayList<WeakReference<DataSetObserver>> observers = mObservers;
		final int count = observers.size();
		for (int i = count - 1; i >= 0; i--) {
			final WeakReference<DataSetObserver> weak = observers.get(i);
			final DataSetObserver obs = weak.get();
			if (obs != null) {
				obs.onChanged();
			} else {
				observers.remove(i);
			}
		}
	}

	/**
	 * Called when something changes in our data set. Cleans up any weak
	 * references that are no longer valid along the way.
	 */
	private void notifyInvalidateObservers() {
		final ArrayList<WeakReference<DataSetObserver>> observers = mObservers;
		final int count = observers.size();
		for (int i = count - 1; i >= 0; i--) {
			final WeakReference<DataSetObserver> weak = observers.get(i);
			final DataSetObserver obs = weak.get();
			if (obs != null) {
				obs.onInvalidated();
			} else {
				observers.remove(i);
			}
		}
	}

	/**
	 * This thread does the actual work of fetching and parsing Panoramio JSON
	 * response data.
	 */
	private static class AttendeesLoader extends Thread {

		public AttendeesLoader() {

		}

		@Override
		public void run() {
			ArrayList<String> users = new ArrayList<String>();
			users.add("107100127479392600261");
			
			PlusWrap pw = new PlusWrap(AttendeeManager.mContext);
			
			if (users != null) {
				for (int i = 0; i < users.size(); i++) {
					final boolean done = (i == users.size() - 1);
					String userId = users.get(i);
					Person u;
					try {
						u = pw.get().people().get(userId).execute();						
						final String name = (String) u.getDisplayName();
						String icon = u.getImage().getUrl();
						Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(icon).getContent());
						
						final Attendee item = new Attendee(mContext, userId, name, bitmap, mHandler);
						mHandler.post(new Runnable() {
							public void run() {
								sInstance.mLoading = !done;
								sInstance.add(item);
							}
						});
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
					
					
				}
			} else {
				mHandler.post(new Runnable(){
					public void run(){						
					}
				});
				
			}
		}
		
	}
}
