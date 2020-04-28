function logInClinicalCentreAdmin(email, password){
  $.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/clinicalCenterAdmin/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(response)  {
			alert("Clinical center admin logged in")
		},
		error : function(response) {
			alert("Clinical center admin login failed")
		}
	})
}