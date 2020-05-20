var patientsTable;
var medicalReportTable;
var appointment_id;
var medicalRecord_id;
var current_secNum;
var diagnosis = [];

// ako su personalni podaci editovani, ponovo saljemo upit serveru
var edited = false;

$(document).ready( function () {

	// postavljanje maksimalnog datuma rodjenja koji se moze odabrati na danas
	document.getElementById("dateOfBirthEdit").max = new Date().toISOString().split("T")[0];

	//select2 radi zbog ovoga
	$.fn.modal.Constructor.prototype.enforceFocus = function() {};

	/*View personal information*/
	$('.d-profile').on('click', function(e){
		e.preventDefault()
		viewPersonalInformation()
	})
	
	/*Edit personal information*/
	$('#editDoctorProfile').click(function(e) {
		e.preventDefault();
		$('.content').hide()
        $('.doctor-edit-profile').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
        editPersonalInformation()
	})
	
	$('#confirmEdit').click(function(e) {
		e.preventDefault()
		saveUpdatedDoctor()
	})

	$('#cancelEdit').click(function(e) {
		e.preventDefault()
		document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
		$('.content').hide()
		$('.doctor-profile').show()
	})
	
	/*Change password*/
	$('#changePasswordBtn').click(function(e) {
		e.preventDefault()
		changePassword()
	})

	$("#cancelChangePasswordBtn").click(function(e) {
		e.preventDefault()
		$('.content').hide()
		$('.home-page').show()
		$("#password").val("")
		$("#passwordConfirm").val("")
		hideValidate($("#password"))
		hideValidate($("#passwordConfirm"))
	})

	/*Check if passwords match*/
	$("#password").on('blur', function(event){
		event.preventDefault()
		var pass = $("#password").val()
		var rep = $("#passwordConfirm").val()
		if(pass != rep){
			showValidate($("#password"))
			showValidate($("#passwordConfirm"))
		}else{
			hideValidate($("#password"))
			hideValidate($("#passwordConfirm"))
		}
	})
	$("#passwordConfirm").on('blur', function(event){
		event.preventDefault()
		var pass = $("#password").val()
		var rep = $("#passwordConfirm").val()
		if(pass != rep){
			showValidate($("#password"))
			showValidate($("#passwordConfirm"))
		}else{
			hideValidate($("#password"))
			hideValidate($("#passwordConfirm"))
		}
	})
	$("#password").on('focus', function(event){
		event.preventDefault()
		hideValidate($("#password"))
		hideValidate($("#passwordConfirm"))
	})
	$("#passwordConfirm").on('focus', function(event){
		event.preventDefault()
		hideValidate($("#password"))
		hideValidate($("#passwordConfirm"))
	})

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

	// vracanje na prikaz svih pacijenata
	$('#patientBack').click(function() {
		$('.content').hide()
		$('.doctor-patients').show()
		// scroll to top of page
		document.body.scrollTop = 0
		document.documentElement.scrollTop = 0
	})
	
	/*Show form to create medical report*/
	$('#startAppointment').click(function(e){
		e.preventDefault()
		
		if(appointment_id == null){
			alert("There's no current appointment for this patient!")
			return
		}
		
		$('#prescriptionMR').select2({ width: '100%' });
		$.ajax({
			type : "GET",
			async: false,
			url : "../../theGoodShepherd/diagnosis",
			dataType : "json",
			success : function(data)  {
				$('#diagnosisMR').empty();
				$('#prescriptionMR').empty();
				$.each(data, function(index, diagnose){
					diagnosis.push(diagnose)
					$('#diagnosisMR').append(new Option(diagnose.name));
					if(index == 0){
						$.each(diagnose.prescriptions, function(i, medicine){
							$('#prescriptionMR').append(new Option(medicine.name, medicine.id));
					})
					}
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
	
	$('#diagnosisMR').change(function(e){
		e.preventDefault()
		var name = $('#diagnosisMR').val()
		
		$('#prescriptionMR').empty();
		$.each(diagnosis, function(index, diagnose){
			if(diagnose.name == name){
				$.each(diagnose.prescriptions, function(index, medicine){
					$('#prescriptionMR').append(new Option(medicine.name, medicine.id));
				})
			}
		})
		/*$.ajax({
			type : "GET",
			async: false,
			url : "../../theGoodShepherd/prescription/doctor/"+name,
			dataType : "json",
			success : function(data)  {
				$('#prescriptionMR').empty();
				$.each(data, function(index, medicine){
					$('#prescriptionMR').append(new Option(medicine.name, medicine.id));
				})
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})*/
	})
	
	/*Adding new medical report*/
	$("#write_report").on('click', function(event){
		
		event.preventDefault()
		
		var description = $("#descriptionMR").val().trim()
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
			appId: appointment_id,
			description: description,
			diagnosisName: diagnosis,
			prescriptionIds: prescription
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
				viewPatientProfile(current_secNum)
				//patientsTable.ajax.reload()
				//medicalReportTable.ajax.reload()
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})

		$("#descriptionMR").val('')
		$("#diagnosisMR").val('')
		$('#prescriptionMR').val(null).trigger('change')
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
	
	/*Show form for modifying medical record and fill it in*/
	$('#changeRecord').click(function(e){
		e.preventDefault()
		
		if(appointment_id == null){
			alert("You can only change patient's medical record during an appointment!")
			$('.content').hide()
			$('.doctor-patient-profile').show()
			return
		}
		
		$('.doctor-patient-profile').hide()
		$('.modify-medical-record').show()

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
		var alergies = $('#alergiesMR').val().trim()
		
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
				//patientsTable.ajax.reload()
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})

		document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
	})
	
	//cancel modifying medical record
	$('#cancel_record').click(function(e){
		e.preventDefault()
		$('.content').hide()
		$('.doctor-patient-profile').show()
		document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
	})

	/*Changing password check if passwords match*/
	$("#password").on('blur', function(event){
		event.preventDefault()
		var pass = $("#password").val()
		var rep = $("#passwordConfirm").val()
		if(pass != rep){
			showValidate($("#password"))
			showValidate($("#passwordConfirm"))
		}else{
			hideValidate($("#password"))
			hideValidate($("#passwordConfirm"))
		}
	})
	$("#passwordConfirm").on('blur', function(event){
		event.preventDefault()
		var pass = $("#password").val()
		var rep = $("#passwordConfirm").val()
		if(pass != rep){
			showValidate($("#password"))
			showValidate($("#passwordConfirm"))
		}else{
			hideValidate($("#password"))
			hideValidate($("#passwordConfirm"))
		}
	})
	$("#password").on('focus', function(event){
		event.preventDefault()
		hideValidate($("#password"))
		hideValidate($("#passwordConfirm"))
	})
	$("#passwordConfirm").on('focus', function(event){
		event.preventDefault()
		hideValidate($("#password"))
		hideValidate($("#passwordConfirm"))
	})

	/*Check if start work is number*/
	$("#startWorkEdit").on('blur', function(e){
		e.preventDefault()
		if(isNaN($("#startWorkEdit").val())){
			showValidate($("#startWorkEdit"))
		}
	})
	$("#startWorkEdit").on('click', function(e){
		hideValidate($("#startWorkEdit"))
	})
	/*Check if end work is number*/
	$("#endWorkEdit").on('blur', function(e){
		e.preventDefault()
		if(isNaN($("#endWorkEdit").val())){
			showValidate($("#endWorkEdit"))
		}
	})
	$("#endWorkEdit").on('click', function(e){
		hideValidate($("#endWorkEdit"))
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
	} else {
		patientsTable.ajax.reload()
	}
}

function viewPersonalInformation(){
	// obracamo se serveru samo prvi put
	if ($("#fullNameBig").text() == "" || edited == true) {
		$.ajax({
			type : "GET",
			async: false,
			url : "../../theGoodShepherd/doctor/personalInformation" ,
			dataType: "json",
			success : function(output)  {
				$("#fullNameBig").text(output.name + " " + output.surname)
				$("#specializationBig").text(output.specialization)
				$("#fullName").text(output.name + " " + output.surname)
				$("#email").text(output.email)
				$("#gender").text(output.gender)
				$("#dateOfBirth").text(output.dateOfBirth)
				$("#phoneNumber").text(output.phoneNumber)
				$("#securityNumber").text(output.securityNumber)
				$("#address").text(output.address + ", " + output.city + ", " + output.country)
				$("#workingHours").text(output.startWork + ":00 - " + output.endWork + ":00")
				$("#specialization").text(output.specialization)
				$("#qualifications").empty()
				for (i = 0; i < output.appTypes.length; i++) {
					$("#qualifications").append('<li>' + output.appTypes[i] + '</li>');
				}
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
	}
}

function editPersonalInformation(){
	$('#doctorQualifications').select2({ width: '100%' });
	$.ajax({
		type : "GET",
		async: false,
		url : "../../theGoodShepherd/doctor/personalInformation" ,
		dataType: "json",
		success : function(output)  {
			$("#emailEdit").val(output.email)
			$("#nameEdit").val(output.name)
			$("#surnameEdit").val(output.surname)
			$("#genderEdit").val(output.gender);
			$("#dateOfBirthEdit").val(output.dateOfBirth)
			$("#phoneNumberEdit").val(output.phoneNumber)
			$("#securityNumberEdit").val(output.securityNumber)
			$("#addressEdit").val(output.address)
			$("#cityEdit").val(output.city)
			$("#countryEdit").val(output.country)
			$("#startWorkEdit").val(output.startWork)
			$("#endWorkEdit").val(output.endWork)
			$("#specializationDoctorEdit").val(output.specialization)
			$("#doctorQualifications").empty()
			$.each(output.allAppTypes, function( key, value ) {
				var newOption = new Option(value, value, false, false);
				$("#doctorQualifications").append(newOption);
			})
			$("#doctorQualifications").val(output.appTypes)
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}

function saveUpdatedDoctor(){
	var emailV = $("#emailEdit").val()
	var nameV = $("#nameEdit").val()
	var surnameV = $("#surnameEdit").val()
	var genderV = $("#genderEdit").val();
	var dateOfBirthV = $("#dateOfBirthEdit").val()
	var phoneNumberV = $("#phoneNumberEdit").val()
	var securityNumberV = $("#securityNumberEdit").val()
	var addressV = $("#addressEdit").val()
	var cityV = $("#cityEdit").val()
	var countryV = $("#countryEdit").val()
	var specializationV = $("#specializationDoctorEdit").val()
	var appTypesV = $("#doctorQualifications").val()
	var startWorkV = $("#startWorkEdit").val()
	var endWorkV = $("#endWorkEdit").val()
	
	if(!nameV || !surnameV || !genderV || !dateOfBirthV || !specializationV || 
		!startWorkV || !endWorkV){
		alert("Not all required fields are filled!")
		return;
	}

	if(isNaN(startWorkV)){
		alert("Start work time must be a number!")
		return;
	} else {
		if (parseInt(startWorkV) <0 || parseInt(startWorkV) > 24) {
			alert("Start work time must be a number greater than 0 and less than 24!")
			return;
		}
	}
	if(isNaN(endWorkV)){
		alert("End work time must be a number!")
		return;
	} else {
		if (parseInt(endWorkV) <0 || parseInt(endWorkV) > 24) {
			alert("End work time must be a number greater than 0 and less than 24!")
			return;
		}
	}
	if (!isNaN(startWorkV) && !isNaN(endWorkV)) {
		if(parseInt(startWorkV) >= parseInt(endWorkV)) {
			alert("Start work time must be before end work time!")
			return;
		}
	}

	var dateObject = new Date(dateOfBirthV);
	var currentDate = new Date();

	if(currentDate < dateObject){
		alert("Wrong date of birth!")
		return;
	}

	var newDoctor = {
		name: nameV,
		surname: surnameV,
		email: emailV,
		gender: genderV,
		dateOfBirth: dateOfBirthV,
		phoneNumber: phoneNumberV,
		securityNumber: securityNumberV,
		address: addressV,
		city: cityV,
		country: countryV,
		specialization: specializationV,
		appTypes: appTypesV,
		startWork: startWorkV,
		endWork: endWorkV
	}
	
	$.ajax({
		type : "POST",
		url : "../../theGoodShepherd/doctor/editPersonalInformation" ,
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(newDoctor),
		success : function(response)  {
			edited = true
			$(".d-profile").click()
			alert("Succesfully edited personal information.")
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}

function changePassword() {
	var passwordV = $("#password").val()
	var confirmPasswordV = $("#passwordConfirm").val()

	if(passwordV != confirmPasswordV){
		alert("Passwords do not match!")
		return;
	}
	
	if(!passwordV || !confirmPasswordV){
		alert("All fields must be filled!")
		return;
	}

	$.ajax({
		type : "POST",
		async: false,
		url : "../../theGoodShepherd/doctor/changePassword/"+passwordV,
		success : function(data)  {
			alert("Succesfully changed password.")
			$('.content').hide()
			$('.home-page').show()
			$("#password").val("")
			$("#passwordConfirm").val("")
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
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
			if (data.medicalRecords != null) {
				medicalRecord_id = data.medicalRecords.id
			}
			if (data.appointment != null) {
				appointment_id = data.appointment.id
			}
			viewMedicalReports(data)
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}

function viewMedicalReports(data){
	$('.content').hide()
    $('.doctor-patient-profile').show()
    $("#fullNamePatient").text(data.name + " " + data.surname)
    $("#emailPatient").text(data.email)
    $("#genderPatient").text(data.gender)
    $("#dateOfBirthPatient").text(data.dateOfBirth)
    $("#phoneNumberPatient").text(data.phoneNumber)
    $("#securityNumberPatient").text(data.securityNumber)
    $("#addressPatient").text(data.address + ", " + data.city + ", " + data.country)
    if(data.medicalRecords == null){
		$('#medicalReport').hide()
		$('h5').hide()
		// sakrivanje dugmadi za pregled
		$('.app-btns').hide()
        $("#generalReport").text("You do not have access to patients medical record.")
    }else{
		$('#medicalReport').show()
		$('h5').show()
		// prikaz dugmadi za pregled
		$('.app-btns').show()
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
							if (data.prescriptionMedicines != null) {
								for (var i = 0; i < data.prescriptionMedicines.length; i++) {
									allMedicine += data.prescriptionMedicines[i]
									if (i != data.prescriptionMedicines.length - 1) {
										allMedicine += ", "
									}
								}
							}
							return allMedicine
						}
					}]
    		})
    	} else {
			if (data.medicalRecords.medicalReports != null) {
				medicalReportTable.clear().rows.add(data.medicalRecords.medicalReports).draw();
			}
		}
	}
}


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

/*Validation and forms*/
function showValidate(input) {
	var thisAlert = $(input).parent();

	$(thisAlert).addClass('alert-validate');
}
function hideValidate(input) {
	var thisAlert = $(input).parent();

	$(thisAlert).removeClass('alert-validate');
}