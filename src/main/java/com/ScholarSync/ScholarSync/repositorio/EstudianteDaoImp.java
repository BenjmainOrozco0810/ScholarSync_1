//package com.ScholarSync.ScholarSync.repositorio;
//import com.ScholarSync.ScholarSync.entity.Estudiante;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Repository
//@Transactional
//public class EstudianteDaoImp    {
//
//    @PersistenceContext
//    EntityManager entytiManager;
//
//    @Override
//    @Transactional
//    public List<Estudiante> getEstudiante() {
//        String query = "FROM Estudiante";
//        return entytiManager.createQuery(query).getResultList();
//    }
//
//    @Override
//    public void eliminar(Long id) {
//        Estudiante usuario = entytiManager.find(Estudiante.class,id);
//        entytiManager.remove(usuario);
//    }
//
//    @Override
//    public void registrar(Estudiante estudiante) {
//        entytiManager.merge(estudiante);
//    }
//
//
//
//}
//
