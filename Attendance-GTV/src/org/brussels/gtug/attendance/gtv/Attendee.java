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

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Holds one item returned from the PackageManager.
 */
public class Attendee implements Parcelable {

	private Context mContext;
	private String mUserId;
	private String mName;
	private Bitmap mIcon;

	public Attendee(Context context, String userId,
			String name, Bitmap bitmap, Handler handler) {
		mContext = context;
		
		mUserId = userId;
		mName = name;
		mIcon = bitmap;
	}

	public long getId() {
		return Long.parseLong(mUserId);
	}
	
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(mUserId);
		parcel.writeString(mName);
	}

	public void clear() {
	}

	public String getName() {
		return mName;
	}
	
	public Bitmap getIcon(){
		return mIcon;
	}

}
