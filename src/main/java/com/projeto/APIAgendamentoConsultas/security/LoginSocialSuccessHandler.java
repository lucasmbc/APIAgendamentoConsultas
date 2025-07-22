package com.projeto.APIAgendamentoConsultas.security;

import com.projeto.APIAgendamentoConsultas.domain.model.User;
import com.projeto.APIAgendamentoConsultas.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final String DEFAULT_PASSWORD = "321";

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        User user = userService.findByEmail(email);

        if(user == null) {
            user = registerUserInTheDatabase(email);
        }

        authentication = new CustomAuthentication(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private User registerUserInTheDatabase(String email) {
        User user = new User();
        user.setEmail(email);

        user.setUsername(getLoginFromEmail(email));

        user.setPassword(DEFAULT_PASSWORD);
        user.setRoles(List.of("OPERADOR"));

        userService.create(user);
        return user;
    }

    private String getLoginFromEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
