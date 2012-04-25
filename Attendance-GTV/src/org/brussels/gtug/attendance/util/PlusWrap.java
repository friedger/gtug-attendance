/*
 * Copyright 2011 Google Inc.
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

package org.brussels.gtug.attendance.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessProtectedResource;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.json.JsonHttpRequest;
import com.google.api.client.http.json.JsonHttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusRequest;

public class PlusWrap {
  private static final HttpTransport HTTP_TRANSPORT =
      AndroidHttp.newCompatibleTransport();
  private static final JsonFactory JSON_FACTORY = new GsonFactory();
  private final Plus plus;

  public PlusWrap(Context ctx) {
    final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
    final String accessToken = prefs.getString(AuthUtils.PREF_TOKEN_GPLUS, null);
    final GoogleAccessProtectedResource protectedResource =
        new GoogleAccessProtectedResource(accessToken);

    plus = Plus.builder(HTTP_TRANSPORT, JSON_FACTORY)
       .setApplicationName("Attendance for Google TV")
       .setHttpRequestInitializer(protectedResource)
       .setJsonHttpRequestInitializer(new JsonHttpRequestInitializer() {
           @Override
           public void initialize(JsonHttpRequest request) {
             PlusRequest plusRequest = (PlusRequest) request;
             plusRequest.setKey("");
           }
       })
       .build();
  }

  public Plus get() {
    return plus;
  }
}