package mrs.isa.team12.clinical.center.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// Registered user class
@Entity
public class RegisteredUser {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@Column(name = "password", unique = false, nullable = false)
	private String password;
	
	@Column(name = "name", unique = false, nullable = false)
	private String name;
	
	@Column(name = "surname", unique = false, nullable = false)
	private String surname;
	
	@Column(name = "address", unique = false, nullable = false)
	private String address;
	
	@Column(name = "city", unique = false, nullable = false)
	private String city;
	
	@Column(name = "country", unique = false, nullable = false)
	private String country;
	
	@Column(name = "phoneNumber", unique = false, nullable = false)
	private String phoneNumber;
	
	@Column(name = "securityNumber", unique = false, nullable = false)
	private Integer securityNumber;
	
	
	public RegisteredUser() {}
	
	public RegisteredUser(String email, String password, String name, String surname, String address, String city,
			String country, String phoneNumber, Integer securityNumber) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
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
