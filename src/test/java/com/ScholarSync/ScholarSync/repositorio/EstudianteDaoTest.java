package com.ScholarSync.ScholarSync.repositorio;

import com.ScholarSync.ScholarSync.entity.Estudiante;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EstudianteDaoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EstudianteDao estudianteDao;


    @Test
    void guardarEstudiante_RetornaEstudianteGuardado() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Juan");
        estudiante.setCorreo("juan@example.com");
        estudiante.setTelefono("1230456897");
        estudiante.setIdioma("español");

        Estudiante guardado = estudianteDao.save(estudiante);

        assertNotNull(guardado.getId());
        assertEquals("Juan", guardado.getNombre());
    }

    @Test
    void buscarPorId_EstudianteExistente_RetornaEstudiante() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Ana");
        estudiante.setCorreo("ana@example.com");
        estudiante.setTelefono("1230456897");
        estudiante.setIdioma("español");

        entityManager.persist(estudiante);

        Estudiante encontrado = estudianteDao.findById(estudiante.getId()).orElse(null);

        assertNotNull(encontrado);
        assertEquals("Ana", encontrado.getNombre());
    }

    @Test
    void listarTodos_RetornaListaNoVacia() {
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

        entityManager.persist(estudiante1);
        entityManager.persist(estudiante2);

        List<Estudiante> estudiantes = estudianteDao.findAll();

        assertEquals(2, estudiantes.size());
    }


    @Test
    void existsByCorreo_CorreoExistente_RetornaTrue() {
        String correo = "maria@example.com";
        Estudiante estudiante1 = new Estudiante();
        estudiante1.setNombre("Maria");
        estudiante1.setCorreo(correo);
        estudiante1.setTelefono("1230456897");
        estudiante1.setIdioma("español");

        entityManager.persist(estudiante1);

        boolean existe = estudianteDao.existsByCorreo(correo);

        assertTrue(existe);
    }

    @Test
    void existsByCorreo_CorreoInexistente_RetornaFalse() {
        boolean existe = estudianteDao.existsByCorreo("noexiste@mail.com");

        assertFalse(existe);
    }


    @Test
    void eliminarEstudiante_SeEliminaCorrectamente() {
        Estudiante estudiante1 = new Estudiante();
        estudiante1.setNombre("Carlos");
        estudiante1.setCorreo("carlos@mail.com");
        estudiante1.setTelefono("1230456897");
        estudiante1.setIdioma("español");
        entityManager.persist(estudiante1);

        estudianteDao.deleteById(estudiante1.getId());

        assertFalse(estudianteDao.existsById(estudiante1.getId()));
    }
}