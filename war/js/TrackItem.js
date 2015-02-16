function TrackItem(jsonObject) {

    var latLong = jsonObject.latLong;
    var snappedLatLong = jsonObject.snappedLatLong;
    this.lat = parseFloat(latLong.toString().split(",")[0]);
    this.lng = parseFloat(latLong.toString().split(",")[1]);
    this.snappedLat = parseFloat(snappedLatLong.toString().split(",")[0]);
    this.snappedLong = parseFloat(snappedLatLong.toString().split(",")[1]);
    this.searchIndex = this.snappedLat + ":" + this.snappedLong;
    this.elevation = parseInt(jsonObject.elevation);

    var speedNum;
    if (isNaN(parseInt(jsonObject.speed))) {
        speedNum = 0;
    }
    else {
        speedNum = parseInt(jsonObject.speed);
    }

    this.speed = speedNum;
    this.speedLimit = parseInt(jsonObject.speedLimit);
}

TrackItem.prototype.Lat = function (){
    return(this.lat);
}

TrackItem.prototype.Lng = function () {
    return(this.lng);
}

TrackItem.prototype.SnappedLat = function () {
    return(this.snappedLat);
}

TrackItem.prototype.SnappedLng = function () {
    return(this.snappedLong);
}

TrackItem.prototype.Elevation = function () {
    return(this.elevation);
}

TrackItem.prototype.Speed = function () {
    return(this.speed);
}

TrackItem.prototype.SpeedLimit = function () {
    return(this.speedLimit);
}

TrackItem.prototype.Index = function () {
    return(this.searchIndex)
}