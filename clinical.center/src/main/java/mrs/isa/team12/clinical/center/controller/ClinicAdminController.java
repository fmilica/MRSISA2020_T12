package mrs.isa.team12.clinical.center.controller;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.dto.RegisteredUserDto;
import mrs.isa.team12.clinical.center.model.AppointmentRequest;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.Ordination;
import mrs.isa.team12.clinical.center.model.RegisteredUser;
import mrs.isa.team12.clinical.center.model.wrapper.ClinicAdminWrapper;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentRequestService;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicAdminService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicService;
import mrs.isa.team12.clinical.center.service.interfaces.DoctorService;
import mrs.isa.team12.clinical.center.service.interfaces.OrdinationService;
import mrs.isa.team12.clinical.center.service.interfaces.RegisteredUserService;


@RestController
@RequestMapping("theGoodShepherd/clinicAdmin")
public class ClinicAdminController {
	
	private ClinicAdminService adminService;
	private ClinicService clinicService;
	private DoctorService doctorService;
	private AppointmentRequestService appointmentReqService;
	private AppointmentService appointmentService;
	private OrdinationService ordinationService;
	private RegisteredUserService userService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public ClinicAdminController(ClinicAdminService adminService, ClinicService clinicService,
			DoctorService doctorService, AppointmentRequestService appointmentReqService, AppointmentService appointmentService,
			OrdinationService ordinationService, RegisteredUserService userService) {
		this.adminService = adminService;
		this.clinicService = clinicService;
		this.doctorService = doctorService;
		this.appointmentReqService = appointmentReqService;
		this.appointmentService = appointmentService;
		this.ordinationService = ordinationService;
		this.userService = userService;
	}

	/*
	 url: GET localhost:8081/theGoodShepherd/clinicAdmin
	 HTTP request for viewing clinic administrators
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClinicAdminWrapper>> getAllClinicAdmins() {
		
		// da li je neko ulogovan
		// da li je odgovarajuceg tipa
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can view  all clinic administrators.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<ClinicAdmin> clinicAdmins = adminService.findAll();
		List<ClinicAdminWrapper> clinicAdminWrapper = new ArrayList<ClinicAdminWrapper>();
		
		for(ClinicAdmin ca : clinicAdmins) {
			clinicAdminWrapper.add(new ClinicAdminWrapper(ca, ca.getClinic().getName()));
		}
		
		return new ResponseEntity<>(clinicAdminWrapper, HttpStatus.OK);
	}

	/*
	 url: POST localhost:8081/theGoodShepherd/clinicAdmin/addNewClinicAdmin/{clinicName}
	 HTTP request for adding new clinic administrator
	 receives ClinicAdmin object
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewClinicAdmin/{clinicId}",
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicAdmin> createClinicAdmin(@RequestBody ClinicAdmin clinicAdmin, @PathVariable String clinicId) {
		
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can add new clinical center administrators.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		System.out.println(clinicAdmin.getEmail());
		RegisteredUser user = userService.findOneByEmail(clinicAdmin.getEmail());
		if( user != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with given email already exists!");
		}
		
		user = userService.findOneBySecurityNumber(clinicAdmin.getSecurityNumber());
		if( user != null) {
			System.out.println("Hallo");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with given security number already exists!");
		}
		
		Clinic clinic = clinicService.findOneById(Long.parseLong(clinicId));
		
		if(clinic == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic with given name does not exist.");
		}
		
		clinic.add(clinicAdmin);
		clinicService.save(clinic);
		
		return new ResponseEntity<>(clinicAdmin, HttpStatus.CREATED);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinicAdmin/logIn/{email}/{password}
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
		
		ClinicAdmin clinicAdmin = adminService.findOneByEmail(email);
		if(clinicAdmin == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic administrator with given email does not exist.");
			/*HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("400", "Bad Request");
			
			return new ResponseEntity<ClinicAdmin>(null, responseHeaders, HttpStatus.BAD_REQUEST);*/
		}
		
		if(!clinicAdmin.getPassword().equals(password)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password do not match!");
		}
		// postavlja trenutno ulogovanog na sesiju
		session.setAttribute("currentUser", clinicAdmin);
		
		//treba da vraca clinicAdmin
		return new ResponseEntity<>(new RegisteredUserDto(clinicAdmin), HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/clinicAdmin/getDoctors
	 HTTP request for viewing doctors in clinic admins clinic
	 returns ResponseEntity object
	 */
	@GetMapping(value = "getDoctors" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Doctor>> getDoctors() {
		
		// da li je neko ulogovan
		// da li je odgovarajuceg tipa
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can view  all clinic administrators.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<Doctor> doctors = doctorService.findAllByClinicId(currentUser.getClinic().getId());
		
		return new ResponseEntity<>(doctors, HttpStatus.OK);
	}
	
	/*
	 * url: POST localhost:8081/theGoodShepherd/clinicAdmin/acceptAppointmentRequest
	 * HTTP request for sending an acceptance email to patient
	 * receives: AppointmentRequest instance
	 * returns: String instance
	 * */
	@PostMapping(value = "/acceptAppointmentRequest" ,
			consumes = MediaType.APPLICATION_JSON_VALUE, //-> {"id" : 2, "appointment" :{ "ordination" :{ "name" : "Ordination1" }}} npr
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> acceptAppointmentRequest(@RequestBody AppointmentRequest appointmentRequest){
		//proveriti da li je termin slobodan
			//ako jeste posalji mejl korisniku da je odobreno
			//ako nije, pronadji prvi slobodni termin i posalji koristiku te detalje
		if(session.getAttribute("currentUser") == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		ClinicAdmin currentAdmin = (ClinicAdmin) session.getAttribute("currentUser");
		AppointmentRequest appointmentReq = appointmentReqService.findOneById(appointmentRequest.getId());
		System.out.println("dodjes li ovde majke ti");
		if(appointmentReq == null) { //mozda bi ovde trebalo proveriti da li je to neki koji je pre prihvacen/odbijen? mozda kasnije..
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Appointment request doesn't exist!");
		}
		//postavi confirmed na true kod AppointmentRequest i Appointment
		appointmentReq.setApproved(true);
		appointmentReq.getAppointment().setConfirmed(true);
		//dodati appointment doktoru, pacijentu i svima kojima treba
		Doctor doctor = doctorService.findOneByEmail(appointmentReq.getAppointment().getDoctor().getEmail());
		doctor.addAppointment(appointmentReq.getAppointment());
		Clinic clinic = clinicService.findOneById(appointmentReq.getClinic().getId());
		clinic.addAppointment(appointmentReq.getAppointment());
		
		//
		Ordination ord = ordinationService.findOneByName(appointmentRequest.getAppointment().getOrdination().getName());
		ord.addAppointment(appointmentReq.getAppointment());
		appointmentReq.getAppointment().setOrdination(ord);
		ordinationService.save(ord);
		//
		
		//sacuvati sve u bazama
		doctorService.save(doctor);
		clinicService.save(clinic);
		appointmentService.save(appointmentReq.getAppointment());
		try {
			adminService.sendNotificaitionAsync(currentAdmin, appointmentReq.getAppointment().getPatient(), 
					appointmentReq.getAppointment(), true);
			return new ResponseEntity<>("Request accepted!", HttpStatus.OK);
		}catch( Exception e ){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	/*
	 * url: POST localhost:8081/theGoodShepherd/clinicAdmin/declineAppointmentRequest
	 * HTTP request for sending a rejection email to patient
	 * receives: AppointmentRequest instance
	 * returns: String instance
	 * */
	@PostMapping(value = "/declineAppointmentRequest" ,
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> declineAppointmentRequest(@RequestBody AppointmentRequest appointmentRequest){
		if (session.getAttribute("currentUser") == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current user doesn't exist!");
		}
		ClinicAdmin currentAdmin = (ClinicAdmin) session.getAttribute("currentUser");
		AppointmentRequest appointmentReq = appointmentReqService.findOneById(appointmentRequest.getId());
		if(appointmentReq == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Appointment request doesn't exist!");
		}
		appointmentReq.setApproved(false);
		appointmentReq.getAppointment().setConfirmed(false);
		try {
			adminService.sendNotificaitionAsync(currentAdmin, appointmentReq.getAppointment().getPatient(),null, false);
			return new ResponseEntity<>("Request rejected!", HttpStatus.OK);
		}catch( Exception e ){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
}
