package com.example.ms_receta_medica.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.ms_receta_medica.model.RecetaMedica;

@DataJpaTest
@ActiveProfiles("test")
class RecetaMedicaRepositoryTest {

    @Autowired
    private RecetaMedicaRepository recetaMedicaRepository;

    private RecetaMedica receta;

    @BeforeEach
    void setUp() {
        recetaMedicaRepository.deleteAll();

        receta = new RecetaMedica();
        receta.setNomMedicamento("Ibuprofeno");
        receta.setDiasTomarMedicamento(7);
        receta.setInicioReceta(LocalDate.of(2026, 7, 1));
        receta.setNomMedico("Dr. Juan Perez");
        receta.setRunMedico("12345678-9");
        receta.setCantTomarDia(3);
        receta.setFirmaMedico("firma-digital-001");
    }

    @Test
    @DisplayName("Deberia guardar una receta medica correctamente")
    void deberiaGuardarRecetaMedica() {
        RecetaMedica guardada = recetaMedicaRepository.save(receta);

        assertThat(guardada.getId()).isNotNull();
        assertThat(guardada.getNomMedicamento()).isEqualTo("Ibuprofeno");
        assertThat(guardada.getDiasTomarMedicamento()).isEqualTo(7);
        assertThat(guardada.getInicioReceta()).isEqualTo(LocalDate.of(2026, 7, 1));
        assertThat(guardada.getNomMedico()).isEqualTo("Dr. Juan Perez");
        assertThat(guardada.getRunMedico()).isEqualTo("12345678-9");
        assertThat(guardada.getCantTomarDia()).isEqualTo(3);
        assertThat(guardada.getFirmaMedico()).isEqualTo("firma-digital-001");
    }

    @Test
    @DisplayName("Deberia retornar receta medica por id")
    void deberiaRetornarRecetaMedicaPorId() {
        RecetaMedica guardada = recetaMedicaRepository.save(receta);

        Optional<RecetaMedica> encontrada = recetaMedicaRepository.findById(guardada.getId());

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getNomMedicamento()).isEqualTo("Ibuprofeno");
    }

    @Test
    @DisplayName("Deberia retornar vacio cuando el id no existe")
    void deberiaRetornarVacioCuandoIdNoExiste() {
        Optional<RecetaMedica> encontrada = recetaMedicaRepository.findById(999L);

        assertThat(encontrada).isEmpty();
    }

    @Test
    @DisplayName("Deberia listar todas las recetas medicas")
    void deberiaListarTodasLasRecetas() {
        recetaMedicaRepository.save(receta);

        RecetaMedica otraReceta = new RecetaMedica();
        otraReceta.setNomMedicamento("Paracetamol");
        otraReceta.setDiasTomarMedicamento(5);
        otraReceta.setInicioReceta(LocalDate.of(2026, 7, 5));
        otraReceta.setNomMedico("Dra. Maria Soto");
        otraReceta.setRunMedico("98765432-1");
        otraReceta.setCantTomarDia(2);
        otraReceta.setFirmaMedico("firma-digital-002");
        recetaMedicaRepository.save(otraReceta);

        List<RecetaMedica> recetas = recetaMedicaRepository.findAll();

        assertThat(recetas).hasSize(2);
    }

    @Test
    @DisplayName("Deberia actualizar una receta medica existente")
    void deberiaActualizarRecetaMedica() {
        RecetaMedica guardada = recetaMedicaRepository.save(receta);

        guardada.setDiasTomarMedicamento(10);
        guardada.setCantTomarDia(4);
        RecetaMedica actualizada = recetaMedicaRepository.save(guardada);

        assertThat(actualizada.getId()).isEqualTo(guardada.getId());
        assertThat(actualizada.getDiasTomarMedicamento()).isEqualTo(10);
        assertThat(actualizada.getCantTomarDia()).isEqualTo(4);
    }

    @Test
    @DisplayName("Deberia eliminar una receta medica por id")
    void deberiaEliminarRecetaMedicaPorId() {
        RecetaMedica guardada = recetaMedicaRepository.save(receta);

        recetaMedicaRepository.deleteById(guardada.getId());

        Optional<RecetaMedica> encontrada = recetaMedicaRepository.findById(guardada.getId());
        assertThat(encontrada).isEmpty();
    }

    @Test
    @DisplayName("Deberia contar la cantidad de recetas medicas")
    void deberiaContarRecetasMedicas() {
        recetaMedicaRepository.save(receta);

        long total = recetaMedicaRepository.count();

        assertThat(total).isEqualTo(1);
    }
}