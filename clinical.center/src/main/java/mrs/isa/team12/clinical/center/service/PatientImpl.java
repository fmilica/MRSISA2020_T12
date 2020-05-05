package mrs.isa.team12.clinical.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

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
}
