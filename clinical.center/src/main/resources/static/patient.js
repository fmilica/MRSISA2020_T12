
$(document).ready(function() {

	// prikaz linka za registraciju
	$('#register').show()

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