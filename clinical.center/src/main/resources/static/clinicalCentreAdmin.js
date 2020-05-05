var clinicAdminsTable;

$(document).ready(function() {
	
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
							return data.clinic.name;
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
        var clinic = $('#clinicName').val()
        var gender = $('#genderC').val()
        var birth = new Date($('#dateBirthC').val())
        
        $.ajax({
            type : "POST",
            url : "/theGoodShepherd/clinicAdmin/addNewClinicAdmin",
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
                "clinic" : {
                	"name" : clinic
                }
            }),
            success : function(response)  {
            	alert("New clinic admin added!")
            	$('.content').hide()
        		$('.clinic-admins').show()
            	clinicAdminsTable.ajax.reload();
            },
            error : function(response) {
            	alert(response.responseJSON.message)
            }
        })
    })
    
    $('#cancel_clinicAdmin').click(function(e){
    	e.preventDefault()
    	$('#emailC').val('')
    	$('#passwordC').val('')
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
    })
	
	
	$('#add_clinic').click(function(e){
        e.preventDefault()
        console.log("kliknuto")
        var name = $('#name').val()
        var country = $('#country').val()
        var city = $('#city').val()
        var address = $('#address').val()
        var description = $('#description').val()
        
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
                "description" : description,
                "clinicalCentre" : {
                	"name" : "The Good Shepherd"
                }
            }),
            success: function(data){
            	alert("Successfully added new clinic!")
            },
            error : function(response) {
                alert("Clinic with specified name already exists!")
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