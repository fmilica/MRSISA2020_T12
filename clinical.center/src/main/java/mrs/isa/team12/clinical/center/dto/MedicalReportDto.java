package mrs.isa.team12.clinical.center.dto;

import java.util.HashSet;
import java.util.Set;

import mrs.isa.team12.clinical.center.model.MedicalReport;
import mrs.isa.team12.clinical.center.model.Prescription;

public class MedicalReportDto {
	
	private String description;
	private String diagnosisName;
	private Set<String> prescriptionMedicines;
	
	
	public MedicalReportDto(MedicalReport medicalReport) {
		this.description = medicalReport.getDescription();
		this.diagnosisName = medicalReport.getDiagnosis().getName();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDiagnosisName() {
		return diagnosisName;
	}
	public void setDiagnosisName(String diagnosisName) {
		this.diagnosisName = diagnosisName;
	}
	public Set<String> getPrescriptionMedicines() {
		return prescriptionMedicines;
	}
	public void setPrescriptionMedicines(Set<Prescription> prescriptions) {
		for(Prescription p : prescriptions) {
			if (this.prescriptionMedicines == null) {
				this.prescriptionMedicines = new HashSet<String>();
			}
			this.prescriptionMedicines.add(p.getMedicine());
		}
	}
	
	@Override
	public String toString() {
		return "MedicalReportDto [description=" + description + ", diagnosisName=" + diagnosisName
				+ ", prescriptionMedicines=" + prescriptionMedicines + "]";
	}
}
