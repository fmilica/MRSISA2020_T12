package mrs.isa.team12.clinical.center.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.dto.ViewClinicDto;
import mrs.isa.team12.clinical.center.dto.ViewClinicPatientDto;
import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.Clinic;
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
	 HTTP request for viewing all existing clinics
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ViewClinicDto>> getAllClinics() {
		
		ClinicalCentreAdmin currentUser;
		try {
			currentUser = (ClinicalCentreAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clinical center administrators can view  all clinics.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<Clinic> clinics = clinicService.findAll();
		List<ViewClinicDto> clinicsDto = new ArrayList<ViewClinicDto>();
		
		for(Clinic c : clinics) {
			clinicsDto.add(new ViewClinicDto(c));
		}
		
		return new ResponseEntity<>(clinicsDto, HttpStatus.OK);
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
	 url: GET localhost:8081/theGoodShepherd/clinics/filterClinics/{appTypeName}
	 HTTP request for viewing clinics that have appointmentType specified by name
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/filterClinics/{appTypeName}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ViewClinicPatientDto>> getClinicsByAppTypeName(@PathVariable("appTypeName") String appTypeName) {
		Patient currentUser;
		try {
			currentUser = (Patient) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patients can filter clinics by appointment type.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		List<AppointmentType> types = appointmentTypeService.findAllByName(appTypeName);
		
		// svi doktori iz svih klinika koji mogu da odrade pregled
		List<Doctor> sertifiedDoctors = doctorService.findAllByAppointmentTypesIn(types);

		List<ViewClinicPatientDto> clinicsWithSertifiedDoctors = new ArrayList<ViewClinicPatientDto>();
		for(Doctor d : sertifiedDoctors) {
			ViewClinicPatientDto clinic = new ViewClinicPatientDto(d.getClinic());
			clinic.setAppointmentTypes(d.getClinic().getAppointmentTypes());
			if (!clinicsWithSertifiedDoctors.contains(clinic)) {
				clinicsWithSertifiedDoctors.add(clinic);
			}
		}

		return new ResponseEntity<>(clinicsWithSertifiedDoctors, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/clinics/filterClinics/{appTypeName}/{date}
	 HTTP request for viewing clinics that have appointmentType specified by name
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/filterClinics/{appTypeName}/{appDate}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ViewClinicPatientDto>> getClinicsByAppTypeNameAndDate(@PathVariable("appTypeName") String appTypeName,
																					@PathVariable("appDate") String appDate) {
		Patient currentUser;
		try {
			currentUser = (Patient) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only patients can filter clinics by appointment type.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}

		List<AppointmentType> types = appointmentTypeService.findAllByName(appTypeName);
		
		// svi doktori iz svih klinika koji mogu da odrade pregled
		List<Doctor> sertifiedDoctors = doctorService.findAllByAppointmentTypesIn(types);
		// svi doktori koji imaju slobodnog vremena za pregled
		List<Doctor> freeSertifiedDoctors = new ArrayList<>();
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dt.parse(appDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		for (Doctor d : sertifiedDoctors) {
			// slobodna vremena za taj dan i tog doktora
			List<Integer> times = new ArrayList<Integer>();
			for (int i = d.getStartWork(); i < d.getEndWork(); i++) {
				times.add(i);
			}
			if (d.getAppointments() != null) {
				for (Appointment a : d.getAppointments()) {
					if (a.getDate().equals(date)) {
						Integer start = a.getStartTime();
						for (int i = 0; i < a.getAppType().getDuration(); i++) {
							times.remove(new Integer(start+i));
						}
					}
				}
			}
			// pronalazimo tip pregleda koji je trazen da bismo dobili njegovu duzinu
			AppointmentType doctorType = null;
			Set<AppointmentType> doctorTypes = d.getAppointmentTypes();
			for (AppointmentType at : types) {
				for (AppointmentType t : doctorTypes) {
					if (at.equals(t)) {
						doctorType = t;
						break;
					}
				}
			}
			// imamo listu slobodnih vremena
			// provera da li imamo dovoljno uzastopnih sati za pregled
			List<Integer> freeTimes = new ArrayList<Integer>();
			for(Integer i : times) {
				boolean hasConsecutive = true;
				for(int j = 1; j < doctorType.getDuration(); j++) {
					if (!times.contains(i+j)) {
						hasConsecutive = false;
					}
				}
				if (hasConsecutive) {
					freeTimes.add(i);
				}
			}
			// da li ima slobodnog taj doktor
			if (!freeTimes.isEmpty()) {
				freeSertifiedDoctors.add(d);
			}
		}
		
		List<ViewClinicPatientDto> clinicsWithSertifiedDoctors = new ArrayList<ViewClinicPatientDto>();
		for(Doctor d : freeSertifiedDoctors) {
			ViewClinicPatientDto clinic = new ViewClinicPatientDto(d.getClinic());
			clinic.setAppointmentTypes(d.getClinic().getAppointmentTypes());
			if (!clinicsWithSertifiedDoctors.contains(clinic)) {
				clinicsWithSertifiedDoctors.add(clinic);
			}
		}

		return new ResponseEntity<>(clinicsWithSertifiedDoctors, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/clinics/getDoctors/{clinicId}
	 HTTP request for viewing all doctors from clinic given by id
	 receives Long object
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/getDoctors/{clinicId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Doctor>> getAllClinicDoctors(@PathVariable("clinicId") Long clinicId) {
		// ko ima pravo?
		List<Doctor> doctors = doctorService.findAllByClinicId(clinicId);

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
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clinic with given name already exists!");
		}
		clinic.setClinicalCentre(currentUser.getClinicalCentre());
		Clinic newClinic = clinicService.save(clinic);

		return new ResponseEntity<>(newClinic, HttpStatus.CREATED);
	}
}
