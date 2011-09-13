package be.niob.gtug.attendance.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeviceServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(DeviceServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		
	}
}
