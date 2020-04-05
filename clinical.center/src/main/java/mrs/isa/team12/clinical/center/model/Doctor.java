package mrs.isa.team12.clinical.center.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Doctor extends RegisteredUser {

	private String workingHours;
	private double rating;
	
	
	public Doctor() {
		// TODO Auto-generated constructor stub
	}

	public Doctor(@JsonProperty("username") String username, @JsonProperty("password") String password, 
			@JsonProperty("role") Role role,@JsonProperty("name") String name,@JsonProperty("surname") String surname,
			@JsonProperty("address") String address,@JsonProperty("city") String city, @JsonProperty("country")String country,
			@JsonProperty("phoneNumber") String phoneNumber,@JsonProperty("securityNumber") String securityNumber,
			@JsonProperty("workingHours") String workingHours) {
		super(username, password, role, name, surname, address, city, country, phoneNumber, securityNumber);
		this.workingHours = workingHours;
		this.rating = 0.0;
	}

	public String getWorkingHours() {
		return workingHours;
	}


	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}


	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}
	
	

}
