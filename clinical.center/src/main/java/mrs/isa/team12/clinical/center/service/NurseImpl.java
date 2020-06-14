package mrs.isa.team12.clinical.center.service;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mrs.isa.team12.clinical.center.dto.NursePersonalInformationDto;
import mrs.isa.team12.clinical.center.model.Nurse;
import mrs.isa.team12.clinical.center.repository.NurseRepository;
import mrs.isa.team12.clinical.center.service.interfaces.NurseService;

@Service
@Transactional(readOnly = true)
public class NurseImpl implements NurseService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private NurseRepository nurseRep;
	
	@Autowired
	public NurseImpl(NurseRepository nurseRep) {
		this.nurseRep = nurseRep;
	}
	
	@Override
	public Nurse findOneById(Long id) {
		logger.info("> findOneById");
		
		Nurse n = nurseRep.findOneById(id);
		
		logger.info("< findOneById");
		return n;
	}

	@Override
	public Nurse findOneByEmail(String email) {
		logger.info("> findOneByEmail");
		
		Nurse n = nurseRep.findOneByEmail(email);
		
		logger.info("< findOneByEmail");
		return n;
	}
	
	@Override
	public List<Nurse> findAll() {
		logger.info("> findAll");
		List<Nurse> nurses = nurseRep.findAll();
		logger.info("< findAll");
		return nurses;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Nurse updatePassword(Long id, String newPassword) {
		logger.info("> update id:{}", id);
		
		Nurse nurseToUpdate = nurseRep.findOneById(id);
		String oldPassword = nurseToUpdate.getPassword();
		if(oldPassword.equals(newPassword)) {
			return null;
		}
		
		nurseToUpdate.setPassword(newPassword);
		nurseToUpdate.setLogged(true);
		Nurse n = nurseRep.save(nurseToUpdate);
		
		logger.info("< update id:{}", id);
		return n;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Nurse update(NursePersonalInformationDto editedProfile) {
		logger.info("> update id:{}", editedProfile.getId());
		
		Nurse nurseToUpdate = nurseRep.findOneById(editedProfile.getId());
		
		nurseToUpdate.setName(editedProfile.getName());
		nurseToUpdate.setSurname(editedProfile.getSurname());
		nurseToUpdate.setGender(editedProfile.getGender());
		nurseToUpdate.setDateOfBirth(editedProfile.getDateOfBirth());
		nurseToUpdate.setPhoneNumber(editedProfile.getPhoneNumber());
		nurseToUpdate.setAddress(editedProfile.getAddress());
		nurseToUpdate.setCity(editedProfile.getCity());
		nurseToUpdate.setCountry(editedProfile.getCountry());
		//da li treba da se snimi mozda moze i bez snimanja tj sam da snimi ?
		
		logger.info("< update id:{}", editedProfile.getId());
		return nurseRep.save(nurseToUpdate);
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
	public Nurse save(Nurse nurse) {
		logger.info("> create");
		Nurse n = nurseRep.findOneByEmail(nurse.getEmail());
		if( n!= null) {
			logger.info("< EntityExistsException");
			throw new EntityExistsException();
		}
		nurse.setActive(true);
		Nurse saved = nurseRep.save(nurse);
		logger.info("< create");
		return saved;
	}

	@Override
	public List<Nurse> findAllByClinicId(Long id) {
		logger.info("> findAllByClinicId id:{}", id);
		List<Nurse> n = nurseRep.findAllByClinicId(id);
		logger.info("< findAllByClinicId id:{}", id);
		return n;
	}


	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Nurse n) {
		logger.info("> delete id:{}", n.getId());
		n.setActive(false);
		nurseRep.save(n);
		logger.info("< delete id:{}", n.getId());
	}

}
