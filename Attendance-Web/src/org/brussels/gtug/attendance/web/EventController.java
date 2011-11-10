package org.brussels.gtug.attendance.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.brussels.gtug.attendance.domain.Device;
import org.brussels.gtug.attendance.domain.Event;
import org.brussels.gtug.attendance.service.EventManager;
import org.brussels.gtug.attendance.service.RegistrationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.google.android.c2dm.server.C2DMessaging;
import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

/**
 * 
 * @author Sander Versluys
 *
 */
@Controller
@RequestMapping("/events*")
public class EventController implements ServletContextAware {

	private ServletContext servletContext;
	private EventManager eventManager;
	private RegistrationManager registrationManager;

	@Autowired
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@Autowired
	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}
	
	@Autowired
	public void setRegistrationManager(RegistrationManager registrationManager) {
		this.registrationManager = registrationManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String all() {
		List<Event> events = eventManager.getEvents(false);
		JSONArray list = new JSONArray();
		if (events != null) {
			for (Event event : events) {
				list.put(getJSONObject(event));
			}
		}
		return list.toString();
	}
	
	@RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
	@ResponseBody
	public String get(@PathVariable("eventId") Long eventId) {
		Event event = eventManager.getEvent(eventId);
		return getJSONObject(event).toString();
	}
	
	protected JSONObject getJSONObject(Event event) {
		try {
			JSONObject object = new JSONObject();
			object.put("chapterId", event.getChapterId());
			object.put("id", event.getId());
			object.put("name", event.getName());
			object.put("timeZone", event.getTimeZone());
			object.put("startDate", event.getStartDate());
			object.put("endDate", event.getEndDate());
			object.put("city", event.getCity());
			object.put("state", event.getState());
			object.put("country", event.getCountry());
			object.put("latitude", event.getLatitude());
			object.put("longitude", event.getLongitude());
			object.put("url", event.getUrl());
			object.put("themes", event.getTopics());
			object.put("attendees", event.getAttendees());
			return object;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/sync", method = RequestMethod.GET)
	@ResponseBody
	public void sync() {
		eventManager.sync();
	}
	
	@RequestMapping(value = "/checkin", method = RequestMethod.POST)
	@ResponseBody
	public void checkin(@RequestParam("eventId") Long eventId,
						@RequestParam("accountName") String accountName) {
		
		eventManager.checkin(eventId, accountName);
	}

	@RequestMapping(value = "/attendees", method = RequestMethod.POST)
	@ResponseBody
	public void attendees(@RequestParam("eventId") Long eventId) {
		
		
		//eventManager.getReg(eventId, accountName);
		
	}
	
	@RequestMapping(value = "/show/{eventId}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable("eventId") Long eventId) {
		Event event = eventManager.getEvent(eventId);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("event", event);
		
		return new ModelAndView("event", model);
	}
	
	public static final String ACTION_ALERT = "alert";
	public static final String ACTION_VIBRATE = "vibrate";
	public static final String ACTION_MESSAGE = "message";
	
	@RequestMapping(value = "/notify", method = RequestMethod.POST)
	@ResponseBody
	public String notify(@RequestParam("eventId") Long eventId,
			             @RequestParam("action") String action,
			             @RequestParam("data") String data) {
		
		Event event = eventManager.getEvent(eventId);
		if (event != null) {
			
			C2DMessaging push = C2DMessaging.get(servletContext);
			
			String collapseKey = "" + action.hashCode();
			
			for (String accountName : event.getAttendees()) {
				
				List<Device> devices = registrationManager.getDevicesForAccount(accountName);
				for (Device device : devices) {
					
					if ("ac2dm".equals(device.getType())) {
						push.sendNoRetry(device.getDeviceRegistrationID(),
								collapseKey, "action", action, "data", data);
					}
					
				}
				
			}
			
			return "ok";
		}
		
		return "failed";
		
	}

	

}
