package com.example.demo.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.dto.AccessTokenDTO;
import com.example.demo.api.dto.LoginDTO;
import com.example.demo.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthApi {

    private AuthService authService;

    public AuthApi(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<AccessTokenDTO> authLogin(@RequestBody @Valid LoginDTO loginDTO) throws Exception {
        String userId = authService.validateCredentials(loginDTO.username(), loginDTO.password());
        var token = new AccessTokenDTO(authService.generateJwt(userId));

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

}
