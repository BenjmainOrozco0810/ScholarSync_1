package com.ScholarSync.ScholarSync.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "estudiantes")
@ToString
@EqualsAndHashCode
public class Estudiante{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 255, message = "Máximo 255 caracteres")
        @Column(name = "nombre")
        private String nombre;

        @NotBlank
        @Email(message = "Correo inválido")
        @Column(unique = true,name = "correo")
        private String correo;

        @NotBlank
        @Pattern(regexp = "^\\d{10}$", message = "Teléfono debe tener 10 dígitos")
        @Column(name = "telefono")
        private String telefono;

        @NotBlank
        @Column(name = "idioma")
        private String idioma; // Validar con enum después
}
