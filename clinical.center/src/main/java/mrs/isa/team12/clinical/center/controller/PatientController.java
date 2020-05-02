package mrs.isa.team12.clinical.center.controller;

import javax.servlet.http.HttpServletRequest;
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

import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.service.interfaces.PatientService;

@RestController
@RequestMapping("theGoodShepherd/patient")
public class PatientController {

	private PatientService patientService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
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
	public ResponseEntity<Patient> registerPatient(HttpServletRequest req, @RequestBody Patient patient) {
		// vec postoji ulogovani korisnik, ne moze se registrovati
		if (req.getSession().getAttribute("currentUser") != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already loged in!");
		}
		
		// provera da li postoji
		// TREBA DA PROVERAVA U SVIM BAZAMA, JEDINSTVEN MEDJU SVIMA!!!
		Patient existing = patientService.findOneByEmail(patient.getEmail());
		if (existing != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified email already exists!");
		}
		// ne postoji u bazi
		// sacuvamo ga
		Patient saved = patientService.save(patient);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

}
