package com.ScholarSync.ScholarSync.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

class EstudianteValidationTest {

    private static Validator validator;

    @BeforeAll
    static void before() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void cuandoNombreEsValido_entoncesNoViolaciones() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Ana Pérez");
        estudiante.setCorreo("ana@mail.com");
        estudiante.setTelefono("1234567890");
        estudiante.setIdioma("español");

        Set<ConstraintViolation<Estudiante>> violations = validator.validate(estudiante);

        assertThat(violations).isEmpty();
    }

    @Test
    void cuandoNombreEsVacio_entoncesViolacionNotBlank() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(""); // Inválido
        estudiante.setCorreo("test@mail.com");
        estudiante.setTelefono("1234567890");
        estudiante.setIdioma("ingles");

        Set<ConstraintViolation<Estudiante>> violations = validator.validate(estudiante);

        assertThat(violations)
                .extracting("message")
                .contains("El nombre es obligatorio");
    }

    @Test
    void cuandoCorreoEsInvalido_entoncesViolacionEmail() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Juan");
        estudiante.setCorreo("correo-invalido"); // Inválido
        estudiante.setTelefono("0987654321");
        estudiante.setIdioma("frances");

        Set<ConstraintViolation<Estudiante>> violations = validator.validate(estudiante);

        assertThat(violations)
                .extracting("message")
                .contains("Correo inválido");
    }

    @Test
    void cuandoTelefonoNoTiene10Digitos_entoncesViolacionPattern() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Luisa");
        estudiante.setCorreo("luisa@mail.com");
        estudiante.setTelefono("12345"); // Inválido
        estudiante.setIdioma("español");

        Set<ConstraintViolation<Estudiante>> violations = validator.validate(estudiante);

        assertThat(violations)
                .extracting("message")
                .contains("Teléfono debe tener 10 dígitos");
    }

    @Test
    void cuandoIdiomaEsVacio_entoncesViolacionNotBlank() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Carlos");
        estudiante.setCorreo("carlos@mail.com");
        estudiante.setTelefono("1234567890");
        estudiante.setIdioma(""); // Inválido

        Set<ConstraintViolation<Estudiante>> violations = validator.validate(estudiante);

        assertThat(violations)
                .extracting("message")
                .containsAnyOf("no debe estar vacío", "no debe estar en blanco");
    }
}