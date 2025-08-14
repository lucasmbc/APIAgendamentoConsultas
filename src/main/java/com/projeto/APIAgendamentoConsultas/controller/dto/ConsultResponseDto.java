package com.projeto.APIAgendamentoConsultas.controller.dto;

import com.projeto.APIAgendamentoConsultas.domain.model.StatusConsult;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(name = "Consult Response")
public record ConsultResponseDto(
        UUID id,
        LocalDateTime dateTime,
        StatusConsult status,
        String observations,
        UUID patientId,
        UUID doctorId
) {}
