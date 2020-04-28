package mrs.isa.team12.clinical.center.service.interfaces;

import java.util.ArrayList;

import mrs.isa.team12.clinical.center.model.Doctor;

public interface DoctorService {

	void addDoctor(Doctor doctor);
	
	ArrayList<Doctor> listDoctors();
	
	Doctor findOneByEmail(String email);
}
