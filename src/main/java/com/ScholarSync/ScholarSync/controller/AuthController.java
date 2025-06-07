package com.ScholarSync.ScholarSync.controller;

import com.ScholarSync.ScholarSync.entity.Maestro;
import com.ScholarSync.ScholarSync.repositorio.MaestroDao;
import com.ScholarSync.ScholarSync.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    @Autowired
    private MaestroDao maestroDao;

    @Autowired
    private JWTUtils jwtutil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody Maestro maestro) {

        Maestro maestroLogeado = maestroDao.obtenerMaestroPorCredenciales(maestro);

        if (maestroLogeado != null){

            String tokenJwt = jwtutil.create(String.valueOf(maestroLogeado.getId()),maestroLogeado.getCorreo());

            return tokenJwt;

        }
        return "FAIL";
    }

}
