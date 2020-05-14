var patientsTable;
var medicalReportTable;
var appointment_id;
var medicalRecord_id;
var current_secNum;

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
		current_secNum = secNum
		viewPatientProfile(secNum)
	});
	
	/*Show form to create medical report*/
	$('#startAppointment').click(function(e){
		e.preventDefault()
		
		if(appointment_id == null){
			alert("There's no current appointment for this patient!")
			return
		}
		
		$('#prescriptionMR').select2();
		$.ajax({
			type : "GET",
			async: false,
			url : "../../theGoodShepherd/diagnosis",
			dataType : "json",
			success : function(data)  {
				$('#diagnosisMR').empty();
				$.each(data, function(index, diagnose){
					$('#diagnosisMR').append(new Option(diagnose.name));
				})
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
		$.ajax({
			type : "GET",
			async: false,
			url : "../../theGoodShepherd/prescription",
			dataType : "json",
			success : function(data)  {
				$.each(data, function(index, medicine){
					$('#prescriptionMR').empty();
					$('#prescriptionMR').append(new Option(medicine.name));
				})
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})

		$('.doctor-patient-profile').hide()
		$('.writeMedicalReport').show()
		// scroll to top of page
		document.body.scrollTop = 0
		document.documentElement.scrollTop = 0
	})
	
	/*Adding new medical report*/
	$("#write_report").on('click', function(event){
		
		event.preventDefault()
		
		var description = $("#descriptionMR").val()
		var diagnosis = $("#diagnosisMR").val()
		var presc = $('#prescriptionMR').val()

		if (!description || !diagnosis) {
			alert("Not all required fields are filled!")
			return
		}

		var prescription = []
		for (var i = 0; i < presc.length; i++) {
			prescription.push(parseInt(presc[i]))
		}

		var medRec = {
			appointment: {
				id : appointment_id
			},
			description: description,
			diagnosis: {
				name : diagnosis
			},
			prescription: prescription
		}

		$.ajax({
			type : "POST",
			async: false,
			url : "../../theGoodShepherd/medicalReport/addNewMedicalReport",
			contentType : "application/json",
			//dataType : "json",
			data : JSON.stringify(medRec),
			success : function()  {
				alert("Successfully finished appointment!")
				$('.content').hide()
				$('.doctor-patient-profile').show()
				patientsTable.ajax.reload()
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
	})
	
	/*Canceling appointment report?*/
	$('#cancel_report').click(function(e){
		e.preventDefault()
		$('.content').hide()
		$('.doctor-patient-profile').show()
		$("#descriptionMR").val('')
		$("#diagnosisMR").val('')
		$('#prescriptionMR').val(null).trigger('change')
	})
	
	/*Show form form modifying medical record and fill it in*/
	$('#changeRecord').click(function(e){
		e.preventDefault()
		$('.doctor-patient-profile').hide()
		$('.modify-medical-record').show()
		
		if(appointment_id == null){
			alert("You can only change patient's medical record during an appointment!")
			return
		}
		
		$.ajax({
			type : "POST",
			async: false,
			url : "../../theGoodShepherd/medicalRecord/" + medicalRecord_id,
			dataType : "json",
			success : function(data)  {
				$('#heightMR').val(data.height)
				$('#weightMR').val(data.weight)
				$('#bloodPressureMR').val(data.bloodPressure)
				$('#bloodTypeMR').val(data.bloodType)
				$('#alergiesMR').val(data.allergies)
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
		
	})
	
	/*Modify medical record*/
	$('#modify_record').on('click', function(event){
		
		event.preventDefault()
		
		var height = $('#heightMR').val()
		var weight = $('#weightMR').val()
		var bloodPressure = $('#bloodPressureMR').val()
		var bloodType = $('#bloodTypeMR').val()
		var alergies = $('#alergiesMR').val()
		
		if (!height || !weight || !bloodPressure || !bloodType || !alergies) {
			alert("Not all required fields are filled!")
			return
		}
		
		var medRec = {
				id: medicalRecord_id,
				height: height,
				weight: weight,
				bloodPressure: bloodPressure,
				bloodType: bloodType,
				allergies: alergies
		}
		
		$.ajax({
			type : "POST",
			async: false,
			url : "../../theGoodShepherd/medicalRecord/modifyMedicalRecord",
			contentType : "application/json",
			data : JSON.stringify(medRec),
			success : function()  {
				alert("Successfully modified medical record!")
				$('.content').hide()
				$('.doctor-patient-profile').show()
				viewPatientProfile(current_secNum)
				patientsTable.ajax.reload()
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
	})
	
	//cancel modifying medical record
	$('#cancel_record').click(function(e){
		e.preventDefault()
		$('.content').hide()
		$('.doctor-patient-profile').show()
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

function viewPatientProfile(secNum) {
	$.ajax({
		type : "POST",
		async: false,
		url : "../../theGoodShepherd/patient/viewProfile/" + secNum,
		dataType: "json",
		success : function(data)  {
			console.log(data)
			medicalRecord_id = data.medicalRecords.id
			viewMedicalReports(data)
			appointment_id = data.appointment.id
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}

function viewMedicalReports(data){
	$('.content').hide()
    $('.doctor-patient-profile').show()
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
		$("#generalReport").text("")
    	$("#height").text(data.medicalRecords.height)
    	$("#weight").text(data.medicalRecords.weight)
    	$("#bloodPressure").text(data.medicalRecords.bloodPressure)
    	$("#bloodType").text(data.medicalRecords.bloodType)
    	$("#allergies").text(data.medicalRecords.allergies)
    	if (!$.fn.DataTable.isDataTable('#medicalReports')) {
    		medicalReportTable = $('#medicalReports').DataTable({
				data: data.medicalRecords.medicalReports,
    			columns: [
    				{ data: 'description'},
					{ data: 'diagnosisName'},
					{
						data: null,
						render: function (data) {
							var allMedicine = ""
							for (var i = 0; i < data.prescriptionMedicines.length; i++) {
								allMedicine += data.prescriptionMedicines[i]
								if (i != data.prescriptionMedicines.length - 1) {
									allMedicine += ", "
								}
							}
							return allMedicine
						}
					}]
    		})
    	} else {
			medicalReportTable.clear().rows.add(data).draw();
		}
	}
}
