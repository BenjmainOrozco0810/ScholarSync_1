package com.ScholarSync.ScholarSync.controller;


import com.ScholarSync.ScholarSync.entity.Estudiante;
import com.ScholarSync.ScholarSync.repositorio.EstudianteDao;
import com.ScholarSync.ScholarSync.service.EstudianteService;
import com.ScholarSync.ScholarSync.utils.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EstudianteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private EstudianteService estudianteService;

    @Mock
    private EstudianteDao estudianteDao;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private EstudianteController estudianteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEstudiantes_ConTokenValido_RetornaLista() {

        EstudianteController controllerSpy = Mockito.spy(estudianteController);
        doReturn(true).when(controllerSpy).validarToken("Bearer token_valido");

        when(estudianteService.getEstudiante()).thenReturn(List.of(new Estudiante(), new Estudiante()));

        List<Estudiante> respuesta = controllerSpy.getEstudiantes("Bearer token_valido");

        assertEquals(2, respuesta.size());
        verify(estudianteService).getEstudiante();
    }

    @Test
    void eliminarEstudiante_ConTokenValido_EjecutaEliminacion() {

        EstudianteController controllerSpy = Mockito.spy(estudianteController);
        doReturn(true).when(controllerSpy).validarToken("Bearer token_valido");


        controllerSpy.eliminar("Bearer token_valido", 1L);


        verify(estudianteService).eliminarEstudiante(1L);
        verify(controllerSpy).validarToken("Bearer token_valido");
    }

    @Test
    void validarToken_FuncionaComoFrontendLoEspera() {

        when(jwtUtils.getKey("Bearer token_frontend")).thenReturn("maestor_id");


        boolean resultado = estudianteController.validarToken("Bearer token_frontend");

        assertTrue(resultado);
    }


    @Test
    void getEstudiantes_ConTokenInvalido_RetornaNull() {
        when(jwtUtils.getKey("token_invalido")).thenReturn(null);
        assertNull(estudianteController.getEstudiantes("Bearer token_invalido"));
    }


    @Test
    void registrarEstudiante_GuardaCorrectamente() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Juan");

        estudianteController.registrarEstudiante(estudiante);

        verify(estudianteService, times(1)).crearEstudiante(estudiante);
    }


    @Test
    void getEstudiantePorId_Existe_RetornaEstudiante() {
        Estudiante estudianteMock = new Estudiante();
        estudianteMock.setId(1L);
        when(estudianteDao.findById(1L)).thenReturn(java.util.Optional.of(estudianteMock));

        ResponseEntity<Estudiante> respuesta = estudianteController.getEstudiante(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1L, respuesta.getBody().getId());
    }

    @Test
    void getEstudiantePorId_NoExiste_LanzaExcepcion() {
        when(estudianteDao.findById(99L)).thenReturn(java.util.Optional.empty());

        assertThrows(EstudianteController.ResourceNotFoundException.class, () -> {
            estudianteController.getEstudiante(99L);
        });
    }


    @Test
    void actualizarContacto_ActualizaCorreoYTelefono() {

        Estudiante estudianteMock = new Estudiante();
        estudianteMock.setId(1L);


        when(estudianteDao.findById(1L)).thenReturn(java.util.Optional.of(estudianteMock));


        when(estudianteDao.save(any(Estudiante.class))).thenAnswer(invocation -> {
            Estudiante estudianteGuardado = invocation.getArgument(0);

            return estudianteGuardado;
        });


        Map<String, String> updates = new HashMap<>();
        updates.put("correo", "nuevo@correo.com");
        updates.put("telefono", "123456789");


        ResponseEntity<Estudiante> respuesta = estudianteController.actualizarContacto(1L, updates);


        assertNotNull(respuesta.getBody());
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("nuevo@correo.com", respuesta.getBody().getCorreo());
        assertEquals("123456789", respuesta.getBody().getTelefono());


        verify(estudianteDao).save(any(Estudiante.class));
    }
}