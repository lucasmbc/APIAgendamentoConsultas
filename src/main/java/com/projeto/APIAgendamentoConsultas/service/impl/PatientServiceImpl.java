package com.projeto.APIAgendamentoConsultas.service.impl;

import com.projeto.APIAgendamentoConsultas.domain.model.Patient;
import com.projeto.APIAgendamentoConsultas.domain.repository.PatientRepository;
import com.projeto.APIAgendamentoConsultas.service.PatientService;
import com.projeto.APIAgendamentoConsultas.service.exception.BusinessException;
import com.projeto.APIAgendamentoConsultas.service.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Transactional(readOnly = true)
    public List<Patient> findAll() {
        return this.patientRepository.findAllWithConsultations();
    }

    @Transactional(readOnly = true)
    public Patient findById(Long id) {
        return this.patientRepository.findByIdWithConsultations(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Patient create(Patient patientToCreate) {
        ofNullable(patientToCreate).orElseThrow(() -> new BusinessException("Patient to create must not be null."));

        if (patientRepository.existsByCpf(patientToCreate.getCpf())) {
            throw new BusinessException("This CPF already exists.");
        }
        if (patientRepository.existsByEmail(patientToCreate.getEmail())) {
            throw new BusinessException("This email already exists.");
        }

        return this.patientRepository.save(patientToCreate);
    }

    @Transactional
    public Patient update(Long id, Patient patientToUpdate) {
        Patient dbPatient = this.findById(id);
        if (!dbPatient.getId().equals(patientToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }

        dbPatient.setName(patientToUpdate.getName());
        dbPatient.setBirthDate(patientToUpdate.getBirthDate());
        dbPatient.setPhone(patientToUpdate.getPhone());

        return this.patientRepository.save(dbPatient);
    }

    @Transactional
    public void delete(Long id) {
        Patient dbPatient = this.findById(id);
        this.patientRepository.delete(dbPatient);
    }
}
