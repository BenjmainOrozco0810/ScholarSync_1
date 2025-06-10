package com.ScholarSync.ScholarSync.service;
import com.ScholarSync.ScholarSync.entity.Estudiante;
import com.ScholarSync.ScholarSync.repositorio.EstudianteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EstudianteService {
    @Autowired
    private EstudianteDao estudianteRepository;

    @Transactional
    public Estudiante crearEstudiante(Estudiante estudiante) {


        if (estudianteRepository.existsByCorreo(estudiante.getCorreo()))
            throw new IllegalArgumentException("El correo ya está registrado");


        List<String> idiomasPermitidos = List.of("ingles", "español", "frances");
        if (!idiomasPermitidos.contains(estudiante.getIdioma().toLowerCase()))
            throw new IllegalArgumentException("Idioma no válido. Use: ingles, español o frances");
        return estudianteRepository.save(estudiante);

    }

    public List<Estudiante> getEstudiante() {
        return estudianteRepository.findAll();
    }


    @Transactional
    public void eliminarEstudiante(Long id) {
        if (!estudianteRepository.existsById(id)) {
            throw new IllegalArgumentException("ID de estudiante no válido");
        }
        estudianteRepository.deleteById(id);
    }
}
