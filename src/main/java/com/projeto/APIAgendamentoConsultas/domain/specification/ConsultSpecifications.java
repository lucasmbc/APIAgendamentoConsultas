package com.projeto.APIAgendamentoConsultas.domain.specification;

import com.projeto.APIAgendamentoConsultas.domain.model.Consult;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

public class ConsultSpecifications {

    public static Specification<Consult> hasDoctorId(UUID doctorId) {
        return (root, query, cb) -> cb.equal(root.get("doctor").get("id"), doctorId);
    }

    public static Specification<Consult> hasPatientId(UUID patientId) {
        return (root, query, cb) -> cb.equal(root.get("patient").get("id"), patientId);
    }

    public static Specification<Consult> startDateBetween(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) -> cb.between(root.get("dateTime"), start, end);
    }
}
