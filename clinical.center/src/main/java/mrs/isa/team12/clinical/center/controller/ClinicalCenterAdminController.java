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

import mrs.isa.team12.clinical.center.model.ClinicalCentre;
import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicalCenterAdminService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicalCenterService;

@RestController
@RequestMapping("theGoodShepherd/clinicalCenterAdmin")
public class ClinicalCenterAdminController {

	private ClinicalCenterAdminService clinicalCenterAdminService;
	private ClinicalCenterService centreService;

	@Autowired
	private HttpSession session;
	
	@Autowired
	public ClinicalCenterAdminController(ClinicalCenterAdminService clinicalCenterAdminService, ClinicalCenterService centreService) {
		this.clinicalCenterAdminService = clinicalCenterAdminService;
		this.centreService = centreService;
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/clinicalCentreAdmin
	 HTTP request for viewing clinical centre administrators
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClinicalCentreAdmin>> getAllClinicalCentreAdmins() {
		
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
		
		List<ClinicalCentreAdmin> clinicalCenreAdmins = clinicalCenterAdminService.findAll();
		
		return new ResponseEntity<>(clinicalCenreAdmins, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinicalCenterAdmin/logIn/{email}/{password}
	 HTTP request for checking email and password
	 receives String email and String password
	 returns ResponseEntity object
	 */
	@PostMapping(value = "logIn/{email}/{password}")
	public ResponseEntity<ClinicalCentreAdmin> logIn(@PathVariable String email, @PathVariable String password){
		
		if (session.getAttribute("currentUser") != null) {
			// postoji ulogovani korisnik
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User already loged in!");
		}
		
		ClinicalCentreAdmin clinicalCentreAdmin = clinicalCenterAdminService.findOneByEmail(email);
		
		if(clinicalCentreAdmin == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinical center administrator with given email does not exist.");
		}
		
		if(!clinicalCentreAdmin.getPassword().equals(password)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password do not match!");
		}
		
		return new ResponseEntity<>(clinicalCentreAdmin, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinicalCentreAdmin/addNewClinicalCentreAdmin
	 HTTP request for adding new clinic administrator
	 receives ClinicAdmin object
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewClinicalCentreAdmin",
			 consumes = MediaType.APPLICATION_JSON_VALUE, 
			 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicalCentreAdmin> createClinicalCentreAdmin(@RequestBody ClinicalCentreAdmin clinicalCentreAdmin) {
		
		// da li je neko ulogovan
		// da li je odgovarajuceg tipa
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can add new clinical center administrators.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		// posto sada imamo ternutno ulogovanog, ovo ne mora ovako da se radi
		// to jest, ne mora da se ovom novom administratoru na frontu setuje ime klinickog centra
		// vec se na osnovu trenutno ulogovanog ta infomracija moze dobaviti
		
		// treba dodati proveru da li email vec postoji
		// i sta je vec jedinstveno...... (tuga, sve, malte ne)
		ClinicalCentre clinicalCentre = centreService.findOneByName(clinicalCentreAdmin.getClinicalCentre().getName());
		clinicalCentre.add(clinicalCentreAdmin);
		
		clinicalCentreAdmin.setClinicalCentre(clinicalCentre);
		clinicalCenterAdminService.save(clinicalCentreAdmin);
		centreService.save(clinicalCentre);
		
		return new ResponseEntity<>(clinicalCentreAdmin, HttpStatus.CREATED);
	}
}