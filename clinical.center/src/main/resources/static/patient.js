function logInPatient(email, password){
	
	//$(location).attr('href', 'patient_hp.html');
	window.location.href = "html/home-pages/patient_hp.html"
	/*$.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/patient/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(response)  {
			alert("Patient logged in")
			$(location).attr('href', 'patient_hp.html');
		},
		error : function(response) {
			alert("Patient log in failed")
		}
	})*/
}