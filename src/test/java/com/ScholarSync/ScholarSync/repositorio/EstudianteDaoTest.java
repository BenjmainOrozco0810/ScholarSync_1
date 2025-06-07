package com.ScholarSync.ScholarSync.repositorio;

import com.ScholarSync.ScholarSync.entity.Estudiante;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EstudianteDaoTest {

    @Autowired
    private EstudianteDao estudianteDao;

    @Test
    public void guardarEstudianteTest (){

        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Lucas Morales");
        estudiante.setCorreo("lucasmorales@gmail.com");
        estudiante.setTelefono("1230456897");
        estudiante.setIdioma("español");

        Estudiante guardado = estudianteDao.save(estudiante);

        assertNotNull(guardado.getId());
        assertEquals("Lucas Morales",guardado.getNombre());
    }

    @Test
    public void buscarPorCorreoTest(){

        String correo = "testcorreo@gmail.com";
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Lucas Morales");
        estudiante.setCorreo(correo);
        estudiante.setTelefono("1230456897");
        estudiante.setIdioma("español");
        estudianteDao.save(estudiante);
        boolean exist = estudianteDao.existsByCorreo(correo);

        assertTrue(exist);

    }


}