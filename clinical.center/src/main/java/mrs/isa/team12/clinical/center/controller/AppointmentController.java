package mrs.isa.team12.clinical.center.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.dto.AppointmentDto;
import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentService;
import mrs.isa.team12.clinical.center.service.interfaces.PatientService;

@RestController
@RequestMapping("theGoodShepherd/appointment")
public class AppointmentController {
	
	private AppointmentService appointmentService;
	private PatientService patientService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public AppointmentController(AppointmentService appointmentService, PatientService patientService) {
		this.appointmentService = appointmentService;
		this.patientService = patientService;
	}
	
	
	/*
	 url: GET localhost:8081/theGoodShepherd/appointment
	 HTTP request for getting all appointments in one clinic
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppointmentDto>> getAllClinicAppointments() {
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical administrators can view clinic appointments.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<Appointment> appointments = appointmentService.findAllByClinicIdAndConfirmedAndFinished(currentUser.getClinic().getId(), true, false);
		List<AppointmentDto> dto = new ArrayList<AppointmentDto>();
		for(Appointment a : appointments) {
			dto.add(new AppointmentDto(a));
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	/*
	 url: GET localhost:8081/theGoodShepherd/appointment/available/{clinicId}
	 HTTP request for getting all appointments in one clinic
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/available/{clinicId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppointmentDto>> getAllAvailableClinicAppointments(@PathVariable("clinicId") Long clinicId) {
		Patient currentUser;
		try {
			currentUser = (Patient) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only a patient can view available clinic appointments.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<Appointment> appointments = appointmentService.findAllByClinicIdAndPatient(clinicId, null);
		List<AppointmentDto> dto = new ArrayList<AppointmentDto>();
		for(Appointment a : appointments) {
			dto.add(new AppointmentDto(a));
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/appointment/schedule/{appId}
	 HTTP request for scheduling a predefined appointment
	 receives Long appId
	 returns ResponseEntity object
	 */
	@PostMapping(value = "schedule/{appId}",
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppointmentDto> schedulePredefinedApp(@PathVariable("appId") Long appId) {
		Patient currentUser;
		try {
			currentUser = (Patient) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only a patient can schedule an appointments.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		Patient patient = patientService.findOneById(currentUser.getId());
		Appointment app = appointmentService.findById(appId);
		app.setPatient(currentUser);
		patient.addAppointment(app);
		app = appointmentService.save(app);
		return new ResponseEntity<>(new AppointmentDto(app), HttpStatus.OK);
	}
}
