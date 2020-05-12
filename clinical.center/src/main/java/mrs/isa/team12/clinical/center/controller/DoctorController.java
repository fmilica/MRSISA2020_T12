package mrs.isa.team12.clinical.center.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.dto.DoctorFreeTimesDto;
import mrs.isa.team12.clinical.center.dto.RegisteredUserDto;
import mrs.isa.team12.clinical.center.dto.ViewPatientsDto;
import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.model.RegisteredUser;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentTypeService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicService;
import mrs.isa.team12.clinical.center.service.interfaces.DoctorService;
import mrs.isa.team12.clinical.center.service.interfaces.RegisteredUserService;

@RestController
@RequestMapping("theGoodShepherd/doctor")
public class DoctorController {

	private DoctorService doctorService;
	private AppointmentTypeService appointmentTypeService;
	private ClinicService clinicService;
	private RegisteredUserService userService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public DoctorController(DoctorService doctorService, AppointmentTypeService appointmentTypeService,
			ClinicService clinicService, RegisteredUserService userService) {
		this.doctorService = doctorService;
		this.appointmentTypeService = appointmentTypeService;
		this.clinicService = clinicService;
		this.userService = userService;
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/doctor/logIn/{email}/{password}
	 HTTP request for checking email and password
	 receives String email and String password
	 returns ResponseEntity object
	 */
	@PostMapping(value = "logIn/{email}/{password}")
	public ResponseEntity<RegisteredUserDto> logIn(@PathVariable String email, @PathVariable String password){
		
		if (session.getAttribute("currentUser") != null) {
			// postoji ulogovani korisnik
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User already loged in!");
		}
		
		Doctor doctor = doctorService.findOneByEmail(email);
		
		if(doctor == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor with given email does not exist.");
		}
		
		if(!doctor.getPassword().equals(password)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password do not match!");
		}
		
		session.setAttribute("currentUser", doctor);
		
		return new ResponseEntity<>(new RegisteredUserDto(doctor), HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/doctor/getAll
	 HTTP request for viewing all doctors
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/getAll",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Doctor>> getAllDoctors() {
		
		List<Doctor> doctors = doctorService.findAll();
		
		return new ResponseEntity<>(doctors, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/doctor/certified/clinic/{appTypeName}/{clinicId}
	 HTTP request for getting all doctors with appointment type given by name
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/certified/clinic/{appTypeName}/{clinicId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Doctor>> getDoctorsByAppTypeName(@PathVariable("appTypeName") String appTypeName,
																@PathVariable("clinicId") Long clinicId) {
		
		Patient currentUser;
		try {
			currentUser = (Patient) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patient can view certified doctors.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		Clinic clinic = clinicService.findOneById(clinicId);
		List<AppointmentType> types = appointmentTypeService.findAllByName(appTypeName);
		List<Doctor> certifiedClinicDoctors = doctorService.findAllByClinicAndAppointmentTypesIn(clinic, types);
		
		return new ResponseEntity<>(certifiedClinicDoctors, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/doctor/certified/clinic/{appTypeName}/{appDate}/{clinicId}
	 HTTP request for getting all doctors with appointment type given by name
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/certified/clinic/{appTypeName}/{appDate}/{clinicId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DoctorFreeTimesDto>> getDoctorsByAppTypeName(@PathVariable("appTypeName") String appTypeName,
																@PathVariable("appDate") String appDate,
																@PathVariable("clinicId") Long clinicId) {
		
		Patient currentUser;
		try {
			currentUser = (Patient) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patient can view certified doctors.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dt.parse(appDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		AppointmentType type = appointmentTypeService.findOneByNameAndClinicId(appTypeName, clinicId);
		List<Doctor> certifiedClinicDoctors = doctorService.findAllByClinicIdAndAppointmentTypes(clinicId, type);
		
		List<DoctorFreeTimesDto> freeCertifiedClinicDoctors = new ArrayList<DoctorFreeTimesDto>();
		
		for (Doctor d : certifiedClinicDoctors) {
			// slobodna vremena za taj dan i tog doktora
			List<Integer> times = new ArrayList<Integer>();
			for (int i = d.getStartWork(); i < d.getEndWork(); i++) {
				times.add(i);
			}
			if (d.getAppointments() != null) {
				for (Appointment a : d.getAppointments()) {
					if (a.getDate().equals(date)) {
						Integer start = a.getStartTime();
						for (int i = 0; i < a.getAppType().getDuration(); i++) {
							times.remove(new Integer(start+i));
						}
					}
				}
			}
			// imamo listu slobodnih vremena
			// provera da li imamo dovoljno uzastopnih sati za pregled
			List<Integer> freeTimes = new ArrayList<Integer>();
			for(Integer i : times) {
				boolean hasConsecutive = true;
				for(int j = 1; j < type.getDuration(); j++) {
					if (!times.contains(i+j)) {
						hasConsecutive = false;
					}
				}
				if (hasConsecutive) {
					freeTimes.add(i);
				}
			}
			// da li ima slobodnog taj doktor
			if (!freeTimes.isEmpty()) {
				freeCertifiedClinicDoctors.add(new DoctorFreeTimesDto(d, freeTimes));
			}
		}
		
		return new ResponseEntity<>(freeCertifiedClinicDoctors, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/doctor/findOne/{id}
	 HTTP request for getting doctor from database with given id
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/findOne/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Doctor getDoctorById(@PathVariable("id") Long id) {
		// ko koristi ovo od korisnika, ko ima prava?
		return doctorService.findOneById(id);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/doctor/addNewDoctor
	 HTTP request for adding new doctor
	 receives Doctor object doctor
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewDoctor")
	public ResponseEntity<Doctor> addNewDoctor(@RequestBody Doctor doctor){
		
		ClinicAdmin admin;
		try {
			admin = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic administrators can add new doctors.");
		}
		if (admin == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		RegisteredUser existing = userService.findOneByEmail(doctor.getEmail());
		if (existing != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified email already exists!");
		}
		existing = userService.findOneBySecurityNumber(doctor.getSecurityNumber());
		if (existing != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with specified security number already exists!");
		}
		// ne postoji u bazi
		// sacuvamo ga
		doctor.setClinic(admin.getClinic());
		Doctor saved = doctorService.save(doctor);
		
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/doctor/filterDoctors
	 HTTP request for filtering clinic doctors
	 returns ResponseEntity object
	 */
	/*@GetMapping(value = "/filterPatients", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ViewPatientsDto>> filterPatients(@RequestParam String name, @RequestParam String surname, @RequestParam String securityNumber) {

		// da li je neko ulogovan
		// da li je odgovarajuceg tipa
		Doctor currentUser;
		try {
			currentUser = (Doctor) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only doctor can view registered patients");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		List<Patient> patients = patientService.filter(name, surname, securityNumber);
		List<ViewPatientsDto> dto = new ArrayList<ViewPatientsDto>();
		for(Patient p : patients) {
			dto.add(new ViewPatientsDto(p));
		}
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}*/
	
}
