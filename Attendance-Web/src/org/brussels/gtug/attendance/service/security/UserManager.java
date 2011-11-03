package org.brussels.gtug.attendance.service.security;

import org.brussels.gtug.attendance.domain.User;

public interface UserManager {

	public boolean isUserLoggedIn();

	public boolean isUserAdmin();

	public User getUser();

	public String createLoginUrl(String url);

	public String createLogoutUrl(String url);
}
