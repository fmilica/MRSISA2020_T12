package mrs.isa.team12.clinical.center.dto;

import mrs.isa.team12.clinical.center.model.Clinic;

public class ClinicRatingDto {

	private Long id;
	private String name;
	private String fullAddress;
	private Double rating;
	private Boolean rated;
	
	public ClinicRatingDto(Clinic clinic, Long patientId) {
		this.id = clinic.getId();
		this.name = clinic.getName();
		this.fullAddress = clinic.getAddress() + ", " + clinic.getCity() + ", " + clinic.getCountry();
		this.rating = clinic.getRating();
		this.rated = clinic.alreadyRated(patientId);
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
	public String getFullAddress() {
		return fullAddress;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public Boolean getRated() {
		return rated;
	}
	public void setRated(Boolean rated) {
		this.rated = rated;
	}
}
