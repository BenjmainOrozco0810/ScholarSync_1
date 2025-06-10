package com.ScholarSync.ScholarSync.controller;

import com.ScholarSync.ScholarSync.entity.Estudiante;
import com.ScholarSync.ScholarSync.repositorio.EstudianteDao;
import com.ScholarSync.ScholarSync.service.EstudianteService;
import com.ScholarSync.ScholarSync.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private EstudianteDao estudianteRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping (value = "api/estudiantes", method = RequestMethod.GET)
    public List<Estudiante> getEstudiantes(@RequestHeader (value = "Authorization") String token) {

        if (!validarToken(token)){return null;}

        return estudianteService.getEstudiante();

    }

    public boolean validarToken(String token){
        String maestorId = jwtUtils.getKey(token);
        return maestorId != null;
    }

    @RequestMapping (value = "api/estudiante", method = RequestMethod.POST)
    public void registrarEstudiante(@RequestBody Estudiante estudiante) {
        estudianteService.crearEstudiante(estudiante);

    }

    @RequestMapping (value = "api/estudiante/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader (value = "Authorization") String token,@PathVariable Long id) {
        if (!validarToken(token)){return;}
        estudianteService.eliminarEstudiante(id);
    }
    @GetMapping("/api/estudiantes/{id}")
    public ResponseEntity<Estudiante> getEstudiante(@PathVariable Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));
        return ResponseEntity.ok(estudiante);
    }
    @PutMapping("/api/estudiantes/{id}")
    public ResponseEntity<Estudiante> actualizarEstudiante(@PathVariable Long id, @RequestBody Estudiante estudianteDetails) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));

        estudiante.setNombre(estudianteDetails.getNombre());
        estudiante.setCorreo(estudianteDetails.getCorreo());
        estudiante.setTelefono(estudianteDetails.getTelefono());
        estudiante.setIdioma(estudianteDetails.getIdioma());

        Estudiante updatedEstudiante = estudianteRepository.save(estudiante);
        return ResponseEntity.ok(updatedEstudiante);
    }
    @PatchMapping("/api/estudiantes/{id}/contacto")
    public ResponseEntity<Estudiante> actualizarContacto(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));

        if (updates.containsKey("correo")) {
            estudiante.setCorreo(updates.get("correo"));
        }
        if (updates.containsKey("telefono")) {
            estudiante.setTelefono(updates.get("telefono"));
        }

        Estudiante updatedEstudiante = estudianteRepository.save(estudiante);
        return ResponseEntity.ok(updatedEstudiante);
    }
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }


}