package com.example.ms_receta_medica.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Receta_medica")
public class RecetaMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nomMedicamento;
    private Integer diasTomarMedicamento;
    private LocalDate inicioReceta;
    private String nomMedico;
    private String runMedico;
    private Integer cantTomarDia;
    private String firmaMedico;

}