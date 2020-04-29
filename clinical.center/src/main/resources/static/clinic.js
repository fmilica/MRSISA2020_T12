$(document).ready(function() {
    $('#addClinic').click(function(e){
        e.preventDefault()
        
        var name = $('#name').val()
        var country = $('#country').val()
        var city = $('#city').val()
        var address = $('#address').val()
        var description = $('#description').val()
        var clinicalCenter = $('#clinicalCenter').val()
        
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
                	"name" : clinicalCenter
                }
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