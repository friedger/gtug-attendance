package org.brussels.gtug.attendance.web;

import java.util.List;

import org.brussels.gtug.attendance.domain.Event;
import org.brussels.gtug.attendance.service.EventManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

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
				try {
					JSONObject map = new JSONObject();
					map.put("chapterId", event.getChapterId());
					map.put("id", event.getId());
					map.put("name", event.getName());
					map.put("timeZone", event.getTimeZone());
					map.put("startDate", event.getStartDate());
					map.put("endDate", event.getEndDate());
					map.put("city", event.getCity());
					map.put("state", event.getState());
					map.put("country", event.getCountry());
					map.put("latitude", event.getLatitude());
					map.put("longitude", event.getLongitude());
					map.put("url", event.getUrl());
					map.put("themes", event.getTopics());
					list.put(map);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return list.toString();
	}

	@RequestMapping(value = "/sync", method = RequestMethod.GET)
	@ResponseBody
	public void sync() {
		eventManager.sync();
	}

}
