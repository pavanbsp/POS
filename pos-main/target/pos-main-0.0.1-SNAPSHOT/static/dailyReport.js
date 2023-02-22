function getReportsUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/reports";
}

function generateDailyReport() {
    var json = formData();
    var url = getReportsUrl() + "/dailyReport";
   $.ajax({
       contentType: 'application/json',
       url: url,
       type: 'POST',
       data: json,
       headers: {
            'Content-Type': 'application/json'
      },
       success: function(data){
            displayDailyReport(data);
       },
       error: handleAjaxError
   });
}

function displayDailyReport(data) {
    var table = $('#dailyReport-table').DataTable();
    if(table != null) {
        table.clear().destroy();
    }
    var $tbody = $('#dailyReport-table').find('tbody');
    $tbody.empty();
    for(var i in data){
        var e = data[i];
        var row = '<tr>'
        + '<td>' + e.date + '</td>'
        + '<td>' + e.invoiced_orders_count + '</td>'
        + '<td>' + e.invoiced_items_count + '</td>'
        + '<td>'  + e.total_revenue + '</td>'
        + '</tr>';
        $tbody.append(row);
    }
    $('#dailyReport-table').DataTable({
        bAutoWidth: false,
        aoColumns : [
            { sWidth: '30%' },
            { sWidth: '20%' },
            { sWidth: '20%' },
            { sWidth: '30%' },
        ]
    });
}

function downloadDailyReportPDF() {
    var json = formData();
	var url = getReportsUrl() + "/dailyReport/download";
	$.ajax({
	    contentType: 'application/json',
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function(data) {
            toPdf(data, "DailyReport_");
            success("Daily Report Pdf downloaded successfully");
       },
       error: function(response){
            handleAjaxError(response);
       }
	});
}

function downloadDailyReportCSV() {
    var json = formData();
    var url = getReportsUrl() + "/dailyReport";
   $.ajax({
       contentType: 'application/json',
       url: url,
       type: 'POST',
       data: json,
       headers: {
            'Content-Type': 'application/json'
      },
       success: function(data){
            toCSV(data, "DailyReport_");
            success("Day Sales report csv downloaded successfully");
       },
       error: handleAjaxError
   });
}

function formData() {
    var toDate = document.getElementById("toDate").value.trim();
    var fromDate = document.getElementById("fromDate").value.trim();
    var json = {
        "from": fromDate,
        "to": toDate
    };
    console.log(json);
    return JSON.stringify(json);
}


function disableDate() {
    var fromDateField = document.getElementById("fromDate");
    var toDateField = document.getElementById("toDate");

    var selectedFromDate = new Date(fromDateField.value);
    var selectedToDate = new Date(toDateField.value);
    var minEndDate = selectedFromDate.toISOString().split('T')[0];
    var maxEndDate = selectedToDate.toISOString().split('T')[0];
    toDateField.setAttribute("min", minEndDate);
    fromDateField.setAttribute("max", maxEndDate);
}

function initialize_date() {
    var date = new Date();
    var today = new Date(new Date().setDate(date.getDate()));
    var last = new Date(new Date().setDate(date.getDate() - 30));
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();
    today = yyyy + "-" + mm + "-" + dd;

    dd = String(last.getDate()).padStart(2, '0');
    mm = String(last.getMonth() + 1).padStart(2, '0'); //January is 0!
    yyyy = last.getFullYear();
    last = yyyy + "-" + mm + "-" + dd;
    $('#toDate').val(today);
    $('#fromDate').val(last);

    var toDate = document.getElementById("toDate");
    var fromDate = document.getElementById("fromDate");
    var tomorrow = new Date(date.getTime());
    var minDate = tomorrow.toISOString().split('T')[0];
    toDate.setAttribute("max", minDate);
    fromDate.setAttribute("max", minDate);
    disableDate();
}

function init() {
    $('#downloadDailyReportPDF').click(downloadDailyReportPDF);
    $('#downloadDailyReportCSV').click(downloadDailyReportCSV);
    $('#generateDailyReport').click(generateDailyReport);
    $('#fromDate').on('change', disableDate);
    $('#toDate').on('change', disableDate);
    generateDailyReport();
}

$(document).ready(initialize_date);
$(document).ready(init);