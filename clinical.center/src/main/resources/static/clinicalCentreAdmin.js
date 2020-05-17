var clinicAdminsTable;
var clinicalCenterAdminsTable;
var clinicsTable;
var requestsTable;
var diagnosisTable;
var medicineTable;
var choosenRequest;

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
					{ data: 'email'},
					{ data: 'name'},
					{ data: 'surname'},
					{ data: 'gender'},
					{ data: 'dateOfBirth'},
					{ data: 'address'},
					{ data: 'city'},
					{ data: 'country'},
					{ data: 'phoneNumber'},
					{ data: 'securityNumber'},
					{
						data: null,
						render: function (data) {
							return data.clinicName;
						}
					}]
				})
		} else {
			clinicAdminsTable.ajax.reload()
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
        var clinicId = $('#availableClinics').val()
        var gender = $('#genderC').val()
        var birth = $('#dateBirthC').val()
        
        if(!name || !surname || !security || !username ||
        		!password || !repeatPassword || !clinicId || !gender || !birth){
        	alert("All required fields must be filled!")
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
		        document.body.scrollTop = 0
		        document.documentElement.scrollTop = 0
            	$('.content').hide()
        		$('.clinic-admins').show()
            	clinicAdminsTable.ajax.reload();
            },
            error : function(response) {
            	alert(response.responseJSON.message)
            }
        })
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
	    document.body.scrollTop = 0
	    document.documentElement.scrollTop = 0
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
					{ data: null,
						render: function (data) {
							if(data.description == ''){
								return '/'
							}else{
								return data.description
							}
						}
					}
				]
			})
		} else {
			clinicsTable.ajax.reload()
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
        
        if( !name || !country || !city || !address){
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
	
    /*View all clinical center admins*/
     $('#centerAdmins').click(function(event) {
		event.preventDefault()
		// inicijalizujemo je ako vec nismo
		if (!$.fn.DataTable.isDataTable('#clinicalCenterAdmins')) {
			clinicalCenterAdmins = $('#clinicalCenterAdmins').DataTable({
				ajax: {
					url: "../../theGoodShepherd/clinicalCenterAdmin",
					dataSrc: ''
				},
				columns: [
					{ data: 'email'},
					{ data: 'name'},
					{ data: 'surname'},
					{ data: 'gender'},
					{ data: 'dateOfBirth'},
					{ data: 'address'},
					{ data: 'city'},
					{ data: 'country'},
					{ data: 'phoneNumber'},
					{ data: 'securityNumber'}]
			})
		} else {
			clinicalCenterAdmins.ajax.reload()
		}
	})
    /*Add new clinical center admin*/
    $('#addClinicalCenterAdmin').click(function(e) {
    	e.preventDefault()
    	$('.content').hide()
    	$('.addClinicalCenterAdmin').show()
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
        var birth = $('#dateBirthCA').val()
        
        if( !name || !surname || !security || !username || !password ||
        		!gender || !birth){
        	alert("All required fields must be filled!")
        	return;
        }

        var currentDate = new Date();
        var birthDate = new Date(birth);
        if(currentDate < birthDate){
            alert("Wrong date of birth!")
            return;
        }
        
        if(isNaN(security)){
			alert("Security number must be a number!")
			return;
		}
        
        
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
                "securityNumber" : security
            }),
            success : function(response)  {
            	$('.content').hide()
        		$('.center-admins').show()
                alert("New clinical center admin added!")
                clearAddClinicalCenterAdmin()
                clinicalCenterAdmins.ajax.reload()
            },
            error : function(response) {
            	alert(response.responseJSON.message)
            }
        })
    })
    
    $('#cancel_centreAdmin').click(function(e){
    	e.preventDefault()
    	clearAddClinicalCenterAdmin()
    })
    
    /*Check if securityNumber is number*/
	$("#securityCA").on('blur', function(e){
		e.preventDefault()
		if(isNaN($("#securityCA").val())){
			showValidate($("#securityCA"))
		}
	})
	$("#securityCA").on('click', function(e){
		hideValidate($("#securityCA"))
	})
    
    /*View all registration requests*/
    $('#registrationReq').click(function(event) {
		event.preventDefault()
		// inicijalizujemo je ako vec nismo
		if (!$.fn.DataTable.isDataTable('#registrationReqTable')) {
			requestsTable = $('#registrationReqTable').DataTable({
				ajax: {
					url: "../../theGoodShepherd/registrationReq",
					dataSrc: ''
				},
				columns: [
					{ data: 'user.email'},
					{ data: 'user.password'},
					{ data: 'user.name'},
					{ data: 'user.surname'},
					{ data: 'user.gender'},
					{ data: 'user.dateOfBirth'},
					{ data: null,
						render: function(data){
							return '<button name="acceptRequest" class="btn btn-info add-button" onclick="acceptReq('+ data.id +')">Accept</button>' +
							'<button name="declineRequest" class="btn btn-info add-button" onclick="declineReq('+ data.id +')">Deline</button>'
						}
					}
				]
			})
		} else {
			requestsTable.ajax.reload()
		}
	})
	
	/*View all code books*/
    $('#codeBooks').click(function(event) {
		event.preventDefault()
		// inicijalizujemo je ako vec nismo
		if (!$.fn.DataTable.isDataTable('#diagnosisTable')) {
			diagnosisTable = $('#diagnosisTable').DataTable({
				ajax: {
					url: "../../theGoodShepherd/diagnosis",
					dataSrc: ''
				},
				columns: [
					{ data: 'id'},
					{ data: 'name'},
					{ 
						data: null,
						render: function (data) {
							return data.dpDto.id;}
					}]
			})
		} else {
			diagnosisTable.ajax.reload()
		}
		if (!$.fn.DataTable.isDataTable('#medicineTable')) {
			medicineTable = $('#medicineTable').DataTable({
				ajax: {
					url: "../../theGoodShepherd/prescription",
					dataSrc: ''
				},
				columns: [
					{ data: 'id'},
					{ data: 'name'}]
			})
		} else {
			medicineTable.ajax.reload()
		}
	})
	
	//Fill in the form for adding new diagnose
	$('#addDiagnose').click(function() {
		$.ajax({
            type : "GET",
            url : "/theGoodShepherd/diagnosePrescription",
            dataType: "json",
            success: function(data){
            	$("#codeBookId").empty()
            	$.each(data, function(index, dp) {
					$("#codeBookId").append(new Option(dp.id, dp.id));
				})
            },
            error : function(response) {
            	alert(response.responseJSON.message)
            }
        })
	})
	
	//Add new diagnose
	$('#add_diagnose').click(function(e){
		e.preventDefault()
		
		var name = $('#nameDiagnose').val()
		var codeBook = $("#codeBookId").val()
		
		if(!name){
			alert("All required fields must be filled!")
        	return;
		}
		
		$.ajax({
            type : "POST",
            url : "/theGoodShepherd/diagnosis/addNewDiagnose",
            contentType : "application/json",
            dataType: "json",
            data : JSON.stringify({
                "name" : name,
                "diagnosePerscription" : {
                	"id" : codeBook
                }
            }),
            success: function(data){
            	alert("Successfully added new diagnose!")
            	$('#nameDiagnose').val('')
            	$('.content').hide()
        		$('.code-books').show()
            	diagnosisTable.ajax.reload(); 
            },
            error : function(response) {
            	alert(response.responseJSON.message)
            }
        })
	})
	
	//Cancel adding new diagnose
	$('#cancel_diagnose').click(function(e){
		e.preventDefault()
		$('#name').val('')
    	$('.content').hide()
		$('.code-books').show()
	})
	
	//Add new medicine
	$('#add_medicine').click(function(e){
		e.preventDefault()
		
		var name = $('#nameMedicine').val()
		
		if(!name){
			alert("All required fields must be filled!")
        	return;
		}
		
		$.ajax({
            type : "POST",
            url : "/theGoodShepherd/prescription/addNewPrescription",
            contentType : "application/json",
            dataType: "json",
            data : JSON.stringify({
                "medicine" : name
            }),
            success: function(data){
            	alert("Successfully added new medicine!")
            	$('#nameMedicine').val('')
            	$('.content').hide()
        		$('.code-books').show()
            	medicineTable.ajax.reload(); 
            },
            error : function(response) {
            	alert(response.responseJSON.message)
            }
        })
	})
	
	//Cancel adding new medicine
	$('#cancel_medicine').click(function(e){
		e.preventDefault()
		$('#name').val('')
    	$('.content').hide()
		$('.code-books').show()
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

function clearAddClinicalCenterAdmin() {
	$('#emailCA').val('')
	$('#passwordCA').val('')
	$('#nameCA').val('')
	$('#surnameCA').val('')
    $('#countryCA').val('')
    $('#cityCA').val('')
    $('#addressCA').val('')
    $('#phoneCA').val('')
    $('#securityCA').val('')
    $('#clinicalCentreCA').val('')
    $('#genderCA').val('')
    $('#dateBirthCA').val('')
    hideValidate($("#securityCA"))
    $('.content').hide()
    $('.center-admins').show()
    document.body.scrollTop = 0
    document.documentElement.scrollTop = 0
}

function acceptReq(id){
	
	//kursor ceka
	$("body").css("cursor", "progress")
	$.ajax({
        type : "POST",
        url : "/theGoodShepherd/clinicalCenterAdmin/acceptRegistrationRequest",
        contentType : "application/json",
        //dataType: "json",
        data : JSON.stringify({
            "id" : id
        }),
        success : function()  {
        	requestsTable.ajax.reload();
        	$('.content').hide()
    		$('.registration-req').show()
    		$("body").css("cursor", "default");
            alert("Registration request accepted!")
        },
        error : function(response) {
        	alert(response.responseJSON.message)
        }
    })
}

function declineReq(id){
	//kursor ceka 
	$("body").css("cursor", "progress")
	$.ajax({
        type : "POST",
        url : "/theGoodShepherd/clinicalCenterAdmin/declineRegistrationRequest",
        contentType : "application/json",
        //dataType: "json",
        data : JSON.stringify({
            "id" : id
        }),
        success : function()  {
        	requestsTable.ajax.reload();
        	$('.content').hide()
    		$('.registration-req').show()
    		$("body").css("cursor", "default")
            alert("Registration request rejected!")
        },
        error : function(response) {
        	alert(response.responseJSON.message)
        }
    })
}