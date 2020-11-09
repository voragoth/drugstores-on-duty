package com.github.voragoth.drugstores.facade.impl;

import com.github.voragoth.drugstores.dto.Drugstore;
import com.github.voragoth.drugstores.facade.DrugstoresOnDutyFacade;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementacion para la fachada de la logica de negocio de farmacias de turno.
 *
 * @author Manuel Vasquez Cruz.
 */
@Service
public class DrugstoresOnDutyFacadeImpl implements DrugstoresOnDutyFacade {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Drugstore> getDrugStoresOnDuty(String brand, String commune, String region) {
        throw new IllegalStateException("metodo aun no implementado");
    }
}
