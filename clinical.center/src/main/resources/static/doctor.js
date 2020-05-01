$(document).ready( function () {
	
	var table = $('#doctorTable').DataTable({
		"sAjaxSource": "../../theGoodShepherd/doctor/getAll",
		"sAjaxDataProp": "",
		"order": [[ 0, "asc" ]],
		"aoColumns": [
		    { "mData": "id"},
		    { "mData": "email" },
			{ "mData": "password" },
			{ "mData": "name" },
			{ "mData": "surname" },
			{ "mData": "gender" },
			{ "mData": "dateOfBirth" },
			{ "mData": "address" },
			{ "mData": "city" },
			{ "mData": "country" },
			{ "mData": "phoneNumber" },
			{ "mData": "securityNumber" },
			{ "mData": "specialization" }
		]
	})
	
})

function logInDoctor(email, password){
		
	$.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/doctor/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(response)  {
			window.location.href = "html/home-pages/doctor_hp.html"
		},
		error : function(response) {
			alert("Doctor log in failed")
		}
	})
}