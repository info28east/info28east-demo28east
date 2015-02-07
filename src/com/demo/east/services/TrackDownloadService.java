package com.demo.east.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.demo.east.file.KMZFile;
import com.demo.east.kml.KMLParser;
import com.demo.east.request.RequestMessage;
import com.demo.east.request.RoadsJSON;
import com.demo.east.tracks.SnappedPoint;
import com.demo.east.tracks.SpeedLimit;
import com.demo.east.tracks.Track;
import com.demo.east.tracks.TrackRecordingItem;
import com.google.appengine.api.appidentity.AppIdentityService;
import com.google.appengine.api.appidentity.AppIdentityServiceFactory;
import com.google.apphosting.api.ApiProxy;
import com.google.gson.Gson;

@Path("/tracks")
public class TrackDownloadService
{	
	
	
	@GET
	@Path("/testvalues/{texttoreturn}")
	@Produces(MediaType.APPLICATION_JSON)
	public String TextResponseText(@PathParam("texttoreturn") String texttoreturn)throws Exception
	{
		return(texttoreturn);
	}
	
	/*
	@GET
	@Path("/download/{fileID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String DownloadTrack(@PathParam("fileID") String fileID)throws Exception
	{		
		RequestMessage message = null;
		Track track = new Track();
		Gson gson = new Gson();
		String returnJSON = "";
		
		try{
			RoadsJSON roadsJson = new RoadsJSON();
			KMLParser parser = new KMLParser();
			KMZFile f = new KMZFile(fileID);
			f.setFileID(fileID);
			
			String kml = f.DownloadKML();
			ArrayList<TrackRecordingItem> recordingItems = parser.ParseKML(kml);
			
			message = roadsJson.GetSnappedJSon(recordingItems);
			
			if(message.getStatus().equals("OK")){
				ArrayList<SnappedPoint[]> arrSnapped = (ArrayList<SnappedPoint[]>)message.getResponseObject();
				
		    	track.setArrSnappedPoints(arrSnapped);
		    	track.setTrackRecordingItems(recordingItems);
				
		    	message = roadsJson.GetSpeedLimits(track.getSnappedPoints());
		    	
		    	ArrayList<SpeedLimit[]> arrTest = (ArrayList<SpeedLimit[]>)message.getResponseObject();
		    	
		    	track.setArrSpeedLimits(arrTest);
			}	    	
		}
		catch(Exception exception){
			System.out.println(exception.getMessage());
		}
		finally{
			if(message.getStatus().equals("OK")){
				returnJSON = gson.toJson(track.getTrackRecordingItems());
			}
			else if(message.getStatus().equals("ERROR"))
			{
				returnJSON = message.getStatus() + " : " + message.getRequestMessage() + "  ::::  " + message.getStatusMessage();
			}
		}
		
		return(returnJSON);
	}*/
	
	
	@GET
	@Path("/download/{fileID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response DownloadTrack(@PathParam("fileID") String fileID)throws Exception
	{		
		RequestMessage message = null;
		Track track = new Track();
		Gson gson = new Gson();
		String returnJSON = "";
		
		try{
			RoadsJSON roadsJson = new RoadsJSON();
			KMLParser parser = new KMLParser();
			KMZFile f = new KMZFile(fileID);
			f.setFileID(fileID);
			
			String kml = f.DownloadKML();
			ArrayList<TrackRecordingItem> recordingItems = parser.ParseKML(kml);
			
			message = roadsJson.GetSnappedJSon(recordingItems);
			
			if(message.getStatus().equals("OK")){
				ArrayList<SnappedPoint[]> arrSnapped = (ArrayList<SnappedPoint[]>)message.getResponseObject();
				
		    	track.setArrSnappedPoints(arrSnapped);
		    	track.setTrackRecordingItems(recordingItems);
				
		    	message = roadsJson.GetSpeedLimits(track.getSnappedPoints());
		    	
		    	ArrayList<SpeedLimit[]> arrTest = (ArrayList<SpeedLimit[]>)message.getResponseObject();
		    	
		    	track.setArrSpeedLimits(arrTest);
			}	    	
		}
		catch(Exception exception){
			System.out.println(exception.getMessage());
		}
		finally{
			if(message.getStatus().equals("OK")){
				returnJSON = gson.toJson(track.getTrackRecordingItems());
			}
			else if(message.getStatus().equals("ERROR"))
			{
				returnJSON = gson.toJson(message);
			}
		}
		
		return(Response.ok(returnJSON).header("Access-Control-Allow-Origin", "*").build());
	}
	
	
	@GET
	@Path("/externalip/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetExternalIP()throws Exception
	{
		String testIP = new BufferedReader(new InputStreamReader(new URL("http://agentgatech.appspot.com").openStream())).readLine();
		
		return(Response.ok(testIP).header("Access-Control-Allow-Origin", "*").build());
	}
	
	
	
	@GET
	@Path("/appid/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetAppID()throws Exception
	{
		String appID = ApiProxy.getCurrentEnvironment().getAppId();
		
		return(Response.ok(appID).header("Access-Control-Allow-Origin", "*").build());
	}
	
	
	
	@GET
	@Path("/user/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetUser()throws Exception
	{
		String appID = ApiProxy.getCurrentEnvironment().getEmail();
		
		return(Response.ok(appID).header("Access-Control-Allow-Origin", "*").build());
	}
	
	
	
	
	@GET
	@Path("/serviceaccountname/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ServiceAccountName()throws Exception
	{
		AppIdentityService appIdentity = AppIdentityServiceFactory.getAppIdentityService();
		
		String appID = appIdentity.getServiceAccountName();
		
		return(Response.ok(appID).header("Access-Control-Allow-Origin", "*").build());
	}
	
	
}