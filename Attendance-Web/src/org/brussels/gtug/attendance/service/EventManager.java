package org.brussels.gtug.attendance.service;

import java.util.List;

import org.brussels.gtug.attendance.domain.Event;

/**
 * 
 * @author Sander Versluys
 *
 */
public interface EventManager {

	public void sync();
	
	public Event getEvent(Long id);
	
	public List<Event> getEvents(boolean futureOnly);
	
	public void checkin(Long eventId, String accountName);
	
}
