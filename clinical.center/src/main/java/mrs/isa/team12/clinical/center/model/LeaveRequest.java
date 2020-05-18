package mrs.isa.team12.clinical.center.model;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "leave_request")
public class LeaveRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Version
	private Long version;
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "leave_id")
	private Leave leave;
	
	@Column(name = "approved", nullable = false, unique = false)
	private Boolean approved;
	
	@Column(name = "description", nullable = true, unique = false)
	private String description;
	
	public LeaveRequest() {}

	public LeaveRequest(Leave leave, Boolean approved, String description) {
		super();
		this.leave = leave;
		this.approved = approved;
		this.description = description;
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
}
