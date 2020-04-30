function logInClinicAdmin(email, password){
	
    $.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/clinicAdmin/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(data,textStatus)  {
			window.location.href = "html/home-pages/clinic_admin_hp.html"
		},
		error : function(response) {
			alert("Clinic admin login failed")
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
			success : function(response)  {
				alert("Successfully added new ordination!")
				window.location.href = "../../html/home-pages/clinic_admin_hp.html"
			},
			error : function(response) {
				alert("Clinic admin login failed")
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
			success : function(response)  {
				alert("Successfully added new appointmentType!")
				window.location.href = "../../html/home-pages/clinic_admin_hp.html"
			},
			error : function(response) {
				alert("Clinic admin login failed")
			}
		})
	})
	
	$("#cancel-appointment-type").on('click', function(event){
		event.preventDefault()
		
		$("#ordination_name").val('')
		
	})

})