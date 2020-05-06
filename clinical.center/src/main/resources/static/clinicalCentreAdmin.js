var clinicAdminsTable;
var clinicsTable;

$(document).ready(function() {
	
	/*Clinic options when adding clinic admin*/
	$('#addClinicAdmin').click(function() {
		$.ajax({
            type : "GET",
            url : "/theGoodShepherd/clinics",
            dataType: "json",
            success : function(data)  {
            	$("#availableClinics").empty()
            	$.each(data, function(index, clinic) {
					$("#availableClinics").append(new Option(clinic.name, clinic.id));
				})
            },
            error : function(response) {
            	alert(response.responseJSON.message)
            }
        })
	})
	
	/*View all clinic admins*/
	$('#clinicAdmins').click(function(event) {
		event.preventDefault()
		// inicijalizujemo je ako vec nismo
		if (!$.fn.DataTable.isDataTable('#clinicAdminsTable')) {
			clinicAdminsTable = $('#clinicAdminsTable').DataTable({
				ajax: {
					url: "../../theGoodShepherd/clinicAdmin",
					dataSrc: ''
				},
				columns: [
					{ data: 'clinicAdmin.email'},
					{ data: 'clinicAdmin.name'},
					{ data: 'clinicAdmin.surname'},
					{ data: 'clinicAdmin.gender'},
					{ data: 'clinicAdmin.dateOfBirth'},
					{ data: 'clinicAdmin.address'},
					{ data: 'clinicAdmin.city'},
					{ data: 'clinicAdmin.country'},
					{ data: 'clinicAdmin.phoneNumber'},
					{ data: 'clinicAdmin.securityNumber'},
					{
						data: null,
						render: function (data) {
							return data.clinicName;
						}
					}]
				})
		}
	})
	/*Add new clinic admin*/
	$('#add_clinicAdmin').click(function(e){
        e.preventDefault()
        
        var name = $('#nameC').val()
        var surname = $('#surnameC').val()
        var country = $('#countryC').val()
        var city = $('#cityC').val()
        var address = $('#addressC').val()
        var phone = $('#phoneC').val()
        var security = $('#securityC').val()
        var username = $('#emailC').val()
        var password = $('#passwordC').val()
        var repeatPassword = $("#passwordRepeatC").val()
        var clinicId = $('#availableClinics').val()
        var gender = $('#genderC').val()
        var birth = $('#dateBirthC').val()
        
        if(!name || !surname || !country || !city || !address || !phone || !security || !username ||
        		!password || !repeatPassword || !clinicId || !gender || !birth){
        	alert("All required fields must be filled!")
        	return;
        }
        
        if(password != repeatPassword){
        	return;
        }
        
        if(isNaN(security)){
        	return;
        }
        var dateObject = new Date(birth);
        var currentDate = new Date();
        
        if(currentDate < dateObject){
            alert("Wrong date of birth!")
            return;
        }
        
        $.ajax({
            type : "POST",
            url : "/theGoodShepherd/clinicAdmin/addNewClinicAdmin/" + clinicId,
            contentType : "application/json",
            dataType: "json",
            data : JSON.stringify({
                "email" : username,
                "password" : password,
                "name" : name,
                "surname" : surname,
                "gender" : gender,
                "dateOfBirth" : birth,
                "address" : address,
                "city": city,
                "country" : country,
                "phoneNumber" : phone,
                "securityNumber" : security
            }),
            success : function(response)  {
            	alert("New clinic admin added!")
            	
            	$('#emailC').val('')
		    	$('#passwordC').val('')
		    	$("#passwordRepeatC").val('')
		    	$('#nameC').val('')
		    	$('#surnameC').val('')
		        $('#countryC').val('')
		        $('#cityC').val('')
		        $('#addressC').val('')
		        $('#phoneC').val('')
		        $('#securityC').val('')
		        $('#clinicName').val('')
		        $('#genderC').val('')
		        $('#dateBirthC').val('')
		        
            	$('.content').hide()
        		$('.clinic-admins').show()
            	clinicAdminsTable.ajax.reload();
            },
            error : function(response) {
            	alert(response.responseJSON.message)
            }
        })
    })
    
    /*Check if passwords match*/
    $("#passwordC").on('blur', function(event){
		event.preventDefault()
		var pass = $("#passwordC").val()
		var rep = $("#passwordRepeatC").val()
		if(pass != rep){
			showValidate($("#passwordC"))
			showValidate($("#passwordRepeatC"))
		}else{
			hideValidate($("#passwordC"))
			hideValidate($("#passwordRepeatC"))
		}
	})
	$("#passwordRepeatC").on('blur', function(event){
		event.preventDefault()
		var pass = $("#passwordC").val()
		var rep = $("#passwordRepeatC").val()
		if(pass != rep){
			showValidate($("#passwordC"))
			showValidate($("#passwordRepeatC"))
		}else{
			hideValidate($("#passwordC"))
			hideValidate($("#passwordRepeatC"))
		}
	})
	$("#passwordC").on('focus', function(event){
		event.preventDefault()
		hideValidate($("#passwordC"))
		hideValidate($("#passwordRepeatC"))
	})
	$("#passwordRepeatC").on('focus', function(event){
		event.preventDefault()
		hideValidate($("#passwordC"))
		hideValidate($("#passwordRepeatC"))
	})
	
	/*Check if securityNumber is number*/
	$("#securityC").on('blur', function(e){
		e.preventDefault()
		if(isNaN($("#securityC").val())){
			showValidate($("#securityC"))
		}
	})
	$("#securityC").on('click', function(e){
		hideValidate($("#securityC"))
	})
    
    $('#cancel_clinicAdmin').click(function(e){
		e.preventDefault()
		hideValidate($("#securityC"))
    	hideValidate($("#passwordC"))
		hideValidate($("#passwordRepeatC"))
    	$('#emailC').val('')
    	$('#passwordC').val('')
    	$("#passwordRepeatC").val('')
    	$('#nameC').val('')
    	$('#surnameC').val('')
        $('#countryC').val('')
        $('#cityC').val('')
        $('#addressC').val('')
        $('#phoneC').val('')
        $('#securityC').val('')
        $('#clinicName').val('')
        $('#genderC').val('')
        $('#dateBirthC').val('')
        $('.addClinicAdmin').hide()
		$('.clinic-admins').show()
    })
	
    
	/*View all clinics*/
    $('#clinics').click(function(event) {
		event.preventDefault()
		// inicijalizujemo je ako vec nismo
		if (!$.fn.DataTable.isDataTable('#clinicTable')) {
			clinicsTable = $('#clinicTable').DataTable({
				ajax: {
					url: "../../theGoodShepherd/clinics",
					dataSrc: ''
				},
				columns: [
					{ data: 'name'},
					{ data: 'address'},
					{ data: 'city'},
					{ data: 'country'},
					{ data: 'description'}]
			})
		}
	})
	
    /*Add new clinic*/
	$('#add_clinic').click(function(e){
        e.preventDefault()
        var name = $('#name').val()
        var country = $('#country').val()
        var city = $('#city').val()
        var address = $('#address').val()
        var description = $('#description').val()
        
        if( !name || !country || !city || !address || !description){
        	alert("All required fields must be filled!")
        	return;
        }
        
        $.ajax({
            type : "POST",
            url : "/theGoodShepherd/clinics/addNewClinic",
            contentType : "application/json",
            dataType: "json",
            data : JSON.stringify({
                "name" : name,
                "address" : address,
                "city": city,
                "country" : country,
                "description" : description
            }),
            success: function(data){
            	alert("Successfully added new clinic!")
            	$('#name').val('')
		        $('#country').val('')
		        $('#city').val('')
		        $('#address').val('')
		        $('#description').val('')
            	$('.content').hide()
        		$('.clinics').show()
            	clinicsTable.ajax.reload();
            },
            error : function(response) {
            	alert(response.responseJSON.message)
            }
        })
    })
    
    $('#cancel_clinic').click(function(e){
    	e.preventDefault()
    	$('#name').val('')
        $('#country').val('')
        $('#city').val('')
        $('#address').val('')
        $('#description').val('')
        $('.content').hide()
        $('.clinics').show()
    })
	
    $('#add_centreAdmin').click(function(e){
        e.preventDefault()
        
        var name = $('#nameCA').val()
        var surname = $('#surnameCA').val()
        var country = $('#countryCA').val()
        var city = $('#cityCA').val()
        var address = $('#addressCA').val()
        var phone = $('#phoneCA').val()
        var security = $('#securityCA').val()
        var username = $('#emailCA').val()
        var password = $('#passwordCA').val()
        var clinicalCentre = $('#clinicalCentre').val()
        var gender = $('#genderCA').val()
        var birth = new Date($('#dateBirthCA').val())
        
        $.ajax({
            type : "POST",
            url : "/theGoodShepherd/clinicalCenterAdmin/addNewClinicalCentreAdmin",
            contentType : "application/json",
            dataType: "json",
            data : JSON.stringify({
                "email" : username,
                "password" : password,
                "name" : name,
                "surname" : surname,
                "gender" : gender,
                "dateOfBirth" : birth,
                "address" : address,
                "city": city,
                "country" : country,
                "phoneNumber" : phone,
                "securityNumber" : security,
                "clinicalCentre" : {
                	"name" : clinicalCentre
                }
            }),
            success : function(response)  {
            	$('.content').hide()
        		$('.center-admins').show()
                alert("New clinical center admin added!")
            },
            error : function(response) {
            	alert(response.responseJSON.message)
            }
        })
    })
    
    $('#cancel_centreAdmin').click(function(e){
    	e.preventDefault()
    	$('#email').val('')
    	$('#password').val('')
    	$('#name').val('')
    	$('#surname').val('')
        $('#country').val('')
        $('#city').val('')
        $('#address').val('')
        $('#phone').val('')
        $('#security').val('')
        $('#clinicalCentre').val('')
        $('#gender').val('')
        $('#dateBirth').val('')
    })
})

function logInClinicalCentreAdmin(email, password){
	
	 $.ajax({
		type : "POST",
		async: false,
		url : "theGoodShepherd/clinicalCenterAdmin/logIn//" + email + "//" + password ,
		dataType: "json",
		success : function(output, status, xhr)  {
			sessionStorage.setItem('nameSurname', output.name + ' ' + output.surname);
			window.location.href = "html/home-pages/centar_admin_hp.html"
		},
		error : function(response) {
			alert(response.responseJSON.message)
		}
	})
}

function showValidate(input) {
    var thisAlert = $(input).parent();

    $(thisAlert).addClass('alert-validate');
}

function hideValidate(input) {
    var thisAlert = $(input).parent();

    $(thisAlert).removeClass('alert-validate');
}