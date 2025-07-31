package com.projeto.APIAgendamentoConsultas.controller.dto;

import java.util.List;
import java.util.UUID;

public record DoctorResponseDto(
        UUID id,
        String name,
        String email,
        String phone,
        String crm,
        String specialty,
        List<ConsultDto> consult
) {}
