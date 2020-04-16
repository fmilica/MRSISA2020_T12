package mrs.isa.team12.clinical.center.model;

public class Prescription {

	private String medicine;
	
	
	public Prescription() {
		super();
	}

	public Prescription(String medicine) {
		super();
		this.medicine = medicine;
	}

	public String getMedicine() {
		return medicine;
	}

	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	
}
