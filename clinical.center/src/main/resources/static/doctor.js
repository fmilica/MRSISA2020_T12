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
	
	// pretplata svih elemenata tabele na dogadjaj
	$('body').on('click', 'button.table-button', function() {
		// prikaz profila za odabranog pacijenta
		var secNum = $(this).attr('id')
		viewPatientProfile(secNum)
	});
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

function viewPatientProfile(secNum){
	$.ajax({
		type : "POST",
		async: false,
		url : "../../theGoodShepherd/patient/viewProfile/" + secNum,
		dataType: "json",
		success : function(data)  {
			$('.content').hide()
	        $('.patient-profile').show()
	        $("#fullName").text(data.name + " " + data.surname)
	        $("#email").text(data.email)
	        $("#gender").text(data.gender)
	        $("#dateOfBirth").text(data.dateOfBirth)
	        $("#phoneNumber").text(data.phoneNumber)
	        $("#securityNumber").text(data.securityNumber)
	        $("#address").text(data.address + ", " + data.city + ", " + data.country)
	        if(data.medicalRecords == null){
		        $('#medicalReport').hide()
		        $('h5').hide()
		        $("#generalReport").text("You dont have access to patients medical record.")
	        }else{
	        	$('#medicalReport').show()
	        	$('h5').show()
	        	$("#generalReport").text("General Report")
	        	$("#height").text(data.medicalRecords.height)
	        	$("#weight").text(data.medicalRecords.weight)
	        	$("#bloodPressure").text(data.medicalRecords.bloodPressure)
	        	$("#bloodType").text(data.medicalRecords.bloodType)
	        	$("#allergies").text(data.medicalRecords.allergies)
	        }
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}
