package mrs.isa.team12.clinical.center.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.service.interfaces.PatientService;

@RestController
@RequestMapping("theGoodShepherd/patient")
public class PatientController {

	private PatientService patientService;
	
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
	@PostMapping(value = "logIn/{email}/{password}")
	public ResponseEntity<Patient> logIn(@PathVariable String email, @PathVariable String password){
		
		Patient patient = patientService.findOneByEmail(email);
		
		if(patient == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		if(!patient.getPassword().equals(password)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(patient, HttpStatus.OK);
	}
	
	/*
	 * url: POST localhost:8081/theGoodShepherd/patinet/register
	 * HTTP request for creating new patient profile
	 * receives: Patient instance
	 * returns: ReponseEntity instance
	 */
	/*
	@PostMapping(value = "register")
	public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient) {
		
		Clinic clinic = clinicService.findOneByName(clinicName);
		
		clinic.add(clinicAdmin);
		
		Clinic savedClinic = clinicService.save(clinic);
		
		return new ResponseEntity<>(clinicAdmin, HttpStatus.CREATED);
	}*/
}
