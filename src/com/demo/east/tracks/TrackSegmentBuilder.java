package com.demo.east.tracks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class TrackSegmentBuilder {
	
	private ArrayList<TrackPointItem> trackPointItems;
	private ArrayList<TrackSegmentItem> segmentItems;
	private ArrayList<ResultSegment> resultSegments;
			
	public TrackSegmentBuilder(ArrayList<TrackPointItem> trackPointItems){
		this.trackPointItems = trackPointItems;
		
		try{
			if(this.trackPointItems != null){
				AddSegmentItems();
			}
		}
		catch(ParseException pExc){
			
		}
		catch(Exception exc){
			
		}		
	}
	

	public ArrayList<ResultSegment> getResultSegments() {
		return resultSegments;
	}
	
	
	private void AddSegmentItems() throws ParseException{
		this.segmentItems = new ArrayList<TrackSegmentItem>();
		this.resultSegments = new ArrayList<ResultSegment>();
		
		try{
			for(int index = 0 ; index < this.trackPointItems.size(); index ++){
				
				if(index <= (this.trackPointItems.size() - 2))
				{
					TrackPointItem fromItem = this.trackPointItems.get(index);
					TrackPointItem toItem = this.trackPointItems.get(index+1);
					
					TrackSegmentItem segment = new TrackSegmentItem();
					ResultSegment resultSegment = new ResultSegment();
					
					segment.setPointFromLat(fromItem.getLatLong().split(",")[0]);
					segment.setPointFromLng(fromItem.getLatLong().split(",")[1]);
					
					segment.setPointToLat(toItem.getLatLong().split(",")[0]);
					segment.setPointToLng(toItem.getLatLong().split(",")[1]);
					
					segment.setPointSnappedFromLat(fromItem.getSnappedLatLong().split(",")[0]);			
					segment.setPointSnappedFromLng(fromItem.getSnappedLatLong().split(",")[1]);
					
					segment.setPointSnappedToLat(toItem.getSnappedLatLong().split(",")[0]);			
					segment.setPointSnappedToLng(toItem.getSnappedLatLong().split(",")[1]);
					
					Integer fromAlt = Integer.valueOf(String.valueOf(Math.round(Double.valueOf(fromItem.getElevation()))));
					Integer toAlt = Integer.valueOf(String.valueOf(Math.round(Double.valueOf(toItem.getElevation()))));
					
					segment.setFromAltitude(fromAlt);
					segment.setToAltitude(Integer.valueOf(toAlt));
					
					segment.setFromBearing(Integer.valueOf(fromItem.getBearing()));
					segment.setToBearing(Integer.valueOf(toItem.getBearing()));
					
					resultSegment.setFromPointLatLng(segment.getPointFromLat() + "," + segment.getPointFromLng());
					resultSegment.setToPointLatLng(segment.getPointToLat() + "," + segment.getPointToLng());
					
					resultSegment.setFromSnappedLatLng(segment.getPointSnappedFromLat() + "," + segment.getPointSnappedFromLng());
					resultSegment.setToSnappedLatLng(segment.getPointSnappedToLat() + "," + segment.getPointSnappedToLng());
									
					resultSegment.setFromAltitude(String.valueOf(fromAlt));
					resultSegment.setToAltitude(String.valueOf(toAlt));
					
					String tmpFromDate = fromItem.getTimeStamp().split("T")[0];
					String tmpFromTime = fromItem.getTimeStamp().split("T")[1];
					tmpFromTime = tmpFromTime.substring(0, tmpFromTime.length() - 1);
					
					SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.SSS");
					Date fromDate = format.parse(tmpFromDate + " " + tmpFromTime);
					segment.setFromDateTime(fromDate);
					
					String tmpTomDate = toItem.getTimeStamp().split("T")[0];
					String tmpToTime = toItem.getTimeStamp().split("T")[1];
					tmpToTime = tmpToTime.substring(0, tmpToTime.length() - 1);
					
					resultSegment.setFromTime(tmpFromTime);
					resultSegment.setToTime(tmpToTime);
					
					Date toDate = format.parse(tmpTomDate + " " + tmpToTime);
					segment.setToDateTime(toDate);
					
					segment.setSpeedLimit(Double.valueOf(fromItem.getSpeedLimit()));
					segment.setDistance(CalculateDistances(segment));
					segment.setSpeed(CalculateSpeed(segment));
					
					resultSegment.setSpeedLimit(fromItem.getSpeedLimit());
					resultSegment.setDistance(String.valueOf(segment.getDistance()));
					resultSegment.setSpeed(String.valueOf(segment.getSpeed()));
					
					resultSegment.setFromBearing(segment.getFromBearing());
					resultSegment.setToBearing(segment.getToBearing());
					
					this.segmentItems.add(segment);
					this.resultSegments.add(resultSegment);
				}
			}
		}
		catch(Exception exc){
			System.out.println(exc.getMessage());
		}
	}
	
	
	private Double CalculateDistances(TrackSegmentItem segment){
		Double returnDistance = null;
		
		try
		{
			int R = 6371; // Radius of the earth
			
			Double latDistance = deg2rad(Double.valueOf(segment.getPointToLat()) - Double.valueOf(segment.getPointFromLat()));
			Double lngDistance = deg2rad(Double.valueOf(segment.getPointToLng()) - Double.valueOf(segment.getPointFromLng()));
			
			Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
		            + Math.cos(deg2rad(Double.valueOf(segment.getPointFromLat()))) * Math.cos(deg2rad(Double.valueOf(segment.getPointToLat())))
		            * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
			Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			double distance = R * c * 1000; // convert to meters
			
			double height = segment.getFromAltitude() - segment.getToAltitude();
			returnDistance = Math.pow(distance, 2) + Math.pow(height, 2);
		}
		catch(Exception exc){
			System.out.println(exc.getMessage());
		}
		return(Math.sqrt(returnDistance));
	}
	
	
	private double CalculateSpeed(TrackSegmentItem segment){
		
		double returnSpeed = 0.0;
		
		try{
			SimpleDateFormat timeDiffformat = new SimpleDateFormat("ss.SSS");
			long diff = segment.getToDateTime().getTime() - segment.getFromDateTime().getTime();
			double distance = segment.getDistance();
			double mPerS = ((distance / diff) * 1000);
			returnSpeed = mPerS * 3.6;			
		}
		catch(Exception exc){
			System.out.println(exc.getMessage());
		}
		return(returnSpeed);
	}
	
	
	
	private Double deg2rad(double deg) {
	    return (deg * Math.PI / 180.0);
	}
	
	
	
}