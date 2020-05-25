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
	public Appointment findById(Long id) {
		logger.info("> findOneById id:{}", id);
		Appointment app = appointmentRepository.findOneById(id);
		logger.info("< findOneById id:{}", id);
		return app;
	}
	
	@Override
	//cuvanje u bazu treba da bude pesimisticko je l ? posto on jos ne postoji, to da je pitamo jer ja ne znam kako to
	@Transactional(readOnly = false)
	public Appointment save(Appointment a) {
		logger.info("> create");
		Appointment app = appointmentRepository.save(a);
		logger.info("< create");
		return app;
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
	public List<Appointment> findAll() {
		logger.info("> findAll");
		List<Appointment> appointments = appointmentRepository.findAll();
		logger.info("< findAll");
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
	
}
