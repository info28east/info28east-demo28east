
function TrackChart() {
    try {
        this.elevationChart = null;
        this.speedChart = null;

        // Load the Visualization API and the piechart package.
        google.load('visualization', '1.0', { 'packages': ['corechart'] });

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
            areaOpacity: 0.6,
            lineWidth: 1
        };
        data.addRows(rows);

        this.elevationChart.draw(data, options);
    }
    catch (e) {
        alert(e.message);
    }
}


TrackChart.prototype.SpeedData = function (tracks, div) {
    var chart;
    var data;
    var i = 0;
    var ref = this;

    try {
        this.speedChart = new google.visualization.AreaChart(div);
        data = new google.visualization.DataTable();

        data.addColumn('number', 'vertexindex');
        data.addColumn('number', 'Speed');
        data.addColumn('number', 'SpeedLimit');

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
        this.speedChart.setSelection([{ row: index, column: 1 }, { row: index, column: 2 }]);
        this.elevationChart.setSelection([{ row: index, column: 1 }]);
    }
    catch (e) {
        alert(e.message);
    }
}