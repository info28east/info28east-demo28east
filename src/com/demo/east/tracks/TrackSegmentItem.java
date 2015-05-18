package com.demo.east.tracks;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

public class TrackSegmentItem {
	
	private String pointFromLat = "";
	private String pointFromLng = "";
	private String pointToLat = "";
	private String pointToLng = "";
	
	private String pointSnappedFromLat = "";
	private String pointSnappedFromLng = "";
	private String pointSnappedToLat = "";
	private String pointSnappedToLng = "";
	
	private Date fromDateTime;
	private Date toDateTime;
	private String fromToTime = "";
	
	private int fromBearing = 0;
	private int toBearing = 0;
	private Double distance = 0.0;
	private Integer fromAltitude = 0;
	private Integer toAltitude = 0;
	private Double speed = 0.0;
	private Double speedLimit = 0.0;
	
	private NumberFormat formatter = null;
	
	public TrackSegmentItem(){
		formatter = new DecimalFormat("#0.00");
	}
	
	public Double getSpeedLimit() {
		return speedLimit;
	}
	public void setSpeedLimit(Double speedLimit) {
		this.speedLimit = speedLimit;
	}
	public Double getSpeed() {
		return Double.valueOf(formatter.format(speed));
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	public Integer getFromAltitude() {
		return fromAltitude;
	}
	public void setFromAltitude(Integer fromAltitude) {
		this.fromAltitude = fromAltitude;
	}
	
	public Integer getToAltitude() {
		return toAltitude;
	}
	public void setToAltitude(Integer toAltitude) {
		this.toAltitude = toAltitude;
	}
	public String getPointFromLat() {
		return pointFromLat;
	}
	public void setPointFromLat(String pointFromLat) {
		this.pointFromLat = pointFromLat;
	}
	public String getPointFromLng() {
		return pointFromLng;
	}
	public void setPointFromLng(String pointFromLng) {
		this.pointFromLng = pointFromLng;
	}
	public String getPointToLat() {
		return pointToLat;
	}
	public void setPointToLat(String pointToLat) {
		this.pointToLat = pointToLat;
	}
	public String getPointToLng() {
		return pointToLng;
	}
	public void setPointToLng(String pointToLng) {
		this.pointToLng = pointToLng;
	}
	public String getPointSnappedFromLat() {
		return pointSnappedFromLat;
	}
	public void setPointSnappedFromLat(String pointSnappedFromLat) {
		this.pointSnappedFromLat = pointSnappedFromLat;
	}
	public String getPointSnappedFromLng() {
		return pointSnappedFromLng;
	}
	public void setPointSnappedFromLng(String pointSnappedFromLng) {
		this.pointSnappedFromLng = pointSnappedFromLng;
	}
	public String getPointSnappedToLat() {
		return pointSnappedToLat;
	}
	public void setPointSnappedToLat(String pointSnappedToLat) {
		this.pointSnappedToLat = pointSnappedToLat;
	}
	public String getPointSnappedToLng() {
		return pointSnappedToLng;
	}
	public void setPointSnappedToLng(String pointSnappedToLng) {
		this.pointSnappedToLng = pointSnappedToLng;
	}
	public Date getFromDateTime() {
		return fromDateTime;
	}
	public void setFromDateTime(Date fromDateTime) {
		this.fromDateTime = fromDateTime;
	}
	public Date getToDateTime() {
		return toDateTime;
	}
	public void setToDateTime(Date toDateTime) {
		this.toDateTime = toDateTime;
	}
	public String getFromToTime() {
		return fromToTime;
	}
	public void setFromToTime(String fromToTime) {
		this.fromToTime = fromToTime;
	}
	public Double getDistance() {
		return Double.valueOf(formatter.format(distance));
	}
	public void setDistance(Double distance) {
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
