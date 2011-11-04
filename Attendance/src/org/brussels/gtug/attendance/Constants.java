package org.brussels.gtug.attendance;

public class Constants {

	public static final String TAG = "GTUG-Attendance";
	
    /**
     * The URL of the app engine service.
     */
    public static final String APP_SERVER_URL = "https://gtugattendance.appspot.com";
    
    public static final String ROUTE_REGISTER = "/device/register";    
    public static final String ROUTE_UNREGISTER = "/device/unregister";
    public static final String ROUTE_SIGNUP = "/signup";

    /**
     * The C2DM sender ID for the server. A C2DM registration with this name
     * must exist for the app to function correctly.
     */
    public static final String SENDER_ID = "c2dm@brussels-gtug.org";
    
    public static final String ACTION_MEETINGS = "actionMeetings";
    
    public static final String ACTION_LOADED = "actionLoaded";
    
}
