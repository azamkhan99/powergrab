package uk.ac.ed.inf.powergrab;


import java.io.*;

import java.net.*;




public class map {
	
static String md = "2019/01/01";

	static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}


	public static void downloadMap(String mapDate)
			throws IOException {
		URL mapUrl = new URL("http://homepages.inf.ed.ac.uk/stg/powergrab/" + mapDate + "/powergrabmap.geojson");
		HttpURLConnection httpConn = (HttpURLConnection) mapUrl.openConnection();
		httpConn.setReadTimeout(10000);
		httpConn.setConnectTimeout(15000);
		httpConn.setRequestMethod("GET");
		httpConn.setDoInput(true);
		httpConn.connect();
		InputStream is = httpConn.getInputStream();
		String mapSource = convertStreamToString(is);
		System.out.println(mapSource);
	}




}
	