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

import mrs.isa.team12.clinical.center.dto.DoctorDto;
import mrs.isa.team12.clinical.center.dto.DoctorFreeTimesDto;
import mrs.isa.team12.clinical.center.dto.DoctorPersonalInformationDto;
import mrs.isa.team12.clinical.center.dto.RegisteredUserDto;
import mrs.isa.team12.clinical.center.model.AppointmentType;
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
	 url: GET localhost:8081/theGoodShepherd/doctor/personalInformation
	 HTTP request for doctors personal information
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/personalInformation",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DoctorPersonalInformationDto> viewPersonalInformation() {
		
		Doctor currentUser;
		try {
			currentUser = (Doctor) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only doctor can view his personal information.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		Doctor doctor = doctorService.findOneById(currentUser.getId());

		DoctorPersonalInformationDto dto = new DoctorPersonalInformationDto(doctor);
		dto.setAppTypes(doctor.getAppointmentTypes());
		dto.setAllAppTypes(doctor.getClinic().getAppointmentTypes());
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/doctor/editPersonalInformation
	 HTTP request for editing doctors personal information
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/editPersonalInformation")
	public ResponseEntity<DoctorPersonalInformationDto> editPersonalInformation(@RequestBody DoctorPersonalInformationDto editedDoctor) {
		System.out.println("Dosao");
		Doctor currentUser;
		try {
			currentUser = (Doctor) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only doctor can edit his personal information.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		editedDoctor.setId(currentUser.getId());
		
		Doctor doctor = doctorService.update(editedDoctor);
		
		return new ResponseEntity<>(new DoctorPersonalInformationDto(doctor), HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/doctor/getAll
	 HTTP request for viewing all doctors
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/getAll",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DoctorDto>> getAllDoctors() {
		/*
		 * da
		 * li
		 * ce
		 * ovo
		 * ikome
		 * ikada
		 * trebati
		 * ?
		 */
		//mozda i nece
		List<Doctor> doctors = doctorService.findAll();
		List<DoctorDto> doctorsDto = new ArrayList<DoctorDto>();
		for(Doctor d : doctors) {
			doctorsDto.add(new DoctorDto(d));
		}
		return new ResponseEntity<>(doctorsDto, HttpStatus.OK);
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
			date = new Date(dt.parse(appDate).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		AppointmentType type = appointmentTypeService.findOneByNameAndClinicId(appTypeName, clinicId);
		List<Doctor> certifiedClinicDoctors = doctorService.findAllByClinicIdAndAppointmentTypes(clinicId, type);
		
		List<DoctorFreeTimesDto> freeCertifiedClinicDoctors = new ArrayList<DoctorFreeTimesDto>();
		
		for (Doctor d : certifiedClinicDoctors) {
			List<Integer> freeTimes = d.getAvailableTimesForDateAndType(date, type);
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
	public DoctorDto getDoctorById(@PathVariable("id") Long id) {
		/*
		 * da
		 * li
		 * ce
		 * nekome
		 * nekad
		 * ovo
		 * trebati
		 * ?
		 */
		// ko koristi ovo od korisnika, ko ima prava?
		return new DoctorDto(doctorService.findOneById(id));
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/doctor/addNewDoctor
	 HTTP request for adding new doctor
	 receives Doctor object doctor
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewDoctor")
	public ResponseEntity<DoctorDto> addNewDoctor(@RequestBody Doctor doctor){
		
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
		
		return new ResponseEntity<>(new DoctorDto(saved), HttpStatus.CREATED);
	}
	
	// FILTRIRANJE DOKTORA NA BEKU
	// MOZDA ZATREBA
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
