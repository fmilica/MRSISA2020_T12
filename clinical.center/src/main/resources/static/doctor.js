var patientsTable;

function logInDoctor(email, password){
		
	$.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/doctor/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(output)  {
			sessionStorage.setItem('nameSurname', output.name + ' ' + output.surname);
			window.location.href = "html/home-pages/doctor_hp.html"
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}

$(document).ready( function () {
	
	/*View all patients*/
	$('#doctorPatients').click(function(event) {
		event.preventDefault()
		viewAllPatients()
		
	})
	
	/*Filter patients*/
	$('#filterPatients').click(function(e) {
		e.preventDefault()
		filterPatients()
	})
})


function viewAllPatients(){
	// inicijalizujemo je ako vec nismo
	if (!$.fn.DataTable.isDataTable('#patientsTable')) {
		patientsTable = $('#patientsTable').DataTable({
			ajax: {
				url: "../../theGoodShepherd/patient",
				dataSrc: ''
			},
			columns: [
				{ data: 'name'},
				{ data: 'surname'},
				{ data: 'securityNumber'},
				{
					data: null,
					render: function (data) {
						var button = '<button id="'+data.securityNumber+'" class="btn btn-info table-button" name="'+data.securityNumber+'">View profile</button>';
					  	return button;
					}
				}
				]
		})
	}
}

function filterPatients(){
	var nameV = $('#patientName').val()
	var surnameV = $('#patientSurname').val()
	var secNumV = $('#patientSecurityNumber').val()
	
	patientsTable.ajax.url("../../theGoodShepherd/patient/filterPatients?name=" + nameV + "&surname=" + surnameV + "&securityNumber=" + secNumV)
	patientsTable.ajax.reload()
	
	if (isNaN(secNumV)) {
		alert("Security number must be a number!")
		return
	}

}
