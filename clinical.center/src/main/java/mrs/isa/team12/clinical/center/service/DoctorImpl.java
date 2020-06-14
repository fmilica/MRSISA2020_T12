package mrs.isa.team12.clinical.center.service;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mrs.isa.team12.clinical.center.dto.DoctorPersonalInformationDto;
import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.AppointmentRequest;
import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.Leave;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.model.Rating;
import mrs.isa.team12.clinical.center.repository.DoctorRepository;
import mrs.isa.team12.clinical.center.service.interfaces.DoctorService;

@Service
@Transactional(readOnly = true)
public class DoctorImpl implements DoctorService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private DoctorRepository doctorRep;
	
	@Autowired
	private JavaMailSenderImpl javaMailSender;
	
	@Autowired
	public DoctorImpl(DoctorRepository doctorRep) {
		this.doctorRep = doctorRep;
	}

	@Override
	@Transactional(readOnly = false)
	// zbog @Lock anotacije u repository klasi
	public Doctor findOneById(Long id) {
		logger.info("> findOneById id:{}", id);
		Doctor doctor =  doctorRep.findOneById(id);
		logger.info("< findOneById id:{}", id);
		return doctor;
	}
	
	@Override
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
	public Doctor save(Doctor d) {
		logger.info("> create");
		Doctor doctor = doctorRep.findOneByEmail(d.getEmail());
		if(doctor != null) {
			logger.info("< EntityExistsException");
			throw new EntityExistsException();
		}
		d.setActive(true);
		Doctor saved =  doctorRep.save(d);
		logger.info("< create");
		return saved;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Doctor d) {
		logger.info("> delete id:{}", d.getId());
		d.setActive(false);
		doctorRep.save(d);
		logger.info("< delete id:{}", d.getId());
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Doctor updatePassword(Long doctorId, String newPassword) {
		logger.info("> update id{}", doctorId);
		Doctor doctorToUpdate = doctorRep.findOneById(doctorId);
		String oldPassword = doctorToUpdate.getPassword();
        if(oldPassword.equals(newPassword)) {
            return null;
        }
		doctorToUpdate.setPassword(newPassword);
		doctorToUpdate.setLogged(true);
		Doctor updated = doctorRep.save(doctorToUpdate);
		logger.info("< update id{}", doctorId);
		return updated; 
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Doctor update(DoctorPersonalInformationDto editedDoctor, Set<AppointmentType> appTypes) {
		logger.info("> update id{}", editedDoctor.getId());
		Doctor doctorToUpdate = doctorRep.findOneById(editedDoctor.getId());
		doctorToUpdate.setName(editedDoctor.getName());
		doctorToUpdate.setSurname(editedDoctor.getSurname());
		doctorToUpdate.setGender(editedDoctor.getGender());
		doctorToUpdate.setDateOfBirth(editedDoctor.getDateOfBirth());
		doctorToUpdate.setStartWork(editedDoctor.getStartWork());
		doctorToUpdate.setEndWork(editedDoctor.getEndWork());
		doctorToUpdate.setPhoneNumber(editedDoctor.getPhoneNumber());
		doctorToUpdate.setAddress(editedDoctor.getAddress());
		doctorToUpdate.setCity(editedDoctor.getCity());
		doctorToUpdate.setCountry(editedDoctor.getCountry());
		doctorToUpdate.setSpecialization(editedDoctor.getSpecialization());
		doctorToUpdate.setAppointmentTypes(appTypes);
		Doctor updated = doctorRep.save(doctorToUpdate);
		logger.info("< update id{}", editedDoctor.getId());
		return updated;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Doctor update(Doctor d, Appointment a) {
		Doctor doctorToUpdate = doctorRep.findOneById(d.getId());
		logger.info("> update id{}", doctorToUpdate.getId());
		doctorToUpdate.addAppointment(a);
		Doctor updated = doctorRep.save(doctorToUpdate);
		logger.info("< update id{}", doctorToUpdate.getId());
		return updated; 
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Doctor update(Doctor d, Patient p) {
		Doctor doctorToUpdate = doctorRep.findOneById(d.getId());
		logger.info("> update id{}", doctorToUpdate.getId());
		doctorToUpdate.addPatient(p);
		Doctor updated = doctorRep.save(doctorToUpdate);
		logger.info("< update id{}", doctorToUpdate.getId());
		return updated; 
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Doctor updateRating(Doctor d) {
		Doctor doctorToUpdate = doctorRep.findOneById(d.getId());
		logger.info("> update id{}", doctorToUpdate.getId());
		int ratingSum = 0;
		for(Rating r : doctorToUpdate.getRatings()) {
			ratingSum += r.getRating();
		}
		double newRating = ratingSum / doctorToUpdate.getRatings().size();
		doctorToUpdate.setRating(newRating);
		Doctor updated = doctorRep.save(doctorToUpdate);
		logger.info("< update id{}", updated.getId());
		return updated; 
	}

	@Override
	public Doctor findOneByEmail(String email) {
		logger.info("> findOneByEmail email:{}", email);
		Doctor doctor =  doctorRep.findOneByEmail(email);
		logger.info("< findOneByEmail email:{}", email);
		return doctor;
	}
	
	@Override
	public List<Doctor> findAll() {
		logger.info("> findAll");
		List<Doctor> doctors = doctorRep.findAll();
		logger.info("< findAll");
		return doctors;
	}

	@Override
	public List<Doctor> findAllByClinicId(Long id) {
		logger.info("> findAllByClinicId id:{}", id);
		List<Doctor> doctors = doctorRep.findAllByClinicId(id);
		logger.info("< findAllByClinicId id:{}", id);
		return doctors;
	}

	@Override
	public List<Doctor> findAllByAppointmentTypes(AppointmentType a) {
		logger.info("> findAllByAppointmentTypes appTypeName:{}", a.getName());
		List<Doctor> doctors =  doctorRep.findAllByAppointmentTypes(a);
		logger.info("< findAllByAppointmentTypes appTypeName:{}", a.getName());
		return doctors;
	}

	@Override
	public List<Doctor> findAllByAppointmentTypesIn(List<AppointmentType> types) {
		logger.info("> findAllByAppointmentTypesIn");
		List<Doctor> doctors =  doctorRep.findAllByAppointmentTypesIn(types);
		logger.info("< findAllByAppointmentTypesIn");
		return doctors;
	}

	@Override
	public List<Doctor> findAllByClinicIdAndAppointmentTypes(Long clinicId, AppointmentType type) {
		logger.info("> findAllByClinicIdAndAppointmentTypes clinicId:{}, appTypeName:{}", clinicId, type.getName());
		List<Doctor> doctors =  doctorRep.findAllByClinicIdAndAppointmentTypes(clinicId, type);
		logger.info("< findAllByClinicIdAndAppointmentTypes clinicId:{}, appTypeName:{}", clinicId, type.getName());
		return doctors;
	}
	
	@Override
	public List<Doctor> findAllByClinicAndAppointmentTypesIn(Clinic clinic, List<AppointmentType> types) {
		logger.info("> findAllByClinicAndAppointmentTypesIn");
		List<Doctor> doctors =  doctorRep.findAllByClinicAndAppointmentTypesIn(clinic, types);
		logger.info("< findAllByClinicAndAppointmentTypesIn");
		return doctors;
	}

	@Override
	@Async
	public void sendNotificaitionAsync(ClinicAdmin ca, Patient p, Appointment a, boolean acceptance, Set<Doctor> doctors) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy.");
		javaMailSender.setUsername(ca.getEmail());
		javaMailSender.setPassword(ca.getPassword());
		System.out.println("Slanje emaila...");
		SimpleMailMessage mail = new SimpleMailMessage();
		Iterator<Doctor> itr = doctors.iterator();
		String[] to = new String[doctors.size()+1];
		to[0] = a.getDoctor().getEmail();
		int i = 1;
		while(itr.hasNext()) {
			String toMail = itr.next().getEmail();
			to[i] = toMail;
			i++;
		}
		mail.setTo(to);
		mail.setFrom(ca.getEmail());
		if(acceptance == true) {
			mail.setSubject("New operation assigned!");
			mail.setText("Hello Doctor" + ",\n\nAdmin " + ca.getEmail() + " assigned you to participate in an operation!\n" + 
					a.getAppType().getName() + " operation scheduled for " + 
					sdf1.format(a.getDate()) + " at " + a.getStartTime() + ":00" +
					" in clinic " + a.getClinic().getName() + ", operation room " + a.getOrdination().getName() + " " + a.getOrdination().getOrdinationNumber() + 
					", by doctor "+ a.getDoctor().getName() + " " + a.getDoctor().getSurname() + ".\n" +
					"Patient is " + p.getName() + " " + p.getSurname() + ", with security number " + p.getSecurityNumber() + ".\n" + 
					"\nBest wishes,\nClinical center The Good Shepherd");
		}
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}

	@Override
	@Async
	public void sendDoctorNotificaitionAsync(ClinicAdmin ca, Patient p, Appointment a, boolean acceptance, Doctor d) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy.");
		javaMailSender.setUsername(ca.getEmail());
		javaMailSender.setPassword(ca.getPassword());
		System.out.println("Slanje emaila...");
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(d.getEmail());
		mail.setFrom(ca.getEmail());
		if(acceptance == true) {
			mail.setSubject("New appointment assigned!");
			mail.setText("Hello " + d.getName() + " " + d.getSurname() + ",\n\nYou have new appointment!\n" + 
					a.getAppType().getName() + " appointment scheduled for " + 
					sdf1.format(a.getDate()) + " at " + a.getStartTime() + ":00" +
					" in clinic " + a.getClinic().getName() + ", operation room " + a.getOrdination().getName() + ".\n" +
					"Patient is " + p.getName() + " " + p.getSurname() + ", with security number " + p.getSecurityNumber() + ".\n" + 
					"\nBest wishes,\nClinical center The Good Shepherd");
		}
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
	public Doctor update(AppointmentRequest ar, Long id) {
		logger.info("> update id:{}", id);
		Doctor d = doctorRep.findOneById(id);
		if(d == null) {
			logger.info("< NoSuchElementException", id);
			throw new NoSuchElementException();
		}
		for (Appointment a : d.getAppointments()) {
			if (a.getConfirmed()) {
				if(a.getDate().equals(ar.getAppointment().getDate()) && (
							((a.getStartTime()>= ar.getAppointment().getStartTime() && a.getStartTime() <= ar.getAppointment().getEndTime())
							&& (a.getEndTime() >= ar.getAppointment().getStartTime() && a.getEndTime() <= ar.getAppointment().getEndTime()))
							|| (a.getStartTime() <= ar.getAppointment().getStartTime() && a.getEndTime() >= ar.getAppointment().getEndTime()))) {
					logger.info("< DoctorNoLongerAvailable", id);
					return null;
				}
			}
		}
		for (Leave l : d.getLeaveList()) {
			if(l.getLeaveRequest().getApproved()) {
				if((ar.getAppointment().getDate().after(l.getStartDate()) && ar.getAppointment().getDate().before(l.getEndDate()))){
					logger.info("< DoctorNoLongerAvailable", id);
					return null;
				}
			}
		}
		ar.getAppointment().addDoctor(d);
		d.addAppointment(ar.getAppointment());
		d = doctorRep.save(d);
		logger.info("< update id:{}", id);
		return d;
	}

}
