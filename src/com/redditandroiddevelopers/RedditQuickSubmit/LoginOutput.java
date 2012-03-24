package com.redditandroiddevelopers.RedditQuickSubmit;
import java.util.List;


public class LoginOutput {

	
	private List<String> errors;
	private LoginData data;
	
	public static class LoginData {

		private String modhash;
		private String cookie;
		
		public String getModhash() {
			return modhash;
		}
		
		public String getCookie() {
			return cookie;
		}
		
	}
	
	public LoginData getData() {
		return data;
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
}
