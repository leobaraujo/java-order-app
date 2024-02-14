package com.example.demo.api;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.api.dto.NewUserDTO;
import com.example.demo.api.dto.UpdateUserRoleDTO;
import com.example.demo.domain.entity.User;
import com.example.demo.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User")
@SecurityRequirement(name = "Bearer Token")
public class UserApi {

    private UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all users as array")
    })
    public ResponseEntity<List<User>> getAll() {
        var userList = userService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the user"),
            @ApiResponse(responseCode = "404", description = "If the user is not found")
    })
    public ResponseEntity<User> getById(@PathVariable UUID id) {
        try {
            var user = userService.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "If username is invalid")
    })
    public ResponseEntity<String> create(@RequestBody @Valid NewUserDTO newUserDTO) throws Exception {
        User newUser = userService.create(newUserDTO);
        URI newResourceLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUser.getId()).toUri();

        return ResponseEntity.created(newResourceLocation).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "If username is invalid or user is not found")
    })
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody @Valid NewUserDTO newUserDTO) throws Exception {
        userService.update(id, newUserDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}/role")
    @Operation(summary = "Update user's role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "If role is invalid or user is not found")
    })
    public ResponseEntity<Void> updateRole(@PathVariable UUID id, @RequestBody @Valid UpdateUserRoleDTO updateUserRoleDTO) throws Exception {
        userService.updateRole(id, updateUserRoleDTO.role());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "400", description = "If id is null")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean isUserDeleted = userService.delete(id);
        HttpStatus status = isUserDeleted ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).build();
    }

}
