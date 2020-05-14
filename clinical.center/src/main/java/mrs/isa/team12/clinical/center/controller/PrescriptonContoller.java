package mrs.isa.team12.clinical.center.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrs.isa.team12.clinical.center.dto.PrescriptionDto;
import mrs.isa.team12.clinical.center.model.Prescription;
import mrs.isa.team12.clinical.center.service.interfaces.PrescriptionService;

@RestController
@RequestMapping("theGoodShepherd/prescription")
public class PrescriptonContoller {
	
	private PrescriptionService prescriptionService;
	
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
		
		List<Prescription> prescription = prescriptionService.findAll();
		
		List<PrescriptionDto> prescriptionDto = new ArrayList<PrescriptionDto>();
		for (Prescription presc : prescription) {
			prescriptionDto.add(new PrescriptionDto(presc));
		}
		
		return new ResponseEntity<>(prescriptionDto, HttpStatus.OK);
	}
}
