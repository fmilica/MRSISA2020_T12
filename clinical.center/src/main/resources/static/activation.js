
$(document).ready(function(){
   $('#activationCode').click(function(e){
    	var search = window.location.search.split("=")[1]
    	
    	$.ajax({
    		type : "GET",
    		async: false,
    		url : "/regitrationConfirm/"+search ,
    		success : function()  {
    			window.location.href = "index.html"
    			alert("You can login now!\nWelcome!")
    		},
    		error : function(response) {
    			alert("almost...")
    			window.location.href = "badUser.html"
    		}
    	})
    });
})
    