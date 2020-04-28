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

import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicAdminService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicService;


@RestController
@RequestMapping("theGoodSheperd/clinicAdmin")
public class ClinicAdminController {
	
	private ClinicAdminService adminService;
	private ClinicService clinicService;
	
	@Autowired
	public ClinicAdminController(ClinicAdminService adminService, ClinicService clinicService) {
		this.adminService = adminService;
		this.clinicService = clinicService;
	}
	
	/*
	 url: GET localhost:8081/theGoodSheperd/clinicAdmin
	 HTTP request for viewing clinic administrators
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClinicAdmin>> getAllClinicAdmins() {
		
		List<ClinicAdmin> clinicAdmins = adminService.findAll();
		
		return new ResponseEntity<>(clinicAdmins, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodSheperd/clinicAdmin/addNewClinicAdmin/{clinicName}
	 HTTP request for adding new clinic administrator
	 receives ClinicAdmin object
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewClinicAdmin/{clinicName}",
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicAdmin> createClinicAdmin(@RequestBody ClinicAdmin clinicAdmin, @PathVariable String clinicName) {
		
		Clinic clinic = clinicService.findOneByName(clinicName);
		
		clinic.add(clinicAdmin);
		
		Clinic savedClinic = clinicService.save(clinic);
		
		return new ResponseEntity<>(clinicAdmin, HttpStatus.CREATED);
	}
	

	/* testiranje ove sotone
	 * 1) kreiramo kliniku
	 * 2) ispisemo kliniku
	 * 3) kreiramo admina (sa parametrom path-a imenom klinike)
	 * 4) ispisemo admina
	 * 5) ispisemo kliniku
	 */
	
	@PostMapping(value = "logIn/{email}/{password}")
	public ResponseEntity<ClinicAdmin> logIn(@PathVariable String email, @PathVariable String password){
		
		ClinicAdmin clinicAdmin = adminService.findOneByEmail(email);
		
		System.out.println(clinicAdmin);
		
		if(clinicAdmin == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		if(!clinicAdmin.getPassword().equals(password)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(clinicAdmin, HttpStatus.OK);
	}
}
