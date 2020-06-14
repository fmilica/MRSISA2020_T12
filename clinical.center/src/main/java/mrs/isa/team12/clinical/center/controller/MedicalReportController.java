package mrs.isa.team12.clinical.center.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.dto.MedicalReportDto;
import mrs.isa.team12.clinical.center.dto.MedicalReportVerifyDto;
import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.Diagnosis;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.MedicalReport;
import mrs.isa.team12.clinical.center.model.Nurse;
import mrs.isa.team12.clinical.center.model.Prescription;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicService;
import mrs.isa.team12.clinical.center.service.interfaces.DiagnosisService;
import mrs.isa.team12.clinical.center.service.interfaces.DoctorService;
import mrs.isa.team12.clinical.center.service.interfaces.MedicalReportService;
import mrs.isa.team12.clinical.center.service.interfaces.NurseService;
import mrs.isa.team12.clinical.center.service.interfaces.PrescriptionService;

@RestController
@RequestMapping("theGoodShepherd/medicalReport")
public class MedicalReportController {
	
	private MedicalReportService medicalReportService;
	private AppointmentService appointmentService;
	private DiagnosisService diagnosisService;
	private PrescriptionService prescriptionService;
	private ClinicService clinicService;
	private DoctorService doctorService;
	private NurseService nurseService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public MedicalReportController(MedicalReportService mrs, AppointmentService as, DiagnosisService ds,
			PrescriptionService ps, ClinicService clinicService, DoctorService doctorService, NurseService nurseService) {
		this.medicalReportService = mrs;
		this.appointmentService = as;
		this.diagnosisService = ds;
		this.prescriptionService = ps;
		this.clinicService = clinicService;
		this.doctorService = doctorService;
		this.nurseService = nurseService;
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/medicalReport/
	 HTTP request for geting all not verified medical reports 
	 returns ResponseEntity object
	 */
	@GetMapping(value = "")
	public ResponseEntity<List<MedicalReportVerifyDto>> getMedicalReports(){
		
		Nurse nurse;
		try {
			nurse = (Nurse) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only nurses can verify medical reports.");
		}
		if (nurse == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<MedicalReport> medicalReports = medicalReportService.findAll();
		List<MedicalReportVerifyDto> medicalReportDtos = new ArrayList<MedicalReportVerifyDto>();
		
		for (MedicalReport mr : medicalReports) {
			if(!mr.isVerified() && !mr.getPrescriptions().isEmpty()) {
				medicalReportDtos.add(new MedicalReportVerifyDto(mr));
			}
		}
		return new ResponseEntity<>(medicalReportDtos, HttpStatus.CREATED);
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
		medicalReport.setVerified(false);
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
		//appointmentService.update(appointment);
		
		// kada se zavrsi pregled, klinici i doktorima dodajemo novog pacijenta
		// doktoru dodajemo novog pacijenta
		doctor = doctorService.update(doctor, appointment.getPatient());
		// klinici dodajemo novog pacijenta
		Clinic clinic = appointment.getClinic();
		clinic = clinicService.update(clinic, appointment.getPatient());
		
		medicalReportDto = new MedicalReportDto(medicalReport);
		
		return new ResponseEntity<>(medicalReportDto, HttpStatus.CREATED);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/medicalReport/verify/{medicalRecordId}
	 HTTP request for verifing a prescription
	 receives Doctor object doctor
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/verify/{medicalRecordId}")
	public void verifyPrescription(@PathVariable("medicalRecordId") Long medicalRecordId) throws Exception{
		
		Nurse nurse;
		try {
			nurse = (Nurse) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only nurses can verify prescription.");
		}
		if (nurse == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		try {
			medicalReportService.update(medicalRecordId, nurse);
		}catch(NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Medical report with given id doesn't exist!");
		} catch(ObjectOptimisticLockingFailureException e1) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Medical report has been dealt with in the meantime!");
		}
	}
	
	/*Overavanje neoverenih recepata na kraju dana*/
	@Scheduled(cron = "${schedule.cron}")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void verify() {
		List<MedicalReport> prescriptions = medicalReportService.findAllByVerified(false);
		Nurse nurse = nurseService.findAll().get(0);
		for(MedicalReport mr : prescriptions) {
			mr.setVerified(true);
			mr.setNurse(nurse);
		}
	}
}
