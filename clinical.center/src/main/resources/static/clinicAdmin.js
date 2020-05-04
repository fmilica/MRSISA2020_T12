function logInClinicAdmin(email, password){
	
    $.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/clinicAdmin/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(response)  {
			window.location.href = "html/home-pages/clinic_admin_hp.html"
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}

$(document).ready(function() {
	
	/*View all appointment types*/
	$("#clinicOrdinations").on('click', function(event){
		event.preventDefault()
		var ordinationTable = $('#ordinationTable').DataTable({
		ajax: {
			url: "../../theGoodShepherd/ordination/getClinicsOrdinations",
			dataSrc: ''
		},
		columns: [
			{ data: 'name'},
			{ data: 'type'}]
		})
	})

	/*Adding new operating room*/
	$("#submit_ordination").on('click', function(event){
		event.preventDefault()
		
		var nameV = $("#ordination_name").val()
		var typeV = $("#ordination_type").val()
		
		var ordination = {
			name: nameV,
			type: typeV
		}
		
		$.ajax({
			type : "POST",
			async: false,
			url : "../../theGoodShepherd/ordination/addNewOrdination",
			contentType : "application/json",
			dataType : "json",
			data : JSON.stringify(ordination),
			success : function()  {
				alert("Successfully added new ordination!")
				$('.content').hide()
        		$('.clinic-ordinations').show()
				var table = $('#ordinationTable').DataTable();
				table.ajax.reload();
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
	})
	
	$("#cancel-ordination").on('click', function(event){
		event.preventDefault()
		
		$("#ordination_name").val('')
		
	})
	
	/**********/

	/*View all appointment types*/
	$("#clinicAppTypes").on('click', function(event){
		event.preventDefault()
		var appTypeTable = $('#appTypeTable').DataTable({
		ajax: {
			url: "../../theGoodShepherd/appointmentType/getClinicsTypes",
			dataSrc: ''
		},
		columns: [
			{ data: 'name'},
			{ data: 'duration'},
			{ data: 'price'}]
		})
	})
	
	/*Adding new appointment type*/
	$("#submit_appointment_type").on('click', function(event){
		
		event.preventDefault()
		
		var nameV = $("#appointment_name").val()
		var durationV = $("#appointment_duration").val()
		var priceV = $("#appointment_price").val()

		var appType = {
			name: nameV,
			duration: durationV,
			price: priceV
		}

		$.ajax({
			type : "POST",
			async: false,
			url : "../../theGoodShepherd/appointmentType/addNewAppointmentType",
			contentType : "application/json",
			dataType : "json",
			data : JSON.stringify(appType),
			success : function()  {
				alert("Successfully added new appointment type!")
				$('.content').hide()
        		$('.clinic-appTypes').show()
				var table = $('#appTypeTable').DataTable();
				table.ajax.reload();
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
	})
	
	$("#cancel-appointment-type").on('click', function(event){
		event.preventDefault()
		$('.clinic-addAppType').hide()
		$('.clinic-appTypes').show()
	})
	
	/**********/

	/*View all doctors */
	$("#clinicDoctors").on('click', function(event){
		event.preventDefault()
		var doctorsTable = $('#doctorTable').DataTable({
		ajax: {
			url: "../../theGoodShepherd/clinicAdmin/getDoctors",
			dataSrc: ''
		},
		columns: [
			{ data: 'email'},
			{ data: 'name'},
			{ data: 'surname'},
			{ data: 'gender'},
			{ data: 'dateOfBirth'},
			{ data: 'address'},
			{ data: 'city'},
			{ data: 'country'},
			{ data: 'phoneNumber'},
			{ data: 'securityNumber'},
			{ data: 'specialization'}]
		})
	})
	
	/*Adding new doctor*/
	$("#submit_doctor").on('click', function(event){
		event.preventDefault()
		
		var emailV = $("#doctorEmail").val()
		var nameV = $("#firstNameDoctor").val()
		var surnameV = $("#lastNameDoctor").val()
		var passwordV = $("#passwordDoctor").val()
		var confirmPasswordV = $("#confirm-passwordDoctor").val()
		var genderV = $("#genderDoctor").val()
		var dateOfBirthV = "11.11.1978."
		var specializationV = $("#specializationDoctor").val()
		var securityNumV = $("#securityNumberDoctor").val()
		var phoneNumberV = $("#phoneNumberDoctor").val()
		var addressV = $("#addressDoctor").val()
		var cityV = $("#cityDoctor").val()
		var countryV = $("#countryDoctor").val()
		
		var newDoctor = {
			email: emailV,
			name: nameV,
			surname: surnameV,
			password: passwordV,
			gender: genderV,
			dateOfBirth: dateOfBirthV,
			securityNumber: securityNumV,
			phoneNumber: phoneNumberV,
			address: addressV,
			city: cityV,
			country: countryV,
			specialization: specializationV
		}
		
		$.ajax({
			type : "POST",
			url : "../../theGoodShepherd/doctor/addNewDoctor",
			contentType : "application/json",
			dataType : "json",
			data : JSON.stringify(newDoctor),
			success : function(response){
				// vrati ga na pregled svih doktora
				$('.content').hide()
        		$('.clinic-doctors').show()
				alert("New doctor saved!")
				var table = $('#doctorTable').DataTable();
				table.ajax.reload();
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
	})
	
	$("#cancel_doctor").on('click', function(event){
		event.preventDefault()
		$('.clinic-addDoctor').hide()
		$('.clinic-doctors').show()
	})

	/**********/

	// pretplata svih elemenata sa klasom na klik
	$('body').on('click', 'button.table-button', function() {
		// odabrao je da zakaze neki pregled, sada je to zapamceno
		alert("You choose this appointment to schedule!")
		// podesi parametre koji je aktivan
		var examReqId = $(this).attr('id')
	});
	$('body').on('click', 'button.table-button-examReq', function() {
		// prikupljanje podataka i kreiranje pregleda
		var doctorId = $(this).attr('id')
		var time = $('#time'+doctorId).val()
		createAppointment(doctorId, time)
	});

	$('#clinicExamReq').click(function(event) {
		// tabela sa svim zahtevima
		event.preventDefault()
		var examReqTable = $('#examReqTable').DataTable({
			ajax: {
				// napraviti transportni objekat koji moze da se salje dovde
				// ne znam kako drugacije bi mogao da dobavlja ove informacije
				// mislim da bi pucalo sve
				// a samo nam trebaju stringovi realno
				// neki mali transportni objekat, da se ne vidi, negde sakriven...
				// mada i on bi pucao jer bi se getovale vrednosti iz onog
				// ne bi trebalo da puca ako se kreira u okviru servisa, tada je, mislim, konekcija i dalje otvorena
				// objekat recimo ovakav
				/*
				var appointmentReq = {
					id: "",
					date: "",
					appType: "",
					doctorId: "",
					time: ""
				}
				*/
				url: "../../theGoodShepherd/ordination/getClinicsOrdinations",
				dataSrc: ''
			},
			columns: [
				{ data: 'appointmentType'},
				{ data: 'doctor'},
				{ data: 'date'},
				{ data: 'time'},
				{
					data: null,
					render: function (data) {
						var button = '<button id="'+data.id+'" class="btn btn-info table-button">Choose</button>';
						return button;
					}
				}]
		})

		// tabela sa sobama za pregled
		var examRoomTable = $('#examRoomTable').DataTable({
			ajax: {
				url: "../../theGoodShepherd/ordination/getClinicsOrdinations",
				dataSrc: ''
			},
			columns: [
				{ data: 'name'},
				{ data: 'number'},
				{ data: 'firstFreeTime'},
				{
					data: null,
					render: function (data) {
						var button = '<button id="'+data.id+'" class="btn btn-info table-button">Schedule</button>';
						return button;
					}
				}]
		})

		
	})

})