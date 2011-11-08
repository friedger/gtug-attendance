package org.brussels.gtug.attendance.web;

import java.util.List;

import org.brussels.gtug.attendance.domain.Event;
import org.brussels.gtug.attendance.service.EventManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class EventController {

	private EventManager eventManager;

	@Autowired
	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
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

}
