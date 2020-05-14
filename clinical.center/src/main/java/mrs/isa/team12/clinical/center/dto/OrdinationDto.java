package mrs.isa.team12.clinical.center.dto;

import mrs.isa.team12.clinical.center.model.Ordination;
import mrs.isa.team12.clinical.center.model.enums.OrdinationType;

public class OrdinationDto {
	
	private String name;
	private Integer ordinationNumber;
	private OrdinationType type;
	
	public OrdinationDto(Ordination o) {
		this.name = o.getName();
		this.ordinationNumber = o.getOrdinationNumber();
		this.type = o.getType();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOrdinationNumber() {
		return ordinationNumber;
	}
	public void setOrdinationNumber(Integer ordinationNumber) {
		this.ordinationNumber = ordinationNumber;
	}
	public OrdinationType getType() {
		return type;
	}
	public void setType(OrdinationType type) {
		this.type = type;
	}
}