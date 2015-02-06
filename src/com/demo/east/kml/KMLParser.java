package com.demo.east.kml;


import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.demo.east.tracks.TrackRecordingItem;



public class KMLParser {
	
		
	public ArrayList<TrackRecordingItem> ParseKML(String kml) throws Exception{
		
		ArrayList<TrackRecordingItem> returnList = new ArrayList<TrackRecordingItem>();
		
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(kml)));
			
			doc.getDocumentElement().normalize();
			
			KMLFactoryTEST kmlFact = new KMLFactoryTEST(doc);
			ArrayList<Node> listCoords = kmlFact.FindNodes("gx:Track", "gx:coord");
			ArrayList<Node> listTimeStamps = kmlFact.FindNodes("gx:Track", "when");
			ArrayList<Node> listSpeed = kmlFact.FindNodes("gx:SimpleArrayData", "name='speed'", "gx:value");
			ArrayList<Node> listAccuracy = kmlFact.FindNodes("gx:SimpleArrayData", "name='accuracy'", "gx:value");
			
			for(int idx = 0; idx < listCoords.size(); idx++)
			{
				TrackRecordingItem item = new TrackRecordingItem();					
				String coords[] = listCoords.get(idx).getTextContent().split(" ");
				
				item.setLatLong(coords[1] + "," + coords[0]);
				item.setElevation(coords[2]);
				item.setTimeStamp(listTimeStamps.get(idx).getTextContent());
				item.setSpeed(listSpeed.get(idx).getTextContent());
				item.setAccuracy(listAccuracy.get(idx).getTextContent());
				
				returnList.add(item);
			}
		}
		catch(Exception exception)
		{
			throw exception;
		}
		
		return(returnList);
	}
	
}
