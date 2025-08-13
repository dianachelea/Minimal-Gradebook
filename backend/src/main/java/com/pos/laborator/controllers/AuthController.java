package com.pos.laborator.controllers;

import authenticate.AuthServiceGrpc;
import authenticate.AuthServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gradebook/auth")
public class AuthController {

    private final AuthServiceGrpc.AuthServiceBlockingStub authStub;

    public AuthController() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        authStub = AuthServiceGrpc.newBlockingStub(channel);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthServiceOuterClass.AuthRequest request = AuthServiceOuterClass.AuthRequest.newBuilder()
                    .setUsername(loginRequest.getUsername())
                    .setPassword(loginRequest.getPassword())
                    .build();

            AuthServiceOuterClass.AuthResponse response = authStub.authenticate(request);
            String token = response.getJWS();

            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(401).body("Autentificare esuata. Verificati credentialele.");
            }

            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Eroare la autentificare: " + e.getMessage());
        }
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
