package com.demo.east.tracks;



public class TrackRecordingItem {
	
	private String latLong = "";
	private String elevation = "";
	private String speed = "";
	private String timeStamp = "";
	private String accuracy = "";
	private String snappedLatLong = "";
	private String placeID = "";
	private String speedLimit = "";
	
	public String getLatLong() {
		return latLong;
	}
	public void setLatLong(String latitude) {
		this.latLong = latitude;
	}
	
	
	public String getElevation() {
		return elevation;
	}
	public void setElevation(String elevation) {
		this.elevation = elevation;
	}
	
	
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
	public String getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}
	
	
	public String getSnappedLatLong() {
		return snappedLatLong;
	}
	public void setSnappedLatLong(String snappedLatLong) {
		this.snappedLatLong = snappedLatLong;
	}
	
	
	public String getPlaceId(){
		return(placeID);
	}
	public void setPlaceId(String placeid){
		placeID = placeid;
	}
	
	
	public String getSpeedLimit(){
		return(placeID);
	}
	public void setSpeedLimit(String limit){
		speedLimit = limit;
	}
}
