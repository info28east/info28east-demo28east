package com.demo.east.tracks;


import java.util.ArrayList;


public class Track {
	
	private ArrayList<TrackPointItem> trackRecordingItems;
	private ArrayList<SnappedPoint[]> collArrSnappedPoints;
	private ArrayList<SnappedPoint> collSnappedPoints;
	private ArrayList<SpeedLimit[]> collArrSpeedLimits;
	private ArrayList<SpeedLimit> collSpeedLimits;
	
	
	public ArrayList<TrackPointItem> getTrackRecordingItems() {
		return trackRecordingItems;
	}
	public void setTrackRecordingItems(
			ArrayList<TrackPointItem> trackRecordingItems) {
		this.trackRecordingItems = trackRecordingItems;
		IntegrateSnappedPoints();
	}
	
	
	
	public ArrayList<SnappedPoint[]> getArrSnappedPoints() {
		return collArrSnappedPoints;		
	}
	public void setArrSnappedPoints(ArrayList<SnappedPoint[]> arrSnappedPoints) {
		this.collArrSnappedPoints = arrSnappedPoints;
		IntegrateSnappedPoints();
	}
	
	
	
	public ArrayList<SnappedPoint> getSnappedPoints(){
		return(collSnappedPoints);
	}
	
	
	
	public ArrayList<SpeedLimit[]> getArrSpeedLimits(){
		return(collArrSpeedLimits);
	}
	public void setArrSpeedLimits(ArrayList<SpeedLimit[]> speedlimits){
		collArrSpeedLimits = speedlimits;
		IntegrateSpeedLimits();
	}
	
	
	
	private void ConcatSpeedLimits(){
		try{
			collSpeedLimits = new ArrayList<SpeedLimit>();
			for(SpeedLimit[] arrSpeedLimit : collArrSpeedLimits){
				for(SpeedLimit item : arrSpeedLimit){
					collSpeedLimits.add(item);
				}
			}
		}
		catch(Exception exc){
			System.out.println(exc.getMessage());
		}
	}
	
	
	
	private void ConcatSnappedPoints(){
		try{
			collSnappedPoints = new ArrayList<SnappedPoint>();
			for(SnappedPoint[] arrPoints : collArrSnappedPoints){
				for(SnappedPoint point : arrPoints){
					collSnappedPoints.add(point);
				}
			}
		}
		catch(Exception exc)
		{
			System.out.println(exc.getMessage());
		}
	}
	
	
	
	private void IntegrateSpeedLimits(){
		try{
			if(collArrSpeedLimits != null && collSnappedPoints  != null){
				if(collArrSpeedLimits.size() > 0 && collSnappedPoints.size() > 0){
					ConcatSpeedLimits();
					if(collSpeedLimits.size() == collSnappedPoints.size()){
						int index=0;
						for (SnappedPoint item : collSnappedPoints){
							SpeedLimit speedLimit = collSpeedLimits.get(index);
							TrackPointItem point = trackRecordingItems.get(index);
							point.setSpeedLimit(speedLimit.getSpeedLimit());							
							index++;
						}
					}
				}
			}
		}
		catch(Exception exc){
			System.out.println(exc.getMessage());
		}
	}
	
	
	
	private void IntegrateSnappedPoints(){
		try{
			if(collArrSnappedPoints != null && trackRecordingItems != null){
				if(collArrSnappedPoints.size() > 0 && trackRecordingItems.size() > 0){
					ConcatSnappedPoints();
					if(collSnappedPoints.size() == trackRecordingItems.size()){
						int index=0;
						for (TrackPointItem item : trackRecordingItems){
							SnappedPoint point = collSnappedPoints.get(index);
							item.setSnappedLatLong(point.getLocation().getLatitude() + "," + point.getLocation().getLongitude());
							item.setPlaceId(point.getPlaceId());
							index++;
						}
					}					
				}
			}
		}
		catch(Exception exc){
			
		}
	}
}