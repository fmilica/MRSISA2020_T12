package mrs.isa.team12.clinical.center.model.wrapper;

import mrs.isa.team12.clinical.center.model.ClinicAdmin;

public class ClinicAdminWrapper {

	private ClinicAdmin clinicAdmin;
	private String clinicName;
	
	public ClinicAdminWrapper(ClinicAdmin clinicAdmin, String clinicName) {
		super();
		this.clinicAdmin = clinicAdmin;
		this.clinicName = clinicName;
	}
	
	public ClinicAdmin getClinicAdmin() {
		return clinicAdmin;
	}
	public void setClinicAdmin(ClinicAdmin clinicAdmin) {
		this.clinicAdmin = clinicAdmin;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
}
