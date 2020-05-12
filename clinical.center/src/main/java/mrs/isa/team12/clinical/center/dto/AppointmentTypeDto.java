package mrs.isa.team12.clinical.center.dto;

import mrs.isa.team12.clinical.center.model.AppointmentType;

public class AppointmentTypeDto {
	
	private String name;
	private Double price;
	
	public AppointmentTypeDto(AppointmentType appType) {
		this.name = appType.getName();
		this.price = appType.getPrice();
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
	
	

}
