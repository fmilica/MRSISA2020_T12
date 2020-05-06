package mrs.isa.team12.clinical.center.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentTypeService;
import mrs.isa.team12.clinical.center.service.interfaces.DoctorService;

@RestController
@RequestMapping("theGoodShepherd/appointmentType")
public class AppointmentTypeController {

	private AppointmentTypeService appointmentTypeService;
	private DoctorService doctorService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public AppointmentTypeController(AppointmentTypeService appointmentTypeService, DoctorService doctorService) {
		this.appointmentTypeService = appointmentTypeService;
		this.doctorService = doctorService;
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/appointmentType/getAllTypesNames
	 HTTP request for getting all unique names of appointment types
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/getAllTypesNames",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<AppointmentType>> getAllTypesNames() {
		Patient currentUser;
		try {
			currentUser = (Patient) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patients can view all appointment types.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<AppointmentType> appTypes = appointmentTypeService.findAll();
		/*Set<String> appTypeNames = new HashSet<String>();
		for (AppointmentType appType : appTypes) {
			appTypeNames.add(appType.getName());
		}*/
		Set<AppointmentType> appTypesUnique = new HashSet<AppointmentType>();
		for (AppointmentType appType : appTypes) {
			appTypesUnique.add(appType);
		}
		return new ResponseEntity<>(appTypesUnique, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/appointmentType/getClinicsTypes
	 HTTP request for getting all appointment types from one clinic
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/getClinicsTypes",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppointmentType>> getClinicsTypes() {
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic administrators can view all appointment types.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<AppointmentType> clinicsTypes = appointmentTypeService.findAllByClinicId(currentUser.getClinic().getId());
		return new ResponseEntity<>(clinicsTypes, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/appointmentType/addNewAppointmentType
	 HTTP request for adding new ordination
	 receives AppointmentType object
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewAppointmentType",
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppointmentType> createOrdination(@RequestBody AppointmentType appType) {
		
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic administrators can create new appointment types.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		Set<Doctor> certified = new HashSet<Doctor>();
		for(Doctor d : appType.getDoctors()) {
			Doctor d1 = doctorService.findOneById(d.getId());
			certified.add(d1);
		}
		
		AppointmentType existing = appointmentTypeService.findOneByNameAndClinicId(appType.getName(), currentUser.getClinic().getId());
		
		if (existing == null) {
			/*		SOME BODY HEEELP OPEN CONNE CTI ON!!!!!*/
			// ne postoji u bazi
			
			//currentUser.getClinic().addAppType(appType);
			appType.setClinic(currentUser.getClinic());
			appType.setDoctors(certified);
			
			AppointmentType saved = appointmentTypeService.save(appType);
			
			return new ResponseEntity<>(saved, HttpStatus.CREATED);
		}
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment type with given name already exists!");
		
	}

}
