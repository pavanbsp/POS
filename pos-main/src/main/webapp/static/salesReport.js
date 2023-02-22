function getReportsUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/reports";
}

function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}

function generateSalesReport(){
    var json = formData();
    var url = getReportsUrl() + "/sales-report";
    $.ajax({
        contentType: 'application/json',
        url: url,
        type: 'POST',
        data: json,
        dataType: 'json',
        success: function(data){
            displaySalesReport(data);
        },
        error: handleAjaxError
    });
}

function displaySalesReport(data) {
    var $tbody = $('#SalesReport-table').find('tbody');
    $tbody.empty();
    var table = $('#SalesReport-table').DataTable();
    if(table != null) {
        table.clear().destroy();
    }
    var totalQuantity = 0;
    var totalRevenue = 0;
    for(var i in data){
        var e = data[i];
        var row = '<tr>'
        + '<td>' + e.brand + '</td>'
        + '<td>' + e.category + '</td>'
        + '<td>'  + e.quantity + '</td>'
        + '<td>'  + e.revenue + '</td>'
        + '</tr>';
        $tbody.append(row);
    }
    $('#SalesReport-table').DataTable();
}

function downloadSalesPDF() {
    var json = formData();
    var url = getReportsUrl() + "/sales-report/download";
	$.ajax({
       url: url,
       type: 'POST',
       data: json,
       headers: {
           'Content-Type': 'application/json'
         },
	    success: function(data) {
            toPdf(data, "SalesReport_");
            success("Sales Report Pdf downloaded successfully");
       },
       error: handleAjaxError
	});
}

function downloadSalesCSV() {
    var json = formData();
    var url = getReportsUrl() + "/sales-report";
    $.ajax({
        contentType: 'application/json',
        url: url,
        type: 'POST',
        data: json,
        dataType: 'json',
        success: function(data){
            toCSV(data, "SalesReport_");
            success("Inventory report csv downloaded successfully");
        },
        error: handleAjaxError
    });
}


function formData() {
    var toDate = new Date(document.getElementById("toDate").value.trim());
    var fromDate = new Date(document.getElementById("fromDate").value.trim());
    var brandInput = document.getElementById("inputBrand").value;
    var categoryInput = document.getElementById("inputCategory").value;
    var json = {
        "to": toDate.toISOString(),
        "brand": brandInput,
         "category": categoryInput,
        "from": fromDate.toISOString(),
    };
    console.log(json);
    return JSON.stringify(json);
}


function getDropdowns(){
	var url = getBrandUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		loadDropdowns(data);
	   },
	   error: handleAjaxError
	});
}

function loadDropdowns(data){
	var $ddownbrand = $('#inputBrand');
	var $ddowncategory = $('#inputCategory');
	var brand = [];
	var category = [];
	$ddownbrand.empty();
	$ddowncategory.empty();
	for(var i in data) {
	    var e = data[i];
	    brand.push(e.brand);
	    category.push(e.category);
	}
	let uniqueBrands = [...new Set(brand)];
	let uniqueCategory = [...new Set(category)];
	var initial_brand = '<option value=""> all brands </option>';
	$ddownbrand.append(initial_brand);
	var initial_category = '<option value=""> all categories </option>';
	$ddowncategory.append(initial_category);
	for(var i in uniqueBrands) {
	    var b = '<option value="' + uniqueBrands[i] + '">' + uniqueBrands[i] + '</option>';
	    $ddownbrand.append(b);
	}
	for(var i in uniqueCategory) {
	    var b = '<option value="' + uniqueCategory[i] + '">' + uniqueCategory[i] + '</option>';
    	$ddowncategory.append(b);
    }
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
    generateSalesReport();
    $('#downloadSalesReportPDF').click(downloadSalesPDF);
    $('#downloadSalesReportCSV').click(downloadSalesCSV);
    $('#generateSalesReport').click(generateSalesReport);
    $('#fromDate').on('change', disableDate);
    $('#toDate').on('change', disableDate);
    getDropdowns();
}

$(document).ready(initialize_date);
$(document).ready(init);