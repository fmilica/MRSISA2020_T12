package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.List;

import mrs.isa.team12.clinical.center.model.LeaveRequest;

public interface LeaveRequestService {
	
	List<LeaveRequest> findAllByLeaveMedicalPersoneId(Long id);
	
	LeaveRequest save(LeaveRequest lr);
}
