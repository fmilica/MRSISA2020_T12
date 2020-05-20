package mrs.isa.team12.clinical.center.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.dto.ClinicAdminPersonalInformationDto;
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
	public ClinicAdmin findOneById(Long id) {
		return clinicAdminRep.findOneById(id);
	}

	@Override
	@Async
	public void sendNotificaitionAsync(ClinicAdmin admin, Patient patient, Appointment appointment, boolean acceptance, boolean operation, boolean predefined) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy.");
		String op = "Appointment";
		if(operation == true) {
			op = "Operation";
		}
		String adminFrom = "";
		if(predefined == true) {
			adminFrom = "thegoodshepherdadm@gmail.com";
			javaMailSender.setUsername(adminFrom);
			javaMailSender.setPassword("admin1tgs");
		}else {
			adminFrom = admin.getEmail();
			javaMailSender.setUsername(admin.getEmail());
			javaMailSender.setPassword(admin.getPassword());
		}
		System.out.println("Slanje emaila...");
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(patient.getEmail());
		
		mail.setFrom(adminFrom);
		String disc = "";
		if(acceptance == true) {
			if(appointment.getDiscount() == null) {
				disc = "0";
			}
			else {
				disc = appointment.getDiscount()*100 + "";
			}
			mail.setSubject(op+" request accepted!");
			String mailText = "Hello " + patient.getName();
			
			String details = appointment.getAppType().getName() +" "+ op.toLowerCase() + " scheduled for " + 
					sdf1.format(appointment.getDate()) + " at " + appointment.getStartTime() + ":00" +
					" in clinic " + appointment.getClinic().getName() + ", ordination " + appointment.getOrdination().getName() +
					" " + appointment.getOrdination().getOrdinationNumber() +
					", by doctor "+ appointment.getDoctor().getName() + " " + appointment.getDoctor().getSurname() + ".\n" +
					"That "+ op.toLowerCase() +" costs " + appointment.getAppType().getPrice() + "\u20ac with " + disc + "% of discount.";
			
			if(predefined == true) {
				mailText += ",\n\nYou scheduled an appointment!\n";
				mailText += details;
				mailText += "You may see it in 'My Appointments' tab on your profile.";
			}
			else {
				mailText += ",\n\nAdmin " + adminFrom + " accepted your "+ op.toLowerCase() +" request!\n";
				mailText += details;
				if(operation == true) {
					mailText += "You may see it in 'My Appointments' tab on your profile.";
				}else {
					mailText += "You may confirm it or decline it in 'My Appointments' tab on your profile.";
				}
			}
			mailText += "\nBest wishes,\nClinical center The Good Shepherd";
			mail.setText(mailText);
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

	@Override
	public ClinicAdmin updatePassword(Long id, String newPassword) {
		ClinicAdmin clinicAdminToUpdate = clinicAdminRep.findOneById(id);
		clinicAdminToUpdate.setPassword(newPassword);

		return clinicAdminRep.save(clinicAdminToUpdate);
	}

	@Override
	public ClinicAdmin update(ClinicAdminPersonalInformationDto editedProfile) {
		ClinicAdmin clinicAdminToUpdate = clinicAdminRep.findOneById(editedProfile.getId());
		clinicAdminToUpdate.setName(editedProfile.getName());
		clinicAdminToUpdate.setSurname(editedProfile.getSurname());
		clinicAdminToUpdate.setGender(editedProfile.getGender());
		clinicAdminToUpdate.setDateOfBirth(editedProfile.getDateOfBirth());
		clinicAdminToUpdate.setPhoneNumber(editedProfile.getPhoneNumber());
		clinicAdminToUpdate.setAddress(editedProfile.getAddress());
		clinicAdminToUpdate.setCity(editedProfile.getCity());
		clinicAdminToUpdate.setCountry(editedProfile.getCountry());
		//da li treba da se snimi mozda moze i bez snimanja tj sam da snimi ?
		return clinicAdminRep.save(clinicAdminToUpdate);
	}

	

}
