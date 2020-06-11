package mrs.isa.team12.clinical.center.dto;

import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.model.enums.Specialization;

public class DoctorRatingDto {
	
	private Long id;
	private String fullName;
	private Specialization specialization;
	private String clinicName;
	private Double rating;
	private Boolean rated;
	
	public DoctorRatingDto() {}
	
	public DoctorRatingDto(Doctor doctor) {
		this.id = doctor.getId();
		this.fullName = doctor.getName() + " " + doctor.getSurname();
		this.specialization = doctor.getSpecialization();
		this.clinicName = doctor.getClinic().getName();
		this.rating = doctor.getRating();
	}
	
	public DoctorRatingDto(Doctor doctor, Long patientId) {
		this.id = doctor.getId();
		this.fullName = doctor.getName() + " " + doctor.getSurname();
		this.specialization = doctor.getSpecialization();
		this.clinicName = doctor.getClinic().getName();
		this.rating = doctor.getRating();
		this.rated = doctor.alreadyRated(patientId);
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Specialization getSpecialization() {
		return specialization;
	}
	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
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
