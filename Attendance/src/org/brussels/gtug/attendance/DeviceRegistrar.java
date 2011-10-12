package org.brussels.gtug.attendance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Register/unregister with the third-party server
 */
public class DeviceRegistrar {
    public static final String ACCOUNT_NAME_EXTRA = "AccountName";

    public static final String STATUS_EXTRA = "Status";

    public static final int REGISTERED_STATUS = 1;

    public static final int UNREGISTERED_STATUS = 2;

    public static final int ERROR_STATUS = 3;

    private static final String TAG = "DeviceRegistrar";

    public static void registerOrUnregister(final Context context,
            final String deviceRegistrationId, final boolean register) {
        final SharedPreferences settings = Util.getSharedPreferences(context);
        final String accountName = settings.getString(Util.ACCOUNT_NAME, "Unknown");
        final Intent updateUIIntent = new Intent(Util.UPDATE_UI_INTENT);
        
        new AsyncTask<Void, Void, Integer>() {

			@Override
			protected Integer doInBackground(Void... params) {
				
				try {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost post = new HttpPost(Constants.APP_SERVER_URL + (register ? Constants.ROUTE_REGISTER : Constants.ROUTE_UNREGISTER));
					post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
					ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
					if (register)
						postParams.add(new BasicNameValuePair("accountName", accountName));
					postParams.add(new BasicNameValuePair("registrationId", deviceRegistrationId));
					post.setEntity(new UrlEncodedFormEntity(postParams, "UTF-8"));
					HttpResponse response = httpclient.execute(post);
					StringBuffer responseBuffer = new StringBuffer();
					InputStream instream = response.getEntity().getContent();
					InputStreamReader reader = new InputStreamReader(instream, "UTF-8");
				    BufferedReader in = new BufferedReader(reader);
				    String readed;
				    while ((readed = in.readLine()) != null) {
				    	responseBuffer.append(readed);
				    }
				    Log.d(TAG, responseBuffer.toString());
				    return register ? REGISTERED_STATUS : UNREGISTERED_STATUS;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return ERROR_STATUS;
			}
			
			private void clearPreferences(SharedPreferences.Editor editor) {
                editor.remove(Util.ACCOUNT_NAME);
                editor.remove(Util.AUTH_COOKIE);
                editor.remove(Util.DEVICE_REGISTRATION_ID);
            }
			
			@Override
			protected void onPostExecute(Integer result) {
				
				SharedPreferences settings = Util.getSharedPreferences(context);
                SharedPreferences.Editor editor = settings.edit();
				
				switch (result) {
				case REGISTERED_STATUS:
				case UNREGISTERED_STATUS:
	                if (register) {
	                    editor.putString(Util.DEVICE_REGISTRATION_ID, deviceRegistrationId);
	                } else {
	                    clearPreferences(editor);
	                }
	                editor.commit();
	                updateUIIntent.putExtra(ACCOUNT_NAME_EXTRA, accountName);
	                updateUIIntent.putExtra(STATUS_EXTRA, register ? REGISTERED_STATUS
	                        : UNREGISTERED_STATUS);
	                context.sendBroadcast(updateUIIntent);
					break;
				case ERROR_STATUS:

	                clearPreferences(editor);
	                editor.commit();

	                updateUIIntent.putExtra(ACCOUNT_NAME_EXTRA, accountName);
	                updateUIIntent.putExtra(STATUS_EXTRA, ERROR_STATUS);
	                context.sendBroadcast(updateUIIntent);
					break;
				}
			}
        	
		}.execute();
        
        
        // TODO: contact application server using http requests
        
        /*RegistrationInfoRequest request = getRequest(context);
        RegistrationInfoProxy proxy = request.create(RegistrationInfoProxy.class);
        proxy.setDeviceRegistrationId(deviceRegistrationId);

        String deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        proxy.setDeviceId(deviceId);

        Request<Void> req;
        if (register) {
            req = request.register().using(proxy);
        } else {
            req = request.unregister().using(proxy);
        }

        req.fire(new Receiver<Void>() {
            private void clearPreferences(SharedPreferences.Editor editor) {
                editor.remove(Util.ACCOUNT_NAME);
                editor.remove(Util.AUTH_COOKIE);
                editor.remove(Util.DEVICE_REGISTRATION_ID);
            }

            @Override
            public void onFailure(ServerFailure failure) {
                Log.w(TAG, "Failure, got :" + failure.getMessage());
                // Clean up application state
                SharedPreferences.Editor editor = settings.edit();
                clearPreferences(editor);
                editor.commit();

                updateUIIntent.putExtra(ACCOUNT_NAME_EXTRA, accountName);
                updateUIIntent.putExtra(STATUS_EXTRA, ERROR_STATUS);
                context.sendBroadcast(updateUIIntent);
            }

            @Override
            public void onSuccess(Void response) {
                SharedPreferences settings = Util.getSharedPreferences(context);
                SharedPreferences.Editor editor = settings.edit();
                if (register) {
                    editor.putString(Util.DEVICE_REGISTRATION_ID, deviceRegistrationId);
                } else {
                    clearPreferences(editor);
                }
                editor.commit();
                updateUIIntent.putExtra(ACCOUNT_NAME_EXTRA, accountName);
                updateUIIntent.putExtra(STATUS_EXTRA, register ? REGISTERED_STATUS
                        : UNREGISTERED_STATUS);
                context.sendBroadcast(updateUIIntent);
            }
        });*/
    }

}
