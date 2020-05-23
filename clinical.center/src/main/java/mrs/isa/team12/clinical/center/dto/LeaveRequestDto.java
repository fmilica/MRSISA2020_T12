package mrs.isa.team12.clinical.center.dto;

import java.text.SimpleDateFormat;

import mrs.isa.team12.clinical.center.model.LeaveRequest;
import mrs.isa.team12.clinical.center.model.enums.LeaveType;

public class LeaveRequestDto {
	private String startDate;
	private String endDate;
	private String type;
	private String description;
	private String approved;
	
	public LeaveRequestDto() {}
	
	public LeaveRequestDto(LeaveRequest lr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.startDate = sdf.format(lr.getLeave().getStartDate());
		this.endDate = sdf.format(lr.getLeave().getEndDate());
		if(lr.getLeave().getType() == LeaveType.Paid) {
			this.type = "Paid";
		}else {
			this.type = "Vacation";
		}
		this.description = lr.getDescription();
		if(lr.getApproved()) {
			this.approved = "Yes";
		}else {
			this.approved = "No";
		}
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}
}
