package com.projeto.APIAgendamentoConsultas.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Schema(name = "Patient Request")
public record PatientRequestDto(

        @NotBlank(message = "Name must not be blank")
        String name,

        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "CPF must not be blank")
        @CPF
        String cpf,

        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        @NotBlank(message = "Phone must not be blank")
        String phone

) {}
