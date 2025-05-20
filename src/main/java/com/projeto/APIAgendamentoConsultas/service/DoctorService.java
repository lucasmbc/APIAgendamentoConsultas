package com.projeto.APIAgendamentoConsultas.service;

import com.projeto.APIAgendamentoConsultas.domain.model.Doctor;

import java.util.List;

public interface DoctorService extends CrudService<Long, Doctor>{
    List<Doctor> findBySpecialty(String specialty);
}
