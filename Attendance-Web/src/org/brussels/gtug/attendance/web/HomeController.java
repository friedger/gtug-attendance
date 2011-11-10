package org.brussels.gtug.attendance.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.brussels.gtug.attendance.domain.Event;
import org.brussels.gtug.attendance.service.EventManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home*")
public class HomeController {
	
	private EventManager eventManager;

	@Autowired
	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home() {
		List<Event> events = eventManager.getEvents(false);
		Collections.reverse(events);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("events", events);
		
		return new ModelAndView("events", model);
	}
	
	
	
}
