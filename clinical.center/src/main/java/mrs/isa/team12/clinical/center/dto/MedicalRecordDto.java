package mrs.isa.team12.clinical.center.dto;

import mrs.isa.team12.clinical.center.model.MedicalRecords;

public class MedicalRecordDto {
	private Integer height;
	private Integer weight;
	private String bloodPressure;
	private String bloodType;
	private String allergies;
	
	public MedicalRecordDto(MedicalRecords medicalRecords) {
		super();
		this.height = medicalRecords.getHeight();
		this.weight = medicalRecords.getWeight();
		this.bloodPressure = medicalRecords.getBloodPressure();
		this.bloodType = medicalRecords.getBloodType();
		this.allergies = medicalRecords.getAllergies();
	}
	
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public String getBloodPressure() {
		return bloodPressure;
	}
	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	public String getBloodType() {
		return bloodType;
	}
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	public String getAllergies() {
		return allergies;
	}
	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}
	
}
