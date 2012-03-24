package com.redditandroiddevelopers.RedditQuickSubmit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class RedditAPI {


	public static UserData login(String u, String user, String pw) throws IOException {

		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("api_type", "json"));
		nameValuePairs.add(new BasicNameValuePair("user", user));
		nameValuePairs.add(new BasicNameValuePair("passwd", pw));		
		
		String response = httpPost(u, nameValuePairs);
		
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(response).getAsJsonObject().get("json");
		
		LoginOutput output = gson.fromJson(element, LoginOutput.class);
		
		UserData outUser = new UserData(output.getData().getModhash(), output.getData().getCookie());
		return outUser;
	        
	}
	
	//Non-functional at this point.  Need to figure out how to correctly handle the reddit_session cookie.
	
	public static SubmitOutput submit(String u, String title, String link, String subreddit, String kind, String modhash, String cookie) throws IOException {
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("title", title));
		nameValuePairs.add(new BasicNameValuePair("url", link));
		nameValuePairs.add(new BasicNameValuePair("sr", subreddit));
		nameValuePairs.add(new BasicNameValuePair("kind", kind));
		nameValuePairs.add(new BasicNameValuePair("uh", modhash));
		
		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie aCookie = new BasicClientCookie("reddit_session", cookie);
		cookieStore.addCookie(aCookie);
		
		String response = httpPost(u, nameValuePairs, cookie);
		
		System.out.println(response);
		
		return null;
	}
	
	
private static String httpPost(String _url, List<NameValuePair> nameValuePairs) throws IOException {
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(_url);
		
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		
		HttpResponse response = httpclient.execute(httppost);

		String line = "";
	    StringBuilder total = new StringBuilder();

	    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

	    while ((line = rd.readLine()) != null) { 
	        total.append(line); 
	    }
		
		return total.toString();
		
	}

private static String httpPost(String _url, List<NameValuePair> nameValuePairs, String cookie) throws IOException {
	
	DefaultHttpClient httpclient = new DefaultHttpClient();
	HttpPost httppost = new HttpPost(_url);
	
	CookieStore cookieStore = new BasicCookieStore();
	cookieStore.addCookie(new BasicClientCookie("reddit_session", cookie));
	
	HttpContext localContext = new BasicHttpContext();
	localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	
	httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	
	HttpResponse response = httpclient.execute(httppost, localContext);

	String line = "";
    StringBuilder total = new StringBuilder();

    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

    while ((line = rd.readLine()) != null) { 
        total.append(line); 
    }
	
	return total.toString();
	
}
}
