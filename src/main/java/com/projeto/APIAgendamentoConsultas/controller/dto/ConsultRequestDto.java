package com.projeto.APIAgendamentoConsultas.controller.dto;

import com.projeto.APIAgendamentoConsultas.domain.model.StatusConsult;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsultRequestDto(
        @FutureOrPresent(message = "The consultation date cannot be in the past")
        @NotNull(message = "The date and time to be created cannot be null")
        LocalDateTime dateTime,

        StatusConsult status,

        String observations,

        @NotNull(message = "Patient ID cannot be null")
        UUID patientId,

        @NotNull(message = "Doctor ID cannot be null")
        UUID doctorId
) {}
