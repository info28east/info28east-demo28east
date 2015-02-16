
function TrackModel(map) {

    this.baseURL = "http://demo28east.appspot.com/rest/tracks/download/";
    this.trackResult = null;
    this.map = map;
    this.trackVertices = new google.maps.MVCArray();
    this.prevMarker = null;

    this.vertexEventMarkers = new Array();
    Observable.call(this);
}

TrackModel.prototype = new Observable();

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

                google.maps.event.addListener(mrk, 'mouseover', function (event) {
                    var searchIndex = event.latLng.lat().toString() + ":" + event.latLng.lng().toString();
                    var item = ref.FindTrackItem(searchIndex);
                    ref.fireEvent("markermouseover", item);
                });
            }
        }
    }
    catch (e) {
        throw e;
    }
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