package com.demo.east.tracks;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;



public class TrackResult {
	
	private String kmzID = "";
	private String date = "";
	private String totalDistance = "";
	private String travelTime = "";
	private String avgSpeed = "";
	private String startTime = "";
	private String endTime = "";
	private String minSpeed = "";
	private String maxSpeed = "";
	private String totalViolations = "";
	
	
	private ArrayList<ResultSegment> trackSegments = null;
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ArrayList<ResultSegment> getResultSegments() {
		return trackSegments;
	}

	public void setResultSegments(ArrayList<ResultSegment> trackSegments) {
		this.trackSegments = trackSegments;
		
		totalDistance = this.getTotalDistance();
		startTime = this.trackSegments.get(0).getFromTime();
		endTime = this.trackSegments.get(this.trackSegments.size()-1).getToTime();
		travelTime = this.getTravelTime();
		avgSpeed = this.getAvgSpeed();
		minSpeed = this.getMinSpeed();
		maxSpeed = this.getMaxSpeed();
		totalViolations = this.getTotalViolations();
	}
	
	
	public String getKmzID() {
		return kmzID;
	}

	public void setKmzID(String kmzID) {
		this.kmzID = kmzID;
	}
	
	
	public String getTotalDistance() {
		double totalDistanceKm = 0; 
		double totalDistanceM = 0;
		
		for(ResultSegment item : trackSegments){
			totalDistanceM = totalDistanceM + Double.valueOf(item.getDistance());
		}
		
		totalDistanceKm = totalDistanceM / 1000;
		NumberFormat formatter = new DecimalFormat("#0.00");
		totalDistanceKm = Double.valueOf(formatter.format(totalDistanceKm));
		
		totalDistance = String.valueOf(totalDistanceKm);
		return totalDistance;
	}
	
	
	
	public String getTravelTime() {
		double totalTravelTime = 0.0;
		
		try{
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			format.setTimeZone(TimeZone.getTimeZone("GMT"));
			
			Date fromTime = format.parse(this.startTime);
			Date toTime = format.parse(this.endTime);
			
			long diff = toTime.getTime() - fromTime.getTime();
			
			travelTime = format.format(new Date(diff));	
		}
		catch(Exception exc){
			System.out.println(exc.getMessage());
		}
		return travelTime;
	}
	
	
	
	public String getAvgSpeed() {
		double returnSpeed = 0.0;
		
		try{
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			format.setTimeZone(TimeZone.getTimeZone("GMT"));
			
			Date fromTime = format.parse(this.startTime);
			Date toTime = format.parse(this.endTime);
			
			long diff = toTime.getTime() - fromTime.getTime();
			
			double distanceM = Double.valueOf(this.totalDistance) * 1000;
			
			double mPerS = ((distanceM / diff) * 1000);
			returnSpeed = mPerS * 3.6;
			
			NumberFormat formatter = new DecimalFormat("#0.00");
			avgSpeed = formatter.format(returnSpeed);			 
		}
		catch(Exception exc){
			System.out.println(exc.getMessage());
		}
		
		return avgSpeed;
	}
	
	
	
	public String getMinSpeed() {
		double min = 0.0;
		int index = 0;
				
		for(ResultSegment item : trackSegments){			
			if(index == 0){
				min = Double.valueOf(item.getSpeed());
			}
			else{
				if(min > Double.valueOf(item.getSpeed())){
					min = Double.valueOf(item.getSpeed());
				}
			}
			index++;
		}
		
		minSpeed = String.valueOf(min);
		return minSpeed;
	}
	
	
	
	public String getMaxSpeed() {
		double max = 0.0;
		int index = 0;
				
		for(ResultSegment item : trackSegments){			
			if(index == 0){
				max = Double.valueOf(item.getSpeed());
			}
			else{
				if(max < Double.valueOf(item.getSpeed())){
					max = Double.valueOf(item.getSpeed());
				}
			}
			index++;
		}
		
		maxSpeed = String.valueOf(max);
		return maxSpeed;
	}
	
	
	
	public String getTotalViolations() {
		int cntViolations = 0;
		
		for(ResultSegment item : trackSegments){
			if(Double.valueOf(item.getSpeed()) > Double.valueOf(item.getSpeedLimit())){
				cntViolations++;
			}
		}
		
		totalViolations = String.valueOf(cntViolations);
		return totalViolations;
	}
	
}
