package com.projeto.APIAgendamentoConsultas.controller.dto;

import com.projeto.APIAgendamentoConsultas.domain.model.Doctor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class DoctorDto {
    Long id;

    @NotNull
    @NotBlank(message = "Doctor name must not be blank")
    String name;

    @NotBlank(message = "Doctor email must not be blank")
    @Email(message = "Invalid email")
    String email;

    @NotBlank(message = "Phone must not be blank")
    String phone;

    @NotNull(message = "Doctor CRM must not be null")
    @NotBlank(message = "Doctor CRM must not be blank")
    String crm;

    String specialty;

    List<ConsultDto> consult;

    public DoctorDto(Doctor model) {
        this(
                model.getId(),
                model.getName(),
                model.getEmail(),
                model.getPhone(),
                model.getCrm(),
                model.getSpecialty(),
                ofNullable(model.getConsultations()).orElse(emptyList()).stream().map(ConsultDto::new).toList());
    }

    public Doctor toModel() {
        Doctor model = new Doctor();

        model.setId(this.id);
        model.setName(this.name);
        model.setEmail(this.email);
        model.setPhone(this.phone);
        model.setCrm(this.crm);
        model.setSpecialty(this.specialty);
        model.setConsultations(ofNullable(this.consult).orElse(emptyList()).stream().map(ConsultDto::toModel).toList());

        return model;
    }
}
