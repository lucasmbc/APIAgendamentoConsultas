package com.projeto.APIAgendamentoConsultas.domain.repository;

import com.projeto.APIAgendamentoConsultas.domain.model.Consult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultRepository extends JpaRepository<Consult, UUID> {
    boolean existsByDoctorIdAndDateTime(UUID doctorId, LocalDateTime dateTime);
    boolean existsByPatientIdAndDateTimeBetween(UUID patientId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT c FROM Consult c WHERE c.doctor.id = :doctorId AND c.dateTime BETWEEN :start AND :end")
    List<Consult> findByDoctorAndPeriod(@Param("doctorId") UUID doctorId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
