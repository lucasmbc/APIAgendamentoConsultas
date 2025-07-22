package com.projeto.APIAgendamentoConsultas.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record ClientDto(
    @NotBlank(message = "Required field")
    String clientId,
    @NotBlank(message = "Required field")
    String clientSecret,
    @NotBlank(message = "Required field")
    String redirectURI,
    @NotBlank(message = "Required field")
    String scope
) {
}
