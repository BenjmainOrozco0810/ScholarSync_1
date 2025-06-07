package com.ScholarSync.ScholarSync.controller;

import com.ScholarSync.ScholarSync.entity.Estudiante;
import com.ScholarSync.ScholarSync.repositorio.EstudianteDao;
import com.ScholarSync.ScholarSync.service.EstudianteService;
import com.ScholarSync.ScholarSync.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EstudianteController.class)
class EstudianteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstudianteService estudianteService;

    @MockBean
    private EstudianteDao estudianteDao;

    @MockBean
    private JWTUtils jwtUtils;



    @Test
    public void testGetEstudiante() throws Exception {

        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Lucas Morales");
        estudiante.setCorreo("lucasmorales@gmail.com");
        estudiante.setTelefono("1230456897");
        estudiante.setIdioma("español");

        when(estudianteDao.findById(1L)).thenReturn(Optional.of(estudiante));

        mockMvc.perform(get("/api/estudiantes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Lucas Morales"));

    }

//    @Test
//    public void testCrearEstudianteInvalido()throws Exception{
//
//        Estudiante estudiante = new Estudiante();
//        estudiante.setIdioma("12345");
//
//        mockMvc.perform(post("/api/estudiante")
//                        .content(asJsonString(estudiante))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//
//
//    }
//    private static String asJsonString(final Object obj){
//
//        try{
//            return  new ObjectMapper().writeValueAsString(obj);
//        }catch (Exception e){
//            throw new RuntimeException(e);
//        }
//
//    }


}