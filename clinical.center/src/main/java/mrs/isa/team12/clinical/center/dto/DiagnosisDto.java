package mrs.isa.team12.clinical.center.dto;

import mrs.isa.team12.clinical.center.model.Diagnosis;

public class DiagnosisDto {
	private Long id;
	private String name;
	
	public DiagnosisDto() {}
	
	public DiagnosisDto(Diagnosis d) {
		this.id = d.getId();
		this.name = d.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
