package com.example.ms_opinion_del_paciente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_opinion_del_paciente.model.OpinionPaciente;

public interface OpinionPacienteRepository extends JpaRepository<OpinionPaciente, Long>{

}
