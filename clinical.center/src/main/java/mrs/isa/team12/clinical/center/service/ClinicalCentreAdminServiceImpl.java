package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.dto.ClinicalCentreAdminPersonalInformationDto;
import mrs.isa.team12.clinical.center.model.ClinicalCentreAdmin;
import mrs.isa.team12.clinical.center.model.RegisteredUser;
import mrs.isa.team12.clinical.center.repository.ClinicalCentreAdminRepository;
import mrs.isa.team12.clinical.center.service.interfaces.ClinicalCenterAdminService;

@Service
public class ClinicalCentreAdminServiceImpl implements ClinicalCenterAdminService {

	private ClinicalCentreAdminRepository clinicCentreAdminRep;
	
	@Autowired
	private JavaMailSenderImpl javaMailSender;
	
	@Autowired
	public ClinicalCentreAdminServiceImpl(ClinicalCentreAdminRepository clinicCentreAdminRep) {
		this.clinicCentreAdminRep = clinicCentreAdminRep;
	}
	
	@Override
	public ClinicalCentreAdmin findOneByEmail(String email) {
		return clinicCentreAdminRep.findOneByEmail(email);
	}

	@Override
	public List<ClinicalCentreAdmin> findAll() {
		return clinicCentreAdminRep.findAll();
	}

	@Override
	public ClinicalCentreAdmin save(ClinicalCentreAdmin cca) {
		return clinicCentreAdminRep.save(cca);
	}

	@Override
	public void sendNotificaitionAsync(ClinicalCentreAdmin admin, RegisteredUser user, String description, boolean acceptance) {
		javaMailSender.setUsername(admin.getEmail());
		javaMailSender.setPassword(admin.getPassword());
		System.out.println("Slanje emaila...");
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(admin.getEmail());
		if(acceptance == true) {
			mail.setSubject("Registration request accepted!");
			mail.setText("Hello " + user.getName() + ",\n\nAdmin " + admin.getEmail() + " accepted your registration request!\n" + 
					"You can login now! Wellcome! " +
					"\nBest wishes,\nClinical center The Good Shepherd");
		}else {
			mail.setSubject("Registration request rejected!");
			mail.setText("Hello " + user.getName() + ",\n\nAdmin " + admin.getEmail() + " declined your registration request!\n" +
					"With proper explanation:\"" + description + "\"" +
					"\nBest wishes,\nClinical center The Good Shepherd");
		}
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}

	@Override
	public ClinicalCentreAdmin findOneById(Long id) {
		return clinicCentreAdminRep.findOneById(id);
	}

	@Override
	public ClinicalCentreAdmin update(ClinicalCentreAdminPersonalInformationDto editedProfile) {
		ClinicalCentreAdmin clinicalCentreAdminToUpdate = clinicCentreAdminRep.findOneById(editedProfile.getId());
		
		clinicalCentreAdminToUpdate.setName(editedProfile.getName());
		clinicalCentreAdminToUpdate.setSurname(editedProfile.getSurname());
		clinicalCentreAdminToUpdate.setGender(editedProfile.getGender());
		clinicalCentreAdminToUpdate.setDateOfBirth(editedProfile.getDateOfBirth());
		clinicalCentreAdminToUpdate.setPhoneNumber(editedProfile.getPhoneNumber());
		clinicalCentreAdminToUpdate.setAddress(editedProfile.getAddress());
		clinicalCentreAdminToUpdate.setCity(editedProfile.getCity());
		clinicalCentreAdminToUpdate.setCountry(editedProfile.getCountry());
		
		return clinicCentreAdminRep.save(clinicalCentreAdminToUpdate);
	}

}