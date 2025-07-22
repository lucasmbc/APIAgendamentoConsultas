package com.projeto.APIAgendamentoConsultas.security;

import com.projeto.APIAgendamentoConsultas.domain.model.User;
import com.projeto.APIAgendamentoConsultas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String passwordTyped = authentication.getCredentials().toString();

        User userFound = userService.findByUsername(login);

        if(userFound == null) {
            throw getUserNotFoundError();
        }

        String encryptedPassword = userFound.getPassword();

        boolean passwordMatch = passwordEncoder.matches(passwordTyped, encryptedPassword);

        if(passwordMatch) {
            return new CustomAuthentication(userFound);
        }

        throw getUserNotFoundError();
    }

    private AuthenticationException getUserNotFoundError() {
        return new UsernameNotFoundException("Incorrect username and/or password!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
