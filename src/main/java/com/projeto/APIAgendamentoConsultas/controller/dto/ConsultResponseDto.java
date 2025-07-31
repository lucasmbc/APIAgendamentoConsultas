package com.projeto.APIAgendamentoConsultas.controller.dto;

import com.projeto.APIAgendamentoConsultas.domain.model.StatusConsult;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsultResponseDto(
        UUID id,
        LocalDateTime dateTime,
        StatusConsult status,
        String observations,
        UUID patientId,
        UUID doctorId
) {}
