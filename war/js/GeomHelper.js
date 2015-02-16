

function getPolyLineBounds(path) {
    
    var minx = 0;
    var maxx = 0;
    var miny = 0;
    var maxy = 0;

    //Get minx value
    for (var index = 0; index < path.length; index++) {
        var pointXVal = path.getArray()[index].lat();
        if (minx == 0) {
            minx = pointXVal;
        }
        if (minx < pointXVal) {
            minx = pointXVal;
        }
    }

    //Get maxx value
    for (var index = 0; index < path.length; index++) {
        var pointXVal = path.getArray()[index].lat();
        if (maxx == 0) {
            maxx = pointXVal;
        }
        if (maxx > pointXVal) {
            maxx = pointXVal;
        }
    }

    //Get miny value
    for (var index = 0; index < path.length; index++) {
        var pointYVal = path.getArray()[index].lng();
        if (miny == 0) {
            miny = pointYVal;
        }
        if (miny < pointYVal) {
            miny = pointYVal;
        }
    }

    //Get maxy value
    for (var index = 0; index < path.length; index++) {
        var pointYVal = path.getArray()[index].lng();
        if (maxy == 0) {
            maxy = pointYVal;
        }
        if (maxy > pointYVal) {
            maxy = pointYVal;
        }
    }


    var nePoint = new google.maps.LatLng(minx, miny);
    var swPoint = new google.maps.LatLng(maxx, maxy);

    var latlngbnds = new google.maps.LatLngBounds(swPoint, nePoint);

    return(latlngbnds)
}