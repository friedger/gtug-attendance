package org.brussels.gtug.attendance;

public class Setup {

	public static final String TAG = "GTUG-Attendance";
	
    /**
     * The URL of the app engine service.
     */
    public static final String APP_SERVER_URL = "https://attendance-gtug.appspot.com";
    
    public static final String ROUTE_REGISTER = "/register";    
    public static final String ROUTE_UNREGISTER = "/unregister";
    public static final String ROUTE_SIGNUP = "/signup";

    /**
     * The C2DM sender ID for the server. A C2DM registration with this name
     * must exist for the app to function correctly.
     */
    public static final String SENDER_ID = "versluyssander.c2dm@gmail.com";
    
    public static final String ACTION_MEETINGS = "actionMeetings";
    
    public static final String ACTION_LOADED = "actionLoaded";
    
}
