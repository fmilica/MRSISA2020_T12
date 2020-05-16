package mrs.isa.team12.clinical.center.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.dto.DoctorPersonalInformationDto;
import mrs.isa.team12.clinical.center.model.AppointmentType;
import mrs.isa.team12.clinical.center.model.Clinic;
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
	public List<Doctor> findAllByClinicId(Long id) {
		return doctorRep.findAllByClinicId(id);
	}

	@Override
	public Doctor findOneById(Long id) {
		return doctorRep.findOneById(id);
	}

	@Override
	public List<Doctor> findAllByAppointmentTypes(AppointmentType a) {
		return doctorRep.findAllByAppointmentTypes(a);
	}

	@Override
	public List<Doctor> findAllByAppointmentTypesIn(List<AppointmentType> types) {
		return doctorRep.findAllByAppointmentTypesIn(types);
	}

	@Override
	public List<Doctor> findAllByClinicAndAppointmentTypesIn(Clinic clinic, List<AppointmentType> types) {
		return doctorRep.findAllByClinicAndAppointmentTypesIn(clinic, types);
	}

	@Override
	public List<Doctor> findAllByClinicIdAndAppointmentTypes(Long clinicId, AppointmentType type) {
		return doctorRep.findAllByClinicIdAndAppointmentTypes(clinicId, type);
	}

	@Override
	public Doctor update(DoctorPersonalInformationDto editedDoctor, Set<AppointmentType> appTypes) {
		Doctor doctorToUpdate = doctorRep.findOneById(editedDoctor.getId());
		doctorToUpdate.setName(editedDoctor.getName());
		doctorToUpdate.setSurname(editedDoctor.getSurname());
		doctorToUpdate.setGender(editedDoctor.getGender());
		doctorToUpdate.setDateOfBirth(editedDoctor.getDateOfBirth());
		doctorToUpdate.setPhoneNumber(editedDoctor.getPhoneNumber());
		doctorToUpdate.setAddress(editedDoctor.getAddress());
		doctorToUpdate.setCity(editedDoctor.getCity());
		doctorToUpdate.setCountry(editedDoctor.getCountry());
		doctorToUpdate.setSpecialization(editedDoctor.getSpecialization());
		doctorToUpdate.setAppointmentTypes(appTypes);
		//da li treba da se snimi mozda moze i bez snimanja tj sam da snimi ?
		Doctor updated = doctorRep.save(doctorToUpdate);
		return updated;
	}

}
