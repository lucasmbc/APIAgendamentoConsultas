package com.projeto.APIAgendamentoConsultas.service.impl;

import com.projeto.APIAgendamentoConsultas.domain.model.Consult;
import com.projeto.APIAgendamentoConsultas.domain.model.Doctor;
import com.projeto.APIAgendamentoConsultas.domain.model.Patient;
import com.projeto.APIAgendamentoConsultas.domain.repository.ConsultRepository;
import com.projeto.APIAgendamentoConsultas.domain.repository.DoctorRepository;
import com.projeto.APIAgendamentoConsultas.domain.repository.PatientRepository;
import com.projeto.APIAgendamentoConsultas.service.ConsultService;
import com.projeto.APIAgendamentoConsultas.service.exception.BusinessException;
import com.projeto.APIAgendamentoConsultas.service.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConsultServiceImpl implements ConsultService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ConsultRepository consultRepository;

    @Transactional(readOnly = true)
    public List<Consult> findAll() {
        return this.consultRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Consult findById(UUID id) {
        return this.consultRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Consult create(Consult consultToCreate) {

        if (consultRepository.existsByDoctorIdAndDateTime(consultToCreate.getDoctor().getId(), consultToCreate.getDateTime())) {
            throw new BusinessException("Médico já possui consulta agendada neste horário");
        }

        Patient patient = patientRepository.findById(consultToCreate.getPatient().getId())
                .orElseThrow(NotFoundException::new);

        Doctor doctor = doctorRepository.findById(consultToCreate.getDoctor().getId())
                .orElseThrow(NotFoundException::new);

        LocalDateTime startDay = consultToCreate.getDateTime().withHour(0).withMinute(0);
        LocalDateTime endDay = consultToCreate.getDateTime().withHour(23).withMinute(59);
        if(consultRepository.existsByPatientIdAndDateTimeBetween(consultToCreate.getPatient().getId(), startDay, endDay)) {
            throw new BusinessException("Paciente já possui consulta agendada neste dia");
        }

        Consult savedConsult = consultRepository.save(consultToCreate);

        patient.addConsultation(savedConsult);
        doctor.addConsultation(savedConsult);

        return savedConsult;
    }

    @Transactional
    public Consult update(UUID id, Consult consultToUpdate) {
        Consult dbConsult = this.findById(id);
        if(!dbConsult.getId().equals(consultToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }

        dbConsult.setStatus(consultToUpdate.getStatus());
        dbConsult.setObservations(consultToUpdate.getObservations());
        dbConsult.setDateTime(consultToUpdate.getDateTime());
        dbConsult.setPatient(consultToUpdate.getPatient());
        dbConsult.setDoctor(consultToUpdate.getDoctor());
        
        return this.consultRepository.save(dbConsult);
    }

    @Override
    public void delete(UUID id) {
        Consult dbConsult = this.findById(id);
        this.consultRepository.delete(dbConsult);
    }
}
