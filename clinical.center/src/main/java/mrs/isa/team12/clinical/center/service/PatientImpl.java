package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.dto.PatientProfileDto;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.repository.PatientRepository;
import mrs.isa.team12.clinical.center.service.interfaces.PatientService;



@Service
public class PatientImpl implements PatientService {

	private PatientRepository patientRep;
	
	@Autowired
	private JavaMailSenderImpl javaMailSender;
	
	@Autowired
	public PatientImpl(PatientRepository patientRep) {
		this.patientRep = patientRep;
	}
	
	@Override
	public Patient findOneById(Long id) {
		return patientRep.findOneById(id);
	}

	@Override
	public Patient findOneByEmail(String email) {
		return patientRep.findOneByEmail(email);
	}

	@Override
	public Patient save(Patient p) {
		return patientRep.save(p);
	}

	@Override
	public void sendNotificaitionAsync(ClinicAdmin admin, Patient patient) {
		javaMailSender.setUsername(patient.getEmail());
		javaMailSender.setPassword(patient.getPassword());
		System.out.println("Slanje emaila...");
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(admin.getEmail());
		mail.setFrom(patient.getEmail());
		mail.setSubject("Scheduling an appointment");
		mail.setText("Hello " + admin.getName() + ",\n\nPatient " + patient.getEmail() + " sent a request for an appointment.\n" + 
					"Best wishes,\nClinical center The Good Shepherd");
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
		
	}

	@Override
	public List<Patient> findAll() {
		return patientRep.findAll();
	}

	@Override
	public List<Patient> filter(String name, String surname, String secNum) {
		return patientRep.findAllByNameContainingIgnoreCaseAndSurnameContainingIgnoreCaseAndSecurityNumberContainingIgnoreCase(name, surname, secNum);
	}

	@Override
	public Patient findOneBySecurityNumber(String securityNumber) {
		return patientRep.findOneBySecurityNumber(securityNumber);
	}

	@Override
	public void deleteById(Long id) {
		patientRep.deleteById(id);
	}

	@Override
	public Patient update(PatientProfileDto p) {
		Patient patientToUpdate = patientRep.findOneById(p.getId());
		patientToUpdate.setName(p.getName());
		patientToUpdate.setSurname(p.getSurname());
		patientToUpdate.setGender(p.getGender());
		patientToUpdate.setDateOfBirth(p.getDateOfBirth());
		patientToUpdate.setPhoneNumber(p.getPhoneNumber());
		patientToUpdate.setAddress(p.getAddress());
		patientToUpdate.setCity(p.getCity());
		patientToUpdate.setCountry(p.getCountry());
		//da li treba da se snimi mozda moze i bez snimanja tj sam da snimi ?
		return patientRep.save(patientToUpdate);
	}

	@Override
	public Patient updatePassword(Long id, String newPassword) {
		Patient patientToUpdate = patientRep.findOneById(id);
		patientToUpdate.setPassword(newPassword);
		
		return patientRep.save(patientToUpdate);
	}
}
