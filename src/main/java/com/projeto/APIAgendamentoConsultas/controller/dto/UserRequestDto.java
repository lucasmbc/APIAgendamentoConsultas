package com.projeto.APIAgendamentoConsultas.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserRequestDto(

        @NotBlank(message = "Username must not be blank")
        String username,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, message = "Password must be at least six characters long")
        String password,

        @NotEmpty(message = "Roles cannot be empty")
        List<String> roles
) {}
