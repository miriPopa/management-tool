package com.store_management_tool.management_tool.service;

import com.store_management_tool.management_tool.dto.keycloak.AuthorizationRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KeycloakService {
    private final String TOKEN_URL = "http://localhost:8180/auth/realms/storeAppTool/protocol/openid-connect/token";

    public String getToken(AuthorizationRequest authorizationRequest){
        Map<Object, Object> formData = Map.of(
                "grant_type", "password",
                "client_id", "user-client",
                "username", authorizationRequest.getUsername(),
                "password", authorizationRequest.getPassword()
        );

        // Build the form-encoded body
        String formBody = formData.entrySet()
                .stream()
                .map(entry -> URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8) + "="
                        + URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TOKEN_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(formBody))
                .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response.body();
    }
}
