package com.github.voragoth.drugstores.facade.impl;

import com.github.voragoth.drugstores.facade.AdminDivisionsFacade;
import com.github.voragoth.drugstores.constants.ErrorMessagesConstants;
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
    public Map<String, String> getCommunes(String region) {
        log.info("Obteniendo comunas");
        Map<String, String> communes = adminDivisionsProviderService.getCommunes(region);
        log.info("{} comunas obtenidas", MapUtils.size(communes));
        if(log.isDebugEnabled()) {
            log.debug("Comunas: {}", communes);
        }
        if (MapUtils.isEmpty(communes)) {
            log.error("0 comunas obtenidas");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessagesConstants.COMMUNES_NOT_FOUND);
        }
        return communes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getRegions() {
        log.info("Obteniendo regiones");
        Map<String, String> regions = adminDivisionsProviderService.getRegions();
        log.info("{} regiones obtenidas", MapUtils.size(regions));
        if(log.isDebugEnabled()) {
            log.debug("Regiones: {}", regions);
        }
        if (MapUtils.isEmpty(regions)) {
            log.error("0 regiones obtenidas");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessagesConstants.REGIONS_NOT_FOUND);
        }
        return regions;
    }
}
