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

import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.ClinicalCentre;
import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentTypeService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicalCenterService;
import mrs.isa.team12.clinical.center.service.interfaces.DoctorService;

@RestController
@RequestMapping("/theGoodShepherd/clinics")
public class ClinicController {
	
	private ClinicService clinicService;
	private ClinicalCenterService centerService;
	private DoctorService doctorService;
	private AppointmentTypeService appointmentTypeService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public ClinicController(ClinicService clinicService, ClinicalCenterService centerService, 
			DoctorService doctorService, AppointmentTypeService appointmentTypeService) {
		this.clinicService = clinicService;
		this.centerService = centerService;
		this.doctorService = doctorService;
		this.appointmentTypeService = appointmentTypeService;
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/clinics
	 HTTP request for viewing clinics
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Clinic>> getAllClinics() {
		// ko ima pravo?
		List<Clinic> clinics = clinicService.findAll();
		
		return new ResponseEntity<>(clinics, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/clinics/appTypeId/{appTypeId}
	 HTTP request for viewing clinics that have appointmentType specified by name
	 returns ResponseEntity object
	 */
	// OVO MOZE AKO NEKAKO SKONTAMO KAKO DA POSALJEMO PATHVARIABLE KAO LONG, A NE STRING
	@GetMapping(value = "/appTypeId/{appTypeId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Clinic>> getClinicsByAppTypeId(@PathVariable("appTypeId") Long appTypeId) {
		Patient currentUser;
		try {
			currentUser = (Patient) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patients can filter clinics by appointment type.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		// mislim da mora da se radi join klinike sa appType da bismo dobili sve klinike sa tim imenom appType-a
		List<Clinic> clinicsByAppTypeId = clinicService.findAllByAppointmentTypeId(appTypeId);
		
		//List<AppointmentType> appType = appointmentTypeService.findAllByName(appTypeName);
		
		
		
		//List<Clinic> clinicsByAppType = clinicService.findAllByAppointmentTypeId(appType.getId());
		
		return new ResponseEntity<>(clinicsByAppTypeId, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/clinics/{appTypeName}
	 HTTP request for viewing clinics that have appointmentType specified by name
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/{appTypeName}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Clinic>> getClinicsByAppTypeName(@PathVariable("appTypeName") String appTypeName) {
		Patient currentUser;
		try {
			currentUser = (Patient) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patients can filter clinics by appointment type.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		// mislim da mora da se radi join klinike sa appType da bismo dobili sve klinike sa tim imenom appType-a
		List<Clinic> clinicsByAppTypeName = clinicService.findAllByAppointmentTypeName(appTypeName);
		
		//List<AppointmentType> appType = appointmentTypeService.findAllByName(appTypeName);
		
		
		
		//List<Clinic> clinicsByAppType = clinicService.findAllByAppointmentTypeId(appType.getId());
		
		return new ResponseEntity<>(clinicsByAppTypeName, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/clinics/getDoctors/{clinicId}/{appTypeId}
	 HTTP request for viewing all doctors from clinic given by id
	 receives Long object
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/getDoctors/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Doctor>> getAllClinicDoctors(@PathVariable("id") Long id) {
		// ko ima pravo?
		List<Doctor> doctors = doctorService.findAllByClinicId(id);
		
		return new ResponseEntity<>(doctors, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/clinics/getDoctors/{clinicId}
	 HTTP request for viewing all doctors from clinic given by id
	 receives Long object
	 returns ResponseEntity object
	 */
	/*@GetMapping(value = "/getDoctors/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Doctor>> getAllClinicDoctors(@PathVariable("id") Long id) {
		// ko ima pravo?
		List<Doctor> doctors = doctorService.findAllByClinicId(id);
		
		return new ResponseEntity<>(doctors, HttpStatus.OK);
	}*/

	/*
	 url: POST localhost:8081/theGoodShepherd/clinics/addNewClinic
	 HTTP request for adding new clinic
	 receives Clinic object
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewClinic",
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Clinic> createClinic(@RequestBody Clinic clinic) {
		
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can create new clinics.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		Clinic c = clinicService.findOneByName(clinic.getName());
		
		if(c != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clinic with given name already exists.");
		}
		
		// isto i ovde, posto ulogovani administrator centra pravi kliniku, odatle moze da se dobavi njeno ime
		ClinicalCentre clinicalCenter = centerService.findOneByName(clinic.getClinicalCentre().getName());
		clinicalCenter.addClinic(clinic);
		
		clinic.setClinicalCentre(clinicalCenter);
		Clinic newClinic = clinicService.save(clinic);
		centerService.save(clinicalCenter);

		return new ResponseEntity<>(newClinic, HttpStatus.CREATED);
	}
}
