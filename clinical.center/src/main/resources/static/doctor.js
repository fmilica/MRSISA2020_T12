function logInDoctor(email, password){
		
	$.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/doctor/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(response)  {
			alert("Doctor logged in")
			window.location.href = "html/home-pages/doctor_hp.html"
		},
		error : function(response) {
			alert("Doctor log in failed")
		}
	})
}