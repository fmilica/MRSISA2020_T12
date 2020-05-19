package mrs.isa.team12.clinical.center.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.dto.MedicalReportDto;
import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.Diagnosis;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.MedicalReport;
import mrs.isa.team12.clinical.center.model.Prescription;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentService;
import mrs.isa.team12.clinical.center.service.interfaces.DiagnosisService;
import mrs.isa.team12.clinical.center.service.interfaces.MedicalReportService;
import mrs.isa.team12.clinical.center.service.interfaces.PrescriptionService;

@RestController
@RequestMapping("theGoodShepherd/medicalReport")
public class MedicalReportController {
	
	private MedicalReportService medicalReportService;
	private AppointmentService appointmentService;
	private DiagnosisService diagnosisService;
	private PrescriptionService prescriptionService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public MedicalReportController(MedicalReportService mrs, AppointmentService as, DiagnosisService ds,
			PrescriptionService ps) {
		this.medicalReportService = mrs;
		this.appointmentService = as;
		this.diagnosisService = ds;
		this.prescriptionService = ps;
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/medicalReport/addNewMedicalReport
	 HTTP request for adding new medical report
	 receives Doctor object doctor
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewMedicalReport")
	public ResponseEntity<MedicalReportDto> addNewMedicalReport(@RequestBody MedicalReportDto medicalReportDto){
		
		Doctor doctor;
		try {
			doctor = (Doctor) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only doctors can add new medical reports.");
		}
		if (doctor == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		Appointment appointment = appointmentService.findById(medicalReportDto.getAppId());		
		if(appointment == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment doesn't exist!");
		}
		
		MedicalReport medicalReport = new MedicalReport();
		medicalReport.setDescription(medicalReportDto.getDescription());
		
		Diagnosis d =  diagnosisService.findOneByName(medicalReportDto.getDiagnosisName());
		medicalReport.setDiagnosis(d);
		medicalReport.setAppointment(appointment);
		appointment.setMedicalReport(medicalReport);
		// zavrsava pregled
		appointment.setFinished(true);
		
		// dodavanje lekova
		for(Long prescriptionId : medicalReportDto.getPrescriptionIds()) {
			Prescription p = prescriptionService.findOneById(prescriptionId);
			p.addMedicalReport(medicalReport);
			medicalReport.addPrescription(p);
		}
		
		//diagnosisService.save(medicalReport.getDiagnosis());
		medicalReportService.save(medicalReport);
		appointmentService.save(appointment);
		
		medicalReportDto = new MedicalReportDto(medicalReport);
		
		return new ResponseEntity<>(medicalReportDto, HttpStatus.CREATED);
	}
}
