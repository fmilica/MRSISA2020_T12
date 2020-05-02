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

	
	/*Adding new appointment type*/
	$("#submit_appointment_type").on('click', function(event){
		event.preventDefault()
		
		var nameV = $("#appointment_name").val()

		var appType = {
			name: nameV
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
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
	})
	
	$("#cancel-appointment-type").on('click', function(event){
		event.preventDefault()
		
		$("#ordination_name").val('')
		
	})
	
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
})