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

import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentTypeService;

@RestController
@RequestMapping("theGoodShepherd/appointmentType")
public class AppointmentTypeController {

	private AppointmentTypeService appointmentTypeService;
	
	@Autowired
	public AppointmentTypeController(AppointmentTypeService appointmentTypeService) {
		this.appointmentTypeService = appointmentTypeService;
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/appointmentType/addNewAppointmentType
	 HTTP request for adding new ordination
	 receives Ordination object
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewAppointmentType",
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppointmentType> createOrdination(HttpServletRequest req, @RequestBody AppointmentType appType) {
		
		// potrebno dodati kliniku i u model, pa i ovde
		//ClinicAdmin admin = (ClinicAdmin) req.getSession().getAttribute("currentUser");
		
		if (req.getSession().getAttribute("currentUser") == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user loged in!");
		}
	
		AppointmentType existing = appointmentTypeService.findOneByName(appType.getName());
		
		if (existing == null) {
			// ne postoji u bazi
			AppointmentType saved = appointmentTypeService.save(appType);
			return new ResponseEntity<>(saved, HttpStatus.CREATED);
		}
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment type with given name already exists!");
		
	}
}
