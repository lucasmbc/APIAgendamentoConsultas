package com.projeto.APIAgendamentoConsultas.service;

import com.projeto.APIAgendamentoConsultas.domain.model.Consult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ConsultService extends CrudService<UUID, Consult> {
    List<Consult> findByDoctorIdAndStartDateBetween(UUID doctorId, LocalDateTime start, LocalDateTime end);
}
