package mrs.isa.team12.clinical.center.model.events;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import mrs.isa.team12.clinical.center.model.RegisteredUser;
import mrs.isa.team12.clinical.center.service.interfaces.RegisteredUserService;

@Component
public class RegistrationListener implements
  ApplicationListener<OnRegistrationCompleteEvent> {
  
    @Autowired
    private RegisteredUserService service;
  
	@Autowired
	private Environment env;
    
    @Autowired
    private JavaMailSenderImpl mailSender;
 
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        try {
			this.confirmRegistration(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
 
    private void confirmRegistration(OnRegistrationCompleteEvent event) throws ObjectOptimisticLockingFailureException {
    	mailSender.setUsername(env.getProperty("spring.mail.username"));
    	mailSender.setPassword(env.getProperty("spring.mail.password"));
        String token = UUID.randomUUID().toString();
        RegisteredUser user = null;
		try {
			user = service.updateVerificationToken(event.getUser(), token);
		} catch (Exception e) {
			System.out.println("----------------------------------------------------------");
		}
        if (user != null) {
	        String recipientAddress = user.getEmail();
	        String subject = "Registration Confirmation";
	        String confirmationUrl = event.getAppUrl() + "/regitrationConfirm.html?token=" + user.getVerificationToken();
	        //String message = messages.getMessage("message.regSucc", null, event.getLocale());
	         
	        SimpleMailMessage email = new SimpleMailMessage();
	        email.setTo(recipientAddress);
	        email.setFrom(env.getProperty("spring.mail.username"));
	        email.setSubject(subject);																			//"http://localhost:8081"
	        email.setText("Follow this link to activate your profile on The Good Shepherd Clinical Center: " + "\r\n" + "https://tim12-clinical-centre.herokuapp.com" + confirmationUrl);
	        mailSender.send(email);
        }
    }
}
