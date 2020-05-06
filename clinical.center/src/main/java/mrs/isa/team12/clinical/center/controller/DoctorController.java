package mrs.isa.team12.clinical.center.controller;

import java.util.Date;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentTypeService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicService;
import mrs.isa.team12.clinical.center.service.interfaces.DoctorService;

@RestController
@RequestMapping("theGoodShepherd/doctor")
public class DoctorController {

	private DoctorService doctorService;
	private AppointmentTypeService appointmentTypeService;
	private ClinicService clinicService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public DoctorController(DoctorService doctorService, AppointmentTypeService appointmentTypeService,
			ClinicService clinicService) {
		this.doctorService = doctorService;
		this.appointmentTypeService = appointmentTypeService;
		this.clinicService = clinicService;
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/doctor/logIn/{email}/{password}
	 HTTP request for checking email and password
	 receives String email and String password
	 returns ResponseEntity object
	 */
	@PostMapping(value = "logIn/{email}/{password}")
	public ResponseEntity<Doctor> logIn(@PathVariable String email, @PathVariable String password){
		
		if (session.getAttribute("currentUser") != null) {
			// postoji ulogovani korisnik
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User already loged in!");
		}
		
		Doctor doctor = doctorService.findOneByEmail(email);
		
		if(doctor == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor with given email does not exist.");
		}
		
		if(!doctor.getPassword().equals(password)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password do not match!");
		}
		
		session.setAttribute("currentUser", doctor);
		
		return new ResponseEntity<>(doctor, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/doctor/getAll
	 HTTP request for viewing all doctors
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/getAll",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Doctor>> getAllDoctors() {
		
		List<Doctor> doctors = doctorService.findAll();
		
		return new ResponseEntity<>(doctors, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/doctor/certified/clinic/{appTypeName}/{clinicId}
	 HTTP request for getting all doctors with appointment type given by name
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/certified/clinic/{appTypeName}/{clinicId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Doctor>> getDoctorsByAppTypeName(@PathVariable("appTypeName") String appTypeName,
																@PathVariable("clinicId") Long clinicId) {
		
		Patient currentUser;
		try {
			currentUser = (Patient) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patient can view certified doctors.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		Clinic clinic = clinicService.findOneById(clinicId);
		List<AppointmentType> types = appointmentTypeService.findAllByName(appTypeName);
		List<Doctor> certifiedClinicDoctors = doctorService.findAllByClinicAndAppointmentTypesIn(clinic, types);
		
		System.out.println(certifiedClinicDoctors);
		
		return new ResponseEntity<>(certifiedClinicDoctors, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/doctor/certified/free/clinic/{appTypeName}/{date}/{clinicId}
	 HTTP request for getting all doctors with appointment type given by name
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/certified/clinic/{appTypeName}/{date}/{clinicId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Doctor>> getDoctorsByAppTypeName(@PathVariable("appTypeName") String appTypeName,
																@PathVariable("date") String date,
																@PathVariable("clinicId") Long clinicId) {
		
		Patient currentUser;
		try {
			currentUser = (Patient) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patient can view certified doctors.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		Clinic clinic = clinicService.findOneById(clinicId);
		List<AppointmentType> types = appointmentTypeService.findAllByName(appTypeName);
		List<Doctor> certifiedClinicDoctors = doctorService.findAllByClinicAndAppointmentTypesIn(clinic, types);
		
		System.out.println(certifiedClinicDoctors.get(0).getAppointments());
		
		return new ResponseEntity<>(certifiedClinicDoctors, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/doctor/findOne/{id}
	 HTTP request for getting doctor from database with given id
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/findOne/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Doctor getDoctorById(@PathVariable("id") Long id) {
		// ko koristi ovo od korisnika, ko ima prava?
		return doctorService.getDoctorById(id);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/doctor/addNewDoctor
	 HTTP request for adding new doctor
	 receives Doctor object doctor
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewDoctor")
	public ResponseEntity<Doctor> addNewDoctor(@RequestBody Doctor doctor){
		
		ClinicAdmin admin;
		try {
			admin = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic administrators can add new doctors.");
		}
		if (admin == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		// provera da li postoji
		// TREBA DA PROVERAVA U SVIM BAZAMA, JEDINSTVEN MEDJU SVIMA!!!
		Doctor existing = doctorService.findOneByEmail(doctor.getEmail());
		if (existing != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified email already exists!");
		}
		// ne postoji u bazi
		// sacuvamo ga
		doctor.setClinic(admin.getClinic());
		Doctor saved = doctorService.save(doctor);
		
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	
}
