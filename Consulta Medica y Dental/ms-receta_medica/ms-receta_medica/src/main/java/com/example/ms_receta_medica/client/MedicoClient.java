package com.example.ms_receta_medica.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ms_receta_medica.dto.ApiResponse;
import com.example.ms_receta_medica.dto.MedicoResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MedicoClient {
    private final WebClient webClient;

    private final String BASE_URL = "http://localhost:8082/api/v1/medicos";

    public MedicoResponse getMedicoClient(String id, String token) {

        ApiResponse<MedicoResponse> response = webClient.get()
                .uri(BASE_URL + id)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<ApiResponse<MedicoResponse>>() {})
                .block();

        return response != null ? response.getData() : null;
    }
}