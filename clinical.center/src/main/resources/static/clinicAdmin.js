var ordinationTable;
var appTypeTable;
var doctorTable;
var examReqTable;
var examRoomTable;

var changedExamTime = false;

var examReq = {
	reqId: "",
	ordId: "",
	date: "",
	time: ""
}

function logInClinicAdmin(email, password){
	
    $.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/clinicAdmin/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(output)  {
			sessionStorage.setItem('nameSurname', output.name + ' ' + output.surname);
			window.location.href = "html/home-pages/clinic_admin_hp.html"
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}

$(document).ready(function() {
	
	/*View all ordinations*/
	$("#clinicOrdinations").on('click', function(event){
		event.preventDefault()
		// inicijalizujemo je ako vec nismo
		if (!$.fn.DataTable.isDataTable('#ordinationTable')) {
			ordinationTable = $('#ordinationTable').DataTable({
				ajax: {
					url: "../../theGoodShepherd/ordination/getClinicsOrdinations",
					dataSrc: ''
				},
				columns: [
					{ data: 'name'},
					{ data: 'ordinationNumber'},
					{ data: 'type'}]
				})
		}
	})

	/*Adding new ordination*/
	$("#submit_ordination").on('click', function(event){
		event.preventDefault()
		
		var nameV = $("#ordination_name").val()
		var numberV = $("#ordination_number").val()
		var typeV = $("#ordination_type").val()
		
		if (!nameV || !numberV || !typeV) {
			alert("Not all required fields are filled!")
			return
		}

		if (isNaN(numberV)) {
			alert("Ordination number must be a number!")
			return
		}

		var ordination = {
			name: nameV,
			ordinationNumber: numberV,
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
				// ciscenje forme za novo dodavanje
				clearOrdinationForm()
				ordinationTable.ajax.reload();
				// scroll to top of page
				document.body.scrollTop = 0
				document.documentElement.scrollTop = 0
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
	})
	
	/*Cancel add ordination*/
	$("#cancel-ordination").on('click', function(event){
		event.preventDefault()
		clearOrdinationForm()
		$('.clinic-addOrdination').hide()
		$('.clinic-ordinations').show()
		hideValidate($("#ordination_number"))
		// scroll to top of page
		document.body.scrollTop = 0
  		document.documentElement.scrollTop = 0
	})

	/*Check if ordination number is number*/
	$("#ordination_number").on('blur', function(e){
		e.preventDefault()
		if(isNaN($("#ordination_number").val())){
			showValidate($("#ordination_number"))
		}
	})
	
	$("#ordination_number").on('click', function(e){
		hideValidate($("#ordination_number"))
	})
	
	/**********/

	/*View all appointment types*/
	$("#clinicAppTypes").on('click', function(event){
		
		$('#certifiedDoctors').select2();
		
		$.ajax({
			type : "GET",
			url : "../../theGoodShepherd/clinicAdmin/getDoctors",
			success : function(data) {
				$('#certifiedDoctors').empty();
				$.each(data, function(index, doctor) {
					$('#certifiedDoctors').append('<option value="'+doctor.id+'">'+doctor.name+' '+doctor.surname+'</option>')
					//$("#certifiedDoctors").append(new Option(doctor.name + " " + doctor.surname, doctor.id));
				})
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
		
		event.preventDefault()
		if (!$.fn.DataTable.isDataTable('#appTypeTable')) {
			appTypeTable = $('#appTypeTable').DataTable({
			ajax: {
				url: "../../theGoodShepherd/appointmentType/getClinicsTypes",
				dataSrc: ''
			},
			columns: [
				{ data: 'name'},
				{
					data: null,
					render: function (data) {
						return data.duration + " h";
					}
				},
				{
					data: null,
					render: function (data) {
						return data.price + " &euro;";
					}
				}]
			})
		}
	})
	
	/*Adding new appointment type*/
	$("#submit_appointment_type").on('click', function(event){
		
		event.preventDefault()
		
		var nameV = $("#appointment_name").val()
		var durationV = $("#appointment_duration").val()
		var priceV = $("#appointment_price").val()
		var doctorsV1 = $("#certifiedDoctors").val()

		if (!nameV || !durationV || !priceV) {
			alert("Not all required fields are filled!")
			return
		}

		if (isNaN(durationV)) {
			alert("Duration must be a number!")
			return
		}

		if (isNaN(priceV)) {
			alert("Price must be a number!")
			return
		}

		var doctorsV = []
		for (var i = 0; i < doctorsV1.length; i++) {
			doctorsV.push(parseInt(doctorsV1[i]))
		}

		var appType = {
			name: nameV,
			duration: durationV,
			price: priceV,
			doctors: doctorsV
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
				// ciscenje forme za novo dodavanje
				clearAppTypeForm()
				appTypeTable.ajax.reload()
				// scroll to top of page
				document.body.scrollTop = 0
				document.documentElement.scrollTop = 0
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
	})
	
	/*Cancel add appointment*/
	$("#cancel-appointment-type").on('click', function(event){
		event.preventDefault()
		clearAppTypeForm()
		$('.clinic-addAppType').hide()
		$('.clinic-appTypes').show()
		hideValidate($("#appointment_duration"))
		hideValidate($("#appointment_price"))
		// scroll to top of page
		document.body.scrollTop = 0
  		document.documentElement.scrollTop = 0
	})

	/*Check if appointment type duration and price are numbers*/
	$("#appointment_duration").on('blur', function(e){
		e.preventDefault()
		if(isNaN($("#appointment_duration").val())){
			showValidate($("#appointment_duration"))
		}
	})
	$("#appointment_duration").on('click', function(e){
		hideValidate($("#appointment_duration"))
	})
	$("#appointment_price").on('blur', function(e){
		e.preventDefault()
		if(isNaN($("#appointment_price").val())){
			showValidate($("#appointment_price"))
		}
	})
	$("#appointment_price").on('click', function(e){
		hideValidate($("#appointment_price"))
	})
	
	/**********/

	/*View all doctors */
	$("#clinicDoctors").on('click', function(event){
		event.preventDefault()
		// inicijalizujemo je ako vec nismo
		if (!$.fn.DataTable.isDataTable('#ordinationTable')) {
			doctorTable = $('#doctorTable').DataTable({
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
				{ data: 'specialization'},
				{ 
					data: null,
						render: function(data) {
							return data.startWork + ":00"
						}
				},
				{ 
					data: null,
						render: function(data) {
							return data.endWork + ":00"
						}
				},]
			})
		}
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
		var dateOfBirthV = $("#dateOfBirthDoctor").val()
		var specializationV = $("#specializationDoctor").val()
		var securityNumV = $("#securityNumberDoctor").val()
		var startWorkV = $("#startWorkDoctor").val()
		var endWorkV = $("#endWorkDoctor").val()
		var phoneNumberV = $("#phoneNumberDoctor").val()
		var addressV = $("#addressDoctor").val()
		var cityV = $("#cityDoctor").val()
		var countryV = $("#countryDoctor").val()
		
		if(!emailV || !nameV || !surnameV || !passwordV || !confirmPasswordV || !genderV ||
				!dateOfBirthV || !specializationV || !securityNumV || !startWorkV || !endWorkV){
			alert("Not all required fields are filled!")
			return;
		}
		
		if(passwordV != confirmPasswordV){
			alert("Passwords do not match!")
			return;
		}
		
		if(isNaN(securityNumV)){
			alert("Security number must be a number!")
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
			email: emailV,
			name: nameV,
			surname: surnameV,
			password: passwordV,
			gender: genderV,
			dateOfBirth: dateOfBirthV,
			securityNumber: securityNumV,
			startWork: startWorkV,
			endWork: endWorkV,
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
				alert("New doctor saved!")
				$('.content').hide()
				$('.clinic-doctors').show()
				// ciscenje forme za novo dodavanje
				clearDoctorForm()
				doctorTable.ajax.reload()
				// scroll to top of page
				document.body.scrollTop = 0
				document.documentElement.scrollTop = 0
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
	})
	
	/*Adding new doctor check if passwords match*/
	$("#passwordDoctor").on('blur', function(event){
		event.preventDefault()
		var pass = $("#passwordDoctor").val()
		var rep = $("#confirm-passwordDoctor").val()
		if(pass != rep){
			showValidate($("#passwordDoctor"))
			showValidate($("#confirm-passwordDoctor"))
		}else{
			hideValidate($("#passwordDoctor"))
			hideValidate($("#confirm-passwordDoctor"))
		}
	})
	$("#confirm-passwordDoctor").on('blur', function(event){
		event.preventDefault()
		var pass = $("#passwordDoctor").val()
		var rep = $("#confirm-passwordDoctor").val()
		if(pass != rep){
			showValidate($("#passwordDoctor"))
			showValidate($("#confirm-passwordDoctor"))
		}else{
			hideValidate($("#passwordDoctor"))
			hideValidate($("#confirm-passwordDoctor"))
		}
	})
	$("#passwordDoctor").on('focus', function(event){
		event.preventDefault()
		hideValidate($("#passwordDoctor"))
		hideValidate($("#confirm-passwordDoctor"))
	})
	$("#confirm-passwordDoctor").on('focus', function(event){
		event.preventDefault()
		hideValidate($("#passwordDoctor"))
		hideValidate($("#confirm-passwordDoctor"))
	})
	
	$("#cancel_doctor").on('click', function(event){
		event.preventDefault()
		clearDoctorForm()
		$('.clinic-addDoctor').hide()
		$('.clinic-doctors').show()
		hideValidate($("#passwordDoctor"))
		hideValidate($("#confirm-passwordDoctor"))
		hideValidate($("#securityNumberDoctor"))
		// scroll to top of page
		document.body.scrollTop = 0
  		document.documentElement.scrollTop = 0
	})
	
	/*Check if securityNumber is number*/
	$("#securityNumberDoctor").on('blur', function(e){
		e.preventDefault()
		if(isNaN($("#securityNumberDoctor").val())){
			showValidate($("#securityNumberDoctor"))
		}
	})
	$("#securityNumberDoctor").on('click', function(e){
		hideValidate($("#securityNumberDoctor"))
	})
	/*Check if start work is number*/
	$("#startWorkDoctor").on('blur', function(e){
		e.preventDefault()
		if(isNaN($("#startWorkDoctor").val())){
			showValidate($("#startWorkDoctor"))
		}
	})
	$("#startWorkDoctor").on('click', function(e){
		hideValidate($("#startWorkDoctor"))
	})
	/*Check if end work is number*/
	$("#endWorkDoctor").on('blur', function(e){
		e.preventDefault()
		if(isNaN($("#endWorkDoctor").val())){
			showValidate($("#endWorkDoctor"))
		}
	})
	$("#endWorkDoctor").on('click', function(e){
		hideValidate($("#endWorkDoctor"))
	})
	/**********/
	/*Appointment requests*/

	// pretplata svih elemenata sa klasom na klik
	$('body').on('click', 'button.table-button', function() {
		// odabrao je da zakaze neki pregled, sada je to zapamceno
		// podesi parametre koji je aktivan
		//TODO DA SKOCI NA POCETAK TABELE SA EXAMINATION ROOMS
		var examReqId = $(this).attr('id')
		examReq.reqId = examReqId
		var dateTime = $(this).attr('name')
		examReq.date = dateTime.split(" ")[0]
		examReq.time = dateTime.split(" ")[1]
		//da se popuni tabela slobodnim klinikama za taj dan
		viewExaminationRoomsForAppointmentRequests()
	});
	$('body').on('click', 'button.table-button-schedule', function() {
		// prikupljanje podataka i kreiranje pregleda
		var ordinationId = $(this).attr('id')
		var currentDate = $(this).attr('name')
		var time = $('#time'+ordinationId).val()
		scheduleOrdination(ordinationId, currentDate, time)
	});

	$('#clinicExamReq').click(function(event) {
		// tabela sa svim zahtevima
		event.preventDefault()
		// inicijalizujemo je ako vec nismo
		if (!$.fn.DataTable.isDataTable('#examReqTable')) {
			examReqTable = $('#examReqTable').DataTable({
				ajax: {
					url: "../../theGoodShepherd/appointmentRequest",
					dataSrc: ''
				},
				columns: [
					{ data: 'doctorFullName'},
					{ data: 'patientFullName'},
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
					{
						data: null,
						render: function (data) {
							var button = '<button id="'+data.id+'" name="'+data.date+" "+data.startTime+'" class="btn btn-info table-button">Choose request</button>';
							return button;
						}
					}]
			})
		}
	})
	
	/*Front filter for clinics*/
	$("#filterExamRoom").on('click', function(e){
		e.preventDefault()
		filterExaminationRooms()
	})

	/*// filtriranje soba za preglede
	$('#filterExamRoom').click(function(e) {
		e.preventDefault()

		var nameV = $('#examRoomName').val()
		var numberV = $('#examRoomNumber').val()
		var dateV = $('#examRoomDate').val()

		if (isNaN(numberV)) {
			alert("Ordination number must be a number.")
			return
		}

		// filtriranje klinika na beku
		if (nameV && numberV) {
			examRoomTable.ajax.url("../../theGoodShepherd/ordination/getClinicsExamination/"+nameV+"/"+numberV)
			examRoomTable.ajax.reload()
		} else if (!nameV && numberV) {
			examRoomTable.ajax.url("../../theGoodShepherd/ordination/getClinicsExaminationNumber/"+numberV)
			examRoomTable.ajax.reload()
		} else if (nameV && !numberV) {
			examRoomTable.ajax.url("../../theGoodShepherd/ordination/getClinicsExaminationName/"+nameV)
			examRoomTable.ajax.reload()
		} else if (!nameV && !numberV) {
			examRoomTable.ajax.url("../../theGoodShepherd/ordination/getClinicsExamination")
			examRoomTable.ajax.reload()
		}
	})*/
	
	function scheduleOrdination(ordinationId, currentDate, time) {
		examReq.ordId = ordinationId
		examReq.date = currentDate
		examReq.time = time
		alert("You scheduled an examination room for an appointment!")
		alert(JSON.stringify(examReq))
		alert("Milice, send mejl pacijentu da potvrdi ili odbije")
	}

})

function filterExaminationRooms(){
	
	var nameV = $('#examRoomName').val()
	var roomNumberV = $('#examRoomNumber').val()
	
	examRoomTable
    .column(0)
    .search(nameV)
    .draw();
	
	examRoomTable
    .column(1)
    .search(roomNumberV)
    .draw();
}

function viewExaminationRoomsForAppointmentRequests(){
	// tabela sa sobama za pregled
	if (!$.fn.DataTable.isDataTable('#examRoomTable')) {
		examRoomTable = $('#examRoomTable').DataTable({
			ajax: {
				url: "../../theGoodShepherd/ordination/getAvailableExaminationRooms/"+examReq.reqId,
				dataSrc: '',
				async: false
			},
			columns: [
				{ data: 'name'},
				{ data: 'ordinationNumber'},
				{ data: 'date'},
				{
					data: null,
					render: function (data) {
						var availableTimes = data.availableTimes == null ? [] : (data.availableTimes instanceof Array ? data.availableTimes : [data.availableTimes])
						
						if (data.date != examReq.date) {
							changedExamTime = true
						} else {
							if (availableTimes.length > 1) {
								changedExamTime = true
							} else {
								if (availableTimes[0] != examReq.time) {
									changedExamTime = true
								} else {
									changedExamTime = false
								}
							}
						}
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
						if (!examReq.reqId) {
							return "Specify examination request to schedule"
						}
						return '<button id="'+data.id+'" name="'+data.date+'" class="btn btn-info table-button-schedule">Schedule ordination</button>';
					}
				}]
		})
	} else {
		examRoomTable.ajax.url("../../theGoodShepherd/ordination/getAvailableExaminationRooms/"+examReq.reqId)
		examRoomTable.ajax.reload()
	}
	if (changedExamTime) {
		alert("No examination rooms available for given date and time.\nPlease choose new examination time.")
	}
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

function clearOrdinationForm() {
	$("#ordination_name").val('')
	$("#ordination_number").val('')
}

function clearAppTypeForm() {
	$("#appointment_name").val('')
	$("#appointment_duration").val('')
	$("#appointment_price").val('')
	$('#certifiedDoctors').val(null).trigger('change')
}

function clearDoctorForm() {
	$("#doctorEmail").val('')
	$("#firstNameDoctor").val('')
	$("#lastNameDoctor").val('')
	$("#passwordDoctor").val('')
	$("#confirm-passwordDoctor").val('')
	$("#securityNumberDoctor").val('')
	$("#startWorkDoctor").val('')
	$("#endWorkDoctor").val('')
	$("#phoneNumberDoctor").val('')
	$("#addressDoctor").val('')
	$("#cityDoctor").val('')
	$("#countryDoctor").val('')
}