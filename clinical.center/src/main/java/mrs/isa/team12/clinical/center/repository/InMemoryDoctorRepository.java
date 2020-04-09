package mrs.isa.team12.clinical.center.repository;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import mrs.isa.team12.clinical.center.model.Doctor;

@Repository
public class InMemoryDoctorRepository implements DoctorRepository {

	private ArrayList<Doctor> doctorDB = new ArrayList<Doctor>();
	
	/*
	 adds new doctor to clinic center
	 receives Doctor object
	 */
	@Override
	public void addDoctor(Doctor doctor) {
		doctorDB.add(doctor);
		
	}

	/*
	 return all doctors in the database
	 returns ArrayList<Doctor>
	 */
	@Override
	public ArrayList<Doctor> listDoctors() {
		return doctorDB;
	}

	

}
