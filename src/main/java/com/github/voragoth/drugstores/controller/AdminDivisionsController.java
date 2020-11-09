package com.github.voragoth.drugstores.controller;

import com.github.voragoth.drugstores.facade.AdminDivisionsFacade;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * La clase de controlodor para disponibilizar comunas y regiones.
 *
 * @author Manuel Vasquez Cruz
 */
@Validated
@RestController
@RequestMapping("/api")
public class AdminDivisionsController {

    /**
     * La fachada para la obtencion de regiones y comunas.
     */
    private AdminDivisionsFacade adminDivisionsFacade;

    /**
     * Constructor para enlazar las dependencias del controlador.
     *
     * @param adminDivisionsFacade La fachada con la logica de negocio.
     */
    public AdminDivisionsController(AdminDivisionsFacade adminDivisionsFacade) {
        this.adminDivisionsFacade = adminDivisionsFacade;
    }

    /**
     * Metodo que disponibiliza el endpoint para obtener las comunas en base a una region.
     *
     * @param region la region para filtrar las comunas.
     * @return la lista comuna-llave de comunas pertenecientes a la region.
     */
    @GetMapping("/v1/comunas")
    public Map<String, String> getCommunes(
            @RequestParam(name = "region", required = false, defaultValue = "${drugstores.defaultRegion:7}")
                    String region) {
        return adminDivisionsFacade.getCommunes(region);
    }

    /**
     * Metodo que disponibiliza las regiones.
     *
     * @return la lista comuna-llave de regiones.
     */
    @GetMapping("/v1/regiones")
    public Map<String, String> getRegions() {
        return adminDivisionsFacade.getRegions();
    }
}
