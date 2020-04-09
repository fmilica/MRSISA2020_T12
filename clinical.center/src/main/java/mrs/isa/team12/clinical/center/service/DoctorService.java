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
	
	/*
	 adds new doctor to clinic center
	 receives Doctor object
	 */
	public void addDoctor(Doctor doctor) {
		doctorRepository.addDoctor(doctor);
	}
	
	/*
	 return all doctors in the database
	 returns ArrayList<Doctor>
	 */
	public ArrayList<Doctor> listDoctors(){
		return doctorRepository.listDoctors();
	}

}
