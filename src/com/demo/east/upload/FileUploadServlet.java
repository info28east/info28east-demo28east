package com.demo.east.upload;

import gwtupload.server.UploadAction;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.demo.east.kml.KMLParser;
import com.demo.east.request.RequestMessage;
import com.demo.east.request.RoadsJSON;
import com.demo.east.tracks.SnappedPoint;
import com.demo.east.tracks.SpeedLimit;
import com.demo.east.tracks.Track;
import com.demo.east.tracks.TrackPointItem;
import com.demo.east.tracks.TrackResult;
import com.demo.east.tracks.TrackSegmentBuilder;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
				
		PrintWriter writer = response.getWriter();
		TrackResult requestResult = null;
		Gson gson = new Gson();
		RequestMessage message = new RequestMessage();
		ByteArrayOutputStream out = null;
		byte[] buffer = new byte[2048];
		ServletFileUpload fileUpload = new ServletFileUpload();
		
		try {
			
			FileItemIterator iterator = fileUpload.getItemIterator(request);
			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
				InputStream in = item.openStream();
				
				out = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int n = 0;
				
				while (-1!=(n=in.read(buf)))
				{
					out.write(buf, 0, n);
				}
				
				out.close();
				in.close();
				byte[] res = out.toByteArray();
				
				java.util.zip.ZipInputStream zipInput = new ZipInputStream(new ByteArrayInputStream(res));				
				ZipEntry ze = zipInput.getNextEntry();
				
				ByteArrayOutputStream output = null;
				
				while(ze!=null){
					int len;	
					output = new ByteArrayOutputStream();
					
					while ((len = zipInput.read(buffer)) > 0) {
						output.write(buffer, 0, len);
					}
					
					ze = zipInput.getNextEntry();
				}
				
				zipInput.closeEntry();
				zipInput.close();
				
				if(output!=null){
					String kml = output.toString();
					Track track = new Track();
					TrackSegmentBuilder segmentBuilder = null;
					
					KMLParser parser = new KMLParser();
					ArrayList<TrackPointItem> recordingItems = parser.ParseKML(kml);
					
					RoadsJSON roadsJson = new RoadsJSON();
					message = roadsJson.GetSnappedJSon(recordingItems);
					
					if(message.getStatus().equals("OK")){
						ArrayList<SnappedPoint[]> arrSnapped = (ArrayList<SnappedPoint[]>)message.getResponseObject();
						
				    	track.setArrSnappedPoints(arrSnapped);
				    	track.setTrackRecordingItems(recordingItems);
						
				    	message = roadsJson.GetSpeedLimits(track.getSnappedPoints());
				    	
				    	ArrayList<SpeedLimit[]> arrTest = (ArrayList<SpeedLimit[]>)message.getResponseObject();
				    	
				    	track.setArrSpeedLimits(arrTest);
				    	segmentBuilder = new TrackSegmentBuilder(track.getTrackRecordingItems());
				    	requestResult = new TrackResult();
				    	requestResult.setResultSegments(segmentBuilder.getResultSegments());
					}
				}
			}
		} 
		catch (FileUploadException e) {
			writer.println("ERROR : FileUploadException - " + " Stacktrace : " + e.getStackTrace() + " : Error Message - " + e.getMessage());
			e.printStackTrace();
			message.setStatus("ERROR");
			message.setStatusMessage(e.getMessage());
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			writer.println("ERROR : Exception - " + " Stacktrace : " + e.getStackTrace() + " : Error Message - " + e.getMessage());
			e.printStackTrace();
			message.setStatus("ERROR");
			message.setStatusMessage(e.getMessage());
		}
		finally{
			response.setContentType("text/html");
			
			if(message.getStatus().equals("OK")){
				String json = gson.toJson(requestResult);
				response.getWriter().write(json);
			}
			else if(message.getStatus().equals("ERROR"))
			{
				String json = gson.toJson(message);
				response.getWriter().write(json);
			}
		}
	}
}