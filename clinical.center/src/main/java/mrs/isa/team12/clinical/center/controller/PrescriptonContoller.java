package mrs.isa.team12.clinical.center.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.dto.PrescriptionDto;
import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.Prescription;
import mrs.isa.team12.clinical.center.service.interfaces.PrescriptionService;

@RestController
@RequestMapping("theGoodShepherd/prescription")
public class PrescriptonContoller {
	
	private PrescriptionService prescriptionService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public PrescriptonContoller(PrescriptionService ps) {
		this.prescriptionService = ps;
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/prescription
	 HTTP request for viewing all prescriptions
	 returns ResponseEntity object
	 */
	@GetMapping(value = "",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PrescriptionDto>> getAllPrescriptions () {
		
		ClinicalCentreAdmin currentUser = null;
		Doctor currentDoctor = null;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e1) {
			try {
				currentDoctor = (Doctor) session.getAttribute("currentUser");
			} catch (ClassCastException e2) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators and doctors can view  all diagnosis.");
			}
		}
		if (currentUser == null && currentDoctor == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<Prescription> prescription = prescriptionService.findAll();
		
		List<PrescriptionDto> prescriptionDto = new ArrayList<PrescriptionDto>();
		for (Prescription presc : prescription) {
			prescriptionDto.add(new PrescriptionDto(presc));
		}
		
		return new ResponseEntity<>(prescriptionDto, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/precription/addNewPrescription
	 HTTP request for adding new prescription
	 returns ResponseEntity object
	 */
	@PostMapping(value = "addNewPrescription",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PrescriptionDto> addNewPrescription(@RequestBody Prescription prescription) {
		
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can add new diagnose.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		prescriptionService.save(prescription);
		PrescriptionDto prescriptionDto = new PrescriptionDto(prescription);
		
		return new ResponseEntity<>(prescriptionDto, HttpStatus.OK);
	}
	
}
