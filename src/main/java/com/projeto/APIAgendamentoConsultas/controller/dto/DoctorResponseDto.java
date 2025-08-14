package com.projeto.APIAgendamentoConsultas.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(name = "Doctor Response")
public record DoctorResponseDto(
        UUID id,
        String name,
        String email,
        String phone,
        String crm,
        String specialty,
        List<ConsultResponseDto> consult
) {}
