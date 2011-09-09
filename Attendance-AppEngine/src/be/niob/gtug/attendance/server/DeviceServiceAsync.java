package be.niob.gtug.attendance.server;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DeviceServiceAsync {

	public void getDevices(AsyncCallback<List<String>> async);
	
}
