package mrs.isa.team12.clinical.center.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicService;

@RestController
@RequestMapping("/theGoodSheperd/clinics")
public class ClinicController {
	
	private ClinicService service;
	
	@Autowired
	public ClinicController(ClinicService service) {
		this.service = service;
	}
	
	/*
	 url: GET localhost:8081/theGoodSheperd/clinics
	 HTTP request for viewing clinics
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Clinic>> getAllClinics() {
		
		List<Clinic> clinics = service.findAll();
		
		return new ResponseEntity<>(clinics, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodSheperd/clinics/addNewClinic
	 HTTP request for adding new clinic
	 receives Clinic object
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewClinic",
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Clinic> createClinic(@RequestBody Clinic clinic) {
		
		Clinic newClinic = service.save(clinic);

		return new ResponseEntity<>(newClinic, HttpStatus.CREATED);
	}
}
