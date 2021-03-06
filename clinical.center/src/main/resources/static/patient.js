var accessToken = "zY8x5DiT6IjNIoPjDSrIDhuiL7XI56A7RuNyqQjQsL8D2nVyJOC9eQTv59EaBxmH"
var map = null;
var clinicMapIcon;
var existingMarker = null;

var price;
var duration;

var doctorSchedule = null;

var newAppointment = {
	date: "",
	appTypeName: "",
	appDuration: "",
	clinicId: "",
	clinicName: "",
	doctorId: "",
	doctorName: "",
	time: ""
}

var allClinicsTable;
var predefinedApps;
var clinicsTable;
var doctorsClinicTable;
var allDoctorsClinicTable;
var medicalReportTable;
var finishedAppsTable;
var confirmedAppsTable;
var unconfirmedAppsTable;
var visitedDoctorsTable;
var visitedClinicsTable;

$(document).ready(function() {

	// postavljanje maksimalnog datuma rodjenja koji se moze odabrati na danas
	document.getElementById("dateOfBirthEdit").max = new Date().toISOString().split("T")[0];

	/*------------------------------------------------------------------------------*/
	// pregled profila
	$('.profile').click(function() {
		$.ajax({
			type : "GET",
			url : "../../theGoodShepherd/patient/viewProfile",
			dataType: "json",
			success : function(data)  {
				fillProfile(data)
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
	})
	
	/*Edit personal information*/
	$('#editPatientProfile').click(function(e) {
		e.preventDefault();
		$('.content').hide()
        $('.patient-edit-profile').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
	})
	
	$('#confirmEdit').click(function(e) {
		e.preventDefault()
		saveUpdatedProfile()
	})

	$('#cancelEdit').click(function(e) {
		e.preventDefault()
		$('.content').hide()
		fillEditForm()
		$('.patient-profile').show()
		document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
	})
	
	/*Change password*/
	$('#changePasswordBtn').click(function(e) {
		e.preventDefault()
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
			url : "../../theGoodShepherd/patient/changePassword/"+passwordV,
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
	
	/*------------------------------------------------------------------------------*/
	
	/* Pregled svih klinika i njihovih predefinisanih pregleda */
	// pregled svih klinika
	$('#viewAllClinics').on('click', function(e){
		e.preventDefault()
		viewAllClinics()
	})

	// vracanje na pregled svih klinika
	$('#clinicBack').click(function(e) {
		e.preventDefault()
		$('.content').hide()
		$('.patient-viewAllClinics').show()
	})

	// filtriranje svih klinika po imenu i adresi
	$('#filterAllClinicsByAtributes').click(function(e){
		e.preventDefault()
		filterAllClinics()
	})

	// pregled svih slobodnih predefinisanih pregleda
	$('#viewPredefined').click(function(e) {
		e.preventDefault()
		$('.content').hide()
		$('.view-predefined').show()
	})

	// pregled svih doktora jedne klinike
	$('#viewDoctors').click(function(e) {
		e.preventDefault()
		$('.content').hide()
		$('.clinic-all-doctors').show()
		// dobavljanje appTypes klinike
		doctorSchedule = true
		getClinicAppTypes(newAppointment.clinicId)
		newAppointment.appTypeName="all"
		newAppointment.date="all"
		newAppointment.appDuration=""
		initialiseClinicAllDoctors(newAppointment.clinicId, newAppointment.clinicName)
		//OVDE
	})

	// filtriranje doktora
	$('#filterDoctors').click(function(e) {
		e.preventDefault()
		// postavljanje parametara pregleda
		var date = $('#filterAllAppDate').val()
		newAppointment.date = date
		var appTypeName = $('#filterClinicAppType').val()
		newAppointment.appTypeName = appTypeName
		var duration = $('#filterClinicAppType option[value="' + appTypeName + '"]').attr("name")
		newAppointment.appDuration = duration
		allDoctorsClinicTable.ajax.url( "../../theGoodShepherd/doctor/certified/clinic/"+newAppointment.appTypeName+"/"+newAppointment.date+"/"+newAppointment.clinicId)
		allDoctorsClinicTable.ajax.reload()
	})
	// ponistavanje filtera doktora
	$('#clearDoctorsFilter').click(function(e) {
		e.preventDefault()
		// skidanje parametra pregleda
		newAppointment.date = "all"
		newAppointment.appTypeName = "all"
		newAppointment.appDuration = ""
		// dobavljanje svih klinika ponovo
		allDoctorsClinicTable.ajax.url( "../../theGoodShepherd/doctor/certified/clinic/"+newAppointment.appTypeName+"/"+newAppointment.date+"/"+newAppointment.clinicId)
		allDoctorsClinicTable.ajax.reload()
	})

	// filtriranje doktora klinike po atributima
	$("#filterAllDoctorsByAtributes").on('click', function(e){
		e.preventDefault()
		filterAllDoctors()
	})

	// vracanje na pregled klinike
	$('#predefinedBack').click(function(e) {
		e.preventDefault()
		$('.content').hide()
		$('.view-clinic').show()
	})

	// filtriranje predefinisanih pregleda
	$('#filterPredefined').click(function(e) {
		e.preventDefault()
		filterPredefinedApps()
	})

	// pretplata na klik na dugmad u tabeli svih klinika
	// (Visit clinic)
	$('body').on('click', 'button.visit-clinic-btn', function() {
		// popunjavanje podataka o odabranoj klinici
		var clinicId = $(this).attr('id')
		var clinicName = $(this).attr('name')
		fillClinicInfo(clinicId, clinicName)
		newAppointment.clinicId = clinicId
		newAppointment.clinicName = clinicName
		$('.content').hide()
		$('.view-clinic').show()
	});
	// pretplata na klik na dugmad u tabeli slobodnih pregleda
	// (Schedule)
	$('body').on('click', 'button.schedule-app-btn', function() {
		// popunjavanje podataka o odabranoj klinici
		var appId = $(this).attr('id')
		schedulePredefinedApp(appId)
	});

	/*********************************/

	/* Kreiranje novog pregleda - pacijent */
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
		doctorSchedule = false
		initialiseClinicDoctors(clinicId, tokens[0])
	});
	$('body').on('click', 'button.table-button-doctor', function() {
		// prikupljanje podataka i kreiranje pregleda
		var doctorId = $(this).attr('id')
		newAppointment.doctorName = $(this).attr('name')
		var time = $('#time'+doctorId).val()
		createAppointment(doctorId, time)
	});
	
	$('#confirmApp').click(function(e) {
		e.preventDefault()

		var app = {
			clinic: {
				id: newAppointment.clinicId
			}
		}
		
		// prvo upise novi pregled i zahtev za pregled u bazu
		$.ajax({
			type: "POST",
			url: "../../theGoodShepherd/appointment/createPatientApp",
			contentType: "application/json",
			data: JSON.stringify(newAppointment),
			success: function() {
				$("body").css("cursor", "default");
				alert("Appointment request sent successfully!\n" +
					  "You will get an email when a clinic administrator confirms it.\n" +
					  "The appointment then becomes visible in 'Unconfirmed appointments' tab.")
				$('.content').hide()
				if (doctorSchedule == true) {
					$('.clinic-all-doctors').show()
					allDoctorsClinicTable.ajax.reload()
				} else {
					$('.patient-createNewApp').show()
				}
			},
			error: function(response) {
				alert(response.responseJSON.message)
				allDoctorsClinicTable.ajax.reload()
			}
		})

		// salje se mejl svim administratorima klinike
		//kursor ceka
		$("body").css("cursor", "progress")
		$.ajax({
			type : "POST",
			url : "../../theGoodShepherd/patient/sendAppointment",
			contentType: "application/json",
			data: JSON.stringify(app),
			success : function() {
	    		// ovde ne mora nista
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
	})

	$('#cancelApp').on('click', function(e){
		e.preventDefault()
		$('.patient-confirm-app').hide()
		if (doctorSchedule == true) {
			$('.clinic-all-doctors').show()
		} else {
			$('.patient-createNewApp').show()
		}
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
	})

	// dobavljanje vrednosti tipova pregleda i dodavanje u select
	$('#scheduleNewAppointment').click(function() {
		$.ajax({
			type : "GET",
			url : "../../theGoodShepherd/appointmentType/getAllTypesNames",
			success : function(data) {
				$('#filterAppType').find('option').remove()
				$.each(data, function(index, appTypeName) {
					$("#filterAppType").append(new Option(appTypeName, appTypeName));
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
		newAppointment.appTypeName = ""
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
		newAppointment.appTypeName = appTypeName

		// filtriranje klinika na beku
		clinicsTable.ajax.url("../../theGoodShepherd/clinics/filterClinics/"+appTypeName+"/"+date)
		clinicsTable.ajax.reload()
		// sakrije doktore
		$('#doctorTableDiv').hide()
		// doda novu vrednost
		clinicsTable.rows().every( function ( rowIdx, tableLoop, rowLoop ) {
			var data = this.data();
			data[4] = newAppointment.appTypeName
			this.data(data)
		 } )
	})

	/*View clinics*/
	$("#scheduleNewAppointment").on('click', function(e){
		e.preventDefault()
		viewClinics()
	})
	
	/*Filter given clinics by name and address*/
	$("#filterClinicsByAtributes").on('click', function(e){
		e.preventDefault()
		filterClinics()
	})
	
	/*Filter doctors by name and surname and rating */
	$("#filterDoctorsByAtributes").on('click', function(e){
		e.preventDefault()
		filterDoctors()
	})
	/**************/

	/*---------------------------------------------------------------*/
	/* View my appointments */
	// view finished appointments
	$('#finishedApps').click(function() {
		initialiseFinishedApps()
		$('.content').hide()
		$('.patient-finished-apps').show()
	})
	// view confirmed appointments
	$('#confirmedApps').click(function() {
		initialiseConfirmedApps()
		$('.content').hide()
		$('.patient-confirmed-apps').show()
	})
	// cancel confirmed appointment
	$('body').on('click', 'button.cancel-app-btn', function() {
		// dobavljanje id-a pregleda
		var appId = $(this).attr('id')
		cancelAppointment(appId)
	});
	// view unconfirmed appointments
	$('#unconfirmedApps').click(function() {
		initialiseUnconfirmedApps()
		$('.content').hide()
		$('.patient-unconfirmed-apps').show()
	})
	/*---------------------------------------------------------------*/

	/*---------------------------------------------------------------*/
	/* View doctors and clinics visited */
	$('#patientsDoctorsClinics').click(function() {
		initialiseVisitedDoctorsTable()
		initialiseVisitedClinicsTable()
	})
	// pretplata na klin na dugmad za ocenjivanje
	$('body').on('click', 'button.rate-doctor-btn', function() {
		// dobavljanje id-a doktora
		var doctorId = $(this).attr('id')
		var rating = $("#ratingD"+doctorId).val()
		rateDoctor(doctorId, rating)
	});
	$('body').on('click', 'button.rate-clinic-btn', function() {
		// dobavljanje id-a klinike
		var clinicId = $(this).attr('id')
		var rating = $("#ratingC"+clinicId).val()
		rateClinic(clinicId, rating)
	});
	/*---------------------------------------------------------------*/
})

/*Profile*/
/*
function editPersonalInformation(){
	$.ajax({
		type : "GET",
		async: false,
		url : "../../theGoodShepherd/patient/viewProfile" ,
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
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}
*/
function saveUpdatedProfile(){
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
	
	if(!nameV || !surnameV || !genderV || !dateOfBirthV){
		alert("Not all required fields are filled!")
		return;
	}

	var dateObject = new Date(dateOfBirthV);
	var currentDate = new Date();

	if(currentDate < dateObject){
		alert("Wrong date of birth!")
		return;
	}

	var newPatient = {
		name: nameV,
		surname: surnameV,
		email: emailV,
		gender: genderV,
		dateOfBirth: dateOfBirthV,
		phoneNumber: phoneNumberV,
		securityNumber: securityNumberV,
		address: addressV,
		city: cityV,
		country: countryV
	}
	
	$.ajax({
		type : "POST",
		url : "../../theGoodShepherd/patient/editPersonalInformation" ,
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(newPatient),
		success : function(response)  {
			fillProfile(response)
			document.body.scrollTop = 0
			document.documentElement.scrollTop = 0
			alert("Succesfully edited personal information.")
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}

/* Predefinisani pregledi */
// inicijalizacija tabele svih klinika
function viewAllClinics() {
	if (!$.fn.DataTable.isDataTable('#allClinicsTable')) {
		allClinicsTable = $('#allClinicsTable').DataTable({
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
				data: null,
				render: function (data) {
					return '<button id="'+data.id+'" name="'+data.name+'" class="btn btn-info visit-clinic-btn">Visit clnic</button>';
				}
			}]
		})
		// specijalizovano filtriranje
		$.fn.DataTable.ext.search.push(
			function( settings, data, dataIndex ) {
				// omogucava da radi samo za zeljenu tabelu
				// za sve druge vraca da uvek zadovoljava uslov
				// tako tabele ostanu nepromenjene
				if ( settings.nTable.id !== 'allClinicsTable' ) {
					return true;
				}
				var minRatingV = $('#allClinicsMinRating').val()
				var rating = parseFloat(data[2]) || 0; // use data for the age column
			
				if ((isNaN( minRatingV )) || ( minRatingV <= rating)) {
					return true;
				}
				return false;
			}
		)
	} else {
		allClinicsTable.ajax.reload()
	}
}
// filtriranje svih klinika po imenu i adresi
function filterAllClinics(){
	
	var nameV = $('#allClinicsName').val()
	var addressV = $('#allClinicsAddress').val()

	allClinicsTable
	.column(0).search(nameV)
	.column(1).search(addressV)
	.draw()
}
// popunjavanje podataka o klinici
function fillClinicInfo(clinicId, clinicName) {
	$.ajax({
		type : "POST",
		url : "../../theGoodShepherd/clinics/"+clinicId,
		dataType: "json",
		success : function(output)  {
			$('.clinicName').text(output.name)
			$('#clinicRating').text(output.rating)
			$('#clinicAddress').text(output.address)
			$('#clinicCity').text(output.city)
			$('#clinicCountry').text(output.country)
			initialiseClinicMarker(output.address, output.city, output.country)
			$('#clinicDescription').text(output.description)
			viewAvailableApps(clinicId, clinicName)
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}
// inicijalizacija mape sa klinikom
function initialiseClinicMap() {
	// create map
	map = new L.map('clinicOnMap', { zoomControl: false }).setView([35, -26], 2.5)
	// add tile layer to map (actual map)
    L.tileLayer('https://tile.jawg.io/jawg-sunny/{z}/{x}/{y}.png?access-token='+accessToken, {
        attribution: "<a href=\"https://www.jawg.io\" target=\"_blank\">&copy; Jawg</a> - <a href=\"https://www.openstreetmap.org\" target=\"_blank\">&copy; OpenStreetMap</a>&nbsp;contributors",
        minZoom: 2.5,
        zoomControl: false,
	}).addTo(map)
	// create clinic icon
	clinicMapIcon = L.icon({
		iconUrl: '../../img/icons/index/clinic_admin.png',
		iconSize:     [40, 40], // size of the icon
		iconAnchor:   [20, 20], // point of the icon which will correspond to marker's location
	})
}
function initialiseClinicMarker(clinicAddress, clinicCity, clinicCountry) {
	if (!map) {
		// inicijalizacija mapa klinika
		initialiseClinicMap()
	}
	var mapGeoApiUrl = "https://api.jawg.io/places/v1/search?text="+clinicAddress+" "+clinicCity+" "+clinicCountry+"&access-token="+accessToken+"&size=1"
	$.ajax({
		type : "GET",
		url : mapGeoApiUrl,
		dataType: "json",
		success : function(output)  {
			var coordinates = output.features[0].geometry.coordinates
			if (existingMarker) {
				var newLatLng = new L.LatLng(coordinates[1], coordinates[0]);
    			existingMarker.setLatLng(newLatLng); 
			} else {
				existingMarker = L.marker([coordinates[1], coordinates[0]], {icon: clinicMapIcon}).addTo(map)
			}
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}
// pregled svih slobodnih pregleda klinike
function viewAvailableApps(clinicId, clinicName) {
	$('#clinicPredefined').text(clinicName + "'s predefined appointments")
	if (!$.fn.DataTable.isDataTable('#predefinedApps')) {
		predefinedApps = $('#predefinedApps').DataTable({
		responsive: true,
		ajax: {
			url: "../../theGoodShepherd/appointment/available/"+clinicId,
			dataSrc: ''
		},
		columns: [
			{ data: 'date'},
			{
				data: null,
				render: function(data) {
					return data.startTime + ":00"
				}
			},
			{
				data: null,
				render: function(data) {
					return data.endTime + ":00"
				}
			},
			{ data: 'appType'},
			{ data: 'doctor'},
			{ data: 'ordination'},
			{ 
				data: null,
				render: function(data) {
					return data.price + " &euro;"
				}
			},
			{ 
				data: null,
				render: function(data) {
					if (data.discount == null) {
						return "/"
					} else {
						return data.discount + " %"
					}
				}
			},
			{
				data: null,
				render: function (data) {
					return '<button id="'+data.id+'" class="btn btn-info schedule-app-btn">Schedule</button>';
				}
			}]
		})
	} else {
		predefinedApps.ajax.url("../../theGoodShepherd/appointment/available/"+clinicId)
		predefinedApps.ajax.reload()
	}
}
// filtriranje svih klinika po imenu i adresi
function filterPredefinedApps(){
	
	var dateV = $('#filterPredefinedDate').val()
	var typeV = $('#filterPredefinedType').val()
	var doctorV = $('#filterPredefinedDoctor').val()

	predefinedApps
	.column(0).search(dateV)
	.column(3).search(typeV)
	.column(4).search(doctorV)
	.draw()
}
// zakazivanje pregleda
function schedulePredefinedApp(appId) {
	var colorEvent;
	$.ajax({
		type : "POST",
		url : "../../theGoodShepherd/appointment/schedule/"+appId,
		dataType: "json",
		success : function(output)  {
			if(output.typeOf == "appointment"){
				colorEvent = '#48baf7' 
			}else{
				colorEvent = '#2aebb4'
			}
	        calendar.addEvent({
				id: output.id,
		        title: output.appType + " " + output.typeOf + ' with doctor ' + output.doctor,
		        description: output.appType + " " + output.typeOf + ' with doctor ' + output.doctor,
		        start: output.date+"T0"+output.startTime+":00:00",
		        end: output.date+"T0"+output.endTime+":00:00",
		        color: colorEvent
		    });
	        //calendar.render();
			alert("Succesfully scheduled an appointment!\nAll appointments are visible in 'My appointments' tab.")	
			predefinedApps.ajax.reload()
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}

/*************************/

function filterDoctors(){
	var nameV = $('#doctorName').val()
	var surnameV = $('#doctorSurname').val()
	var ratingV = $('#doctorRating').val()
	
	doctorsClinicTable
	.column(0).search(nameV)
	.column(1).search(surnameV)
	.column(2).search(ratingV)
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
					if (newAppointment.appTypeName) {
						for (var i = 0; i < data.appointmentTypes.length; i++) {
							if (data.appointmentTypes[i].name == newAppointment.appTypeName) {
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
					if (newAppointment.appTypeName) {
						return newAppointment.appTypeName
					} else {
						return "Not specified"
					}
				}
			},
			{
				// duration
				data: null,
				render: function (data) {
					if (newAppointment.appTypeName) {
						for (var i = 0; i < data.appointmentTypes.length; i++) {
							if (data.appointmentTypes[i].name == newAppointment.appTypeName) {
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
					if (!newAppointment.date || !newAppointment.appTypeName) {
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
				url: "../../theGoodShepherd/doctor/certified/clinic/"+newAppointment.appTypeName+"/"+newAppointment.date+"/"+clinicId,
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
						var availableTimes = data.availableTimes == null ? [] : (data.availableTimes instanceof Array ? data.availableTimes : [data.availableTimes])
						var options = ""
						for (var i = 0; i < availableTimes.length; i++) {
							options += '<option value="'+availableTimes[i]+'">'+availableTimes[i]+':00</option>'
						}
						return '<select class="form-control input-height available-times" id="time'+data.id+'">' + 
								options + '</select>'
					}
				},
				{
					data: null,
					render: function (data) {
						if (newAppointment.appTypeName && newAppointment.date) {
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
		doctorsClinicTable.ajax.url( "../../theGoodShepherd/doctor/certified/clinic/"+newAppointment.appTypeName+"/"+newAppointment.date+"/"+clinicId)
		doctorsClinicTable.ajax.reload()
		$('#doctorTableDiv').show()
	}
}

function getClinicAppTypes(clinicId) {
	$.ajax({
		type : "POST",
		url : "../../theGoodShepherd/appointmentType/getClinicTypes/"+clinicId,
		contentType: "application/json",
		success : function(data) {
			$('#filterClinicAppType').find('option').remove()
			$.each(data, function(index, appType) {
				var option = '<option name="'+appType.duration+'" value="'+appType.name+'">'+appType.name+'</option>'
				$("#filterClinicAppType").append(option)
				//$("#filterClinicAppType").append(new Option(appType.name, appType.name));
			})
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}

function initialiseClinicAllDoctors(clinicId, clinicName) {
	// popunjavamo podatke o pregledu
	newAppointment.clinicId = clinicId
	newAppointment.clinicName = clinicName

	// dodavanje imena klinike u naslov tabele
	$('#allDoctorsClinic').text("Doctors from " + clinicName)

	// provara da je tabela vec inicijalizovana
	if (!$.fn.DataTable.isDataTable('#allDoctorsClinicTable')) {
		// nije inicijalizovana
		// inicijalizacija same tabele
		allDoctorsClinicTable = $('#allDoctorsClinicTable').DataTable({
			responsive: true,
			ajax: {
				url: "../../theGoodShepherd/doctor/certified/clinic/"+newAppointment.appTypeName+"/"+newAppointment.date+"/"+clinicId,
				dataSrc: ''
			},
			columns: [
				{ data: 'name'},
				{ data: 'surname'},
				{ data: 'rating'},
				{
					data: null,
					render: function (data) {
						if (data.availableTimes == null) {
							return 'Specify date and appointment type to view available times.'
						}
						var availableTimes = data.availableTimes == null ? [] : (data.availableTimes instanceof Array ? data.availableTimes : [data.availableTimes])
						var options = ""
						for (var i = 0; i < availableTimes.length; i++) {
							options += '<option value="'+availableTimes[i]+'">'+availableTimes[i]+':00</option>'
						}
						return '<select class="form-control input-height available-times" id="time'+data.id+'">' + 
								options + '</select>'
					}
				},
				{
					data: null,
					render: function (data) {
						if (newAppointment.appTypeName && newAppointment.date && newAppointment.appDuration) {
							var button = '<button id="'+data.id+'" name="' + data.name + ' ' + data.surname + '"class="btn btn-info table-button-doctor">Schedule</button>';
							return button;
						} else {
							return "Choose all parameters to schedule"
						}
					}
				}
			]
		})
	} else {
		// jeste inicijalizovana
		allDoctorsClinicTable.ajax.url("../../theGoodShepherd/doctor/certified/clinic/"+newAppointment.appTypeName+"/"+newAppointment.date+"/"+clinicId)
		allDoctorsClinicTable.ajax.reload()
	}
}

function filterAllDoctors() {
	var nameV = $('#allDoctorName').val()
	var surnameV = $('#allDoctorSurname').val()
	var ratingV = $('#allDoctorRating').val()
	
	allDoctorsClinicTable
	.column(0).search(nameV)
	.column(1).search(surnameV)
	.column(2).search(ratingV)
    .draw();
}

function createAppointment(doctorId, time) {
	newAppointment.doctorId = doctorId;
	newAppointment.time = time
	// prikaz svih podataka
	$('#date').text(newAppointment.date)
	$('#time').text(newAppointment.time + ":00")
	$('#duration').text(newAppointment.appDuration)
	$('#appType').text(newAppointment.appTypeName)
	$('#doctor').text(newAppointment.doctorName)
	$('#clinic').text(newAppointment.clinicName)
	$('.content').hide()
	$('.patient-confirm-app').show()
	document.body.scrollTop = 0
    document.documentElement.scrollTop = 0
}

function fillProfile(data){
	// basic information
	$('.content').hide()
    $('.patient-profile').show()
	$(".fullName").text(data.name + " " + data.surname)
    $("#email").text(data.email)
    $("#gender").text(data.gender)
    $("#dateOfBirth").text(data.dateOfBirth)
    $("#securityNumber").text(data.securityNumber)
    if(data.phoneNumber == ''){
		$("#phoneNumber").text('/')
	}else{
		$("#phoneNumber").text(data.phoneNumber)
	}
	$("#securityNumber").text(data.securityNumber)
	var adresa = data.address + ", " + data.city + ", " + data.country
	if(adresa == ', , '){
		$("#address").text('/')
	}else{
		$("#address").text(adresa)
	}
	// medical reports
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
		medicalReportTable.clear().rows.add(data.medicalRecords.medicalReports).draw();
	}
	$("#emailEdit").val(data.email)
	$("#nameEdit").val(data.name)
	$("#surnameEdit").val(data.surname)
	$("#genderEdit").val(data.gender);
	$("#dateOfBirthEdit").val(data.dateOfBirth)
	$("#phoneNumberEdit").val(data.phoneNumber)
	$("#securityNumberEdit").val(data.securityNumber)
	$("#addressEdit").val(data.address)
	$("#cityEdit").val(data.city)
	$("#countryEdit").val(data.country)
}

function fillEditForm() {
	// fill edit form
	$("#emailEdit").val($("#email").text())
	var fullName = $(".fullName").text().split(" ")
	$("#nameEdit").val(fullName[0])
	$("#surnameEdit").val(fullName[2])
	$("#genderEdit").val($("#gender").text());
	$("#dateOfBirthEdit").val($("#dateOfBirth").text())
	$("#phoneNumberEdit").val($("#phoneNumber").text())
	var fullAddress = $("#address").text().split(", ")
	$("#addressEdit").val(fullAddress[0])
	$("#cityEdit").val(fullAddress[1])
	$("#countryEdit").val(fullAddress[2])
}

/*-----------------------------------------------------*/
function initialiseFinishedApps() {
	if (!$.fn.DataTable.isDataTable('#finishedAppsTable')) {
		finishedAppsTable = $('#finishedAppsTable').DataTable({
			ajax: {
				url: "../../theGoodShepherd/appointment/allPatientFinished",
				dataSrc: ''
			},
			columns: [
				{ data: 'appType'},
				{ data: 'date'},
				{
					data: null,
					render: function(data) {
						return data.startTime + ":00"
					}
				},
				{
					data: null,
					render: function(data) {
						return data.endTime + ":00"
					}
				},
				{ data: 'ordination'},
				{
					data: null,
					render: function(data) {
						return data.price + " &euro;"
					}
				},
				{
					data: null,
					render: function(data) {
						if (data.discount) {
							return data.discount + "%"
						} else {
							return "0%"
						}
					}
				},
				{ data: 'doctor'}
			]
		})
	} else {
		finishedAppsTable.ajax.reload()
	}
}
function initialiseConfirmedApps() {
	if (!$.fn.DataTable.isDataTable('#confirmedAppsTable')) {
		confirmedAppsTable = $('#confirmedAppsTable').DataTable({
			ajax: {
				url: "../../theGoodShepherd/appointment/allPatientConfirmed",
				dataSrc: ''
			},
			columns: [
				{ data: 'appType'},
				{ data: 'date'},
				{
					data: null,
					render: function(data) {
						return data.startTime + ":00"
					}
				},
				{
					data: null,
					render: function(data) {
						return data.endTime + ":00"
					}
				},
				{ data: 'ordination'},
				{
					data: null,
					render: function(data) {
						return data.price + " &euro;"
					}
				},
				{
					data: null,
					render: function(data) {
						if (data.discount) {
							return data.discount + "%"
						} else {
							return "0%"
						}
					}
				},
				{ data: 'doctor'},
				{ 
					data: null,
					render: function(data) {
						if (data.canCancel) {
							return '<button id="'+data.id+'" class="table-action btn btn-info cancel-app-btn">Cancel</button>'
						} else {
							return 'You can no longer cancel this appointment.'
						}
					}
				}
			]
		})
	} else {
		confirmedAppsTable.ajax.reload()
	}
}
// cancel appointment
function cancelAppointment(appId) {
	$.ajax({
		type : "POST",
		url : "../../theGoodShepherd/appointment/cancel/"+appId,
		contentType: "application/json",
		success : function(output)  {
			alert("You succesfully canceled an appointment!")
			confirmedAppsTable.ajax.reload()
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}

function initialiseUnconfirmedApps() {
	if (!$.fn.DataTable.isDataTable('#unconfirmedAppsTable')) {
		unconfirmedAppsTable = $('#unconfirmedAppsTable').DataTable({
			ajax: {
				url: "../../theGoodShepherd/appointment/allPatientUnconfirmed",
				dataSrc: ''
			},
			columns: [
				{ data: 'appType'},
				{ data: 'date'},
				{
					data: null,
					render: function(data) {
						return data.startTime + ":00"
					}
				},
				{
					data: null,
					render: function(data) {
						return data.endTime + ":00"
					}
				},
				{ data: 'ordination'},
				{
					data: null,
					render: function(data) {
						return data.price + " &euro;"
					}
				},
				{
					data: null,
					render: function(data) {
						if (data.discount) {
							return data.discount + "%"
						} else {
							return "0%"
						}
					}
				},
				{ data: 'doctor'},
				{ data: null,
					render: function(data){
						return '<button name="confirmApp" class="btn btn-info add-button" onclick="confirmApp('+ data.id +')">Confirm</button>' +
						'<button name="declineApp" class="btn btn-info add-button" onclick="declineApp('+ data.id +')">Decline</button>'
					}
				}
			]
		})
	} else {
		unconfirmedAppsTable.ajax.reload()
	}
}
// prihvatanje pregleda
function confirmApp(appId) {
	$.ajax({
		type : "POST",
		url : "../../theGoodShepherd/appointment/confirmApp/"+appId,
		contentType: "application/json",
		success : function(output) {
			if(output.typeOf == "appointment"){
				colorEvent = '#48baf7' 
			}else{
				colorEvent = '#2aebb4'
			}
	        calendar.addEvent({
				id: output.id,
		        title: output.appType + " " + output.typeOf + ' with doctor ' + output.doctor,
		        description: output.appType + " " + output.typeOf + ' with doctor ' + output.doctor,
		        start: output.date+"T0"+output.startTime+":00:00",
		        end: output.date+"T0"+output.endTime+":00:00",
		        color: colorEvent
		    });
			alert("Succesfully accepted an appointment!\nYou can view all your upcoming appointments in the 'Upcoming appointments' tab.")
			//calendar.render();
			unconfirmedAppsTable.ajax.reload()
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}
// odbijanje pregleda
function declineApp(appId) {
	$.ajax({
		type : "POST",
		url : "../../theGoodShepherd/appointment/declineApp/"+appId,
		contentType: "application/json",
		success : function() {
			alert("Succesfully declined an appointment!\nThis appointment does no longer exist among yours.")
			unconfirmedAppsTable.ajax.reload()
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}
/*-----------------------------------------------------*/

function initialiseVisitedDoctorsTable() {
	if (!$.fn.DataTable.isDataTable('#visitedDoctorsTable')) {
		visitedDoctorsTable = $('#visitedDoctorsTable').DataTable({
			ajax: {
				url: "../../theGoodShepherd/patient/getVisitedDoctors",
				dataSrc: ''
			},
			columns: [
				{ data: 'fullName'},
				{ data: 'specialization'},
				{ data: 'clinicName'},
				{ data: 'rating'},
				{ data: null,
					render: function(data){
						var select = '<select class="table-action form-control input-height" id="ratingD'+data.id+'">'
									+ '<option value="'+1+'">1</option>'
									+ '<option value="'+2+'">2</option>'
									+ '<option value="'+3+'">3</option>'
									+ '<option value="'+4+'">4</option>'
									+ '<option value="'+5+'">5</option>'
									+ '</select>'
						var btn =  '<button id="'+data.id+'" class="table-action btn btn-info rate-doctor-btn">Rate</button>'
						var div = '<div class="table-action-btns">'+select+btn+'</div>'
						if (data.rated) {
							return "You already rated this doctor."
						} else {
							return div
						}
					}
				}
			]
		})
	} else {
		visitedDoctorsTable.ajax.reload()
	}
}
function rateDoctor(doctorId, rating) {
	$.ajax({
		type : "POST",
		url : "../../theGoodShepherd/patient/rateDoctor/"+doctorId+"/"+rating,
		contentType: "application/json",
		success : function(output)  {
			alert("You succesfully rated a doctor!")
			visitedDoctorsTable.ajax.reload()
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}
function initialiseVisitedClinicsTable() {
	if (!$.fn.DataTable.isDataTable('#visitedClinicsTable')) {
		visitedClinicsTable = $('#visitedClinicsTable').DataTable({
			ajax: {
				url: "../../theGoodShepherd/patient/getVisitedClinics",
				dataSrc: ''
			},
			columns: [
				{ data: 'name'},
				{ data: 'fullAddress'},
				{ data: 'rating'},
				{ data: null,
					render: function(data){
						var select = '<select class="table-action form-control input-height" id="ratingC'+data.id+'">'
									+ '<option value="'+1+'">1</option>'
									+ '<option value="'+2+'">2</option>'
									+ '<option value="'+3+'">3</option>'
									+ '<option value="'+4+'">4</option>'
									+ '<option value="'+5+'">5</option>'
									+ '</select>'
						var btn = '<button id="'+data.id+'" class="table-action btn btn-info rate-clinic-btn">Rate</button>'
						var div = '<div class="table-action-btns">'+select+btn+'</div>'
						if (data.rated) {
							return "You already rated this clinic."
						} else {
							return div
						}
					}
				}
			]
		})
	} else {
		visitedClinicsTable.ajax.reload()
	}
}
function rateClinic(clinicId, rating) {
	$.ajax({
		type : "POST",
		url : "../../theGoodShepherd/patient/rateClinic/"+clinicId+"/"+rating,
		contentType: "application/json",
		success : function(output)  {
			alert("You succesfully rated a clinic!")
			visitedClinicsTable.ajax.reload()
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}
/*-----------------------------------------------------*/

function logInPatient(email, password) {
	$.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/patient/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(output)  {
			currentIsPatient = true;
			sessionStorage.setItem('nameSurname', output.name + ' ' + output.surname);
			window.location.href = "html/home-pages/patient_hp.html"
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