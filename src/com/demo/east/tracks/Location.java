package com.demo.east.tracks;

public class Location {
	private float latitude;
	private float longitude;
		
	public Location(){}
	
	public float getLatitude(){
		return(latitude);
	}
	public void setLatitude(float lat){
		latitude = lat;
	}
		
	public float getLongitude(){
		return(longitude);
	}
	public void setLongitude(float lat){
		longitude = lat;
	}
}
