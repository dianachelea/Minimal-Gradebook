package com.pos.laborator.controllers;
import authenticate.AuthServiceGrpc;
import authenticate.AuthServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


public class AuthServiceClient {

    private final AuthServiceGrpc.AuthServiceBlockingStub authStub;

    public AuthServiceClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        authStub = AuthServiceGrpc.newBlockingStub(channel);
    }

    public String authenticate(String username, String password) {
        AuthServiceOuterClass.AuthRequest request = AuthServiceOuterClass.AuthRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();

        AuthServiceOuterClass.AuthResponse response = authStub.authenticate(request);
        return response.getJWS();
    }

    public String validate(String jws) {
        AuthServiceOuterClass.ValidateRequest request = AuthServiceOuterClass.ValidateRequest.newBuilder()
                .setJWS(jws)
                .build();

        AuthServiceOuterClass.ValidateResponse response = authStub.validate(request);
        return response.getMessage();
    }

    public String invalidate(String jws) {
        AuthServiceOuterClass.InvalidateRequest request = AuthServiceOuterClass.InvalidateRequest.newBuilder()
                .setJWS(jws)
                .build();

        AuthServiceOuterClass.InvalidateResponse response = authStub.invalidate(request);
        return response.getMessage();
    }

}

