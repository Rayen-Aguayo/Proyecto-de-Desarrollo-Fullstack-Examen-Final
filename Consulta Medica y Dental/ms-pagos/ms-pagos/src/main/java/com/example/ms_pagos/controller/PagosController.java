package com.example.ms_pagos.controller;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ms_pagos.dto.*;
import com.example.ms_pagos.service.PagosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Pagos", description = "Operaciones relacionadas con la gestión de pagos médicos")
@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
public class PagosController {

    private final PagosService pagosService;

    @Operation(summary = "Registrar pago", description = "Crea un nuevo pago médico. Requiere rol ADMIN.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Pago registrado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<PagosResponse>> crear(
            @Valid @RequestBody PagosDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<PagosResponse>builder()
                        .success(true)
                        .message("Pago registrado")
                        .data(pagosService.crear(dto, token))
                        .build()
        );
    }

    @Operation(summary = "Listar pagos", description = "Retorna todos los pagos del sistema. Requiere rol ADMIN.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<PagosResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<PagosResponse>>builder()
                        .success(true)
                        .data(pagosService.listar(token))
                        .build()
        );
    }

    @Operation(summary = "Obtener pago por ID", description = "Busca un pago utilizando su identificador único. Requiere rol USER o ADMIN.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Pago obtenido exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<PagosResponse>> obtener(
            @Parameter(description = "ID del pago", example = "1")
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        PagosResponse pago = pagosService.obtener(id, token);

        EntityModel<PagosResponse> recurso = EntityModel.of(pago);
        recurso.add(linkTo(methodOn(PagosController.class).obtener(id, token)).withSelfRel());
        recurso.add(linkTo(methodOn(PagosController.class).listar(token)).withRel("all"));
        recurso.add(linkTo(methodOn(PagosController.class).actualizar(id, null, token)).withRel("update"));
        recurso.add(linkTo(methodOn(PagosController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(
                ApiResponse.<PagosResponse>builder()
                        .success(true)
                        .data(pago)
                        .build()
        );
    }

    @Operation(summary = "Actualizar pago por ID", description = "Modifica un pago existente. Requiere rol USER o ADMIN.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Pago actualizado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<PagosResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PagosDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<PagosResponse>builder()
                        .success(true)
                        .message("Pago actualizado")
                        .data(pagosService.actualizar(id, dto, token))
                        .build()
        );
    }

    @Operation(summary = "Eliminar pago por ID", description = "Elimina un pago existente. Requiere rol USER o ADMIN.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Pago eliminado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token inválido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado")
    })

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        pagosService.eliminar(id);

        return ResponseEntity.status(200).body(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Pago eliminado")
                        .build()
        );
    }
}