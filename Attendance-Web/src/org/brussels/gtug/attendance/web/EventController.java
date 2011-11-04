package org.brussels.gtug.attendance.web;

import org.brussels.gtug.attendance.service.EventManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/events*")
public class EventController {

	private EventManager eventManager;
	
	@Autowired
	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String all() {
		StringBuffer buffer = new StringBuffer();
		/*try {
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
        }*/
		return buffer.toString();
	}

	@RequestMapping(value = "/sync", method = RequestMethod.GET)
	@ResponseBody
	public void sync() {
		eventManager.sync();
	}

}
