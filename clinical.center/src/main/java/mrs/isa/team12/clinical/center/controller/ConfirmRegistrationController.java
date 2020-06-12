package mrs.isa.team12.clinical.center.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.model.RegisteredUser;
import mrs.isa.team12.clinical.center.service.interfaces.RegisteredUserService;

@RestController
@RequestMapping("regitrationConfirm")
public class ConfirmRegistrationController {
	
	@Autowired
	private RegisteredUserService service;
	 
	@GetMapping(value="/{token}")
	public void confirmRegistration(@PathVariable("token") String token) {
	    
	    RegisteredUser patient = service.findOneByVerificationToken(token);
	    if(patient == null) {
	    	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not valid validation key.");
	    }
	    String verificationToken = patient.getVerificationToken();
	    if (verificationToken == null) {
	    	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not valid validation key.");
	    }
	     
	    patient.setLogged(true); 
	    service.save(patient);
	}
}
