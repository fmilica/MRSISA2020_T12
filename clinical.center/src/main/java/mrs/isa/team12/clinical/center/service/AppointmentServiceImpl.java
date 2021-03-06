package mrs.isa.team12.clinical.center.service;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.LeaveRequest;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.repository.AppointmentRepository;
import mrs.isa.team12.clinical.center.service.interfaces.AppointmentService;

@Service
@Transactional(readOnly = true)
public class AppointmentServiceImpl implements AppointmentService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	public AppointmentServiceImpl(AppointmentRepository atr) {
		this.appointmentRepository = atr;
	}
	
	@Override
	public Appointment findById(Long id) {
		logger.info("> findOneById id:{}", id);
		Appointment app = appointmentRepository.findOneById(id);
		logger.info("< findOneById id:{}", id);
		return app;
	}
	
	@Override
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
	public Appointment save(Appointment a) {
		logger.info("> create");
		List<Appointment> existing = appointmentRepository.findAllExisting(a.getDoctor().getId(), a.getDate(), a.getStartTime(), a.getEndTime());
		if (existing.isEmpty()) {
			Appointment app = appointmentRepository.save(a);
			logger.info("< create");
			return app;
		} else {
			logger.info("< EntityExistsException");
			throw new EntityExistsException();
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Long appId) {
		logger.info("> delete id:{}", appId);
		Appointment app = appointmentRepository.findOneById(appId);
		if (app == null) {
			throw new NoSuchElementException();
		}
		app.setActive(false);
		app = appointmentRepository.save(app);
		logger.info("< delete id:{}", app.getId());
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Appointment update(Long appId) throws Exception {
		logger.info("> update id:{}", appId);
		// pronalenje pregleda
		Appointment app = this.findById(appId);
		app.setConfirmed(true);
		app.getAppointmentRequest().setApproved(true);
		// snimanje u bazu
		appointmentRepository.save(app);
		logger.info("< update id:{}", app.getId());
		return app;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Appointment update(Patient patient, Long appId) throws ResponseStatusException {
		logger.info("> update id:{}", appId);
		// pronalazenje pregleda
		Appointment app = this.findById(appId);
		// postavljanje unakrsne veze sa pacijentom
		app.setPatient(patient);
		patient.addAppointment(app);
		// snimanje u bazu
		appointmentRepository.save(app);
		logger.info("< update id:{}", app.getId());
		return app;
	}
	
	@Override
	public List<Appointment> findAll() {
		logger.info("> findAll");
		List<Appointment> appointments = appointmentRepository.findAll();
		logger.info("< findAll");
		return appointments;
	}
	
	@Override
	public List<Appointment> findAllByClinicIdAndFinished(Long id, Boolean finished) {
		logger.info("> findAllByClinicIdAndFinished id:{}, confirmed:{}", id, finished);
		List<Appointment> appointments = appointmentRepository.findAllByClinicIdAndFinished(id, finished);
		logger.info("< findAllByClinicIdAndFinished id:{}, confirmed:{}", id, finished);
		return appointments;
	}

	@Override
	public List<Appointment> findAllByClinicIdAndPatient(Long clinicId, Patient patient) {
		logger.info("> findAllByClinicIdAndPatient");
		List<Appointment> appointments = appointmentRepository.findAllByClinicIdAndPatient(clinicId, patient);
		logger.info("< findAllByClinicIdAndPatient");
		return appointments;
	}
	
	@Override
	public List<Appointment> findAllByPatientIdAndDoctorId(Long patientId, Long doctorId) {
		logger.info("> findAllByPatientIdAndDoctorId");
		List<Appointment> appointments = appointmentRepository.findAllByPatientIdAndDoctorId(patientId, doctorId);
		logger.info("< findAllByPatientIdAndDoctorId");
		return appointments;
	}
	
	@Override
	public List<Appointment> findAllByPatientIdAndFinished(Long patientId, Boolean finished) {
		logger.info("> findAllByPatientIdAndFinished");
		List<Appointment> appointments =  appointmentRepository.findAllByPatientIdAndFinished(patientId, finished);
		logger.info("< findAllByPatientIdAndFinished");
		return appointments;
	}

	@Override
	public List<Appointment> findAllByPatientIdAndConfirmed(Long patientId, Boolean confirmed) {
		logger.info("> findAllByPatientIdAndConfirmed");
		List<Appointment> appointments =  appointmentRepository.findAllByPatientIdAndConfirmed(patientId, confirmed);
		logger.info("< findAllByPatientIdAndConfirmed");
		return appointments;
	}

	@Override
	public List<Appointment> findAllByClinicIdAndConfirmedAndFinished(Long id, Boolean confirmed, Boolean finished) {
		logger.info("> findAllByClinicIdAndConfirmedAndFinished");
		List<Appointment> appointments = appointmentRepository.findAllByClinicIdAndConfirmedAndFinished(id, confirmed, finished);
		logger.info("< findAllByClinicIdAndConfirmedAndFinished");
		return appointments;
	}

	@Override
	public List<Appointment> findAllByPatientIdAndConfirmedAndFinished(Long patientId, Boolean confirmed,
			Boolean finished) {
		logger.info("> findAllByPatientIdAndConfirmedAndFinished");
		List<Appointment> appointments = appointmentRepository.findAllByPatientIdAndConfirmedAndFinished(patientId, confirmed, finished);
		logger.info("< findAllByPatientIdAndConfirmedAndFinished");
		return appointments;
	}

	@Override
	public List<Appointment> findAllByDoctorId(Long id) {
		logger.info("> findAllByDoctorId");
		List<Appointment> apps = appointmentRepository.findAllByDoctorId(id);
		logger.info("< findAllByDoctorId");
		return apps;
	}

	@Override
	public List<Appointment> findAllByPatientId(Long id) {
		logger.info("> findAllByPatientId");
		List<Appointment> apps = appointmentRepository.findAllByPatientId(id);
		logger.info("< findAllByPatientId");
		return apps;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void deleteAppointments(Doctor d, LeaveRequest lr) {
		logger.info("> deleteAppointments");
		
		List<Appointment> apps = findAllByDoctorIdAndDateBetween(d.getId(), lr.getLeave().getStartDate(), lr.getLeave().getEndDate());
		
		for (Appointment app : apps) {
			app.setActive(false);
			appointmentRepository.save(app);
			//obavesti pacijenta
		}
		
		Set<Doctor> doctors = new HashSet<Doctor>();
		doctors.add(d);
		
		List<Appointment> oper = findAllByDoctorsIn(doctors);
		
		for (Appointment app : oper) {
			app.setActive(false);
			appointmentRepository.save(app);
			//obavesti pacijenta
		}
		
		logger.info("< deleteAppointments");
	}

	@Override
	public List<Appointment> findAllByDoctorsIn(Set<Doctor> doctors) {
		logger.info("> findAllByDoctorsIn");
		List<Appointment> apps = appointmentRepository.findAllByDoctorsIn(doctors);
		logger.info("< findAllByDoctorsIn");
		return apps;
	}
	
	@Override
	public List<Appointment> findAllByDoctorIdAndDateBetween(Long id, Date d1, Date d2) {
		logger.info("> findAllByDoctorIdAndDateBetween");
		List<Appointment> apps = appointmentRepository.findAllByDoctorIdAndDateBetween(id,d1,d2);
		logger.info("< findAllByDoctorIdAndDateBetween");
		return apps;
	}

	@Override
	public List<Appointment> findAllByOrdinationId(Long id) {
		logger.info("> findAllByOrdinationId id:{id}", id);
		List<Appointment> apps = appointmentRepository.findAllByOrdinationId(id);
		logger.info("> findAllByOrdinationId id:{id}", id);
		return apps;
	}
	
}
