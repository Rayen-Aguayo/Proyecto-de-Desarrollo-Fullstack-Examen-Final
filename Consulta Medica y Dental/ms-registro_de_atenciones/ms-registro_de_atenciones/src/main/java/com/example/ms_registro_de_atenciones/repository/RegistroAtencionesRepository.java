package com.example.ms_registro_de_atenciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_registro_de_atenciones.model.RegistroAtenciones;

public interface RegistroAtencionesRepository extends JpaRepository<RegistroAtenciones, Long> {

}
