package org.brussels.gtug.attendance.service;

import java.util.List;

import org.brussels.gtug.attendance.domain.Event;


public interface EventManager {

	public void sync();
	
	public List<Event> getEvents(boolean futureOnly);
	
}
