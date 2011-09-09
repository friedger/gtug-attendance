package be.niob.gtug.attendance.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DeviceServiceImpl extends RemoteServiceServlet implements DeviceService {

	@Override
	public List<String> getDevices() {
		List<String> devices = new ArrayList<String>();
		devices.add("pipo");
		devices.add("clown");
		return devices;
		//return DeviceInfo.getAllDevices();
	}
	
}
