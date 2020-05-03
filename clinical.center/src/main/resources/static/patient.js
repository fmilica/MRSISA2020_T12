
var newAppointment = {
	date: "",
	appType: "",
	clinicId: "",
	doctorId: "",
	time: ""
}

var clinicsTable;
var doctorsClinicTable;

$(document).ready(function() {

	// pretplata svih elemenata sa klasom na klik
	$('body').on('click', 'button.table-button', function() {
		// inicijalizacija tabele sa doktorima odabrane klinike
		var clinicId = $(this).attr('id')
		var clinicName = $(this).attr('name')
		initialiseClinicDoctors(clinicId, clinicName)
	});
	$('body').on('click', 'button.table-button-doctor', function() {
		// prikupljanje podataka i kreiranje pregleda
		var doctorId = $(this).attr('id')
		var time = $('#time'+doctorId).val()
		createAppointment(doctorId, time)
	});

	// filtriranje klinika
	$('#filterClinics').click(function(e) {
		e.preventDefault()
		// postavljanje parametara pregleda
		var date = "datum"//$('#filterAppDate').val()
		newAppointment.date = date
		var appType = $('#filterAppType').val()
		newAppointment.appType = appType

		// filtriranje klinika na beku
		// TODO
		// sakrije doktore
		$('#doctorTableDiv').hide()
		// doda novu vrednost
		clinicsTable.rows().every( function ( rowIdx, tableLoop, rowLoop ) {
			var data = this.data();
			data[4] = newAppointment.appType
			this.data(data)
		 } )
	})

	// inicijalizacija tabele sa klinikama
	clinicsTable = $('#clinicsTable').DataTable({
		responsive: true,
		ajax: {
			url: "../../theGoodShepherd/clinics",
			dataSrc: ''
		},
		columns: [
			{ data: 'name'},
			//{ data: 'rating'},
			{ data: 'address'},
			{ data: 'address'},
			{ data: 'address'},
			{
				data: null,
				render: function () {
					if (newAppointment.appType) {
						return newAppointment.appType
					} else {
						return "Not specified"
					}
				}
			},
			/*{ 
				data: null,
				render: function(data) {
					if (false) {
						$('.discount').show()
						return "JU GET A POPUST"
					}
					if (data.discount) {
						$('.discount').show()
						return data.discount
					}
				}
			},*/
			//{ data: 'price'},
			{
				data: null,
				render: function (data) {
					var button = '<button id="'+data.id+'" class="btn btn-info table-button" name="'+data.name+'">View doctors</button>';
				  	return button;
				}
			}]
	})

	$('#register').click(function(e) {
		e.preventDefault();

		var emailV = $('#email').val()
		var nameV = $('#name').val()
		var surnameV = $('#surname').val()
		var passwordV1 = $('#password').val()
		//var passwordV2 = $('#passwordConfirm').val()
		var genderV = $('#gender').val()
		var dateOfBirthV = "11.11.1978."//$('#').val()
		var securityNumberV = parseInt($('#securityNumber').val())
		var phoneNumberV = $('#phoneNumber').val()
		var addressV = $('#address').val()
		var cityV = $('#city').val()
		var countryV = $('#country').val()

		var newPatient = {
			email: emailV,
			name: nameV,
			surname: surnameV,
			password: passwordV1,
			gender: genderV,
			dateOfBirth: dateOfBirthV,
			securityNumber: securityNumberV,
			phoneNumber: phoneNumberV,
			address: addressV,
			city: cityV,
			country: countryV
		}

		$.ajax({
			type : "POST",
			url : "../../theGoodShepherd/patient/register",
			contentType : "application/json",
			dataType : "json",
			data : JSON.stringify(newPatient),
			success : function(response){
				window.location.href = "../../html/home-pages/patient_hp.html"
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
	})
})

function initialiseClinicDoctors(clinicId, clinicName) {
	// popunjavamo podatke o pregledu
	newAppointment.clinicId = clinicId
	newAppointment.clinicName = clinicName

	// dodavanje imena klinike u naslov tabele
	$('#doctorsClinic').text("Doctors from " + clinicName)

	// provara da je tabela vec inicijalizovana
	if (!$.fn.DataTable.isDataTable('#doctorsClinicTable')) {
		// nije inicijalizovana
		// inicijalizacija same tabele
		doctorsClinicTable = $('#doctorsClinicTable').DataTable({
			responsive: true,
			ajax: {
				url: "../../theGoodShepherd/clinics/getDoctors/"+clinicId,
				dataSrc: ''
			},
			columns: [
				{ data: 'name'},
				{ data: 'surname'},
				{ data: 'rating'},
				{
					data: null,
					render: function (data) {
					return '<select class="form-control input-height available-times" id="time'+data.id+'">' + 
								'<option selected="selected" value="12:00">12:00</option>' + 
								'<option value="14:00">14:00</option>' +
						'</select>'
					}
				},
				{
					data: null,
					render: function (data) {
						if (newAppointment.appType && newAppointment.date) {
							var button = '<button id="'+data.id+'" class="btn btn-info table-button-doctor">Schedule</button>';
							return button;
						} else {
							return "Choose all parameters to schedule"
						}
					}
				}
			]
		})
		$('#doctorTableDiv').show()
	} else {
		// jeste inicijalizovana
		doctorsClinicTable.ajax.url("../../theGoodShepherd/clinics/getDoctors/"+clinicId)
		doctorsClinicTable.ajax.reload()
		$('#doctorTableDiv').show()
	}
}

function createAppointment(doctorId, time) {
	newAppointment.doctorId = doctorId;
	newAppointment.time = time
	alert("redirekt na stranicu sa svim podacima")
	alert(JSON.stringify(newAppointment))
}

function logInPatient(email, password) {

	$.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/patient/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(response)  {
			window.location.href = "html/home-pages/patient_hp.html"
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}