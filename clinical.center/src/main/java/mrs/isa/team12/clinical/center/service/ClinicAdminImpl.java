package mrs.isa.team12.clinical.center.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.Appointment;
import mrs.isa.team12.clinical.center.model.ClinicAdmin;
import mrs.isa.team12.clinical.center.model.Patient;
import mrs.isa.team12.clinical.center.repository.ClinicAdminRepository;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicAdminService;

@Service
public class ClinicAdminImpl implements ClinicAdminService {
	
	private ClinicAdminRepository clinicAdminRep;
	
	@Autowired
	private JavaMailSenderImpl javaMailSender;
	
	@Autowired
	public ClinicAdminImpl(ClinicAdminRepository clinicAdminRep) {
		this.clinicAdminRep = clinicAdminRep;
	}

	@Override
	public List<ClinicAdmin> findAll() {
		return this.clinicAdminRep.findAll();
	}

	@Override
	public ClinicAdmin save(ClinicAdmin ca) {
		return this.clinicAdminRep.save(ca);
	}

	@Override
	public ClinicAdmin findOneByEmail(String email) {
		return clinicAdminRep.findOneByEmail(email);
	}

	@Override
	public void sendNotificaitionAsync(ClinicAdmin admin, Patient patient, Appointment appointment, boolean acceptance) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy.");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		javaMailSender.setUsername(admin.getEmail());
		javaMailSender.setPassword(admin.getPassword());
		System.out.println("Slanje emaila...");
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(patient.getEmail());
		mail.setFrom(admin.getEmail());
		
		String disc = "";
		if(appointment.getDiscount() == null) {
			disc = "0";
		}
		else {
			disc = appointment.getDiscount()*100 + "";
		}
		if(acceptance == true) {
			mail.setSubject("Appointment request accepted!");
			mail.setText("Hello " + patient.getName() + ",\n\nAdmin " + admin.getEmail() + " accepted your appointment request!\n" + 
					appointment.getType().getName() + " appointment scheduled for " + 
					sdf1.format(appointment.getDate()) + " at " + sdf2.format(appointment.getTime()) +
					" in clinic " + appointment.getClinic().getName() + ", ordination " + appointment.getOrdination().getName() +
					", by doctor "+ appointment.getDoctor().getName() + " " + appointment.getDoctor().getSurname() + ".\n" +
					"Your appointment costs " + appointment.getType().getPrice() + " with " + disc + "% of discount." +
					"\nBest wishes,\nClinical center The Good Shepherd");
		}else {
			mail.setSubject("Appointment request rejected!");
			mail.setText("Hello " + patient.getName() + ",\n\nAdmin " + admin.getEmail() + " declined your appointment request!\n" + 
					"Best wishes,\nClinical center The Good Shepherd");
		}
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}

	@Override
	public List<ClinicAdmin> findAllByClinicId(Long clinicId) {
		return clinicAdminRep.findAllByClinicId(clinicId);
	}

	

}
