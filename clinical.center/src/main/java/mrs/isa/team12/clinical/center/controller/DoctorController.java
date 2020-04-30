package mrs.isa.team12.clinical.center.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.service.interfaces.DoctorService;

@RestController
@RequestMapping("theGoodShepherd/doctor")
public class DoctorController {

	private DoctorService doctorService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/doctor/logIn/{email}/{password}
	 HTTP request for checking email and password
	 receives String email and String password
	 returns ResponseEntity object
	 */
	@PostMapping(value = "logIn/{email}/{password}")
	public ResponseEntity<Doctor> logIn(@PathVariable String email, @PathVariable String password){
		
		Doctor doctor = doctorService.findOneByEmail(email);
		
		if(doctor == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		if(!doctor.getPassword().equals(password)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(doctor, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/doctor/addNewDoctor
	 HTTP request for adding new doctor
	 receives Doctor object doctor
	 returns ResponseEntity object
	 */
	@PostMapping(value = "addNewDoctor")
	public ResponseEntity<Doctor> addNewDoctor(@RequestBody Doctor doctor){
		
		ClinicAdmin admin = (ClinicAdmin) session.getAttribute("currentUser");
		
		// provera da li postoji
		// TREBA DA PROVERAVA U SVIM BAZAMA, JEDINSTVEN MEDJU SVIMA!!!
		Doctor existing = doctorService.findOneByEmail(doctor.getEmail());
		if (existing != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified email already exists!");
		}
		// ne postoji u bazi
		// sacuvamo ga
		doctor.setClinic(admin.getClinic());
		Doctor saved = doctorService.save(doctor);
		
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	
}
