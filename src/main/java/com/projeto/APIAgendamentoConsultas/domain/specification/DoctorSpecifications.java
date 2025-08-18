package com.projeto.APIAgendamentoConsultas.domain.specification;

import com.projeto.APIAgendamentoConsultas.domain.model.Doctor;
import org.springframework.data.jpa.domain.Specification;

public class DoctorSpecifications {

    public static Specification<Doctor> hasSpecialty(String specialty) {
        return ((root, query, cb) -> specialty == null
                ? null
                : cb.like(cb.lower(root.get("specialty")), "%" + specialty.toLowerCase() + "%"));
    }

    public static Specification<Doctor> hasName(String name) {
        return ((root, query, cb) -> name == null
                ? null
                : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
    }

    public static Specification<Doctor> hasCrm(String crm) {
        return ((root, query, cb) -> crm == null
                ? null
                : cb.equal(root.get("crm"), crm));
    }
}
