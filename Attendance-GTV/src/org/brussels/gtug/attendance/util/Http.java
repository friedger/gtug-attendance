package org.brussels.gtug.attendance.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class Http {

	private static final String TAG = "Http";

	public static String post(String url, List<NameValuePair> params) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse response = httpclient.execute(post);
			StringBuffer responseBuffer = new StringBuffer();
			InputStream instream = response.getEntity().getContent();
			InputStreamReader reader = new InputStreamReader(instream, "UTF-8");
		    BufferedReader in = new BufferedReader(reader);
		    String readed;
		    while ((readed = in.readLine()) != null) {
		    	responseBuffer.append(readed);
		    }
		    String responseString = responseBuffer.toString();
		    Log.d(TAG, responseString);
		    return responseString;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String get(String url) {
		try {
			Log.d(TAG, "Get url: " + url);
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);
			
			request.addHeader("Content-type", "application/json");
			
			BasicResponseHandler handler = new BasicResponseHandler();
			String response;
			response = httpClient.execute(request, handler);
			Log.d(TAG, "Get response: " + response);
			return response;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
