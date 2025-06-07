package com.ScholarSync.ScholarSync.service;

import com.ScholarSync.ScholarSync.entity.Estudiante;
import com.ScholarSync.ScholarSync.repositorio.EstudianteDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EstudianteServiceTest {

    @Mock
    private EstudianteDao estudianteDao;

    @InjectMocks
    private EstudianteService estudianteService;

    @Test
    public void testCrearEstudiante(){
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Lucas Morales");
        estudiante.setCorreo("lucasmorales@gmail.com");
        estudiante.setTelefono("123046897");
        estudiante.setIdioma("español");

        when(estudianteDao.existsByCorreo(anyString())).thenReturn(false);
        when(estudianteDao.save(any(Estudiante.class))).thenReturn(estudiante);

        Estudiante resultado = estudianteService.crearEstudiante(estudiante);
        assertNotNull(resultado);
        assertEquals("Lucas Morales",resultado.getNombre());
        verify(estudianteDao, times(1)).save(estudiante);

    }

    @Test
    public void testCrearEstudianteCorreoExistente(){
        Estudiante estudiante = new Estudiante();
        estudiante.setCorreo("esxtente@gmail.com");

        when(estudianteDao.existsByCorreo(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            estudianteService.crearEstudiante(estudiante);
        });
    }

}