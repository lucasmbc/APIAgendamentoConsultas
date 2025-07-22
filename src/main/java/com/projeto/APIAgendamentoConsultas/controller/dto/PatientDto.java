package com.projeto.APIAgendamentoConsultas.controller.dto;

import com.projeto.APIAgendamentoConsultas.domain.model.Patient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class PatientDto {
    private Long id;

    @NotNull
    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "CPF must not be blank")
    @CPF
    private String cpf;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Phone must not be blank")
    private String phone;

    private List<ConsultDto> consult;

    public PatientDto(Patient model) {
        this(
                model.getId(),
                model.getName(),
                model.getEmail(),
                model.getCpf(),
                model.getBirthDate(),
                model.getPhone(),
                ofNullable(model.getConsultations()).orElse(emptyList()).stream().map(ConsultDto::new).toList());
    }

    public Patient toModel() {
        Patient model = new Patient();
        model.setId(this.id);
        model.setName(this.name);
        model.setEmail(this.email);
        model.setPhone(this.phone);
        model.setCpf(this.cpf);
        model.setBirthDate(this.birthDate);
        model.setConsultations(ofNullable(this.consult).orElse(emptyList()).stream().map(ConsultDto::toModel).toList());
        return model;
    }
}
