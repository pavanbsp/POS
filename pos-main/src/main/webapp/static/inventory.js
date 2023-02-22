
function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
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
function addInventory(event){
	//Set the values to update
	var $form = $("#inventory-add-form :input");
	var json = toJson($form);
	var url = getInventoryUrl();
    if(!validateInventory(json)) {
        return false;
    }
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getInventoryList();
	   		displayAddInventory();
	   		success("Inventory added successfully");
	   },
	   error: handleAjaxError
	});
	return false;
}

function updateInventory(event){
	//Get the ID
	var id = $("#inventory-edit-form input[name=id]").val();	
	var url = getInventoryUrl() + "/" + id;

	//Set the values to update
	var $form = $("#inventory-edit-form :input");
	var json = toJson($form);
    if(!validateInventory(json)) {
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
	   		getInventoryList();
            success("Inventory updated successfully");
	   },
	   error: handleAjaxError
	});

	return false;
}


function getInventoryList(){
	var url = getInventoryUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayInventoryList(data);
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#inventoryFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
    //keys check
    var title = Object.keys(fileData[0]);
    if(title[0]!='barcode' || title[1]!='quantity' || title.length!=2){
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
	    getInventoryList();
	    $('#process-data').attr("disabled",true);
	    if(fileData.length != errorData.length) {
            var msg = "Brand uploaded successfully";
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
	var url = getInventoryUrl();

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

function displayInventoryList(data){
var $thead = $('#inventory-table').find('thead');
    $thead.empty();
    var head;

    if(getRole() == "supervisor") {
            head = '<tr>'
            + '<th scope="col">' + "ID" + '</th>'
            + '<th scope="col">' + "Barcode" + '</th>'
            + '<th scope="col">'  + "Name" + '</th>'
            + '<th scope="col">'  + "Quantity" + '</th>'
            + '<th scope="col">' + "Actions" + '</th>'
            + '</tr>';
    }
    else {
            head = '<tr>'
            + '<th scope="col">' + "ID" + '</th>'
            + '<th scope="col">' + "Barcode" + '</th>'
            + '<th scope="col">'  + "Name" + '</th>'
            + '<th scope="col">'  + "Quantity" + '</th>'
            + '</tr>';
    }
    $thead.append(head);
	var $tbody = $('#inventory-table').find('tbody');
	var table = $('#inventory-table').DataTable();
    if(table != null) {
        table.clear().destroy();
    }
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button type="button" class="btn btn-warning" onclick="displayEditInventory(' + e.id + ')"> <i class="fa fa-pencil-square" aria-hidden="true"></i> </button>'
		if(getRole() == "operator") {
            var row = '<tr>'
                    + '<td>' + e.id + '</td>'
                    + '<td>' + e.barcode + '</td>'
                    + '<td>' + e.name + '</td>'
                    + '<td>'  + e.quantity + '</td>'
                    + '</tr>';
        }
        else{
            var row = '<tr>'
            + '<td>' + e.id + '</td>'
            + '<td>' + e.barcode + '</td>'
            + '<td>' + e.name + '</td>'
            + '<td>'  + e.quantity + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';

            }
        $tbody.append(row);
	}
	if(getRole() == "operator") {
        $('#inventory-table').DataTable({
            stateSave: true,
            bAutoWidth: false,
            aoColumns : [
                { sWidth: '10%' },
                { sWidth: '20%' },
                { sWidth: '35%' },
                { sWidth: '35%' },
            ]
        });
    }
    else{
        $('#inventory-table').DataTable({
            stateSave: true,
            bAutoWidth: false,
            aoColumns : [
                { sWidth: '10%' },
                { sWidth: '20%' },
                { sWidth: '30%' },
                { sWidth: '30%' },
                { sWidth: '10%' },
            ]
        });
    }
    if(reload == true) {
        $('#inventory-table').DataTable().order([[0, 'asc']]);
        $('#inventory-table').DataTable().page('first').draw('page');
        reload = false;
    }
}

function displayEditInventory(id){
	var url = getInventoryUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayInventory(data);   
	   },
	   error: handleAjaxError
	});	
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#inventoryFile');
	$file.val('');
	$('#inventoryFileName').html("Choose File");
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
	var $file = $('#inventoryFile');
	var fileName = $file.val();
    var f=fileName.split("\\");
    if(String(f).split(".")[1]!="tsv"){
        error("Upload file should be tsv");
        displayUploadData();
        return;
    }
    $('#inventoryFileName').html(f[f.length-1]);
    $('#process-data').attr("disabled",false);
}

function displayUploadData(){
    $("#download-errors").attr("disabled",true);
    $('#process-data').attr("disabled",true);
 	resetUploadDialog(); 	
	closeDivs();
    document.getElementById("upload-div").style.display="table";
}

function displayAddInventory(){
	$("#inventory-add-form input[name=barcode]").val("");
	$("#inventory-add-form input[name=quantity]").val("");
	closeDivs();
    document.getElementById("add-div").style.display="table";
    $("#inventory-add-form input[name=barcode]").val("").focus();
}

function displayInventory(data){
	$("#inventory-edit-form input[name=barcode]").val(data.barcode);
	$("#inventory-edit-form input[name=quantity]").val(data.quantity);
	$("#inventory-edit-form input[name=id]").val(data.id);	
	closeDivs();
    document.getElementById("edit-div").style.display="table";
    $("#inventory-edit-form input[name=quantity]").val(data.quantity).focus();
}

function validateInventory(data) {
	json = JSON.parse(data);
	if(isBlank(json.barcode)) {
		error("Barcode field must not be empty");
		return false;
	}
	if(isBlank(json.quantity)) {
        error("Quantity field must not be empty");
        return false;
    }
    else if(isNaN(parseInt(json.quantity)) || !isInt(json.quantity)){
        error("Quantity field must be an integer value: "+ json.quantity);
        return false;
    }
    if(parseInt(json.quantity)<0){
        error("Quantity cannot be negative");
        return false;
    }
	return true;
}

//INITIALIZATION CODE
function init(){
    $('#show-add-inventory').click(displayAddInventory);
	$('#add-inventory').click(addInventory);
	$('#update-inventory').click(updateInventory);
	$('#refresh-data').click(getInventoryList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#inventoryFile').on('change', updateFileName);
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
$(document).ready(init);
$(document).ready(getInventoryList);

