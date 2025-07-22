package com.projeto.APIAgendamentoConsultas.controller.dto;

import java.util.List;
import java.util.UUID;

public record UserResponseDto(UUID id, String userName, List<String> role) {
}
