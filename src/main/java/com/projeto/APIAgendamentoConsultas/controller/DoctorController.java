package com.projeto.APIAgendamentoConsultas.controller;

import com.projeto.APIAgendamentoConsultas.controller.dto.DoctorRequestDto;
import com.projeto.APIAgendamentoConsultas.controller.dto.DoctorResponseDto;
import com.projeto.APIAgendamentoConsultas.controller.mapper.DoctorMapper;
import com.projeto.APIAgendamentoConsultas.service.DoctorService;
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
@RequestMapping("/medicos")
@RequiredArgsConstructor
@Tag(name = "Doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper mapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'GERENTE')")
    @Operation(summary = "Get all doctors", description = "Retrieve a list of all registered doctors")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operation successful")})
    public ResponseEntity<List<DoctorResponseDto>> findAll(
            @RequestParam(value = "specialty", required = false) String specialty,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "crm", required = false) String crm
    ) {

        var doctors = doctorService.findDoctors(specialty, name, crm).stream().map(mapper::toResponseDto).toList();

        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'GERENTE')")
    @Operation(summary = "Get a doctor by ID", description = "Retrieve a specific doctor based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<DoctorResponseDto> findById(@PathVariable UUID id) {
        var doctor = doctorService.findById(id);
        return ResponseEntity.ok(mapper.toResponseDto(doctor));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'GERENTE')")
    @Operation(summary = "Create a new doctor", description = "Create a new doctor and return the created doctor's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid doctor data provided")
    })
    public ResponseEntity<DoctorResponseDto> create(@Valid @RequestBody DoctorRequestDto requestDto) {
        var doctor = doctorService.create(mapper.toModel(requestDto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(doctor.getId())
                .toUri();
        return ResponseEntity.created(location).body(mapper.toResponseDto(doctor));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR', 'GERENTE')")
    @Operation(summary = "Update a doctor", description = "Update the data of an existing doctor based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor updated successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor not found"),
            @ApiResponse(responseCode = "422", description = "Invalid doctor data provided")
    })
    public ResponseEntity<DoctorResponseDto> update(@PathVariable UUID id, @RequestBody DoctorRequestDto requestDto) {
        var doctor = doctorService.update(id, mapper.toModel(requestDto));
        return ResponseEntity.ok(mapper.toResponseDto(doctor));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(summary = "Delete a doctor", description = "Delete an existing doctor based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doctor deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
