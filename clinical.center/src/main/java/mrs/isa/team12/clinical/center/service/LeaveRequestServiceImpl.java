package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mrs.isa.team12.clinical.center.model.LeaveRequest;
import mrs.isa.team12.clinical.center.repository.LeaveRequestRepository;
import mrs.isa.team12.clinical.center.service.interfaces.LeaveRequestService;

@Service
@Transactional(readOnly = true)
public class LeaveRequestServiceImpl implements LeaveRequestService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private LeaveRequestRepository leaveReqRep;
	
	@Autowired
	public LeaveRequestServiceImpl(LeaveRequestRepository lrr) {
		this.leaveReqRep = lrr;
	}

	@Override
	public List<LeaveRequest> findAllByLeaveMedicalPersoneId(Long id) {
		logger.info("> findAllByMedicalPersoneId");
		List<LeaveRequest> lr = leaveReqRep.findAllByLeaveMedicalPersoneId(id);
		logger.info("< findAllByMedicalPersoneId");
		return lr;
	}

	@Override
	@Transactional(readOnly = false)
	public LeaveRequest save(LeaveRequest lr) {
		logger.info("> create");
		LeaveRequest leaveReq = leaveReqRep.save(lr);
		logger.info("< create");
		return leaveReq;
	}
	
	

}
