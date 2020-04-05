package mrs.isa.team12.clinical.center.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrs.isa.team12.clinical.center.model.ClinicAdministrator;
import mrs.isa.team12.clinical.center.service.ClinicAdminService;

@RestController
@RequestMapping("/api/clinicAdmins")
public class ClinicAdminController {
	
	private ClinicAdminService clinicAdminService;
	
	@Autowired
	public ClinicAdminController(ClinicAdminService clinicAdminService) {
		this.clinicAdminService = clinicAdminService;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<ClinicAdministrator>> getClinicAdmins(){
		Collection<ClinicAdministrator> admins = clinicAdminService.findAll();
		return new ResponseEntity<Collection<ClinicAdministrator>>(admins, HttpStatus.OK);
	}
	
	@PostMapping(value = "/addClinicAdmin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicAdministrator> createClinicAdmin(@RequestBody ClinicAdministrator clinicAdmin){
		ClinicAdministrator admin = clinicAdminService.create(clinicAdmin);
		if(admin == null) {
			return new ResponseEntity<ClinicAdministrator>(admin, HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity<ClinicAdministrator>(admin, HttpStatus.OK);
		}
	}
	
	
}