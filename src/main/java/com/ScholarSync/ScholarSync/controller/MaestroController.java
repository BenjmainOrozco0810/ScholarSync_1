package com.ScholarSync.ScholarSync.controller;

import com.ScholarSync.ScholarSync.entity.Maestro;
import com.ScholarSync.ScholarSync.repositorio.MaestroDao;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaestroController {

    @Autowired
    MaestroDao maestroDao;

    @RequestMapping(value = "api/maestro", method = RequestMethod.POST)
    public void registrarMaestro(@RequestBody Maestro maestro) {

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash =  argon2.hash(1,1024,1,maestro.getPassword());
        maestro.setPassword(hash);
        maestroDao.registrar(maestro);

    }
}
