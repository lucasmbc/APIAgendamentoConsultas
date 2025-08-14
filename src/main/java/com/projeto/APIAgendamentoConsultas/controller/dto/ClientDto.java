package com.projeto.APIAgendamentoConsultas.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Client")
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
