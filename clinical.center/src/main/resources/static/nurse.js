
// ako su personalni podaci editovani, ponovo saljemo upit serveru
var edited = false;

$(document).ready(function(){

	// postavljanje maksimalnog datuma rodjenja koji se moze odabrati na danas
	document.getElementById("dateOfBirthEdit").max = new Date().toISOString().split("T")[0];

	/*------------------------------------------------------------------------*/
	/*View profile*/
	$('.n-profile').on('click', function(e){
		e.preventDefault()
		viewPersonalInformation()
	})

	/*Change personal information*/
	$('#editNurseProfile').on('click', function(e){
		e.preventDefault()
		$('.content').hide()
		$('.nurse-edit-profile').show()
		document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
		editPersonalInformation()
	})

	$('#confirmEdit').click(function(e) {
		e.preventDefault()
		saveUpdatedProfile()
	})

	$('#cancelEdit').click(function(e) {
		e.preventDefault()
		$('.content').hide()
		$('.nurse-profile').show()
		document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
	})

	/*Change password*/
	$('#changePasswordBtn').click(function(e) {
		e.preventDefault()
		changePassword()
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
	/*------------------------------------------------------------------------*/
})

function viewPersonalInformation(){
	// obracamo se serveru samo prvi put
	if ($("#fullNameBig").text() == "" || edited == true) {
		$.ajax({
			type : "GET",
			async: false,
			url : "../../theGoodShepherd/nurse/personalInformation" ,
			dataType: "json",
			success : function(output)  {
				$("#fullNameBig").text(output.name + " " + output.surname)
				$("#fullName").text(output.name + " " + output.surname)
				$("#email").text(output.email)
				$("#gender").text(output.gender)
				$("#dateOfBirth").text(output.dateOfBirth)
				$("#phoneNumber").text(output.phoneNumber)
				$("#securityNumber").text(output.securityNumber)
				$("#address").text(output.address + ", " + output.city + ", " + output.country)
			},
			error : function(response) {
				alert(response.responseJSON.message)
			}
		})
	}
}

function editPersonalInformation(){
	$.ajax({
		type : "GET",
		async: false,
		url : "../../theGoodShepherd/nurse/personalInformation" ,
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

	var newNurse = {
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
		url : "../../theGoodShepherd/nurse/editPersonalInformation" ,
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(newNurse),
		success : function(response)  {
			edited = true
			$(".n-profile").click()
			alert("Succesfully edited personal information.")
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}

function changePassword() {
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
		url : "../../theGoodShepherd/nurse/changePassword/"+passwordV,
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
}

function logInNurse(email, password){
	$.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/nurse/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(output)  {
			sessionStorage.setItem('nameSurname', output.name + ' ' + output.surname);
			window.location.href = "html/home-pages/nurse_hp.html"
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