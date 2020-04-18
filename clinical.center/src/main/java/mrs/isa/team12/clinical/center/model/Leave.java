package mrs.isa.team12.clinical.center.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import mrs.isa.team12.clinical.center.model.enums.LeaveType;

@Entity
public class Leave {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "startDate", nullable = false, unique = false)
	private Date startDate;
	
	@Column(name = "endDate", nullable = false, unique = false)
	private Date endDate;
	
	@Column(name = "type", nullable = false, unique = false)
	private LeaveType type;

	public Leave() {}
	
	public Leave(Date startDate, Date endDate, LeaveType type) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public LeaveType getType() {
		return type;
	}

	public void setType(LeaveType type) {
		this.type = type;
	}
	
	

}
