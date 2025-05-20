package com.projeto.APIAgendamentoConsultas.domain.repository;

import com.projeto.APIAgendamentoConsultas.domain.model.Doctor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByCrm(String crm);
    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "consultations")
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.specialty) LIKE LOWER(:specialty)")
    List<Doctor> findBySpecialtyIgnoreCase(@Param("specialty") String specialty);

    @EntityGraph(attributePaths = "consultations")
    @Query("SELECT d FROM Doctor d")
    List<Doctor> findAllWithConsultations();

    @EntityGraph(attributePaths = "consultations")
    @Query("SELECT d FROM Doctor d WHERE d.id = :id")
    Optional<Doctor> findByIdWithConsultations(@Param("id") Long id);
}
