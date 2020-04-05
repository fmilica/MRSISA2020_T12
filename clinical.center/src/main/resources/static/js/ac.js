$(document).ready(function() {
    $('#addCA').click(function(e){
        e.preventDefault()
        
        var name = $('#name').val()
        var surname = $('#surname').val()
        var country = $('#country').val()
        var city = $('#city').val()
        var address = $('#address').val()
        var phone = $('#phone').val()
        var security = $('#security').val()
        var clinic = $('#clinic').val()
        var username = $('#username').val()
        var password = $('#password').val()
        
        $.ajax({
            type : "POST",
            url : "/api/clinicAdmins/addClinicAdmin",
            contentType : "application/json",
            dataType: "json",
            data : JSON.stringify({
                "username" : username,
                "password" : password,
                "role": "ClinicAdmin",
                "name" : name,
                "surname" : surname,
                "country" : country,
                "city": city,
                "address" : address,
                "phone" : phone,
                "security" : security,
                "clinicID" : clinic
            }),
            success : function(response)  {
                alert("uspelo")
            },
            error : function(response) {
                alert("nije uspelo")
            }
        })
    })
})
