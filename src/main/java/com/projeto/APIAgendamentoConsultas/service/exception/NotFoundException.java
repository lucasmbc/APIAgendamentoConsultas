package com.projeto.APIAgendamentoConsultas.service.exception;

public class NotFoundException extends BusinessException{
    public NotFoundException() {
        super("Resource not found.");
    }
}
