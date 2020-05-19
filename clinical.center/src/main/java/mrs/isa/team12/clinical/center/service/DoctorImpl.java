package mrs.isa.team12.clinical.center.service;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.dto.DoctorPersonalInformationDto;
import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.Clinic;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.repository.DoctorRepository;
import mrs.isa.team12.clinical.center.service.interfaces.DoctorService;

@Service
public class DoctorImpl implements DoctorService {

	private DoctorRepository doctorRep;
	
	@Autowired
	private JavaMailSenderImpl javaMailSender;
	
	@Autowired
	public DoctorImpl(DoctorRepository doctorRep) {
		this.doctorRep = doctorRep;
	}

	@Override
	public Doctor findOneByEmail(String email) {
		return doctorRep.findOneByEmail(email);
	}

	@Override
	public Doctor save(Doctor d) {
		return doctorRep.save(d);
	}

	@Override
	public List<Doctor> findAll() {
		return doctorRep.findAll();
	}

	@Override
	public List<Doctor> findAllByClinicId(Long id) {
		return doctorRep.findAllByClinicId(id);
	}

	@Override
	public Doctor findOneById(Long id) {
		return doctorRep.findOneById(id);
	}

	@Override
	public List<Doctor> findAllByAppointmentTypes(AppointmentType a) {
		return doctorRep.findAllByAppointmentTypes(a);
	}

	@Override
	public List<Doctor> findAllByAppointmentTypesIn(List<AppointmentType> types) {
		return doctorRep.findAllByAppointmentTypesIn(types);
	}

	@Override
	public List<Doctor> findAllByClinicAndAppointmentTypesIn(Clinic clinic, List<AppointmentType> types) {
		return doctorRep.findAllByClinicAndAppointmentTypesIn(clinic, types);
	}

	@Override
	public List<Doctor> findAllByClinicIdAndAppointmentTypes(Long clinicId, AppointmentType type) {
		return doctorRep.findAllByClinicIdAndAppointmentTypes(clinicId, type);
	}
	
	@Override
	public Doctor updatePassword(Long doctorId, String newPassword) {
		Doctor doctorToUpdate = doctorRep.findOneById(doctorId);
		doctorToUpdate.setPassword(newPassword);
		Doctor updated = doctorRep.save(doctorToUpdate);
		return updated; 
	}

	@Override
	public Doctor update(DoctorPersonalInformationDto editedDoctor, Set<AppointmentType> appTypes) {
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
		//da li treba da se snimi mozda moze i bez snimanja tj sam da snimi ?
		Doctor updated = doctorRep.save(doctorToUpdate);
		return updated;
	}

	@Override
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
					" in clinic " + a.getClinic().getName() + ", operation room " + a.getOrdination().getName() +
					", by doctor "+ a.getDoctor().getName() + " " + a.getDoctor().getSurname() + ".\n" +
					"Patient is " + p.getName() + " " + p.getSurname() + ", with security number " + p.getSecurityNumber() + ".\n" + 
					"\nBest wishes,\nClinical center The Good Shepherd");
		}
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}

}
