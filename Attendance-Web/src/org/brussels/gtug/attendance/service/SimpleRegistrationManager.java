package org.brussels.gtug.attendance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.jdo.Extent;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletContext;

import org.brussels.gtug.attendance.domain.Device;
import org.springframework.stereotype.Service;

import com.google.android.c2dm.server.C2DMessaging;
import com.google.android.c2dm.server.PMF;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * 
 * @author Sander Versluys
 *
 */
@Service
public class SimpleRegistrationManager implements RegistrationManager {

	private static final Logger log = Logger
			.getLogger(SimpleRegistrationManager.class.getName());

	private static final int MAX_DEVICES = 5;

	@Override
	public void register(String deviceRegistrationId, String deviceType,
			String deviceId, String accountName) {

		log.info("in register: accountName = " + accountName);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<Device> registrations = getDevicesForAccount(accountName);

			log.info("got registrations");
			if (registrations.size() > MAX_DEVICES) {
				log.info("got registrations > MAX_DEVICES");
				// we could return an error - but user can't handle it yet.
				// we can't let it grow out of bounds.
				// TODO: we should also define a 'ping' message and
				// expire/remove
				// unused registrations
				Device oldest = registrations.get(0);
				if (oldest.getRegistrationTimestamp() == null) {
					pm.deletePersistent(oldest);
				} else {
					long oldestTime = oldest.getRegistrationTimestamp()
							.getTime();
					for (int i = 1; i < registrations.size(); i++) {
						if (registrations.get(i).getRegistrationTimestamp()
								.getTime() < oldestTime) {
							oldest = registrations.get(i);
							oldestTime = oldest.getRegistrationTimestamp()
									.getTime();
						}
					}
					pm.deletePersistent(oldest);
				}
			}

			// Get device if it already exists, else create
			String suffix = (deviceId != null ? "#"
					+ Long.toHexString(Math.abs(deviceId.hashCode())) : "");
			log.info("suffix = " + suffix);
			Key key = KeyFactory.createKey(Device.class.getSimpleName(),
					accountName + suffix);
			log.info("key = " + key);

			Device device = null;
			try {
				device = pm.getObjectById(Device.class, key);
			} catch (JDOObjectNotFoundException e) {
				log.info("Caught JDOObjectNotFoundException");
			}
			if (device == null) {
				device = new Device(key, deviceRegistrationId);
				device.setType(deviceType);
			} else {
				// update registration id
				device.setDeviceRegistrationID(deviceRegistrationId);
				device.setRegistrationTimestamp(new Date());
			}

			pm.makePersistent(device);
			return;
		} catch (Exception e) {
			log.info("Caught exception: " + e);
		} finally {
			pm.close();
		}
	}

	@Override
	public void unregister(String deviceRegistrationID, String accountName) {
		log.info("in unregister: accountName = " + accountName);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<Device> registrations = getDevicesForAccount(accountName);
			for (int i = 0; i < registrations.size(); i++) {
				Device device = registrations.get(i);
				if (device.getDeviceRegistrationID().equals(
						deviceRegistrationID)) {
					pm.deletePersistent(device);
					// Keep looping in case of duplicates
				}
			}
		} catch (JDOObjectNotFoundException e) {
			log.warning("User " + accountName + " unknown");
		} catch (Exception e) {
			log.warning("Error unregistering device: " + e.getMessage());
		} finally {
			pm.close();
		}
	}

	@Override
	public List<Device> getDevices() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<Device> result = new ArrayList<Device>();
			Extent<Device> extent = pm.getExtent(Device.class, false);
			Iterator<Device> it = extent.iterator();
			while (it.hasNext()) {
				Device di = it.next();
				result.add(di);
			}
			extent.closeAll();
			log.info("Return " + result.size() + " devices");
			return result;
		} finally {
			pm.close();
		}
	}

	@Override
	public List<Device> getDevicesForAccount(String accountName) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			// Canonicalize account name
			accountName = accountName.toLowerCase(Locale.ENGLISH);
			Query query = pm.newQuery(Device.class);
			query.setFilter("key >= '" + accountName + "' && key < '"
					+ accountName + "$'");
			@SuppressWarnings("unchecked")
			List<Device> qresult = (List<Device>) query.execute();
			// Copy to array - we need to close the query
			List<Device> result = new ArrayList<Device>();
			for (Device di : qresult) {
				result.add(di);
			}
			query.closeAll();
			log.info("Return " + result.size() + " devices for user "
					+ accountName);
			return result;
		} finally {
			pm.close();
		}
	}

	@Override
	public void ping(ServletContext servletContext) {
		String message = "ping";
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			String sender = "nobody";
			if (user != null) {
				sender = user.getEmail();
			}
			log.info("sendMessage: sender = " + sender);

			// ok = we sent to at least one device.
			boolean ok = false;

			// Send push message to phone
			C2DMessaging push = C2DMessaging.get(servletContext);
			boolean res = false;

			String collapseKey = "" + message.hashCode();

			// delete will fail if the pm is different than the one used to
			// load the object - we must close the object when we're done

			List<Device> devices = null;
			devices = getDevices();
			log.info("sendMessage: got " + devices.size() + " registrations");

			// Deal with upgrades and multi-device:
			// If user has one device with an old version and few new ones -
			// the old registration will be deleted.
			if (devices.size() > 1) {
				// Make sure there is no 'bare' registration
				// Keys are sorted - check the first
				Device first = devices.get(0);
				Key oldKey = first.getKey();
				if (oldKey.toString().indexOf("#") < 0) {
					// multiple devices, first is old-style.
					devices.remove(0); // don't send to it
					pm.deletePersistent(first);
				}
			}

			int numSendAttempts = 0;
			for (Device device : devices) {
				if (!"ac2dm".equals(device.getType())) {
					continue; // user-specified device type
				}

				res = doSendViaC2dm(message, sender, push, collapseKey, device);
				numSendAttempts++;

				if (res) {
					ok = true;
				}
			}

			if (ok) {
				// return "Success: Message sent";
			} else if (numSendAttempts == 0) {
				// return "Failure: User " + recipient + " not registered";
			} else {
				// return "Failure: Unable to send message";
			}
		} catch (Exception e) {
			// return "Failure: Got exception " + e;
		} finally {
			pm.close();
		}
	}

	private static boolean doSendViaC2dm(String message, String sender,
			C2DMessaging push, String collapseKey, Device device) {
		// Trim message if needed.
		if (message.length() > 1000) {
			message = message.substring(0, 1000) + "[...]";
		}

		return push.sendNoRetry(device.getDeviceRegistrationID(),
				collapseKey, "sender", sender, "message", message);
	}

}
