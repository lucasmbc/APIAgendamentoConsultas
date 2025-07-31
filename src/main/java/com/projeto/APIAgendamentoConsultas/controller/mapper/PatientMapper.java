package com.projeto.APIAgendamentoConsultas.controller.mapper;

import com.projeto.APIAgendamentoConsultas.controller.dto.ConsultDto;
import com.projeto.APIAgendamentoConsultas.controller.dto.PatientRequestDto;
import com.projeto.APIAgendamentoConsultas.controller.dto.PatientResponseDto;
import com.projeto.APIAgendamentoConsultas.domain.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class PatientMapper {

    public Patient toModel(PatientRequestDto dto) {
        Patient patient = new Patient();
        patient.setName(dto.name());
        patient.setEmail(dto.email());
        patient.setPhone(dto.phone());
        patient.setCpf(dto.cpf());
        patient.setBirthDate(dto.birthDate());
        return patient;
    }

    public PatientResponseDto toResponseDto(Patient patient) {

        return new PatientResponseDto(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getCpf(),
                patient.getBirthDate(),
                patient.getPhone(),
                ofNullable(patient.getConsultations()).orElse(emptyList()).stream().map(ConsultDto::new).toList()
        );
    }
}
