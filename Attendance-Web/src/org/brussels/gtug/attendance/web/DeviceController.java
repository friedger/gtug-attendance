package org.brussels.gtug.attendance.web;

import java.util.List;

import javax.servlet.ServletContext;

import org.brussels.gtug.attendance.domain.Device;
import org.brussels.gtug.attendance.service.RegistrationManager;
import org.brussels.gtug.attendance.service.security.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.ServletContextAware;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

@Controller
@RequestMapping("/device/*")
public class DeviceController implements ServletContextAware {
	
	private ServletContext servletContext;
	private RegistrationManager registrationManager;
	private UserManager userManager;
	
	@ResponseStatus(value = HttpStatus.FORBIDDEN, reason="You'll have to login first")
	public class NoAccessException extends RuntimeException {
	    
	}
	
	@Autowired
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@Autowired
	public void setRegistrationManager(RegistrationManager registrationManager) {
		this.registrationManager = registrationManager;
	}
	
	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public void register(@RequestParam("deviceRegistrationId") String deviceRegistrationId,
						 @RequestParam("deviceId") String deviceId,
						 @RequestParam("accountName") String accountName) {
		
		registrationManager.register(deviceRegistrationId, "ac2dm", deviceId, accountName);
	}
	
	@RequestMapping(value = "/unregister", method = RequestMethod.POST)
	@ResponseBody
	public void unregister(@RequestParam("accountName") String accountName, 
					  	   @RequestParam("deviceRegistrationId") String deviceRegistrationId) {
		
		registrationManager.unregister(deviceRegistrationId, accountName);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public String all() {
		if(userManager.isUserAdmin()) {
			JSONArray devicesJson = new JSONArray();
			List<Device> devices = registrationManager.getDevices();
			for (Device device : devices) {
				JSONObject deviceJson = new JSONObject();
				try {
					deviceJson.put("id", device.getKey().getId());
					deviceJson.put("registrationId", device.getDeviceRegistrationID());
					devicesJson.put(deviceJson);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return devicesJson.toString();
		} else {
			throw new NoAccessException();
		}
	}
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	@ResponseBody
	public void ping() {
		if(userManager.isUserAdmin()) {
			registrationManager.ping(servletContext);
		}
	}

	
}
