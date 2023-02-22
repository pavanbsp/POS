function getReportsUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/reports";
}

function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}

function generateBrandReport() {
    var $form = $("#input-form").find('select, textarea, input');
    var json = toJson($form);
    var url = getReportsUrl() + "/brandReport";
   $.ajax({
       contentType: 'application/json',
       url: url,
       type: 'POST',
       data: json,
       headers: {
            'Content-Type': 'application/json'
      },
       success: function(data){
            displayBrandReport(data);
       },
       error: handleAjaxError
   });
}

function displayBrandReport(data) {
    var $tbody = $('#brandReport-table').find('tbody');
    $tbody.empty();
    var table = $('#brandReport-table').DataTable();
    if(table != null) {
        table.clear().destroy();
    }
    for(var i in data){
        var e = data[i];
        var row = '<tr>'
        + '<td>' + e.brand + '</td>'
        + '<td>' + e.category + '</td>'
        + '</tr>';
        $tbody.append(row);
    }
    $('#brandReport-table').DataTable();
}

function downloadBrandReportPDF() {
	var url = getReportsUrl() + "/brandReport/download";
	var $form = $("#input-form").find('select, textarea, input');
	var json = toJson($form);
	$.ajax({
	   url: url,
	   type: 'POST',
       data: json,
       headers: {
            'Content-Type': 'application/json'
          },
	    success: function(data) {
            toPdf(data, "BrandReport_");
            success("Brand Report Pdf downloaded successfully");
       },
       error: function(response){
            handleAjaxError(response);
       }
	});
}

function downloadBrandReportCSV() {
    var $form = $("#input-form").find('select, textarea, input');
    var json = toJson($form);
    var url = getReportsUrl() + "/brandReport";
   $.ajax({
       contentType: 'application/json',
       url: url,
       type: 'POST',
       data: json,
       headers: {
            'Content-Type': 'application/json'
      },
       success: function(data){
            toCSV(reportData, "BrandReport_");
            success("Brand report csv downloaded successfully");
       },
       error: handleAjaxError
   });
}

function init() {
    $('#downloadBrandReportPDF').click(downloadBrandReportPDF);
    $('#downloadBrandReportCSV').click(downloadBrandReportCSV);
    $('#generateBrandReport').click(generateBrandReport);
    getDropdowns();
}

$(document).ready(init);

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
    generateBrandReport();
}
