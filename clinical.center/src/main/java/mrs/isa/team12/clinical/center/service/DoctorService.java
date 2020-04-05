package mrs.isa.team12.clinical.center.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.Doctor;
import mrs.isa.team12.clinical.center.repository.DoctorRepository;

@Service
public class DoctorService {

	private DoctorRepository doctorRepository;
	
	@Autowired
	public DoctorService(DoctorRepository doctorRepository) {
		this.doctorRepository = doctorRepository;
	}
	
	public void addDoctor(Doctor doctor) {
		doctorRepository.addDoctor(doctor);
	}
	
	public ArrayList<Doctor> listDoctors(){
		return doctorRepository.listDoctors();
	}

}
