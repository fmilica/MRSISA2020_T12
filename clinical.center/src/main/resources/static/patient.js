var price;
var duration;

var newAppointment = {
	date: "",
	appType: "",
	appDuration: "",
	clinicId: "",
	clinicName: "",
	doctorId: "",
	doctorName: "",
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
		var atributes = $(this).attr('name')
		var tokens = atributes.split('|')
		newAppointment.appDuration = tokens[2]
		newAppointment.clinicName = tokens[0]
		initialiseClinicDoctors(clinicId, tokens[0])
	});
	$('body').on('click', 'button.table-button-doctor', function() {
		// prikupljanje podataka i kreiranje pregleda
		var doctorId = $(this).attr('id')
		newAppointment.doctorName = $(this).attr('name')
		var time = $('#time'+doctorId).val()
		createAppointment(doctorId, time)
	});
	
	$('#cancelApp').on('click', function(e){
		e.preventDefault()
		$('.patient-confirm-app').hide()
		$('.patient-clinics').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
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
		newAppointment.appDuration = ""
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
	
	/*Filter given clinics by name and address*/
	$("#filterClinicsByAtributes").on('click', function(e){
		e.preventDefault()
		filterClinics()
	})
	
	/*Filter doctors by name and surname*/
	$("#filterDoctorsByAtributes").on('click', function(e){
		e.preventDefault()
		filterDoctors()
	})
})

function filterDoctors(){
	var nameV = $('#doctorName').val()
	var surnameV = $('#doctorSurname').val()
	var ratingV = $('#doctorRating').val()
	
	doctorsClinicTable
    .column(0)
    .search(nameV)
    .draw();
	
	doctorsClinicTable
    .column(1)
    .search(surnameV)
    .draw();
	
	doctorsClinicTable
    .column(2)
    .search(ratingV)
    .draw();
}

function filterClinics(){
	
	var nameV = $('#clinicName').val()
	var addressV = $('#clinicAddress').val()
	
	clinicsTable
    .column(0)
    .search(nameV)
    .draw();
	
	clinicsTable
    .column(1)
    .search(addressV)
    .draw();
}

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
								price = data.appointmentTypes[i].price + " &euro"
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
				// duration
				data: null,
				render: function (data) {
					if (newAppointment.appType) {
						for (var i = 0; i < data.appointmentTypes.length; i++) {
							if (data.appointmentTypes[i].name == newAppointment.appType) {
								duration = data.appointmentTypes[i].duration + "h"
								return data.appointmentTypes[i].duration + "h"
							}
						}
						return ""
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
					var button = '<button id="'+data.id+'" class="btn btn-info table-button" name="'+data.name+ '|' + price + '|' + duration + '">View doctors</button>';
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
							var button = '<button id="'+data.id+'" name="' + data.name + ' ' + data.surname + '"class="btn btn-info table-button-doctor">Schedule</button>';
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
	newAppointment.time = time + ":00"
	// prikaz svih podataka
	$('#date').text(newAppointment.date)
	$('#time').text(newAppointment.time)
	$('#duration').text(newAppointment.appDuration)
	$('#appType').text(newAppointment.appType)
	$('#doctor').text(newAppointment.doctorName)
	$('#clinic').text(newAppointment.clinicName)
	$('.content').hide()
	$('.patient-confirm-app').show()
	document.body.scrollTop = 0
    document.documentElement.scrollTop = 0
	alert("Milica ovde treba da uveze slanje mejla clinic adminima za tu kliniku!")
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