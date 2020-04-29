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

import mrs.isa.team12.clinical.center.model.ClinicalCentre;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicalCenterService;

@RestController
@RequestMapping("/theGoodSheperd/clinicalCentres")
public class ClinicalCenterController {
	
	private ClinicalCenterService clinicalCentreService;
	
	@Autowired
	public ClinicalCenterController(ClinicalCenterService ccs) {
		this.clinicalCentreService = ccs;
	}
	
	/*
	 url: GET localhost:8081/theGoodSheperd/clinicalCentres
	 HTTP request for viewing clinical centres
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClinicalCentre>> getAllClinicalCentres() {
		
		List<ClinicalCentre> clinicalCentres = clinicalCentreService.findAll();
		
		return new ResponseEntity<>(clinicalCentres, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodSheperd/clinicalCentres/addNewClinicalCentre
	 HTTP request for adding new clinic
	 receives Clinic object
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewClinicalCentre",
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicalCentre> createClinic(@RequestBody ClinicalCentre clinicalCentre) {
		
		ClinicalCentre newClinicalCentre = clinicalCentreService.save(clinicalCentre);

		return new ResponseEntity<>(newClinicalCentre, HttpStatus.CREATED);
	}
}
