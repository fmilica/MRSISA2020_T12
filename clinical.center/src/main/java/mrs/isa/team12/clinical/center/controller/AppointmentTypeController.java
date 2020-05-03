package mrs.isa.team12.clinical.center.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.PricelistItem;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentTypeService;
import mrs.isa.team12.clinical.center.service.interfaces.PriceListItemService;

@RestController
@RequestMapping("theGoodShepherd/appointmentType")
public class AppointmentTypeController {

	private AppointmentTypeService appointmentTypeService;
	private PriceListItemService priceListItemService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public AppointmentTypeController(AppointmentTypeService appointmentTypeService,
			PriceListItemService priceListItemService) {
		this.appointmentTypeService = appointmentTypeService;
		this.priceListItemService = priceListItemService;
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/appointmentType/addNewAppointmentType/{price}
	 HTTP request for adding new ordination
	 receives Ordination object
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewAppointmentType/{price}",
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppointmentType> createOrdination(@RequestBody AppointmentType appType, @PathVariable String price) {
		
		System.out.println("HELOOOOOOOOOOOOOOOOooooooooooooooo");
		
		// potrebno dodati kliniku i u model, pa i ovde
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic administrators can create new appointment types.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		PricelistItem priceListItem = new PricelistItem(Double.parseDouble(price), currentUser.getClinic().getClinicalCentre().getPriceList());
		System.out.println(priceListItem);
		
		AppointmentType existing = appointmentTypeService.findOneByName(appType.getName());
		
		if (existing == null) {
			/*		SOME BODY HEEELP OPEN CONNE CTI ON!!!!!*/
			// ne postoji u bazi
			//AppointmentType saved = appointmentTypeService.save(appType);
			priceListItem.setAppointmentType(appType);
			appType.setPricelistItem(priceListItem);
			
			priceListItemService.save(priceListItem);
			return new ResponseEntity<>(appType, HttpStatus.CREATED);
		}
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment type with given name already exists!");
		
	}

}
