package com.redditandroiddevelopers.RedditQuickSubmit;

public class UserData {

	private String modhash;
	private String cookie;
	
	public UserData(String _modhash, String _cookie) {
		modhash = _modhash;
		cookie = _cookie;
	}
	
	public String getModhash() {
		return modhash;
	}
	
	public String getCookie() {
		//om nom nom
		return cookie;
	}
	
	
	@Override
	public String toString() {
		return "Modhash: " + modhash + "\nCookie: "+ cookie; 
	}
}
