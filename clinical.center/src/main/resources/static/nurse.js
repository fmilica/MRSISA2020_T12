function logInNurse(email, password){

	$.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/nurse/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(response)  {
			alert("Nurse logged in")
			window.location.href = "html/home-pages/nurse_hp.html"
		},
		error : function(response) {
			alert("Patient log in failed")
		}
	})
}