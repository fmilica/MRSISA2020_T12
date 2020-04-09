package mrs.isa.team12.clinical.center.repository;

import java.util.ArrayList;

import mrs.isa.team12.clinical.center.model.Doctor;

//Interface for Doctor database access
public interface DoctorRepository {
	
	public void addDoctor(Doctor doctor);
	
	public ArrayList<Doctor> listDoctors();
	
}
