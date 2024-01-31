package com.customermanagement.app.helper;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

/*
 * This class manages the list of URLs, which can be used in filters to
 * check if the request URL is considered public or not
 */
@Component
public class UrlServiceHelper {

	List<String> PUBLIC_URLS = Arrays.asList(
			
			"/api/register",
			"/api/authenticate",
			"/api/error"
			);
	
	public boolean isPublicUrl(String url) {
		
		return PUBLIC_URLS.contains(url);
	}
}
