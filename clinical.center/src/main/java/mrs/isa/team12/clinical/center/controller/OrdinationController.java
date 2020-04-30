package mrs.isa.team12.clinical.center.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Ordination;
import mrs.isa.team12.clinical.center.service.interfaces.OrdinationService;

@RestController
@RequestMapping("theGoodShepherd/ordination")
public class OrdinationController {

	private OrdinationService ordinationService;
	
	@Autowired
	public OrdinationController(OrdinationService ordinationService) {
		this.ordinationService = ordinationService;
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
	public ResponseEntity<Ordination> createOrdination(HttpServletRequest req, @RequestBody Ordination ordination) {
		
		if (req.getSession().getAttribute("currentUser") == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user loged in!");
		}
	
		ClinicAdmin admin = (ClinicAdmin) req.getSession().getAttribute("currentUser");
		
		Ordination o = ordinationService.findOneByName(ordination.getName());
		
		if(o == null) {
			ordination.setClinic(admin.getClinic());
			ordinationService.save(ordination);
			return new ResponseEntity<>(ordination, HttpStatus.CREATED);
		}
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ordination with given name already exists!");
		
	}
	
}
