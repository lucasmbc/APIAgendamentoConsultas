package com.projeto.APIAgendamentoConsultas.controller;

import com.projeto.APIAgendamentoConsultas.controller.dto.PatientDto;
import com.projeto.APIAgendamentoConsultas.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
@Tag(name = "Patients Controller", description = "RESTful API for managing clinic.")
public record PatientController(PatientService patientService) {

    @GetMapping
    @Operation(summary = "Get all patients", description = "Retrieve a list of all registered patients")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operation successful")})
    public ResponseEntity<List<PatientDto>> findAll() {
        var patients = patientService.findAll();
        var patientsDto = patients.stream().map(PatientDto::new).toList();
        return ResponseEntity.ok(patientsDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a patient by ID", description = "Retrieve a specific patient based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<PatientDto> findById(@PathVariable Long id) {
        var patient = patientService.findById(id);
        return ResponseEntity.ok(new PatientDto(patient));
    }

    @PostMapping
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
    @Operation(summary = "Update a patient", description = "Update the data of an existing patient based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "422", description = "Invalid patient data provided")
    })
    public ResponseEntity<PatientDto> update(@PathVariable Long id, @RequestBody PatientDto patientDto) {
        var patient = patientService.update(id, patientDto.toModel());
        return ResponseEntity.ok(new PatientDto(patient));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient", description = "Delete an existing patient based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
