var calendar;

document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    calendar = new FullCalendar.Calendar(calendarEl, {
        height: 450,
        plugins: [ 'interaction', 'dayGrid', 'timeGrid', 'list'],
        header: {
          left: 'listYear,dayGridMonth,timeGridWeek', //timeGridDay,listWeek, a dodat je timelineCustom
          center: 'title',
          right: 'prev,next'
        },
        selectable: true,
        events: calendarFill(),
        eventClick: function(data){
        		if(data.event.title == "Appointment"){
        			alert(data.event.extendedProps.description+"\nOpening patient's profile..")
        			document.body.scrollTop = 0
        			document.documentElement.scrollTop = 0
        			viewPatientProfile(data.event.id)
        		}
    		}
    	}
    );
    calendar.render();
  });

$(document).ready( function () {
	
	var nameSurname = sessionStorage.getItem('nameSurname')
	$("#shownNameSurname").html(nameSurname)
	$("#bigNameSurname").text(nameSurname)

    /* dropdown links */
    // My Profile
    // Change password
    $('#dropdownChangePass').click(function() {
        $('.content').hide()
        $('.change-password').show()
    })
    // Logout
    $('#dropdownLogout').click(function() {
    	$.ajax({
    		type : "GET",
    		async: false,
    		url : "../../theGoodShepherd/logOut",
    		success : function(response)  {
    			window.location.href = "../../index.html"
    		},
    		error : function(response) {
    			alert(response.responseJSON.message)
    		}
    	})
    })
    
    // Home Page always active on login
    $('#homePage').addClass('active-nav-item')
    /* sidebar navigation */
    $('.nav-item').click(function(e) {
        $('.nav-item').removeClass('active-nav-item')
        // removes from home page
        $('#homePage').removeClass('active-nav-item')
        $(this).addClass('active-nav-item')
    })
    // Home Page
    $('#homePage').click(function() {
        $('.content').hide()
        $('.home-page').show()
        $('#clinicProfile').removeClass('active-sub')
        $('#sub').hide("slow")
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    
    /* Clinical center administrator */
     $('.cca-profile').click(function() {
        $('.content').hide()
        $('.clinical-centre-admin-profile').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Clinics
    $('#clinics').click(function() {
        $('.content').hide()
        $('.clinics').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Clinic admins
    $('#clinicAdmins').click(function() {
        $('.content').hide()
        $('.clinic-admins').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Center admins
    $('#centerAdmins').click(function() {
        $('.content').hide()
        $('.center-admins').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Registration requests
    $('#registrationReq').click(function() {
        $('.content').hide()
        $('.registration-req').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Code books
    $('#codeBooks').click(function() {
        if ($(this).hasClass('active-sub')) {
            $(this).removeClass('active-sub')
            $('#sub').hide("slow")
        } else {
            $(this).addClass('active-sub')
            $('#sub').show("slow")
        }
    })
    // Diagnosis book
    $('#diagnosisBook').click(function() {
        $('.content').hide()
        $('.diagnosis-book').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Prescription book
    $('#prescriptionBook').click(function() {
        $('.content').hide()
        $('.prescription-book').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Add new diagnose
    $('#addDiagnose').click(function(){
    	$('.content').hide()
        $('.add-diagnose').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    //Add new medicine
    $('#addMedicine').click(function(){
    	$('.content').hide()
        $('.add-medicine').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Add new clinic administrator
    $('#addClinicAdmin').click(function() {
        $('.content').hide()
        $('.addClinicAdmin').show()
    })
	// Add new clinic
    $('#addClinic').click(function() {
        $('.content').hide()
        $('.addNewClinic').show()
    })

    /* Clinic administrator */
    
    //Clinical centre admin profile
     $('.ca-profile').click(function() {
        $('.content').hide()
        $('.clinic-admin-profile').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Clinic profile
    $('#clinicProfile').click(function() {
        if ($(this).hasClass('active-sub')) {
            $(this).removeClass('active-sub')
            $('#sub').hide("slow")
        } else {
            $(this).addClass('active-sub')
            $('#sub').show("slow")
        }
    })
    // Clinic information
    $('#clinicInformation').click(function() {
        $('.content').hide()
        $('.clinic-information').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Clinic appointments
    $('#clinicAppointments').click(function() {
        $('.content').hide()
        $('.clinic-appointments').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Clinic doctors
    $('#clinicDoctors').click(function() {
        $('.content').hide()
        $('.clinic-doctors').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Clinic ordinations
    $('#clinicOrdinations').click(function() {
        $('.content').hide()
        $('.clinic-ordinations').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Clinic appointment types
    $('#clinicAppTypes').click(function() {
        $('.content').hide()
        $('.clinic-appTypes').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Clinic pricelist
    $('#clinicPricelist').click(function() {
        $('.content').hide()
        $('.clinic-pricelist').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Create appointment
    $('#createClinicApp').click(function() {
        $('.content').hide()
        $('.clinic-createClinicApp').show()
        $('#clinicProfile').removeClass('active-sub')
        $('#sub').hide("slow")
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Clinic reports
    $('#clinicReports').click(function() {
        $('.content').hide()
        $('.clinic-reports').show()
        $('#clinicProfile').removeClass('active-sub')
        $('#sub').hide("slow")
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Clinic examination requests
    $('#clinicExamReq').click(function() {
        $('.content').hide()
        $('.clinic-clinicExamReq').show()
        $('#clinicProfile').removeClass('active-sub')
        $('#sub').hide("slow")
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
	// Clinic operation requests
    $('#clinicOperReq').click(function() {
        $('.content').hide()
        $('.clinic-clinicOperReq').show()
        $('#clinicProfile').removeClass('active-sub')
        $('#sub').hide("slow")
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Clinic vacation/paid leave
    $('#clinicVacation').click(function() {
        $('.content').hide()
        $('#clinicProfile').removeClass('active-sub')
        $('#sub').hide("slow")
        $('.clinic-vacation').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Clinic add doctor
    $('#addDoctor').click(function() {
        $('.content').hide()
        $('.clinic-addDoctor').show()
    })
    // Clinic add ordination
    $('#addOrdination').click(function() {
        $('.content').hide()
        $('.clinic-addOrdination').show()
    })
    // Clinic add appointment type
    $('#addAppType').click(function() {
    	$('.content').hide()
    	$('.clinic-addAppType').show()
    })

    /* Medical personnel */
    // Medical personnel calendar
    $('#medicalCalendar').click(function() {
        $('.content').hide()
        $('.medical-calendar').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Medical personnel vacation
    $('#medicalVacation').click(function() {
        $('.content').hide()
        $('.medical-vacation').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })

    /* Doctor */
    // Doctor profile
    $('.d-profile').click(function() {
        $('.content').hide()
        $('.doctor-profile').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Doctor patients
    $('#doctorPatients').click(function() {
        $('.content').hide()
        $('.doctor-patients').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })

    /* Nurse */
    
    //Nurse profile
     $('.n-profile').click(function() {
        $('.content').hide()
        $('.nurse-profile').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Nurse verify prescription
    $('#nurseVerifyPrescription').click(function() {
        $('.content').hide()
        $('.nurse-verifyPrescription').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    // Nurse patients
    $('#nursePatients').click(function() {
        $('.content').hide()
        $('.nurse-patients').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })

    /* Patient */
    /* Clinics submenu */
    $('#patientClinics').click(function() {
        if ($(this).hasClass('active-sub')) {
            $(this).removeClass('active-sub')
            $('#sub').hide("slow")
        } else {
            $(this).addClass('active-sub')
            $('#sub').show("slow")
        }
    })
    /* View all clinics */
    $('#viewAllClinics').click(function() {
        $('.content').hide()
        $('.patient-viewAllClinics').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    /* Create new appointment */
    $('#scheduleNewAppointment').click(function() {
        $('.content').hide()
        $('.patient-createNewApp').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    /* Patient existing appointments */
    $('#patientApps').click(function() {
        if ($(this).hasClass('active-sub')) {
            $(this).removeClass('active-sub')
            $('#sub2').hide("slow")
        } else {
            $(this).addClass('active-sub')
            $('#sub2').show("slow")
        }
    })
    /* Patient confirmed appointments */
    $('#confirmedApps').click(function() {
        $('.content').hide()
        $('.patient-confirmed-apps').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    /* Patient unconfirmed appointments */
    $('#unconfirmedApps').click(function() {
        $('.content').hide()
        $('.patient-unconfirmed-apps').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    $('#patientMedicalRecord').click(function() {
        $('.content').hide()
        $('.patient-medicalRecord').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    $('.profile').click(function() {
        $('.content').hide()
        $('.patient-profile').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
})


function calendarFill(){
	var eventsToAdd = []
	var colorEvent;
	$.ajax({
		type : "GET",
		async: false,
		url : "../../theGoodShepherd/appointment/medicalPersonnel",
		dataType: "json",
		success : function(output)  {
			$.each(output, function(index, appointment){
				if(appointment.type == "Appointment"){
					colorEvent = '#48baf7' 
				}else{
					colorEvent = '#2aebb4'
				}
				eventsToAdd.push({
					id: appointment.id,
			        title: appointment.type,
			        description: appointment.type+' with patient ' + appointment.patient,
			        start: appointment.date+"T"+appointment.startTime+":00:00",
			        end: appointment.date+"T"+appointment.endTime+":00:00",
			        color: colorEvent
			    });
			});
		}
	})
	console.log(eventsToAdd);
	return eventsToAdd;
}