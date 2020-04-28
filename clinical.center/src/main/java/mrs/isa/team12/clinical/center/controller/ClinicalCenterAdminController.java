package mrs.isa.team12.clinical.center.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicalCenterAdminService;

@RestController
@RequestMapping("theGoodShepherd/clinicalCenterAdmin")
public class ClinicalCenterAdminController {

	private ClinicalCenterAdminService clinicalCenterAdminService;

	@Autowired
	public ClinicalCenterAdminController(ClinicalCenterAdminService clinicalCenterAdminService) {
		this.clinicalCenterAdminService = clinicalCenterAdminService;
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
	
}
