
(function ($) {
    "use strict";


    /*==================================================================
    [ Focus input ]*/
    $('.input100').each(function(){
        $(this).on('blur', function(){
            if($(this).val().trim() != "") {
                $(this).addClass('has-val');
                hideValidate(this)
            }
            else {
                $(this).removeClass('has-val');
                showValidate(this)
            }
        })    
    })
    
  
    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

    $('.validate-form').on('submit',function(){

        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) == false){
                showValidate(input[i]);
            }
        }
        
        var email = $("#email").val()
        var password = $("#password").val()
        
        $.ajax({
    		type : "POST",
    		async: false,
    		url : "theGoodSheperd/clinicAdmin/logIn//" + email + "//" + password ,
    		dataType: "json",
    		success : function(response)  {
    			alert("Uspeo")
    		},
    		error : function(response) {
    			alert("Nije uspeo")
    		}
    	})
    });


    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });

    function validate (input) {
        /*if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        }*/
       // else {
        if($(input).val().trim() == ''){
            return false;
        }
        //}
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }
    
    
})(jQuery);