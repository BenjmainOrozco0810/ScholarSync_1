package com.ScholarSync.ScholarSync.service;

import com.ScholarSync.ScholarSync.entity.Estudiante;
import com.ScholarSync.ScholarSync.repositorio.EstudianteDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstudianteServiceTest {

    @Mock
    private EstudianteDao estudianteRepository;

    @InjectMocks
    private EstudianteService estudianteService;


    @Test
    void crearEstudiante_ConDatosValidos_RetornaEstudiante() {

        Estudiante estudianteValido = new Estudiante();
        estudianteValido.setNombre("Ana");
        estudianteValido.setCorreo("ana@mail.com");
        estudianteValido.setIdioma("español");

        when(estudianteRepository.existsByCorreo("ana@mail.com")).thenReturn(false);
        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(estudianteValido);


        Estudiante resultado = estudianteService.crearEstudiante(estudianteValido);


        assertNotNull(resultado);
        assertEquals("Ana", resultado.getNombre());
        verify(estudianteRepository).save(estudianteValido);
    }

    @Test
    void crearEstudiante_ConCorreoExistente_LanzaExcepcion() {

        Estudiante estudiante = new Estudiante();
        estudiante.setCorreo("repetido@mail.com");
        when(estudianteRepository.existsByCorreo("repetido@mail.com")).thenReturn(true);


        assertThrows(IllegalArgumentException.class, () -> {
            estudianteService.crearEstudiante(estudiante);
        }, "Debería lanzar IllegalArgumentException por correo duplicado");
    }

    @Test
    void crearEstudiante_ConIdiomaInvalido_LanzaExcepcion() {

        Estudiante estudiante = new Estudiante();
        estudiante.setCorreo("nuevo@mail.com");
        estudiante.setIdioma("alemán");

        when(estudianteRepository.existsByCorreo("nuevo@mail.com")).thenReturn(false);


        assertThrows(IllegalArgumentException.class, () -> {
            estudianteService.crearEstudiante(estudiante);
        }, "Debería lanzar IllegalArgumentException por idioma inválido");
    }


    @Test
    void getEstudiante_RetornaListaNoVacia() {
        Estudiante estudiante1 = new Estudiante();
        estudiante1.setNombre("Carlos");
        estudiante1.setCorreo("carlos@mail.com");
        estudiante1.setTelefono("1230456897");
        estudiante1.setIdioma("español");
        Estudiante estudiante2 = new Estudiante();
        estudiante2.setNombre("Diana");
        estudiante2.setCorreo("diana@mail.com");
        estudiante2.setTelefono("1230456897");
        estudiante2.setIdioma("español");


        List<Estudiante> estudiantesMock = Arrays.asList( estudiante1,estudiante2);
        when(estudianteRepository.findAll()).thenReturn(estudiantesMock);


        List<Estudiante> resultado = estudianteService.getEstudiante();


        assertEquals(2, resultado.size());
        verify(estudianteRepository).findAll();
    }


    @Test
    void eliminarEstudiante_ConIdExistente_EliminaCorrectamente() {

        Long idExistente = 1L;
        when(estudianteRepository.existsById(idExistente)).thenReturn(true);
        doNothing().when(estudianteRepository).deleteById(idExistente);


        estudianteService.eliminarEstudiante(idExistente);


        verify(estudianteRepository).deleteById(idExistente);
    }

    @Test
    void eliminarEstudiante_ConIdInexistente_LanzaExcepcion() {

        Long idInexistente = 99L;
        when(estudianteRepository.existsById(idInexistente)).thenReturn(false);


        assertThrows(IllegalArgumentException.class, () -> {
            estudianteService.eliminarEstudiante(idInexistente);
        }, "Debería lanzar IllegalArgumentException por ID inválido");
    }
}