package mrs.isa.team12.clinical.center.controller;

import java.util.List;

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

import mrs.isa.team12.clinical.center.model.ClinicalCentre;
import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicalCenterAdminService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicalCenterService;

@RestController
@RequestMapping("theGoodShepherd/clinicalCenterAdmin")
public class ClinicalCenterAdminController {

	private ClinicalCenterAdminService clinicalCenterAdminService;
	private ClinicalCenterService centreService;

	@Autowired
	public ClinicalCenterAdminController(ClinicalCenterAdminService clinicalCenterAdminService, ClinicalCenterService centreService) {
		this.clinicalCenterAdminService = clinicalCenterAdminService;
		this.centreService = centreService;
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/clinicalCentreAdmin
	 HTTP request for viewing clinical centre administrators
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClinicalCentreAdmin>> getAllClinicalCentreAdmins() {
		
		List<ClinicalCentreAdmin> clinicalCenreAdmins = clinicalCenterAdminService.findAll();
		
		return new ResponseEntity<>(clinicalCenreAdmins, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinicalCenterAdmin/logIn/{email}/{password}
	 HTTP request for checking email and password
	 receives String email and String password
	 returns ResponseEntity object
	 */
	@PostMapping(value = "logIn/{email}/{password}")
	public ResponseEntity<ClinicalCentreAdmin> logIn(@PathVariable String email, @PathVariable String password){
		
		ClinicalCentreAdmin clinicalCentreAdmin = clinicalCenterAdminService.findOneByEmail(email);
		
		if(clinicalCentreAdmin == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		if(!clinicalCentreAdmin.getPassword().equals(password)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(clinicalCentreAdmin, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinicalCentreAdmin/addNewClinicalCentreAdmin
	 HTTP request for adding new clinic administrator
	 receives ClinicAdmin object
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewClinicalCentreAdmin",
			 consumes = MediaType.APPLICATION_JSON_VALUE, 
			 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicalCentreAdmin> createClinicAdmin(@RequestBody ClinicalCentreAdmin clinicalCentreAdmin) {
		
		ClinicalCentre clinicalCentre = centreService.findOneByName(clinicalCentreAdmin.getClinicalCentre().getName());
		clinicalCentre.add(clinicalCentreAdmin);
		
		clinicalCentreAdmin.setClinicalCentre(clinicalCentre);
		clinicalCenterAdminService.save(clinicalCentreAdmin);
		centreService.save(clinicalCentre);
		
		return new ResponseEntity<>(clinicalCentreAdmin, HttpStatus.CREATED);
	}
}