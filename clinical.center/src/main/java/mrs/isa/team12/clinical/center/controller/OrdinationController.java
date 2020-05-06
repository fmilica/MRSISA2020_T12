package mrs.isa.team12.clinical.center.controller;

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

import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Ordination;
import mrs.isa.team12.clinical.center.model.enums.OrdinationType;
import mrs.isa.team12.clinical.center.service.interfaces.OrdinationService;

@RestController
@RequestMapping("theGoodShepherd/ordination")
public class OrdinationController {

	private OrdinationService ordinationService;
	
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
	
}
