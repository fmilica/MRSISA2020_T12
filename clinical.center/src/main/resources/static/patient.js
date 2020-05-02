
$(document).ready(function() {

	// inicijalizacija tabele sa klinikama
	var clinicsTable = $('#clinicsTable').DataTable({
		responsive: true,
		ajax: {
			url: "../../theGoodShepherd/clinics",
			dataSrc: ''
		},
		columns: [
			{ data: 'name'},
			//{ data: 'rating'},
			{ data: 'address'},
			//{ data: 'price'},
			{
				data: null,
				render: function (data) {
					var button = '<button id="'+data.id+'" class="btn btn-info table-button" name="'+data.name+'">View doctors</button>';
					// pretplata na dogadjaj klika dugmeta klinike
					$('.table-button').click(function(e) {
						// inicijalizacija tabele sa doktorima odabrane klinike
						var clinicId = $(this).attr('id')
						var clinicName = $(this).attr('name')
						initialiseClinicDoctors(clinicId, clinicName)
					})
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
	// dodavanje imena klinike u naslov tabele
	$('#doctorsClinic').append(clinicName)
	// inicijalizacija same tabele
	var doctorsClinicTable = $('#doctorsClinicTable').DataTable({
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
				return '<select class="form-control input-height available-times" id="'+data.id+'">' + 
							'<option selected="selected" value="12:00">12:00</option>' + 
							'<option value="14:00">14:00</option>' +
					   '</select>'
				}
			}]
	})
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