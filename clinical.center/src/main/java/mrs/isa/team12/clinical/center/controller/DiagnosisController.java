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

import mrs.isa.team12.clinical.center.dto.DiagnosisDto;
import mrs.isa.team12.clinical.center.model.Diagnosis;
import mrs.isa.team12.clinical.center.service.interfaces.DiagnosisService;

@RestController
@RequestMapping("theGoodShepherd/diagnosis")
public class DiagnosisController {
	
	private DiagnosisService diagnosiService;
	
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
		
		List<Diagnosis> diagnosis = diagnosiService.findAll();
		
		List<DiagnosisDto> diagnosisDto = new ArrayList<DiagnosisDto>();
		for (Diagnosis diagnose : diagnosis) {
			diagnosisDto.add(new DiagnosisDto(diagnose));
		}
		
		return new ResponseEntity<>(diagnosisDto, HttpStatus.OK);
	}
	
}
