package com.projeto.APIAgendamentoConsultas.controller;

import com.projeto.APIAgendamentoConsultas.controller.dto.PatientRequestDto;
import com.projeto.APIAgendamentoConsultas.controller.dto.PatientResponseDto;
import com.projeto.APIAgendamentoConsultas.controller.mapper.PatientMapper;
import com.projeto.APIAgendamentoConsultas.service.PatientService;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
@Tag(name = "Patients", description = "RESTful API for managing clinic.")
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper mapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'GERENTE')")
    @Operation(summary = "Get all patients", description = "Retrieve a list of all registered patients")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operation successful")})
    public ResponseEntity<List<PatientResponseDto>> findAll(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cpf", required = false) String cpf) {
        if(name != null) {
            var patientsName = patientService.findByName(name).stream().map(mapper::toResponseDto).toList();
            return ResponseEntity.ok(patientsName);
        }

        if(cpf != null) {
            var patientByCpf = patientService.findByCpf(cpf).stream().map(mapper::toResponseDto).toList();
            return ResponseEntity.ok(patientByCpf);
        }

        var patients = patientService.findAll().stream().map(mapper::toResponseDto).toList();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'GERENTE')")
    @Operation(summary = "Get a patient by ID", description = "Retrieve a specific patient based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<PatientResponseDto> findById(@PathVariable UUID id) {
        var patient = patientService.findById(id);
        return ResponseEntity.ok(mapper.toResponseDto(patient));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'GERENTE')")
    @Operation(summary = "Create a new patient", description = "Create a new patient and return the created patient's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid patient data provided")
    })
    public ResponseEntity<PatientResponseDto> create(@Valid @RequestBody PatientRequestDto requestDto) {
        var patient = patientService.create(mapper.toModel(requestDto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(patient.getId())
                .toUri();
        return ResponseEntity.created(location).body(mapper.toResponseDto(patient));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'GERENTE')")
    @Operation(summary = "Update a patient", description = "Update the data of an existing patient based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "422", description = "Invalid patient data provided")
    })
    public ResponseEntity<PatientResponseDto> update(@PathVariable UUID id, @RequestBody PatientRequestDto requestDto) {
        var patient = patientService.update(id, mapper.toModel(requestDto));
        return ResponseEntity.ok(mapper.toResponseDto(patient));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(summary = "Delete a patient", description = "Delete an existing patient based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
