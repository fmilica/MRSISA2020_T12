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
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.dto.RegisteredUserDto;
import mrs.isa.team12.clinical.center.model.ClinicalCentre;
import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.model.RegistrationRequest;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicalCenterAdminService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicalCenterService;
import mrs.isa.team12.clinical.center.service.interfaces.PatientService;
import mrs.isa.team12.clinical.center.service.interfaces.RegistrationRequestService;

@RestController
@RequestMapping("theGoodShepherd/clinicalCenterAdmin")
public class ClinicalCenterAdminController {

	private ClinicalCenterAdminService clinicalCenterAdminService;
	private ClinicalCenterService centreService;
	private RegistrationRequestService registrationService;
	private PatientService patientService;

	@Autowired
	private HttpSession session;
	
	@Autowired
	public ClinicalCenterAdminController(ClinicalCenterAdminService clinicalCenterAdminService, ClinicalCenterService centreService, 
			RegistrationRequestService registrationService, PatientService patientService) {
		this.clinicalCenterAdminService = clinicalCenterAdminService;
		this.centreService = centreService;
		this.registrationService = registrationService;
		this.patientService = patientService;
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/clinicalCenterAdmin
	 HTTP request for viewing clinical center administrators
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClinicalCentreAdmin>> getAllClinicalCentreAdmins() {
		/*
		 * HELO
		 * KO
		 * OVO
		 * KORISTI
		 * NEMA
		 * NIGDE
		 * NA
		 * FRONTU
		 * ?
		 */
		// da li je neko ulogovan
		// da li je odgovarajuceg tipa
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can view  all clinic administrators.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
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
	public ResponseEntity<RegisteredUserDto> logIn(@PathVariable String email, @PathVariable String password){
		
		if (session.getAttribute("currentUser") != null) {
			// postoji ulogovani korisnik
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User already loged in!");
		}
		
		ClinicalCentreAdmin clinicalCentreAdmin = clinicalCenterAdminService.findOneByEmail(email);
		
		if(clinicalCentreAdmin == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinical center administrator with given email does not exist.");
		}
		
		if(!clinicalCentreAdmin.getPassword().equals(password)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password do not match!");
		}
		
		session.setAttribute("currentUser", clinicalCentreAdmin);
		
		return new ResponseEntity<>(new RegisteredUserDto(clinicalCentreAdmin), HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinicalCenterAdmin/addNewClinicalCentreAdmin
	 HTTP request for adding new clinic administrator
	 receives ClinicAdmin object
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewClinicalCentreAdmin",
			 consumes = MediaType.APPLICATION_JSON_VALUE, 
			 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicalCentreAdmin> createClinicalCentreAdmin(@RequestBody ClinicalCentreAdmin clinicalCentreAdmin) {
		
		// da li je neko ulogovan
		// da li je odgovarajuceg tipa
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can add new clinical center administrators.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}

		/*
		 * DA
		 * LI
		 * SE
		 * RADI
		 * PROVERA
		 * JEDINSTVENOSTI
		 * EMAIL-A
		 * ?
		 */
		
		ClinicalCentre clinicalCentre = centreService.findOneByName(clinicalCentreAdmin.getClinicalCentre().getName());
		if(clinicalCentre == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinical centre with given name does not exist.");
		}
		
		clinicalCentre.add(clinicalCentreAdmin);
		
		clinicalCentreAdmin.setClinicalCentre(clinicalCentre);
		clinicalCenterAdminService.save(clinicalCentreAdmin);
		centreService.save(clinicalCentre);
		
		return new ResponseEntity<>(clinicalCentreAdmin, HttpStatus.CREATED);
	}
	

	@PostMapping(value = "/acceptRegistrationRequest",
			 consumes = MediaType.APPLICATION_JSON_VALUE)
	public void acceptRegistrationRequest(@RequestBody RegistrationRequest regReq) {
		
		// da li je neko ulogovan
		// da li je odgovarajuceg tipa
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can accept new registration requests.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		RegistrationRequest registrationRequest = registrationService.findOneById(regReq.getId());
		
		if(registrationRequest == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registration request with given id does not exist.");
		}
		registrationRequest.setApproved(true);
		registrationService.save(registrationRequest);
		try {
			clinicalCenterAdminService.sendNotificaitionAsync(currentUser, registrationRequest.getUser(),registrationRequest.getDescription(), true);
		}catch( Exception e ){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@PostMapping(value = "/declineRegistrationRequest",
			 consumes = MediaType.APPLICATION_JSON_VALUE)
	public void declineRegistrationRequest(@RequestBody RegistrationRequest regReq) {
		
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can accept new registration requests.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		RegistrationRequest registrationRequest = registrationService.findOneById(regReq.getId());
		
		if(registrationRequest == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registration request with given id does not exist.");
		}
		registrationService.deleteById(registrationRequest.getId());
		patientService.deleteById(registrationRequest.getUser().getId());
		try {
			clinicalCenterAdminService.sendNotificaitionAsync(currentUser, registrationRequest.getUser(),registrationRequest.getDescription(), false);
		}catch( Exception e ){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}