package org.brussels.gtug.attendance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

/**
 * 
 * @author Sander Versluys
 *
 */
public class GPNS {
	
	public static final String GPNS_REGISTRATION_ENDPOINT =
			"http://gpns.dev.reference.be/registration-ws/registration.wsdl";
	
	public static final String GPNS_NOTIFICATION_ENDPOINT = 
			"http://gpns.dev.reference.be/notification-ws/notification.wsdl";

	public void register(String deviceToken,
						 String username,
						 String applicationName,
						 String applicationPackageName,
						 String applicationVersion,
						 String os,
						 String osVersion,
						 String[] categories) {
		
		SoapEnvelope env = new SoapEnvelope("ref", 
											"http://www.reference.be/gpns/registrationWS", 
											"registrationRequest");
		
		env.addToBody("DeviceToken", deviceToken);
		env.addToBody("Username", username);
		env.addToBody("ApplicationName", applicationName);
		env.addToBody("ApplicationPackageName", applicationPackageName);
		env.addToBody("ApplicationVersion", applicationVersion);
		env.addToBody("Os", os);
		env.addToBody("OsVersion", osVersion);
		// TODO : add all categories
		if (categories.length > 0)
			env.addToBody("Category", categories[0]);
		
		try {
		
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost post = new HttpPost(GPNS_REGISTRATION_ENDPOINT);
			
			post.setHeader("Content-Type", "application/soap+xml; charset=utf-8");
			post.setHeader("User-Agent", "Android");
			post.setEntity(new StringEntity(env.toString(), "UTF-8"));

			HttpResponse response = httpclient.execute(post);
			
			StringBuffer responseBuffer = new StringBuffer();
			InputStream instream = response.getEntity().getContent();
			
			InputStreamReader reader = new InputStreamReader(instream, "UTF-8");
		    BufferedReader in = new BufferedReader(reader);

		    String readed;
		    while ((readed = in.readLine()) != null) {
		    	responseBuffer.append(readed);
		    }
		    
		    Log.d("GPNS", responseBuffer.toString());
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void unregister(String deviceToken,
						   String applicationPackageName) {
		
	}
	
	public void update(String deviceToken,
					   String ApplicationPackageName,
					   String ApplicationVersion, 
					   String[] categories) {
		
	}
	
	private class SoapEnvelope {
		
		private String prefix;
		private String namespace;
		private String bodyName;
		private Map<String, String> params;
		
		public SoapEnvelope(String prefix, String namespace, String bodyName) {
			this.prefix = prefix;
			this.namespace = namespace;
			this.bodyName = bodyName;
			this.params = new HashMap<String, String>();
		}
		
		public void addToBody(String name, String value) {
			params.put(name, value);
		}
		
		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			buffer.append(String.format("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:%s=\"%s\">", prefix, namespace));
			buffer.append("<soapenv:Header/>");
			buffer.append("<soapenv:Body>");
			buffer.append(String.format("<%s:%s>", prefix, bodyName));
			for (Map.Entry<String, String> entry : params.entrySet()) {
				buffer.append(String.format("<%1$s:%2$s>%3$s</%1$s:%2$s>", prefix, entry.getKey(), entry.getValue()));
		    }
			buffer.append(String.format("</%s:%s>", prefix, bodyName));
			buffer.append("</soapenv:Body>");
			buffer.append("</soapenv:Envelope>");
			return buffer.toString();
		}
		
	}
	
}
