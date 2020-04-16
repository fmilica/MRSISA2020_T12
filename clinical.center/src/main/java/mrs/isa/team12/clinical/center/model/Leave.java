package mrs.isa.team12.clinical.center.model;

import java.util.Date;

import mrs.isa.team12.clinical.center.model.enums.LeaveType;

public class Leave {
	
	private Date startDate;
	private Date endDate;
	private LeaveType type;

	public Leave() {
	}
	
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
