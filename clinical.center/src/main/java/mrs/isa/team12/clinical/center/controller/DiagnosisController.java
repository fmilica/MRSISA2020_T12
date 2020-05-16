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

import mrs.isa.team12.clinical.center.dto.DiagnosisDto;
import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.model.Diagnosis;
import mrs.isa.team12.clinical.center.service.interfaces.DiagnosisService;

@RestController
@RequestMapping("theGoodShepherd/diagnosis")
public class DiagnosisController {
	
	private DiagnosisService diagnosiService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public DiagnosisController(DiagnosisService ds) {
		this.diagnosiService = ds;
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/diagnosis
	 HTTP request for viewing all diagnosis
	 returns ResponseEntity object
	 */
	@GetMapping(value = "",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DiagnosisDto>> getAllDisagnosis() {
		
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can view  all diagnosis.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<Diagnosis> diagnosis = diagnosiService.findAll();
		
		List<DiagnosisDto> diagnosisDto = new ArrayList<DiagnosisDto>();
		for (Diagnosis diagnose : diagnosis) {
			diagnosisDto.add(new DiagnosisDto(diagnose));
		}
		
		return new ResponseEntity<>(diagnosisDto, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/diagnosis/addNewDiagnose
	 HTTP request for adding new diagnose
	 returns ResponseEntity object
	 */
	@PostMapping(value = "addNewDiagnose",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DiagnosisDto> addNewDiagnose(@RequestBody Diagnosis diagnose) {
		System.out.println(diagnose);
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can add new diagnose.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		diagnosiService.save(diagnose);
		DiagnosisDto diagnosisDto = new DiagnosisDto(diagnose);
		
		return new ResponseEntity<>(diagnosisDto, HttpStatus.OK);
	}
	
}
