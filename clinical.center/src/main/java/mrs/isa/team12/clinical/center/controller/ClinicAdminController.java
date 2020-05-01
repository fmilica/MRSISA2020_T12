package mrs.isa.team12.clinical.center.controller;

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

import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicAdminService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicService;


@RestController
@RequestMapping("theGoodShepherd/clinicAdmin")
public class ClinicAdminController {
	
	private ClinicAdminService adminService;
	private ClinicService clinicService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public ClinicAdminController(ClinicAdminService adminService, ClinicService clinicService) {
		this.adminService = adminService;
		this.clinicService = clinicService;
	}

	/*
	 url: GET localhost:8081/theGoodShepherd/clinicAdmin
	 HTTP request for viewing clinic administrators
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClinicAdmin>> getAllClinicAdmins() {
		
		List<ClinicAdmin> clinicAdmins = adminService.findAll();
		
		return new ResponseEntity<>(clinicAdmins, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinicAdmin/addNewClinicAdmin/{clinicName}
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
		
		clinicService.save(clinic);
		
		return new ResponseEntity<>(clinicAdmin, HttpStatus.CREATED);
	}
	

	/* testiranje ove sotone
	 * 1) kreiramo kliniku
	 * 2) ispisemo kliniku
	 * 3) kreiramo admina (sa parametrom path-a imenom klinike)
	 * 4) ispisemo admina
	 * 5) ispisemo kliniku
	 */
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinicAdmin/logIn/{email}/{password}
	 HTTP request for checking email and password
	 receives String email and String password
	 returns ResponseEntity object
	 */
	@PostMapping(value = "logIn/{email}/{password}")
	public ResponseEntity<ClinicAdmin> logIn(@PathVariable String email, @PathVariable String password){
		
		if (session.getAttribute("currentUser") != null) {
			// some user is already logged in, can not have two logged in users!
			//return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		ClinicAdmin clinicAdmin = adminService.findOneByEmail(email);
		if(clinicAdmin == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			/*HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("400", "Bad Request");
			
			return new ResponseEntity<ClinicAdmin>(null, responseHeaders, HttpStatus.BAD_REQUEST);*/
		}
		
		if(!clinicAdmin.getPassword().equals(password)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		// postavlja trenutno ulogovanog na sesiju
		session.setAttribute("currentUser", clinicAdmin);
		
		//treba da vraca clinicAdmin
		return new ResponseEntity<>(clinicAdmin, HttpStatus.OK);
		
		
	}
}
