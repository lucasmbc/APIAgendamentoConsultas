package com.projeto.APIAgendamentoConsultas.controller;

import com.projeto.APIAgendamentoConsultas.controller.dto.PatientDto;
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
@Tag(name = "Patients Controller", description = "RESTful API for managing clinic.")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'GERENTE')")
    @Operation(summary = "Get all patients", description = "Retrieve a list of all registered patients")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operation successful")})
    public ResponseEntity<List<PatientDto>> findAll() {
        var patients = patientService.findAll();
        var patientsDto = patients.stream().map(PatientDto::new).toList();
        return ResponseEntity.ok(patientsDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'GERENTE')")
    @Operation(summary = "Get a patient by ID", description = "Retrieve a specific patient based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<PatientDto> findById(@PathVariable UUID id) {
        var patient = patientService.findById(id);
        return ResponseEntity.ok(new PatientDto(patient));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'GERENTE')")
    @Operation(summary = "Create a new patient", description = "Create a new patient and return the created patient's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid patient data provided")
    })
    public ResponseEntity<PatientDto> create(@Valid @RequestBody PatientDto patientDto) {
        var patient = patientService.create(patientDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(patient.getId())
                .toUri();
        return ResponseEntity.created(location).body(new PatientDto(patient));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'GERENTE')")
    @Operation(summary = "Update a patient", description = "Update the data of an existing patient based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "422", description = "Invalid patient data provided")
    })
    public ResponseEntity<PatientDto> update(@PathVariable UUID id, @RequestBody PatientDto patientDto) {
        var patient = patientService.update(id, patientDto.toModel());
        return ResponseEntity.ok(new PatientDto(patient));
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
