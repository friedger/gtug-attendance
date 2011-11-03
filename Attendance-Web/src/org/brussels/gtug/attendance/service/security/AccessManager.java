package org.brussels.gtug.attendance.service.security;

public interface AccessManager {

	public boolean hasAccess();

	public void setUsersService(UserManager us);
}
