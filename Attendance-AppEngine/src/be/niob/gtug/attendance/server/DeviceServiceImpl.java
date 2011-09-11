package be.niob.gtug.attendance.server;

import java.util.ArrayList;
import java.util.List;

import be.niob.gtug.attendance.client.DeviceService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DeviceServiceImpl extends RemoteServiceServlet implements DeviceService {

	@Override
	public List<String> getDevices() {
		
		List<String> deviceStrings = new ArrayList<String>();
		List<DeviceInfo> devices = DeviceInfo.getAllDevices();
		for (DeviceInfo device: devices) 
			deviceStrings.add(device.getKey().toString());
		
		return deviceStrings;
	}
	
}
