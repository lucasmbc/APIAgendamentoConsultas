package com.projeto.APIAgendamentoConsultas.service;

import com.projeto.APIAgendamentoConsultas.domain.model.User;
import com.projeto.APIAgendamentoConsultas.domain.repository.UserRepository;
import com.projeto.APIAgendamentoConsultas.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll(String username) {
        if (userRepository.count() == 0) {
            throw new NotFoundException();
        }

        var user = new User();
        user.setUsername(username);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<User> userExample = Example.of(user, matcher);

        List<User> users = userRepository.findAll(userExample);

        if (users.isEmpty()) {
            throw new NotFoundException();
        }

        return users;
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User create(User userToCreate) {
        var password = userToCreate.getPassword();
        userToCreate.setPassword(passwordEncoder.encode(password));
        return userRepository.save(userToCreate);
    }

    public void delete(UUID id) {
        User dbUser = this.findById(id);
        userRepository.delete(dbUser);
    }
}
