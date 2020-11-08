package com.github.voragoth.drugstores.service.impl;

import com.github.voragoth.drugstores.service.AdminDivisionsProviderService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Implementacion del servicio que obtiene las comunas y regiones.
 *
 * @author Manuel Vasquez Cruz
 */
@Service
public class AdminDivisionsProviderServiceImpl implements AdminDivisionsProviderService {

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
        return null;
    }
}
