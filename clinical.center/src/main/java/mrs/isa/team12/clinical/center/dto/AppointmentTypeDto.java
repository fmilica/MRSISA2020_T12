package mrs.isa.team12.clinical.center.dto;

import mrs.isa.team12.clinical.center.model.AppointmentType;

public class AppointmentTypeDto {
	
	private String name;
	private Double price;
	private Integer duration;
	
	public AppointmentTypeDto(AppointmentType appType) {
		this.name = appType.getName();
		this.price = appType.getPrice();
		this.duration = appType.getDuration();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
}
