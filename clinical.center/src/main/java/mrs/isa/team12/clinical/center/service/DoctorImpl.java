package mrs.isa.team12.clinical.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.repository.DoctorRepository;
import mrs.isa.team12.clinical.center.service.interfaces.DoctorService;

@Service
public class DoctorImpl implements DoctorService {

	private DoctorRepository doctorRep;
	
	@Autowired
	public DoctorImpl(DoctorRepository doctorRep) {
		this.doctorRep = doctorRep;
	}

	@Override
	public Doctor findOneByEmail(String email) {
		return doctorRep.findOneByEmail(email);
	}

	@Override
	public Doctor save(Doctor d) {
		return doctorRep.save(d);
	}

	@Override
	public List<Doctor> findAll() {
		return doctorRep.findAll();
	}

	@Override
	public Doctor getDoctorById(Long id) {
		return doctorRep.findOneById(id);
	}

	@Override
	public List<Doctor> findAllByClinicId(Long id) {
		return doctorRep.findAllByClinicId(id);
	}

}
