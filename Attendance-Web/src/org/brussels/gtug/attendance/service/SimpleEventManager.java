package org.brussels.gtug.attendance.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import org.brussels.gtug.attendance.domain.Event;
import org.brussels.gtug.attendance.util.DateTypeAdapter;
import org.springframework.stereotype.Service;

import com.google.android.c2dm.server.PMF;
import com.google.appengine.repackaged.com.google.common.base.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Service
public class SimpleEventManager implements EventManager {

	private static final Logger log = Logger
			.getLogger(SimpleRegistrationManager.class.getName());

	private static String BRUSSELS_EVENTS_URL = "http://www.gtugs.org/events?chapter=5002&all=true";

	public void sync() {

		String json = getEventsJson();
		json = json.substring(10, json.length() - 1);
		if (!StringUtil.isEmptyOrWhitespace(json)) {
			
			Gson gson = new GsonBuilder()
		    	.registerTypeAdapter(Date.class, new DateTypeAdapter())
		    	.create();
			
			Type collectionType = new TypeToken<Collection<Event>>() {
			}.getType();
			List<Event> events = gson.fromJson(json, collectionType);

			PersistenceManager pm = PMF.get().getPersistenceManager();
			for (Event event : events) {
				try {
					pm.makePersistent(event);
				} catch (JDOObjectNotFoundException e) {
					log.warning(e.getMessage());
				} catch (Exception e) {
					log.warning(e.getMessage());
				}
			}
			pm.close();
		}

	}

	private String getEventsJson() {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(BRUSSELS_EVENTS_URL);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			log.log(Level.ALL, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.log(Level.ALL, e.getMessage());
		}
		return buffer.toString();
	}

}
