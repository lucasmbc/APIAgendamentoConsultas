package com.projeto.APIAgendamentoConsultas.controller.dto;

import com.projeto.APIAgendamentoConsultas.domain.model.Consult;
import com.projeto.APIAgendamentoConsultas.domain.model.Doctor;
import com.projeto.APIAgendamentoConsultas.domain.model.Patient;
import com.projeto.APIAgendamentoConsultas.domain.model.StatusConsult;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class ConsultDto {
    Long id;

    @FutureOrPresent(message = "A data da consulta não pode ser no passado")
    @NotNull(message = "The date and time to be created cannot be null")
    LocalDateTime dateTime;

    StatusConsult status;

    String observations;

    @NotNull(message = "Patient ID cannot be null")
    Long patientId;

    @NotNull(message = "Doctor ID cannot be null")
    Long doctorId;

    public ConsultDto(Consult model) {
        this(
                model.getId(),
                model.getDateTime(),
                model.getStatus(),
                model.getObservations(),
                model.getPatient().getId(),
                model.getDoctor().getId()
        );
    }

    public Consult toModel() {
        Consult model = new Consult();
        Patient patient = new Patient();
        Doctor doctor = new Doctor();

        model.setId(this.id);
        model.setDateTime(this.dateTime);
        model.setStatus(this.status);
        model.setObservations(this.observations);
        patient.setId(this.patientId);
        model.setPatient(patient);
        doctor.setId(this.doctorId);
        model.setDoctor(doctor);

        return model;
    }
}
