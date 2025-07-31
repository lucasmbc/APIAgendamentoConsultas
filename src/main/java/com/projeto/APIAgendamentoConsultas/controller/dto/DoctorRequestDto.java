package com.projeto.APIAgendamentoConsultas.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DoctorRequestDto(

        @NotBlank(message = "Doctor name must not be blank")
        String name,

        @NotBlank(message = "Doctor email must not be blank")
        @Email(message = "Invalid email")
        String email,

        @NotBlank(message = "Phone must not be blank")
        String phone,

        @NotBlank(message = "Doctor CRM must not be blank")
        String crm,

        @NotBlank(message = "Specialty must not be blank")
        String specialty
) {}
