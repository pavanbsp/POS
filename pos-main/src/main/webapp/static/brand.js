function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content");
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
function addBrand(event){
	//Set the values to update
	var $form = $('#brand-add-form :input');
	var json = toJson($form);
	if(!validateBrandForm(json)) {
	    return false;
	}
	var url = getBrandUrl();
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getBrandList();
            displayAddBrand();
            success("Brand added successfully");
            add = true;
	   },
	   error: handleAjaxError
	});
	return false;
}

function updateBrand(event){
	//Get the ID
	var id = $("#brand-edit-form input[name=id]").val();
	var url = getBrandUrl() + "/" + id;
	//Set the values to update
	var $form = $("#brand-edit-form :input");
	var json = toJson($form);
	if(!validateBrandForm(json)) {
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
	   		getBrandList();
	   		$('#edit-brand-modal').modal('toggle');
	   		success("Brand updated successfully");
	   },
	   error: handleAjaxError
	});

	return false;
}


function getBrandList(){
	var url = getBrandUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrandList(data);
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#brandFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	//keys check
    var title = Object.keys(fileData[0]);
    if(title[0]!='brand' || title[1]!='category' || title.length!=2){
            error("Incorrect tsv format");
            return;
    }
    //no of rows check
    if (fileData.length>5000){
        error("File Rows should be within 5000 rows");
        return;
    }
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
	    getBrandList();
	    $('#process-data').attr("disabled",true);
	    if(fileData.length != errorData.length) {
            var msg = "Brands uploaded successfully";
            if(errorData.length != 0) {
                msg = "Some " + msg;
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
	var url = getBrandUrl();

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
	   	   		row.error=response.responseJSON.message
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayBrandList(data){
    var $thead = $('#brand-table').find('thead');
    $thead.empty();
    var head;

    if(getRole() == "supervisor") {
            head = '<tr>'
            + '<th scope="col">' + "ID" + '</th>'
            + '<th scope="col">' + "Brand" + '</th>'
            + '<th scope="col">'  + "Category" + '</th>'
            + '<th scope="col">' + "Actions" + '</th>'
            + '</tr>';
    }
    else {
                head = '<tr>'
                + '<th scope="col">' + "ID" + '</th>'
                + '<th scope="col">' + "Brand" + '</th>'
                + '<th scope="col">'  + "Category" + '</th>'
                + '</tr>';
    }

    $thead.append(head);
	var $tbody = $('#brand-table').find('tbody');
	var table = $('#brand-table').DataTable();
    if(table != null) {
        table.clear().destroy();
    }
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button type="button" class="btn btn-warning" onclick="displayEditBrand(' + e.id + ')"> <i class="fa fa-pencil-square" aria-hidden="true"></i> </button>'
		if(getRole() == "operator") {
            var row = '<tr>'
                    + '<td>' + e.id + '</td>'
                    + '<td>' + e.brand + '</td>'
                    + '<td>'  + e.category + '</td>'
                    + '</tr>';
		}
		else{
            var row = '<tr>'
            + '<td>' + e.id + '</td>'
            + '<td>' + e.brand + '</td>'
            + '<td>'  + e.category + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';

            }
            $tbody.append(row);
	}
	$('#brand-table').DataTable({
	    stateSave: true,
	});
	if(add == true){
        $('#brand-table').DataTable().page('last').draw('page');
        add = false;
    }
    if(reload == true) {
        $('#brand-table').DataTable().order([[0, 'asc']]);
        $('#brand-table').DataTable().page('first').draw('page');
        reload = false;
    }
}

function displayEditBrand(id){
	var url = getBrandUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrand(data);
	   },
	   error: handleAjaxError
	});
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#brandFile');
	$file.val('');
	$('#brandFileName').html("Choose File");
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
	var $file = $('#brandFile');
	var fileName = $file.val();
	var f=fileName.split("\\");
	if(String(f).split(".")[1]!="tsv"){
        error("Upload file should be tsv");
        displayUploadData();
        return;
    }
    $('#brandFileName').html(f[f.length-1]);
    $('#process-data').attr("disabled",false);
}

function displayUploadData(){
    $("#download-errors").attr("disabled",true);
    $('#process-data').attr("disabled",true);
 	resetUploadDialog();
	closeDivs();
    document.getElementById("upload-div").style.display="table";
}

function displayAddBrand(){
	$("#brand-add-form input[name=brand]").val("").focus();
	$("#brand-add-form input[name=category]").val("");
	closeDivs();
	document.getElementById("add-div").style.display="table";
	$("#brand-add-form input[name=brand]").val("").focus();
}

function displayBrand(data){
	$("#brand-edit-form input[name=brand]").val(data.brand);
	$("#brand-edit-form input[name=category]").val(data.category);
	$("#brand-edit-form input[name=id]").val(data.id);
	closeDivs();
    document.getElementById("edit-div").style.display="table";
    $("#brand-edit-form input[name=brand]").val(data.brand).focus();
}

function validateBrandForm(data) {
    json = JSON.parse(data);
    if(isBlank(json.brand)) {
        error("Brand field must not be empty")
        return false;
    }
    if(isBlank(json.category)) {
        error("Category field must not be empty")
        return false;
    }
    return true;
}
//INITIALIZATION CODE
function init(){
    $('#show-add-brand').click(displayAddBrand);
	$('#add-brand').click(addBrand);
	$('#update-brand').click(updateBrand);
	$('#refresh-data').click(getBrandList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#brandFile').on('change', updateFileName);
    $('#close-add-div').click(closeDivs);
    $('#close-edit-div').click(closeDivs);
    $('#close-upload-div').click(closeDivs);
}

function closeDivs() {
    document.getElementById("add-div").style.display="none";
    document.getElementById("edit-div").style.display="none";
    document.getElementById("upload-div").style.display="none";
}

var reload = true;
var add = false;
$(document).ready(init);
$(document).ready(getBrandList);