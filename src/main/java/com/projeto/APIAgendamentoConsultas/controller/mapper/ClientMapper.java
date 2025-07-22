package com.projeto.APIAgendamentoConsultas.controller.mapper;

import com.projeto.APIAgendamentoConsultas.controller.dto.ClientDto;
import com.projeto.APIAgendamentoConsultas.domain.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    public Client toEntity(ClientDto dto) {
        Client client = new Client();
        client.setClientId(dto.clientId());
        client.setClientSecret(dto.clientSecret());
        client.setRedirectURI(dto.redirectURI());
        client.setScope(dto.scope());
        return client;
    }
}
