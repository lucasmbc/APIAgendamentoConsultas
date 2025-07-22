package com.projeto.APIAgendamentoConsultas.domain.repository;

import com.projeto.APIAgendamentoConsultas.domain.model.Patient;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);

//    @Query("SELECT DISTINCT p FROM Patient p LEFT JOIN FETCH p.consultations")
    @EntityGraph(attributePaths = "consultations")
    @Query("SELECT p FROM Patient p")
    List<Patient> findAllWithConsultations();

    @EntityGraph(attributePaths = "consultations")
    @Query("SELECT p FROM Patient p WHERE p.id = :id")
    Optional<Patient> findByIdWithConsultations(@Param("id") UUID id);
}
