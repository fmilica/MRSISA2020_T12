package mrs.isa.team12.clinical.center.model;

public class AppointmentType {
	
	private String name;
	private Double price;

	public AppointmentType() {
		// TODO Auto-generated constructor stub
	}

	public AppointmentType(String name, Double price) {
		super();
		this.name = name;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	

}
