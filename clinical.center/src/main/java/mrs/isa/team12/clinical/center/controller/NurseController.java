package mrs.isa.team12.clinical.center.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrs.isa.team12.clinical.center.model.Nurse;
import mrs.isa.team12.clinical.center.service.NurseImpl;

@RestController
@RequestMapping("theGoodShepherd/nurse")
public class NurseController {

	private NurseImpl nurseService;

	@Autowired
	public NurseController(NurseImpl nurseService) {
		this.nurseService = nurseService;
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/nurse/logIn/{email}/{password}
	 HTTP request for checking email and password
	 receives String email and String password
	 returns ResponseEntity object
	 */
	@PostMapping(value = "logIn/{email}/{password}")
	public ResponseEntity<Nurse> logIn(@PathVariable String email, @PathVariable String password){
		
		Nurse nurse = nurseService.findOneByEmail(email);
		
		if(nurse == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		if(!nurse.getPassword().equals(password)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(nurse, HttpStatus.OK);
	}
	
}
