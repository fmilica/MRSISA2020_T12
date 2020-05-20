package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import mrs.isa.team12.clinical.center.model.Appointment;
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
	@Transactional(readOnly = false)
	public Appointment save(Appointment a) {
		return appointmentRepository.save(a);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Appointment app) {
		logger.info("> delete id:{}", app.getId());
		appointmentRepository.delete(app);
		logger.info("< delete id:{}", app.getId());
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Appointment update(Long appId) throws Exception {
		logger.info("> update id:{}", appId);
		// pronalenje pregleda
		Appointment app = this.findById(appId);
		app.setConfirmed(true);
		// snimanje u bazu
		appointmentRepository.save(app);
		logger.info("< update id:{}", app.getId());
		return app;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Appointment update(Patient patient, Long appId) throws ResponseStatusException {
		logger.info("> update id:{}", appId);
		// pronalenje pregleda
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
	public List<Appointment> findAllByPatientIdAndDoctorId(Long patientId, Long doctorId) {
		return appointmentRepository.findAllByPatientIdAndDoctorId(patientId, doctorId);
	}
	
	@Override
	public List<Appointment> findAllByPatientIdAndFinished(Long patientId, Boolean finished) {
		return appointmentRepository.findAllByPatientIdAndFinished(patientId, finished);
	}

	@Override
	public List<Appointment> findAllByPatientIdAndConfirmed(Long patientId, Boolean confirmed) {
		return appointmentRepository.findAllByPatientIdAndConfirmed(patientId, confirmed);
	}

	@Override
	public List<Appointment> findAll() {
		return appointmentRepository.findAll();
	}

	@Override
	public Appointment findById(Long id) {
		return appointmentRepository.findOneById(id);
	}

	@Override
	public List<Appointment> findAllByClinicIdAndConfirmedAndFinished(Long id, Boolean confirmed, Boolean finished) {
		return appointmentRepository.findAllByClinicIdAndConfirmedAndFinished(id, confirmed, finished);
	}

	@Override
	public List<Appointment> findAllByClinicIdAndPatient(Long clinicId, Patient patient) {
		return appointmentRepository.findAllByClinicIdAndPatient(clinicId, patient);
	}

	@Override
	public List<Appointment> findAllByPatientIdAndConfirmedAndFinished(Long patientId, Boolean confirmed,
			Boolean finished) {
		return appointmentRepository.findAllByPatientIdAndConfirmedAndFinished(patientId, confirmed, finished);
	}
	
}
