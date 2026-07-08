package com.example.ms_receta_medica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_receta_medica.model.RecetaMedica;

public interface RecetaMedicaRepository extends JpaRepository<RecetaMedica, Long>{

}