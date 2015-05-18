package com.demo.east.tracks;

public class ResultSegment {
	
	private String fromPointLatLng = "";
	private String toPointLatLng = "";
	private String fromSnappedLatLng = "";
	private String toSnappedLatLng = "";
	
	private String fromAltitude = "";
	private String toAltitude = "";
	
	private String fromTime = "";
	private String toTime = "";
	
	private String speedLimit = "";
	private String speed = "";
	private String distance = "";
	private int fromBearing = 0;
	private int toBearing = 0;
		
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public String getFromPointLatLng() {
		return fromPointLatLng;
	}
	public void setFromPointLatLng(String fromPointLatLng) {
		this.fromPointLatLng = fromPointLatLng;
	}
	public String getToPointLatLng() {
		return toPointLatLng;
	}
	public void setToPointLatLng(String toPointLatLng) {
		this.toPointLatLng = toPointLatLng;
	}
	public String getFromSnappedLatLng() {
		return fromSnappedLatLng;
	}
	public void setFromSnappedLatLng(String fromSnappedLatLng) {
		this.fromSnappedLatLng = fromSnappedLatLng;
	}
	public String getToSnappedLatLng() {
		return toSnappedLatLng;
	}
	public void setToSnappedLatLng(String toSnappedLatLng) {
		this.toSnappedLatLng = toSnappedLatLng;
	}
	public String getFromAltitude() {
		return fromAltitude;
	}
	public void setFromAltitude(String fromAltitude) {
		this.fromAltitude = fromAltitude;
	}
	public String getToAltitude() {
		return toAltitude;
	}
	public void setToAltitude(String toAltitude) {
		this.toAltitude = toAltitude;
	}
	public String getSpeedLimit() {
		return speedLimit;
	}
	public void setSpeedLimit(String speedLimit) {
		this.speedLimit = speedLimit;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public int getFromBearing(){
		return fromBearing;
	}
	public void setFromBearing(int fromBearing){
		this.fromBearing = fromBearing;
	}
	public int getToBearing(){
		return toBearing;
	}
	public void setToBearing(int toBearing){
		this.toBearing = toBearing;
	}
}
