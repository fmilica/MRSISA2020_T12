function logInDoctor(email, password){
	$.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/doctor/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(response)  {
			alert("Doctor logged in")
		},
		error : function(response) {
			alert("Doctor log in failed")
		}
	})
}