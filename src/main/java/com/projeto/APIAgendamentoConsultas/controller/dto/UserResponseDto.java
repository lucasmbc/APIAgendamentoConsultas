package com.projeto.APIAgendamentoConsultas.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(name = "User Response")
public record UserResponseDto(UUID id, String userName, List<String> role) {
}
