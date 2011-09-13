package be.niob.gtug.attendance.server;

import java.util.ArrayList;
import java.util.List;

import be.niob.gtug.attendance.client.DeviceService;

import com.google.appengine.api.utils.SystemProperty;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DeviceServiceImpl extends RemoteServiceServlet implements DeviceService {

	@Override
	public List<String> getDevices() {
		
		List<String> deviceStrings = new ArrayList<String>();
		List<DeviceInfo> devices = DeviceInfo.getAllDevices();
		for (DeviceInfo device: devices) 
			deviceStrings.add(device.getKey().getKind());
		
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development) {
			deviceStrings.add("device1");
			deviceStrings.add("device2");
		}
		
		return deviceStrings;
	}
	
}
