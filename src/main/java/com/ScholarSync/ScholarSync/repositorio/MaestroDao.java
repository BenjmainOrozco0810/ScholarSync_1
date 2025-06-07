package com.ScholarSync.ScholarSync.repositorio;

import com.ScholarSync.ScholarSync.entity.Estudiante;
import com.ScholarSync.ScholarSync.entity.Maestro;

import java.util.List;

public interface MaestroDao {

    List<Maestro> getMaestros();

    void registrar(Maestro maestro);

    Maestro obtenerMaestroPorCredenciales(Maestro maestro);
}
