package org.brussels.gtug.attendance.web;

import java.util.List;

import org.brussels.gtug.attendance.domain.Device;
import org.brussels.gtug.attendance.service.RegistrationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

@Controller
@RequestMapping("/device/*")
public class DeviceController {
	
	private RegistrationManager registrationManager;
	
	@Autowired
	public void setRegistrationManager(RegistrationManager registrationManager) {
		this.registrationManager = registrationManager;
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
	}
}
