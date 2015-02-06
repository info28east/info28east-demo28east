package com.demo.east.kml;


import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class KMLFactoryTEST {
	
	private Document _doc;
	private ArrayList<Node> _returnList;
	
	public KMLFactoryTEST(Document doc){
		this._doc = doc;
	}
	
	
	
	public ArrayList<Node> FindNodes(String withTagName){
		_returnList = new ArrayList<Node>();
		NodeList list = _doc.getElementsByTagName(withTagName);		
		for(int i = 0; i < list.getLength(); i++)
		{
			_returnList.add(list.item(i));
		}
		return(_returnList);
	}
	
	
	
	public ArrayList<Node> FindNodes(String withParentTagName, String withTagName){
		_returnList = new ArrayList<Node>();
		NodeList list = _doc.getElementsByTagName(withTagName);
		for(int i = 0; i < list.getLength(); i++)
		{
			if(list.item(i).getParentNode().getNodeName() == withParentTagName){
				_returnList.add(list.item(i));
			}			
		}		
		return(_returnList);
	}
	
	
	
	public ArrayList<Node> FindNodes(String withParentTagName, String withParentAttrExpression, String withTagName){
		_returnList = new ArrayList<Node>();		
		String arrExp[] = withParentAttrExpression.split("=");
		String attrName = arrExp[0];
		String attrVal = arrExp[1].replace("'", "");
		
		NodeList list = _doc.getElementsByTagName(withTagName);
		for(int i = 0; i < list.getLength(); i++)
		{
			if(list.item(i).getParentNode().getNodeName() == withParentTagName){
				if(list.item(i).getParentNode().getAttributes().getNamedItem(attrName).getNodeValue().equals(attrVal)){
					_returnList.add(list.item(i));
				}
			}
		}
		return(_returnList);
	}
}
