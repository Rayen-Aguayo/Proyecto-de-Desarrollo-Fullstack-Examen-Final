package com.example.ms_reserva_y_anular_hora.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ms_reserva_y_anular_hora.dto.ApiResponse;
import com.example.ms_reserva_y_anular_hora.dto.PacienteResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PacienteClient {
    private final WebClient webClient;
    private final String BASE_URL = "http://localhost:8085/api/v1/paciente/";
    public PacienteResponse getPacienteClient(String run, String token){
    ApiResponse<PacienteResponse> response = webClient.get()
                .uri(BASE_URL + run)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(new org.springframework.core.ParameterizedTypeReference<ApiResponse<PacienteResponse>>() {})
                .block();
        return response != null ? response.getData() : null;
        }
}
