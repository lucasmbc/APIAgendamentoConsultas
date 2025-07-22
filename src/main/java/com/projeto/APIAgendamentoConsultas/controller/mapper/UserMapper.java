package com.projeto.APIAgendamentoConsultas.controller.mapper;

import com.projeto.APIAgendamentoConsultas.controller.dto.UserRequestDto;
import com.projeto.APIAgendamentoConsultas.controller.dto.UserResponseDto;
import com.projeto.APIAgendamentoConsultas.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public User toModel(UserRequestDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setRoles(dto.roles());
        return user;
    }

    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(user.getId(), user.getUsername(), user.getRoles());
    }
}
