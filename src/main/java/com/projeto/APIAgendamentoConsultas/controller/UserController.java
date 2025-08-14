package com.projeto.APIAgendamentoConsultas.controller;

import com.projeto.APIAgendamentoConsultas.controller.dto.UserRequestDto;
import com.projeto.APIAgendamentoConsultas.controller.dto.UserResponseDto;
import com.projeto.APIAgendamentoConsultas.controller.mapper.UserMapper;
import com.projeto.APIAgendamentoConsultas.domain.model.User;
import com.projeto.APIAgendamentoConsultas.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
public record UserController(UserService userService, UserMapper mapper) {

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user and return the created user's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid user data provided")
    })
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto request) {
        User user = mapper.toModel(request);
        User createdUser = userService.create(user);

        UserResponseDto responseDto = mapper.toResponseDto(createdUser);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping
    @Operation(summary = "Get all users or find by username", description = "Retrieve all users or find a specific user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<UserResponseDto>> findAll(@RequestParam(required = false) String username) {
//        if (username != null && !username.isEmpty()) {
//            var user = userService.findByUsername(username);
//            return ResponseEntity.ok(List.of(new UserResponseDto(user)));
//        }

        var users = userService.findAll(username);
        var usersDto = users.stream().map(mapper::toResponseDto).toList();
        return ResponseEntity.ok(usersDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID", description = "Retrieve a specific user based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponseDto> findById(@PathVariable String id) {
        var idUser = UUID.fromString(id);
        var user = userService.findById(idUser);
        return ResponseEntity.ok(mapper.toResponseDto(user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Delete an existing user based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> delete(@PathVariable String id) {
        var idUser = UUID.fromString(id);
        userService.delete(idUser);
        return ResponseEntity.noContent().build();
    }
}
