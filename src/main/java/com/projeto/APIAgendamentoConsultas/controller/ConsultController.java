package com.projeto.APIAgendamentoConsultas.controller;

import com.projeto.APIAgendamentoConsultas.controller.dto.ConsultRequestDto;
import com.projeto.APIAgendamentoConsultas.controller.dto.ConsultResponseDto;
import com.projeto.APIAgendamentoConsultas.controller.mapper.ConsultMapper;
import com.projeto.APIAgendamentoConsultas.service.ConsultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
@Tag(name = "Consults")
public class ConsultController {

    private final ConsultService consultService;
    private final ConsultMapper mapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'GERENTE')")
    @Operation(summary = "Get all consults", description = "Retrieve a list of all registered consults")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operation successful")})
    public ResponseEntity<List<ConsultResponseDto>> findAll(
            @RequestParam(value = "doctorId", required = false) String doctorId,
            @RequestParam(value = "patientId", required = false) String patientId,
            @RequestParam(value = "start", required = false) String start,
            @RequestParam(value = "end", required = false) String end
    ) {

        var consults = consultService.findConsults(doctorId, patientId, start, end).stream().map(mapper::toResponseDto).toList();

        return ResponseEntity.ok(consults);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'GERENTE')")
    @Operation(summary = "Get a consult by ID", description = "Retrieve a specific consult based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Consult not found")
    })
    public ResponseEntity<ConsultResponseDto> findById(@PathVariable UUID id) {
        var consult = consultService.findById(id);
        return ResponseEntity.ok(mapper.toResponseDto(consult));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'GERENTE')")
    @Operation(summary = "Create a new consult", description = "Create a new consult and return the created consult's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consult created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid consult data provided")
    })
    public ResponseEntity<ConsultResponseDto> create(@Valid @RequestBody ConsultRequestDto requestDto) {
        var consult = consultService.create(mapper.toModel(requestDto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(consult.getId())
                .toUri();
        return ResponseEntity.created(location).body(mapper.toResponseDto(consult));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'GERENTE')")
    @Operation(summary = "Update a consult", description = "Update the data of an existing consult based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consult updated successfully"),
            @ApiResponse(responseCode = "404", description = "Consult not found"),
            @ApiResponse(responseCode = "422", description = "Invalid consult data provided")
    })
    public ResponseEntity<ConsultResponseDto> update(@PathVariable UUID id, @RequestBody ConsultRequestDto requestDto) {
        var consult = consultService.update(id, mapper.toModel(requestDto));
        return ResponseEntity.ok(mapper.toResponseDto(consult));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(summary = "Delete a consult", description = "Delete an existing consult based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consult deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Consult not found")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        consultService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
