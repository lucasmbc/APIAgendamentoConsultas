package com.projeto.APIAgendamentoConsultas.controller.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PatientResponseDto(
        UUID id,
        String name,
        String email,
        String cpf,
        LocalDate birthDate,
        String phone,
        List<ConsultDto> consult
) {}
