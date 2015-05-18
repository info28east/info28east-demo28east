
function TrackSegment(segmentJson) {

    this.pointFromLat = parseFloat(segmentJson.fromPointLatLng.split(",")[0]);
    this.pointFromLng = parseFloat(segmentJson.fromPointLatLng.split(",")[1]);
    this.pointToLat = parseFloat(segmentJson.toPointLatLng.split(",")[0]);
    this.pointToLng = parseFloat(segmentJson.toPointLatLng.split(",")[1]);
	
    this.pointSnappedFromLat = parseFloat(segmentJson.fromSnappedLatLng.split(",")[0]);
    this.pointSnappedFromLng = parseFloat(segmentJson.fromSnappedLatLng.split(",")[1]);;
    this.pointSnappedToLat = parseFloat(segmentJson.toSnappedLatLng.split(",")[0]);
    this.pointSnappedToLng = parseFloat(segmentJson.toSnappedLatLng.split(",")[1]);
	
    this.fromAltitude = segmentJson.fromAltitude;
    this.toAltitude = segmentJson.toAltitude;
    
    this.fromBearing = segmentJson.fromBearing;
    this.toBearing = segmentJson.toBearing;
    
    this.speed = segmentJson.speed;
    this.speedLimit = segmentJson.speedLimit;    
}



TrackSegment.prototype.FromLat = function () {
    return(this.pointFromLat);
}

TrackSegment.prototype.FromLng = function () {
    return (this.pointFromLng);
}

TrackSegment.prototype.ToLat = function () {
    return (this.pointToLat);
}

TrackSegment.prototype.ToLng = function () {
    return (this.pointSnappedFromLat);
}



TrackSegment.prototype.SnappedFromLat = function () {
    return (this.pointSnappedFromLat);
}

TrackSegment.prototype.SnappedFromLng = function () {
    return (this.pointSnappedFromLng);
}

TrackSegment.prototype.SnappedToLat = function () {
    return (this.pointSnappedToLat);
}

TrackSegment.prototype.SnappedToLng = function () {
    return (this.pointSnappedToLng);
}



TrackSegment.prototype.Distance = function () {
    return (this.distance);
}

TrackSegment.prototype.Speed = function () {
    return (parseFloat(this.speed));
}

TrackSegment.prototype.SpeedLimit = function () {
    return (parseFloat(this.speedLimit));
}



TrackSegment.prototype.FromAltitude = function () {
    return (parseInt(this.fromAltitude));
}

TrackSegment.prototype.ToAltitude = function () {
    return (parseInt(this.toAltitude));
}

TrackSegment.prototype.Index = function () {
    return (this.pointSnappedFromLat.toString() + ":" + this.pointSnappedFromLng.toString());
}



TrackSegment.prototype.FromBearing = function () {
    return (parseInt(this.fromBearing));
}
TrackSegment.prototype.ToBearing = function () {
    return (parseInt(this.toBearing));
}


