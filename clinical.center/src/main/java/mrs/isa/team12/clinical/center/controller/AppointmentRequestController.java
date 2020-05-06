package mrs.isa.team12.clinical.center.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.model.AppointmentRequest;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentRequestService;

@RestController
@RequestMapping("theGoodShepherd/appointmentRequest")
public class AppointmentRequestController {

	private AppointmentRequestService appointmentRequestService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public AppointmentRequestController(AppointmentRequestService appointmentRequestService) {
		this.appointmentRequestService = appointmentRequestService;
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/appointmentRequest
	 HTTP request for getting all appointment requests from one clinic
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppointmentRequest>> getAllClinicAppReqs() {
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical administrators can view all appointment requesets.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<AppointmentRequest> reqs = appointmentRequestService.findAllByClinic(currentUser.getClinic());
		System.out.println(reqs);
		//System.out.println(reqs.get(0).getAppointment().getAppType().getName());
		return new ResponseEntity<>(reqs, HttpStatus.OK);
	}
}
