package org.brussels.gtug.attendance.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.brussels.gtug.attendance.service.SimpleRegistrationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/events")
public class EventController {
	
	private static final Logger log = Logger.getLogger(SimpleRegistrationManager.class.getName());
	
	private static String EVENTS_URL = "http://www.gtugs.org/events?id=5002";
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String all() {
		StringBuffer buffer = new StringBuffer();
		 try {
            URL url = new URL(EVENTS_URL);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
        } catch (MalformedURLException e) {
        	e.printStackTrace();
        	log.log(Level.ALL, e.getMessage());
        } catch (IOException e) {
        	e.printStackTrace();
        	log.log(Level.ALL, e.getMessage());
        }
		return buffer.toString();
	}


}
