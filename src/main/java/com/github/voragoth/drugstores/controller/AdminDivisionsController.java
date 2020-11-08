package com.github.voragoth.drugstores.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * La clase de controlodor para disponibilizar comunas y regiones.
 *
 * @author Manuel Vasquez Cruz
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class AdminDivisionsController {


    /**
     * Metodo que disponibiliza el endpoint para obtener las comunas en base a una region.
     *
     * @param region la region para filtrar las comunas.
     * @return la lista comuna-llave de comunas pertenecientes a la region.
     */
    @GetMapping("/v1/comunas")
    public Map<String, Integer> getCommunes(@RequestParam(name = "region", required = false,
            defaultValue = "${drugstores.defaultRegion:7}") Integer region) {
        return new HashMap<>();
    }

    /**
     * Metodo que disponibiliza las regiones.
     *
     * @return la lista comuna-llave de regiones.
     */
    @GetMapping("/v1/regiones")
    public Map<String, Integer> getRegions() {
        return new HashMap<>();
    }
}
