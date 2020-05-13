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

import mrs.isa.team12.clinical.center.dto.ExamRoomDto;
import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.AppointmentRequest;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Ordination;
import mrs.isa.team12.clinical.center.model.enums.OrdinationType;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentRequestService;
import mrs.isa.team12.clinical.center.service.interfaces.OrdinationService;

@RestController
@RequestMapping("theGoodShepherd/ordination")
public class OrdinationController {

	private OrdinationService ordinationService;
	private AppointmentRequestService appointmentRequestService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public OrdinationController(OrdinationService ordinationService) {
		this.ordinationService = ordinationService;
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/ordination/getClinicsOrdinations
	 HTTP request for getting all ordinations from one clinic
	 receives Ordination object
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/getClinicsOrdinations",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ordination>> getClinicsOrdinations() {
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic administrators can view all ordinations.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<Ordination> clinicsOrdins = ordinationService.findAllByClinicId(currentUser.getClinic().getId());
		return new ResponseEntity<>(clinicsOrdins, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/ordination/getClinicsExamination
	 HTTP request for getting all ordinations from one clinic
	 receives Ordination object
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/getClinicsExamination",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ordination>> getClinicsExamination() {
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic administrators can view all ordinations.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<Ordination> clinicsExamRooms = ordinationService.findAllByClinicIdAndType(currentUser.getClinic().getId(), OrdinationType.ConsultingRoom);
		return new ResponseEntity<>(clinicsExamRooms, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/ordination/getClinicsOrdinations/{name}
	 HTTP request for getting all exam rooms from one clinic with given name
	 receives Ordination object
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/getClinicsExaminationName/{name}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ordination>> getClinicsOrdinationsFilter(@PathVariable("name") String name) {
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic administrators can view all ordinations.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<Ordination> clinicsOrdins = ordinationService.findAllByClinicIdAndNameContainingIgnoreCaseAndType(currentUser.getClinic().getId(), name, OrdinationType.ConsultingRoom);
		return new ResponseEntity<>(clinicsOrdins, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/ordination/getClinicsOrdinations/{number}
	 HTTP request for getting all exam rooms from one clinic with given number
	 receives Ordination object
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/getClinicsExaminationNumber/{number}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ordination>> getClinicsOrdinationsFilter(@PathVariable("number") Integer number) {
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic administrators can view all ordinations.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<Ordination> clinicsOrdins = ordinationService.findAllByClinicIdAndOrdinationNumberAndType(currentUser.getClinic().getId(), number, OrdinationType.ConsultingRoom);
		return new ResponseEntity<>(clinicsOrdins, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/ordination/getClinicsOrdinations/{name}/{number}
	 HTTP request for getting all exam rooms from one clinic with given name and number
	 receives Ordination object
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/getClinicsExamination/{name}/{number}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ordination>> getClinicsOrdinationsFilter(@PathVariable("name") String name,
																		@PathVariable("number") Integer number) {
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic administrators can view all ordinations.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<Ordination> clinicsOrdins = ordinationService.findAllByClinicIdAndNameContainingIgnoreCaseAndOrdinationNumberAndType(currentUser.getClinic().getId(), name, number, OrdinationType.ConsultingRoom);
		return new ResponseEntity<>(clinicsOrdins, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/ordination/addNewOrdination/{name}/{type}
	 HTTP request for adding new ordination
	 receives Ordination object
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewOrdination",
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ordination> createOrdination(@RequestBody Ordination ordination) {
		
		ClinicAdmin admin;
		try {
			admin = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic administrators can add new ordinations.");
		}
		if (admin == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		Ordination o = ordinationService.findOneByNameAndOrdinationNumber(ordination.getName(), ordination.getOrdinationNumber());
		
		if(o == null) {
			ordination.setClinic(admin.getClinic());
			ordinationService.save(ordination);
			return new ResponseEntity<>(ordination, HttpStatus.CREATED);
		}
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ordination with given name and number combination already exists!");
		
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/ordination/getAvailableOrdinations/{appointReqId}
	 HTTP request for adding new ordination
	 receives Ordination object
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/getAvailableExaminationRooms/{appReqId}", 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ExamRoomDto>> getAvailableExaminationRooms(@PathVariable("appReqId") Long id) {
		
		ClinicAdmin admin;
		try {
			admin = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic administrators can view available examination rooms.");
		}
		if (admin == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		//uzimamo appointment request da bismo dosli do njegovog datuma
		AppointmentRequest appReq = appointmentRequestService.findOneById(id);
		
		Date examDate = appReq.getAppointment().getDate();
		Integer examStartTime = appReq.getAppointment().getStartTime();
		Integer examEndTime = appReq.getAppointment().getEndTime();
		
		List<Integer> availableTimes = new ArrayList<Integer>();
		availableTimes.add(examStartTime);	
		
		//sve ordinacije te klinike
		List<Ordination> examRooms = ordinationService.findAllByClinicIdAndType(admin.getClinic().getId(), OrdinationType.ConsultingRoom);
		
		// lista prihvatljivih ordinacija
		List<ExamRoomDto> satisfyingOrdinations = new ArrayList<ExamRoomDto>();
		
		//prolazimo kroz ordinacije te klinike
		for (Ordination o : examRooms) {
			// uzimamo u obzir trenutnu ordinaciju
			boolean satisfying = true;
			// prolazimo kroz zakazane preglede u toj ordinaciji
			for (Appointment a : o.getAppointments()) {
				// proveravamo da li ima za taj datum zakazan pregled
				if (a.getDate().equals(examDate)) {
					// proveravamo od kada do kada traje zakazani pregled
					if ((examStartTime >= a.getStartTime() && examStartTime <= a.getEndTime())
							|| (examEndTime >= a.getStartTime() && examEndTime <= a.getEndTime())
							|| (examStartTime < a.getStartTime() && examEndTime > a.getEndTime())) {
						satisfying = false;
						break;
					}
				}
			}
			if(satisfying) {
				satisfyingOrdinations.add(new ExamRoomDto(o, examDate, availableTimes));
			}
		}
		// vracamo sve sobe
		return new ResponseEntity<>(satisfyingOrdinations, HttpStatus.OK);
	}
}
