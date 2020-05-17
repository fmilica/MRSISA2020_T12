package mrs.isa.team12.clinical.center.dto;

import mrs.isa.team12.clinical.center.model.Clinic;

public class ClinicDto {
	
	private Long id;
	private String name;
	private Double rating;
	private String address;
	private String city;
	private String country;
	private String description;
	
	public ClinicDto() {}
	
	public ClinicDto(Clinic clinic) {
		this.id = clinic.getId();
		this.name = clinic.getName();
		this.rating = clinic.getRating();
		this.address = clinic.getAddress();
		this.city = clinic.getCity();
		this.country = clinic.getCountry();
		this.description = clinic.getDescription();
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
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
