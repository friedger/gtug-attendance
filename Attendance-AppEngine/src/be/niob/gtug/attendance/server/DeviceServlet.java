package be.niob.gtug.attendance.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class DeviceServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(DeviceServlet.class.getName());
	
	private static final String ROOT = "/devices";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String path = req.getPathInfo();
		
		resp.setContentType("application/json");

		if (req.equals(ROOT + "/all")) {
			Gson gson = new Gson();
			gson.toJson(DeviceInfo.getAllDevices());
		}
	}
}
