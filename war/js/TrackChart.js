
function TrackChart() {
    try {
    	
        this.elevationChart = null;
        this.speedChart = null;
        this.gaugeChart = null;
        this.chartData = null;
        this.tracks = null;
        this.gaugeData = null;
        this.gaugeOpions = null;
        
        // Load the Visualization API and the piechart package.
        google.load('visualization', '1.0', { 'packages': ['corechart','gauge'] });

        Observable.call(this);
    }
    catch (e) {
        alert("{error : \"" + e.message + "\"}");
    }
}

TrackChart.prototype = new Observable();

TrackChart.prototype.Data = function (tracks, div) {
    var chart;
    var data;
    var i = 0;
    ref = this;

    try{
        this.elevationChart = new google.visualization.AreaChart(div);
        data = new google.visualization.DataTable();

        data.addColumn('number', 'vertexindex');
        data.addColumn('number', 'Elevation');

        var rows = [];
        var len = tracks.length;

        while (i < len) {
            var item = tracks[i];
            rows.push([i, item.FromAltitude()]);
            i++;
        }
        
        var options = {
            title: "Elevation",
            colors: ["gray"],
            titleTextStyle: {
                color: "gray",
                fontName: "sans-serif",
                fontSize: 15
            },
            chart: {
                title: 'Elevation',
                subtitle: 'in metres'
            },
            legend: {
                textStyle: {
                    color: 'gray',
                    fontSize: 11,
                    fontName: 'sans-serif'
                }
            },
            vAxis: {
                format: '#,### m',
                textStyle: {
                    color: 'gray',
                    fontSize: 12,
                    fontName: 'sans-serif',
                    bold: true
                }
            },
            hAxis: {
                textStyle: {
                    color: 'white'
                }
            },
            width: '400px',
            height: '200px',
            areaOpacity: 0.3,            
            lineWidth: 1
        };
        data.addRows(rows);

        function selectHandler() {
            var selectedItem = ref.elevationChart.getSelection()[0];
            ref.SetSelection(selectedItem.row);
            ref.fireEvent("chartselect", selectedItem);
        }

        google.visualization.events.addListener(ref.elevationChart, 'select', selectHandler);

        this.elevationChart.draw(data, options);
    }
    catch (e) {
        alert(e.message);
    }
}


TrackChart.prototype.GaugeData = function (tracks, divGauge) {
	var chart;
    var data;
    var i = 0;
    
    this.tracks = tracks;
    
    try{    	
    	this.gaugeChart = new google.visualization.Gauge(divGauge);
    	this.gaugeData = google.visualization.arrayToDataTable([
    	  ['Label', 'Value'],
		  ['Speed', 0],
		]);
	    
    	this.gaugeOpions = {	    
	    		width: 300, height: 300, max: 220,
	            redFrom: 140, redTo: 220,
	            yellowFrom:90, yellowTo: 130,
	            minorTicks: 2
	    };
	    
		this.gaugeChart.draw(this.gaugeData, this.gaugeOpions);
    }
    catch(e){
    	alert(e.message);
    }
}


TrackChart.prototype.SpeedData = function (tracks, divArea) {
    var chart;
    var data;
    var i = 0;
    var ref = this;
    
    try {
    	
        this.speedChart = new google.visualization.AreaChart(divArea);
        data = new google.visualization.DataTable();
        
        data.addColumn('number', 'vertexindex');
        data.addColumn('number', 'Actual Speed');
        data.addColumn('number', 'Speed Limit');

        var rows = [];

        var len = tracks.length;

        while (i < len) {
            var item = tracks[i];
            rows.push([i, item.Speed(), item.SpeedLimit()]);
            i++;
        }
        
        var options = {
            title: "Speed / Speed Limit",
            titleTextStyle: {
                color: "gray",
                fontName: "sans-serif",
                fontSize: 15
            },
            chart: {
                title: 'Speed / Speed Limit',
                subtitle: 'in metres'
            },
            legend:{
                textStyle: {
                    color: 'gray',
                    fontSize: 11,
                    fontName: 'sans-serif'
                }
            },
            vAxis: {
                format: '# km/h',
                textStyle: {
                    color: 'gray',
                    fontSize: 12,
                    fontName: 'sans-serif',
                    bold: true
                }
            },
            hAxis: {
                textStyle: {
                    color: 'white'
                }
            },
            width: '400px',
            height: '200px',
            areaOpacity: 0.1,
            lineWidth: 1
        };
        
        
        
        data.addRows(rows);
        
        function selectHandler() {
            var selectedItem = ref.speedChart.getSelection()[0];
            ref.SetSelection(selectedItem.row);
            ref.fireEvent("chartselect", selectedItem);
        }
        
        google.visualization.events.addListener(ref.speedChart, 'select', selectHandler);
        this.speedChart.draw(data, options);        
    }
    catch (e) {
        alert(e.message);
    }
}


TrackChart.prototype.SetSelection = function (index) {
    try{
    	var track = this.tracks[index];
        this.gaugeData.setValue(0, 1, Math.round(track.Speed()));
        this.gaugeChart.draw(this.gaugeData, this.gaugeOpions);
        
    	this.speedChart.setSelection([{ row: index, column: 1 }, { row: index, column: 2 }]);
        this.elevationChart.setSelection([{ row: index, column: 1 }]);
    }
    catch (e) {
        alert(e.message);
    }
}