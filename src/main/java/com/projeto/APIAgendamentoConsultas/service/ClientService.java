package com.projeto.APIAgendamentoConsultas.service;

import com.projeto.APIAgendamentoConsultas.domain.model.Client;
import com.projeto.APIAgendamentoConsultas.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public Client save(Client client) {
        var encryptedPassword = encoder.encode(client.getClientSecret());
        client.setClientSecret(encryptedPassword);
        return repository.save(client);
    }

    public Client getByClientID(String clientId) {
        return repository.findByClientId(clientId);
    }
}
