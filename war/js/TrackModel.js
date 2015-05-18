// this variable will collect the html which will eventually be placed in the side_bar 
var side_bar_html = "";
var html = "HALOO";

// arrays to hold copies of the markers used by the side_bar 
var gmarkers = [];

var sv = new google.maps.StreetViewService();
var clickedMarker = null;
var panorama = null;
var clickedTrackItem = null;

// Create the shared infowindow with three DIV placeholders
// One for a text string, oned for the html content from the xml, one for the StreetView panorama.
var content = document.createElement("DIV");
var title = document.createElement("DIV");
content.appendChild(title);
var streetview = document.createElement("DIV");
streetview.style.width = "300px";
streetview.style.height = "300px";
content.appendChild(streetview);
var htmlContent = document.createElement("DIV");
content.appendChild(htmlContent);


var infowindow = new google.maps.InfoWindow({
  size: new google.maps.Size(150, 50),
  content: content
});


function TrackModel(map) {
	
	this.xmlHttpRequest = new XMLHttpRequest();
	this.sv = new google.maps.StreetViewService();
    this.baseURL = "http://demo28east.appspot.com/rest/tracks/download/";
    this.trackResult = null;
    this.map = map;
    this.trackVertices = new google.maps.MVCArray();
    this.prevMarker = null;
    this.markers = new Array();
    this.currentMarker = null;
    this.currentInfoWindow = new google.maps.InfoWindow();
        
    this.vertexEventMarkers = new Array();
    Observable.call(this);
}

TrackModel.prototype = new Observable();



TrackModel.prototype.KMZRequest = function (file, callback) {
	var ref = this;
	
	try{
		ref.xmlHttpRequest.onreadystatechange = ref.getReadyStateHandler(ref.xmlHttpRequest, callback);
		ref.xmlHttpRequest.open("POST", "kmz", true);
		ref.xmlHttpRequest.send(file);
	}
	catch(e){
		
	}
}

TrackModel.prototype.getReadyStateHandler = function(xmlHttpRequest, callback){
	var ref = this;
	
	return function() {
    	if (xmlHttpRequest.readyState == 4) {
    		if (xmlHttpRequest.status == 200) {
    			var returnJSON = xmlHttpRequest.responseText;
    			var json = JSON.parse(returnJSON);
    			ref.trackResult = new TrackResult(json);
    			callback();    			
   			} else {
    			alert("Http error " + xmlHttpRequest.status + ":" + xmlHttpRequest.statusText);
  			}		    	
  		}
  	};
	
}


TrackModel.prototype.TrackRequest = function (trackID, callback) {    
    var ref = this;
    var returnPoly;

    try {
        var requestURL = this.baseURL + trackID;
        
        $.ajax({
            url: requestURL,
            type: "GET",
            processData: false,
            timeout: 100000,
            dataType: "json",
            success: function (data) {
                ref.trackResult = new TrackResult(data);
                callback();
            },
            error: function (jqXHR, status) {
                alert(" -- ERROR !!");
            }
        });
    }
    catch (e) {
        alert("Error in GetTrack : " + e.message);
    }

    return (this.trackResult);
}


TrackModel.prototype.ConstructTrack = function () {
    var arrVertices;
    var ref = this;

    try {
        if (ref.trackResult.trackSegments == null || ref.trackResult.trackSegments.length == 0) {
            return(null);
        }
        else {            
            arrVertices = new google.maps.MVCArray();
            
            for (var index = 0; index < ref.trackResult.trackSegments.length; index++) {

                var linePath = new google.maps.MVCArray();
                var trackSegment = ref.trackResult.trackSegments[index];

                var fromPoint = new google.maps.LatLng(parseFloat(trackSegment.SnappedFromLat()), parseFloat(trackSegment.SnappedFromLng()), true);
                var toPoint = new google.maps.LatLng(parseFloat(trackSegment.SnappedToLat()), parseFloat(trackSegment.SnappedToLng()), true);

                linePath.push(fromPoint);
                linePath.push(toPoint);

                var trackColor = "green";
                var trackWeight = "2";
                var trackOpacity = 0.7;
                
                var speed = parseFloat(trackSegment.Speed());
                var limit = parseFloat(trackSegment.SpeedLimit());

                if (speed > limit) {
                    trackColor = "red";
                    trackWeight = "3";
                    trackOpacity = 1;
                }

                var lineOptions = {
                    map: ref.map,
                    path: linePath,
                    strokeColor: trackColor,
                    strokeWeight: trackWeight,
                    strokeOpacity: trackOpacity,
                    visible: true
                };
                
                var line = new google.maps.Polyline(lineOptions);
                ref.trackVertices.push(fromPoint);
                
                var mrkOption = {
                    map: ref.map,
                    position: fromPoint,
                    icon: {
                        path: google.maps.SymbolPath.CIRCLE,
                        fillColor: 'white',
                        strokeColor: 'black',
                        strokeWeight: 2,
                        scale: 6,
                        fillOpacity: 1
                    },
                    opacity: 0
                }
                
                var mrk = new google.maps.Marker(mrkOption);
                ref.vertexEventMarkers.push(mrk);
                
                /*
                google.maps.event.addListener(mrk, 'mouseover', function (event) {
                    var searchIndex = event.latLng.lat().toString() + ":" + event.latLng.lng().toString();
                    var item = ref.FindTrackItem(searchIndex);
                    ref.fireEvent("markermouseover", item);
                });*/
                
                
                google.maps.event.addListener(mrk, 'click', function (event) {
                	try{
                		var searchIndex = event.latLng.lat().toString() + ":" + event.latLng.lng().toString();
                        var item = ref.FindTrackItem(searchIndex)[0];
                        clickedTrackItem = item;
                                                
                		for (var i = 0; i < ref.markers.length; i++) {
                			ref.markers[i].setMap(null);
                	    }
                	    ref.markers = [];
                	    
                		//ref.currentInfoWindow = new google.maps.InfoWindow();
                		var geocoder = new google.maps.Geocoder();
                		var latlng = new google.maps.LatLng(event.latLng.lat(), event.latLng.lng());
                		
                		geocoder.geocode({ 'latLng': latlng }, function (results, status) {
                	        if (status == google.maps.GeocoderStatus.OK) {
                	        	if (results[0]) {
                	        		var marker = new google.maps.Marker({
                	                    position: latlng,
                	                    map: ref.map,
                	                    icon: {
                	                        path: google.maps.SymbolPath.CIRCLE,
                	                        fillColor: 'white',
                	                        strokeColor: 'black',
                	                        strokeWeight: 2,
                	                        scale: 6,
                	                        fillOpacity: 1
                	                    },
                	                    opacity: 0
                	        		});
                	        		
                	        		ref.markers.push(marker);
                	        		clickedMarker = marker;
                	        		sv.getPanoramaByLocation(latlng, 50, processSVData);                	        		
                	        	}
                	        }
                		});
                	}
                	catch(e){
                		alert(e.message);
                	}
                });
                
            }
        }
    }
    catch (e) {
        throw e;
    }
}

function processSVData(data, status) {
	  if (status == google.maps.StreetViewStatus.OK) {
	    var marker = clickedMarker;
	    openInfoWindow(clickedMarker);

	    if (!!panorama && !!panorama.setPano) {

	      panorama.setPano(data.location.pano);
	      panorama.setPov({
	        heading: clickedTrackItem.ToBearing(),
	        pitch: 0,
	        zoom: 1
	      });
	      panorama.setVisible(true);

	      google.maps.event.addListener(marker, 'click', function() {

	        var markerPanoID = data.location.pano;
	        // Set the Pano to use the passed panoID
	        panorama.setPano(markerPanoID);
	        panorama.setPov({
	          heading: 270,
	          pitch: 0,
	          zoom: 1
	        });
	        panorama.setVisible(true);
	      });
	    }
	  } else {
	    openInfoWindow(clickedMarker);
	    title.innerHTML = clickedMarker.getTitle() + "<br>Street View data not found for this location";
	    htmlContent.innerHTML = clickedMarker.myHtml;
	    panorama.setVisible(false);
	    // alert("Street View data not found for this location.");
	  }
}

var pin = new google.maps.MVCObject();

google.maps.event.addListenerOnce(infowindow, "domready", function() {
  panorama = new google.maps.StreetViewPanorama(streetview, {
    navigationControl: false,
    enableCloseButton: false,
    addressControl: false,
    linksControl: false,
    visible: true
  });
  panorama.bindTo("position", pin);
});


function openInfoWindow(marker) {
    title.innerHTML = marker.getTitle();
    htmlContent.innerHTML = marker.myHtml;
    pin.set("position", marker.getPosition());
    infowindow.open(map, marker);
}


TrackModel.prototype.setMarkerVisibility = function (index) {
    var mrk = this.vertexEventMarkers[index];
    if (this.prevMarker == null) {
        this.prevMarker = mrk;
    }
    else {
        this.prevMarker.setOpacity(0);
        this.prevMarker = mrk;
    }    
    mrk.setOpacity(1);
}


TrackModel.prototype.getTrackResult = function () {
    return(this.trackResult);
}


TrackModel.prototype.TrackVertices = function () {
    return(this.trackVertices);
}


TrackModel.prototype.FindTrackItem = function (searchIndex) {
    var i = 0;
    var len = this.trackResult.TrackSegments().length;
    
    while(i<len){
        var item = this.trackResult.TrackSegments()[i];
        if (item.Index() == searchIndex) {
            return ([item,i]);
        }
        i++;
    }
}