package mrs.isa.team12.clinical.center.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.AppointmentRequest;
import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.model.RegisteredUser;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentRequestService;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentService;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentTypeService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicAdminService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicService;
import mrs.isa.team12.clinical.center.service.interfaces.DoctorService;
import mrs.isa.team12.clinical.center.service.interfaces.PatientService;
import mrs.isa.team12.clinical.center.service.interfaces.RegisteredUserService;

@RestController
@RequestMapping("theGoodShepherd/patient")
public class PatientController {

	private PatientService patientService;
	private AppointmentRequestService appointmentRequestService;
	private ClinicService clinicService;
	private DoctorService doctorService;
	private AppointmentService appointmentService;
	private AppointmentTypeService appointmentTypeService;
	private ClinicAdminService clinicAdminService;
	private RegisteredUserService userService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public PatientController(PatientService patientService, AppointmentRequestService appointmentRequestService,
			ClinicService clinicService, DoctorService doctorService, AppointmentService appointmentService,
			AppointmentTypeService appointmentTypeService, ClinicAdminService clinicAdminService,
			RegisteredUserService userService) {
		this.patientService = patientService;
		this.appointmentRequestService = appointmentRequestService;
		this.clinicService = clinicService;
		this.doctorService = doctorService;
		this.appointmentService = appointmentService;
		this.appointmentTypeService = appointmentTypeService;
		this.clinicAdminService = clinicAdminService;
		this.userService = userService;
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/patient/logIn/{email}/{password}
	 HTTP request for checking email and password
	 receives String email and String password
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/logIn/{email}/{password}")
	public ResponseEntity<Patient> logIn(@PathVariable String email, @PathVariable String password){
		
		if (session.getAttribute("currentUser") != null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User already loged in!");
		}
		
		Patient patient = patientService.findOneByEmail(email);
		
		if(patient == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient with given email does not exist.");
		}
		
		if(!patient.getPassword().equals(password)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password do not match!");
		}
		
		session.setAttribute("currentUser", patient);
		
		return new ResponseEntity<>(patient, HttpStatus.OK);
	}
	
	/*
	 * url: POST localhost:8081/theGoodShepherd/patient/register
	 * HTTP request for creating new patient profile
	 * receives: Patient instance
	 * returns: ReponseEntity instance
	 */
	
	@PostMapping(value = "/register",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient) {
		// vec postoji ulogovani korisnik, ne moze se registrovati
		if (session.getAttribute("currentUser") != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already loged in!");
		}
		
		// provera da li postoji
		RegisteredUser existing = userService.findOneByEmail(patient.getEmail());
		if (existing != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified email already exists!");
		}
		existing = userService.findOneBySecurityNumber(patient.getSecurityNumber());
		if (existing != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified security number already exists!");
		}
		// ne postoji u bazi
		// sacuvamo ga
		Patient saved = patientService.save(patient);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	/*
	 * url: POST localhost:8081/theGoodShepherd/patient/sendAppointment
	 * HTTP request for sending an email to clinic admin
	 * receives: Appointment instance
	 * returns: ReponseEntity instance
	 * */
	@PostMapping(value = "/sendAppointment",
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppointmentRequest> sendAppointment(@RequestBody Appointment appointment) throws ParseException{
		//slanje emaila
		System.out.println(appointment.getClinic());
		//ovo ce se zanemariti jer ne znam da saljem datum preko postmana
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy.");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		appointment.setDate(sdf1.parse("12.6.2020."));
		appointment.setStartTime(sdf2.parse("12:00"));
		//ovo je izdvojeno odeljenje i brise se cim posaljemo datum preko ajaxa
		
		if (session.getAttribute("currentUser") == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current user doesn't exist!");
		}
		Patient currentPatient = (Patient) session.getAttribute("currentUser");
		try {
			Doctor doctor = doctorService.findOneById(appointment.getDoctor().getId());
			Clinic clinic = clinicService.findOneById(appointment.getClinic().getId());
			
			AppointmentRequest appointmentRequest = new AppointmentRequest(appointment, new Date(), false, clinic);
			appointmentRequest.getAppointment().setClinic(clinic);
			appointmentRequest.getAppointment().setDoctor(doctor);
			appointmentRequest.getAppointment().setPatient(currentPatient);
			
			//moramo svakom adminu klinike poslati mejl
			for (ClinicAdmin admin : clinicAdminService.findAllByClinicId(clinic.getId())) {
				patientService.sendNotificaitionAsync(admin, currentPatient);
			}
			AppointmentType appointmentType = appointmentTypeService.findOneByName(appointment.getAppType().getName());
			appointmentRequest.getAppointment().setAppType(appointmentType);
			//appointmentType.addAppointment(appointmentRequest.getAppointment());
			//ali samo jednom dodati appointmentRequest u bazu
			//clinic.addAppointmentRequest(appointmentRequest);
			appointmentService.save(appointmentRequest.getAppointment());
			appointmentRequestService.save(appointmentRequest);
			clinicService.save(clinic);
			appointmentTypeService.save(appointmentType);
			return new ResponseEntity<>(appointmentRequest, HttpStatus.OK);
		}catch( Exception e ){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
