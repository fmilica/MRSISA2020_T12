package mrs.isa.team12.clinical.center.dto;

import mrs.isa.team12.clinical.center.model.Clinic;

public class ViewClinicDto {
	
	private String name;
	private String address;
	private String city;
	private String country;
	private String description;
	
	public ViewClinicDto(Clinic clinic) {
		this.name = clinic.getName();
		this.address = clinic.getAddress();
		this.city = clinic.getCity();
		this.country = clinic.getCountry();
		this.description = clinic.getDescription();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
