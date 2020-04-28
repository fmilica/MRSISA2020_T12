function logInClinicAdmin(email, password){
    $.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/clinicAdmin/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(response)  {
			alert("Clinic admin logged in")
		},
		error : function(response) {
			alert("Clinic admin login failed")
		}
	})
}