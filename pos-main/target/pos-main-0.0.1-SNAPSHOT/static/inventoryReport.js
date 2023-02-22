function getReportsUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/reports";
}

function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}

function generateInventoryReport() {
    var $form = $("#input-form").find('select, textarea, input');
    var json = toJson($form);
    var url = getReportsUrl() + "/inventoryReport";
   $.ajax({
       contentType: 'application/json',
       url: url,
       type: 'POST',
       data: json,
       headers: {
            'Content-Type': 'application/json'
      },
       success: function(data){
            displayInventoryReport(data);
       },
       error: handleAjaxError
   });
}

function displayInventoryReport(data) {
    var $tbody = $('#inventoryReport-table').find('tbody');
    $tbody.empty();
    var table = $('#inventoryReport-table').DataTable();
    if(table != null) {
        table.clear().destroy();
    }
    for(var i in data){
        var e = data[i];
        var row = '<tr>'
        + '<td>' + e.name + '</td>'
        + '<td>' + e.brand + '</td>'
        + '<td>' + e.category + '</td>'
        + '<td>'  + e.quantity + '</td>'
        + '</tr>';
        $tbody.append(row);
    }
    $('#inventoryReport-table').DataTable();
}

function downloadInventoryPDF() {
	var url = getReportsUrl() + "/inventoryReport/download";
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
            toPdf(data, "InventoryReport_");
            success("Inventory Report Pdf downloaded successfully");
       },
       error: function(response){
            handleAjaxError(response);
       }
	});
}

function downloadInventoryCSV() {
    var $form = $("#input-form").find('select, textarea, input');
    var json = toJson($form);
    var url = getReportsUrl() + "/inventoryReport";
   $.ajax({
       contentType: 'application/json',
       url: url,
       type: 'POST',
       data: json,
       headers: {
            'Content-Type': 'application/json'
      },
       success: function(data){
        toCSV(data, "InventoryReport_");
        success("Inventory report csv downloaded successfully");
       },
       error: handleAjaxError
   });
}

function init() {
    $('#downloadInventoryPDF').click(downloadInventoryPDF);
    $('#downloadInventoryCSV').click(downloadInventoryCSV);
    $('#generateInventoryReport').click(generateInventoryReport);
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
    generateInventoryReport();
}
