package com.demo.east.tracks;

public class SnappedPoint {
	private Location location;
	private int originalIndex;
	private String placeId;
	
	public SnappedPoint(){}
	
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getOriginalIndex() {
		return originalIndex;
	}

	public void setOriginalIndex(int originalIndex) {
		this.originalIndex = originalIndex;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	
	
}
