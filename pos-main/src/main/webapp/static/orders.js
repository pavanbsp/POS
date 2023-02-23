function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orders";
}
function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}

function getProductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}

function getOrderItemsUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderitems";
}

var role;

function getRole(){
	var r = $("meta[name=role]").attr("content");
    document.getElementById("buttons").style.display="block";
	role = r;
}

var OrderItemList = [];

//BUTTON ACTIONS
function addOrder(event){
	//Set the values to update
    var json = JSON.stringify(OrderItemList);
	var url = getOrderUrl();
    if(OrderItemList.length == 0) {
        error("Cannot place empty orders");
        return;
    }
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	        deleteAll();
	        getOrderList();
            displayEditOrder(response.id);
            success("Order placed successfully");
            add = true;
	   },
	   error: handleAjaxError
	});
	return false;
}

function getOrderList(){
	var url = getOrderUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderList(data);
	   },
	   error: handleAjaxError
	});
}

function displayEditOrder(id){
    $('html').animate({
            scrollTop: $("#view-order-div").offset().top},
    'slow');
	var url = getOrderUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	        curOrder = id;
	   		displayOrder(data);
	   },
	   error: function() {
	        getOrderList();
	        closeDivs();
	        success("Empty order deleted");
	   }
	});
	replace("add-order-div","view-order-div");
}


function displayOrder(data){
        document.getElementById("orderNo").innerHTML = "Order no : " + data.id;
        document.getElementById("add-order-item-existing").style.display ='none';
    	$("#view-order-div input[name=orderId]").val(data.id);
    	if(data.status != "Not Invoiced") {
            $('#add-order-item-existing').attr('disabled',true);
    	}
    	else {
    	    $('#add-order-item-existing').attr('disabled',false);
    	    document.getElementById("add-order-item-existing").style.display ='block';
    	}
    	getOrderItemsList(data.id, data.status);
}
//UI DISPLAY METHODS

function displayOrderList(data){
	var $tbody = $('#orders-table').find('tbody');
	var table = $('#orders-table').DataTable();
    if(table != null) {
        table.clear().destroy();
    }
	$tbody.empty();
	for(var i in data){
		var e = data[i];
        time = e['time'].split('T').join(" ")
        var buttonHtml;
		if(e.status == "Not Invoiced") {
		    buttonHtml = '<button class="btn btn-sm btn-warning" onclick="displayEditOrder(' + e.id + ')"><i class="fa fa-pencil-square" aria-hidden="true"></i></button>  '
		}
		else{
		    buttonHtml = '<button class="btn btn-sm btn-primary" onclick="displayEditOrder(' + e.id + ')"><i class="fa fa-eye" aria-hidden="true"></i></button>'
		}
		buttonHtml += '<button class="btn btn-sm btn-success" style="margin-left:10px;"  onclick="downloadPDF('+ e.id +')" ><i class="fa fa-file-invoice" aria-hidden="true"></i></button>';
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + time + '</td>';
		if(e.status == "Not Invoiced") {
		    row += '<td>' + "Pending" + '</td>';
		}
		else {
		    row += '<td>' + "Confirmed" + '</td>';
		}
		row += '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
    if(add == true){
        $('#orders-table').DataTable({
            order: [[0, 'asc']],
            bAutoWidth: false,
            order: [[0, 'asc']],
            aoColumns : [
                { sWidth: '5%' },
                { sWidth: '40%' },
                { sWidth: '30%' },
                { sWidth: '25%' },
            ]
        });
        $('#orders-table').DataTable().page('last').draw('page');
        add = false;
        return;
    }
    if(reload == true) {
         $('#orders-table').DataTable({
    	    order: [[0, 'asc']],
    	    bAutoWidth: false,
    	    order: [[0, 'asc']],
            aoColumns : [
                { sWidth: '5%' },
                { sWidth: '40%' },
                { sWidth: '30%' },
                { sWidth: '25%' },
            ]
    	});
        reload = false;
        return
    }
	$('#orders-table').DataTable({
	    stateSave: true,
	    bAutoWidth: false,
	    order: [[0, 'asc']],
        aoColumns : [
            { sWidth: '5%' },
            { sWidth: '40%' },
            { sWidth: '30%' },
            { sWidth: '25%' },
        ]
	});
}


//----------------------------------------------------------------------------------------------

function deleteOrderItemsInitial(id){
    OrderItemList.splice(id,1);
    displayOrderItemsListInitial();
}

function deleteAll(){

    OrderItemList = [];
    displayOrderItemsListInitial();
}

function updateOrderItemInitial() {
    var $form = $("#orderItemInitial-edit-form");
    var json = toJson($form);
    const myObj = JSON.parse(json);
    if(!validateOrderItem(json)) {
        return false;
    }
    var url = getInventoryUrl() + "/barcode/" + myObj.barcode;
        $.ajax({
           url: url,
           type: 'GET',
           headers: {
                'Content-Type': 'application/json'
              },
           success: function(quantity) {
                getProductUpdate(quantity);
           },
           error: function(){
                getProduct(-1);
           }
        });
}

function getProductUpdate(quantity) {
    var $form = $("#orderItemInitial-edit-form");
    var json = toJson($form);
    const myObj = JSON.parse(json);
    var url = getProductUrl() + "/barcode/" + myObj.barcode;
        $.ajax({
           url: url,
           type: 'GET',
           headers: {
                'Content-Type': 'application/json'
              },
           success: function(data) {
                callbackUpdate(quantity, data);
           },
           error: function(){
                callbackUpdate(quantity, -1);
           }
        });
}

function callbackUpdate(quantityAvailable, data) {
    var $form = $("#orderItemInitial-edit-form");
	var json = toJson($form);
	const myObj = JSON.parse(json);
    var ind = checkIfAlreadyPresent(JSON.parse(json).barcode);
    if(parseFloat(JSON.parse(json).sellingPrice) > data.mrp) {
        error("Selling price should be less than MRP");
        return;
    }
    OrderItemList[ind].sellingPrice = $("#orderItemInitial-edit-form input[name=sellingPrice]").val();

    var qty = parseInt(JSON.parse(json).quantity);
    if(quantityAvailable == -1){
        error("Product with given Barcode does not exists ");
    }
    else if(quantityAvailable >= qty){
        OrderItemList[ind].quantity = qty;
        success("Order item updated successfully");
        displayOrderItemsListInitial();
        displayEditOrderItemInitialModal(data);
    }
    else {
        error("Only few products are available, available products = " + quantityAvailable);
    }
}

function addOrderItemsInitial(event){
	var $form = $("#orderItemInitial-add-form");
	var json = toJson($form);
	const myObj = JSON.parse(json);
	if(!validateOrderItem(json)) {
	    return false;
	}
	var url = getInventoryUrl() + "/barcode/" + myObj.barcode;
        $.ajax({
           url: url,
           type: 'GET',
           headers: {
                'Content-Type': 'application/json'
              },
           success: function(quantity) {
                getProduct(quantity);
                $('#order-item-table-initial').DataTable().page('last').draw('page');
           },
           error: function(){
                getProduct(-1);
           }
        });
}

function getProduct(quantity) {
    var $form = $("#orderItemInitial-add-form");
    var json = toJson($form);
    const myObj = JSON.parse(json);
    var url = getProductUrl() + "/barcode/" + myObj.barcode;
        $.ajax({
           url: url,
           type: 'GET',
           headers: {
                'Content-Type': 'application/json'
              },
           success: function(data) {
                callback(quantity, data);
           },
           error: function(){
                callback(quantity, -1);
           }
        });
}

function callback(quantityAvailable, data) {
    var $form = $("#orderItemInitial-add-form");
	var json = toJson($form);
	const myObj = JSON.parse(json);
    var ind = checkIfAlreadyPresent(JSON.parse(json).barcode);
    if(parseFloat(JSON.parse(json).sellingPrice) > data.mrp) {
        error("Selling price should be less than MRP");
        return;
    }
    if(ind == -1) {
        if(quantityAvailable >= parseInt(JSON.parse(json).quantity)) {
                OrderItemList.push(myObj);
                displayOrderItemsListInitial();
                displayAddOrderItemInitialModal();
                success("Order item added successfully");
        }
        else {
                if(quantityAvailable == -1){
                    error("Product with given Barcode does not exists ");
                }
                else{
                    error("Only few products are available, available products = " + quantityAvailable);
                }
         }
    }
    else {
            var qty = parseInt(OrderItemList[ind].quantity) + parseInt(JSON.parse(json).quantity);
            var sellingPriceInitial = parseFloat(OrderItemList[ind].sellingPrice);
            if(parseFloat(JSON.parse(json).sellingPrice) != sellingPriceInitial) {
                error("Item already exists with different selling price, you can edit the item here");
                $('#add-orderItemInitial-modal').modal('toggle');
                displayEditOrderItemInitial(ind);
                return;
            }
            if(quantityAvailable >= qty){
                OrderItemList[ind].quantity = qty;
                success("Order exists, and updated successfully");
                displayOrderItemsListInitial();
                displayAddOrderItemInitialModal();
            }
            else {
                if(quantityAvailable == -1){
                    error("Product with given Barcode does not exists ");
                }
                else{
                    error("Only few products are available, available products = " + Math.max(0,parseInt(quantityAvailable-parseInt(OrderItemList[ind].quantity))));
                }
            }
    }
}

function getOrderItemsList(id, status){
	var url = getOrderItemsUrl() + "/order/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderItemsList(data, status);
	   },
	   error: handleAjaxError
	});
}

function addOrderItems(event){
	var $form = $("#orderItem-add-form");
	var json = toJson($form);
	const myObj = JSON.parse(json);
    var id = myObj.orderId;
	var url = getOrderItemsUrl();
	if(!validateOrderItem(json)) {
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
	   		displayEditOrder(id);
	   		displayAddOrderItemModal();
	   		 $('#order-item-table').DataTable().page('last').draw('page');
            success("Order item added successfully");
	   },
	   error: function(response){
            var errorData = JSON.parse(response.responseText);
            console.log(errorData);
            if(errorData.message == "Order Item already exists with different selling price") {
                error(errorData.message + ", you can edit the item here");
                displayAddOrderItemModal();
                getOrderItemModalByBarcode(id, myObj.barcode);
            }
            else{
                handleAjaxError(response);
            }
       }
       	});
       	return false;
       }

function getOrderItemModalByBarcode(id, barcode) {
    var url = getOrderItemsUrl() + "/order/" + id;
    $.ajax({
      url: url,
      type: 'GET',
      success: function(data) {
           for(var i in data){
               var e = data[i];
               if(e.barcode == barcode) {
                   displayEditOrderItems(e.id);
                    return;
               }
           }
      },
      error: handleAjaxError
    });
}

function updateOrderItem(event){
	//Get the ID
	var id = $("#orderItem-edit-form input[name=id]").val();
	var url = getOrderItemsUrl() + "/" + id;
    var order_id = $("#orderItem-edit-form input[name=orderId]").val();
	//Set the values to update
	var $form = $("#orderItem-edit-form");
	var json = toJson($form);
    if(!validateOrderItem(json)) {
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
	        success("Order item updated successfully");
	        $('#edit-orderItem-modal').modal('toggle');
	   		displayEditOrder(order_id);
	   },
	   error: handleAjaxError
	});

	return false;
}

function deleteOrderItems(id, orderId){
    if(order_items == 1) {
        var x =  confirm("Are you sure you want to delete? Deleting order_item will delete the entire order completely");
        if(x == false) return;
    }
	var url = getOrderItemsUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		displayEditOrder(orderId);
	   		success("Order Item deleted successfully");
	   },
	   error: handleAjaxError
	});
}

function displayEditOrderItems(id){
	var url = getOrderItemsUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderItem(data);
	   },
	   error: handleAjaxError
	});
}

function displayEditOrderItemInitial(srlNo) {
    displayEditOrderItemInitialModal(OrderItemList[srlNo]);
}


function displayOrderItemsListInitial(){
        replace("view-order-div","add-order-div");
        var data = OrderItemList;
        var $tbody = $('#order-item-table-initial').find('tbody');
    	$tbody.empty();
    	var srlNo = 0;
    	var table = $('#order-item-table-initial').DataTable();
        if(table != null) {
            table.clear().destroy();
        }
    	for(var i in data){
    	    srlNo++;
    		var e = data[i];
    		var buttonHtml = '<button type="button" class="btn btn-warning" onclick="displayEditOrderItemInitial(' + parseInt(srlNo-1) + ')"><i class="fa fa-pencil-square" aria-hidden="true"></i></button>'
            buttonHtml += ' <button type="button" class="btn btn-danger" onclick="deleteOrderItemsInitial(' + i + ')"><i class="fa fa-trash" aria-hidden="true"></i></button>'
    		var row = '<tr>'
    		+ '<td>' + srlNo + '</td>'
    		+ '<td>' + e.barcode + '</td>'
    		+ '<td>' + e.quantity + '</td>'
    		+ '<td>' + (e.quantity*e.sellingPrice).toFixed(2) + '</td>'
    		+ '<td style="display: block ruby";>' + buttonHtml + '</td>'
    		+ '</tr>';
            $tbody.append(row);
    	}
    	$('#order-item-table-initial').DataTable({
    	    stateSave: true,
    	});
}

var order_items;
function displayOrderItemsList(data, status){
    var $tbody = $('#order-item-table').find('tbody');
    var table = $('#order-item-table').DataTable();
    if(table != null) {
        table.clear().destroy();
    }
	$tbody.empty();
	var srlNo = 0;
	for(var i in data){
	    srlNo++;
		var e = data[i];
		var buttonHtml = ' <button type="button" class="btn btn-warning" onclick="displayEditOrderItems(' + e.id + ')"><i class="fa fa-pencil-square" aria-hidden="true"></i></button>';
		buttonHtml += '  <button type="button" class="btn btn-danger" onclick="deleteOrderItems(' + e.id + ',' + e.orderId + ')"><i class="fa fa-trash" aria-hidden="true"></i></button>';
		var row = '<tr>'
		+ '<td>' + srlNo + '</td>'
		+ '<td>' + e.productName + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + (e.quantity*e.sellingPrice).toFixed(2) + '</td>'
		+ '<td style="display: block ruby;">' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	order_items = srlNo;
	if(status == "Not Invoiced") {
	    $('#order-item-table').DataTable({
	    });
	}
	else {
	    $('#order-item-table').DataTable({
            columnDefs: [
                   {
                        target: 5,
                        visible: false,
                        searchable: false,
                   },
            ],
            });
	}
}

function checkIfAlreadyPresent(barcode) {
	for(var i in OrderItemList) {
		var e = OrderItemList[i];
		if(e.barcode.localeCompare(barcode) == 0){
			return i;
		}
	}
	return -1;
}

function displayAddOrderItemModal(){
    	$("#orderItem-add-form input[name=id]").val("");
    	$("#orderItem-add-form input[name=orderId]").val($("#view-order-div input[name=orderId]").val());
    	$("#orderItem-add-form input[name=barcode]").val("");
    	$("#orderItem-add-form input[name=sellingPrice]").val("");
    	$("#orderItem-add-form input[name=quantity]").val("");
    	document.getElementById('quantityAvailableAdd').style.display = "none";
    	document.getElementById('mrpAdd').style.display = "none";
    	document.getElementById('nameAdd').style.display = "none";
    	$('#add-orderItem-modal').modal('toggle');
    	$("#orderItem-add-form input[name=barcode]").val("").focus();
}

function displayAddOrderItemInitialModal(){
        $("#orderItemInitial-add-form input[name=barcode]").val("");
    	$("#orderItemInitial-add-form input[name=id]").val("");
    	$("#orderItemInitial-add-form input[name=orderId]").val("");
    	$("#orderItemInitial-add-form input[name=sellingPrice]").val("");
    	$("#orderItemInitial-add-form input[name=quantity]").val("");
    	document.getElementById('quantityAvailable').style.display = "none";
    	document.getElementById('mrp').style.display = "none";
    	document.getElementById('nameAddInitial').style.display = "none";
    	$('#add-orderItemInitial-modal').modal('toggle');
    	$("#orderItemInitial-add-form input[name=barcode]").val("").focus();
}

function displayEditOrderItemInitialModal(data){
        $("#orderItemInitial-edit-form input[name=barcode]").val(data.barcode);
    	$("#orderItemInitial-edit-form input[name=id]").val("");
    	$("#orderItemInitial-edit-form input[name=orderId]").val("");
    	$("#orderItemInitial-edit-form input[name=sellingPrice]").val(data.sellingPrice);
    	$("#orderItemInitial-edit-form input[name=quantity]").val(data.quantity);
    	updateAttributesEditInitial();
    	$('#edit-orderItemInitial-modal').modal('toggle');
    	$("#orderItemInitial-edit-form input[name=quantity]").val(data.quantity).focus();
}

function displayOrderItem(data){
    	$("#orderItem-edit-form input[name=id]").val(data.id);
    	$("#orderItem-edit-form input[name=orderId]").val(data.orderId);
    	$("#orderItem-edit-form input[name=barcode]").val(data.barcode);
    	$("#orderItem-edit-form input[name=sellingPrice]").val(data.sellingPrice);
    	$("#orderItem-edit-form input[name=quantity]").val(data.quantity);
    	updateAttributesEdit();
    	$('#edit-orderItem-modal').modal('toggle');
    	$("#orderItem-edit-form input[name=quantity]").val(data.quantity).focus();
}

function validateOrderItem(data) {
	json = JSON.parse(data);
	if(isBlank(json.barcode)) {
		error("Barcode must not be empty");
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
	if(parseInt(json.quantity)<=0) {
		error("Quantity must be positive");
		return false;
	}

	if(isBlank(json.sellingPrice) ) {
        error("Selling price must not be empty");
        return false;
    }
    else if(isNaN((json.sellingPrice))){
        error("Selling price must be a double value: "+json.sp);
                    return false;
    }
    if(parseFloat(json.sellingPrice)<=0) {
        error("Selling price must be positive");
        return false;
    }
	return true;
}

//------------------------------------------------------------------------------------------------
var curOrder = -1;

function downloadPDF(id) {
	var url = getOrderUrl() + "/invoice/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	        toPdf(data, "Invoice_");
	        getOrderList();
	        if(curOrder == id) {
	            displayEditOrder(id);
	        }
            success("Pdf downloaded successfully");
	   },
	   error: function(response){
	   		handleAjaxError(response);
	   }
	});
}

//-------------------------------------------------------------------------------
//INITIALIZATION CODE
function init(){
    $('#submit-order').click(addOrder);
	$('#show-add-order').click(displayOrderItemsListInitial);
	$('#add-orderItem').click(addOrderItems);
	$('#add-orderItemInitial').click(addOrderItemsInitial);
	$('#add-order-item').click(displayAddOrderItemInitialModal);
	$('#add-order-item-existing').click(displayAddOrderItemModal);
	$('#update-orderItem').click(updateOrderItem);
	$('#edit-orderItemInitial').click(updateOrderItemInitial);
	$('#delete-order-items').click(deleteAll);
	$('#refresh-data').click(getOrderList);
    $('#close-add-div').click(closeDivs);
    $('#close-view-div').click(closeDivs);
    $("#orderItem-add-form input[name=barcode]").on('change', updateAttributesAdd);
    $("#orderItemInitial-add-form input[name=barcode]").on('change', updateAttributesAddInitial);
}

function replace( hide, show ) {
  document.getElementById(hide).style.display="none";
  document.getElementById(show).style.display="block";
}

function closeDivs() {
    document.getElementById("add-order-div").style.display="none";
    document.getElementById("view-order-div").style.display="none";
}

var reload = true;
var add = false;
$(document).ready(init);
$(document).ready(getRole());
$(document).ready(getOrderList);

//-------------------------------------------------------------------------------------------------------
//update the fields entered after barcode

function updateAttributesAdd() {
    var barcode = $("#orderItem-add-form input[name=barcode]").val();
    if(isBlank(barcode)) {
        document.getElementById('quantityAvailableAdd').style.display = "none";
        document.getElementById('mrpAdd').style.display = "none";
        document.getElementById('nameAdd').style.display = "none";
        return;
    }
    var url = getInventoryUrl() + "/barcode/" + barcode;
    $.ajax({
       url: url,
       type: 'GET',
       headers: {
            'Content-Type': 'application/json'
          },
       success: function(quantity) {
            document.getElementById('quantityAvailableAdd').innerHTML = "Available Quantity = " + quantity;
            document.getElementById('quantityAvailableAdd').style.color = "#32CD32";
            document.getElementById('quantityAvailableAdd').style.display = "block";
       },
       error: function(){
            document.getElementById('quantityAvailableAdd').innerHTML = "Product didn't exist in inventory";
            document.getElementById('quantityAvailableAdd').style.color = "red";
            document.getElementById('quantityAvailableAdd').style.display = "block";
       }
    });
    url = getProductUrl() + "/barcode/" + barcode;
    $.ajax({
       url: url,
       type: 'GET',
       headers: {
            'Content-Type': 'application/json'
          },
       success: function(data) {
            document.getElementById('nameAdd').innerHTML = "Name = " + data.name;
            document.getElementById('nameAdd').style.color = "#32CD32";
            document.getElementById('nameAdd').style.display = "block";

            document.getElementById('mrpAdd').innerHTML = "Mrp = " + data.mrp;
            document.getElementById('mrpAdd').style.color = "#32CD32";
            document.getElementById('mrpAdd').style.display = "block";
       },
       error: function(){
          document.getElementById('nameAdd').style.display = "none";

          document.getElementById('mrpAdd').innerHTML = "Product with given barcode not exists";
          document.getElementById('mrpAdd').style.color = "red";
          document.getElementById('mrpAdd').style.display = "block";
     }
    });
}

function updateAttributesAddInitial() {
    var barcode = $("#orderItemInitial-add-form input[name=barcode]").val();
    if(isBlank(barcode)) {
        document.getElementById('quantityAvailable').style.display = "none";
        document.getElementById('mrp').style.display = "none";
        document.getElementById('nameAddInitial').style.display = "none";
        return;
    }
    var url = getInventoryUrl() + "/barcode/" + barcode;
    $.ajax({
       url: url,
       type: 'GET',
       headers: {
            'Content-Type': 'application/json'
          },
       success: function(quantity) {
            var ind = checkIfAlreadyPresent(barcode);
            if(ind != -1) {
                quantity = parseInt(quantity-parseInt(OrderItemList[ind].quantity));
                $("#orderItemInitial-add-form input[name=sellingPrice]").val(OrderItemList[ind].sellingPrice);
            }
            if(quantity < 0) {
                quantity = 0;
            }
            document.getElementById('quantityAvailable').innerHTML = "Available Quantity = " + quantity;
            document.getElementById('quantityAvailable').style.color = "#32CD32";
            document.getElementById('quantityAvailable').style.display = "block";
       },
       error: function(){
            document.getElementById('quantityAvailable').innerHTML = "Product didn't exist in inventory";
            document.getElementById('quantityAvailable').style.color = "red";
            document.getElementById('quantityAvailable').style.display = "block";
       }
    });
    url = getProductUrl() + "/barcode/" + barcode;
    $.ajax({
       url: url,
       type: 'GET',
       headers: {
            'Content-Type': 'application/json'
          },
       success: function(data) {
           document.getElementById('nameAddInitial').innerHTML = "Name = " + data.name;
           document.getElementById('nameAddInitial').style.color = "#32CD32";
           document.getElementById('nameAddInitial').style.display = "block";

            document.getElementById('mrp').innerHTML = "Mrp = " + data.mrp;
            document.getElementById('mrp').style.color = "#32CD32";
            document.getElementById('mrp').style.display = "block";

       },
       error: function(){
            document.getElementById('nameAddInitial').style.display = "none";

          document.getElementById('mrp').innerHTML = "Product with given barcode not exists";
          document.getElementById('mrp').style.color = "red";
          document.getElementById('mrp').style.display = "block";
     }
    });
}

function updateAttributesEditInitial() {
    var barcode = $("#orderItemInitial-edit-form input[name=barcode]").val();
    var url = getInventoryUrl() + "/barcode/" + barcode;
    $.ajax({
       url: url,
       type: 'GET',
       headers: {
            'Content-Type': 'application/json'
          },
       success: function(quantity) {
            var ind = checkIfAlreadyPresent(barcode);
            if(ind != -1) {
                quantity = parseInt(quantity-parseInt(OrderItemList[ind].quantity));
            }
            if(quantity < 0) {
                quantity = 0;
            }
            document.getElementById('quantityAvailableEditInitial').innerHTML = "Available Quantity = " + quantity;
            document.getElementById('quantityAvailableEditInitial').style.color = "#32CD32";
            document.getElementById('quantityAvailableEditInitial').style.display = "block";
       },
       error: function(){
            document.getElementById('quantityAvailableEditInitial').innerHTML = "Product didn't exist in inventory";
            document.getElementById('quantityAvailableEditInitial').style.color = "red";
            document.getElementById('quantityAvailableEditInitial').style.display = "block";
       }
    });
    url = getProductUrl() + "/barcode/" + barcode;
    $.ajax({
       url: url,
       type: 'GET',
       headers: {
            'Content-Type': 'application/json'
          },
       success: function(data) {
            document.getElementById('nameEditInitial').innerHTML = "Name = " + data.name;
            document.getElementById('nameEditInitial').style.color = "#32CD32";
            document.getElementById('nameEditInitial').style.display = "block";

            document.getElementById('mrpEditInitial').innerHTML = "Mrp = " + data.mrp;
            document.getElementById('mrpEditInitial').style.color = "#32CD32";
            document.getElementById('mrpEditInitial').style.display = "block";
       },
       error: function(){
          document.getElementById('mrpEditInitial').innerHTML = "Product with given barcode not exists";
          document.getElementById('mrpEditInitial').style.color = "red";
          document.getElementById('mrpEditInitial').style.display = "block";
     }
    });
}

function updateAttributesEdit() {
    var barcode = $("#orderItem-edit-form input[name=barcode]").val();
    var url = getInventoryUrl() + "/barcode/" + barcode;
    $.ajax({
       url: url,
       type: 'GET',
       headers: {
            'Content-Type': 'application/json'
          },
       success: function(quantity) {
            document.getElementById('quantityAvailableEdit').innerHTML = "Available Quantity = " + quantity;
            document.getElementById('quantityAvailableEdit').style.color = "#32CD32";
            document.getElementById('quantityAvailableEdit').style.display = "block";
       },
       error: function(){
            document.getElementById('quantityAvailableEdit').innerHTML = "Product didn't exist in inventory";
            document.getElementById('quantityAvailableEdit').style.color = "red";
            document.getElementById('quantityAvailableEdit').style.display = "block";
       }
    });
    url = getProductUrl() + "/barcode/" + barcode;
    $.ajax({
       url: url,
       type: 'GET',
       headers: {
            'Content-Type': 'application/json'
          },
       success: function(data) {
           document.getElementById('nameEdit').innerHTML = "Name = " + data.name;
           document.getElementById('nameEdit').style.color = "#32CD32";
           document.getElementById('nameEdit').style.display = "block";

            document.getElementById('mrpEdit').innerHTML = "Mrp = " + data.mrp;
            document.getElementById('mrpEdit').style.color = "#32CD32";
            document.getElementById('mrpEdit').style.display = "block";
       },
       error: function(){
          document.getElementById('mrpEdit').innerHTML = "Product with given barcode not exists";
          document.getElementById('mrpEdit').style.color = "red";
          document.getElementById('mrpEdit').style.display = "block";
     }
    });
}