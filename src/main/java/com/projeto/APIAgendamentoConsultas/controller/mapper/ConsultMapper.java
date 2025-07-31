package com.projeto.APIAgendamentoConsultas.controller.mapper;

import com.projeto.APIAgendamentoConsultas.controller.dto.ConsultRequestDto;
import com.projeto.APIAgendamentoConsultas.controller.dto.ConsultResponseDto;
import com.projeto.APIAgendamentoConsultas.domain.model.Consult;
import com.projeto.APIAgendamentoConsultas.domain.model.Doctor;
import com.projeto.APIAgendamentoConsultas.domain.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsultMapper {

    public Consult toModel(ConsultRequestDto dto) {
        Consult consult = new Consult();
        Patient patient = new Patient();
        Doctor doctor = new Doctor();

        consult.setDateTime(dto.dateTime());
        consult.setStatus(dto.status());
        consult.setObservations(dto.observations());
        patient.setId(dto.patientId());
        consult.setPatient(patient);
        doctor.setId(dto.doctorId());
        consult.setDoctor(doctor);
        return consult;
    }

    public ConsultResponseDto toResponseDto(Consult consult) {
        return new ConsultResponseDto(
                consult.getId(),
                consult.getDateTime(),
                consult.getStatus(),
                consult.getObservations(),
                consult.getPatient().getId(),
                consult.getDoctor().getId()
        );
    }
}
