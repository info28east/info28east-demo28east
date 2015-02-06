package com.demo.east.request;


import java.util.ArrayList;

import com.demo.east.tracks.SnappedPoint;
import com.demo.east.tracks.SpeedLimit;
import com.demo.east.tracks.TrackRecordingItem;



public class RoadsJSON {

	public RequestMessage GetSnappedJSon(ArrayList<TrackRecordingItem> kmzPoints) throws Exception{
		RequestMessage message = null;
		ArrayList<SnappedPoint[]> returnValue = new ArrayList<SnappedPoint[]>();
		
		try{
			String points = "";
			int kmzIndex = 0;
			int incr=0;
			
			int leni = kmzPoints.size() / 100;
			double lenf = (double)kmzPoints.size() / 100;
			
			if((lenf-leni) > 0){
				leni++;
			}
			
			for(int y = 0; y < leni; y++){
				if(y>0){kmzIndex++; points="";}
				
				for(int i = kmzIndex; i < kmzPoints.size(); i ++){
					points = points + kmzPoints.get(i).getLatLong() + "|";
					
					if(incr == 99){
						incr = 0;
						break;
					}
					
					kmzIndex++;
					incr++;
				}
				
				points = points.substring(0, points.length() - 1);
				
				RoadsRequest req = new RoadsRequest();
				req.setApiKey("AIzaSyBo33WjjBhcM2wW2coQtdyjtq6qP6YZ6rk");
				req.setPointsToRequest(points);
				message = req.RequestSnappedPoints();
				
				if(message.getStatus().equals("OK")){
					SnappedPoint[] ret = (SnappedPoint[]) message.getResponseObject();
					returnValue.add(ret);
					message.setResponseObject(returnValue);
				}
				if(message.getStatus().equals("ERROR")){
					throw new Exception();
				}
			}
		}
		catch(Exception exc){
			
		}
		
		return(message);
	}
	
	
	
	public RequestMessage GetSpeedLimits(ArrayList<SnappedPoint> snappedPoints) throws Exception{
		RequestMessage message = null;
		ArrayList<SpeedLimit[]> returnValue = new ArrayList<SpeedLimit[]>();
		
		try{
			String placeIds = "";
			int kmzIndex = 0;
			int incr=0;
			
			int leni = snappedPoints.size() / 100;
			double lenf = (double)snappedPoints.size() / 100;
			
			if((lenf-leni) > 0){
				leni++;
			}
			
			for(int y = 0; y < leni; y++){
				if(y>0){kmzIndex++; placeIds="";}
				
				for(int i = kmzIndex; i < snappedPoints.size(); i ++){
					placeIds = placeIds + "&placeId=" + snappedPoints.get(i).getPlaceId();
					
					if(incr == 99){
						incr = 0;
						break;
					}
					
					kmzIndex++;
					incr++;
				}
				
				RoadsRequest req = new RoadsRequest();
				req.setApiKey("AIzaSyBo33WjjBhcM2wW2coQtdyjtq6qP6YZ6rk");
				req.setPlaceIdsToRequest(placeIds);
				message = req.RequestSpeedLImits(snappedPoints);
				
				if(message.getStatus().equals("OK")){
					SpeedLimit[] arrLimits = (SpeedLimit[]) message.getResponseObject();
					returnValue.add(arrLimits);
					message.setResponseObject(returnValue);
				}
				if(message.getStatus().equals("ERROR")){
					throw new Exception();
				}
			}
		}
		catch(Exception exc){
			throw exc;
		}
		
		return(message);
	}
	
}
