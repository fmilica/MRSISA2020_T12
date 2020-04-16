package mrs.isa.team12.clinical.center.model;

public class LeaveRequest {
	
	private Leave leave;
	private Boolean approved;
	private String description;
	
	private Clinic clinic;

	public LeaveRequest() {
		super();
	}

	public LeaveRequest(Leave leave, Boolean approved, String description, Clinic clinic) {
		super();
		this.leave = leave;
		this.approved = approved;
		this.description = description;
		this.clinic = clinic;
	}

	public Leave getLeave() {
		return leave;
	}

	public void setLeave(Leave leave) {
		this.leave = leave;
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

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

}
