package mrs.isa.team12.clinical.center.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	@PostMapping(value = "/addNewOrdination/{name}/{type}",
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ordination> createOrdination(HttpServletRequest req, @PathVariable String name, @PathVariable String type) {
		System.out.println("HEEEEEEEEEEEEEEEEEEEEEEEEELOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
		ClinicAdmin adim = (ClinicAdmin) req.getSession().getAttribute("currentUser");
		
		if (req.getSession().getAttribute("currentUser") == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user loged in!");
		}
	
		System.out.println("HEEEEEEEEEEEEEEEEEEEEEEEEELOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
		System.out.println(adim.getClinic().getId());
		Ordination o = ordinationService.findOneByName(name);
		
		if(o==null) {
			ordinationService.save(o);
		}
		
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		
	}
	
}
