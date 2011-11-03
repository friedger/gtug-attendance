package org.brussels.gtug.attendance.service.security;

import org.brussels.gtug.attendance.domain.User;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AppEngineUserService {

	private String afterLoginEndpoint;
	private String afterLogoutEndpoint;

	public boolean isUserLoggedIn() {
		UserService userService = UserServiceFactory.getUserService();

		return userService.isUserLoggedIn();
	}

	public boolean isUserAdmin() {
		UserService userService = UserServiceFactory.getUserService();

		try {
			return userService.isUserAdmin();
		} catch (IllegalStateException ex) {
			return false;
		}
	}

	public String getLoginUrl() {
		if (afterLoginEndpoint != null) {
			return createLoginUrl(afterLoginEndpoint);
		} else {
			return createLoginUrl("/");
		}
	}

	public String getLogoutUrl() {
		if (afterLogoutEndpoint != null) {
			return createLogoutUrl(afterLogoutEndpoint);
		} else {
			return createLogoutUrl("/");
		}
	}

	public User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		com.google.appengine.api.users.User currentUser = userService
				.getCurrentUser();

		if (currentUser == null) {
			return null;
		}

		User user = new User();
		user.setId(currentUser.getUserId());
		user.setName(currentUser.getNickname());
		user.setEmail(currentUser.getEmail());
		user.setIsAdmin(userService.isUserAdmin());

		return user;
	}

	public void setAfterLoginEndpoint(String afterLoginEndpoint) {
		this.afterLoginEndpoint = afterLoginEndpoint;
	}

	public void setAfterLogoutEndpoint(String afterLogoutEndpoint) {
		this.afterLogoutEndpoint = afterLogoutEndpoint;
	}

	private String createLoginUrl(String destinationUrl) {
		UserService userService = UserServiceFactory.getUserService();

		return userService.createLoginURL(destinationUrl);
	}

	private String createLogoutUrl(String destinationUrl) {
		UserService userService = UserServiceFactory.getUserService();

		return userService.createLogoutURL(destinationUrl);
	}
}
