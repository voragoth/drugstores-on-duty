package com.github.voragoth.drugstores.service.impl;

import com.github.voragoth.drugstores.dto.Drugstore;
import com.github.voragoth.drugstores.service.DrugstoresProviderService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementacion del servicio que obtiene y filtra las farmacias de turno.
 *
 * @author Manuel Vasquez Cruz
 */
@Service
public class DrugstoresProviderServiceImpl implements DrugstoresProviderService {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Drugstore> getDrugStoresOnDuty(String brand, String commune, Integer region) {
        throw new IllegalStateException("metodo aun no implementado");
    }
}
