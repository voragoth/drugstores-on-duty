package com.github.voragoth.drugstores.facade;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Contrato para la fachada que orquesta la obtencion de comunas y regiones.
 *
 * @author Manuel Vasquez Cruz.
 */
public interface AdminDivisionsFacade {

    /**
     * Metodo que retorna las comunas en base a una region.
     *
     * @param region la region para filtrar las comunas.
     * @return la lista comuna-llave de comunas pertenecientes a la region.
     */
    Map<String, Integer> getCommunes(Integer region);

    /**
     * Metodo que retorna las regiones.
     *
     * @return la lista comuna-llave de regiones.
     */
    Map<String, Integer> getRegions();
}
