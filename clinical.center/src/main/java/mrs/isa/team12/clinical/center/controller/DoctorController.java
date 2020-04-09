package mrs.isa.team12.clinical.center.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.service.DoctorService;

//Controller for receiving client requests from doctor input form
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

	private DoctorService doctorService;
	
	@Autowired
	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
	
	/*
	 url: GET localhost:8081/api/doctors
	 HTTP request for viewing doctors
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Doctor>> listDoctors() {
		return new ResponseEntity<ArrayList<Doctor>>(doctorService.listDoctors(), HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/api/doctors
	 HTTP request for adding new doctor
	 receives Doctor object
	 returns ResponseEntity object
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addDoctor(@RequestBody Doctor doctor) {
		doctorService.addDoctor(doctor);
	}

}
