$(document).ready(function() {
	
	// date format : yyyy-mm-dd
	
	$('#register').on('click', function(e){
		e.preventDefault();

		var emailV = $('#email').val()
		var nameV = $('#name').val()
		var surnameV = $('#surname').val()
		var passwordV1 = $('#password').val()
		var passwordV2 = $('#passwordConfirm').val()
		var genderV = $('#gender').val()
		var dateOfBirthV = $('#dateOfBirth').val()
		var securityNumberV = $('#securityNumber').val()
		var phoneNumberV = $('#phoneNumber').val()
		var addressV = $('#address').val()
		var cityV = $('#city').val()
		var countryV = $('#country').val()
		
		// check if all fields are filled
		if (!emailV || !nameV || !surnameV || !passwordV1 || !passwordV2 ||
				!genderV || !dateOfBirthV || !securityNumberV || 
				!phoneNumberV || !addressV || !cityV || !countryV) {
			alert("All fields must be filled!")
			return
		}
		
		if (passwordV1 != passwordV2) {
			return
		}
		
		if (isNaN(securityNumberV)) {
			return;
		}
		
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
	
	/*When cancel is clicked*/
	$("#cancel").on('click', function(e){
		window.location.href = "../../loginP.html"
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
	
	/*Check if securityNumber is number*/
	
	$("#securityNumber").on('blur', function(e){
		e.preventDefault()
		if(isNaN($("#securityNumber").val())){
			showValidate($("#securityNumber"))
		}
	})
	
	$("#securityNumber").on('click', function(e){
		hideValidate($("#securityNumber"))
	})
})

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }