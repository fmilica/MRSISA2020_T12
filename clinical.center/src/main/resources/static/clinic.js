$(document).ready(function() {
    $('#add_clinic').click(function(e){
        e.preventDefault()
        
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
            error : function(response) {
                alert("Clinic with specified name already exists!")
            }
        })
    })
    
    $('cancel_clinic').click(function(e){
    	e.preventDefault()
    	
    	$('#name').val('')
        $('#country').val('')
        $('#city').val('')
        $('#address').val('')
        $('#description').val('')
    })
})