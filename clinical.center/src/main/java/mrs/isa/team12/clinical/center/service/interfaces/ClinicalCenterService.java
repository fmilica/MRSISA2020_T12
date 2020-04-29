package mrs.isa.team12.clinical.center.service.interfaces;


import java.util.List;

import mrs.isa.team12.clinical.center.model.ClinicalCentre;

public interface ClinicalCenterService {
	
	public List<ClinicalCentre> findAll();
	
	public ClinicalCentre findOneByName(String name);

	public ClinicalCentre save(ClinicalCentre cc);
}
