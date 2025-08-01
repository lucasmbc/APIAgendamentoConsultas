package com.projeto.APIAgendamentoConsultas.service;

import com.projeto.APIAgendamentoConsultas.domain.model.Doctor;

import java.util.List;
import java.util.UUID;

public interface DoctorService extends CrudService<UUID, Doctor>{
    List<Doctor> findBySpecialty(String specialty);
}
