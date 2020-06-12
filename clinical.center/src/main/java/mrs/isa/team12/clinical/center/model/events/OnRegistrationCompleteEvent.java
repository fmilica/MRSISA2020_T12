package mrs.isa.team12.clinical.center.model.events;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import mrs.isa.team12.clinical.center.model.RegisteredUser;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appUrl;
    private Locale locale;
    private RegisteredUser user;
 
    public OnRegistrationCompleteEvent(RegisteredUser user, Locale locale, String appUrl) {
        super(user);
         
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}
}