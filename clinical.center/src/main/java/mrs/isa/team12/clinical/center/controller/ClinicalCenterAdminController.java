package mrs.isa.team12.clinical.center.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.dto.ClinicalCentreAdminDto;
import mrs.isa.team12.clinical.center.dto.ClinicalCentreAdminPersonalInformationDto;
import mrs.isa.team12.clinical.center.dto.RegisteredUserDto;
import mrs.isa.team12.clinical.center.model.ClinicalCentre;
import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.model.RegisteredUser;
import mrs.isa.team12.clinical.center.model.RegistrationRequest;
import mrs.isa.team12.clinical.center.model.events.OnRegistrationCompleteEvent;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicalCenterAdminService;
import mrs.isa.team12.clinical.center.service.interfaces.RegisteredUserService;
import mrs.isa.team12.clinical.center.service.interfaces.RegistrationRequestService;

@RestController
@RequestMapping("theGoodShepherd/clinicalCenterAdmin")
public class ClinicalCenterAdminController {

	private ClinicalCenterAdminService clinicalCenterAdminService;
	private RegistrationRequestService registrationService;
	private RegisteredUserService userService;

	@Autowired
	private HttpSession session;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public ClinicalCenterAdminController(ClinicalCenterAdminService clinicalCenterAdminService,  
			RegistrationRequestService registrationService, RegisteredUserService userService) {
		this.clinicalCenterAdminService = clinicalCenterAdminService;
		this.registrationService = registrationService;
		this.userService = userService;
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/clinicalCenterAdmin
	 HTTP request for viewing clinical center administrators
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClinicalCentreAdminDto>> getAllClinicalCentreAdmins() {

		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can view  all clinical center administrators.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<ClinicalCentreAdminDto> dtos = new ArrayList<ClinicalCentreAdminDto>();
		
		List<ClinicalCentreAdmin> clinicalCenreAdmins = clinicalCenterAdminService.findAll();
		for(ClinicalCentreAdmin cca: clinicalCenreAdmins) {
			dtos.add(new ClinicalCentreAdminDto(cca));
		}
		
		return new ResponseEntity<>(dtos, HttpStatus.OK);
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
		
		return new ResponseEntity<>(new RegisteredUserDto(clinicalCentreAdmin, clinicalCentreAdmin.getLogged()), HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinicalCenterAdmin/changePassword/{password}
	 HTTP request for changing password
	 receives String password
	 returns ResponseEntity object
	 */
	@PostMapping(value = "changePassword/{password}")
	public ResponseEntity<RegisteredUserDto> changePassword(@PathVariable String password){
		
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical centre admin can change his password.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		ClinicalCentreAdmin updated = clinicalCenterAdminService.updatePassword(currentUser.getId(), password);
		
		if(updated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password cant be same as old!");
		}
		
		session.setAttribute("currentUser", updated);
		
		return new ResponseEntity<>(new RegisteredUserDto(updated), HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/clinicalCenterAdmin/personalInformation
	 HTTP request for clinical centre admin personal information
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/personalInformation",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicalCentreAdminPersonalInformationDto> viewPersonalInformation() {
		
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical centre admin can view his personal information.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		ClinicalCentreAdmin clinicalCentreAdmin = clinicalCenterAdminService.findOneById(currentUser.getId());

		return new ResponseEntity<>(new ClinicalCentreAdminPersonalInformationDto(clinicalCentreAdmin), HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinicalCenterAdmin/editPersonalInformation
	 HTTP request for editing clinical centre admin personal information
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/editPersonalInformation")
	public ResponseEntity<ClinicalCentreAdminPersonalInformationDto> editPersonalInformation(@RequestBody ClinicalCentreAdminPersonalInformationDto editedProfile) {

		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical centre admin can edit his personal information.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		if (!currentUser.getEmail().equals(editedProfile.getEmail())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email can't be changed!");
		}
		
		editedProfile.setId(currentUser.getId());
			
		ClinicalCentreAdmin clinicalCentreAdmin = clinicalCenterAdminService.update(editedProfile);
		
		// postavljanje novog, izmenjenog administratora klinickog centra na sesiju
		session.setAttribute("currentUser", clinicalCentreAdmin);
		
		return new ResponseEntity<>(new ClinicalCentreAdminPersonalInformationDto(clinicalCentreAdmin), HttpStatus.OK);
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
	public ResponseEntity<ClinicalCentreAdminDto> createClinicalCentreAdmin(@RequestBody ClinicalCentreAdmin clinicalCentreAdmin) {
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

		RegisteredUser user = userService.findOneByEmail(clinicalCentreAdmin.getEmail());
		if (user != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with given email already exists!");
		}
		user = userService.findOneBySecurityNumber(clinicalCentreAdmin.getSecurityNumber());
		if (user != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with given security number already exists!");
		}
		
		currentUser = clinicalCenterAdminService.findOneById(currentUser.getId());
		ClinicalCentre clinicalCentre = currentUser.getClinicalCentre();
		clinicalCentre.add(clinicalCentreAdmin);
		clinicalCentreAdmin.setClinicalCentre(clinicalCentre);
		clinicalCentreAdmin.setLogged(false);
		
		clinicalCenterAdminService.save(clinicalCentreAdmin);
		
		ClinicalCentreAdminDto dto = new ClinicalCentreAdminDto(clinicalCentreAdmin);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinicalCenterAdmin/acceptRegistrationRequest
	 HTTP request for accepting registration request
	 receives RegistrationRequest object
	 */
	@PostMapping(value = "/acceptRegistrationRequest",
			 consumes = MediaType.APPLICATION_JSON_VALUE)
	public void acceptRegistrationRequest(@RequestBody RegistrationRequest regReq, HttpServletRequest request) {
		
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
		
		RegistrationRequest registrationRequest;
		try {
			registrationRequest = registrationService.update(regReq.getId(), true);
			
			// slanje email-a sa linkom
			String appUrl = session.getServletContext().getContextPath();
	        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registrationRequest.getUser(), request.getLocale(), appUrl));			
		}catch(NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sepcified registration request doesn't exist!");
		}catch(ObjectOptimisticLockingFailureException e1 ){
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Registration request was dealt with in the meantime.");
		}
	}
	
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinicalCenterAdmin/declineRegistrationRequest
	 HTTP request for declining a registration request
	 receives RegistrationRequest object
	 */
	@PostMapping(value = "/declineRegistrationRequest",
			 consumes = MediaType.APPLICATION_JSON_VALUE)
	public void declineRegistrationRequest(@RequestBody RegistrationRequest regReq) {
		
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can decline new registration requests.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		//pokusam da obrisem samo patient i nadam se da ce pobrisati i ostalo
		String rr = null;
		try {
			rr = registrationService.delete(regReq.getId());
			clinicalCenterAdminService.sendNotificaitionAsync(currentUser, rr, regReq.getDescription(), false);
		}catch(NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient with requested id doesn't exist!");
		}catch(ObjectOptimisticLockingFailureException e1 ){
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Registration request was dealt with in the meantime.");
		}
		
	}

}