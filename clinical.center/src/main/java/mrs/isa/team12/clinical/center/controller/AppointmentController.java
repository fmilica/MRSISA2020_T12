package mrs.isa.team12.clinical.center.controller;

import java.util.ArrayList;
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

import mrs.isa.team12.clinical.center.dto.ViewAppointmentDto;
import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentService;

@RestController
@RequestMapping("theGoodShepherd/appointment")
public class AppointmentController {
	
	private AppointmentService appointmentService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public AppointmentController(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}
	
	
	/*
	 url: GET localhost:8081/theGoodShepherd/appointment
	 HTTP request for getting all appointments in one clinic
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ViewAppointmentDto>> getAllClinicAppointments() {
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical administrators can view clinic appointments.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<Appointment> appointments = appointmentService.findAllByClinicIdAndConfirmedAndFinished(currentUser.getClinic().getId(), true, false);
		List<ViewAppointmentDto> dto = new ArrayList<ViewAppointmentDto>();
		for(Appointment a : appointments) {
			dto.add(new ViewAppointmentDto(a));
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

}
