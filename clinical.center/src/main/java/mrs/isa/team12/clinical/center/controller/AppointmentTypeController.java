package mrs.isa.team12.clinical.center.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.dto.AppointmentTypeDto;
import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentTypeService;
import mrs.isa.team12.clinical.center.service.interfaces.DoctorService;

@RestController
@RequestMapping("theGoodShepherd/appointmentType")
public class AppointmentTypeController {

	private AppointmentTypeService appointmentTypeService;
	private DoctorService doctorService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public AppointmentTypeController(AppointmentTypeService appointmentTypeService, DoctorService doctorService) {
		this.appointmentTypeService = appointmentTypeService;
		this.doctorService = doctorService;
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/appointmentType/getAllTypesNames
	 HTTP request for getting all unique names of appointment types
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/getAllTypesNames",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<String>> getAllTypesNames() {
		Patient currentUser;
		try {
			currentUser = (Patient) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patients can view all appointment types.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<AppointmentType> appTypes = appointmentTypeService.findAll();
		Set<String> appTypesUniqueNames = new HashSet<String>();
		for (AppointmentType appType : appTypes) {
			appTypesUniqueNames.add(appType.getName());
		}
		return new ResponseEntity<>(appTypesUniqueNames, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/appointmentType/getClinicTypes/{clinicId}
	 HTTP request for getting all clinic appointment types
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/getClinicTypes/{clinicId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppointmentTypeDto>> getClinicAppTypes(@PathVariable("clinicId") Long clinicId) {
		Patient currentUser;
		try {
			currentUser = (Patient) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patients can view all clinic appointment types.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<AppointmentType> clinicsTypes = appointmentTypeService.findAllByClinicId(clinicId);
		List<AppointmentTypeDto> clinicsTypesDtos = new ArrayList<AppointmentTypeDto>();
		for(AppointmentType a : clinicsTypes) {
			clinicsTypesDtos.add(new AppointmentTypeDto(a));
		}
		
		return new ResponseEntity<>(clinicsTypesDtos, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/appointmentType/getClinicsTypes
	 HTTP request for getting all appointment types from one clinic
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/getClinicsTypes",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppointmentTypeDto>> getClinicsTypes() {
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic administrators can view all appointment types.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<AppointmentType> clinicsTypes = appointmentTypeService.findAllByClinicId(currentUser.getClinic().getId());
		List<AppointmentTypeDto> clinicsTypesDtos = new ArrayList<AppointmentTypeDto>();
		for(AppointmentType a : clinicsTypes) {
			clinicsTypesDtos.add(new AppointmentTypeDto(a));
		}
		return new ResponseEntity<>(clinicsTypesDtos, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/appointmentType/viewAppType/{appTypeId}
	 HTTP request for viewing specified appType
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/viewAppType/{appTypeId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppointmentTypeDto> viewAppType(@PathVariable Long appTypeId) {
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic administrators can edit appointment types.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		AppointmentType appType = appointmentTypeService.findOneById(appTypeId);
		
		//provera da li tip pregleda ima zakazane preglede
		//ukoliko ima zakazane preglede ne moze biti obrisan
		for(Appointment a : appType.getAppointments()) {
			if(!a.getFinished()) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Appointment type with scheduled appointments cant be edited!");
			}
		}
		return new ResponseEntity<>(new AppointmentTypeDto(appType), HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/appointmentType/addNewAppointmentType
	 HTTP request for adding new ordination
	 receives AppointmentType object
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewAppointmentType",
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppointmentTypeDto> addNewAppointmentType(@RequestBody AppointmentType appType) {
		
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic administrators can create new appointment types.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		Set<Doctor> certified = new HashSet<Doctor>();
		for(Doctor d : appType.getDoctors()) {
			Doctor d1 = doctorService.findOneById(d.getId());
			certified.add(d1);
		}
		// dodavanje doktora u tip pregleda
		appType.setDoctors(certified);
		
		try {
			AppointmentType saved = appointmentTypeService.save(appType, currentUser.getClinic());
			return new ResponseEntity<>(new AppointmentTypeDto(saved), HttpStatus.CREATED);
		} catch (EntityExistsException e1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment type with given name already exists!");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "In the meantime, an appointment type with same name has been added.\nYou can add a new appointment type.");
		}
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/appointmentType/edit
	 HTTP request for editing appointment type
	 parameter: String
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/edit",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void editAppType(@RequestBody AppointmentTypeDto editedAppTypeDto) {
		
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic admins can edit appointment types from their clinic.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}

		try {
			appointmentTypeService.update(editedAppTypeDto, currentUser.getClinic().getId());
		} catch (ObjectOptimisticLockingFailureException  e1) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Appointment type you're trying to edit has been edited. Please try again.");
		} catch (EntityExistsException e2) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment type with given name already exists!");
		}
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/appointmentType/delete/{appTypeId}
	 HTTP request for deleting appointment type
	 parameter: String
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/delete/{appTypeId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteAppType(@PathVariable Long appTypeId) {
		
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinic admins can remove appointment types from their clinic.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		try {
			AppointmentType edited = appointmentTypeService.delete(appTypeId);
			//provera da li tip pregleda ima zakazane preglede
			//ukoliko ima zakazane preglede ne moze biti obrisan
			if (edited == null) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Appointment type with scheduled appointments cant be deleted!");
			}
		} catch (ObjectOptimisticLockingFailureException  e1) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Appointment type you're trying to delete has been edited. Please try again.");
		} catch (NoSuchElementException e2) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Specified appointment type does not exist.");
		}
	}

}
