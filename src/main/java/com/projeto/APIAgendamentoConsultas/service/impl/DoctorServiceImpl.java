package com.projeto.APIAgendamentoConsultas.service.impl;

import com.projeto.APIAgendamentoConsultas.domain.model.Doctor;
import com.projeto.APIAgendamentoConsultas.domain.repository.DoctorRepository;
import com.projeto.APIAgendamentoConsultas.service.DoctorService;
import com.projeto.APIAgendamentoConsultas.service.exception.BusinessException;
import com.projeto.APIAgendamentoConsultas.service.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Transactional(readOnly = true)
    public List<Doctor> findAll() {
        if(doctorRepository.findAllWithConsultations().isEmpty()) {
            throw new NotFoundException();
        }

        return this.doctorRepository.findAllWithConsultations();
    }

    @Transactional(readOnly = true)
    public Doctor findById(UUID id) {
        return this.doctorRepository.findByIdWithConsultations(id).orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Doctor> findBySpecialty(String specialty) {
        if(doctorRepository.findBySpecialtyIgnoreCase(specialty).isEmpty()) {
            throw new NotFoundException();
        }

        return this.doctorRepository.findBySpecialtyIgnoreCase(specialty);
    }

    @Transactional
    public Doctor create(Doctor doctorToCreate) {

        if (doctorRepository.existsByCrm(doctorToCreate.getCrm())) {
            throw new BusinessException("This CRM already exists.");
        }
        if (doctorRepository.existsByEmail(doctorToCreate.getEmail())) {
            throw new BusinessException("This email already exists.");
        }

        return this.doctorRepository.save(doctorToCreate);
    }

    @Transactional
    public Doctor update(UUID id, Doctor doctorToUpdate) {
        Doctor dbDoctor = this.findById(id);

        dbDoctor.setName(doctorToUpdate.getName());
        dbDoctor.setPhone(doctorToUpdate.getPhone());
        dbDoctor.setEmail(doctorToUpdate.getEmail());
        dbDoctor.setSpecialty(doctorToUpdate.getSpecialty());

        return this.doctorRepository.save(dbDoctor);
    }

    @Transactional
    public void delete(UUID id) {
        Doctor dbDoctor = this.findById(id);
        this.doctorRepository.delete(dbDoctor);
    }
}
