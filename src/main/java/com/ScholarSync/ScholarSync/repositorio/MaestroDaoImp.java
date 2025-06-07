package com.ScholarSync.ScholarSync.repositorio;

import com.ScholarSync.ScholarSync.entity.Maestro;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class MaestroDaoImp implements MaestroDao {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    @Transactional
    public List<Maestro> getMaestros(){
        String query = "From Maestro";
        return entityManager.createQuery(query).getResultList();
    }


    @Override
    public void registrar(Maestro maestro) {
        entityManager.merge(maestro);
    }
    @Override
    public Maestro obtenerMaestroPorCredenciales(Maestro maestro) {
        String query = "FROM Maestro WHERE correo = :correo";
        List<Maestro> lista =entityManager.createQuery(query)
                .setParameter("correo",maestro.getCorreo())
                .getResultList();

        if (lista.isEmpty()){
            return null;
        }

        String passwordHashed = lista.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHashed,maestro.getPassword())){
            return lista.get(0);
        }
        return null;

    }
}
