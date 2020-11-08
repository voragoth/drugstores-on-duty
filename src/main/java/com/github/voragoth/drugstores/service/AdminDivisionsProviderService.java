package com.github.voragoth.drugstores.service;

import java.util.Map;

/**
 * Contrato para el servicio que obtiene las comunas y regiones.
 *
 * @author Manuel Vasquez Cruz
 */
public interface AdminDivisionsProviderService {

    /**
     * Metodo que retorna las comunas en base a una region.
     *
     * @param region la region para filtrar las comunas.
     * @return la lista comuna-llave de comunas pertenecientes a la region.
     */
    Map<String, Integer> getCommunes(Byte region);

    /**
     * Metodo que retorna las regiones.
     *
     * @return la lista comuna-llave de regiones.
     */
    Map<String, Integer> getRegions();
}
