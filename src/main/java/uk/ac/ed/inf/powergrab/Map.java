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
		this.mapSource = convertStreamToString(is);
		//System.out.println(mapSource);
	}

	static List<List<Double>> sml = new ArrayList<List<Double>>();
	static List<Double> scs = new ArrayList<Double>();

	public void collectingFeatures() {
		FeatureCollection fc = FeatureCollection.fromJson(this.mapSource);
		List<Feature> f = fc.features();

		for (int i = 0; i < f.size(); i++) {

			double coinage = (double) f.get(i).getProperty("coins").getAsFloat();
			double power = (double) f.get(i).getProperty("power").getAsFloat();
			this.scs.add(coinage);

			Geometry g = f.get(i).geometry();
			Point p = (Point) g;
			List<Double> latlong = p.coordinates();
			this.sml.add(latlong);

			//System.out.println("coin value: " +coinage + "	" + "power: " + power + "	" +"coordinates: " + latlong);

		}

	}



}
	