package be.niob.gtug.attendance.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DeviceServiceAsync {

	public void getDevices(AsyncCallback<List<String>> async);
	
}
