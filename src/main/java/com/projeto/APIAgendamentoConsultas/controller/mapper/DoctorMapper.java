package com.projeto.APIAgendamentoConsultas.controller.mapper;

import com.projeto.APIAgendamentoConsultas.controller.dto.DoctorRequestDto;
import com.projeto.APIAgendamentoConsultas.controller.dto.DoctorResponseDto;
import com.projeto.APIAgendamentoConsultas.domain.model.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class DoctorMapper {

    private final ConsultMapper mapper;

    public Doctor toModel(DoctorRequestDto dto) {
        Doctor doctor = new Doctor();
        doctor.setName(dto.name());
        doctor.setEmail(dto.email());
        doctor.setPhone(dto.phone());
        doctor.setCrm(dto.crm());
        doctor.setSpecialty(dto.specialty());
        return doctor;
    }

    public DoctorResponseDto toResponseDto(Doctor doctor) {
        return new DoctorResponseDto(
                doctor.getId(),
                doctor.getName(),
                doctor.getEmail(),
                doctor.getPhone(),
                doctor.getCrm(),
                doctor.getSpecialty(),
                ofNullable(doctor.getConsultations()).orElse(emptyList()).stream().map(mapper::toResponseDto).toList()
        );
    }
}
