package org.brussels.gtug.attendance.service;

import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletContext;

import org.brussels.gtug.attendance.domain.Device;

/**
 * 
 * @author Sander Versluys
 *
 */
public interface RegistrationManager extends Serializable {
	
	public void register(String deviceRegistrationId, String deviceType, String deviceId, String accountName);
	 
	public void unregister(String deviceRegistrationID, String accountName);
	 
	public List<Device> getDevices();
	 
	public List<Device> getDevicesForAccount(String accountName);
	
	public void ping(ServletContext servletContext);
	
}