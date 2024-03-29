package com.example.rssr.shared;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldVerifier {

	public static boolean isValidUrl(String url) {
		String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		if (!IsMatch(url, regex))
			return false;
		return true;
	}
	
	public static boolean isValidName(String name) {
		if (name!="")
			return true;
		return false;
	}
	
	public static boolean isValidPassword(String pass) {
		if (pass!="")
			return true;
		return false;
	}
	
	private static boolean IsMatch(String s, String pattern) {
        try {
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
        return false;
        } 
    }
}
