package com.demo.east.file;



import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;



public class KMZFile  
{
	private String fileID; 
	private String fileStatus;
	//private String bassAddress = "https://drive.google.com/uc?export=download&id=0B9XAhrEjfi6hbnBZemIwcEtMWTA";
	private String bassAddress = "https://drive.google.com/uc?export=download&id=";
	
	
	public KMZFile(String fileid){
		this.fileID = fileid;
	}
	
	
	public String DownloadKML ()throws IOException{
		
		String returnString = "";
		ByteArrayOutputStream out = null;
		InputStream in = null;
		byte[] buffer = new byte[1024];
		
		try{
			URL link = new URL(bassAddress + this.fileID); //The file that you want to download
			
			//Code to download
			in = new BufferedInputStream(link.openStream());
			out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;

			while (-1!=(n=in.read(buf)))
			{
				out.write(buf, 0, n);
			}

			out.close();
			in.close();
			byte[] response = out.toByteArray();
			
			java.util.zip.ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(response));
			
			ZipEntry ze = zis.getNextEntry();
			
			ByteArrayOutputStream output = null;
			
			while(ze!=null){	 		    	   		   					 
				int len;	
				output = new ByteArrayOutputStream();
				
				while ((len = zis.read(buffer)) > 0) {
					output.write(buffer, 0, len);
				}
				
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();
						
			returnString = output.toString();
			
			System.out.println(returnString);
			
		}
		catch(IOException exception){
			throw exception;
		}
		
		return(returnString);
	}



	public String getFileID() {
		return fileID;
	}
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}
	public String getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}



}
