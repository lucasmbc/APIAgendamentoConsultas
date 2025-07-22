package com.projeto.APIAgendamentoConsultas.controller;

import com.projeto.APIAgendamentoConsultas.security.CustomAuthentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Login View Controller")
public class LoginViewController {

    @GetMapping("/login")
    public String pageLogin() {
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    public String pageHome(Authentication authentication) {
        if(authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.getUser());
        }

        return "Olá " + authentication.getName();
    }

    @GetMapping("/authorized")
    @ResponseBody
    public String getAuthorizationCode(@RequestParam("code") String code){
        return "Seu authorization code: " + code;
    }
}
