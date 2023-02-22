
function getProductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}

function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}

function getRole(){
	var role = $("meta[name=role]").attr("content");
	if(role == "supervisor") {
        document.getElementById("buttons").style.display="block";
    }
    else {
        $("#table").removeClass('col-8').addClass('col-11');
        document.getElementById("table").style.marginLeft ="30px";
    }
	return role;
}

//BUTTON ACTIONS
function addProduct(event){
	//Set the values to update
	var $form = $("#product-add-form").find('select, textarea, input');
	console.log($form);
	var json = toJson($form);
	var url = getProductUrl();
	if(!validateProduct(json)) {
        return false;
    }
	$.ajax({
	   contentType: 'application/json',
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getProductList();
	   		displayAddProduct();
            success("Product added successfully");
            add = true;
	   },
	   error: handleAjaxError
	});
	return false;
}

function updateProduct(event){
    var table = $('#product-table').DataTable();
	//Get the ID
	var id = $("#product-edit-form input[name=id]").val();
	var url = getProductUrl() + "/" + id;
	//Set the values to update
	$("#product-edit-form select[name=brand]").attr("disabled", false);
    $("#product-edit-form select[name=category]").attr("disabled", false);
	var $form = $("#product-edit-form").find('select, textarea, input');
	var json = toJson($form);
    if(!validateProduct(json)) {
        return false;
    }
	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getProductList();
	        $('#edit-product-modal').modal('toggle');
            success("Product updated successfully");
	   },
	   error: handleAjaxError
	});

	return false;
}


function getProductList(){
	var url = getProductUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProductList(data);
	   },
	   error: handleAjaxError
	});
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

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#productFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
    fileData = results.data;
    //keys check
    var title = Object.keys(fileData[0]);
    console.log(title);
    if(title[0]!='barcode' || title[1]!='brand' || title[2]!='category' || title[3]!='name' || title[4]!='mrp'|| title.length!=5){
        error("Incorrect tsv format");
        return;
    }
    //no of rows check
    if (fileData.length>5000){
        error("File Rows should be within 5000 rows");
        return;
    }
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
	    getProductList();
	    $('#process-data').attr("disabled",true);
		if(fileData.length != errorData.length) {
            var msg = "Brands uploaded successfully";
            if(errorData.length != 0) {
                msg = "Some brands uploaded successfully";
            }
            success(msg);
        }
        if(errorData.length > 0) {
            error("There are some errors while uploading some brands, pls download and check the errors");
        }
        return;
	}

	//Process next row
	var row = fileData[processCount];
	processCount++;

	var json = JSON.stringify(row);
	var url = getProductUrl();

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		uploadRows();
	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayProductList(data){
    var $thead = $('#product-table').find('thead');
    $thead.empty();
    var head;

    if(getRole() == "supervisor") {
            head = '<tr>'
            + '<th scope="col">ID</th>'
            + '<th scope="col">Barcode</th>'
            + '<th scope="col">Brand</th>'
            + '<th scope="col">Category</th>'
            + '<th scope="col">Name</th>'
            + '<th scope="col">Mrp</th>'
            + '<th scope="col">Actions</th>'
            + '</tr>';
    }
    else {
        head = '<tr>'
            + '<th scope="col">ID</th>'
            + '<th scope="col">Barcode</th>'
            + '<th scope="col">Brand</th>'
            + '<th scope="col">Category</th>'
            + '<th scope="col">Name</th>'
            + '<th scope="col">Mrp</th>'
            + '</tr>';
    }
    $thead.append(head);

	var $tbody = $('#product-table').find('tbody');
	var table = $('#product-table').DataTable();
    if(table != null) {
        table.clear().destroy();
    }
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button type="button" class="btn btn-warning" onclick="displayEditProduct(' + e.id + ')"><i class="fa fa-pencil-square" aria-hidden="true"></i></button>'
		if(getRole() == "operator") {
            var row = '<tr>'
                + '<td>' + e.id + '</td>'
                + '<td>' + e.barcode + '</td>'
                + '<td>' + e.brand + '</td>'
                + '<td>'  + e.category + '</td>'
                + '<td>' + e.name + '</td>'
                + '<td>' + e.mrp + '</td>'
                + '</tr>';
        }
        else{
            var row = '<tr>'
                + '<td>' + e.id + '</td>'
                + '<td>' + e.barcode + '</td>'
                + '<td>' + e.brand + '</td>'
                + '<td>'  + e.category + '</td>'
                + '<td>' + e.name + '</td>'
                + '<td>' + e.mrp + '</td>'
                + '<td>' + buttonHtml + '</td>'
                + '</tr>';
        }
        $tbody.append(row);
	}
	if(getRole() == "operator") {
	    $('#product-table').DataTable({
	        stateSave: true,
    	    bAutoWidth: false,
            aoColumns : [
                { sWidth: '5%' },
                { sWidth: '15%' },
                { sWidth: '20%' },
                { sWidth: '20%' },
                { sWidth: '20%' },
                { sWidth: '10%' },
            ]
    	});
	}
	else{
        $('#product-table').DataTable({
            stateSave: true,
            bAutoWidth: false,
            aoColumns : [
                { sWidth: '5%' },
                { sWidth: '15%' },
                { sWidth: '20%' },
                { sWidth: '20%' },
                { sWidth: '20%' },
                { sWidth: '10%' },
                { sWidth: '10%' },
            ]
        });
    }
    if(add == true){
        $('#product-table').DataTable().page('last').draw('page');
        add = false;
    }
    if(reload == true) {
        $('#product-table').DataTable().order([[0, 'asc']]);
        $('#product-table').DataTable().page('first').draw('page');
        reload = false;
    }
}

function loadDropdowns(data){
	var $ddownbrand = $('#product-add-form').find('#inputBrand');
	var $ddowncategory = $('#product-add-form').find('#inputCategory');
	var $ddownbrandedit = $('#product-edit-form').find('#inputBrand');
    var $ddowncategoryedit = $('#product-edit-form').find('#inputCategory');
	var brand = [];
	var category = [];
	$ddownbrand.empty();
	$ddowncategory.empty();
	$ddownbrandedit.empty();
    $ddowncategoryedit.empty();
	for(var i in data) {
	    var e = data[i];
	    brand.push(e.brand);
	    category.push(e.category);
	}
	let uniqueBrands = [...new Set(brand)];
	let uniqueCategory = [...new Set(category)];
	var initial_brand = '<option value = "" disabled selected value> -- select a brand -- </option>';
	$ddownbrand.append(initial_brand);
	$ddownbrandedit.append(initial_brand);
	var initial_category = '<option value = "" disabled selected value> -- select a category -- </option>';
	$ddowncategory.append(initial_category);
	$ddowncategoryedit.append(initial_category);
	for(var i in uniqueBrands) {
	    var b = '<option value="' + uniqueBrands[i] + '">' + uniqueBrands[i] + '</option>';
	    $ddownbrand.append(b);
	    $ddownbrandedit.append(b);
	}
	for(var i in uniqueCategory) {
	    var b = '<option value="' + uniqueCategory[i] + '">' + uniqueCategory[i] + '</option>';
    	$ddowncategory.append(b);
    	$ddowncategoryedit.append(b);
    }
}

function displayEditProduct(id){
	var url = getProductUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProduct(data);
	   },
	   error: handleAjaxError
	});
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#productFile');
	$file.val('');
	$('#productFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts
	updateUploadDialog();
}

function updateUploadDialog(){
    if (errorData.length>0){
    	$("#download-errors").attr("disabled",false);
    }
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
    processCount = 0;
	fileData = [];
	errorData = [];
	updateUploadDialog();
	$("#download-errors").attr("disabled",true);
    $('#process-data').attr("disabled",true);
	var $file = $('#productFile');
	var fileName = $file.val();
	var f=fileName.split("\\");
	if(String(f).split(".")[1]!="tsv"){
        error("Upload file should be tsv");
        displayUploadData();
        return;
    }
    $('#productFileName').html(f[f.length-1]);
    $('#process-data').attr("disabled",false);
}

function displayUploadData(){
    $("#download-errors").attr("disabled",true);
    $('#process-data').attr("disabled",true);
 	resetUploadDialog();
 	closeDivs();
	document.getElementById("upload-div").style.display="table";
}

function displayAddProduct(){
	$("#product-add-form input[name=barcode]").val("");
    $("#product-edit-form select[name=brand]").val("");
    $("#product-edit-form select[name=category]").val("");
	$("#product-add-form input[name=name]").val("");
	$("#product-add-form input[name=mrp]").val("");
	closeDivs();
	document.getElementById("add-div").style.display="table";
	$("#product-add-form input[name=barcode]").val("").focus();
}

function displayProduct(data){
	$("#product-edit-form input[name=barcode]").val(data.barcode).attr("readonly", true);
	$("#product-edit-form select[name=brand]").val(data.brand).attr("disabled", true);
	$("#product-edit-form select[name=category]").val(data.category).attr("disabled", true);
	$("#product-edit-form input[name=name]").val(data.name);
	$("#product-edit-form input[name=mrp]").val(data.mrp);
	$("#product-edit-form input[name=id]").val(data.id);
	closeDivs();
    document.getElementById("edit-div").style.display="table";
    $("#product-edit-form input[name=name]").val(data.name).focus();
}

function validateProduct(data) {
	json = JSON.parse(data);
	if(isBlank(json.barcode)) {
        error("Barcode field must not be empty");
        return false;
    }
	if(isBlank(json.brand)) {
		error("Brand field must not be empty");
		return false;
	}
	if(isBlank(json.category)) {
		error("Category field must not be empty");
		return false;
	}
	if(isBlank(json.name)) {
		error("Name field must not be empty");
		return false;
	}
	if(isBlank(json.mrp)) {
		error("Mrp field must not be empty");
		return false;
	}
	else if(isNaN(json.mrp)){
        error("Mrp field must be a float value: "+ json.mrp);
        return false;
    }
    else if(parseInt(json.mrp)<=0){
        error("MRP must be positive");
        return false;
    }
	return true;
}

//INITIALIZATION CODE
function init(){
	$('#show-add-product').click(displayAddProduct);
	$('#add-product').click(addProduct);
	$('#update-product').click(updateProduct);
	$('#refresh-data').click(getProductList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName);
    $('#close-add-div').click(closeDivs);
    $('#close-edit-div').click(closeDivs);
    $('#close-upload-div').click(closeDivs);
}

function closeDivs() {
    document.getElementById("add-div").style.display="none";
    document.getElementById("edit-div").style.display="none";
    document.getElementById("upload-div").style.display="none";
}

var add = false;
var reload = true;
$(document).ready(init);
$(document).ready(getProductList);
$(document).ready(getDropdowns);

