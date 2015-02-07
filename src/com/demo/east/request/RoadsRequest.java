package com.demo.east.request;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.demo.east.client.Demo28east;
import com.demo.east.tracks.SnappedPoint;
import com.demo.east.tracks.SpeedLimit;
import com.google.appengine.api.mail.MailService.Message;
import com.google.gson.Gson;



public class RoadsRequest {

	private String apiKey = "";
	private String pointsToRequest = "";
	private String placeIdsToRequest = "";
	private Gson gson; 



	public RoadsRequest (){
		gson = new Gson();
	}



	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}



	public String getPointsToRequest() {
		return pointsToRequest;
	}
	public void setPointsToRequest(String pointsToRequest) {
		this.pointsToRequest = pointsToRequest;
	}



	public String getPlaceIdsToRequest(){
		return(placeIdsToRequest);
	}
	public void setPlaceIdsToRequest(String placesToRequest) {
		this.placeIdsToRequest = placesToRequest;
	}



	public RequestMessage RequestSnappedPoints() throws Exception{
		String urlReq = "";
		String json = "";
		SnappedPoint[] returnValue = null;
		String requestMessage = "";
		RequestMessage returnMessage = new RequestMessage();
		HttpURLConnection conn = null;
		
		try{
			
			urlReq = "https://www.googleapis.com/roads/v1/snapToRoads?path=" + URLEncoder.encode(pointsToRequest, "UTF-8") + "&key=" + URLEncoder.encode(apiKey, "UTF-8");
			
			URL urlObj = new URL(urlReq);						
	    	conn = (HttpURLConnection) urlObj.openConnection();	
	    	conn.setRequestProperty("X-Custom-Header", "xxx");
	    	conn.setRequestProperty("Content-Type", "application/json");
	    	
	    	requestMessage = requestMessage + "JUST BEFORE GetReponseCode()";
	    		    		    	
	    	
			if (conn.getResponseCode() != 200) {
				requestMessage = requestMessage + "Response Code : 0 ";
				
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			else if(conn.getResponseCode() == 200){
				
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				
				String output;
				
				while ((output = br.readLine()) != null) {
					json = json + output;
				}

				json = json.replace(" ", "");
				json = json.replace("{\"snappedPoints\":", "");
				json = json.substring(0, json.length()-1);

				returnValue = gson.fromJson(json, SnappedPoint[].class);

				returnMessage.setStatus("OK");
				returnMessage.setStatusMessage("Request Successfull");
				returnMessage.setResponseObject(returnValue);
				returnMessage.setRequestMessage(urlReq);
			}
			
			conn.disconnect();			 
		}
		catch(Exception exc){
			
			if(conn.getResponseMessage() != null){
				requestMessage = requestMessage + "  :  Response message IS " + conn.getResponseMessage();
			}
			else{
				requestMessage = requestMessage + "  :  Response message IS NULL  :  ";
			}
			
			returnMessage.setStatus("ERROR");
			returnMessage.setStatusMessage(exc.getMessage());
			returnMessage.setRequestMessage(requestMessage + "  ====  ");
		}
		return(returnMessage);
	}
	
	
	
	public RequestMessage RequestSpeedLImits(ArrayList<SnappedPoint> snappedPoints) throws Exception{
		String json = "";
		SpeedLimit[] returnValue = null;
		String urlReq = "";
		String requestMessage;
		
		RequestMessage returnMessage = new RequestMessage();
		
		try{
			urlReq = "https://www.googleapis.com/roads/v1/speedLimits?key=" + apiKey + placeIdsToRequest;
			
			requestMessage = "Before REQUEST :::: ";
			
			URL url = new URL(urlReq);
			requestMessage = requestMessage +  "  URL created";
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			requestMessage = requestMessage + " ::: Connection Open";
			
			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			
			if (conn.getResponseCode() != 200) {
				requestMessage = requestMessage + "Response Code : " + conn.getResponseCode();
				
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			else if(conn.getResponseCode() == 200){

				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String output;

				while ((output = br.readLine()) != null) {
					json = json + output;
				}

				json = json.replace(" ", "");
				json = json.replace("{\"speedLimits\":", "");
				json = json.substring(0, json.length()-1);

				returnValue = gson.fromJson(json, SpeedLimit[].class);

				returnMessage.setStatus("OK");
				returnMessage.setStatusMessage("Request Successfull");
				returnMessage.setResponseObject(returnValue);
				returnMessage.setRequestMessage(urlReq);
			}
			
			returnMessage.setRequestMessage(requestMessage);
			
			conn.disconnect();
		}
		catch(Exception exc){
			returnMessage.setStatus("ERROR");
			returnMessage.setStatusMessage(exc.getMessage());
		}
		
		return(returnMessage);
	}

}
