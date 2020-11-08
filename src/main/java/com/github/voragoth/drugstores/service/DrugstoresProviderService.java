package com.github.voragoth.drugstores.service;

import com.github.voragoth.drugstores.dto.Drugstore;

import java.util.List;

/**
 * Contrato para el servicio que obtiene las farmacias de turno.
 *
 * @author Manuel Vasquez Cruz
 */
public interface DrugstoresProviderService {

    /**
     * Metodo con la logica de negocio para obtener las farmacias filtradas por local, comuna y/o region.
     *
     * @param brand   el local para filtrar la farmacia.
     * @param commune la comuna para filtrar la farmacia.
     * @param region  la region para filtrar la farmacia.
     * @return la lista de farmacias filtradas.
     */
    List<Drugstore> getDrugStoresOnDuty(String brand, String commune, Integer region);
}
