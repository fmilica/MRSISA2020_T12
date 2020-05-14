package mrs.isa.team12.clinical.center.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.dto.AppointmentRequestDto;
import mrs.isa.team12.clinical.center.dto.ClinicPatientDto;
import mrs.isa.team12.clinical.center.dto.MedicalRecordDto;
import mrs.isa.team12.clinical.center.dto.PatientDto;
import mrs.isa.team12.clinical.center.dto.PatientProfileDto;
import mrs.isa.team12.clinical.center.dto.PatientsDto;
import mrs.isa.team12.clinical.center.dto.RegisteredUserDto;
import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.AppointmentRequest;
import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.model.RegisteredUser;
import mrs.isa.team12.clinical.center.model.RegistrationRequest;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentRequestService;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentService;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentTypeService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicAdminService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicService;
import mrs.isa.team12.clinical.center.service.interfaces.DoctorService;
import mrs.isa.team12.clinical.center.service.interfaces.PatientService;
import mrs.isa.team12.clinical.center.service.interfaces.RegisteredUserService;
import mrs.isa.team12.clinical.center.service.interfaces.RegistrationRequestService;

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
	private RegistrationRequestService registrationService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public PatientController(PatientService patientService, AppointmentRequestService appointmentRequestService,
			ClinicService clinicService, DoctorService doctorService, AppointmentService appointmentService,
			AppointmentTypeService appointmentTypeService, ClinicAdminService clinicAdminService,
			RegisteredUserService userService, RegistrationRequestService registrationService) {
		this.patientService = patientService;
		this.appointmentRequestService = appointmentRequestService;
		this.clinicService = clinicService;
		this.doctorService = doctorService;
		this.appointmentService = appointmentService;
		this.appointmentTypeService = appointmentTypeService;
		this.clinicAdminService = clinicAdminService;
		this.userService = userService;
		this.registrationService = registrationService;
	}
	

	/*
	 url: GET localhost:8081/theGoodShepherd/patient
	 HTTP request for viewing registered patients
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PatientsDto>> getAllPatients() {
		// da li je neko ulogovan
		// da li je odgovarajuceg tipa
		Doctor currentUser;
		try {
			currentUser = (Doctor) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only doctor can view registered patients");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<Patient> patients = patientService.findAll();
		List<PatientsDto> dto = new ArrayList<PatientsDto>();
		for(Patient p : patients) {
			dto.add(new PatientsDto(p));
		}
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/patient/filterPatients
	 HTTP request for filtering registered patients
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/filterPatients", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PatientsDto>> filterPatients(@RequestParam String name, @RequestParam String surname, @RequestParam String securityNumber) {

		// da li je neko ulogovan
		// da li je odgovarajuceg tipa
		Doctor currentUser;
		try {
			currentUser = (Doctor) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only doctor can view registered patients");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		List<Patient> patients = patientService.filter(name, surname, securityNumber);
		List<PatientsDto> dto = new ArrayList<PatientsDto>();
		for(Patient p : patients) {
			dto.add(new PatientsDto(p));
		}
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/patient/viewProfile
	 HTTP request for viewing logged in patient profile
	 receives String securityNumber
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/viewProfile")
	public ResponseEntity<PatientProfileDto> viewProfile(){
		
		Patient currentUser;
		try {
			currentUser = (Patient) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only a patient can view it's profile.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		currentUser = patientService.findOneByEmail(currentUser.getEmail());
		
		MedicalRecordDto medicalRecords = new MedicalRecordDto(currentUser.getMedicalRecords());
		// uzecemo sve preglede, ali cemo dodati samo one koji su finished!
		// da bismo izbegli dodavanje medical record i u appointment, vec da bude samo u pacijentu
		medicalRecords.setMedicalReports(currentUser.getAppointments());
		return new ResponseEntity<>(new PatientProfileDto(currentUser, medicalRecords), HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/patient/viewProfile/{secNum}
	 HTTP request for viewing chosen patient profile
	 receives String securityNumber
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/viewProfile/{secNum}")
	public ResponseEntity<PatientProfileDto> viewProfile(@PathVariable String secNum){
		
		Doctor currentUser;
		try {
			currentUser = (Doctor) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only doctor can view registered patients");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		Patient patient = patientService.findOneBySecurityNumber(secNum);
		List<Appointment> appointments = appointmentService.findAllByPatientIdAndDoctorId(patient.getId(), currentUser.getId());
		if(appointments.size() == 0) {
			return new ResponseEntity<>(new PatientProfileDto(patient), HttpStatus.OK);
		}
		MedicalRecordDto medicalRecords = new MedicalRecordDto(patient.getMedicalRecords());
		// uzecemo sve preglede, ali cemo dodati samo one koji su finished!
		// da bismo izbegli dodavanje medical record i u appointment, vec da bude samo u pacijentu
		medicalRecords.setMedicalReports(patient.getAppointments());
		return new ResponseEntity<>(new PatientProfileDto(patient, medicalRecords), HttpStatus.OK);
	}
	
	
	/*
	 url: POST localhost:8081/theGoodShepherd/patient/logIn/{email}/{password}
	 HTTP request for checking email and password
	 receives String email and String password
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/logIn/{email}/{password}")
	public ResponseEntity<RegisteredUserDto> logIn(@PathVariable String email, @PathVariable String password){
		
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
		
		return new ResponseEntity<>(new RegisteredUserDto(patient), HttpStatus.OK);
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
	public ResponseEntity<PatientDto> registerPatient(@RequestBody Patient patient) {
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
		RegistrationRequest regReq = new RegistrationRequest(patient, false, "");
		registrationService.save(regReq);
		return new ResponseEntity<>(new PatientDto(saved), HttpStatus.CREATED);
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
	public ResponseEntity<AppointmentRequestDto> sendAppointment(@RequestBody Appointment appointment) throws ParseException{
		//slanje emaila
		System.out.println(appointment.getClinic());
		//ovo ce se zanemariti jer ne znam da saljem datum preko postmana
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy.");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		//appointment.setDate(sdf1.parse("12.6.2020."));
		//appointment.setStartTime(sdf2.parse("12:00"));
		//ovo je izdvojeno odeljenje i brise se cim posaljemo datum preko ajaxa
		
		if (session.getAttribute("currentUser") == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current user doesn't exist!");
		}
		Patient currentPatient = (Patient) session.getAttribute("currentUser");
		try {
			Doctor doctor = doctorService.findOneById(appointment.getDoctor().getId());
			Clinic clinic = clinicService.findOneById(appointment.getClinic().getId());
			
			AppointmentRequest appointmentRequest = new AppointmentRequest(appointment, new java.sql.Date(new java.util.Date().getTime()), false, clinic);
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
			return new ResponseEntity<>(new AppointmentRequestDto(appointmentRequest), HttpStatus.OK);
		}catch( Exception e ){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/patient/clinics
	 HTTP request for viewing all existing clinics
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/clinics", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClinicPatientDto>> getAllClinics() {
		Patient currentUser;
		try {
			currentUser = (Patient) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patient can view  all clinics.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<Clinic> clinics = clinicService.findAll();
		List<ClinicPatientDto> clinicsDto = new ArrayList<ClinicPatientDto>();
		
		for(Clinic c : clinics) {
			ClinicPatientDto clinic = new ClinicPatientDto(c);
			clinic.setAppointmentTypes(c.getAppointmentTypes());
			clinicsDto.add(clinic);
		}
		return new ResponseEntity<>(clinicsDto, HttpStatus.OK);
	}
}
