package com.example.demo.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.dto.AccessTokenDTO;
import com.example.demo.api.dto.LoginDTO;
import com.example.demo.domain.exception.InvalidUserException;
import com.example.demo.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthApi {

    private AuthService authService;

    public AuthApi(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    @Operation(summary = "Authenticates the user and returns an access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the access token"),
            @ApiResponse(responseCode = "404", description = "If username or password are invalid")
    })
    public ResponseEntity<AccessTokenDTO> authLogin(@RequestBody @Valid LoginDTO loginDTO) throws InvalidUserException {
        String userId = authService.validateCredentials(loginDTO.username(), loginDTO.password());
        var token = new AccessTokenDTO(authService.generateJwt(userId));

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

}
