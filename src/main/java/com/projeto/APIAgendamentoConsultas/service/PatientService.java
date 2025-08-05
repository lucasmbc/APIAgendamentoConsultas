package com.projeto.APIAgendamentoConsultas.service;

import com.projeto.APIAgendamentoConsultas.domain.model.Patient;

import java.util.List;
import java.util.UUID;

public interface PatientService extends CrudService<UUID, Patient>{
    List<Patient> findByName(String name);
    List<Patient> findByCpf(String cpf);
}
