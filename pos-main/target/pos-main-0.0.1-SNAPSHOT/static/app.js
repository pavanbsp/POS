
//HELPER METHOD
function toJson($form){
    var serialized = $form.serializeArray();
    console.log(serialized);
    var s = '';
    var data = {};
    for(s in serialized){
        data[serialized[s]['name']] = serialized[s]['value']
    }
    var json = JSON.stringify(data);
    return json;
}


function handleAjaxError(response){
	var response = JSON.parse(response.responseText);
	toastr.options.timeOut=30000;
    toastr.error(response.message);
}

function readFileData(file, callback){
	var config = {
		header: true,
		delimiter: "\t",
		skipEmptyLines: "greedy",
		complete: function(results) {
			callback(results);
	  	}	
	}
	Papa.parse(file, config);
}


function writeFileData(arr){
	var config = {
		quoteChar: '',
		escapeChar: '',
		delimiter: "\t"
	};
	
	var data = Papa.unparse(arr, config);
    var blob = new Blob([data], {type: 'text/tsv;charset=utf-8;'});
    var fileUrl =  null;

    if (navigator.msSaveBlob) {
        fileUrl = navigator.msSaveBlob(blob, 'download.tsv');
    } else {
        fileUrl = window.URL.createObjectURL(blob);
    }
    var tempLink = document.createElement('a');
    tempLink.href = fileUrl;
    tempLink.setAttribute('download', 'download.tsv');
    tempLink.click(); 
}
function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

function isInt(n) {
   return n % 1 === 0;
}

function toCSV(data, name) {
    var titleKeys = Object.keys(data[0])

    var refinedData = []
    refinedData.push(titleKeys)

    data.forEach(item => {
      refinedData.push(Object.values(item))
    })

    let csvContent = ''

    refinedData.forEach(row => {
      csvContent += row.join(',') + '\n'
    })
    var blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8,' })
    var link=document.createElement('a');
    link.href=window.URL.createObjectURL(blob);
    link.download= name + new Date() + ".csv";
    link.click();
}

 //data is the base64 encoded string
function base64ToArrayBuffer(base64) {
    var binaryString = window.atob(base64);
    var binaryLen = binaryString.length;
    var bytes = new Uint8Array(binaryLen);
    for (var i = 0; i < binaryLen; i++) {
        var ascii = binaryString.charCodeAt(i);
        bytes[i] = ascii;
    }
    return bytes;
}

function toPdf(data, name){
    var arrrayBuffer = base64ToArrayBuffer(data);
    var blob = new Blob([arrrayBuffer], {type: "application/pdf"});
    var link=document.createElement('a');
    link.href=window.URL.createObjectURL(blob);
    link.download = name + new Date() + ".pdf";
    link.click();
}

function error(msg) {
    toastr.options.timeOut=30000;
    toastr.error(msg);
}

function success(msg) {
    toastr.options.timeOut=5000;
    toastr.success(msg);
}

toastr.options = {
  "closeButton": true,
  "debug": false,
  "progressBar": true,
  "positionClass": "toast-top-right",
  "showDuration": "none",
  "hideDuration": "none",
  "timeOut": "none",
  "extendedTimeOut": "none",
  "showEasing": "swing",
  "hideEasing": "linear",
  "showMethod": "fadeIn",
  "hideMethod": "fadeOut",
  "font-size":	"15px"
};

function init(){
    function hoverDropRight() {
        var dropdownContent = document.getElementsByClassName("dropdown-menu")[0];
        dropdownContent.style.display = "block";
    }
    function nohoverDropRight() {
        var dropdownContent = document.getElementsByClassName("dropdown-menu")[0];
        dropdownContent.style.display = "none";
    }
    var dropdown = document.getElementsByClassName("dropdown-btn");
    dropdown[0].addEventListener("mouseover", hoverDropRight);
    dropdown[0].addEventListener("mouseout", nohoverDropRight);

    var dropright = document.getElementsByClassName("dropdown-menu");
    dropright[0].addEventListener("mouseover", hoverDropRight);
    dropright[0].addEventListener("mouseout", nohoverDropRight);
}

$(document).ready(init);