package org.brussels.gtug.attendance.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {

	private static final String SALT = "qS45sd6pdcx789";
	
	public static String sha1(String s) {
		try {
			s += SALT;
			
	        // Create MD5 Hash
	        MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();
	        
	        // Create Hex String
	        StringBuffer hexString = new StringBuffer();
	        for (int i=0; i<messageDigest.length; i++)
	            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
	        return hexString.toString();
	        
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return "";
	}
	
}
