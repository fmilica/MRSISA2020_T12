package mrs.isa.team12.clinical.center.controller;

import java.util.List;

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

import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.service.interfaces.DoctorService;

@RestController
@RequestMapping("theGoodShepherd/doctor")
public class DoctorController {

	private DoctorService doctorService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
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
	@RequestMapping(value = "getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Doctor>> getAllDoctors() {
		
		List<Doctor> doctors = doctorService.findAll();
		
		return new ResponseEntity<>(doctors, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/doctor/findOne/{id}
	 HTTP request for getting doctor from database with given id
	 returns ResponseEntity object
	 */
	@RequestMapping(value = "findOne/{id}")
	public Doctor getDoctorById(@PathVariable("id") Long id){
		// ko koristi ovo od korisnika, ko ima prava?
		return doctorService.getDoctorById(id);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/doctor/addNewDoctor
	 HTTP request for adding new doctor
	 receives Doctor object doctor
	 returns ResponseEntity object
	 */
	@PostMapping(value = "addNewDoctor")
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
