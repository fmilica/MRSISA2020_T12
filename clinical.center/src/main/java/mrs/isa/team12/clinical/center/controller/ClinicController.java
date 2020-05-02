package mrs.isa.team12.clinical.center.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.ClinicalCentre;
import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicalCenterService;

@RestController
@RequestMapping("/theGoodShepherd/clinics")
public class ClinicController {
	
	private ClinicService service;
	private ClinicalCenterService centerService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public ClinicController(ClinicService service, ClinicalCenterService centerService) {
		this.service = service;
		this.centerService = centerService;
	}
	
	/*
	 url: GET localhost:8081/theGoodSheperd/clinics
	 HTTP request for viewing clinics
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Clinic>> getAllClinics() {
		// ko ima pravo?
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
		
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can create new clinics.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		Clinic c = service.findOneByName(clinic.getName());
		
		if(c != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clinic with given name already exists.");
		}
		
		// isto i ovde, posto ulogovani administrator centra pravi kliniku, odatle moze da se dobavi njeno ime
		ClinicalCentre clinicalCenter = centerService.findOneByName(clinic.getClinicalCentre().getName());
		clinicalCenter.addClinic(clinic);
		
		clinic.setClinicalCentre(clinicalCenter);
		Clinic newClinic = service.save(clinic);
		centerService.save(clinicalCenter);

		return new ResponseEntity<>(newClinic, HttpStatus.CREATED);
	}
}
