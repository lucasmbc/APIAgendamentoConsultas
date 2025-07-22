package com.projeto.APIAgendamentoConsultas.service;

import java.util.List;

public interface CrudService<ID, T> {
    List<T> findAll();

    default List<T> findAllByExample(String username) {
        return null;
    }

    T findById(ID id);
    T create(T entity);
    default T update(ID id, T entity) {
        throw new UnsupportedOperationException("Update operation is not supported");
    };
    void delete(ID id);
}
