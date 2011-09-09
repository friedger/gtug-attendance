package be.niob.gtug.attendance.server;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("devices")
public interface DeviceService extends RemoteService {

	public List<String> getDevices(); 
	
}
