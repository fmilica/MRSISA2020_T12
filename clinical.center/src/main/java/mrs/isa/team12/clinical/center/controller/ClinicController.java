package mrs.isa.team12.clinical.center.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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

import mrs.isa.team12.clinical.center.dto.AppointmentReportsDto;
import mrs.isa.team12.clinical.center.dto.AppointmentTypeDto;
import mrs.isa.team12.clinical.center.dto.ClinicDto;
import mrs.isa.team12.clinical.center.dto.ClinicPatientDto;
import mrs.isa.team12.clinical.center.dto.DoctorDto;
import mrs.isa.team12.clinical.center.dto.IncomeReportsDto;
import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentService;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentTypeService;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicService;
import mrs.isa.team12.clinical.center.service.interfaces.DoctorService;

@RestController
@RequestMapping("/theGoodShepherd/clinics")
public class ClinicController {
	
	private ClinicService clinicService;
	private DoctorService doctorService;
	private AppointmentTypeService appointmentTypeService;
	private AppointmentService appointmentService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	public ClinicController(ClinicService clinicService,
			DoctorService doctorService, AppointmentTypeService appointmentTypeService, AppointmentService appointmentService) {
		this.clinicService = clinicService;
		this.doctorService = doctorService;
		this.appointmentTypeService = appointmentTypeService;
		this.appointmentService = appointmentService;
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/clinics
	 HTTP request for viewing all existing clinics
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClinicDto>> getAllClinics() {
		
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
		List<ClinicDto> clinicsDto = new ArrayList<ClinicDto>();
		
		for(Clinic c : clinics) {
			clinicsDto.add(new ClinicDto(c));
		}
		
		return new ResponseEntity<>(clinicsDto, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinics/viewClinicInformation
	 HTTP request for viewing clinic data for current clinic admin
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/viewClinicInformation")
	public ResponseEntity<ClinicDto> viewClinic() {
		
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only a clinic admin can view clinic information.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		Clinic clinic = clinicService.findOneById(currentUser.getClinic().getId());
		
		ClinicDto clinicDto = new ClinicDto(clinic);
		
		clinicDto.setDoctorRatings(clinic.getDoctors());
		
		return new ResponseEntity<>(clinicDto, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinics/calculateAppReport/{startDate}/{endDate}
	 HTTP request for calculating number of appointments in given period of time
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/calculateAppReport/{startDate}/{endDate}")
	public ResponseEntity<AppointmentReportsDto> calculateAppReport(@PathVariable Date startDate, @PathVariable Date endDate) {
		
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only a clinic admin can view number of appointments.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM");
		
		HashMap<Date, Integer> dates = new HashMap<Date, Integer>();
		ArrayList<String> datesStrings = new ArrayList<String>();
		ArrayList<Integer> counts = new ArrayList<Integer>();
		int total = 0;
		
		while(startDate.before(endDate)) {
			dates.put(startDate, 0);
			startDate = new Date(startDate.getTime() + (1000 * 60 * 60 * 24));
		}
		
		List<Appointment> appointments = appointmentService.findAllByClinicIdAndFinished(currentUser.getClinic().getId(), true);
		
		for(Appointment a : appointments) {
			if(dates.containsKey(a.getDate())) {
				Integer count = dates.get(a.getDate());
				dates.put(a.getDate(), ++count);
				total++;
			}
		}
		
		TreeMap<Date, Integer> sorted = new TreeMap<>(dates); 
        for (Map.Entry<Date, Integer> entry : sorted.entrySet()) {
        	datesStrings.add(format.format(entry.getKey()));
        	counts.add(entry.getValue());
        } 
		
		return new ResponseEntity<>(new AppointmentReportsDto(datesStrings, counts, total), HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinics/calculateIncReport/{startDate}/{endDate}
	 HTTP request for calculating clinic income in given period of time
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/calculateIncReport/{startDate}/{endDate}")
	public ResponseEntity<IncomeReportsDto> calculateIncReport(@PathVariable Date startDate, @PathVariable Date endDate) {
		
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only a clinic admin can view clinic income.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM");
		
		HashMap<Date, Double> dates = new HashMap<Date, Double>();
		ArrayList<String> datesStrings = new ArrayList<String>();
		ArrayList<Double> income = new ArrayList<Double>();
		int total = 0;
		
		while(startDate.before(endDate)) {
			dates.put(startDate, 0.0);
			startDate = new Date(startDate.getTime() + (1000 * 60 * 60 * 24));
		}
		
		List<Appointment> appointments = appointmentService.findAllByClinicIdAndFinished(currentUser.getClinic().getId(), true);
		
		for(Appointment a : appointments) {
			if(dates.containsKey(a.getDate())) {
				Double inc = dates.get(a.getDate());
				inc += a.getPrice();
				dates.put(a.getDate(), inc);
				total += a.getPrice();
			}
		}
		
		TreeMap<Date, Double> sorted = new TreeMap<>(dates); 
       for (Map.Entry<Date, Double> entry : sorted.entrySet()) {
	       	datesStrings.add(format.format(entry.getKey()));
	       	income.add(entry.getValue());
       } 
		
		return new ResponseEntity<>(new IncomeReportsDto(datesStrings, income, total), HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinics/viewPricelist
	 HTTP request for viewing clinic pricelist
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/viewPricelist")
	public ResponseEntity<List<AppointmentTypeDto>> viewClinicPricelist() {
		
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only a clinic admin can view clinic pricelist.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		Clinic clinic = clinicService.findOneById(currentUser.getClinic().getId());
		
		List<AppointmentTypeDto> pricelist = new ArrayList<AppointmentTypeDto>();
		
		for(AppointmentType at : clinic.getAppointmentTypes()) {
			pricelist.add(new AppointmentTypeDto(at));
		}
		
		return new ResponseEntity<>(pricelist, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinics/editClinicInformation
	 HTTP request for editing clinic information
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/editClinicInformation")
	public ResponseEntity<ClinicDto> editClinic(@RequestBody ClinicDto editedClinic) {
		
		ClinicAdmin currentUser;
		try {
			currentUser = (ClinicAdmin) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only a clinic admin can edit clinic information.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		editedClinic.setId(currentUser.getClinic().getId());
		
		Clinic clinic = clinicService.update(editedClinic);
		
		ClinicDto clinicDto = new ClinicDto(clinic);
		
		return new ResponseEntity<>(clinicDto, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/theGoodShepherd/clinics/{clinicId}
	 HTTP request for viewing data of clinic specified by clinicId
	 receives Long clinicId
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/{clinicId}")
	public ResponseEntity<ClinicDto> getClinic(@PathVariable("clinicId") Long clinicId) {
		
		Patient currentUser;
		try {
			currentUser = (Patient) session.getAttribute("currentUser");
		} catch (ClassCastException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only a patient can view clinic data.");
		}
		if (currentUser == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No user loged in!");
		}
		
		Clinic clinic = clinicService.findOneById(clinicId);
		
		ClinicDto clinicDto = new ClinicDto(clinic);
		
		return new ResponseEntity<>(clinicDto, HttpStatus.OK);
	}
	
	/*
	 url: GET localhost:8081/theGoodShepherd/clinics/filterClinics/{appTypeName}
	 HTTP request for viewing clinics that have appointmentType specified by name
	 returns ResponseEntity object
	 */
	@GetMapping(value = "/filterClinics/{appTypeName}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClinicPatientDto>> getClinicsByAppTypeName(@PathVariable("appTypeName") String appTypeName) {
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

		List<ClinicPatientDto> clinicsWithSertifiedDoctors = new ArrayList<ClinicPatientDto>();
		for(Doctor d : sertifiedDoctors) {
			ClinicPatientDto clinic = new ClinicPatientDto(d.getClinic());
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
	public ResponseEntity<List<ClinicPatientDto>> getClinicsByAppTypeNameAndDate(@PathVariable("appTypeName") String appTypeName,
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
			date = new Date(dt.parse(appDate).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		for (Doctor d : sertifiedDoctors) {
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
			List<Integer> freeTimes = d.getAvailableTimesForDateAndType(date, doctorType);
			// da li ima slobodnog taj doktor
			if (!freeTimes.isEmpty()) {
				freeSertifiedDoctors.add(d);
			}
		}
		
		List<ClinicPatientDto> clinicsWithSertifiedDoctors = new ArrayList<ClinicPatientDto>();
		for(Doctor d : freeSertifiedDoctors) {
			ClinicPatientDto clinic = new ClinicPatientDto(d.getClinic());
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
	public ResponseEntity<List<DoctorDto>> getAllClinicDoctors(@PathVariable("clinicId") Long clinicId) {
		/*
		 * da
		 * li
		 * ce
		 * ovo
		 * neko
		 * nekad
		 * koristiti
		 * ?
		 */
		
		// ko ima pravo?
		List<Doctor> doctors = doctorService.findAllByClinicId(clinicId);
		List<DoctorDto> doctorsDto = new ArrayList<DoctorDto>();
		for (Doctor d : doctors) {
			doctorsDto.add(new DoctorDto(d));
		}
		return new ResponseEntity<>(doctorsDto, HttpStatus.OK);
	}

	/*
	 url: POST localhost:8081/theGoodShepherd/clinics/addNewClinic
	 HTTP request for adding new clinic
	 receives Clinic object
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addNewClinic",
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicDto> createClinic(@RequestBody Clinic clinic) {
		
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

		return new ResponseEntity<>(new ClinicDto(newClinic), HttpStatus.CREATED);
	}
}
