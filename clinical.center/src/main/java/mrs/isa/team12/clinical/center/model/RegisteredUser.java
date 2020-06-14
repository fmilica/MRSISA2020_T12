package mrs.isa.team12.clinical.center.model;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Where;

// Registered user class
@Entity
@Table(name = "registered_user")
@Where(clause="is_active=true")
@Inheritance(strategy=TABLE_PER_CLASS)
public class RegisteredUser implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Version
	private Long version;
	
	@Column(name="is_active")
	private Boolean active;
	
	@Column(name="email", unique= true, nullable=false)
	private String email;
	
	@Column(name="password", unique= false, nullable=false)
	private String password;
	
	@Column(name="name", unique=false, nullable=false)
	private String name;
	
	@Column(name="surname", unique=false, nullable=false)
	private String surname;
	
	@Column(name="gender", unique=false, nullable=false)
	private String gender;
	
	@Column(name="date_of_birth", unique=false, nullable=false)
	private String dateOfBirth;
	
	@Column(name="address", unique=false, nullable=true)
	private String address;
	
	@Column(name="city", unique=false, nullable=true)
	private String city;
	
	@Column(name="country", unique=false, nullable=true)
	private String country;
	
	@Column(name="phone_number", unique=false, nullable=true)
	private String phoneNumber;
	
	@Column(name="security_number", unique=false, nullable=false)
	private String securityNumber;
	
	//za potrebe izmene lozinke prilikom prvog logina
	@Column(name = "logged")
	private Boolean logged;
	
	@Column(name = "token")
	private String token;
	
	public RegisteredUser() {
		this.active = true;
	}
	
	public RegisteredUser(String email, String password, String name, String surname, String gender, 
			String dateOfBirth, String address, String city, String country, 
			String phoneNumber, String securityNumber, Boolean logged) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.surname = surname;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.securityNumber = securityNumber;
		this.active = true;
		this.logged = logged;
	}
	
	public RegisteredUser(Long id, String email, String password, String name, String surname, String gender, 
			String dateOfBirth, String address, String city, String country, 
			String phoneNumber, String securityNumber, Boolean logged) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.surname = surname;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.securityNumber = securityNumber;
		this.active = true;
		this.logged = logged;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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
	public String getSecurityNumber() {
		return securityNumber;
	}
	public void setSecurityNumber(String securityNumber) {
		this.securityNumber = securityNumber;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Boolean getLogged() {
		return logged;
	}
	public void setLogged(Boolean logged) {
		this.logged = logged;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((logged == null) ? 0 : logged.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((securityNumber == null) ? 0 : securityNumber.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
	        return true;
	    if (obj == null)
	        return false;
	    if (!(obj instanceof RegisteredUser))
			return false;
	    RegisteredUser user = (RegisteredUser) obj;
	    return this.id.equals(user.getId());
	}
	public String getVerificationToken() {
		return token;
	}
	public void setVerificationToken(String verificationToken) {
		this.token = verificationToken;
	}
}
