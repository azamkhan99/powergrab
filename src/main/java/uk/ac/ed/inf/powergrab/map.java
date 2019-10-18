package uk.ac.ed.inf.powergrab;
import com.mapbox.geojson.FeatureCollection;

import java.io.*;
import java.net.*;
import java.util.*;
import com.mapbox.geojson.*;

public class Map {
	
//static String md = "2019/01/01";
private String mapSource = "";

	static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}


	public void downloadMap(String mapDate)
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
		this.mapSource = mapSource;
		//System.out.println(mapSource);
	}

	public void collectingFeatures() {
		FeatureCollection fc = FeatureCollection.fromJson(this.mapSource);
		List<Feature> f = fc.features();
		for (int i= 0; i < f.size(); i++){

			float coinage = f.get(i).getProperty("coins").getAsFloat();

			Geometry g = f.get(i).geometry();
			Point p = (Point) g;
			//List<Double> latlong = p.coordinates();
			Double lat = p.coordinates().get(0);
			Double lon = p.coordinates().get(1);

			System.out.println(lon);

		}

	}




}
	