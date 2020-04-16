package mrs.isa.team12.clinical.center.model;

public class RegistrationRequest {
	
	private RegisteredUser user;
	private Boolean approved;
	private String description;
	
	public RegistrationRequest() {
		super();
	}
	public RegistrationRequest(RegisteredUser user, Boolean approved, String description) {
		super();
		this.user = user;
		this.approved = approved;
		this.description = description;
	}
	public RegisteredUser getUser() {
		return user;
	}
	public void setUser(RegisteredUser user) {
		this.user = user;
	}
	public Boolean getApproved() {
		return approved;
	}
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
