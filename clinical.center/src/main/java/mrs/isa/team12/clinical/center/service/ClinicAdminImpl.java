package mrs.isa.team12.clinical.center.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
		if(acceptance == true) {
			if(appointment.getDiscount() == null) {
				disc = "0";
			}
			else {
				disc = appointment.getDiscount()*100 + "";
			}
			mail.setSubject("Appointment request accepted!");
			mail.setText("Hello " + patient.getName() + ",\n\nAdmin " + admin.getEmail() + " accepted your appointment request!\n" + 
					appointment.getAppType().getName() + " appointment scheduled for " + 
					sdf1.format(appointment.getDate()) + " at " + appointment.getStartTime() + ":00" +
					" in clinic " + appointment.getClinic().getName() + ", ordination " + appointment.getOrdination().getName() +
					", by doctor "+ appointment.getDoctor().getName() + " " + appointment.getDoctor().getSurname() + ".\n" +
					"Your appointment costs " + appointment.getAppType().getPrice() + " with " + disc + "% of discount." +
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
