package mrs.isa.team12.clinical.center.model;

import org.hibernate.annotations.Entity;

//Registered user class
@Entity
public class RegisteredUser {

	private String email;
	private String password;
	private String name;
	private String suername;
	private String address;
	private String city;
	private String country;
	private String phoneNumber;
	private Integer securityNumber;
	
	
	public RegisteredUser() {
		
	}
	
	public RegisteredUser(String email, String password, String name, String suername, String address, String city,
			String country, String phoneNumber, Integer securityNumber) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.suername = suername;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.securityNumber = securityNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSuername() {
		return suername;
	}
	public void setSuername(String suername) {
		this.suername = suername;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Integer getSecurityNumber() {
		return securityNumber;
	}
	public void setSecurityNumber(Integer securityNumber) {
		this.securityNumber = securityNumber;
	}
	
	
}
