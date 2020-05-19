package mrs.isa.team12.clinical.center.controller;

import java.util.ArrayList;
import java.util.Iterator;
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
import mrs.isa.team12.clinical.center.dto.PrescriptionDto;
import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.model.DiagnosePerscription;
import mrs.isa.team12.clinical.center.model.Diagnosis;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.Prescription;
import mrs.isa.team12.clinical.center.service.interfaces.DiagnosisPrescriptionService;
import mrs.isa.team12.clinical.center.service.interfaces.DiagnosisService;
import mrs.isa.team12.clinical.center.service.interfaces.PrescriptionService;

@RestController
@RequestMapping("theGoodShepherd/diagnosis")
public class DiagnosisController {
	
	private DiagnosisService diagnosiService;
	private DiagnosisPrescriptionService diagnosisPrescriptionService;
	private PrescriptionService prescriptionService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public DiagnosisController(DiagnosisService ds, DiagnosisPrescriptionService dps, PrescriptionService ps) {
		this.diagnosiService = ds;
		this.diagnosisPrescriptionService = dps;
		this.prescriptionService = ps;
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/diagnosis
	 HTTP request for viewing all diagnosis
	 returns ResponseEntity object
	 */
	@GetMapping(value = "",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DiagnosisDto>> getAllDisagnosis() {
		
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
		
		DiagnosePerscription dp = diagnosisPrescriptionService.findOneById(diagnose.getDiagnosePerscription().getId());
		diagnose.setDiagnosePerscription(dp);
		diagnosiService.save(diagnose);
		DiagnosisDto diagnosisDto = new DiagnosisDto(diagnose);
		
		return new ResponseEntity<>(diagnosisDto, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/diagnosis/addDiagnosePrescription
	 nista me ne pitajte, ovo ce optimistickim zakljucavanjem da se sredi
	 returns ResponseEntity object
	 */
	@PostMapping(value = "addDiagnosePrescription",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addDiagnosePrescription(@RequestBody PrescriptionDto dtp) {
		System.out.println(dtp);
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can add new medicine.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		Prescription p = prescriptionService.findOneById(dtp.getId());
		
		Iterator<Diagnosis> i = p.getDiagnosis().iterator(); // traversing over HashSet
		while(i.hasNext()) {
			Diagnosis d = diagnosiService.findOneByName(i.next().getName());
			d.addPrescription(p);
			diagnosiService.save(d);
		}
		
	}
}
