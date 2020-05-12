
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
	
	// postavljanje minimalne vrednosti koju pacijent moze da odabere za datum
	// (danas)
	document.getElementById("filterAppDate").min = new Date().toISOString().split("T")[0];

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

	// filtriranje doktora
	$('#filterDoctors').click(function(e) {
		e.preventDefault()
		var nameV = $('#doctorFilterName').val()
		var surnameV = $('#doctorFilterSurname').val()
		var ratingV = $('#doctorFilterRating').val()
		//doctorsClinicTable.ajax.url("../../theGoodShepherd/clinics/getDoctors/"+clinicId/*+"/"+newAppointment.appType*/+"/"+nameV+"/"+surnameV+"/"+ratingV)
	})

	// dobavljanje vrednosti tipova pregleda i dodavanje u select
	$('#patientClinics').click(function() {
		$.ajax({
			type : "GET",
			url : "../../theGoodShepherd/appointmentType/getAllTypesNames",
			success : function(data) {
				$('#filterAppType').find('option').remove()
				$.each(data, function(index, appType) {
					$("#filterAppType").append(new Option(appType.name, appType.name));
				})
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
	})

	// ponistavanje filtera klinika
	$('#clearClinicsFilter').click(function(e) {
		e.preventDefault()
		// skidanje parametra pregleda
		newAppointment.date = ""
		newAppointment.appType = ""
		// dobavljanje svih klinika ponovo
		clinicsTable.ajax.url("../../theGoodShepherd/patient/clinics")
		clinicsTable.ajax.reload()
	})

	// filtriranje klinika
	$('#filterClinics').click(function(e) {
		e.preventDefault()
		// postavljanje parametara pregleda
		var date = $('#filterAppDate').val()
		newAppointment.date = date
		var appTypeName = $('#filterAppType').val()
		newAppointment.appType = appTypeName

		// filtriranje klinika na beku
		clinicsTable.ajax.url("../../theGoodShepherd/clinics/filterClinics/"+appTypeName+"/"+date)
		clinicsTable.ajax.reload()
		// sakrije doktore
		$('#doctorTableDiv').hide()
		// doda novu vrednost
		clinicsTable.rows().every( function ( rowIdx, tableLoop, rowLoop ) {
			var data = this.data();
			data[4] = newAppointment.appType
			this.data(data)
		 } )
	})

	/*View clinics*/
	$("#patientClinics").on('click', function(e){
		e.preventDefault()
		viewClinics()
	})
})

function viewClinics(){
	if (!$.fn.DataTable.isDataTable('#clinicsTable')) {
		clinicsTable = $('#clinicsTable').DataTable({
		responsive: true,
		ajax: {
			url: "../../theGoodShepherd/patient/clinics",
			dataSrc: ''
		},
		columns: [
			{ data: 'name'},
			{ data: 'address'},
			{ data: 'rating'},
			{
				// price
				data: null,
				render: function (data) {
					if (newAppointment.appType) {
						for (var i = 0; i < data.appointmentTypes.length; i++) {
							if (data.appointmentTypes[i].name == newAppointment.appType) {
								return data.appointmentTypes[i].price + " &euro;"
							}
						}
						return ""
					} else {
						return "Not specified"
					}
				}
			},
			{
				// appointmentType
				data: null,
				render: function () {
					if (newAppointment.appType) {
						return newAppointment.appType
					} else {
						return "Not specified"
					}
				}
			},
			{
				data: null,
				render: function (data) {
					if (!newAppointment.date || !newAppointment.appType) {
						return "Choose all parameters to view available doctors"
					}
					var button = '<button id="'+data.id+'" class="btn btn-info table-button" name="'+data.name+'">View doctors</button>';
				  	return button;
				}
			}]
	})
}
}

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
				url: "../../theGoodShepherd/doctor/certified/clinic/"+newAppointment.appType+"/"+newAppointment.date+"/"+clinicId,
				dataSrc: ''
			},
			columns: [
				{ data: 'name'},
				{ data: 'surname'},
				{ data: 'rating'},
				/*{
					data: null,
					render: function () {
						return "";
					}
				},*/
				{
					data: null,
					render: function (data) {
						var options = ""
						for (var i = 0; i < data.availableTimes.length; i++) {
							options += '<option value="'+data.availableTimes[i]+'">'+data.availableTimes[i]+':00</option>'
						}
					return '<select class="form-control input-height available-times" id="time'+data.id+'">' + 
								options + '</select>'
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
		doctorsClinicTable.ajax.url( "../../theGoodShepherd/doctor/certified/clinic/"+newAppointment.appType+"/"+newAppointment.date+"/"+clinicId)
		doctorsClinicTable.ajax.reload()
		$('#doctorTableDiv').show()
	}
}

function createAppointment(doctorId, time) {
	newAppointment.doctorId = doctorId;
	newAppointment.time = time
	//alert("redirekt na stranicu sa svim podacima")
	alert(JSON.stringify(newAppointment))
	alert("U narednom sprintu, veza sa slanjem emaila!")
}

function logInPatient(email, password) {

	$.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/patient/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(output)  {
			sessionStorage.setItem('nameSurname', output.name + ' ' + output.surname);
			window.location.href = "html/home-pages/patient_hp.html"
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}