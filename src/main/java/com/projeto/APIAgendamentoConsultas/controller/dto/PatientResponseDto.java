package com.projeto.APIAgendamentoConsultas.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Schema(name = "Patient Response")
public record PatientResponseDto(
        UUID id,
        String name,
        String email,
        String cpf,
        LocalDate birthDate,
        String phone,
        List<ConsultResponseDto> consult
) {}
