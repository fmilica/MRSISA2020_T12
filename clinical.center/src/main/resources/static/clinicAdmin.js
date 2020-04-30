function logInClinicAdmin(email, password){
	
    $.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/clinicAdmin/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(data,textStatus,xhr )  {
			localStorage.setItem('current_user', data);
			alert(data.name)
			window.location.href = "html/home-pages/clinic_admin_hp.html"
		},
		error : function(response) {
			alert("Clinic admin login failed")
		}
	})
}


	/*Adding new operating room*/
	$("#submit_ordination").on('click', function(event){
		event.preventDefault()
		
		var name = $("#ordination_name").val()
		var type = $("#ordination_type").val()
		
		alert(name)
		alert(type)
		
		var clinicAdmin = localStorage.getItem('current_user')
		
		alert(clinicAdmin.name)
		alert(clinicAdmin.surname)
		
		/*$.ajax({
			type : "POST",
			async: false,
			url : "theGoodShepherd/clinicAdmin/logIn//" + email + "//" + password ,
			dataType: "json",
			success : function(response)  {
				window.location.href = "html/home-pages/clinic_admin_hp.html"
			},
			error : function(response) {
				alert("Clinic admin login failed")
			}
		})*/
	})
	
	$("#cancel-ordination").on('click', function(event){
		event.preventDefault()
		
		$("#ordination_name").val('')
		
	})
		
		
		