package com.projeto.APIAgendamentoConsultas.domain.specification;

import com.projeto.APIAgendamentoConsultas.domain.model.Patient;
import org.springframework.data.jpa.domain.Specification;

public class PatientSpecifications {

    public static Specification<Patient> hasName(String name) {
        return (root, query, cb) -> name == null
                ? null
                : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Patient> hasCpf(String cpf) {
        return (root, query, cb) -> cpf == null
                ? null
                : cb.equal(root.get("cpf"), cpf);
    }
}
