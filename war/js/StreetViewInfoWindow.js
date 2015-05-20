function StreetViewInfo(map){
	
	this.map= map;
	this.content = document.createElement("DIV");
		
	this.streetview = document.createElement("DIV");
	this.streetview.style.width = "300px";
	this.streetview.style.height = "300px";
	this.content.appendChild(this.streetview);
	
	this.panorama = null;
	this.clickedMarker = null;
	this.clickedTrackItem = null;
	this.htmlContent = document.createElement("DIV");
	this.content.appendChild(this.htmlContent);
	
	this.sv = new google.maps.StreetViewService();
	this.infowindow = new google.maps.InfoWindow({
		size: new google.maps.Size(150, 50),
		content: this.content
	});	
	
	this.gmarkers = [];
	this.side_bar_html = "";
	this.html = "HALOO";	
	this.pin = new google.maps.MVCObject();	
}



StreetViewInfo.prototype.StreetViewService = function(){
	return(this.sv);
}



StreetViewInfo.prototype.SetClickedMarker = function(marker){
	this.clickedMarker = marker;
}



StreetViewInfo.prototype.SetClickedTrackItem = function(trackItem){
	this.clickedTrackItem = trackItem;
}



StreetViewInfo.prototype.SetPanoramaElement = function(element){
	this.panorama = new google.maps.StreetViewPanorama(element, {
		navigationControl: false,
		enableCloseButton: false,
		addressControl: false,
		linksControl: false,
		visible: true
	});
}



StreetViewInfo.prototype.ShowStreetView = function(latlng, bearing){
	var ref = this;
	
	this.sv.getPanoramaByLocation(latlng, 50, function(data, status){
		if (status == google.maps.StreetViewStatus.OK) {
			ref.pin.set("position", latlng);
			ref.panorama.bindTo("position", ref.pin);			
			if (!!ref.panorama && !!ref.panorama.setPano) {				
				ref.panorama.setPano(data.location.pano);
				ref.panorama.setPov({
					heading: bearing,
					pitch: 0,
					zoom: 1
				});
				ref.panorama.setVisible(true);
			}
		}
	});
}



StreetViewInfo.prototype.OpenStreetViewInfoWindow = function(latlng) {
	var ref = this;
	
	this.sv.getPanoramaByLocation(latlng, 50, function(data, status){
		if (status == google.maps.StreetViewStatus.OK) {
			ref.OpenInfoWindow();
			
			ref.panorama = new google.maps.StreetViewPanorama(ref.streetview, {
				navigationControl: false,
				enableCloseButton: false,
				addressControl: false,
				linksControl: false,
				visible: true
			});
			ref.panorama.bindTo("position", ref.pin);
			
			if (!!ref.panorama && !!ref.panorama.setPano) {				
				ref.panorama.setPano(data.location.pano);
				ref.panorama.setPov({
					heading: ref.clickedTrackItem.ToBearing(),
					pitch: 0,
					zoom: 1
				});
				ref.panorama.setVisible(true);
			}
		}
	});
}



StreetViewInfo.prototype.OpenInfoWindow = function () {
	try{
		this.htmlContent.innerHTML = "TEST 2";
		this.pin.set("position", this.clickedMarker.getPosition());
		this.infowindow.open(this.map, this.clickedMarker);
	}
	catch(e){
		alert(e.message);
	}
}