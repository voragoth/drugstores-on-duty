package com.github.voragoth.drugstores.service.impl;

import com.github.voragoth.drugstores.constants.ApplicationConstants;
import com.github.voragoth.drugstores.feign.client.AdminDivisionsFeignClient;
import com.github.voragoth.drugstores.mapper.DrugstoreOnDutyMapper;
import com.github.voragoth.drugstores.service.AdminDivisionsProviderService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementacion del servicio que obtiene las comunas y regiones.
 *
 * @author Manuel Vasquez Cruz
 */
@Slf4j
@Component
public class AdminDivisionsProviderServiceImpl implements AdminDivisionsProviderService {

    /**
     * El cliente feign que conecta al servicio con las comunas y regiones
     */
    private AdminDivisionsFeignClient adminDivisionsFeignClient;

    /**
     * El mapper para separar logica.
     */
    private DrugstoreOnDutyMapper drugstoreOnDutyMapper;

    /**
     * Constructor necesario para inyectar las dependencias.
     *
     * @param adminDivisionsFeignClient el cliente feign.
     * @param drugstoreOnDutyMapper     el mapper.
     */
    public AdminDivisionsProviderServiceImpl(AdminDivisionsFeignClient adminDivisionsFeignClient,
                                             DrugstoreOnDutyMapper drugstoreOnDutyMapper) {
        this.adminDivisionsFeignClient = adminDivisionsFeignClient;
        this.drugstoreOnDutyMapper = drugstoreOnDutyMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getCommunes(String region) {
        Map<String, Object> formData = new HashMap<>();
        formData.put(ApplicationConstants.REGION_ID_FEIGN_KEY, region);
        log.info("Invocando el cliente feign para obtener comunas");
        Document document = adminDivisionsFeignClient.getCommunes(formData);
        log.info("Respuesta comunas obtenida");
        if(log.isDebugEnabled()) {
            log.debug("Respuesta original Comunas: {}", document.body().toString());
        }
        Elements elements = document.select("option");
        return drugstoreOnDutyMapper.mapElementListToMap(
                elements.stream().filter(el -> !el.hasAttr("selected")).collect(Collectors.toList())
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getRegions() {
        log.info("Invocando el cliente feign para obtener regiones");
        Document document = adminDivisionsFeignClient.getRegions();
        log.info("Respuesta regiones obtenida");
        if(log.isDebugEnabled()) {
            log.debug("Respuesta original Regiones: {}", document.body().toString());
        }
        Elements elements = document.select("option");
        return drugstoreOnDutyMapper.mapElementListToMap(
                elements.stream().filter(el -> !el.hasAttr("selected")).collect(Collectors.toList())
        );
    }
}
