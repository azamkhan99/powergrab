package uk.ac.ed.inf.powergrab;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.net.URL;
import java.net.URLConnection;

public class map {
	
static String mapDate = "2019/01/01";

	public static void downloadMap(String fileURL, String saveDir)
			throws IOException {
		URL mapUrl = new URL("http://homepages.inf.ed.ac.uk/stg/powergrab/" + mapDate + "/powergrabmap.geojson");
		HttpURLConnection httpConn = (HttpURLConnection) mapUrl.openConnection();
		int responseCode = httpConn.getResponseCode();
		InputStream is = httpConn.getInputStream();
		
	}
}
	