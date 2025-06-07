package com.ScholarSync.ScholarSync.repositorio;

import com.ScholarSync.ScholarSync.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstudianteDao extends JpaRepository<Estudiante, Long> {

    boolean existsByCorreo(String correo);



}
