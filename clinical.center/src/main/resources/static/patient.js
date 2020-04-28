function logInPatient(email, password){
	$.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/patient/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(response)  {
			alert("Patient logged in")
		},
		error : function(response) {
			alert("Patient log in failed")
		}
	})
}