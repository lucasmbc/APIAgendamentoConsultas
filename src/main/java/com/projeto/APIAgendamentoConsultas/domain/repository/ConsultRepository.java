package com.projeto.APIAgendamentoConsultas.domain.repository;

import com.projeto.APIAgendamentoConsultas.domain.model.Consult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultRepository extends JpaRepository<Consult, UUID>, JpaSpecificationExecutor<Consult> {
    boolean existsByDoctorIdAndDateTime(UUID doctorId, LocalDateTime dateTime);
    boolean existsByPatientIdAndDateTimeBetween(UUID patientId, LocalDateTime start, LocalDateTime end);
}
