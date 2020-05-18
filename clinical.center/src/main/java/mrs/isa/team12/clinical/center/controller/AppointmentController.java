package mrs.isa.team12.clinical.center.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.dto.AppointmentDto;
import mrs.isa.team12.clinical.center.dto.AppointmentPredefinedDto;
import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.Ordination;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentService;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentTypeService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicService;
import mrs.isa.team12.clinical.center.service.interfaces.DoctorService;
import mrs.isa.team12.clinical.center.service.interfaces.OrdinationService;
import mrs.isa.team12.clinical.center.service.interfaces.PatientService;

@RestController
@RequestMapping("theGoodShepherd/appointment")
public class AppointmentController {
	
	private AppointmentService appointmentService;
	private PatientService patientService;
	private AppointmentTypeService appointmentTypeService;
	private DoctorService doctorService;
	private OrdinationService ordinationService;
	private ClinicService clinicService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public AppointmentController(AppointmentService appointmentService, PatientService patientService,
			AppointmentTypeService appointmentTypeService, DoctorService doctorService,
			OrdinationService ordinationService, ClinicService clinicService) {
		this.appointmentService = appointmentService;
		this.patientService = patientService;
		this.appointmentTypeService = appointmentTypeService;
		this.doctorService = doctorService;
		this.ordinationService = ordinationService;
		this.clinicService = clinicService;
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
	 url: POST localhost:8081/theGoodShepherd/appointment/createPredefined
	 HTTP request for creating a predefined appointment
	 receives Long appId
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/createPredefined",
				 consumes = MediaType.APPLICATION_JSON_VALUE,
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppointmentDto> createPredefinedAppointment(@RequestBody AppointmentPredefinedDto appDto) {
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only a clinic administrator can create a predefined appointment!");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		// kreiranje novog appointmenta
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = new Date(dt.parse(appDto.getDate()).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		AppointmentType type = appointmentTypeService.findOneByNameAndClinicId(appDto.getAppTypeName(), currentUser.getClinic().getId());
		Clinic c = clinicService.findOneById(currentUser.getClinic().getId());
		Doctor d = doctorService.findOneById(appDto.getDoctorId());
		Ordination o = ordinationService.findOneById(appDto.getOrdinationId());
		
		Appointment appointment = new Appointment(date, appDto.getTime(), type, appDto.getDiscount(), true, false, o, d, c);
		
		// klinici dodamo novi pregled
		c.addAppointment(appointment);
		// doktoru dodamo novi pregled
		d.addAppointment(appointment);
		// ordinaciji dodamo novi pregled
		o.addAppointment(appointment);
		
		appointment = appointmentService.save(appointment);
		
		return new ResponseEntity<>(new AppointmentDto(appointment), HttpStatus.OK);
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
