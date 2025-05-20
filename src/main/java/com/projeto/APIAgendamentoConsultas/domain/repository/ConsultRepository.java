package com.projeto.APIAgendamentoConsultas.domain.repository;

import com.projeto.APIAgendamentoConsultas.domain.model.Consult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultRepository extends JpaRepository<Consult, Long> {
    boolean existsByDoctorIdAndDateTime(Long doctorId, LocalDateTime dateTime);
    boolean existsByPatientIdAndDateTimeBetween(Long patientId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT c FROM Consult c WHERE c.doctor.id = :doctorId AND c.dateTime BETWEEN :start AND :end")
    List<Consult> findByDoctorAndPeriod(@Param("doctorId") Long doctorId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
