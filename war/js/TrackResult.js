
function TrackResult(jsonResponse) {

    this.KmzID = jsonResponse.kmzID;
    this.date = jsonResponse.date;
    this.totalDistance = jsonResponse.totalDistance;
    this.travelTime = jsonResponse.travelTime;
    this.avgSpeed = jsonResponse.avgSpeed;
    this.startTime = jsonResponse.startTime;
    this.endTime = jsonResponse.endTime;
    this.minSpeed = jsonResponse.minSpeed;
    this.maxSpeed = jsonResponse.maxSpeed;
    this.totalViolations = jsonResponse.totalViolations;

    this.trackSegments = new Array();
    var ref = this;

    $.each(jsonResponse.trackSegments, function (track, segment) {
        var trackSegment = new TrackSegment(segment);
        ref.trackSegments.push(trackSegment);
    });

}

TrackResult.prototype.KmzID = function(){
    return(this.KmzID);
}

TrackResult.prototype.Date = function () {
    return (this.date);
}

TrackResult.prototype.TotalDistance = function () {
    return (this.totalDistance);
}

TrackResult.prototype.TravelTime = function () {
    return (this.travelTime);
}

TrackResult.prototype.AvgSpeed = function () {
    return (this.avgSpeed);
}

TrackResult.prototype.StartTime = function () {
    return (this.startTime);
}

TrackResult.prototype.EndTime = function () {
    return (this.endTime);
}

TrackResult.prototype.MinSpeed = function () {
    return (this.minSpeed);
}

TrackResult.prototype.MaxSpeed = function () {
    return (this.maxSpeed);
}

TrackResult.prototype.TotalViolations = function () {
    return (this.totalViolations);
}

TrackResult.prototype.TrackSegments = function () {
    return (this.trackSegments);
}