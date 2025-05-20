package com.projeto.APIAgendamentoConsultas.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends Person {

    @Column(nullable = false, unique = true)
    private String crm;

    @Column(nullable = false)
    private String specialty;

    @OneToMany(mappedBy = "doctor", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
    private List<Consult> consultations = new ArrayList<>();

    public void addConsultation(Consult consult) {
        this.consultations.add(consult);
        consult.setDoctor(this);
    }
}
