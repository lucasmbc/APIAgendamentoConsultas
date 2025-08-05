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

    List<Patient> findByNameStartingWithIgnoreCase(String name);
    List<Patient> findByCpf(String cpf);
}
