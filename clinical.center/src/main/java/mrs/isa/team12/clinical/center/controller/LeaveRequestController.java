package mrs.isa.team12.clinical.center.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.dto.LeaveDto;
import mrs.isa.team12.clinical.center.dto.LeaveRequestDto;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.Leave;
import mrs.isa.team12.clinical.center.model.LeaveRequest;
import mrs.isa.team12.clinical.center.model.MedicalPersonnel;
import mrs.isa.team12.clinical.center.model.Nurse;
import mrs.isa.team12.clinical.center.model.enums.LeaveType;
import mrs.isa.team12.clinical.center.service.interfaces.LeaveRequestService;

@RestController
@RequestMapping("theGoodShepherd/leaveRequest")
public class LeaveRequestController {
	
	private LeaveRequestService leaveRequestService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public LeaveRequestController(LeaveRequestService lrs) {
		this.leaveRequestService = lrs;
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/leaveRequest
	 HTTP request for getting all leave requests of current user
	 returns ResponseEntity object
	*/
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LeaveRequestDto>> getAllLeaveRequests() {
		Nurse nurse = null;
		Doctor doctor = null;
		Long id;
		try {
			nurse = (Nurse) session.getAttribute("currentUser");
			id = nurse.getId();
		} catch (ClassCastException e) {
			try {
				doctor = (Doctor) session.getAttribute("currentUser");
				id = doctor.getId();
			} catch (ClassCastException ex) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only doctors can view their leave requests.");
			}
		}
		if (nurse == null && doctor == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<LeaveRequest> lr = leaveRequestService.findAllByLeaveMedicalPersoneId(id);
		List<LeaveRequestDto> lrDtos = new ArrayList<LeaveRequestDto>();
		for (LeaveRequest leaveRequest : lr) {
			lrDtos.add(new LeaveRequestDto(leaveRequest));
		}
		
		return new ResponseEntity<>(lrDtos, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/leaveRequest
	 HTTP request for getting all leave requests of current user
	 returns ResponseEntity object
	*/
	@PostMapping(value = "/addNewLeaveRequest",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addNewLeaveRequest(@RequestBody LeaveDto leave) {
		Nurse nurse = null;
		Doctor doctor = null;
		MedicalPersonnel mp = null;
		try {
			nurse = (Nurse) session.getAttribute("currentUser");
			mp = nurse;
		} catch (ClassCastException e) {
			try {
				doctor = (Doctor) session.getAttribute("currentUser");
				mp = doctor;
			} catch (ClassCastException ex) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only doctors can view their leave requests.");
			}
		}
		if (nurse == null && doctor == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		LeaveType lt = leave.getType() == "Paid" ? LeaveType.Paid : LeaveType.Vacation;
		LeaveRequest lr = new LeaveRequest(new Leave(leave.getStartDate(), leave.getEndDate(), lt, mp), false, "");
		
		leaveRequestService.save(lr);
	}
}
