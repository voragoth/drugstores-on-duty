package com.github.voragoth.drugstores.facade.impl;

import com.github.voragoth.drugstores.facade.AdminDivisionsFacade;
import com.github.voragoth.drugstores.handler.ErrorMessagesConstants;
import com.github.voragoth.drugstores.service.AdminDivisionsProviderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/**
 * Implementacion para la fachada de obtencion de comunas y regiones.
 *
 * @author Manuel Vasquez Cruz.
 */
@Slf4j
@Service
public class AdminDivisionsFacadeImpl implements AdminDivisionsFacade {

    /**
     * Servicio que provee de las regiones y comunas.
     */
    private AdminDivisionsProviderService adminDivisionsProviderService;

    /**
     * Constructor de la fachada.
     *
     * @param adminDivisionsProviderService servicio que provee las regiones y comunas
     */
    public AdminDivisionsFacadeImpl(AdminDivisionsProviderService adminDivisionsProviderService) {
        this.adminDivisionsProviderService = adminDivisionsProviderService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Integer> getCommunes(Byte region) {
        Map<String, Integer> communes = adminDivisionsProviderService.getCommunes(region);
        log.info("Comunas: {}", communes);
        if (MapUtils.isEmpty(communes)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessagesConstants.COMMUNES_NOT_FOUND);
        }
        return communes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Integer> getRegions() {
        Map<String, Integer> regions = adminDivisionsProviderService.getRegions();
        log.info("Regiones: {}", regions);
        if (MapUtils.isEmpty(regions)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessagesConstants.REGIONS_NOT_FOUND);
        }
        return regions;
    }
}
