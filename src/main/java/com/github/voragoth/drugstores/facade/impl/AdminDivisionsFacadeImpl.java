package com.github.voragoth.drugstores.facade.impl;

import com.github.voragoth.drugstores.facade.AdminDivisionsFacade;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Implementacion para la fachada de obtencion de comunas y regiones.
 *
 * @author Manuel Vasquez Cruz.
 */
@Service
public class AdminDivisionsFacadeImpl implements AdminDivisionsFacade {

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Integer> getCommunes(Byte region) {
        throw new IllegalStateException("metodo aun no implementado");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Integer> getRegions() {
        throw new IllegalStateException("metodo aun no implementado");
    }
}
