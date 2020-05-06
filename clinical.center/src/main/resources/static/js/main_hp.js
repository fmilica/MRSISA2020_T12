document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        height: 450,
        plugins: [ 'interaction', 'dayGrid', 'timeGrid', 'list'],
        header: {
          left: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek',
          center: 'title',
          right: 'prev,next'
        },
        selectable: true,
        events: [
            { // this object will be "parsed" into an Event Object
                title: 'The First', // a property!
                start: '2020-04-14', // a property!
                end: '2020-04-15' // a property! ** see important note below about 'end' **
            },
            { // this object will be "parsed" into an Event Object
                title: 'https://fullcalendar.io/docs/event-source-object', // a property!
                start: '2020-04-18T17:30:00', // a property!
                end: '2020-04-18T22:00:00' // a property! ** see important note below about 'end' **
            },
            {
                title: 'https://fullcalendar.io/docs/external-dragging-demo',
                start: '2020-04-16T08:30:00', // a property!
                end: '2020-04-16T22:00:00' // a property! ** see important note below about 'end' **
            },
            {
                title: 'payed leave',
                start: '2020-04-01',
                end: '2020-04-08',
                rendering: 'background'
            }
          ]
    });

    calendar.render();
  });

$(document).ready( function () {
	
	var nameSurname = sessionStorage.getItem('nameSurname')
	$("#shownNameSurname").html(nameSurname)
	$("#bigNameSurname").text(nameSurname)

    /* dropdown links */
    // My Profile
    $('#dropdownProfile').click(function() {
    	alert("Hello")
    })
    // Change password
    $('#dropdownChangePass').click(function() {
        alert("hello")
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
    // Prescription book
    $('#prescriptionBook').click(function() {
        $('.content').hide()
        $('.prescription-book').show()
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
    // Doctor patients
    $('#doctorPatients').click(function() {
        $('.content').hide()
        $('.doctor-patients').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })

    /* Nurse */
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
    $('#patientClinics').click(function() {
        $('.content').hide()
        $('.patient-clinics').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    $('#patientApps').click(function() {
        $('.content').hide()
        $('.patient-apps').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
    $('#patientMedicalRecord').click(function() {
        $('.content').hide()
        $('.patient-medicalRecord').show()
        document.body.scrollTop = 0
        document.documentElement.scrollTop = 0
    })
})
